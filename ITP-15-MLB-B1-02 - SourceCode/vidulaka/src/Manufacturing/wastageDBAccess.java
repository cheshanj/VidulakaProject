package Manufacturing;

import Home.dbconnect;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class wastageDBAccess {
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    public String amountU;
    public String amountA;
    
    public wastageDBAccess()
    {
        con=(Connection) dbconnect.connect();
    }
    
    public ResultSet getFreeOrderID() throws SQLException
    {
        String sql="select orderID from orders where orderID NOT IN (select orderID from wastage order by orderID ) AND orderID IN (select orderID from used_raw_material order by orderID) AND orderID IN (select orderID from assign_raw_material order by orderID)";
        pst=con.prepareStatement(sql);
        rs=pst.executeQuery();
        return rs;
    }
    
    public String getUsedAmount(String id,String icode) throws SQLException
    {
        String sql="select amount from used_raw_material where orderID='"+id+"' AND itemCode='"+icode+"'";
        pst=con.prepareStatement(sql);
        rs=pst.executeQuery();
        
        while(rs.next())
        {
            amountU=rs.getString("amount");
        }
        return amountU;
    }
    
    public String getAssignAmount(String id,String icode) throws SQLException
    {
        String sql="select amount from assign_raw_material where orderID='"+id+"' AND itemCode='"+icode+"'";
        pst=con.prepareStatement(sql);
        rs=pst.executeQuery();
        
        while(rs.next())
        {
            amountA=rs.getString("amount");
        }
        return amountA;
    }
    
    public boolean updateWastate(String id,String icode,double amount) throws SQLException
    {
        boolean Status=false;
        
        String sql="select orderDate from orders where orderID='"+id+"'";
        pst=con.prepareStatement(sql);
        rs=pst.executeQuery();
        String date=null;
        while(rs.next())
        {
            date=rs.getString("orderDate");
            String sql1= "insert into wastage(orderID,wasteItemID,wastedAmount,wasteDate) values('"+id+"','"+icode+"','"+amount+"','"+date+"')";
            pst=con.prepareStatement(sql1);
            pst.execute();
        }
        
        Status=true;
        
        return Status;
    }
    
    public ResultSet getMonthlyWastage(String d) throws SQLException
    {
        String sql="select wasteItemID AS 'Wasted Item', sum(wastedAmount) AS 'Total Wasted Amount' from wastage where wasteDate like '_____"+d+"___' group by wasteItemID";
        pst=con.prepareStatement(sql);
        rs=pst.executeQuery();
        return rs;
    }
    
    public ResultSet fillComboOrderID(String itemCode) throws SQLException
    {
        String sql="select orderID from wastage where wasteItemID='"+itemCode+"' group by orderID";
        pst=con.prepareStatement(sql);
        rs=pst.executeQuery();
        
        return rs;
    }
    
    public ResultSet checkCustomWastage(String id,String code) throws SQLException
    {
        System.out.println(id);
        System.out.println(code);
        String sql="select orderID AS 'Order ID',wasteItemID AS 'Item Code',wastedAmount AS 'Wasted Amount' from wastage where orderID='"+id+"' AND wasteItemID='"+code+"'";
        pst=con.prepareStatement(sql);
        rs=pst.executeQuery();
        
        return rs;
    }
}
