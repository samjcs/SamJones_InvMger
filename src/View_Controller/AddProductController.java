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
public class AddProductController implements Initializable {
    Inventory inv;
    
    @FXML
    private TextField AddProductIDTextField;
    @FXML
    private TextField AddProductNameTextField;
    @FXML
    private TextField AddProductInvTextField;
    @FXML
    private TextField AddProductPriceTextField;
    @FXML
    private TextField AddProductInvMaxTextField;
    @FXML
    private TextField AddProductInvMinTextField;
    @FXML
    private Button AddProductSaveButton;
    @FXML
    private Button AddProductCancelButton;
    @FXML
    private Button AddProductSearchButton;
    @FXML
    private TextField AddProductSearchTextField;
    @FXML
    private TableView<?> AddProductSearchTable;
    @FXML
    private Button AddProductDeleteButton;
    @FXML
    private TableView<?> AddProductDeleteTable;
    @FXML
    private Button AddProductAddButton;

    
    public AddProductController(Inventory inv) {
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
    private void AddProductID(ActionEvent event) {
    }

    @FXML
    private void AddProductName(ActionEvent event) {
    }

    @FXML
    private void AddProductInv(ActionEvent event) {
    }

    @FXML
    private void AddProductPrice(ActionEvent event) {
    }

    @FXML
    private void AddProductInvMax(ActionEvent event) {
    }

    @FXML
    private void AddProductInvMin(ActionEvent event) {
    }

    @FXML
    private void AddProductSave(ActionEvent event) {
    }

    @FXML
    private void AddProductCancel(ActionEvent event) {
    }

    @FXML
    private void AddProductSearch(ActionEvent event) {
    }

    @FXML
    private void AddProductSearchText(ActionEvent event) {
    }

    @FXML
    private void AddProductDelete(ActionEvent event) {
    }

    @FXML
    private void AddProductAdd(ActionEvent event) {
    }
    
}
