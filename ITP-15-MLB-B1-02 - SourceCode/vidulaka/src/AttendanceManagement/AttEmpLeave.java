/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceManagement;

import Home.*;
import javax.swing.JDesktopPane;

import AttendanceManagement.yourinitialframe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import Home.dbconnect;
import com.mysql.jdbc.StringUtils;
//import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Sumudu Malshan
 */
public class AttEmpLeave extends javax.swing.JInternalFrame {

       Connection con= null;
         PreparedStatement pst= null;
         ResultSet rst=null; 
         int emp_id_mouse_clicked= 0;
         
         boolean Empname;
         boolean Empage;
         String lv_session="not relevant";
         int eid=0;
         int rid;
         String stat;
         String post="no manager";
        
         //tableload for employee handling
            public void tableLoad(){
        try{
        String qry= "SELECT e.empID,e.name,e.age,d.deptName,e.nic,e.address,e.userName,e.password FROM employee e ,department d WHERE e.deptID=d.deptID";
                
        
        
        pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
        
        att_edetails.setModel(DbUtils.resultSetToTableModel(rst));
        }
       catch(Exception e){
         JOptionPane.showMessageDialog(this,e);
       
       }
    
    }
            
            
          //table loads for leave managment
                  public void tableLoad1(){
        try{
        String qry= "SELECT reqID,type,fromDate,toDate,session,description,status,reason FROM leave_request WHERE empID = '"+eid+"'";
        pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
        
        table1.setModel(DbUtils.resultSetToTableModel(rst));
        }
       catch(Exception e){
         JOptionPane.showMessageDialog(this,e);
       
       }
    
    }
                   public void tableLoad2(){
        try{
        String qry= "SELECT reqID,type,fromDate,toDate,session,description,status FROM leave_request WHERE empID = '"+eid+"'";
        pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
        
        table2.setModel(DbUtils.resultSetToTableModel(rst));
        }
       catch(Exception e){
         JOptionPane.showMessageDialog(this,e);
       
       }
    
    } 
                public void tableLoad3(){
        try{
        String qry= "SELECT r.reqID,e.name,r.type,r.fromDate,r.toDate,r.session,r.description FROM leave_request r,employee e WHERE r.empID=e.empID and r.status='Pending'" ;
        pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
        
        table3.setModel(DbUtils.resultSetToTableModel(rst));
        }
       catch(Exception e){
         JOptionPane.showMessageDialog(this,e);
       
       }
    
    }

       
         
         
    public AttEmpLeave() {
      initComponents();
           ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
            //connect to the database
        con= dbconnect.connect();
        tableLoad();
        
        att_mtype.setVisible(false);
        att_desig.setVisible(false);
        jButton8.setVisible(false);
        add_other.setVisible(false);
        update_other.setVisible(false);
        delete_other.setVisible(false);
        search_other.setVisible(false);
        att_other_search.setVisible(false);
        error_name.setVisible(false);
        error_add.setVisible(false);
        error_nic.setVisible(false);
        error_age.setVisible(false);
        leave_session1.setVisible(false);
        leave_session2.setVisible(false);
        jLabel26.setVisible(false);jLabel27.setVisible(false);jLabel28.setVisible(false);
        jLabel29.setVisible(false);jLabel30.setVisible(false);jLabel31.setVisible(false);
        jLabel32.setVisible(false);jLabel33.setVisible(false);jLabel34.setVisible(false);
        jLabel35.setVisible(false);jLabel36.setVisible(false);jLabel37.setVisible(false);
        jLabel38.setVisible(false);jLabel39.setVisible(false);
        jLabel41.setVisible(true);
        
        rejected.setVisible(false);
        approved.setVisible(false);
        
        
        
        jLabel22.setVisible(false);jLabel40.setVisible(false);jLabel42.setVisible(false);
        jLabel43.setVisible(false); jLabel44.setVisible(false); jLabel45.setVisible(false);
        jLabel46.setVisible(false); jLabel47.setVisible(false); jLabel48.setVisible(false);
        jLabel49.setVisible(false); jLabel50.setVisible(false); jLabel51.setVisible(false);
        jLabel52.setVisible(false);jLabel53.setVisible(false);  jLabel23.setVisible(false);
        jLabel24.setVisible(false);
        
        jPanel3.setVisible(false);
        
        //jPanel15.setVisible(false);
        
        Leave lv=new Leave();
        LeaveDAO da= new   LeaveDAO();
        
        
        //calculating the no of available leaves
        try{
        
     
        String qry1="SELECT empID FROM employee WHERE username='"+login.uName+"' ";
      
        pst= con.prepareStatement(qry1);
        rst=pst.executeQuery();
        while(rst.next())
         eid= rst.getInt("empID");
     
        
        System.out.println(eid);
       String qry2="SELECT* FROM manager WHERE empID='"+eid+"'";
        pst= con.prepareStatement(qry2);
        rst=pst.executeQuery();
         if(rst.next()){
             
             //JOptionPane.showMessageDialog(this,"dude is a manager "); 
              post=rst.getString("managerType");
             
            
             System.out.println(post);
           lv.setEid(eid);
           int[] leaves1=da.chkAvbLeaves(lv,1);
            for(int no : leaves1){
             System.out.println(no); 
            }
           ml.setText(Integer.toString(leaves1[1]));
           pl.setText(Integer.toString(leaves1[2]));
           cl.setText(Integer.toString(leaves1[0]));
           sl.setText(Integer.toString(leaves1[3]));
           
           if(post.equalsIgnoreCase("HR manager")){
           
               jButton5.setVisible(false);
               jPanel2.setVisible(false);
               jPanel3.setVisible(false);
               jButton1.setVisible(false);
               jButton4.setVisible(true);
               jButton13.setVisible(false);
               jPanel4.setVisible(true);
               jPanel13.setVisible(false);
           
             }
           else if(!(post.equalsIgnoreCase("Manager Admin"))){
             jButton5.setVisible(false);
             jButton4.setVisible(false);
             jButton1.setVisible(false);
                jPanel13.setVisible(true);
                jPanel2.setVisible(false);
                jPanel4.setVisible(false);
                jButton13.setVisible(true);
                jPanel4.setVisible(true);
                
               
               
         }
           
           
           
           
             
         }
         else{
          try{   
              String qry="SELECT* FROM production_office_staff WHERE empID='"+eid+"'";
        pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
         if(rst.next()){
           post=rst.getString("designation");
             System.out.println(post);
         
         } 
             
         } 
          catch(Exception e){
              System.out.println(e);
          
          }
          
            if(post.equalsIgnoreCase("security")){
                System.out.println("is a security");
                jButton5.setVisible(false);
                jButton4.setVisible(false);
                jPanel2.setVisible(false);
                jPanel4.setVisible(false);
                jButton13.setVisible(true);
                jButton1.setVisible(true);
                    jPanel3.setVisible(true);
                     jPanel13.setVisible(true);
            }
            else{
                     jButton5.setVisible(false);
             jButton4.setVisible(false);
             jButton1.setVisible(false);
                jPanel13.setVisible(true);
                jPanel2.setVisible(false);
                jPanel4.setVisible(false);
                jButton13.setVisible(true);
                jPanel4.setVisible(true);
              
            }
               
                lv.setEid(eid);
                int[] leaves2=da.chkAvbLeaves(lv,2);
           ml.setText(Integer.toString(leaves2[1]));
           pl.setText(Integer.toString(leaves2[2]));
           cl.setText(Integer.toString(leaves2[0]));
           sl.setText(Integer.toString(leaves2[3]));
         }
        //tableLoad1();
        tableLoad2();
        tableLoad3(); 
         
      }
        catch(Exception e){
           e.printStackTrace();
        
        }
          //Checking for approved or rejected leaves 
        try{
            int rejcount=0;
            int appcount=0;
        String qry4="SELECT reqID,type,fromDate,toDate,session,description,status,reason FROM leave_request WHERE empID ='"+eid+"' and checkbit= 0 and(status='Rejected' or status = 'Approved')";
        pst= con.prepareStatement(qry4);
        rst=pst.executeQuery();
        table1.setModel(DbUtils.resultSetToTableModel(rst)); 
        String qry4_duplicate="SELECT reqID,type,fromDate,toDate,session,description,status,reason FROM leave_request WHERE empID ='"+eid+"' and checkbit= 0 and(status='Rejected' or status = 'Approved')";
        pst= con.prepareStatement(qry4_duplicate);
        rst=pst.executeQuery();
        
        if(rst.next()){
          
            System.out.println("there are results");
               //table1.setModel(DbUtils.resultSetToTableModel(rst)); 
                String qry5="SELECT count(reqID)as appcount FROM leave_request WHERE empID ='"+eid+"' and checkbit= 0 and status='Approved'";
                String qry6="SELECT count(reqID)as rejcount FROM leave_request WHERE empID ='"+eid+"' and checkbit= 0 and status='Rejected'";
                 pst= con.prepareStatement(qry5);
                  rst=pst.executeQuery();
                 if(rst.next())
                     appcount=rst.getInt("appcount");
                  pst= con.prepareStatement(qry6);
                  rst=pst.executeQuery();
                 if(rst.next())
                     rejcount=rst.getInt("rejcount");
               if(appcount>0){
               approved.setText("You have " +appcount+" approved leave requests");
               approved.setVisible(true);
               }
               if(rejcount>0){
               rejected.setText("You have " +rejcount+" rejected leave requests");
               rejected.setVisible(true);
               }
            
        }
        else{
               tableLoad1();
        }
        }
        catch (Exception e){
            System.out.println(e);
        }
        
        
        String qry3="SELECT count(reqID) as preq FROM leave_request WHERE status='Pending'";
        try{
          pst= con.prepareStatement(qry3);
        rst=pst.executeQuery();
         if(rst.next()){
            int count=rst.getInt("preq");
             jLabel41.setText( count+" Pending Leave Requests");
       
         }
        }
        catch(Exception e){
            System.out.println(e);
        }
        
         try{
           String qry7="   SELECT * FROM leave_request WHERE reqID= (SELECT MAX(reqID)as max FROM leave_request WHERE empID='"+eid+"' and status ='Approved')";
        pst= con.prepareStatement(qry7);
        rst=pst.executeQuery();
         if(rst.next())
                // System.out.println("max is");
                 //System.out.println(rst.getInt("max"));
                 jLabel57.setText(rst.getString("type"));
                 jLabel58.setText(rst.getString("fromDate"));
                 jLabel59.setText(rst.getString("toDate"));
           }
         catch(Exception e){
             System.out.println(e); 
         }
           
            try{
        String qry7="UPDATE leave_request SET checkbit=1 WHERE empID ='"+eid+"' and checkbit= 0 and(status='Rejected' or status = 'Approved')";
        pst= con.prepareStatement(qry7);
            pst.execute();
            } 
            catch(Exception e){
                System.out.println(e);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        backPanel = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        rightPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        error_age = new javax.swing.JLabel();
        error_add = new javax.swing.JLabel();
        error_nic = new javax.swing.JLabel();
        error_name = new javax.swing.JLabel();
        att_name = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        att_edetails = new javax.swing.JTable();
        lable_name = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        att_age = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        att_cat = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        att_dep = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        att_nic = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        att_address = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        att_pwd = new javax.swing.JTextField();
        add_em = new javax.swing.JButton();
        update_em = new javax.swing.JButton();
        delete_em = new javax.swing.JButton();
        clear_em = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        att_otherdetails = new javax.swing.JTable();
        att_mtype = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        att_desig = new javax.swing.JTextField();
        att_id = new javax.swing.JLabel();
        att_usern = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        add_other = new javax.swing.JButton();
        update_other = new javax.swing.JButton();
        delete_other = new javax.swing.JButton();
        att_em_search = new javax.swing.JTextField();
        search_em = new javax.swing.JButton();
        att_other_search = new javax.swing.JTextField();
        title = new javax.swing.JLabel();
        search_other = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        approved = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        rejected = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        table2 = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        ml = new javax.swing.JLabel();
        pl = new javax.swing.JLabel();
        cl = new javax.swing.JLabel();
        sl = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        leave_type = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        leave_desc = new javax.swing.JTextArea();
        leave_from = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        leave_to = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        leave_session1 = new javax.swing.JRadioButton();
        leave_session2 = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        table3 = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        lv_reason = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        emp_id = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jScrollPane9 = new javax.swing.JScrollPane();
        pending_tb = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        approved_tb = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        rejected_tb = new javax.swing.JTable();
        jButton11 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        att_eid_chkinout = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        bck = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
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

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/attendance.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        leftPanel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 100, -1));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/approve Leaves.png"))); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        leftPanel.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 100, -1));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/emp handling.png"))); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        leftPanel.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, -1));

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/rqst Lvs.png"))); // NOI18N
        jButton13.setBorderPainted(false);
        jButton13.setContentAreaFilled(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        leftPanel.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 100, 80));

        rightPanel.setMaximumSize(new java.awt.Dimension(910, 570));
        rightPanel.setMinimumSize(new java.awt.Dimension(910, 570));
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new java.awt.Dimension(910, 570));
        rightPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(915, 570));
        jPanel2.setMinimumSize(new java.awt.Dimension(915, 570));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(915, 570));
        jPanel2.setLayout(null);

        error_age.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        error_age.setForeground(new java.awt.Color(255, 0, 0));
        error_age.setText("This field is empty");
        jPanel2.add(error_age);
        error_age.setBounds(130, 170, 120, 20);

        error_add.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        error_add.setForeground(new java.awt.Color(255, 0, 0));
        error_add.setText("This field is empty");
        jPanel2.add(error_add);
        error_add.setBounds(130, 300, 120, 14);

        error_nic.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        error_nic.setForeground(new java.awt.Color(255, 0, 0));
        error_nic.setText("This field is empty");
        jPanel2.add(error_nic);
        error_nic.setBounds(130, 254, 120, 20);

        error_name.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        error_name.setForeground(new java.awt.Color(255, 0, 0));
        error_name.setText("This field is empty");
        error_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                error_nameKeyReleased(evt);
            }
        });
        jPanel2.add(error_name);
        error_name.setBounds(130, 90, 130, 20);

        att_name.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                att_nameMouseClicked(evt);
            }
        });
        att_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                att_nameActionPerformed(evt);
            }
        });
        att_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                att_nameKeyReleased(evt);
            }
        });
        jPanel2.add(att_name);
        att_name.setBounds(120, 90, 160, 25);

        att_edetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        att_edetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                att_edetailsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(att_edetails);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(290, 90, 610, 360);

        lable_name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lable_name.setForeground(new java.awt.Color(255, 255, 255));
        lable_name.setText("Name");
        jPanel2.add(lable_name);
        lable_name.setBounds(20, 90, 80, 20);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Employee ID");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(20, 130, 80, 17);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Age");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(20, 170, 60, 17);

        att_age.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                att_ageMouseClicked(evt);
            }
        });
        att_age.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                att_ageActionPerformed(evt);
            }
        });
        att_age.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                att_ageKeyReleased(evt);
            }
        });
        jPanel2.add(att_age);
        att_age.setBounds(120, 170, 160, 25);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Category");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(20, 50, 80, 20);

        att_cat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Category", "Manager", "Production & Office Staff", "Distribution Staff", "Folding Staff" }));
        jPanel2.add(att_cat);
        att_cat.setBounds(120, 50, 160, 25);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Department");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(20, 210, 80, 17);

        att_dep.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Department", "Office Staff", "Production Staff", "Distribution Staff", "Folding Staff" }));
        jPanel2.add(att_dep);
        att_dep.setBounds(120, 210, 160, 25);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("NIC");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(20, 250, 30, 20);

        att_nic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                att_nicMouseClicked(evt);
            }
        });
        att_nic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                att_nicKeyReleased(evt);
            }
        });
        jPanel2.add(att_nic);
        att_nic.setBounds(120, 250, 160, 25);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Address");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(20, 290, 80, 20);

        att_address.setColumns(20);
        att_address.setRows(5);
        att_address.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                att_addressMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(att_address);

        jPanel2.add(jScrollPane2);
        jScrollPane2.setBounds(120, 290, 160, 80);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Password");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(20, 430, 70, 17);

        att_pwd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                att_pwdActionPerformed(evt);
            }
        });
        jPanel2.add(att_pwd);
        att_pwd.setBounds(120, 430, 160, 25);

        add_em.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        add_em.setText("Add");
        add_em.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_emActionPerformed(evt);
            }
        });
        jPanel2.add(add_em);
        add_em.setBounds(10, 500, 60, 25);

        update_em.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        update_em.setText("Update");
        update_em.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_emActionPerformed(evt);
            }
        });
        jPanel2.add(update_em);
        update_em.setBounds(80, 500, 80, 25);

        delete_em.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        delete_em.setText("Delete");
        delete_em.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_emActionPerformed(evt);
            }
        });
        jPanel2.add(delete_em);
        delete_em.setBounds(170, 500, 70, 25);

        clear_em.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        clear_em.setText("Clear");
        clear_em.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear_emActionPerformed(evt);
            }
        });
        jPanel2.add(clear_em);
        clear_em.setBounds(250, 500, 70, 25);

        jButton9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton9.setText("Next");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton9);
        jButton9.setBounds(300, 50, 70, 25);

        att_otherdetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        att_otherdetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                att_otherdetailsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(att_otherdetails);

        jPanel2.add(jScrollPane3);
        jScrollPane3.setBounds(290, 90, 610, 360);

        att_mtype.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                att_mtypeMouseClicked(evt);
            }
        });
        jPanel2.add(att_mtype);
        att_mtype.setBounds(120, 90, 160, 25);

        jButton8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton8.setText("Back");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton8);
        jButton8.setBounds(380, 50, 70, 25);

        att_desig.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                att_desigMouseClicked(evt);
            }
        });
        att_desig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                att_desigActionPerformed(evt);
            }
        });
        jPanel2.add(att_desig);
        att_desig.setBounds(120, 90, 160, 25);

        att_id.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        att_id.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(att_id);
        att_id.setBounds(120, 130, 90, 25);
        jPanel2.add(att_usern);
        att_usern.setBounds(120, 390, 160, 25);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("User Name");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(20, 390, 90, 17);

        add_other.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        add_other.setText("Add");
        add_other.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_otherActionPerformed(evt);
            }
        });
        jPanel2.add(add_other);
        add_other.setBounds(10, 500, 60, 25);

        update_other.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        update_other.setText("Update");
        update_other.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_otherActionPerformed(evt);
            }
        });
        jPanel2.add(update_other);
        update_other.setBounds(80, 500, 80, 25);

        delete_other.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        delete_other.setText("Delete");
        delete_other.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_otherActionPerformed(evt);
            }
        });
        jPanel2.add(delete_other);
        delete_other.setBounds(170, 500, 70, 25);

        att_em_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                att_em_searchActionPerformed(evt);
            }
        });
        jPanel2.add(att_em_search);
        att_em_search.setBounds(590, 50, 190, 25);

        search_em.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        search_em.setText("Search");
        search_em.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_emActionPerformed(evt);
            }
        });
        jPanel2.add(search_em);
        search_em.setBounds(810, 50, 90, 25);

        att_other_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                att_other_searchActionPerformed(evt);
            }
        });
        jPanel2.add(att_other_search);
        att_other_search.setBounds(590, 50, 190, 25);

        title.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setText("Employee Details");
        jPanel2.add(title);
        title.setBounds(20, 10, 300, 30);

        search_other.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        search_other.setText("Search");
        search_other.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_otherActionPerformed(evt);
            }
        });
        jPanel2.add(search_other);
        search_other.setBounds(810, 50, 90, 25);

        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        jPanel2.add(jLabel3);
        jLabel3.setBounds(0, 0, 915, 570);

        rightPanel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMaximumSize(new java.awt.Dimension(915, 570));
        jPanel3.setMinimumSize(new java.awt.Dimension(915, 570));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(915, 570));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setLayout(null);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Leave Status", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        jPanel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel5.setLayout(null);

        approved.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        approved.setText("approved");
        jPanel5.add(approved);
        approved.setBounds(20, 40, 344, 27);

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setText("Due to :");
        jPanel5.add(jLabel23);
        jLabel23.setBounds(480, 40, 70, 14);

        jLabel24.setText("jLabel24");
        jPanel5.add(jLabel24);
        jLabel24.setBounds(580, 30, 270, 30);

        rejected.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rejected.setText("rejeced");
        jPanel5.add(rejected);
        rejected.setBounds(20, 90, 330, 25);

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setText("Request ID  :");
        jPanel5.add(jLabel22);
        jLabel22.setBounds(20, 40, 90, 14);

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel40.setText("Leave Type :");
        jPanel5.add(jLabel40);
        jLabel40.setBounds(20, 70, 90, 14);

        jLabel42.setText("From Date   :");
        jPanel5.add(jLabel42);
        jLabel42.setBounds(20, 100, 100, 14);

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel43.setText("To Date       :");
        jPanel5.add(jLabel43);
        jLabel43.setBounds(20, 130, 90, 14);

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel44.setText("Session        :");
        jPanel5.add(jLabel44);
        jLabel44.setBounds(20, 160, 100, 14);

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel45.setText("Description :");
        jPanel5.add(jLabel45);
        jLabel45.setBounds(240, 40, 80, 14);

        jLabel46.setText("jLabel46");
        jPanel5.add(jLabel46);
        jLabel46.setBounds(130, 40, 50, 14);

        jLabel47.setText("jLabel47");
        jPanel5.add(jLabel47);
        jLabel47.setBounds(130, 70, 100, 14);

        jLabel48.setText("jLabel48");
        jPanel5.add(jLabel48);
        jLabel48.setBounds(130, 100, 90, 14);

        jLabel49.setText("jLabel49");
        jPanel5.add(jLabel49);
        jLabel49.setBounds(130, 130, 90, 14);

        jLabel50.setText("jLabel50");
        jPanel5.add(jLabel50);
        jLabel50.setBounds(130, 160, 100, 14);

        jLabel51.setText("jLabel51");
        jPanel5.add(jLabel51);
        jLabel51.setBounds(360, 40, 150, 14);

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel52.setText("Status        :");
        jPanel5.add(jLabel52);
        jLabel52.setBounds(240, 70, 90, 14);

        jLabel53.setText("jLabel53");
        jPanel5.add(jLabel53);
        jLabel53.setBounds(360, 70, 100, 14);

        jPanel7.add(jPanel5);
        jPanel5.setBounds(10, 10, 860, 210);

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(table1);

        jPanel7.add(jScrollPane4);
        jScrollPane4.setBounds(10, 250, 860, 242);

        jTabbedPane2.addTab("Leave Status", jPanel7);

        jPanel9.setLayout(null);

        table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        table2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table2MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(table2);

        jPanel9.add(jScrollPane5);
        jScrollPane5.setBounds(230, 240, 650, 210);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Your Leave Detials", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        jPanel10.setLayout(null);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Leaves available ");
        jPanel10.add(jLabel15);
        jLabel15.setBounds(580, 20, 140, 14);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("Medical leaves        :");
        jPanel10.add(jLabel18);
        jLabel18.setBounds(540, 50, 140, 14);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setText("Personal Leaves     :");
        jPanel10.add(jLabel19);
        jLabel19.setBounds(540, 70, 140, 14);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("Cassual Leaves       :");
        jPanel10.add(jLabel20);
        jLabel20.setBounds(540, 90, 150, 14);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setText("Short Leaves           :");
        jPanel10.add(jLabel21);
        jLabel21.setBounds(540, 110, 150, 14);

        ml.setText("jLabel25");
        jPanel10.add(ml);
        ml.setBounds(700, 50, 40, 14);

        pl.setText("jLabel26");
        jPanel10.add(pl);
        pl.setBounds(700, 70, 40, 14);

        cl.setText("jLabel27");
        jPanel10.add(cl);
        cl.setBounds(700, 90, 40, 14);

        sl.setText("jLabel28");
        jPanel10.add(sl);
        sl.setBounds(700, 110, 40, 14);

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel54.setText("Last leave taken");
        jPanel10.add(jLabel54);
        jLabel54.setBounds(20, 30, 110, 14);

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel55.setText("Leave Type    :");
        jPanel10.add(jLabel55);
        jLabel55.setBounds(20, 60, 100, 14);

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel56.setText("Duration         :");
        jPanel10.add(jLabel56);
        jLabel56.setBounds(20, 90, 80, 14);

        jLabel57.setText("jLabel57");
        jPanel10.add(jLabel57);
        jLabel57.setBounds(160, 60, 140, 14);

        jLabel58.setText("jLabel58");
        jPanel10.add(jLabel58);
        jLabel58.setBounds(130, 90, 90, 14);

        jLabel59.setText("jLabel59");
        jPanel10.add(jLabel59);
        jLabel59.setBounds(240, 90, 100, 14);

        jPanel9.add(jPanel10);
        jPanel10.setBounds(10, 10, 860, 150);

        leave_type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose Leave Type", "Personal Leaves", "Medical Leaves", "Cassual Leaves", "Short Leaves" }));
        leave_type.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                leave_typeItemStateChanged(evt);
            }
        });
        leave_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leave_typeActionPerformed(evt);
            }
        });
        jPanel9.add(leave_type);
        leave_type.setBounds(10, 200, 210, 30);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Leave Application");
        jPanel9.add(jLabel16);
        jLabel16.setBounds(10, 170, 130, 20);

        leave_desc.setColumns(20);
        leave_desc.setRows(5);
        leave_desc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                leave_descMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(leave_desc);

        jPanel9.add(jScrollPane6);
        jScrollPane6.setBounds(10, 270, 210, 70);

        leave_from.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                leave_fromMouseClicked(evt);
            }
        });
        jPanel9.add(leave_from);
        leave_from.setBounds(10, 370, 210, 30);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("From :");
        jPanel9.add(jLabel17);
        jLabel17.setBounds(10, 350, 35, 14);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("To :");
        jPanel9.add(jLabel14);
        jLabel14.setBounds(10, 410, 20, 14);

        leave_to.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                leave_toMouseClicked(evt);
            }
        });
        jPanel9.add(leave_to);
        leave_to.setBounds(10, 430, 210, 30);

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("Submit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton2);
        jButton2.setBounds(10, 470, 210, 25);

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setText("Cancel Leave");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton3);
        jButton3.setBounds(740, 460, 130, 25);

        buttonGroup1.add(leave_session1);
        leave_session1.setText("Morning ");
        leave_session1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leave_session1ActionPerformed(evt);
            }
        });
        jPanel9.add(leave_session1);
        leave_session1.setBounds(10, 240, 110, 23);

        buttonGroup1.add(leave_session2);
        leave_session2.setText("Afternoon ");
        leave_session2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leave_session2ActionPerformed(evt);
            }
        });
        jPanel9.add(leave_session2);
        leave_session2.setBounds(120, 240, 110, 23);

        jTabbedPane2.addTab("Leave Request", jPanel9);

        jPanel3.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 890, 540));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Greenbackground.jpg"))); // NOI18N
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel4.setBackground(new java.awt.Color(102, 255, 102));
        jPanel4.setMaximumSize(new java.awt.Dimension(915, 570));
        jPanel4.setMinimumSize(new java.awt.Dimension(915, 570));
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(915, 570));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Request Deatails", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        jPanel11.setLayout(null);

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel26.setText("Request ID       :");
        jPanel11.add(jLabel26);
        jLabel26.setBounds(20, 30, 100, 14);

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel27.setText("Employee Name:");
        jPanel11.add(jLabel27);
        jLabel27.setBounds(20, 60, 100, 14);

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel28.setText("Leave Type       :");
        jPanel11.add(jLabel28);
        jLabel28.setBounds(20, 90, 100, 14);

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel29.setText("From Date         :");
        jPanel11.add(jLabel29);
        jLabel29.setBounds(20, 120, 90, 14);

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel30.setText("To Date             :");
        jPanel11.add(jLabel30);
        jLabel30.setBounds(20, 150, 100, 14);

        jLabel31.setText("Session       :");
        jPanel11.add(jLabel31);
        jLabel31.setBounds(280, 30, 80, 14);

        jLabel32.setText("Description :");
        jPanel11.add(jLabel32);
        jLabel32.setBounds(280, 60, 80, 14);

        jLabel33.setText("jLabel33");
        jPanel11.add(jLabel33);
        jLabel33.setBounds(130, 30, 70, 14);

        jLabel34.setText("jLabel34");
        jPanel11.add(jLabel34);
        jLabel34.setBounds(130, 60, 100, 14);

        jLabel35.setText("jLabel35");
        jPanel11.add(jLabel35);
        jLabel35.setBounds(130, 90, 100, 14);

        jLabel36.setText("jLabel36");
        jPanel11.add(jLabel36);
        jLabel36.setBounds(130, 120, 100, 14);

        jLabel37.setText("jLabel37");
        jPanel11.add(jLabel37);
        jLabel37.setBounds(130, 150, 100, 14);

        jLabel38.setText("jLabel38");
        jPanel11.add(jLabel38);
        jLabel38.setBounds(440, 30, 90, 14);

        jLabel39.setText("jLabel39");
        jPanel11.add(jLabel39);
        jLabel39.setBounds(400, 60, 170, 30);

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel41.setText("No of pending requests");
        jPanel11.add(jLabel41);
        jLabel41.setBounds(170, 60, 310, 30);

        table3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        table3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table3MouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(table3);

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setText("Pending Requests");

        jButton6.setText("Approve");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Reject");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("In case of a rejection add resons here"));
        jPanel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        lv_reason.setColumns(20);
        lv_reason.setRows(5);
        jScrollPane8.setViewportView(lv_reason);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6)
                        .addGap(26, 26, 26)
                        .addComponent(jButton7)
                        .addGap(20, 20, 20))
                    .addComponent(jScrollPane7)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel25)
                        .addGap(11, 11, 11))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton6)
                            .addComponent(jButton7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)))
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jTabbedPane1.addTab("Pending Requests", jPanel6);

        jPanel8.setLayout(null);
        jPanel8.add(emp_id);
        emp_id.setBounds(28, 33, 269, 20);

        jButton10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton10.setText("Search");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton10);
        jButton10.setBounds(340, 29, 74, 25);

        pending_tb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        jScrollPane9.setViewportView(pending_tb);

        jTabbedPane4.addTab("Pending Leaves", jScrollPane9);

        approved_tb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        jScrollPane10.setViewportView(approved_tb);

        jTabbedPane4.addTab("Approved Leaves", jScrollPane10);

        rejected_tb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        jScrollPane11.setViewportView(rejected_tb);

        jTabbedPane4.addTab("Rejected Leaves", jScrollPane11);

        jPanel8.add(jTabbedPane4);
        jTabbedPane4.setBounds(20, 100, 820, 200);

        jButton11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton11.setText("Leave Report");
        jPanel8.add(jButton11);
        jButton11.setBounds(700, 330, 140, 25);

        jTabbedPane1.addTab("Employee Leave History", jPanel8);

        jPanel4.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 870, 490));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel13.setMaximumSize(new java.awt.Dimension(915, 570));
        jPanel13.setMinimumSize(new java.awt.Dimension(915, 570));
        jPanel13.setPreferredSize(new java.awt.Dimension(915, 570));
        jPanel13.setLayout(null);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enter the employee ID here ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        jPanel14.setMaximumSize(new java.awt.Dimension(390, 320));
        jPanel14.setMinimumSize(new java.awt.Dimension(390, 320));
        jPanel14.setPreferredSize(new java.awt.Dimension(390, 320));
        jPanel14.setLayout(null);

        att_eid_chkinout.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                att_eid_chkinoutComponentAdded(evt);
            }
        });
        att_eid_chkinout.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                att_eid_chkinoutInputMethodTextChanged(evt);
            }
        });
        att_eid_chkinout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                att_eid_chkinoutActionPerformed(evt);
            }
        });
        att_eid_chkinout.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                att_eid_chkinoutPropertyChange(evt);
            }
        });
        att_eid_chkinout.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                att_eid_chkinoutKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                att_eid_chkinoutKeyReleased(evt);
            }
        });
        jPanel14.add(att_eid_chkinout);
        att_eid_chkinout.setBounds(69, 51, 246, 30);

        jButton12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton12.setText("Submit");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton12);
        jButton12.setBounds(69, 140, 246, 25);

        jPanel13.add(jPanel14);
        jPanel14.setBounds(50, 80, 390, 320);

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Profile", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        jPanel15.setMaximumSize(new java.awt.Dimension(390, 320));
        jPanel15.setMinimumSize(new java.awt.Dimension(390, 320));
        jPanel15.setPreferredSize(new java.awt.Dimension(390, 320));
        jPanel15.setLayout(null);

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel60.setText("Name ");
        jPanel15.add(jLabel60);
        jLabel60.setBounds(41, 68, 39, 17);

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel61.setText("Age");
        jPanel15.add(jLabel61);
        jLabel61.setBounds(41, 113, 23, 17);

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel62.setText("NIC Number");
        jPanel15.add(jLabel62);
        jLabel62.setBounds(41, 156, 75, 17);

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel63.setText("jLabel63");
        jPanel15.add(jLabel63);
        jLabel63.setBounds(141, 68, 230, 17);

        jLabel64.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel64.setText("jLabel64");
        jPanel15.add(jLabel64);
        jLabel64.setBounds(141, 113, 200, 17);

        jLabel65.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel65.setText("jLabel65");
        jPanel15.add(jLabel65);
        jLabel65.setBounds(141, 156, 200, 17);

        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel66.setText("Department");
        jPanel15.add(jLabel66);
        jLabel66.setBounds(41, 200, 74, 17);

        jLabel67.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel67.setText("jLabel67");
        jPanel15.add(jLabel67);
        jLabel67.setBounds(141, 200, 210, 17);

        jPanel13.add(jPanel15);
        jPanel15.setBounds(470, 80, 390, 320);

        jLabel68.setText("jLabel68");
        jPanel13.add(jLabel68);
        jLabel68.setBounds(700, 50, 40, 14);

        bck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        bck.setText("jLabel69");
        jPanel13.add(bck);
        bck.setBounds(0, 0, 920, 560);

        rightPanel.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 915, 570));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setMaximumSize(new java.awt.Dimension(915, 570));
        jPanel1.setMinimumSize(new java.awt.Dimension(915, 570));
        jPanel1.setPreferredSize(new java.awt.Dimension(915, 570));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Welcome");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 120, -1, -1));

        rightPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // removing panels
              // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels
        jPanel3.setVisible(false);
        jPanel2.setVisible(true);
        jPanel4.setVisible(false);
        jPanel13.setVisible(false);
        
        rightPanel.add(jPanel2);
        rightPanel.repaint();
        rightPanel.revalidate();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // removing panels
        //rightPanel.removeAll();
        //rightPanel.repaint();
        //rightPanel.revalidate();
        //adding panels
     
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
         
        jPanel2.setVisible(false);
        jPanel3.setVisible(false);
        jPanel4.setVisible(true);
        jPanel13.setVisible(false);
        
        rightPanel.add(jPanel4);
        rightPanel.repaint();
        rightPanel.revalidate();
        
     
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
         
        jPanel2.setVisible(false);
        jPanel3.setVisible(false);
        jPanel4.setVisible(false);
        jPanel13.setVisible(true);
        
        rightPanel.add(jPanel13);
        rightPanel.repaint();
        rightPanel.revalidate();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void att_pwdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_att_pwdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_att_pwdActionPerformed

    private void add_emActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_emActionPerformed
        
            int did = 0;
        
     // String eid = att_id.getText();
      String name=att_name.getText();
     String  age= att_age.getText();
      String  dep=att_dep.getSelectedItem().toString();
     
     
      String nic=att_nic.getText();
      String address= att_address.getText();
      String usern=att_usern.getText();
      String pwd=att_pwd.getText();
      
 
     
        if(StringUtils.isEmptyOrWhitespaceOnly(name)||StringUtils.isEmptyOrWhitespaceOnly(age)||StringUtils.isEmptyOrWhitespaceOnly(nic)||StringUtils.isEmptyOrWhitespaceOnly(address)||dep.equalsIgnoreCase("select department")) {
            
          JOptionPane.showMessageDialog(this,"one or more fields are empty");
          
          if(dep.equalsIgnoreCase("select department"))
            JOptionPane.showMessageDialog(this,"Select a Department");
          if(StringUtils.isEmptyOrWhitespaceOnly(name))
              //error_name.setVisible(true);
                  att_name.setBackground(Color.PINK);
          if(StringUtils.isEmptyOrWhitespaceOnly(age))
               //error_age.setVisible(true);
                 att_age.setBackground(Color.PINK);
          if(StringUtils.isEmptyOrWhitespaceOnly(address))
               //error_add.setVisible(true);
                 att_address.setBackground(Color.PINK);
          if(Empname||Empage)
                JOptionPane.showMessageDialog(this,"one or more fields are Invalid"); 
          if(StringUtils.isEmptyOrWhitespaceOnly(nic))
               //error_nic.setVisible(true);
                 att_nic.setBackground(Color.PINK);
          else if(validation.val_nic(att_nic.getText()))
                 att_nic.setBackground(Color.PINK);
        
         
        } 
        else if(validation.val_nic(att_nic.getText())){
               //JOptionPane.showMessageDialog(this,"Invalid Id");
               att_nic.setBackground(Color.PINK);
               if(Empname||Empage)
                JOptionPane.showMessageDialog(this,"one or more fields are Invalid"); 
                   
        }
        else if(Empname||Empage)
                JOptionPane.showMessageDialog(this,"one or more fields are Invalid");   
              
        else{
        
      
       try{    
           int ageI = Integer.parseInt(age);
       
           
      String qry1="SELECT deptID FROM department WHERE deptName='"+dep+"'";
         pst= con.prepareStatement(qry1);
        rst=pst.executeQuery();
        if(rst.next())
            did =rst.getInt("deptID");
         
      
      String qry2= "INSERT INTO employee (name,age,deptID,nic,address,userName,password)VALUES('"+name+"','"+ageI+"','"+did+"','"+nic+"','"+address+"','"+usern+"','"+pwd+"');";
      pst=con.prepareStatement(qry2);
      pst.execute();
       //jButton2.setVisible(false);
       //jButton8.setVisible(true);
      }
      catch(Exception e){
       JOptionPane.showMessageDialog(this,e);
      }
        
       tableLoad();
        }
      
        
    }//GEN-LAST:event_add_emActionPerformed

    private void att_edetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_att_edetailsMouseClicked
       
          error_name.setVisible(false);
          error_add.setVisible(false);
          error_nic.setVisible(false);
          error_age.setVisible(false);
        int index; 
         
      int r= att_edetails.getSelectedRow();  
      String eid= att_edetails.getValueAt(r,0).toString();
      String  name= att_edetails.getValueAt(r,1).toString();
      String age= att_edetails.getValueAt(r,2).toString();
      String dep=att_edetails.getValueAt(r,3).toString();
      String nic= att_edetails.getValueAt(r,4).toString();
      String address=att_edetails.getValueAt(r,5).toString();
     String usern=att_edetails.getValueAt(r,6).toString();
      
        System.out.println(att_edetails.getValueAt(r,6).toString());
       String pwd=att_edetails.getValueAt(r,7).toString();
      if(dep.equalsIgnoreCase("Office Staff"))
          index=1;
      else if(dep.equalsIgnoreCase("Production Staff"))
          index=2;
      else if(dep.equalsIgnoreCase("Distibution Staff")) 
          index=3;
      else 
          index=4;
                  
      
      att_name.setText(name);
      att_id.setText(eid);
      att_age.setText(age);
      att_dep.setSelectedIndex(index);
      att_nic.setText(nic);
      att_address.setText(address);
      att_usern.setText(usern);
      att_pwd.setText(pwd);
    }//GEN-LAST:event_att_edetailsMouseClicked

    private void clear_emActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_emActionPerformed
        
        att_name.setText(null);
      att_id.setText("");
      att_age.setText(null);
      att_dep.setSelectedIndex(0);
      att_nic.setText(null);
      att_address.setText(null);
      att_usern.setText(null);
      att_pwd.setText(null);
      
       
    }//GEN-LAST:event_clear_emActionPerformed

    private void update_emActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_emActionPerformed
     String name=att_name.getText();
     String  age= att_age.getText();
     String  dep=att_dep.getSelectedItem().toString();
     String nic=att_nic.getText();
     String address= att_address.getText(); 
     
      if(att_id.getText().isEmpty())
         JOptionPane.showMessageDialog(this,"Please select an entry to update"); 
      else{
        int x=JOptionPane.showConfirmDialog(this ,"Do you really want to update" );
      if(x==0){
          if(StringUtils.isEmptyOrWhitespaceOnly(name)||StringUtils.isEmptyOrWhitespaceOnly(age)||StringUtils.isEmptyOrWhitespaceOnly(nic)||StringUtils.isEmptyOrWhitespaceOnly(address)||dep.equalsIgnoreCase("select department")) {
            
          JOptionPane.showMessageDialog(this,"one or more fields are empty");
          
          if(dep.equalsIgnoreCase("select department"))
            JOptionPane.showMessageDialog(this,"Select a Department");
          if(StringUtils.isEmptyOrWhitespaceOnly(name))
              //error_name.setVisible(true);
               att_name.setBackground(Color.pink);
          if(StringUtils.isEmptyOrWhitespaceOnly(age))
              // error_age.setVisible(true);
               att_age.setBackground(Color.pink);
          if(StringUtils.isEmptyOrWhitespaceOnly(address))
               //error_add.setVisible(true);
               att_address.setBackground(Color.pink);
          if(StringUtils.isEmptyOrWhitespaceOnly(nic))
               //error_nic.setVisible(true);
               att_nic.setBackground(Color.pink);
          else if(validation.val_nic(att_nic.getText()))
                  att_nic.setBackground(Color.pink);
          }
          else if(validation.val_nic(att_nic.getText()))
               //JOptionPane.showMessageDialog(this,"Invalid Id");
               att_nic.setBackground(Color.pink);
          else{
          
      int eid=Integer.parseInt(att_id.getText());
      //String name= att_name.getText();
      int    ageI =Integer.parseInt(att_age.getText());
    // String  dep=att_dep.getSelectedItem().toString();
      //String address=att_address.getText();
      //String nic= att_nic.getText();
       String usern= att_usern.getText();
      String pwd=att_pwd.getText();
    try {
        int did=0;
        String qry1="SELECT deptID FROM department WHERE deptName='"+dep+"'";
        pst= con.prepareStatement(qry1);
        rst=pst.executeQuery();
        if(rst.next())
            did =rst.getInt("deptID");
            
      String qry2="UPDATE employee SET name='"+name+"',age='"+ageI+"',deptID='"+did+"', nic='"+nic+"',address='"+address+"',userName='"+usern+"',password='"+pwd+"'  where empID='"+eid+"' " ;
          
              pst=con.prepareStatement(qry2);
              pst.execute();
          } catch (Exception e) {
           JOptionPane.showMessageDialog(this,"sql error");
          }
      
      
      
      tableLoad();
          }
      }
    }//GEN-LAST:event_update_emActionPerformed
    }
    private void delete_emActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_emActionPerformed
         
     
     if(att_id.getText().isEmpty())
         JOptionPane.showMessageDialog(this,"Please select an entry to delete");
     else{
        int x=JOptionPane.showConfirmDialog(this ,"Do you really want to delete?" );
    
    if(x==0){
      int eid=Integer.parseInt(att_id.getText());  
     String qry="DELETE FROM employee WHERE empID='"+eid+"'";
        try {
             pst=con.prepareStatement(qry);
              pst.execute();
        } catch (Exception e) {
           JOptionPane.showMessageDialog(this,"sql error"); 
        }
    }
         tableLoad();
    }//GEN-LAST:event_delete_emActionPerformed
    }
    private void att_ageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_att_ageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_att_ageActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
       String type=att_cat.getSelectedItem().toString();
       
        if(type.equalsIgnoreCase("select category")){
             JOptionPane.showMessageDialog(this,"Please selecet a Category");   
        }
        else if(type.equalsIgnoreCase("manager")){
       lable_name.setText("Type");
       att_name.setVisible(false);
       att_desig.setVisible(false);
       att_mtype.setVisible(true);
        jButton8.setVisible(true);
        att_em_search.setText("");
        att_other_search.setText("");
       changePos1();
        adjust1();
        title.setText("Manager Details");
        loadMdeails();
        }
        else if(type.equalsIgnoreCase("production & office staff")){
        lable_name.setText("Designation");
        att_name.setVisible(false);
        att_mtype.setVisible(false);
        att_desig.setVisible(true);
        jButton8.setVisible(true);
           att_em_search.setText("");
        att_other_search.setText("");
       title.setText("Production & Office Staff Details");
        changePos1();
        adjust1();
        loadPOdetails();
        
        }
        else if(type.equalsIgnoreCase("distribution staff")){
           lable_name.setText("Designation");
        att_name.setVisible(false);
        att_mtype.setVisible(false);
        att_desig.setVisible(true);
        jButton8.setVisible(true);
           att_em_search.setText("");
        att_other_search.setText("");
         title.setText("Distribution Staff Details");
        
        changePos1();
        adjust1();
        loadDdetails();
        }
        
        else if(type.equalsIgnoreCase("folding staff")){
          lable_name.setText("Designation");
        att_name.setVisible(false);
        att_mtype.setVisible(false);
        att_desig.setVisible(true);
        jButton8.setVisible(true);
           att_em_search.setText("");
        att_other_search.setText("");
        title.setText("Folding Staff Details");
        changePos1();
        adjust1();
        loadFdetails();
        
        }
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
     lable_name.setText("Name");
       att_name.setVisible(true);
       att_mtype.setVisible(false);
      title.setText("Employee Details");
       // changePos2();
        adjust2();
      jButton8.setVisible(false);
      tableLoad();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void att_desigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_att_desigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_att_desigActionPerformed

    private void att_otherdetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_att_otherdetailsMouseClicked
      String type=att_cat.getSelectedItem().toString();
       
        if(type.equalsIgnoreCase("manager")){
        
        
        int r= att_otherdetails.getSelectedRow(); 
      emp_id_mouse_clicked=Integer.parseInt(att_otherdetails.getValueAt(r,0).toString());
      String mtype = att_otherdetails.getValueAt(r,1).toString(); 
       att_mtype.setText(mtype);
       
        }
        else{
          
        int r= att_otherdetails.getSelectedRow(); 
       emp_id_mouse_clicked=Integer.parseInt(att_otherdetails.getValueAt(r,0).toString());
      String desig = att_otherdetails.getValueAt(r,1).toString(); 
       att_desig.setText(desig);
        
        }
       
        
    }//GEN-LAST:event_att_otherdetailsMouseClicked

    private void add_otherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_otherActionPerformed
     
       if(att_id.getText().isEmpty())
             JOptionPane.showMessageDialog(this,"please go back and select an entry");  
       else{
           
         String type=att_cat.getSelectedItem().toString();
           if(type.equalsIgnoreCase("manager")){
               if(StringUtils.isEmptyOrWhitespaceOnly(att_mtype.getText()))
                   //error_name.setVisible(true);
                     att_mtype.setBackground(Color.pink);
               else{
        int eid=Integer.parseInt(att_id.getText());
        String mtype=att_mtype.getText();
        
        try{
        String qry1="INSERT INTO manager (empID,managerType)VALUES('"+eid+"','"+mtype+"');";
        pst=con.prepareStatement(qry1);
        pst.execute(); 
        } 
         catch(Exception e){
       JOptionPane.showMessageDialog(this,"sql error");
      }
        loadMdeails();
     } 
           }
         else if(type.equalsIgnoreCase("production & office staff")){
               if(StringUtils.isEmptyOrWhitespaceOnly(att_desig.getText()))
                   //error_name.setVisible(true);
                   att_desig.setBackground(Color.pink);
               else{
           int eid=Integer.parseInt(att_id.getText());
        String desig =att_desig.getText();
        
        try{
        String qry1="INSERT INTO production_office_staff (empID,designation)VALUES ('"+eid+"','"+desig+"');";
        pst=con.prepareStatement(qry1);
        pst.execute(); 
        } 
         catch(Exception e){
       JOptionPane.showMessageDialog(this,"sql error");
      }
        loadPOdetails();
     }
         }
         else if(type.equalsIgnoreCase("distribution staff")){
             
                if(StringUtils.isEmptyOrWhitespaceOnly(att_desig.getText()))
                   //error_name.setVisible(true);
                     att_desig.setBackground(Color.pink);
                else{
                int eid=Integer.parseInt(att_id.getText());
        String desig =att_desig.getText();
        
        try{
        String qry1="INSERT INTO distribution_staff (empID,designation)VALUES ('"+eid+"','"+desig+"');";
        pst=con.prepareStatement(qry1);
        pst.execute(); 
        } 
         catch(Exception e){
       JOptionPane.showMessageDialog(this,"sql error");
      }
        loadDdetails();
     }
         }
         else if(type.equalsIgnoreCase("folding staff")){
                if(StringUtils.isEmptyOrWhitespaceOnly(att_desig.getText()))
                  // error_name.setVisible(true);
                     att_desig.setBackground(Color.pink);
                else{
        int eid=Integer.parseInt(att_id.getText());
        String desig =att_desig.getText();
        
        try{
        String qry1="INSERT INTO folding_staff (empID,designation)VALUES ('"+eid+"','"+desig+"');";
        pst=con.prepareStatement(qry1);
        pst.execute(); 
        } 
         catch(Exception e){
       JOptionPane.showMessageDialog(this,"sql error");
      }
        loadFdetails();
     }
         }
       }
        
    }//GEN-LAST:event_add_otherActionPerformed

    private void update_otherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_otherActionPerformed
       
       if(StringUtils.isEmptyOrWhitespaceOnly(att_mtype.getText())&& StringUtils.isEmptyOrWhitespaceOnly(att_desig.getText()))
             JOptionPane.showMessageDialog(this,"please select an entry to update");  
       else{           
        int x=JOptionPane.showConfirmDialog(this ,"Do you really want to update" );
        if(x==0){
          String type=att_cat.getSelectedItem().toString();
              if(type.equalsIgnoreCase("manager")){
        //int eid=Integer.parseInt(att_id.getText());
        String mtype=att_mtype.getText();
        
        try{
        String qry1="UPDATE manager SET managerType='"+mtype+"' where empID='"+emp_id_mouse_clicked+"' " ;
        pst=con.prepareStatement(qry1);
        pst.execute(); 
        } 
         catch(Exception e){
       JOptionPane.showMessageDialog(this,"sql error");
      }
        loadMdeails();
     } 
         else if(type.equalsIgnoreCase("production & office staff")){
           //int eid=Integer.parseInt(att_id.getText());
        String desig =att_desig.getText();
        
        try{
        String qry1="UPDATE production_office_staff SET designation='"+desig+"' where empID='"+emp_id_mouse_clicked+"' " ;
        pst=con.prepareStatement(qry1);
        pst.execute(); 
        } 
         catch(Exception e){
       JOptionPane.showMessageDialog(this,"sql error");
      }
        loadPOdetails();
     }
         else if(type.equalsIgnoreCase("distribution staff")){
                //int eid=Integer.parseInt(att_id.getText());
        String desig =att_desig.getText();
        
        try{
        String qry1="UPDATE distribution_staff SET designation='"+desig+"' where empID='"+emp_id_mouse_clicked+"' " ;
        pst=con.prepareStatement(qry1);
        pst.execute(); 
        } 
         catch(Exception e){
       JOptionPane.showMessageDialog(this,"sql error");
      }
        loadDdetails();
     }
         else if(type.equalsIgnoreCase("folding staff")){
        //int eid=Integer.parseInt(att_id.getText());
        String desig =att_desig.getText();
        
        try{
        String qry1="UPDATE folding_staff SET designation='"+desig+"' where empID='"+emp_id_mouse_clicked+"' " ;
        pst=con.prepareStatement(qry1);
        pst.execute(); 
        } 
         catch(Exception e){
       JOptionPane.showMessageDialog(this,"sql error");
      }
        loadFdetails();
     }
        } 
       }
        
    }//GEN-LAST:event_update_otherActionPerformed

    private void att_mtypeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_att_mtypeMouseClicked
       // error_name.setVisible(false);
         att_mtype.setBackground(Color.white);
    }//GEN-LAST:event_att_mtypeMouseClicked

    private void delete_otherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_otherActionPerformed
            if(StringUtils.isEmptyOrWhitespaceOnly(att_mtype.getText())&& StringUtils.isEmptyOrWhitespaceOnly(att_desig.getText()))
             JOptionPane.showMessageDialog(this,"please select an entry to Delete");
            else{
        
        int x=JOptionPane.showConfirmDialog(this ,"Do you really want to Delete" );
        if(x==0){
          String type=att_cat.getSelectedItem().toString();
              if(type.equalsIgnoreCase("manager")){
        //int eid=Integer.parseInt(att_id.getText());
        //String mtype=att_mtype.getText();
        
        try{
        String qry1="DELETE FROM manager WHERE empID='"+emp_id_mouse_clicked+"'"; 
        pst=con.prepareStatement(qry1);
        pst.execute(); 
        } 
         catch(Exception e){
       JOptionPane.showMessageDialog(this,"sql error");
      }
        loadMdeails();
     } 
         else if(type.equalsIgnoreCase("production & office staff")){
           //int eid=Integer.parseInt(att_id.getText());
       // String desig =att_desig.getText();
        
        try{
        String qry1="DELETE FROM production_office_staff WHERE empID='"+emp_id_mouse_clicked+"'";
        pst=con.prepareStatement(qry1);
        pst.execute(); 
        } 
         catch(Exception e){
       JOptionPane.showMessageDialog(this,"sql error");
      }
        loadPOdetails();
     }
         else if(type.equalsIgnoreCase("distribution staff")){
                //int eid=Integer.parseInt(att_id.getText());
       // String desig =att_desig.getText();
        
        try{
        String qry1="DELETE FROM distribution_staff WHERE empID='"+emp_id_mouse_clicked+"'";
        pst=con.prepareStatement(qry1);
        pst.execute(); 
        } 
         catch(Exception e){
       JOptionPane.showMessageDialog(this,"sql error");
      }
        loadDdetails();
     }
         else if(type.equalsIgnoreCase("folding staff")){
        //int eid=Integer.parseInt(att_id.getText());
        //String desig =att_desig.getText();
        
        try{
        String qry1="DELETE FROM folding_staff WHERE empID='"+emp_id_mouse_clicked+"'";
        pst=con.prepareStatement(qry1);
        pst.execute(); 
        } 
         catch(Exception e){
       JOptionPane.showMessageDialog(this,"sql error");
      }
        loadFdetails();
     }
        } 
        
        
            }  
    }//GEN-LAST:event_delete_otherActionPerformed

    private void att_em_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_att_em_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_att_em_searchActionPerformed

    private void search_emActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_emActionPerformed
     
        String term=att_em_search.getText();
      try{
        String qry= "SELECT e.empID,e.name,e.age,d.deptName,e.nic,e.address,e.userName,e.password FROM employee e ,department d WHERE e.deptID=d.deptID AND e.name LIKE '%"+term+"%'";
      
        
        
        pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
        
        att_edetails.setModel(DbUtils.resultSetToTableModel(rst));
        }
       catch(Exception e){
         JOptionPane.showMessageDialog(this,"sql error");
       
       }
        
        
        
        
    }//GEN-LAST:event_search_emActionPerformed

    private void search_otherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_otherActionPerformed
         
        String term=att_other_search.getText();
         String type=att_cat.getSelectedItem().toString();
         try{
      if(type.equalsIgnoreCase("manager")){
         //try{
       
                String qry= "SELECT m.empID,m.managerType FROM manager m where m.managerType LIKE '%"+term+"%'";
        
        
        pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
        
        att_otherdetails.setModel(DbUtils.resultSetToTableModel(rst));
        }
//       catch(Exception e){
//         JOptionPane.showMessageDialog(this,e);
//       
//       } 
          
          
//      }
      else if(type.equalsIgnoreCase("production & office staff")){
//               try{
       
                String qry= "SELECT p.empID,p.designation FROM production_office_staff p where p.designation LIKE '%"+term+"%'";
        
        
        pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
        
        att_otherdetails.setModel(DbUtils.resultSetToTableModel(rst));
        }
//       catch(Exception e){
//         JOptionPane.showMessageDialog(this,e);
//       
//       } 
//      }
      else if(type.equalsIgnoreCase("distribution staff")){
//                 try{
       
                String qry= "SELECT empID,designation FROM distribution_staff where  designation LIKE '%"+term+"%'";
        
        
        pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
        
        att_otherdetails.setModel(DbUtils.resultSetToTableModel(rst));
        }
//       catch(Exception e){
//         JOptionPane.showMessageDialog(this,e);
//       
//       } 
//      }
      else if(type.equalsIgnoreCase("folding staff")){
      
//               try{
       
                String qry= "SELECT empID,designation FROM folding_staff where designation LIKE '%"+term+"%'";
        
        
        pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
        
        att_otherdetails.setModel(DbUtils.resultSetToTableModel(rst));
        }
//       catch(Exception e){
//         JOptionPane.showMessageDialog(this,e);
//       
//       } 
      }
         catch(Exception e){
         JOptionPane.showMessageDialog(this,e);
         }
         
        
        
        
    }//GEN-LAST:event_search_otherActionPerformed

    private void att_other_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_att_other_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_att_other_searchActionPerformed

    private void att_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_att_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_att_nameActionPerformed

    private void att_nameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_att_nameMouseClicked
       //error_name.setVisible(false);
           att_name.setBackground(Color.white);
    }//GEN-LAST:event_att_nameMouseClicked

    private void att_ageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_att_ageMouseClicked
        //error_age.setVisible(false);
         att_age.setBackground(Color.white);
    }//GEN-LAST:event_att_ageMouseClicked

    private void att_nicMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_att_nicMouseClicked
           //error_nic.setVisible(false);
          att_nic.setBackground(Color.white);
    }//GEN-LAST:event_att_nicMouseClicked

    private void att_addressMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_att_addressMouseClicked
           //error_add.setVisible(false);
            att_address.setBackground(Color.white);
    }//GEN-LAST:event_att_addressMouseClicked

    private void att_desigMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_att_desigMouseClicked
        //error_name.setVisible(false);
         att_desig.setBackground(Color.white);
         
    }//GEN-LAST:event_att_desigMouseClicked

    private void error_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_error_nameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_error_nameKeyReleased

    private void att_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_att_nameKeyReleased
        Character c=evt.getKeyChar();
        if( Character.isDigit(c)){
          evt.consume();
            att_name.setBackground(Color.PINK);
            Empname=true;
        }
        else{
            att_name.setBackground(Color.WHITE);
            Empname=false;
        }
    }//GEN-LAST:event_att_nameKeyReleased

    private void att_ageKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_att_ageKeyReleased
   /*    Character c=evt.getKeyChar();
        if(!( Character.isDigit(c)||(c==KeyEvent.VK_BACKSPACE)||(c==KeyEvent.VK_DELETE))){
          evt.consume();
            att_age.setBackground(Color.PINK);
            Empage=true;
        }
        else{
           att_age.setBackground(Color.white); 
           Empage=false;
        } */
          
    }//GEN-LAST:event_att_ageKeyReleased

    private void att_nicKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_att_nicKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_att_nicKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       boolean chk1; 
       boolean chk2; 
     String lv_type= leave_type.getSelectedItem().toString();
     String lv_desc= leave_desc.getText();
     java.sql.Date lv_from=null;
      java.sql.Date lv_to=null;
     
               try{
                 lv_from=new java.sql.Date(leave_from.getDate().getTime());
                     //leave_from.setBackground(Color.white);
                     chk1=false;
             }
             catch(NullPointerException e){
                     //leave_from.setBackground(Color.pink);
                     chk1=true;
             }
             
             
               try{
                 lv_to=new java.sql.Date(leave_to.getDate().getTime());
                    // leave_to.setBackground(Color.white);
                     chk2=false;
             }
             catch(NullPointerException e){
                //leave_to.setBackground(Color.pink);
                   chk2=true;
             }
      
      
      
        
        
        if(lv_type.equalsIgnoreCase("choose leave type")||StringUtils.isEmptyOrWhitespaceOnly(lv_desc)||chk1==true||chk2==true){
         
         JOptionPane.showMessageDialog(this,"one or more fields are empty");
             if(lv_type.equalsIgnoreCase("choose leave type"))
                 JOptionPane.showMessageDialog(this,"Please choose a leave type");
             if(StringUtils.isEmptyOrWhitespaceOnly(lv_desc))
                 leave_desc.setBackground(Color.pink); 
             if(chk1)
                    leave_from.setBackground(Color.pink);
             if(chk2)
                   leave_to.setBackground(Color.pink);
            if(lv_type.equalsIgnoreCase("short leaves")&& lv_session.equalsIgnoreCase("not relevant"))
           
                  JOptionPane.showMessageDialog(this,"please select a session");
            if(lv_to.before(lv_from))
           JOptionPane.showMessageDialog(this,"choose the date correctly");
        /* if(StringUtils.isEmptyOrWhitespaceOnly(lv_from.toString()))
            JOptionPane.showMessageDialog(null,"Please choose a the from date"); 
         if(StringUtils.isEmptyOrWhitespaceOnly(lv_to.toString()))
            JOptionPane.showMessageDialog(null,"Please choose a the to date");*/
       
        } 
        else if(lv_type.equalsIgnoreCase("short leaves")&& lv_session.equalsIgnoreCase("not relevant")){
           
            JOptionPane.showMessageDialog(this,"please select a session");
             if(lv_to.before(lv_from))
           JOptionPane.showMessageDialog(this,"choose the date correctly");
        }
        else if(lv_to.before(lv_from))
           JOptionPane.showMessageDialog(this,"choose the date correctly");
        
       
        else{
        
     
     
     Leave Lv=new Leave(eid,lv_type,lv_desc,lv_from,lv_to,lv_session);
     LeaveDAO da=new LeaveDAO();
     if(da.insertLeave(Lv))
       JOptionPane.showMessageDialog(this,"your leave was successfully placed");
     else
       JOptionPane.showMessageDialog(this,"Please try again");   
      tableLoad1();
      tableLoad2();
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void leave_session1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leave_session1ActionPerformed
         lv_session="Morning";
    }//GEN-LAST:event_leave_session1ActionPerformed

    private void leave_session2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leave_session2ActionPerformed
         lv_session="Afternoon";
    }//GEN-LAST:event_leave_session2ActionPerformed

    private void leave_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leave_typeActionPerformed
         String lv_type= leave_type.getSelectedItem().toString();
         if(lv_type.equalsIgnoreCase("short leaves")){
            leave_session1.setVisible(true);
            leave_session2.setVisible(true);  
         }
         else{
            leave_session1.setVisible(false);
            leave_session2.setVisible(false);
         }
    }//GEN-LAST:event_leave_typeActionPerformed

    private void table2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table2MouseClicked
        int r= table2.getSelectedRow();   
        rid = Integer.parseInt(table2.getValueAt(r,0).toString());
        stat= table2.getValueAt(r,6).toString();
         System.out.println("rid is "+ rid);
         System.out.println("status is "+ stat);
    }//GEN-LAST:event_table2MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      System.out.println("rid is "+ rid);
      System.out.println("status isss "+ stat);
      Leave Lv= new Leave();
      LeaveDAO da=new  LeaveDAO();
      Lv.setRid(rid);
      Lv.setStat(stat);
     if( da. deleteLeave(Lv))
       JOptionPane.showMessageDialog(this,"Leave was successfuly canceled");    
     else
       JOptionPane.showMessageDialog(this,"Try again"); 
      tableLoad1();
      tableLoad2();
     
    }//GEN-LAST:event_jButton3ActionPerformed

    private void table3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table3MouseClicked
        jLabel41.setVisible(false);     
        int r= table3.getSelectedRow();  
      jLabel33.setText(table3.getValueAt(r,0).toString());
          jLabel34.setText(table3.getValueAt(r,1).toString());
              jLabel35.setText(table3.getValueAt(r,2).toString());
                  jLabel36.setText(table3.getValueAt(r,3).toString());
                      jLabel37.setText(table3.getValueAt(r,4).toString());
                          jLabel38.setText(table3.getValueAt(r,5).toString());
                              jLabel39.setText(table3.getValueAt(r,6).toString());
        jLabel26.setVisible(true);jLabel27.setVisible(true);jLabel28.setVisible(true);
        jLabel29.setVisible(true);jLabel30.setVisible(true);jLabel31.setVisible(true);
        jLabel32.setVisible(true);jLabel33.setVisible(true);jLabel34.setVisible(true);
        jLabel35.setVisible(true);jLabel36.setVisible(true);jLabel37.setVisible(true);
        jLabel38.setVisible(true);jLabel39.setVisible(true);
       
      
    }//GEN-LAST:event_table3MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        rid=Integer.parseInt(jLabel33.getText());
        Leave lv=new Leave();
        LeaveDAO da= new   LeaveDAO();
        lv.setEid(eid);
        lv.setRid(rid);
        
        if(da.approveLeave(lv))
           JOptionPane.showMessageDialog(this,"Approved");
        else
           JOptionPane.showMessageDialog(this,"Error"); 
        tableLoad3();
        
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
           rid=Integer.parseInt(jLabel33.getText());
           String reason=lv_reason.getText();
        Leave lv=new Leave();
        LeaveDAO da= new   LeaveDAO();
        lv.setEid(eid);
        lv.setRid(rid);
        lv.setReason(reason);
        
        if(da.rejectLeave(lv))
           JOptionPane.showMessageDialog(this,"Rejected");
        else
           JOptionPane.showMessageDialog(this,"Error"); 
        tableLoad3();
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        int searchkey=Integer.parseInt(emp_id.getText());
        
        String qry1="SELECT reqID,type,fromDate,toDate,session,description  FROM leave_request WHERE status='Pending' and empID='"+searchkey+"' ";
        String qry2="SELECT reqID,type,fromDate,toDate,session,description  FROM leave_request WHERE empID='"+searchkey+"' and status='Approved' ";
        String qry3="SELECT reqID,type,fromDate,toDate,session,description,reason FROM  leave_request WHERE empID='"+searchkey+"' and status='Rejected' ";
        
        try{
        pst= con.prepareStatement(qry1);
        rst=pst.executeQuery();
        pending_tb.setModel(DbUtils.resultSetToTableModel(rst));
        pst= con.prepareStatement(qry2);
        rst=pst.executeQuery();
        approved_tb.setModel(DbUtils.resultSetToTableModel(rst));
         pst= con.prepareStatement(qry3);
        rst=pst.executeQuery();
        rejected_tb.setModel(DbUtils.resultSetToTableModel(rst));
        }
        catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
       
        approved.setVisible(false);
        rejected.setVisible(false);
        jLabel23.setVisible(false);
        jLabel24.setVisible(false);
        jLabel22.setVisible(true);jLabel40.setVisible(true);jLabel42.setVisible(true);
        jLabel43.setVisible(true); jLabel44.setVisible(true); jLabel45.setVisible(true);
        jLabel46.setVisible(true); jLabel47.setVisible(true); jLabel48.setVisible(true);
        jLabel49.setVisible(true); jLabel50.setVisible(true); jLabel51.setVisible(true);
        jLabel52.setVisible(true);jLabel53.setVisible(true);
        
         int r= table1.getSelectedRow();  
         jLabel46.setText(table1.getValueAt(r,0).toString());
         jLabel47.setText(table1.getValueAt(r,1).toString());
         jLabel48.setText(table1.getValueAt(r,2).toString());
         jLabel49.setText(table1.getValueAt(r,3).toString());
         jLabel50.setText(table1.getValueAt(r,4).toString());
         jLabel51.setText(table1.getValueAt(r,5).toString());
         jLabel53.setText(table1.getValueAt(r,6).toString());
       
         if(table1.getValueAt(r,6).toString().equalsIgnoreCase("rejected")){
             jLabel24.setText(table1.getValueAt(r,7).toString());
             jLabel23.setVisible(true);
             jLabel24.setVisible(true);
         
         
         }
         
         
           
        
        
        
        
        
    }//GEN-LAST:event_table1MouseClicked

    private void att_eid_chkinoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_att_eid_chkinoutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_att_eid_chkinoutActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
              int employeeID =Integer.parseInt(att_eid_chkinout.getText());
              //get system date time
              try{
              String qry1="SELECT * FROM  employee WHERE empID='"+employeeID+"' ";
                pst= con.prepareStatement(qry1);
                 rst=pst.executeQuery();
              if(rst.next()){
                //jPanel15.setVisible(true);
                jLabel63.setText(rst.getString("name"));
                jLabel64.setText(Integer.toString(rst.getInt("age")));
                jLabel65.setText(rst.getString("nic"));
                int depid=rst.getInt("deptID");
                String qry2="SELECT *FROM department WHERE deptID='"+depid+"'";
                pst= con.prepareStatement(qry2);
                 rst=pst.executeQuery();
                 if(rst.next())
                    jLabel67.setText(rst.getString("deptName"));   
                
              }
              
              }
              catch(Exception e){
                  System.out.println(e);
              }
              
            
             java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
          Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println( sdf.format(cal.getTime()) );
             
              int attcount=0;
            try{
              String qry1 ="SELECT count(*)as attcount FROM attendance WHERE empID='"+employeeID+"' and date='"+currentDate+"' ";
                 pst= con.prepareStatement(qry1);
                 rst=pst.executeQuery();
                 if(rst.next())
                attcount=rst.getInt("attcount");
                 
                 
                if(attcount==0){
                    String qry2="INSERT INTO attendance(date,checkInTime,empID)VALUES('"+currentDate+"','"+sdf.format(cal.getTime())+"' ,'"+employeeID+"')";
                    pst=con.prepareStatement(qry2);
                    pst.execute();
                }
                else if(attcount==1){
                String qry3="UPDATE attendance SET checkOutTime ='"+sdf.format(cal.getTime())+"' WHERE empID= '"+employeeID+"' and date='"+currentDate+"'";
                pst=con.prepareStatement(qry3);
                pst.execute();
                
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
                
              
          
    }//GEN-LAST:event_jButton12ActionPerformed

    private void att_eid_chkinoutPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_att_eid_chkinoutPropertyChange
       
      /*int employeeID =Integer.parseInt(att_eid_chkinout.getText());
              //get system date time
              try{
              String qry1="SELECT * FROM  employee WHERE empID='"+employeeID+"' ";
                pst= con.prepareStatement(qry1);
                 rst=pst.executeQuery();
              if(rst.next()){
                //jPanel15.setVisible(true);
                jLabel63.setText(rst.getString("name"));
                jLabel64.setText(Integer.toString(rst.getInt("age")));
                jLabel65.setText(rst.getString("nic"));
                int depid=rst.getInt("deptID");
                String qry2="SELECT *FROM department WHERE deptID='"+depid+"'";
                pst= con.prepareStatement(qry2);
                 rst=pst.executeQuery();
                 if(rst.next())
                    jLabel67.setText(rst.getString("deptName"));   
                
              }
              
              }
              catch(Exception e){
                  System.out.println(e);
              }
              
            
             java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
          Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println( sdf.format(cal.getTime()) );
             
              int attcount=0;
            try{
              String qry1 ="SELECT count(*)as attcount FROM attendance WHERE empID='"+employeeID+"' and date='"+currentDate+"' ";
                 pst= con.prepareStatement(qry1);
                 rst=pst.executeQuery();
                 if(rst.next())
                attcount=rst.getInt("attcount");
                 
                 
                if(attcount==0){
                String qry2="INSERT INTO attendance(date,checkInTime,empID)VALUES('"+currentDate+"','"+sdf.format(cal.getTime())+"' ,'"+employeeID+"')";
                pst=con.prepareStatement(qry2);
                pst.execute();
                }
                else if(attcount==1){
                String qry3="UPDATE attendance SET checkOutTime ='"+sdf.format(cal.getTime())+"' WHERE empID= '"+employeeID+"' and date='"+currentDate+"'";
                pst=con.prepareStatement(qry3);
                pst.execute();
                
                }
            }
            catch(Exception e){
                System.out.println(e);
            } */ 
        
          //System.out.println("yo yo yo boys");
              ///JOptionPane.showMessageDialog(null,"pppppppppppppppppppp"); 
    }//GEN-LAST:event_att_eid_chkinoutPropertyChange

    private void att_eid_chkinoutKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_att_eid_chkinoutKeyReleased
        
        
        
        
        
        
    }//GEN-LAST:event_att_eid_chkinoutKeyReleased

    private void att_eid_chkinoutInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_att_eid_chkinoutInputMethodTextChanged
        
                
        // TODO add your handling code here:
    }//GEN-LAST:event_att_eid_chkinoutInputMethodTextChanged

    private void att_eid_chkinoutComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_att_eid_chkinoutComponentAdded
//System.out.println("hi hi hi hoooi");        
// TODO add your handling code here:
    }//GEN-LAST:event_att_eid_chkinoutComponentAdded

    private void leave_typeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_leave_typeItemStateChanged
     
        String lv_type= leave_type.getSelectedItem().toString();
        if(lv_type.equalsIgnoreCase("medical leaves")){
          if(Integer.parseInt(ml.getText())==0)
              JOptionPane.showMessageDialog(null,"you have no medical leaves left"); 
        
        }
        else if(lv_type.equalsIgnoreCase("personal leaves")){
           if(Integer.parseInt(pl.getText())==0)
              JOptionPane.showMessageDialog(null,"you have no personal leaves left"); 
        
        }
          else if(lv_type.equalsIgnoreCase("cassual leaves")){
           if(Integer.parseInt(cl.getText())==0)
              JOptionPane.showMessageDialog(null,"you have no cassual leaves left"); 
        
        }
          else if(lv_type.equalsIgnoreCase("short leaves")){
           if(Integer.parseInt(sl.getText())==0)
              JOptionPane.showMessageDialog(null,"you have no short leaves left"); 
        
        }
        
        
        
        
    }//GEN-LAST:event_leave_typeItemStateChanged

    private void leave_descMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_leave_descMouseClicked
       leave_desc.setBackground(Color.white);
    }//GEN-LAST:event_leave_descMouseClicked

    private void leave_fromMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_leave_fromMouseClicked
             leave_from.setBackground(Color.white);
    }//GEN-LAST:event_leave_fromMouseClicked

    private void leave_toMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_leave_toMouseClicked
             leave_to.setBackground(Color.white);
    }//GEN-LAST:event_leave_toMouseClicked

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        
           rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
         
        jPanel2.setVisible(false);
        jPanel3.setVisible(true);
        jPanel4.setVisible(false);
        jPanel13.setVisible(false);
        
        rightPanel.add(jPanel3);
        rightPanel.repaint();
        rightPanel.revalidate();
        
        
    }//GEN-LAST:event_jButton13ActionPerformed

    private void att_eid_chkinoutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_att_eid_chkinoutKeyPressed
        System.out.println("hellooooooooo");
    }//GEN-LAST:event_att_eid_chkinoutKeyPressed
  private void changePos1(){

    add_other.setBounds(20,150, 60,25);
       update_other.setBounds(90,150, 80,25);
        delete_other.setBounds(180,150, 70,25);
         //clear_em.setBounds(250,200, 70,25);
    }
  /*private void changePos2(){

    add_em.setBounds(10,500, 60,25);
       update_em.setBounds(80,500, 80,25);
        delete_em.setBounds(170,500, 70,25);
         clear_em.setBounds(250,500, 70,25);
    }*/
  private void adjust1(){
        jLabel7.setVisible(false);
        att_id.setVisible(false);
        jLabel8.setVisible(false);
        att_age.setVisible(false);   
        jLabel3.setVisible(false);
        jLabel10.setVisible(false);
        att_dep.setVisible(false);
        jLabel11.setVisible(false);
        att_nic.setVisible(false);
        jLabel12.setVisible(false);
        jScrollPane2.setVisible(false);
        jLabel13.setVisible(false);
        jLabel6.setVisible(false);
        att_usern.setVisible(false);
        att_pwd.setVisible(false);
        jScrollPane1.setVisible(false);
        jScrollPane3.setVisible(true);
         search_other.setVisible(true);
         att_other_search.setVisible(true);
         search_em.setVisible(false);
         att_em_search.setVisible(false);
         
       add_em.setVisible(false);
       update_em.setVisible(false);
       delete_em.setVisible(false);
       clear_em.setVisible(false);
         
              
       add_other.setVisible(true);
       update_other.setVisible(true);
       delete_other.setVisible(true);
        
          error_name.setVisible(false);
          error_add.setVisible(false);
          error_nic.setVisible(false);
          error_age.setVisible(false);
                 
                 att_mtype.setText("");
                 att_desig.setText(""); 
  }
    private void adjust2(){
        jLabel7.setVisible(true);
        att_id.setVisible(true);
        jLabel8.setVisible(true);
        att_age.setVisible(true);   
        jLabel3.setVisible(true);
        jLabel10.setVisible(true);
        att_dep.setVisible(true);
        jLabel11.setVisible(true);
        att_nic.setVisible(true);
        jLabel12.setVisible(true);
        jScrollPane2.setVisible(true);
        jLabel6.setVisible(true);
        att_usern.setVisible(true);
        jLabel13.setVisible(true);
        att_pwd.setVisible(true);
        jScrollPane1.setVisible(true);
        jScrollPane3.setVisible(false);
        search_other.setVisible(false);
        search_em.setVisible(true);
        att_em_search.setVisible(true);
        att_other_search.setVisible(false);
        add_em.setVisible(true);
        update_em.setVisible(true);
        delete_em.setVisible(true);
         clear_em.setVisible(true);
         
         add_other.setVisible(false);
       update_other.setVisible(false);
        delete_other.setVisible(false);
        
  }
private void loadMdeails(){
    try{
       
                String qry= "SELECT empID,managerType FROM manager";
        
        
        pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
        
        att_otherdetails.setModel(DbUtils.resultSetToTableModel(rst));
        }
       catch(Exception e){
         JOptionPane.showMessageDialog(null,e);
       
       }  

}
private void loadPOdetails(){

  try{
       
                String qry= "SELECT empID,designation FROM production_office_staff";
        
        
        pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
        
        att_otherdetails.setModel(DbUtils.resultSetToTableModel(rst));
        }
       catch(Exception e){
         JOptionPane.showMessageDialog(null,e);
       
       }
}
private void loadDdetails(){

     try{
       
                String qry= "SELECT empID,designation FROM distribution_staff";
        
        
        pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
        
        att_otherdetails.setModel(DbUtils.resultSetToTableModel(rst));
        }
       catch(Exception e){
         JOptionPane.showMessageDialog(null,e);
       
       }


}
private void loadFdetails(){

     try{
       
                String qry= "SELECT empID,designation FROM folding_staff";
        
        
        pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
        
        att_otherdetails.setModel(DbUtils.resultSetToTableModel(rst));
        }
       catch(Exception e){
         JOptionPane.showMessageDialog(null,e);
       
       }


}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_em;
    private javax.swing.JButton add_other;
    private javax.swing.JLabel approved;
    private javax.swing.JTable approved_tb;
    private javax.swing.JTextArea att_address;
    private javax.swing.JTextField att_age;
    private javax.swing.JComboBox att_cat;
    private javax.swing.JComboBox att_dep;
    private javax.swing.JTextField att_desig;
    private javax.swing.JTable att_edetails;
    private javax.swing.JTextField att_eid_chkinout;
    private javax.swing.JTextField att_em_search;
    private javax.swing.JLabel att_id;
    private javax.swing.JTextField att_mtype;
    private javax.swing.JTextField att_name;
    private javax.swing.JTextField att_nic;
    private javax.swing.JTextField att_other_search;
    private javax.swing.JTable att_otherdetails;
    private javax.swing.JTextField att_pwd;
    private javax.swing.JTextField att_usern;
    private javax.swing.JPanel backPanel;
    private javax.swing.JLabel bck;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel cl;
    private javax.swing.JButton clear_em;
    private javax.swing.JButton delete_em;
    private javax.swing.JButton delete_other;
    private javax.swing.JTextField emp_id;
    private javax.swing.JLabel error_add;
    private javax.swing.JLabel error_age;
    private javax.swing.JLabel error_name;
    private javax.swing.JLabel error_nic;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private static javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JLabel lable_name;
    private javax.swing.JTextArea leave_desc;
    private com.toedter.calendar.JDateChooser leave_from;
    private javax.swing.JRadioButton leave_session1;
    private javax.swing.JRadioButton leave_session2;
    private com.toedter.calendar.JDateChooser leave_to;
    private javax.swing.JComboBox leave_type;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JTextArea lv_reason;
    private javax.swing.JLabel ml;
    private javax.swing.JTable pending_tb;
    private javax.swing.JLabel pl;
    private javax.swing.JLabel rejected;
    private javax.swing.JTable rejected_tb;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JButton search_em;
    private javax.swing.JButton search_other;
    private javax.swing.JLabel sl;
    private javax.swing.JTable table1;
    private javax.swing.JTable table2;
    private javax.swing.JTable table3;
    private javax.swing.JLabel title;
    private javax.swing.JButton update_em;
    private javax.swing.JButton update_other;
    // End of variables declaration//GEN-END:variables
}
