/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package seminar;

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
 * @author Hasindu
 */
public class ReportView extends JFrame
{
    Connection con = null;
    
    public ReportView(String fileName)
    {
        this(fileName, null);
    }
    public ReportView(String fileName, HashMap para)
    {
        super("Vidulaka Publishers Pvt (ltd)/Press Management System (Report Viewer)");

        con = (Connection)dbconnect.connect();

        try
        {
            JasperPrint print = JasperFillManager.fillReport(fileName, para, con);
            JRViewer viewer = new JRViewer(print);
            Container c = getContentPane();
            c.add(viewer);            
        } 
        catch (JRException jRException)
        {
            
        }
        setBounds(170, 10, 1000, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
}
