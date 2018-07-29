/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class dbconnect {
      public static Connection connect(){
    
        Connection conn = null;
         
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/vidulaka", "root", "");
        }catch(Exception e){
        
            System.out.println(e);
        }
    
        return conn;
    }

    Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
   

    }

