package Manufacturing;

import Home.dbconnect;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class RegOrdersDBAccess {
    
    Connection con=null;
    PreparedStatement pst= null;
    PreparedStatement pst1= null;
    ResultSet rs=null;
    ResultSet rs1=null;
    
    public RegOrdersDBAccess()
    {
        con=(Connection)dbconnect.connect();
    }
    
    public ResultSet searchOrderID(String item) throws SQLException
    {
        String sqlS="select orders.orderID AS 'Order ID', product.productType AS 'Product Name', product.productGrade AS 'Grade', orders.quantity AS 'Quantity', orders.orderDate AS 'Ordered Date', orders.dueDate AS 'Due Date',orders.printingStatus AS 'Status'\n" +
                    "from orders, product\n" +
                    "where orders.pid=product.productID AND orders.checkStatus='complete' AND product.productType NOT LIKE '%Paperset%' AND orderID='"+item+"'\n" +
                    "group by orders.orderID";
        pst=con.prepareStatement(sqlS);
        rs=pst.executeQuery();
        return rs;
    }
    
    public ResultSet searchOrderDate(String item) throws SQLException
    {
        String sqlS="select orders.orderID AS 'Order ID', product.productType AS 'Product Name', product.productGrade AS 'Grade', orders.quantity AS 'Quantity', orders.orderDate AS 'Ordered Date', orders.dueDate AS 'Due Date',orders.printingStatus AS 'Status'\n" +
                    "from orders, product\n" +
                    "where orders.pid=product.productID AND orders.checkStatus='complete' AND product.productType NOT LIKE '%Paperset%' AND dueDate='"+item+"'\n" +
                    "group by orders.orderID";
        pst=con.prepareStatement(sqlS);
        rs=pst.executeQuery();
        return rs;
    }
    
    public ResultSet searchStatus(String item) throws SQLException
    {
        String sqlS="select orders.orderID AS 'Order ID', product.productType AS 'Product Name', product.productGrade AS 'Grade', orders.quantity AS 'Quantity', orders.orderDate AS 'Ordered Date', orders.dueDate AS 'Due Date',orders.printingStatus AS 'Status'\n" +
                    "from orders, product\n" +
                    "where orders.pid=product.productID AND orders.checkStatus='complete' AND product.productType NOT LIKE '%Paperset%' AND printingStatus='"+item+"'\n" +
                    "group by orders.orderID";
        pst=con.prepareStatement(sqlS);
        rs=pst.executeQuery();
        return rs;
    }
    
    public ResultSet searchProduct(String item) throws SQLException
    {
        String sqlS="select orders.orderID AS 'Order ID', product.productType AS 'Product Name', product.productGrade AS 'Grade', orders.quantity AS 'Quantity', orders.orderDate AS 'Ordered Date', orders.dueDate AS 'Due Date',orders.printingStatus AS 'Status'\n" +
                    "from orders, product\n" +
                    "where orders.pid=product.productID AND orders.checkStatus='complete' AND product.productType NOT LIKE '%Paperset%' AND product.productType LIKE '%"+item+"%'\n" +
                    "group by orders.orderID";
        pst=con.prepareStatement(sqlS);
        rs=pst.executeQuery();
        return rs;
    }
    
    public ResultSet searchOrderIDM(String item) throws SQLException
    {
        String sqlS="select orders.orderID AS 'Order ID', product.productType AS 'Product Name', product.productGrade AS 'Grade', orders.quantity AS 'Quantity', orders.orderDate AS 'Ordered Date', orders.dueDate AS 'Due Date',orders.printingStatus AS 'Status'\n" +
                    "from orders, product\n" +
                    "where orders.pid=product.productID AND orders.checkStatus='complete' AND product.productType LIKE '%Paperset%' AND orderID='"+item+"'\n" +
                    "group by orders.orderID";
        pst=con.prepareStatement(sqlS);
        rs=pst.executeQuery();
        return rs;
    }
    
    public ResultSet searchOrderDateM(String item) throws SQLException
    {
        String sqlS="select orders.orderID AS 'Order ID', product.productType AS 'Product Name', product.productGrade AS 'Grade', orders.quantity AS 'Quantity', orders.orderDate AS 'Ordered Date', orders.dueDate AS 'Due Date',orders.printingStatus AS 'Status'\n" +
                    "from orders, product\n" +
                    "where orders.pid=product.productID AND orders.checkStatus='complete' AND product.productType LIKE '%Paperset%' AND dueDate='"+item+"'\n" +
                    "group by orders.orderID";
        pst=con.prepareStatement(sqlS);
        rs=pst.executeQuery();
        return rs;
    }
    
    public ResultSet searchStatusM(String item) throws SQLException
    {
        String sqlS="select orders.orderID AS 'Order ID', product.productType AS 'Product Name', product.productGrade AS 'Grade', orders.quantity AS 'Quantity', orders.orderDate AS 'Ordered Date', orders.dueDate AS 'Due Date',orders.printingStatus AS 'Status'\n" +
                    "from orders, product\n" +
                    "where orders.pid=product.productID AND orders.checkStatus='complete' AND product.productType LIKE '%Paperset%' AND printingStatus='"+item+"'\n" +
                    "group by orders.orderID";
        pst=con.prepareStatement(sqlS);
        rs=pst.executeQuery();
        return rs;
    }
    
    public ResultSet searchProductM(String item) throws SQLException
    {
        String sqlS="select orders.orderID AS 'Order ID', product.productType AS 'Product Name', product.productGrade AS 'Grade', orders.quantity AS 'Quantity', orders.orderDate AS 'Ordered Date', orders.dueDate AS 'Due Date',orders.printingStatus AS 'Status'\n" +
                    "from orders, product\n" +
                    "where orders.pid=product.productID AND orders.checkStatus='complete' AND product.productType LIKE '%Paperset%' AND product.productType LIKE '%"+item+"%'\n" +
                    "group by orders.orderID";
        pst=con.prepareStatement(sqlS);
        rs=pst.executeQuery();
        return rs;
    }
    
    public boolean updatePrintingNormal(int orderID)throws SQLException
    {   
        boolean Status=false;
        
        String sql1= "update orders set printingStatus='processing' where orderID='"+orderID+"'";
        pst=con.prepareStatement(sql1);
        pst.execute();
        Status=true;
        
        return Status;
    }
    
    public boolean updatePrintingModelPaper(int orderID)throws SQLException
    {   
        boolean Status=false;
        
        String sql1= "update orders set printingStatus='processing' where orderID='"+orderID+"'";
        pst=con.prepareStatement(sql1);
        pst.execute();
        Status=true;
        
        return Status;
    }
}
