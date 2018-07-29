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
public class foldingStaffSalDAO {
 
       Connection conn=null;
       ResultSet rs=null;
       PreparedStatement pst=null;

       public foldingStaffSalDAO()
    {
        conn=(Connection) dbconnect.connect();
    }
       
    public void foldingStaffSalAddEntry(foldingStaffSal f1) throws SQLException
       {  
            try {
                  Statement stmt = (Statement) conn.createStatement();
                  String insertQ = "INSERT INTO folding_stf_sal(  empID, empName, deptName, designation, basicSal, attBonus, noOfPieces, piecerate, advAmt, SpecialBonus, NetSal, month) "
                  + "VALUES( '"+f1.getEmpId()+"','" +f1.getEmpName() + "','" +f1.getDeptName() + "','" +f1.getDesignation() + "','" + f1.getBasicSal()+ "','"+f1.getAttBonus()+"','" +f1.getNoOfPieces()+ "','" +f1.getpieceRate()+ "','"+f1.getAdvAmt()+"','"+f1.getSpecialBonus()+"','"+f1.getNetSal()+"','"+f1.getMonth()+"')";
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
    
    
          
    public void foldingStaffSalUpdateEntry(foldingStaffSal f1) throws SQLException
       {  
            try {
                  Statement stmt = (Statement) conn.createStatement();
                  String updateQ = "UPDATE folding_stf_sal SET empID ='"+f1.getEmpId()+"',empName='"+ f1.getEmpName() + "', deptName='" +f1.getDeptName() + "', designation='" +f1.getDesignation() + "', basicSal='" + f1.getBasicSal()+ "', attBonus='"+f1.getAttBonus()+"', noOfPieces='" +f1.getNoOfPieces()+ "', piecerate='" +f1.getpieceRate()+ "', advAmt='"+f1.getAdvAmt()+"', SpecialBonus='"+f1.getSpecialBonus()+"', NetSal='"+f1.getNetSal()+"', month='"+f1.getMonth()+"' WHERE fsalID='"+f1.getsalId()+"'";
                  System.out.println(updateQ);
                  stmt.executeUpdate(updateQ);
                
                  
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
    
       
         public void foldingStfSalDeleteEntry(int salId){
                try{
                    Statement stmt = (Statement) conn.createStatement();
                    String deleteQ ="DELETE FROM folding_stf_sal  WHERE fsalID='"+salId+"' ";
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
    
        
    
    
        public  ResultSet loadFoldingEmpNameCmb()
        {
      
            
                      String query4= "SELECT * FROM employee e, folding_staff f WHERE e.empID=f.empID"  ;
               try {
                      pst=conn.prepareStatement(query4);
                      rs=pst.executeQuery();
                    }catch (SQLException ex) {
                      Logger.getLogger(PayrollGUI.class.getName()).log(Level.SEVERE, null, ex);
                     }
                      return rs;
       }
    
        
        
       public ResultSet search(String searchBy){
     
          try {
               Statement stmt = (Statement) conn.createStatement(); 
               String serachQ="SELECT * FROM folding_stf_sal WHERE month LIKE '%"+searchBy +"%' ";
               rs=stmt.executeQuery(serachQ);
              
            
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
      
           return rs;
     }
        
       
       public int eIdOnValChange(String ename) {
       
            int eid = 0;
        String query5="SELECT * FROM  employee WHERE name= '"+ename +"' ";
           System.out.println(query5);
     try{
            pst=conn.prepareStatement(query5);
            rs=pst.executeQuery();
            if(rs.next())
            {
            eid=Integer.parseInt(rs.getString("empID"));
           
            
            }
                           
         }catch(SQLException | NumberFormatException e)
         {
             System.out.println(e);
         }
      
               System.out.println(eid);
              return eid;  
    }
       
    
    public String desigOnValChange(int eId) 
    {
       String desig = null;
         String queString="select * from master_file where empID='"+eId+"'";
         try{
            pst=conn.prepareStatement(queString);
            rs=pst.executeQuery();
            if(rs.next())
            {
            desig=rs.getString("emPosition");
           
            
            }
                           
         }catch(SQLException | NumberFormatException e)
         {
             System.out.println(e);
         }
        return desig;
    }
       
       
       
       
    
//    public double basicSalOnValChange(String dname,String desig,int eid) {
//          
//        String query8="SELECT * FROM  master_file WHERE departmentName= '"+dname+"'  AND emPosition='"+desig+"' AND empID='"+eid+"' ";
//        double basicSal=0;
//     try{
//            pst=conn.prepareStatement(query8);
//            System.out.println(query8);
//            rs=pst.executeQuery();
//            if(rs.next())
//            {
//               basicSal=Double.parseDouble(rs.getString("basicSal"));
//           
//            }
//                           
//        }catch(SQLException | NumberFormatException e)
//        {
//            System.out.println(e);
//        }
//      
//             System.out.println(basicSal);
//              return basicSal;  
//        } 
// 
   
    
    public double basicSalOnValChange(int eid) {
          
        String query8="SELECT * FROM  master_file WHERE  empID='"+eid+"' ";
        double basicSal = 0;
     try{
            pst=conn.prepareStatement(query8);
            rs=pst.executeQuery();
            if(rs.next())
            {
               basicSal=Double.parseDouble(rs.getString("basicSal"));
           
            }
                           
        }catch(SQLException | NumberFormatException e)
        {
            System.out.println(e);
        }
      
              return basicSal;  
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
       
  
     public double pieceRateOnvalChange(int eid)
     {
         double pieceRate = 0;
         try 
        {
            String query4= " SELECT * FROM master_file WHERE empID='"+eid+"' ";
            pst=conn.prepareStatement(query4);
            rs=pst.executeQuery();
            
            while(rs.next())
            {
               pieceRate=Double.parseDouble(rs.getString("pieceRate"));
            }
            
        }
        catch (SQLException | NumberFormatException e)
        { 
           System.out.println(e);  
            
        }
          return pieceRate;
     
               
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
     
       
    public boolean checkDuplicateValues(String month,int eid)
    {      
            boolean results=false;
        String query="SELECT COUNT(*) FROM folding_stf_sal WHERE empID='"+eid+"' AND month='"+month+"' ";    
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
       
    public  ResultSet loadFoldingSalTbl()
    {
        try 
        {
            String query4= " SELECT fsalID as 'Salary ID', empID as 'Employee ID', empName as 'Employee Name', deptName as Department, designation as Designation, basicSal as 'Basic Salary', attBonus as 'Attendance Bonus', noOfPieces as 'No of Pieces' , piecerate as 'Piece Rate', advAmt as 'Advance Amount', SpecialBonus as 'Special Bonus', NetSal as 'Net Salary', month as 'Month' FROM folding_stf_sal ";
            pst=conn.prepareStatement(query4);
            rs=pst.executeQuery();
            
        }
        catch (Exception e)
        { 
           System.out.println(e);  
            
        }
          return rs;
    }
   
    
    public int  getNoOfPieces(int empID,String month)
    {
        int noOfPieces = 0;
       
        
       String queryString="SELECT SUM(noOfPieces) as number  FROM folding_rate WHERE empID='"+empID+"' AND date LIKE '%"+month+"%' ";
      
       try{
         pst=conn.prepareStatement(queryString);
         rs=pst.executeQuery();
       
         if(rs.next())
         {
           noOfPieces=Integer.parseInt(rs.getString("number"));
         
         }
       
       
       }catch(SQLException | NumberFormatException e)
       {
           System.out.println(e);
       }
       
      return noOfPieces;
    }       
     

   
    public double calcSal(double pbasicSal, double pAdvAmt,double pspecialBonus,double pPieceRate,double noOfPieces,double pAttendanceBonus)
    {
             double netSal;
           
            
              netSal=Math.round((pbasicSal+pspecialBonus+(pPieceRate*noOfPieces)+pAttendanceBonus)-(pAdvAmt));
       
              
    
               return netSal;
    } 
    
       
       
}
