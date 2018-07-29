/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payrollAndLoanMngmt;

import Home.dbconnect;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class advanceDetailsDAO {
    
     
      Connection conn=null;
      ResultSet rs=null;
      PreparedStatement pst=null;
    
     
       public advanceDetailsDAO()
       {
          conn=(Connection) dbconnect.connect();
       }
       
      
       public void advanceAddEntry(advanceDetails ad) throws SQLException
       {  
            try {
                  Statement stmt = (Statement) conn.createStatement();
                  String insertQ = "INSERT INTO advance(empID, empName, deptName, advAmt, date, status, month) "
                  + "VALUES( '"+ad.getEmpId()+"','" + ad.getEmpName() + "','" +ad.getDeptName() + "','" +ad.getAdvAmount() + "','" +ad.getDate()+ "','" +ad.getAdvStatus() + "','" + ad.getMonth()+ "')";
                  System.out.println(insertQ);
                  stmt.executeUpdate(insertQ);
                
                  
                            //  JOptionPane.showMessageDialog(null,"Data Insertion Successful!!!"); 
                  } 
                 
                  catch (SQLException sQLException) {
                        System.out.println(sQLException + "-----------Insert query failed------------");
                       
                           //  JOptionPane.showMessageDialog(null,"Data Insertion Failed!!!");
                  } 
                 finally {
                          conn.close();
                 }
                         
       }  
    
       
       public void updateAdvEntry(advanceDetails ad) throws SQLException 
       {  
            try {
                 Statement stmt = (Statement) conn.createStatement();
                 String updateQ="UPDATE  advance SET empID = '"+ad.getEmpId()+"', empName = '"+ad.getEmpName()+"',  deptName = '"+ad.getDeptName()+"',  advAmt = '"+ad.getAdvAmount()+"',  date = '"+ad.getDate()+"',  month = '"+ad.getMonth()+"',  status = '"+ad.getAdvStatus()+"' WHERE AdvId='"+ad.getAdvID()+"' ";
                 System.out.println(updateQ);
                 stmt.executeUpdate(updateQ);
                
              //JOptionPane.showMessageDialog(null, "Update succesful!!!!");
        } catch (SQLException sQLException) {
           System.out.println(sQLException + "-----------update query failed------");
             //JOptionPane.showMessageDialog(null, "Update failed!!!!");
           
        } finally {
            conn.close();
        }
      
    }
       
       
       
    public void deletemfEntry(int adId){
        try{
            Statement stmt = (Statement) conn.createStatement();
            String deleteQ ="DELETE FROM advance  WHERE AdvId='"+adId+"' ";
            System.out.println(deleteQ);
            stmt.executeUpdate(deleteQ);
            
          //      JOptionPane.showMessageDialog(null, "Successfully deleted!!!!!");
        }
        catch(Exception e)
        {
            System.out.println(e+"Delete Operation Falied");
             // JOptionPane.showMessageDialog(null, e);
        }
        finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(masterFileDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }  
       
       
     }   
       
 
     
   public int eIdOnValChange(String ename) {
          
        String query5="SELECT * FROM  employee WHERE name= '"+ename +"' ";
        int eId =0;
     try{
            pst=conn.prepareStatement(query5);
            rs=pst.executeQuery();
            if(rs.next())
            {
            eId=Integer.parseInt(rs.getString("empID"));
           
            
            }
                           
         }catch(SQLException | NumberFormatException e)
         {
             System.out.println(e);
         }
      
              return eId;  
    }   
        
        
    public String dnameOnValChange(int eId) {
          
        String query6="SELECT * FROM  employee WHERE empID= '"+eId +"' ";
        int deptId ;
        String dname = null;
     try{
            pst=conn.prepareStatement(query6);
            rs=pst.executeQuery();
            if(rs.next())
            {
                deptId=Integer.parseInt(rs.getString("deptID"));
          
               String query7="SELECT * FROM  department WHERE deptID= '"+deptId +"' ";
               pst=conn.prepareStatement(query7);
               rs=pst.executeQuery();
                  
                  if(rs.next())
                  {
                        dname=rs.getString("deptName");
                  }
            
            }
                           
         }catch(SQLException | NumberFormatException e)
         {
             System.out.println(e);
         }
      
              return dname;  
    } 
    
    
    public ResultSet search(String searchBy){
     
          try {
               Statement stmt = (Statement) conn.createStatement(); 
               String serachQ="SELECT * FROM advance WHERE month LIKE'%"+searchBy +"%' ";
               rs=stmt.executeQuery(serachQ);
              
            
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
      
           return rs;
     }
    
       
    public  ResultSet loadAdvanceTable()
    {
        try 
        {
            String query4= " SELECT AdvId as 'Advance ID', empID as 'Employee ID', empName as 'Employee Name', deptName as Department, advAmt as 'Advance Amount', date as Date, status as Status, month as Month FROM advance ";
            pst=conn.prepareStatement(query4);
            rs=pst.executeQuery();
            
        }
        catch (Exception e)
        { 
           System.out.println(e);  
            
        }
          return rs;
    }
    
}
 