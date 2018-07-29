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
public class advanceDetails {
    
    private int advId;
    private int empId;
    private String empName;
    private String deptName;
    private double advAmount;
    private String date;
    private String status;
    private String month;
    
    
      public advanceDetails()
      {}
    
      public advanceDetails(int pempId, String pempName, String pdeptName, double padvAmount, String pdate, String pstatus, String pmonth)
      {
           this.empId=pempId;
           this.empName=pempName;
           this.deptName=pdeptName;
           this.advAmount=padvAmount;
           this.date=pdate;
           this.status=pstatus;
           this.month=pmonth;
      }
    

      public void setAdvID(int padvId)
      {
           this.advId=padvId;
      }
      
      public void setEmpId(int pEmpID)
      {
           this.empId=pEmpID;
      }
      
      public void setEmpName(String pEmpName)
      {
           this.empName=pEmpName;
      }
      
      public void setDeptName(String pDeptName)
      {
           this.deptName=pDeptName;
      }
      
      public void setAdvAmount(double padvAmnt)
      {
           this.advAmount=padvAmnt;
      }
      
      public void setDate(String pDate)
      {
           this.date=pDate;
      }
      
      
      public void setAdvStatus(String pStatus)
      {
           this.status=pStatus;
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
    

      public int getAdvID()
      {
           return this.advId;
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
      
      public double getAdvAmount()
      {
           return this.advAmount;
      }
      
      public String getDate()
      {
           return this.date;
      }
      
      
      public String getAdvStatus()
      {
           return this.status;
      }
      
      
      public String getMonth()
      {
           setMonth();
           return this.month;
      } 
      

}

