package Manufacturing;

import Home.dbconnect;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class specialOrderDBAccess {
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    
    public specialOrderDBAccess()
    {
        con=(Connection) dbconnect.connect();
    }
    
    public ResultSet searchOrderID(String item) throws SQLException
    {
        String sqlS="select seminar_resource.semID AS 'Seminar ID',seminar_resource.sem_draft AS 'Document Path',seminar_resource.noOfPapers AS 'Amount',seminar.date AS 'Due Date',seminar_resource.printingStatus AS 'Printing Status' "
                  + "from seminar_resource, seminar "
                  + "where seminar_resource.semID=seminar.seminarID AND seminar_resource.semID='"+item+"'";
        pst=con.prepareStatement(sqlS);
        rs=pst.executeQuery();
        return rs;
    }
    
    public ResultSet searchOrderDate(String item) throws SQLException
    {
        String sqlS="select seminar_resource.semID AS 'Seminar ID',seminar_resource.sem_draft AS 'Document Path',seminar_resource.noOfPapers AS 'Amount',seminar.date AS 'Due Date',seminar_resource.printingStatus AS 'Printing Status' "
                  + "from seminar_resource, seminar "
                  + "where seminar_resource.semID=seminar.seminarID AND seminar.date='"+item+"'";
        pst=con.prepareStatement(sqlS);
        rs=pst.executeQuery();
        return rs;
    }
    
    public ResultSet searchStatus(String item) throws SQLException
    {
        String sqlS="select seminar_resource.semID AS 'Seminar ID',seminar_resource.sem_draft AS 'Document Path',seminar_resource.noOfPapers AS 'Amount',seminar.date AS 'Due Date',seminar_resource.printingStatus AS 'Printing Status' "
                  + "from seminar_resource, seminar "
                  + "where seminar_resource.semID=seminar.seminarID AND seminar_resource.printingStatus='"+item+"'";
        pst=con.prepareStatement(sqlS);
        rs=pst.executeQuery();
        return rs;
    }
    
    public ResultSet TsearchOrderID(String item) throws SQLException
    {
        String sqlS="select tr.tenderID AS 'Tender ID',tr.draftID AS 'Draft ID',tr.amount AS 'Amount',t.dueDate AS 'Due Date',t.printingStatus AS 'Printing Status' from tender_order tr, tender t where tr.tenderID = t.tenderID AND tr.tenderID='"+item+"' ";
        pst=con.prepareStatement(sqlS);
        rs=pst.executeQuery();
        return rs;
    }
    
    public ResultSet TsearchOrderDate(String item) throws SQLException
    {
        String sqlS="select tr.tenderID AS 'Tender ID',tr.draftID AS 'Draft ID',tr.amount AS 'Amount',t.dueDate AS 'Due Date',t.printingStatus AS 'Printing Status' from tender_order tr, tender t where tr.tenderID = t.tenderID AND t.dueDate='"+item+"'";
        pst=con.prepareStatement(sqlS);
        rs=pst.executeQuery();
        return rs;
    }
    
    public ResultSet TsearchStatus(String item) throws SQLException
    {
        String sqlS="select tr.tenderID AS 'Tender ID',tr.draftID AS 'Draft ID',tr.amount AS 'Amount',t.dueDate AS 'Due Date',t.printingStatus AS 'Printing Status' from tender_order tr, tender t where tr.tenderID = t.tenderID AND t.printingStatus='"+item+"'";
        pst=con.prepareStatement(sqlS);
        rs=pst.executeQuery();
        return rs;
    }
    
    public boolean updatePrintinTender(String tID) throws SQLException
    {
        boolean Status=false;
        
        String sql1= "update tender set printingStatus='processing' where tenderID='"+tID+"'";
        pst=con.prepareStatement(sql1);
        pst.execute();
        Status=true;
        
        return Status;
    }
    
    public boolean updatePrintinSeminar(String sID) throws SQLException
    {
        boolean Status=false;
        
        String sql1= "update seminar_resource set printingStatus='processing' where semID='"+sID+"'";
        pst=con.prepareStatement(sql1);
        pst.execute();
        Status=true;
        
        return Status;
    }
     
    public boolean updatedistributeTender(String tID)throws SQLException
    {
        boolean Status=false;
        
        String sql= "update tender set printingStatus='complete' where tenderID='"+tID+"'";
        pst=con.prepareStatement(sql);
        pst.execute();
        Status=true;
        
        return Status;
    }
    
    public boolean updatedistributeSeminar(String sID) throws SQLException
    {
        boolean Status=false;
        
        String sql= "update seminar_resource set printingStatus='complete' where semID='"+sID+"'";
        pst=con.prepareStatement(sql);
        pst.execute();
        Status=true;
        
        return Status;
    }
}
