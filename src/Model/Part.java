/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Sam
 */
public abstract class Part {
    int id;
    String name;
    double price;
    int stock;
    int min;
    int max;
    
    Part(int id, String name, double price, int stock, int min, int max) {
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max); 
    }
    
    void setId(int id){ this.id = id; }
    void setName(String name){ this.name = name; }
    void setPrice(double price){ this.price = price; }
    void setStock(int stock) { this.stock = stock; }
    void setMin(int min){ this.min = min; }
    void setMax(int max){ this.max = max; }
    void setPrice(int max){ this.price = (double) max; }
    public int getId(){ return this.id;}
    public String getName(){ return this.name; }
    public double getPrice(){ return this.price; }
    public int getStock(){ return this.stock; }
    public int getMin(){ return this.min; }
    public int getMax(){ return this.max; }
    
}
