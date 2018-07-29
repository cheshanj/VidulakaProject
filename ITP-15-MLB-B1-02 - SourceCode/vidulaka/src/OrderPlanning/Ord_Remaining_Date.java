/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrderPlanning;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Samsung
 */
public class Ord_Remaining_Date {
     public long rdates(JDateChooser d1, JDateChooser d2) {
        long ord_daysdiff = 0;
        try {
           
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");//wanted date format for the calculation

            String ord_sd = df.format(d1.getDate());
            String ord_ed = df.format(d2.getDate());
            int ord_sy = Integer.parseInt(ord_sd.substring(0, 4));//get the starting year from the order date date chooser
            int ord_sm = Integer.parseInt(ord_sd.substring(5, 7));
            int ord_sda = Integer.parseInt(ord_sd.substring(8, 10));
            int ord_ey = Integer.parseInt(ord_ed.substring(0, 4));//get the ending year from the due date date chooser
            int ord_em = Integer.parseInt(ord_ed.substring(5, 7));
            int ord_eda = Integer.parseInt(ord_ed.substring(8, 10));
            Calendar ord_c1 = Calendar.getInstance();
            Calendar ord_c2 = Calendar.getInstance();

            ord_c1.set(ord_sy, ord_sm, ord_sda);//pass starting year,month,date to Calendar class object
            ord_c2.set(ord_ey, ord_em, ord_eda);//pass ending year,month,date to another Calendar class object
            long ord_m1 = ord_c1.getTimeInMillis();//get the whole date in milliseconds
            long ord_m2 = ord_c2.getTimeInMillis();
            long diff = ord_m2 - ord_m1;//get the difference of dates in milliseconds
            ord_daysdiff = diff / (24 * 60 * 60 * 1000);//convert the difference of dates in milliseconds to days

        } catch (NullPointerException e) {
            // System.out.println(e);//handle null dates
        }
         
        return ord_daysdiff;
     }

    
}
