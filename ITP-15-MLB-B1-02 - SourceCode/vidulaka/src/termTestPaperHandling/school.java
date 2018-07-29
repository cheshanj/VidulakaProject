/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termTestPaperHandling;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author User
 */
public class school {
    
    private int schoolId;
    private String schoolName;
    private String address;
    private String principle;
    private int provinceId;
    private String phone;
    private String email;
    private String division;
    private Pattern pattern;
    private Matcher matcher;
    
    public school(){
        pattern=Pattern.compile(EMAIL_PATTERN);
        
    }
    private static final String EMAIL_PATTERN="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public school(int pschoolId,String pschoolName,String paddress,String pprincipe,int pprovinceId,String pphone,String pemail,String pdivision){
        this.schoolId=pschoolId;
        this.schoolName=pschoolName;
        this.address=paddress;
        this.principle=pprincipe;
        this.provinceId=pprovinceId;
        this.phone=pphone;
        this.email=pemail;
        this.division=pdivision;
    }
     public school(String pschoolName,String paddress,String pprincipe,int pprovinceId,String pphone,String pemail,String pdivision){
      
        this.schoolName=pschoolName;
        this.address=paddress;
        this.principle=pprincipe;
        this.provinceId=pprovinceId;
        this.phone=pphone;
        this.email=pemail;
        this.division=pdivision;
    }
    
    public boolean validate(final String hex){
        matcher=pattern.matcher(hex);
        return matcher.matches();
    }
    
    public int getSchoolId()
    {
            return schoolId;
    }
    public String getSchoolName()
    {
            return schoolName;
    }
    public String getAddress()
    {
            return address;
    }
    public String getPrinciple()
    {
            return principle;
    }
    public int getProvinceId()
    {
            return provinceId;
    }
    public String getPhone()
    {
            return phone;
    }
    public String getEmail()
    {
            return email;
    }
    public String getDivision()
    {
            return division;
    }
}
