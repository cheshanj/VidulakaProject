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
public class distribution {
    
    private int distributionID; 
    private int driverID;
    private int repID;
    private String vNO;
    private int orderID;
    private int receiver;
    private String status;
    
    public distribution () {
    
    }
    
    public distribution (int pdistID, int pdriverID, int prepID, String pvNO, int preceiver, int porderID, String pstatus) {
    
        this.distributionID = pdistID;
        this.driverID = pdriverID;
        this.repID = prepID;
        this.vNO = pvNO;
        this.receiver = preceiver;
        this.orderID = porderID;
        this.status = pstatus;
    }
    
    public distribution (int pdriverID, int prepID, String pvNO,int preceiver, int porderID,  String pstatus) {
    
        this.driverID = pdriverID;
        this.repID = prepID;
        this.vNO = pvNO;
        this.receiver = preceiver;
        this.orderID = porderID;
        this.status = pstatus;
    }
    
    public void setdriverID (int pdriverID){
    
        this.driverID = pdriverID;
    }
    
    public void setrepID (int prepID){
    
        this.repID = prepID;
    }
    
    public void setvNO(String pvNO){
    
        this.vNO = pvNO;
    }

    public void setOrderID(int porderID) {
        this.orderID = porderID;
    }
    
    public void setreceiver(int preceiver){
    
        this.receiver = preceiver;
    }
    
    public void setstatus(String pstatus){
    
        this.status = pstatus;
    }
    
    public int getdistributionID(){
        
        return this.distributionID;
    }
    
    public int getdriverID(){
    
        return this.driverID;
    }
    
    public int getrepID(){
    
        return this.repID;
    }
    
    public String getvNO(){
    
        return this.vNO;
    }
    
    public int getorderID(){
    
        return this.orderID;
    }
    
    public int getreceiver(){
    
        return this.receiver;
    }
    
    public String getstatus(){
    
        return this.status;
    }
}
