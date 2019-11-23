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

/**
 * FXML Controller class
 *
 * @author Sam
 */
public class ModifyPartController implements Initializable {
    Inventory inv;
    Part modifyPart;
    InhousePart inhousePart;
    OutsourcedPart outsourcePart;
    
    @FXML
    private RadioButton ModifyInhousePartRadio;

    @FXML
    private RadioButton ModifyOutsourcedPartRadio;

    @FXML
    private TextField ModifyPartIDTextField;

    @FXML
    private TextField ModifyPartNameTextField;

    @FXML
    private TextField ModifyPartInvTextField;

    @FXML
    private TextField ModifyPartPriceTextField;

    @FXML
    private TextField ModifyPartInvMaxTextField;

    @FXML
    private TextField ModifyPartInvMinTextField;

    @FXML
    private TextField ModifyPartMachineCompanyTextField;

    @FXML
    private Label MachineCompanyLabel;

    @FXML
    private Button ModifyPartSaveButton;

    @FXML
    private Button ModifyPartCancelButton;

    public ModifyPartController(Inventory inv, Part modifyPart) {
        this.inv = inv;
        this.modifyPart = modifyPart;
    }
    
    private void populatePartData() {
        ModifyPartIDTextField.setText(Integer.toString(modifyPart.getId()));
        ModifyPartNameTextField.setText(modifyPart.getName());
        ModifyPartInvTextField.setText(Integer.toString(modifyPart.getStock()));
        ModifyPartPriceTextField.setText(Double.toString(modifyPart.getPrice()));
        ModifyPartInvMaxTextField.setText(Integer.toString(modifyPart.getMax()));
        ModifyPartInvMinTextField.setText(Integer.toString(modifyPart.getMin()));
        
        if(inhousePart != null) {
            ModifyPartMachineCompanyTextField.setText(Integer.toString(inhousePart.getMachineId()));
        } else {
            ModifyPartMachineCompanyTextField.setText(outsourcePart.getCompanyName());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup radioGroup = new ToggleGroup();
        ModifyInhousePartRadio.setToggleGroup(radioGroup);
        ModifyOutsourcedPartRadio.setToggleGroup(radioGroup);
            
        if(modifyPart instanceof InhousePart) {
            this.inhousePart = (InhousePart) modifyPart;
            this.outsourcePart = null;
            ModifyInhousePartRadio.setSelected(true);
            ModifyOutsourcedPartRadio.setSelected(false);
 
        } else {
            this.outsourcePart = (OutsourcedPart) modifyPart;
            this.inhousePart = null;
            ModifyOutsourcedPartRadio.setSelected(true);
            ModifyInhousePartRadio.setSelected(false);
        }
        
        populatePartData();
        
    }    


    @FXML
    private void ModifyInhousePart(MouseEvent event) {
        if(ModifyInhousePartRadio.isSelected()) {
            ModifyPartMachineCompanyTextField.setText("Machine");
        }
    }

    @FXML
    private void ModifyOutsourcedPart(MouseEvent event) {
         if(ModifyOutsourcedPartRadio.isSelected()) {
            ModifyPartMachineCompanyTextField.setText("Company");
        }
    }

    @FXML
    private void ModifyPartSave(MouseEvent event) {
        if(ModifyInhousePartRadio.isSelected()) {
            InhousePart newPart = getNewInhousePartValues();
            inv.getAllParts().set(inv.getAllParts().indexOf(modifyPart), newPart);
        
        }
        
        if (ModifyOutsourcedPartRadio.isSelected()) {
            OutsourcedPart newPart = getNewOutsourcedPartValues();
            inv.getAllParts().set(inv.getAllParts().indexOf(modifyPart), newPart);
        }
        
        changeToMainScene(event);
    }
    
      private InhousePart getNewInhousePartValues() {
            int machineId = Integer.parseInt(ModifyPartMachineCompanyTextField.getText());
            int id = modifyPart.getId();
            String name = ModifyPartNameTextField.getText();
            double price = Double.parseDouble(ModifyPartPriceTextField.getText());
            int stock = Integer.parseInt(ModifyPartInvTextField.getText());
            int min = Integer.parseInt(ModifyPartInvMinTextField.getText());
            int max = Integer.parseInt(ModifyPartInvMaxTextField.getText());
            
            return new InhousePart(id, name, price, stock, min, max, machineId);
    }
    
       private OutsourcedPart getNewOutsourcedPartValues() {
            String companyName = ModifyPartMachineCompanyTextField.getText();
            int id = modifyPart.getId();
            String name = ModifyPartNameTextField.getText();
            double price = Double.parseDouble(ModifyPartPriceTextField.getText());
            int stock = Integer.parseInt(ModifyPartInvTextField.getText());
            int min = Integer.parseInt(ModifyPartInvMinTextField.getText());
            int max = Integer.parseInt(ModifyPartInvMaxTextField.getText());
            
            return new OutsourcedPart(id, name, price, stock, min, max, companyName);
    }

    @FXML
    private void ModifyPartCancel(MouseEvent event) {
        changeToMainScene(event);
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
