/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termTestPaperHandling;

/**
 *
 * @author User
 */
public class tender {
    
    private int tenderId;
    private java.sql.Date dueDate;
    private String printStatus;
    private String distStatus;
    private int provinceId;
    private int term;
    private String description;
    private int totalPapers;
    private java.sql.Date receivedDate;
    private double income;
    private String director;
    private int fromGrade;
    private int toGrade;

public tender(){
    
}

public tender(java.sql.Date pdueDate,int pprovinceId,int pterm,String pdescription,java.sql.Date preceivedDate,double pincome,String pdirector,int pfromGrade,int ptoGrade){
 
        this.dueDate=pdueDate;
        this.provinceId=pprovinceId;
        this.term=pterm;
        this.description=pdescription;
        this.receivedDate=preceivedDate;
        this.income=pincome;
        this.director=pdirector;
        this.fromGrade=pfromGrade;
        this.toGrade=ptoGrade;
}
    //Get methods
    public int getTenderId()
    {
            return tenderId;
    }
    public java.sql.Date getDueDate()
    {
            return dueDate;
    }
    public String getPrintStatus()
    {
            return printStatus;
    }
    public String getDtstStatus()
    {
            return distStatus;
    }
    public int getProvinceId()
    {
            return provinceId;
    }
    public int getTerm()
    {
            return term;
    }
    public String getDesciption()
    {
            return description;
    }
    public int getTotalPapers()
    {
            return totalPapers;
    }
    public java.sql.Date getReceivedDate()
    {
            return receivedDate;
    }
    public double getIncome()
    {
            return income;
    }public String getDirector()
    {
            return director;
    }
    public int getFromGrade()
    {
            return fromGrade;
    }
    public int getToGrade()
    {
            return toGrade;
    }
    
    //Set methods
    
    public void setTenderId(int ptenderId)
    {
            this.tenderId=ptenderId;
    }
    public void setDueDate(java.sql.Date pdueDate)
    {
            this.dueDate=pdueDate;
    }
    public void setPrintStatus(String pprintStatus)
    {
            this.printStatus=pprintStatus;
    }
    public void setDtstStatus(String pdistStatus)
    {
            this.distStatus=pdistStatus;
    }
    public void setProvinceId(int pprovinceId)
    {
            this.provinceId=pprovinceId;
    }
    public void setTerm(int pterm)
    {
            this.term=pterm;
    }
    public void setDesciption(String pdescription)
    {
            this.description=pdescription;
    }
    public void setTotalPapers(int ptotalpapers)
    {
            this.totalPapers=ptotalpapers;
    }
    public void setReceivedDate(java.sql.Date preceivedDate)
    {
            this.receivedDate=preceivedDate;
    }
    public void setIncome(double pincome)
    {
            this.income=pincome;
    }
    public void setDirector(String pdirector)
    {
            this.director=pdirector;
    }
    public void setFromGrade(int pfromGrade)
    {
            this.fromGrade=pfromGrade;
    }
    public void setToGrade(int ptoGrade)
    {
            this.toGrade=ptoGrade;
    }
}