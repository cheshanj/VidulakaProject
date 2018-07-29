/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrderPlanning;

import Home.dbconnect;
import java.awt.Container;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Samsung
 */
public class orderReport extends JFrame {
    public orderReport  (String f){
        this(f,null);
    }
      public orderReport(String fileName,HashMap parameter){
        super("view report");
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
           // Connection con=null;
            
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3308/vidulaka", "root", "root");
          //  JasperReport jasperReport=JasperCompileManager.compileReport(file Name);
            JasperPrint print=JasperFillManager.fillReport(fileName, parameter,con);
            JRViewer viewer=new JRViewer(print);
            Container c=getContentPane();
            c.add(viewer);
        } 
         catch(Exception e){
           // e.printStackTrace();
        }
        //setBounds(10, 10, 1000, 500);
               
        setBounds(170, 105, 1000, 550);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE );
    }
}
