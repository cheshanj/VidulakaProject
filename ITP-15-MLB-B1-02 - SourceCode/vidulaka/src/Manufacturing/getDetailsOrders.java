package Manufacturing;

public class getDetailsOrders {
    
    private String regSearchItem;
    private String regSearchCato;
    
    public getDetailsOrders()
    {
    }
    
    public getDetailsOrders(String cato, String item)
    {
        this.regSearchItem=item;
        this.regSearchCato=cato;
    }
    
    public void setItem(String item)
    {
         this.regSearchItem=item;
    }
    
    public void setCato(String cato)
    {
        this.regSearchCato=cato;
    }
    
    public String getItem()
    {
        return this.regSearchItem;
    }
    
    public String getCato()
    {
        return this.regSearchCato;
    }
   
    
}
