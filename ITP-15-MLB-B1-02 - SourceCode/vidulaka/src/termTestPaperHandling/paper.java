/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termTestPaperHandling;

/**
 *
 * @author User
 */
public class paper {
    private int tenderId;
    private int schoolId;
    private int grade;
    private String subject;
    private int amount;
    private int newAmount;
    
    public paper(){
        
    }
    public paper(int ptenderId,int pschoolId,int pgrade,String psubject,int pamount){
        this.amount=pamount;
        this.grade=pgrade;
        this.schoolId=pschoolId;
        this.subject=psubject;
        this.tenderId=ptenderId;
    }
    public paper(int pnewamt,int ptenderId,int pgrade,String psubject){
        this.newAmount=pnewamt;
        this.tenderId=ptenderId;
        this.grade=pgrade;
        this.subject=psubject;
    }
    
    public int gettenderId()
    {
            return tenderId;
    }
    public int getschoolId()
    {
            return schoolId;
    }
    public int getgrade()
    {
            return grade;
    }
    public String getsubject()
    {
            return subject;
    }
    public int getamount()
    {
            return amount;
    }
    public int getoldAmount()
    {
            return newAmount;
    }
}
