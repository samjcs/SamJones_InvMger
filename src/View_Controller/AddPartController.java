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
    ObservableList<Part> partInventory;
    ObservableList<Part> partInventorySearch;
    
    @FXML
    private RadioButton InhousePartRadio;
    @FXML
    private RadioButton OutsourcedPartRadio;
    @FXML
    private TextField AddPartIDTextField;
    @FXML
    private TextField AddPartNameTextField;
    @FXML
    private TextField AddPartInvTextField;
    @FXML
    private TextField AddPartPriceTextField;
    @FXML
    private TextField AddPartInvMaxTextField;
    @FXML
    private TextField AddPartInvMinTextField;
    @FXML
    private TextField AddPartMachineCompanyTextField;
    @FXML
    private Label AddPartMachineCompanyLabel;
    @FXML
    private Button AddPartSaveButton;
    @FXML
    private Button AddPartCancelButton;

    public AddPartController(Inventory inv) {
        this.inv = inv;
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Set Toggle Group for radio buttons
        ToggleGroup radioGroup = new ToggleGroup();
        InhousePartRadio.setToggleGroup(radioGroup);
        OutsourcedPartRadio.setToggleGroup(radioGroup);
        InhousePartRadio.setSelected(true);
        
        //init inventory
        partInventory = inv.getAllParts();
    }    

    @FXML
    private void CheckAddInhousePart(MouseEvent event) {
        if(InhousePartRadio.isSelected()) {
            AddPartMachineCompanyLabel.setText("Machine");
            AddPartMachineCompanyTextField.setText("Machine ID");
        }
    }

    @FXML
    private void CheckAddOutsourcedPart(MouseEvent event) {
        if (OutsourcedPartRadio.isSelected()) {
            AddPartMachineCompanyLabel.setText("Company");
            AddPartMachineCompanyTextField.setText("Company Name");
        }
        
    }

    @FXML
    private void AddPartSave(MouseEvent event) {

        
        if(InhousePartRadio.isSelected()) {
            InhousePart newPart = getNewInhousePartValues();
            inv.addPart(newPart);
        }
        
        if (OutsourcedPartRadio.isSelected()) {
            OutsourcedPart newPart = getNewOutsourcedPartValues();
            inv.addPart(newPart);
        }
        
        changeToMainScene(event);
    }
    
    private InhousePart getNewInhousePartValues() {
            int machineId = Integer.parseInt(AddPartMachineCompanyTextField.getText());
            int id = inv.getNextPartId();
            String name = AddPartNameTextField.getText();
            double price = Double.parseDouble(AddPartPriceTextField.getText());
            int stock = Integer.parseInt(AddPartInvTextField.getText());
            int min = Integer.parseInt(AddPartInvMinTextField.getText());
            int max = Integer.parseInt(AddPartInvMaxTextField.getText());
            
            return new InhousePart(id, name, price, stock, min, max, machineId);
    }
    
       private OutsourcedPart getNewOutsourcedPartValues() {
            String companyName = AddPartMachineCompanyTextField.getText();
            int id = inv.getNextPartId();
            String name = AddPartNameTextField.getText();
            double price = Double.parseDouble(AddPartPriceTextField.getText());
            int stock = Integer.parseInt(AddPartInvTextField.getText());
            int min = Integer.parseInt(AddPartInvMinTextField.getText());
            int max = Integer.parseInt(AddPartInvMaxTextField.getText());
            
            return new OutsourcedPart(id, name, price, stock, min, max, companyName);
    }
    
    private boolean checkInhousePartValues(InhousePart newPart) {
      

        return true;
    }

    @FXML
    private void AddPartCancel(MouseEvent event) {
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
            Logger.getLogger(AddPartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
