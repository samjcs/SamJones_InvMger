/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 *
 * @author Sam
 */
public class Product {
    ObservableList<Part> associatedParts;
    int id;
    String name;
    double price;
    int stock;
    int min;
    int max;
    
    public Product(int id, String name, double price, int stock, int min, int max) {
        associatedParts = FXCollections.observableArrayList();
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max); 
    }
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price;}
    public void setStock(int stock) { this.stock = stock; }
    public void setMin(int min) { this.min = min; }
    public void setMax(int max) { this.max = max; }
    public void setPrice(int max) {this.max = max; }
    public int getId() { return this.id;}
    public String getName() { return this.name; }
    public double getPrice(){ return this.price; }
    public int getStock() { return this.stock; }
    public int getMin() { return this.min; }
    public int getMax() { return this.max; } 
   
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    public void deleteAssociatedPart(Part associatedPart) {
        associatedParts.remove(associatedParts.indexOf(associatedPart));
    }
    
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }
}