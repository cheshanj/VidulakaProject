/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termTestPaperHandling;

import Home.dbconnect;
import com.mysql.jdbc.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author User
 */
public class paperDA {
    Connection conn=null;
        ResultSet rs=null;
        
        public paperDA()
       {
          conn=(Connection) dbconnect.connect();
       }
        
        public void addPaperCount(paper pr) throws SQLException
       {  
            try {
                  Statement stmt = (Statement) conn.createStatement();
                  String inqry = "INSERT INTO school_paper_count(tenderID, schoolID, grade, subject, amount) VALUES( '"+pr.gettenderId()+"','"+pr.getschoolId()+"','"+pr.getgrade()+"','" +pr.getsubject()+ "','" + pr.getamount()+ "')";
                  System.out.println(inqry);
                  stmt.executeUpdate(inqry);
                            //JOptionPane.showMessageDialog(null,"Data Insertion Successful!!!"); 
                  } 
                 
                  catch (SQLException sQLException) {
                        System.out.println(sQLException + "Insertion faied");
                             //JOptionPane.showMessageDialog(,"Data Insertion Failed!!!");
                  } 
                 
       }
        
        public void updateAmount(paper pr) throws SQLException{
            try {
                  Statement stmt = (Statement) conn.createStatement();
                  String inqry = "update paper set amount='"+pr.getoldAmount()+"' where subject='"+pr.getsubject()+"' and grade='"+pr.getgrade()+"' and tenderID='"+pr.gettenderId()+"'";
                  System.out.println(inqry);
                  stmt.executeUpdate(inqry);
                            //JOptionPane.showMessageDialog(null,"Data Insertion Successful!!!"); 
                  } 
                 
                  catch (SQLException sQLException) {
                        System.out.println(sQLException + "updation failed");
                             //JOptionPane.showMessageDialog(,"Data Insertion Failed!!!");
                  } 
                
        }
}
