/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AttendanceManagement;

/**
 *
 * @author HP
 */
public class Leave {
 private int eid;
 private String lv_type;
 private String lv_desc;
 private java.sql.Date lv_from; 
 private java.sql.Date lv_to;
 private String lv_session;
 int rid;
 String stat;
 String reason;
  
public Leave(){
}
public Leave(int peid,String plv_type,String plv_desc,java.sql.Date plv_from,java.sql.Date plv_to ,String plv_session){
 this.eid=peid;
 this.lv_type=plv_type;
 this.lv_desc=plv_desc;
 this.lv_from=plv_from; 
 this.lv_to=plv_to;
 this.lv_session=plv_session;

}
//get methds
public int getEid(){
 return eid;
}
public String getLV_type(){
 return lv_type;
}
public String getLV_desc(){
 return lv_desc; 
}
public String getLV_session(){
 return lv_session;
}
public  java.sql.Date getLV_from(){
 return lv_from;
}
public  java.sql.Date getLV_to(){
 return lv_to;
}
public  int getRid(){
 return rid;
}
public String getStat(){
 return stat;
}
public String getReason(){
 return reason;
}


 //set methods
public void setEid(int peid){
 eid=peid;
}
public void setRid(int prid){
 rid=prid;
}
public void setStat(String pstat){
 stat=pstat;
}
public void setReason(String preason){
 reason=preason;
}
}
