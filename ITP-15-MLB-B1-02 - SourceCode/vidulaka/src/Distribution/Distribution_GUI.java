/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distribution;

import Home.*;
import javax.swing.JDesktopPane;
import Distribution.yourinitialframe;
import Report.ReportView;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import static java.lang.String.format;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Sumudu Malshan
 */
public class Distribution_GUI extends javax.swing.JInternalFrame {

    /**
     * Creates new form internal
     */
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    ResultSet rs2 = null;
    ResultSet rs3 = null;
    ResultSet rs4 = null;
    ResultSet rs5 = null;
    ResultSet rs6 = null;
    ResultSet rs7 = null;
    ResultSet rs8 = null;
    ResultSet rs9 = null;
    ResultSet rs10 = null;
    ResultSet rs11 = null;
    ResultSet rs12 = null;
    ResultSet rs13 = null;
    ResultSet rs14 = null;
    ResultSet rs15 = null;
    ResultSet rs16 = null;
    ResultSet rs17 = null;
    ResultSet rs18 = null;
    ResultSet rs19 = null;
    ResultSet rs20 = null;
    ResultSet rs21 = null;
    byte[] userimage;
    DBO emailVal = new DBO();
    boolean agentfname;
    boolean agentlname;
    boolean agentphone;
    boolean vehicletype;
    boolean vehicleavailability;
    boolean shopagID;
    boolean shopname;
    boolean shopphone;
    boolean vehicleNO;
    boolean ramount;
    boolean rdistid;
    boolean rpamount;

    public Distribution_GUI() {
        initComponents();

        conn = dbconnect.connect();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);

        loadagentTable();
        loadvehicleTable();
        loadshopTable();
        loaddistTable();
        loadreturnTable();
        loadcompleteorderTable();

        loadagID();
        loadshopID();
        loaddisID();
        loaddistDriver();
        loaddistRep();
        loaddistVehile();
        loaddistPending();
        loadreturnID();

        buttongroup();

        dist_welcome.setVisible(false);
        dist_distribution.setVisible(false);
        dist_return.setVisible(false);
        dist_agent.setVisible(true);
        dist_vehicles.setVisible(false);

    }

    public void loadagentTable() {

        try {
            String query1 = " SELECT agID AgentID,agFname 'First Name',agLname 'Last Name',address Address,phone Phone,email Email FROM agent ";
            pst = conn.prepareStatement(query1);
            rs = pst.executeQuery();
            agent_table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void loadvehicleTable() {

        try {
            String query2 = " SELECT v.vehicleNo VehicleNO,v.vType 'Vehicle Type',v.vModel 'Vehicle Model',v.serviceDate 'Service Date',v.availability Status ,i.image Image FROM vehicle v,vehicle_image i where v.vehicleNO = i.vehicleID";
            pst = conn.prepareStatement(query2);
            rs1 = pst.executeQuery();
            vehicle_table.setModel(DbUtils.resultSetToTableModel(rs1));

        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void loadshopTable() {

        try {
            String query3 = " SELECT shopID ShopID,shopName 'Shop Name',address Address,phone Phone,agentID AgentID FROM shop ";
            pst = conn.prepareStatement(query3);
            rs2 = pst.executeQuery();
            shop_table.setModel(DbUtils.resultSetToTableModel(rs2));

        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void loadcompleteorderTable() {

        try {
            String query4 = " SELECT o.orderID OrderID,o.pID ProductID,o.orderDate 'Order Date',o.dueDate 'Due Date',o.quantity Quantity FROM orders o where  o.printingStatus = 'complete'  ";
            pst = conn.prepareStatement(query4);
            rs3 = pst.executeQuery();
            completeorder_table.setModel(DbUtils.resultSetToTableModel(rs3));

        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void loaddisID() {

        try {

            String query5 = "select max(distributionID) +1 distibutionID from distribution; ";
            pst = conn.prepareStatement(query5);
            rs4 = pst.executeQuery();

            while (rs4.next()) {

                dist_lbl_disIDshow.setText(rs4.getString("distibutionID"));

            }

        } catch (Exception e) {
        }
    }

    public void loaddistDriver() {

        try {

            String query6 = "select e.name from employee e,distribution_staff d where e.empId = d.empID and e.deptID = 3 and d.designation = 'Driver'; ";
            pst = conn.prepareStatement(query6);
            rs5 = pst.executeQuery();

            while (rs5.next()) {

                dist_cmb_distdriverlist.addItem(rs5.getString("name"));

            }

        } catch (Exception e) {
        }
    }

    public void loaddistRep() {

        try {

            String query7 = "select e.name from employee e,distribution_staff d where e.empID=d.empID and e.deptID = 3 and d.designation = 'Rep';";
            pst = conn.prepareStatement(query7);
            rs6 = pst.executeQuery();

            while (rs6.next()) {

                dist_cmb_distreplist.addItem(rs6.getString("name"));

            }

        } catch (Exception e) {
        }
    }

    public void loaddistVehile() {

        try {

            String query8 = "select vehicleNo from vehicle where availability = 'Available'; ";
            pst = conn.prepareStatement(query8);
            rs7 = pst.executeQuery();

            while (rs7.next()) {

                dist_cmb_distvehiclelist.addItem(rs7.getString("vehicleNo"));

            }

        } catch (Exception e) {
        }
    }

    public void loaddistReseiver() {

        String key = dist_lbl_distorderIDshow.getText();

        try {

            String query9 = "select a.agFname,a.agLname from agent a,request r where r.orderID = '" + key + "' and r.agentID = a.agID  ; ";
            pst = conn.prepareStatement(query9);
            rs8 = pst.executeQuery();

            while (rs8.next()) {

                dist_lbl_distreceivershow1.setText(rs8.getString("agFname"));
                dist_lbl_distreceivershow2.setText(rs8.getString("agLname"));

            }

        } catch (Exception e) {
        }
    }

    public void loaddistPending() {

        try {

            String query10 = "select count(distributionID) pending from distribution where status = 'On Progress'; ";
            pst = conn.prepareStatement(query10);
            rs9 = pst.executeQuery();

            while (rs9.next()) {

                dist_lbl_distpendingshow.setText(rs9.getString("pending"));

            }

        } catch (Exception e) {
        }
    }

    public void loaddistTable() {

        try {
            String query11 = " SELECT distributionID DistributionID,driverID DriverID,repId RepID,vehicleNo VehicleNO,receiver ReceiverID,orderID OrderID,status Status FROM distribution ";
            pst = conn.prepareStatement(query11);
            rs10 = pst.executeQuery();
            distributions_table.setModel(DbUtils.resultSetToTableModel(rs10));

        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void loadreturnTable() {

        try {
            String query12 = " SELECT reID ReturnID,agentID AgentID,productID ProductID,amount Amount,date Date,disID DistributionID,paidAmount 'Paid Amount' FROM returns ";
            pst = conn.prepareStatement(query12);
            rs11 = pst.executeQuery();
            return_table.setModel(DbUtils.resultSetToTableModel(rs11));

        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void loadreturnagID() {

        int key = Integer.parseInt(dist_txt_returndistID.getText());

        String sql = "SELECT receiver FROM distribution where distributionID LIKE '%" + key + "%'";

        try {
            pst = conn.prepareStatement(sql);
            rs12 = pst.executeQuery();

            while (rs12.next()) {

                dist_lbl_ragentIDshow.setText(rs12.getString("receiver"));

            }

        } catch (Exception e) {
        }
    }

    public void loadreturnPID() {

        String key = dist_txt_returndistID.getText();

        try {

            String query13 = "select o.pid, p.productType from orders o, distribution d, product p where d.orderID = o.orderID and o.pid = p.productID and d.distributionID = '" + key + "'; ";
            pst = conn.prepareStatement(query13);
            rs13 = pst.executeQuery();

            while (rs13.next()) {

                dist_lbl_rproductIDshow.setText(rs13.getString("pid"));
                dist_lbl_returnptypeshow.setText(rs13.getString("productType"));

            }

        } catch (Exception e) {
        }
    }

    public void loadreturnID() {

        try {

            String query14 = "select max(reID) +1 returnID from returns ";
            pst = conn.prepareStatement(query14);
            rs14 = pst.executeQuery();

            while (rs14.next()) {

                dist_lbl_returnIDshow.setText(rs14.getString("returnID"));

            }

        } catch (Exception e) {
        }
    }

    public int getdriverId() {

        int driverid = 0;
        String key = dist_cmb_distdriverlist.getSelectedItem().toString();

        try {

            String query15 = "select empID from employee where name = '" + key + "'  ";
            pst = conn.prepareStatement(query15);
            rs15 = pst.executeQuery();

            while (rs15.next()) {

                driverid = Integer.parseInt(rs15.getString("empID"));
            }

        } catch (Exception e) {
        }

        return driverid;
    }

    public int getrepId() {

        int repid = 0;
        String key = dist_cmb_distreplist.getSelectedItem().toString();

        try {

            String query16 = "select empID from employee where name = '" + key + "'  ";
            pst = conn.prepareStatement(query16);
            rs16 = pst.executeQuery();

            while (rs16.next()) {

                repid = Integer.parseInt(rs16.getString("empID"));
            }

        } catch (Exception e) {
        }

        return repid;
    }

    public int getreceiverid() {

        int receiver = 0;
        String key = dist_lbl_distorderIDshow.getText();

        try {

            String query17 = "select a.agID receiver from agent a,request r where r.orderID = '" + key + "' and r.agentID = a.agID  ; ";
            pst = conn.prepareStatement(query17);
            rs17 = pst.executeQuery();

            while (rs17.next()) {

                receiver = Integer.parseInt(rs17.getString("receiver"));
            }

        } catch (Exception e) {
        }

        return receiver;
    }

    public void buttongroup() {

        ButtonGroup bg = new ButtonGroup();
        bg.add(dist_rdb_distpending);
        bg.add(dist_rdb_distcomplete);
    }

    public void clearbuttons() {

        ButtonGroup cb = new ButtonGroup();
        cb.add(dist_rdb_distpending);
        cb.add(dist_rdb_distcomplete);
        cb.clearSelection();
    }

    public void setdrivername(String id) {

        try {

            String query18 = "select e.name name from employee e,distribution_staff d where e.empId = d.empID and e.deptID = 3 and d.designation = 'Driver' and d.empID = '" + id + "' ";
            pst = conn.prepareStatement(query18);
            rs18 = pst.executeQuery();

            while (rs18.next()) {

                dist_cmb_distdriverlist.setSelectedItem(rs18.getString("name"));

            }

        } catch (Exception e) {
        }
    }

    public void setrepname(String id) {

        try {

            String query19 = "select e.name name from employee e,distribution_staff d where e.empId = d.empID and e.deptID = 3 and d.designation = 'Rep' and d.empID = '" + id + "' ";
            pst = conn.prepareStatement(query19);
            rs19 = pst.executeQuery();

            while (rs19.next()) {

                dist_cmb_distreplist.setSelectedItem(rs19.getString("name"));

            }

        } catch (Exception e) {
        }
    }

    public void setstatus(String key) {

        if (key.equals("Completed")) {

            dist_rdb_distcomplete.setSelected(true);
            dist_rdb_distpending.setSelected(false);
        } else {

            dist_rdb_distcomplete.setSelected(false);
            dist_rdb_distpending.setSelected(true);
        }
    }

    public void loadagID() {

        try {

            String query20 = "select max(agID) +1 agID from agent; ";
            pst = conn.prepareStatement(query20);
            rs20 = pst.executeQuery();

            while (rs20.next()) {

                dist_lbl_agentidshow.setText(rs20.getString("agID"));

            }

        } catch (Exception e) {
        }
    }

    public void loadshopID() {

        try {

            String query21 = "select max(shopID) +1 shopID from shop; ";
            pst = conn.prepareStatement(query21);
            rs21 = pst.executeQuery();

            while (rs21.next()) {

                dist_lbl_shopIDshow.setText(rs21.getString("shopID"));

            }

        } catch (Exception e) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backPanel = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        dist_btn_distribution = new javax.swing.JButton();
        dist_btn_returns = new javax.swing.JButton();
        dist_btn_agents = new javax.swing.JButton();
        dist_btn_vehicles = new javax.swing.JButton();
        rightPanel = new javax.swing.JPanel();
        dist_distribution = new javax.swing.JPanel();
        dist_lbl_distributionhead = new javax.swing.JLabel();
        dist_lbl_disID = new javax.swing.JLabel();
        dist_lbl_disIDshow = new javax.swing.JLabel();
        dist_comorders = new javax.swing.JPanel();
        dist_table_completeorders = new javax.swing.JScrollPane();
        completeorder_table = new javax.swing.JTable();
        dist_lbl_comordershead = new javax.swing.JLabel();
        dist_lbl_distorderID = new javax.swing.JLabel();
        dist_lbl_distorderIDshow = new javax.swing.JLabel();
        dist_lbl_distdriver = new javax.swing.JLabel();
        dist_lbl_distrep = new javax.swing.JLabel();
        dist_lbl_distvehicle = new javax.swing.JLabel();
        dist_lbl_distreceiver = new javax.swing.JLabel();
        dist_lbl_distreceivershow1 = new javax.swing.JLabel();
        dist_lbl_distreceivershow2 = new javax.swing.JLabel();
        dist_lbl_diststatus = new javax.swing.JLabel();
        dist_cmb_distdriverlist = new javax.swing.JComboBox();
        dist_cmb_distreplist = new javax.swing.JComboBox();
        dist_cmb_distvehiclelist = new javax.swing.JComboBox();
        dist_rdb_distpending = new javax.swing.JRadioButton();
        dist_rdb_distcomplete = new javax.swing.JRadioButton();
        dist_pending = new javax.swing.JPanel();
        dist_lbl_distpendinghead = new javax.swing.JLabel();
        dist_lbl_distpendingshow = new javax.swing.JLabel();
        dist_distributions = new javax.swing.JPanel();
        dist_lbl_disthead = new javax.swing.JLabel();
        dist_table_distributions = new javax.swing.JScrollPane();
        distributions_table = new javax.swing.JTable();
        dist_txt_distsearch = new javax.swing.JTextField();
        dist_btn_distadd = new javax.swing.JButton();
        dist_btn_distupdate = new javax.swing.JButton();
        dist_btn_distdelete = new javax.swing.JButton();
        dist_btn_distclear = new javax.swing.JButton();
        dist_lbl_distributionbg = new javax.swing.JLabel();
        dist_return = new javax.swing.JPanel();
        dist_lbl_returnhead = new javax.swing.JLabel();
        dist_lbl_returntID = new javax.swing.JLabel();
        dist_lbl_ragentID = new javax.swing.JLabel();
        dist_lbl_rproductID = new javax.swing.JLabel();
        dist_lbl_ramount = new javax.swing.JLabel();
        dist_lbl_rdate = new javax.swing.JLabel();
        dist_lbl_rpaidamount = new javax.swing.JLabel();
        dist_lbl_ragentIDshow = new javax.swing.JLabel();
        dist_lbl_rproductIDshow = new javax.swing.JLabel();
        dist_lbl_returnIDshow = new javax.swing.JLabel();
        dist_txt_ramount = new javax.swing.JTextField();
        dist_txt_rpaidamount = new javax.swing.JTextField();
        dist_txt_rsearch = new javax.swing.JTextField();
        dist_table_return = new javax.swing.JScrollPane();
        return_table = new javax.swing.JTable();
        dist_btn_returnadd = new javax.swing.JButton();
        dist_btn_returnupdate = new javax.swing.JButton();
        dist_btn_returndelete = new javax.swing.JButton();
        dist_btn_returnclear = new javax.swing.JButton();
        dist_dte_returndate = new com.toedter.calendar.JDateChooser();
        dist_getreturns = new javax.swing.JPanel();
        dist_lbl_returngetinfohead = new javax.swing.JLabel();
        dist_lbl_rdistributionID = new javax.swing.JLabel();
        dist_txt_returndistID = new javax.swing.JTextField();
        dist_btn_returndetails = new javax.swing.JButton();
        dist_lbl_returnptypeshow = new javax.swing.JLabel();
        dist_returnreport = new javax.swing.JPanel();
        dist_btn_returnallreport = new javax.swing.JButton();
        dist_lbl_returnreport = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        dist_lbl_returnbg = new javax.swing.JLabel();
        dist_agent = new javax.swing.JPanel();
        dist_btn_agentadd = new javax.swing.JButton();
        dist_btn_agentupdate = new javax.swing.JButton();
        dist_btn_agentdelete = new javax.swing.JButton();
        dist_btn_agentclear = new javax.swing.JButton();
        dist_btn_agentshop = new javax.swing.JButton();
        dist_txt_agentsearch = new javax.swing.JTextField();
        dist_txt_agentfname = new javax.swing.JTextField();
        dist_txt_agentadd = new javax.swing.JTextField();
        dist_txt_agentphone = new javax.swing.JTextField();
        dist_txt_agentemail = new javax.swing.JTextField();
        dist_txt_agentlname = new javax.swing.JTextField();
        dist_table_agent = new javax.swing.JScrollPane();
        agent_table = new javax.swing.JTable();
        dist_lbl_agentid = new javax.swing.JLabel();
        dist_lbl_agentidshow = new javax.swing.JLabel();
        dist_lbl_agentfname = new javax.swing.JLabel();
        dist_lbl_agentlname = new javax.swing.JLabel();
        dist_lbl_address = new javax.swing.JLabel();
        dist_lbl_agentphone = new javax.swing.JLabel();
        dist_lbl_agentemail = new javax.swing.JLabel();
        dist_lbl_agenthead = new javax.swing.JLabel();
        dist_shop = new javax.swing.JPanel();
        dist_lbl_shophead = new javax.swing.JLabel();
        dist_lbl_shopagentID = new javax.swing.JLabel();
        dist_lbl_shopID = new javax.swing.JLabel();
        dist_lbl_shopname = new javax.swing.JLabel();
        dist_lbl_shopaddress = new javax.swing.JLabel();
        dist_lbl_shopphone = new javax.swing.JLabel();
        dist_lbl_shopIDshow = new javax.swing.JLabel();
        dist_txt_shopname = new javax.swing.JTextField();
        dist_txt_shopaddress = new javax.swing.JTextField();
        dist_txt_shopphone = new javax.swing.JTextField();
        dist_txt_shopagID = new javax.swing.JTextField();
        dist_txt_shopsearch = new javax.swing.JTextField();
        dist_table_shop = new javax.swing.JScrollPane();
        shop_table = new javax.swing.JTable();
        dist_btn_shopclear = new javax.swing.JButton();
        dist_btn_shopupdate = new javax.swing.JButton();
        dist_btn_shopadd = new javax.swing.JButton();
        dist_btn_shopdelete = new javax.swing.JButton();
        dist_agentreport = new javax.swing.JPanel();
        dist_lbl_agentreport = new javax.swing.JLabel();
        dist_btn_agentfullreport = new javax.swing.JButton();
        dist_btn_agentsinglereport = new javax.swing.JButton();
        dist_lbl_agentbg = new javax.swing.JLabel();
        dist_vehicles = new javax.swing.JPanel();
        dist_lbl_vehicleNO = new javax.swing.JLabel();
        dist_lbl_vehicletype = new javax.swing.JLabel();
        dist_lbl_vehiclemodel = new javax.swing.JLabel();
        dist_lbl_vehicleservicedate = new javax.swing.JLabel();
        dist_lbl_vehicleavailble = new javax.swing.JLabel();
        dist_lbl_vehiclehead = new javax.swing.JLabel();
        dist_txt_vehicleNO = new javax.swing.JTextField();
        dist_txt_vehicletype = new javax.swing.JTextField();
        dist_txt_vehiclemodel = new javax.swing.JTextField();
        dist_txt_vehiclesearch = new javax.swing.JTextField();
        dist_btn_vehicleadd = new javax.swing.JButton();
        dist_btn_vehicleupdate = new javax.swing.JButton();
        dist_btn_vehicledelete = new javax.swing.JButton();
        dist_btn_vehicleclear = new javax.swing.JButton();
        dist_btn_vehicleimage = new javax.swing.JButton();
        dist_table_vehicle = new javax.swing.JScrollPane();
        vehicle_table = new javax.swing.JTable();
        dist_lbl_vehicleimageshow = new javax.swing.JLabel();
        dist_lbl_vehicleimage = new javax.swing.JLabel();
        dist_lbl_vehicleerrormessage = new javax.swing.JLabel();
        dist_dte_vservicedate = new com.toedter.calendar.JDateChooser();
        dist_cmb_vavailability = new javax.swing.JComboBox();
        dist_lbl_vehiclebg = new javax.swing.JLabel();
        dist_welcome = new javax.swing.JPanel();
        dist_lbl_welcome = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(51, 51, 51));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setForeground(new java.awt.Color(51, 51, 51));
        setMaximizable(true);
        setEnabled(false);
        setFocusCycleRoot(false);
        setMaximumSize(new java.awt.Dimension(1024, 570));
        setMinimumSize(new java.awt.Dimension(1024, 570));
        setPreferredSize(new java.awt.Dimension(1024, 570));
        setRequestFocusEnabled(false);
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVerifyInputWhenFocusTarget(false);
        setVisible(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        backPanel.setMaximumSize(new java.awt.Dimension(1024, 570));
        backPanel.setMinimumSize(new java.awt.Dimension(1024, 570));
        backPanel.setOpaque(false);
        backPanel.setPreferredSize(new java.awt.Dimension(1024, 570));

        leftPanel.setMaximumSize(new java.awt.Dimension(100, 570));
        leftPanel.setMinimumSize(new java.awt.Dimension(100, 570));
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new java.awt.Dimension(100, 570));
        leftPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dist_btn_distribution.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Distribution/images/distribute.png"))); // NOI18N
        dist_btn_distribution.setBorderPainted(false);
        dist_btn_distribution.setContentAreaFilled(false);
        dist_btn_distribution.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        dist_btn_distribution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_distributionActionPerformed(evt);
            }
        });
        leftPanel.add(dist_btn_distribution, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 100, -1));

        dist_btn_returns.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Distribution/images/returns.png"))); // NOI18N
        dist_btn_returns.setBorderPainted(false);
        dist_btn_returns.setContentAreaFilled(false);
        dist_btn_returns.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        dist_btn_returns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_returnsActionPerformed(evt);
            }
        });
        leftPanel.add(dist_btn_returns, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 100, -1));

        dist_btn_agents.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Distribution/images/agents.png"))); // NOI18N
        dist_btn_agents.setBorderPainted(false);
        dist_btn_agents.setContentAreaFilled(false);
        dist_btn_agents.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        dist_btn_agents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_agentsActionPerformed(evt);
            }
        });
        leftPanel.add(dist_btn_agents, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, -1));

        dist_btn_vehicles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Distribution/images/vehicle.png"))); // NOI18N
        dist_btn_vehicles.setBorderPainted(false);
        dist_btn_vehicles.setContentAreaFilled(false);
        dist_btn_vehicles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_vehiclesActionPerformed(evt);
            }
        });
        leftPanel.add(dist_btn_vehicles, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 100, -1));

        rightPanel.setMaximumSize(new java.awt.Dimension(910, 570));
        rightPanel.setMinimumSize(new java.awt.Dimension(910, 570));
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new java.awt.Dimension(910, 570));
        rightPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dist_distribution.setBackground(new java.awt.Color(102, 255, 102));
        dist_distribution.setMaximumSize(new java.awt.Dimension(915, 570));
        dist_distribution.setMinimumSize(new java.awt.Dimension(915, 570));
        dist_distribution.setOpaque(false);
        dist_distribution.setPreferredSize(new java.awt.Dimension(915, 570));
        dist_distribution.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dist_lbl_distributionhead.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        dist_lbl_distributionhead.setForeground(new java.awt.Color(204, 204, 204));
        dist_lbl_distributionhead.setText("Distribution");
        dist_distribution.add(dist_lbl_distributionhead, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, -1, -1));

        dist_lbl_disID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_disID.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_disID.setText("Distribution ID");
        dist_distribution.add(dist_lbl_disID, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        dist_lbl_disIDshow.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_disIDshow.setForeground(new java.awt.Color(255, 255, 255));
        dist_distribution.add(dist_lbl_disIDshow, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 270, 60, 20));

        dist_comorders.setBackground(new java.awt.Color(204, 204, 204));
        dist_comorders.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        completeorder_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        completeorder_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                completeorder_tableMouseClicked(evt);
            }
        });
        dist_table_completeorders.setViewportView(completeorder_table);

        dist_lbl_comordershead.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_comordershead.setForeground(new java.awt.Color(0, 51, 0));
        dist_lbl_comordershead.setText("Completed Orders");

        javax.swing.GroupLayout dist_comordersLayout = new javax.swing.GroupLayout(dist_comorders);
        dist_comorders.setLayout(dist_comordersLayout);
        dist_comordersLayout.setHorizontalGroup(
            dist_comordersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dist_comordersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dist_comordersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dist_comordersLayout.createSequentialGroup()
                        .addComponent(dist_lbl_comordershead)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(dist_table_completeorders, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE))
                .addContainerGap())
        );
        dist_comordersLayout.setVerticalGroup(
            dist_comordersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dist_comordersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dist_lbl_comordershead)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dist_table_completeorders, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dist_distribution.add(dist_comorders, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 550, 140));

        dist_lbl_distorderID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_distorderID.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_distorderID.setText("Order ID");
        dist_distribution.add(dist_lbl_distorderID, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        dist_lbl_distorderIDshow.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_distorderIDshow.setForeground(new java.awt.Color(255, 255, 255));
        dist_distribution.add(dist_lbl_distorderIDshow, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 310, 60, 20));

        dist_lbl_distdriver.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_distdriver.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_distdriver.setText("Driver");
        dist_distribution.add(dist_lbl_distdriver, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, -1));

        dist_lbl_distrep.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_distrep.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_distrep.setText("Representative");
        dist_distribution.add(dist_lbl_distrep, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, -1, -1));

        dist_lbl_distvehicle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_distvehicle.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_distvehicle.setText("Vehicle");
        dist_distribution.add(dist_lbl_distvehicle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, -1, -1));

        dist_lbl_distreceiver.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_distreceiver.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_distreceiver.setText("Receiver");
        dist_distribution.add(dist_lbl_distreceiver, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, -1, -1));

        dist_lbl_distreceivershow1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_distreceivershow1.setForeground(new java.awt.Color(255, 255, 255));
        dist_distribution.add(dist_lbl_distreceivershow1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 470, 60, 20));

        dist_lbl_distreceivershow2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_distreceivershow2.setForeground(new java.awt.Color(255, 255, 255));
        dist_distribution.add(dist_lbl_distreceivershow2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 470, 60, 20));

        dist_lbl_diststatus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_diststatus.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_diststatus.setText("Status");
        dist_distribution.add(dist_lbl_diststatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, -1, -1));

        dist_cmb_distdriverlist.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select a Driver" }));
        dist_distribution.add(dist_cmb_distdriverlist, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 350, 140, -1));

        dist_cmb_distreplist.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select a Repersentative" }));
        dist_distribution.add(dist_cmb_distreplist, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 390, 140, -1));

        dist_cmb_distvehiclelist.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select a Vehicle" }));
        dist_distribution.add(dist_cmb_distvehiclelist, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 430, 140, -1));

        dist_rdb_distpending.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_rdb_distpending.setForeground(new java.awt.Color(255, 255, 255));
        dist_rdb_distpending.setText("On Progress");
        dist_distribution.add(dist_rdb_distpending, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 510, -1, -1));

        dist_rdb_distcomplete.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_rdb_distcomplete.setForeground(new java.awt.Color(255, 255, 255));
        dist_rdb_distcomplete.setText("Completed");
        dist_distribution.add(dist_rdb_distcomplete, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 510, -1, -1));

        dist_pending.setBackground(new java.awt.Color(204, 204, 204));
        dist_pending.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        dist_pending.setForeground(new java.awt.Color(204, 204, 204));

        dist_lbl_distpendinghead.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_distpendinghead.setForeground(new java.awt.Color(0, 51, 0));
        dist_lbl_distpendinghead.setText("Pending Distributions");

        dist_lbl_distpendingshow.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        dist_lbl_distpendingshow.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout dist_pendingLayout = new javax.swing.GroupLayout(dist_pending);
        dist_pending.setLayout(dist_pendingLayout);
        dist_pendingLayout.setHorizontalGroup(
            dist_pendingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dist_lbl_distpendinghead)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dist_pendingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dist_lbl_distpendingshow, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        dist_pendingLayout.setVerticalGroup(
            dist_pendingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dist_pendingLayout.createSequentialGroup()
                .addComponent(dist_lbl_distpendinghead)
                .addGap(18, 18, 18)
                .addComponent(dist_lbl_distpendingshow, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        dist_distribution.add(dist_pending, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 80, 150, 100));

        dist_distributions.setBackground(new java.awt.Color(204, 204, 204));
        dist_distributions.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        dist_lbl_disthead.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_disthead.setForeground(new java.awt.Color(0, 51, 0));
        dist_lbl_disthead.setText("Distributions");

        distributions_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        distributions_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                distributions_tableMouseClicked(evt);
            }
        });
        dist_table_distributions.setViewportView(distributions_table);

        dist_txt_distsearch.setText("Search");
        dist_txt_distsearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dist_txt_distsearchFocusLost(evt);
            }
        });
        dist_txt_distsearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_distsearchMouseClicked(evt);
            }
        });
        dist_txt_distsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dist_txt_distsearchKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout dist_distributionsLayout = new javax.swing.GroupLayout(dist_distributions);
        dist_distributions.setLayout(dist_distributionsLayout);
        dist_distributionsLayout.setHorizontalGroup(
            dist_distributionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dist_distributionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dist_distributionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dist_table_distributions)
                    .addGroup(dist_distributionsLayout.createSequentialGroup()
                        .addComponent(dist_lbl_disthead)
                        .addGap(0, 409, Short.MAX_VALUE))
                    .addComponent(dist_txt_distsearch))
                .addContainerGap())
        );
        dist_distributionsLayout.setVerticalGroup(
            dist_distributionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dist_distributionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dist_lbl_disthead)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dist_txt_distsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(dist_table_distributions, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        dist_distribution.add(dist_distributions, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 280, 520, 220));

        dist_btn_distadd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_distadd.setText("New Delivery");
        dist_btn_distadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_distaddActionPerformed(evt);
            }
        });
        dist_distribution.add(dist_btn_distadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 240, 140, -1));

        dist_btn_distupdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_distupdate.setText("Complete Delivery");
        dist_btn_distupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_distupdateActionPerformed(evt);
            }
        });
        dist_distribution.add(dist_btn_distupdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 240, -1, -1));

        dist_btn_distdelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_distdelete.setText("Cancel Delivery");
        dist_btn_distdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_distdeleteActionPerformed(evt);
            }
        });
        dist_distribution.add(dist_btn_distdelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 240, -1, -1));

        dist_btn_distclear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_distclear.setText("Clear");
        dist_btn_distclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_distclearActionPerformed(evt);
            }
        });
        dist_distribution.add(dist_btn_distclear, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 530, 80, -1));

        dist_lbl_distributionbg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        dist_distribution.add(dist_lbl_distributionbg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(dist_distribution, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        dist_return.setBackground(new java.awt.Color(255, 255, 255));
        dist_return.setMaximumSize(new java.awt.Dimension(915, 570));
        dist_return.setMinimumSize(new java.awt.Dimension(915, 570));
        dist_return.setOpaque(false);
        dist_return.setPreferredSize(new java.awt.Dimension(915, 570));
        dist_return.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dist_lbl_returnhead.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        dist_lbl_returnhead.setForeground(new java.awt.Color(204, 204, 204));
        dist_lbl_returnhead.setText("Return Handling");
        dist_return.add(dist_lbl_returnhead, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, -1, -1));

        dist_lbl_returntID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_returntID.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_returntID.setText("Return ID");
        dist_return.add(dist_lbl_returntID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        dist_lbl_ragentID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_ragentID.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_ragentID.setText("Agent ID");
        dist_return.add(dist_lbl_ragentID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        dist_lbl_rproductID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_rproductID.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_rproductID.setText("Product ID");
        dist_return.add(dist_lbl_rproductID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, -1));

        dist_lbl_ramount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_ramount.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_ramount.setText("Amount");
        dist_return.add(dist_lbl_ramount, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, -1, -1));

        dist_lbl_rdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_rdate.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_rdate.setText("Date");
        dist_return.add(dist_lbl_rdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, -1, -1));

        dist_lbl_rpaidamount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_rpaidamount.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_rpaidamount.setText("Paid Amount");
        dist_return.add(dist_lbl_rpaidamount, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 400, -1, -1));

        dist_lbl_ragentIDshow.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_ragentIDshow.setForeground(new java.awt.Color(255, 255, 255));
        dist_return.add(dist_lbl_ragentIDshow, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 110, 20));

        dist_lbl_rproductIDshow.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_rproductIDshow.setForeground(new java.awt.Color(255, 255, 255));
        dist_return.add(dist_lbl_rproductIDshow, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, 40, 20));

        dist_lbl_returnIDshow.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_returnIDshow.setForeground(new java.awt.Color(255, 255, 255));
        dist_return.add(dist_lbl_returnIDshow, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 120, 20));

        dist_txt_ramount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_ramountMouseClicked(evt);
            }
        });
        dist_txt_ramount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dist_txt_ramountKeyTyped(evt);
            }
        });
        dist_return.add(dist_txt_ramount, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, 140, -1));

        dist_txt_rpaidamount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_rpaidamountMouseClicked(evt);
            }
        });
        dist_txt_rpaidamount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_txt_rpaidamountActionPerformed(evt);
            }
        });
        dist_txt_rpaidamount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dist_txt_rpaidamountKeyTyped(evt);
            }
        });
        dist_return.add(dist_txt_rpaidamount, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 400, 140, -1));

        dist_txt_rsearch.setText("Search");
        dist_txt_rsearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dist_txt_rsearchFocusLost(evt);
            }
        });
        dist_txt_rsearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_rsearchMouseClicked(evt);
            }
        });
        dist_txt_rsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_txt_rsearchActionPerformed(evt);
            }
        });
        dist_txt_rsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dist_txt_rsearchKeyTyped(evt);
            }
        });
        dist_return.add(dist_txt_rsearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 250, 550, -1));

        return_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        return_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                return_tableMouseClicked(evt);
            }
        });
        dist_table_return.setViewportView(return_table);

        dist_return.add(dist_table_return, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 280, 550, 170));

        dist_btn_returnadd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_returnadd.setText("Add Return");
        dist_btn_returnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_returnaddActionPerformed(evt);
            }
        });
        dist_return.add(dist_btn_returnadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 480, 120, -1));

        dist_btn_returnupdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_returnupdate.setText("Update Return");
        dist_btn_returnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_returnupdateActionPerformed(evt);
            }
        });
        dist_return.add(dist_btn_returnupdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 520, -1, -1));

        dist_btn_returndelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_returndelete.setText("Delete Return");
        dist_btn_returndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_returndeleteActionPerformed(evt);
            }
        });
        dist_return.add(dist_btn_returndelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 480, 120, -1));

        dist_btn_returnclear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_returnclear.setText("Clear");
        dist_btn_returnclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_returnclearActionPerformed(evt);
            }
        });
        dist_return.add(dist_btn_returnclear, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 520, 120, -1));
        dist_return.add(dist_dte_returndate, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 340, 140, -1));

        dist_getreturns.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        dist_getreturns.setForeground(new java.awt.Color(204, 204, 204));

        dist_lbl_returngetinfohead.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_returngetinfohead.setForeground(new java.awt.Color(0, 51, 0));
        dist_lbl_returngetinfohead.setText("Get Infomaton");

        dist_lbl_rdistributionID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_rdistributionID.setText("Distribution ID");

        dist_txt_returndistID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_returndistIDMouseClicked(evt);
            }
        });
        dist_txt_returndistID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dist_txt_returndistIDKeyTyped(evt);
            }
        });

        dist_btn_returndetails.setText("Details");
        dist_btn_returndetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_returndetailsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dist_getreturnsLayout = new javax.swing.GroupLayout(dist_getreturns);
        dist_getreturns.setLayout(dist_getreturnsLayout);
        dist_getreturnsLayout.setHorizontalGroup(
            dist_getreturnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dist_getreturnsLayout.createSequentialGroup()
                .addGroup(dist_getreturnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dist_btn_returndetails)
                    .addGroup(dist_getreturnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(dist_getreturnsLayout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(dist_lbl_returngetinfohead))
                        .addGroup(dist_getreturnsLayout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addComponent(dist_lbl_rdistributionID)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(dist_txt_returndistID, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        dist_getreturnsLayout.setVerticalGroup(
            dist_getreturnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dist_getreturnsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dist_lbl_returngetinfohead)
                .addGap(17, 17, 17)
                .addGroup(dist_getreturnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dist_lbl_rdistributionID, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dist_txt_returndistID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dist_btn_returndetails)
                .addContainerGap())
        );

        dist_return.add(dist_getreturns, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 80, 250, 110));

        dist_lbl_returnptypeshow.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_returnptypeshow.setForeground(new java.awt.Color(255, 255, 255));
        dist_return.add(dist_lbl_returnptypeshow, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, 140, 20));

        dist_returnreport.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        dist_returnreport.setForeground(new java.awt.Color(204, 204, 204));

        dist_btn_returnallreport.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dist_btn_returnallreport.setText("Returns Reports");
        dist_btn_returnallreport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_returnallreportActionPerformed(evt);
            }
        });

        dist_lbl_returnreport.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        dist_lbl_returnreport.setForeground(new java.awt.Color(0, 51, 0));
        dist_lbl_returnreport.setText("Get Reports ");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setText("Returns pre Agent");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dist_returnreportLayout = new javax.swing.GroupLayout(dist_returnreport);
        dist_returnreport.setLayout(dist_returnreportLayout);
        dist_returnreportLayout.setHorizontalGroup(
            dist_returnreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dist_returnreportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dist_lbl_returnreport)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(dist_returnreportLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(dist_returnreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dist_btn_returnallreport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        dist_returnreportLayout.setVerticalGroup(
            dist_returnreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dist_returnreportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dist_lbl_returnreport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dist_btn_returnallreport)
                .addContainerGap())
        );

        dist_return.add(dist_returnreport, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 460, 170, 100));

        dist_lbl_returnbg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        dist_return.add(dist_lbl_returnbg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(dist_return, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        dist_agent.setBackground(new java.awt.Color(0, 0, 255));
        dist_agent.setMaximumSize(new java.awt.Dimension(915, 570));
        dist_agent.setMinimumSize(new java.awt.Dimension(915, 570));
        dist_agent.setOpaque(false);
        dist_agent.setPreferredSize(new java.awt.Dimension(915, 570));
        dist_agent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dist_btn_agentadd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_agentadd.setText("Add Agent");
        dist_btn_agentadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_agentaddActionPerformed(evt);
            }
        });
        dist_agent.add(dist_btn_agentadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 90, 120, -1));

        dist_btn_agentupdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_agentupdate.setText("Update Agent");
        dist_btn_agentupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_agentupdateActionPerformed(evt);
            }
        });
        dist_agent.add(dist_btn_agentupdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 140, 120, -1));

        dist_btn_agentdelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_agentdelete.setText("Delete Agent");
        dist_btn_agentdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_agentdeleteActionPerformed(evt);
            }
        });
        dist_agent.add(dist_btn_agentdelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 190, 120, -1));

        dist_btn_agentclear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_agentclear.setText("Clear");
        dist_btn_agentclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_agentclearActionPerformed(evt);
            }
        });
        dist_agent.add(dist_btn_agentclear, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 240, 120, -1));

        dist_btn_agentshop.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_agentshop.setText("View Shops");
        dist_btn_agentshop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_agentshopActionPerformed(evt);
            }
        });
        dist_agent.add(dist_btn_agentshop, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 290, 120, -1));

        dist_txt_agentsearch.setText("Search");
        dist_txt_agentsearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dist_txt_agentsearchFocusLost(evt);
            }
        });
        dist_txt_agentsearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_agentsearchMouseClicked(evt);
            }
        });
        dist_txt_agentsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dist_txt_agentsearchKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dist_txt_agentsearchKeyTyped(evt);
            }
        });
        dist_agent.add(dist_txt_agentsearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, 450, -1));

        dist_txt_agentfname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_agentfnameMouseClicked(evt);
            }
        });
        dist_txt_agentfname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dist_txt_agentfnameKeyTyped(evt);
            }
        });
        dist_agent.add(dist_txt_agentfname, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 130, 25));

        dist_txt_agentadd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_agentaddMouseClicked(evt);
            }
        });
        dist_agent.add(dist_txt_agentadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 200, 130, 25));

        dist_txt_agentphone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_agentphoneMouseClicked(evt);
            }
        });
        dist_txt_agentphone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dist_txt_agentphoneKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dist_txt_agentphoneKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dist_txt_agentphoneKeyTyped(evt);
            }
        });
        dist_agent.add(dist_txt_agentphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, 130, 25));

        dist_txt_agentemail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_agentemailMouseClicked(evt);
            }
        });
        dist_txt_agentemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_txt_agentemailActionPerformed(evt);
            }
        });
        dist_agent.add(dist_txt_agentemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 300, 130, 25));

        dist_txt_agentlname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_agentlnameMouseClicked(evt);
            }
        });
        dist_txt_agentlname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dist_txt_agentlnameKeyTyped(evt);
            }
        });
        dist_agent.add(dist_txt_agentlname, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 130, 25));

        agent_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        agent_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                agent_tableMouseClicked(evt);
            }
        });
        dist_table_agent.setViewportView(agent_table);

        dist_agent.add(dist_table_agent, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, -1, 200));

        dist_lbl_agentid.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_agentid.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_agentid.setText("Agent ID");
        dist_agent.add(dist_lbl_agentid, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, 20));

        dist_lbl_agentidshow.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_agentidshow.setForeground(new java.awt.Color(255, 255, 255));
        dist_agent.add(dist_lbl_agentidshow, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 60, 20));

        dist_lbl_agentfname.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_agentfname.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_agentfname.setText("First Name");
        dist_agent.add(dist_lbl_agentfname, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 80, 20));

        dist_lbl_agentlname.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_agentlname.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_agentlname.setText("Last Name");
        dist_agent.add(dist_lbl_agentlname, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, 20));

        dist_lbl_address.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_address.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_address.setText("Address");
        dist_agent.add(dist_lbl_address, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, 20));

        dist_lbl_agentphone.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_agentphone.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_agentphone.setText("Phone No");
        dist_agent.add(dist_lbl_agentphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, -1, 20));

        dist_lbl_agentemail.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_agentemail.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_agentemail.setText("Email");
        dist_agent.add(dist_lbl_agentemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, 20));

        dist_lbl_agenthead.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        dist_lbl_agenthead.setForeground(new java.awt.Color(204, 204, 255));
        dist_lbl_agenthead.setText("Agent Handling");
        dist_agent.add(dist_lbl_agenthead, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, -1, -1));

        dist_shop.setBackground(new java.awt.Color(153, 153, 153));
        dist_shop.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        dist_shop.setAlignmentX(0.0F);
        dist_shop.setAlignmentY(0.0F);
        dist_shop.setMaximumSize(new java.awt.Dimension(900, 230));
        dist_shop.setMinimumSize(new java.awt.Dimension(900, 230));
        dist_shop.setName(""); // NOI18N
        dist_shop.setPreferredSize(new java.awt.Dimension(900, 230));
        dist_shop.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dist_lbl_shophead.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        dist_lbl_shophead.setForeground(new java.awt.Color(204, 204, 204));
        dist_lbl_shophead.setText("Shops Handling");
        dist_shop.add(dist_lbl_shophead, new org.netbeans.lib.awtextra.AbsoluteConstraints(352, 13, -1, -1));

        dist_lbl_shopagentID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_shopagentID.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_shopagentID.setText("Agent ID");
        dist_shop.add(dist_lbl_shopagentID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        dist_lbl_shopID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_shopID.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_shopID.setText("Shop ID");
        dist_shop.add(dist_lbl_shopID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        dist_lbl_shopname.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_shopname.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_shopname.setText("Shop Name");
        dist_shop.add(dist_lbl_shopname, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        dist_lbl_shopaddress.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_shopaddress.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_shopaddress.setText("Address");
        dist_shop.add(dist_lbl_shopaddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        dist_lbl_shopphone.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_shopphone.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_shopphone.setText("Phone");
        dist_shop.add(dist_lbl_shopphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));

        dist_lbl_shopIDshow.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_shopIDshow.setForeground(new java.awt.Color(255, 255, 255));
        dist_shop.add(dist_lbl_shopIDshow, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 101, 25));

        dist_txt_shopname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_shopnameMouseClicked(evt);
            }
        });
        dist_txt_shopname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dist_txt_shopnameKeyTyped(evt);
            }
        });
        dist_shop.add(dist_txt_shopname, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 115, 101, 25));

        dist_txt_shopaddress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_shopaddressMouseClicked(evt);
            }
        });
        dist_shop.add(dist_txt_shopaddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 101, 25));

        dist_txt_shopphone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_shopphoneMouseClicked(evt);
            }
        });
        dist_txt_shopphone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dist_txt_shopphoneKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dist_txt_shopphoneKeyTyped(evt);
            }
        });
        dist_shop.add(dist_txt_shopphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 101, 25));

        dist_txt_shopagID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_shopagIDMouseClicked(evt);
            }
        });
        dist_txt_shopagID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dist_txt_shopagIDKeyTyped(evt);
            }
        });
        dist_shop.add(dist_txt_shopagID, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 101, 25));

        dist_txt_shopsearch.setText("Search");
        dist_txt_shopsearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dist_txt_shopsearchFocusLost(evt);
            }
        });
        dist_txt_shopsearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_shopsearchMouseClicked(evt);
            }
        });
        dist_txt_shopsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dist_txt_shopsearchKeyTyped(evt);
            }
        });
        dist_shop.add(dist_txt_shopsearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, 450, 25));

        shop_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        shop_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                shop_tableMouseClicked(evt);
            }
        });
        dist_table_shop.setViewportView(shop_table);

        dist_shop.add(dist_table_shop, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, -1, 114));

        dist_btn_shopclear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_shopclear.setText("Clear");
        dist_btn_shopclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_shopclearActionPerformed(evt);
            }
        });
        dist_shop.add(dist_btn_shopclear, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 190, 110, -1));

        dist_btn_shopupdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_shopupdate.setText("Edit");
        dist_btn_shopupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_shopupdateActionPerformed(evt);
            }
        });
        dist_shop.add(dist_btn_shopupdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 110, 110, -1));

        dist_btn_shopadd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_shopadd.setText("Insert");
        dist_btn_shopadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_shopaddActionPerformed(evt);
            }
        });
        dist_shop.add(dist_btn_shopadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 70, 110, -1));

        dist_btn_shopdelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_shopdelete.setText("Remove");
        dist_btn_shopdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_shopdeleteActionPerformed(evt);
            }
        });
        dist_shop.add(dist_btn_shopdelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 150, 110, -1));

        dist_agent.add(dist_shop, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, -1, -1));
        dist_shop.getAccessibleContext().setAccessibleName("");
        dist_shop.getAccessibleContext().setAccessibleDescription("");

        dist_agentreport.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        dist_agentreport.setForeground(new java.awt.Color(153, 153, 153));

        dist_lbl_agentreport.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        dist_lbl_agentreport.setForeground(new java.awt.Color(0, 51, 0));
        dist_lbl_agentreport.setText("Agent Reports");

        dist_btn_agentfullreport.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_agentfullreport.setText("Agent Report");
        dist_btn_agentfullreport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_agentfullreportActionPerformed(evt);
            }
        });

        dist_btn_agentsinglereport.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_agentsinglereport.setText("Single Agent Report");
        dist_btn_agentsinglereport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_agentsinglereportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dist_agentreportLayout = new javax.swing.GroupLayout(dist_agentreport);
        dist_agentreport.setLayout(dist_agentreportLayout);
        dist_agentreportLayout.setHorizontalGroup(
            dist_agentreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dist_agentreportLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dist_agentreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dist_agentreportLayout.createSequentialGroup()
                        .addComponent(dist_lbl_agentreport)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dist_agentreportLayout.createSequentialGroup()
                        .addComponent(dist_btn_agentsinglereport)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addComponent(dist_btn_agentfullreport, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        dist_agentreportLayout.setVerticalGroup(
            dist_agentreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dist_agentreportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dist_lbl_agentreport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dist_agentreportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dist_btn_agentfullreport)
                    .addComponent(dist_btn_agentsinglereport))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dist_agent.add(dist_agentreport, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 320, 70));

        dist_lbl_agentbg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        dist_agent.add(dist_lbl_agentbg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(dist_agent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        dist_vehicles.setBackground(new java.awt.Color(0, 0, 255));
        dist_vehicles.setMaximumSize(new java.awt.Dimension(915, 570));
        dist_vehicles.setName(""); // NOI18N
        dist_vehicles.setPreferredSize(new java.awt.Dimension(915, 570));
        dist_vehicles.setVerifyInputWhenFocusTarget(false);
        dist_vehicles.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dist_lbl_vehicleNO.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_vehicleNO.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_vehicleNO.setText("Vehicle NO");
        dist_vehicles.add(dist_lbl_vehicleNO, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        dist_lbl_vehicletype.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_vehicletype.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_vehicletype.setText("Vehicle Type");
        dist_vehicles.add(dist_lbl_vehicletype, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        dist_lbl_vehiclemodel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_vehiclemodel.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_vehiclemodel.setText("Vehicle Model");
        dist_vehicles.add(dist_lbl_vehiclemodel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));

        dist_lbl_vehicleservicedate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_vehicleservicedate.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_vehicleservicedate.setText("Service Date");
        dist_vehicles.add(dist_lbl_vehicleservicedate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, -1));

        dist_lbl_vehicleavailble.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dist_lbl_vehicleavailble.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_vehicleavailble.setText("Availability");
        dist_vehicles.add(dist_lbl_vehicleavailble, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, -1));

        dist_lbl_vehiclehead.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        dist_lbl_vehiclehead.setForeground(new java.awt.Color(204, 204, 204));
        dist_lbl_vehiclehead.setText("Vehicle Handling");
        dist_vehicles.add(dist_lbl_vehiclehead, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, -1, -1));

        dist_txt_vehicleNO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_vehicleNOMouseClicked(evt);
            }
        });
        dist_txt_vehicleNO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_txt_vehicleNOActionPerformed(evt);
            }
        });
        dist_txt_vehicleNO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dist_txt_vehicleNOKeyTyped(evt);
            }
        });
        dist_vehicles.add(dist_txt_vehicleNO, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 150, -1));

        dist_txt_vehicletype.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_vehicletypeMouseClicked(evt);
            }
        });
        dist_txt_vehicletype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_txt_vehicletypeActionPerformed(evt);
            }
        });
        dist_txt_vehicletype.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dist_txt_vehicletypeKeyTyped(evt);
            }
        });
        dist_vehicles.add(dist_txt_vehicletype, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 150, -1));

        dist_txt_vehiclemodel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_vehiclemodelMouseClicked(evt);
            }
        });
        dist_vehicles.add(dist_txt_vehiclemodel, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 150, -1));

        dist_txt_vehiclesearch.setText("Search");
        dist_txt_vehiclesearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dist_txt_vehiclesearchFocusLost(evt);
            }
        });
        dist_txt_vehiclesearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dist_txt_vehiclesearchMouseClicked(evt);
            }
        });
        dist_txt_vehiclesearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dist_txt_vehiclesearchKeyTyped(evt);
            }
        });
        dist_vehicles.add(dist_txt_vehiclesearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 150, 490, -1));

        dist_btn_vehicleadd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_vehicleadd.setText("Add Vehicle");
        dist_btn_vehicleadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_vehicleaddActionPerformed(evt);
            }
        });
        dist_vehicles.add(dist_btn_vehicleadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 100, -1, -1));

        dist_btn_vehicleupdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_vehicleupdate.setText("Update Vehicle");
        dist_btn_vehicleupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_vehicleupdateActionPerformed(evt);
            }
        });
        dist_vehicles.add(dist_btn_vehicleupdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 100, -1, -1));

        dist_btn_vehicledelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_vehicledelete.setText("Delete Vehicle");
        dist_btn_vehicledelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_vehicledeleteActionPerformed(evt);
            }
        });
        dist_vehicles.add(dist_btn_vehicledelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 100, -1, -1));

        dist_btn_vehicleclear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_btn_vehicleclear.setText("Clear");
        dist_btn_vehicleclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_vehicleclearActionPerformed(evt);
            }
        });
        dist_vehicles.add(dist_btn_vehicleclear, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 420, 120, -1));

        dist_btn_vehicleimage.setText("Image");
        dist_btn_vehicleimage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dist_btn_vehicleimageActionPerformed(evt);
            }
        });
        dist_vehicles.add(dist_btn_vehicleimage, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 540, -1, -1));

        vehicle_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Vehicle NO", "Vehicle Type", "Vehicle Model", "Next Service Date", "Availbility"
            }
        ));
        vehicle_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vehicle_tableMouseClicked(evt);
            }
        });
        dist_table_vehicle.setViewportView(vehicle_table);

        dist_vehicles.add(dist_table_vehicle, new org.netbeans.lib.awtextra.AbsoluteConstraints(392, 180, 490, 190));

        dist_lbl_vehicleimageshow.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        dist_lbl_vehicleimageshow.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_vehicleimageshow.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        dist_vehicles.add(dist_lbl_vehicleimageshow, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 420, 170, 140));

        dist_lbl_vehicleimage.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        dist_lbl_vehicleimage.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_vehicleimage.setText("Vehicle");
        dist_vehicles.add(dist_lbl_vehicleimage, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 70, -1));

        dist_lbl_vehicleerrormessage.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dist_lbl_vehicleerrormessage.setForeground(new java.awt.Color(255, 255, 255));
        dist_vehicles.add(dist_lbl_vehicleerrormessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 524, 190, 30));
        dist_vehicles.add(dist_dte_vservicedate, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 290, 150, -1));

        dist_cmb_vavailability.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select a Status", "Available", "Unavailable" }));
        dist_vehicles.add(dist_cmb_vavailability, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 360, 150, -1));

        dist_lbl_vehiclebg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        dist_lbl_vehiclebg.setToolTipText("");
        dist_vehicles.add(dist_lbl_vehiclebg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(dist_vehicles, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        dist_welcome.setBackground(new java.awt.Color(0, 0, 0));
        dist_welcome.setMaximumSize(new java.awt.Dimension(915, 570));
        dist_welcome.setMinimumSize(new java.awt.Dimension(915, 570));
        dist_welcome.setPreferredSize(new java.awt.Dimension(915, 570));
        dist_welcome.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dist_lbl_welcome.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        dist_lbl_welcome.setForeground(new java.awt.Color(255, 255, 255));
        dist_lbl_welcome.setText("Welcome");
        dist_lbl_welcome.setMaximumSize(new java.awt.Dimension(915, 570));
        dist_lbl_welcome.setMinimumSize(new java.awt.Dimension(915, 570));
        dist_welcome.add(dist_lbl_welcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, -1, -1));

        rightPanel.add(dist_welcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout backPanelLayout = new javax.swing.GroupLayout(backPanel);
        backPanel.setLayout(backPanelLayout);
        backPanelLayout.setHorizontalGroup(
            backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backPanelLayout.createSequentialGroup()
                .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );
        backPanelLayout.setVerticalGroup(
            backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backPanelLayout.createSequentialGroup()
                .addGroup(backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(backPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/background.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 0, -1, -1));

        setBounds(0, 0, 1024, 570);
    }// </editor-fold>//GEN-END:initComponents

    private void dist_btn_distributionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_distributionActionPerformed
        // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels

        dist_welcome.setVisible(false);
        dist_distribution.setVisible(true);
        dist_return.setVisible(false);
        dist_agent.setVisible(false);
        dist_vehicles.setVisible(false);

        rightPanel.add(dist_distribution);
        rightPanel.repaint();
        rightPanel.revalidate();


    }//GEN-LAST:event_dist_btn_distributionActionPerformed

    private void dist_btn_returnsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_returnsActionPerformed
        // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels

        dist_welcome.setVisible(false);
        dist_distribution.setVisible(false);
        dist_return.setVisible(true);
        dist_agent.setVisible(false);
        dist_vehicles.setVisible(false);

        rightPanel.add(dist_return);
        rightPanel.repaint();
        rightPanel.revalidate();
    }//GEN-LAST:event_dist_btn_returnsActionPerformed

    private void dist_btn_agentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_agentsActionPerformed

        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels

        dist_welcome.setVisible(false);
        dist_distribution.setVisible(false);
        dist_return.setVisible(false);
        dist_agent.setVisible(true);
        dist_vehicles.setVisible(false);

        rightPanel.add(dist_agent);
        rightPanel.repaint();
        rightPanel.revalidate();
    }//GEN-LAST:event_dist_btn_agentsActionPerformed

    private void dist_btn_vehiclesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_vehiclesActionPerformed
        // TODO add your handling code here:
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels

        dist_welcome.setVisible(false);
        dist_distribution.setVisible(false);
        dist_return.setVisible(false);
        dist_agent.setVisible(false);
        dist_vehicles.setVisible(true);

        rightPanel.add(dist_vehicles);
        rightPanel.repaint();
        rightPanel.revalidate();
    }//GEN-LAST:event_dist_btn_vehiclesActionPerformed

    private void dist_btn_agentaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_agentaddActionPerformed
        // TODO add your handling code here:

        if (dist_txt_agentfname.getText().isEmpty() || dist_txt_agentlname.getText().isEmpty() || dist_txt_agentadd.getText().isEmpty() || dist_txt_agentphone.getText().isEmpty() || dist_txt_agentemail.getText().isEmpty()) {
            if (dist_txt_agentfname.getText().isEmpty()) {
                dist_txt_agentfname.setBackground(Color.PINK);
            }
            if (dist_txt_agentlname.getText().isEmpty()) {
                dist_txt_agentlname.setBackground(Color.PINK);
            }
            if (dist_txt_agentadd.getText().isEmpty()) {
                dist_txt_agentadd.setBackground(Color.PINK);
            }
            if (dist_txt_agentphone.getText().isEmpty()) {
                dist_txt_agentphone.setBackground(Color.PINK);
            }
            if (dist_txt_agentemail.getText().isEmpty()) {
                dist_txt_agentemail.setBackground(Color.PINK);
            }

            JOptionPane.showMessageDialog(rootPane, "There is one or more empty fields");
        } else {
            if (!emailVal.validate(dist_txt_agentemail.getText().trim())) {
                JOptionPane.showMessageDialog(rootPane, "Not an valied Email address");
            } else {

                int i = dist_txt_agentphone.getText().length();
                if (i > 10 || i < 10) {
                    JOptionPane.showMessageDialog(rootPane, "There should be 10 only characters in a phone NO");
                } else {
                    if (agentfname || agentlname || agentphone) {
                        JOptionPane.showMessageDialog(rootPane, "One or more invalid fields");
                    } else {
                        String agfname = dist_txt_agentfname.getText();
                        String aglname = dist_txt_agentlname.getText();
                        String agaddress = dist_txt_agentadd.getText();
                        String agphone = dist_txt_agentphone.getText();
                        String agemail = dist_txt_agentemail.getText();

                        agent aa = new agent(agfname, aglname, agaddress, agphone, agemail);

                        DBO dao = new DBO();
                        dao.addagent(aa);

                        dist_txt_agentfname.setText("");
                        dist_txt_agentlname.setText("");
                        dist_txt_agentadd.setText("");
                        dist_txt_agentphone.setText("");
                        dist_txt_agentemail.setText("");

                        loadagentTable();
                        loadagID();

                        JOptionPane.showMessageDialog(rootPane, "New record added");
                    }
                }
            }
    }//GEN-LAST:event_dist_btn_agentaddActionPerformed
    }
    private void dist_txt_vehicletypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_txt_vehicletypeActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_dist_txt_vehicletypeActionPerformed

    private void agent_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_agent_tableMouseClicked
        // TODO add your handling code here:

        int r = agent_table.getSelectedRow();

        String agID = agent_table.getValueAt(r, 0).toString();
        String agfname = agent_table.getValueAt(r, 1).toString();
        String aglname = agent_table.getValueAt(r, 2).toString();
        String agaddress = agent_table.getValueAt(r, 3).toString();
        String agphone = agent_table.getValueAt(r, 4).toString();
        String agemail = agent_table.getValueAt(r, 5).toString();

        dist_lbl_agentidshow.setText(agID);
        dist_txt_agentfname.setText(agfname);
        dist_txt_agentlname.setText(aglname);
        dist_txt_agentadd.setText(agaddress);
        dist_txt_agentphone.setText(agphone);
        dist_txt_agentemail.setText(agemail);

    }//GEN-LAST:event_agent_tableMouseClicked

    private void dist_btn_agentupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_agentupdateActionPerformed
        // TODO add your handling code here:
        if (dist_txt_agentfname.getText().isEmpty() || dist_txt_agentlname.getText().isEmpty() || dist_txt_agentadd.getText().isEmpty() || dist_txt_agentphone.getText().isEmpty() || dist_txt_agentemail.getText().isEmpty()) {
            if (dist_txt_agentfname.getText().isEmpty()) {
                dist_txt_agentfname.setBackground(Color.PINK);
            }
            if (dist_txt_agentlname.getText().isEmpty()) {
                dist_txt_agentlname.setBackground(Color.PINK);
            }
            if (dist_txt_agentadd.getText().isEmpty()) {
                dist_txt_agentadd.setBackground(Color.PINK);
            }
            if (dist_txt_agentphone.getText().isEmpty()) {
                dist_txt_agentphone.setBackground(Color.PINK);
            }
            if (dist_txt_agentemail.getText().isEmpty()) {
                dist_txt_agentemail.setBackground(Color.PINK);
            }
            JOptionPane.showMessageDialog(rootPane, "There is one or more empty fields");

        } else {
            if (!emailVal.validate(dist_txt_agentemail.getText().trim())) {
                JOptionPane.showMessageDialog(rootPane, "Not an valied Email address");
            } else {
                int i = dist_txt_agentphone.getText().length();
                if (i > 10 || i < 10) {
                    JOptionPane.showMessageDialog(rootPane, "There should be 10 only characters in a phone NO");
                } else {
                    int agID = Integer.parseInt(dist_lbl_agentidshow.getText());
                    String agfname = dist_txt_agentfname.getText();
                    String aglname = dist_txt_agentlname.getText();
                    String agaddress = dist_txt_agentadd.getText();
                    String agphone = dist_txt_agentphone.getText();
                    String agemail = dist_txt_agentemail.getText();

                    agent ua = new agent(agID, agfname, aglname, agaddress, agphone, agemail);

                    DBO dao = new DBO();
                    dao.updateagent(ua);

                    dist_lbl_agentidshow.setText("");
                    dist_txt_agentfname.setText("");
                    dist_txt_agentlname.setText("");
                    dist_txt_agentadd.setText("");
                    dist_txt_agentphone.setText("");
                    dist_txt_agentemail.setText("");

                    loadagentTable();

                    JOptionPane.showMessageDialog(rootPane, "Update Successful");
                }
            }
        }
    }//GEN-LAST:event_dist_btn_agentupdateActionPerformed

    private void dist_btn_agentclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_agentclearActionPerformed
        // TODO add your handling code here:

        dist_lbl_agentidshow.setText("");
        dist_txt_agentfname.setText("");
        dist_txt_agentlname.setText("");
        dist_txt_agentadd.setText("");
        dist_txt_agentphone.setText("");
        dist_txt_agentemail.setText("");
        dist_txt_agentsearch.setText("Search");
        loadagID();
    }//GEN-LAST:event_dist_btn_agentclearActionPerformed

    private void dist_btn_agentdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_agentdeleteActionPerformed
        // TODO add your handling code here:

        int agID = Integer.parseInt(dist_lbl_agentidshow.getText());
        String agfname = dist_txt_agentfname.getText();
        String aglname = dist_txt_agentlname.getText();
        String agaddress = dist_txt_agentadd.getText();
        String agphone = dist_txt_agentphone.getText();
        String agemail = dist_txt_agentemail.getText();

        agent da = new agent(agID, agfname, aglname, agaddress, agphone, agemail);

        DBO dao = new DBO();
        dao.deleteagent(da);

        dist_lbl_agentidshow.setText("");
        dist_txt_agentfname.setText("");
        dist_txt_agentlname.setText("");
        dist_txt_agentadd.setText("");
        dist_txt_agentphone.setText("");
        dist_txt_agentemail.setText("");

        loadagentTable();

        JOptionPane.showMessageDialog(rootPane, "Record has been deleted");
    }//GEN-LAST:event_dist_btn_agentdeleteActionPerformed

    private void dist_btn_vehicleaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_vehicleaddActionPerformed
        // TODO add your handling code here:
        java.sql.Date o = null;
        try {
            o = new java.sql.Date(dist_dte_vservicedate.getDate().getTime());
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(rootPane, "Enter a Service date");
        }

        if (dist_txt_vehicleNO.getText().isEmpty() || dist_txt_vehicletype.getText().isEmpty() || dist_txt_vehiclemodel.getText().isEmpty() || dist_cmb_vavailability.getSelectedItem().equals("Select a Status")) {

            if (dist_dte_returndate.getDate() == null) {
                JOptionPane.showMessageDialog(rootPane, "Please select a Date");
            }

            if (dist_txt_vehicleNO.getText().isEmpty()) {
                dist_txt_vehicleNO.setBackground(Color.PINK);
            }
            if (dist_txt_vehicletype.getText().isEmpty()) {
                dist_txt_vehicletype.setBackground(Color.PINK);
            }
            if (dist_txt_vehiclemodel.getText().isEmpty()) {
                dist_txt_vehiclemodel.setBackground(Color.PINK);
            }
            if (dist_cmb_vavailability.getSelectedItem().equals("Select a Status")) {
                dist_cmb_vavailability.setBackground(Color.PINK);
            }
            JOptionPane.showMessageDialog(rootPane, "There is one or more empty fields");

        } else {

            if (vehicletype || vehicleavailability) {
                JOptionPane.showMessageDialog(rootPane, "One or more invalid fields");
            }
            SimpleDateFormat dte = new SimpleDateFormat("YYY-MM-dd", Locale.getDefault());

            String vNO = dist_txt_vehicleNO.getText();
            String vType = dist_txt_vehicletype.getText();
            String vModel = dist_txt_vehiclemodel.getText();
            String vServicedate = dte.format(dist_dte_vservicedate.getDate());
            String vavailability = dist_cmb_vavailability.getSelectedItem().toString();

            vehicle av = new vehicle(vNO, vType, vModel, vServicedate, vavailability);

            DBO dbo = new DBO();
            dbo.addvehicle(av);

            dist_txt_vehicleNO.setText("");
            dist_txt_vehicletype.setText("");
            dist_txt_vehiclemodel.setText("");
            dist_dte_vservicedate.setCalendar(null);
            dist_cmb_vavailability.setSelectedItem("Select a Status");
            dist_lbl_vehicleimageshow.setIcon(null);

            loadvehicleTable();

            JOptionPane.showMessageDialog(rootPane, "New record added");
        }
    }//GEN-LAST:event_dist_btn_vehicleaddActionPerformed

    private void vehicle_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vehicle_tableMouseClicked
        // TODO add your handling code here:

        SimpleDateFormat dte = new SimpleDateFormat("YYY-MM-dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();

        try {
            int r = vehicle_table.getSelectedRow();
            String vNO = vehicle_table.getValueAt(r, 0).toString();
            byte[] imagebytes;
            Image i;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vidulaka", "root", "12345");
            PreparedStatement ps = conn.prepareStatement("select * from vehicle_image where vehicleID='" + vNO + "'");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                imagebytes = rs.getBytes("image");

                ImageIcon icon = new ImageIcon(imagebytes);
                dist_lbl_vehicleimageshow.setIcon(icon);
            }

            String vType = vehicle_table.getValueAt(r, 1).toString();
            String vModel = vehicle_table.getValueAt(r, 2).toString();
            String date = vehicle_table.getValueAt(r, 3).toString();
            String vavailability = vehicle_table.getValueAt(r, 4).toString();
            String vimage = vehicle_table.getValueAt(r, 5).toString();

            try {
                cal.setTime(dte.parse(date));
            } catch (ParseException ex) {
                System.out.println(ex);
            }

            dist_txt_vehicleNO.setText(vNO);
            dist_txt_vehicletype.setText(vType);
            dist_txt_vehiclemodel.setText(vModel);
            dist_dte_vservicedate.setCalendar(cal);
            dist_cmb_vavailability.setSelectedItem(vavailability);
            dist_lbl_vehicleimageshow.setText(vimage);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_vehicle_tableMouseClicked

    private void dist_btn_vehicleclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_vehicleclearActionPerformed
        // TODO add your handling code here:
        dist_txt_vehicleNO.setText("");
        dist_txt_vehicletype.setText("");
        dist_txt_vehiclemodel.setText("");
        dist_cmb_vavailability.setSelectedItem("Select a Status");
        dist_txt_vehiclesearch.setText("Search");
        dist_lbl_vehicleimageshow.setText("");
        dist_dte_vservicedate.setCalendar(null);
        dist_lbl_vehicleimageshow.setIcon(null);
    }//GEN-LAST:event_dist_btn_vehicleclearActionPerformed

    private void dist_btn_vehicleupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_vehicleupdateActionPerformed
        // TODO add your handling code here:

        SimpleDateFormat dte = new SimpleDateFormat("YYY-MM-dd", Locale.getDefault());

        String vNO = dist_txt_vehicleNO.getText();
        String vType = dist_txt_vehicletype.getText();
        String vModel = dist_txt_vehiclemodel.getText();
        String vServicedate = dte.format(dist_dte_vservicedate.getDate());
        String vavailability = dist_cmb_vavailability.getSelectedItem().toString();

        vehicle uv = new vehicle(vNO, vType, vModel, vServicedate, vavailability);

        DBO dbo = new DBO();
        dbo.updatevehicle(uv);

        dist_txt_vehicleNO.setText("");
        dist_txt_vehicletype.setText("");
        dist_txt_vehiclemodel.setText("");
        dist_cmb_vavailability.setSelectedItem("Select a Status");
        dist_dte_vservicedate.setCalendar(null);
        dist_lbl_vehicleimageshow.setIcon(null);

        loadvehicleTable();

        JOptionPane.showMessageDialog(rootPane, "Update Successful");
    }//GEN-LAST:event_dist_btn_vehicleupdateActionPerformed

    private void dist_btn_vehicledeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_vehicledeleteActionPerformed
        // TODO add your handling code here:
        SimpleDateFormat dte = new SimpleDateFormat("YYY-MM-dd", Locale.getDefault());

        String vNO = dist_txt_vehicleNO.getText();
        String vType = dist_txt_vehicletype.getText();
        String vModel = dist_txt_vehiclemodel.getText();
        String vServicedate = dte.format(dist_dte_vservicedate.getDate());
        String vavailability = dist_cmb_vavailability.getSelectedItem().toString();

        vehicle dv = new vehicle(vNO, vType, vModel, vServicedate, vavailability);

        DBO dbo = new DBO();
        dbo.deletevehicle(dv);

        dist_txt_vehicleNO.setText("");
        dist_txt_vehicletype.setText("");
        dist_txt_vehiclemodel.setText("");
        dist_cmb_vavailability.setSelectedItem("Select a Status");
        dist_dte_vservicedate.setCalendar(null);
        dist_lbl_vehicleimageshow.setIcon(null);

        loadvehicleTable();

        JOptionPane.showMessageDialog(rootPane, "Record has been deleted");
    }//GEN-LAST:event_dist_btn_vehicledeleteActionPerformed

    private void dist_txt_agentphoneKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_agentphoneKeyReleased
        // TODO add your handling code here:
        int i = dist_txt_agentphone.getText().length();
        if (i > 10) {
            dist_txt_agentphone.setBackground(Color.PINK);
            agentphone = true;
        } else {
            dist_txt_agentphone.setBackground(Color.WHITE);
            agentphone = false;
        }
    }//GEN-LAST:event_dist_txt_agentphoneKeyReleased

    private void dist_txt_agentphoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_agentphoneKeyTyped
        // TODO add your handling code here:
        Character c = evt.getKeyChar();
        if (!(Character.isDigit(c))) {
            dist_txt_agentphone.setBackground(Color.PINK);
            agentphone = true;
        } else {
            dist_txt_agentphone.setBackground(Color.WHITE);
            agentphone = false;
        }

    }//GEN-LAST:event_dist_txt_agentphoneKeyTyped

    private void dist_txt_agentphoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_agentphoneKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_dist_txt_agentphoneKeyPressed

    private void dist_txt_agentfnameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_agentfnameKeyTyped
        // TODO add your handling code here:
        Character c = evt.getKeyChar();
        if (Character.isDigit(c)) {

            dist_txt_agentfname.setBackground(Color.PINK);
            agentfname = true;
        } else {
            dist_txt_agentfname.setBackground(Color.WHITE);
            agentfname = false;
        }
    }//GEN-LAST:event_dist_txt_agentfnameKeyTyped

    private void dist_txt_agentlnameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_agentlnameKeyTyped
        // TODO add your handling code here:
        Character c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            dist_txt_agentlname.setBackground(Color.PINK);
            agentlname = true;
        } else {
            dist_txt_agentlname.setBackground(Color.WHITE);
            agentlname = false;
        }
    }//GEN-LAST:event_dist_txt_agentlnameKeyTyped

    private void dist_txt_vehicletypeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_vehicletypeKeyTyped
        // TODO add your handling code here:
        Character c = evt.getKeyChar();
        if ((Character.isDigit(c))) {
            dist_txt_vehicletype.setBackground(Color.PINK);
            vehicletype = true;
        } else {
            dist_txt_vehicletype.setBackground(Color.WHITE);
            vehicletype = false;
        }
    }//GEN-LAST:event_dist_txt_vehicletypeKeyTyped

    private void dist_btn_returnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_returnaddActionPerformed
        // TODO add your handling code here:

        if (dist_txt_returndistID.getText().isEmpty() || dist_txt_ramount.getText().isEmpty() || dist_txt_rpaidamount.getText().isEmpty()) {
            if (dist_txt_returndistID.getText().isEmpty()) {
                dist_txt_returndistID.setBackground(Color.PINK);
            }
            if (dist_txt_ramount.getText().isEmpty()) {
                dist_txt_ramount.setBackground(Color.PINK);
            }
            if (dist_txt_rpaidamount.getText().isEmpty()) {
                dist_txt_rpaidamount.setBackground(Color.PINK);
            }
            if (dist_dte_returndate.getDate() == null) {
                JOptionPane.showMessageDialog(rootPane, "Please select a Date");
            }

            JOptionPane.showMessageDialog(rootPane, "There is one or more empty fields");
        } else {
            SimpleDateFormat dte = new SimpleDateFormat("YYY-MM-dd", Locale.getDefault());

            int reID = Integer.parseInt(dist_lbl_returnIDshow.getText());
            int agID = Integer.parseInt(dist_lbl_ragentIDshow.getText());
            String pID = dist_lbl_rproductIDshow.getText();
            int amount = Integer.parseInt(dist_txt_ramount.getText());
            String date = dte.format(dist_dte_returndate.getDate());
            int distID = Integer.parseInt(dist_txt_returndistID.getText());
            double pamount = Double.parseDouble(dist_txt_rpaidamount.getText());

            returns ar = new returns(reID, agID, pID, amount, date, distID, pamount);

            DBO dbo = new DBO();
            dbo.addreturn(ar);

            dist_lbl_returnIDshow.setText("");
            dist_lbl_ragentIDshow.setText("");
            dist_lbl_rproductIDshow.setText("");
            dist_lbl_returnptypeshow.setText("");
            dist_txt_ramount.setText("");
            dist_dte_returndate.setCalendar(null);
            dist_txt_returndistID.setText("");
            dist_txt_rpaidamount.setText("");

            loadreturnTable();
            loadreturnID();

            JOptionPane.showMessageDialog(rootPane, "New record added");
        }

    }//GEN-LAST:event_dist_btn_returnaddActionPerformed

    private void dist_txt_agentsearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_agentsearchKeyTyped
        // TODO add your handling code here:
        String key = dist_txt_agentsearch.getText();

        String sql = "SELECT * FROM agent WHERE agFname LIKE '%" + key + "%'";

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            agent_table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
        }
    }//GEN-LAST:event_dist_txt_agentsearchKeyTyped

    private void dist_txt_agentsearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_agentsearchKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_dist_txt_agentsearchKeyPressed

    private void dist_txt_agentemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_txt_agentemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dist_txt_agentemailActionPerformed

    private void dist_txt_vehiclesearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_vehiclesearchMouseClicked
        // TODO add your handling code here:
        dist_txt_vehiclesearch.setText("");
    }//GEN-LAST:event_dist_txt_vehiclesearchMouseClicked

    private void dist_txt_agentsearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_agentsearchMouseClicked
        // TODO add your handling code here:
        dist_txt_agentsearch.setText("");
    }//GEN-LAST:event_dist_txt_agentsearchMouseClicked

    private void dist_txt_vehiclesearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_vehiclesearchKeyTyped
        // TODO add your handling code here:
        String key = dist_txt_vehiclesearch.getText();

        String sql = "SELECT * FROM vehicle WHERE vType LIKE '%" + key + "%'";

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            vehicle_table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
        }
    }//GEN-LAST:event_dist_txt_vehiclesearchKeyTyped

    private void dist_btn_agentshopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_agentshopActionPerformed
        // TODO add your handling code here:
        int key = Integer.parseInt(dist_lbl_agentidshow.getText());
        String agID = dist_lbl_agentidshow.getText();

        String sql = "SELECT * FROM shop WHERE agentID LIKE '%" + key + "%'";

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            shop_table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
        }

        dist_txt_shopagID.setText(agID);

    }//GEN-LAST:event_dist_btn_agentshopActionPerformed

    private void dist_txt_shopsearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_shopsearchMouseClicked
        // TODO add your handling code here:
        dist_txt_shopsearch.setText("");
    }//GEN-LAST:event_dist_txt_shopsearchMouseClicked

    private void dist_txt_shopsearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_shopsearchKeyTyped
        // TODO add your handling code here:
        String key = dist_txt_shopsearch.getText();

        String sql = "SELECT * FROM shop WHERE shopName LIKE '%" + key + "%'";

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            shop_table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
        }
    }//GEN-LAST:event_dist_txt_shopsearchKeyTyped

    private void shop_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_shop_tableMouseClicked
        // TODO add your handling code here:
        int r = shop_table.getSelectedRow();

        String shopID = shop_table.getValueAt(r, 0).toString();
        String shname = shop_table.getValueAt(r, 1).toString();
        String shadd = shop_table.getValueAt(r, 2).toString();
        String shphone = shop_table.getValueAt(r, 3).toString();
        String shagID = shop_table.getValueAt(r, 4).toString();

        dist_lbl_shopIDshow.setText(shopID);
        dist_txt_shopname.setText(shname);
        dist_txt_shopaddress.setText(shadd);
        dist_txt_shopphone.setText(shphone);
        dist_txt_shopagID.setText(shagID);

    }//GEN-LAST:event_shop_tableMouseClicked

    private void dist_btn_shopclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_shopclearActionPerformed
        // TODO add your handling code here:
        dist_lbl_shopIDshow.setText("");
        dist_txt_shopname.setText("");
        dist_txt_shopaddress.setText("");
        dist_txt_shopphone.setText("");
        dist_txt_shopagID.setText("");
        dist_txt_shopsearch.setText("Search");
        loadshopID();
    }//GEN-LAST:event_dist_btn_shopclearActionPerformed

    private void dist_btn_shopaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_shopaddActionPerformed
        // TODO add your handling code here:
        if (dist_txt_shopname.getText().isEmpty() || dist_txt_shopaddress.getText().isEmpty() || dist_txt_shopphone.getText().isEmpty() || dist_txt_shopagID.getText().isEmpty()) {
            if (dist_txt_shopname.getText().isEmpty()) {
                dist_txt_shopname.setBackground(Color.PINK);
            }
            if (dist_txt_shopaddress.getText().isEmpty()) {
                dist_txt_shopaddress.setBackground(Color.PINK);
            }
            if (dist_txt_shopphone.getText().isEmpty()) {
                dist_txt_shopphone.setBackground(Color.PINK);
            }
            if (dist_txt_shopagID.getText().isEmpty()) {
                dist_txt_shopagID.setBackground(Color.PINK);
            }
            JOptionPane.showMessageDialog(rootPane, "There is one or more empty fields");

        } else {
            if (shopagID || shopname || shopphone) {
                JOptionPane.showMessageDialog(rootPane, "One or more invalid fields");

            } else {
                String shname = dist_txt_shopname.getText();
                String shaddress = dist_txt_shopaddress.getText();
                String shphone = dist_txt_shopphone.getText();
                int shagID = Integer.parseInt(dist_txt_shopagID.getText());

                shop as = new shop(shname, shaddress, shphone, shagID);

                DBO dao = new DBO();
                dao.addshop(as);

                dist_lbl_shopIDshow.setText("");
                dist_txt_shopname.setText("");
                dist_txt_shopaddress.setText("");
                dist_txt_shopphone.setText("");
                dist_txt_shopagID.setText("");

                loadshopTable();
                loadshopID();

                JOptionPane.showMessageDialog(rootPane, "New record added");
            }
        }
    }//GEN-LAST:event_dist_btn_shopaddActionPerformed

    private void dist_btn_shopupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_shopupdateActionPerformed
        // TODO add your handling code here:

        int shID = Integer.parseInt(dist_lbl_shopIDshow.getText());
        String shname = dist_txt_shopname.getText();
        String shaddress = dist_txt_shopaddress.getText();
        String shphone = dist_txt_shopphone.getText();
        int shagID = Integer.parseInt(dist_txt_shopagID.getText());

        shop us = new shop(shID, shname, shaddress, shphone, shagID);

        DBO dao = new DBO();
        dao.updateshop(us);

        dist_lbl_shopIDshow.setText("");
        dist_txt_shopname.setText("");
        dist_txt_shopaddress.setText("");
        dist_txt_shopphone.setText("");
        dist_txt_shopagID.setText("");

        loadshopTable();

        JOptionPane.showMessageDialog(rootPane, "Update Sucsessful");
    }//GEN-LAST:event_dist_btn_shopupdateActionPerformed

    private void dist_btn_vehicleimageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_vehicleimageActionPerformed
        // TODO add your handling code here:

        String key = dist_txt_vehicleNO.getText();

        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(rootPane);
        File f = fc.getSelectedFile();
        String path = f.getAbsolutePath();

        dist_lbl_vehicleimageshow.setIcon(new ImageIcon(path));
        try {
            FileInputStream fin = new FileInputStream(f);
            int len = (int) f.length();
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vidulaka?zeroDateTimeBehavior=convertToNull", "root", "12345");
            PreparedStatement ps = conn.prepareStatement("INSERT INTO vehicle_image(image,vehicleID) VALUES(?,'" + key + "')");
            ps.setBinaryStream(1, fin, len);
            int status = ps.executeUpdate();
            if (status > 0) {
                dist_lbl_vehicleerrormessage.setText("Success");
            } else {
                dist_lbl_vehicleerrormessage.setText("Not");
            }

        } catch (Exception e) {
        }

        loadvehicleTable();

    }//GEN-LAST:event_dist_btn_vehicleimageActionPerformed

    private void dist_btn_shopdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_shopdeleteActionPerformed
        // TODO add your handling code here:
        int shID = Integer.parseInt(dist_lbl_shopIDshow.getText());
        String shname = dist_txt_shopname.getText();
        String shaddress = dist_txt_shopaddress.getText();
        String shphone = dist_txt_shopphone.getText();
        int shagID = Integer.parseInt(dist_txt_shopagID.getText());

        shop ds = new shop(shID, shname, shaddress, shphone, shagID);

        DBO dao = new DBO();
        dao.deleteshop(ds);

        dist_lbl_shopIDshow.setText("");
        dist_txt_shopname.setText("");
        dist_txt_shopaddress.setText("");
        dist_txt_shopphone.setText("");
        dist_txt_shopagID.setText("");

        loadshopTable();

        JOptionPane.showMessageDialog(rootPane, "Record has been deleted");
    }//GEN-LAST:event_dist_btn_shopdeleteActionPerformed

    private void dist_txt_shopphoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_shopphoneKeyTyped
        // TODO add your handling code here:
        Character c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE || (c == KeyEvent.VK_DELETE)))) {
            dist_txt_shopphone.setBackground(Color.PINK);
            shopphone = true;
        } else {
            dist_txt_shopphone.setBackground(Color.WHITE);
            shopphone = false;

        }
    }//GEN-LAST:event_dist_txt_shopphoneKeyTyped

    private void dist_txt_shopphoneKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_shopphoneKeyReleased
        // TODO add your handling code here:
        int i = dist_txt_agentphone.getText().length();
        if (i > 10) {
            JOptionPane.showMessageDialog(rootPane, "There should be 10 only characters");
        }
    }//GEN-LAST:event_dist_txt_shopphoneKeyReleased

    private void dist_txt_agentaddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_agentaddMouseClicked
        // TODO add your handling code here:
        dist_txt_agentadd.setBackground(Color.WHITE);
    }//GEN-LAST:event_dist_txt_agentaddMouseClicked

    private void dist_txt_agentemailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_agentemailMouseClicked
        // TODO add your handling code here:
        dist_txt_agentemail.setBackground(Color.WHITE);
    }//GEN-LAST:event_dist_txt_agentemailMouseClicked

    private void dist_txt_agentphoneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_agentphoneMouseClicked
        // TODO add your handling code here:
        dist_txt_agentphone.setBackground(Color.WHITE);
    }//GEN-LAST:event_dist_txt_agentphoneMouseClicked

    private void dist_txt_agentlnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_agentlnameMouseClicked
        // TODO add your handling code here:
        dist_txt_agentlname.setBackground(Color.WHITE);
    }//GEN-LAST:event_dist_txt_agentlnameMouseClicked

    private void dist_txt_agentfnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_agentfnameMouseClicked
        // TODO add your handling code here:
        dist_txt_agentfname.setBackground(Color.WHITE);
    }//GEN-LAST:event_dist_txt_agentfnameMouseClicked

    private void dist_txt_vehicletypeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_vehicletypeMouseClicked
        // TODO add your handling code here:
        dist_txt_vehicletype.setBackground(Color.WHITE);

    }//GEN-LAST:event_dist_txt_vehicletypeMouseClicked

    private void dist_txt_vehicleNOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_vehicleNOMouseClicked
        // TODO add your handling code here:
        dist_txt_vehicleNO.setBackground(Color.WHITE);

    }//GEN-LAST:event_dist_txt_vehicleNOMouseClicked

    private void dist_txt_vehiclemodelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_vehiclemodelMouseClicked
        // TODO add your handling code here:
        dist_txt_vehiclemodel.setBackground(Color.WHITE);

    }//GEN-LAST:event_dist_txt_vehiclemodelMouseClicked

    private void dist_txt_vehicleNOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_txt_vehicleNOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dist_txt_vehicleNOActionPerformed

    private void dist_txt_shopagIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_shopagIDKeyTyped
        // TODO add your handling code here:
        Character c = evt.getKeyChar();
        if (!(Character.isDigit(c))) {
            dist_txt_shopagID.setBackground(Color.PINK);
            shopagID = true;
        } else {
            dist_txt_shopagID.setBackground(Color.WHITE);
            shopagID = false;
        }
    }//GEN-LAST:event_dist_txt_shopagIDKeyTyped

    private void dist_txt_shopnameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_shopnameKeyTyped
        // TODO add your handling code here:
        Character c = evt.getKeyChar();
        if (Character.isDigit(c)) {

            dist_txt_shopname.setBackground(Color.PINK);
            shopname = true;
        } else {
            dist_txt_shopname.setBackground(Color.WHITE);
            shopname = false;
        }
    }//GEN-LAST:event_dist_txt_shopnameKeyTyped

    private void dist_txt_shopagIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_shopagIDMouseClicked
        // TODO add your handling code here:
        dist_txt_shopagID.setBackground(Color.WHITE);

    }//GEN-LAST:event_dist_txt_shopagIDMouseClicked

    private void dist_txt_shopnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_shopnameMouseClicked
        // TODO add your handling code here:
        dist_txt_shopname.setBackground(Color.WHITE);

    }//GEN-LAST:event_dist_txt_shopnameMouseClicked

    private void dist_txt_shopaddressMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_shopaddressMouseClicked
        // TODO add your handling code here:
        dist_txt_shopaddress.setBackground(Color.WHITE);

    }//GEN-LAST:event_dist_txt_shopaddressMouseClicked

    private void dist_txt_shopphoneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_shopphoneMouseClicked
        // TODO add your handling code here:
        dist_txt_shopphone.setBackground(Color.WHITE);

    }//GEN-LAST:event_dist_txt_shopphoneMouseClicked

    private void dist_txt_vehicleNOKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_vehicleNOKeyTyped
        // TODO add your handling code here:
        int i = dist_txt_vehicleNO.getText().length();
        if (i > 6) {
            dist_txt_vehicleNO.setBackground(Color.PINK);
            vehicleNO = true;
        } else {
            dist_txt_vehicleNO.setBackground(Color.WHITE);
            vehicleNO = false;
        }
    }//GEN-LAST:event_dist_txt_vehicleNOKeyTyped

    private void completeorder_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_completeorder_tableMouseClicked
        // TODO add your handling code here:

        int r = completeorder_table.getSelectedRow();

        String orderID = completeorder_table.getValueAt(r, 0).toString();

        dist_lbl_distorderIDshow.setText(orderID);

        loaddistReseiver();
    }//GEN-LAST:event_completeorder_tableMouseClicked

    private void dist_txt_distsearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_distsearchMouseClicked
        // TODO add your handling code here:

        dist_txt_distsearch.setText("");
    }//GEN-LAST:event_dist_txt_distsearchMouseClicked

    private void dist_txt_distsearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dist_txt_distsearchFocusLost
        // TODO add your handling code here:

        dist_txt_distsearch.setText("Search");
    }//GEN-LAST:event_dist_txt_distsearchFocusLost

    private void dist_txt_agentsearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dist_txt_agentsearchFocusLost
        // TODO add your handling code here:

        dist_txt_agentsearch.setText("Search");
    }//GEN-LAST:event_dist_txt_agentsearchFocusLost

    private void dist_txt_shopsearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dist_txt_shopsearchFocusLost
        // TODO add your handling code here:

        dist_txt_shopsearch.setText("Search");
    }//GEN-LAST:event_dist_txt_shopsearchFocusLost

    private void dist_txt_vehiclesearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dist_txt_vehiclesearchFocusLost
        // TODO add your handling code here:

        dist_txt_vehiclesearch.setText("Search");
    }//GEN-LAST:event_dist_txt_vehiclesearchFocusLost

    private void dist_txt_distsearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_distsearchKeyTyped
        // TODO add your handling code here:

        String key = dist_txt_distsearch.getText();

        String sql = "SELECT * FROM distribution WHERE distributionID LIKE '%" + key + "%'";

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            agent_table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
        }
    }//GEN-LAST:event_dist_txt_distsearchKeyTyped

    private void dist_btn_distaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_distaddActionPerformed
        // TODO add your handling code here:

        if (dist_cmb_distdriverlist.getSelectedItem().equals("Select a Driver") || dist_cmb_distreplist.getSelectedItem().equals("Select a Repersentative") || dist_cmb_distvehiclelist.getSelectedItem().equals("Select a Vehicle")) {
            if (dist_cmb_distdriverlist.getSelectedItem().equals("Select a Driver")) {
                JOptionPane.showMessageDialog(rootPane, "Please select a Driver");
            }
            if (dist_cmb_distreplist.getSelectedItem().equals("Select a Repersentative")) {
                JOptionPane.showMessageDialog(rootPane, "Please select a Repersentative");
            }
            if (dist_cmb_distvehiclelist.getSelectedItem().equals("Select a Vehicle")) {
                JOptionPane.showMessageDialog(rootPane, "Please select a Vehicle");
            }
            JOptionPane.showMessageDialog(rootPane, "There is one or more empty fields");
        } else {
            int orderid = Integer.parseInt(dist_lbl_distorderIDshow.getText());
            int driverid = getdriverId();
            int repid = getrepId();
            String vNO = dist_cmb_distvehiclelist.getSelectedItem().toString();
            int receiver = getreceiverid();

            String status = null;
            if (dist_rdb_distpending.isSelected()) {
                status = dist_rdb_distpending.getText();
            } else if (dist_rdb_distcomplete.isSelected()) {
                status = dist_rdb_distcomplete.getText();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please Select a Status");
            }

            distribution ad = new distribution(driverid, repid, vNO, receiver, orderid, status);

            DBO dao = new DBO();
            dao.adddistribution(ad);

            dist_lbl_distorderIDshow.setText("");
            dist_cmb_distdriverlist.setSelectedItem("Select a Driver");
            dist_cmb_distreplist.setSelectedItem("Select a Repersentative");
            dist_cmb_distvehiclelist.setSelectedItem("Select a Vehicle");
            dist_lbl_distreceivershow1.setText("");
            dist_lbl_distreceivershow2.setText("");
            clearbuttons();

            loaddistTable();
            loaddistPending();
            loaddisID();

            JOptionPane.showMessageDialog(rootPane, "New delivery is assigned");
        }

    }//GEN-LAST:event_dist_btn_distaddActionPerformed

    private void dist_txt_rpaidamountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_txt_rpaidamountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dist_txt_rpaidamountActionPerformed

    private void dist_txt_rsearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_rsearchMouseClicked
        // TODO add your handling code here:

        dist_txt_rsearch.setText("");
    }//GEN-LAST:event_dist_txt_rsearchMouseClicked

    private void dist_txt_rsearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dist_txt_rsearchFocusLost
        // TODO add your handling code here:

        dist_txt_rsearch.setText("Search");
    }//GEN-LAST:event_dist_txt_rsearchFocusLost

    private void dist_btn_returndetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_returndetailsActionPerformed
        // TODO add your handling code here:

        loadreturnagID();
        loadreturnPID();

    }//GEN-LAST:event_dist_btn_returndetailsActionPerformed

    private void dist_btn_agentfullreportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_agentfullreportActionPerformed
        // TODO add your handling code here:
        ReportView r = new ReportView("E:\\7\\sumudu\\vidulaka\\src\\Distribution\\all_agents.jasper");
        r.setVisible(true);

        r.setVisible(true);
        r.toFront();
        r.repaint();
        r.setAlwaysOnTop(true);
    }//GEN-LAST:event_dist_btn_agentfullreportActionPerformed

    private void dist_btn_distupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_distupdateActionPerformed
        // TODO add your handling code here:

        if (dist_cmb_distdriverlist.getSelectedItem().equals("Select a Driver") || dist_cmb_distreplist.getSelectedItem().equals("Select a Repersentative") || dist_cmb_distvehiclelist.getSelectedItem().equals("Select a Vehicle")) {
            if (dist_cmb_distdriverlist.getSelectedItem().equals("Select a Driver")) {
                JOptionPane.showMessageDialog(rootPane, "Please select a Driver");
            }
            if (dist_cmb_distreplist.getSelectedItem().equals("Select a Repersentative")) {
                JOptionPane.showMessageDialog(rootPane, "Please select a Repersentative");
            }
            if (dist_cmb_distvehiclelist.getSelectedItem().equals("Select a Vehicle")) {
                JOptionPane.showMessageDialog(rootPane, "Please select a Vehicle");
            }
            JOptionPane.showMessageDialog(rootPane, "There is one or more empty fields");
        } else {

            int distid = Integer.parseInt(dist_lbl_disIDshow.getText());
            int orderid = Integer.parseInt(dist_lbl_distorderIDshow.getText());
            int driverid = getdriverId();
            int repid = getrepId();
            String vNO = dist_cmb_distvehiclelist.getSelectedItem().toString();
            int receiver = getreceiverid();

            String status = null;
            if (dist_rdb_distpending.isSelected()) {
                status = dist_rdb_distpending.getText();
            } else if (dist_rdb_distcomplete.isSelected()) {
                status = dist_rdb_distcomplete.getText();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please Select a Status");
            }

            distribution ud = new distribution(distid, driverid, repid, vNO, receiver, orderid, status);

            DBO dao = new DBO();
            dao.updatedistribution(ud);

            dist_lbl_distorderIDshow.setText("");
            dist_cmb_distdriverlist.setSelectedItem("Select a Driver");
            dist_cmb_distreplist.setSelectedItem("Select a Repersentative");
            dist_cmb_distvehiclelist.setSelectedItem("Select a Vehicle");
            dist_lbl_distreceivershow1.setText("");
            dist_lbl_distreceivershow2.setText("");
            clearbuttons();

            loaddistTable();
            loaddistPending();

            JOptionPane.showMessageDialog(rootPane, "Delivery has been completed");
        }

    }//GEN-LAST:event_dist_btn_distupdateActionPerformed

    private void distributions_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_distributions_tableMouseClicked
        // TODO add your handling code here:

        int r = distributions_table.getSelectedRow();

        String distID = distributions_table.getValueAt(r, 0).toString();
        String driverID = distributions_table.getValueAt(r, 1).toString();
        String repID = distributions_table.getValueAt(r, 2).toString();
        String vNO = distributions_table.getValueAt(r, 3).toString();
        int receiver = Integer.parseInt(distributions_table.getValueAt(r, 4).toString());
        String orderID = distributions_table.getValueAt(r, 5).toString();
        String status = distributions_table.getValueAt(r, 6).toString();

        dist_lbl_disIDshow.setText(distID);
        setdrivername(driverID);
        setrepname(repID);
        dist_cmb_distvehiclelist.setSelectedItem(vNO);
        loaddistReseiver();
        dist_lbl_distorderIDshow.setText(orderID);
        setstatus(status);
    }//GEN-LAST:event_distributions_tableMouseClicked

    private void dist_btn_distdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_distdeleteActionPerformed
        // TODO add your handling code here:

        int distid = Integer.parseInt(dist_lbl_disIDshow.getText());
        int orderid = Integer.parseInt(dist_lbl_distorderIDshow.getText());
        int driverid = getdriverId();
        int repid = getrepId();
        String vNO = dist_cmb_distvehiclelist.getSelectedItem().toString();
        int receiver = getreceiverid();

        String status = null;
        if (dist_rdb_distpending.isSelected()) {
            status = dist_rdb_distpending.getText();
        } else if (dist_rdb_distcomplete.isSelected()) {
            status = dist_rdb_distcomplete.getText();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please Select a Status");
        }

        if (status.equals("On Progress")) {

            distribution dd = new distribution(distid, driverid, repid, vNO, receiver, orderid, status);

            DBO dao = new DBO();
            dao.deletedistribution(dd);

            dist_lbl_distorderIDshow.setText("");
            dist_cmb_distdriverlist.setSelectedItem("Select a Driver");
            dist_cmb_distreplist.setSelectedItem("Select a Repersentative");
            dist_cmb_distvehiclelist.setSelectedItem("Select a Vehicle");
            dist_lbl_distreceivershow1.setText("");
            dist_lbl_distreceivershow2.setText("");
            clearbuttons();

            loaddistTable();
            loaddistPending();
            loaddisID();

            JOptionPane.showMessageDialog(rootPane, "Delivery has been canceled");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Sorry the delivery is alrady completed");
        }


    }//GEN-LAST:event_dist_btn_distdeleteActionPerformed

    private void dist_btn_distclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_distclearActionPerformed
        // TODO add your handling code here:

        loaddisID();
        dist_lbl_distorderIDshow.setText("");
        dist_cmb_distdriverlist.setSelectedItem("Select a Driver");
        dist_cmb_distreplist.setSelectedItem("Select a Repersentative");
        dist_cmb_distvehiclelist.setSelectedItem("Select a Vehicle");
        dist_lbl_distreceivershow1.setText("");
        dist_lbl_distreceivershow2.setText("");
        clearbuttons();
    }//GEN-LAST:event_dist_btn_distclearActionPerformed

    private void dist_btn_returnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_returnupdateActionPerformed
        // TODO add your handling code here:

        if (dist_txt_returndistID.getText().isEmpty() || dist_txt_ramount.getText().isEmpty() || dist_txt_rpaidamount.getText().isEmpty()) {
            if (dist_txt_returndistID.getText().isEmpty()) {
                dist_txt_returndistID.setBackground(Color.PINK);
            }
            if (dist_txt_ramount.getText().isEmpty()) {
                dist_txt_ramount.setBackground(Color.PINK);
            }
            if (dist_txt_rpaidamount.getText().isEmpty()) {
                dist_txt_rpaidamount.setBackground(Color.PINK);
            }
            if (dist_dte_returndate.getDate() == null) {
                JOptionPane.showMessageDialog(rootPane, "Please select a Date");
            }

            JOptionPane.showMessageDialog(rootPane, "There is one or more empty fields");
        } else {
            SimpleDateFormat dte = new SimpleDateFormat("YYY-MM-dd", Locale.getDefault());

            int reID = Integer.parseInt(dist_lbl_returnIDshow.getText());
            int agID = Integer.parseInt(dist_lbl_ragentIDshow.getText());
            String pID = dist_lbl_rproductIDshow.getText();
            int amount = Integer.parseInt(dist_txt_ramount.getText());
            String date = dte.format(dist_dte_returndate.getDate());
            int distID = Integer.parseInt(dist_txt_returndistID.getText());
            double pamount = Double.parseDouble(dist_txt_rpaidamount.getText());

            returns ar = new returns(reID, agID, pID, amount, date, distID, pamount);

            DBO dbo = new DBO();
            dbo.updatereturn(ar);

            dist_lbl_returnIDshow.setText("");
            dist_lbl_ragentIDshow.setText("");
            dist_lbl_rproductIDshow.setText("");
            dist_lbl_returnptypeshow.setText("");
            dist_txt_ramount.setText("");
            dist_dte_returndate.setCalendar(null);
            dist_txt_returndistID.setText("");
            dist_txt_rpaidamount.setText("");

            loadreturnTable();

            JOptionPane.showMessageDialog(rootPane, "Updated has been done");
        }
    }//GEN-LAST:event_dist_btn_returnupdateActionPerformed

    private void dist_btn_returndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_returndeleteActionPerformed
        // TODO add your handling code here:

        SimpleDateFormat dte = new SimpleDateFormat("YYY-MM-dd", Locale.getDefault());

        int reID = Integer.parseInt(dist_lbl_returnIDshow.getText());
        int agID = Integer.parseInt(dist_lbl_ragentIDshow.getText());
        String pID = dist_lbl_rproductIDshow.getText();
        int amount = Integer.parseInt(dist_txt_ramount.getText());
        String date = dte.format(dist_dte_returndate.getDate());
        int distID = Integer.parseInt(dist_txt_returndistID.getText());
        double pamount = Double.parseDouble(dist_txt_rpaidamount.getText());

        returns ar = new returns(reID, agID, pID, amount, date, distID, pamount);

        DBO dbo = new DBO();
        dbo.deletereturns(ar);

        dist_lbl_returnIDshow.setText("");
        dist_lbl_ragentIDshow.setText("");
        dist_lbl_rproductIDshow.setText("");
        dist_lbl_returnptypeshow.setText("");
        dist_txt_ramount.setText("");
        dist_dte_returndate.setCalendar(null);
        dist_txt_returndistID.setText("");
        dist_txt_rpaidamount.setText("");

        loadreturnTable();

        JOptionPane.showMessageDialog(rootPane, "Record has been deleted");
    }//GEN-LAST:event_dist_btn_returndeleteActionPerformed

    private void dist_btn_returnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_returnclearActionPerformed
        // TODO add your handling code here:

        dist_lbl_returnIDshow.setText("");
        dist_lbl_ragentIDshow.setText("");
        dist_lbl_rproductIDshow.setText("");
        dist_lbl_returnptypeshow.setText("");
        dist_txt_ramount.setText("");
        dist_dte_returndate.setCalendar(null);
        dist_txt_returndistID.setText("");
        dist_txt_rpaidamount.setText("");

        loadreturnID();
    }//GEN-LAST:event_dist_btn_returnclearActionPerformed

    private void dist_txt_rsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_txt_rsearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dist_txt_rsearchActionPerformed

    private void dist_txt_rsearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_rsearchKeyTyped
        // TODO add your handling code here:
        String key = dist_txt_rsearch.getText();

        String sql = "SELECT * FROM returns WHERE reID LIKE '%" + key + "%'";

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            vehicle_table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
        }
    }//GEN-LAST:event_dist_txt_rsearchKeyTyped

    private void dist_txt_returndistIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_returndistIDMouseClicked
        // TODO add your handling code here:
        dist_txt_returndistID.setText("");
        dist_txt_returndistID.setBackground(Color.WHITE);
    }//GEN-LAST:event_dist_txt_returndistIDMouseClicked

    private void return_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_return_tableMouseClicked
        // TODO add your handling code here:

//        SimpleDateFormat dte = new SimpleDateFormat("YYY-MM-dd", Locale.getDefault());
//        Calendar cal  = Calendar.getInstance();
        int r = return_table.getSelectedRow();

        String reID = return_table.getValueAt(r, 0).toString();
        String agID = return_table.getValueAt(r, 1).toString();
        String pID = return_table.getValueAt(r, 2).toString();
        String amount = return_table.getValueAt(r, 3).toString();
        String date = return_table.getValueAt(r, 4).toString();
        String distID = return_table.getValueAt(r, 5).toString();
        String pamount = return_table.getValueAt(r, 6).toString();

        java.util.Date dte;
        try {
            dte = new SimpleDateFormat("YYYY-MM-dd").parse(date);
            dist_dte_returndate.setDate(dte);
        } catch (ParseException ex) {
        }

//        try {
//            cal.setTime(dte.parse(date));
//        } catch (ParseException ex) {
//            System.out.println(ex);
//        }
        dist_lbl_returnIDshow.setText(reID);
        dist_lbl_ragentIDshow.setText(agID);
        dist_lbl_rproductIDshow.setText(pID);
        dist_txt_ramount.setText(amount);

        dist_txt_returndistID.setText(distID);
        dist_txt_rpaidamount.setText(pamount);

        loadreturnPID();
    }//GEN-LAST:event_return_tableMouseClicked

    private void dist_txt_ramountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_ramountKeyTyped
        // TODO add your handling code here:
        Character c = evt.getKeyChar();
        if (!(Character.isDigit(c))) {
            dist_txt_ramount.setBackground(Color.PINK);
            ramount = true;
        } else {
            dist_txt_ramount.setBackground(Color.WHITE);
            ramount = false;
        }
    }//GEN-LAST:event_dist_txt_ramountKeyTyped

    private void dist_txt_ramountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_ramountMouseClicked
        // TODO add your handling code here:
        dist_txt_ramount.setBackground(Color.WHITE);
    }//GEN-LAST:event_dist_txt_ramountMouseClicked

    private void dist_txt_returndistIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_returndistIDKeyTyped
        // TODO add your handling code here:
        Character c = evt.getKeyChar();
        if (!(Character.isDigit(c))) {
            dist_txt_returndistID.setBackground(Color.PINK);
            rdistid = true;
        } else {
            dist_txt_returndistID.setBackground(Color.WHITE);
            rdistid = false;
        }
    }//GEN-LAST:event_dist_txt_returndistIDKeyTyped

    private void dist_txt_rpaidamountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dist_txt_rpaidamountKeyTyped
        // TODO add your handling code here:
        Character c = evt.getKeyChar();
        if (!(Character.isDigit(c))) {
            dist_txt_rpaidamount.setBackground(Color.PINK);
            rpamount = true;
        } else {
            dist_txt_rpaidamount.setBackground(Color.WHITE);
            rpamount = false;
        }
    }//GEN-LAST:event_dist_txt_rpaidamountKeyTyped

    private void dist_txt_rpaidamountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dist_txt_rpaidamountMouseClicked
        // TODO add your handling code here:
        dist_txt_rpaidamount.setBackground(Color.WHITE);
    }//GEN-LAST:event_dist_txt_rpaidamountMouseClicked

    private void dist_btn_agentsinglereportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_agentsinglereportActionPerformed
        // TODO add your handling code here:

        int id = Integer.parseInt(dist_lbl_agentidshow.getText());

        HashMap para = new HashMap();
        if (id != 0) {
            para.put("agent", id);
            ReportView r = new ReportView("E:\\7\\sumudu\\vidulaka\\src\\Distribution\\agent_single.jasper", para);
            r.setVisible(true);

            r.setVisible(true);
            r.toFront();
            r.repaint();
            r.setAlwaysOnTop(true);
        }
    }//GEN-LAST:event_dist_btn_agentsinglereportActionPerformed

    private void dist_btn_returnallreportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dist_btn_returnallreportActionPerformed
        // TODO add your handling code here:
        ReportView r = new ReportView("E:\\7\\sumudu\\vidulaka\\src\\Distribution\\all_return.jasper");
        r.setVisible(true);

        r.toFront();
        r.repaint();
        r.setAlwaysOnTop(true);

    }//GEN-LAST:event_dist_btn_returnallreportActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int id = Integer.parseInt(dist_lbl_ragentIDshow.getText());

        HashMap para = new HashMap();
        if (id != 0) {
            para.put("agid", id);
            ReportView r = new ReportView("E:\\7\\sumudu\\vidulaka\\src\\Distribution\\return_report.jasper", para);
            r.setVisible(true);

            r.setVisible(true);
            r.toFront();
            r.repaint();
            r.setAlwaysOnTop(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable agent_table;
    private javax.swing.JPanel backPanel;
    private javax.swing.JTable completeorder_table;
    private javax.swing.JPanel dist_agent;
    private javax.swing.JPanel dist_agentreport;
    private javax.swing.JButton dist_btn_agentadd;
    private javax.swing.JButton dist_btn_agentclear;
    private javax.swing.JButton dist_btn_agentdelete;
    private javax.swing.JButton dist_btn_agentfullreport;
    private javax.swing.JButton dist_btn_agents;
    private javax.swing.JButton dist_btn_agentshop;
    private javax.swing.JButton dist_btn_agentsinglereport;
    private javax.swing.JButton dist_btn_agentupdate;
    private javax.swing.JButton dist_btn_distadd;
    private javax.swing.JButton dist_btn_distclear;
    private javax.swing.JButton dist_btn_distdelete;
    private javax.swing.JButton dist_btn_distribution;
    private javax.swing.JButton dist_btn_distupdate;
    private javax.swing.JButton dist_btn_returnadd;
    private javax.swing.JButton dist_btn_returnallreport;
    private javax.swing.JButton dist_btn_returnclear;
    private javax.swing.JButton dist_btn_returndelete;
    private javax.swing.JButton dist_btn_returndetails;
    private javax.swing.JButton dist_btn_returns;
    private javax.swing.JButton dist_btn_returnupdate;
    private javax.swing.JButton dist_btn_shopadd;
    private javax.swing.JButton dist_btn_shopclear;
    private javax.swing.JButton dist_btn_shopdelete;
    private javax.swing.JButton dist_btn_shopupdate;
    private javax.swing.JButton dist_btn_vehicleadd;
    private javax.swing.JButton dist_btn_vehicleclear;
    private javax.swing.JButton dist_btn_vehicledelete;
    private javax.swing.JButton dist_btn_vehicleimage;
    private javax.swing.JButton dist_btn_vehicles;
    private javax.swing.JButton dist_btn_vehicleupdate;
    private javax.swing.JComboBox dist_cmb_distdriverlist;
    private javax.swing.JComboBox dist_cmb_distreplist;
    private javax.swing.JComboBox dist_cmb_distvehiclelist;
    private javax.swing.JComboBox dist_cmb_vavailability;
    private javax.swing.JPanel dist_comorders;
    private javax.swing.JPanel dist_distribution;
    private javax.swing.JPanel dist_distributions;
    private com.toedter.calendar.JDateChooser dist_dte_returndate;
    private com.toedter.calendar.JDateChooser dist_dte_vservicedate;
    private javax.swing.JPanel dist_getreturns;
    private javax.swing.JLabel dist_lbl_address;
    private javax.swing.JLabel dist_lbl_agentbg;
    private javax.swing.JLabel dist_lbl_agentemail;
    private javax.swing.JLabel dist_lbl_agentfname;
    private javax.swing.JLabel dist_lbl_agenthead;
    private javax.swing.JLabel dist_lbl_agentid;
    private javax.swing.JLabel dist_lbl_agentidshow;
    private javax.swing.JLabel dist_lbl_agentlname;
    private javax.swing.JLabel dist_lbl_agentphone;
    private javax.swing.JLabel dist_lbl_agentreport;
    private javax.swing.JLabel dist_lbl_comordershead;
    private javax.swing.JLabel dist_lbl_disID;
    private javax.swing.JLabel dist_lbl_disIDshow;
    private javax.swing.JLabel dist_lbl_distdriver;
    private javax.swing.JLabel dist_lbl_disthead;
    private javax.swing.JLabel dist_lbl_distorderID;
    private javax.swing.JLabel dist_lbl_distorderIDshow;
    private javax.swing.JLabel dist_lbl_distpendinghead;
    private javax.swing.JLabel dist_lbl_distpendingshow;
    private javax.swing.JLabel dist_lbl_distreceiver;
    private javax.swing.JLabel dist_lbl_distreceivershow1;
    private javax.swing.JLabel dist_lbl_distreceivershow2;
    private javax.swing.JLabel dist_lbl_distrep;
    private javax.swing.JLabel dist_lbl_distributionbg;
    private javax.swing.JLabel dist_lbl_distributionhead;
    private javax.swing.JLabel dist_lbl_diststatus;
    private javax.swing.JLabel dist_lbl_distvehicle;
    private javax.swing.JLabel dist_lbl_ragentID;
    private javax.swing.JLabel dist_lbl_ragentIDshow;
    private javax.swing.JLabel dist_lbl_ramount;
    private javax.swing.JLabel dist_lbl_rdate;
    private javax.swing.JLabel dist_lbl_rdistributionID;
    private javax.swing.JLabel dist_lbl_returnIDshow;
    private javax.swing.JLabel dist_lbl_returnbg;
    private javax.swing.JLabel dist_lbl_returngetinfohead;
    private javax.swing.JLabel dist_lbl_returnhead;
    private javax.swing.JLabel dist_lbl_returnptypeshow;
    private javax.swing.JLabel dist_lbl_returnreport;
    private javax.swing.JLabel dist_lbl_returntID;
    private javax.swing.JLabel dist_lbl_rpaidamount;
    private javax.swing.JLabel dist_lbl_rproductID;
    private javax.swing.JLabel dist_lbl_rproductIDshow;
    private javax.swing.JLabel dist_lbl_shopID;
    private javax.swing.JLabel dist_lbl_shopIDshow;
    private javax.swing.JLabel dist_lbl_shopaddress;
    private javax.swing.JLabel dist_lbl_shopagentID;
    private javax.swing.JLabel dist_lbl_shophead;
    private javax.swing.JLabel dist_lbl_shopname;
    private javax.swing.JLabel dist_lbl_shopphone;
    private javax.swing.JLabel dist_lbl_vehicleNO;
    private javax.swing.JLabel dist_lbl_vehicleavailble;
    private javax.swing.JLabel dist_lbl_vehiclebg;
    private javax.swing.JLabel dist_lbl_vehicleerrormessage;
    private javax.swing.JLabel dist_lbl_vehiclehead;
    private javax.swing.JLabel dist_lbl_vehicleimage;
    private javax.swing.JLabel dist_lbl_vehicleimageshow;
    private javax.swing.JLabel dist_lbl_vehiclemodel;
    private javax.swing.JLabel dist_lbl_vehicleservicedate;
    private javax.swing.JLabel dist_lbl_vehicletype;
    private javax.swing.JLabel dist_lbl_welcome;
    private javax.swing.JPanel dist_pending;
    private javax.swing.JRadioButton dist_rdb_distcomplete;
    private javax.swing.JRadioButton dist_rdb_distpending;
    private javax.swing.JPanel dist_return;
    private javax.swing.JPanel dist_returnreport;
    private javax.swing.JPanel dist_shop;
    private javax.swing.JScrollPane dist_table_agent;
    private javax.swing.JScrollPane dist_table_completeorders;
    private javax.swing.JScrollPane dist_table_distributions;
    private javax.swing.JScrollPane dist_table_return;
    private javax.swing.JScrollPane dist_table_shop;
    private javax.swing.JScrollPane dist_table_vehicle;
    private javax.swing.JTextField dist_txt_agentadd;
    private javax.swing.JTextField dist_txt_agentemail;
    private javax.swing.JTextField dist_txt_agentfname;
    private javax.swing.JTextField dist_txt_agentlname;
    private javax.swing.JTextField dist_txt_agentphone;
    private javax.swing.JTextField dist_txt_agentsearch;
    private javax.swing.JTextField dist_txt_distsearch;
    private javax.swing.JTextField dist_txt_ramount;
    private javax.swing.JTextField dist_txt_returndistID;
    private javax.swing.JTextField dist_txt_rpaidamount;
    private javax.swing.JTextField dist_txt_rsearch;
    private javax.swing.JTextField dist_txt_shopaddress;
    private javax.swing.JTextField dist_txt_shopagID;
    private javax.swing.JTextField dist_txt_shopname;
    private javax.swing.JTextField dist_txt_shopphone;
    private javax.swing.JTextField dist_txt_shopsearch;
    private javax.swing.JTextField dist_txt_vehicleNO;
    private javax.swing.JTextField dist_txt_vehiclemodel;
    private javax.swing.JTextField dist_txt_vehiclesearch;
    private javax.swing.JTextField dist_txt_vehicletype;
    private javax.swing.JPanel dist_vehicles;
    private javax.swing.JPanel dist_welcome;
    private javax.swing.JTable distributions_table;
    private javax.swing.JButton jButton1;
    private static javax.swing.JLabel jLabel1;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JTable return_table;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JTable shop_table;
    private javax.swing.JTable vehicle_table;
    // End of variables declaration//GEN-END:variables
}
