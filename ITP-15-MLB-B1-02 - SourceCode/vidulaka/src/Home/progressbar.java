/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sumudu Malshan
 */
public class progressbar extends javax.swing.JFrame {

    /**
     * Creates new form progressbar
     */
    public progressbar() {
        initComponents();
        
        
          new Thread(new Runnable() {

            @Override
            public void run() {
                for(int i=0; i<=100; i++){
                
                    jProgressBar1.setValue(i);
                    
                    if(i<10){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(progressbar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    jLabel1.setText(null);
                    jLabel1.setText("Loading Database............");
                    }
                    
                    else if(i<20){
                        try {
                            Thread.sleep(90);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(progressbar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    jLabel1.setText("Connecting Database..........");
                    }
                    
                    else if(i<30){
                        try {
                            Thread.sleep(80);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(progressbar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    jLabel1.setText("Loading interface..........");
                    }
                    
                    else if(i<40){
                        try {
                            Thread.sleep(70);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(progressbar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    jLabel1.setText("Loading Images..........");
                    }
                     
                    else if(i<50){
                        try {
                            Thread.sleep(60);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(progressbar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    jLabel1.setText("Reading Preferences..........");
                    }
                      
                    else if(i<60){
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(progressbar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    jLabel1.setText("Scanning for plugins..........");
                    }
                       
                    else if(i<70){
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(progressbar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    jLabel1.setText("Initializing tools..........");
                    }
                    else if(i<80){
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(progressbar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    jLabel1.setText("Loading fonts..........");
                    }
                         else if(i<90){
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(progressbar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    jLabel1.setText("Reading tool functions..........");
                    }
                         else if(i==100){
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(progressbar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    jLabel1.setText("Finishing..........");
                    s();
                    }
                    
                                        }
                
            }
        }).start();
    }
    void s(){
    
        new login().setVisible(true);
        this.dispose();
    
    }
    

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Loading");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));
        getContentPane().add(jProgressBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 540, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/progreebar.jpg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setSize(new java.awt.Dimension(565, 285));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(progressbar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(progressbar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(progressbar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(progressbar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new progressbar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}


