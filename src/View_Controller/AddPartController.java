/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.*;
import java.io.IOException;
import java.net.URL;
import java.lang.Double;
import java.lang.Integer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

/**
 * FXML Controller class
 *
 * @author Sam
 */
public class AddPartController implements Initializable {
    Inventory inv;
    
    @FXML
    private RadioButton inhousePartRadio;
    @FXML
    private RadioButton outsourcedPartRadio;
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
    private TextField machineIdAndCompanyField;
    @FXML
    private Label machineIdAndCompanyLabel;
    @FXML
    private Button savePartButton;
    @FXML
    private Button cancelAddPartButton;

    public AddPartController(Inventory inv) {
        this.inv = inv;
    }
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Set Toggle Group for radio buttons
        ToggleGroup radioGroup = new ToggleGroup();
        inhousePartRadio.setToggleGroup(radioGroup);
        outsourcedPartRadio.setToggleGroup(radioGroup);
        inhousePartRadio.setSelected(true);
    }    

    @FXML
    private void toggleInhousePart(MouseEvent event) {
        if(inhousePartRadio.isSelected()) {
            machineIdAndCompanyLabel.setText("Machine");
            machineIdAndCompanyField.setPromptText("Machine ID");
        }
    }

    @FXML
    private void toggleOutsourcedPart(MouseEvent event) {
        if (outsourcedPartRadio.isSelected()) {
            machineIdAndCompanyLabel.setText("Company");
            machineIdAndCompanyField.setPromptText("Company Name");
        }
        
    }

    @FXML
    private void savePart(MouseEvent event) {
        if(inhousePartRadio.isSelected()) {
            InhousePart newPart = getNewInhousePartValues();
            if(newPart != null && checkPartValues(newPart)){
                inv.addPart(newPart);
                changeToMainScene(event);
            }
        }
        
        if (outsourcedPartRadio.isSelected()) {
            OutsourcedPart newPart = getNewOutsourcedPartValues();
            if(newPart != null && checkPartValues(newPart)) {
                inv.addPart(newPart);
                changeToMainScene(event);
            }
        }
    }
    
    private InhousePart getNewInhousePartValues() {   
        try {
            int id = inv.getNextPartId();
            String name = nameField.getText();
            int machineId = Integer.parseInt(machineIdAndCompanyField.getText());
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());
            int min = Integer.parseInt(stockMinField.getText());
            int max = Integer.parseInt(stockMaxField.getText());
             return new InhousePart(id, name, price, stock, min, max, machineId);
        } catch(NumberFormatException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText("Invalid Data Part Data");
            alert.setContentText(String.format("All firelds are required and need to be in the following format: \n\n"
                    + "Name: Text \n"
                    + "Price: Decimal Number \n"
                    + "Stock: Whole Number \n"
                    + "Max: Whole Number \n"
                    + "Min: Whole Number \n"
                    + "Machine ID: Whole Number \n"));
            alert.show();

            return null;
        }
            
           
    }
    
       private OutsourcedPart getNewOutsourcedPartValues() {
           try {
                String companyName = machineIdAndCompanyField.getText();
                int id = inv.getNextPartId();
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                int min = Integer.parseInt(stockMinField.getText());
                int max = Integer.parseInt(stockMaxField.getText());
                return new OutsourcedPart(id, name, price, stock, min, max, companyName);
           } catch (NumberFormatException e) {
               Alert alert = new Alert(AlertType.WARNING);
               alert.setHeaderText("Invalid Data Part Data");
               alert.setContentText(String.format("All firelds are required and need to be in the following format: \n\n"
                       + "Name: Text \n"
                       + "Price: Decimal Number \n"
                       + "Stock: Whole Number \n"
                       + "Max: Whole Number \n"
                       + "Min: Whole Number \n"
                       + "Company Name: Text"));
               alert.show();
               
               return null;
           }
    }
    
    private boolean checkPartValues(Part newPart) {
        Alert alert = new Alert(AlertType.WARNING);
        
        if (newPart.getStock() < 1) {
            alert.setHeaderText("Inventory Level Error");
            alert.setContentText(String.format("Stock Level: %d Cannont be less then 1", newPart.getStock()));
            alert.show();
            return false;
        } else if (newPart.getPrice() < 0) {
            alert.setHeaderText("Part Price Error");
            alert.setContentText("Price cannot be less then 0.00");
            alert.show();
            return false;
        } else if (newPart.getStock() < newPart.getMin()) {
            alert.setHeaderText("Inventory level error");
            alert.setContentText(String.format("Stock Level: %d Cannont be less then minimum ammount: %d", newPart.getStock(), newPart.getMin()));
            alert.show();
            return false;
        } else if (newPart.getStock() > newPart.getMax()) {
            alert.setHeaderText("Inventory level error");
            alert.setContentText(String.format("Stock Level: %d Cannont be greater then maximum ammount: %d", newPart.getStock(), newPart.getMax()));
            alert.show();
            return false;
        } else if (newPart.getName().isEmpty()) {
            alert.setHeaderText("Invalid Data Entry");
            alert.setContentText(String.format("Part name %s cannot be empty", newPart.getName()));
            alert.show();
            return false;
        } else {
            return true;
        }
    }

    @FXML
    private void cancelAddPart(MouseEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Cancel Adding New Part");
        alert.setContentText("Are you sure you wish to cancel?");
        alert.showAndWait()
                .filter( response -> response == ButtonType.OK)
                .ifPresent(response -> changeToMainScene(event));     
    }
    
    private void changeToMainScene(MouseEvent event) {
  
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/samjones_invmger/MainScene.fxml"));
        samjones_invmger.MainSceneController controller = new samjones_invmger.MainSceneController(inv);
        loader.setController(controller);
         
        setStageAndScene(loader, event);   
    }
    
    private void setStageAndScene(FXMLLoader loader, MouseEvent event) {
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch(IOException ex) {
            Logger.getLogger(AddPartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
