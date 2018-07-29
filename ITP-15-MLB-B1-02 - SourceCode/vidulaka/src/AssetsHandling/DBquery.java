/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AssetsHandling;

import Home.dbconnect;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;




/**
 *
 * @author SIERRA
 */


public class DBquery {
    
    

    
        private Pattern pattern;
        private Matcher matcher;
 
        
   private static final String EMAIL_PATTERN = 
"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

      private dbconnect db=null;
       
     public DBquery (){
         pattern =Pattern.compile(EMAIL_PATTERN);
         db = new dbconnect();
     }  
        
     //----Email validation
     // Email validation method
     
     
     public boolean validate(final String hex){
         matcher = pattern.matcher(hex);
         return matcher.matches();
         
     }
  
   
  public boolean fixadd(getnset a) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();

         
            String query = "INSERT INTO fixed_asset(name,datePurchased,quantitty)  "
                    + "VALUES( '" + a.getName() + "','" + a.getDate() + "','" + a.getQuan()  + "')";
        
            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            //JOptionPane.showMessageDialog("");
            //JOptionPane.showMessageDialog(null, "cant input duplicate values"); 
            result = false;
        } finally {
          //  dbConnManager.con_close(dbConn);
        }
        return result;
    }
  
    public boolean fixadd1(getnset a1) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();

         
            String query = "INSERT INTO office_equipment(type)  "
                    + "VALUES( '" + a1.getOeqt()+ "')";

            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }
         //JOptionPane.showMessageDialog(rootPane, "Inserted Successfully"); 
        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            //JOptionPane.showMessageDialog(, sQLException);
            result = false;
        } finally {
          //  dbConnManager.con_close(dbConn);
        }
        return result;
    }
    
    public boolean fixadd2(getnset a2) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();

         
            String query = "INSERT INTO warrenty(type,timePeriod)  "
                    + "VALUES( '" + a2.getWty()+"','" + a2.getWtimep()+ "')";

            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            //JOptionPane.showMessageDialog(null, sQLException);
            result = false;
        } finally {
          //  dbConnManager.con_close(dbConn);
        }
        return result;
    } 
    
      public boolean fixadd3(getnset a3) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();

         
            String query = "INSERT INTO machine(serviceDate)  "
                    + "VALUES( '" + a3.getOserviced()+ "')";

            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            //JOptionPane.showMessageDialog(null, sQLException);
            result = false;
        } finally {
          //  dbConnManager.con_close(dbConn);
        }
        return result;
    } 
      public boolean updatefix(getnset u1) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Update fixed_asset set name='" + u1.getName() + "',datePurchased='" + u1.getDate() + "',quantitty='" + u1.getQuan() + "'" + "where assetID='" + u1.getAssetid() + "';";
            
            System.out.println(query);
           

            int val = stmt.executeUpdate(query);
            

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            JOptionPane.showMessageDialog(null, sQLException);

            result = false;
        } finally {
           // dbConnManager.con_close(dbConn);
        }
        return result;
    }
      
      
       public boolean updatefix1(getnset u1) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();
            String query1 = "Update office_equipment set type='" + u1.getOeqt() + " ' " + "where assetID='" + u1.getAssetid() + "';";
            
            System.out.println(query1);
           

            int val = stmt.executeUpdate(query1);
            

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            JOptionPane.showMessageDialog(null, sQLException);

            result = false;
        } finally {
           // dbConnManager.con_close(dbConn);
        }
        return result;
    }
        public boolean updatefix2(getnset u1) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();
            String query1 = "Update machine set serviceDate='" + u1.getOserviced() + " ' " + "where assetID='" + u1.getAssetid() + "';";
            
            System.out.println(query1);
           

            int val = stmt.executeUpdate(query1);
            

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            JOptionPane.showMessageDialog(null, sQLException);

            result = false;
        } finally {
           // dbConnManager.con_close(dbConn);
        }
        return result;
    }
         public boolean updatefix3(getnset u1) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();
            String query1 = "Update warrenty set type='" + u1.getWty() +  "',timePeriod='" + u1.getWtimep() + "'" + "where assetID='" + u1.getAssetid() + "';";
            
            System.out.println(query1);
           

            int val = stmt.executeUpdate(query1);
            

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            JOptionPane.showMessageDialog(null, sQLException);

            result = false;
        } finally {
           // dbConnManager.con_close(dbConn);
        }
        return result;
    }
         
         public boolean delete1(getnset uu1) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Delete  from fixed_asset where assetID='" + uu1.getAssetid() + "';";
            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            JOptionPane.showMessageDialog(null, sQLException);

            result = false;
        } finally {
           // dbConnManager.con_close(dbConn);
        }
        return result;
    }
         
         public boolean Tech(getnset b) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();

         
            String query = "INSERT INTO technician(techID,companyName,addressLine1,addressLine2,phone,email)  "
                    + "VALUES( '" + b.getTechid()+"','" + b.getCompname()+"','" + b.getAdd1()+"','" + b.getAdd2()+"','" + b.getPhone()+"','" + b.getEmail()+"')";

            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            //JOptionPane.showMessageDialog(null, sQLException);
            result = false;
        } finally {
          //  dbConnManager.con_close(dbConn);
        }
        return result;
    }
         
         public boolean updatetech(getnset b11) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Update technician set CompanyName='" + b11.getCompname() + "',addressLine1='" + b11.getAdd1() + "',addressLine2='" + b11.getAdd2() + "',phone='" + b11.getPhone() + "',email='" + b11.getEmail() + "'"+ "where techID='" + b11.getTechid() + "';";
            
            System.out.println(query);
           

            int val = stmt.executeUpdate(query);
            

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            JOptionPane.showMessageDialog(null, sQLException);

            result = false;
        } finally {
           // dbConnManager.con_close(dbConn);
        }
        return result;
    }
          public boolean deletetech(getnset uu11) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Delete  from technician where techID='" + uu11.getTechid() + "';";
            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            JOptionPane.showMessageDialog(null, sQLException);

            result = false;
        } finally {
           // dbConnManager.con_close(dbConn);
        }
        return result;
    }
         
         public boolean Supp(getnset c) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();

         
            String query = "INSERT INTO repair_supplier(addressLine1,addressLine2,name,email,phone)  "
                    + "VALUES( '"+ c.getAddr1()+"','" + c.getAddr2()+"','" + c.getSuppname()+"','" + c.getMail()+"','" + c.getContct()+ "')";

            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            //JOptionPane.showMessageDialog(null, sQLException);
            result = false;
        } finally {
          //  dbConnManager.con_close(dbConn);
        }
        return result;
    }
         
         public boolean updatesupp(getnset c1) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Update repair_supplier set addressLine1='" + c1.getAddr1() + "',addressLine2='" + c1.getAddr2() + "',name='" + c1.getSuppname() + "',email='" + c1.getMail() + "',phone='" + c1.getContct()+ "'"+ "where rsupID='" + c1.getSuppid()+ "';";
            
            System.out.println(query);
           

            int val = stmt.executeUpdate(query);
            

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            JOptionPane.showMessageDialog(null, sQLException);

            result = false;
        } finally {
           // dbConnManager.con_close(dbConn);
        }
        return result;
    }
      
         public boolean deletesupp(getnset uu12) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Delete  from repair_supplier where rsupID='" + uu12.getSuppid() + "';";
            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            JOptionPane.showMessageDialog(null, sQLException);

            result = false;
        } finally {
           // dbConnManager.con_close(dbConn);
        }
        return result;
    }
         
         
         public boolean assetrepair(getnset r) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();
            
            //String query1= "SELECT fixed_asset.name  FROM fixed_asset INNER JOIN repair ON fixed_asset.assetID=repair.assetID ";
                      
                                   
                                                

         
            String query = "INSERT INTO repair(date,spentTotalAmount,description,assetname)  "
                    + "VALUES( '" + r.getDate2() + "','" + r.getTot() + "','" + r.getDesc()  + "','" + r.getRepname()  + "')";
        
           // System.out.println(query1);
            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            //JOptionPane.showMessageDialog("");
            //JOptionPane.showMessageDialog(null, "cant input duplicate values"); 
            result = false;
        } finally {
          //  dbConnManager.con_close(dbConn);
        }
        return result;
    }
         
          public boolean updaterepair(getnset r1) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Update repair set date='" + r1.getDate2()+ "',description='" + r1.getDesc()+ "',spentTotalAmount='" + r1.getTot()+ "'"+ "where repairID='" + r1.getRepairid()+ "'";
            
            System.out.println(query);
           

            int val = stmt.executeUpdate(query);
            

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            JOptionPane.showMessageDialog(null, sQLException);

            result = false;
        } finally {
           // dbConnManager.con_close(dbConn);
        }
        return result;
    }
          
        public boolean deleterepair(getnset r2) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = db.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Delete  from repair  where repairID='" + r2.getRepairid()+ "';";
            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            JOptionPane.showMessageDialog(null, sQLException);

            result = false;
        } finally {
           // dbConnManager.con_close(dbConn);
        }
        return result;
    }
            
  
}

