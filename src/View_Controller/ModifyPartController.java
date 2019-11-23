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
    int partId;
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

    public ModifyPartController(Inventory inv, int partId) {
        this.inv = inv;
        this.partId = partId;
    }
    
    private void populateOutsourcedPartData() {
        ModifyPartIDTextField.setText(Integer.toString(outsourcePart.getId()));
        ModifyPartNameTextField.setText(outsourcePart.getName());
        ModifyPartInvTextField.setText(Integer.toString(outsourcePart.getStock()));
        ModifyPartPriceTextField.setText(Double.toString(outsourcePart.getPrice()));
        ModifyPartInvMaxTextField.setText(Integer.toString(outsourcePart.getMax()));
        ModifyPartInvMinTextField.setText(Integer.toString(outsourcePart.getMin()));
        ModifyPartMachineCompanyTextField.setText(outsourcePart.getCompanyName());
    }
    
    private void populateInhousePartData() {
        ModifyPartIDTextField.setText(Integer.toString(inhousePart.getId()));
        ModifyPartNameTextField.setText(inhousePart.getName());
        ModifyPartInvTextField.setText(Integer.toString(inhousePart.getStock()));
        ModifyPartPriceTextField.setText(Double.toString(inhousePart.getPrice()));
        ModifyPartInvMaxTextField.setText(Integer.toString(inhousePart.getMax()));
        ModifyPartInvMinTextField.setText(Integer.toString(inhousePart.getMin()));
        ModifyPartMachineCompanyTextField.setText(Integer.toString(inhousePart.getMachineId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup radioGroup = new ToggleGroup();
        ModifyInhousePartRadio.setToggleGroup(radioGroup);
        ModifyOutsourcedPartRadio.setToggleGroup(radioGroup);
        ObservableList<Part> partInventory = inv.getAllParts();
        
        for(Part part : partInventory) {
            if(part.getId() == this.partId) {
                if(part instanceof InhousePart) {
                    this.inhousePart = (InhousePart) part;
                    ModifyInhousePartRadio.setSelected(true);
                    ModifyOutsourcedPartRadio.setSelected(false);
                    populateInhousePartData();
                } else {
                    this.outsourcePart = (OutsourcedPart) part;
                    ModifyOutsourcedPartRadio.setSelected(true);
                    ModifyInhousePartRadio.setSelected(false);
                    populateOutsourcedPartData();
                }
                
            }
        }
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
    }

    @FXML
    private void ModifyPartCancel(MouseEvent event) {
        changeToMainScene(event);
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
            Logger.getLogger(ModifyPartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
