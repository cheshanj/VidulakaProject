/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payrollAndLoanMngmt;

import Home.*;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;


//import yourfunctionname.yourinitialframe;
/**
 *
 * @author Sumudu Malshan
 * 
 * 
 */
public class PayrollGUI extends javax.swing.JInternalFrame {

    /**
     * Creates new form internal
     */
    
        Connection conn=null;
        PreparedStatement pst= null;
        ResultSet rs=null;
       // ResultSet rs1=null;        

   
         boolean mfbasicSal;
         boolean mfsinOtRate;
         boolean mfdoubleOtRate;
         boolean mfepf;
         boolean mfpieceRAte;
         boolean loanLoamAmt;
         boolean offprdSinOtRate;
         boolean offPrdDoubleOtRate;
         boolean offprdAttBonus;
         boolean offPrdSpecialBonus;
         boolean offPrdNetSal;
         boolean distSalSpecialBonus;
         boolean foldingSalAttBonus;
         boolean foldingSpecialBonus;
         boolean foldingSalNetal;
         boolean advAmt;
         
         
    public PayrollGUI() {
        initComponents();
            conn=dbconnect.connect();
           ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
            loadMfDeptName();
            loadMfTable();
            //txtmfPeiceRate.setText(String.valueOf(0));
            txtmfPeiceRate.setText("");
            txtmfPeiceRate.setEditable(false);
               
            off_prd_salCal p1=new off_prd_salCal();
            lblSalMonth.setText(p1.getMonth());
            loadOff_Prd_sal_Table();
            loadEmpNamecmb(); 
            lblsalIdDisplay.setText("");
               txtEmpId.setText("");
               txtEmpId.setText("");
               txtDesignation.setText("");
               txtBasicSalary.setText("");
               txtAtndnceBonus.setText("");
               txtAdvanceAmt.setText("");
               txtLoanAmt.setText("");
               txtDeptName.setText("");
               lblsalIdDisplay.setVisible(false);

              loadLoanTable();
              lblLoanIdDisplay.setVisible(false);
            
              loadAdvanceTable(); 
              advanceDetails ad=new advanceDetails();
              lblAdvMonthDisplay.setText(ad.getMonth());
              lblAdvIdDisplay.setVisible(false);
              
              pnlPayrollMasterFile.setVisible(true);
              pnlPayrollOffPrdStfSal.setVisible(false);
              pnlPayrollDistributionStaffSal.setVisible(false);
              pnlPayrollFoldingSatffl.setVisible(false);
              pnlPayrollLoan.setVisible(false);
              pnlPayrolAdvancel.setVisible(false);
              pnlPayrollWelcome.setVisible(false);
              
              loadDistEmpNamecmb();
              loadDistStfSalTable();
              distributionStaffSal d1= new distributionStaffSal();
              lbldistMonthDisplay.setText(d1.getMonth());
              lblDistSalIdDisplay.setVisible(false);
             // btnDistInsertSal.setEnabled(true);
              
              loadFoldingEmpName();
              foldingStaffSal f1=new foldingStaffSal();
              lblFoldingSaltMonthDisplay.setText(f1.getMonth());
              loadFoldingSalTbl();
    }
    
    
     public void loadMfTable()
    { 
           masterFileDAO mfDAO=new masterFileDAO(); 
           rs=mfDAO.loadMfTable();
           
           mfTable.setModel(DbUtils.resultSetToTableModel(rs));
            
    }
    
     
     
     public void loadMfDeptName()
    {            
             masterFileDAO mfDAO= new masterFileDAO();
             rs=mfDAO.loadMfDeptName();
            
            try{ 
            while(rs.next())
            {
                cmbmfDepName.addItem(rs.getString("deptName"));
            }
            
            }catch(Exception e)
            {
                System.out.println(e);
            }
            
    }
     
     
     public void loadOff_Prd_sal_Table()
    { 
           off_prd_salCalDAO ofDAO=new off_prd_salCalDAO();
           rs=ofDAO.loadOff_Prd_sal_Table();
        try {
            tbl_off_prd_sal.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
             System.out.println(e);
        }
        
    }
     
     public void loadEmpNamecmb()
    {            
           off_prd_salCalDAO ofDAO=new off_prd_salCalDAO();
           rs=ofDAO.loadEmpNamecmb();
           
            try {
            
                while(rs.next())
            {
              cmbEmpName.addItem(rs.getString("name"));
                
            }
            }catch (SQLException ex) {
                Logger.getLogger(PayrollGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
       
    }
     
     
     public void loadLoanTable()
    { 
         loanHandlingDAO lnDAO= new loanHandlingDAO();
         rs=lnDAO.loadLoanTable();
        try 
        {
            tblLoan.setModel(DbUtils.resultSetToTableModel(rs));
            
        }
        catch (Exception e)
        { 
           System.out.println(e);  
            
        }
    }
     
     
     public void loadAdvanceTable()
    { 
        advanceDetailsDAO adDAO=new advanceDetailsDAO();
        rs=adDAO.loadAdvanceTable();
        try 
        {
            tblAdvance.setModel(DbUtils.resultSetToTableModel(rs));
            
        }
        catch (Exception e)
        { 
           System.out.println(e);  
            
        }
    }
     
     
     public void loadDistStfSalTable()
    { 
           distributionStaffSalDAO disDAO=new distributionStaffSalDAO();
           rs=disDAO.loadDistStfSalTable();
         
        try 
        {
             tblDistributionSal.setModel(DbUtils.resultSetToTableModel(rs));
            
        }
        catch (Exception e)
        { 
           System.out.println(e);  
            
        }
    }
     
     
     public void loadDistEmpNamecmb()
    {            
              distributionStaffSalDAO disDAO=new distributionStaffSalDAO();
              rs=disDAO.loadDistEmpNamecmb();
        
            try {
                 while(rs.next())
                {
                cmbDistSalEmpName.addItem(rs.getString("name"));
                
                }
            }catch (SQLException ex) {
                Logger.getLogger(PayrollGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
          
    }
     
     
     public void loadFoldingEmpName() 
     {
           foldingStaffSalDAO fDAO=new foldingStaffSalDAO();
           rs=fDAO.loadFoldingEmpNameCmb();
          
         try {
                              
              while(rs.next()){     
                cmbFoldingSalEmpName.addItem(rs.getString("name"));
              }
         }catch (SQLException ex) {
                Logger.getLogger(PayrollGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

           
             
     }
     
     
     public void loadFoldingSalTbl()
    { 
        foldingStaffSalDAO fDAO=new foldingStaffSalDAO();
        rs=fDAO.loadFoldingSalTbl();
        try 
        {
            tblFoldingStfSal.setModel(DbUtils.resultSetToTableModel(rs));
            
        }
        catch (Exception e)
        { 
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

        backPanel = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        btnPrd_off_stf = new javax.swing.JButton();
        btnMasterFile = new javax.swing.JButton();
        btnFoldingStaff = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        btnDistributionStf = new javax.swing.JButton();
        rightPanel = new javax.swing.JPanel();
        pnlPayrollMasterFile = new javax.swing.JPanel();
        txtmfEmpID = new javax.swing.JTextField();
        txtmfSingleOtRate = new javax.swing.JTextField();
        txtmfEpfRate = new javax.swing.JTextField();
        txtmfPeiceRate = new javax.swing.JTextField();
        txtSearch = new javax.swing.JTextField();
        btnDeleteMfEntry = new javax.swing.JButton();
        btnMfSearch = new javax.swing.JButton();
        btnUpdateMfEntry = new javax.swing.JButton();
        btnAddMfEntry = new javax.swing.JButton();
        cmbmfEmpName = new javax.swing.JComboBox();
        lblSingalOTRate = new javax.swing.JLabel();
        lblDoubleOTRate = new javax.swing.JLabel();
        lblDepartment = new javax.swing.JLabel();
        lblPieceRate = new javax.swing.JLabel();
        lblBasicSal = new javax.swing.JLabel();
        lblmfEmpID = new javax.swing.JLabel();
        txtmfDoubleOtRate = new javax.swing.JTextField();
        lblmfErrorBasicSal = new javax.swing.JLabel();
        lblmfErrorEpf = new javax.swing.JLabel();
        lbl_mf_Id = new javax.swing.JLabel();
        lblMfErrorPieceRate = new javax.swing.JLabel();
        lblMfErrorDoubleOt = new javax.swing.JLabel();
        lblMfErrorSinOt = new javax.swing.JLabel();
        cmbmfDepName = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        mfTable = new javax.swing.JTable();
        txtmfBasicSal = new javax.swing.JTextField();
        txtmfEmpPosition = new javax.swing.JTextField();
        lblmfEmpPost = new javax.swing.JLabel();
        btnMfClear = new javax.swing.JButton();
        lblEPFRate = new javax.swing.JLabel();
        lblMfName = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pnlPayrollOffPrdStfSal = new javax.swing.JPanel();
        lblDesignation = new javax.swing.JLabel();
        lblMonth = new javax.swing.JLabel();
        lblsalIdDisplay = new javax.swing.JLabel();
        lblEmpID = new javax.swing.JLabel();
        lblEmpName = new javax.swing.JLabel();
        lblDeptName = new javax.swing.JLabel();
        lblBasicSalary = new javax.swing.JLabel();
        cmbEmpName = new javax.swing.JComboBox();
        txtNetSal = new javax.swing.JTextField();
        txtEmpId = new javax.swing.JTextField();
        txtDesignation = new javax.swing.JTextField();
        txtBasicSalary = new javax.swing.JTextField();
        lblSinOTHrs = new javax.swing.JLabel();
        txtSinOtHrs = new javax.swing.JTextField();
        lblDoubleOTHrs = new javax.swing.JLabel();
        txtDoubleOtHrs = new javax.swing.JTextField();
        lblAtdnceBonus = new javax.swing.JLabel();
        txtAtndnceBonus = new javax.swing.JTextField();
        lblAdvanceAmt = new javax.swing.JLabel();
        txtAdvanceAmt = new javax.swing.JTextField();
        lblLoanAmt = new javax.swing.JLabel();
        txtLoanAmt = new javax.swing.JTextField();
        lblSalMonth = new javax.swing.JLabel();
        btnDeleteSal = new javax.swing.JButton();
        btnInsetSal = new javax.swing.JButton();
        btnUpdateSal = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_off_prd_sal = new javax.swing.JTable();
        lblSpecialBonus = new javax.swing.JLabel();
        btnCalcSal = new javax.swing.JButton();
        txtSpecialBonus = new javax.swing.JTextField();
        txtPrdOffSearch = new javax.swing.JTextField();
        btnPrdOffSearch = new javax.swing.JButton();
        txtDeptName = new javax.swing.JTextField();
        btnPrdOffSalClear = new javax.swing.JButton();
        lblPrdOffSalName = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnPrdOffSalGenPaySheet = new javax.swing.JButton();
        lblPrdOffPaySheetMon = new javax.swing.JLabel();
        txtPrdOffPaySheetMon = new javax.swing.JTextField();
        lblBkgroundP3 = new javax.swing.JLabel();
        pnlPayrollDistributionStaffSal = new javax.swing.JPanel();
        lblDistSalIdDisplay = new javax.swing.JLabel();
        lblDistEmpId = new javax.swing.JLabel();
        lblDistEmpName = new javax.swing.JLabel();
        lblDistDeptName = new javax.swing.JLabel();
        lblDistSpecialBonus = new javax.swing.JLabel();
        lblDistBasicSal = new javax.swing.JLabel();
        txtDistSalDesignation = new javax.swing.JTextField();
        txtDistSalNetSal = new javax.swing.JTextField();
        txtDistSalEmpID = new javax.swing.JTextField();
        lblDistAdvAmount = new javax.swing.JLabel();
        lbldistMonthDisplay = new javax.swing.JLabel();
        txtDistSalSpecialBonus = new javax.swing.JTextField();
        txtDistSalBasicSal = new javax.swing.JTextField();
        txtDistSearch = new javax.swing.JTextField();
        btnDistSearch = new javax.swing.JButton();
        txtDistSalAdvAmount = new javax.swing.JTextField();
        btnDistDeleteSal = new javax.swing.JButton();
        btnDistUpdateSal = new javax.swing.JButton();
        btnDistInsertSal = new javax.swing.JButton();
        lbldistMonth = new javax.swing.JLabel();
        btnDistCalcSal = new javax.swing.JButton();
        cmbDistSalEmpName = new javax.swing.JComboBox();
        txtDistSalDeptName = new javax.swing.JTextField();
        lblDistDesignation = new javax.swing.JLabel();
        btnDistDeleteSal1 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDistributionSal = new javax.swing.JTable();
        lblDisStaffSalName = new javax.swing.JLabel();
        pnlDisPaySheet = new javax.swing.JPanel();
        btnDisPaySheet = new javax.swing.JButton();
        lblPrdOffPaySheetMon1 = new javax.swing.JLabel();
        txtDisSheetMon = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        pnlPayrollFoldingSatffl = new javax.swing.JPanel();
        lblFoldingSaltMonthDisplay = new javax.swing.JLabel();
        lblFoldingSaltMonth = new javax.swing.JLabel();
        lblFoldingSalEmpName = new javax.swing.JLabel();
        cmbFoldingSalEmpName = new javax.swing.JComboBox();
        txtFoldingSalEmpId = new javax.swing.JTextField();
        lblFoldingSalEmpId = new javax.swing.JLabel();
        lblFoldingSalDesignation = new javax.swing.JLabel();
        txtFoldingSalDesignation = new javax.swing.JTextField();
        lblFoldingSalDeptName = new javax.swing.JLabel();
        txtFoldingSalDeptName = new javax.swing.JTextField();
        txtFoldingSalNetSal = new javax.swing.JTextField();
        btnFoldingSalCalcSal = new javax.swing.JButton();
        lblFoldingSalAttBonus = new javax.swing.JLabel();
        txtFoldingSalAttendanceBonus = new javax.swing.JTextField();
        lblFoldingSalBasicSal = new javax.swing.JLabel();
        txtFoldingSalNoOfPieces = new javax.swing.JTextField();
        txtFoldingSalBasicSal = new javax.swing.JTextField();
        lblFoldingSalNoOfPieces = new javax.swing.JLabel();
        txtFoldingSalPieceRate = new javax.swing.JTextField();
        lblFoldingSalAdvAmt = new javax.swing.JLabel();
        lblFoldingSalPieceRate = new javax.swing.JLabel();
        txtFoldingSalAdvAmt = new javax.swing.JTextField();
        lblFoldngSalName = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblFoldingStfSal = new javax.swing.JTable();
        txtFoldingSalSpecialBonus = new javax.swing.JTextField();
        lblFoldingSalSpecialBonus = new javax.swing.JLabel();
        btnFoldngSalInsert = new javax.swing.JButton();
        btnFoldingSalUpdate = new javax.swing.JButton();
        btnFoldingSalDelete = new javax.swing.JButton();
        txtFoldingSalSearch = new javax.swing.JTextField();
        btnFoldingSalSearch = new javax.swing.JButton();
        btnFoldingSalClear = new javax.swing.JButton();
        lblfoldingStaffSalID = new javax.swing.JLabel();
        pnlFoldingPaySheet = new javax.swing.JPanel();
        btnFoldingPaySheet = new javax.swing.JButton();
        lblFoldingSheetMon = new javax.swing.JLabel();
        txtFoldingSheetMon = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        pnlPayrolAdvancel = new javax.swing.JPanel();
        lblAdvIdDisplay = new javax.swing.JLabel();
        lblAdvEmpID = new javax.swing.JLabel();
        lblAdvName = new javax.swing.JLabel();
        lblAdvDeptName = new javax.swing.JLabel();
        lblAdvAdvAmount = new javax.swing.JLabel();
        lblAdvDate = new javax.swing.JLabel();
        lblAdvStatus = new javax.swing.JLabel();
        txtAdvEmpName = new javax.swing.JTextField();
        txtAdvEmpId = new javax.swing.JTextField();
        txtAdvDeptName = new javax.swing.JTextField();
        txtAdvAdvAmount = new javax.swing.JTextField();
        cmbAdvStatus = new javax.swing.JComboBox();
        lblAdvMonth = new javax.swing.JLabel();
        lblAdvMonthDisplay = new javax.swing.JLabel();
        btnAdvUpdateEntry = new javax.swing.JButton();
        btnAdvDeleteEntry = new javax.swing.JButton();
        btnAdvAddEntry = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblAdvance = new javax.swing.JTable();
        btnAdverach = new javax.swing.JButton();
        txtAdvSearch = new javax.swing.JTextField();
        txtAdvDate = new com.toedter.calendar.JDateChooser();
        lblAdvNameDisplay = new javax.swing.JLabel();
        btnAdvClear = new javax.swing.JButton();
        pnlAdvanceReport = new javax.swing.JPanel();
        btnAdvanceReport = new javax.swing.JButton();
        lblAdvanceReport = new javax.swing.JLabel();
        txtAdvanceReport = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        pnlPayrollLoan = new javax.swing.JPanel();
        lblLoanIdDisplay = new javax.swing.JLabel();
        lblLoanStatus = new javax.swing.JLabel();
        txtLoanEmpId = new javax.swing.JTextField();
        txtLoanEmpName = new javax.swing.JTextField();
        txtLoanDeptName = new javax.swing.JTextField();
        lblLoanEmpId = new javax.swing.JLabel();
        lblLoanEmpName = new javax.swing.JLabel();
        lblLoanDeptName = new javax.swing.JLabel();
        lblLoanLoanAmt = new javax.swing.JLabel();
        txtLoanLoanAmt = new javax.swing.JTextField();
        txtMonthlyDue = new javax.swing.JTextField();
        txtLoanDue = new javax.swing.JTextField();
        lblLentDate = new javax.swing.JLabel();
        lblMonthlyDue = new javax.swing.JLabel();
        lblLoanDue = new javax.swing.JLabel();
        cmbLoanStatus = new javax.swing.JComboBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLoan = new javax.swing.JTable();
        btnDeleteLoan = new javax.swing.JButton();
        btnInsertLoan = new javax.swing.JButton();
        btnUpdateLoan = new javax.swing.JButton();
        txtLoanSearch = new javax.swing.JTextField();
        btnloanSearch = new javax.swing.JButton();
        txtLentDate = new com.toedter.calendar.JDateChooser();
        lblLoanName = new javax.swing.JLabel();
        btnLoanClear = new javax.swing.JButton();
        pnlFoldingPaySheet1 = new javax.swing.JPanel();
        btnLoanSheetMon = new javax.swing.JButton();
        lblLoanSheetMon = new javax.swing.JLabel();
        txtLoanSheetMon = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        pnlPayrollWelcome = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
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

        btnPrd_off_stf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/payrollAndLoanMngmt/images/op.png"))); // NOI18N
        btnPrd_off_stf.setBorderPainted(false);
        btnPrd_off_stf.setContentAreaFilled(false);
        btnPrd_off_stf.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnPrd_off_stf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrd_off_stfActionPerformed(evt);
            }
        });
        leftPanel.add(btnPrd_off_stf, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 100, -1));

        btnMasterFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/payrollAndLoanMngmt/images/mf.png"))); // NOI18N
        btnMasterFile.setBorderPainted(false);
        btnMasterFile.setContentAreaFilled(false);
        btnMasterFile.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnMasterFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasterFileActionPerformed(evt);
            }
        });
        leftPanel.add(btnMasterFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 90));

        btnFoldingStaff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/payrollAndLoanMngmt/images/folding.png"))); // NOI18N
        btnFoldingStaff.setBorderPainted(false);
        btnFoldingStaff.setContentAreaFilled(false);
        btnFoldingStaff.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnFoldingStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFoldingStaffActionPerformed(evt);
            }
        });
        leftPanel.add(btnFoldingStaff, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 100, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/payrollAndLoanMngmt/images/loan.png"))); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        leftPanel.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 100, -1));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/payrollAndLoanMngmt/images/adv.png"))); // NOI18N
        jButton6.setBorderPainted(false);
        jButton6.setContentAreaFilled(false);
        jButton6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        leftPanel.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 100, -1));

        btnDistributionStf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/payrollAndLoanMngmt/images/distribution.png"))); // NOI18N
        btnDistributionStf.setBorderPainted(false);
        btnDistributionStf.setContentAreaFilled(false);
        btnDistributionStf.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnDistributionStf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDistributionStfActionPerformed(evt);
            }
        });
        leftPanel.add(btnDistributionStf, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 100, -1));

        rightPanel.setMaximumSize(new java.awt.Dimension(910, 570));
        rightPanel.setMinimumSize(new java.awt.Dimension(910, 570));
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new java.awt.Dimension(910, 570));
        rightPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlPayrollMasterFile.setBackground(new java.awt.Color(0, 0, 255));
        pnlPayrollMasterFile.setMaximumSize(new java.awt.Dimension(915, 570));
        pnlPayrollMasterFile.setMinimumSize(new java.awt.Dimension(915, 570));
        pnlPayrollMasterFile.setOpaque(false);
        pnlPayrollMasterFile.setPreferredSize(new java.awt.Dimension(915, 570));
        pnlPayrollMasterFile.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtmfEmpID.setEditable(false);
        txtmfEmpID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtmfEmpIDMouseClicked(evt);
            }
        });
        txtmfEmpID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmfEmpIDActionPerformed(evt);
            }
        });
        txtmfEmpID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmfEmpIDKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmfEmpIDKeyReleased(evt);
            }
        });
        pnlPayrollMasterFile.add(txtmfEmpID, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 140, -1));

        txtmfSingleOtRate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtmfSingleOtRateMouseClicked(evt);
            }
        });
        txtmfSingleOtRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmfSingleOtRateKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmfSingleOtRateKeyTyped(evt);
            }
        });
        pnlPayrollMasterFile.add(txtmfSingleOtRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 130, -1));

        txtmfEpfRate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtmfEpfRateMouseClicked(evt);
            }
        });
        txtmfEpfRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmfEpfRateKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmfEpfRateKeyReleased(evt);
            }
        });
        pnlPayrollMasterFile.add(txtmfEpfRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 170, 130, -1));

        txtmfPeiceRate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtmfPeiceRateMouseClicked(evt);
            }
        });
        txtmfPeiceRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmfPeiceRateKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmfPeiceRateKeyTyped(evt);
            }
        });
        pnlPayrollMasterFile.add(txtmfPeiceRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 210, 130, -1));

        txtSearch.setBackground(new java.awt.Color(204, 255, 204));
        txtSearch.setToolTipText("search (Dept Name)");
        txtSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtSearchMouseEntered(evt);
            }
        });
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });
        pnlPayrollMasterFile.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 30, 180, -1));

        btnDeleteMfEntry.setText("Delete Entry");
        btnDeleteMfEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteMfEntryActionPerformed(evt);
            }
        });
        pnlPayrollMasterFile.add(btnDeleteMfEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 170, 100, 30));

        btnMfSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/payrollAndLoanMngmt/images/searchbtn.jpg"))); // NOI18N
        btnMfSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMfSearchActionPerformed(evt);
            }
        });
        pnlPayrollMasterFile.add(btnMfSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 20, 40, 40));

        btnUpdateMfEntry.setText("Update Entry");
        btnUpdateMfEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateMfEntryActionPerformed(evt);
            }
        });
        pnlPayrollMasterFile.add(btnUpdateMfEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 120, 100, 30));

        btnAddMfEntry.setText("Add Entry");
        btnAddMfEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMfEntryActionPerformed(evt);
            }
        });
        pnlPayrollMasterFile.add(btnAddMfEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 70, 100, 30));

        cmbmfEmpName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Employee", " " }));
        cmbmfEmpName.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cmbmfEmpNamePopupMenuWillBecomeVisible(evt);
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbmfEmpName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbmfEmpNameActionPerformed(evt);
            }
        });
        pnlPayrollMasterFile.add(cmbmfEmpName, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 140, 30));

        lblSingalOTRate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSingalOTRate.setForeground(new java.awt.Color(255, 255, 255));
        lblSingalOTRate.setText("Singal OT Rate");
        pnlPayrollMasterFile.add(lblSingalOTRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 90, 90, 20));

        lblDoubleOTRate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDoubleOTRate.setForeground(new java.awt.Color(255, 255, 255));
        lblDoubleOTRate.setText("Double OT Rate");
        pnlPayrollMasterFile.add(lblDoubleOTRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, 100, 20));

        lblDepartment.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDepartment.setForeground(new java.awt.Color(255, 255, 255));
        lblDepartment.setText("Deptartment");
        pnlPayrollMasterFile.add(lblDepartment, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 80, 20));

        lblPieceRate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPieceRate.setForeground(new java.awt.Color(255, 255, 255));
        lblPieceRate.setText("piece Rate");
        pnlPayrollMasterFile.add(lblPieceRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 210, 70, 20));

        lblBasicSal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblBasicSal.setForeground(new java.awt.Color(255, 255, 255));
        lblBasicSal.setText("Basic salary");
        pnlPayrollMasterFile.add(lblBasicSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, 70, 20));

        lblmfEmpID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblmfEmpID.setForeground(new java.awt.Color(255, 255, 255));
        lblmfEmpID.setText("Employee ID");
        pnlPayrollMasterFile.add(lblmfEmpID, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 80, 20));

        txtmfDoubleOtRate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtmfDoubleOtRateMouseClicked(evt);
            }
        });
        txtmfDoubleOtRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmfDoubleOtRateKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmfDoubleOtRateKeyTyped(evt);
            }
        });
        pnlPayrollMasterFile.add(txtmfDoubleOtRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 130, 130, -1));

        lblmfErrorBasicSal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblmfErrorBasicSal.setForeground(new java.awt.Color(255, 0, 0));
        pnlPayrollMasterFile.add(lblmfErrorBasicSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 180, 20, 20));

        lblmfErrorEpf.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblmfErrorEpf.setForeground(new java.awt.Color(255, 51, 51));
        pnlPayrollMasterFile.add(lblmfErrorEpf, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 130, 20, 20));

        lbl_mf_Id.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_mf_Id.setForeground(new java.awt.Color(255, 255, 255));
        lbl_mf_Id.setText("Employee Name");
        pnlPayrollMasterFile.add(lbl_mf_Id, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 100, 20));

        lblMfErrorPieceRate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMfErrorPieceRate.setForeground(new java.awt.Color(255, 51, 51));
        pnlPayrollMasterFile.add(lblMfErrorPieceRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 180, 20, 20));

        lblMfErrorDoubleOt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMfErrorDoubleOt.setForeground(new java.awt.Color(255, 51, 51));
        pnlPayrollMasterFile.add(lblMfErrorDoubleOt, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 80, 20, 20));

        lblMfErrorSinOt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMfErrorSinOt.setForeground(new java.awt.Color(255, 51, 51));
        pnlPayrollMasterFile.add(lblMfErrorSinOt, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 30, 20, 20));

        cmbmfDepName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Department", " " }));
        cmbmfDepName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbmfDepNameActionPerformed(evt);
            }
        });
        pnlPayrollMasterFile.add(cmbmfDepName, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 140, 30));

        mfTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Deptarment Name", "Employee Name", "Employee ID", "Designation", "Basic Salary", "Single OT Rate", "Double OT Rate", "EPF Rate", "Piece Rate"
            }
        ));
        mfTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mfTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(mfTable);

        pnlPayrollMasterFile.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 880, 230));

        txtmfBasicSal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtmfBasicSalMouseClicked(evt);
            }
        });
        txtmfBasicSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmfBasicSalActionPerformed(evt);
            }
        });
        txtmfBasicSal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmfBasicSalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmfBasicSalKeyTyped(evt);
            }
        });
        pnlPayrollMasterFile.add(txtmfBasicSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, 140, -1));

        txtmfEmpPosition.setEditable(false);
        txtmfEmpPosition.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtmfEmpPositionMouseClicked(evt);
            }
        });
        txtmfEmpPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmfEmpPositionActionPerformed(evt);
            }
        });
        txtmfEmpPosition.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmfEmpPositionKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmfEmpPositionKeyTyped(evt);
            }
        });
        pnlPayrollMasterFile.add(txtmfEmpPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 140, -1));

        lblmfEmpPost.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblmfEmpPost.setForeground(new java.awt.Color(255, 255, 255));
        lblmfEmpPost.setText("Employee Position");
        pnlPayrollMasterFile.add(lblmfEmpPost, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 120, 20));

        btnMfClear.setText("Clear All");
        btnMfClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMfClearActionPerformed(evt);
            }
        });
        pnlPayrollMasterFile.add(btnMfClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 220, 100, 30));

        lblEPFRate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEPFRate.setForeground(new java.awt.Color(255, 255, 255));
        lblEPFRate.setText("EPF Rate");
        pnlPayrollMasterFile.add(lblEPFRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 170, 60, 20));

        lblMfName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMfName.setForeground(new java.awt.Color(255, 255, 255));
        lblMfName.setText("Master File");
        pnlPayrollMasterFile.add(lblMfName, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 100, 20));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        pnlPayrollMasterFile.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(pnlPayrollMasterFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlPayrollOffPrdStfSal.setBackground(new java.awt.Color(255, 255, 255));
        pnlPayrollOffPrdStfSal.setMaximumSize(new java.awt.Dimension(915, 570));
        pnlPayrollOffPrdStfSal.setMinimumSize(new java.awt.Dimension(915, 570));
        pnlPayrollOffPrdStfSal.setOpaque(false);
        pnlPayrollOffPrdStfSal.setPreferredSize(new java.awt.Dimension(915, 570));
        pnlPayrollOffPrdStfSal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDesignation.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDesignation.setForeground(new java.awt.Color(255, 255, 255));
        lblDesignation.setText("Designation");
        pnlPayrollOffPrdStfSal.add(lblDesignation, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, -1, 20));

        lblMonth.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMonth.setForeground(new java.awt.Color(255, 255, 255));
        lblMonth.setText("Month");
        pnlPayrollOffPrdStfSal.add(lblMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 60, 20));

        lblsalIdDisplay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblsalIdDisplay.setForeground(new java.awt.Color(255, 255, 255));
        pnlPayrollOffPrdStfSal.add(lblsalIdDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 330, 70, 20));

        lblEmpID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEmpID.setForeground(new java.awt.Color(255, 255, 255));
        lblEmpID.setText("Employee ID");
        pnlPayrollOffPrdStfSal.add(lblEmpID, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, -1, 20));

        lblEmpName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEmpName.setForeground(new java.awt.Color(255, 255, 255));
        lblEmpName.setText("Employee Name");
        pnlPayrollOffPrdStfSal.add(lblEmpName, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, 20));

        lblDeptName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDeptName.setForeground(new java.awt.Color(255, 255, 255));
        lblDeptName.setText("Department Name");
        pnlPayrollOffPrdStfSal.add(lblDeptName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, 20));

        lblBasicSalary.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblBasicSalary.setForeground(new java.awt.Color(255, 255, 255));
        lblBasicSalary.setText("Basic Salary");
        pnlPayrollOffPrdStfSal.add(lblBasicSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 80, 20));

        cmbEmpName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select employee" }));
        cmbEmpName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbEmpNameMouseClicked(evt);
            }
        });
        cmbEmpName.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbEmpNamePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbEmpName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEmpNameActionPerformed(evt);
            }
        });
        cmbEmpName.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                cmbEmpNameInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        pnlPayrollOffPrdStfSal.add(cmbEmpName, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 190, 30));

        txtNetSal.setEditable(false);
        txtNetSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNetSalActionPerformed(evt);
            }
        });
        pnlPayrollOffPrdStfSal.add(txtNetSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 290, 140, 30));

        txtEmpId.setEditable(false);
        txtEmpId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpIdActionPerformed(evt);
            }
        });
        pnlPayrollOffPrdStfSal.add(txtEmpId, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 140, 30));

        txtDesignation.setEditable(false);
        pnlPayrollOffPrdStfSal.add(txtDesignation, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, 140, 30));

        txtBasicSalary.setEditable(false);
        txtBasicSalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBasicSalaryActionPerformed(evt);
            }
        });
        pnlPayrollOffPrdStfSal.add(txtBasicSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 140, 30));

        lblSinOTHrs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSinOTHrs.setForeground(new java.awt.Color(255, 255, 255));
        lblSinOTHrs.setText("Single OT Hours");
        pnlPayrollOffPrdStfSal.add(lblSinOTHrs, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 100, 20));

        txtSinOtHrs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSinOtHrsMouseClicked(evt);
            }
        });
        txtSinOtHrs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSinOtHrsKeyReleased(evt);
            }
        });
        pnlPayrollOffPrdStfSal.add(txtSinOtHrs, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 140, 30));

        lblDoubleOTHrs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDoubleOTHrs.setForeground(new java.awt.Color(255, 255, 255));
        lblDoubleOTHrs.setText("Double OT Hours");
        pnlPayrollOffPrdStfSal.add(lblDoubleOTHrs, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 100, 110, 20));

        txtDoubleOtHrs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDoubleOtHrsMouseClicked(evt);
            }
        });
        txtDoubleOtHrs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDoubleOtHrsActionPerformed(evt);
            }
        });
        txtDoubleOtHrs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDoubleOtHrsKeyReleased(evt);
            }
        });
        pnlPayrollOffPrdStfSal.add(txtDoubleOtHrs, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 90, 140, 30));

        lblAtdnceBonus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAtdnceBonus.setForeground(new java.awt.Color(255, 255, 255));
        lblAtdnceBonus.setText("Attendance Bonus");
        pnlPayrollOffPrdStfSal.add(lblAtdnceBonus, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 140, 120, 20));

        txtAtndnceBonus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAtndnceBonusMouseClicked(evt);
            }
        });
        txtAtndnceBonus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAtndnceBonusKeyReleased(evt);
            }
        });
        pnlPayrollOffPrdStfSal.add(txtAtndnceBonus, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 130, 140, 30));

        lblAdvanceAmt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAdvanceAmt.setForeground(new java.awt.Color(255, 255, 255));
        lblAdvanceAmt.setText("Advance amount");
        pnlPayrollOffPrdStfSal.add(lblAdvanceAmt, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 180, 110, 20));

        txtAdvanceAmt.setEditable(false);
        pnlPayrollOffPrdStfSal.add(txtAdvanceAmt, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 170, 140, 30));

        lblLoanAmt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLoanAmt.setForeground(new java.awt.Color(255, 255, 255));
        lblLoanAmt.setText("Loan Amount");
        pnlPayrollOffPrdStfSal.add(lblLoanAmt, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 220, -1, 20));

        txtLoanAmt.setEditable(false);
        pnlPayrollOffPrdStfSal.add(txtLoanAmt, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 210, 140, 30));

        lblSalMonth.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblSalMonth.setForeground(new java.awt.Color(255, 255, 255));
        pnlPayrollOffPrdStfSal.add(lblSalMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 200, 20));

        btnDeleteSal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDeleteSal.setText("Delete Entry");
        btnDeleteSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSalActionPerformed(evt);
            }
        });
        pnlPayrollOffPrdStfSal.add(btnDeleteSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 170, 150, 30));

        btnInsetSal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnInsetSal.setText("Insert Entry");
        btnInsetSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsetSalActionPerformed(evt);
            }
        });
        pnlPayrollOffPrdStfSal.add(btnInsetSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 90, 150, 30));

        btnUpdateSal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUpdateSal.setText("Update Entry");
        btnUpdateSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateSalActionPerformed(evt);
            }
        });
        pnlPayrollOffPrdStfSal.add(btnUpdateSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 130, 150, 30));

        tbl_off_prd_sal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Salary ID", "Employee ID", "Employee Name", "Department Name", "Designation", "Basic Salary", "Single OT Hours", "Double OT Hours", "AttendanceBonus", "Advance Amountl", "Loan Amount", "Special Bonus", "Net salary"
            }
        ));
        tbl_off_prd_sal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_off_prd_salMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_off_prd_sal);

        pnlPayrollOffPrdStfSal.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 890, 180));

        lblSpecialBonus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSpecialBonus.setForeground(new java.awt.Color(255, 255, 255));
        lblSpecialBonus.setText("Special Bonus");
        pnlPayrollOffPrdStfSal.add(lblSpecialBonus, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 250, -1, 20));

        btnCalcSal.setText("calculate Net Salary");
        btnCalcSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcSalActionPerformed(evt);
            }
        });
        pnlPayrollOffPrdStfSal.add(btnCalcSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 290, 140, 30));

        txtSpecialBonus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSpecialBonusMouseClicked(evt);
            }
        });
        txtSpecialBonus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSpecialBonusKeyReleased(evt);
            }
        });
        pnlPayrollOffPrdStfSal.add(txtSpecialBonus, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 250, 140, 30));

        txtPrdOffSearch.setBackground(new java.awt.Color(204, 255, 204));
        txtPrdOffSearch.setToolTipText("search (month,Year)");
        txtPrdOffSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtPrdOffSearchMouseEntered(evt);
            }
        });
        txtPrdOffSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrdOffSearchActionPerformed(evt);
            }
        });
        txtPrdOffSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrdOffSearchKeyTyped(evt);
            }
        });
        pnlPayrollOffPrdStfSal.add(txtPrdOffSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 20, 160, -1));

        btnPrdOffSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/payrollAndLoanMngmt/images/searchbtn.jpg"))); // NOI18N
        btnPrdOffSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrdOffSearchActionPerformed(evt);
            }
        });
        pnlPayrollOffPrdStfSal.add(btnPrdOffSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 10, 40, 40));

        txtDeptName.setEditable(false);
        pnlPayrollOffPrdStfSal.add(txtDeptName, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 140, 30));

        btnPrdOffSalClear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPrdOffSalClear.setText("Clear All");
        btnPrdOffSalClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrdOffSalClearActionPerformed(evt);
            }
        });
        pnlPayrollOffPrdStfSal.add(btnPrdOffSalClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 210, 150, 30));

        lblPrdOffSalName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPrdOffSalName.setForeground(new java.awt.Color(255, 255, 255));
        lblPrdOffSalName.setText("Production and Office Staff ");
        pnlPayrollOffPrdStfSal.add(lblPrdOffSalName, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 250, 20));

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnPrdOffSalGenPaySheet.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPrdOffSalGenPaySheet.setText("Generate PaySheet");
        btnPrdOffSalGenPaySheet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrdOffSalGenPaySheetActionPerformed(evt);
            }
        });

        lblPrdOffPaySheetMon.setText("Month/Year");

        txtPrdOffPaySheetMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrdOffPaySheetMonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPrdOffPaySheetMon, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnPrdOffSalGenPaySheet, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addComponent(txtPrdOffPaySheetMon)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(lblPrdOffPaySheetMon, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(txtPrdOffPaySheetMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrdOffSalGenPaySheet, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlPayrollOffPrdStfSal.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 260, 200, 90));

        lblBkgroundP3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        pnlPayrollOffPrdStfSal.add(lblBkgroundP3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(pnlPayrollOffPrdStfSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlPayrollDistributionStaffSal.setBackground(new java.awt.Color(102, 255, 102));
        pnlPayrollDistributionStaffSal.setMaximumSize(new java.awt.Dimension(915, 570));
        pnlPayrollDistributionStaffSal.setMinimumSize(new java.awt.Dimension(915, 570));
        pnlPayrollDistributionStaffSal.setOpaque(false);
        pnlPayrollDistributionStaffSal.setPreferredSize(new java.awt.Dimension(915, 570));
        pnlPayrollDistributionStaffSal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDistSalIdDisplay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDistSalIdDisplay.setForeground(new java.awt.Color(255, 255, 255));
        pnlPayrollDistributionStaffSal.add(lblDistSalIdDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 290, 140, 20));

        lblDistEmpId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDistEmpId.setForeground(new java.awt.Color(255, 255, 255));
        lblDistEmpId.setText("Employee ID");
        pnlPayrollDistributionStaffSal.add(lblDistEmpId, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, -1, 20));

        lblDistEmpName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDistEmpName.setForeground(new java.awt.Color(255, 255, 255));
        lblDistEmpName.setText("Employee Name");
        pnlPayrollDistributionStaffSal.add(lblDistEmpName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, 20));

        lblDistDeptName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDistDeptName.setForeground(new java.awt.Color(255, 255, 255));
        lblDistDeptName.setText("Department Name");
        pnlPayrollDistributionStaffSal.add(lblDistDeptName, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 120, 20));

        lblDistSpecialBonus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDistSpecialBonus.setForeground(new java.awt.Color(255, 255, 255));
        lblDistSpecialBonus.setText("Special Bonus");
        pnlPayrollDistributionStaffSal.add(lblDistSpecialBonus, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 180, -1, 20));

        lblDistBasicSal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDistBasicSal.setForeground(new java.awt.Color(255, 255, 255));
        lblDistBasicSal.setText("Basic Salary");
        pnlPayrollDistributionStaffSal.add(lblDistBasicSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, -1, 20));

        txtDistSalDesignation.setEditable(false);
        txtDistSalDesignation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDistSalDesignationActionPerformed(evt);
            }
        });
        txtDistSalDesignation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDistSalDesignationKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDistSalDesignationKeyReleased(evt);
            }
        });
        pnlPayrollDistributionStaffSal.add(txtDistSalDesignation, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, 140, 30));

        txtDistSalNetSal.setEditable(false);
        txtDistSalNetSal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDistSalNetSalMouseClicked(evt);
            }
        });
        txtDistSalNetSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDistSalNetSalActionPerformed(evt);
            }
        });
        txtDistSalNetSal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDistSalNetSalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDistSalNetSalKeyTyped(evt);
            }
        });
        pnlPayrollDistributionStaffSal.add(txtDistSalNetSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 270, 140, 30));

        txtDistSalEmpID.setEditable(false);
        pnlPayrollDistributionStaffSal.add(txtDistSalEmpID, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 140, 30));

        lblDistAdvAmount.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDistAdvAmount.setForeground(new java.awt.Color(255, 255, 255));
        lblDistAdvAmount.setText("Advance Amount");
        pnlPayrollDistributionStaffSal.add(lblDistAdvAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 230, -1, 20));

        lbldistMonthDisplay.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbldistMonthDisplay.setForeground(new java.awt.Color(255, 255, 255));
        pnlPayrollDistributionStaffSal.add(lbldistMonthDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 230, 20));

        txtDistSalSpecialBonus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDistSalSpecialBonusMouseClicked(evt);
            }
        });
        txtDistSalSpecialBonus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDistSalSpecialBonusKeyReleased(evt);
            }
        });
        pnlPayrollDistributionStaffSal.add(txtDistSalSpecialBonus, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 170, 140, 30));

        txtDistSalBasicSal.setEditable(false);
        pnlPayrollDistributionStaffSal.add(txtDistSalBasicSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, 140, 30));

        txtDistSearch.setBackground(new java.awt.Color(204, 255, 204));
        txtDistSearch.setToolTipText("search (Month,Year)");
        txtDistSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtDistSearchMouseEntered(evt);
            }
        });
        txtDistSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDistSearchActionPerformed(evt);
            }
        });
        txtDistSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDistSearchKeyTyped(evt);
            }
        });
        pnlPayrollDistributionStaffSal.add(txtDistSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 180, -1));

        btnDistSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/payrollAndLoanMngmt/images/searchbtn.jpg"))); // NOI18N
        btnDistSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDistSearchActionPerformed(evt);
            }
        });
        pnlPayrollDistributionStaffSal.add(btnDistSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 10, 40, 40));

        txtDistSalAdvAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDistSalAdvAmountKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDistSalAdvAmountKeyTyped(evt);
            }
        });
        pnlPayrollDistributionStaffSal.add(txtDistSalAdvAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 220, 140, 30));

        btnDistDeleteSal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDistDeleteSal.setText("Delete Entry");
        btnDistDeleteSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDistDeleteSalActionPerformed(evt);
            }
        });
        pnlPayrollDistributionStaffSal.add(btnDistDeleteSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 160, 120, 30));

        btnDistUpdateSal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDistUpdateSal.setText("Update Entry");
        btnDistUpdateSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDistUpdateSalActionPerformed(evt);
            }
        });
        pnlPayrollDistributionStaffSal.add(btnDistUpdateSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 120, 120, 30));

        btnDistInsertSal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDistInsertSal.setText("Insert Entry");
        btnDistInsertSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDistInsertSalActionPerformed(evt);
            }
        });
        pnlPayrollDistributionStaffSal.add(btnDistInsertSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 80, 120, 30));

        lbldistMonth.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbldistMonth.setForeground(new java.awt.Color(255, 255, 255));
        lbldistMonth.setText("Month");
        pnlPayrollDistributionStaffSal.add(lbldistMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 70, 20));

        btnDistCalcSal.setText("calculate Net Salary");
        btnDistCalcSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDistCalcSalActionPerformed(evt);
            }
        });
        pnlPayrollDistributionStaffSal.add(btnDistCalcSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 270, 140, 30));

        cmbDistSalEmpName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Employee" }));
        cmbDistSalEmpName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbDistSalEmpNameMouseClicked(evt);
            }
        });
        cmbDistSalEmpName.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbDistSalEmpNamePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbDistSalEmpName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDistSalEmpNameActionPerformed(evt);
            }
        });
        cmbDistSalEmpName.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                cmbDistSalEmpNameInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        pnlPayrollDistributionStaffSal.add(cmbDistSalEmpName, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 140, 30));

        txtDistSalDeptName.setEditable(false);
        txtDistSalDeptName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDistSalDeptNameActionPerformed(evt);
            }
        });
        txtDistSalDeptName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDistSalDeptNameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDistSalDeptNameKeyReleased(evt);
            }
        });
        pnlPayrollDistributionStaffSal.add(txtDistSalDeptName, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, 140, 30));

        lblDistDesignation.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDistDesignation.setForeground(new java.awt.Color(255, 255, 255));
        lblDistDesignation.setText(" Designation");
        pnlPayrollDistributionStaffSal.add(lblDistDesignation, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 80, 20));

        btnDistDeleteSal1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDistDeleteSal1.setText("Clear All");
        btnDistDeleteSal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDistDeleteSal1ActionPerformed(evt);
            }
        });
        pnlPayrollDistributionStaffSal.add(btnDistDeleteSal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 200, 120, 30));

        tblDistributionSal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Salary ID", "Employee ID", "Employee Name", "Department Name", "Designation", "Basic Salary", "Special Bonus", "Advance Amount", "Net Salary", "Month"
            }
        ));
        tblDistributionSal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDistributionSalMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblDistributionSal);

        pnlPayrollDistributionStaffSal.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 350, 880, 210));

        lblDisStaffSalName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblDisStaffSalName.setForeground(new java.awt.Color(255, 255, 255));
        lblDisStaffSalName.setText("Distribution Staff ");
        pnlPayrollDistributionStaffSal.add(lblDisStaffSalName, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 170, 20));

        pnlDisPaySheet.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnDisPaySheet.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDisPaySheet.setText("Generate PaySheet");
        btnDisPaySheet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisPaySheetActionPerformed(evt);
            }
        });

        lblPrdOffPaySheetMon1.setText("Month,Year");

        txtDisSheetMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDisSheetMonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDisPaySheetLayout = new javax.swing.GroupLayout(pnlDisPaySheet);
        pnlDisPaySheet.setLayout(pnlDisPaySheetLayout);
        pnlDisPaySheetLayout.setHorizontalGroup(
            pnlDisPaySheetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDisPaySheetLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlDisPaySheetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPrdOffPaySheetMon1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDisPaySheetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnDisPaySheet, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addComponent(txtDisSheetMon)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pnlDisPaySheetLayout.setVerticalGroup(
            pnlDisPaySheetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDisPaySheetLayout.createSequentialGroup()
                .addComponent(lblPrdOffPaySheetMon1, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(txtDisSheetMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDisPaySheet, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlPayrollDistributionStaffSal.add(pnlDisPaySheet, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 250, 200, 90));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        pnlPayrollDistributionStaffSal.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(pnlPayrollDistributionStaffSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlPayrollFoldingSatffl.setBackground(new java.awt.Color(102, 255, 102));
        pnlPayrollFoldingSatffl.setMaximumSize(new java.awt.Dimension(915, 570));
        pnlPayrollFoldingSatffl.setOpaque(false);
        pnlPayrollFoldingSatffl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFoldingSaltMonthDisplay.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFoldingSaltMonthDisplay.setForeground(new java.awt.Color(255, 255, 255));
        pnlPayrollFoldingSatffl.add(lblFoldingSaltMonthDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 210, 20));

        lblFoldingSaltMonth.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFoldingSaltMonth.setForeground(new java.awt.Color(255, 255, 255));
        lblFoldingSaltMonth.setText("Month");
        pnlPayrollFoldingSatffl.add(lblFoldingSaltMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 70, 20));

        lblFoldingSalEmpName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFoldingSalEmpName.setForeground(new java.awt.Color(255, 255, 255));
        lblFoldingSalEmpName.setText("Employee Name");
        pnlPayrollFoldingSatffl.add(lblFoldingSalEmpName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, 20));

        cmbFoldingSalEmpName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Employee" }));
        cmbFoldingSalEmpName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbFoldingSalEmpNameMouseClicked(evt);
            }
        });
        cmbFoldingSalEmpName.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbFoldingSalEmpNamePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbFoldingSalEmpName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFoldingSalEmpNameActionPerformed(evt);
            }
        });
        cmbFoldingSalEmpName.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                cmbFoldingSalEmpNameInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        pnlPayrollFoldingSatffl.add(cmbFoldingSalEmpName, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 140, 30));

        txtFoldingSalEmpId.setEditable(false);
        pnlPayrollFoldingSatffl.add(txtFoldingSalEmpId, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 140, 30));

        lblFoldingSalEmpId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFoldingSalEmpId.setForeground(new java.awt.Color(255, 255, 255));
        lblFoldingSalEmpId.setText("Employee ID");
        pnlPayrollFoldingSatffl.add(lblFoldingSalEmpId, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, -1, 20));

        lblFoldingSalDesignation.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFoldingSalDesignation.setForeground(new java.awt.Color(255, 255, 255));
        lblFoldingSalDesignation.setText(" Designation");
        pnlPayrollFoldingSatffl.add(lblFoldingSalDesignation, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 80, 20));

        txtFoldingSalDesignation.setEditable(false);
        txtFoldingSalDesignation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFoldingSalDesignationActionPerformed(evt);
            }
        });
        txtFoldingSalDesignation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFoldingSalDesignationKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFoldingSalDesignationKeyReleased(evt);
            }
        });
        pnlPayrollFoldingSatffl.add(txtFoldingSalDesignation, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, 140, 30));

        lblFoldingSalDeptName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFoldingSalDeptName.setForeground(new java.awt.Color(255, 255, 255));
        lblFoldingSalDeptName.setText("Department Name");
        pnlPayrollFoldingSatffl.add(lblFoldingSalDeptName, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 120, 20));

        txtFoldingSalDeptName.setEditable(false);
        txtFoldingSalDeptName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFoldingSalDeptNameActionPerformed(evt);
            }
        });
        txtFoldingSalDeptName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFoldingSalDeptNameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFoldingSalDeptNameKeyReleased(evt);
            }
        });
        pnlPayrollFoldingSatffl.add(txtFoldingSalDeptName, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, 140, 30));

        txtFoldingSalNetSal.setEditable(false);
        txtFoldingSalNetSal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFoldingSalNetSalMouseClicked(evt);
            }
        });
        txtFoldingSalNetSal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFoldingSalNetSalKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFoldingSalNetSalKeyReleased(evt);
            }
        });
        pnlPayrollFoldingSatffl.add(txtFoldingSalNetSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 320, 140, 30));

        btnFoldingSalCalcSal.setText("calculate Net Salary");
        btnFoldingSalCalcSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFoldingSalCalcSalActionPerformed(evt);
            }
        });
        pnlPayrollFoldingSatffl.add(btnFoldingSalCalcSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 320, 140, 30));

        lblFoldingSalAttBonus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFoldingSalAttBonus.setForeground(new java.awt.Color(255, 255, 255));
        lblFoldingSalAttBonus.setText("Attendance Bonus");
        pnlPayrollFoldingSatffl.add(lblFoldingSalAttBonus, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 80, -1, 20));

        txtFoldingSalAttendanceBonus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFoldingSalAttendanceBonusMouseClicked(evt);
            }
        });
        txtFoldingSalAttendanceBonus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFoldingSalAttendanceBonusKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFoldingSalAttendanceBonusKeyTyped(evt);
            }
        });
        pnlPayrollFoldingSatffl.add(txtFoldingSalAttendanceBonus, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, 140, 30));

        lblFoldingSalBasicSal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFoldingSalBasicSal.setForeground(new java.awt.Color(255, 255, 255));
        lblFoldingSalBasicSal.setText("Basic Salary");
        pnlPayrollFoldingSatffl.add(lblFoldingSalBasicSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, -1, 20));

        txtFoldingSalNoOfPieces.setEditable(false);
        txtFoldingSalNoOfPieces.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFoldingSalNoOfPiecesMouseClicked(evt);
            }
        });
        txtFoldingSalNoOfPieces.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFoldingSalNoOfPiecesKeyReleased(evt);
            }
        });
        pnlPayrollFoldingSatffl.add(txtFoldingSalNoOfPieces, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, 140, 30));

        txtFoldingSalBasicSal.setEditable(false);
        pnlPayrollFoldingSatffl.add(txtFoldingSalBasicSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 320, 140, 30));

        lblFoldingSalNoOfPieces.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFoldingSalNoOfPieces.setForeground(new java.awt.Color(255, 255, 255));
        lblFoldingSalNoOfPieces.setText("Number of pieces");
        pnlPayrollFoldingSatffl.add(lblFoldingSalNoOfPieces, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 130, -1, 20));

        txtFoldingSalPieceRate.setEditable(false);
        txtFoldingSalPieceRate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFoldingSalPieceRateMouseClicked(evt);
            }
        });
        txtFoldingSalPieceRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFoldingSalPieceRateKeyReleased(evt);
            }
        });
        pnlPayrollFoldingSatffl.add(txtFoldingSalPieceRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 170, 140, 30));

        lblFoldingSalAdvAmt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFoldingSalAdvAmt.setForeground(new java.awt.Color(255, 255, 255));
        lblFoldingSalAdvAmt.setText("Advance Amount");
        pnlPayrollFoldingSatffl.add(lblFoldingSalAdvAmt, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 220, -1, 20));

        lblFoldingSalPieceRate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFoldingSalPieceRate.setForeground(new java.awt.Color(255, 255, 255));
        lblFoldingSalPieceRate.setText("Piece Rate ");
        pnlPayrollFoldingSatffl.add(lblFoldingSalPieceRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 180, -1, 20));

        txtFoldingSalAdvAmt.setEditable(false);
        txtFoldingSalAdvAmt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFoldingSalAdvAmtMouseClicked(evt);
            }
        });
        txtFoldingSalAdvAmt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFoldingSalAdvAmtKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFoldingSalAdvAmtKeyReleased(evt);
            }
        });
        pnlPayrollFoldingSatffl.add(txtFoldingSalAdvAmt, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 220, 140, 30));

        lblFoldngSalName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFoldngSalName.setForeground(new java.awt.Color(255, 255, 255));
        lblFoldngSalName.setText("Folding Staff ");
        pnlPayrollFoldingSatffl.add(lblFoldngSalName, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 130, 20));

        tblFoldingStfSal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Salary ID", "Employee ID", "Employee Name", "Department Name", "Designation", "Basic Salary", "Attendance Bonus", "Number of pieces", "Piece Rate", "Advance Amount", "Special Bonus", "Net Salary", "Month"
            }
        ));
        tblFoldingStfSal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFoldingStfSalMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblFoldingStfSal);

        pnlPayrollFoldingSatffl.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 370, 880, 190));

        txtFoldingSalSpecialBonus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFoldingSalSpecialBonusMouseClicked(evt);
            }
        });
        txtFoldingSalSpecialBonus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFoldingSalSpecialBonusKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFoldingSalSpecialBonusKeyTyped(evt);
            }
        });
        pnlPayrollFoldingSatffl.add(txtFoldingSalSpecialBonus, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 270, 140, 30));

        lblFoldingSalSpecialBonus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFoldingSalSpecialBonus.setForeground(new java.awt.Color(255, 255, 255));
        lblFoldingSalSpecialBonus.setText("Special Bonus");
        pnlPayrollFoldingSatffl.add(lblFoldingSalSpecialBonus, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 270, -1, 20));

        btnFoldngSalInsert.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnFoldngSalInsert.setText("Insert Entry");
        btnFoldngSalInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFoldngSalInsertActionPerformed(evt);
            }
        });
        pnlPayrollFoldingSatffl.add(btnFoldngSalInsert, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 80, 120, 30));

        btnFoldingSalUpdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnFoldingSalUpdate.setText("Update Entry");
        btnFoldingSalUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFoldingSalUpdateActionPerformed(evt);
            }
        });
        pnlPayrollFoldingSatffl.add(btnFoldingSalUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 120, 120, 30));

        btnFoldingSalDelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnFoldingSalDelete.setText("Delete Entry");
        btnFoldingSalDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFoldingSalDeleteActionPerformed(evt);
            }
        });
        pnlPayrollFoldingSatffl.add(btnFoldingSalDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 160, 120, 30));

        txtFoldingSalSearch.setBackground(new java.awt.Color(204, 255, 204));
        txtFoldingSalSearch.setToolTipText("search (Month,Year)");
        txtFoldingSalSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtFoldingSalSearchMouseEntered(evt);
            }
        });
        txtFoldingSalSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFoldingSalSearchActionPerformed(evt);
            }
        });
        txtFoldingSalSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFoldingSalSearchKeyTyped(evt);
            }
        });
        pnlPayrollFoldingSatffl.add(txtFoldingSalSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 30, 180, -1));

        btnFoldingSalSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/payrollAndLoanMngmt/images/searchbtn.jpg"))); // NOI18N
        btnFoldingSalSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFoldingSalSearchActionPerformed(evt);
            }
        });
        pnlPayrollFoldingSatffl.add(btnFoldingSalSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 20, 40, 40));

        btnFoldingSalClear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnFoldingSalClear.setText("Clear All");
        btnFoldingSalClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFoldingSalClearActionPerformed(evt);
            }
        });
        pnlPayrollFoldingSatffl.add(btnFoldingSalClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 200, 120, 30));
        pnlPayrollFoldingSatffl.add(lblfoldingStaffSalID, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 330, 40, 20));

        pnlFoldingPaySheet.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnFoldingPaySheet.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnFoldingPaySheet.setText("Generate PaySheet");
        btnFoldingPaySheet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFoldingPaySheetActionPerformed(evt);
            }
        });

        lblFoldingSheetMon.setText("Month,Year");

        txtFoldingSheetMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFoldingSheetMonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFoldingPaySheetLayout = new javax.swing.GroupLayout(pnlFoldingPaySheet);
        pnlFoldingPaySheet.setLayout(pnlFoldingPaySheetLayout);
        pnlFoldingPaySheetLayout.setHorizontalGroup(
            pnlFoldingPaySheetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFoldingPaySheetLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlFoldingPaySheetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFoldingSheetMon, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlFoldingPaySheetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnFoldingPaySheet, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addComponent(txtFoldingSheetMon)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pnlFoldingPaySheetLayout.setVerticalGroup(
            pnlFoldingPaySheetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFoldingPaySheetLayout.createSequentialGroup()
                .addComponent(lblFoldingSheetMon, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(txtFoldingSheetMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFoldingPaySheet, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlPayrollFoldingSatffl.add(pnlFoldingPaySheet, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 250, 200, 90));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        pnlPayrollFoldingSatffl.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(pnlPayrollFoldingSatffl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlPayrolAdvancel.setBackground(new java.awt.Color(102, 255, 102));
        pnlPayrolAdvancel.setMaximumSize(new java.awt.Dimension(915, 570));
        pnlPayrolAdvancel.setOpaque(false);
        pnlPayrolAdvancel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAdvIdDisplay.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblAdvIdDisplay.setForeground(new java.awt.Color(255, 255, 255));
        pnlPayrolAdvancel.add(lblAdvIdDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 130, 20));

        lblAdvEmpID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAdvEmpID.setForeground(new java.awt.Color(255, 255, 255));
        lblAdvEmpID.setText("Employee ID");
        pnlPayrolAdvancel.add(lblAdvEmpID, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 80, 20));

        lblAdvName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAdvName.setForeground(new java.awt.Color(255, 255, 255));
        lblAdvName.setText("Employee Name");
        pnlPayrolAdvancel.add(lblAdvName, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 100, 20));

        lblAdvDeptName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAdvDeptName.setForeground(new java.awt.Color(255, 255, 255));
        lblAdvDeptName.setText("Department Name");
        pnlPayrolAdvancel.add(lblAdvDeptName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 120, 20));

        lblAdvAdvAmount.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAdvAdvAmount.setForeground(new java.awt.Color(255, 255, 255));
        lblAdvAdvAmount.setText("Advance Amount");
        pnlPayrolAdvancel.add(lblAdvAdvAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 110, 20));

        lblAdvDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAdvDate.setForeground(new java.awt.Color(255, 255, 255));
        lblAdvDate.setText("Date");
        pnlPayrolAdvancel.add(lblAdvDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, 30, 20));

        lblAdvStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAdvStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblAdvStatus.setText("Status");
        pnlPayrolAdvancel.add(lblAdvStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 220, 40, 20));

        txtAdvEmpName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAdvEmpNameActionPerformed(evt);
            }
        });
        txtAdvEmpName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAdvEmpNameKeyReleased(evt);
            }
        });
        pnlPayrolAdvancel.add(txtAdvEmpName, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 140, 30));

        txtAdvEmpId.setEditable(false);
        txtAdvEmpId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAdvEmpIdActionPerformed(evt);
            }
        });
        txtAdvEmpId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAdvEmpIdKeyReleased(evt);
            }
        });
        pnlPayrolAdvancel.add(txtAdvEmpId, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 140, 30));

        txtAdvDeptName.setEditable(false);
        txtAdvDeptName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAdvDeptNameActionPerformed(evt);
            }
        });
        pnlPayrolAdvancel.add(txtAdvDeptName, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, 140, 30));

        txtAdvAdvAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAdvAdvAmountActionPerformed(evt);
            }
        });
        txtAdvAdvAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAdvAdvAmountKeyReleased(evt);
            }
        });
        pnlPayrolAdvancel.add(txtAdvAdvAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 110, 140, 30));

        cmbAdvStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Status", "Deducted", "Not Deducted" }));
        pnlPayrolAdvancel.add(cmbAdvStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 210, 140, 30));

        lblAdvMonth.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblAdvMonth.setForeground(new java.awt.Color(255, 255, 255));
        lblAdvMonth.setText("Month");
        pnlPayrolAdvancel.add(lblAdvMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 60, 20));

        lblAdvMonthDisplay.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblAdvMonthDisplay.setForeground(new java.awt.Color(255, 255, 255));
        pnlPayrolAdvancel.add(lblAdvMonthDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 130, 20));

        btnAdvUpdateEntry.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAdvUpdateEntry.setText("Update Entry");
        btnAdvUpdateEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdvUpdateEntryActionPerformed(evt);
            }
        });
        pnlPayrolAdvancel.add(btnAdvUpdateEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 100, 120, 30));

        btnAdvDeleteEntry.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAdvDeleteEntry.setText("Delete Entry");
        btnAdvDeleteEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdvDeleteEntryActionPerformed(evt);
            }
        });
        pnlPayrolAdvancel.add(btnAdvDeleteEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 140, 120, 30));

        btnAdvAddEntry.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAdvAddEntry.setText("Add Entry");
        btnAdvAddEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdvAddEntryActionPerformed(evt);
            }
        });
        pnlPayrolAdvancel.add(btnAdvAddEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 60, 120, 30));

        tblAdvance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Advance ID", "Employee ID", "Employee Name", "Department Name", "Advance Amount", "Date", "Status", "Month"
            }
        ));
        tblAdvance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAdvanceMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblAdvance);

        pnlPayrolAdvancel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 890, 240));

        btnAdverach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/payrollAndLoanMngmt/images/searchbtn.jpg"))); // NOI18N
        btnAdverach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdverachActionPerformed(evt);
            }
        });
        pnlPayrolAdvancel.add(btnAdverach, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 10, 40, 40));

        txtAdvSearch.setBackground(new java.awt.Color(204, 255, 204));
        txtAdvSearch.setToolTipText("search ( Month)");
        txtAdvSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtAdvSearchMouseEntered(evt);
            }
        });
        txtAdvSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAdvSearchActionPerformed(evt);
            }
        });
        txtAdvSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAdvSearchKeyTyped(evt);
            }
        });
        pnlPayrolAdvancel.add(txtAdvSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, 180, -1));

        txtAdvDate.setDateFormatString("YYY-MM-dd");
        pnlPayrolAdvancel.add(txtAdvDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 160, 140, 30));

        lblAdvNameDisplay.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblAdvNameDisplay.setForeground(new java.awt.Color(255, 255, 255));
        lblAdvNameDisplay.setText("Advance Details");
        pnlPayrolAdvancel.add(lblAdvNameDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 150, 20));

        btnAdvClear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAdvClear.setText("Clear All");
        btnAdvClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdvClearActionPerformed(evt);
            }
        });
        pnlPayrolAdvancel.add(btnAdvClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 180, 120, 30));

        pnlAdvanceReport.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnAdvanceReport.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAdvanceReport.setText("Generate Report");
        btnAdvanceReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdvanceReportActionPerformed(evt);
            }
        });

        lblAdvanceReport.setText("Month,Year");

        txtAdvanceReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAdvanceReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAdvanceReportLayout = new javax.swing.GroupLayout(pnlAdvanceReport);
        pnlAdvanceReport.setLayout(pnlAdvanceReportLayout);
        pnlAdvanceReportLayout.setHorizontalGroup(
            pnlAdvanceReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdvanceReportLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlAdvanceReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAdvanceReport, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlAdvanceReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnAdvanceReport, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addComponent(txtAdvanceReport)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pnlAdvanceReportLayout.setVerticalGroup(
            pnlAdvanceReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAdvanceReportLayout.createSequentialGroup()
                .addComponent(lblAdvanceReport, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(txtAdvanceReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAdvanceReport, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlPayrolAdvancel.add(pnlAdvanceReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 220, 200, 90));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        pnlPayrolAdvancel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(pnlPayrolAdvancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlPayrollLoan.setBackground(new java.awt.Color(102, 255, 102));
        pnlPayrollLoan.setMaximumSize(new java.awt.Dimension(915, 570));
        pnlPayrollLoan.setOpaque(false);
        pnlPayrollLoan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLoanIdDisplay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLoanIdDisplay.setForeground(new java.awt.Color(255, 255, 255));
        pnlPayrollLoan.add(lblLoanIdDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 300, 70, 20));

        lblLoanStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLoanStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblLoanStatus.setText("Status");
        pnlPayrollLoan.add(lblLoanStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 250, -1, 20));

        txtLoanEmpId.setEditable(false);
        txtLoanEmpId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoanEmpIdActionPerformed(evt);
            }
        });
        txtLoanEmpId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLoanEmpIdKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLoanEmpIdKeyReleased(evt);
            }
        });
        pnlPayrollLoan.add(txtLoanEmpId, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 140, 30));

        txtLoanEmpName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoanEmpNameActionPerformed(evt);
            }
        });
        txtLoanEmpName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLoanEmpNameKeyReleased(evt);
            }
        });
        pnlPayrollLoan.add(txtLoanEmpName, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 140, 30));

        txtLoanDeptName.setEditable(false);
        pnlPayrollLoan.add(txtLoanDeptName, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 140, 30));

        lblLoanEmpId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLoanEmpId.setForeground(new java.awt.Color(255, 255, 255));
        lblLoanEmpId.setText("Employee ID");
        pnlPayrollLoan.add(lblLoanEmpId, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, -1, 20));

        lblLoanEmpName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLoanEmpName.setForeground(new java.awt.Color(255, 255, 255));
        lblLoanEmpName.setText("Employee Name");
        pnlPayrollLoan.add(lblLoanEmpName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, -1, 20));

        lblLoanDeptName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLoanDeptName.setForeground(new java.awt.Color(255, 255, 255));
        lblLoanDeptName.setText("Department Name");
        pnlPayrollLoan.add(lblLoanDeptName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 120, 20));

        lblLoanLoanAmt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLoanLoanAmt.setForeground(new java.awt.Color(255, 255, 255));
        lblLoanLoanAmt.setText("Loan Amount");
        pnlPayrollLoan.add(lblLoanLoanAmt, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, -1, 20));

        txtLoanLoanAmt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtLoanLoanAmtMouseEntered(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtLoanLoanAmtMouseClicked(evt);
            }
        });
        txtLoanLoanAmt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLoanLoanAmtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLoanLoanAmtKeyTyped(evt);
            }
        });
        pnlPayrollLoan.add(txtLoanLoanAmt, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, 140, 30));
        pnlPayrollLoan.add(txtMonthlyDue, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 150, 140, 30));
        pnlPayrollLoan.add(txtLoanDue, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 200, 140, 30));

        lblLentDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLentDate.setForeground(new java.awt.Color(255, 255, 255));
        lblLentDate.setText("Date");
        pnlPayrollLoan.add(lblLentDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 110, -1, 20));

        lblMonthlyDue.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMonthlyDue.setForeground(new java.awt.Color(255, 255, 255));
        lblMonthlyDue.setText("Monthly Due Amount");
        pnlPayrollLoan.add(lblMonthlyDue, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 150, -1, 20));

        lblLoanDue.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLoanDue.setForeground(new java.awt.Color(255, 255, 255));
        lblLoanDue.setText("Total Due Amount");
        pnlPayrollLoan.add(lblLoanDue, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 200, -1, 20));

        cmbLoanStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Loan Status", "Pending Approval", "Loan Approved", "Loan Paid" }));
        pnlPayrollLoan.add(cmbLoanStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 250, 140, 30));

        tblLoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Loan ID", "Employee ID", "Employee Name", " Department Name", "Loant Amount", "Date", "Monthly Due", "Total Due", "Status"
            }
        ));
        tblLoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLoanMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblLoan);

        pnlPayrollLoan.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 900, 210));

        btnDeleteLoan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDeleteLoan.setText("Delete Loan Details");
        btnDeleteLoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteLoanActionPerformed(evt);
            }
        });
        pnlPayrollLoan.add(btnDeleteLoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 160, 160, 30));

        btnInsertLoan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnInsertLoan.setText("Insert Loan Deatils");
        btnInsertLoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertLoanActionPerformed(evt);
            }
        });
        pnlPayrollLoan.add(btnInsertLoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 80, 160, 30));

        btnUpdateLoan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUpdateLoan.setText("Update Loan Details");
        btnUpdateLoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateLoanActionPerformed(evt);
            }
        });
        pnlPayrollLoan.add(btnUpdateLoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 120, 160, 30));

        txtLoanSearch.setBackground(new java.awt.Color(204, 255, 204));
        txtLoanSearch.setToolTipText("search ( Employee ID)");
        txtLoanSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtLoanSearchMouseEntered(evt);
            }
        });
        txtLoanSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoanSearchActionPerformed(evt);
            }
        });
        txtLoanSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLoanSearchKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLoanSearchKeyTyped(evt);
            }
        });
        pnlPayrollLoan.add(txtLoanSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, 180, -1));

        btnloanSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/payrollAndLoanMngmt/images/searchbtn.jpg"))); // NOI18N
        btnloanSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloanSearchActionPerformed(evt);
            }
        });
        pnlPayrollLoan.add(btnloanSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 10, 40, 40));
        pnlPayrollLoan.add(txtLentDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 100, 140, 30));

        lblLoanName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblLoanName.setForeground(new java.awt.Color(255, 255, 255));
        lblLoanName.setText("Loan Handling");
        pnlPayrollLoan.add(lblLoanName, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 140, 20));

        btnLoanClear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLoanClear.setText("Clear All");
        btnLoanClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoanClearActionPerformed(evt);
            }
        });
        pnlPayrollLoan.add(btnLoanClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 200, 160, 30));

        pnlFoldingPaySheet1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnLoanSheetMon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLoanSheetMon.setText("Generate Loan Report");
        btnLoanSheetMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoanSheetMonActionPerformed(evt);
            }
        });

        lblLoanSheetMon.setText("Month,Year");

        txtLoanSheetMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoanSheetMonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFoldingPaySheet1Layout = new javax.swing.GroupLayout(pnlFoldingPaySheet1);
        pnlFoldingPaySheet1.setLayout(pnlFoldingPaySheet1Layout);
        pnlFoldingPaySheet1Layout.setHorizontalGroup(
            pnlFoldingPaySheet1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFoldingPaySheet1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlFoldingPaySheet1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLoanSheetMon, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlFoldingPaySheet1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnLoanSheetMon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtLoanSheetMon)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFoldingPaySheet1Layout.setVerticalGroup(
            pnlFoldingPaySheet1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFoldingPaySheet1Layout.createSequentialGroup()
                .addComponent(lblLoanSheetMon, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(txtLoanSheetMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLoanSheetMon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlPayrollLoan.add(pnlFoldingPaySheet1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 250, 200, 90));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        pnlPayrollLoan.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(pnlPayrollLoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlPayrollWelcome.setBackground(new java.awt.Color(0, 0, 0));
        pnlPayrollWelcome.setMaximumSize(new java.awt.Dimension(915, 570));
        pnlPayrollWelcome.setMinimumSize(new java.awt.Dimension(915, 570));
        pnlPayrollWelcome.setPreferredSize(new java.awt.Dimension(915, 570));
        pnlPayrollWelcome.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("PayRoll And Loan Management");
        pnlPayrollWelcome.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tempus Sans ITC", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Welcome");
        pnlPayrollWelcome.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, -1, -1));

        rightPanel.add(pnlPayrollWelcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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

    private void btnMasterFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasterFileActionPerformed
        // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels
        
              pnlPayrollMasterFile.setVisible(true);
              pnlPayrollOffPrdStfSal.setVisible(false);
              pnlPayrollDistributionStaffSal.setVisible(false);
              pnlPayrollFoldingSatffl.setVisible(false);
              pnlPayrollLoan.setVisible(false);
              pnlPayrolAdvancel.setVisible(false);
              pnlPayrollWelcome.setVisible(false);
        
        rightPanel.add(pnlPayrollMasterFile);
        rightPanel.repaint();
        rightPanel.revalidate();
    }//GEN-LAST:event_btnMasterFileActionPerformed

    private void btnPrd_off_stfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrd_off_stfActionPerformed
         // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels
        
              pnlPayrollMasterFile.setVisible(false);
              pnlPayrollOffPrdStfSal.setVisible(true);
              pnlPayrollDistributionStaffSal.setVisible(false);
              pnlPayrollFoldingSatffl.setVisible(false);
              pnlPayrollLoan.setVisible(false);
              pnlPayrolAdvancel.setVisible(false);
              pnlPayrollWelcome.setVisible(false);
        
        rightPanel.add(pnlPayrollOffPrdStfSal);
        rightPanel.repaint();
        rightPanel.revalidate();
    }//GEN-LAST:event_btnPrd_off_stfActionPerformed

    private void txtmfEmpIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmfEmpIDActionPerformed
        
    }//GEN-LAST:event_txtmfEmpIDActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnAddMfEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMfEntryActionPerformed
 
           
               if(cmbmfEmpName.getSelectedItem()=="Select Employee" || cmbmfDepName.getSelectedItem()=="Select Department" || txtmfBasicSal.getText().isEmpty() || txtmfSingleOtRate.getText().isEmpty() || txtmfDoubleOtRate.getText().isEmpty() || txtmfEpfRate.getText().isEmpty() || txtmfPeiceRate.getText().isEmpty() )
               {   
                    JOptionPane.showMessageDialog(rootPane, "One or more empty fields.Please enter valid data.");

                 if(txtmfBasicSal.getText().isEmpty())
                   {
                      txtmfBasicSal.setBackground(Color.PINK);
                      //JOptionPane.showMessageDialog(rootPane, "One or more empty fields.Please enter valid data.");
                      
                   }
                 if(txtmfSingleOtRate.getText().isEmpty())
                   {
                      txtmfSingleOtRate.setBackground(Color.PINK);
                      //JOptionPane.showMessageDialog(rootPane, "One or more empty fields.Please enter valid data.");
                      
                   } 
                 if(txtmfDoubleOtRate.getText().isEmpty())
                   {
                      txtmfDoubleOtRate.setBackground(Color.PINK);
                     // JOptionPane.showMessageDialog(rootPane, "One or more empty fields.Please enter valid data.");
                   }  
                 if(txtmfEpfRate.getText().isEmpty())
                   {
                      txtmfEpfRate.setBackground(Color.PINK);
                     // JOptionPane.showMessageDialog(rootPane, "One or more empty fields.Please enter valid data.");
                   } 
                if(txtmfPeiceRate.getText().isEmpty())
                   {
                      txtmfPeiceRate.setBackground(Color.PINK);
                     // JOptionPane.showMessageDialog(rootPane, "One or more empty fields.Please enter valid data.");
                   }
          
               }
                else{
                     if(mfbasicSal||mfsinOtRate||mfdoubleOtRate||mfepf||mfpieceRAte){
                         JOptionPane.showMessageDialog(this, "One or more fields are invalid");
                     }
                     else{
                            String deptName=cmbmfDepName.getSelectedItem().toString();
                            int empId=Integer.parseInt(txtmfEmpID.getText());
                            masterFileDAO mfDAO1=new masterFileDAO();
                            boolean results=mfDAO1.checkPrimaryKeyViolations(deptName,empId);
                            System.out.println(results);
                        
                            if (results==true)
                           {  
                                   JOptionPane.showMessageDialog(this, "Data already exists! Please re select options!!");
                           }
                           else{
                                 
                                 try{
                                     //String DeptName=cmbmfDepName.getSelectedItem().toString();
                                     String empName=cmbmfEmpName.getSelectedItem().toString();
                                     //int empId=Integer.parseInt(txtmfEmpID.getText());
                                     String EmpPost=txtmfEmpPosition.getText();
                                     Double basicSal=Double.parseDouble(txtmfBasicSal.getText());
                                     Double sinOTRate=Double.parseDouble(txtmfSingleOtRate.getText());
                                     Double doubleOTRate=Double.parseDouble(txtmfDoubleOtRate.getText());
                                     Double epfRate= Double.parseDouble(txtmfEpfRate.getText());
                                     Double pieceRate=Double.parseDouble(txtmfPeiceRate.getText());
                                     
                                     
                                     
                                     masterfile mf1=new masterfile(deptName, EmpPost, basicSal, sinOTRate, doubleOTRate, epfRate, pieceRate, empName, empId);
                                     mfDAO1.masterFileAddEntry(mf1);
                                     JOptionPane.showMessageDialog(this, "Data Insertion Sucessful!!");
                                     loadMfTable();
                                     
                                     
                                 } catch (SQLException ex)
                                 {
                                     Logger.getLogger(PayrollGUI.class.getName()).log(Level.SEVERE, null, ex);
                                 }
 
                                    cmbmfEmpName.setSelectedIndex(0);
                                     cmbmfDepName.setSelectedIndex(0);
                                     txtmfEmpID.setText("");
                                     txtmfEmpPosition.setText("");
                                     txtmfBasicSal.setText("");
                                     txtmfSingleOtRate.setText("");
                                     txtmfDoubleOtRate.setText("");
                                     txtmfEpfRate.setText("");
                                     txtmfPeiceRate.setText("");

                             }
                                                               
                                    
                  }
                     
               }
               
               
    }//GEN-LAST:event_btnAddMfEntryActionPerformed

    private void btnMfSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMfSearchActionPerformed
        
         String searchKey=txtSearch.getText();
       //  masterfile mf4= new masterfile();
         masterFileDAO mfDAO1=new masterFileDAO();
         rs=mfDAO1.search(searchKey);
         mfTable.setModel( DbUtils.resultSetToTableModel(rs));
        
        
    }//GEN-LAST:event_btnMfSearchActionPerformed

    private void btnUpdateMfEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateMfEntryActionPerformed

        
                
              
 
                 int cnfrm= JOptionPane.showConfirmDialog(rootPane, "Proceed Update?");
                 
               if(cnfrm==0){ 
                   
                   
                  if(cmbmfEmpName.getSelectedItem()=="Select Department" || cmbmfDepName.getSelectedItem()=="Select Department" ||txtmfBasicSal.getText().isEmpty() || txtmfSingleOtRate.getText().isEmpty() || txtmfDoubleOtRate.getText().isEmpty() || txtmfEpfRate.getText().isEmpty() || txtmfPeiceRate.getText().isEmpty() )
                 {   
                    JOptionPane.showMessageDialog(rootPane, "One or more empty fields.Please enter valid data.");
                 
                
                  if(txtmfBasicSal.getText().isEmpty())
                   {
                      txtmfSingleOtRate.setBackground(Color.PINK);
                      //JOptionPane.showMessageDialog(rootPane, "One or more empty fields.Please enter valid data.");
                      //mfbasicSal=true;
                   }  
                    
                    
                 if(txtmfSingleOtRate.getText().isEmpty())
                   {
                      txtmfSingleOtRate.setBackground(Color.PINK);
                      //JOptionPane.showMessageDialog(rootPane, "One or more empty fields.Please enter valid data.");
                     // mfsinOtRate=true;
                   }  
                 if(txtmfDoubleOtRate.getText().isEmpty())
                   {
                      txtmfDoubleOtRate.setBackground(Color.PINK);
                     // JOptionPane.showMessageDialog(rootPane, "One or more empty fields.Please enter valid data.");
                     // mfdoubleOtRate=true;
                   }  
                 if(txtmfEpfRate.getText().isEmpty())
                   {
                      txtmfEpfRate.setBackground(Color.PINK);
                     // JOptionPane.showMessageDialog(rootPane, "One or more empty fields.Please enter valid data.");
                     // mfepf=true;
                   } 
           if(txtmfPeiceRate.getText().isEmpty())
                   {
                      txtmfPeiceRate.setBackground(Color.PINK);
                     // JOptionPane.showMessageDialog(rootPane, "One or more empty fields.Please enter valid data.");
                     // mfpieceRAte=true;
                   } 
                 } 
                   
                  else{ 
                      if(mfbasicSal||mfsinOtRate||mfdoubleOtRate||mfepf||mfpieceRAte){
                          
                          JOptionPane.showMessageDialog(this,"One or more invalid fields!");
                      }
                      else{  

                    String DeptName=cmbmfDepName.getSelectedItem().toString();
                    String empName=cmbEmpName.getSelectedItem().toString();
                    int empId=Integer.parseInt(txtmfEmpID.getText());
                    String empPoSt=txtmfEmpPosition.getText();
                    Double basicSal=Double.parseDouble(txtmfBasicSal.getText());
                    Double sinOTRate=Double.parseDouble(txtmfSingleOtRate.getText());
                    Double doubleOTRate=Double.parseDouble(txtmfDoubleOtRate.getText());
                    Double epfRate= Double.parseDouble(txtmfEpfRate.getText());
                    Double pieceRate=Double.parseDouble(txtmfPeiceRate.getText());
                    
                    try {
                    masterfile mf2=new masterfile(DeptName, empPoSt, basicSal, sinOTRate, doubleOTRate, epfRate, pieceRate, empName, empId);
                    JOptionPane.showMessageDialog(rootPane, "Updated Sucessfully!!");
                    masterFileDAO mfDAO1=new masterFileDAO();
                          
                    mfDAO1.updateMfEntry(mf2);
                    loadMfTable();
                              
                              } catch (SQLException ex) {
                              Logger.getLogger(PayrollGUI.class.getName()).log(Level.SEVERE, null, ex);
                              }

                                 cmbmfEmpName.setSelectedIndex(0);
                                     cmbmfDepName.setSelectedIndex(0);
                                     txtmfEmpID.setText("");
                                     txtmfEmpPosition.setText("");
                                     txtmfBasicSal.setText("");
                                     txtmfSingleOtRate.setText("");
                                     txtmfDoubleOtRate.setText("");
                                     txtmfEpfRate.setText("");
                                     txtmfPeiceRate.setText("");
                                     btnAddMfEntry.setEnabled(true);
                    
                            }
 
                        }
                    } 
       
               else
               {
                            
                                     cmbmfEmpName.setSelectedIndex(0);
                                     cmbmfDepName.setSelectedIndex(0);
                                     txtmfEmpID.setText("");
                                     txtmfEmpPosition.setText("");
                                     txtmfBasicSal.setText("");
                                     txtmfSingleOtRate.setText("");
                                     txtmfDoubleOtRate.setText("");
                                     txtmfEpfRate.setText("");
                                     txtmfPeiceRate.setText("");
                                     btnAddMfEntry.setEnabled(true);
               
               }
              
                
                
    }//GEN-LAST:event_btnUpdateMfEntryActionPerformed

    private void btnDeleteMfEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteMfEntryActionPerformed
          
             int cnfrm=JOptionPane.showConfirmDialog(rootPane, "Do you really want to delete?");
             if(cnfrm==0){           
             
             int empId=Integer.parseInt(txtmfEmpID.getText());
             String deptName=cmbmfDepName.getSelectedItem().toString();
             masterFileDAO mfDAO = new masterFileDAO();
             mfDAO.deletemfEntry(empId,deptName);
             JOptionPane.showMessageDialog(rootPane, "Deleted Sucessfully!!");
             loadMfTable();
             
             }
             
                cmbmfDepName.setSelectedItem("Select Department"); 
                cmbmfEmpName.setSelectedItem("Select Employee"); 
                txtmfEmpID.setText("");
                txtmfEmpPosition.setText("");
                txtmfBasicSal.setText("");
                txtmfSingleOtRate.setText("");
                txtmfDoubleOtRate.setText("");
                txtmfEpfRate.setText("");
                txtmfPeiceRate.setText("");
                btnAddMfEntry.setEnabled(true);
           
    }//GEN-LAST:event_btnDeleteMfEntryActionPerformed

    private void btnFoldingStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFoldingStaffActionPerformed
        
        // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels
        
              pnlPayrollMasterFile.setVisible(false);
              pnlPayrollOffPrdStfSal.setVisible(false);
              pnlPayrollDistributionStaffSal.setVisible(false);
              pnlPayrollFoldingSatffl.setVisible(true);
              pnlPayrollLoan.setVisible(false);
              pnlPayrolAdvancel.setVisible(false);
              pnlPayrollWelcome.setVisible(false);
        
        rightPanel.add(pnlPayrollFoldingSatffl);
        rightPanel.repaint();
        rightPanel.revalidate();
        
    }//GEN-LAST:event_btnFoldingStaffActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels
        
              pnlPayrollMasterFile.setVisible(false);
              pnlPayrollOffPrdStfSal.setVisible(false);
              pnlPayrollDistributionStaffSal.setVisible(false);
              pnlPayrollFoldingSatffl.setVisible(false);
              pnlPayrollLoan.setVisible(true);
              pnlPayrolAdvancel.setVisible(false);
              pnlPayrollWelcome.setVisible(false);
        
        rightPanel.add(pnlPayrollLoan);
        rightPanel.repaint();
        rightPanel.revalidate();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        //removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels
        
              pnlPayrollMasterFile.setVisible(false);
              pnlPayrollOffPrdStfSal.setVisible(false);
              pnlPayrollDistributionStaffSal.setVisible(false);
              pnlPayrollFoldingSatffl.setVisible(false);
              pnlPayrollLoan.setVisible(false);
              pnlPayrolAdvancel.setVisible(true);
              pnlPayrollWelcome.setVisible(false);
        
        rightPanel.add(pnlPayrolAdvancel);
        rightPanel.repaint();
        rightPanel.revalidate();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void mfTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mfTableMouseClicked
        
        int row=mfTable.getSelectedRow();
        
           btnAddMfEntry.setEnabled(false);
           
         txtmfBasicSal.setBackground(Color.WHITE);
        txtmfEmpID.setBackground(Color.WHITE);
        txtmfSingleOtRate.setBackground(Color.WHITE); 
        txtmfDoubleOtRate.setBackground(Color.WHITE); 
        txtmfEpfRate.setBackground(Color.WHITE);
        txtmfPeiceRate.setBackground(Color.WHITE);
        
        String depName=mfTable.getValueAt(row, 0).toString();
        String empNAme=mfTable.getValueAt(row, 1).toString();
        String empID=mfTable.getValueAt(row, 2).toString();
        String empPost=mfTable.getValueAt(row, 3).toString();
        String basicSal=mfTable.getValueAt(row, 4).toString();
        String sinOT=mfTable.getValueAt(row, 5).toString();
        String dOT=mfTable.getValueAt(row, 6).toString();
        String epfRate=mfTable.getValueAt(row, 7).toString();
        String pieceRate=mfTable.getValueAt(row, 8).toString();
        
        
        cmbmfDepName.setSelectedItem(depName);
        cmbmfEmpName.setSelectedItem(empNAme);   
        txtmfEmpPosition.setText(empPost); 
        txtmfEmpID.setText(empID); 
        txtmfBasicSal.setText(basicSal);
        txtmfSingleOtRate.setText(sinOT); 
        txtmfDoubleOtRate.setText(dOT); 
        txtmfEpfRate.setText(epfRate);
        txtmfPeiceRate.setText(pieceRate);
        
        
       
    }//GEN-LAST:event_mfTableMouseClicked

    private void txtmfEmpIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmfEmpIDKeyTyped
       
        
    }//GEN-LAST:event_txtmfEmpIDKeyTyped

    private void txtmfSingleOtRateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmfSingleOtRateKeyTyped
         /*char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE) 
        {
           // JOptionPane.showMessageDialog(this, "Wrong Characters!!!Type Only Numeric data");
            lblErrorMsg.setText("*Wrong data type! Enter numeric data only!");
            getToolkit().beep();
            evt.consume();
        }*/
        
    }//GEN-LAST:event_txtmfSingleOtRateKeyTyped

    private void txtmfEpfRateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmfEpfRateKeyTyped
        
        /* char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE) ||(c==KeyEvent.VK_DECIMAL))
        {
           // JOptionPane.showMessageDialog(this, "Wrong Characters!!!Type Only Numeric data");
            lblErrorMsg.setText("*Wrong data type! Enter numeric data only!");
            getToolkit().beep();
            evt.consume();
        }*/
    }//GEN-LAST:event_txtmfEpfRateKeyTyped

    private void txtmfPeiceRateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmfPeiceRateKeyTyped
        /*char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE)||(c==KeyEvent.VK_DECIMAL))
        {
           // JOptionPane.showMessageDialog(this, "Wrong Characters!!!Type Only Numeric data");
            lblErrorMsg.setText("*Wrong data type! Enter numeric data only!");
            getToolkit().beep();
            evt.consume();
        }*/
    }//GEN-LAST:event_txtmfPeiceRateKeyTyped

    private void cmbmfEmpNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbmfEmpNameActionPerformed
        
              try{
            masterFileDAO mfDAO=new masterFileDAO();
            
            
            String ename=cmbmfEmpName.getSelectedItem().toString();
            
            String deptName=cmbmfDepName.getSelectedItem().toString();
            
            int eid=(mfDAO.eidOnvalChange(ename, deptName));
            String stringEID=String.valueOf(eid);
            txtmfEmpID.setText(stringEID);
            String desig =String.valueOf(mfDAO.desigOnValChange(eid));
            txtmfEmpPosition.setText(desig);
            
        }catch(Exception e)
        {
            System.out.println(e);
        }

       
    }//GEN-LAST:event_cmbmfEmpNameActionPerformed

    private void txtSearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseEntered
      txtSearch.setToolTipText("Search (Dept ID)");
        
    }//GEN-LAST:event_txtSearchMouseEntered

    private void btnInsetSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsetSalActionPerformed

        if(cmbEmpName.getSelectedItem().equals("Select Employee") ||txtSinOtHrs.getText().isEmpty()||txtDoubleOtHrs.getText().isEmpty()||txtAtndnceBonus.getText().isEmpty()|| txtSpecialBonus.getText().isEmpty()||txtNetSal.getText().isEmpty())
        {
                 JOptionPane.showMessageDialog(rootPane, "One or more fields are empty! ");
                 if(txtSinOtHrs.getText().isEmpty()){
                    txtSinOtHrs.setBackground(Color.PINK); 
                 }
                 if(txtDoubleOtHrs.getText().isEmpty()){
                    txtDoubleOtHrs.setBackground(Color.PINK); 
                 }
                 if(txtAtndnceBonus.getText().isEmpty()){
                    txtAtndnceBonus.setBackground(Color.PINK); 
                 }
                 if(txtSpecialBonus.getText().isEmpty()){
                    txtSpecialBonus.setBackground(Color.PINK); 
                 }
                if(txtNetSal.getText().isEmpty()) {
                    txtNetSal.setBackground(Color.PINK);
                  }
        }   
        else{
            if(offPrdDoubleOtRate||offPrdSpecialBonus||offprdAttBonus||offprdSinOtRate||offPrdNetSal){
                JOptionPane.showMessageDialog(this, "One or more fields are invalid");
            }
            else{
                         String month=lblSalMonth.getText();
                         int empId=Integer.parseInt(txtEmpId.getText());
                         System.out.println("eid"+ empId);
                         off_prd_salCalDAO opDAO1 = new off_prd_salCalDAO();
                         boolean results=opDAO1.checkDuplicateValues(month,empId);
                    if(results==true){
                       JOptionPane.showMessageDialog(this, "Data already exists!!");
                
                }
                
                else{
                            //String month=lblSalMonth.getText();
                            //int empId=Integer.parseInt(txtEmpId.getText());
                            String empName=cmbEmpName.getSelectedItem().toString();
                            String deptName=txtDeptName.getText();
                            String designation=txtDesignation.getText();
                            double basicSal=Double.parseDouble(txtBasicSalary.getText());
                            double sinOTHrs=Double.parseDouble(txtSinOtHrs.getText());
                            double doubleOTHrs=Double.parseDouble(txtDoubleOtHrs.getText());
                            double attBonus=Double.parseDouble(txtAtndnceBonus.getText());
                            double advAmt=Double.parseDouble(txtAdvanceAmt.getText());
                            double loanAmt=Double.parseDouble(txtLoanAmt.getText());
                            double specialBonus=Double.parseDouble(txtSpecialBonus.getText());
                            double netSal=Double.parseDouble(txtNetSal.getText());

                            try {
                                    off_prd_salCal p1=new off_prd_salCal(empId, empName, deptName, designation, basicSal, sinOTHrs, doubleOTHrs, attBonus, advAmt, loanAmt, specialBonus, netSal, month);
                                    //off_prd_salCalDAO opDAO1 = new off_prd_salCalDAO(); 
                                    opDAO1.updateMonthlyLoanDue(empId,loanAmt);
                                    opDAO1.updateAdvStatus(empId, month);
                                    opDAO1.off_prd_salCalAddEntry(p1);
                                    loadOff_Prd_sal_Table();
                                    loadLoanTable();
                                    loadAdvanceTable();
                                    JOptionPane.showMessageDialog(rootPane, "Record inserted Sucessfully");


                                } catch (SQLException ex) {
                                    Logger.getLogger(PayrollGUI.class.getName()).log(Level.SEVERE, null, ex);
                                }
                }
                    
                    

                                   lblsalIdDisplay.setText("");
                                   cmbEmpName.setSelectedIndex(0);
                                   txtEmpId.setText("");
                                   txtEmpId.setText("");
                                   txtDesignation.setText("");
                                   txtBasicSalary.setText("");
                                   txtSinOtHrs.setText("");
                                   txtDoubleOtHrs.setText("");
                                   txtAtndnceBonus.setText("");
                                   txtAdvanceAmt.setText("");
                                   txtLoanAmt.setText("");
                                   txtSpecialBonus.setText("");
                                   txtNetSal.setText("");

        }
        }
    }//GEN-LAST:event_btnInsetSalActionPerformed

    private void cmbEmpNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbEmpNameMouseClicked
       
    }//GEN-LAST:event_cmbEmpNameMouseClicked

    private void cmbEmpNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmpNameActionPerformed
          
            String month=lblSalMonth.getText();
            String empName=cmbEmpName.getSelectedItem().toString();
            off_prd_salCalDAO pDAO2=new off_prd_salCalDAO();
            int eId=(pDAO2.eIdOnValChange(empName));
            //int eId= Integer.parseInt(txtEmpId.getText());
            txtEmpId.setText(String.valueOf(eId));
            txtDeptName.setText(pDAO2.dnameOnValChange(eId));
            txtDesignation.setText(pDAO2.desigOnValChange(eId));
            txtBasicSalary.setText(pDAO2.basicSalOnValChange(txtDeptName.getText(),txtDesignation.getText(),eId));
            double advAmount=pDAO2.setSalAdvAmount(eId,lblSalMonth.getText());
            txtAdvanceAmt.setText(String.valueOf(advAmount));
            double loanAmt=pDAO2.setSalLoanAmount(eId);
            txtLoanAmt.setText(String.valueOf(loanAmt));
           
            
    }//GEN-LAST:event_cmbEmpNameActionPerformed

    private void cmbEmpNameInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_cmbEmpNameInputMethodTextChanged

    }//GEN-LAST:event_cmbEmpNameInputMethodTextChanged

    private void tbl_off_prd_salMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_off_prd_salMouseClicked
        
        int row=tbl_off_prd_sal.getSelectedRow();
        
         btnDistInsertSal.setEnabled(false);
        
        
        String salId=tbl_off_prd_sal.getValueAt(row, 0).toString();
        String empId=tbl_off_prd_sal.getValueAt(row, 1).toString();
        String empName=tbl_off_prd_sal.getValueAt(row, 2).toString();
        String deptName=tbl_off_prd_sal.getValueAt(row, 3).toString();
        String desig=tbl_off_prd_sal.getValueAt(row, 4).toString();
        String basicSal=tbl_off_prd_sal.getValueAt(row, 5).toString();
        String sinOTHrs=tbl_off_prd_sal.getValueAt(row, 6).toString();
        String dOTHrs=tbl_off_prd_sal.getValueAt(row, 7).toString();
        String attbonus=tbl_off_prd_sal.getValueAt(row, 8).toString();
        String advAmt=tbl_off_prd_sal.getValueAt(row, 9).toString();
        String loan=tbl_off_prd_sal.getValueAt(row, 10).toString();
        String specialBonus=tbl_off_prd_sal.getValueAt(row, 11).toString();
        String netSal=tbl_off_prd_sal.getValueAt(row, 12).toString();
        
        
        lblsalIdDisplay.setText(salId);
        cmbEmpName.setSelectedItem(empName);   
        txtEmpId.setText(empId); 
        txtDeptName.setText(deptName); 
        txtDesignation.setText(desig); 
        txtBasicSalary.setText(basicSal);
        txtSinOtHrs.setText(sinOTHrs); 
        txtDoubleOtHrs.setText(dOTHrs);
        txtAtndnceBonus.setText(attbonus);
        txtAdvanceAmt.setText(advAmt);
        txtLoanAmt.setText(loan);
        txtSpecialBonus.setText(specialBonus);
        txtNetSal.setText(netSal);
        
        
        txtSinOtHrs.setBackground(Color.WHITE); 
        txtDoubleOtHrs.setBackground(Color.WHITE); 
        txtAtndnceBonus.setBackground(Color.WHITE); 
        txtAdvanceAmt.setBackground(Color.WHITE); 
        txtLoanAmt.setBackground(Color.WHITE); 
        txtSpecialBonus.setBackground(Color.WHITE); 
        
    }//GEN-LAST:event_tbl_off_prd_salMouseClicked

    private void btnUpdateSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateSalActionPerformed
   
        int cnfrm=JOptionPane.showConfirmDialog(rootPane, "Proceed update?");
        
        if(cnfrm==0){
            if(txtSinOtHrs.getText().isEmpty()||txtDoubleOtHrs.getText().isEmpty()||txtAtndnceBonus.getText().isEmpty()|| txtSpecialBonus.getText().isEmpty()||txtNetSal.getText().isEmpty() )
        {
                 JOptionPane.showMessageDialog(rootPane, "One or more fields are empty! ");
                 if(txtSinOtHrs.getText().isEmpty()){
                    txtSinOtHrs.setBackground(Color.PINK); 
                 }
                 if(txtDoubleOtHrs.getText().isEmpty()){
                    txtDoubleOtHrs.setBackground(Color.PINK); 
                 }
                 if(txtAtndnceBonus.getText().isEmpty()){
                    txtAtndnceBonus.setBackground(Color.PINK); 
                 }
                 if(txtSpecialBonus.getText().isEmpty()){
                    txtSpecialBonus.setBackground(Color.PINK); 
                 }
                
                 if(txtNetSal.getText().isEmpty()){
                    txtNetSal.setBackground(Color.PINK); 
                 }
        }   
        else{
            if(offPrdDoubleOtRate||offPrdSpecialBonus||offprdAttBonus||offprdSinOtRate||offPrdNetSal){
                JOptionPane.showMessageDialog(this, "One or more fields are invalid");
            }
            else{
        int salID=Integer.parseInt(lblsalIdDisplay.getText());
        String month=lblMonth.getText();
        int empId=Integer.parseInt(txtEmpId.getText());
        String empName=cmbEmpName.getSelectedItem().toString();
        String deptName=txtEmpId.getText();
        String designation=txtDesignation.getText().toString();
        double basicSal=Double.parseDouble(txtBasicSalary.getText());
        double sinOTHrs=Double.parseDouble(txtSinOtHrs.getText());
        double doubleOTHrs=Double.parseDouble(txtDoubleOtHrs.getText());
        double attBonus=Double.parseDouble(txtAtndnceBonus.getText());
        double advAmt=Double.parseDouble(txtAdvanceAmt.getText());
        double loanAmt=Double.parseDouble(txtLoanAmt.getText());
        double specialBonus=Double.parseDouble(txtSpecialBonus.getText());
        double netSal=Double.parseDouble(txtNetSal.getText());
        
        try {   
                off_prd_salCal p2= new off_prd_salCal(empId,empName,deptName,designation,basicSal,sinOTHrs, doubleOTHrs, attBonus, advAmt,loanAmt,specialBonus, netSal,month);
                p2.setsalId(salID);
                off_prd_salCalDAO pDAO4 = new off_prd_salCalDAO();
                pDAO4.updateoff_prd_salCal(p2);
                loadOff_Prd_sal_Table();
                JOptionPane.showMessageDialog(rootPane, "Updated Sucessfully");
                
            } catch (SQLException ex) {
                Logger.getLogger(PayrollGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
                         
                                lblsalIdDisplay.setText("");
               cmbEmpName.setSelectedIndex(0);
               txtEmpId.setText("");
               txtEmpId.setText("");
               txtDesignation.setText("");
               txtBasicSalary.setText("");
               txtSinOtHrs.setText("");
               txtDoubleOtHrs.setText("");
               txtAtndnceBonus.setText("");
               txtAdvanceAmt.setText("");
               txtLoanAmt.setText("");
               txtSpecialBonus.setText("");
               txtNetSal.setText("");    
                btnInsetSal.setEnabled(true);
            }
            }
        }
        else{
               lblsalIdDisplay.setText("");
               cmbEmpName.setSelectedIndex(0);
               txtEmpId.setText("");
               txtEmpId.setText("");
               txtDesignation.setText("");
               txtBasicSalary.setText("");
               txtSinOtHrs.setText("");
               txtDoubleOtHrs.setText("");
               txtAtndnceBonus.setText("");
               txtAdvanceAmt.setText("");
               txtLoanAmt.setText("");
               txtSpecialBonus.setText("");
               txtNetSal.setText("");
               
               txtSinOtHrs.setBackground(Color.WHITE);
               txtDoubleOtHrs.setBackground(Color.WHITE);
               txtAtndnceBonus.setBackground(Color.WHITE);
               txtSpecialBonus.setBackground(Color.WHITE);
           

        
           btnInsetSal.setEnabled(true);
        }
    }//GEN-LAST:event_btnUpdateSalActionPerformed

    private void cmbEmpNamePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbEmpNamePopupMenuWillBecomeInvisible
             
    }//GEN-LAST:event_cmbEmpNamePopupMenuWillBecomeInvisible

    private void btnInsertLoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertLoanActionPerformed
      
             SimpleDateFormat dte = new SimpleDateFormat("YYY-MM-dd",Locale.getDefault()); 
        
            if( txtLoanLoanAmt.getText().isEmpty() || cmbLoanStatus.getSelectedItem()=="Select Loan Status")
            {
                 JOptionPane.showMessageDialog(rootPane, "One or more empty fields. Please enter valid data!"); 
   
                if(txtLoanLoanAmt.getText().isEmpty()){

                   txtLoanLoanAmt.setBackground(Color.PINK);
                   }
                
               }
            else{
                     if(loanLoamAmt )
                     {
                          JOptionPane.showMessageDialog(rootPane, "One or more Invalid fields. Please enter valid data!"); 
   
                     }
                     else{
                        int empId=Integer.parseInt(txtLoanEmpId.getText());
                        String ename=txtLoanEmpName.getText();
                        String dname=txtLoanDeptName.getText();
                        double loanAmt=Double.parseDouble(txtLoanLoanAmt.getText());
                        String lentDate=dte.format(txtLentDate.getDate());
                        double monhtlyDue=Double.parseDouble(txtMonthlyDue.getText());
                        double totalDue=Double.parseDouble(txtLoanDue.getText());
                        String status=cmbLoanStatus.getSelectedItem().toString();


                        try {
                              loanHandling ln1=new loanHandling(empId, ename, dname, loanAmt, lentDate, monhtlyDue, totalDue, status);
                              loanHandlingDAO lnDAO1=new loanHandlingDAO();
                              lnDAO1.loanAddEntry(ln1);
                              JOptionPane.showMessageDialog(rootPane, "Inserted Sucessfully!!");
                              loadLoanTable();




                     } catch (SQLException | HeadlessException e) {

                     }  
                        
                         lblLoanIdDisplay.setText("");
                        txtLoanEmpId.setText("");
                        txtLoanEmpId.setText("");
                        txtLoanEmpName.setText("");
                        txtLoanDeptName.setText("");
                        txtLoanLoanAmt.setText("");
             //           txtLentDate.setText("");
                        txtMonthlyDue.setText("");
                        txtLoanDue.setText("");
                        cmbLoanStatus.setSelectedIndex(0);
                 }      
              
                       
        }   
    }//GEN-LAST:event_btnInsertLoanActionPerformed

    private void btnUpdateLoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateLoanActionPerformed
        
      int cnfrm= JOptionPane.showConfirmDialog(rootPane, "Proceed Update?");
      
        btnInsertLoan.setEnabled(true);
        
          if(cnfrm==0){ 
        
            if((txtLoanLoanAmt.getText().isEmpty())|| (cmbLoanStatus.getSelectedItem()=="SelectLoan Status") )
            {        
                   JOptionPane.showMessageDialog(rootPane, "One or more Fileds are empty! Please enter valid data ");
                   if((txtLoanLoanAmt.getText().isEmpty()))
                   {
                      txtLoanLoanAmt.setBackground(Color.PINK);
                   }   
            }
        
           else{
                
                if(loanLoamAmt)
                {
                 JOptionPane.showMessageDialog(rootPane, "One or more Invalid fields! Please enter valid data "); 
                }
                else{
                    SimpleDateFormat dte = new SimpleDateFormat("YYY-MM-dd",Locale.getDefault());  

                  int empId=Integer.parseInt(txtLoanEmpId.getText());
                        String ename=txtLoanEmpName.getText();
                        String dname=txtLoanDeptName.getText();
                        double loanAmt=Double.parseDouble(txtLoanLoanAmt.getText());
                        String lentDate=dte.format(txtLentDate.getDate());
                        double monhtlyDue=Double.parseDouble(txtMonthlyDue.getText());
                        double totalDue=Double.parseDouble(txtLoanDue.getText());
                        String status=cmbLoanStatus.getSelectedItem().toString();

                    try{
                         loanHandling ln1=new loanHandling(empId, ename, dname, loanAmt, lentDate, monhtlyDue, totalDue, status);
                              ln1.setloanId(Integer.parseInt(lblLoanIdDisplay.getText()));
                              loanHandlingDAO lnDAO1=new loanHandlingDAO();
                              lnDAO1.updatLoan(ln1);
                              JOptionPane.showMessageDialog(rootPane, "Updated Sucessfully");
                              loadLoanTable();
    
                       }catch(NumberFormatException | SQLException | HeadlessException e)
                       {
                               System.out.println(e);      
                       }
                    
                    
                        
                         lblLoanIdDisplay.setText("");
                        txtLoanEmpId.setText("");
                        txtLoanEmpId.setText("");
                        txtLoanEmpName.setText("");
                        txtLoanDeptName.setText("");
                        txtLoanLoanAmt.setText("");
             //           txtLentDate.setText("");
                        txtMonthlyDue.setText("");
                        txtLoanDue.setText("");
                        cmbLoanStatus.setSelectedIndex(0);
                }         
            }
    
          } 
          
          else{
                       lblLoanIdDisplay.setText("");
                        txtLoanEmpId.setText("");
                        txtLoanEmpId.setText("");
                        txtLoanEmpName.setText("");
                        txtLoanDeptName.setText("");
                        txtLoanLoanAmt.setText("");
             //           txtLentDate.setText("");
                        txtMonthlyDue.setText("");
                        txtLoanDue.setText("");
                        cmbLoanStatus.setSelectedIndex(0);
                        
          }
    }//GEN-LAST:event_btnUpdateLoanActionPerformed

    private void tblLoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoanMouseClicked
          
        btnInsertLoan.setEnabled(false);
        
        int row=tblLoan.getSelectedRow();
        
        String loanId=tblLoan.getValueAt(row, 0).toString();
        String empId=tblLoan.getValueAt(row, 1).toString();
        String empName=tblLoan.getValueAt(row, 2).toString();
        String deptName=tblLoan.getValueAt(row, 3).toString();
        String loanAmt=tblLoan.getValueAt(row, 4).toString();
        String lentDate=tblLoan.getValueAt(row, 5).toString();
        String MonthlyDue=tblLoan.getValueAt(row, 6).toString();
        String TotalDue=tblLoan.getValueAt(row, 8).toString();
        String Status=tblLoan.getValueAt(row, 7).toString();
       
        
        
        lblLoanIdDisplay.setText(loanId);
        txtLoanEmpId.setText(empId);
        txtLoanEmpName.setText(empName);
        txtLoanLoanAmt.setText(loanAmt);
        txtLoanDeptName.setText(deptName);
       // txtLentDate.setText(lentDate);
        txtMonthlyDue.setText(MonthlyDue);
        txtLoanDue.setText(TotalDue);
        cmbLoanStatus.setSelectedItem(Status);
        
        txtLoanEmpId.setBackground(Color.WHITE);
        txtLoanEmpName.setBackground(Color.WHITE);
        txtLoanLoanAmt.setBackground(Color.WHITE);
        txtLoanDeptName.setBackground(Color.WHITE);
        txtMonthlyDue.setBackground(Color.WHITE);
        txtLoanDue.setBackground(Color.WHITE);
        
    }//GEN-LAST:event_tblLoanMouseClicked

    private void btnDeleteLoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteLoanActionPerformed
       
           int cnfrm=JOptionPane.showConfirmDialog(rootPane, "Do you relly want to delete");
         if(cnfrm==0){
            int loanId=Integer.parseInt(lblLoanIdDisplay.getText());
             loanHandling ln= new loanHandling();
             ln.setloanId(loanId);
             loanId=ln.getloanId();
             loanHandlingDAO lnDAO = new loanHandlingDAO();
             lnDAO.deleteLoanEntry(loanId);
             loadLoanTable();
              JOptionPane.showMessageDialog(rootPane, "Deleted Sucessfully");
        
             lblLoanIdDisplay.setText("");
             txtLoanEmpId.setText("");
             txtLoanEmpId.setText("");
             txtLoanEmpName.setText("");
             txtLoanDeptName.setText("");
             txtLoanLoanAmt.setText("");
            // txtLentDate.setText("");
             txtMonthlyDue.setText("");
             txtLoanDue.setText("");
             cmbLoanStatus.setSelectedIndex(0);
             
             btnInsertLoan.setEnabled(true);

         }   
    }//GEN-LAST:event_btnDeleteLoanActionPerformed

    private void btnDistributionStfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDistributionStfActionPerformed
         // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels
        
              pnlPayrollMasterFile.setVisible(false);
              pnlPayrollOffPrdStfSal.setVisible(false);
              pnlPayrollDistributionStaffSal.setVisible(true);
              pnlPayrollFoldingSatffl.setVisible(false);
              pnlPayrollLoan.setVisible(false);
              pnlPayrolAdvancel.setVisible(false);
              pnlPayrollWelcome.setVisible(false);
        
        rightPanel.add(pnlPayrollDistributionStaffSal);
        rightPanel.repaint();
        rightPanel.revalidate();
    }//GEN-LAST:event_btnDistributionStfActionPerformed

    private void txtBasicSalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBasicSalaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBasicSalaryActionPerformed

    private void txtAdvEmpNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdvEmpNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAdvEmpNameActionPerformed

    private void txtAdvEmpIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdvEmpIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAdvEmpIdActionPerformed

    private void txtAdvDeptNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdvDeptNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAdvDeptNameActionPerformed

    private void txtAdvAdvAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdvAdvAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAdvAdvAmountActionPerformed

    private void btnAdverachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdverachActionPerformed
         String searchKey=txtAdvSearch.getText();
         advanceDetailsDAO adDAO1=new advanceDetailsDAO();
         rs=adDAO1.search(searchKey);
         tblAdvance.setModel( DbUtils.resultSetToTableModel(rs));
    }//GEN-LAST:event_btnAdverachActionPerformed

    private void txtAdvSearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAdvSearchMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAdvSearchMouseEntered

    private void txtAdvSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdvSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAdvSearchActionPerformed

    private void txtmfDoubleOtRateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmfDoubleOtRateKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmfDoubleOtRateKeyTyped

    private void txtPrdOffSearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPrdOffSearchMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrdOffSearchMouseEntered

    private void txtPrdOffSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrdOffSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrdOffSearchActionPerformed

    private void btnPrdOffSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrdOffSearchActionPerformed
        
          String searchKey=txtPrdOffSearch.getText();
         off_prd_salCalDAO opDAO1=new off_prd_salCalDAO();
         rs=opDAO1.search(searchKey);
         tbl_off_prd_sal.setModel( DbUtils.resultSetToTableModel(rs));
    }//GEN-LAST:event_btnPrdOffSearchActionPerformed

    private void txtLoanEmpIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoanEmpIdActionPerformed
        
            
            
    }//GEN-LAST:event_txtLoanEmpIdActionPerformed

    private void txtLoanEmpIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoanEmpIdKeyPressed
         
    }//GEN-LAST:event_txtLoanEmpIdKeyPressed

    private void txtLoanEmpIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoanEmpIdKeyReleased
            
    }//GEN-LAST:event_txtLoanEmpIdKeyReleased

    private void txtLoanLoanAmtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoanLoanAmtKeyReleased
       
         String decimalPattern = "^[\\d.]+$";  
         String number=txtLoanLoanAmt.getText();  
         boolean match = Pattern.matches(decimalPattern, number);
          if(match==false)
          {
             //lblLoanErrorMsg.setText("*Invalid Data Value!! Please Re enter data"  );
             //lblLoanErrorLoanAmt.setText("*");
              
              txtLoanLoanAmt.setBackground(Color.PINK);
              loanLoamAmt=true;
          }
          
          else
          {
              //lblLoanErrorMsg.setText(" "  );
             // lblLoanErrorLoanAmt.setText("");
             txtLoanLoanAmt.setBackground(Color.WHITE);
             loanLoamAmt=false; 
          }
        
          
           double loanAmt=Double.parseDouble(txtLoanLoanAmt.getText());
           double monthlyDue= (loanAmt/100)*5;
           String monDue=String.valueOf(monthlyDue);
           txtMonthlyDue.setText(monDue);
           txtLoanDue.setText(txtLoanLoanAmt.getText());
           
           
        
           
    }//GEN-LAST:event_txtLoanLoanAmtKeyReleased

    private void btnAdvAddEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdvAddEntryActionPerformed
        
          SimpleDateFormat dte = new SimpleDateFormat("YYY-MM-dd",Locale.getDefault());
        
        if((txtAdvAdvAmount.getText().isEmpty())|| (cmbAdvStatus.getSelectedItem()=="Select Status") )
        {
               JOptionPane.showMessageDialog(rootPane, "Cannot insert empty data! Please enter valid data ");
               if((txtAdvAdvAmount.getText().isEmpty()))
               {
                  txtAdvAdvAmount.setBackground(Color.PINK);
               } 
               
               
        }
        
         else{ 
                if(advAmt)
                {
                     JOptionPane.showMessageDialog(rootPane, "One or more invalid fields! Please enter valid data ");
                
                }
                else{

                    int empId=Integer.parseInt(txtAdvEmpId.getText());
                    String empName=txtAdvEmpName.getText();
                    String deptName=txtAdvDeptName.getText();
                    double advAmount=Double.parseDouble(txtAdvAdvAmount.getText());
                    String date=dte.format(txtAdvDate.getDate());
                    String Status=cmbAdvStatus.getSelectedItem ().toString();
                    String month=lblAdvMonthDisplay.getText();

                  try{
                     advanceDetails ad1=new advanceDetails(empId, empName, deptName, advAmount, date, Status, month);
                     advanceDetailsDAO adDAO1=new advanceDetailsDAO();
                     adDAO1.advanceAddEntry(ad1);
                     loadAdvanceTable();
                     JOptionPane.showMessageDialog(rootPane, "Data inserted Sucessfully!!");
         
                            lblAdvIdDisplay.setText("");
                            txtAdvEmpId.setText("");
                            txtAdvEmpName.setText("");
                            txtAdvDeptName.setText("");
                            txtAdvAdvAmount.setText("");
                          
                            cmbAdvStatus.setSelectedIndex(0);
            
            
                            }catch(SQLException | HeadlessException e)
                            {
                                  System.out.println(e);       
                            }    
                  }
                }
        
                     btnAdvAddEntry.setEnabled(true);
    }//GEN-LAST:event_btnAdvAddEntryActionPerformed

    private void txtAdvEmpIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdvEmpIdKeyReleased
            
    }//GEN-LAST:event_txtAdvEmpIdKeyReleased

    private void btnAdvUpdateEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdvUpdateEntryActionPerformed

        int cnfrm= JOptionPane.showConfirmDialog(rootPane, "Proceed Update?");
      
        btnAdvAddEntry.setEnabled(true);
        
          if(cnfrm==0){ 
        
            if((txtAdvAdvAmount.getText().isEmpty())|| (cmbAdvStatus.getSelectedItem()=="Select Status") )
            {        
                   JOptionPane.showMessageDialog(rootPane, "One or more Fileds are empty! Please enter valid data ");
                   if((txtAdvAdvAmount.getText().isEmpty()))
                   {
                      txtAdvAdvAmount.setBackground(Color.PINK);
                   }   
            }
        
           else{
                
                if(advAmt)
                {
                 JOptionPane.showMessageDialog(rootPane, "One or more Invalid fields! Please enter valid data "); 
                }
                else{
                    SimpleDateFormat dte = new SimpleDateFormat("YYY-MM-dd",Locale.getDefault());  

                   int empId=Integer.parseInt(txtAdvEmpId.getText());
                   String empName=txtAdvEmpName.getText();
                   String deptName=txtAdvDeptName.getText();
                   double advAmount=Double.parseDouble(txtAdvAdvAmount.getText());
                   String date=dte.format(txtAdvDate.getDate());
                   String Status=cmbAdvStatus.getSelectedItem().toString();
                   String month=lblAdvMonthDisplay.getText();

                    try{
                         advanceDetails ad1=new advanceDetails(empId, empName, deptName, advAmount, date, Status, month);
                         advanceDetailsDAO adDAO3=new advanceDetailsDAO();
                         ad1.setAdvID(Integer.parseInt(lblAdvIdDisplay.getText()));
                         adDAO3.updateAdvEntry(ad1);
                         loadAdvanceTable();
                        JOptionPane.showMessageDialog(rootPane, "Updated Sucessfully");
                       }catch(NumberFormatException | SQLException | HeadlessException e)
                       {
                               System.out.println(e);      
                       }
                    
                        txtAdvAdvAmount.setBackground(Color.WHITE);
                        lblAdvIdDisplay.setText("");
                        txtAdvEmpId.setText("");
                        txtAdvEmpName.setText("");
                        txtAdvDeptName.setText("");
                        txtAdvAdvAmount.setText("");
                        //txtAdvDate.setText("");
                        cmbAdvStatus.setSelectedIndex(0);
                }         
            }
    
          } 
          
          else{
          
                txtAdvAdvAmount.setBackground(Color.WHITE);
                lblAdvIdDisplay.setText("");
                txtAdvEmpId.setText("");
                txtAdvEmpName.setText("");
                txtAdvDeptName.setText("");
                txtAdvAdvAmount.setText("");
                //txtAdvDate.setText("");
                cmbAdvStatus.setSelectedIndex(0);
          }
    }//GEN-LAST:event_btnAdvUpdateEntryActionPerformed

    private void btnAdvDeleteEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdvDeleteEntryActionPerformed
         
        int cnfrm=JOptionPane.showConfirmDialog(rootPane, "Do you really want to delete? ");
       
        if(cnfrm==0){        
         int advId=Integer.parseInt(lblAdvIdDisplay.getText());
          advanceDetailsDAO adDAO4=new advanceDetailsDAO();
          adDAO4.deletemfEntry(advId);
         loadAdvanceTable();
          
        }
        
            lblAdvIdDisplay.setText("");
            txtAdvEmpId.setText("");
            txtAdvEmpName.setText("");
            txtAdvDeptName.setText("");
            txtAdvAdvAmount.setText("");
            cmbAdvStatus.setSelectedIndex(0);
        
         btnAdvAddEntry.setEnabled(true);
    }//GEN-LAST:event_btnAdvDeleteEntryActionPerformed

    private void tblAdvanceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAdvanceMouseClicked
        
        int row=tblAdvance.getSelectedRow();
        
        btnAdvAddEntry.setEnabled(false);
        
        String advId=tblAdvance.getValueAt(row, 0).toString();
        String empID=tblAdvance.getValueAt(row, 1).toString();
        String empName=tblAdvance.getValueAt(row, 2).toString();
        String deptName=tblAdvance.getValueAt(row, 3).toString();
        String advAmount=tblAdvance.getValueAt(row, 4).toString();
        String date=tblAdvance.getValueAt(row, 5).toString();
        String status=tblAdvance.getValueAt(row, 6).toString();
        String month=tblAdvance.getValueAt(row, 7).toString();
        
        
        lblAdvMonthDisplay.setText(month);
        lblAdvIdDisplay.setText(advId);   
        txtAdvEmpId.setText(empID); 
        txtAdvEmpName.setText(empName); 
        txtAdvDeptName.setText(deptName); 
        txtAdvAdvAmount.setText(advAmount); 
        //txtAdvDate.setText(date);
        cmbAdvStatus.setSelectedItem(status);
        
    }//GEN-LAST:event_tblAdvanceMouseClicked

    private void btnCalcSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcSalActionPerformed
         
            double netSal;
          
        if( cmbEmpName.getSelectedItem().equals("Select employee")||txtSinOtHrs.getText().isEmpty()||txtDoubleOtHrs.getText().isEmpty()||txtAtndnceBonus.getText().isEmpty()|| txtSpecialBonus.getText().isEmpty())
        {           
                txtNetSal.setText("");
              
                 JOptionPane.showMessageDialog(rootPane, "One or more fields are empty! ");
                 if(txtSinOtHrs.getText().isEmpty()){
                    txtSinOtHrs.setBackground(Color.PINK); 
                 }
                 if(txtDoubleOtHrs.getText().isEmpty()){
                    txtDoubleOtHrs.setBackground(Color.PINK); 
                 }
                 if(txtAtndnceBonus.getText().isEmpty()){
                    txtAtndnceBonus.setBackground(Color.PINK); 
                 }
                 if(txtSpecialBonus.getText().isEmpty()){
                    txtSpecialBonus.setBackground(Color.PINK); 
                 }
                
        }   
        else{
            if(offPrdDoubleOtRate||offPrdSpecialBonus||offprdAttBonus||offprdSinOtRate||offPrdNetSal){
                JOptionPane.showMessageDialog(this, "One or more fields are invalid");
                
                txtNetSal.setText("");
            }
            else{
                 
                     txtNetSal.setBackground(Color.WHITE);
        
                    int empId=Integer.parseInt(txtEmpId.getText());
                    double basicSal=Double.parseDouble(txtBasicSalary.getText());
                    double sinOtHrs=Double.parseDouble(txtSinOtHrs.getText());
                    double doubleOtHrs=Double.parseDouble(txtDoubleOtHrs.getText());
                    double advAmnt=Double.parseDouble(txtAdvanceAmt.getText());
                    double loanAmt=Double.parseDouble(txtLoanAmt.getText());
                    double attBonus=Double.parseDouble(txtAtndnceBonus.getText());
                    double SpecialBonus=Double.parseDouble(txtSpecialBonus.getText());

        
                       try {
                                off_prd_salCalDAO opDAO=new off_prd_salCalDAO();
                                netSal=opDAO.calcSal(empId, basicSal,SpecialBonus, sinOtHrs, doubleOtHrs, loanAmt, advAmnt, attBonus);
                                txtNetSal.setText(String.valueOf(netSal));
                           } 
                    catch (Exception e) 
                           {
                                 System.out.println(e);                           
                           }
        
                        
                }
            }
       
    }//GEN-LAST:event_btnCalcSalActionPerformed

    private void btnDeleteSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSalActionPerformed

          int cnfrm=JOptionPane.showConfirmDialog(rootPane, "Do you really want to delete?");
        
          if(cnfrm==0){
            int salId=Integer.parseInt(lblsalIdDisplay.getText());
            off_prd_salCal op=new off_prd_salCal();
            op.setsalId(salId);
            off_prd_salCalDAO opDAO= new off_prd_salCalDAO();
            opDAO.delete_off_prd_salCalEntry(salId);
            loadOff_Prd_sal_Table();
            JOptionPane.showMessageDialog(rootPane, "Deleted Sucessfully");
          }  
               lblsalIdDisplay.setText("");
               cmbEmpName.setSelectedIndex(0);
               txtEmpId.setText("");
               txtEmpId.setText("");
               txtDesignation.setText("");
               txtBasicSalary.setText("");
               txtSinOtHrs.setText("");
               txtDoubleOtHrs.setText("");
               txtAtndnceBonus.setText("");
               txtAdvanceAmt.setText("");
               txtLoanAmt.setText("");
               txtSpecialBonus.setText("");
               txtNetSal.setText("");
          
              
          btnInsetSal.setEnabled(true);
          
    }//GEN-LAST:event_btnDeleteSalActionPerformed

    private void txtLoanLoanAmtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoanLoanAmtKeyTyped
        
        
    }//GEN-LAST:event_txtLoanLoanAmtKeyTyped

    private void txtLoanSearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLoanSearchMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoanSearchMouseEntered

    private void txtLoanSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoanSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoanSearchActionPerformed

    private void btnloanSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnloanSearchActionPerformed
         int empId=Integer.parseInt(txtLoanSearch.getText());
         loanHandlingDAO lnDAO1=new loanHandlingDAO();
         rs=lnDAO1.search(empId);
         tblLoan.setModel( DbUtils.resultSetToTableModel(rs));
    }//GEN-LAST:event_btnloanSearchActionPerformed

    private void txtmfEmpIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmfEmpIDKeyReleased
         String decimalPattern = "^[\\d.]+$";  
         String number=txtmfEmpID.getText();  
         boolean match = Pattern.matches(decimalPattern, number);
          if(match==false)
          {
            // lblErrorMsg.setText("*Invalid Data Values!! Please Re enter data"  );
            //lblmfErrorBasicSal.setText("*");
              
              txtmfEmpID.setBackground(Color.PINK);
              mfbasicSal=true;
          }
          
          else
          {
              //lblErrorMsg.setText(" "  );
              //lblmfErrorBasicSal.setText("");
              
              txtmfEmpID.setBackground(Color.WHITE);
              mfbasicSal=false;
              
          }
    }//GEN-LAST:event_txtmfEmpIDKeyReleased

    private void txtmfSingleOtRateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmfSingleOtRateKeyReleased
        
        String decimalPattern = "^[\\d.]+$";  
         String number=txtmfSingleOtRate.getText();  
         boolean match = Pattern.matches(decimalPattern, number);
          if(match==false)
          {
            // lblErrorMsg.setText("*Invalid Data Values!! Please Re enter data"  );
            // lblMfErrorSinOt.setText("*");
              
              txtmfSingleOtRate.setBackground(Color.PINK);
              mfsinOtRate=true;
          }
          
          else
          {
              //lblErrorMsg.setText(" "  );
              //lblMfErrorSinOt.setText("");
              
               txtmfSingleOtRate.setBackground(Color.WHITE);
               mfsinOtRate=false;
          }
    }//GEN-LAST:event_txtmfSingleOtRateKeyReleased

    private void txtmfDoubleOtRateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmfDoubleOtRateKeyReleased
        
        String decimalPattern = "^[\\d.]+$";  
         String number=txtmfDoubleOtRate.getText();  
         boolean match = Pattern.matches(decimalPattern, number);
          if(match==false)
          {
            // lblErrorMsg.setText("*Invalid Data Values!! Please Re enter data"  );
            // lblMfErrorDoubleOt.setText("*");
              
              txtmfDoubleOtRate.setBackground(Color.PINK);
              mfdoubleOtRate=true;
          }
          
          else
          {
             // lblErrorMsg.setText(" "  );
             // lblMfErrorDoubleOt.setText("");
              
             txtmfDoubleOtRate.setBackground(Color.WHITE);
             mfdoubleOtRate=false;
          }
    }//GEN-LAST:event_txtmfDoubleOtRateKeyReleased

    private void txtmfEpfRateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmfEpfRateKeyReleased
        String decimalPattern = "^[\\d.]+$";  
         String number=txtmfEpfRate.getText();  
         boolean match = Pattern.matches(decimalPattern, number);
          if(match==false)
          {
            // lblErrorMsg.setText("*Invalid Data Values!! Please Re enter data"  );
            // lblmfErrorEpf.setText("*");
              
             txtmfEpfRate.setBackground(Color.PINK);
             mfepf=true;
          }
          
          else
          {
              //lblErrorMsg.setText(" "  );
             // lblmfErrorEpf.setText("");
              
              txtmfEpfRate.setBackground(Color.WHITE);
              mfepf=false;
          }
    }//GEN-LAST:event_txtmfEpfRateKeyReleased

    private void txtmfPeiceRateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmfPeiceRateKeyReleased
      
        String decimalPattern = "^[\\d.]+$";  
         String number=txtmfPeiceRate.getText();  
         boolean match = Pattern.matches(decimalPattern, number);
          if(match==false)
          {   
             txtmfPeiceRate.setBackground(Color.PINK);
             mfpieceRAte=true;
          }
          
          else
          {
              txtmfPeiceRate.setBackground(Color.WHITE);
              mfpieceRAte=false;
          }
          
    }//GEN-LAST:event_txtmfPeiceRateKeyReleased

    private void txtAdvAdvAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdvAdvAmountKeyReleased
        
        String decimalPattern = "^[\\d.]+$";  
         String number=txtAdvAdvAmount.getText();  
         boolean match = Pattern.matches(decimalPattern, number);
          if(match==false)
          {
             txtAdvAdvAmount.setBackground(Color.PINK);
             advAmt=true; 
             
          }
          
          else
          {
             advAmt=false;
             txtAdvAdvAmount.setBackground(Color.WHITE);
          }
    }//GEN-LAST:event_txtAdvAdvAmountKeyReleased

    private void txtDistSalNetSalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDistSalNetSalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDistSalNetSalKeyReleased

    private void txtDistSalNetSalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDistSalNetSalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDistSalNetSalKeyTyped

    private void txtDistSearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDistSearchMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDistSearchMouseEntered

    private void txtDistSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDistSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDistSearchActionPerformed

    private void btnDistSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDistSearchActionPerformed
        
        String searchKey=txtDistSearch.getText();
         distributionStaffSalDAO disDAO1=new distributionStaffSalDAO();
         rs=disDAO1.search(searchKey);
         tblDistributionSal.setModel( DbUtils.resultSetToTableModel(rs));
        
    }//GEN-LAST:event_btnDistSearchActionPerformed

    private void txtDistSalDesignationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDistSalDesignationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDistSalDesignationActionPerformed

    private void txtDistSalDesignationKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDistSalDesignationKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDistSalDesignationKeyPressed

    private void txtDistSalDesignationKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDistSalDesignationKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDistSalDesignationKeyReleased

    private void txtDistSalAdvAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDistSalAdvAmountKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDistSalAdvAmountKeyReleased

    private void txtDistSalAdvAmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDistSalAdvAmountKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDistSalAdvAmountKeyTyped

    private void btnDistDeleteSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDistDeleteSalActionPerformed
         
        int cnfrm=JOptionPane.showConfirmDialog(rootPane, "Do you really want to delete?");
        
          if(cnfrm==0){
            int salId=Integer.parseInt(lblDistSalIdDisplay.getText());
            distributionStaffSal d3=new distributionStaffSal();
            d3.setsalId(salId);
            distributionStaffSalDAO disDAO= new distributionStaffSalDAO();
            disDAO.deleteDistSalEntry(salId);
            loadDistStfSalTable();
            JOptionPane.showMessageDialog(rootPane, "Deleted Sucessfully");
            
              
          }
              
                            txtEmpId.setText("");
                            cmbDistSalEmpName.setSelectedIndex(0);
                            txtDistSalDesignation.setText("");
                            txtDistSalDeptName.setText("");
                            txtDistSalBasicSal.setText("");
                            txtDistSalAdvAmount.setText("");
                            txtDistSalSpecialBonus.setText("");
                            txtDistSalNetSal.setText("");
          
                        btnDistInsertSal.setEnabled(true);
          
        
        
    }//GEN-LAST:event_btnDistDeleteSalActionPerformed

    private void btnDistUpdateSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDistUpdateSalActionPerformed
        
         int cnfrm=JOptionPane.showConfirmDialog(rootPane, "Proceed update?");
        
        if(cnfrm==0){
        if(cmbDistSalEmpName.getSelectedItem().equals("Select Employee") ||txtDistSalSpecialBonus.getText().isEmpty() ||txtDistSalNetSal.getText().isEmpty() )
        {  
                 JOptionPane.showMessageDialog(rootPane, "One or more fields are empty! ");
                 if(txtDistSalSpecialBonus.getText().isEmpty()){
                    txtDistSalSpecialBonus.setBackground(Color.PINK); 
                 }
                 
                 if(txtDistSalNetSal.getText().isEmpty()){
                    txtDistSalNetSal.setBackground(Color.PINK); 
                 }
        }   
        else{
            if(distSalSpecialBonus){
                
                JOptionPane.showMessageDialog(this, "One or more fields are invalid");
            }
            else{   
                                     String month=lbldistMonthDisplay.getText();
                                     int salId=Integer.parseInt(lblDistSalIdDisplay.getText());
                                    int empId=Integer.parseInt(txtDistSalEmpID.getText());
                                    String empName=cmbDistSalEmpName.getSelectedItem().toString();
                                    String deptName=txtDistSalDeptName.getText();
                                    String desig=txtDistSalDesignation.getText();
                                    double basicSal=Double.parseDouble(txtDistSalBasicSal.getText());
                                    double advAmt=Double.parseDouble(txtDistSalAdvAmount.getText());
                                    double specialBonus=Double.parseDouble(txtDistSalSpecialBonus.getText());
                                    double netSal=Double.parseDouble(txtDistSalNetSal.getText());

        
                                 try {  
                                    distributionStaffSal d1=new distributionStaffSal(empId, empName, deptName, desig, basicSal, advAmt, specialBonus, netSal, month);
                                    d1.setsalId(salId);
                                    //DisDAO1.updateAdvStatus(empId, month);
                                    distributionStaffSalDAO DisDAO1=new distributionStaffSalDAO();
                                    DisDAO1.updateDistStaffSalCal(d1);
                                    loadDistStfSalTable();
                                    JOptionPane.showMessageDialog(rootPane, "Record inserted Sucessfully");

                                 } catch (SQLException ex) {
                                         Logger.getLogger(PayrollGUI.class.getName()).log(Level.SEVERE, null, ex);
                                       }
                                 
                                 txtEmpId.setText("");
                            cmbDistSalEmpName.setSelectedIndex(0);
                            txtDistSalDesignation.setText("");
                            txtDistSalDeptName.setText("");
                            txtDistSalBasicSal.setText("");
                            txtDistSalAdvAmount.setText("");
                            txtDistSalSpecialBonus.setText("");
                            txtDistSalNetSal.setText("");
                            btnDistInsertSal.setEnabled(true);
                }
            }
        }
        
        else{
                            txtEmpId.setText("");
                            cmbDistSalEmpName.setSelectedIndex(0);
                            txtDistSalDesignation.setText("");
                            txtDistSalDeptName.setText("");
                            txtDistSalBasicSal.setText("");
                            txtDistSalAdvAmount.setText("");
                            txtDistSalSpecialBonus.setText("");
                            txtDistSalNetSal.setText("");
                                  
               
              
             
           txtDistSalSpecialBonus.setBackground(Color.WHITE);
           txtDistSalSpecialBonus.setBackground(Color.WHITE);
        
           btnDistInsertSal.setEnabled(true);
        }
    }//GEN-LAST:event_btnDistUpdateSalActionPerformed

    private void btnDistInsertSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDistInsertSalActionPerformed
        
        
       if(cmbDistSalEmpName.getSelectedItem().equals("Select Employee") ||txtDistSalSpecialBonus.getText().isEmpty() ||txtDistSalNetSal.getText().isEmpty() )
        {
                 JOptionPane.showMessageDialog(rootPane, "One or more fields are empty! ");
                 if(txtDistSalSpecialBonus.getText().isEmpty()){
                    txtDistSalSpecialBonus.setBackground(Color.PINK); 
                 }
                 
                 if(txtDistSalNetSal.getText().isEmpty()){
                    txtDistSalNetSal.setBackground(Color.PINK); 
                 }
        }   
        else{
            if(distSalSpecialBonus){
                JOptionPane.showMessageDialog(this, "One or more fields are invalid");
            }
            else{
                         String month=lbldistMonthDisplay.getText();
                         int empId=Integer.parseInt(txtDistSalEmpID.getText());
                         System.out.println("eid"+ empId);
                         distributionStaffSalDAO DisDAO1 = new distributionStaffSalDAO();
                         boolean results=DisDAO1.checkDuplicateValues(month,empId);
                    if(results==true){
                       JOptionPane.showMessageDialog(this, "Data already exists!!");
                
                }
                
                else{
                            //String month=lblSalMonth.getText();
                            //int empId=Integer.parseInt(txtEmpId.getText());
                            String empName=cmbDistSalEmpName.getSelectedItem().toString();
                            String deptName=txtDistSalDeptName.getText();
                            String desig=txtDistSalDesignation.getText();
                            double basicSal=Double.parseDouble(txtDistSalBasicSal.getText());
                            double advAmt=Double.parseDouble(txtDistSalAdvAmount.getText());
                            double specialBonus=Double.parseDouble(txtDistSalSpecialBonus.getText());
                            double netSal=Double.parseDouble(txtDistSalNetSal.getText());

                            try {
                                    distributionStaffSal d1=new distributionStaffSal(empId, empName, deptName, desig, basicSal, advAmt, specialBonus, netSal, month);
                                    DisDAO1.updateAdvStatus(empId,month);
                                    DisDAO1.distributionStaffSalAddEntry(d1);
                                    loadDistStfSalTable();
                                    loadAdvanceTable();
                                    JOptionPane.showMessageDialog(rootPane, "Record inserted Sucessfully");


                                } catch (SQLException ex) {
                                    Logger.getLogger(PayrollGUI.class.getName()).log(Level.SEVERE, null, ex);
                                }
                }
                    
                    

                            txtEmpId.setText("");
                            cmbDistSalEmpName.setSelectedIndex(0);
                            txtDistSalDesignation.setText("");
                            txtDistSalDeptName.setText("");
                            txtDistSalBasicSal.setText("");
                            txtDistSalAdvAmount.setText("");
                            txtDistSalSpecialBonus.setText("");
                            txtDistSalNetSal.setText("");
                                  
        }
        }
        
        
        
    }//GEN-LAST:event_btnDistInsertSalActionPerformed

    private void btnDistCalcSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDistCalcSalActionPerformed
     
        double netSal=0;
        
        if(cmbDistSalEmpName.getSelectedItem().equals("Select Employee") ||txtDistSalSpecialBonus.getText().isEmpty())
        {
                 JOptionPane.showMessageDialog(rootPane, "One or more fields are empty! ");
                 if(txtDistSalSpecialBonus.getText().isEmpty()){
                    txtDistSalSpecialBonus.setBackground(Color.PINK); 
                 }
        }   
        else{
            if(distSalSpecialBonus){
                JOptionPane.showMessageDialog(this, "One or more fields are invalid");
            }
            else{
        
              txtDistSalNetSal.setBackground(Color.WHITE);
                
         Double basicSal=Double.parseDouble(txtDistSalBasicSal.getText());
         Double advAmt=Double.parseDouble(txtDistSalAdvAmount.getText());
         Double specialBonus=Double.parseDouble(txtDistSalSpecialBonus.getText());
         
                try {
                    
                    distributionStaffSalDAO dDAO=new distributionStaffSalDAO();
                    netSal=dDAO.calcSal(basicSal, advAmt, specialBonus);
                
                } catch (Exception e) {
                    
                    System.out.println(e);
                }
         
         
         txtDistSalNetSal.setText(String.valueOf(netSal));
        } 
    
        }     
    }//GEN-LAST:event_btnDistCalcSalActionPerformed
    
    private void txtSinOtHrsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSinOtHrsKeyReleased

        txtNetSal.setText("");
        
         String decimalPattern = "^[\\d.]+$";  
         String number=txtSinOtHrs.getText();  
         boolean match = Pattern.matches(decimalPattern, number);
          if(match==false)
          {
            // lblOffPrdErrorMsg.setText("*Invalid Data Value!! Please Re enter data"  );
            // lblSinOtHrsError.setText("*");
              
              txtSinOtHrs.setBackground(Color.PINK);
              offprdSinOtRate=true;
          }
          
          else
          {
              
              txtSinOtHrs.setBackground(Color.WHITE);
              offprdSinOtRate=false;
          }
 
        
    }//GEN-LAST:event_txtSinOtHrsKeyReleased

    private void txtDoubleOtHrsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDoubleOtHrsKeyReleased
        
         txtNetSal.setText("");
        
         String decimalPattern = "^[\\d.]+$";  
         String number=txtDoubleOtHrs.getText();  
         boolean match = Pattern.matches(decimalPattern, number);
          if(match==false)
          {
              txtDoubleOtHrs.setBackground(Color.PINK);
              offPrdDoubleOtRate=true;
              
          }
          
          else
          {
             
              txtDoubleOtHrs.setBackground(Color.WHITE);
              offPrdDoubleOtRate=false;
              
          }
        
    }//GEN-LAST:event_txtDoubleOtHrsKeyReleased

    private void txtAtndnceBonusKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAtndnceBonusKeyReleased

        txtNetSal.setText("");
        
         String decimalPattern = "^[\\d.]+$";  
         String number=txtAtndnceBonus.getText();  
         boolean match = Pattern.matches(decimalPattern, number);
          if(match==false)
          {
             //lblOffPrdErrorMsg.setText("*Invalid Data Value!! Please Re enter data"  );
            // lblOffPrdSalAttBonusError.setText("*");
              
              txtAtndnceBonus.setBackground(Color.PINK);
              offprdAttBonus=true;
          }
          
          else
          {
              txtAtndnceBonus.setBackground(Color.WHITE);
              offprdAttBonus=false;
          }
    }//GEN-LAST:event_txtAtndnceBonusKeyReleased

    private void txtSpecialBonusKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSpecialBonusKeyReleased

         txtNetSal.setText("");
        
         String decimalPattern = "^[\\d.]+$";  
         String number=txtSpecialBonus.getText();  
         boolean match = Pattern.matches(decimalPattern, number);
          if(match==false)
          {
             //lblOffPrdErrorMsg.setText("*Invalid Data Value!! Please Re enter data"  );
             //lblOffPrdSalSpBounusError.setText("*");
              
              txtSpecialBonus.setBackground(Color.PINK);
              offPrdSpecialBonus=true;
              
          }
          
          else
          {
             // lblOffPrdErrorMsg.setText(" "  );
             // lblOffPrdSalSpBounusError.setText("");
              
              txtSpecialBonus.setBackground(Color.WHITE);
              offPrdSpecialBonus=false;

          }
    }//GEN-LAST:event_txtSpecialBonusKeyReleased

    private void cmbDistSalEmpNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbDistSalEmpNameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbDistSalEmpNameMouseClicked

    private void cmbDistSalEmpNamePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbDistSalEmpNamePopupMenuWillBecomeInvisible
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbDistSalEmpNamePopupMenuWillBecomeInvisible

    private void cmbDistSalEmpNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDistSalEmpNameActionPerformed
        
            String empName=cmbDistSalEmpName.getSelectedItem().toString();
            if("Select Employee".equals(empName)){
               txtDistSalEmpID.setText("");
            }
               else {
                
                    distributionStaffSalDAO DisDAO2=new distributionStaffSalDAO();
                    int eId=(DisDAO2.eIdOnValChange(empName));
                    txtDistSalEmpID.setText(String.valueOf(eId));
                    txtDistSalDeptName.setText(DisDAO2.dnameOnValChange(eId));
                    txtDistSalDesignation.setText(DisDAO2.desigOnValChange(eId));
                    txtDistSalBasicSal.setText(DisDAO2.basicSalOnValChange(txtDistSalDeptName.getText(),txtDistSalDesignation.getText(),eId));
                    double advAmount=DisDAO2.setSalAdvAmount(eId,lblSalMonth.getText());
                    txtDistSalAdvAmount.setText(String.valueOf(advAmount));
                       }
           
    }//GEN-LAST:event_cmbDistSalEmpNameActionPerformed

    private void cmbDistSalEmpNameInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_cmbDistSalEmpNameInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbDistSalEmpNameInputMethodTextChanged

    private void txtmfEmpIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtmfEmpIDMouseClicked
        txtmfEmpID.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtmfEmpIDMouseClicked

    private void txtmfSingleOtRateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtmfSingleOtRateMouseClicked
         txtmfSingleOtRate.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtmfSingleOtRateMouseClicked

    private void txtmfDoubleOtRateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtmfDoubleOtRateMouseClicked
        txtmfDoubleOtRate.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtmfDoubleOtRateMouseClicked

    private void txtmfEpfRateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtmfEpfRateMouseClicked
        txtmfEpfRate.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtmfEpfRateMouseClicked

    private void txtmfPeiceRateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtmfPeiceRateMouseClicked
        txtmfPeiceRate.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtmfPeiceRateMouseClicked

    private void txtAdvEmpNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdvEmpNameKeyReleased
            
            advanceDetailsDAO adDAO2=new advanceDetailsDAO();
            String empName= txtAdvEmpName.getText();
            txtAdvEmpId.setText(String.valueOf(adDAO2.eIdOnValChange(empName)));
            int eId=Integer.parseInt(txtAdvEmpId.getText());
            txtAdvDeptName.setText(adDAO2.dnameOnValChange(eId));
        
    }//GEN-LAST:event_txtAdvEmpNameKeyReleased

    private void txtLoanEmpNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoanEmpNameActionPerformed
        
            
        
    }//GEN-LAST:event_txtLoanEmpNameActionPerformed

    private void txtLoanEmpNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoanEmpNameKeyReleased
        loanHandlingDAO ln4=new loanHandlingDAO();
            String ename= txtLoanEmpName.getText();
            txtLoanEmpId.setText(String.valueOf(ln4.eIdOnValChange(ename)));            
            int eId=Integer.parseInt(txtLoanEmpId.getText());
            txtLoanDeptName.setText(ln4.dnameOnValChange(eId));
        
    }//GEN-LAST:event_txtLoanEmpNameKeyReleased

    private void cmbmfDepNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbmfDepNameActionPerformed
       
        //int itemCount = cmbmfEmpName.getItemCount();

//    for(int i=0;i<itemCount;i++){
//        cmbmfEmpName.removeItemAt(0);
//     }
        
        cmbmfEmpName.removeAllItems();
       
        cmbmfEmpName.setSelectedItem("Select Employee");
       
       
       if(cmbmfDepName.getSelectedItem().equals("Select Department") )
       {
       
              cmbmfEmpName.setSelectedItem("Select Employee");
       }       
        
       else{ 
       String deptName= cmbmfDepName.getSelectedItem().toString();

         if(deptName.equals("Folding Staff"))
           {
                  txtmfPeiceRate.setText("");
                  txtmfPeiceRate.setEditable(true);
           }
         else{
             
                  txtmfPeiceRate.setText(String.valueOf(0));
                  txtmfPeiceRate.setEditable(false);
         }
       masterFileDAO mfDAO=new masterFileDAO();
       rs=mfDAO.enameOnvalChange(deptName);
       
            try {
                while(rs.next()){
                    
                    String ename=rs.getString("name");
                    cmbmfEmpName.addItem(ename);
      
                }    
            
            
              } 
            
            
               catch (SQLException ex) {
                Logger.getLogger(PayrollGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        
       }
                             
                 
    }//GEN-LAST:event_cmbmfDepNameActionPerformed

    private void txtmfBasicSalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtmfBasicSalMouseClicked
        
        txtmfBasicSal.setBackground(Color.WHITE);
        
    }//GEN-LAST:event_txtmfBasicSalMouseClicked

    private void txtmfBasicSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmfBasicSalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmfBasicSalActionPerformed

    private void txtmfBasicSalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmfBasicSalKeyReleased
        
        String decimalPattern = "^[\\d.]+$";  
         String number=txtmfBasicSal.getText();  
         boolean match = Pattern.matches(decimalPattern, number);
          if(match==false)
          { 
              
              
             txtmfBasicSal.setBackground(Color.PINK);
             mfbasicSal=true;
          }
          
          else
          {   
              
              txtmfBasicSal.setBackground(Color.WHITE);
              mfbasicSal=false;
          }
        
    }//GEN-LAST:event_txtmfBasicSalKeyReleased

    private void txtmfBasicSalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmfBasicSalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmfBasicSalKeyTyped

    private void txtmfEmpPositionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtmfEmpPositionMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmfEmpPositionMouseClicked

    private void txtmfEmpPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmfEmpPositionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmfEmpPositionActionPerformed

    private void txtmfEmpPositionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmfEmpPositionKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmfEmpPositionKeyReleased

    private void txtmfEmpPositionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmfEmpPositionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmfEmpPositionKeyTyped

    private void cmbmfEmpNamePopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbmfEmpNamePopupMenuWillBecomeVisible
/*
        try{
            masterFileDAO mfDAO=new masterFileDAO();
            
            
            String ename=cmbmfEmpName.getSelectedItem().toString();
            System.out.println(ename);
            
            String deptName=cmbmfDepName.getSelectedItem().toString();
            System.out.println(deptName);
            
            int eid=(mfDAO.eidOnvalChange(ename, deptName));
            String stringEID=String.valueOf(eid);
            txtmfEmpID.setText(stringEID);
            String desig =String.valueOf(mfDAO.desigOnValChange(eid));
            txtmfEmpPosition.setText(desig);
            
        }catch(Exception e)
        {
            System.out.println(e);
        }
        
  */      
    }//GEN-LAST:event_cmbmfEmpNamePopupMenuWillBecomeVisible

    private void btnMfClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMfClearActionPerformed
        
                cmbmfDepName.setSelectedItem("Select Department"); 
                cmbmfEmpName.setSelectedItem("Select Employee"); 
                txtmfEmpID.setText("");
                txtmfEmpID.setBackground(Color.WHITE);
                txtmfEmpPosition.setText("");
                txtmfEmpPosition.setBackground(Color.WHITE);
                txtmfBasicSal.setText("");
                txtmfBasicSal.setBackground(Color.WHITE);
                txtmfSingleOtRate.setText("");
                txtmfSingleOtRate.setBackground(Color.WHITE);
                txtmfDoubleOtRate.setText("");
                txtmfDoubleOtRate.setBackground(Color.WHITE);
                txtmfEpfRate.setText("");
                txtmfEpfRate.setBackground(Color.WHITE);
                txtmfPeiceRate.setText("");
                txtmfPeiceRate.setBackground(Color.WHITE);
                btnAddMfEntry.setEnabled(true);
                txtSearch.setText("");

                loadMfTable();
                btnAddMfEntry.setEnabled(true);
                
    }//GEN-LAST:event_btnMfClearActionPerformed

    private void txtSinOtHrsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSinOtHrsMouseClicked
        txtSinOtHrs.setBackground(Color.WHITE);
        
    }//GEN-LAST:event_txtSinOtHrsMouseClicked

    private void txtDoubleOtHrsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDoubleOtHrsMouseClicked
         txtDoubleOtHrs.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtDoubleOtHrsMouseClicked

    private void txtAtndnceBonusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAtndnceBonusMouseClicked
        txtAtndnceBonus.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtAtndnceBonusMouseClicked

    private void txtSpecialBonusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSpecialBonusMouseClicked
        txtSpecialBonus.setBackground(Color.WHITE);
        
    }//GEN-LAST:event_txtSpecialBonusMouseClicked

    private void txtNetSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNetSalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNetSalActionPerformed

    private void btnPrdOffSalClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrdOffSalClearActionPerformed
       
               lblsalIdDisplay.setText("");
               cmbEmpName.setSelectedIndex(0);
               txtEmpId.setText("");
               txtEmpId.setBackground(Color.WHITE);
               txtDesignation.setText("");
               txtDesignation.setBackground(Color.WHITE);
               txtBasicSalary.setText("");
               txtBasicSalary.setBackground(Color.WHITE);
               txtSinOtHrs.setText("");
               txtSinOtHrs.setBackground(Color.WHITE);
               txtDoubleOtHrs.setText("");
               txtDoubleOtHrs.setBackground(Color.WHITE);
               txtAtndnceBonus.setText("");
               txtAtndnceBonus.setBackground(Color.WHITE);
               txtAdvanceAmt.setText("");
               txtAdvanceAmt.setBackground(Color.WHITE);
               txtLoanAmt.setText("");
               txtLoanAmt.setBackground(Color.WHITE);
               txtSpecialBonus.setText("");
               txtSpecialBonus.setBackground(Color.WHITE);
               txtNetSal.setText("");
               txtNetSal.setBackground(Color.WHITE);
        
               btnInsetSal.setEnabled(true);
               loadOff_Prd_sal_Table();
               txtPrdOffSearch.setText("");
    }//GEN-LAST:event_btnPrdOffSalClearActionPerformed

    private void txtDistSalDeptNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDistSalDeptNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDistSalDeptNameActionPerformed

    private void txtDistSalDeptNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDistSalDeptNameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDistSalDeptNameKeyPressed

    private void txtDistSalDeptNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDistSalDeptNameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDistSalDeptNameKeyReleased

    private void txtDistSalSpecialBonusKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDistSalSpecialBonusKeyReleased
       
          txtDistSalNetSal.setText("");
        
        String decimalPattern = "^[\\d.]+$";  
         String number=txtDistSalSpecialBonus.getText();  
         boolean match = Pattern.matches(decimalPattern, number);
          if(match==false)
          { 
              
              
             txtDistSalSpecialBonus.setBackground(Color.PINK);
             distSalSpecialBonus=true;
          }
          
          else
          {   
              
              txtDistSalSpecialBonus.setBackground(Color.WHITE);
              distSalSpecialBonus=false;
          }
    }//GEN-LAST:event_txtDistSalSpecialBonusKeyReleased

    private void txtDistSalSpecialBonusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDistSalSpecialBonusMouseClicked
       
        txtDistSalSpecialBonus.setBackground(Color.WHITE);
        
    }//GEN-LAST:event_txtDistSalSpecialBonusMouseClicked

    private void txtEmpIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmpIdActionPerformed

    private void txtDistSalNetSalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDistSalNetSalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDistSalNetSalMouseClicked

    private void btnDistDeleteSal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDistDeleteSal1ActionPerformed
        
                            txtEmpId.setText("");
                            cmbDistSalEmpName.setSelectedIndex(0);
                            txtDistSalDesignation.setText("");
                            txtDistSalDeptName.setText("");
                            txtDistSalBasicSal.setText("");
                            txtDistSalAdvAmount.setText("");
                            txtDistSalSpecialBonus.setText("");
                            txtDistSalNetSal.setText("");
                            txtDistSearch.setText("");
                            
                            loadDistStfSalTable();
        
                            txtDistSalSpecialBonus.setBackground(Color.WHITE);
                            txtDistSalNetSal.setBackground(Color.WHITE);
                            btnDistInsertSal.setEnabled(true);
                            
    }//GEN-LAST:event_btnDistDeleteSal1ActionPerformed

    private void tblDistributionSalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDistributionSalMouseClicked
        
           int row=tblDistributionSal.getSelectedRow();
        
         btnDistInsertSal.setEnabled(false);
        
        
        String salId=tblDistributionSal.getValueAt(row, 0).toString();
        String empId=tblDistributionSal.getValueAt(row, 1).toString();
        String empName=tblDistributionSal.getValueAt(row, 2).toString();
        String deptName=tblDistributionSal.getValueAt(row, 3).toString();
        String desig=tblDistributionSal.getValueAt(row, 4).toString();
        String basicSal=tblDistributionSal.getValueAt(row, 5).toString();
        String specialBonus=tblDistributionSal.getValueAt(row, 6).toString();
        String advAmt=tblDistributionSal.getValueAt(row, 7).toString();
        String netSal=tblDistributionSal.getValueAt(row, 8).toString();
        
        
        lblDistSalIdDisplay.setText(salId);
        txtDistSalEmpID.setText(empId);   
        cmbDistSalEmpName.setSelectedItem(empName); 
        txtDistSalDeptName.setText(deptName); 
        txtDistSalDesignation.setText(desig); 
        txtDistSalBasicSal.setText(basicSal);
        txtDistSalSpecialBonus.setText(specialBonus);
        txtDistSalNetSal.setText(netSal);
        txtDistSalAdvAmount.setText(advAmt);
        
        
        txtDistSalSpecialBonus.setBackground(Color.WHITE);
        
    }//GEN-LAST:event_tblDistributionSalMouseClicked

    private void txtDoubleOtHrsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDoubleOtHrsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDoubleOtHrsActionPerformed

    private void cmbFoldingSalEmpNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbFoldingSalEmpNameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbFoldingSalEmpNameMouseClicked

    private void cmbFoldingSalEmpNamePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbFoldingSalEmpNamePopupMenuWillBecomeInvisible
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbFoldingSalEmpNamePopupMenuWillBecomeInvisible

    private void cmbFoldingSalEmpNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFoldingSalEmpNameActionPerformed
        
          String ename=cmbFoldingSalEmpName.getSelectedItem().toString();
          String month=lblFoldingSaltMonthDisplay.getText();
        
          if(ename.equals("Select Employee"))
          {
                  txtFoldingSalEmpId.setText("");
          }
          else{
         foldingStaffSalDAO fDAO=new foldingStaffSalDAO();
         int eid=fDAO.eIdOnValChange(ename);
         String dname=fDAO.dnameOnValChange(eid);
         String desig=fDAO.desigOnValChange(eid);
         double basicSal=fDAO.basicSalOnValChange(eid);
         double pieceRate=fDAO.pieceRateOnvalChange(eid);
         double advAmt=fDAO.setSalAdvAmount(eid, month);
         int noOfPieces=fDAO.getNoOfPieces(eid, month);
         
         txtFoldingSalEmpId.setText(String.valueOf(eid));
         txtFoldingSalDeptName.setText(dname);
         txtFoldingSalDesignation.setText(desig);
         txtFoldingSalBasicSal.setText(String.valueOf(basicSal));
         txtFoldingSalPieceRate.setText(String.valueOf(pieceRate));
         txtFoldingSalAdvAmt.setText(String.valueOf(advAmt));
         txtFoldingSalNoOfPieces.setText(String.valueOf(noOfPieces));
         
          }
    }//GEN-LAST:event_cmbFoldingSalEmpNameActionPerformed

    private void cmbFoldingSalEmpNameInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_cmbFoldingSalEmpNameInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbFoldingSalEmpNameInputMethodTextChanged

    private void txtFoldingSalDesignationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFoldingSalDesignationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalDesignationActionPerformed

    private void txtFoldingSalDesignationKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoldingSalDesignationKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalDesignationKeyPressed

    private void txtFoldingSalDesignationKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoldingSalDesignationKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalDesignationKeyReleased

    private void txtFoldingSalDeptNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFoldingSalDeptNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalDeptNameActionPerformed

    private void txtFoldingSalDeptNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoldingSalDeptNameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalDeptNameKeyPressed

    private void txtFoldingSalDeptNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoldingSalDeptNameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalDeptNameKeyReleased

    private void txtFoldingSalNetSalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFoldingSalNetSalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalNetSalMouseClicked

    private void txtFoldingSalNetSalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoldingSalNetSalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalNetSalKeyReleased

    private void txtFoldingSalNetSalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoldingSalNetSalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalNetSalKeyTyped

    private void btnFoldingSalCalcSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFoldingSalCalcSalActionPerformed
         
          txtFoldingSalNetSal.setBackground(Color.WHITE);
        
        
         if(cmbFoldingSalEmpName.getSelectedItem().equals("Select Employee") ||txtFoldingSalAttendanceBonus.getText().isEmpty() || txtFoldingSalSpecialBonus.getText().isEmpty() )
        {
                 JOptionPane.showMessageDialog(rootPane, "One or more fields are empty! ");
                 if(txtFoldingSalSpecialBonus.getText().isEmpty()){
                    txtFoldingSalSpecialBonus.setBackground(Color.PINK); 
                 }
                 
                 if(txtFoldingSalNetSal.getText().isEmpty()){
                    txtFoldingSalNetSal.setBackground(Color.PINK); 
                 }
                 
                 if(txtFoldingSalAttendanceBonus.getText().isEmpty()){
                    txtFoldingSalAttendanceBonus.setBackground(Color.PINK); 
                 }
        } 
        
        else{
            if(foldingSalAttBonus|| foldingSpecialBonus )
            {
                JOptionPane.showMessageDialog(this, "One or more fields are invalid");
            }
            else{
                            
         
                    double netSal=0; 

                    double basicSal=Double.parseDouble(txtFoldingSalBasicSal.getText());
                    double advAmt=Double.parseDouble(txtFoldingSalAdvAmt.getText());
                    double attBonus=Double.parseDouble(txtFoldingSalAttendanceBonus.getText());
                    double noOfPieces=Integer.parseInt(txtFoldingSalNoOfPieces.getText());
                    double pieceRate=Double.parseDouble(txtFoldingSalPieceRate.getText());
                    double specialBonus=Double.parseDouble(txtFoldingSalSpecialBonus.getText());


                    foldingStaffSalDAO fDAO=new foldingStaffSalDAO();
                    netSal=fDAO.calcSal(basicSal, advAmt, specialBonus, pieceRate, noOfPieces, attBonus);

                    txtFoldingSalNetSal.setText(String.valueOf(netSal));
        
            }
         }         
    }//GEN-LAST:event_btnFoldingSalCalcSalActionPerformed

    private void txtFoldingSalAttendanceBonusKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoldingSalAttendanceBonusKeyReleased
        
         txtFoldingSalNetSal.setText("");
        
         String decimalPattern = "^[\\d.]+$";  
         String number=txtFoldingSalAttendanceBonus.getText();  
         boolean match = Pattern.matches(decimalPattern, number);
          if(match==false)
          {   
             txtFoldingSalAttendanceBonus.setBackground(Color.PINK);
             foldingSalAttBonus=true;
          }
          
          else
          {   
              
              txtFoldingSalAttendanceBonus.setBackground(Color.WHITE);
              foldingSalAttBonus=false;
          }
        
    }//GEN-LAST:event_txtFoldingSalAttendanceBonusKeyReleased

    private void txtFoldingSalAttendanceBonusKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoldingSalAttendanceBonusKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalAttendanceBonusKeyTyped

    private void txtFoldingSalNoOfPiecesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFoldingSalNoOfPiecesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalNoOfPiecesMouseClicked

    private void txtFoldingSalNoOfPiecesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoldingSalNoOfPiecesKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalNoOfPiecesKeyReleased

    private void txtFoldingSalPieceRateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFoldingSalPieceRateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalPieceRateMouseClicked

    private void txtFoldingSalPieceRateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoldingSalPieceRateKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalPieceRateKeyReleased

    private void txtFoldingSalAdvAmtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoldingSalAdvAmtKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalAdvAmtKeyReleased

    private void txtFoldingSalAdvAmtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoldingSalAdvAmtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalAdvAmtKeyTyped

    private void tblFoldingStfSalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFoldingStfSalMouseClicked
        
            int row=tblFoldingStfSal.getSelectedRow();
        
         btnFoldngSalInsert.setEnabled(false);
        
        
        String salId=tblFoldingStfSal.getValueAt(row, 0).toString();
        String empId=tblFoldingStfSal.getValueAt(row, 1).toString();
        String empName=tblFoldingStfSal.getValueAt(row, 2).toString();
        String deptName=tblFoldingStfSal.getValueAt(row, 3).toString();
        String desig=tblFoldingStfSal.getValueAt(row, 4).toString();
        String basicSal=tblFoldingStfSal.getValueAt(row, 5).toString();
        String attBonus=tblFoldingStfSal.getValueAt(row, 6).toString();
        String NoOfPices=tblFoldingStfSal.getValueAt(row, 7).toString();
        String PiceRate=tblFoldingStfSal.getValueAt(row, 8).toString();
        String advAmt=tblFoldingStfSal.getValueAt(row, 9).toString();
        String specialBonus=tblFoldingStfSal.getValueAt(row, 10).toString();
        String netSal=tblFoldingStfSal.getValueAt(row, 11).toString();
        
        
        lblfoldingStaffSalID.setText(salId);
        txtFoldingSalEmpId.setText(empId);   
        cmbFoldingSalEmpName.setSelectedItem(empName); 
        txtFoldingSalDeptName.setText(deptName); 
        txtFoldingSalDesignation.setText(desig); 
        txtFoldingSalBasicSal.setText(basicSal);
        txtFoldingSalSpecialBonus.setText(specialBonus);
        txtFoldingSalNetSal.setText(netSal);
        txtFoldingSalAdvAmt.setText(advAmt);
        txtFoldingSalNoOfPieces.setText(NoOfPices);
        txtFoldingSalPieceRate.setText(PiceRate);
        txtFoldingSalAttendanceBonus.setText(attBonus);
        
        txtFoldingSalAttendanceBonus.setBackground(Color.WHITE);
        txtFoldingSalSpecialBonus.setBackground(Color.WHITE);
       
        
        
        
    }//GEN-LAST:event_tblFoldingStfSalMouseClicked

    private void txtFoldingSalSpecialBonusKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoldingSalSpecialBonusKeyReleased
        
        txtFoldingSalNetSal.setText("");
        
         String decimalPattern = "^[\\d.]+$";  
         String number=txtFoldingSalSpecialBonus.getText();  
         boolean match = Pattern.matches(decimalPattern, number);
          if(match==false)
          { 
              
              
             txtFoldingSalSpecialBonus.setBackground(Color.PINK);
             foldingSpecialBonus=true;
          }
          
          else
          {   
              
              txtFoldingSalSpecialBonus.setBackground(Color.WHITE);
              foldingSpecialBonus=false;
          }
        
        
    }//GEN-LAST:event_txtFoldingSalSpecialBonusKeyReleased

    private void txtFoldingSalSpecialBonusKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoldingSalSpecialBonusKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalSpecialBonusKeyTyped

    private void btnFoldngSalInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFoldngSalInsertActionPerformed
        
          if(cmbFoldingSalEmpName.getSelectedItem().equals("Select Employee") ||txtFoldingSalAttendanceBonus.getText().isEmpty() || txtFoldingSalSpecialBonus.getText().isEmpty()||txtFoldingSalNetSal.getText().isEmpty() )
        {
                 JOptionPane.showMessageDialog(rootPane, "One or more fields are empty! ");
                 if(txtFoldingSalSpecialBonus.getText().isEmpty()){
                    txtFoldingSalSpecialBonus.setBackground(Color.PINK); 
                 }
                 
                 if(txtFoldingSalNetSal.getText().isEmpty()){
                    txtFoldingSalNetSal.setBackground(Color.PINK); 
                 }
                 
                 if(txtFoldingSalAttendanceBonus.getText().isEmpty()){
                    txtFoldingSalAttendanceBonus.setBackground(Color.PINK); 
                 }
        }   
        else{
            if(foldingSalAttBonus|| foldingSalNetal||foldingSpecialBonus ){
                JOptionPane.showMessageDialog(this, "One or more fields are invalid");
            }
            else{
                         String month=lblFoldingSaltMonthDisplay.getText();
                         int empId=Integer.parseInt(txtFoldingSalEmpId.getText());
                         foldingStaffSalDAO fDAO1 = new foldingStaffSalDAO();
                         boolean results=fDAO1.checkDuplicateValues(month,empId);
                    if(results==true){
                       JOptionPane.showMessageDialog(this, "Data already exists!!");
                
                }
                
                else{
                            
                            String empName=cmbFoldingSalEmpName.getSelectedItem().toString();
                            String deptName=txtFoldingSalDeptName.getText();
                            String desig=txtFoldingSalDesignation.getText();
                            double basicSal=Double.parseDouble(txtFoldingSalBasicSal.getText());
                            double advAmt=Double.parseDouble(txtFoldingSalAdvAmt.getText());
                            double attBonus=Double.parseDouble(txtFoldingSalAttendanceBonus.getText());
                            double specialBonus=Double.parseDouble(txtFoldingSalSpecialBonus.getText());
                            double netSal=Double.parseDouble(txtFoldingSalNetSal.getText());
                            int noOfPieces=Integer.parseInt(txtFoldingSalNoOfPieces.getText());
                            double pieceRate=Double.parseDouble(txtFoldingSalPieceRate.getText());

                            try {
                                    foldingStaffSal f1=new foldingStaffSal(empId, empName, deptName, desig, basicSal, noOfPieces, pieceRate, advAmt, specialBonus, netSal, month, attBonus);
                                    fDAO1.foldingStaffSalAddEntry(f1);
                                    loadFoldingSalTbl();
                                    JOptionPane.showMessageDialog(rootPane, "Record inserted Sucessfully");


                                } catch (SQLException ex) {
                                    Logger.getLogger(PayrollGUI.class.getName()).log(Level.SEVERE, null, ex);
                                }
                }
                    
                    

                            txtFoldingSalEmpId.setText("");
                            cmbFoldingSalEmpName.setSelectedIndex(0);
                            txtFoldingSalAdvAmt.setText("");
                            txtFoldingSalAttendanceBonus.setText("");
                            txtFoldingSalBasicSal.setText("");
                            txtFoldingSalDeptName.setText("");
                            txtFoldingSalDesignation.setText("");
                            txtFoldingSalNoOfPieces.setText("");
                            txtFoldingSalPieceRate.setText("");
                            txtFoldingSalSpecialBonus.setText("");
                            txtFoldingSalNetSal.setText("");
                            
            }
        }
       
        
    }//GEN-LAST:event_btnFoldngSalInsertActionPerformed

    private void btnFoldingSalUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFoldingSalUpdateActionPerformed
        
            int cnfrm=JOptionPane.showConfirmDialog(rootPane, "Proceed update?");
            
            if(cnfrm==0){
             if(cmbFoldingSalEmpName.getSelectedItem().equals("Select Employee") ||txtFoldingSalAttendanceBonus.getText().isEmpty() || txtFoldingSalSpecialBonus.getText().isEmpty()||txtFoldingSalNetSal.getText().isEmpty() )
              {
                 JOptionPane.showMessageDialog(rootPane, "One or more fields are empty! ");
                 if(txtFoldingSalSpecialBonus.getText().isEmpty()){
                    txtFoldingSalSpecialBonus.setBackground(Color.PINK); 
                 }
                 
                 if(txtFoldingSalNetSal.getText().isEmpty()){
                    txtFoldingSalNetSal.setBackground(Color.PINK); 
                 }
                 
                 if(txtFoldingSalAttendanceBonus.getText().isEmpty()){
                    txtFoldingSalAttendanceBonus.setBackground(Color.PINK); 
                 }
              }   
                else{
                            if(foldingSalAttBonus|| foldingSalNetal||foldingSpecialBonus )
                            {
                                JOptionPane.showMessageDialog(this, "One or more fields are invalid");
                            }
                        else{
                                
                                    int salId=Integer.parseInt(lblfoldingStaffSalID.getText());
                                    int empId=Integer.parseInt(txtFoldingSalEmpId.getText());
                                    String empName=cmbFoldingSalEmpName.getSelectedItem().toString();
                                    String deptName=txtFoldingSalDeptName.getText();
                                    String desig=txtFoldingSalDesignation.getText();
                                    double basicSal=Double.parseDouble(txtFoldingSalBasicSal.getText());
                                    double advAmt=Double.parseDouble(txtFoldingSalAdvAmt.getText());
                                    double attBonus=Double.parseDouble(txtFoldingSalAttendanceBonus.getText());
                                    double specialBonus=Double.parseDouble(txtFoldingSalSpecialBonus.getText());
                                    double netSal=Double.parseDouble(txtFoldingSalNetSal.getText());
                                    int noOfPieces=Integer.parseInt(txtFoldingSalNoOfPieces.getText());
                                    double pieceRate=Double.parseDouble(txtFoldingSalPieceRate.getText());
                                    String month=lblFoldingSaltMonthDisplay.getText();
                                    
                                try {    
                                    foldingStaffSal f1=new foldingStaffSal(empId, empName, deptName, desig, basicSal, noOfPieces, pieceRate, advAmt, specialBonus, netSal, month, attBonus);
                                    f1.setsalId(salId);
                                    foldingStaffSalDAO fDAO=new foldingStaffSalDAO();
                                    fDAO.foldingStaffSalUpdateEntry(f1);
                                    loadFoldingSalTbl();
                                    
                                    JOptionPane.showMessageDialog(rootPane, "Record Updated Sucessfully");
                                    } catch (SQLException ex) 
                                    {
                                    Logger.getLogger(PayrollGUI.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                            
                                
                                 
                            txtFoldingSalEmpId.setText("");
                            cmbFoldingSalEmpName.setSelectedIndex(0);
                            txtFoldingSalAdvAmt.setText("");
                            txtFoldingSalAttendanceBonus.setText("");
                            txtFoldingSalBasicSal.setText("");
                            txtFoldingSalDeptName.setText("");
                            txtFoldingSalDesignation.setText("");
                            txtFoldingSalNoOfPieces.setText("");
                            txtFoldingSalPieceRate.setText("");
                            txtFoldingSalSpecialBonus.setText("");
                            txtFoldingSalNetSal.setText("");
             
                             btnFoldngSalInsert.setEnabled(true);
                    
                          }

                      }    
             }    
            else{   
                            txtFoldingSalEmpId.setText("");
                            cmbFoldingSalEmpName.setSelectedIndex(0);
                            txtFoldingSalAdvAmt.setText("");
                            txtFoldingSalAttendanceBonus.setText("");
                            txtFoldingSalBasicSal.setText("");
                            txtFoldingSalDeptName.setText("");
                            txtFoldingSalDesignation.setText("");
                            txtFoldingSalNoOfPieces.setText("");
                            txtFoldingSalPieceRate.setText("");
                            txtFoldingSalSpecialBonus.setText("");
                            txtFoldingSalNetSal.setText("");
  
                           btnFoldngSalInsert.setEnabled(true);
        
             }
                           
    }//GEN-LAST:event_btnFoldingSalUpdateActionPerformed

    private void btnFoldingSalDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFoldingSalDeleteActionPerformed
        
           int cnfrm=JOptionPane.showConfirmDialog(rootPane, "Do you really want to delete?");
        
          if(cnfrm==0){
            int salId=Integer.parseInt(lblfoldingStaffSalID.getText());
            foldingStaffSal f3=new foldingStaffSal();
            f3.setsalId(salId);
            foldingStaffSalDAO fDAO= new foldingStaffSalDAO();
            fDAO.foldingStfSalDeleteEntry(salId);
            loadFoldingSalTbl();
            JOptionPane.showMessageDialog(rootPane, "Deleted Sucessfully");
            
              
          }
              
                            txtFoldingSalEmpId.setText("");
                            cmbFoldingSalEmpName.setSelectedIndex(0);
                            txtFoldingSalAdvAmt.setText("");
                            txtFoldingSalAttendanceBonus.setText("");
                            txtFoldingSalBasicSal.setText("");
                            txtFoldingSalDeptName.setText("");
                            txtFoldingSalDesignation.setText("");
                            txtFoldingSalNoOfPieces.setText("");
                            txtFoldingSalPieceRate.setText("");
                            txtFoldingSalSpecialBonus.setText("");
                            txtFoldingSalNetSal.setText("");
          
                             btnFoldngSalInsert.setEnabled(true);
          
        
        
        
    }//GEN-LAST:event_btnFoldingSalDeleteActionPerformed

    private void btnFoldingSalClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFoldingSalClearActionPerformed
        
                            txtFoldingSalEmpId.setText("");
                            cmbFoldingSalEmpName.setSelectedIndex(0);
                            txtFoldingSalAdvAmt.setText("");
                            txtFoldingSalAttendanceBonus.setText("");
                            txtFoldingSalBasicSal.setText("");
                            txtFoldingSalDeptName.setText("");
                            txtFoldingSalDesignation.setText("");
                            txtFoldingSalNoOfPieces.setText("");
                            txtFoldingSalPieceRate.setText("");
                            txtFoldingSalSpecialBonus.setText("");
                            txtFoldingSalNetSal.setText("");
                            txtFoldingSalSearch.setText("");
                            
                            txtFoldingSalSpecialBonus.setBackground(Color.WHITE);
                            txtFoldingSalAttendanceBonus.setBackground(Color.WHITE);
                            loadFoldingSalTbl();
                                  
                            btnFoldngSalInsert.setEnabled(true);
 
                            
    }//GEN-LAST:event_btnFoldingSalClearActionPerformed

    private void txtFoldingSalSearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFoldingSalSearchMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalSearchMouseEntered

    private void txtFoldingSalSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFoldingSalSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalSearchActionPerformed

    private void btnFoldingSalSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFoldingSalSearchActionPerformed
        
        String key=txtFoldingSalSearch.getText();
        foldingStaffSalDAO fDAO=new foldingStaffSalDAO();
        rs=fDAO.search(key);
        tblFoldingStfSal.setModel(DbUtils.resultSetToTableModel(rs));
        
        
    }//GEN-LAST:event_btnFoldingSalSearchActionPerformed

    private void txtDistSalNetSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDistSalNetSalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDistSalNetSalActionPerformed

    private void txtFoldingSalAttendanceBonusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFoldingSalAttendanceBonusMouseClicked
       
         txtFoldingSalAttendanceBonus.setBackground(Color.WHITE);
        
    }//GEN-LAST:event_txtFoldingSalAttendanceBonusMouseClicked

    private void txtFoldingSalAdvAmtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFoldingSalAdvAmtMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSalAdvAmtMouseClicked

    private void txtFoldingSalSpecialBonusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFoldingSalSpecialBonusMouseClicked
        
        txtFoldingSalSpecialBonus.setBackground(Color.WHITE);
        
    }//GEN-LAST:event_txtFoldingSalSpecialBonusMouseClicked

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        
        String key=txtSearch.getText();
        
        masterFileDAO mfDAo=new masterFileDAO();
        rs=mfDAo.search(key);
        
        mfTable.setModel(DbUtils.resultSetToTableModel(rs));
          
    }//GEN-LAST:event_txtSearchKeyTyped

    private void txtPrdOffSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrdOffSearchKeyTyped
        
        String key=txtPrdOffSearch.getText();
        
        off_prd_salCalDAO ofDAO=new off_prd_salCalDAO();
        rs=ofDAO.search(key);
        tbl_off_prd_sal.setModel(DbUtils.resultSetToTableModel(rs));
        
    }//GEN-LAST:event_txtPrdOffSearchKeyTyped

    private void txtDistSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDistSearchKeyTyped
        
        String key=txtDistSearch.getText();
        
        distributionStaffSalDAO disDAO =new distributionStaffSalDAO();
         rs=disDAO.search(key);
         tblDistributionSal.setModel(DbUtils.resultSetToTableModel(rs));
        
    }//GEN-LAST:event_txtDistSearchKeyTyped

    private void txtFoldingSalSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoldingSalSearchKeyTyped
        
        String key=txtFoldingSalSearch.getText();
        foldingStaffSalDAO fDAO=new foldingStaffSalDAO();
        rs=fDAO.search(key);
        tblFoldingStfSal.setModel(DbUtils.resultSetToTableModel(rs));
        
    }//GEN-LAST:event_txtFoldingSalSearchKeyTyped

    private void txtAdvSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdvSearchKeyTyped
       
         String searchKey=txtAdvSearch.getText();
         advanceDetailsDAO adDAO1=new advanceDetailsDAO();
         rs=adDAO1.search(searchKey);
         tblAdvance.setModel( DbUtils.resultSetToTableModel(rs));
        
        
    }//GEN-LAST:event_txtAdvSearchKeyTyped

    private void btnAdvClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdvClearActionPerformed
        
            lblAdvIdDisplay.setText("");
            txtAdvEmpId.setText("");
            txtAdvEmpName.setText("");
            txtAdvDeptName.setText("");
            txtAdvAdvAmount.setText("");
            cmbAdvStatus.setSelectedIndex(0);
            txtAdvAdvAmount.setBackground(Color.WHITE);
        
            btnAdvAddEntry.setEnabled(true);
            loadAdvanceTable();
            
            txtAdvSearch.setText("");
    }//GEN-LAST:event_btnAdvClearActionPerformed

    private void txtLoanSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoanSearchKeyTyped
        
    }//GEN-LAST:event_txtLoanSearchKeyTyped

    private void txtLoanSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoanSearchKeyReleased
      
               
        int empId=Integer.parseInt(txtLoanSearch.getText());
         loanHandlingDAO lnDAO1=new loanHandlingDAO();
         rs=lnDAO1.search(empId);
         tblLoan.setModel( DbUtils.resultSetToTableModel(rs));
   

        
    }//GEN-LAST:event_txtLoanSearchKeyReleased

    private void btnLoanClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoanClearActionPerformed
        
          lblLoanIdDisplay.setText("");
           txtLoanEmpId.setText("");
           txtLoanEmpId.setText("");
           txtLoanEmpName.setText("");
           txtLoanDeptName.setText("");
           txtLoanLoanAmt.setText("");
          // txtLentDate.setText("");
           txtMonthlyDue.setText("");
           txtLoanDue.setText("");
           cmbLoanStatus.setSelectedIndex(0);
           txtLoanSearch.setText("");
           
           txtLoanLoanAmt.setBackground(Color.WHITE);
           
           loadLoanTable();
           btnInsertLoan.setEnabled(true);
    }//GEN-LAST:event_btnLoanClearActionPerformed

    private void txtLoanLoanAmtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLoanLoanAmtMouseClicked
        
        txtLoanLoanAmt.setBackground(Color.WHITE);
        
    }//GEN-LAST:event_txtLoanLoanAmtMouseClicked

    private void txtLoanLoanAmtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLoanLoanAmtMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoanLoanAmtMouseEntered

    private void btnPrdOffSalGenPaySheetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrdOffSalGenPaySheetActionPerformed
       
        HashMap para = new HashMap();
        if(txtPrdOffPaySheetMon.getText().length() !=0)
        {
          para.put("month", txtPrdOffPaySheetMon.getText());
          reportViewer re=new reportViewer("C:\\Users\\Sumudu Malshan\\Desktop\\vidulakaReport\\Payroll\\PrdOffPaySheet.jasper", para);
          re.setVisible(true);
     
            re.setAlwaysOnTop(true);
        }
        
    }//GEN-LAST:event_btnPrdOffSalGenPaySheetActionPerformed

    private void txtPrdOffPaySheetMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrdOffPaySheetMonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrdOffPaySheetMonActionPerformed

    private void btnDisPaySheetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisPaySheetActionPerformed
        
        
        HashMap para = new HashMap();
        if(txtDisSheetMon.getText().length() !=0)
        {
          para.put("month", txtDisSheetMon.getText());
          reportViewer re=new reportViewer("C:\\Users\\Sumudu Malshan\\Desktop\\vidulakaReport\\Payroll\\report1.jasper", para);
          re.setVisible(true);
          re.setAlwaysOnTop(true);
        
        }
        

        
        
    }//GEN-LAST:event_btnDisPaySheetActionPerformed

    private void txtDisSheetMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDisSheetMonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDisSheetMonActionPerformed

    private void btnFoldingPaySheetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFoldingPaySheetActionPerformed
       
         HashMap para = new HashMap();
        if(txtFoldingSheetMon.getText().length() !=0)
        {
          para.put("month", txtFoldingSheetMon.getText());
          reportViewer re=new reportViewer("C:\\Users\\Sumudu Malshan\\Desktop\\vidulakaReport\\Payroll\\FoldingPaySheet.jasper", para);
          re.setVisible(true);
          re.setAlwaysOnTop(true);
        }
       
        
    }//GEN-LAST:event_btnFoldingPaySheetActionPerformed

    private void txtFoldingSheetMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFoldingSheetMonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoldingSheetMonActionPerformed

    private void btnLoanSheetMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoanSheetMonActionPerformed
        String finaltxt="";
        StringTokenizer token =new StringTokenizer(txtLoanSheetMon.getText(),",");
        String month=token.nextToken();
        int monthval=0;
        int year=Integer.parseInt( token.nextToken());
        if(month.equalsIgnoreCase("JANUARY"))
        {
            monthval=1;
        
        }
        else if(month.equalsIgnoreCase("February"))
        {
            monthval=2;
        
        }
        else if(month.equalsIgnoreCase("March"))
        {
            monthval=3;
        
        }
        else if(month.equalsIgnoreCase("April"))
        {
            monthval=4;
        
        }
        else if(month.equalsIgnoreCase("may"))
        {
            monthval=5;
        
        }
        else if(month.equalsIgnoreCase("June"))
        {
            monthval=6;
        
        }
        else if(month.equalsIgnoreCase("July"))
        {
            monthval=7;
        
        }
        else if(month.equalsIgnoreCase("august"))
        {
            monthval=8;
        
        }
        else if(month.equalsIgnoreCase("september"))
        {
            monthval=9;
        
        }
        else if(month.equalsIgnoreCase("Octomber"))
        {
            monthval=10;
        
        }
        else if(month.equalsIgnoreCase("november"))
        {
            monthval= 11;
        
        }
        else if(month.equalsIgnoreCase("December"))
        {
            monthval= 12;
        
        }
        
        
        
        finaltxt=year+"-0"+monthval;
        System.out.println(finaltxt);
        
         HashMap para = new HashMap();
        
            
           
          para.put("month", finaltxt);
          reportViewer re=new reportViewer("C:\\Users\\Sumudu Malshan\\Desktop\\vidulakaReport\\Payroll\\loanReport.jasper", para);
          re.setVisible(true);
          re.setAlwaysOnTop(true);
       
        
    }//GEN-LAST:event_btnLoanSheetMonActionPerformed

    private void txtLoanSheetMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoanSheetMonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoanSheetMonActionPerformed

    private void btnAdvanceReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdvanceReportActionPerformed

         HashMap para = new HashMap();
        if(txtAdvanceReport.getText().length() !=0)
        {
          para.put("month", txtAdvanceReport.getText());
          reportViewer re=new reportViewer("C:\\Users\\Sumudu Malshan\\Desktop\\vidulakaReport\\Payroll\\advanceReport.jasper", para);
          re.setVisible(true);
          re.setAlwaysOnTop(true);
        }
        
    }//GEN-LAST:event_btnAdvanceReportActionPerformed

    private void txtAdvanceReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdvanceReportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAdvanceReportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backPanel;
    private javax.swing.JButton btnAddMfEntry;
    private javax.swing.JButton btnAdvAddEntry;
    private javax.swing.JButton btnAdvClear;
    private javax.swing.JButton btnAdvDeleteEntry;
    private javax.swing.JButton btnAdvUpdateEntry;
    private javax.swing.JButton btnAdvanceReport;
    private javax.swing.JButton btnAdverach;
    private javax.swing.JButton btnCalcSal;
    private javax.swing.JButton btnDeleteLoan;
    private javax.swing.JButton btnDeleteMfEntry;
    private javax.swing.JButton btnDeleteSal;
    private javax.swing.JButton btnDisPaySheet;
    private javax.swing.JButton btnDistCalcSal;
    private javax.swing.JButton btnDistDeleteSal;
    private javax.swing.JButton btnDistDeleteSal1;
    private javax.swing.JButton btnDistInsertSal;
    private javax.swing.JButton btnDistSearch;
    private javax.swing.JButton btnDistUpdateSal;
    private javax.swing.JButton btnDistributionStf;
    private javax.swing.JButton btnFoldingPaySheet;
    private javax.swing.JButton btnFoldingSalCalcSal;
    private javax.swing.JButton btnFoldingSalClear;
    private javax.swing.JButton btnFoldingSalDelete;
    private javax.swing.JButton btnFoldingSalSearch;
    private javax.swing.JButton btnFoldingSalUpdate;
    private javax.swing.JButton btnFoldingStaff;
    private javax.swing.JButton btnFoldngSalInsert;
    private javax.swing.JButton btnInsertLoan;
    private javax.swing.JButton btnInsetSal;
    private javax.swing.JButton btnLoanClear;
    private javax.swing.JButton btnLoanSheetMon;
    private javax.swing.JButton btnMasterFile;
    private javax.swing.JButton btnMfClear;
    private javax.swing.JButton btnMfSearch;
    private javax.swing.JButton btnPrdOffSalClear;
    private javax.swing.JButton btnPrdOffSalGenPaySheet;
    private javax.swing.JButton btnPrdOffSearch;
    private javax.swing.JButton btnPrd_off_stf;
    private javax.swing.JButton btnUpdateLoan;
    private javax.swing.JButton btnUpdateMfEntry;
    private javax.swing.JButton btnUpdateSal;
    private javax.swing.JButton btnloanSearch;
    private javax.swing.JComboBox cmbAdvStatus;
    private javax.swing.JComboBox cmbDistSalEmpName;
    private javax.swing.JComboBox cmbEmpName;
    private javax.swing.JComboBox cmbFoldingSalEmpName;
    private javax.swing.JComboBox cmbLoanStatus;
    private javax.swing.JComboBox cmbmfDepName;
    private javax.swing.JComboBox cmbmfEmpName;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private static javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblAdvAdvAmount;
    private javax.swing.JLabel lblAdvDate;
    private javax.swing.JLabel lblAdvDeptName;
    private javax.swing.JLabel lblAdvEmpID;
    private javax.swing.JLabel lblAdvIdDisplay;
    private javax.swing.JLabel lblAdvMonth;
    private javax.swing.JLabel lblAdvMonthDisplay;
    private javax.swing.JLabel lblAdvName;
    private javax.swing.JLabel lblAdvNameDisplay;
    private javax.swing.JLabel lblAdvStatus;
    private javax.swing.JLabel lblAdvanceAmt;
    private javax.swing.JLabel lblAdvanceReport;
    private javax.swing.JLabel lblAtdnceBonus;
    private javax.swing.JLabel lblBasicSal;
    private javax.swing.JLabel lblBasicSalary;
    private javax.swing.JLabel lblBkgroundP3;
    private javax.swing.JLabel lblDepartment;
    private javax.swing.JLabel lblDeptName;
    private javax.swing.JLabel lblDesignation;
    private javax.swing.JLabel lblDisStaffSalName;
    private javax.swing.JLabel lblDistAdvAmount;
    private javax.swing.JLabel lblDistBasicSal;
    private javax.swing.JLabel lblDistDeptName;
    private javax.swing.JLabel lblDistDesignation;
    private javax.swing.JLabel lblDistEmpId;
    private javax.swing.JLabel lblDistEmpName;
    private javax.swing.JLabel lblDistSalIdDisplay;
    private javax.swing.JLabel lblDistSpecialBonus;
    private javax.swing.JLabel lblDoubleOTHrs;
    private javax.swing.JLabel lblDoubleOTRate;
    private javax.swing.JLabel lblEPFRate;
    private javax.swing.JLabel lblEmpID;
    private javax.swing.JLabel lblEmpName;
    private javax.swing.JLabel lblFoldingSalAdvAmt;
    private javax.swing.JLabel lblFoldingSalAttBonus;
    private javax.swing.JLabel lblFoldingSalBasicSal;
    private javax.swing.JLabel lblFoldingSalDeptName;
    private javax.swing.JLabel lblFoldingSalDesignation;
    private javax.swing.JLabel lblFoldingSalEmpId;
    private javax.swing.JLabel lblFoldingSalEmpName;
    private javax.swing.JLabel lblFoldingSalNoOfPieces;
    private javax.swing.JLabel lblFoldingSalPieceRate;
    private javax.swing.JLabel lblFoldingSalSpecialBonus;
    private javax.swing.JLabel lblFoldingSaltMonth;
    private javax.swing.JLabel lblFoldingSaltMonthDisplay;
    private javax.swing.JLabel lblFoldingSheetMon;
    private javax.swing.JLabel lblFoldngSalName;
    private javax.swing.JLabel lblLentDate;
    private javax.swing.JLabel lblLoanAmt;
    private javax.swing.JLabel lblLoanDeptName;
    private javax.swing.JLabel lblLoanDue;
    private javax.swing.JLabel lblLoanEmpId;
    private javax.swing.JLabel lblLoanEmpName;
    private javax.swing.JLabel lblLoanIdDisplay;
    private javax.swing.JLabel lblLoanLoanAmt;
    private javax.swing.JLabel lblLoanName;
    private javax.swing.JLabel lblLoanSheetMon;
    private javax.swing.JLabel lblLoanStatus;
    private javax.swing.JLabel lblMfErrorDoubleOt;
    private javax.swing.JLabel lblMfErrorPieceRate;
    private javax.swing.JLabel lblMfErrorSinOt;
    private javax.swing.JLabel lblMfName;
    private javax.swing.JLabel lblMonth;
    private javax.swing.JLabel lblMonthlyDue;
    private javax.swing.JLabel lblPieceRate;
    private javax.swing.JLabel lblPrdOffPaySheetMon;
    private javax.swing.JLabel lblPrdOffPaySheetMon1;
    private javax.swing.JLabel lblPrdOffSalName;
    private javax.swing.JLabel lblSalMonth;
    private javax.swing.JLabel lblSinOTHrs;
    private javax.swing.JLabel lblSingalOTRate;
    private javax.swing.JLabel lblSpecialBonus;
    private javax.swing.JLabel lbl_mf_Id;
    private javax.swing.JLabel lbldistMonth;
    private javax.swing.JLabel lbldistMonthDisplay;
    private javax.swing.JLabel lblfoldingStaffSalID;
    private javax.swing.JLabel lblmfEmpID;
    private javax.swing.JLabel lblmfEmpPost;
    private javax.swing.JLabel lblmfErrorBasicSal;
    private javax.swing.JLabel lblmfErrorEpf;
    private javax.swing.JLabel lblsalIdDisplay;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JTable mfTable;
    private javax.swing.JPanel pnlAdvanceReport;
    private javax.swing.JPanel pnlDisPaySheet;
    private javax.swing.JPanel pnlFoldingPaySheet;
    private javax.swing.JPanel pnlFoldingPaySheet1;
    private javax.swing.JPanel pnlPayrolAdvancel;
    private javax.swing.JPanel pnlPayrollDistributionStaffSal;
    private javax.swing.JPanel pnlPayrollFoldingSatffl;
    private javax.swing.JPanel pnlPayrollLoan;
    private javax.swing.JPanel pnlPayrollMasterFile;
    private javax.swing.JPanel pnlPayrollOffPrdStfSal;
    private javax.swing.JPanel pnlPayrollWelcome;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JTable tblAdvance;
    private javax.swing.JTable tblDistributionSal;
    private javax.swing.JTable tblFoldingStfSal;
    private javax.swing.JTable tblLoan;
    private javax.swing.JTable tbl_off_prd_sal;
    private javax.swing.JTextField txtAdvAdvAmount;
    private com.toedter.calendar.JDateChooser txtAdvDate;
    private javax.swing.JTextField txtAdvDeptName;
    private javax.swing.JTextField txtAdvEmpId;
    private javax.swing.JTextField txtAdvEmpName;
    private javax.swing.JTextField txtAdvSearch;
    private javax.swing.JTextField txtAdvanceAmt;
    private javax.swing.JTextField txtAdvanceReport;
    private javax.swing.JTextField txtAtndnceBonus;
    private javax.swing.JTextField txtBasicSalary;
    private javax.swing.JTextField txtDeptName;
    private javax.swing.JTextField txtDesignation;
    private javax.swing.JTextField txtDisSheetMon;
    private javax.swing.JTextField txtDistSalAdvAmount;
    private javax.swing.JTextField txtDistSalBasicSal;
    private javax.swing.JTextField txtDistSalDeptName;
    private javax.swing.JTextField txtDistSalDesignation;
    private javax.swing.JTextField txtDistSalEmpID;
    private javax.swing.JTextField txtDistSalNetSal;
    private javax.swing.JTextField txtDistSalSpecialBonus;
    private javax.swing.JTextField txtDistSearch;
    private javax.swing.JTextField txtDoubleOtHrs;
    private javax.swing.JTextField txtEmpId;
    private javax.swing.JTextField txtFoldingSalAdvAmt;
    private javax.swing.JTextField txtFoldingSalAttendanceBonus;
    private javax.swing.JTextField txtFoldingSalBasicSal;
    private javax.swing.JTextField txtFoldingSalDeptName;
    private javax.swing.JTextField txtFoldingSalDesignation;
    private javax.swing.JTextField txtFoldingSalEmpId;
    private javax.swing.JTextField txtFoldingSalNetSal;
    private javax.swing.JTextField txtFoldingSalNoOfPieces;
    private javax.swing.JTextField txtFoldingSalPieceRate;
    private javax.swing.JTextField txtFoldingSalSearch;
    private javax.swing.JTextField txtFoldingSalSpecialBonus;
    private javax.swing.JTextField txtFoldingSheetMon;
    private com.toedter.calendar.JDateChooser txtLentDate;
    private javax.swing.JTextField txtLoanAmt;
    private javax.swing.JTextField txtLoanDeptName;
    private javax.swing.JTextField txtLoanDue;
    private javax.swing.JTextField txtLoanEmpId;
    private javax.swing.JTextField txtLoanEmpName;
    private javax.swing.JTextField txtLoanLoanAmt;
    private javax.swing.JTextField txtLoanSearch;
    private javax.swing.JTextField txtLoanSheetMon;
    private javax.swing.JTextField txtMonthlyDue;
    private javax.swing.JTextField txtNetSal;
    private javax.swing.JTextField txtPrdOffPaySheetMon;
    private javax.swing.JTextField txtPrdOffSearch;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSinOtHrs;
    private javax.swing.JTextField txtSpecialBonus;
    private javax.swing.JTextField txtmfBasicSal;
    private javax.swing.JTextField txtmfDoubleOtRate;
    private javax.swing.JTextField txtmfEmpID;
    private javax.swing.JTextField txtmfEmpPosition;
    private javax.swing.JTextField txtmfEpfRate;
    private javax.swing.JTextField txtmfPeiceRate;
    private javax.swing.JTextField txtmfSingleOtRate;
    // End of variables declaration//GEN-END:variables
}
