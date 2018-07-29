/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AssetsHandling;

import java.sql.Date;

/**
 *
 * @author SIERRA
 */
public class getnset {
    private int assetid;
    private String name;
    private String date1;
    private int quan;
    private String wty;
    private String wtimep;
    private String oeqt;
    private String oserviced;
    private int techid;
    private String compname ;
    private String add1;
    private String add2 ;
    private String phone;
    private String email ;   
    private int suppid;
    private String Addr1 ;
    private String Addr2;
    private String suppname ;
    private String mail;
    private int contct ; 
    
    
    //repair onwards
    private int repairid;
    private String date2;
    private String desc;
    private double tot;
    private String repname;
    
    public getnset(){
    
    }
    
    public getnset( String name, String d, int quan) {
       
        this.name = name;
        this.date1 = d;
        this.quan = quan;
    }

    public getnset( String wty, String wtimep) {
      
        this.wty = wty;
        this.wtimep = wtimep;
    }

    public getnset(int assetid, String wty, String wtimep) {
        this.assetid = assetid;
        this.wty = wty;
        this.wtimep = wtimep;
    }

    public getnset(int assetid, String oeqt) {
        this.assetid = assetid;
        this.oeqt = oeqt;
    }

    public getnset(int assetid) {
        this.assetid = assetid;
    }

    public void getnsetdate(String oserviced) {
        this.oserviced = oserviced;
    }

    public getnset( String oeqt) {
   
        this.oeqt = oeqt; 
    }

    public void getnset1(int id,String oserviced) {
       
      this.assetid=id;
        this.oserviced =oserviced;
    }

    public getnset(int assetid, String name, String date1, int quan) {
        this.assetid = assetid;
        this.name = name;
        this.date1 = date1;
        this.quan = quan;
    }

   

    

    
    public getnset(int techid, String compname, String add1, String add2, String phone, String email) {
        //this.techid = techid;
      this.techid=techid;
        this.compname = compname;
        this.add1 = add1;
        this.add2 = add2;
        this.phone = phone;
        this.email = email;
    }

    public getnset(String compname, String add1, String add2, String phone, String email) {
        this.compname = compname;
        this.add1 = add1;
        this.add2 = add2;
        this.phone = phone;
        this.email = email;
    }

  
    
    public getnset(String Addr1, String Addr2, String suppname, String mail, int contct) {
  
        this.Addr1 = Addr1;
        this.Addr2 = Addr2;
        this.suppname = suppname;
        this.mail = mail;
        this.contct = contct;
    }

    public getnset(int suppid, String Addr1, String Addr2, String suppname, String mail, int contct) {
        this.suppid = suppid;
        this.Addr1 = Addr1;
        this.Addr2 = Addr2;
        this.suppname = suppname;
        this.mail = mail;
        this.contct = contct;
    }
    

    //repair

    public getnset(int repairid, String date2, String desc, double tot) {
        this.repairid = repairid;
        this.date2 = date2;
        this.desc = desc;
        this.tot = tot;
    }

    public getnset(String date2, String desc, double tot) {
        this.date2 = date2;
        this.desc = desc;
        this.tot = tot;
    }

    public getnset(String date2, String desc, double tot, String repname,int repId) {
        this.date2 = date2;
        this.desc = desc;
        this.tot = tot;
        this.repname = repname;
        this.repairid=repId;
    }
    public getnset(String date2, String desc, double tot, String repname) {
        this.date2 = date2;
        this.desc = desc;
        this.tot = tot;
        this.repname = repname;
    }
    
    
    
    
    

    public int delgetnset(int techid) {
        this.techid = techid;
        return techid;
    }

   
    public int delgetnset2(int suppid) {
        this.suppid = suppid;
        return suppid;
    }

   public int delgetnset3(int repairid){
       this.repairid = repairid;
       return repairid;
   }

    
    
    public int getAssetid() {
        return assetid;
    }

    public void setAssetid(int assetid) {
        this.assetid = assetid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date1;
    }

    public void setDate(String d) {
        this.date1 = d;
    }

    public int getQuan() {
        return quan;
    }

    public void setQuan(int quan) {
        this.quan = quan;
    }

    public String getWty() {
        return wty;
    }

    public void setWty(String wty) {
        this.wty = wty;
    }

    public String getWtimep() {
        return wtimep;
    }

    public void setWtimep(String wtimep) {
        this.wtimep = wtimep;
    }

    public String getOeqt() {
        return oeqt;
    }

    public void setOeqt(String oeqt) {
        this.oeqt = oeqt;
    }

    public String getOserviced() {
        return oserviced;
    }

    public void setOserviced(String oserviced) {
        this.oserviced = oserviced;
    }

    public int getnset11(int id) {
        this.assetid=id;
        return assetid;
    }
    public int getTechid() {
        return techid;
    }

    public void setTechid(int techid) {
        this.techid = techid;
    }

    public String getCompname() {
        return compname;
    }

    public void setCompname(String compname) {
        this.compname = compname;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSuppid() {
        return suppid;
    }

    public void setSuppid(int suppid) {
        this.suppid = suppid;
    }

    public String getAddr1() {
        return Addr1;
    }

    public void setAddr1(String Addr1) {
        this.Addr1 = Addr1;
    }

    public String getAddr2() {
        return Addr2;
    }

    public void setAddr2(String Addr2) {
        this.Addr2 = Addr2;
    }

    public String getSuppname() {
        return suppname;
    }

    public void setSuppname(String suppname) {
        this.suppname = suppname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getContct() {
        return contct;
    }

    public void setContct(int contct) {
        this.contct = contct;
    }

   
    //repair

    public int getRepairid() {
        return repairid;
    }

    public void setRepairid(int repairid) {
        this.repairid = repairid;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getTot() {
        return tot;
    }

    public void setTot(double tot) {
        this.tot = tot;
    }

    public String getRepname() {
        return repname;
    }

    public void setRepname(String repname) {
        this.repname = repname;
    }
    
    
    
    
    
    
}
