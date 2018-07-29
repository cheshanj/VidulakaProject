/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Distribution;

/**
 *
 * @author DELL
 */

public class agent {

    private int agID;
    private String agfname;
    private String aglname;
    private String agaddress;
    private String agphone;
    private String agemail;

    public agent() {

    }

    public agent(String pagfname, String paglname, String padd, String pagphone, String pagemail) {
        this.agfname = pagfname;
        this.aglname = paglname;
        this.agaddress = padd;
        this.agphone = pagphone;
        this.agemail = pagemail;
    }

    public agent(int pagid, String pagfname, String paglname, String padd, String pagphone, String pagemail) {
        this.agID = pagid;
        this.agfname = pagfname;
        this.aglname = paglname;
        this.agaddress = padd;
        this.agphone = pagphone;
        this.agemail = pagemail;
    }

    public void setagfname(String pagfname) {
        this.agfname = pagfname;
    }

    public void setaglname(String paglname) {
        this.aglname = paglname;
    }

    public void setagadd(String pagadd) {
        this.agaddress = pagadd;
    }

    public void setagphone(String pagphone) {
        this.agphone = pagphone;
    }

    public void setagemail(String pagemail) {
        this.agemail = pagemail;
    }

    public int getagID() {
        return this.agID;
    }

    public String getagfname() {
        return this.agfname;
    }

    public String getaglname() {
        return this.aglname;
    }

    public String getagadd() {
        return this.agaddress;
    }

    public String getagphone() {
        return this.agphone;
    }

    public String getagemail() {
        return this.agemail;
    }
}
