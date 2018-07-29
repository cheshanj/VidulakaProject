package Manufacturing;

public class specialOrderGetSet {
    
    private String seminarCombo;
    private String tenderCombo;
    private String seminarSearch;
    private String tenderSearch;
    private String DocumentPath;
    private String dueDate;
    private int semId;
    private int amount;
    
    public specialOrderGetSet(){}
    
    public specialOrderGetSet(String sCombo,String tCombo,String sSearch,String tSearch)
    {
        this.seminarCombo=sCombo;
        this.tenderCombo=tCombo;
        this.seminarSearch=sSearch;
        this.tenderSearch=tSearch;
    }
    
    public String getSemSearch()
    {
        return this.seminarSearch;
    }
    
    public String getTenSearch()
    {
         return this.tenderSearch;
    }
    
    public String getSemCombo()
    {
        return this.seminarCombo;
    }
    
    public String getTenCombo()
    {
        return this.tenderCombo;
    }
    
    public void setSemSearch(String sSearch)
    {
        this.seminarSearch=sSearch;
    }
    
    public void setTenSearch(String tSearch)
    {
        this.tenderSearch=tSearch;
    }
    
    public void setSemCombo(String sCombo)
    {
        this.seminarCombo=sCombo;
    }
    
    public void setTenCombo(String tCombo)
    {
        this.tenderCombo=tCombo;
    }
    
    public void setsemID(int id)
    {
        this.semId=id;
    }
    
    public void setDocumentPath(String path)
    {
        this.DocumentPath=path;
    }
    
    public void setAmount(int amunt)
    {
        this.amount=amunt;
    }
    
    public void setDueDate(String date)
    {
        this.dueDate=date;
    }
    
    public int getsemID()
    {
        return this.semId;
    }
    
    public String getDocumentPath()
    {
        return this.DocumentPath;
    }
    
    public int getAmount()
    {
        return this.amount;
    }
    
    public String getDueDate()
    {
        return this.dueDate;
    }
    
}
