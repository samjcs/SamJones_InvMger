/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sam
 */
public class ModifyProductController implements Initializable {
    Inventory inv;
    Product selectedProductFromMain;
    ObservableList<Part> loadParts;
    ObservableList<Part> partSearchResults;
    
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField stockField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField stockMaxField;
    @FXML
    private TextField stockMinField;
    @FXML
    private Button saveProduct;
    @FXML
    private Button cancelModifyProduct;
    @FXML
    private Button partButtonSearch;
    @FXML
    private TextField partSearchField;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<?, ?> partId;
    @FXML
    private TableColumn<?, ?> partName;
    @FXML
    private TableColumn<?, ?> partInventoryLevel;
    @FXML
    private TableColumn<?, ?> partPrice;
    @FXML
    private Button deleteAssociatedPart;
    @FXML
    private TableView<Part> associatedPartTable;
    @FXML
    private TableColumn<?, ?> associtedPartId;
    @FXML
    private TableColumn<?, ?> associtedPartName;
    @FXML
    private TableColumn<?, ?> associtedPartInventoryLevel;
    @FXML
    private TableColumn<?, ?> associtedPartPrice;
    @FXML
    private Button addAssociatedPart;

    
    public ModifyProductController(Inventory inv, Product product) {
        this.inv = inv;
        this.selectedProductFromMain = product;
        this.loadParts = FXCollections.observableArrayList();
        this.partSearchResults = FXCollections.observableArrayList();
        this.loadParts.setAll(product.getAllAssociatedParts());
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genPartTable();
        populateProductData(this.selectedProductFromMain);
    }    

    private void populateProductData(Product product) {
        idField.setText(Integer.toString(product.getId()));
        nameField.setText(product.getName());
        stockField.setText(Integer.toString(product.getStock()));
        priceField.setText(Double.toString(product.getPrice()));
        stockMaxField.setText(Integer.toString(product.getMax()));
        stockMinField.setText(Integer.toString(product.getMin()));
        
        
        genAssosciatedPartTable(this.loadParts);
    }
    
    @FXML
    private void saveProduct(MouseEvent event) {
        Product updatedProduct = getProductValues();
        if(updatedProduct != null && checkProductValues(updatedProduct)) {
            inv.updateProduct(inv.getAllProducts().indexOf(this.selectedProductFromMain), updatedProduct);
            changeToMainScene(event);
        }
    }

    private Product getProductValues() {
        try {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        double price = Double.parseDouble(priceField.getText());
        int stock = Integer.parseInt(stockField.getText());
        int min = Integer.parseInt(stockMinField.getText());
        int max = Integer.parseInt(stockMaxField.getText());

        Product updatedProduct = new Product(id, name, price, stock, min, max);

        for (Part part : associatedPartTable.getItems()) {
            updatedProduct.addAssociatedPart(part);
        }

        return updatedProduct;
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Invalid Data Product Data");
            alert.setContentText(String.format("All firelds are required and need to be in the following format: \n\n"
                    + "Name: Text \n"
                    + "Price: Decimal Number \n"
                    + "Stock: Whole Number \n"
                    + "Max: Whole Number \n"
                    + "Min: Whole Number \n"));
            alert.show();
            return null;
        }
    }
    
    private boolean checkProductValues(Product updatedProduct) {
        Alert alert = new Alert(Alert.AlertType.WARNING);

        if (updatedProduct.getStock() <= 1) {
            alert.setHeaderText("Inventory Level Error");
            alert.setContentText(String.format("Stock Level: %d Cannont be less then 1", updatedProduct.getStock()));
            alert.show();
            return false;
        } else if (updatedProduct.getStock() < updatedProduct.getMin()) {
            alert.setHeaderText("Inventory level error");
            alert.setContentText(String.format("Stock Level: %d Cannont be less then minimum ammount: %d", updatedProduct.getStock(), updatedProduct.getMin()));
            alert.show();
            return false;
        } else if (updatedProduct.getStock() > updatedProduct.getMax()) {
            alert.setHeaderText("Inventory level error");
            alert.setContentText(String.format("Stock Level: %d Cannont be greater then maximum ammount: %d", updatedProduct.getStock(), updatedProduct.getMax()));
            alert.show();
            return false;
        } else if (updatedProduct.getName().isEmpty()) {
            alert.setHeaderText("Invalid Data Entry");
            alert.setContentText(String.format("Product name %s cannot be empty", updatedProduct.getName()));
            alert.show();
            return false;
        } else if (updatedProduct.getAllAssociatedParts().size() < 1) {
            alert.setHeaderText("Missing Associated Parts");
            alert.setContentText("Product must contain at least one part");
            alert.show();
            return false;
        } else if (updatedProduct.getPrice() >= 0.00 && updatedProduct.getAllAssociatedParts().size() >= 1) {
            double totalPriceOfParts = 0.00;
            for (Part part : updatedProduct.getAllAssociatedParts()) {
                totalPriceOfParts += part.getPrice();
            }

            if (updatedProduct.getPrice() < totalPriceOfParts) {
                alert.setHeaderText("Invalid Prodcut Price");
                alert.setContentText(String.format("Product Price cannot be less then total price of associated parts"));
                alert.show();
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    @FXML
    private void searchPartInventory(MouseEvent event) {
        partSearchResults.clear();
        String searchQuery = partSearchField.getText();

        if (searchQuery.isEmpty()) {
            genPartTable();
        } else {
            try {
                int partId = Integer.parseInt(searchQuery);
                Part foundPart = inv.lookupPart(partId);

                partSearchResults.add(foundPart);
                partTable.setItems(partSearchResults);
                partTable.refresh();

            } catch (NumberFormatException e) {
                partSearchResults.setAll(inv.lookupPart(searchQuery));
                partTable.setItems(partSearchResults);
                partTable.refresh();
            }
        }
    }

   @FXML
    private void deletePartFromAssociatedList(MouseEvent event) {
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setHeaderText("Delete Part");
       alert.setContentText("Are you sure you want to delete this part?");
       alert.showAndWait()
               .filter(response -> response == ButtonType.OK)
               .ifPresent((ButtonType response) -> {
                   Part selectedPart = associatedPartTable.getSelectionModel().getSelectedItem();
                   ObservableList currentParts = associatedPartTable.getItems();
                   currentParts.remove(currentParts.indexOf(selectedPart));
                   associatedPartTable.setItems(currentParts);
                   associatedPartTable.refresh();
               });
    }

    @FXML
    private void addPartToAssociatedList(MouseEvent event) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        ObservableList currentParts = associatedPartTable.getItems();
        currentParts.add(selectedPart);
        associatedPartTable.setItems(currentParts);
        associatedPartTable.refresh();
    }
    
    private void genPartTable() {
        partTable.setItems(inv.getAllParts());
        partTable.refresh();
    }
    
    private void genAssosciatedPartTable(ObservableList<Part> parts) {
        associatedPartTable.setItems(parts);
        associatedPartTable.refresh();
    }
    
    private void changeToMainScene(MouseEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/samjones_invmger/MainScene.fxml"));
        samjones_invmger.MainSceneController controller = new samjones_invmger.MainSceneController(this.inv);
        loader.setController(controller);

        setStageAndScene(loader, event);
    }

    private void setStageAndScene(FXMLLoader loader, MouseEvent event) {
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ModifyProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void cancelModifyProduct(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Cancel Modifying Product");
        alert.setContentText("Are you sure you wish to cancel?");
        alert.showAndWait()
               .filter(response -> response == ButtonType.OK)
               .ifPresent((ButtonType response) -> {
                   changeToMainScene(event);
               });

    }
    
}
