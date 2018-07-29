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
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class loanHandlingDAO {
    
    
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    
    public loanHandlingDAO()
    {
        conn=(Connection) dbconnect.connect();
    }
    
    
    public void loanAddEntry(loanHandling ln) throws SQLException
       {  
            try {
                  Statement stmt = (Statement) conn.createStatement();
                  String insertQ = "INSERT INTO loan(empID, empName, deptName, LoanAmt, lentDate, MonthlyDue, status, totalDue) "
                  + "VALUES( '"+ln.getempId()+"','" +ln.getEmpName()+ "','" +ln.getDepartment()+ "','" +ln.getLoanAmt()+ "','" +ln.getLentDate()+ "','" +ln.getMonthlyDue() + "','" +ln.getStatus()+ "','"+ln.getTotalDue()+"')" ;
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
    
        
    
        public void updatLoan(loanHandling ln) throws SQLException 
       {  
            try {
                 Statement stmt = (Statement) conn.createStatement();
                 String updateQ="UPDATE  loan SET   empID = '"+ln.getempId()+"', empName='"+ln.getEmpName()+"' ,deptName='"+ln.getDepartment() +"', LoanAmt = '"+ln.getLoanAmt()+"',lentDate = '"+ln.getLentDate()+"',  monthlyDue = '"+ln.getMonthlyDue()+"',  totalDue = '"+ln.getTotalDue()+"',  status = '"+ln.getStatus()+"' where loanID='"+ln.getloanId()+"'";
                 System.out.println(updateQ);
                 stmt.executeUpdate(updateQ);
                
              //  JOptionPane.showMessageDialog(null, "Update succesful!!!!");
        } catch (SQLException sQLException) {
           System.out.println(sQLException + "-----------update query failed------");
           // JOptionPane.showMessageDialog(null, "Update failed!!!!");
           
        } finally {
            conn.close();
        }
      
    }
    
    
        
        public void deleteLoanEntry(int loanId){
        try{
            Statement stmt = (Statement) conn.createStatement();
            String deleteQ ="DELETE FROM loan  WHERE loanID='"+loanId+"' ";
            System.out.println(deleteQ);
            stmt.executeUpdate(deleteQ);
            
             //JOptionPane.showMessageDialog(null, "Successfully deleted!!!!!");
        }
        catch(Exception e)
        {
             //JOptionPane.showMessageDialog(null, e);
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
               System.out.println(query7);
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
    
    
    
    public ResultSet search(int empId){
          try {
               Statement stmt = (Statement) conn.createStatement(); 
               String serachQ="SELECT * FROM loan WHERE empID ='"+empId+"' ";
               rs=stmt.executeQuery(serachQ);
              
            
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
      
           return rs;
     }
    
    public ResultSet loadLoanTable()
    {
       try 
        {
            String query4= " SELECT loanID as 'Loan ID', empID as 'Employee ID', empName as 'Employee Name', deptName as 'Department Name', LoanAmt as 'Loan Amount', lentDate as 'Date', MonthlyDue as 'Monthly Due', status as 'Status', totalDue as 'Total Due' FROM loan ";
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







