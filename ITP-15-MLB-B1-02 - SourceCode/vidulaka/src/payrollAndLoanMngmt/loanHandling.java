/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payrollAndLoanMngmt;



/**
 *
 * @author User
 */
public class loanHandling {
    
     private int loanId;
     private int empId;
     private String empName;
     private String deptName;
     private double loanAmt;
     private String lentDate;
     private double monthlyDue;
     private double totalDue;
     private String status;
     
     
     public loanHandling()
     {
     }
     
     
     public loanHandling(int pempId, String pempName,String pdeptName,double ploanAmt, String plentDate, double pmonthlyDue, double ptotalDue,String pstatus)
     {
            this.empId=pempId;
            this.empName=pempName;
            this.deptName=pdeptName;
            this.loanAmt=ploanAmt;
            this.lentDate=plentDate;
            this.monthlyDue=pmonthlyDue;
            this.totalDue=ptotalDue;
            this.status=pstatus;
            
     }
     
     
     public void setloanId(int ploadId) 
     {
          this.loanId=ploadId;   
     }
     
    
     public void setempId(int pempId)
     {
         this.empId=pempId;
     }
     
     
     public void setEmpName(String pEmpName)
     {
         this.empName=pEmpName;
     }
     
     public void setDepartment(String pDept)
     {
         this.deptName=pDept;
     }
     
     
     public void setLoanAmt(double pLoanAmt)
     {
         this.loanAmt=pLoanAmt;
     }
     
     public void setLentDate(String pLentDate)
     {
         this.lentDate=pLentDate;
     }
     
     public void setMonthlyDue(double pmonthlyDue)
     {
         this.monthlyDue=pmonthlyDue;
     }
     
     
     public void setTotalDue(double pTotalDue)
     {
         this.totalDue=pTotalDue;
     }
     
     
     public void setStatus(String pStatus)
     {
         this.status=pStatus;
     }
     
     
     public int getloanId() 
     {
          return this.loanId;  
     }
     
    
     public int getempId()
     {
         return this.empId;
     }
     
     
     public String getEmpName()
     {
         return this.empName;
     }
     
     public String getDepartment()
     {
         //return this.deptName;
         return this.deptName;
     }
     
     
     public double getLoanAmt()
     {
         return this.loanAmt;
     }
     
     public String getLentDate()
     {
         return this.lentDate;
     }
     
     public double getMonthlyDue()
     {
         return this.monthlyDue;
     }
     
     
     public double getTotalDue()
     {
         return this.totalDue;
     }
     
     
     public String getStatus()
     {
         return this.status;
     }
       
     
}
