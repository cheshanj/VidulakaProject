/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payrollAndLoanMngmt;

import java.util.Calendar;

/**
 *
 * @author User
 */
public class foldingStaffSal {
    
    
    private String month;
    private int salId;
    private int empId;
    private String empName;
    private String deptName;
    private String designation;
    private double basicSal;
    private double advAmt;
    private double specialBonus;
    private double netSal;
    private int noOfPieces;
    private double pieceRate;
   private double  attBonus;
    
    
    
     public  foldingStaffSal( int pempId, String pempName, String pdeptName,String pdesignation, double pbasicSal,int pNoOfPieces, double pPieceRate,double padvAmt,double pspecialBonus, double pnetSal,String pmonth,double pAttBonus)
    {
             this.month=pmonth;
             this.empId=pempId;
             this.empName=pempName;
             this.deptName=pdeptName;
             this.designation=pdesignation;
             this.basicSal=pbasicSal;
             this.noOfPieces=pNoOfPieces;
             this.pieceRate=pPieceRate;
             this.advAmt=padvAmt;
             this.specialBonus=pspecialBonus;
             this.netSal=pnetSal;
             this.attBonus=pAttBonus;
    
    }
    
    
    public foldingStaffSal()
    {        
    }
   
    public void setMonth()
    {
       int mon=Calendar.getInstance().get(Calendar.MONTH);   
      switch(mon){
           case 0: this.month="January,"+Calendar.getInstance().get(Calendar.YEAR); break;
           case 1: this.month="Februay,"+Calendar.getInstance().get(Calendar.YEAR); break;    
           case 2: this.month="March,"+Calendar.getInstance().get(Calendar.YEAR); break;  
           case 3: this.month="April,"+Calendar.getInstance().get(Calendar.YEAR); break;    
           case 4: this.month="May,"+Calendar.getInstance().get(Calendar.YEAR); break;    
           case 5: this.month="June,"+Calendar.getInstance().get(Calendar.YEAR); break;
           case 6: this.month="July,"+Calendar.getInstance().get(Calendar.YEAR); break;
           case 7: this.month="August,"+Calendar.getInstance().get(Calendar.YEAR); break;
           case 8: this.month="September,"+Calendar.getInstance().get(Calendar.YEAR); break;    
           case 9: this.month="October,"+Calendar.getInstance().get(Calendar.YEAR); break;
           case 10: this.month="November,"+Calendar.getInstance().get(Calendar.YEAR); break;
           case 11: this.month="December,"+Calendar.getInstance().get(Calendar.YEAR); break;    
           default: break;
               
       }
       
    }
    
    
    public void setsalId(int psalId)
    {
        this.salId=psalId;
    }        
    
    
    public void setEmpId(int pempId)
    {
        this.empId=pempId;
    }
    
    
    public void setEmpName(String pempName)
    {
        this.empName=pempName;
    }
    
    
    public void setDeptName(String pdeptName)
    {
        this.deptName=pdeptName;
    }
    
    
    public void setDesignation(String pdesignation)
    {
        this.designation=pdesignation;
    }
    
    
    public void setBasicSal(double pbasicsal)
    {
        this.basicSal=pbasicsal;
    }
    
    public void setNoOfPieces(int pNoOfPieces)
    {
        this.noOfPieces=pNoOfPieces;
    }
    
    public void setPieceRate(double ppieceRate)
    {
        this.pieceRate=ppieceRate;
    }
    
   
    public void setAdvAmt(double pAdvAmt)
    {
        this.advAmt=pAdvAmt;
    }
   
     public void setAttBonus(double pAttBonus)
    {
        this.attBonus=pAttBonus;
    }
   
    
    
    public void setSpecialBonus(double pspecialBonus)
    {
        this.specialBonus=pspecialBonus;
    }
    
    
    public void setNelSal(int pnetSal)
    {
        this.netSal=pnetSal;
        
    }
    
    
    public String getMonth()
    {
       setMonth();
       return this.month; 
    }
    
    
    public int getsalId()
    {
       return this.salId;
    }        
    
    
    public int getEmpId()
    {
        return this.empId;
    }
    
    
    public String getEmpName()
    {
        return this.empName;
    }
    
    
    public String getDeptName()
    {
        return this.deptName;
    }
    
    
    public String getDesignation()
    {
        return this.designation;
    }
    
    
    public double getBasicSal()
    {
        return this.basicSal;
    }
    
    public double getAdvAmt()
    {
        return this.advAmt;
    }
    
    public double getNoOfPieces()
    {
        return this.noOfPieces ;
    }
    
    public double getpieceRate()
    {
        return this.pieceRate;
    }
    
    public double getAttBonus()
    {
        return this.attBonus;
    }
    
    

    public double getSpecialBonus()
    {
        return this.specialBonus;
    }
    
    
    public double getNetSal()
    {
        return this.netSal;
        
    }
    
    
}
