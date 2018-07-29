/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distribution;

import Home.dbconnect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class DBO {

    private Pattern pattern;
    private Matcher matcher;
    private Matcher NicMatcher;

    private static final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private dbconnect dbConnManager = null;

    public DBO() {
        pattern = Pattern.compile(EMAIL_PATTERN);
        dbConnManager = new dbconnect();
    }

    //------Email Validation
    //Email validation method
    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }
//-------------------------------------------------------------------------------------------------------------------------------

    public boolean addagent(agent a) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.connect();

            Statement stmt = dbConn.createStatement();

            String query = "INSERT INTO agent(agfname,aglname,address,phone,email)"
                    + "VALUES( '" + a.getagfname() + "','" + a.getaglname() + "','" + a.getagadd() + "','" + a.getagphone() + "','" + a.getagemail() + "')";

            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }
            // JOptionPane.showMessageDialog(null,"Table agents added");
        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            JOptionPane.showMessageDialog(null, sQLException);
            result = false;
        }
        return result;

    }

    public boolean updateagent(agent ua) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Update agent set agfname='" + ua.getagfname() + "',aglname='" + ua.getaglname() + "',address='" + ua.getagadd() + "',phone='" + ua.getagphone() + "',email='" + ua.getagemail() + "'" + "where agID='" + ua.getagID() + "';";
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
        }
        return result;
    }

    public boolean deleteagent(agent da) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Delete from agent where agID='" + da.getagID() + "';";
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
        }
        return result;
    }

    public boolean addvehicle(vehicle v) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.connect();

            Statement stmt = dbConn.createStatement();

            String query = "INSERT INTO vehicle(vehicleNO,vType,vModel,serviceDate,availability)"
                    + "VALUES( '" + v.getvNO() + "','" + v.getvtype() + "','" + v.getvmodel() + "','" + v.getvservicedate() + "','" + v.getvavailability() + "')";

            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }
            // JOptionPane.showMessageDialog(null,"Table agents added");
        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            JOptionPane.showMessageDialog(null, sQLException);
            result = false;
        }
        return result;

    }

    public boolean updatevehicle(vehicle uv) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Update vehicle set vehicleNO='" + uv.getvNO() + "',vType='" + uv.getvtype() + "',vModel='" + uv.getvmodel() + "',serviceDate='" + uv.getvservicedate() + "',availability='" + uv.getvavailability() + "'" + "where vehicleNO='" + uv.getvNO() + "';";
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
        }
        return result;
    }

    public boolean deletevehicle(vehicle dv) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Delete from vehicle where vehicleNO='" + dv.getvNO() + "';";
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
        }
        return result;
    }

    public boolean addshop(shop s) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.connect();

            Statement stmt = dbConn.createStatement();

            String query = "INSERT INTO shop(shopName,address,phone,agentID)"
                    + "VALUES( '" + s.getshname() + "','" + s.getshadd() + "','" + s.getshphone() + "','" + s.getshagID() + "')";

            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }
            // JOptionPane.showMessageDialog(null,"Table agents added");
        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            JOptionPane.showMessageDialog(null, sQLException);
            result = false;
        }
        return result;

    }

    public boolean updateshop(shop us) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Update shop set shopName='" + us.getshname() + "',address='" + us.getshadd() + "',phone='" + us.getshphone() + "',agentID='" + us.getshagID() + "'where shopID='" + us.getshID() + "';";
            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Updated query failed");
            JOptionPane.showMessageDialog(null, sQLException);

            result = false;
        }
        return result;
    }

    public boolean deleteshop(shop ds) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Delete from shop where shopID='" + ds.getshID() + "';";
            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Delete query failed");
            JOptionPane.showMessageDialog(null, sQLException);

            result = false;
        }
        return result;
    }
    
    public boolean adddistribution(distribution d) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.connect();

            Statement stmt = dbConn.createStatement();

            String query = "INSERT INTO distribution(driverID,repID,vehicleNo,receiver,orderID,status)"
                    + "VALUES( '" + d.getdriverID() + "','" + d.getrepID() + "','" + d.getvNO() + "','" + d.getreceiver() + "','" + d.getorderID() + "','" + d.getstatus() + "')";

            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }
            // JOptionPane.showMessageDialog(null,"Table agents added");
        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            JOptionPane.showMessageDialog(null, sQLException);
            result = false;
        }
        return result;

    }
    
    public boolean updatedistribution(distribution ud) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Update distribution set driverID='" + ud.getdriverID() + "',repID='" + ud.getrepID() + "',vehicleNo='" + ud.getvNO() + "',receiver='" + ud.getreceiver() + "',orderID='" + ud.getorderID() + "',status ='"+ ud.getstatus() + "'where distributionID='" + ud.getdistributionID() + "';";
            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Update query failed");
            JOptionPane.showMessageDialog(null, sQLException);

            result = false;
        }
        return result;
    }
    
    public boolean deletedistribution(distribution dd) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Delete from distribution where distributionID='" + dd.getdistributionID() + "';";
            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Delete query failed");
            JOptionPane.showMessageDialog(null, sQLException);

            result = false;
        }
        return result;
    }
    
    public boolean addreturn(returns r) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.connect();

            Statement stmt = dbConn.createStatement();

            String query = "INSERT INTO returns(reID,agentID,productID,amount,date,disID,paidAmount)"
                    + "VALUES( '" + r.getrid() + "','" + r.getagid() + "','" + r.getpid() + "','" + r.getamount() + "','" + r.getdate() + "','" + r.getdistid() + "','" + r.getpaid() +  "')";

            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }
            // JOptionPane.showMessageDialog(null,"Table agents added");
        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");
            JOptionPane.showMessageDialog(null, sQLException);
            result = false;
        }
        return result;

    }
    
    public boolean updatereturn(returns ur) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Update returns set agentID='" + ur.getagid() + "',productID='" + ur.getpid() + "',amount='" + ur.getamount() + "',date='" + ur.getdate() + "',disID='" + ur.getdistid() + "',paidAmount ='"+ ur.getpaid() + "'where reID='" + ur.getrid() + "';";
            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Update query failed");
            JOptionPane.showMessageDialog(null, sQLException);

            result = false;
        }
        return result;
    }
    
    public boolean deletereturns(returns dr) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.connect();

            Statement stmt = dbConn.createStatement();
            String query = "Delete from returns where reID='" + dr.getrid() + "';";
            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Delete query failed");
            JOptionPane.showMessageDialog(null, sQLException);

            result = false;
        }
        return result;
    }
}

