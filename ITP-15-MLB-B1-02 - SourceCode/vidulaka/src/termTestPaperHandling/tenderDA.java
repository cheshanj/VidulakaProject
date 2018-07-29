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
import Home.dbconnect;
import Home.home;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;


public class tenderDA {
    
        Connection conn=null;
        ResultSet rs=null;
        
        public tenderDA()
       {
          conn=(Connection) dbconnect.connect();
       }
        
        public boolean addNewTender(tender td) throws SQLException
       {  
            try {
                  Statement stmt = (Statement) conn.createStatement();
                  String inqry = "INSERT INTO tender(dueDate, printingStatus, distStatus, provinceID, term, description, receivedDate, income, director, fromGrade, toGrade) "
                  + "VALUES( '"+td.getDueDate()+"','not complete','not complete','" +td.getProvinceId()+ "','" + td.getTerm() + "','" +td.getDesciption()+ "','" + td.getReceivedDate()+ "','" +td.getIncome() + "','" + td.getDirector()+ "','"+td.getFromGrade()+"','"+td.getToGrade()+"')";
                  System.out.println(inqry);
                  stmt.executeUpdate(inqry);
                  return true;
                  
                            //JOptionPane.showMessageDialog(null,"Data Insertion Successful!!!"); 
                  } 
                 
                  catch (SQLException sQLException) {
                        System.out.println(sQLException + "Add new tender insertion query failed");
                        return false;
                             //JOptionPane.showMessageDialog(,"Data Insertion Failed!!!");
                  } 
                 finally {
                          conn.close();
                 }
                         
       }
        
        public boolean updateDueDate(tender td) throws SQLException{
            
        
        try{
            String updtDueDateqry="UPDATE tender SET dueDate='"+td.getDueDate()+"' where tenderID='"+td.getTenderId()+"'";
            System.out.println(updtDueDateqry); 
            PreparedStatement pst = conn.prepareStatement(updtDueDateqry); 
            pst.executeUpdate();
            return true;
                   
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
        }
        
        public boolean deleteTender(tender td) throws SQLException{
            
        
        try{
            String deleteTender="DELETE from tender where tenderID='"+td.getTenderId()+"'";
            System.out.println(deleteTender); 
            PreparedStatement pst = conn.prepareStatement(deleteTender); 
            pst.executeUpdate();
            return true;
                   
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
        }
}
