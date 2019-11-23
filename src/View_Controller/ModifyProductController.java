/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Sam
 */
public class ModifyProductController implements Initializable {
    Inventory inv;
    @FXML
    private TextField ModifyProductIDTextField;
    @FXML
    private TextField ModifyProductNameTextField;
    @FXML
    private TextField ModifyProductInvTextField;
    @FXML
    private TextField ModifyProductPriceTextField;
    @FXML
    private TextField ModifyProductInvMaxTextField;
    @FXML
    private TextField ModifyProductInvMinTextField;
    @FXML
    private Button ModifyProductSaveButton;
    @FXML
    private Button AddProductCancelButton;
    @FXML
    private Button ModifyProductSearchButton;
    @FXML
    private TextField ModifyProductSearchTextField;
    @FXML
    private TableView<?> ModifyProductSearchTabe;
    @FXML
    private Button ModifyProductDeleteButton;
    @FXML
    private TableView<?> ModifyProductDeleteTabel;
    @FXML
    private Button ModifyProductAddButton;

    
    public ModifyProductController(Inventory inv) {
        this.inv = inv;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ModifyProductID(ActionEvent event) {
    }

    @FXML
    private void ModifyProductName(ActionEvent event) {
    }

    @FXML
    private void ModifyProductInv(ActionEvent event) {
    }

    @FXML
    private void ModifyProductPrice(ActionEvent event) {
    }

    @FXML
    private void ModifyProductInvMax(ActionEvent event) {
    }

    @FXML
    private void ModifyProductInvMin(ActionEvent event) {
    }

    @FXML
    private void ModifyProductSave(ActionEvent event) {
    }

    @FXML
    private void AddProductCancel(ActionEvent event) {
    }

    @FXML
    private void ModifyProductSearch(ActionEvent event) {
    }

    @FXML
    private void ModifyProductSearchText(ActionEvent event) {
    }

    @FXML
    private void ModifyProductDelete(ActionEvent event) {
    }

    @FXML
    private void ModiyProductAdd(ActionEvent event) {
    }
    
}
