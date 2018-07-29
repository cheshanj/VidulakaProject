package OrderPlanning;

import Home.dbconnect;
import java.sql.*;
import javax.swing.JOptionPane;

public class Ord_updateAssRaw {

    Connection con = null;
    ResultSet rs;
    PreparedStatement ps = null;
    CallableStatement cs = null;

    Ord_Request or = new Ord_Request();
   // boolean ord_Status;
   // boolean ordStatus[] = new boolean[10];

    public void ordstatus(int oid, String pid, String q) {
        // get a connection to a db
        con = dbconnect.connect();
        //int a = oid - 1;

        String[] raw = new String[10];
        int i = 0;

        //get raw materialID for current selected product
        String sql = "select rawMaterialID from product where productID='" + pid + "'";
        float quan[] = new float[10];
        float Q = Float.parseFloat(q);
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery(sql);

            while (rs.next()) {

                raw[i] = rs.getString("rawMaterialID");

                i++;
            }

        } catch (Exception e) {
        }

        int j = 0;

        //get quantity of every raw material for current product
        while (raw[j] != null) {
            String sql1 = "select quantity from product where productID='" + pid + "' and rawMaterialID='" + raw[j] + "' ";
            try {
                ps = con.prepareStatement(sql1);
                rs = ps.executeQuery(sql1);

                while (rs.next()) {
                    quan[j] = rs.getFloat("quantity");

                    j++;
                }
            } catch (Exception e) {
            }

        }

        //get total amount after reducing of every raw material
        int y = 0;
        float amt[] = new float[10];
        float Tot[] = new float[10];
        while (raw[y] != null) {
            String sql1 = "select amount from raw_material where itemCode='" + raw[y] + "'";
            try {
                ps = con.prepareStatement(sql1);
                rs = ps.executeQuery(sql1);

                while (rs.next()) {

                    amt[y] = rs.getFloat("amount");
                    Tot[y] = amt[y] - (quan[y] * Q);

                    y++;
                }
            } catch (Exception e) {
            }

        }
        int k = 0;
        float camt[] = new float[10];
        float tot[] = new float[10];
        //get critical amount of every raw material for current product and check for the availablility
        while (raw[k] != null) {
            String sql2 = "Select criticalAmount from raw_material where itemCode='" + raw[k] + "'";
            try {
                ps = con.prepareStatement(sql2);
                rs = ps.executeQuery(sql2);
                while (rs.next()) {

                    camt[k] = rs.getFloat("criticalAmount");

                    tot[k] = quan[k] * Q;

                    if (camt[k] <= Tot[k]) {
                      //  ordStatus[k] = true;
                        //Ord_insertAssignRaw iar=new Ord_insertAssignRaw(); 
                        
                        /*if (or.ord_btnInsert.isEnabled()) {
                                System.out.println(tot[k]);
                            //iar.insertAssRa(oid,raw[k],tot[k]);
                            try {
                                String sql4 = "insert into assign_raw_material (orderID,itemCode,amount) values ('" + oid + "','" + raw[k] + "','" + tot[k] + "') ";
                                ps = con.prepareStatement(sql4);

                                ps.execute();

                            } catch (Exception e) {

                                System.out.println(e);
                            }

                        }*/ 
                           
                            try { //System.out.println(tot[k]);
                                String sql5 = "update assign_raw_material set amount='" + tot[k] + "' where orderID='" + oid + "' and itemCode='"+raw[k]+"' ";
                                ps = con.prepareStatement(sql5);

                                ps.execute();

                            } catch (Exception e) {

                                System.out.println(e);
                            }
                        }
                        /*  try {
                         String sql4 = "insert into assign_raw_material (orderID,itemCode,amount) values ('" + oid + "','" + raw[k] + "','" + quan[k] + "') ";
                         ps = con.prepareStatement(sql4);
                         System.out.println(sql4);
                         ps.execute();

                         } catch (Exception e) {

                         System.out.println(e);
                         }*/
                     
                }
                k++;
            } catch (Exception e) {
            }
        }
        //int m = 0;

        //set order status if any of the above raw material not avialable.
       /* while (raw[m] != null) {
            if (ordStatus[m] == false) {
                ord_Status = false;

                break;
            } else {
                ord_Status = true;

            }
            m++;

        }

        return ord_Status;
*/
    }

}
