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
import net.proteanit.sql.DbUtils;

/**
 *
 * @author User
 */    
public class masterFileDAO {
   
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement pst;
     
       public masterFileDAO()
       {
          conn=(Connection) dbconnect.connect();
       }
       
      
       public void masterFileAddEntry(masterfile mf) throws SQLException
       {  
            try {
                  Statement stmt = (Statement) conn.createStatement();
                  String insertQ = "INSERT INTO master_file(departmentName,empName,empID,emPosition, basicSal, singleOTRate, doubleOTRate, epfRate, pieceRate) "
                  + "VALUES( '"+mf.getDepName()+"','"+mf.getEmpName() +"','"+mf.getEmpId()+"','" + mf.getEmpPost() + "','" + mf.getBasicSal() + "','" +mf.getSingleOtRate() + "','" + mf.getDoubleOTRate()+ "','" +mf.getEpfRate() + "','" + mf.getPieceRate()+ "')";
                  System.out.println(insertQ);
                  stmt.executeUpdate(insertQ);

                             //JOptionPane.showMessageDialog(null,"Data Insertion Successful!!!"); 
                  } 
                 
                  catch (SQLException sQLException) {
                        System.out.println(sQLException + "-----------Insert query failed------------");
                       
                           // JOptionPane.showConfirmDialog(null, "bla bla");
                  } 
                 finally {
                          conn.close();
                 }
                         
       }

             
       public void updateMfEntry(masterfile mf) throws SQLException 
       {  
            try {
                 Statement stmt = (Statement) conn.createStatement();
                 String updateQ="UPDATE  master_file SET departmentName = '"+mf.getDepName()+"', emPosition = '"+mf.getEmpPost()+"',basicSal = '"+mf.getBasicSal()+"',  singleOTRate = '"+mf.getSingleOtRate()+"',  doubleOTRate = '"+mf.getDoubleOTRate()+"',  epfRate = '"+mf.getEpfRate()+"',  pieceRate = '"+mf.getPieceRate()+"' WHERE departmentName='"+mf.getDepName()+"' AND empID='"+mf.getEmpId()+"' ";
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
                 
  
     public void deletemfEntry(int mfEid,String mfDept){
        try{
            Statement stmt = (Statement) conn.createStatement();
            String deleteQ ="DELETE FROM master_file  WHERE departmentName='"+mfDept+"' AND empID='"+mfEid+"' ";
            System.out.println(deleteQ);
            stmt.executeUpdate(deleteQ);
            
          //      JOptionPane.showMessageDialog(null, "Successfully deleted!!!!!");
        }
        catch(Exception e)
        {
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
     
     public ResultSet search(String searchBy){
     
          try {
               Statement stmt = (Statement) conn.createStatement(); 
               String serachQ="SELECT * FROM master_file WHERE departmentName LIKE '%"+searchBy+"%' ";
               rs=stmt.executeQuery(serachQ);
              
            
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
      
           return rs;
     }    
      
     
     
       public ResultSet enameOnvalChange(String deptName)
       {
       if(("Office Staff".equals(deptName))) 

       {             
   
           try{
               String query4= " SELECT * FROM employee WHERE deptID=1"  ;
                 pst=conn.prepareStatement(query4);
                 rs=pst.executeQuery();
          
                 
             }
           catch(Exception e)
           {
               System.out.println(e);
           }
           
       }     
          
        
       if(("Production Staff".equals(deptName))) 

       {             
   
           try{
               String query4= " SELECT * FROM employee WHERE deptID=2"  ;
                 pst=conn.prepareStatement(query4);
                 rs=pst.executeQuery();

             }
           catch(Exception e)
           {
               System.out.println(e);
           }
           
       }
       
       
       
       
       if("Distribution Staff".equals(deptName)) 

       {             
   
           try{
               String query4= " SELECT * FROM employee WHERE deptID=3"  ;
                 pst=conn.prepareStatement(query4);
                 rs=pst.executeQuery();
            
             }
           catch(Exception e)
           {
               System.out.println(e);
           }
       
       }
       
       
       if("Folding Staff".equals(deptName)) 

       {             
   
           try{
               String query4= " SELECT * FROM employee WHERE deptID=4"  ;
                 pst=conn.prepareStatement(query4);
                 rs=pst.executeQuery();
           
           }catch(Exception e)
           {
               System.out.println(e);
           }
           
       
       }
           
             return rs;
       
       }
     
     
     
   
       public int eidOnvalChange(String ename,String dname )
       {   
            int eid = 0,depId = 0;
            
            
           try{
               String query= " SELECT * FROM department WHERE deptName='"+dname+"'"   ;
                 pst=conn.prepareStatement(query);
                 rs=pst.executeQuery();
                 System.out.println(query);
               
                 while(rs.next())
                 {
                 
                    depId= Integer.parseInt(rs.getString("deptID"));
                     
                 }
                 
                 
               
               String query2= " SELECT * FROM employee WHERE name='"+ename+"' AND deptID='"+ depId+"' "   ;
                 pst=conn.prepareStatement(query2);
                 rs=pst.executeQuery();

                 while(rs.next())
                 {
                      eid=Integer.parseInt(rs.getString("empID"));
                 
                 }
                 
                 
           }catch(Exception e)
           {
               System.out.println(e);
           }
           
            
       
       
                 return eid;  
       }
    
    
    
    
       public String desigOnValChange(int eId) {
          
        String query7="SELECT * FROM  employee WHERE empId= '"+eId +"' ";
        String desig = null;
        int deptId;
        String dname=null;
     try{
            pst=conn.prepareStatement(query7);
          //  System.out.println(query7);
            rs=pst.executeQuery();
            if(rs.next())
           {
               deptId=Integer.parseInt(rs.getString("deptID"));
               System.out.println(deptId);
                  String query8="SELECT * FROM  department WHERE deptID= '"+deptId +"' ";
                   pst=conn.prepareStatement(query8); 
                  // System.out.println(query8);
                   rs=pst.executeQuery();
           }      
                   if(rs.next())
                   {
                      dname = rs.getString("deptName");
                      System.out.println(dname);
                      
                        if(dname.equalsIgnoreCase("Distribution Staff"))
                        {
                           String query9="SELECT COUNT(*) FROM distribution_staff WHERE empID='"+eId +"' ";    
                           pst=conn.prepareStatement(query9);
                          // System.out.println(query9);
                           rs=pst.executeQuery();
                          if (rs.next())
                           {
                               int count = rs.getInt(1);
                               System.out.println(count);
                           
                            if(count>=1){
                                    String query="SELECT * FROM distribution_staff WHERE empID='"+eId +"' ";
                                   pst=conn.prepareStatement(query);
                                 //  System.out.println(query);
                                   rs=pst.executeQuery();
                                     if(rs.next()){ desig=rs.getString("designation"); }                            
                            }
                           }
                           else
                            {
                                String query="SELECT * FROM manager WHERE empID='"+eId +"' ";     
                                pst=conn.prepareStatement(query); 
                               // System.out.println(query);
                                rs=pst.executeQuery();
                                
                                if(rs.next()){ desig=rs.getString("designation"); } 
                            }
                           
                        }
                      
                        
                         
                        else if(dname.equalsIgnoreCase("Folding Staff"))
                        {
                         String query10="SELECT * FROM  folding_staff WHERE empID= '"+eId +"' ";
                        // System.out.println(query10);
                         pst=conn.prepareStatement(query10);
                         rs=pst.executeQuery();
                                  if(rs.next())
                                  {      desig=rs.getString("designation");
                                  
                                  }                
                        }
                        
                        
                        
                        else if(dname.equalsIgnoreCase("Office Staff") )
                        {
                          String query10="SELECT COUNT(*) FROM production_office_staff WHERE empID='"+eId +"' ";    
                           pst=conn.prepareStatement(query10); 
                           rs=pst.executeQuery();
                           
                           if (rs.next())
                           {
                               int count = rs.getInt(1);
                               System.out.println(count);
                           
                            if(count>=1){
                                    String query="SELECT * FROM production_office_staff WHERE empID='"+eId +"' ";
                                   pst=conn.prepareStatement(query);
                                 //  System.out.println(query);
                                   rs=pst.executeQuery();
                                     if(rs.next()){ desig=rs.getString("designation"); }                            
                            }
                           }
                           else
                            {
                                String query="SELECT * FROM manager WHERE empID='"+eId +"' ";     
                                pst=conn.prepareStatement(query); 
                               // System.out.println(query);
                                rs=pst.executeQuery();
                                
                                if(rs.next()){ desig=rs.getString("managerType"); } 
                            }
                            
                            
                            
                        } else if (dname.equalsIgnoreCase("Production Staff")) {
                            String query10="SELECT COUNT(*) FROM production_office_staff WHERE empID='"+eId +"' ";
                            pst=conn.prepareStatement(query10);
                            rs=pst.executeQuery();
                            
                            if (rs.next())
                            {
                                int count = rs.getInt(1);
                                //System.out.println(count);
                                
                                if(count>=1){
                                    String query="SELECT * FROM production_office_staff WHERE empID='"+eId +"' ";
                                    pst=conn.prepareStatement(query);
                                   // System.out.println(query);
                                    rs=pst.executeQuery();
                                    if(rs.next()){ desig=rs.getString("designation"); }
                                }
                            }
                            else
                            {
                                String query="SELECT * FROM manager WHERE empID='"+eId +"' ";
                                pst=conn.prepareStatement(query);
                               // System.out.println(query);
                                rs=pst.executeQuery();
                                
                                if(rs.next()){ desig=rs.getString("managerType"); }
                            }
                      }
                        
                        
                        
                   }
               
               
               
           
            
                           
         }catch(Exception e){}
      
             // System.out.println(desig);
              return desig;  
    }
       
       
       
    public boolean checkPrimaryKeyViolations(String deptName,int eid)
    {      
            boolean results=false;
        String query="SELECT COUNT(*) FROM master_file WHERE empID='"+eid+"' AND departmentName='"+deptName+"' ";    
            try {
                pst=conn.prepareStatement(query);
                // System.out.println(query);
                rs=pst.executeQuery();   
                
              if (rs.next())
                           {
                               int count = rs.getInt(1);
                               System.out.println(count);
                           
                            if(count>=1){
                                 results=true;
                            }
                           }
            } catch (SQLException ex) {
                Logger.getLogger(masterFileDAO.class.getName()).log(Level.SEVERE, null, ex);
            } 
           return results;
    }
         
          
    public ResultSet loadMfTable()
    {
          try 
        {
            String query2= " SELECT departmentName as 'Department Name', empName as 'Employee Name', empID as 'Employee ID', emPosition as 'Designation', basicSal as 'Basic Salary', singleOTRate as 'Single OT Rate', doubleOtRate as 'Double OT Rate', epfRate as 'EPF Rate', pieceRate as 'Piece Rate' FROM master_file";
            pst=conn.prepareStatement(query2);
            rs=pst.executeQuery();
          
        }
        catch (Exception e)
        { 
           System.out.println(e);  
            
        }
    
          return rs;
          
    }
    
    
    
    
    public ResultSet loadMfDeptName(){
        try {
                String query4= " SELECT * FROM department"  ;
                pst=conn.prepareStatement(query4);
                rs=pst.executeQuery();
        
           }
       catch(Exception e)
       { 
            System.out.println(e);
       }
    
       return rs;
    
    }
     
     
     
}
   

    
    
    

