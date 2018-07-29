
package AttendanceManagement;
import Home.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Home.dbconnect;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;
/**
 *
 * @author HP
 */
public class LeaveDAO {
   
      Connection con= null;
         PreparedStatement pst= null;
         ResultSet rst=null;
 public LeaveDAO(){
       con= dbconnect.connect();
 }
 //calculating the no of available leaves
 public int[] chkAvbLeaves(Leave lv ,int arrno){
 int[] arr1={5,6,3,4};
 int[] arr2={4,5,2,3};
  System.out.println(arrno);
 
if(arrno==1){
  System.out.println(lv.getEid());
   
  arr1[0]=arr1[0]-getCassualLeaveCount(lv.getEid());
   arr1[1]=arr1[1]-getMedicalLeaveCount(lv.getEid());
    arr1[2]=arr1[2]-getPersonalLeaveCount(lv.getEid());
     arr1[3]=arr1[3]-getShortLeaveCount(lv.getEid());
  
  
    return arr1;
   }
else{
    
  arr2[0]=arr2[0]-getCassualLeaveCount(lv.getEid());
   arr2[1]=arr2[1]-getMedicalLeaveCount(lv.getEid());
    arr2[2]=arr2[2]-getPersonalLeaveCount(lv.getEid());
     arr2[3]=arr2[3]-getShortLeaveCount(lv.getEid());
  

    return arr2;
}   
 
 
 
}
 //end of chkAvbLeaves()
 // inserting the leave detail to the DB
public boolean insertLeave(Leave lv){
    
   try{
     String qry="INSERT INTO  leave_request(status,description,type,fromDate,toDate,empID,session) VALUES('Pending','"+lv.getLV_desc()+"','"+lv.getLV_type()+"','"+lv.getLV_from()+"','"+lv.getLV_to()+"','"+lv.getEid()+"','"+lv.getLV_session()+"')" ;
      pst=con.prepareStatement(qry);
      pst.execute();
      return true;
   }
   catch(Exception e){
      System.out.println(e);
      return false;
   }
    
    
    
}
//end of insertLeave(Leave lv)

public boolean deleteLeave(Leave lv){

    if(lv.getStat().equalsIgnoreCase("pending")){
       try{
     String qry="DELETE FROM leave_request WHERE reqID='"+lv.getRid()+"' ";
      pst=con.prepareStatement(qry);
      pst.execute();
      return true;
   }
   catch(Exception e){
      System.out.println(e);
      return false;
   }
    }
    else
       JOptionPane.showMessageDialog(null,"you cannot cancel this request"); 
        return false;

}


public boolean approveLeave(Leave Lv){
    
   try{
     String qry="UPDATE leave_request SET status='Approved' ,managerID='"+Lv.getEid()+"' WHERE reqID='"+Lv.getRid()+"'";
      pst=con.prepareStatement(qry);
      pst.execute();
      return true;
   }
   catch(Exception e){
      System.out.println(e);
      return false;
   }
    
    
    
}
public boolean rejectLeave(Leave Lv){
    
   try{
     String qry="UPDATE leave_request SET status='Rejected' ,managerID='"+Lv.getEid()+"',reason='"+Lv.getReason()+"' WHERE reqID='"+Lv.getRid()+"'";
      pst=con.prepareStatement(qry);
      pst.execute();
      return true;
   }
   catch(Exception e){
      System.out.println(e);
      return false;
   }
    
    
    
}

public int getCassualLeaveCount(int empid){
    
    System.out.println("getCassualLeaveCount was called");
int Clcount=0;
 String qry="select fromDate,toDate from leave_request where empID='"+empid+"'and type= 'Cassual Leaves' and status='Approved'";
   try{
     
   pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
        while(rst.next()){
          
    Date fd=rst.getDate("fromDate");
       Date td=rst.getDate("toDate");
      long diff=td.getTime()-fd.getTime();
     int days_diff= (int) (diff/(1000*60*60*24));
            System.out.println(days_diff);
            Clcount= Clcount+days_diff;
      }
       System.out.println(Clcount);
       return Clcount;  
        
   }
   catch(Exception e){
    System.out.println(e);
    return -100;
   }

}

public int getPersonalLeaveCount(int empid){
    
     System.out.println("getPersonalLeaveCount was called");
     System.out.println(empid);
int Plcount=0;
 String qry="select fromDate,toDate from leave_request where empID='"+empid+"'and type= 'Personal Leaves' and status='Approved'";
   try{
    
   pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
        while(rst.next()){
          
       
    Date fd=rst.getDate("fromDate");
       Date td=rst.getDate("toDate");
         
      long diff=td.getTime()-fd.getTime();
     int days_diff= (int) (diff/(1000*60*60*24));
            System.out.println(days_diff);
            Plcount= Plcount+days_diff;
      }
       
       return Plcount;  
        
   }
   catch(Exception e){
    System.out.println(e);
        return -100;
   }

}

public int getMedicalLeaveCount(int empid){
     System.out.println("getMedicalLeaveCount was called");
int Mlcount=0;
 String qry="select fromDate,toDate from leave_request where empID='"+empid+"'and type= 'Medical Leaves' and status='Approved'";
   try{
     
   pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
        while(rst.next()){
          
    Date fd=rst.getDate("fromDate");
       Date td=rst.getDate("toDate");
      long diff=td.getTime()-fd.getTime();
     int days_diff= (int) (diff/(1000*60*60*24));
            System.out.println(days_diff);
            Mlcount= Mlcount+days_diff;
      }
        
       System.out.println(Mlcount);
       return Mlcount;  
        
   }
   catch(Exception e){
    System.out.println(e);
        return -100;
   }

}
public int getShortLeaveCount(int empid){
     System.out.println("getShortLeaveCount was called");
int Slcount=0;
String qry="select count(reqID)as shcount from leave_request where empID='"+empid+"'and type= 'Short Leaves' and status='Approved'";
  try{
     
   pst= con.prepareStatement(qry);
        rst=pst.executeQuery();
        while(rst.next()){
      Slcount= rst.getInt("shcount");
 
      }
         System.out.println(Slcount);
       return Slcount;  
        
   }
   catch(Exception e){
    System.out.println(e);
        return -100;
   }
} 

} 









  
    
