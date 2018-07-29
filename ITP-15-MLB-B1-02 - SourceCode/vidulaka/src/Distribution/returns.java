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
public class returns {
    
    int rID;
    int agID;
    String pID;
    int amount;
    String date;
    int disID;
    double paidamount;
    
    public returns (){
    
    }
    
    public returns (int prID, int pagID, String ppID, int pamount, String pdate, int pdisID, double ppaid){
        
        this.rID = prID;
        this.agID = pagID;
        this.pID = ppID;
        this.amount = pamount;
        this.date = pdate;
        this.disID = pdisID;
        this.paidamount = ppaid;
    }
    
    public returns ( int pagID, String ppID, int pamount, String pdate, int pdisID, double ppaid){
        
        this.agID = pagID;
        this.pID = ppID;
        this.amount = pamount;
        this.date = pdate;
        this.disID = pdisID;
        this.paidamount = ppaid;   
    }
    
    public void setrID(int prID){
    
        this.rID = prID;
    }
    
    public void setagID(int pagID){
    
        this.agID = pagID;
    }
    
    public void setpID(String ppID){
    
        this.pID = ppID;
    }
    
    public void setamount(int pamount){
    
        this.amount = pamount;
    }
    
    public void setdate(String pdate){
    
        this.date = pdate;
    }
    
    public void setdistID(int pdistID){
    
        this.disID = pdistID;
    }
    
    public void setpaid(double ppaid){
    
        this.paidamount = ppaid;
    }
    
    public int getrid(){
    
        return this.rID;
    }
    
    public int getagid(){
    
        return this.agID;
    }
    
    public String getpid(){
    
        return this.pID;
    }
    
    public int getamount(){
    
        return this.amount;
    }
    
    public String getdate() {
    
        return this.date;
    }
    
    public int getdistid(){
    
        return this.disID;
    }
    
    public double getpaid(){
    
        return this.paidamount;
    }
}
