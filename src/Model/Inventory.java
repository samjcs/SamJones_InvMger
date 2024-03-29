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
public class Inventory {
    ObservableList<Part> allParts;
    ObservableList<Product> allProducts;
    
    public Inventory() {
        allParts = FXCollections.observableArrayList();
        allProducts = FXCollections.observableArrayList();
    }
    
    public void addPart(Part newPart) {
        allParts.add(newPart);
    }
    
    public void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    
    public Part lookupPart(int partId) {
        Part foundPart = null;
        for(Part part : allParts) {
            if(part.getId() == partId) {
                foundPart = part;
            }
        }
        
        return foundPart;
    }
    
    public ObservableList<Part> lookupPart(String searchQuery) {
         ObservableList<Part> foundParts = FXCollections.observableArrayList();
         
         for(Part part : allParts) {
             if(part.getName().contains(searchQuery)) {
                 foundParts.add(part);
             }
         }
         
         return foundParts;
    }
    
    void updatePart(int index, Part selectedPart) {
         allParts.set(index, selectedPart);
    }
    
    void deletePart(Part selectedPart) {
        allParts.remove(allParts.indexOf(selectedPart));
    }
    
    public ObservableList<Part> getAllParts() {
         return this.allParts;
     }
    
    public Product lookupProduct(int productId) {
        Product foundProduct = null;
        for(Product product : allProducts) {
            if(product.getId() == productId) {
                foundProduct = product;
            }
        }
        
        return foundProduct;
    }
    
     public ObservableList<Product> lookupProduct(String searchQuery) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        for(Product product : allProducts) {
            if(product.getName().contains(searchQuery)) {
                foundProducts.add(product);
            }
        }
        
        return foundProducts;
     }
     
    public void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }
    
    public void deleteProduct(Product selectedProduct) {
        allProducts.remove(allProducts.indexOf(selectedProduct));
    }
    
    public ObservableList<Product> getAllProducts() {
         return this.allProducts;
    }
    
    public int getNextProductId() {
        int nextId = 0;
        for (Product product : this.getAllProducts()) {
            if (product.getId() > nextId) {
                nextId = product.getId();
            }
        }
        
        return nextId;
    }
    
    public int getNextPartId() {
        int nextId = 0;
        for (Part part : this.getAllParts()) {
            if (part.getId() > nextId) {
                nextId = part.getId();
            }
        }
        
        return nextId;
    }
     
}

