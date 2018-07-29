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
public class off_prd_salCalDAO {
    
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    
    public off_prd_salCalDAO()
       {
          conn=(Connection) dbconnect.connect();
       }
       
    
       
       public void off_prd_salCalAddEntry(off_prd_salCal p1) throws SQLException
       {  
            try {
                  Statement stmt = (Statement) conn.createStatement();
                  String insertQ = "INSERT INTO prd_off_stf_sal(empID, empName, deptName, empPost, basicSal, sinOTHrs, doubleOTHrs, attBonus, AdvAmt, loanAmt, SpecialBonus, NetSal, month) "
                  + "VALUES( '"+p1.getEmpId()+"','" +p1.getEmpName() + "','" +p1.getDeptName() + "','" +p1.getDesignation() + "','" + p1.getBasicSal()+ "','" +p1.getSinOtHrs() + "','"+p1.getDoubleOtHrs()+"','" +p1.getAttBonus()+ "','" +p1.getAdvAmt()+ "','" +p1.getLoanAmt()+ "','" +p1.getSpecialBonus()+ "','" +p1.getNetSal()+ "','"+p1.getMonth()+"')";
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
       
       
       
       public void updateoff_prd_salCal(off_prd_salCal p1) throws SQLException 
       {  
            try {
                 Statement stmt = (Statement) conn.createStatement();
                 String updateQ="UPDATE  prd_off_stf_sal SET  empID= '"+p1.getEmpId()+"', empName = '"+p1.getEmpName()+"', deptName='"+p1.getDeptName()+"' ,empPost='"+p1.getDesignation() +"', basicSal = '"+p1.getBasicSal()+"',  sinOTHrs = '"+p1.getSinOtHrs()+"',  doubleOTHrs = '"+p1.getDoubleOtHrs()+"',  attBonus = '"+p1.getAttBonus()+"',  AdvAmt = '"+p1.getAdvAmt()+"',loanAmt='"+p1.getLoanAmt()+"',SpecialBonus='"+p1.getSpecialBonus()+"',NetSal='"+p1.getNetSal()+"' WHERE psalID='"+p1.getsalId()+"' ";
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
       
     
    public void delete_off_prd_salCalEntry(int salId){
        try{
            Statement stmt = (Statement) conn.createStatement();
            String deleteQ ="DELETE FROM prd_off_stf_sal  WHERE psalID='"+salId+"' ";
            System.out.println(deleteQ);
            stmt.executeUpdate(deleteQ);
            
          //      JOptionPane.showMessageDialog(null, "Successfully deleted!!!!!");
        }
        catch(Exception e)
        {
             System.out.println(e);
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
               
               
               
           
            
                           
         }catch(SQLException | NumberFormatException e)
         {
             System.out.println(e);
         }
      
             // System.out.println(desig);
              return desig;  
    } 
    
    
    
    public String basicSalOnValChange(String dname,String desig,int eid) {
          
        String query8="SELECT * FROM  master_file WHERE departmentName= '"+dname+"'  AND emPosition='"+desig+"' AND empID='"+eid+"' ";
        String basicSal = null;
     try{
            pst=conn.prepareStatement(query8);
            rs=pst.executeQuery();
            if(rs.next())
            {
               basicSal=rs.getString("basicSal");
           
            }
                           
        }catch(Exception e){}
      
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
          catch(SQLException | NumberFormatException e)
          {
                  System.out.println(e);
          }
           
                return advAmount;
       } 
    
       
       public double setSalLoanAmount(int empId)
       {    
            double loanAmount=0.0;
         
              try{
                String query="SELECT * FROM loan WHERE empID='"+empId+"' AND status='Loan Approved'";
                pst=conn.prepareStatement(query);
                rs=pst.executeQuery();
    
                if(rs.next())
                {
                  loanAmount=Double.parseDouble(rs.getString("MonthlyDue"));
                
                }
              
              }
              catch(SQLException | NumberFormatException e)
              {
                  System.out.println(e);
              }
       
              return loanAmount;
       }
       
       
       
        
       
       
       public ResultSet search(String searchBy){
     
          try {
               Statement stmt = (Statement) conn.createStatement(); 
               String serachQ="SELECT * FROM prd_off_stf_sal WHERE month LIKE '%"+searchBy +"%' ";
               rs=stmt.executeQuery(serachQ);
              
            
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
      
           return rs;
     }
       
      
       
      public void updateAdvStatus(int peId, String month)
      {
                int advId=0;
          
          try {
              
                
               String query="SELECT * FROM advance WHERE empID='"+peId+"' AND month='"+month+"' AND status='Not Deducted'";
                pst=conn.prepareStatement(query);
                rs=pst.executeQuery();
               if(rs.next())
               {
                    advId=Integer.parseInt(rs.getString("AdvId"));
                    
               }     
                       String query2="UPDATE advance SET status='Deducted' WHERE AdvId='"+advId+"'";
                       Statement stmt = (Statement) conn.createStatement(); 
                       stmt.executeUpdate(query2);
              
                  
          } catch (SQLException | NumberFormatException e) {
              System.out.println(e);
          }
    
      
      } 
      
      
      
      public void updateMonthlyLoanDue(int eId,double monDue )
      {
          try {
                  double updatedLoanAmt;
              
                String query="SELECT * FROM loan WHERE empID='"+eId+"' AND status='Loan Approved'";
                pst=conn.prepareStatement(query);
                rs=pst.executeQuery();
             
               if(rs.next())
               {
                   double totDue=Double.parseDouble(rs.getString("totalDue"));
                   double loanID=Double.parseDouble(rs.getString("loanID"));
                   updatedLoanAmt=totDue-monDue;
                   
                   String query2="UPDATE loan SET totalDue='"+updatedLoanAmt+"' WHERE loanID='"+loanID+"' ";
                   Statement stmt = (Statement) conn.createStatement();
                   stmt.executeUpdate(query2);   

                     if(updatedLoanAmt==0)
                     {
                        if(updatedLoanAmt==0){
                            String query3="UPDATE loan SET status='Loan Paid' WHERE loanID='"+loanID+"' "; 
                            stmt = (Statement) conn.createStatement();
                            stmt.executeUpdate(query3);

                        }
                     }
                   
               } 
                
              
              
          } catch (Exception e) {
              System.out.println(e);
          }
      
      
      
      }
      
      
      
      
         
    public boolean checkDuplicateValues(String month,int eid)
    {      
            boolean results=false;
        String query="SELECT COUNT(*) FROM prd_off_stf_sal WHERE empID='"+eid+"' AND month='"+month+"' ";    
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

      
    public double calcSal(int empId,double pbasicSal, double pspecialBonus,double psinOtHrs,double pDoubleOtHrs,double pLoanAmt,double pAdvAmt,double pAttBonus)
    {
           double netSal;
           double sinOtRate=0;
           double doubleOtRate=0; 
           double doubleOtAmt;
           double sinOtAmt;
           double epfRate=0;
           String query="SELECT * FROM master_file WHERE empID='"+empId+"'";
           
              try {
            
                  pst=conn.prepareStatement(query);
                  System.out.println(query);
                  rs=pst.executeQuery();
                  
                   if(rs.next())
                   {
                        sinOtRate=Double.parseDouble(rs.getString("singleOTRate"));
                        doubleOtRate=Double.parseDouble(rs.getString("doubleOtRate"));
                        epfRate=Double.parseDouble(rs.getString("epfRate"));
                   }
                  
                  }
              catch (SQLException | NumberFormatException e) {
                      
                      System.out.println(e+"Salary caluculation Falied!! ");
                  }
  
             // System.out.println("sinOtRate:"+sinOtRate);
             // System.out.println("DoubleOtRate:"+doubleOtRate);
              
              sinOtAmt=(pbasicSal/(8*25d)*sinOtRate);
              doubleOtAmt=(pbasicSal/(8*25d)*doubleOtRate);
              
             // System.out.println("sinOtAmt:"+sinOtAmt);
             // System.out.println("doubleOtAmt:"+doubleOtAmt);
              
              netSal=Math.round(((pbasicSal+(psinOtHrs*sinOtAmt)+(pDoubleOtHrs*doubleOtAmt)+pAttBonus+pspecialBonus)-(pAdvAmt+pLoanAmt))-((100-epfRate)/100d));
       
              
    
               return netSal;
    } 
    
    
    
    public ResultSet loadOff_Prd_sal_Table()
    {
           try 
        {
            String query3= " SELECT psalID as 'Salary ID', empID as 'Employee ID', empName as 'Employee Name', deptName as Department, empPost as 'Designation', basicSal as 'Basic Salary', sinOTHrs as 'Single OT Hours', doubleOTHrs as 'Double OT Hours', attBonus as 'Attendance Bonus', AdvAmt as 'Advance Amount', loanAmt as 'Loan Amount', SpecialBonus as 'Special Bonus', NetSal as 'Net Salary', month as Month FROM prd_off_stf_sal";
            pst=conn.prepareStatement(query3);
            rs=pst.executeQuery();
            
        }
        catch (Exception e)
        { 
           System.out.println(e);  
            
        }
           return rs;
    }
    
    
    public ResultSet loadEmpNamecmb()
    {
            try {
                
                String query4= " SELECT * FROM employee e , production_office_staff p WHERE  e.empID=p.empID"  ;
                pst=conn.prepareStatement(query4);
                rs=pst.executeQuery();
                          
            }catch (SQLException ex) {
                Logger.getLogger(PayrollGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        return rs;
    }
    
    
       
}
