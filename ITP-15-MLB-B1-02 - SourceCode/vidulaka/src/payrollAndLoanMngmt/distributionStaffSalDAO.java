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
public class distributionStaffSalDAO {
    
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    
    public distributionStaffSalDAO()
    {
        conn=(Connection) dbconnect.connect();
    }
    
    
    public void distributionStaffSalAddEntry(distributionStaffSal d1) throws SQLException
       {  
            try {
                  Statement stmt = (Statement) conn.createStatement();
                  String insertQ = "INSERT INTO distribution_stf_sal( empID, empName, deptName, designation,basicSal, SpecialBonus, AdvanceAmt, NetSal, month) "
                  + "VALUES( '"+d1.getEmpId()+"','" +d1.getEmpName() + "','" +d1.getDeptName() + "','" +d1.getDesignation() + "','" + d1.getBasicSal()+ "','"+d1.getSpecialBonus()+"','" +d1.getAdvAmt()+ "','" +d1.getNetSal()+ "','"+d1.getMonth()+"')";
                  System.out.println(insertQ);
                  stmt.executeUpdate(insertQ);
                
                  
                            //  JOptionPane.showMessageDialog(null,"Data Insertion Successful!!!"); 
                  } 
                 
                  catch (SQLException sQLException) {
                        System.out.println(sQLException + "-----------Insert query failed------------");
                       
                           //JOptionPane.showMessageDialog(null,"Data Insertion Failed!!!");
                  } 
                 finally {
                          conn.close();
                 } 
    
        }
    
    
    
        public void updateDistStaffSalCal(distributionStaffSal d2) throws SQLException 
       {  
            try {
                 Statement stmt = (Statement) conn.createStatement();
                 String updateQ="UPDATE  distribution_stf_sal SET  empID= '"+d2.getEmpId()+"', empName = '"+d2.getEmpName()+"', deptName='"+d2.getDeptName()+"' ,designation='"+d2.getDesignation() +"', basicSal = '"+d2.getBasicSal()+"', AdvanceAmt = '"+d2.getAdvAmt()+"',SpecialBonus='"+d2.getSpecialBonus()+"',NetSal='"+d2.getNetSal()+"' WHERE dsalID='"+d2.getsalId()+"' ";
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
 
        
        
     public void deleteDistSalEntry(int salId){
        try{
            Statement stmt = (Statement) conn.createStatement();
            String deleteQ ="DELETE FROM distribution_stf_sal  WHERE dsalID='"+salId+"' ";
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
               String serachQ="SELECT * FROM distribution_stf_sal WHERE month LIKE '%"+searchBy +"%' ";
               rs=stmt.executeQuery(serachQ);
              
            
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
      
           return rs;
     }
        
        
        
     public boolean checkDuplicateValues(String month,int eid)
    {      
            boolean results=false;
        String query="SELECT COUNT(*) FROM distribution_stf_sal WHERE empID='"+eid+"' AND month='"+month+"' ";    
            try {
                pst=conn.prepareStatement(query);
                System.out.println(query);
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
                           
         }catch(SQLException | NumberFormatException e){}
      
              return dname;  
    } 
        
        
        
    public int eIdOnValChange(String ename) {
          
        String query5="SELECT * FROM  employee WHERE name= '"+ename +"' ";
        int eid = 0;
     try{
            pst=conn.prepareStatement(query5);
            rs=pst.executeQuery();
            if(rs.next())
            {
            eid=Integer.parseInt(rs.getString("empID"));
           
            
            }
                           
         }catch(Exception e){}
      
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
                              // System.out.println(count);
                           
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
    
    
    
    public String basicSalOnValChange(String dname,String desig,int eid) {
          
        String query8="SELECT * FROM  master_file WHERE departmentName= '"+dname+"'  AND emPosition='"+desig+"' AND  empID='"+eid+"' ";
        String basicSal = null;
     try{
            pst=conn.prepareStatement(query8);
            rs=pst.executeQuery();
            if(rs.next())
            {
               basicSal=rs.getString("basicSal");
           
            }
                           
        }catch(Exception e)
        {
            System.out.println(e);
        }
      
              return basicSal;  
        } 
 
    
    public double setSalAdvAmount(int empId,String month)
       {
           double advAmount=0.0;
           try{
           String query="SELECT * FROM advance WHERE empID='"+empId+"' AND month='"+month+"' AND status='Not Deducted'" ;
           System.out.println(query);
           pst=conn.prepareStatement(query);
           rs=pst.executeQuery();
           
             if(rs.next())
              {
                 advAmount=Double.parseDouble(rs.getString("advAmt"));
                  System.out.println(advAmount);
               }
           
           }
          catch(SQLException | NumberFormatException e){
              System.out.println(e);
          }
           
                return advAmount;
       }


       
       public void updateAdvStatus(int peId, String month)
      {
                int advId=0;
          
          try {
              
                
               String query="SELECT * FROM advance WHERE empID='"+peId+"' AND month='"+month+"' AND status='Not Deducted'";
                pst=conn.prepareStatement(query);
                rs=pst.executeQuery();
                System.out.println("this"+query);
               if(rs.next())
               {
                    advId=Integer.parseInt(rs.getString("AdvId"));
                    
               }     
                       String query2="UPDATE advance SET status='Deducted' WHERE AdvId='"+advId+"'";
                       Statement stmt = (Statement) conn.createStatement(); 
                       stmt.executeUpdate(query2);
                        System.out.println("up"+query2);
                  
          } catch (SQLException | NumberFormatException e) {
              System.out.println(e);
          }
    
      
      }
    
    
    
    
   public double calcSal(double pbasicSal, double pAdvAmt,double pspecialBonus)
    {
           double netSal;
           
            
              netSal=Math.round((pbasicSal+pspecialBonus)-pAdvAmt);
       
              
    
               return netSal;
    } 
    
    
      public ResultSet loadDistStfSalTable()
      {
      
          try 
        {
            String query4= " SELECT dsalID as 'Salary ID', empID as 'Employee ID', empName as 'Employee Name' , deptName as 'Department Name', designation as 'Designation', basicSal as 'Basic Salary' , SpecialBonus as 'Special Bonus', AdvanceAmt as 'Advance Amount', NetSal as 'Net Salary', month as Month FROM distribution_stf_sal ";
            pst=conn.prepareStatement(query4);
            rs=pst.executeQuery();
            
        }
        catch (Exception e)
        { 
           System.out.println(e);  
            
        }
          return rs;
          
      }
   

      public  ResultSet loadDistEmpNamecmb()
      {
      
      try {
                String query4= " SELECT * FROM employee e,distribution_staff d  WHERE d.empID=e.empID "  ;
                pst=conn.prepareStatement(query4);
                rs=pst.executeQuery();
           }catch (SQLException ex) {
                Logger.getLogger(PayrollGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
                return rs;
      }
      
      
}




