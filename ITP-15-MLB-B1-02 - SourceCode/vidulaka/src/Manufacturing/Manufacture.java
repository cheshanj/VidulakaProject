package Manufacturing;

import Home.*;
//import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.List;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;


public class Manufacture extends javax.swing.JInternalFrame {

    Connection con = null;
    PreparedStatement pst= null;
    PreparedStatement pst1= null;
    ResultSet rs=null;
    ResultSet rs1=null;
    
    public int nextCount;
    
    RegOrdersDBAccess ROD = new RegOrdersDBAccess();
    
    boolean papers;
    boolean plates;
    boolean inkB;
    boolean inkC;
    boolean inkM;
    boolean inkY;
    public String filename=null;
    
    public Manufacture() {
        initComponents();
           ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
           
        con=dbconnect.connect();
        
        tableLoad();
        modelPaperTable();
        RawMaterials.setVisible(false);
        HandleSpecialOrders.setVisible(false);
        CheckWastage.setVisible(false);
        WorkRate.setVisible(false);
        tick.setVisible(false) ;

    }
    
    public void tableLoad()
    {
        try{
            String sql= "select orders.orderID AS 'Order ID', product.productType AS 'Product Name', product.productGrade AS 'Grade', orders.quantity AS 'Quantity', orders.orderDate AS 'Ordered Date', orders.dueDate AS 'Due Date',orders.printingStatus AS 'Status'\n" +
                        "from orders, product\n" +
                        "where orders.pid=product.productID AND orders.checkStatus='complete' AND product.productType NOT LIKE '%Paperset%'\n" +
                        "group by orders.orderID";
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery(); 
            
            regOrderTable.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error while loading the Order table!");
        }
    }
    
    public void modelPaperTable()
    {
        try
        {
            String sql4 = "select orders.orderID AS 'Order ID', product.productType AS 'Product Name', product.productGrade AS 'Grade', orders.quantity AS 'Quantity', orders.orderDate AS 'Ordered Date', orders.dueDate AS 'Due Date',orders.printingStatus AS 'Status'\n" +
                          "from orders, product\n" +
                          "where orders.pid=product.productID AND orders.checkStatus='complete' AND productType LIKE '%Paperset%'\n" +
                          "group by orders.orderID";
            pst=con.prepareStatement(sql4);
            rs=pst.executeQuery();
            
            modelPaperOrders.setModel(DbUtils.resultSetToTableModel(rs));
            PO.setText("Paperset Orders :");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, "Error while loading the Modelpaper table!");
        }
    }
    
    public void tableLaodBag()
    {
        try
        {
            String sql4 = "select MOID AS 'Relavant Paper Order ID', coverImage AS 'Image Path', amount AS 'Quantity',printingStatus AS 'Status'\n" +
                          "from bag_order \n";
 
            pst=con.prepareStatement(sql4);
            rs=pst.executeQuery();
            
            modelPaperOrders.setModel(DbUtils.resultSetToTableModel(rs));
            PO.setText("Bag Orders :");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, "Error while loading the Modelpaper table!");
        }
    }
    
    public void UsedRawMaterialTable()
    {
        try
        {
            String sql="select used_raw_material.orderID AS 'Order ID',raw_material.itemName AS 'Item Name', used_raw_material.amount AS 'Amount' from used_raw_material, raw_material where used_raw_material.itemCode=raw_material.itemCode";
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            
            usedRawMaterials.setModel(DbUtils.resultSetToTableModel(rs));
        }
       catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, e);
        }

    }
    
    public void UsedTenderRawMaterialTable()
    {
        try
        {
            String sql="select tender_used_rawmaterial.tenderID AS 'Tender ID',raw_material.itemName AS 'Item Name', tender_used_rawmaterial.amount AS 'Amount' from tender_used_rawmaterial, raw_material where tender_used_rawmaterial.itemCode=raw_material.itemCode";
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            
            usedRawMaterials.setModel(DbUtils.resultSetToTableModel(rs));
        }
       catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, e);
        }

    }
    
    public void UsedSeminarRawMaterialTable()
    {
        try
        {
            String sql="select seminar_used_rawmaterial.seminarID AS 'Seminar ID',raw_material.itemName AS 'Item Name', seminar_used_rawmaterial.amount AS 'Amount' from seminar_used_rawmaterial, raw_material where seminar_used_rawmaterial.itemCode=raw_material.itemCode";
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            
            usedRawMaterials.setModel(DbUtils.resultSetToTableModel(rs));
        }
       catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, e);
        }

    }
    
    public void seminarOdertable()
    {
        try
        {
            String sql="select seminar_resource.semID AS 'Seminar ID',seminar_resource.sem_draft AS 'Document Path',seminar_resource.noOfPapers AS 'Amount',seminar.date AS 'Due Date',seminar_resource.printingStatus AS 'Printing Status' "
                    + "from seminar_resource, seminar where seminar_resource.semID=seminar.seminarID";
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            
            seminarTable.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    public void TenderTable()
    {
        try
        {
            String sql="select tr.tenderID AS 'Tender ID',tr.draftID AS 'Draft ID',tr.amount AS 'Amount',t.dueDate AS 'Due Date',t.printingStatus AS 'Printing Status' from tender_order tr, tender t where tr.tenderID = t.tenderID";
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            
            tenderTable.setModel(DbUtils.resultSetToTableModel(rs));
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    
    public void attendanceTable()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat1 = new SimpleDateFormat("dd MMMM,yyyy");
        Date date = new Date();
        Date date1 = new Date();
        String d=dateFormat.format(date);
        String d1=dateFormat1.format(date1);
        
        System.out.println(d);
        
        try
        {
            String sql="select attendance.empID AS 'Employee ID', employee.name AS 'Name','No Entry' AS 'Entry Status' from attendance, employee where employee.empID=attendance.empID AND employee.deptID=4 AND attendance.date='"+d+"' AND attendance.empID NOT IN(select folding_rate.empID from folding_rate where folding_rate.date='"+d1+"')";
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            
            workRateTable.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    public void wastageTable()
    {
        try
        {
            String sql="select orderID AS 'Order ID',wasteItemID AS 'Item Code',wastedAmount AS 'Wasted Amount',wasteDate AS 'Order Date' from wastage";
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            
            wastageTable.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backPanel = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        ckw = new javax.swing.JButton();
        regOrder = new javax.swing.JButton();
        specialOrder = new javax.swing.JButton();
        wkrate = new javax.swing.JButton();
        rightPanel = new javax.swing.JPanel();
        HandleRegOrders = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        NormalOrders = new javax.swing.JPanel();
        searchBy = new javax.swing.JLabel();
        searchT = new javax.swing.JTextField();
        oid = new javax.swing.JLabel();
        Toid = new javax.swing.JTextField();
        pname = new javax.swing.JLabel();
        Tpname = new javax.swing.JTextField();
        qnty = new javax.swing.JLabel();
        Tqnty = new javax.swing.JTextField();
        TdueDate = new javax.swing.JTextField();
        dueDate = new javax.swing.JLabel();
        sendToPrint = new javax.swing.JButton();
        sendToDistribution = new javax.swing.JButton();
        viewAll = new javax.swing.JButton();
        orderstable = new javax.swing.JScrollPane();
        regOrderTable = new javax.swing.JTable();
        category = new javax.swing.JComboBox();
        Bsearch = new javax.swing.JButton();
        del = new javax.swing.JButton();
        ModelPaper = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        modelPaperOrders = new javax.swing.JTable();
        viewBagOrders = new javax.swing.JButton();
        titleM2 = new javax.swing.JLabel();
        qt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        addOrder = new javax.swing.JButton();
        add = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        MID = new javax.swing.JTextField();
        PO = new javax.swing.JLabel();
        viewModel = new javax.swing.JButton();
        bgStatus = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        bsntPrint = new javax.swing.JButton();
        searchBy1 = new javax.swing.JLabel();
        category1 = new javax.swing.JComboBox();
        searchT1 = new javax.swing.JTextField();
        Bsearch1 = new javax.swing.JButton();
        sndtoDis = new javax.swing.JButton();
        vali1 = new javax.swing.JLabel();
        tick = new javax.swing.JLabel();
        bgReg = new javax.swing.JLabel();
        HandleSpecialOrders = new javax.swing.JPanel();
        titleHS = new javax.swing.JLabel();
        seminarPanel = new javax.swing.JPanel();
        tPanel1 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        sOrderID = new javax.swing.JTextField();
        sDocumentPath = new javax.swing.JTextField();
        sAmount = new javax.swing.JTextField();
        sDueDate = new javax.swing.JTextField();
        searchBy3 = new javax.swing.JLabel();
        seminarCatogory = new javax.swing.JComboBox();
        searchSeminar = new javax.swing.JTextField();
        seminarSearchbtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        seminarTable = new javax.swing.JTable();
        seminarDisbtn = new javax.swing.JButton();
        seminarPrintbtn = new javax.swing.JButton();
        seminarAllbtn = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        tendePanel = new javax.swing.JPanel();
        tPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tOrderID = new javax.swing.JTextField();
        tDocumentPath = new javax.swing.JTextField();
        tAmount = new javax.swing.JTextField();
        tDueDate = new javax.swing.JTextField();
        searchBy2 = new javax.swing.JLabel();
        tenderCatogory = new javax.swing.JComboBox();
        tenderSearch = new javax.swing.JTextField();
        tenderSearchbtn = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tenderTable = new javax.swing.JTable();
        tenderPrintbtn = new javax.swing.JButton();
        tenderDisbtn = new javax.swing.JButton();
        tenderAllbtn = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        bgSpec = new javax.swing.JLabel();
        WorkRate = new javax.swing.JPanel();
        wTitle = new javax.swing.JLabel();
        WmainPanel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        workRateTable = new javax.swing.JTable();
        tableHeading = new javax.swing.JLabel();
        wrPanel = new javax.swing.JPanel();
        wDate = new javax.swing.JLabel();
        wPanel1 = new javax.swing.JPanel();
        firstField = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        wID = new javax.swing.JLabel();
        wName = new javax.swing.JLabel();
        wPiece = new javax.swing.JTextField();
        nxtWk = new javax.swing.JButton();
        updatePiceRate = new javax.swing.JButton();
        viewPice = new javax.swing.JButton();
        viewAttendance = new javax.swing.JButton();
        bgWork = new javax.swing.JLabel();
        CheckWastage = new javax.swing.JPanel();
        cTitle = new javax.swing.JLabel();
        cReportPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        cReportbtn = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        wastageTable = new javax.swing.JTable();
        wastageTableHeading = new javax.swing.JLabel();
        cViewPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        checkItemcmb = new javax.swing.JComboBox();
        orderIDcombo = new javax.swing.JComboBox();
        cCheckbtn = new javax.swing.JButton();
        cMonthlyWabtn = new javax.swing.JButton();
        cUpdateWaTabtn = new javax.swing.JButton();
        bgwaste = new javax.swing.JLabel();
        RawMaterials = new javax.swing.JPanel();
        RawBackPanel = new javax.swing.JPanel();
        id = new javax.swing.JLabel();
        PName = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        Cyan = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        roid = new javax.swing.JTextField();
        rproNo = new javax.swing.JTextField();
        nPa = new javax.swing.JTextField();
        nPl = new javax.swing.JTextField();
        nB = new javax.swing.JTextField();
        nC = new javax.swing.JTextField();
        nM = new javax.swing.JTextField();
        vPa = new javax.swing.JLabel();
        nY = new javax.swing.JTextField();
        vPl = new javax.swing.JLabel();
        vC = new javax.swing.JLabel();
        vM = new javax.swing.JLabel();
        vB = new javax.swing.JLabel();
        vY = new javax.swing.JLabel();
        updateRaw = new javax.swing.JButton();
        backRaw = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        usedRawMaterials = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        rtitle = new javax.swing.JLabel();
        backgroundRaw = new javax.swing.JLabel();
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

        ckw.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/wastage.png"))); // NOI18N
        ckw.setBorderPainted(false);
        ckw.setContentAreaFilled(false);
        ckw.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        ckw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckwActionPerformed(evt);
            }
        });
        leftPanel.add(ckw, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 100, -1));

        regOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manufacturing/images/regular.png"))); // NOI18N
        regOrder.setBorderPainted(false);
        regOrder.setContentAreaFilled(false);
        regOrder.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        regOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regOrderActionPerformed(evt);
            }
        });
        leftPanel.add(regOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, -1));

        specialOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manufacturing/images/special.png"))); // NOI18N
        specialOrder.setBorderPainted(false);
        specialOrder.setContentAreaFilled(false);
        specialOrder.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        specialOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specialOrderActionPerformed(evt);
            }
        });
        leftPanel.add(specialOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 100, -1));

        wkrate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manufacturing/images/workrate.png"))); // NOI18N
        wkrate.setBorderPainted(false);
        wkrate.setContentAreaFilled(false);
        wkrate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wkrateActionPerformed(evt);
            }
        });
        leftPanel.add(wkrate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 100, -1));

        rightPanel.setMaximumSize(new java.awt.Dimension(910, 570));
        rightPanel.setMinimumSize(new java.awt.Dimension(910, 570));
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new java.awt.Dimension(910, 570));
        rightPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        HandleRegOrders.setBackground(new java.awt.Color(255, 255, 255));
        HandleRegOrders.setMaximumSize(new java.awt.Dimension(915, 570));
        HandleRegOrders.setMinimumSize(new java.awt.Dimension(915, 570));
        HandleRegOrders.setOpaque(false);
        HandleRegOrders.setPreferredSize(new java.awt.Dimension(915, 570));
        HandleRegOrders.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        title.setBackground(new java.awt.Color(255, 255, 255));
        title.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setText("Handle Regular Orders");
        HandleRegOrders.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 280, 40));

        NormalOrders.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Handling Regular Order ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        NormalOrders.setPreferredSize(new java.awt.Dimension(430, 420));

        searchBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        searchBy.setText("Search By :");

        searchT.setText("Search");
        searchT.setToolTipText("");
        searchT.setMinimumSize(null);
        searchT.setPreferredSize(new java.awt.Dimension(150, 20));
        searchT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchTFocusLost(evt);
            }
        });
        searchT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchTMouseClicked(evt);
            }
        });
        searchT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTActionPerformed(evt);
            }
        });
        searchT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchTKeyTyped(evt);
            }
        });

        oid.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        oid.setText("Order ID :");

        Toid.setEditable(false);
        Toid.setMinimumSize(null);
        Toid.setPreferredSize(new java.awt.Dimension(150, 23));

        pname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pname.setText("Product Name :");

        Tpname.setEditable(false);
        Tpname.setMinimumSize(null);
        Tpname.setPreferredSize(new java.awt.Dimension(150, 23));

        qnty.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        qnty.setText("Quantity :");

        Tqnty.setEditable(false);
        Tqnty.setMinimumSize(null);
        Tqnty.setPreferredSize(new java.awt.Dimension(150, 23));

        TdueDate.setEditable(false);
        TdueDate.setMinimumSize(null);
        TdueDate.setPreferredSize(new java.awt.Dimension(150, 23));

        dueDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dueDate.setText("Due Date :");

        sendToPrint.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sendToPrint.setText("Send to Print");
        sendToPrint.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        sendToPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendToPrintActionPerformed(evt);
            }
        });

        sendToDistribution.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sendToDistribution.setText("Send to Distribution");
        sendToDistribution.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        sendToDistribution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendToDistributionActionPerformed(evt);
            }
        });

        viewAll.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        viewAll.setText("View All");
        viewAll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        viewAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewAllActionPerformed(evt);
            }
        });

        regOrderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Order ID", "Product Name", "Quantity", "Order Date", "Due Date", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        regOrderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                regOrderTableMouseClicked(evt);
            }
        });
        orderstable.setViewportView(regOrderTable);
        if (regOrderTable.getColumnModel().getColumnCount() > 0) {
            regOrderTable.getColumnModel().getColumn(0).setResizable(false);
            regOrderTable.getColumnModel().getColumn(1).setResizable(false);
            regOrderTable.getColumnModel().getColumn(2).setResizable(false);
            regOrderTable.getColumnModel().getColumn(3).setResizable(false);
            regOrderTable.getColumnModel().getColumn(4).setResizable(false);
            regOrderTable.getColumnModel().getColumn(5).setResizable(false);
        }

        category.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select a Category", "Order ID", "Order Due Date", "Status", "Product" }));
        category.setPreferredSize(new java.awt.Dimension(150, 20));
        category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryActionPerformed(evt);
            }
        });

        Bsearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manufacturing/images/search2.png"))); // NOI18N
        Bsearch.setBorderPainted(false);
        Bsearch.setContentAreaFilled(false);
        Bsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BsearchActionPerformed(evt);
            }
        });

        del.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        del.setText("Delete");
        del.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout NormalOrdersLayout = new javax.swing.GroupLayout(NormalOrders);
        NormalOrders.setLayout(NormalOrdersLayout);
        NormalOrdersLayout.setHorizontalGroup(
            NormalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NormalOrdersLayout.createSequentialGroup()
                .addGroup(NormalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NormalOrdersLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(NormalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(orderstable, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(NormalOrdersLayout.createSequentialGroup()
                                .addGroup(NormalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(NormalOrdersLayout.createSequentialGroup()
                                        .addGap(48, 48, 48)
                                        .addGroup(NormalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(pname)
                                            .addComponent(qnty)
                                            .addComponent(oid)
                                            .addComponent(dueDate))
                                        .addGap(32, 32, 32)
                                        .addGroup(NormalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(Toid, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Tpname, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(TdueDate, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Tqnty, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(viewAll, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(NormalOrdersLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(NormalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(NormalOrdersLayout.createSequentialGroup()
                                .addComponent(searchBy, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(NormalOrdersLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(searchT, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Bsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 39, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NormalOrdersLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(sendToPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(NormalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sendToDistribution, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(del, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        NormalOrdersLayout.setVerticalGroup(
            NormalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NormalOrdersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(NormalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchBy, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(NormalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Bsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchT, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(orderstable, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(NormalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(del, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(viewAll, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(NormalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendToPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendToDistribution, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(NormalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Toid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(oid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(NormalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tpname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pname))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(NormalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tqnty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qnty))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(NormalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TdueDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dueDate))
                .addGap(58, 58, 58))
        );

        HandleRegOrders.add(NormalOrders, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 480));

        ModelPaper.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Handling Modelpaper Set Orders", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        modelPaperOrders.setModel(new javax.swing.table.DefaultTableModel(
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
        modelPaperOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                modelPaperOrdersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(modelPaperOrders);

        viewBagOrders.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        viewBagOrders.setText("View Bag Orders");
        viewBagOrders.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        viewBagOrders.setPreferredSize(new java.awt.Dimension(39, 17));
        viewBagOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewBagOrdersActionPerformed(evt);
            }
        });

        titleM2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        titleM2.setText("Place Bag Order :");

        qt.setMinimumSize(new java.awt.Dimension(6, 25));
        qt.setPreferredSize(new java.awt.Dimension(150, 23));
        qt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qtActionPerformed(evt);
            }
        });
        qt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                qtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qtKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Quantity :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Cover Image :");

        addOrder.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addOrder.setText("Add Order");
        addOrder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        addOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOrderActionPerformed(evt);
            }
        });

        add.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manufacturing/images/attach.png"))); // NOI18N
        add.setBorderPainted(false);
        add.setContentAreaFilled(false);
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Paperset Order ID :");

        MID.setEditable(false);
        MID.setMinimumSize(new java.awt.Dimension(6, 25));
        MID.setPreferredSize(new java.awt.Dimension(150, 23));

        PO.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        PO.setText("Paperset Orders :");

        viewModel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        viewModel.setText("View Paperset Orders");
        viewModel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        viewModel.setPreferredSize(new java.awt.Dimension(39, 17));
        viewModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewModelActionPerformed(evt);
            }
        });

        bgStatus.setText("       Select an Order");
        bgStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));

        status.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        status.setText("Bag Order Availability :");

        bsntPrint.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bsntPrint.setText("Send to Print");
        bsntPrint.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        bsntPrint.setPreferredSize(new java.awt.Dimension(39, 17));
        bsntPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsntPrintActionPerformed(evt);
            }
        });

        searchBy1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        searchBy1.setText("Search By :");

        category1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select a Category", "Order ID", "Order Due Date", "Status", "Product" }));
        category1.setPreferredSize(new java.awt.Dimension(150, 20));
        category1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                category1ActionPerformed(evt);
            }
        });

        searchT1.setText("Search");
        searchT1.setToolTipText("");
        searchT1.setMinimumSize(null);
        searchT1.setPreferredSize(new java.awt.Dimension(150, 20));
        searchT1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchT1FocusLost(evt);
            }
        });
        searchT1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchT1MouseClicked(evt);
            }
        });
        searchT1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchT1ActionPerformed(evt);
            }
        });
        searchT1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchT1KeyTyped(evt);
            }
        });

        Bsearch1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manufacturing/images/search2.png"))); // NOI18N
        Bsearch1.setBorderPainted(false);
        Bsearch1.setContentAreaFilled(false);
        Bsearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bsearch1ActionPerformed(evt);
            }
        });

        sndtoDis.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sndtoDis.setText("Send to Distribution");
        sndtoDis.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        sndtoDis.setPreferredSize(new java.awt.Dimension(39, 17));
        sndtoDis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sndtoDisActionPerformed(evt);
            }
        });

        vali1.setForeground(new java.awt.Color(255, 0, 0));

        tick.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manufacturing/images/tick.png"))); // NOI18N

        javax.swing.GroupLayout ModelPaperLayout = new javax.swing.GroupLayout(ModelPaper);
        ModelPaper.setLayout(ModelPaperLayout);
        ModelPaperLayout.setHorizontalGroup(
            ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ModelPaperLayout.createSequentialGroup()
                .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ModelPaperLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ModelPaperLayout.createSequentialGroup()
                                .addComponent(titleM2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(241, 241, 241))
                            .addGroup(ModelPaperLayout.createSequentialGroup()
                                .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ModelPaperLayout.createSequentialGroup()
                                            .addGap(174, 174, 174)
                                            .addComponent(vali1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(addOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(ModelPaperLayout.createSequentialGroup()
                                            .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(ModelPaperLayout.createSequentialGroup()
                                                    .addComponent(viewModel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                                .addGroup(ModelPaperLayout.createSequentialGroup()
                                                    .addComponent(bsntPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addGap(10, 10, 10)))
                                            .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(sndtoDis, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                                .addComponent(viewBagOrders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                    .addGroup(ModelPaperLayout.createSequentialGroup()
                                        .addGap(53, 53, 53)
                                        .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(ModelPaperLayout.createSequentialGroup()
                                                        .addComponent(status)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(bgStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(ModelPaperLayout.createSequentialGroup()
                                                        .addComponent(jLabel3)
                                                        .addGap(51, 51, 51)
                                                        .addComponent(tick)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(ModelPaperLayout.createSequentialGroup()
                                                    .addComponent(jLabel2)
                                                    .addGap(73, 73, 73)
                                                    .addComponent(qt, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(105, 105, 105)))
                                            .addGroup(ModelPaperLayout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(21, 21, 21)
                                                .addComponent(MID, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(10, 10, 10))))
                    .addGroup(ModelPaperLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(PO))
                    .addGroup(ModelPaperLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ModelPaperLayout.createSequentialGroup()
                                .addComponent(searchBy1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(category1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ModelPaperLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(searchT1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Bsearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(89, 89, Short.MAX_VALUE))
        );
        ModelPaperLayout.setVerticalGroup(
            ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ModelPaperLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchBy1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(category1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Bsearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchT1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PO)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(viewBagOrders, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(viewModel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bsntPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ModelPaperLayout.createSequentialGroup()
                        .addComponent(sndtoDis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1)))
                .addGap(8, 8, 8)
                .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(status)
                    .addComponent(bgStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(titleM2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(MID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(tick, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ModelPaperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(qt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(vali1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        HandleRegOrders.add(ModelPaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, 430, 480));

        bgReg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/board_black_line_texture_background_wood.jpg"))); // NOI18N
        bgReg.setText("Back");
        HandleRegOrders.add(bgReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(HandleRegOrders, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        HandleSpecialOrders.setBackground(new java.awt.Color(0, 0, 255));
        HandleSpecialOrders.setMaximumSize(new java.awt.Dimension(915, 570));
        HandleSpecialOrders.setMinimumSize(new java.awt.Dimension(915, 570));
        HandleSpecialOrders.setOpaque(false);
        HandleSpecialOrders.setPreferredSize(new java.awt.Dimension(915, 570));
        HandleSpecialOrders.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titleHS.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titleHS.setForeground(new java.awt.Color(255, 255, 255));
        titleHS.setText("Handle Special Orders");
        HandleSpecialOrders.add(titleHS, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, -1, -1));

        seminarPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seminar Order Handling", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        seminarPanel.setPreferredSize(new java.awt.Dimension(425, 430));

        tPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seminar Order Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 204, 0))); // NOI18N

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel23.setText("Seminar ID :");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setText("Document Path :");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setText("Due Date :");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel26.setText("Amount :");

        sOrderID.setPreferredSize(new java.awt.Dimension(60, 23));

        sDocumentPath.setPreferredSize(new java.awt.Dimension(60, 23));

        sAmount.setPreferredSize(new java.awt.Dimension(60, 23));

        sDueDate.setPreferredSize(new java.awt.Dimension(60, 23));

        javax.swing.GroupLayout tPanel1Layout = new javax.swing.GroupLayout(tPanel1);
        tPanel1.setLayout(tPanel1Layout);
        tPanel1Layout.setHorizontalGroup(
            tPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tPanel1Layout.createSequentialGroup()
                .addGroup(tPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel26)
                    .addComponent(jLabel25)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sDocumentPath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sOrderID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sDueDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tPanel1Layout.setVerticalGroup(
            tPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(tPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(sOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(sDocumentPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(sAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(sDueDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        searchBy3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        searchBy3.setText("Search By :");

        seminarCatogory.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select a Category", "Seminar ID", "Order Due Date", "Status" }));
        seminarCatogory.setPreferredSize(new java.awt.Dimension(150, 20));
        seminarCatogory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seminarCatogoryActionPerformed(evt);
            }
        });

        searchSeminar.setText("Search");
        searchSeminar.setToolTipText("");
        searchSeminar.setMinimumSize(null);
        searchSeminar.setPreferredSize(new java.awt.Dimension(150, 20));
        searchSeminar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchSeminarFocusLost(evt);
            }
        });
        searchSeminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchSeminarMouseClicked(evt);
            }
        });
        searchSeminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchSeminarActionPerformed(evt);
            }
        });
        searchSeminar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchSeminarKeyTyped(evt);
            }
        });

        seminarSearchbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manufacturing/images/search2.png"))); // NOI18N
        seminarSearchbtn.setBorderPainted(false);
        seminarSearchbtn.setContentAreaFilled(false);
        seminarSearchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seminarSearchbtnActionPerformed(evt);
            }
        });

        seminarTable.setModel(new javax.swing.table.DefaultTableModel(
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
        seminarTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seminarTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(seminarTable);

        seminarDisbtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        seminarDisbtn.setText("Send to Distribution");
        seminarDisbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        seminarDisbtn.setPreferredSize(new java.awt.Dimension(150, 25));
        seminarDisbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seminarDisbtnActionPerformed(evt);
            }
        });

        seminarPrintbtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        seminarPrintbtn.setText("Send to Print");
        seminarPrintbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        seminarPrintbtn.setPreferredSize(new java.awt.Dimension(150, 25));
        seminarPrintbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seminarPrintbtnActionPerformed(evt);
            }
        });

        seminarAllbtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        seminarAllbtn.setText("View All");
        seminarAllbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        seminarAllbtn.setPreferredSize(new java.awt.Dimension(150, 25));
        seminarAllbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seminarAllbtnActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("Seminar Orders :");

        javax.swing.GroupLayout seminarPanelLayout = new javax.swing.GroupLayout(seminarPanel);
        seminarPanel.setLayout(seminarPanelLayout);
        seminarPanelLayout.setHorizontalGroup(
            seminarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seminarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(seminarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, seminarPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(seminarPanelLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, seminarPanelLayout.createSequentialGroup()
                        .addGap(0, 28, Short.MAX_VALUE)
                        .addGroup(seminarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(seminarPanelLayout.createSequentialGroup()
                                .addComponent(searchBy3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(seminarCatogory, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(seminarPanelLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(searchSeminar, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(seminarSearchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, seminarPanelLayout.createSequentialGroup()
                        .addComponent(tPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(seminarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(seminarPanelLayout.createSequentialGroup()
                                .addGroup(seminarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(seminarAllbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(seminarDisbtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, seminarPanelLayout.createSequentialGroup()
                                .addComponent(seminarPrintbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );
        seminarPanelLayout.setVerticalGroup(
            seminarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seminarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(seminarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchBy3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seminarCatogory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(seminarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(seminarSearchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchSeminar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(seminarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(seminarPanelLayout.createSequentialGroup()
                        .addComponent(seminarPrintbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(seminarDisbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(seminarAllbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)))
                .addGap(107, 107, 107))
        );

        HandleSpecialOrders.add(seminarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, -1, 470));

        tendePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tender Order Handling", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        tendePanel.setPreferredSize(new java.awt.Dimension(430, 420));

        tPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tender Order Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 204, 0))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Tender ID :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Document Path :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Amount :");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Due Date :");

        tOrderID.setPreferredSize(new java.awt.Dimension(60, 23));

        tDocumentPath.setPreferredSize(new java.awt.Dimension(60, 23));

        tAmount.setPreferredSize(new java.awt.Dimension(60, 23));

        tDueDate.setPreferredSize(new java.awt.Dimension(60, 23));

        javax.swing.GroupLayout tPanelLayout = new javax.swing.GroupLayout(tPanel);
        tPanel.setLayout(tPanelLayout);
        tPanelLayout.setHorizontalGroup(
            tPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tPanelLayout.createSequentialGroup()
                .addGroup(tPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tOrderID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tDocumentPath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tDueDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        tPanelLayout.setVerticalGroup(
            tPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(tPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tDocumentPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tDueDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        searchBy2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        searchBy2.setText("Search By :");

        tenderCatogory.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select a Category", "Tender ID", "Order Due Date", "Status" }));
        tenderCatogory.setPreferredSize(new java.awt.Dimension(150, 20));
        tenderCatogory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenderCatogoryActionPerformed(evt);
            }
        });

        tenderSearch.setText("Search");
        tenderSearch.setToolTipText("");
        tenderSearch.setMinimumSize(null);
        tenderSearch.setPreferredSize(new java.awt.Dimension(150, 20));
        tenderSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tenderSearchFocusLost(evt);
            }
        });
        tenderSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tenderSearchMouseClicked(evt);
            }
        });
        tenderSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenderSearchActionPerformed(evt);
            }
        });
        tenderSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tenderSearchKeyTyped(evt);
            }
        });

        tenderSearchbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Manufacturing/images/search2.png"))); // NOI18N
        tenderSearchbtn.setBorderPainted(false);
        tenderSearchbtn.setContentAreaFilled(false);
        tenderSearchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenderSearchbtnActionPerformed(evt);
            }
        });

        tenderTable.setModel(new javax.swing.table.DefaultTableModel(
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
        tenderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tenderTableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tenderTable);

        tenderPrintbtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tenderPrintbtn.setText("Send to Print");
        tenderPrintbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        tenderPrintbtn.setPreferredSize(new java.awt.Dimension(150, 25));
        tenderPrintbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenderPrintbtnActionPerformed(evt);
            }
        });

        tenderDisbtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tenderDisbtn.setText("Send to Distribution");
        tenderDisbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        tenderDisbtn.setPreferredSize(new java.awt.Dimension(150, 25));
        tenderDisbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenderDisbtnActionPerformed(evt);
            }
        });

        tenderAllbtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tenderAllbtn.setText("View All");
        tenderAllbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        tenderAllbtn.setPreferredSize(new java.awt.Dimension(150, 25));
        tenderAllbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenderAllbtnActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("Tender Orders :");

        javax.swing.GroupLayout tendePanelLayout = new javax.swing.GroupLayout(tendePanel);
        tendePanel.setLayout(tendePanelLayout);
        tendePanelLayout.setHorizontalGroup(
            tendePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tendePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tendePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tendePanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(14, Short.MAX_VALUE))
                    .addGroup(tendePanelLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tendePanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(tendePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tendePanelLayout.createSequentialGroup()
                                .addComponent(searchBy2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(tenderCatogory, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(tendePanelLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(tenderSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tenderSearchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tendePanelLayout.createSequentialGroup()
                        .addComponent(tPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tendePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tenderPrintbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tenderAllbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tenderDisbtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        tendePanelLayout.setVerticalGroup(
            tendePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tendePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tendePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchBy2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tenderCatogory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tendePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tenderSearchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tenderSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel27)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(tendePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(tendePanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(tenderPrintbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tenderDisbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tenderAllbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)))
                .addGap(39, 39, 39))
        );

        HandleSpecialOrders.add(tendePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 470));

        bgSpec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/blackbackground.jpg"))); // NOI18N
        HandleSpecialOrders.add(bgSpec, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(HandleSpecialOrders, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        WorkRate.setBackground(new java.awt.Color(0, 0, 0));
        WorkRate.setMaximumSize(new java.awt.Dimension(915, 570));
        WorkRate.setMinimumSize(new java.awt.Dimension(915, 570));
        WorkRate.setPreferredSize(new java.awt.Dimension(915, 570));
        WorkRate.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        wTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        wTitle.setForeground(new java.awt.Color(255, 255, 255));
        wTitle.setText("Work Rate");
        WorkRate.add(wTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, -1, -1));

        WmainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manage Work Rates", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        workRateTable.setModel(new javax.swing.table.DefaultTableModel(
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
        workRateTable.setPreferredSize(null);
        workRateTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                workRateTableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(workRateTable);

        tableHeading.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tableHeading.setText("Today's Attendance :");

        wrPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Date", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 204, 0))); // NOI18N

        wDate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        wDate.setText("2nd of March 2015 , Thursday ");

        javax.swing.GroupLayout wrPanelLayout = new javax.swing.GroupLayout(wrPanel);
        wrPanel.setLayout(wrPanelLayout);
        wrPanelLayout.setHorizontalGroup(
            wrPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wrPanelLayout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(wDate, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
        );
        wrPanelLayout.setVerticalGroup(
            wrPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wrPanelLayout.createSequentialGroup()
                .addComponent(wDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        wPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enter Work Rate", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 204, 0))); // NOI18N

        firstField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        firstField.setText("Employee ID :");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel32.setText("Employee Name :");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setText("No Of Pieces :");

        wID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        wID.setText("ID");
        wID.setPreferredSize(new java.awt.Dimension(150, 20));

        wName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        wName.setText("Name");
        wName.setPreferredSize(new java.awt.Dimension(150, 20));

        wPiece.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        wPiece.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                wPieceMouseReleased(evt);
            }
        });

        nxtWk.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nxtWk.setText("Next");
        nxtWk.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        nxtWk.setPreferredSize(null);
        nxtWk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nxtWkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout wPanel1Layout = new javax.swing.GroupLayout(wPanel1);
        wPanel1.setLayout(wPanel1Layout);
        wPanel1Layout.setHorizontalGroup(
            wPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wPanel1Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(wPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(firstField))
                .addGap(18, 18, 18)
                .addGroup(wPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(wID, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(wName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(wPiece))
                .addContainerGap(130, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, wPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nxtWk, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        wPanel1Layout.setVerticalGroup(
            wPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(wPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstField)
                    .addComponent(wID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(wPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(wName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(wPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wPiece, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addGap(24, 24, 24)
                .addComponent(nxtWk, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addContainerGap())
        );

        updatePiceRate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        updatePiceRate.setText("Update Pieces");
        updatePiceRate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        updatePiceRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatePiceRateActionPerformed(evt);
            }
        });

        viewPice.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        viewPice.setText("View Piece Rate ");
        viewPice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        viewPice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewPiceActionPerformed(evt);
            }
        });

        viewAttendance.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        viewAttendance.setText("View Attendance");
        viewAttendance.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        viewAttendance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewAttendanceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout WmainPanelLayout = new javax.swing.GroupLayout(WmainPanel);
        WmainPanel.setLayout(WmainPanelLayout);
        WmainPanelLayout.setHorizontalGroup(
            WmainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WmainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(WmainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(wrPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(wPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(WmainPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(viewPice, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(updatePiceRate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(viewAttendance, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(WmainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(WmainPanelLayout.createSequentialGroup()
                        .addComponent(tableHeading)
                        .addGap(0, 146, Short.MAX_VALUE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        WmainPanelLayout.setVerticalGroup(
            WmainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WmainPanelLayout.createSequentialGroup()
                .addGroup(WmainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(WmainPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(tableHeading)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(WmainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(wrPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(wPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(WmainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(updatePiceRate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewPice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewAttendance, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        WorkRate.add(WmainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 850, 460));

        bgWork.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/board_black_line_texture_background_wood.jpg"))); // NOI18N
        WorkRate.add(bgWork, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(WorkRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        CheckWastage.setBackground(new java.awt.Color(0, 0, 0));
        CheckWastage.setAlignmentY(0.0F);
        CheckWastage.setFocusable(false);
        CheckWastage.setMaximumSize(new java.awt.Dimension(915, 570));
        CheckWastage.setMinimumSize(new java.awt.Dimension(915, 570));
        CheckWastage.setPreferredSize(new java.awt.Dimension(915, 570));
        CheckWastage.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cTitle.setForeground(new java.awt.Color(255, 255, 255));
        cTitle.setText("Wastage Management");
        CheckWastage.add(cTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, -1, -1));

        cReportPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manage Wastage", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Get Reports", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 204, 0))); // NOI18N

        cReportbtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cReportbtn.setText("Get Wastage Report");
        cReportbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        cReportbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cReportbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(cReportbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(cReportbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        wastageTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(wastageTable);

        wastageTableHeading.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        wastageTableHeading.setText("Wastage Order wise:");

        cViewPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "View Wastage", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 204, 0))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Check Wastage for :");

        jLabel16.setText("(Select an Item)");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("Order ID :");

        checkItemcmb.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        checkItemcmb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select an Item", "Paper", "Plate", "Ink Yellow", "Ink Cyan", "Ink Black", "Ink Magenta" }));
        checkItemcmb.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkItemcmbItemStateChanged(evt);
            }
        });
        checkItemcmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkItemcmbActionPerformed(evt);
            }
        });

        cCheckbtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cCheckbtn.setText("Check");
        cCheckbtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        cCheckbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cCheckbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cViewPanelLayout = new javax.swing.GroupLayout(cViewPanel);
        cViewPanel.setLayout(cViewPanelLayout);
        cViewPanelLayout.setHorizontalGroup(
            cViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cViewPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cCheckbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(cViewPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(cViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel22)
                    .addGroup(cViewPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel16)))
                .addGap(18, 18, 18)
                .addGroup(cViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(orderIDcombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkItemcmb, 0, 145, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cViewPanelLayout.setVerticalGroup(
            cViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkItemcmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(orderIDcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cCheckbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cMonthlyWabtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cMonthlyWabtn.setText("Monthly Wastage");
        cMonthlyWabtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        cMonthlyWabtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cMonthlyWabtnActionPerformed(evt);
            }
        });

        cUpdateWaTabtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cUpdateWaTabtn.setText("Update Wastage Table");
        cUpdateWaTabtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        cUpdateWaTabtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cUpdateWaTabtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cReportPanelLayout = new javax.swing.GroupLayout(cReportPanel);
        cReportPanel.setLayout(cReportPanelLayout);
        cReportPanelLayout.setHorizontalGroup(
            cReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cReportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cReportPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(cReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(cReportPanelLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(cUpdateWaTabtn, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addComponent(cMonthlyWabtn, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cViewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(wastageTableHeading))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        cReportPanelLayout.setVerticalGroup(
            cReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cReportPanelLayout.createSequentialGroup()
                .addGroup(cReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(cReportPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(wastageTableHeading)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 14, Short.MAX_VALUE))
                    .addGroup(cReportPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(cReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cMonthlyWabtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cUpdateWaTabtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(cViewPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        CheckWastage.add(cReportPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 830, 430));

        bgwaste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/board_black_line_texture_background_wood.jpg"))); // NOI18N
        bgwaste.setFocusable(false);
        CheckWastage.add(bgwaste, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        rightPanel.add(CheckWastage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        RawMaterials.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        RawBackPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manage Raw Material", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        RawBackPanel.setPreferredSize(new java.awt.Dimension(39, 23));

        id.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        id.setText("Order ID :");
        id.setPreferredSize(new java.awt.Dimension(100, 23));

        PName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PName.setText("Product Name :");
        PName.setPreferredSize(new java.awt.Dimension(100, 23));

        jLabel11.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel11.setText("Used Raw Materials for the Order:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Papers :");
        jLabel12.setPreferredSize(new java.awt.Dimension(100, 23));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Plates :");
        jLabel13.setPreferredSize(new java.awt.Dimension(100, 23));

        jLabel14.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel14.setText("Ink :");
        jLabel14.setPreferredSize(new java.awt.Dimension(100, 23));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Black :");
        jLabel15.setPreferredSize(new java.awt.Dimension(100, 23));

        Cyan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Cyan.setText("Cyan :");
        Cyan.setPreferredSize(new java.awt.Dimension(100, 23));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Magenta :");
        jLabel17.setPreferredSize(new java.awt.Dimension(100, 23));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Yellow :");
        jLabel18.setPreferredSize(new java.awt.Dimension(100, 23));

        roid.setEditable(false);
        roid.setPreferredSize(new java.awt.Dimension(39, 23));

        rproNo.setEditable(false);
        rproNo.setPreferredSize(new java.awt.Dimension(39, 23));

        nPa.setPreferredSize(new java.awt.Dimension(39, 23));
        nPa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nPaMouseClicked(evt);
            }
        });
        nPa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nPaKeyReleased(evt);
            }
        });

        nPl.setPreferredSize(new java.awt.Dimension(39, 23));
        nPl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nPlMouseClicked(evt);
            }
        });
        nPl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nPlKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nPlKeyTyped(evt);
            }
        });

        nB.setPreferredSize(new java.awt.Dimension(39, 23));
        nB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nBMouseClicked(evt);
            }
        });
        nB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nBKeyReleased(evt);
            }
        });

        nC.setPreferredSize(new java.awt.Dimension(39, 23));
        nC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nCMouseClicked(evt);
            }
        });
        nC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nCKeyReleased(evt);
            }
        });

        nM.setPreferredSize(new java.awt.Dimension(39, 23));
        nM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nMMouseClicked(evt);
            }
        });
        nM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nMKeyReleased(evt);
            }
        });

        nY.setPreferredSize(new java.awt.Dimension(39, 23));
        nY.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nYMouseClicked(evt);
            }
        });
        nY.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nYKeyReleased(evt);
            }
        });

        updateRaw.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        updateRaw.setText("Update Raw Maretials");
        updateRaw.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        updateRaw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateRawActionPerformed(evt);
            }
        });

        backRaw.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        backRaw.setText("Back");
        backRaw.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));
        backRaw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backRawActionPerformed(evt);
            }
        });

        usedRawMaterials.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(usedRawMaterials);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Used Raw Materials");

        javax.swing.GroupLayout RawBackPanelLayout = new javax.swing.GroupLayout(RawBackPanel);
        RawBackPanel.setLayout(RawBackPanelLayout);
        RawBackPanelLayout.setHorizontalGroup(
            RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RawBackPanelLayout.createSequentialGroup()
                .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RawBackPanelLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(RawBackPanelLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(RawBackPanelLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(27, 27, 27)
                                        .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(nPa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(nPl, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(vPl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(vPa, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel11)))
                            .addGroup(RawBackPanelLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(id, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                                    .addComponent(PName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rproNo, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(roid, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(RawBackPanelLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(Cyan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(RawBackPanelLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(nM, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(nB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(nC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(vM, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(vC, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(vB, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(RawBackPanelLayout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(nY, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(vY, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RawBackPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(updateRaw, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(backRaw, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)))
                .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RawBackPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RawBackPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(89, 89, 89))))
        );
        RawBackPanelLayout.setVerticalGroup(
            RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RawBackPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(3, 3, 3)
                .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RawBackPanelLayout.createSequentialGroup()
                        .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rproNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nPa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vPa))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nPl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vPl))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vB))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Cyan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vC))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vM))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vY))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(RawBackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(backRaw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(updateRaw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        RawMaterials.add(RawBackPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 740, 400));

        rtitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        rtitle.setForeground(new java.awt.Color(255, 255, 255));
        rtitle.setText("Raw Materials Update");
        RawMaterials.add(rtitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 280, -1));

        backgroundRaw.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/background.jpg"))); // NOI18N
        RawMaterials.add(backgroundRaw, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, -1));

        rightPanel.add(RawMaterials, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 570));

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

    private void specialOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specialOrderActionPerformed
        // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels
        
        HandleRegOrders.setVisible(false);
        HandleSpecialOrders.setVisible(true);
        WorkRate.setVisible(false);
        CheckWastage.setVisible(false);
        
        seminarOdertable();
        TenderTable();
        
        rightPanel.add(HandleSpecialOrders);
        rightPanel.repaint();
        rightPanel.revalidate();
        
        
    }//GEN-LAST:event_specialOrderActionPerformed

    private void regOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regOrderActionPerformed
         // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels
        
        HandleRegOrders.setVisible(true);
        HandleSpecialOrders.setVisible(false);
        WorkRate.setVisible(false);
        CheckWastage.setVisible(false);
        
        
        rightPanel.add(HandleRegOrders);
        rightPanel.repaint();
        rightPanel.revalidate();
        
        
        
        
    }//GEN-LAST:event_regOrderActionPerformed

    private void ckwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckwActionPerformed
         // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels
        HandleRegOrders.setVisible(false);
        HandleSpecialOrders.setVisible(false);
        WorkRate.setVisible(false);
        CheckWastage.setVisible(true);

        wastageTable();
        
        rightPanel.add(CheckWastage);
        rightPanel.repaint();
        rightPanel.revalidate();
    }//GEN-LAST:event_ckwActionPerformed

    private void wkrateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wkrateActionPerformed
         // removing panels
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
        //adding panels
        
        DateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        Date date = new Date();
        wDate.setText(dateFormat.format(date));
        
        attendanceTable();
        nextCount=0;
        updatePiceRate.setEnabled(false);
        if(workRateTable.getRowCount()!=0)
        {
            wID.setText(workRateTable.getValueAt(nextCount,0).toString());
            wName.setText(workRateTable.getValueAt(nextCount,1).toString());
        }

        HandleRegOrders.setVisible(false);
        HandleSpecialOrders.setVisible(false);
        WorkRate.setVisible(true);
        CheckWastage.setVisible(false);
        
        rightPanel.add(WorkRate);
        rightPanel.repaint();
        rightPanel.revalidate();
        
        

    }//GEN-LAST:event_wkrateActionPerformed

    private void sendToPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendToPrintActionPerformed
        try{
            int orderid = Integer.parseInt(Toid.getText());
            int raw = regOrderTable.getSelectedRow();
            
            if(raw == -1)
            {
                JOptionPane.showMessageDialog(this,"Select an Order First");  
            }
            else
            {
                String status =regOrderTable.getValueAt(raw,6).toString();

                if(status.equals("processing"))
                {
                    JOptionPane.showMessageDialog(this,"Order is already in printing division");
                }
                else if(status.equals("complete"))
                {
                    JOptionPane.showMessageDialog(this,"Order has been completed");
                }
                else
                {
                    boolean checkStatus=ROD.updatePrintingNormal(orderid);
                    if(checkStatus==true)
                        JOptionPane.showMessageDialog(this,"Successfully forwared to printing division");
                    else
                        JOptionPane.showMessageDialog(this,"Sending Failed!");
                    
                    tableLoad();
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
        }
    }//GEN-LAST:event_sendToPrintActionPerformed

    private void regOrderTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_regOrderTableMouseClicked
        int raw = regOrderTable.getSelectedRow();
        String ID = regOrderTable.getValueAt(raw,0).toString();
        String Product_Name = regOrderTable.getValueAt(raw,1).toString();
        String grade = regOrderTable.getValueAt(raw,2).toString();
        String quantity = regOrderTable.getValueAt(raw,3).toString();
        String dDate = regOrderTable.getValueAt(raw,5).toString();
        
        Toid.setText(ID);
        Tpname.setText(Product_Name+" Grade "+grade);
        Tqnty.setText(quantity);
        TdueDate.setText(dDate);
        
    }//GEN-LAST:event_regOrderTableMouseClicked

    private void searchTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTActionPerformed

    private void sendToDistributionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendToDistributionActionPerformed
       
        int raw = regOrderTable.getSelectedRow();
        
        if(raw == -1)
        {
            JOptionPane.showMessageDialog(this,"Select an Order First");  
        }
        else
        {
            String finalID = regOrderTable.getValueAt(raw,0).toString();
            String productName=regOrderTable.getValueAt(raw,1).toString();
            String productgrade=regOrderTable.getValueAt(raw,2).toString();
            String printStatus =regOrderTable.getValueAt(raw,6).toString();

            if(printStatus.equals("processing"))
            {

                    try{
                        String sql2 = "update orders set printingStatus='complete' where orderID='"+finalID+"'";
                        pst=con.prepareStatement(sql2);
                        pst.execute();
                        tableLoad();
                        JOptionPane.showMessageDialog(this,"Successfully sent to Distribution");
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
                    }
                    
                    UsedRawMaterialTable();
                    roid.setText(finalID);
                    rproNo.setText(productName+" "+productgrade);
                    
                    rightPanel.removeAll();
                    rightPanel.repaint();
                    rightPanel.revalidate();
                    //adding panels
                    RawMaterials.setVisible(true);
                    
                    rightPanel.add(RawMaterials);
                    rightPanel.repaint();
                    rightPanel.revalidate();
            }
            else if(printStatus.equals("complete"))
            {
                JOptionPane.showMessageDialog(this,"Product already has been sent to Distribution");
            }
            else if(printStatus.equals("not complete"))
            {
                JOptionPane.showMessageDialog(this,"Product should be sent to printing division first");
            }
        }
        
    }//GEN-LAST:event_sendToDistributionActionPerformed

    private void viewAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewAllActionPerformed
        tableLoad();
    }//GEN-LAST:event_viewAllActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
            
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(this);
            File f = chooser.getSelectedFile();
            filename = f.getAbsolutePath();
            tick.setVisible(true) ;
        
    }//GEN-LAST:event_addActionPerformed

    private void addOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOrderActionPerformed

        String Mid=MID.getText();
        String path=filename;
        String qnt=qt.getText();
        
        try
        {
            String sql6="Insert into bag_order(MOID,coverImage,amount,printingStatus)"
                    + "values('"+Mid+"','"+path+"','"+qnt+"','processing')";
            pst=con.prepareStatement(sql6);
            pst.execute();
            JOptionPane.showMessageDialog(this,"Successful");
            
            tableLaodBag();
            status.setVisible(false);
            bgStatus.setVisible(false);
            sndtoDis.setVisible(false);
            bsntPrint.setVisible(false);
            MID.setText("");
            tick.setVisible(false) ;
            qt.setText("");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"Couldn't Palce the Order");
            MID.setText("");
            tick.setVisible(false) ;
            qt.setText("");
        }
    }//GEN-LAST:event_addOrderActionPerformed

    private void BsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BsearchActionPerformed
        
        getDetailsOrders GDO = new getDetailsOrders();
        
        GDO.setItem(searchT.getText());
        GDO.setCato(category.getSelectedItem().toString());
        
        switch(GDO.getCato())
        {
            case "Select a Category":
                JOptionPane.showMessageDialog(this,"Select a Category First");
                break;
            case "Order ID":
                switch(GDO.getItem())
                {
                    case "":
                    case "Search":
                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
                       break; 
                    default:
                        try
                        {
                            ResultSet R1=ROD.searchOrderID(GDO.getItem());
                            ResultSet R2=ROD.searchOrderID(GDO.getItem());
                            int count=0;
                            while(R1.next())
                            {
                                count++;       
                            }
                            if(count == 0)
                            {
                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
                            }
                            else
                            {
                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,count+" Results Found");
                            }
                            searchT.setText("Search");
                        }
                        catch(Exception e){JOptionPane.showMessageDialog(this,"Error!");}
                        break;
                }
                break;
            case "Order Due Date":
                switch(GDO.getItem())
                {
                    case "":
                    case "Search":
                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
                       break; 
                    default:
                        try{
                            ResultSet R1=ROD.searchOrderDate(GDO.getItem());
                            ResultSet R2=ROD.searchOrderDate(GDO.getItem());
                            int count=0;
                            while(R1.next())
                            {
                                count++;       
                            }
                            if(count == 0)
                            {
                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
                            }
                            else
                            {
                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,count+" Results Found");
                            }
                            
                            searchT.setText("Search");
                            
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
                        }
                        break;
                }
                break;
            case "Status":
                switch(GDO.getItem())
                {
                    case "":
                    case "Search":
                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
                       break; 
                    default:
                        try{
                            ResultSet R1=ROD.searchStatus(GDO.getItem());
                            ResultSet R2=ROD.searchStatus(GDO.getItem());
                            int count=0;
                            while(R1.next())
                            {
                                count++;       
                            }
                            if(count == 0)
                            {
                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
                            }
                            else
                            {
                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,count+" Results Found");
                            }
                            searchT.setText("Search");
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
                        }
                        break;
                }
                break;
            case "Product":
                switch(GDO.getItem())
                {
                    case "":
                    case "Search":
                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
                       break; 
                    default:
                        try{
                            ResultSet R1=ROD.searchProduct(GDO.getItem());
                            ResultSet R2=ROD.searchProduct(GDO.getItem());
                            int count=0;
                            while(R1.next())
                            {
                                count++;       
                            }
                            if(count == 0)
                            {
                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
                            }
                            else
                            {
                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,count+" Results Found");
                            }
                            searchT.setText("Search");
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
                        }
                        break;
                }
                break;
        }
        
    }//GEN-LAST:event_BsearchActionPerformed

    private void categoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryActionPerformed

    }//GEN-LAST:event_categoryActionPerformed

    private void viewBagOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewBagOrdersActionPerformed
        status.setVisible(false);
        bgStatus.setVisible(false);
        bsntPrint.setVisible(false);
        sndtoDis.setVisible(false);
        tableLaodBag();
    }//GEN-LAST:event_viewBagOrdersActionPerformed

    private void viewModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewModelActionPerformed
        status.setVisible(true);
        bgStatus.setVisible(true);
        bsntPrint.setVisible(true);
        sndtoDis.setVisible(true);
        modelPaperTable();
    }//GEN-LAST:event_viewModelActionPerformed

    private void searchTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTMouseClicked
        searchT.setText("");
    }//GEN-LAST:event_searchTMouseClicked

    private void modelPaperOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modelPaperOrdersMouseClicked
        MID.setText("");
        qt.setText("");
        try
        {
            int raw = modelPaperOrders.getSelectedRow();
            String ID = modelPaperOrders.getValueAt(raw,0).toString();
            String quantity = modelPaperOrders.getValueAt(raw,3).toString();
           
            String sql="select bagOrderID from bag_order where MOID='"+ID+"'";
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            
            if(rs.next())
            {
                bgStatus.setText("    Available");
            }
            else 
            {  
                bgStatus.setText("    Not Available");
                MID.setText(ID);
                qt.setText(quantity);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
        }
        
    }//GEN-LAST:event_modelPaperOrdersMouseClicked

    private void bsntPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsntPrintActionPerformed
        try{
            int raw = modelPaperOrders.getSelectedRow();
            
            if(raw == -1)
            {
//                JOJptionPane.showMessageDialog(this,"Select an Order First");  
            }
            else
            {
                int orderID= Integer.parseInt(modelPaperOrders.getValueAt(raw,0).toString());
                String bstatus =modelPaperOrders.getValueAt(raw,6).toString();

                if(bstatus.equals("processing"))
                {
                    JOptionPane.showMessageDialog(this,"Order is already in printing division");
                }
                else if(bstatus.equals("complete"))
                {
                    JOptionPane.showMessageDialog(this,"Order has been completed");
                }
                else
                {
                    boolean checkStatus=ROD.updatePrintingModelPaper(orderID);
                    if(checkStatus==true)
                        JOptionPane.showMessageDialog(this,"Successfully forwared to printing division");
                    else
                        JOptionPane.showMessageDialog(this,"Sending Failed!");
                    modelPaperTable();
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
        }
    }//GEN-LAST:event_bsntPrintActionPerformed

    private void category1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_category1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_category1ActionPerformed

    private void searchT1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchT1MouseClicked
        searchT1.setText("");
    }//GEN-LAST:event_searchT1MouseClicked

    private void searchT1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchT1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchT1ActionPerformed

    private void Bsearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bsearch1ActionPerformed
        getDetailsOrders GDO = new getDetailsOrders(category1.getSelectedItem().toString(),searchT1.getText());
        RegOrdersDBAccess ROD = new RegOrdersDBAccess();

        switch(GDO.getCato())
        {
            case "Select a Category":
                JOptionPane.showMessageDialog(this,"Select a Category First");
                break;
            case "Order ID":
                switch(GDO.getItem())
                {
                    case "":
                    case "Search":
                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
                       break; 
                    default:
                        try
                        {
                            ResultSet R1=ROD.searchOrderIDM(GDO.getItem());
                            ResultSet R2=ROD.searchOrderIDM(GDO.getItem());
                            int count=0;
                            while(R1.next())
                            {
                                count++;       
                            }
                            if(count == 0)
                            {
                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
                            }
                            else
                            {
                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,count+" Results Found");
                            }
                            searchT1.setText("Search");
                        }
                        catch(Exception e){JOptionPane.showMessageDialog(this,"Error!");}
                        break;
                }
                break;
            case "Order Due Date":
                switch(GDO.getItem())
                {
                    case "":
                    case "Search":
                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
                       break; 
                    default:
                        try{
                            ResultSet R1=ROD.searchOrderDateM(GDO.getItem());
                            ResultSet R2=ROD.searchOrderDateM(GDO.getItem());
                            int count=0;
                            while(R1.next())
                            {
                                count++;       
                            }
                            if(count == 0)
                            {
                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
                            }
                            else
                            {
                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,count+" Results Found");
                            }
                            
                            searchT1.setText("Search");
                            
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
                        }
                        break;
                }
                break;
            case "Status":
                switch(GDO.getItem())
                {
                    case "":
                    case "Search":
                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
                       break; 
                    default:
                        try{
                            ResultSet R1=ROD.searchStatusM(GDO.getItem());
                            ResultSet R2=ROD.searchStatusM(GDO.getItem());
                            int count=0;
                            while(R1.next())
                            {
                                count++;       
                            }
                            if(count == 0)
                            {
                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
                            }
                            else
                            {
                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,count+" Results Found");
                            }
                            searchT1.setText("Search");
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
                        }
                        break;
                }
                break;
            case "Product":
                switch(GDO.getItem())
                {
                    case "":
                    case "Search":
                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
                       break; 
                    default:
                        try{
                            ResultSet R1=ROD.searchProductM(GDO.getItem());
                            ResultSet R2=ROD.searchProductM(GDO.getItem());
                            int count=0;
                            while(R1.next())
                            {
                                count++;       
                            }
                            if(count == 0)
                            {
                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
                            }
                            else
                            {
                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,count+" Results Found");
                            }
                            searchT1.setText("Search");
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
                        }
                        break;
                }
                break;
        }
    }//GEN-LAST:event_Bsearch1ActionPerformed

    private void qtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtKeyTyped
        
    }//GEN-LAST:event_qtKeyTyped

    private void sndtoDisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sndtoDisActionPerformed
        int raw = modelPaperOrders.getSelectedRow();
        
        if(raw == -1)
        {
            JOptionPane.showMessageDialog(this,"Select an Order First");  
        }
        else
        {
            String finalID = modelPaperOrders.getValueAt(raw,0).toString();
            String productName=modelPaperOrders.getValueAt(raw,1).toString();
            String productgrade=modelPaperOrders.getValueAt(raw,2).toString();
            String printStatus =modelPaperOrders.getValueAt(raw,6).toString();

            if(printStatus.equals("processing"))
            {

                    try{
                        String sql2 = "update orders set printingStatus='complete' where orderID='"+finalID+"'";
                        String sql22="update bag_order set printingStatus='complete' where MOID='"+finalID+"'";
                        pst=con.prepareStatement(sql2);
                        pst1=con.prepareStatement(sql22);
                        pst.execute();
                        pst1.execute();
                        modelPaperTable();
                        JOptionPane.showMessageDialog(this,"Successfully sent to Distribution");
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
                    }
                    
                    UsedRawMaterialTable();
                    roid.setText(finalID);
                    rproNo.setText(productName+" "+productgrade);
                    
                    rightPanel.removeAll();
                    rightPanel.repaint();
                    rightPanel.revalidate();
                    //adding panels
                    RawMaterials.setVisible(true);
                    
                    rightPanel.add(RawMaterials);
                    rightPanel.repaint();
                    rightPanel.revalidate();
            }
            else if(printStatus.equals("complete"))
            {
                JOptionPane.showMessageDialog(this,"Product already has been sent to Distribution");
            }
            else if(printStatus.equals("not complete"))
            {
                JOptionPane.showMessageDialog(this,"Product should be sent to printing division first");
            }
        }
    }//GEN-LAST:event_sndtoDisActionPerformed

    private void qtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qtActionPerformed

    private void updateRawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateRawActionPerformed

        if((nPa.getText().isEmpty())||(nPl.getText().isEmpty())||(nB.getText().isEmpty())||(nY.getText().isEmpty())||(nC.getText().isEmpty())||(nM.getText().isEmpty()))
        {
            JOptionPane.showMessageDialog(this,"One or more fields are empty!");
            if(nPa.getText().isEmpty()){
                nPa.setBackground(Color.PINK);
            }
            if(nPl.getText().isEmpty()){
                nPl.setBackground(Color.PINK);
            }
            if(nB.getText().isEmpty()){
                nB.setBackground(Color.PINK);
            }
            if(nY.getText().isEmpty()){
                nY.setBackground(Color.PINK);
            }
            if(nC.getText().isEmpty()){
                nC.setBackground(Color.PINK);
            }
            if(nM.getText().isEmpty()){
                nM.setBackground(Color.PINK);
            }
        }
        else
            if(papers||plates||inkB||inkC||inkM||inkY){
                JOptionPane.showMessageDialog(this, "One or more fields are invalid!");
            }
            else{
        String idr=roid.getText();
        int item1=Integer.parseInt(nPa.getText());
        double item2=Double.parseDouble(nPl.getText());
        double item3=Double.parseDouble(nB.getText());
        double item4=Double.parseDouble(nY.getText());
        double item5=Double.parseDouble(nC.getText());
        double item6=Double.parseDouble(nM.getText());
            try
            {
                
                String sql1="select * from raw_material where itemCode='PA01' ";
                pst=con.prepareStatement(sql1);
                rs=pst.executeQuery();
                int paper=0;
                while(rs.next())
                {
                    paper=Integer.parseInt(rs.getString("amount"));
                    paper= paper-item1;

                    String sqlq="update raw_material set amount='"+paper+"' where itemCode='PA01'";
                    pst=con.prepareStatement(sqlq);
                    pst.execute();
                }

                String sql2="select * from raw_material where itemCode='PL01' ";
                pst=con.prepareStatement(sql2);
                rs=pst.executeQuery();
                double plate=0;
                while(rs.next())
                {
                    plate=Double.parseDouble(rs.getString("amount"));
                    plate = plate-item2;

                    String sqlq="update raw_material set amount='"+plate+"' where itemCode='PL01'";
                    pst=con.prepareStatement(sqlq);
                    pst.execute();
                }

                String sql3="select * from raw_material where itemCode='IB01' ";
                pst=con.prepareStatement(sql3);
                rs=pst.executeQuery();
                double black=0;
                while(rs.next())
                {
                    black=Double.parseDouble(rs.getString("amount"));
                    black = black-item3;

                    String sqlq="update raw_material set amount='"+black+"' where itemCode='IB01'";
                    pst=con.prepareStatement(sqlq);
                    pst.execute();
                }

                String sql4="select * from raw_material where itemCode='IC01' ";
                pst=con.prepareStatement(sql4);
                rs=pst.executeQuery();
                double cyan=0;
                while(rs.next())
                {
                    cyan=Double.parseDouble(rs.getString("amount"));
                    cyan=cyan-item5;

                    String sqlq="update raw_material set amount='"+cyan+"' where itemCode='IC01'";
                    pst=con.prepareStatement(sqlq);
                    pst.execute();
                }

                String sql5="select * from raw_material where itemCode='IM01' ";
                pst=con.prepareStatement(sql5);
                rs=pst.executeQuery();
                double magenta=0;
                while(rs.next())
                {
                    magenta=Double.parseDouble(rs.getString("amount"));
                    magenta = magenta-item6;

                    String sqlq="update raw_material set amount='"+magenta+"' where itemCode='IM01'";
                    pst=con.prepareStatement(sqlq);
                    pst.execute();
                }

                String sql6="select * from raw_material where itemCode='IY01' ";
                pst=con.prepareStatement(sql6);
                rs=pst.executeQuery();
                double yellow=0;
                while(rs.next())
                {
                    yellow=Double.parseDouble(rs.getString("amount"));
                    yellow = yellow-item4;

                    String sqlq="update raw_material set amount='"+yellow+"' where itemCode='IY01'";
                    pst=con.prepareStatement(sqlq);
                    pst.execute();
                }

                JOptionPane.showMessageDialog(this,"Raw Materials have been updated!");
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this,e);
            }
             
            String checkIDLable=id.getText();
            
            if(checkIDLable.equals("Order ID :"))
            {
                try
                {
                        String sql1="insert into used_raw_material(orderID,itemCode,amount)values('"+idr+"','PA01','"+item1+"')";
                        pst=con.prepareStatement(sql1);
                        pst.execute();

                        String sql2="insert into used_raw_material(orderID,itemCode,amount)values('"+idr+"','PL01','"+item2+"')";
                        pst=con.prepareStatement(sql2);
                        pst.execute();

                        String sql3="insert into used_raw_material(orderID,itemCode,amount)values('"+idr+"','IB01','"+item3+"')";
                        pst=con.prepareStatement(sql3);
                        pst.execute();

                        String sql4="insert into used_raw_material(orderID,itemCode,amount)values('"+idr+"','IY01','"+item4+"')";
                        pst=con.prepareStatement(sql4);
                        pst.execute();

                        String sql5="insert into used_raw_material(orderID,itemCode,amount)values('"+idr+"','IC01','"+item5+"')";
                        pst=con.prepareStatement(sql5);
                        pst.execute();

                        String sql6="insert into used_raw_material(orderID,itemCode,amount)values('"+idr+"','IM01','"+item6+"')";
                        pst=con.prepareStatement(sql6);
                        pst.execute();
                        
                        UsedRawMaterialTable();
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(this,"An error With the insert SQL Query!");
                }
            }
            else if(checkIDLable.equals("Tender ID : "))
            {
                try
                {
                        String sql1="insert into tender_used_rawmaterial(tenderID,itemCode,amount)values('"+idr+"','PA01','"+item1+"')";
                        pst=con.prepareStatement(sql1);
                        pst.execute();

                        String sql2="insert into tender_used_rawmaterial(tenderID,itemCode,amount)values('"+idr+"','PL01','"+item2+"')";
                        pst=con.prepareStatement(sql2);
                        pst.execute();

                        String sql3="insert into tender_used_rawmaterial(tenderID,itemCode,amount)values('"+idr+"','IB01','"+item3+"')";
                        pst=con.prepareStatement(sql3);
                        pst.execute();

                        String sql4="insert into tender_used_rawmaterial(tenderID,itemCode,amount)values('"+idr+"','IY01','"+item4+"')";
                        pst=con.prepareStatement(sql4);
                        pst.execute();

                        String sql5="insert into tender_used_rawmaterial(tenderID,itemCode,amount)values('"+idr+"','IC01','"+item5+"')";
                        pst=con.prepareStatement(sql5);
                        pst.execute();

                        String sql6="insert into tender_used_rawmaterial(tenderID,itemCode,amount)values('"+idr+"','IM01','"+item6+"')";
                        pst=con.prepareStatement(sql6);
                        pst.execute();
                        
                        UsedTenderRawMaterialTable();
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(this,e);
                }
            }
            else if(checkIDLable.equals("Seminar ID : "))
            {
                try
                {
                        String sql1="insert into seminar_used_rawmaterial(seminarID,itemCode,amount)values('"+idr+"','PA01','"+item1+"')";
                        pst=con.prepareStatement(sql1);
                        pst.execute();

                        String sql2="insert into seminar_used_rawmaterial(seminarID,itemCode,amount)values('"+idr+"','PL01','"+item2+"')";
                        pst=con.prepareStatement(sql2);
                        pst.execute();

                        String sql3="insert into seminar_used_rawmaterial(seminarID,itemCode,amount)values('"+idr+"','IB01','"+item3+"')";
                        pst=con.prepareStatement(sql3);
                        pst.execute();

                        String sql4="insert into seminar_used_rawmaterial(seminarID,itemCode,amount)values('"+idr+"','IY01','"+item4+"')";
                        pst=con.prepareStatement(sql4);
                        pst.execute();

                        String sql5="insert into seminar_used_rawmaterial(seminarID,itemCode,amount)values('"+idr+"','IC01','"+item5+"')";
                        pst=con.prepareStatement(sql5);
                        pst.execute();

                        String sql6="insert into seminar_used_rawmaterial(seminarID,itemCode,amount)values('"+idr+"','IM01','"+item6+"')";
                        pst=con.prepareStatement(sql6);
                        pst.execute();
                        
                        UsedSeminarRawMaterialTable();
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(this,"An error With the insert SQL Query!");
                }
            }

            nPa.setText("");
            nPl.setText("");
            nB.setText("");
            nC.setText("");
            nM.setText("");
            nY.setText("");
            

            
        }
       
    }//GEN-LAST:event_updateRawActionPerformed

    private void nPaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nPaKeyReleased
       /* try 
        {
            char c=evt.getKeyChar();
            if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACKSPACE) || (c==KeyEvent.VK_DELETE)))
            { 
                evt.consume();
                nPa.setBackground(Color.PINK);
                papers=true;
            }
           else
           {
               nPa.setBackground(Color.WHITE);
               papers=false;
           } 
        } 
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }//GEN-LAST:event_nPaKeyReleased

    private void nPlKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nPlKeyReleased
      /*  try 
        {
            char c=evt.getKeyChar();
            if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACKSPACE) || (c==KeyEvent.VK_DELETE)))
            { 
                evt.consume();
                nPl.setBackground(Color.PINK);
                plates=true;
            }
           else
           {
               nPl.setBackground(Color.WHITE);
               plates=false;
           } 
        } 
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }//GEN-LAST:event_nPlKeyReleased

    private void nBKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nBKeyReleased
       /* try 
        {
            char c=evt.getKeyChar();
            if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACKSPACE) || (c==KeyEvent.VK_DELETE)))
            { 
                evt.consume();
                nB.setBackground(Color.PINK);
                inkB=true;
            }
           else
           {
               nB.setBackground(Color.WHITE);
               inkB=false;
           } 
        } 
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }//GEN-LAST:event_nBKeyReleased

    private void backRawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backRawActionPerformed
        String checkIDLable=id.getText();
            
        if(checkIDLable.equals("Order ID :"))
        {
            rightPanel.removeAll();
            rightPanel.repaint();
            rightPanel.revalidate();
            //adding panels

            HandleRegOrders.setVisible(true);
            HandleSpecialOrders.setVisible(false);
            WorkRate.setVisible(false);
            CheckWastage.setVisible(false);


            rightPanel.add(HandleRegOrders);
            rightPanel.repaint();
            rightPanel.revalidate();
        }
        else if((checkIDLable.equals("Tender ID : "))||(checkIDLable.equals("Seminar ID : ")))
        {
            rightPanel.removeAll();
            rightPanel.repaint();
            rightPanel.revalidate();
            //adding panels

            HandleRegOrders.setVisible(false);
            HandleSpecialOrders.setVisible(true);
            WorkRate.setVisible(false);
            CheckWastage.setVisible(false);

            seminarOdertable();
            TenderTable();

            rightPanel.add(HandleSpecialOrders);
            rightPanel.repaint();
            rightPanel.revalidate();
        }
    }//GEN-LAST:event_backRawActionPerformed

    private void delActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delActionPerformed
        int raw = regOrderTable.getSelectedRow();
        
        if(raw == -1)
        {
            JOptionPane.showMessageDialog(this,"Select an Order First");  
        }
        else
        {
            try{
                int x=JOptionPane.showConfirmDialog(this, "Are You Sure?");
                
                if(x==0)
                {
                    String delID = regOrderTable.getValueAt(raw,0).toString();
                    String delStatus=regOrderTable.getValueAt(raw,6).toString();
                    
                    if(delStatus.equals("not complete"))
                    {
                        String sql="delete from orders where orderID='"+delID+"'";
                        pst=con.prepareStatement(sql);
                        pst.execute();
                        JOptionPane.showMessageDialog(this,"Successfully Deleted!");

                        Toid.setText("");
                        Tpname.setText("");
                        Tqnty.setText("");
                        TdueDate.setText("");

                        tableLoad();
                    }
                    else if(delStatus.equals("processing"))
                    {
                        JOptionPane.showMessageDialog(this,"Cannot delete! Order is on processing.");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this,"Cannot delete! Order is already finished.");
                    }
                }
            }
            catch(Exception e)
            {
               // JOptionPane.showMessageDialog(this,e);
            }
        }
    }//GEN-LAST:event_delActionPerformed

    private void qtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtKeyReleased
     /*   try 
        {
            char c=evt.getKeyChar();
            if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACKSPACE) || (c==KeyEvent.VK_DELETE)))
            { 
                evt.consume();
                qt.setBackground(Color.PINK);
            }
           else
           {
               qt.setBackground(Color.WHITE);
           } 
    
        } 
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }//GEN-LAST:event_qtKeyReleased

    private void nPlKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nPlKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_nPlKeyTyped

    private void nCKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nCKeyReleased
       /* try 
        {
            char c=evt.getKeyChar();
            if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACKSPACE) || (c==KeyEvent.VK_DELETE)))
            { 
                evt.consume();
                nC.setBackground(Color.PINK);
                inkC=true;
            }
           else
           {
               nC.setBackground(Color.WHITE);
               inkC=false;
           } 
    
        } 
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }//GEN-LAST:event_nCKeyReleased

    private void nMKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nMKeyReleased
       /* try 
        {
            char c=evt.getKeyChar();
            if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACKSPACE) || (c==KeyEvent.VK_DELETE)))
            { 
                evt.consume();
                nM.setBackground(Color.PINK);
                inkM=true;
            }
           else
           {
               nM.setBackground(Color.WHITE);
               inkM=false;
           } 
    
        } 
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }//GEN-LAST:event_nMKeyReleased

    private void nYKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nYKeyReleased
       /* try 
        {
            char c=evt.getKeyChar();
            if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACKSPACE) || (c==KeyEvent.VK_DELETE)))
            { 
                evt.consume();
                nY.setBackground(Color.PINK);
                inkY=true;
            }
           else
           {
               nY.setBackground(Color.WHITE);
               inkY=false;
           } 
    
        } 
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }//GEN-LAST:event_nYKeyReleased

    private void nPaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nPaMouseClicked
        nPa.setBackground(Color.WHITE);
    }//GEN-LAST:event_nPaMouseClicked

    private void nPlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nPlMouseClicked
        nPl.setBackground(Color.WHITE);
    }//GEN-LAST:event_nPlMouseClicked

    private void nBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nBMouseClicked
        nB.setBackground(Color.WHITE);
    }//GEN-LAST:event_nBMouseClicked

    private void nCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nCMouseClicked
       nC.setBackground(Color.WHITE);
    }//GEN-LAST:event_nCMouseClicked

    private void nMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nMMouseClicked
        nM.setBackground(Color.WHITE);
    }//GEN-LAST:event_nMMouseClicked

    private void nYMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nYMouseClicked
        nY.setBackground(Color.WHITE);
    }//GEN-LAST:event_nYMouseClicked

    private void cMonthlyWabtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cMonthlyWabtnActionPerformed
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        String d=dateFormat.format(date);
        DateFormat dateFormat1 = new SimpleDateFormat("MMMM");
        Date date1 = new Date();
        String d1=dateFormat1.format(date1);
        
        wastageDBAccess wS=new wastageDBAccess();
        try{
            ResultSet R1=wS.getMonthlyWastage(d);
            ResultSet R2=wS.getMonthlyWastage(d);
            int count=0;
            while(R1.next())
            {
                count++;
            }
            if(count == 0)
            {
                JOptionPane.showMessageDialog(this,"No wasted items are found for this month!");
            }
            wastageTable.setModel(DbUtils.resultSetToTableModel(R2));
            wastageTableHeading.setText("Wastage for "+d1+" : ");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,e);
        }
        

    }//GEN-LAST:event_cMonthlyWabtnActionPerformed

    private void tenderCatogoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenderCatogoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tenderCatogoryActionPerformed

    private void tenderSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tenderSearchMouseClicked
        tenderSearch.setText("");
    }//GEN-LAST:event_tenderSearchMouseClicked

    private void tenderSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenderSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tenderSearchActionPerformed

    private void tenderSearchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenderSearchbtnActionPerformed
        specialOrderGetSet tGS = new specialOrderGetSet();
        specialOrderDBAccess tDB= new specialOrderDBAccess();
        
        tGS.setTenCombo(tenderCatogory.getSelectedItem().toString());
        tGS.setTenSearch(tenderSearch.getText());
        
        switch(tGS.getTenCombo())
        {
            case "Select a Category":
                JOptionPane.showMessageDialog(this,"Select a Category First");
                break;
            case "Tender ID":
                switch(tGS.getTenSearch())
                {
                    case "":
                    case "Search":
                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
                       break; 
                    default:
                        try
                        {
                            ResultSet R1=tDB.TsearchOrderID(tGS.getTenSearch());
                            ResultSet R2=tDB.TsearchOrderID(tGS.getTenSearch());
                            int count=0;
                            while(R1.next())
                            {
                                count++;       
                            }
                            if(count == 0)
                            {
                                tenderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
                            }
                            else
                            {
                                tenderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,count+" Results Found");
                            }
                            tenderSearch.setText("Search");
                        }
                        catch(Exception e){JOptionPane.showMessageDialog(this,"Error!");}
                        break;
                }
                break;
            case "Order Due Date":
                switch(tGS.getTenSearch())
                {
                    case "":
                    case "Search":
                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
                       break; 
                    default:
                        try{
                            ResultSet R1=tDB.TsearchOrderDate(tGS.getTenSearch());
                            ResultSet R2=tDB.TsearchOrderDate(tGS.getTenSearch());
                            int count=0;
                            while(R1.next())
                            {
                                count++;       
                            }
                            if(count == 0)
                            {
                                tenderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
                            }
                            else
                            {
                                tenderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,count+" Results Found");
                            }
                            
                            tenderSearch.setText("Search");
                            
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
                        }
                        break;
                }
                break;
            case "Status":
                switch(tGS.getTenSearch())
                {
                    case "":
                    case "Search":
                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
                       break; 
                    default:
                        try{
                            ResultSet R1=tDB.TsearchStatus(tGS.getTenSearch());
                            ResultSet R2=tDB.TsearchStatus(tGS.getTenSearch());
                            int count=0;
                            while(R1.next())
                            {
                                count++;       
                            }
                            if(count == 0)
                            {
                                tenderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
                            }
                            else
                            {
                                tenderTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,count+" Results Found");
                            }
                            tenderSearch.setText("Search");
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
                        }
                        break;
                }
                break;
        }
    }//GEN-LAST:event_tenderSearchbtnActionPerformed

    private void seminarCatogoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seminarCatogoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seminarCatogoryActionPerformed

    private void searchSeminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchSeminarMouseClicked
        searchSeminar.setText("");
    }//GEN-LAST:event_searchSeminarMouseClicked

    private void searchSeminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchSeminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchSeminarActionPerformed

    private void seminarSearchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seminarSearchbtnActionPerformed
        
        specialOrderGetSet sGS = new specialOrderGetSet();
        specialOrderDBAccess sDB= new specialOrderDBAccess();
        
        sGS.setSemCombo(seminarCatogory.getSelectedItem().toString());
        sGS.setSemSearch(searchSeminar.getText());
        
        switch(sGS.getSemCombo())
        {
            case "Select a Category":
                JOptionPane.showMessageDialog(this,"Select a Category First");
                break;
            case "Seminar ID":
                switch(sGS.getSemSearch())
                {
                    case "":
                    case "Search":
                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
                       break; 
                    default:
                        try
                        {
                            ResultSet R1=sDB.searchOrderID(sGS.getSemSearch());
                            ResultSet R2=sDB.searchOrderID(sGS.getSemSearch());
                            int count=0;
                            while(R1.next())
                            {
                                count++;       
                            }
                            if(count == 0)
                            {
                                seminarTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
                            }
                            else
                            {
                                seminarTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,count+" Results Found");
                            }
                            searchSeminar.setText("Search");
                        }
                        catch(Exception e){JOptionPane.showMessageDialog(this,"Error!");}
                        break;
                }
                break;
            case "Order Due Date":
                switch(sGS.getSemSearch())
                {
                    case "":
                    case "Search":
                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
                       break; 
                    default:
                        try{
                            ResultSet R1=sDB.searchOrderDate(sGS.getSemSearch());
                            ResultSet R2=sDB.searchOrderDate(sGS.getSemSearch());
                            int count=0;
                            while(R1.next())
                            {
                                count++;       
                            }
                            if(count == 0)
                            {
                                seminarTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
                            }
                            else
                            {
                                seminarTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,count+" Results Found");
                            }
                            
                            searchSeminar.setText("Search");
                            
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
                        }
                        break;
                }
                break;
            case "Status":
                switch(sGS.getSemSearch())
                {
                    case "":
                    case "Search":
                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
                       break; 
                    default:
                        try{
                            ResultSet R1=sDB.searchStatus(sGS.getSemSearch());
                            ResultSet R2=sDB.searchStatus(sGS.getSemSearch());
                            int count=0;
                            while(R1.next())
                            {
                                count++;       
                            }
                            if(count == 0)
                            {
                                seminarTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
                            }
                            else
                            {
                                seminarTable.setModel(DbUtils.resultSetToTableModel(R2));
                                JOptionPane.showMessageDialog(this,count+" Results Found");
                            }
                            searchSeminar.setText("Search");
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
                        }
                        break;
                }
                break;
        }
    }//GEN-LAST:event_seminarSearchbtnActionPerformed

    private void tenderPrintbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenderPrintbtnActionPerformed
       try{
            int Traw = tenderTable.getSelectedRow();
            specialOrderDBAccess sDB = new specialOrderDBAccess();
            
            if(Traw == -1)
            {
                JOptionPane.showMessageDialog(this,"Select an Order First");  
            }
            else
            {
                String tstatus =tenderTable.getValueAt(Traw,4).toString();

                if(tstatus.equals("processing"))
                {
                    JOptionPane.showMessageDialog(this,"Order is already in printing division");
                }
                else if(tstatus.equals("complete"))
                {
                    JOptionPane.showMessageDialog(this,"Order has been completed");
                }
                else
                {
                    boolean checkStatus=sDB.updatePrintinTender(tenderTable.getValueAt(Traw,0).toString());
                    if(checkStatus==true)
                        JOptionPane.showMessageDialog(this,"Successfully forwared to printing division");
                    else
                        JOptionPane.showMessageDialog(this,"Sending Failed!");
                    
                    TenderTable();
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
        }
    }//GEN-LAST:event_tenderPrintbtnActionPerformed

    private void seminarPrintbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seminarPrintbtnActionPerformed
        try{
            int Sraw = seminarTable.getSelectedRow();
            specialOrderDBAccess sDB = new specialOrderDBAccess();
            
            if(Sraw == -1)
            {
                JOptionPane.showMessageDialog(this,"Select an Order First");  
            }
            else
            {
                String sstatus =seminarTable.getValueAt(Sraw,4).toString();
                
                if(sstatus.equals("processing"))
                {
                    JOptionPane.showMessageDialog(this,"Order is already in printing division");
                }
                else if(sstatus.equals("complete"))
                {
                    JOptionPane.showMessageDialog(this,"Order has been completed");
                }
                else
                {
                    boolean checkStatus=sDB.updatePrintinSeminar(seminarTable.getValueAt(Sraw,0).toString());
                    if(checkStatus==true)
                        JOptionPane.showMessageDialog(this,"Successfully forwared to printing division");
                    else
                        JOptionPane.showMessageDialog(this,"Sending Failed!");
                    
                    seminarOdertable();
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
        }
    }//GEN-LAST:event_seminarPrintbtnActionPerformed

    private void seminarTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seminarTableMouseClicked
        int rawSem = seminarTable.getSelectedRow();

        sOrderID.setText(seminarTable.getValueAt(rawSem,0).toString());
        sDocumentPath.setText(seminarTable.getValueAt(rawSem,1).toString());
        sAmount.setText(seminarTable.getValueAt(rawSem,2).toString());
        sDueDate.setText(seminarTable.getValueAt(rawSem,3).toString());
            
    }//GEN-LAST:event_seminarTableMouseClicked

    private void tenderSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tenderSearchFocusLost
       // tenderSearch.setText("Search");
    }//GEN-LAST:event_tenderSearchFocusLost

    private void searchSeminarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchSeminarFocusLost
        //searchSeminar.setText("Search");
    }//GEN-LAST:event_searchSeminarFocusLost

    private void searchTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchTFocusLost
        //searchT.setText("Search");
    }//GEN-LAST:event_searchTFocusLost

    private void searchT1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchT1FocusLost
        //searchT1.setText("Search");
    }//GEN-LAST:event_searchT1FocusLost

    private void tenderTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tenderTableMouseClicked
        int rawTen = tenderTable.getSelectedRow();
        String docID=tenderTable.getValueAt(rawTen,1).toString();
        
        try
        {
            String sql="select document from draft where draftID='"+docID+"' ";
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            
            while(rs.next())
            {
                String docPath=rs.getString("document");
                tDocumentPath.setText(docPath);
            }
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
        }

        tOrderID.setText(tenderTable.getValueAt(rawTen,0).toString());
        tAmount.setText(tenderTable.getValueAt(rawTen,2).toString());
        tDueDate.setText(tenderTable.getValueAt(rawTen,3).toString());
    }//GEN-LAST:event_tenderTableMouseClicked

    private void tenderAllbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenderAllbtnActionPerformed
        TenderTable();
    }//GEN-LAST:event_tenderAllbtnActionPerformed

    private void seminarAllbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seminarAllbtnActionPerformed
        seminarOdertable();
    }//GEN-LAST:event_seminarAllbtnActionPerformed

    private void tenderDisbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenderDisbtnActionPerformed
        int raw = tenderTable.getSelectedRow();
        
        if(raw == -1)
        {
            JOptionPane.showMessageDialog(this,"Select an Order First");  
        }
        else
        {
            String printStatus =tenderTable.getValueAt(raw,4).toString();

            if(printStatus.equals("processing"))
            {
                specialOrderDBAccess T1=new specialOrderDBAccess();
                try
                {
                    boolean checkDT=T1.updatedistributeTender(tenderTable.getValueAt(raw,0).toString());
                    if(checkDT==true)
                        JOptionPane.showMessageDialog(this,"Successfully sent to Distribution");
                    else
                        JOptionPane.showMessageDialog(this,"Sending Failed!");
                    TenderTable();
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
                }
                    
                    UsedTenderRawMaterialTable();
                    id.setText("Tender ID : ");
                    PName.setText("Document Path : ");
                    roid.setText(tenderTable.getValueAt(raw,0).toString());
                    rproNo.setText(tenderTable.getValueAt(raw,1).toString());
                    
                    rightPanel.removeAll();
                    rightPanel.repaint();
                    rightPanel.revalidate();
                    //adding panels
                    RawMaterials.setVisible(true);
                    
                    rightPanel.add(RawMaterials);
                    rightPanel.repaint();
                    rightPanel.revalidate();
            }
            else if(printStatus.equals("complete"))
            {
                JOptionPane.showMessageDialog(this,"Product already has been sent to Distribution");
            }
            else if(printStatus.equals("not complete"))
            {
                JOptionPane.showMessageDialog(this,"Product should be sent to printing division first");
            }
        }
    }//GEN-LAST:event_tenderDisbtnActionPerformed

    private void seminarDisbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seminarDisbtnActionPerformed
        int rawS = seminarTable.getSelectedRow();
        
        if(rawS == -1)
        {
            JOptionPane.showMessageDialog(this,"Select an Order First");  
        }
        else
        {
            String printStatus =seminarTable.getValueAt(rawS,4).toString();

            if(printStatus.equals("processing"))
            {
                specialOrderDBAccess S1=new specialOrderDBAccess();
                try
                {
                    boolean checkDT=S1.updatedistributeSeminar(tenderTable.getValueAt(rawS,0).toString());
                    if(checkDT==true)
                        JOptionPane.showMessageDialog(this,"Successfully sent to Distribution");
                    else
                        JOptionPane.showMessageDialog(this,"Sending Failed!");
                    TenderTable();
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
                }
                    
                    UsedSeminarRawMaterialTable();
                    id.setText("Seminar ID : ");
                    PName.setText("Document Path : ");
                    roid.setText(seminarTable.getValueAt(rawS,0).toString());
                    rproNo.setText(seminarTable.getValueAt(rawS,1).toString());
                    
                    rightPanel.removeAll();
                    rightPanel.repaint();
                    rightPanel.revalidate();
                    //adding panels
                    RawMaterials.setVisible(true);
                    
                    rightPanel.add(RawMaterials);
                    rightPanel.repaint();
                    rightPanel.revalidate();
            }
            else if(printStatus.equals("complete"))
            {
                JOptionPane.showMessageDialog(this,"Product already has been sent to Distribution");
            }
            else if(printStatus.equals("not complete"))
            {
                JOptionPane.showMessageDialog(this,"Product should be sent to printing division first");
            }
        }
    }//GEN-LAST:event_seminarDisbtnActionPerformed

    private void workRateTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_workRateTableMouseClicked
        String tableLable=tableHeading.getText();
        int raw=workRateTable.getSelectedRow();
        if(tableLable.equals("Folding Rates :"))
        {
            wID.setText(workRateTable.getValueAt(raw,2).toString());
            wName.setText(workRateTable.getValueAt(raw,1).toString());
            wPiece.setText(workRateTable.getValueAt(raw,3).toString()); 
        }
    }//GEN-LAST:event_workRateTableMouseClicked

    private void wPieceMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wPieceMouseReleased
//        try 
//        {
//            char c=evt.getKeyChar();
//            if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACKSPACE) || (c==KeyEvent.VK_DELETE)))
//            { 
//                evt.consume();
//                wPiece.setBackground(Color.PINK);
//            }
//           else
//           {
//               wPiece.setBackground(Color.WHITE);
//           } 
//    
//        } 
//        catch (Exception e) {
//            e.printStackTrace();
//        }
    }//GEN-LAST:event_wPieceMouseReleased

    private void nxtWkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nxtWkActionPerformed
        workRateDBAccess wD=new workRateDBAccess();
        int k=workRateTable.getRowCount();
        System.out.println(k);
        String amt=wPiece.getText();
        
        if(amt.equals(""))
        {
            JOptionPane.showMessageDialog(this,"Enter the Number of Pieces!");
        }
        else
        {
            try{
                if(nextCount >= workRateTable.getRowCount())
                {
                    JOptionPane.showMessageDialog(this,"No more Employees to Insert");
                }
                else
                {
                    boolean check=wD.insertFoldingRate(workRateTable.getValueAt(nextCount,0).toString(),amt);
                    if(check==true)
                    {
                        workRateTable.setValueAt("Complete", nextCount, 2);
                        nextCount++;
                        wID.setText(workRateTable.getValueAt(nextCount,0).toString());
                        wName.setText(workRateTable.getValueAt(nextCount,1).toString());
                        wPiece.setText("");
                    }
                    else
                        JOptionPane.showMessageDialog(this,"Inserting Failed!");
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this,"No more Employees to Insert");
                wID.setText("ID");
                wName.setText("Name");
                wPiece.setText("");
            }
        }
        
  
    }//GEN-LAST:event_nxtWkActionPerformed

    private void viewPiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewPiceActionPerformed
        try
        {
            String sql="select folding_rate.empID AS 'Employee ID',employee.name AS 'Employee Name', folding_rate.date AS 'Date', folding_rate.noOfPieces AS 'No of Pieces' from folding_rate,employee where employee.empID=folding_rate.empID ";
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            
            workRateTable.setModel(DbUtils.resultSetToTableModel(rs));
            tableHeading.setText("Folding Rates :");
            nextCount=0;
            firstField.setText("Date :");
            if(workRateTable.getRowCount()!=0)
            {
                wID.setText(workRateTable.getValueAt(nextCount,2).toString());
                wName.setText(workRateTable.getValueAt(nextCount,1).toString());
                wPiece.setText(workRateTable.getValueAt(nextCount,3).toString());
            }
            else
            {
                wID.setText("Date");
                wName.setText("Name");
                wPiece.setText("");
            }
            
        }
       catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, e);
        }
        updatePiceRate.setEnabled(true);
        nxtWk.setEnabled(false);
        
    }//GEN-LAST:event_viewPiceActionPerformed

    private void viewAttendanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewAttendanceActionPerformed
        nextCount=0;
        attendanceTable();
        tableHeading.setText("Today's Attendance :");
        updatePiceRate.setEnabled(false);
        wID.setText(workRateTable.getValueAt(nextCount,0).toString());
        wName.setText(workRateTable.getValueAt(nextCount,1).toString());
        wPiece.setText("");
        nxtWk.setEnabled(true);
    }//GEN-LAST:event_viewAttendanceActionPerformed

    private void updatePiceRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatePiceRateActionPerformed
        int raw=workRateTable.getSelectedRow();
        String amt=wPiece.getText();
        if(raw == -1)
        {
            JOptionPane.showMessageDialog(this,"Select a record First");  
        }
        else
        {
            String id=workRateTable.getValueAt(raw,0).toString();
            String date=wID.getText();
            
            workRateDBAccess wE=new workRateDBAccess();
            try{
                boolean check=wE.updateFoldingRate(id,amt,date);
                if(check==true)
                {
                    JOptionPane.showMessageDialog(this, "Successfully Updated.");
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Updation Failed!");
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this, e);
            }
            
            try
            {
                String sql="select folding_rate.empID AS 'Employee ID',employee.name AS 'Employee Name', folding_rate.date AS 'Date', folding_rate.noOfPieces AS 'No of Pieces' from folding_rate,employee where employee.empID=folding_rate.empID ";
                pst=con.prepareStatement(sql);
                rs=pst.executeQuery();

                workRateTable.setModel(DbUtils.resultSetToTableModel(rs));
                tableHeading.setText("Folding Rates :");
                nextCount=0;
                firstField.setText("Date :");
                wID.setText(workRateTable.getValueAt(nextCount,2).toString());
                wName.setText(workRateTable.getValueAt(nextCount,1).toString());
                wPiece.setText(workRateTable.getValueAt(nextCount,3).toString());
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this, e);
            }
        }
    }//GEN-LAST:event_updatePiceRateActionPerformed

    private void cUpdateWaTabtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cUpdateWaTabtnActionPerformed
           wastageDBAccess wA=new wastageDBAccess();
           try
           {
                ResultSet R1=wA.getFreeOrderID();
                
                    while(R1.next())
                    {
                        String oID=R1.getString("orderID");
                        System.out.println(oID);

                        String MUA=wA.getUsedAmount(oID, "IM01");
                        String MAA=wA.getAssignAmount(oID, "IM01");
                        System.out.println(MUA);
                        System.out.println(MAA);
                        if((Double.parseDouble(MUA))>=Double.parseDouble(MAA))
                        {
                            double WM=Double.parseDouble(MUA)-Double.parseDouble(MAA);
                            boolean check1=wA.updateWastate(oID,"IM01",WM);
                        }

                        String CUA=wA.getUsedAmount(oID, "IC01");
                        String CAA=wA.getAssignAmount(oID, "IC01");
                        if((Double.parseDouble(CUA))>=Double.parseDouble(CAA))
                        {
                            double WC=Double.parseDouble(CUA)-Double.parseDouble(CAA);
                            boolean check2=wA.updateWastate(oID,"IC01",WC);
                        }

                        String BUA=wA.getUsedAmount(oID, "IB01");
                        String BAA=wA.getAssignAmount(oID, "IB01");
                        if((Double.parseDouble(BUA))>=Double.parseDouble(BAA))
                        {
                            double WB=Double.parseDouble(BUA)-Double.parseDouble(BAA);
                            boolean check3=wA.updateWastate(oID,"IB01",WB);
                        }

                        String YUA=wA.getUsedAmount(oID, "IY01");
                        String YAA=wA.getAssignAmount(oID, "IY01");
                        if((Double.parseDouble(YUA))>=Double.parseDouble(YAA))
                        {
                            double WY=Double.parseDouble(YUA)-Double.parseDouble(YAA);
                            boolean check4=wA.updateWastate(oID,"IY01",WY);
                        }

                        String PAUA=wA.getUsedAmount(oID, "PA01");
                        String PAAA=wA.getAssignAmount(oID, "PA01");
                        if((Double.parseDouble(PAUA))>=Double.parseDouble(PAAA))
                        {
                            double WPA=Double.parseDouble(PAUA)-Double.parseDouble(PAAA);
                            boolean check5=wA.updateWastate(oID,"PA01",WPA);
                        }

                        String PLUA=wA.getUsedAmount(oID, "PL01");
                        String PLAA=wA.getAssignAmount(oID, "PL01");
                        if((Double.parseDouble(PLUA))>=Double.parseDouble(PLAA))
                        {
                            double WPL=Double.parseDouble(PLUA)-Double.parseDouble(PLAA);
                            boolean check6=wA.updateWastate(oID,"PL01",WPL);
                        }
                        JOptionPane.showMessageDialog(this,"Successfully updated."); 
                    }
                
                wastageTable();
                wastageTableHeading.setText("Wastage order wise:");
           }
           catch(Exception e)
           {
               JOptionPane.showMessageDialog(this,e);
           }
           
    }//GEN-LAST:event_cUpdateWaTabtnActionPerformed

    private void cReportbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cReportbtnActionPerformed
//        String month=jComboBox1.getSelectedItem().toString();
//        String mon=null;
//        if(month.equals("select a month")){
//            JOptionPane.showMessageDialog(this,"Select a month first");
//        }
//        else if(month.equals("Jan")){
//            mon="01";
//        }
//        else if(month.equals("Feb")){
//            mon="02";
//        }
//        else if(month.equals("Mar")){
//            mon="03";
//        }
//        else if(month.equals("Apr")){
//            mon="04";
//        }
//        else if(month.equals("May")){
//            mon="05";
//        }
//        else if(month.equals("June")){
//            mon="06";
//        }
//        else if(month.equals("July")){
//            mon="07";
//        }
//        else if(month.equals("Aug")){
//            mon="08";
//        }
//        else if(month.equals("Sept")){
//            mon="09";
//        }
//        else if(month.equals("Oct")){
//            mon="10";
//        }
//        else if(month.equals("Nov")){
//            mon="11";
//        }
//        else{
//            mon="12";
//        }
//        
        //HashMap para=new HashMap();
        //para.put("month",mon);
        reportViewer re=new reportViewer("E:\\7\\sumudu\\vidulaka\\src\\Manufacturing\\reportFinal.jasper");
        re.setVisible(true);
        re.toFront();
        re.repaint();
        re.setAlwaysOnTop(true);
    }//GEN-LAST:event_cReportbtnActionPerformed

    private void checkItemcmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkItemcmbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkItemcmbActionPerformed

    private void checkItemcmbItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkItemcmbItemStateChanged
       try{
            wastageDBAccess ws=new wastageDBAccess();

            String itemCode=null;
            String selected=checkItemcmb.getSelectedItem().toString();
            
            if(selected.equals("Paper"))
            {
                itemCode="PA01";
            }
            else if(selected.equals("Ink Yellow"))
            {
                itemCode="IY01";
            }
            else if(selected.equals("Ink Megenta"))
            {
                itemCode="IM01";
            }
            else if(selected.equals("Ink Black"))
            {
                itemCode="IB01";
            }
            else if(selected.equals("Ink Cyan"))
            {
                itemCode="IC01";
            }
            else if(selected.equals("Plate"))
            {
                itemCode="PL01";
            }
            orderIDcombo.removeAllItems();
            ResultSet R1=ws.fillComboOrderID(itemCode);
            while(R1.next())
            {
                String order=R1.getString("orderID");
                orderIDcombo.addItem(order);
            }
       }
       catch(Exception e)
       {
           JOptionPane.showMessageDialog(this,e); 
       }
    }//GEN-LAST:event_checkItemcmbItemStateChanged

    private void cCheckbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cCheckbtnActionPerformed
        try{
            wastageDBAccess ws=new wastageDBAccess();
            String selected=checkItemcmb.getSelectedItem().toString();
            String id=orderIDcombo.getSelectedItem().toString();
            String itemCode=null;
            
            if(selected.equals("Paper"))
            {
                itemCode="PA01";
            }
            else if(selected.equals("Ink Yellow"))
            {
                itemCode="IY01";
            }
            else if(selected.equals("Ink Megenta"))
            {
                itemCode="IM01";
            }
            else if(selected.equals("Ink Black"))
            {
                itemCode="IB01";
            }
            else if(selected.equals("Ink Cyan"))
            {
                itemCode="IC01";
            }
            else if(selected.equals("Plate"))
            {
                itemCode="PL01";
            }
        
            ResultSet R1=ws.checkCustomWastage(id,itemCode);
            wastageTable.setModel(DbUtils.resultSetToTableModel(R1));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,e); 
        }
    }//GEN-LAST:event_cCheckbtnActionPerformed

    private void searchTKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTKeyTyped
//           getDetailsOrders GDO = new getDetailsOrders();
//        
//        GDO.setItem(searchT.getText());
//        GDO.setCato(category.getSelectedItem().toString());
//        
//        switch(GDO.getCato())
//        {
//            case "Select a Category":
//                JOptionPane.showMessageDialog(this,"Select a Category First");
//                break;
//            case "Order ID":
//                switch(GDO.getItem())
//                {
//                    case "Search":
//                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
//                       break; 
//                    default:
//                        try
//                        {
//                            ResultSet R1=ROD.searchOrderID(GDO.getItem());
//                            ResultSet R2=ROD.searchOrderID(GDO.getItem());
//                            int count=0;
//                            while(R1.next())
//                            {
//                                count++;       
//                            }
//                            if(count == 0)
//                            {
//                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
//                            }
//                            else
//                            {
//                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,count+" Results Found");
//                            }
//                            searchT.setText("Search");
//                        }
//                        catch(Exception e){JOptionPane.showMessageDialog(this,"Error!");}
//                        break;
//                }
//                break;
//            case "Order Due Date":
//                switch(GDO.getItem())
//                {
//                    case "Search":
//                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
//                       break; 
//                    default:
//                        try{
//                            ResultSet R1=ROD.searchOrderDate(GDO.getItem());
//                            ResultSet R2=ROD.searchOrderDate(GDO.getItem());
//                            int count=0;
//                            while(R1.next())
//                            {
//                                count++;       
//                            }
//                            if(count == 0)
//                            {
//                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
//                            }
//                            else
//                            {
//                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,count+" Results Found");
//                            }
//                            
//                            searchT.setText("Search");
//                            
//                        }
//                        catch(Exception e)
//                        {
//                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
//                        }
//                        break;
//                }
//                break;
//            case "Status":
//                switch(GDO.getItem())
//                {
//                    case "Search":
//                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
//                       break; 
//                    default:
//                        try{
//                            ResultSet R1=ROD.searchStatus(GDO.getItem());
//                            ResultSet R2=ROD.searchStatus(GDO.getItem());
//                            int count=0;
//                            while(R1.next())
//                            {
//                                count++;       
//                            }
//                            if(count == 0)
//                            {
//                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
//                            }
//                            else
//                            {
//                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,count+" Results Found");
//                            }
//                            searchT.setText("Search");
//                        }
//                        catch(Exception e)
//                        {
//                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
//                        }
//                        break;
//                }
//                break;
//            case "Product":
//                switch(GDO.getItem())
//                {
//                    case "Search":
//                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
//                       break; 
//                    default:
//                        try{
//                            ResultSet R1=ROD.searchProduct(GDO.getItem());
//                            ResultSet R2=ROD.searchProduct(GDO.getItem());
//                            int count=0;
//                            while(R1.next())
//                            {
//                                count++;       
//                            }
//                            if(count == 0)
//                            {
//                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
//                            }
//                            else
//                            {
//                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,count+" Results Found");
//                            }
//                            searchT.setText("Search");
//                        }
//                        catch(Exception e)
//                        {
//                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
//                        }
//                        break;
//                }
//                break;
//        }
    }//GEN-LAST:event_searchTKeyTyped

    private void searchT1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchT1KeyTyped
//        getDetailsOrders GDO = new getDetailsOrders(category1.getSelectedItem().toString(),searchT1.getText());
//        RegOrdersDBAccess ROD = new RegOrdersDBAccess();
//
//        switch(GDO.getCato())
//        {
//            case "Select a Category":
//                JOptionPane.showMessageDialog(this,"Select a Category First");
//                break;
//            case "Order ID":
//                switch(GDO.getItem())
//                {
//                    case "Search":
//                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
//                       break; 
//                    default:
//                        try
//                        {
//                            ResultSet R1=ROD.searchOrderIDM(GDO.getItem());
//                            ResultSet R2=ROD.searchOrderIDM(GDO.getItem());
//                            int count=0;
//                            while(R1.next())
//                            {
//                                count++;       
//                            }
//                            if(count == 0)
//                            {
//                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
//                            }
//                            else
//                            {
//                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,count+" Results Found");
//                            }
//                            searchT1.setText("Search");
//                        }
//                        catch(Exception e){JOptionPane.showMessageDialog(this,"Error!");}
//                        break;
//                }
//                break;
//            case "Order Due Date":
//                switch(GDO.getItem())
//                {
//                    case "Search":
//                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
//                       break; 
//                    default:
//                        try{
//                            ResultSet R1=ROD.searchOrderDateM(GDO.getItem());
//                            ResultSet R2=ROD.searchOrderDateM(GDO.getItem());
//                            int count=0;
//                            while(R1.next())
//                            {
//                                count++;       
//                            }
//                            if(count == 0)
//                            {
//                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
//                            }
//                            else
//                            {
//                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,count+" Results Found");
//                            }
//                            
//                            searchT1.setText("Search");
//                            
//                        }
//                        catch(Exception e)
//                        {
//                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
//                        }
//                        break;
//                }
//                break;
//            case "Status":
//                switch(GDO.getItem())
//                {
//                    case "Search":
//                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
//                       break; 
//                    default:
//                        try{
//                            ResultSet R1=ROD.searchStatusM(GDO.getItem());
//                            ResultSet R2=ROD.searchStatusM(GDO.getItem());
//                            int count=0;
//                            while(R1.next())
//                            {
//                                count++;       
//                            }
//                            if(count == 0)
//                            {
//                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
//                            }
//                            else
//                            {
//                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,count+" Results Found");
//                            }
//                            searchT1.setText("Search");
//                        }
//                        catch(Exception e)
//                        {
//                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
//                        }
//                        break;
//                }
//                break;
//            case "Product":
//                switch(GDO.getItem())
//                {
//                    case "Search":
//                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
//                       break; 
//                    default:
//                        try{
//                            ResultSet R1=ROD.searchProductM(GDO.getItem());
//                            ResultSet R2=ROD.searchProductM(GDO.getItem());
//                            int count=0;
//                            while(R1.next())
//                            {
//                                count++;       
//                            }
//                            if(count == 0)
//                            {
//                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
//                            }
//                            else
//                            {
//                                regOrderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,count+" Results Found");
//                            }
//                            searchT1.setText("Search");
//                        }
//                        catch(Exception e)
//                        {
//                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
//                        }
//                        break;
//                }
//                break;
//        }
    }//GEN-LAST:event_searchT1KeyTyped

    private void tenderSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tenderSearchKeyTyped
//        specialOrderGetSet tGS = new specialOrderGetSet();
//        specialOrderDBAccess tDB= new specialOrderDBAccess();
//        
//        tGS.setTenCombo(tenderCatogory.getSelectedItem().toString());
//        tGS.setTenSearch(tenderSearch.getText());
//        
//        switch(tGS.getTenCombo())
//        {
//            case "Select a Category":
//                JOptionPane.showMessageDialog(this,"Select a Category First");
//                break;
//            case "Tender ID":
//                switch(tGS.getTenSearch())
//                {
//                    case "Search":
//                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
//                       break; 
//                    default:
//                        try
//                        {
//                            ResultSet R1=tDB.TsearchOrderID(tGS.getTenSearch());
//                            ResultSet R2=tDB.TsearchOrderID(tGS.getTenSearch());
//                            int count=0;
//                            while(R1.next())
//                            {
//                                count++;       
//                            }
//                            if(count == 0)
//                            {
//                                tenderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
//                            }
//                            else
//                            {
//                                tenderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,count+" Results Found");
//                            }
//                            tenderSearch.setText("Search");
//                        }
//                        catch(Exception e){JOptionPane.showMessageDialog(this,"Error!");}
//                        break;
//                }
//                break;
//            case "Order Due Date":
//                switch(tGS.getTenSearch())
//                {
//                    case "Search":
//                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
//                       break; 
//                    default:
//                        try{
//                            ResultSet R1=tDB.TsearchOrderDate(tGS.getTenSearch());
//                            ResultSet R2=tDB.TsearchOrderDate(tGS.getTenSearch());
//                            int count=0;
//                            while(R1.next())
//                            {
//                                count++;       
//                            }
//                            if(count == 0)
//                            {
//                                tenderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
//                            }
//                            else
//                            {
//                                tenderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,count+" Results Found");
//                            }
//                            
//                            tenderSearch.setText("Search");
//                            
//                        }
//                        catch(Exception e)
//                        {
//                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
//                        }
//                        break;
//                }
//                break;
//            case "Status":
//                switch(tGS.getTenSearch())
//                {
//                    case "Search":
//                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
//                       break; 
//                    default:
//                        try{
//                            ResultSet R1=tDB.TsearchStatus(tGS.getTenSearch());
//                            ResultSet R2=tDB.TsearchStatus(tGS.getTenSearch());
//                            int count=0;
//                            while(R1.next())
//                            {
//                                count++;       
//                            }
//                            if(count == 0)
//                            {
//                                tenderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
//                            }
//                            else
//                            {
//                                tenderTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,count+" Results Found");
//                            }
//                            tenderSearch.setText("Search");
//                        }
//                        catch(Exception e)
//                        {
//                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
//                        }
//                        break;
//                }
//                break;
//        }
    }//GEN-LAST:event_tenderSearchKeyTyped

    private void searchSeminarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchSeminarKeyTyped
//        specialOrderGetSet sGS = new specialOrderGetSet();
//        specialOrderDBAccess sDB= new specialOrderDBAccess();
//        
//        sGS.setSemCombo(seminarCatogory.getSelectedItem().toString());
//        sGS.setSemSearch(searchSeminar.getText());
//        
//        switch(sGS.getSemCombo())
//        {
//            case "Select a Category":
//                JOptionPane.showMessageDialog(this,"Select a Category First");
//                break;
//            case "Seminar ID":
//                switch(sGS.getSemSearch())
//                {
//                    case "Search":
//                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
//                       break; 
//                    default:
//                        try
//                        {
//                            ResultSet R1=sDB.searchOrderID(sGS.getSemSearch());
//                            ResultSet R2=sDB.searchOrderID(sGS.getSemSearch());
//                            int count=0;
//                            while(R1.next())
//                            {
//                                count++;       
//                            }
//                            if(count == 0)
//                            {
//                                seminarTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
//                            }
//                            else
//                            {
//                                seminarTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,count+" Results Found");
//                            }
//                            searchSeminar.setText("Search");
//                        }
//                        catch(Exception e){JOptionPane.showMessageDialog(this,"Error!");}
//                        break;
//                }
//                break;
//            case "Order Due Date":
//                switch(sGS.getSemSearch())
//                {
//                    case "Search":
//                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
//                       break; 
//                    default:
//                        try{
//                            ResultSet R1=sDB.searchOrderDate(sGS.getSemSearch());
//                            ResultSet R2=sDB.searchOrderDate(sGS.getSemSearch());
//                            int count=0;
//                            while(R1.next())
//                            {
//                                count++;       
//                            }
//                            if(count == 0)
//                            {
//                                seminarTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
//                            }
//                            else
//                            {
//                                seminarTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,count+" Results Found");
//                            }
//                            
//                            searchSeminar.setText("Search");
//                            
//                        }
//                        catch(Exception e)
//                        {
//                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
//                        }
//                        break;
//                }
//                break;
//            case "Status":
//                switch(sGS.getSemSearch())
//                {
//                    case "Search":
//                       JOptionPane.showMessageDialog(this,"Enter a Keyword to Search");
//                       break; 
//                    default:
//                        try{
//                            ResultSet R1=sDB.searchStatus(sGS.getSemSearch());
//                            ResultSet R2=sDB.searchStatus(sGS.getSemSearch());
//                            int count=0;
//                            while(R1.next())
//                            {
//                                count++;       
//                            }
//                            if(count == 0)
//                            {
//                                seminarTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,"No Matching Orders!");
//                            }
//                            else
//                            {
//                                seminarTable.setModel(DbUtils.resultSetToTableModel(R2));
//                                JOptionPane.showMessageDialog(this,count+" Results Found");
//                            }
//                            searchSeminar.setText("Search");
//                        }
//                        catch(Exception e)
//                        {
//                            JOptionPane.showMessageDialog(this,"An error With the SQL Query!");
//                        }
//                        break;
//                }
//                break;
//        }
    }//GEN-LAST:event_searchSeminarKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bsearch;
    private javax.swing.JButton Bsearch1;
    private javax.swing.JPanel CheckWastage;
    private javax.swing.JLabel Cyan;
    private javax.swing.JPanel HandleRegOrders;
    private javax.swing.JPanel HandleSpecialOrders;
    private javax.swing.JTextField MID;
    private javax.swing.JPanel ModelPaper;
    private javax.swing.JPanel NormalOrders;
    private javax.swing.JLabel PName;
    private javax.swing.JLabel PO;
    private javax.swing.JPanel RawBackPanel;
    private javax.swing.JPanel RawMaterials;
    private javax.swing.JTextField TdueDate;
    private javax.swing.JTextField Toid;
    private javax.swing.JTextField Tpname;
    private javax.swing.JTextField Tqnty;
    private javax.swing.JPanel WmainPanel;
    private javax.swing.JPanel WorkRate;
    private javax.swing.JButton add;
    private javax.swing.JButton addOrder;
    private javax.swing.JPanel backPanel;
    private javax.swing.JButton backRaw;
    private javax.swing.JLabel backgroundRaw;
    private javax.swing.JLabel bgReg;
    private javax.swing.JLabel bgSpec;
    private javax.swing.JLabel bgStatus;
    private javax.swing.JLabel bgWork;
    private javax.swing.JLabel bgwaste;
    private javax.swing.JButton bsntPrint;
    private javax.swing.JButton cCheckbtn;
    private javax.swing.JButton cMonthlyWabtn;
    private javax.swing.JPanel cReportPanel;
    private javax.swing.JButton cReportbtn;
    private javax.swing.JLabel cTitle;
    private javax.swing.JButton cUpdateWaTabtn;
    private javax.swing.JPanel cViewPanel;
    private javax.swing.JComboBox category;
    private javax.swing.JComboBox category1;
    private javax.swing.JComboBox checkItemcmb;
    private javax.swing.JButton ckw;
    private javax.swing.JButton del;
    private javax.swing.JLabel dueDate;
    private javax.swing.JLabel firstField;
    private javax.swing.JLabel id;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JTable modelPaperOrders;
    private javax.swing.JTextField nB;
    private javax.swing.JTextField nC;
    private javax.swing.JTextField nM;
    private javax.swing.JTextField nPa;
    private javax.swing.JTextField nPl;
    private javax.swing.JTextField nY;
    private javax.swing.JButton nxtWk;
    private javax.swing.JLabel oid;
    private javax.swing.JComboBox orderIDcombo;
    private javax.swing.JScrollPane orderstable;
    private javax.swing.JLabel pname;
    private javax.swing.JLabel qnty;
    private javax.swing.JTextField qt;
    private javax.swing.JButton regOrder;
    private javax.swing.JTable regOrderTable;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JTextField roid;
    private javax.swing.JTextField rproNo;
    private javax.swing.JLabel rtitle;
    private javax.swing.JTextField sAmount;
    private javax.swing.JTextField sDocumentPath;
    private javax.swing.JTextField sDueDate;
    private javax.swing.JTextField sOrderID;
    private javax.swing.JLabel searchBy;
    private javax.swing.JLabel searchBy1;
    private javax.swing.JLabel searchBy2;
    private javax.swing.JLabel searchBy3;
    private javax.swing.JTextField searchSeminar;
    private javax.swing.JTextField searchT;
    private javax.swing.JTextField searchT1;
    private javax.swing.JButton seminarAllbtn;
    private javax.swing.JComboBox seminarCatogory;
    private javax.swing.JButton seminarDisbtn;
    private javax.swing.JPanel seminarPanel;
    private javax.swing.JButton seminarPrintbtn;
    private javax.swing.JButton seminarSearchbtn;
    private javax.swing.JTable seminarTable;
    private javax.swing.JButton sendToDistribution;
    private javax.swing.JButton sendToPrint;
    private javax.swing.JButton sndtoDis;
    private javax.swing.JButton specialOrder;
    private javax.swing.JLabel status;
    private javax.swing.JTextField tAmount;
    private javax.swing.JTextField tDocumentPath;
    private javax.swing.JTextField tDueDate;
    private javax.swing.JTextField tOrderID;
    private javax.swing.JPanel tPanel;
    private javax.swing.JPanel tPanel1;
    private javax.swing.JLabel tableHeading;
    private javax.swing.JPanel tendePanel;
    private javax.swing.JButton tenderAllbtn;
    private javax.swing.JComboBox tenderCatogory;
    private javax.swing.JButton tenderDisbtn;
    private javax.swing.JButton tenderPrintbtn;
    private javax.swing.JTextField tenderSearch;
    private javax.swing.JButton tenderSearchbtn;
    private javax.swing.JTable tenderTable;
    private javax.swing.JLabel tick;
    private javax.swing.JLabel title;
    private javax.swing.JLabel titleHS;
    private javax.swing.JLabel titleM2;
    private javax.swing.JButton updatePiceRate;
    private javax.swing.JButton updateRaw;
    private javax.swing.JTable usedRawMaterials;
    private javax.swing.JLabel vB;
    private javax.swing.JLabel vC;
    private javax.swing.JLabel vM;
    private javax.swing.JLabel vPa;
    private javax.swing.JLabel vPl;
    private javax.swing.JLabel vY;
    private javax.swing.JLabel vali1;
    private javax.swing.JButton viewAll;
    private javax.swing.JButton viewAttendance;
    private javax.swing.JButton viewBagOrders;
    private javax.swing.JButton viewModel;
    private javax.swing.JButton viewPice;
    private javax.swing.JLabel wDate;
    private javax.swing.JLabel wID;
    private javax.swing.JLabel wName;
    private javax.swing.JPanel wPanel1;
    private javax.swing.JTextField wPiece;
    private javax.swing.JLabel wTitle;
    private javax.swing.JTable wastageTable;
    private javax.swing.JLabel wastageTableHeading;
    private javax.swing.JButton wkrate;
    private javax.swing.JTable workRateTable;
    private javax.swing.JPanel wrPanel;
    // End of variables declaration//GEN-END:variables

   
}
