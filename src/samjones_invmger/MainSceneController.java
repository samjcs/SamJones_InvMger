/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samjones_invmger;

import Model.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType; 
import javafx.scene.control.Alert; 
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author Sam
 */
public class MainSceneController implements Initializable {
    
    Inventory inv;
    
    ObservableList<Part> partInventory;
    ObservableList<Part> partSearchResults;
    ObservableList<Product> productInventory;
    ObservableList<Product> productInventorySearch;
    

    @FXML
    private Button SearchPartButton;
    @FXML
    private TextField partSearchField;
    @FXML
    private Button AddPartButton;
    @FXML
    private Button ModifyPartButton;
    @FXML
    private Button DeletePartButton;
    @FXML
    private TableView<Part> PartTable;
    @FXML
    private TableColumn<Part, ?> partId;
    @FXML
    private TableColumn<Part, ?> partName;
    @FXML
    private TableColumn<Part, ?> partInventoryLevel;
    @FXML
    private TableColumn<Part, ?> partPrice;
    @FXML
    private Button SearchProductButton;
    @FXML
    private TextField productSearchField;
    @FXML
    private Button AddProductButton;
    @FXML
    private Button ModifyProductButton;
    @FXML
    private Button DeleteProductButton;
    @FXML
    private TableView<Product> ProductTable;
    @FXML
    private TableColumn<Product, ?> productId;
    @FXML
    private TableColumn<Product, ?> productName;
    @FXML
    private TableColumn<Product, ?> productInventoryLevel;
    @FXML
    private TableColumn<Product, ?> productPrice;
    @FXML
    private Button ExitButton;

       public MainSceneController(Inventory inv) {
        this.partInventory = FXCollections.observableArrayList();
        this.productInventory = FXCollections.observableArrayList();
        this.partSearchResults = FXCollections.observableArrayList();
        this.productInventorySearch = FXCollections.observableArrayList();
        
        this.inv = inv;
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genPartTable();
        genProductTable();
    }    
    
    void genPartTable() {
        partInventory.setAll(inv.getAllParts());
        PartTable.setItems(partInventory);
        PartTable.refresh();
      
    }
    
    void genProductTable() {
        productInventory.setAll(inv.getAllProducts());
        ProductTable.setItems(productInventory);
        ProductTable.refresh();
    }
    
    @FXML
    private void SearchPart(MouseEvent event) {
        partSearchResults.clear();
        String searchQuery = partSearchField.getText();
        
        if(searchQuery.isEmpty()) {
            genPartTable();
        } else {
            try {
                int partId = Integer.parseInt(searchQuery);
                Part foundPart = inv.lookupPart(partId);

                partSearchResults.add(foundPart);
                PartTable.setItems(partSearchResults);
                PartTable.refresh();

            } catch (NumberFormatException e) {
                partSearchResults.setAll(inv.lookupPart(searchQuery));
                PartTable.setItems(partSearchResults);
                PartTable.refresh();
            }   
        }
    }

    @FXML
    private void AddPart(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/AddPart.fxml"));
            View_Controller.AddPartController controller = new View_Controller.AddPartController(inv);
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ModifyPart(MouseEvent event) throws IOException {
        if(PartTable.getSelectionModel().getSelectedItem()!= null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyPart.fxml"));
                View_Controller.ModifyPartController controller = new View_Controller.ModifyPartController(inv, PartTable.getSelectionModel().getSelectedItem());
                loader.setController(controller);
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }   
        } else {
            Alert warning = new Alert(AlertType.WARNING);
            warning.setContentText("No Part Selected");
            warning.setHeaderText("Please select part to modify");
            warning.show();
        }
    }

    @FXML
    private void DeletePart(MouseEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Delete Part");
        alert.setContentText("Are you sure you want to delete this part?");
        alert.showAndWait()
                .filter( response -> response == ButtonType.OK)
                .ifPresent((ButtonType response) -> {
                    inv.getAllParts().remove(inv.getAllParts().indexOf(PartTable.getSelectionModel().getSelectedItem()));
                    genPartTable();
                });
    }

    @FXML
    private void SearchProduct(MouseEvent event) {
        productInventorySearch.clear();
        String searchQuery = productSearchField.getText().trim();

        if (searchQuery.isEmpty()) {
            genPartTable();
        } else {
            try {
                int productId = Integer.parseInt(searchQuery);
                Product foundProduct = inv.lookupProduct(productId);

                productInventorySearch.add(foundProduct);
                ProductTable.setItems(productInventorySearch);
                ProductTable.refresh();

            } catch (NumberFormatException e) {
                productInventorySearch.setAll(inv.lookupProduct(searchQuery));
                ProductTable.setItems(productInventorySearch);
                ProductTable.refresh();
            }
        }
    
    }
    

    @FXML
    private void AddProduct(MouseEvent event) throws IOException {       
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/AddProduct.fxml"));
            View_Controller.AddProductController controller = new View_Controller.AddProductController(inv);
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ModifyProduct(MouseEvent event) throws IOException {
        
        if (ProductTable.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyProduct.fxml"));
                View_Controller.ModifyProductController controller = new View_Controller.ModifyProductController(inv, ProductTable.getSelectionModel().getSelectedItem());
                loader.setController(controller);
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert warning = new Alert(AlertType.WARNING);
            warning.setContentText("No Product Selected");
            warning.setHeaderText("Please select product to modify");
            warning.show();
        }
    }
        
  
    @FXML
    private void DeleteProduct(MouseEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Delete Product");
        alert.setContentText("Are you sure you want to delete this product?");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent((ButtonType response) -> {
                    inv.getAllProducts().remove(inv.getAllProducts().indexOf(ProductTable.getSelectionModel().getSelectedItem()));
                    genProductTable();
                });
    }

    @FXML
    private void ExitScene(MouseEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Exit Program");
        alert.setContentText("Are you sure you want to exit?");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent((ButtonType response) -> {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                });

    }
}
