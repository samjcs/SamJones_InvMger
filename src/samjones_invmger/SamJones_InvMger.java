/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samjones_invmger;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Sam
 */
public class SamJones_InvMger extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Inventory inv = new Inventory();
        addTestData(inv);
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        samjones_invmger.MainSceneController controller = new samjones_invmger.MainSceneController(inv);
        loader.setController(controller);
        
        Parent root = loader.load();

        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    void addTestData(Inventory inv) {
        //Declaring Inhouse Parts
        Part ihPartA = new InhousePart(1, "ihPartA", 1.01, 1, 1, 10, 1);
        Part ihPartB = new InhousePart(2, "ihPartB", 2.02, 2, 1, 10, 2);
        Part ihPartC = new InhousePart(3, "ihPartC", 3.03, 3, 1, 10, 3);
        Part ihPartD = new InhousePart(4, "ihPartD", 4.04, 4, 1, 10, 4);
        Part ihPartE = new InhousePart(5, "ihPartA", 5.05, 5, 1, 10, 5);
        inv.addPart(ihPartA);
        inv.addPart(ihPartB);
        inv.addPart(ihPartC);
        inv.addPart(ihPartD);
        inv.addPart(ihPartE);
        
        //Declaring Outsourced Parts
        Part osPartF = new OutsourcedPart(6, "osPartF", 6.06, 6, 1, 10, "osCompA");
        Part osPartG = new OutsourcedPart(7, "osPartG", 7.07, 7, 1, 10, "osCompB");
        Part osPartH = new OutsourcedPart(8, "osPartH", 8.08, 8, 1, 10, "osCompC");
        Part osPartI = new OutsourcedPart(9, "osPartI", 9.09, 9, 1, 10, "osCompD");
        Part osPartJ = new OutsourcedPart(10, "osPartJ", 10.10, 1, 1, 10, "osCompE");
        inv.addPart(osPartF);
        inv.addPart(osPartG);
        inv.addPart(osPartH);
        inv.addPart(osPartI);
        inv.addPart(osPartJ);
      
        
        //Declring Products and adding assosciated parts
        Product productA = new Product(1, "productA", 11.11, 1, 1, 10);
        productA.addAssociatedPart(ihPartA);
        productA.addAssociatedPart(osPartF);
        
        Product productB = new Product(2, "productB", 12.12, 2, 1, 10);
        productB.addAssociatedPart(ihPartB);
        productB.addAssociatedPart(osPartG);
        
        Product productC = new Product(3, "productC", 13.13, 3, 1, 10);
        productC.addAssociatedPart(ihPartC);
        productC.addAssociatedPart(osPartH);
        
        Product productD = new Product(4, "productD", 14.14, 4, 1, 10);
        productD.addAssociatedPart(ihPartD);
        productD.addAssociatedPart(osPartI);
        
        Product productE = new Product(5, "productE", 15.15, 5, 1, 10);
        productE.addAssociatedPart(ihPartE);
        productE.addAssociatedPart(osPartJ);
        
        inv.addProduct(productA);
        inv.addProduct(productB);
        inv.addProduct(productC);
        inv.addProduct(productD);
        inv.addProduct(productE);
    }
    
}
