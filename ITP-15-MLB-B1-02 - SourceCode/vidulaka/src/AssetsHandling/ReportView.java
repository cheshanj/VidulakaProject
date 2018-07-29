/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AssetsHandling;

import Home.dbconnect;
import com.mysql.jdbc.Connection;
import java.awt.Container;
import java.util.HashMap;
import javax.swing.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Gayathri
 */
public class ReportView extends JFrame {
   
    Connection conn=null;
    
    public ReportView(String fileName)
    {
        this(fileName, null);
    }
   
     public ReportView (String fileName, HashMap para)
    {
       // super("ABC Solutions Employee/Project Management System (Report Viewer)");

          conn=(Connection) dbconnect.connect();

        try
        {
            JasperPrint print = JasperFillManager.fillReport(fileName, para, conn);
            JRViewer viewer = new JRViewer(print);
            Container c = getContentPane();
            c.add(viewer);            
        } 
        catch (JRException jRException)
        {
            System.out.println(jRException);
        }
        setBounds(170,105,1000,550);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }    
        
        
    }

   
    

