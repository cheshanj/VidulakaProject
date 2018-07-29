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
public class validation {
 public static boolean chk_num(String str){
  for (char c : str.toCharArray()){
     if (!Character.isDigit(c))
        return true; 
  }
  return false;
 }

    
  public static boolean val_nic(String nic){
   if(nic.length()!=10)
       return true;
   else if(nic.charAt(9)!= 'v')
       return true;
   else if(chk_num(nic.substring(0,9)))
       return true;
    else   
      return false;
   /*else if(chk_num(nic.substring(0,10)))
       return true;
   else return nic.charAt(9)!='v';
       
}*/
}
}
     
         
  
    
  

