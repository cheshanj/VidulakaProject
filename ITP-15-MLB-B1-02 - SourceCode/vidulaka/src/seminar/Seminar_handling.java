/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seminar;
import Home.dbconnect;
import Home.login;
import static Home.login.uName;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.Statement;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


//import yourfunctionname.yourinitialframe;
/**
 *
 * @author Sumudu Malshan
 */
public class Seminar_handling extends javax.swing.JInternalFrame {

    /**
     * Creates new form internal
     */
    public  int result;
    
    boolean venuephone;
    boolean totalStudents;
    boolean estimatedcost;
    
    boolean lecturerphone;
    boolean lecID;
    boolean lecAge;
    
    int seminarID;
    int SeminarID;
    String seminarName;
    String vehicleNO; 
    
    String path2;
    
    String sentToPrint;
    int eid;
    
    Connection c=dbconnect.connect();
    
          PreparedStatement pst=null;
          ResultSet rst=null;
   
    
    public Seminar_handling() {
        initComponents();
          
          
          
          
          
        result=0;
                ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
               
                txt_name.setText("");
                txt_subject.setText("");
                txt_age.setText("");
                txt_nic.setText("");
                txt_phone.setText("");
                txt_address.setText("");
//                lbl_img.setIcon(null);
                
          
        try {
         String qry1="SELECT empID FROM employee WHERE username='"+login.uName+"' ";
      
         pst = c.prepareStatement(qry1);
        rst = pst.executeQuery();
        while (rst.next())
         eid = rst.getInt("empID");
        
        } catch (Exception e) {
            System.out.println(e);
        }
       
        acceptApproval.setVisible(false);

                
                
                //load data to history  
                loadSeminarHistoty();
                
                //send data to combo boxes
                sendDataToComboBoxes();
                
                sendDataToApprovedTable();
                
                sendDataToRejectedTable();
                
                checkPrintingStatus();
                
                loadcheckForApprovalTable();
                
                
                
                
                
                create.setVisible(true);
                approvals.setVisible(false);
                history.setVisible(false);
                welcome.setVisible(false);
                add_new_lecturer.setVisible(false);
                add_new_venue.setVisible(false);
                request.setVisible(false);
                
         
                 
              
           
    }
    
    public void loadSeminarHistoty(){
         try {
            DefaultTableModel dtm = (DefaultTableModel)jTable1.getModel();
            Connection c= dbconnect.connect();
            Statement s=c.createStatement();
            ResultSet rs=s.executeQuery("select * from seminar");
            
           
                            while(rs.next()){
                                 Vector v=new Vector();
                                v.add(rs.getString("semTitle"));
                                v.add(rs.getString("lecturer"));
                                v.add(rs.getString("venue"));
                                v.add(rs.getString("date"));
                                v.add(rs.getString("totalStudents"));
                                v.add(rs.getString("estimatedCost"));
                                v.add(rs.getString("semStatus"));
                               
                              

                          dtm.addRow(v);
                   }
         
            
                    } catch (Exception e) {
                        e.printStackTrace();
        }
        
    }
    
    public void sendDataToComboBoxes(){
    
    
      try {
            Connection c=dbconnect.connect();
            Statement s=c.createStatement();
            ResultSet rs=s.executeQuery("select * from lecturer");
            Vector v=new Vector();
            
            while(rs.next()){
                v.add(rs.getString("name"));
            
            
            }
          combo_lecturer.setModel(new DefaultComboBoxModel(v));
            
           
        }catch (Exception e) {
            e.printStackTrace();
        }
                   
                   
                      try {
            Connection c=dbconnect.connect();
            Statement s=c.createStatement();
            ResultSet rs=s.executeQuery("select * from venue");
            Vector v=new Vector();
            
            while(rs.next()){
                v.add(rs.getString("place"));
            
            
            }
          combo_venue.setModel(new DefaultComboBoxModel(v));
            
           
        }catch (Exception e) {
            e.printStackTrace();
        }
        
    
    
    
    }
    
    public void  sendDataToApprovedTable(){
    
     try {
            DefaultTableModel dtm = (DefaultTableModel)approvedTable.getModel();
            Connection c= dbconnect.connect();
            Statement s=c.createStatement();
            ResultSet rs=s.executeQuery("select * from seminar where semStatus='approved'" );
            
           
                            while(rs.next()){
                                 Vector v=new Vector();
                                v.add(rs.getString("seminarID"));
                                v.add(rs.getString("semTitle"));
                                v.add(rs.getString("semStatus"));
                                v.add(rs.getString("description"));
                              v.add(rs.getString("sentToPrint"));
                               
                              

                          dtm.addRow(v);
                   }
         
            
                    } catch (Exception e) {
                        e.printStackTrace();
        }
    
    
    
    }
    
     public void  sendDataToRejectedTable(){
    
     try {
            DefaultTableModel dtm = (DefaultTableModel)rejectedTable.getModel();
            Connection c= dbconnect.connect();
            Statement s=c.createStatement();
            ResultSet rs=s.executeQuery("select * from seminar where semStatus='rejected'");
            
           
                            while(rs.next()){
                                 Vector v=new Vector();
                                v.add(rs.getString("seminarID"));
                                v.add(rs.getString("semTitle"));
                                v.add(rs.getString("semStatus"));
                                v.add(rs.getString("description"));
                              
                               
                              

                          dtm.addRow(v);
                   }
         
            
                    } catch (Exception e) {
                        e.printStackTrace();
        }
    
    
    
    }
     
      public void  checkPrintingStatus(){
    
     try {
            DefaultTableModel dtm = (DefaultTableModel)printingStatusTable.getModel();
            Connection c= dbconnect.connect();
            Statement s=c.createStatement();
            ResultSet rs=s.executeQuery("select * from seminar_resource");
            
           
                            while(rs.next()){
                                 Vector v=new Vector();
                                v.add(rs.getString("semID"));
                                v.add(rs.getString("printingStatus"));
                             
                              
                               
                              

                          dtm.addRow(v);
                   }
         
            
                    } catch (Exception e) {
                        e.printStackTrace();
        }
    
    
    
    }
    
      
public void  loadcheckForApprovalTable(){
    
 
  try {
            DefaultTableModel dtm = (DefaultTableModel)checkForApprovalTable.getModel();
            Connection c= dbconnect.connect();
            Statement s=c.createStatement();
            ResultSet rs=s.executeQuery("select * from seminar where semStatus='pending'");
            
           
                            while(rs.next()){
                                 Vector v=new Vector();
                                v.add(rs.getString("seminarID"));
                                v.add(rs.getString("semTitle"));
                                v.add(rs.getString("lecturer"));
                                v.add(rs.getString("venue"));
                                v.add(rs.getString("date"));
                                v.add(rs.getString("totalStudents"));
                                v.add(rs.getString("estimatedCost"));
                               
                              

                          dtm.addRow(v);
                   }
         
            
                    } catch (Exception e) {
                        e.printStackTrace();
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

        jFileChooser1 = new javax.swing.JFileChooser();
        jFileChooser2 = new javax.swing.JFileChooser();
        jFileChooser3 = new javax.swing.JFileChooser();
        backPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        leftPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        rightPanel = new javax.swing.JPanel();
        create = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btn_seminar_sendreport = new javax.swing.JButton();
        btn_seminar_search = new javax.swing.JButton();
        btn_seminar_update = new javax.swing.JButton();
        btn_seminar_delete = new javax.swing.JButton();
        txt_seminar_title = new javax.swing.JTextField();
        combo_lecturer = new javax.swing.JComboBox();
        btn_add_new_lecturer = new javax.swing.JButton();
        btn_add_new_venue = new javax.swing.JButton();
        combo_venue = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        txt_estimated_cost = new javax.swing.JTextField();
        date = new com.toedter.calendar.JDateChooser();
        txt_total_students = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        btn_seminar_clear = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        approvals = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        approvedTable = new javax.swing.JTable();
        jButton10 = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        printingStatusTable = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        rejectedTable = new javax.swing.JTable();
        jButton16 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        history = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton17 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        add_new_lecturer = new javax.swing.JPanel();
        txt_phone = new javax.swing.JTextField();
        txt_nic = new javax.swing.JTextField();
        txt_age = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_img = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btn_addlecturer_upload = new javax.swing.JButton();
        txt_name = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_address = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_lecturer_id = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txt_subject = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        btn_addlecturer_clear = new javax.swing.JButton();
        btn_addlecturer_delete = new javax.swing.JButton();
        btn_addlecturer_update = new javax.swing.JButton();
        btn_addlecturer_search = new javax.swing.JButton();
        btn_addlecturer_save = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        add_new_venue = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txt_place = new javax.swing.JTextField();
        txt_venue_address = new javax.swing.JTextField();
        txt_venue_phone = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        view_all_venues = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btn_addvenue_clear = new javax.swing.JButton();
        btn_addvenue_delete = new javax.swing.JButton();
        btn_addvenue_update = new javax.swing.JButton();
        btn_addvenue_search = new javax.swing.JButton();
        btn_addvenue_save = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        welcome = new javax.swing.JPanel();
        request = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        lblseminarID = new javax.swing.JLabel();
        lblseminarName = new javax.swing.JLabel();
        loadVehicles = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        VehiclesTable = new javax.swing.JTable();
        toprint = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jLabel29 = new javax.swing.JLabel();
        txtotherdetails = new javax.swing.JTextArea();
        txtnoofpapers = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        lbltick = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        vehicles = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        txtvehicleNO = new javax.swing.JTextField();
        date2 = new com.toedter.calendar.JDateChooser();
        jLabel27 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        back = new javax.swing.JLabel();
        acceptApproval = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        checkForApprovalTable = new javax.swing.JTable();
        jLabel41 = new javax.swing.JLabel();
        lblcount = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        lblSeminarID = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        cmbapproval = new javax.swing.JComboBox();
        jButton15 = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel43 = new javax.swing.JLabel();

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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/background.jpg"))); // NOI18N

        leftPanel.setMaximumSize(new java.awt.Dimension(100, 570));
        leftPanel.setMinimumSize(new java.awt.Dimension(100, 570));
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new java.awt.Dimension(100, 570));
        leftPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/seminar_handeling/images/history.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        leftPanel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 100, -1));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/seminar_handeling/images/approvals.png"))); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        leftPanel.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 100, -1));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/tabButton.png"))); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        leftPanel.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        jLabel14.setText("jLabel14");
        leftPanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 570));

        rightPanel.setMaximumSize(new java.awt.Dimension(910, 570));
        rightPanel.setMinimumSize(new java.awt.Dimension(910, 570));
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new java.awt.Dimension(910, 570));
        rightPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        create.setBackground(new java.awt.Color(0, 0, 255));
        create.setFocusCycleRoot(true);
        create.setFocusTraversalPolicyProvider(true);
        create.setInheritsPopupMenu(true);
        create.setMaximumSize(new java.awt.Dimension(915, 570));
        create.setMinimumSize(new java.awt.Dimension(915, 570));
        create.setOpaque(false);
        create.setPreferredSize(new java.awt.Dimension(915, 570));
        create.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Seminar Title");
        create.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, -1, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Venue");
        create.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, -1, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Lecturer");
        create.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, -1, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Date");
        create.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, -1, 20));

        btn_seminar_sendreport.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_seminar_sendreport.setText("Save");
        btn_seminar_sendreport.setPreferredSize(new java.awt.Dimension(101, 23));
        btn_seminar_sendreport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_seminar_sendreportActionPerformed(evt);
            }
        });
        create.add(btn_seminar_sendreport, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 500, -1, -1));

        btn_seminar_search.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_seminar_search.setText("Search");
        btn_seminar_search.setMaximumSize(new java.awt.Dimension(101, 23));
        btn_seminar_search.setMinimumSize(new java.awt.Dimension(101, 23));
        btn_seminar_search.setPreferredSize(new java.awt.Dimension(101, 23));
        btn_seminar_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_seminar_searchActionPerformed(evt);
            }
        });
        create.add(btn_seminar_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 500, -1, -1));

        btn_seminar_update.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_seminar_update.setText("Update");
        btn_seminar_update.setMaximumSize(new java.awt.Dimension(101, 23));
        btn_seminar_update.setMinimumSize(new java.awt.Dimension(101, 23));
        btn_seminar_update.setPreferredSize(new java.awt.Dimension(101, 23));
        btn_seminar_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_seminar_updateActionPerformed(evt);
            }
        });
        create.add(btn_seminar_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 500, -1, -1));

        btn_seminar_delete.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_seminar_delete.setText("Delete");
        btn_seminar_delete.setMaximumSize(new java.awt.Dimension(101, 23));
        btn_seminar_delete.setMinimumSize(new java.awt.Dimension(101, 23));
        btn_seminar_delete.setPreferredSize(new java.awt.Dimension(101, 23));
        btn_seminar_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_seminar_deleteActionPerformed(evt);
            }
        });
        create.add(btn_seminar_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 500, 93, -1));

        txt_seminar_title.setDoubleBuffered(true);
        txt_seminar_title.setDragEnabled(true);
        txt_seminar_title.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_seminar_titleMouseClicked(evt);
            }
        });
        txt_seminar_title.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_seminar_titleActionPerformed(evt);
            }
        });
        create.add(txt_seminar_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, 169, -1));

        combo_lecturer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select lecturer" }));
        combo_lecturer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_lecturerActionPerformed(evt);
            }
        });
        create.add(combo_lecturer, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 169, -1));

        btn_add_new_lecturer.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_add_new_lecturer.setText("Add New Lecturer");
        btn_add_new_lecturer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_new_lecturerActionPerformed(evt);
            }
        });
        create.add(btn_add_new_lecturer, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 120, 140, -1));

        btn_add_new_venue.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_add_new_venue.setText("Add New Venue ");
        btn_add_new_venue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_new_venueActionPerformed(evt);
            }
        });
        create.add(btn_add_new_venue, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 160, 140, -1));

        combo_venue.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select venue" }));
        combo_venue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_venueActionPerformed(evt);
            }
        });
        create.add(combo_venue, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 169, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Total Students");
        create.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, -1, 20));

        txt_estimated_cost.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_estimated_costMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_estimated_costMouseEntered(evt);
            }
        });
        txt_estimated_cost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_estimated_costKeyReleased(evt);
            }
        });
        create.add(txt_estimated_cost, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 370, 169, -1));
        create.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, 170, -1));

        txt_total_students.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_total_studentsMouseClicked(evt);
            }
        });
        txt_total_students.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_total_studentsActionPerformed(evt);
            }
        });
        txt_total_students.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_total_studentsKeyReleased(evt);
            }
        });
        create.add(txt_total_students, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 320, 169, -1));

        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        create.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 200, 140, -1));

        btn_seminar_clear.setText("Clear");
        btn_seminar_clear.setPreferredSize(new java.awt.Dimension(101, 23));
        btn_seminar_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_seminar_clearActionPerformed(evt);
            }
        });
        create.add(btn_seminar_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 500, 93, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Estimated Cost");
        create.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 370, -1, 20));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Seminar Details");
        create.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        jLabel2.setText("jLabel2");
        create.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(create, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        approvals.setBackground(new java.awt.Color(255, 255, 255));
        approvals.setMaximumSize(new java.awt.Dimension(915, 570));
        approvals.setMinimumSize(new java.awt.Dimension(915, 570));
        approvals.setOpaque(false);
        approvals.setPreferredSize(new java.awt.Dimension(915, 570));
        approvals.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        approvedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Seminar ID", "Seminar Name", "Seminar Status", "Description", "Sent to Print"
            }
        ));
        approvedTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                approvedTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(approvedTable);

        approvals.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 670, 140));

        jButton10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton10.setText("Request Materials");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        approvals.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 100, -1, -1));

        printingStatusTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SemianarID", "Printing Status"
            }
        ));
        jScrollPane10.setViewportView(printingStatusTable);

        approvals.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 400, 670, 140));

        rejectedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Seminar ID", "Seminar Name", "Seminar Status", "Description"
            }
        ));
        jScrollPane8.setViewportView(rejectedTable);

        approvals.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 670, 150));

        jButton16.setText("Set Approvals");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        approvals.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 240, 130, -1));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Approved Seminars");
        approvals.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, -1));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Rejected Seminars");
        approvals.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 177, -1, 30));

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Check Printing Status");
        approvals.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 370, -1, -1));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        jLabel34.setText("jLabel34");
        approvals.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 570));

        rightPanel.add(approvals, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        history.setBackground(new java.awt.Color(102, 255, 102));
        history.setMaximumSize(new java.awt.Dimension(915, 570));
        history.setMinimumSize(new java.awt.Dimension(915, 570));
        history.setOpaque(false);
        history.setPreferredSize(new java.awt.Dimension(915, 570));
        history.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SeminarTitle", "Lecturer", "Venue", "Date", "TotalStudents", "EstimatedCost", "Seminar Status"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        history.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 670, 400));

        jButton17.setText("View Report");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        history.add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 110, 100, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Seminar History");
        history.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        jLabel5.setText("jLabel5");
        history.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(history, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        add_new_lecturer.setPreferredSize(new java.awt.Dimension(915, 570));
        add_new_lecturer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_phone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_phoneMouseClicked(evt);
            }
        });
        txt_phone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_phoneActionPerformed(evt);
            }
        });
        txt_phone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_phoneKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_phoneKeyTyped(evt);
            }
        });
        add_new_lecturer.add(txt_phone, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 240, 292, -1));

        txt_nic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_nicMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_nicMouseEntered(evt);
            }
        });
        txt_nic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nicActionPerformed(evt);
            }
        });
        add_new_lecturer.add(txt_nic, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 200, 292, -1));

        txt_age.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_ageMouseClicked(evt);
            }
        });
        txt_age.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ageActionPerformed(evt);
            }
        });
        txt_age.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_ageKeyTyped(evt);
            }
        });
        add_new_lecturer.add(txt_age, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 292, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Age");
        add_new_lecturer.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Address");
        add_new_lecturer.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 280, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("NIC");
        add_new_lecturer.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, -1, -1));

        lbl_img.setForeground(new java.awt.Color(255, 255, 255));
        lbl_img.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/user.jpg"))); // NOI18N
        lbl_img.setText("Image");
        lbl_img.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        add_new_lecturer.add(lbl_img, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 20, 210, 190));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Contact Number");
        add_new_lecturer.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, -1));

        btn_addlecturer_upload.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_addlecturer_upload.setText("Upload Image");
        btn_addlecturer_upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addlecturer_uploadActionPerformed(evt);
            }
        });
        add_new_lecturer.add(btn_addlecturer_upload, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 220, 120, -1));

        txt_name.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_nameMouseClicked(evt);
            }
        });
        txt_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nameActionPerformed(evt);
            }
        });
        txt_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nameKeyTyped(evt);
            }
        });
        add_new_lecturer.add(txt_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 292, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Name");
        add_new_lecturer.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, -1, -1));

        txt_address.setColumns(20);
        txt_address.setRows(5);
        txt_address.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_addressMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(txt_address);

        add_new_lecturer.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 280, 290, 50));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Subject");
        add_new_lecturer.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Lecturer ID");
        add_new_lecturer.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, -1, -1));

        txt_lecturer_id.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_lecturer_idMouseClicked(evt);
            }
        });
        txt_lecturer_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_lecturer_idActionPerformed(evt);
            }
        });
        txt_lecturer_id.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_lecturer_idKeyTyped(evt);
            }
        });
        add_new_lecturer.add(txt_lecturer_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 292, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Add New Lecturer");
        add_new_lecturer.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, -1, -1));

        txt_subject.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_subjectMouseClicked(evt);
            }
        });
        txt_subject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_subjectActionPerformed(evt);
            }
        });
        add_new_lecturer.add(txt_subject, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 292, -1));

        jButton7.setText("Clear Table");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        add_new_lecturer.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 300, 120, -1));

        jButton6.setText("View All Lecturers");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        add_new_lecturer.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 260, -1, -1));

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setForeground(new java.awt.Color(102, 102, 102));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lecturer ID", "Name", "Subject", "Age", "NIC", "Contact Number", "Address"
            }
        ));
        jScrollPane5.setViewportView(jTable3);

        btn_addlecturer_clear.setText("Clear");
        btn_addlecturer_clear.setPreferredSize(new java.awt.Dimension(101, 23));
        btn_addlecturer_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addlecturer_clearActionPerformed(evt);
            }
        });

        btn_addlecturer_delete.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_addlecturer_delete.setText("Delete");
        btn_addlecturer_delete.setMaximumSize(new java.awt.Dimension(101, 23));
        btn_addlecturer_delete.setMinimumSize(new java.awt.Dimension(101, 23));
        btn_addlecturer_delete.setPreferredSize(new java.awt.Dimension(101, 23));
        btn_addlecturer_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addlecturer_deleteActionPerformed(evt);
            }
        });

        btn_addlecturer_update.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_addlecturer_update.setText("Update");
        btn_addlecturer_update.setMaximumSize(new java.awt.Dimension(101, 23));
        btn_addlecturer_update.setMinimumSize(new java.awt.Dimension(101, 23));
        btn_addlecturer_update.setPreferredSize(new java.awt.Dimension(101, 23));
        btn_addlecturer_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addlecturer_updateActionPerformed(evt);
            }
        });

        btn_addlecturer_search.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_addlecturer_search.setText("Search");
        btn_addlecturer_search.setMaximumSize(new java.awt.Dimension(101, 23));
        btn_addlecturer_search.setMinimumSize(new java.awt.Dimension(101, 23));
        btn_addlecturer_search.setPreferredSize(new java.awt.Dimension(101, 23));
        btn_addlecturer_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addlecturer_searchActionPerformed(evt);
            }
        });

        btn_addlecturer_save.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_addlecturer_save.setText("Save");
        btn_addlecturer_save.setMaximumSize(new java.awt.Dimension(101, 23));
        btn_addlecturer_save.setMinimumSize(new java.awt.Dimension(101, 23));
        btn_addlecturer_save.setPreferredSize(new java.awt.Dimension(101, 23));
        btn_addlecturer_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addlecturer_saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(btn_addlecturer_save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btn_addlecturer_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(btn_addlecturer_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(btn_addlecturer_delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btn_addlecturer_clear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_addlecturer_save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_addlecturer_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_addlecturer_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_addlecturer_delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_addlecturer_clear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        add_new_lecturer.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, 830, 220));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        jLabel16.setText("jLabel16");
        add_new_lecturer.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, -1));

        rightPanel.add(add_new_lecturer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 570));

        add_new_venue.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Place");
        add_new_venue.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, -1, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Address");
        add_new_venue.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Contact Number");
        add_new_venue.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, -1, -1));

        txt_place.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_placeActionPerformed(evt);
            }
        });
        add_new_venue.add(txt_place, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 245, -1));
        add_new_venue.add(txt_venue_address, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, 245, 60));

        txt_venue_phone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_venue_phoneMouseClicked(evt);
            }
        });
        txt_venue_phone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_venue_phoneActionPerformed(evt);
            }
        });
        txt_venue_phone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_venue_phoneKeyTyped(evt);
            }
        });
        add_new_venue.add(txt_venue_phone, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 130, 245, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Add New Venue");
        add_new_venue.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, -1, -1));

        view_all_venues.setBackground(new java.awt.Color(102, 102, 102));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Place", "Phone", "Address"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        btn_addvenue_clear.setText("Clear");
        btn_addvenue_clear.setPreferredSize(new java.awt.Dimension(101, 23));
        btn_addvenue_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addvenue_clearActionPerformed(evt);
            }
        });

        btn_addvenue_delete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_addvenue_delete.setText("Delete");
        btn_addvenue_delete.setPreferredSize(new java.awt.Dimension(101, 23));
        btn_addvenue_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addvenue_deleteActionPerformed(evt);
            }
        });

        btn_addvenue_update.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_addvenue_update.setText("Update");
        btn_addvenue_update.setPreferredSize(new java.awt.Dimension(101, 23));
        btn_addvenue_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addvenue_updateActionPerformed(evt);
            }
        });

        btn_addvenue_search.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_addvenue_search.setText("Search");
        btn_addvenue_search.setPreferredSize(new java.awt.Dimension(101, 23));
        btn_addvenue_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addvenue_searchActionPerformed(evt);
            }
        });

        btn_addvenue_save.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_addvenue_save.setText("Save");
        btn_addvenue_save.setMaximumSize(new java.awt.Dimension(101, 23));
        btn_addvenue_save.setMinimumSize(new java.awt.Dimension(101, 23));
        btn_addvenue_save.setPreferredSize(new java.awt.Dimension(101, 23));
        btn_addvenue_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addvenue_saveActionPerformed(evt);
            }
        });

        jButton3.setText("View All Venues");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton8.setText("Clear Table");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout view_all_venuesLayout = new javax.swing.GroupLayout(view_all_venues);
        view_all_venues.setLayout(view_all_venuesLayout);
        view_all_venuesLayout.setHorizontalGroup(
            view_all_venuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_all_venuesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(view_all_venuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(view_all_venuesLayout.createSequentialGroup()
                        .addComponent(btn_addvenue_save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btn_addvenue_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(btn_addvenue_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(btn_addvenue_delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(btn_addvenue_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 683, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(view_all_venuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        view_all_venuesLayout.setVerticalGroup(
            view_all_venuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(view_all_venuesLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(view_all_venuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(view_all_venuesLayout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(17, 17, 17)
                        .addComponent(jButton8)))
                .addGap(18, 18, 18)
                .addGroup(view_all_venuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_addvenue_save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_addvenue_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_addvenue_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_addvenue_delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_addvenue_clear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        add_new_venue.add(view_all_venues, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, 850, 280));

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        add_new_venue.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, -1));

        rightPanel.add(add_new_venue, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        welcome.setBackground(new java.awt.Color(0, 0, 0));
        welcome.setMaximumSize(new java.awt.Dimension(915, 570));
        welcome.setMinimumSize(new java.awt.Dimension(915, 570));
        welcome.setPreferredSize(new java.awt.Dimension(915, 570));
        welcome.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        rightPanel.add(welcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 0, 910, -1));

        request.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("SeminarID : ");
        request.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Seminar Name : ");
        request.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, -1, -1));

        lblseminarID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblseminarID.setForeground(new java.awt.Color(255, 255, 255));
        request.add(lblseminarID, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 40, -1));

        lblseminarName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblseminarName.setForeground(new java.awt.Color(255, 255, 255));
        request.add(lblseminarName, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 50, -1));

        loadVehicles.setBackground(new java.awt.Color(102, 102, 102));

        jButton12.setText("Load Available Vehicles");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        VehiclesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vehicle Number", "Vehicle Type", "Vehicle Model"
            }
        ));
        VehiclesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VehiclesTableMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(VehiclesTable);

        javax.swing.GroupLayout loadVehiclesLayout = new javax.swing.GroupLayout(loadVehicles);
        loadVehicles.setLayout(loadVehiclesLayout);
        loadVehiclesLayout.setHorizontalGroup(
            loadVehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loadVehiclesLayout.createSequentialGroup()
                .addContainerGap(152, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 607, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121))
            .addGroup(loadVehiclesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        loadVehiclesLayout.setVerticalGroup(
            loadVehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loadVehiclesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        request.add(loadVehicles, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 880, 180));

        toprint.setBackground(new java.awt.Color(102, 102, 102));
        toprint.setForeground(new java.awt.Color(255, 255, 255));

        jButton11.setText("Browse");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Draft");

        jScrollPane6.setForeground(new java.awt.Color(255, 255, 255));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Other Details");

        txtotherdetails.setColumns(20);
        txtotherdetails.setRows(5);
        txtotherdetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtotherdetailsMouseClicked(evt);
            }
        });

        txtnoofpapers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtnoofpapersMouseClicked(evt);
            }
        });
        txtnoofpapers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnoofpapersActionPerformed(evt);
            }
        });
        txtnoofpapers.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnoofpapersKeyReleased(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Number Of Papers");

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Send To Print");

        lbltick.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/tick.png"))); // NOI18N
        lbltick.setEnabled(false);

        jButton13.setText("Send To Print");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout toprintLayout = new javax.swing.GroupLayout(toprint);
        toprint.setLayout(toprintLayout);
        toprintLayout.setHorizontalGroup(
            toprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toprintLayout.createSequentialGroup()
                .addGroup(toprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(toprintLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(toprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, toprintLayout.createSequentialGroup()
                                .addGroup(toprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(toprintLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(txtotherdetails, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(toprintLayout.createSequentialGroup()
                                        .addGroup(toprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel31)
                                            .addComponent(jLabel29)
                                            .addComponent(jLabel24))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                                        .addGroup(toprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtnoofpapers, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(toprintLayout.createSequentialGroup()
                                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(lbltick)))))
                                .addGap(3, 3, 3))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, toprintLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, toprintLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton13)))
                .addContainerGap())
        );
        toprintLayout.setVerticalGroup(
            toprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, toprintLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel37)
                .addGap(18, 18, 18)
                .addGroup(toprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnoofpapers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(toprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtotherdetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(toprintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton11)
                    .addComponent(jLabel31)
                    .addComponent(lbltick, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(jButton13)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        request.add(toprint, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 440, 270));

        vehicles.setBackground(new java.awt.Color(102, 102, 102));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Date");

        txtvehicleNO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtvehicleNOMouseClicked(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Vehicle Number");

        jButton14.setText("Reserve Vehicle");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Vehicle Reservation");

        javax.swing.GroupLayout vehiclesLayout = new javax.swing.GroupLayout(vehicles);
        vehicles.setLayout(vehiclesLayout);
        vehiclesLayout.setHorizontalGroup(
            vehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vehiclesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vehiclesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vehiclesLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(txtvehicleNO, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vehiclesLayout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(date2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(vehiclesLayout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        vehiclesLayout.setVerticalGroup(
            vehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vehiclesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addGap(18, 18, 18)
                .addGroup(vehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtvehicleNO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(18, 18, 18)
                .addGroup(vehiclesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(date2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(jButton14)
                .addContainerGap())
        );

        request.add(vehicles, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 70, 420, 270));

        back.setForeground(new java.awt.Color(255, 255, 255));
        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        back.setText("jLabel37");
        request.add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 570));

        rightPanel.add(request, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        acceptApproval.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        checkForApprovalTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Seminar ID", "Seminar name", "Lecturer", "Venue", "Date", "Total Students", "Estimated Cost"
            }
        ));
        checkForApprovalTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkForApprovalTableMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(checkForApprovalTable);

        acceptApproval.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 674, 247));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("No Of Pending Approvals");
        acceptApproval.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 0, -1, 40));

        lblcount.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblcount.setForeground(new java.awt.Color(51, 51, 51));
        lblcount.setText("2");
        acceptApproval.add(lblcount, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 80, 30, 30));

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OrderPlanning/images/calender1.png"))); // NOI18N
        acceptApproval.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 30, 120, 120));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Seminar ID :");

        lblSeminarID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSeminarID.setForeground(new java.awt.Color(255, 255, 255));

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Set Approval");

        cmbapproval.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbapproval.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Approved", "Rejected" }));

        jButton15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton15.setText("Save");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Description");

        txtDescription.setColumns(20);
        txtDescription.setRows(5);
        jScrollPane11.setViewportView(txtDescription);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40)
                    .addComponent(jLabel42)
                    .addComponent(jLabel45))
                .addGap(51, 51, 51)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSeminarID, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbapproval, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(198, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSeminarID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(cmbapproval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addGap(90, 90, 90))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jButton15))
                        .addGap(23, 23, 23))))
        );

        acceptApproval.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 380, 670, 180));
        acceptApproval.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 360, 930, 10));

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        acceptApproval.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(acceptApproval, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout backPanelLayout = new javax.swing.GroupLayout(backPanel);
        backPanel.setLayout(backPanelLayout);
        backPanelLayout.setHorizontalGroup(
            backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backPanelLayout.createSequentialGroup()
                .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
            .addGroup(backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        backPanelLayout.setVerticalGroup(
            backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backPanelLayout.createSequentialGroup()
                .addGroup(backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(backPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        getContentPane().add(backPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setBounds(0, 0, 1024, 570);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        
        create.setVisible(true);
        approvals.setVisible(false);
        history.setVisible(false);
        add_new_lecturer.setVisible(false);
        add_new_venue.setVisible(false);
        acceptApproval.setVisible(false);
        //adding panels
        rightPanel.add(create);
        rightPanel.repaint();
        rightPanel.revalidate();
        
      
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
     
        DefaultTableModel model20 = (DefaultTableModel)this.approvedTable.getModel();
        model20.setRowCount(0);
        sendDataToApprovedTable();
        
           
        DefaultTableModel model21 = (DefaultTableModel)this.checkForApprovalTable.getModel();
        model21.setRowCount(0);
        loadcheckForApprovalTable();
        
        
        DefaultTableModel mode122 = (DefaultTableModel)this.printingStatusTable.getModel();
        mode122.setRowCount(0);
        checkPrintingStatus();
        
         try {

            String query = "select count(seminarID) seminarID from seminar where semStatus='pending' ";
            pst = c.prepareStatement(query);
            rst = pst.executeQuery();

            while (rst.next()) {

                lblcount.setText(rst.getString("seminarID"));

            }

        } catch (Exception e) {
        }

        
        // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        
        create.setVisible(false);
        approvals.setVisible(true);
        history.setVisible(false);
        add_new_lecturer.setVisible(false);
        add_new_venue.setVisible(false);
        acceptApproval.setVisible(false);
        //adding panels
        rightPanel.add(approvals);
        rightPanel.repaint();
        rightPanel.revalidate();
        
        
             
     
     
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        
        create.setVisible(true);
        approvals.setVisible(false);
        history.setVisible(true);
        add_new_lecturer.setVisible(false);
        add_new_venue.setVisible(false);
        acceptApproval.setVisible(false);
        //adding panels
        rightPanel.add(history);
        rightPanel.repaint();
        rightPanel.revalidate();
        
        DefaultTableModel model20 = (DefaultTableModel)this.jTable1.getModel();
        model20.setRowCount(0);
        loadSeminarHistoty();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_addlecturer_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addlecturer_saveActionPerformed
//         TODO add your handling code here:
        
         if(txt_lecturer_id.getText().isEmpty()||txt_name.getText().isEmpty()||txt_subject.getText().isEmpty()||txt_age.getText().isEmpty()||txt_nic.getText().isEmpty()||txt_phone.getText().isEmpty()||txt_address.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "One or more fields are empty");
            if(txt_lecturer_id.getText().isEmpty()){
                txt_lecturer_id.setBackground(Color.PINK);
            }
            if(txt_name.getText().isEmpty()){
                txt_name.setBackground(Color.PINK);
            }
            if(txt_subject.getText().isEmpty()){
                txt_subject.setBackground(Color.PINK);
            }
           if(txt_age.getText().isEmpty()){
                txt_age.setBackground(Color.PINK);
            }
             if(txt_nic.getText().isEmpty()){
                txt_nic.setBackground(Color.PINK);
            }
                else if(validation.val_nic(txt_nic.getText()))
                txt_nic.setBackground(Color.PINK);
             if(txt_phone.getText().isEmpty()){
                txt_phone.setBackground(Color.PINK);
            }
             if(txt_address.getText().isEmpty()){
                txt_address.setBackground(Color.PINK);
            }
        }
        
        
            
         else{    

            File f=jFileChooser1.getSelectedFile();
            String path = f.getAbsolutePath();
            path=path.replace("\\", "/");
              int x=JOptionPane.showConfirmDialog(this, "Do You Want To Save This Record?");
            try {
            if(x==0){
                Connection c=dbconnect.connect();
                Statement s=c.createStatement();
                s.executeUpdate("INSERT INTO lecturer(lecID,name,subject,age,nic,phone,address,image) VALUES('"+txt_lecturer_id.getText()+"','"+txt_name.getText()+"','"+txt_subject.getText()+"','"+txt_age.getText()+"','"+txt_nic.getText()+"','"+txt_phone.getText()+"','"+txt_address.getText()+"','"+path+"')");
             
                txt_lecturer_id.setText("");
                txt_name.setText("");
                txt_subject.setText("");
                txt_age.setText("");
                txt_nic.setText("");
                txt_phone.setText("");
                txt_address.setText("");
                lbl_img.setIcon(null);
                JOptionPane.showMessageDialog(this,  "Insert successfull");

            }

            else if(x==1){
                txt_lecturer_id.setText("");
                txt_name.setText("");
                txt_subject.setText("");
                txt_age.setText("");
                txt_nic.setText("");
                txt_phone.setText("");
                txt_address.setText("");
//                lbl_img.setIcon(null);
            }

            else if(x==2){
                txt_lecturer_id.setText("");
                txt_age.setText("");
                txt_nic.setText("");
                txt_phone.setText("");
                txt_address.setText("");

            }

             } catch (Exception e) {
             e.printStackTrace();

                                }
            }
     
    }//GEN-LAST:event_btn_addlecturer_saveActionPerformed

    private void btn_addlecturer_uploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addlecturer_uploadActionPerformed
        // TODO add your handling code here:
         try {
            jFileChooser1.showOpenDialog(this);
            File f=jFileChooser1.getSelectedFile();
            String path = f.getAbsolutePath();
            path=path.replace("\\", "/");
                File f1=new File(path);

                Image im=ImageIO.read(f1);
                im=im.getScaledInstance(lbl_img.getWidth(), lbl_img.getHeight(),Image.SCALE_SMOOTH);
                lbl_img.setIcon(new ImageIcon(im));

            } catch (Exception e) {
                e.printStackTrace();
            }
    }//GEN-LAST:event_btn_addlecturer_uploadActionPerformed

    private void btn_addlecturer_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addlecturer_deleteActionPerformed
        // TODO add your handling code here:
         try {
            int x=JOptionPane.showConfirmDialog(this,"Do You Want To Delete This Record?"  );
                    
                 if(x==0){

                    Connection c=dbconnect.connect();
                    Statement s=c.createStatement();
                    s.executeUpdate("delete from lecturer where name='"+txt_name.getText()+"'");
                  
                    txt_name.setText("");
                    txt_subject.setText("");
                    txt_age.setText("");
                    txt_nic.setText("");
                    txt_phone.setText("");
                    txt_address.setText(""); 
                    lbl_img.setIcon(null);
                    JOptionPane.showMessageDialog(this, "Delete Successfull");  
                    }  
                 
                 else if(x==1){
                  
                    txt_name.setText("");
                 }
                 else if(x==2){
                
                    txt_name.setText("");
                 }


            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btn_addlecturer_deleteActionPerformed

    private void btn_add_new_venueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_new_venueActionPerformed
        // TODO add your handling code here:
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        
        create.setVisible(false);
        approvals.setVisible(false);
        history.setVisible(false);
        add_new_lecturer.setVisible(false);
        add_new_venue.setVisible(true);
        //adding panels
        rightPanel.add(add_new_venue);
        rightPanel.repaint();
        rightPanel.revalidate();
        
        DefaultTableModel model = (DefaultTableModel)this.jTable3.getModel();
        model.setRowCount(0);
        
        DefaultTableModel mode2 = (DefaultTableModel)this.jTable2.getModel();
        mode2.setRowCount(0);
    }//GEN-LAST:event_btn_add_new_venueActionPerformed

    private void btn_add_new_lecturerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_new_lecturerActionPerformed
        // TODO add your handling code here:
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        
        create.setVisible(false);
        approvals.setVisible(false);
        history.setVisible(false);
        add_new_lecturer.setVisible(true);
        add_new_venue.setVisible(false);
        
        //adding panels
        rightPanel.add(add_new_lecturer);
        rightPanel.repaint();
        rightPanel.revalidate();
    }//GEN-LAST:event_btn_add_new_lecturerActionPerformed

    private void txt_seminar_titleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_seminar_titleActionPerformed
        // TODO add your handling code here:
        combo_lecturer.grabFocus();
    }//GEN-LAST:event_txt_seminar_titleActionPerformed

    private void btn_seminar_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_seminar_searchActionPerformed
        // TODO add your handling code here:
          
        try {
              
            
            Connection c=dbconnect.connect();
            Statement s=c.createStatement();
            ResultSet rs=s.executeQuery("select * from seminar where semTitle='"+txt_seminar_title.getText()+"'");
            
            
            while(rs.next()){
                result=1;
           
            
  
                
                combo_lecturer.setSelectedItem(rs.getString("lecturer"));
                combo_venue.setSelectedItem(rs.getString("venue"));
                date.setDate(rs.getDate("date"));
                txt_total_students.setText(rs.getString("totalStudents"));
                txt_estimated_cost.setText(rs.getString("estimatedCost"));
                
             
            
            }
           
           if (result==0){
          JOptionPane.showMessageDialog(rootPane, "Record Not Found");
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
          
        if (txt_seminar_title.getText().equals(null)){
            System.out.println("ygf");
        }
    }//GEN-LAST:event_btn_seminar_searchActionPerformed

    private void btn_seminar_sendreportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_seminar_sendreportActionPerformed
        if(txt_seminar_title.getText().isEmpty()||txt_total_students.getText().isEmpty()||txt_estimated_cost.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "One or more fields are empty");
            if(txt_seminar_title.getText().isEmpty()){
                txt_seminar_title.setBackground(Color.PINK);
            }
            if(txt_total_students.getText().isEmpty()){
                txt_total_students.setBackground(Color.PINK);
            }
            if(txt_estimated_cost.getText().isEmpty()){
                txt_estimated_cost.setBackground(Color.PINK);
            }
        }
        else{
            if(totalStudents||estimatedcost){
                JOptionPane.showMessageDialog(this, "One or more fileds are invalid");
            }
            else{    

        try {

            
            int x=JOptionPane.showConfirmDialog(create, "Do You Want To Save This Record?" );
            if(x==0){
                
                  java.sql.Date o = null;
                
                            try {
                                o = new java.sql.Date(date.getDate().getTime());


                            } catch (NullPointerException e) {

                               

                            }
                Connection c=dbconnect.connect();
                Statement s=c.createStatement();
                s.executeUpdate("INSERT INTO seminar(semTitle,lecturer,venue,date,totalStudents,estimatedCost,sentToPrint,semStatus) VALUES('"+txt_seminar_title.getText()+"','"+combo_lecturer.getSelectedItem().toString()+"','"+combo_venue.getSelectedItem().toString()+"','"+o+"','"+txt_total_students.getText()+"','"+txt_estimated_cost.getText()+"','no','pending')");

                txt_seminar_title.setText("");
                combo_lecturer.setSelectedItem("Select Lecturer");
                combo_venue.setSelectedItem("Select Venue");
                date.setDate(null);
                txt_total_students.setText("");
                txt_estimated_cost.setText("");

                JOptionPane.showMessageDialog(create, "Save Successfull");
                
              

            }
            if(x==1){
                txt_seminar_title.setText("");
                combo_lecturer.setSelectedItem("Select Lecturer");
                combo_venue.setSelectedItem("Select Venue");
                
                txt_total_students.setText("");
                txt_estimated_cost.setText("");
                

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
             DefaultTableModel model = (DefaultTableModel)this.jTable1.getModel();
             model.setRowCount(0);
             loadSeminarHistoty();
             
            
            
            }
        }    
    }//GEN-LAST:event_btn_seminar_sendreportActionPerformed

  
    private void txt_placeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_placeActionPerformed
        // TODO add your handling code here:
        txt_venue_phone.grabFocus();
    }//GEN-LAST:event_txt_placeActionPerformed

    private void txt_nameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nameKeyTyped
    
         
    }//GEN-LAST:event_txt_nameKeyTyped

    private void txt_ageKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ageKeyTyped
        // TODO add your handling code here:
          Character c=evt.getKeyChar();
        if(!Character.isDigit(c)){
            evt.consume();
            txt_age.setBackground(Color.PINK);
            lecAge=true;
        }
        else{
            txt_total_students.setBackground(Color.WHITE);
            lecAge=false;
        }
         
    }//GEN-LAST:event_txt_ageKeyTyped

    private void txt_phoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_phoneKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)){
            evt.consume();
            txt_phone.setBackground(Color.PINK);
            lecturerphone=true;
        }
       
        
        else{
            txt_phone.setBackground(Color.WHITE);
           lecturerphone=false;
        }

        int i=txt_phone.getText().length();
        if(i!=9){
            txt_phone.setBackground(Color.PINK);
           lecturerphone=true;
        }
        else{
           txt_phone.setBackground(Color.WHITE);
            lecturerphone=false;
        }
         
    }//GEN-LAST:event_txt_phoneKeyTyped

    private void txt_venue_phoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_venue_phoneKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)){
            evt.consume();
            txt_venue_phone.setBackground(Color.PINK);
            venuephone=true;
        }
       
        
        else{
            txt_venue_phone.setBackground(Color.WHITE);
            venuephone=false;
        }

        int i=txt_venue_phone.getText().length();
        if(i!=9){
            txt_venue_phone.setBackground(Color.PINK);
            venuephone=true;
        }
        else{
           txt_venue_phone.setBackground(Color.WHITE);
            venuephone=false;
        }
    }//GEN-LAST:event_txt_venue_phoneKeyTyped

    private void btn_addlecturer_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addlecturer_updateActionPerformed
      
           File f=jFileChooser1.getSelectedFile();
            String path = f.getAbsolutePath();
            path=path.replace("\\", "/");
           
         try {
            int x=JOptionPane.showConfirmDialog(this, "Do You Want To Update This Record?");
              if (x==0){
                    Connection c=dbconnect.connect();
                    Statement s=c.createStatement();
                     
                    s.executeUpdate("UPDATE lecturer SET lecID='"+txt_lecturer_id.getText()+"',name='"+txt_name.getText()+"',subject='"+txt_subject.getText()+"',age='"+txt_age.getText()+"',nic='"+txt_nic.getText()+"',phone='"+txt_phone.getText()+"',address='"+txt_address.getText()+"',image='"+path+"' where lecID='"+txt_lecturer_id.getText()+"'");
                  
                txt_lecturer_id.setText("");
                txt_name.setText("");
                txt_subject.setText("");
                txt_age.setText("");
                txt_nic.setText("");
                txt_phone.setText("");
                txt_address.setText(""); 
                lbl_img.setIcon(null);
                    JOptionPane.showMessageDialog(this, "Update successfull");
                    
                     
                 }
                 else if(x==1){
                     
                txt_lecturer_id.setText("");    
                txt_name.setText("");
                txt_subject.setText("");
                txt_age.setText("");
                txt_nic.setText("");
                txt_phone.setText("");
                txt_address.setText("");
                lbl_img.setIcon(null);
                
                 }
                 else if(x==2){
                     
                txt_lecturer_id.setText("");
                txt_name.setText("");
                txt_subject.setText("");
                txt_age.setText("");
                txt_nic.setText("");
                txt_phone.setText("");
                txt_address.setText("");
                lbl_img.setIcon(null);
                
        }
            
          
        } catch (Exception e) {
         
            e.printStackTrace();
            
        }
        
    }//GEN-LAST:event_btn_addlecturer_updateActionPerformed

    private void btn_addvenue_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addvenue_saveActionPerformed
        // TODO add your handling code here:
         if(txt_place.getText().isEmpty()||txt_venue_phone.getText().isEmpty()||txt_venue_address.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "One or more fields are empty");
            if(txt_place.getText().isEmpty()){
                txt_place.setBackground(Color.PINK);
            }
            if(txt_venue_phone.getText().isEmpty()){
                txt_venue_phone.setBackground(Color.PINK);
            }
            if(txt_venue_address.getText().isEmpty()){
                txt_venue_address.setBackground(Color.PINK);
            }
         }
        else{
        
        
         int x=JOptionPane.showConfirmDialog(this, "Do You Want To Save This Record?");
        try {
            if(x==0){
                Connection c=dbconnect.connect();
                Statement s=c.createStatement();
                s.executeUpdate("INSERT INTO venue(place,phone,address) VALUES('"+txt_place.getText()+"','"+txt_venue_phone.getText()+"','"+txt_venue_address.getText()+"')");
             
                
               
              
                txt_place.setText("");
                txt_venue_phone.setText("");
                txt_venue_address.setText("");
              
           
                JOptionPane.showMessageDialog(this,  "Insert successfull");

            }

            else if(x==1){
              
                txt_place.setText("");
                txt_venue_phone.setText("");
                txt_venue_address.setText("");

            }

            else if(x==2){
              
                txt_place.setText("");
                txt_venue_phone.setText("");
                txt_venue_address.setText("");

            }

        } catch (Exception e) {
         
            e.printStackTrace();

        }
            }  
         
    }//GEN-LAST:event_btn_addvenue_saveActionPerformed

    private void btn_addvenue_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addvenue_searchActionPerformed
        // TODO add your handling code here:
           try {
            Connection c=dbconnect.connect();
            Statement s=c.createStatement();
            ResultSet rs=s.executeQuery("select * from venue where place='"+txt_place.getText()+"'");
            
            

            while(rs.next()){
            
                result=1;
                
                txt_venue_phone.setText(rs.getString("phone"));
                txt_venue_address.setText(rs.getString("address"));
             
            
            }
            
             if (result==0){
          JOptionPane.showMessageDialog(rootPane, "Record Not Found");
             }

        } catch (Exception e) {
            e.printStackTrace();
        }
           
    }//GEN-LAST:event_btn_addvenue_searchActionPerformed

    private void btn_addvenue_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addvenue_updateActionPerformed
        // TODO add your handling code here:
          try {
            int x=JOptionPane.showConfirmDialog(this, "Do You Want To Update This Record?");
              if (x==0){
                    Connection c=dbconnect.connect();
                    Statement s=c.createStatement();
                    s.executeUpdate("UPDATE venue SET place='"+txt_place.getText()+"',phone='"+txt_venue_phone.getText()+"',address='"+txt_venue_address.getText()+"' where place='"+txt_place.getText()+"'");
                   
                txt_place.setText("");
                txt_venue_phone.setText("");
                txt_venue_address.setText("");
              
                    JOptionPane.showMessageDialog(this, "Update successfull");
                    
                     
                 }
                 else if(x==1){
                    
                 txt_place.setText("");
                txt_venue_phone.setText("");
                txt_venue_address.setText("");
                
                 }
                 else if(x==2){
                
                 txt_place.setText("");
                txt_venue_phone.setText("");
                txt_venue_address.setText("");
                
        }
            
          
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        
    }//GEN-LAST:event_btn_addvenue_updateActionPerformed

    private void btn_addvenue_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addvenue_deleteActionPerformed
        // TODO add your handling code here:
         try {
            int x=JOptionPane.showConfirmDialog(this,"Do You Want To Delete This Record?"  );
                    
                 if(x==0){

                    Connection c=dbconnect.connect();
                    Statement s=c.createStatement();
                    s.executeUpdate("delete from venue where place='"+txt_place.getText()+"'");
                   
                    txt_place.setText("");
                    txt_venue_phone.setText("");
                    txt_venue_address.setText("");
                    JOptionPane.showMessageDialog(this, "Delete successfull");
                    }  
                 
                 else if(x==1){
                  
                    txt_place.setText("");
                    txt_venue_phone.setText("");
                    txt_venue_address.setText("");
                 }
                 else if(x==2){
                  
                    txt_place.setText("");
                    txt_venue_phone.setText("");
                    txt_venue_address.setText("");
                 }


            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_addvenue_deleteActionPerformed

    private void combo_lecturerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_lecturerActionPerformed
        // TODO add your handling code here:
        combo_venue.grabFocus();
    }//GEN-LAST:event_combo_lecturerActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
         try {
            Connection c=dbconnect.connect();
            Statement s=c.createStatement();
            ResultSet rs=s.executeQuery("select * from lecturer");
            Vector v=new Vector();
            
            while(rs.next()){
                v.add(rs.getString("name"));
            
            
            }
          combo_lecturer.setModel(new DefaultComboBoxModel(v));
            
           
        }catch (Exception e) {
            e.printStackTrace();
        }
                   
                   
                      try {
            Connection c=dbconnect.connect();
            Statement s=c.createStatement();
            ResultSet rs=s.executeQuery("select * from venue");
            Vector v=new Vector();
            
            while(rs.next()){
                v.add(rs.getString("place"));
            
            
            }
          combo_venue.setModel(new DefaultComboBoxModel(v));
            
           
        }catch (Exception e) {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_addvenue_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addvenue_clearActionPerformed
        // TODO add your handling code here:
          txt_place.setText("");
          txt_venue_phone.setText("");
          txt_venue_address.setText("");
          
          txt_place.setBackground(Color.white);
          txt_venue_address.setBackground(Color.white);
          txt_venue_phone.setBackground(Color.white);
    }//GEN-LAST:event_btn_addvenue_clearActionPerformed

    private void btn_addlecturer_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addlecturer_clearActionPerformed
        // TODO add your handling code here:
          txt_lecturer_id.setText("");
          txt_name.setText("");
          txt_subject.setText("");
          txt_age.setText("");
          txt_nic.setText("");
          txt_phone.setText("");
          txt_address.setText("");
          lbl_img.setIcon(null);
          
           txt_lecturer_id.setBackground(Color.white);
           txt_name.setBackground(Color.white);
           txt_subject.setBackground(Color.white);
           txt_age.setBackground(Color.white);
           txt_nic.setBackground(Color.white);
           txt_phone.setBackground(Color.white);
           txt_address.setBackground(Color.white);
            
    }//GEN-LAST:event_btn_addlecturer_clearActionPerformed

    private void btn_seminar_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_seminar_clearActionPerformed
        // TODO add your handling code here:
        txt_seminar_title.setText("");
        combo_lecturer.setSelectedItem("Select Lecturer");
        combo_venue.setSelectedItem("Select Venue");
        date.setDate(null);
        txt_total_students.setText("");
        txt_estimated_cost.setText("");
        
        txt_seminar_title.setBackground(Color.white);
        txt_total_students.setBackground(Color.white);
        txt_address.setBackground(Color.white);
        txt_estimated_cost.setBackground(Color.white);
        
         
         
         
         
         
         
         
    }//GEN-LAST:event_btn_seminar_clearActionPerformed

    private void btn_seminar_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_seminar_updateActionPerformed
        // TODO add your handling code here:
          try {
            int x=JOptionPane.showConfirmDialog(this, "Do You Want To Update This Record?");
              if (x==0){
                  
                   java.sql.Date o = null;
                
                            try {
                                o = new java.sql.Date(date.getDate().getTime());


                            } catch (NullPointerException e) {

                            }
                    Connection c=dbconnect.connect();
                    Statement s=c.createStatement();
                    s.executeUpdate("UPDATE seminar SET semTitle='"+txt_seminar_title.getText()+"',lecturer='"+combo_lecturer.getSelectedItem().toString()+"',venue='"+combo_venue.getSelectedItem().toString()+"',date='"+o+"',totalStudents='"+txt_total_students.getText()+"'WHERE semTitle='"+txt_seminar_title.getText()+"'");
                   
                txt_seminar_title.setText("");
                combo_lecturer.setSelectedItem("Select Lecturer");
                combo_venue.setSelectedItem("Select Venue");
                date.setDate(null);
                txt_total_students.setText("");
                txt_estimated_cost.setText("");
                    JOptionPane.showMessageDialog(this, "Update successfull");
                    
                     
                 }
                 else if(x==1){
                    
                txt_seminar_title.setText("");
                combo_lecturer.setSelectedItem("Select Lecturer");
                combo_venue.setSelectedItem("Select Venue");
                date.setDate(null);
                txt_total_students.setText("");
                txt_estimated_cost.setText("");
                
                 }
                 else if(x==2){
                
                txt_seminar_title.setText("");
                combo_lecturer.setSelectedItem("Select Lecturer");
                combo_venue.setSelectedItem("Select Venue");
                date.setDate(null);
                txt_total_students.setText("");
                txt_estimated_cost.setText("");
        }
            
          
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        
    }//GEN-LAST:event_btn_seminar_updateActionPerformed

    private void btn_seminar_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_seminar_deleteActionPerformed
        // TODO add your handling code here:
        try {
            int x=JOptionPane.showConfirmDialog(this,"Do You Want To Delete This Record?"  );
                    
                 if(x==0){

                    Connection c=dbconnect.connect();
                    Statement s=c.createStatement();
                    s.executeUpdate("delete from seminar where semTitle='"+txt_seminar_title.getText()+"'");
                   
                    txt_seminar_title.setText("");
                    combo_lecturer.setSelectedItem("Select Lecturer");
                    combo_venue.setSelectedItem("Select Venue");
                    date.setDate(null);
                    txt_total_students.setText("");
                    txt_estimated_cost.setText("");
                    JOptionPane.showMessageDialog(this, "Delete successfull");
                    }  
                 
                 else if(x==1){
                  
                    txt_seminar_title.setText("");
                    combo_lecturer.setSelectedItem("Select Lecturer");
                    combo_venue.setSelectedItem("Select Venue");
                    date.setDate(null);
                    txt_total_students.setText("");
                    txt_estimated_cost.setText("");
                 }
                 else if(x==2){
                  
                    txt_seminar_title.setText("");
                    combo_lecturer.setSelectedItem("Select Lecturer");
                    combo_venue.setSelectedItem("Select Venue");
                    date.setDate(null);
                    txt_total_students.setText("");
                    txt_estimated_cost.setText("");
                 }


            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_seminar_deleteActionPerformed

    private void btn_addlecturer_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addlecturer_searchActionPerformed
        // TODO add your handling code here:
        
              
        try {
                
            
            Connection c=dbconnect.connect();
            Statement s=c.createStatement();
            ResultSet rs=s.executeQuery("select * from lecturer where lecID='"+txt_lecturer_id.getText()+"'");
            
            

            while(rs.next()){
                result=1;
           
                txt_name.setText(rs.getString("name"));
                txt_subject.setText(rs.getString("subject"));
                txt_age.setText(rs.getString("age"));
                txt_nic.setText(rs.getString("nic"));
                txt_phone.setText(rs.getString("phone"));
                txt_address.setText(rs.getString("address"));
                lbl_img.setText(rs.getString("image"));
  
                
                
             
            
            }

                    if (result==0){
                    JOptionPane.showMessageDialog(this, "Record Not Found");
           }
           
                    File f1=new File(lbl_img.getText().toString());

                    Image im=ImageIO.read(f1);
                    im=im.getScaledInstance(lbl_img.getWidth(), lbl_img.getHeight(),Image.SCALE_SMOOTH);
                    lbl_img.setIcon(new ImageIcon(im));

        } catch (Exception e) {
            e.printStackTrace();
        }
          
       
    }//GEN-LAST:event_btn_addlecturer_searchActionPerformed

    private void txt_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nameActionPerformed
        // TODO add your handling code here:
        txt_subject.grabFocus();
    }//GEN-LAST:event_txt_nameActionPerformed

    private void txt_subjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_subjectActionPerformed
        // TODO add your handling code here:
        txt_age.grabFocus();
    }//GEN-LAST:event_txt_subjectActionPerformed

    private void txt_ageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ageActionPerformed
        // TODO add your handling code here:
        txt_nic.grabFocus();
    }//GEN-LAST:event_txt_ageActionPerformed

    private void txt_nicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nicActionPerformed
        // TODO add your handling code here:
        txt_phone.grabFocus();
    }//GEN-LAST:event_txt_nicActionPerformed

    private void txt_phoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_phoneActionPerformed
        // TODO add your handling code here:
        txt_address.grabFocus();
    }//GEN-LAST:event_txt_phoneActionPerformed

    private void combo_venueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_venueActionPerformed
        // TODO add your handling code here:
        date.grabFocus();
    }//GEN-LAST:event_combo_venueActionPerformed

    private void txt_total_studentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_total_studentsActionPerformed
        // TODO add your handling code here:
        txt_estimated_cost.grabFocus();
    }//GEN-LAST:event_txt_total_studentsActionPerformed

    private void txt_venue_phoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_venue_phoneActionPerformed
        // TODO add your handling code here:
        txt_venue_address.grabFocus();
    }//GEN-LAST:event_txt_venue_phoneActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)this.jTable2.getModel();
        model.setRowCount(0);
        
        
           try {
            DefaultTableModel dtm = (DefaultTableModel)jTable2.getModel();
            Connection c= dbconnect.connect();
            Statement s=c.createStatement();
            ResultSet rs=s.executeQuery("select * from venue");
            
           
                            while(rs.next()){
                                 Vector v=new Vector();
                                v.add(rs.getString("place"));
                                v.add(rs.getString("phone"));
                                v.add(rs.getString("address"));
               
                          dtm.addRow(v);
                   }
         
            
                    } catch (Exception e) {
                        e.printStackTrace();
        }
        
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)this.jTable3.getModel();
        model.setRowCount(0);
      
        
        
        try {
            DefaultTableModel dtm = (DefaultTableModel)jTable3.getModel();
            Connection c= dbconnect.connect();
            Statement s=c.createStatement();
            ResultSet rs=s.executeQuery("select * from lecturer");
            
           
                            while(rs.next()){
                                 Vector v=new Vector();
                                v.add(rs.getString("lecID"));
                                v.add(rs.getString("name"));
                                v.add(rs.getString("subject"));
                                v.add(rs.getString("age"));
                                v.add(rs.getString("nic"));
                                v.add(rs.getString("phone"));
                                v.add(rs.getString("address"));
                               
                              

                          dtm.addRow(v);
                   }
         
            
                    } catch (Exception e) {
                        e.printStackTrace();
        }
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txt_lecturer_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_lecturer_idActionPerformed
        // TODO add your handling code here:
        txt_name.grabFocus();
    }//GEN-LAST:event_txt_lecturer_idActionPerformed

    private void txt_lecturer_idKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_lecturer_idKeyTyped
        // TODO add your handling code here:
                Character c=evt.getKeyChar();
        if(!Character.isDigit(c)){
            evt.consume();
            txt_lecturer_id.setBackground(Color.PINK);
            lecID=true;
        }
        else{
            txt_lecturer_id.setBackground(Color.WHITE);
            lecID=false;
        }
    }//GEN-LAST:event_txt_lecturer_idKeyTyped

    private void txt_total_studentsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_total_studentsKeyReleased
        Character c=evt.getKeyChar();
        if(!Character.isDigit(c)){
            evt.consume();
            txt_total_students.setBackground(Color.PINK);
            totalStudents=true;
        }
        else{
            txt_total_students.setBackground(Color.WHITE);
            totalStudents=false;
        }
    }//GEN-LAST:event_txt_total_studentsKeyReleased

    private void txt_estimated_costKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_estimated_costKeyReleased
        String decimalPattern = "^[\\d.]+$";
        String number=txt_estimated_cost.getText();
        boolean match = Pattern.matches(decimalPattern, number);
        if(match==false)
        {
            //Toolkit.getDefaultToolkit().beep();
            txt_estimated_cost.setBackground(Color.PINK);
            estimatedcost=true;
        }

        else
        {
            txt_estimated_cost.setBackground(Color.WHITE);
            estimatedcost=false;
            //lblmfErrorBasicSal.setText("");;
        }
    }//GEN-LAST:event_txt_estimated_costKeyReleased

    private void txt_seminar_titleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_seminar_titleMouseClicked
        txt_seminar_title.setBackground(Color.WHITE);
    }//GEN-LAST:event_txt_seminar_titleMouseClicked

    private void txt_total_studentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_total_studentsMouseClicked
        txt_total_students.setBackground(Color.WHITE);
    }//GEN-LAST:event_txt_total_studentsMouseClicked

    private void txt_estimated_costMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_estimated_costMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_estimated_costMouseEntered

    private void txt_estimated_costMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_estimated_costMouseClicked
        txt_estimated_cost.setBackground(Color.WHITE);
    }//GEN-LAST:event_txt_estimated_costMouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)this.jTable3.getModel();
        model.setRowCount(0);
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
         DefaultTableModel model = (DefaultTableModel)this.jTable2.getModel();
        model.setRowCount(0);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void txt_phoneKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_phoneKeyReleased
        // TODO add your handling code here:
         
    }//GEN-LAST:event_txt_phoneKeyReleased

    private void txt_nicMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_nicMouseClicked
         txt_nic.setBackground(Color.white);  
    }//GEN-LAST:event_txt_nicMouseClicked

    private void txt_lecturer_idMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_lecturer_idMouseClicked
          txt_lecturer_id.setBackground(Color.white);
    }//GEN-LAST:event_txt_lecturer_idMouseClicked

    private void txt_nameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_nameMouseClicked
        // TODO add your handling code here:
           txt_name.setBackground(Color.white);
    }//GEN-LAST:event_txt_nameMouseClicked

    private void txt_subjectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_subjectMouseClicked
        // TODO add your handling code here:
           txt_subject.setBackground(Color.white);
    }//GEN-LAST:event_txt_subjectMouseClicked

    private void txt_ageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_ageMouseClicked
        // TODO add your handling code here:
           txt_age.setBackground(Color.white);
    }//GEN-LAST:event_txt_ageMouseClicked

    private void txt_phoneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_phoneMouseClicked
        // TODO add your handling code here:
           txt_phone.setBackground(Color.white);
    }//GEN-LAST:event_txt_phoneMouseClicked

    private void txt_addressMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_addressMouseClicked
        // TODO add your handling code here:
           txt_address.setBackground(Color.white);
    }//GEN-LAST:event_txt_addressMouseClicked

    private void txt_venue_phoneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_venue_phoneMouseClicked
        // TODO add your handling code here:
          txt_venue_phone.setBackground(Color.white);
    }//GEN-LAST:event_txt_venue_phoneMouseClicked

    private void txt_nicMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_nicMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nicMouseEntered

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
    
              
                 if(sentToPrint.equals("yes")){
                 JOptionPane.showMessageDialog(this, "Already Entered");

    
                }
                 else if (sentToPrint.equals("no")){

                rightPanel.removeAll();
                rightPanel.repaint();
                rightPanel.revalidate();

                create.setVisible(false);
                approvals.setVisible(false);
                history.setVisible(false);
                add_new_lecturer.setVisible(false);
                add_new_venue.setVisible(false);
                request.setVisible(true);
                //adding panels
                rightPanel.add(request);
                rightPanel.repaint();
                rightPanel.revalidate();
                
                
                        }
         
                

    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        if(txtvehicleNO.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "One or more fields are empty");
            if(txtvehicleNO.getText().isEmpty()){
                txtvehicleNO.setBackground(Color.PINK);
            }
            
         
        }
       
        else{    
            
                try {

                      
                    int x=JOptionPane.showConfirmDialog(this, "Do You Want To Save This Record?" );
                    if(x==0){

                          java.sql.Date d = null;

                                    try {
                                        d = new java.sql.Date(date2.getDate().getTime());
                                         


                                    } catch (NullPointerException e) {

                                    }
                                    
                        Connection c=dbconnect.connect();
                        Statement s=c.createStatement();
                        s.executeUpdate("INSERT INTO sem_vehicle_reservation(seminarID,sem_vehicle_no,reservation_date) VALUES('"+lblseminarID.getText()+"','"+txtvehicleNO.getText()+"','"+d+"')");

                        txtvehicleNO.setText("");
                        date2.setDate(null);
                       

                        JOptionPane.showMessageDialog(this, "Save Successfull");



                    }
                    if(x==1){
                        txtvehicleNO.setText("");
                        date2.setDate(null);



                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
            
                                                          

    }//GEN-LAST:event_jButton14ActionPerformed

    private void approvedTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_approvedTableMouseClicked
       
        int row = approvedTable.getSelectedRow();
        seminarID = Integer.parseInt(approvedTable.getValueAt(row, 0).toString());
        String seminarName = approvedTable.getValueAt(row, 1).toString();
        sentToPrint=approvedTable.getValueAt(row, 4).toString();
        
        lblseminarID.setText(String.valueOf(seminarID));
        lblseminarName.setText(seminarName);
      
    }//GEN-LAST:event_approvedTableMouseClicked

    private void txtnoofpapersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnoofpapersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnoofpapersActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
       
         try {
            DefaultTableModel dtm = (DefaultTableModel)VehiclesTable.getModel();
            Connection c= dbconnect.connect();
            Statement s=c.createStatement();
            ResultSet rs=s.executeQuery("select * from vehicle where availability='Available'");
            
           
                            while(rs.next()){
                                 Vector v=new Vector();
                                v.add(rs.getString("vehicleNO"));
                                v.add(rs.getString("vType"));
                                v.add(rs.getString("vModel"));
                              
                               
                              

                          dtm.addRow(v);
                   }
         
            
                    } catch (Exception e) {
                        e.printStackTrace();
        }
        
    }//GEN-LAST:event_jButton12ActionPerformed

    private void VehiclesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VehiclesTableMouseClicked
       
         int row = VehiclesTable.getSelectedRow();
      String vehicleNO  = VehiclesTable.getValueAt(row, 0).toString();
       

        txtvehicleNO.setText(String.valueOf(vehicleNO));
        
    }//GEN-LAST:event_VehiclesTableMouseClicked

    private void txtvehicleNOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtvehicleNOMouseClicked
        txtvehicleNO.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtvehicleNOMouseClicked

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
    if(txtnoofpapers.getText().isEmpty()||txtotherdetails.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "One or more fields are empty");
            if(txtnoofpapers.getText().isEmpty()){
                txtnoofpapers.setBackground(Color.PINK);
            }
            if(txtotherdetails.getText().isEmpty()){
                txtotherdetails.setBackground(Color.PINK);
            }
         }
        else{
        
          File f=jFileChooser3.getSelectedFile();
            String path2 = f.getAbsolutePath();
           
            
//            path2=path2.replace("\\", "/");
            
         int x=JOptionPane.showConfirmDialog(this, "Do You Want To Save This Record?");
          
        try {
            if(x==0){
                Connection c=dbconnect.connect();
                Statement s=c.createStatement();
               
                s.executeUpdate("INSERT INTO seminar_resource(semID,noOfPapers,sem_other,sem_draft) VALUES('"+lblseminarID.getText()+"','"+txtnoofpapers.getText()+"','"+txtotherdetails.getText()+"','"+path2+"')");
          
                txtnoofpapers.setText("");
                txtotherdetails.setText("");
                lbltick.setEnabled(false);
              
                        try{
                            Connection c1=dbconnect.connect();
                            Statement s1=c1.createStatement();
                            s1.executeUpdate("update seminar set sentToPrint='yes' where seminarID='"+lblseminarID.getText()+"'");
                        }
                        catch(Exception e){

                        }

                JOptionPane.showMessageDialog(this,  "Insert successfull");
                //sendDataToApprovedTable();
            }

            else if(x==1){
              
                txtnoofpapers.setText("");
                txtotherdetails.setText("");
              

            }

            else if(x==2){
              
                txtnoofpapers.setText("");
                txtotherdetails.setText("");
              

            }

        } catch (Exception e) {
         
            e.printStackTrace();

        }
        
    
            }  
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
         try {
            jFileChooser3.showOpenDialog(this);
            File f=jFileChooser3.getSelectedFile();
            String path2 = f.getAbsolutePath();
            
            if(path2 != null ){}
            lbltick.setEnabled(true);
          
               

                

            } catch (Exception e) {
                e.printStackTrace();
            }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void checkForApprovalTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkForApprovalTableMouseClicked
           int row = checkForApprovalTable.getSelectedRow();
        SeminarID = Integer.parseInt(checkForApprovalTable.getValueAt(row, 0).toString());
       
        lblSeminarID.setText(String.valueOf(SeminarID));
      
    }//GEN-LAST:event_checkForApprovalTableMouseClicked

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed

        if(eid == 5){
              DefaultTableModel model1 = (DefaultTableModel)this.checkForApprovalTable.getModel();
                model1.setRowCount(0);
                loadcheckForApprovalTable(); 
                rightPanel.removeAll();
                rightPanel.repaint();
                rightPanel.revalidate();

                create.setVisible(false);
                approvals.setVisible(false);
                history.setVisible(false);
                add_new_lecturer.setVisible(false);
                add_new_venue.setVisible(false);
                request.setVisible(false);
                acceptApproval.setVisible(true);
                //adding panels
                rightPanel.add(acceptApproval);
                rightPanel.repaint();
                rightPanel.revalidate();
               
                
                 try {

            String query = "select count(seminarID) seminarID from seminar where semStatus='pending' ";
            pst = c.prepareStatement(query);
            rst = pst.executeQuery();

            while (rst.next()) {

                lblcount.setText(rst.getString("seminarID"));

            }

        } catch (Exception e) {
        }
//                DefaultTableModel model2 = (DefaultTableModel)this.checkForApprovalTable.getModel();
//                model2.setRowCount(0);
//                loadcheckForApprovalTable();

        }
        
        else{
        JOptionPane.showMessageDialog(this, "You Are Not Autherized");
        }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
          int x=JOptionPane.showConfirmDialog(this, "Do You Want To Save This Record?");
          
        try {
            if(x==0){
                Connection c=dbconnect.connect();
                Statement s=c.createStatement();
                s.executeUpdate("UPDATE seminar SET semStatus='"+cmbapproval.getSelectedItem().toString()+"', description='"+txtDescription.getText()+"' where seminarID='"+lblSeminarID.getText()+"'");
          
               lblSeminarID.setText("");
               txtDescription.setText("");
                       
               
                JOptionPane.showMessageDialog(this,  "Insert successfull");
               
            }

            else if(x==1){
              lblSeminarID.setText("");
            }

            else if(x==2){
                lblSeminarID.setText("");
            }

        } catch (Exception e) {
         
            e.printStackTrace();

        }
        DefaultTableModel model1 = (DefaultTableModel)this.checkForApprovalTable.getModel();
        model1.setRowCount(0);
        loadcheckForApprovalTable();
        
        DefaultTableModel model2 = (DefaultTableModel)this.approvedTable.getModel();
        model2.setRowCount(0);
        sendDataToApprovedTable();
        
        DefaultTableModel model3 = (DefaultTableModel)this.rejectedTable.getModel();
        model3.setRowCount(0);
        sendDataToRejectedTable();
        
        try {

            String query = "select count(seminarID) seminarID from seminar where semStatus='pending' ";
            pst = c.prepareStatement(query);
            rst = pst.executeQuery();

            while (rst.next()) {

                lblcount.setText(rst.getString("seminarID"));

            }

        } catch (Exception e) {
        }

      
    
        
        
       
              
    }//GEN-LAST:event_jButton15ActionPerformed

    private void txtnoofpapersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtnoofpapersMouseClicked
          txtnoofpapers.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtnoofpapersMouseClicked

    private void txtotherdetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtotherdetailsMouseClicked
       txtotherdetails.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtotherdetailsMouseClicked

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        ReportView r = new ReportView("E:\\7\\sumudu\\vidulaka\\src\\seminar\\seminar_history.jasper");
        r.setVisible(true);
        
        r.setVisible(true);
        r.toFront();
        r.repaint();
        r.setAlwaysOnTop(true);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void txtnoofpapersKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnoofpapersKeyReleased
        Character c=evt.getKeyChar();
        if(!Character.isDigit(c)){
            evt.consume();
            txtnoofpapers.setBackground(Color.PINK);
            
        }
        else{
           txtnoofpapers.setBackground(Color.WHITE);
            
        }
    }//GEN-LAST:event_txtnoofpapersKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable VehiclesTable;
    private javax.swing.JPanel acceptApproval;
    private javax.swing.JPanel add_new_lecturer;
    private javax.swing.JPanel add_new_venue;
    private javax.swing.JPanel approvals;
    private javax.swing.JTable approvedTable;
    private javax.swing.JLabel back;
    private javax.swing.JPanel backPanel;
    private javax.swing.JButton btn_add_new_lecturer;
    private javax.swing.JButton btn_add_new_venue;
    private javax.swing.JButton btn_addlecturer_clear;
    private javax.swing.JButton btn_addlecturer_delete;
    private javax.swing.JButton btn_addlecturer_save;
    private javax.swing.JButton btn_addlecturer_search;
    private javax.swing.JButton btn_addlecturer_update;
    private javax.swing.JButton btn_addlecturer_upload;
    private javax.swing.JButton btn_addvenue_clear;
    private javax.swing.JButton btn_addvenue_delete;
    private javax.swing.JButton btn_addvenue_save;
    private javax.swing.JButton btn_addvenue_search;
    private javax.swing.JButton btn_addvenue_update;
    private javax.swing.JButton btn_seminar_clear;
    private javax.swing.JButton btn_seminar_delete;
    private javax.swing.JButton btn_seminar_search;
    private javax.swing.JButton btn_seminar_sendreport;
    private javax.swing.JButton btn_seminar_update;
    private javax.swing.JTable checkForApprovalTable;
    private javax.swing.JComboBox cmbapproval;
    private javax.swing.JComboBox combo_lecturer;
    private javax.swing.JComboBox combo_venue;
    private javax.swing.JPanel create;
    private com.toedter.calendar.JDateChooser date;
    private com.toedter.calendar.JDateChooser date2;
    private javax.swing.JPanel history;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFileChooser jFileChooser2;
    private javax.swing.JFileChooser jFileChooser3;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JLabel lblSeminarID;
    private javax.swing.JLabel lbl_img;
    private javax.swing.JLabel lblcount;
    private javax.swing.JLabel lblseminarID;
    private javax.swing.JLabel lblseminarName;
    private javax.swing.JLabel lbltick;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel loadVehicles;
    private javax.swing.JTable printingStatusTable;
    private javax.swing.JTable rejectedTable;
    private javax.swing.JPanel request;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JPanel toprint;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextArea txt_address;
    private javax.swing.JTextField txt_age;
    private javax.swing.JTextField txt_estimated_cost;
    private javax.swing.JTextField txt_lecturer_id;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_nic;
    private javax.swing.JTextField txt_phone;
    private javax.swing.JTextField txt_place;
    private javax.swing.JTextField txt_seminar_title;
    private javax.swing.JTextField txt_subject;
    private javax.swing.JTextField txt_total_students;
    private javax.swing.JTextField txt_venue_address;
    private javax.swing.JTextField txt_venue_phone;
    private javax.swing.JTextField txtnoofpapers;
    private javax.swing.JTextArea txtotherdetails;
    private javax.swing.JTextField txtvehicleNO;
    private javax.swing.JPanel vehicles;
    private javax.swing.JPanel view_all_venues;
    private javax.swing.JPanel welcome;
    // End of variables declaration//GEN-END:variables
}
