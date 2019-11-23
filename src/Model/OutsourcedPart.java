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
public class OutsourcedPart extends Part{
    private String companyName;
    
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String CompanyName) {
        super(id, name, price, stock, min, max);
        setCompanyName(companyName);
    }
    
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCompanyName() { return this.companyName; }

}
