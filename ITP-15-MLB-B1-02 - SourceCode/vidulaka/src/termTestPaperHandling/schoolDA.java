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
public class schoolDA {
    
    Connection conn=null;
        ResultSet rs=null;
        
        public schoolDA()
       {
          conn=(Connection) dbconnect.connect();
       }
    
    public boolean updateSchool(school sc) throws SQLException
       {  
            try {
                  Statement stmt = (Statement) conn.createStatement();
                  String updtqry = "UPDATE school SET name='"+sc.getSchoolName()+"',address='"+sc.getAddress()+"',principle='"+sc.getPrinciple()+"',provinceID='"+sc.getProvinceId()+"',email='"+sc.getEmail()+"',phone='"+sc.getPhone()+"',division='"+sc.getDivision()+"' where schoolID='"+sc.getSchoolId()+"'";
                  System.out.println(updtqry);
                  stmt.executeUpdate(updtqry);
                  return true;
                  
                            //JOptionPane.showMessageDialog(null,"Data Insertion Successful!!!"); 
                  } 
                 
                  catch (SQLException sQLException) {
                        System.out.println(sQLException + "Updation failed");
                        return false;
                             //JOptionPane.showMessageDialog(,"Data Insertion Failed!!!");
                  } 
                 finally {
                          conn.close();
                 }
                         
       }
    public boolean insertSchool(school sc) throws SQLException{
        try {
                  Statement stmt = (Statement) conn.createStatement();
                  String insrtqry = "insert into school(name,address,principle,provinceID,email,phone,division) values('"+sc.getSchoolName()+"','"+sc.getAddress()+"','"+sc.getPrinciple()+"','"+sc.getProvinceId()+"','"+sc.getEmail()+"','"+sc.getPhone()+"','"+sc.getDivision()+"')";
                  System.out.println(insrtqry);
                  stmt.executeUpdate(insrtqry);
                  return true;
                  
                            //JOptionPane.showMessageDialog(null,"Data Insertion Successful!!!"); 
                  } 
                 
                  catch (SQLException sQLException) {
                        System.out.println(sQLException + "Insertion failed");
                        return false;
                             //JOptionPane.showMessageDialog(,"Data Insertion Failed!!!");
                  } 
                 
    }
}
