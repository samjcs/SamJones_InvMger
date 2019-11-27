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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.lang.Integer;
import java.lang.Double;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author Sam
 */
public class ModifyPartController implements Initializable {
    Inventory inv;
    Part partToUpdate;
    InhousePart inhousePart;
    OutsourcedPart outsourcePart;
    
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
    private Button updatePartButton;

    @FXML
    private Button cancelUpdatePartButton;

    public ModifyPartController(Inventory inv, Part partToUpdate) {
        this.inv = inv;
        this.partToUpdate = partToUpdate;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup radioGroup = new ToggleGroup();
        inhousePartRadio.setToggleGroup(radioGroup);
        outsourcedPartRadio.setToggleGroup(radioGroup);
            
        if(partToUpdate instanceof InhousePart) {
            this.inhousePart = (InhousePart) partToUpdate;
            this.outsourcePart = null;
            inhousePartRadio.setSelected(true);
 
        } else {
            this.outsourcePart = (OutsourcedPart) partToUpdate;
            this.inhousePart = null;
            outsourcedPartRadio.setSelected(true);
        }
        
        populatePartData();
    }

    private void populatePartData() {
        idField.setText(Integer.toString(partToUpdate.getId()));
        nameField.setText(partToUpdate.getName());
        stockField.setText(Integer.toString(partToUpdate.getStock()));
        priceField.setText(Double.toString(partToUpdate.getPrice()));
        stockMaxField.setText(Integer.toString(partToUpdate.getMax()));
        stockMinField.setText(Integer.toString(partToUpdate.getMin()));

        if (inhousePart != null) {
            machineIdAndCompanyField.setText(Integer.toString(inhousePart.getMachineId()));
        } else {
            machineIdAndCompanyField.setText(outsourcePart.getCompanyName());
        }
    }    


    @FXML
    private void toggleInhousePart(MouseEvent event) {
        if(inhousePartRadio.isSelected()) {
            machineIdAndCompanyField.setText("Machine ID");
            machineIdAndCompanyLabel.setText("Machine");
        }
    }

    @FXML
    private void toggleOutsourcedPart(MouseEvent event) {
         if(outsourcedPartRadio.isSelected()) {
            machineIdAndCompanyField.setText("Company Name");
            machineIdAndCompanyLabel.setText("Company");
        }
    }

    @FXML
    private void updatePart(MouseEvent event) {
        if(inhousePartRadio.isSelected()) {
            InhousePart newPart = getNewInhousePartValues();
            if(newPart != null && checkPartValues(newPart)) {
                inv.getAllParts().set(inv.getAllParts().indexOf(partToUpdate), newPart);
                changeToMainScene(event);
            }
        }
        
        if (outsourcedPartRadio.isSelected()) {
            OutsourcedPart newPart = getNewOutsourcedPartValues();
            if(newPart != null && checkPartValues(newPart)) {
               inv.getAllParts().set(inv.getAllParts().indexOf(partToUpdate), newPart);
               changeToMainScene(event);
            }
            
        }
    }
    
     private InhousePart getNewInhousePartValues() {
            try {
                int machineId = Integer.parseInt(machineIdAndCompanyField.getText());
                int id = partToUpdate.getId();
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                int min = Integer.parseInt(stockMinField.getText());
                int max = Integer.parseInt(stockMaxField.getText());
                return new InhousePart(id, name, price, stock, min, max, machineId);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
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
                int id = partToUpdate.getId();
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                int min = Integer.parseInt(stockMinField.getText());
                int max = Integer.parseInt(stockMaxField.getText());
                return new OutsourcedPart(id, name, price, stock, min, max, companyName);
           } catch (NumberFormatException e) {
               Alert alert = new Alert(Alert.AlertType.WARNING);
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
        Alert alert = new Alert(Alert.AlertType.WARNING);

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
    private void cancelUpdatePart(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Cancel Modifying Part");
        alert.setContentText("Are you sure you wish to cancel?");
        
        alert.showAndWait()
                .filter( response -> response == ButtonType.OK)
                .ifPresent(response -> changeToMainScene(event));

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
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch(IOException ex) {
            Logger.getLogger(ModifyPartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
