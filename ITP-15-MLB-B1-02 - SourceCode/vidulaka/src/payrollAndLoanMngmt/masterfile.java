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
public class masterfile {
    

    private String deptName;
    private String empPost;
    private String empName;
    private int empID;
    private double basicSal;
    private double singleOTRate;
    private double doubleOTRate;
    private double epfRate;
    private double pieceRate;
    
    
    public masterfile()
    {
    
    }
    
    
    public masterfile(String pDeptName,String pEmpPost,double pBasicSal,double pSinOTRate,double pdoubleOTRat, double pEpfRate,double pPieceRate, String pempName,int pEmpId)
    {
        this.deptName=pDeptName;
        this.empPost=pEmpPost;
        this.basicSal=pBasicSal;
        this.singleOTRate=pSinOTRate;
        this.doubleOTRate=pdoubleOTRat;
        this.epfRate=pEpfRate;
        this.pieceRate=pPieceRate;
        this.empName=pempName;
        this.empID=pEmpId;
    }
    
    public void setEmpName(String empName)
    {
         this.empName=empName;
    }
    
    public void setEmpID(int empID)
    {
         this.empID=empID;
    }
    
    public void setDept(String deptName)
    {
         this.deptName=deptName;
    }
    

    public void setEmpPost(String empPost)
    {
         this.empPost=empPost;
    }
    

    public void setBasicSal(double basicSal)
    {
         this.basicSal=basicSal;
    }
    

    public void setSingleOTRate(double singalOTRate)
    {
         this.singleOTRate=singalOTRate;
    }
    

    public void setDoubleOTRate(double doubleOTRate)
    {
         this.doubleOTRate=doubleOTRate;
    }    
    
    
    public void setEpfRate(double epfRate)
    {
         this.epfRate=epfRate;
    }
    
    public void setPieceRate(double pieceRate)
    {
         this.pieceRate=pieceRate;
    }
    
    public String getEmpName()
    {
          return this.empName;
    }
    
    public String getDepName()
    {
            return deptName;
    }
        
    public String getEmpPost()
    {
            return empPost;
    }
    
    public int getEmpId()
    {
            return empID;
    }
    
    
    public Double getBasicSal()
    {
            return basicSal;
    }
    
    public Double getSingleOtRate()
    {
            return singleOTRate;
    }
    
    public Double getDoubleOTRate()
    {
            return doubleOTRate;
    }
    
    public Double getEpfRate()
    {
            return epfRate;
    }
    
    public Double getPieceRate()
    {
            return pieceRate;
    }
    
    
}






