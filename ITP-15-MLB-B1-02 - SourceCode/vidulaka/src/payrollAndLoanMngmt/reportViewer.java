/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payrollAndLoanMngmt;

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
 * @author User
 */
public class reportViewer extends JFrame {
   
    Connection conn=null;
    
    public reportViewer(String fileName)
    {
        this(fileName, null);
    }
   
     public reportViewer(String fileName, HashMap para)
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
        setBounds(10,10,900,700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }    
        
        
    }

   
    

