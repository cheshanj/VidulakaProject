/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termTestPaperHandling;

import Home.dbconnect;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author User
 */
public class NewFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form NewFrame
     */
    
    Connection conn=null;
        PreparedStatement pst= null;
        ResultSet rs=null;
        ResultSet rs1=null;
        int updtTenderID;
        int updtTenderHistoryID;
        int PaperTenderId;
        String paperFilename="no";
        int ProvinceId;
        String subject;
        int grade;
        int BillTenderID;
        int BillProvinceID;
        int BillTerm;
        String BillProvinceName;
        int passPrId;
        
        boolean schoolname;
        boolean schoolAddress;
        boolean schoolPrinciple;
        boolean schoolPhone;
        boolean schoolEmail;
        boolean tenderIncome;
        boolean tenderDirector;
        boolean numPapers;
    public NewFrame() {
        initComponents();
        
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
           conn=dbconnect.connect();
           
           
           loadNewTenderProvinceCombo();
           loadTenderTable();
           loadTenderHistoryTable();
           loadschoolProvinceCombo();
           loadTenderPaperTable();
           loadPaperTable();
           loadBillTenderTable();
           loadschoolProvince2Combo();
           
           pnlNewTender.setVisible(false);
           pnlSchools.setVisible(false);
           pnlTenderPapers.setVisible(false);
           pnlTenders.setVisible(true);
           pnlInsertPaperCounts.setVisible(false);
           pnlParcellBills.setVisible(false);
           
           term_lblTick.setVisible(false);
           
           //term_lblErrorPrinciple.setVisible(false);
    }

    public void loadNewTenderProvinceCombo()
    {            
          try {
                String qr= " SELECT * FROM province "  ;
                pst=conn.prepareStatement(qr);
                rs=pst.executeQuery();
            while(rs.next())
            {
       
            term_cmbProvince.addItem(rs.getString("name"));
                
            }
            }catch (SQLException ex) {
                Logger.getLogger(TermTestGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
       
    }
      
    public void loadTenderTable()
    { 
     
        try 
        {
            String tndtblqry= " (SELECT * FROM tender where printingStatus='not complete' AND printingStatus='processing') UNION (SELECT * FROM tender where distStatus='not complete')";
            pst=conn.prepareStatement(tndtblqry);
            rs=pst.executeQuery();
            term_tblCurrentTenders.setModel(DbUtils.resultSetToTableModel(rs));
            
        }
        catch (Exception e)
        { 
           System.out.println(e);  
        }
    }
    
    public void loadTenderHistoryTable()
    { 
     
        try 
        {
            String tndtblhstqry= " SELECT * FROM tender where printingStatus='complete' AND distStatus='complete' ";
            pst=conn.prepareStatement(tndtblhstqry);
            rs=pst.executeQuery();
            term_tblFinishedTenders.setModel(DbUtils.resultSetToTableModel(rs));
            
        }
        catch (Exception e)
        { 
           System.out.println(e);  
        }
    }
    
    public void loadschoolProvinceCombo()
    {            
          try {
                String qr= " SELECT * FROM province"  ;
                pst=conn.prepareStatement(qr);
                rs=pst.executeQuery();
            while(rs.next())
            {
                System.out.println(rs.getString("name"));
            term_cmbPrSelect.addItem(rs.getString("name"));
                
            }
            }catch (SQLException ex) {
                Logger.getLogger(TermTestGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
       
    }
    public void loadschoolProvince2Combo()
    {            
          try {
                String qr= " SELECT * FROM province"  ;
                pst=conn.prepareStatement(qr);
                rs=pst.executeQuery();
            while(rs.next())
            {
                System.out.println(rs.getString("name"));
            term_cmbPrSchool.addItem(rs.getString("name"));
                
            }
            }catch (SQLException ex) {
                Logger.getLogger(TermTestGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
       
    }
    
    public void loadDivisionCombo()
    {            
          try {
                String qr= " SELECT * FROM school where provinceID='"+passPrId+"'";
                System.out.println(qr);
                pst=conn.prepareStatement(qr);
                rs=pst.executeQuery();
            while(rs.next())
            {
       
            term_cmbDvSelect.addItem(rs.getString("division"));
                
            }
            }catch (SQLException ex) {
                Logger.getLogger(TermTestGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
       
    }
    public void loadDivision2Combo()
    {            
          try {
                String qr= " SELECT * FROM school where provinceID='"+passPrId+"'";
                System.out.println(qr);
                pst=conn.prepareStatement(qr);
                rs=pst.executeQuery();
            while(rs.next())
            {
       
            term_cmbDvSchool.addItem(rs.getString("division"));
                
            }
            }catch (SQLException ex) {
                Logger.getLogger(TermTestGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
       
    }
    
    public void loadSchoolCombo(){
        try {
                String qr= " SELECT name from school where provinceID='"+ProvinceId+"'";
                System.out.println(qr);
                pst=conn.prepareStatement(qr);
                rs=pst.executeQuery();
            while(rs.next())
            {
       
            term_cmbSelectSchool.addItem(rs.getString("name"));
                
            }
            }catch (SQLException ex) {
                Logger.getLogger(TermTestGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void loadTenderPaperTable()
    {        
    try 
        {
            String tndtblqry= "SELECT t.tenderID,p.name AS Province ,t.term,t.dueDate,t.fromGrade,t.toGrade from tender t, province p where t.provinceID=p.prID AND t.tenderID in (SELECT tenderID FROM tender where printingStatus='not complete' OR printingStatus='processing')";
            pst=conn.prepareStatement(tndtblqry);
            rs=pst.executeQuery();
                        
term_tblTenderPaper.setModel(DbUtils.resultSetToTableModel(rs));
            
        }
        catch (Exception e)
        { 
           System.out.println(e);  
        }
    }
    
    public void loadPaperTable(){
        try{
            String pprTblqry="Select p.tenderID,p.subject,p.grade,d.document from paper p,draft d where p.paperID=d.paperID ";
            pst=conn.prepareStatement(pprTblqry);
            rs=pst.executeQuery();
            term_tblPapers.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e){
        }
    }
    
     public void loadPaperCountTable()
    {        
    try 
        {
            String tndtblqry= "SELECT p.tenderID as Tender,s.name as School,p.grade as Grade,p.subject as Subject,p.amount as NumofPapers from school_paper_count p,school s where s.schoolID=p.schoolID";
            pst=conn.prepareStatement(tndtblqry);
            rs=pst.executeQuery();
                        
            term_tblPaperCount.setModel(DbUtils.resultSetToTableModel(rs));
            
        }
        catch (Exception e)
        { 
           System.out.println(e);  
        }
    }
     
      public void loadBillTenderTable()
    {        
    try 
        {
            System.out.println("dfdavafbdggb");
            String qry= "SELECT t.tenderID as TenderID,p.name as Province,t.term as Term,t.fromGrade as StartGrade,t.toGrade as LastGrade from tender t,province p where printingStatus='complete' and p.prID=t.provinceID";
            pst=conn.prepareStatement(qry);
            rs=pst.executeQuery();
                        
            term_tblBillTender.setModel(DbUtils.resultSetToTableModel(rs));
            
        }
        catch (Exception e)
        { 
           System.out.println(e);  
        }
    }
      public void loadBillSchoolCombo(){
          try {
                String qr= " SELECT name from school where provinceID='"+BillProvinceID+"'";
                System.out.println(qr);
                pst=conn.prepareStatement(qr);
                rs=pst.executeQuery();
            while(rs.next())
            {
       
            term_cmbBillSchool.addItem(rs.getString("name"));
                
            }
            }catch (SQLException ex) {
                Logger.getLogger(TermTestGUI.class.getName()).log(Level.SEVERE, null, ex);
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
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        background2 = new javax.swing.JLabel();
        rightPanel = new javax.swing.JPanel();
        pnlTenders = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        term_lblShowDueDate = new javax.swing.JLabel();
        term_lblErrorDate = new javax.swing.JLabel();
        term_lblShowTenderHistoryId = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        term_btnUpdateDueDate = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        term_calUpdateDueDate = new com.toedter.calendar.JDateChooser();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        term_tblFinishedTenders = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        term_tblCurrentTenders = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        pnlTenderPapers = new javax.swing.JPanel();
        term_lblShowTenderId = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        term_cmbPaperGrade = new javax.swing.JComboBox();
        term_cmbPaperSubject = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        term_tblTenderPaper = new javax.swing.JTable();
        pnlUploadDoc = new javax.swing.JPanel();
        term_lblTick = new javax.swing.JLabel();
        term_btnCount = new javax.swing.JButton();
        batan1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        term_tblPapers = new javax.swing.JTable();
        batan = new javax.swing.JButton();
        pnlInsertPaperCounts = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        term_cmbSelectSchool = new javax.swing.JComboBox();
        jLabel27 = new javax.swing.JLabel();
        term_txtNumPapers = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        term_tblPaperCount = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        background1 = new javax.swing.JLabel();
        pnlNewTender = new javax.swing.JPanel();
        term_cmbTerm = new javax.swing.JComboBox();
        term_cmbFromGrade = new javax.swing.JComboBox();
        term_calDDate = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        term_lblErrorIncome = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        term_lblErrorDirector = new javax.swing.JLabel();
        term_calRDate = new com.toedter.calendar.JDateChooser();
        jButton6 = new javax.swing.JButton();
        term_txtDirector = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        term_txtIncome = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        term_txtarDescription = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        term_cmbToGrade = new javax.swing.JComboBox();
        term_cmbProvince = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jlabel34 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        background = new javax.swing.JLabel();
        pnlParcellBills = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        term_tblBillTender = new javax.swing.JTable();
        jLabel36 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        term_cmbBillSchool = new javax.swing.JComboBox();
        term_cmbBillGrade = new javax.swing.JComboBox();
        term_cmbBillSubject = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        pnlSchools = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        term_cmbPrSelect = new javax.swing.JComboBox();
        term_cmbDvSelect = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        term_txtSchoolName = new javax.swing.JTextField();
        term_txtPhone = new javax.swing.JTextField();
        term_lblErrorScName = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        term_arSchoolAddress = new javax.swing.JTextArea();
        jLabel25 = new javax.swing.JLabel();
        term_txtEmail = new javax.swing.JTextField();
        term_txtPrinciple = new javax.swing.JTextField();
        term_txtSchoolSearch = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jButton12 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        term_lblErrorAddress = new javax.swing.JLabel();
        term_lblErrorPhone = new javax.swing.JLabel();
        term_lblErrorEmail = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        term_cmbPrSchool = new javax.swing.JComboBox();
        term_cmbDvSchool = new javax.swing.JComboBox();
        jLabel40 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        background4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setMaximumSize(new java.awt.Dimension(1024, 570));
        setMinimumSize(new java.awt.Dimension(1024, 570));
        setPreferredSize(new java.awt.Dimension(1024, 570));
        setVisible(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        backPanel.setMaximumSize(new java.awt.Dimension(1024, 570));
        backPanel.setMinimumSize(new java.awt.Dimension(1024, 570));
        backPanel.setOpaque(false);

        leftPanel.setMaximumSize(new java.awt.Dimension(100, 570));
        leftPanel.setMinimumSize(new java.awt.Dimension(100, 570));
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new java.awt.Dimension(100, 570));
        leftPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/termTestPaperHandling/images/schools.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        leftPanel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 100, -1));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/termTestPaperHandling/images/papers.png"))); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        leftPanel.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 100, -1));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/termTestPaperHandling/images/tenders.png"))); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        leftPanel.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, -1));

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/termTestPaperHandling/images/Parcel bills.png"))); // NOI18N
        jButton14.setBorderPainted(false);
        jButton14.setContentAreaFilled(false);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        leftPanel.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 270, -1, -1));

        background2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        background2.setForeground(new java.awt.Color(255, 255, 255));
        background2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        leftPanel.add(background2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.setMaximumSize(new java.awt.Dimension(910, 570));
        rightPanel.setMinimumSize(new java.awt.Dimension(910, 570));
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new java.awt.Dimension(910, 570));
        rightPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlTenders.setBackground(new java.awt.Color(102, 255, 102));
        pnlTenders.setMaximumSize(new java.awt.Dimension(915, 570));
        pnlTenders.setOpaque(false);
        pnlTenders.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton8.setText("Add new tender");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        pnlTenders.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 70, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tenders");
        pnlTenders.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, -1, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Tender history");
        pnlTenders.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, -1, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Tenders in progress");
        pnlTenders.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, -1, -1));

        term_lblShowDueDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        term_lblShowDueDate.setForeground(new java.awt.Color(255, 255, 255));
        pnlTenders.add(term_lblShowDueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 260, -1, -1));

        term_lblErrorDate.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        term_lblErrorDate.setForeground(new java.awt.Color(255, 255, 255));
        pnlTenders.add(term_lblErrorDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 290, 0, 0));

        term_lblShowTenderHistoryId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        term_lblShowTenderHistoryId.setForeground(new java.awt.Color(255, 255, 255));
        pnlTenders.add(term_lblShowTenderHistoryId, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 530, -1, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Change due date");
        pnlTenders.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 260, -1, -1));

        jLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel.setForeground(new java.awt.Color(255, 255, 255));
        jLabel.setText("Due Date");
        pnlTenders.add(jLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, -1, -1));

        jButton9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton9.setText("Delete entry");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        pnlTenders.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 530, -1, -1));

        term_btnUpdateDueDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        term_btnUpdateDueDate.setText("Change");
        term_btnUpdateDueDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                term_btnUpdateDueDateActionPerformed(evt);
            }
        });
        pnlTenders.add(term_btnUpdateDueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 260, -1, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Tender id");
        pnlTenders.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 530, -1, -1));

        term_calUpdateDueDate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                term_calUpdateDueDateFocusLost(evt);
            }
        });
        term_calUpdateDueDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                term_calUpdateDueDatePropertyChange(evt);
            }
        });
        pnlTenders.add(term_calUpdateDueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 260, -1, -1));

        jSeparator1.setPreferredSize(new java.awt.Dimension(915, 5));
        pnlTenders.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 310, 1220, 20));

        term_tblFinishedTenders.setModel(new javax.swing.table.DefaultTableModel(
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
        term_tblFinishedTenders.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        term_tblFinishedTenders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                term_tblFinishedTendersMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(term_tblFinishedTenders);

        pnlTenders.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, 810, 150));

        term_tblCurrentTenders.setModel(new javax.swing.table.DefaultTableModel(
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
        term_tblCurrentTenders.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        term_tblCurrentTenders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                term_tblCurrentTendersMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(term_tblCurrentTenders);

        pnlTenders.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 810, 130));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        pnlTenders.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, -1));

        rightPanel.add(pnlTenders, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlTenderPapers.setBackground(new java.awt.Color(255, 255, 255));
        pnlTenderPapers.setMaximumSize(new java.awt.Dimension(915, 570));
        pnlTenderPapers.setOpaque(false);
        pnlTenderPapers.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        term_lblShowTenderId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        term_lblShowTenderId.setForeground(new java.awt.Color(255, 255, 255));
        pnlTenderPapers.add(term_lblShowTenderId, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 0, 0));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Paper details");
        pnlTenderPapers.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Tender");
        pnlTenderPapers.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, -1, -1));

        term_cmbPaperGrade.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select grade" }));
        pnlTenderPapers.add(term_cmbPaperGrade, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, -1, -1));

        term_cmbPaperSubject.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select subject", "Maths", "Sinhala", "Science", "English", "History", "ICT", "Social Studies", "Geography" }));
        pnlTenderPapers.add(term_cmbPaperSubject, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 200, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Subject");
        pnlTenderPapers.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 200, -1, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Grade");
        pnlTenderPapers.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, -1, -1));

        term_tblTenderPaper.setModel(new javax.swing.table.DefaultTableModel(
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
        term_tblTenderPaper.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                term_tblTenderPaperMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(term_tblTenderPaper);

        pnlTenderPapers.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 700, 110));

        pnlUploadDoc.setBackground(new java.awt.Color(102, 102, 102));
        pnlUploadDoc.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        term_lblTick.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/tick.png"))); // NOI18N

        term_btnCount.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        term_btnCount.setText("Insert Paper Counts");
        term_btnCount.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        term_btnCount.setMargin(new java.awt.Insets(5, 5, 5, 5));
        term_btnCount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                term_btnCountActionPerformed(evt);
            }
        });

        batan1.setText("Browse");
        batan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batan1ActionPerformed(evt);
            }
        });

        term_tblPapers.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(term_tblPapers);

        batan.setText("Add document");
        batan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlUploadDocLayout = new javax.swing.GroupLayout(pnlUploadDoc);
        pnlUploadDoc.setLayout(pnlUploadDocLayout);
        pnlUploadDocLayout.setHorizontalGroup(
            pnlUploadDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUploadDocLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUploadDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlUploadDocLayout.createSequentialGroup()
                        .addComponent(batan1)
                        .addGap(18, 18, 18)
                        .addComponent(term_lblTick)
                        .addGap(472, 472, 472)
                        .addComponent(batan))
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addComponent(term_btnCount, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        pnlUploadDocLayout.setVerticalGroup(
            pnlUploadDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUploadDocLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUploadDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUploadDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(batan)
                        .addComponent(batan1))
                    .addComponent(term_lblTick))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addGroup(pnlUploadDocLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(term_btnCount, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlTenderPapers.add(pnlUploadDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 880, 270));

        pnlInsertPaperCounts.setBackground(new java.awt.Color(102, 102, 102));
        pnlInsertPaperCounts.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlInsertPaperCounts.setPreferredSize(new java.awt.Dimension(880, 270));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("School");

        term_cmbSelectSchool.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select school" }));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Number of Papers");

        term_txtNumPapers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                term_txtNumPapersActionPerformed(evt);
            }
        });
        term_txtNumPapers.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                term_txtNumPapersKeyReleased(evt);
            }
        });

        term_tblPaperCount.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(term_tblPaperCount);

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setText("Add");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton15.setText("Done");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlInsertPaperCountsLayout = new javax.swing.GroupLayout(pnlInsertPaperCounts);
        pnlInsertPaperCounts.setLayout(pnlInsertPaperCountsLayout);
        pnlInsertPaperCountsLayout.setHorizontalGroup(
            pnlInsertPaperCountsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInsertPaperCountsLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(pnlInsertPaperCountsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton15)
                    .addGroup(pnlInsertPaperCountsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(pnlInsertPaperCountsLayout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addGap(33, 33, 33)
                            .addComponent(term_cmbSelectSchool, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27)
                            .addGap(29, 29, 29)
                            .addComponent(term_txtNumPapers, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(54, 54, 54)
                            .addComponent(jButton3))
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        pnlInsertPaperCountsLayout.setVerticalGroup(
            pnlInsertPaperCountsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInsertPaperCountsLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlInsertPaperCountsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(term_cmbSelectSchool, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(term_txtNumPapers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(31, 31, 31)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton15)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pnlTenderPapers.add(pnlInsertPaperCounts, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        background1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        pnlTenderPapers.add(background1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(pnlTenderPapers, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlNewTender.setBackground(new java.awt.Color(0, 0, 255));
        pnlNewTender.setMaximumSize(new java.awt.Dimension(915, 570));
        pnlNewTender.setOpaque(false);
        pnlNewTender.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        term_cmbTerm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3" }));
        pnlNewTender.add(term_cmbTerm, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 60, -1, -1));

        term_cmbFromGrade.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13" }));
        pnlNewTender.add(term_cmbFromGrade, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, -1, -1));
        pnlNewTender.add(term_calDDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 110, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Add new tender");
        pnlNewTender.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, 150, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Term");
        pnlNewTender.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, -1, -1));

        term_lblErrorIncome.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        term_lblErrorIncome.setForeground(new java.awt.Color(255, 0, 0));
        pnlNewTender.add(term_lblErrorIncome, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 160, 100, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Due Date");
        pnlNewTender.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 110, -1, -1));

        term_lblErrorDirector.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        term_lblErrorDirector.setForeground(new java.awt.Color(255, 0, 0));
        pnlNewTender.add(term_lblErrorDirector, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 160, -1, -1));
        pnlNewTender.add(term_calRDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, -1, -1));

        jButton6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton6.setText("Save tender details");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        pnlNewTender.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 430, -1, -1));

        term_txtDirector.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                term_txtDirectorMouseClicked(evt);
            }
        });
        term_txtDirector.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                term_txtDirectorKeyReleased(evt);
            }
        });
        pnlNewTender.add(term_txtDirector, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 160, 120, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Total income(Rs)");
        pnlNewTender.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, -1, -1));

        term_txtIncome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                term_txtIncomeMouseClicked(evt);
            }
        });
        term_txtIncome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                term_txtIncomeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                term_txtIncomeKeyTyped(evt);
            }
        });
        pnlNewTender.add(term_txtIncome, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 120, -1));

        term_txtarDescription.setColumns(20);
        term_txtarDescription.setRows(5);
        jScrollPane2.setViewportView(term_txtarDescription);

        pnlNewTender.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 270, 420, 90));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("description");
        pnlNewTender.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("upto grade");
        pnlNewTender.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 210, 90, 20));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Director approved");
        pnlNewTender.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, -1, -1));

        jButton10.setText("Back");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        pnlNewTender.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 430, -1, -1));

        term_cmbToGrade.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13" }));
        pnlNewTender.add(term_cmbToGrade, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 210, -1, -1));

        pnlNewTender.add(term_cmbProvince, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Province");
        pnlNewTender.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 60, 20));

        jlabel34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlabel34.setForeground(new java.awt.Color(255, 255, 255));
        jlabel34.setText("Received Date");
        pnlNewTender.add(jlabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, -1, 20));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("From grade");
        pnlNewTender.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 90, 20));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        pnlNewTender.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(pnlNewTender, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlParcellBills.setBackground(new java.awt.Color(255, 255, 255));
        pnlParcellBills.setMaximumSize(new java.awt.Dimension(915, 570));
        pnlParcellBills.setOpaque(false);
        pnlParcellBills.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Parcel Bills");
        pnlParcellBills.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, -1, -1));

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton7.setText("Generate bill to print");
        jButton7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        pnlParcellBills.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 420, 180, 40));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Select tender");

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Grade");

        term_tblBillTender.setModel(new javax.swing.table.DefaultTableModel(
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
        term_tblBillTender.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                term_tblBillTenderMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(term_tblBillTender);

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("School");

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Subject");

        term_cmbBillSchool.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select school" }));

        term_cmbBillGrade.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select grade" }));

        term_cmbBillSubject.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select subject", "Maths", "Sinhala", "English", "Science", "History" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addGap(27, 27, 27)
                .addComponent(term_cmbBillSchool, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98)
                .addComponent(jLabel38)
                .addGap(28, 28, 28)
                .addComponent(term_cmbBillGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel35)
                .addGap(18, 18, 18)
                .addComponent(term_cmbBillSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jLabel38)
                    .addComponent(jLabel35)
                    .addComponent(term_cmbBillSchool, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(term_cmbBillSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(term_cmbBillGrade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
        );

        pnlParcellBills.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 860, 300));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        pnlParcellBills.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(pnlParcellBills, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlSchools.setBackground(new java.awt.Color(102, 255, 102));
        pnlSchools.setMaximumSize(new java.awt.Dimension(915, 570));
        pnlSchools.setMinimumSize(new java.awt.Dimension(915, 570));
        pnlSchools.setOpaque(false);
        pnlSchools.setPreferredSize(new java.awt.Dimension(915, 570));
        pnlSchools.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Division");
        pnlSchools.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 410, -1, -1));

        term_cmbPrSelect.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        term_cmbPrSelect.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select province" }));
        term_cmbPrSelect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                term_cmbPrSelectMouseClicked(evt);
            }
        });
        term_cmbPrSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                term_cmbPrSelectActionPerformed(evt);
            }
        });
        term_cmbPrSelect.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                term_cmbPrSelectPropertyChange(evt);
            }
        });
        pnlSchools.add(term_cmbPrSelect, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 410, -1, -1));

        term_cmbDvSelect.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        term_cmbDvSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                term_cmbDvSelectActionPerformed(evt);
            }
        });
        pnlSchools.add(term_cmbDvSelect, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 410, -1, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("School name");
        pnlSchools.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, -1, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("Add new school");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        pnlSchools.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 220, 160, -1));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Address");
        pnlSchools.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, -1, -1));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("phone");
        pnlSchools.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, -1, 20));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Principle ");
        pnlSchools.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, -1, -1));

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Province");
        pnlSchools.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 310, -1, -1));

        term_txtSchoolName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                term_txtSchoolNameFocusLost(evt);
            }
        });
        term_txtSchoolName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                term_txtSchoolNameMouseClicked(evt);
            }
        });
        term_txtSchoolName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                term_txtSchoolNameKeyReleased(evt);
            }
        });
        pnlSchools.add(term_txtSchoolName, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 270, -1));

        term_txtPhone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                term_txtPhoneMouseClicked(evt);
            }
        });
        term_txtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                term_txtPhoneKeyReleased(evt);
            }
        });
        pnlSchools.add(term_txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 270, -1));

        term_lblErrorScName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        term_lblErrorScName.setForeground(new java.awt.Color(255, 0, 0));
        pnlSchools.add(term_lblErrorScName, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, -1, -1));

        term_arSchoolAddress.setColumns(20);
        term_arSchoolAddress.setRows(5);
        term_arSchoolAddress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                term_arSchoolAddressMouseClicked(evt);
            }
        });
        term_arSchoolAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                term_arSchoolAddressKeyReleased(evt);
            }
        });
        jScrollPane5.setViewportView(term_arSchoolAddress);

        pnlSchools.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 270, 60));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Province");
        pnlSchools.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 410, -1, -1));

        term_txtEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                term_txtEmailMouseClicked(evt);
            }
        });
        pnlSchools.add(term_txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 270, 270, -1));

        term_txtPrinciple.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                term_txtPrincipleMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                term_txtPrincipleMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                term_txtPrincipleMouseExited(evt);
            }
        });
        term_txtPrinciple.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                term_txtPrincipleKeyReleased(evt);
            }
        });
        pnlSchools.add(term_txtPrinciple, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 190, 270, -1));
        pnlSchools.add(term_txtSchoolSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 90, 220, -1));

        jButton13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton13.setText("Search");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        pnlSchools.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 150, -1, -1));

        jSeparator2.setPreferredSize(new java.awt.Dimension(915, 5));
        pnlSchools.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 390, 1080, -1));

        jButton12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton12.setText("Delete School");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        pnlSchools.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 320, 160, -1));

        jButton11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton11.setText("Update school details");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        pnlSchools.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 270, -1, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(jTable2);

        pnlSchools.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 440, 770, 120));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("School handling");
        pnlSchools.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, -1, -1));

        term_lblErrorAddress.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        term_lblErrorAddress.setForeground(new java.awt.Color(255, 0, 0));
        pnlSchools.add(term_lblErrorAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 180, -1, -1));

        term_lblErrorPhone.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        term_lblErrorPhone.setForeground(new java.awt.Color(255, 0, 0));
        pnlSchools.add(term_lblErrorPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 180, -1, -1));

        term_lblErrorEmail.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        term_lblErrorEmail.setForeground(new java.awt.Color(255, 0, 0));
        pnlSchools.add(term_lblErrorEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 230, -1, -1));

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Division");
        pnlSchools.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, -1, -1));

        term_cmbPrSchool.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select province" }));
        term_cmbPrSchool.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                term_cmbPrSchoolMouseClicked(evt);
            }
        });
        term_cmbPrSchool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                term_cmbPrSchoolActionPerformed(evt);
            }
        });
        pnlSchools.add(term_cmbPrSchool, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 310, 160, -1));

        term_cmbDvSchool.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select division" }));
        pnlSchools.add(term_cmbDvSchool, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 350, 160, -1));

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Email");
        pnlSchools.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, -1, -1));

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Name");
        pnlSchools.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, -1, -1));

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 354, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 114, Short.MAX_VALUE)
        );

        pnlSchools.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, 360, 120));

        background4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        pnlSchools.add(background4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(pnlSchools, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setMaximumSize(new java.awt.Dimension(915, 570));
        jPanel1.setMinimumSize(new java.awt.Dimension(915, 570));
        jPanel1.setPreferredSize(new java.awt.Dimension(915, 570));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Welcome");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, -1, -1));

        rightPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/termTestPaperHandling/images/background.jpg"))); // NOI18N

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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels
        pnlNewTender.setVisible(false);
        pnlTenderPapers.setVisible(false);
        pnlTenders.setVisible(false);
        pnlParcellBills.setVisible(false);
        pnlSchools.setVisible(true);
        
        rightPanel.add(pnlSchools);
        rightPanel.repaint();
        rightPanel.revalidate();
        
        
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels
        pnlNewTender.setVisible(false);
        pnlSchools.setVisible(false);
        pnlParcellBills.setVisible(false);
        pnlTenderPapers.setVisible(true);
        pnlTenders.setVisible(false);
        
        rightPanel.add(pnlTenderPapers);
        rightPanel.repaint();
        rightPanel.revalidate();
        
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        
        pnlNewTender.setVisible(false);
        pnlSchools.setVisible(false);
        pnlTenderPapers.setVisible(false);
        pnlParcellBills.setVisible(false);
        pnlTenders.setVisible(true);

        rightPanel.add(pnlTenders);
        rightPanel.repaint();
        rightPanel.revalidate();
        
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        
        pnlParcellBills.setVisible(true);
        pnlNewTender.setVisible(false);
        pnlSchools.setVisible(false);
        pnlTenderPapers.setVisible(false);
        pnlTenders.setVisible(false);

        rightPanel.add(pnlParcellBills);
        rightPanel.repaint();
        rightPanel.revalidate();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        
        pnlNewTender.setVisible(true);
        pnlSchools.setVisible(false);
        pnlTenderPapers.setVisible(false);
        pnlTenders.setVisible(false);
        pnlParcellBills.setVisible(false);

        rightPanel.add(pnlNewTender);
        rightPanel.repaint();
        rightPanel.revalidate();
        
       
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        int p=JOptionPane.showConfirmDialog(this, "Do you want to delete?","Delete",JOptionPane.YES_NO_OPTION);
        if (p==0){
            try{
                tender tdnew=new tender();
                tdnew.setTenderId(updtTenderHistoryID);
                tenderDA tdDA=new tenderDA();
                if(tdDA.deleteTender(tdnew)){
                    JOptionPane.showMessageDialog(this, "Tender is deleted");
                }
            }
            catch(Exception e){

            }
            loadTenderHistoryTable();
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void term_btnUpdateDueDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_term_btnUpdateDueDateActionPerformed
        java.sql.Date updtDueDate=null;
        updtDueDate=new java.sql.Date(term_calUpdateDueDate.getDate().getTime());
        java.sql.Date dueDate=null;
        try{
            String checkDateqry="select receivedDate from tender where tenderID='"+updtTenderID+"'";
            pst=conn.prepareStatement(checkDateqry);
            rs=pst.executeQuery();
            while(rs.next())
            {
                dueDate=rs.getDate("receivedDate");
            }
        }
        catch(Exception e){

        }
        
        if(updtDueDate.before(dueDate)){
                    JOptionPane.showMessageDialog(this, "Due date must greater than received date");
                }
        else{
        try{
            tender tdnew=new tender();
            tdnew.setTenderId(updtTenderID);
            tdnew.setDueDate(updtDueDate);
            tenderDA tdDA=new tenderDA();
            if(tdDA.updateDueDate(tdnew)){
                JOptionPane.showMessageDialog(this, "Due Date updated");
            }
        }
        catch(Exception e){

        }
        loadTenderTable();
        }
    }//GEN-LAST:event_term_btnUpdateDueDateActionPerformed

    private void term_calUpdateDueDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_term_calUpdateDueDatePropertyChange
        //JOptionPane.showMessageDialog(this, "jnadjcnjd");
        

    }//GEN-LAST:event_term_calUpdateDueDatePropertyChange

    private void term_tblFinishedTendersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_term_tblFinishedTendersMouseClicked
        int row=term_tblFinishedTenders.getSelectedRow();

        updtTenderHistoryID=Integer.parseInt(term_tblFinishedTenders.getValueAt(row, 0).toString());

        term_lblShowTenderHistoryId.setText(String.valueOf(updtTenderHistoryID));
    }//GEN-LAST:event_term_tblFinishedTendersMouseClicked

    private void term_tblCurrentTendersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_term_tblCurrentTendersMouseClicked
        /*try{
            int row=term_tblCurrentTenders.getSelectedRow();
            String tableClick1=(term_tblCurrentTenders.getModel().getValueAt(row, 0).toString());
            String chngDateqry="select dueDate from tender where tenderID='"+tableClick1+"'";
            pst=conn.prepareStatement(chngDateqry);
            rs=pst.executeQuery();
            if(rs.next()){
                String addDueDate=rs.getString("dueDate");
                term_lblShowDueDate.setText(addDueDate);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }*/
        int row=term_tblCurrentTenders.getSelectedRow();

        updtTenderID=Integer.parseInt(term_tblCurrentTenders.getValueAt(row, 0).toString());
        String chDueDate=term_tblCurrentTenders.getValueAt(row, 1).toString();

        term_lblShowDueDate.setText(chDueDate);
    }//GEN-LAST:event_term_tblCurrentTendersMouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
         java.sql.Date dueDate=null;
        java.sql.Date receivedDate=null;
        receivedDate=new java.sql.Date(term_calRDate.getDate().getTime());
        dueDate=new java.sql.Date(term_calDDate.getDate().getTime());
        
        if(term_txtIncome.getText().isEmpty() || term_txtDirector.getText().isEmpty()||receivedDate.toString().equals("")||dueDate.toString().equals("")){
            JOptionPane.showMessageDialog(this, "One or more fields are empty");
            if(term_txtIncome.getText().isEmpty()){
                term_txtIncome.setBackground(Color.PINK);
            }

            if(term_txtDirector.getText().isEmpty()){
                term_txtDirector.setBackground(Color.PINK);
            }

        }
        else{
            if(dueDate.before(receivedDate)){
                JOptionPane.showMessageDialog(this, "Due date must be greater than received date");
            }
            else
            {
            if(tenderIncome||tenderDirector){
                JOptionPane.showMessageDialog(this, "One or more fields are invalid");
            }
            else{
            
            String province=term_cmbProvince.getSelectedItem().toString();
            receivedDate=new java.sql.Date(term_calRDate.getDate().getTime());
            dueDate=new java.sql.Date(term_calDDate.getDate().getTime());
            int term=Integer.parseInt(term_cmbTerm.getSelectedItem().toString());
            String director=term_txtDirector.getText();
            Double income=Double.parseDouble(term_txtIncome.getText());
            int fromGrade=Integer.parseInt(term_cmbFromGrade.getSelectedItem().toString());
            int toGrade=Integer.parseInt(term_cmbToGrade.getSelectedItem().toString());
            String description=term_txtarDescription.getText();
            int provinceId = 0;

            try {
                String qr= " SELECT prID from province where name = '"+province+"'" ;
                pst=conn.prepareStatement(qr);
                rs=pst.executeQuery();
                while(rs.next())
                {
                    provinceId=Integer.parseInt(rs.getString("prID"));
                }
            }catch (SQLException ex) {
                Logger.getLogger(TermTestGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                tender tdnew=new tender(dueDate, provinceId, term, description, receivedDate, income, director, fromGrade, toGrade);
                tenderDA tdDAnew=new tenderDA();
                if(tdDAnew.addNewTender(tdnew)){
                    JOptionPane.showMessageDialog(this, "Insertion successfull");
                }
                else{
                    JOptionPane.showMessageDialog(this, "Insertion failed");
                }

            } catch (SQLException ex) {
                Logger.getLogger(TermTestGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadTenderTable();
            rightPanel.removeAll();
            rightPanel.repaint();
            rightPanel.revalidate();
            
            pnlNewTender.setVisible(false);
            pnlSchools.setVisible(false);
            pnlTenderPapers.setVisible(false);
            pnlTenders.setVisible(true);
            pnlParcellBills.setVisible(false);

            rightPanel.add(pnlTenders);
            rightPanel.repaint();
            rightPanel.revalidate();
            
            }   
        }
        }      
    }//GEN-LAST:event_jButton6ActionPerformed

    private void term_txtDirectorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_term_txtDirectorKeyReleased
        Character c=evt.getKeyChar();
        if(Character.isDigit(c)){
            evt.consume();
            term_txtDirector.setBackground(Color.PINK);
            tenderDirector=true;
        }
        else{
            term_txtDirector.setBackground(Color.WHITE);
            tenderDirector=false;
        }
    }//GEN-LAST:event_term_txtDirectorKeyReleased

    private void term_txtIncomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_term_txtIncomeKeyReleased
        
        
        
        String decimalPattern = "([0-9]+\\.[0-9]+)";
        String number=term_txtIncome.getText();
        boolean match = Pattern.matches(decimalPattern, number);
        if(match==false)
        {
            //Toolkit.getDefaultToolkit().beep();
            term_txtIncome.setBackground(Color.PINK);
            tenderIncome=true;
        }

        else
        {
            term_txtIncome.setBackground(Color.WHITE);
            tenderIncome=false;
            //lblmfErrorBasicSal.setText("");;
        }

    }//GEN-LAST:event_term_txtIncomeKeyReleased

    private void term_txtIncomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_term_txtIncomeKeyTyped

        /*try {
            char c=evt.getKeyChar();
            if(Character.isDigit(c))
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
            term_lblErrorIncome.setText("Numbers ");

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }//GEN-LAST:event_term_txtIncomeKeyTyped

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels
        
        pnlNewTender.setVisible(false);
        pnlSchools.setVisible(false);
        pnlTenderPapers.setVisible(false);
        pnlParcellBills.setVisible(false);
        pnlTenders.setVisible(true);

        rightPanel.add(pnlTenders);
        rightPanel.repaint();
        rightPanel.revalidate();
        
        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void term_cmbPrSelectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_term_cmbPrSelectMouseClicked

    }//GEN-LAST:event_term_cmbPrSelectMouseClicked

    private void term_cmbPrSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_term_cmbPrSelectActionPerformed
        term_cmbDvSelect.removeAllItems();
        String passPrName=term_cmbPrSelect.getSelectedItem().toString();
        if(passPrName.equals("select province"))
        {
             
        }
        else{
        try{
            String qry="select * from province where name='"+passPrName+"'";
                    pst=conn.prepareStatement(qry);
                    rs=pst.executeQuery();
                    while(rs.next()){
                        passPrId=Integer.parseInt(rs.getString("prID"));
                    }        
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        loadDivisionCombo();
        }
    }//GEN-LAST:event_term_cmbPrSelectActionPerformed

    private void term_cmbPrSelectPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_term_cmbPrSelectPropertyChange

    }//GEN-LAST:event_term_cmbPrSelectPropertyChange

    private void term_txtSchoolNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_term_txtSchoolNameKeyReleased
        Character c=evt.getKeyChar();
        if(Character.isDigit(c)){
            evt.consume();
            //term_lblErrorScName.setText("*Invalid");
            term_txtSchoolName.setBackground(Color.PINK);
            schoolname=true;
        }
        else{
            //term_lblErrorScName.setText("");
            term_txtSchoolName.setBackground(Color.WHITE);
            schoolname=false;
        }
    }//GEN-LAST:event_term_txtSchoolNameKeyReleased

    private void term_txtPhoneKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_term_txtPhoneKeyReleased
        int i=term_txtPhone.getText().length();
        Character c=evt.getKeyChar();
        if(!Character.isDigit(c)){
            evt.consume();
            term_txtPhone.setBackground(Color.PINK);
            schoolPhone=true;
        }
        else{
            term_txtPhone.setBackground(Color.WHITE);
            schoolPhone=false;
        }
        if(i>10){
            term_txtPhone.setBackground(Color.PINK);
            schoolPhone=true;
        }
        else{
            term_txtPhone.setBackground(Color.WHITE);
            schoolPhone=false;
        }
           
    }//GEN-LAST:event_term_txtPhoneKeyReleased

    private void term_arSchoolAddressKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_term_arSchoolAddressKeyReleased
        /*Character c=evt.getKeyChar();
        if(Character.isDigit(c)){
            evt.consume();
            //term_lblErrorScName.setText("*Invalid");
            term_arSchoolAddress.setBackground(Color.PINK);
            schoolAddress=false;
        }
        else{
            //term_lblErrorScName.setText("");
            term_arSchoolAddress.setBackground(Color.WHITE);
            schoolAddress=true;
        }*/
    }//GEN-LAST:event_term_arSchoolAddressKeyReleased

    private void term_txtPrincipleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_term_txtPrincipleKeyReleased
        Character c=evt.getKeyChar();
        if(Character.isDigit(c)){
            evt.consume();
            //term_lblErrorScName.setText("*Invalid");
            term_txtPrinciple.setBackground(Color.PINK);
            schoolPrinciple=true;
        }
        else{
            //term_lblErrorScName.setText("");
            term_txtPrinciple.setBackground(Color.WHITE);
            schoolPrinciple=false;
    }//GEN-LAST:event_term_txtPrincipleKeyReleased
    }
    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        String div=null;
        if(term_txtSchoolSearch.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Please enter a key word");
        }
        else{
        String schoolName=term_txtSchoolSearch.getText();
        System.out.println(schoolName);
        try{
            String searchSchoolqry="select * from school where name='"+schoolName+"'";
            pst=conn.prepareStatement(searchSchoolqry);
            rs=pst.executeQuery();
            if(rs.next())
            {
                term_txtSchoolName.setText(rs.getString("name"));
                term_arSchoolAddress.setText(rs.getString("address"));
                term_txtPrinciple.setText(rs.getString("principle"));
                term_txtPhone.setText(rs.getString("phone"));
                term_txtEmail.setText(rs.getString("email"));
                div=rs.getString("division");
                
                
            }
            else{
                JOptionPane.showMessageDialog(this, "School is not available");
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        try{
                    term_cmbPrSchool.removeAllItems();
                    term_cmbDvSchool.removeAllItems();
                    term_cmbDvSchool.addItem(div);
                    String scIdqry="select * from province where prID='"+rs.getString("provinceID")+"'";
                    pst=conn.prepareStatement(scIdqry);
                    rs1=pst.executeQuery();
                    while(rs1.next()){
                        term_cmbPrSchool.addItem(rs.getString("name"));
                    }
                }
                catch(Exception e){
                    
                }
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        school sc1=new school();
        if(term_txtSchoolName.getText().isEmpty()  || term_arSchoolAddress.getText().isEmpty() || term_txtPrinciple.getText().isEmpty() || term_txtPhone.getText().isEmpty() || term_txtEmail.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "One or more fields are empty");
            if(term_txtSchoolName.getText().isEmpty()){
                term_txtSchoolName.setBackground(Color.PINK);
            }

            if(term_arSchoolAddress.getText().isEmpty()){
                term_arSchoolAddress.setBackground(Color.PINK);
            }
            if(term_txtPrinciple.getText().isEmpty()){
                term_txtPrinciple.setBackground(Color.PINK);
            }
            if(term_txtPhone.getText().isEmpty()){
                term_txtPhone.setBackground(Color.PINK);
            }
            if(term_txtEmail.getText().isEmpty()){
                term_txtEmail.setBackground(Color.PINK);
            }

        }
        else{
            if(!sc1.validate(term_txtEmail.getText().trim())){
                //JOptionPane.showMessageDialog(this, "");
                term_txtEmail.setBackground(Color.PINK);
                JOptionPane.showMessageDialog(this, "Email is invalid");
            }
            else{
                if(schoolname||schoolPhone||schoolEmail||schoolPrinciple){
                    JOptionPane.showMessageDialog(this, "One or more fields are invalid");
                }
                else{
                String schoolName=term_txtSchoolName.getText();
                String address=term_arSchoolAddress.getText();
                String principle=term_txtPrinciple.getText();
                String phone=term_txtPhone.getText();
                String email=term_txtEmail.getText();
                int schoolId=0;
                int provinceId=0;
                String division=null;
                try{
                    String scIdqry="select * from school where name='"+schoolName+"'";
                    pst=conn.prepareStatement(scIdqry);
                    rs=pst.executeQuery();
                    while(rs.next()){
                        schoolId=Integer.parseInt(rs.getString("schoolID"));
                        provinceId=Integer.parseInt(rs.getString("provinceID"));
                        division=rs.getString("division");
                    }
                }
                catch(Exception e){
                    System.out.println(e);
                }
                try{
                    school sc=new school(schoolId,schoolName,address,principle,provinceId,phone,email,division);
                    schoolDA scDA=new schoolDA();
                    if(scDA.updateSchool(sc)){
                        JOptionPane.showMessageDialog(this, "Updation successful");
                    }
                }
                catch(Exception e){

                }

                term_txtSchoolSearch.setText("");
                term_txtSchoolName.setText("");
                term_arSchoolAddress.setText("");
                term_txtPrinciple.setText("");
                term_txtPhone.setText("");
                term_txtEmail.setText("");
            }

        }
    }//GEN-LAST:event_jButton11ActionPerformed
    }
    private void term_tblTenderPaperMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_term_tblTenderPaperMouseClicked
        term_cmbPaperGrade.removeAllItems();
        int row=term_tblTenderPaper.getSelectedRow();
        PaperTenderId=Integer.parseInt(term_tblTenderPaper.getValueAt(row, 0).toString());
        String ProvinceName=term_tblTenderPaper.getValueAt(row, 1).toString();
        try {
                String qr= " SELECT prID from province where name = '"+ProvinceName+"'" ;
                pst=conn.prepareStatement(qr);
                rs=pst.executeQuery();
                while(rs.next())
                {
                    ProvinceId=Integer.parseInt(rs.getString("prID"));
                }
            }catch (SQLException ex) {
                Logger.getLogger(TermTestGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        int toGrade=Integer.parseInt(term_tblTenderPaper.getValueAt(row, 5).toString());
        int fromGrade=Integer.parseInt(term_tblTenderPaper.getValueAt(row, 4).toString());
        //System.out.println(fromGrade);
//        for(int i=1;i<=term_cmbPaperGrade.getItemCount();i++){
//            term_cmbPaperGrade.remove(i);
//            System.out.println(i);
//        }
        //term_cmbPaperGrade.removeAllItems();
//        for(int i=fromGrade;fromGrade<=toGrade;i++){
//            term_cmbPaperGrade.addItem(i);
//        }
        while(fromGrade<=toGrade){
            term_cmbPaperGrade.addItem(fromGrade++);
        }
        
    }//GEN-LAST:event_term_tblTenderPaperMouseClicked

    private void batan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batan1ActionPerformed
        JFileChooser chooser=new JFileChooser();
        chooser.showOpenDialog(this);
        File f=chooser.getSelectedFile();
        paperFilename=f.getAbsolutePath();
        term_lblTick.setVisible(true);
    }//GEN-LAST:event_batan1ActionPerformed

    private void batanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batanActionPerformed
         
        String checkgrade=term_cmbPaperGrade.getSelectedItem().toString();
        subject=term_cmbPaperSubject.getSelectedItem().toString();
        //System.out.println(grade);
        if(checkgrade.equals("Select grade")){
            JOptionPane.showMessageDialog(this, "Please select a tender and a grade");
        }
        else{        if(checkgrade.equals("Select grade")){

            Integer.parseInt(term_cmbPaperGrade.getSelectedItem().toString());
        }
        if(subject.equals("Select subject")){
            JOptionPane.showMessageDialog(this, "Please select a subject");
        }
        grade=Integer.parseInt(checkgrade);
        int paperId=0;
        try{
            String paperIdqry="select paperID from paper where subject='"+subject+"' AND grade='"+grade+"' AND tenderID='"+PaperTenderId+"'";
                    pst=conn.prepareStatement(paperIdqry);
                    rs=pst.executeQuery();
                    while(rs.next()){
                        paperId=Integer.parseInt(rs.getString("paperID"));
                    }
        }
        catch(Exception e){
            
        }
        if(!paperFilename.equals("no")){
            
        try{
            Statement stmt = (Statement) conn.createStatement();
            String inqry = "INSERT INTO draft(paperID,document) values('"+paperId+"','"+paperFilename+"')";
            System.out.println(inqry);
            stmt.executeUpdate(inqry);
            
            JOptionPane.showMessageDialog(this, "Document saved successfully");
            loadPaperTable();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error occured while saving");
        }
        }
        else{
            JOptionPane.showMessageDialog(this,"Please select a document");
        }
    }//GEN-LAST:event_batanActionPerformed
    }
    private void term_calUpdateDueDateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_term_calUpdateDueDateFocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_term_calUpdateDueDateFocusLost

    private void term_txtSchoolNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_term_txtSchoolNameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_term_txtSchoolNameFocusLost

    private void term_txtEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_term_txtEmailMouseClicked
        term_txtEmail.setBackground(Color.WHITE);
    }//GEN-LAST:event_term_txtEmailMouseClicked

    private void term_txtIncomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_term_txtIncomeMouseClicked
        term_txtIncome.setBackground(Color.WHITE);
    }//GEN-LAST:event_term_txtIncomeMouseClicked

    private void term_txtDirectorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_term_txtDirectorMouseClicked
        term_txtDirector.setBackground(Color.WHITE);
    }//GEN-LAST:event_term_txtDirectorMouseClicked

    private void term_txtSchoolNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_term_txtSchoolNameMouseClicked
        term_txtSchoolName.setBackground(Color.WHITE);
    }//GEN-LAST:event_term_txtSchoolNameMouseClicked

    private void term_arSchoolAddressMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_term_arSchoolAddressMouseClicked
        term_arSchoolAddress.setBackground(Color.WHITE);
    }//GEN-LAST:event_term_arSchoolAddressMouseClicked

    private void term_txtPrincipleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_term_txtPrincipleMouseClicked
        term_txtPrinciple.setBackground(Color.WHITE);
    }//GEN-LAST:event_term_txtPrincipleMouseClicked

    private void term_txtPhoneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_term_txtPhoneMouseClicked
        term_txtPhone.setBackground(Color.WHITE);
    }//GEN-LAST:event_term_txtPhoneMouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void term_txtPrincipleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_term_txtPrincipleMouseEntered
//        if(schoolPrinciple==true){
//        term_lblErrorPrinciple.setVisible(true);
//        }
//        else if(schoolPrinciple==false){
//        term_lblErrorPrinciple.setVisible(false);
//        }
    }//GEN-LAST:event_term_txtPrincipleMouseEntered

    private void term_txtPrincipleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_term_txtPrincipleMouseExited
//        if(schoolPrinciple==true){
//        term_lblErrorPrinciple.setVisible(false);
//        }
//        else if(schoolPrinciple==false){
//        term_lblErrorPrinciple.setVisible(false);
//    }
    }//GEN-LAST:event_term_txtPrincipleMouseExited

    private void term_txtNumPapersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_term_txtNumPapersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_term_txtNumPapersActionPerformed

    private void term_btnCountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_term_btnCountActionPerformed
        pnlUploadDoc.setVisible(false);
        pnlInsertPaperCounts.setVisible(true);
        loadSchoolCombo();
    }//GEN-LAST:event_term_btnCountActionPerformed

    private void term_txtNumPapersKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_term_txtNumPapersKeyReleased
        Character c=evt.getKeyChar();
        if(!Character.isDigit(c)){
            evt.consume();
            term_txtNumPapers.setBackground(Color.PINK);
            numPapers=true;
        }
        else{
            term_txtNumPapers.setBackground(Color.WHITE);
            numPapers=false;
        }
    }//GEN-LAST:event_term_txtNumPapersKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(numPapers==true){
            JOptionPane.showMessageDialog(this,"Enter a valid number");
        }
        else{
            int schoolId=0;
            int oldAmount=0;
            int NumPapers=Integer.parseInt(term_txtNumPapers.getText());
            String school=term_cmbSelectSchool.getSelectedItem().toString();
            try{
            String qry="select schoolID from school where name='"+school+"'"; //getting school id from school name
                    pst=conn.prepareStatement(qry);
                    rs=pst.executeQuery();
                    while(rs.next()){
                        schoolId=Integer.parseInt(rs.getString("schoolID"));
                    }
                }
            catch(Exception e){
                System.out.println(e);
            }
            
            try{
                    String qry1="select amount from paper where grade='"+grade+"' and subject='"+subject+"' and tenderID='"+PaperTenderId+"'"; //getting school id from school name
                    pst=conn.prepareStatement(qry1);
                    rs=pst.executeQuery();
                    while(rs.next()){
                        oldAmount=Integer.parseInt(rs.getString("amount"));
                    }
            }
           catch(Exception e){
               System.out.println(e);
           }
            try{
            paper p=new paper(PaperTenderId,schoolId,grade,subject,NumPapers);
            paperDA pd=new paperDA();
            pd.addPaperCount(p);
            }
            catch(Exception e){
                System.out.println(e);
            }
            try{
            paper p1=new paper(oldAmount+NumPapers,PaperTenderId,grade,subject);
            paperDA pd1=new paperDA();
            pd1.updateAmount(p1);
            }
            catch(Exception e){
                System.out.println(e);
            }
            loadPaperCountTable();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void term_tblBillTenderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_term_tblBillTenderMouseClicked
        term_cmbBillSchool.removeAllItems();
        term_cmbBillGrade.removeAllItems();
        int row=term_tblBillTender.getSelectedRow();
        BillTenderID=Integer.parseInt(term_tblBillTender.getValueAt(row, 0).toString());
        BillProvinceName=term_tblBillTender.getValueAt(row, 1).toString();
        try{
            String qry="select prID from province where name='"+BillProvinceName+"'";
                    pst=conn.prepareStatement(qry);
                    rs=pst.executeQuery();
                    while(rs.next()){
                        BillProvinceID=Integer.parseInt(rs.getString("prID"));
                    }
        }
        catch(Exception e){
            System.out.println(e);
        }
        BillTerm=Integer.parseInt(term_tblBillTender.getValueAt(row, 2).toString());
        int fromGrade=Integer.parseInt(term_tblBillTender.getValueAt(row, 3).toString());
        int toGrade=Integer.parseInt(term_tblBillTender.getValueAt(row, 4).toString());
        for(int i=fromGrade;i<=toGrade;i++){
            term_cmbBillGrade.addItem(i);
        }
        loadBillSchoolCombo();
    }//GEN-LAST:event_term_tblBillTenderMouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if(term_cmbBillSchool.getSelectedItem().toString().equals("select school") || term_cmbBillGrade.getSelectedItem().toString().equals("select grade")||term_cmbBillSchool.getSelectedItem().toString().equals("select school")){
            JOptionPane.showMessageDialog(this, "Select a school,grade and a subject");
        }
        else{
        String schoolName=term_cmbBillSchool.getSelectedItem().toString();
        int BillSchoolId=0;
        try{
            String qry="select schoolID from school where name='"+schoolName+"'";
                    pst=conn.prepareStatement(qry);
                    rs=pst.executeQuery();
                    while(rs.next()){
                        BillSchoolId=Integer.parseInt(rs.getString("schoolID"));
                        System.out.println(BillSchoolId);
                    }
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        HashMap para=new HashMap();
        para.put("tenderId",BillTenderID);
        para.put("schoolId",BillSchoolId);
        para.put("provinceId",BillProvinceID);
        
        reportViewer re=new reportViewer("E:\\7\\sumudu\\vidulaka\\src\\termTestPaperHandling\\ParcellBill.jasper",para);
        re.setVisible(true);
        re.toFront();
        re.repaint();
        re.setAlwaysOnTop(true);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        pnlUploadDoc.setVisible(true);
        pnlInsertPaperCounts.setVisible(false);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void term_cmbDvSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_term_cmbDvSelectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_term_cmbDvSelectActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        school sc2=new school();
        if(term_txtSchoolName.getText().isEmpty()  || term_arSchoolAddress.getText().isEmpty() || term_txtPrinciple.getText().isEmpty() || term_txtPhone.getText().isEmpty() || term_txtEmail.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "One or more fields are empty");
            if(term_txtSchoolName.getText().isEmpty()){
                term_txtSchoolName.setBackground(Color.PINK);
            }

            if(term_arSchoolAddress.getText().isEmpty()){
                term_arSchoolAddress.setBackground(Color.PINK);
            }
            if(term_txtPrinciple.getText().isEmpty()){
                term_txtPrinciple.setBackground(Color.PINK);
            }
            if(term_txtPhone.getText().isEmpty()){
                term_txtPhone.setBackground(Color.PINK);
            }
            if(term_txtEmail.getText().isEmpty()){
                term_txtEmail.setBackground(Color.PINK);
            }

        }
        else{
            if(!sc2.validate(term_txtEmail.getText().trim())){
                //JOptionPane.showMessageDialog(this, "");
                term_txtEmail.setBackground(Color.PINK);
                JOptionPane.showMessageDialog(this, "Email is invalid");
            }
            else{
                if(schoolname||schoolPhone||schoolEmail||schoolPrinciple){
                    JOptionPane.showMessageDialog(this, "One or more fields are invalid");
                }
                else{
                    int schoolId=0;
                int provinceId=0;
                
                String schoolName=term_txtSchoolName.getText();
                String address=term_arSchoolAddress.getText();
                String principle=term_txtPrinciple.getText();
                String phone=term_txtPhone.getText();
                String email=term_txtEmail.getText();
                String prName=term_cmbPrSchool.getSelectedItem().toString();
                String division=term_cmbDvSchool.getSelectedItem().toString();
                
                try{
                    String scIdqry="select * from province where name='"+prName+"'";
                    pst=conn.prepareStatement(scIdqry);
                    rs=pst.executeQuery();
                    while(rs.next()){
                        
                        provinceId=Integer.parseInt(rs.getString("prID"));
                        
                    }
                }
                catch(Exception e){
                    System.out.println(e);
                }
                try{
                    school sc=new school(schoolName,address,principle,provinceId,phone,email,division);
                    schoolDA scDA=new schoolDA();
                    if(scDA.insertSchool(sc)){
                        JOptionPane.showMessageDialog(this, "Insertion successful");
                    }
                }
                catch(Exception e){

                }

                term_txtSchoolSearch.setText("");
                term_txtSchoolName.setText("");
                term_arSchoolAddress.setText("");
                term_txtPrinciple.setText("");
                term_txtPhone.setText("");
                term_txtEmail.setText("");
            }

        }
    }                              
    }//GEN-LAST:event_jButton2ActionPerformed

    private void term_cmbPrSchoolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_term_cmbPrSchoolMouseClicked
        
    }//GEN-LAST:event_term_cmbPrSchoolMouseClicked

    private void term_cmbPrSchoolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_term_cmbPrSchoolActionPerformed
        term_cmbDvSchool.removeAllItems();
        String passPrName=term_cmbPrSchool.getSelectedItem().toString();
        if(passPrName.equals("select province"))
        {
             
        }
        else{
        try{
            String qry="select * from province where name='"+passPrName+"'";
                    pst=conn.prepareStatement(qry);
                    rs=pst.executeQuery();
                    while(rs.next()){
                        passPrId=Integer.parseInt(rs.getString("prID"));
                    }        
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        loadDivision2Combo();
        }
    }//GEN-LAST:event_term_cmbPrSchoolActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backPanel;
    private javax.swing.JLabel background;
    private javax.swing.JLabel background1;
    private javax.swing.JLabel background2;
    private javax.swing.JLabel background4;
    private javax.swing.JButton batan;
    private javax.swing.JButton batan1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel jlabel34;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel pnlInsertPaperCounts;
    private javax.swing.JPanel pnlNewTender;
    private javax.swing.JPanel pnlParcellBills;
    private javax.swing.JPanel pnlSchools;
    private javax.swing.JPanel pnlTenderPapers;
    private javax.swing.JPanel pnlTenders;
    private javax.swing.JPanel pnlUploadDoc;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JTextArea term_arSchoolAddress;
    private javax.swing.JButton term_btnCount;
    private javax.swing.JButton term_btnUpdateDueDate;
    private com.toedter.calendar.JDateChooser term_calDDate;
    private com.toedter.calendar.JDateChooser term_calRDate;
    private com.toedter.calendar.JDateChooser term_calUpdateDueDate;
    private javax.swing.JComboBox term_cmbBillGrade;
    private javax.swing.JComboBox term_cmbBillSchool;
    private javax.swing.JComboBox term_cmbBillSubject;
    private javax.swing.JComboBox term_cmbDvSchool;
    private javax.swing.JComboBox term_cmbDvSelect;
    private javax.swing.JComboBox term_cmbFromGrade;
    private javax.swing.JComboBox term_cmbPaperGrade;
    private javax.swing.JComboBox term_cmbPaperSubject;
    private javax.swing.JComboBox term_cmbPrSchool;
    private javax.swing.JComboBox term_cmbPrSelect;
    private javax.swing.JComboBox term_cmbProvince;
    private javax.swing.JComboBox term_cmbSelectSchool;
    private javax.swing.JComboBox term_cmbTerm;
    private javax.swing.JComboBox term_cmbToGrade;
    private javax.swing.JLabel term_lblErrorAddress;
    private javax.swing.JLabel term_lblErrorDate;
    private javax.swing.JLabel term_lblErrorDirector;
    private javax.swing.JLabel term_lblErrorEmail;
    private javax.swing.JLabel term_lblErrorIncome;
    private javax.swing.JLabel term_lblErrorPhone;
    private javax.swing.JLabel term_lblErrorScName;
    private javax.swing.JLabel term_lblShowDueDate;
    private javax.swing.JLabel term_lblShowTenderHistoryId;
    private javax.swing.JLabel term_lblShowTenderId;
    private javax.swing.JLabel term_lblTick;
    private javax.swing.JTable term_tblBillTender;
    private javax.swing.JTable term_tblCurrentTenders;
    private javax.swing.JTable term_tblFinishedTenders;
    private javax.swing.JTable term_tblPaperCount;
    private javax.swing.JTable term_tblPapers;
    private javax.swing.JTable term_tblTenderPaper;
    private javax.swing.JTextField term_txtDirector;
    private javax.swing.JTextField term_txtEmail;
    private javax.swing.JTextField term_txtIncome;
    private javax.swing.JTextField term_txtNumPapers;
    private javax.swing.JTextField term_txtPhone;
    private javax.swing.JTextField term_txtPrinciple;
    private javax.swing.JTextField term_txtSchoolName;
    private javax.swing.JTextField term_txtSchoolSearch;
    private javax.swing.JTextArea term_txtarDescription;
    // End of variables declaration//GEN-END:variables
}
