/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceManagement;

/**
 *
 * @author User
 */
public class yourinitialframe extends javax.swing.JInternalFrame {

    /**
     * Creates new form yourinitialframe
     */
    public yourinitialframe() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setMaximumSize(new java.awt.Dimension(1024, 570));
        setMinimumSize(new java.awt.Dimension(1024, 570));
        setPreferredSize(new java.awt.Dimension(1024, 570));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane2.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane2.setAlignmentX(0.1F);
        jTabbedPane2.setAlignmentY(0.1F);
        jTabbedPane2.setMaximumSize(new java.awt.Dimension(1024, 570));
        jTabbedPane2.setMinimumSize(new java.awt.Dimension(1024, 570));
        jTabbedPane2.setPreferredSize(new java.awt.Dimension(1024, 570));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/board_black_line_texture_background_wood.jpg"))); // NOI18N
        jLabel3.setMaximumSize(new java.awt.Dimension(1024, 550));
        jLabel3.setMinimumSize(new java.awt.Dimension(1024, 550));
        jLabel3.setName(""); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(1024, 550));
        jPanel3.add(jLabel3);

        jTabbedPane2.addTab("tab1", new javax.swing.ImageIcon(getClass().getResource("/Images/Home.png")), jPanel3); // NOI18N

        jPanel5.setAlignmentX(0.1F);
        jPanel5.setAlignmentY(0.1F);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Green background.jpg"))); // NOI18N
        jPanel5.add(jLabel2);

        jTabbedPane2.addTab("tab3", new javax.swing.ImageIcon(getClass().getResource("/Images/Exit.png")), jPanel5); // NOI18N
        jTabbedPane2.addTab("tab2", new javax.swing.ImageIcon(getClass().getResource("/Images/minimize.png")), jPanel4); // NOI18N

        getContentPane().add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/background.jpg"))); // NOI18N
        jLabel1.setMaximumSize(new java.awt.Dimension(1024, 565));
        jLabel1.setMinimumSize(new java.awt.Dimension(1024, 565));
        jLabel1.setPreferredSize(new java.awt.Dimension(1024, 565));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane2;
    // End of variables declaration//GEN-END:variables
}
