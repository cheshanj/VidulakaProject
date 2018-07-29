/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distribution;

/**
 *
 * @author DELL
 */
public class shop {

    private int shopID;
    private String shopname;
    private String address;
    private String phone;
    private int agID;

    public shop() {

    }

    public shop(String pshopname, String paddress, String phone) {
        this.shopname = pshopname;
        this.address = paddress;
        this.phone = phone;
    }

    public shop(int pshopID, String pshopname, String paddress, String phone) {
        this.shopID = pshopID;
        this.shopname = pshopname;
        this.address = paddress;
        this.phone = phone;
    }

    public shop(String pshopname, String paddress, String phone, int pagID) {
        this.shopname = pshopname;
        this.address = paddress;
        this.phone = phone;
        this.agID = pagID;
    }

    public shop(int pshopID, String pshopname, String paddress, String phone, int pagID) {
        this.shopID = pshopID;
        this.shopname = pshopname;
        this.address = paddress;
        this.phone = phone;
        this.agID = pagID;
    }

    public void setagID(int pagID) {
        this.agID = pagID;
    }

    public void setshname(String pshname) {
        this.shopname = pshname;
    }

    public void setshadd(String padd) {
        this.address = padd;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    public int getshID() {
        return this.shopID;
    }

    public int getshagID() {
        return this.agID;
    }

    public String getshname() {
        return this.shopname;
    }

    public String getshadd() {
        return this.address;
    }

    public String getshphone() {
        return this.phone;
    }
}
