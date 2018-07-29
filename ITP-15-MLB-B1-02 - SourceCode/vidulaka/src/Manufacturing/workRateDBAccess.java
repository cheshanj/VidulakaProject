package Manufacturing;

import Home.dbconnect;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class workRateDBAccess {
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    
    public workRateDBAccess()
    {
        con=(Connection) dbconnect.connect();
    }
    
    public boolean insertFoldingRate(String id,String amount) throws SQLException
    {
         boolean Status=false;
         
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM,yyyy");
        Date date = new Date();
        String d=dateFormat.format(date);

        String sql1= "insert into folding_rate(empID,date,noOfPieces) values('"+id+"','"+d+"','"+amount+"')";
        pst=con.prepareStatement(sql1);
        pst.execute();
        Status=true;
        
        return Status;
    }
    
    public boolean updateFoldingRate(String id,String amount,String date) throws SQLException
    {
        boolean Status=false;
        
        String sql1= "update folding_rate set noOfPieces='"+amount+"' where empID='"+id+"' AND date='"+date+"' ";
        pst=con.prepareStatement(sql1);
        pst.execute();

        Status=true;
        
        return Status;
    }
}
