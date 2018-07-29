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
public class vehicle {

    private String vehicleNO;
    private String vehicletype;
    private String vehiclemodel;
    private String vservicedate;
    private String vavailability;

    public vehicle() {

    }

    public vehicle(String pvehicleNO, String pvehicletype, String pvehiclemodel, String pvservicedate, String pvavailability) {
        this.vehicleNO = pvehicleNO;
        this.vehicletype = pvehicletype;
        this.vehiclemodel = pvehiclemodel;
        this.vservicedate = pvservicedate;
        this.vavailability = pvavailability;
    }

    public void setvNO(String pvehicleNO) {
        this.vehicleNO = pvehicleNO;
    }

    public void setvtype(String pvehicletype) {
        this.vehicletype = pvehicletype;
    }

    public void setvmodel(String pvehiclemodel) {
        this.vehiclemodel = pvehiclemodel;
    }

    public void setvservicedate(String pvservicedate) {
        this.vservicedate = pvservicedate;
    }

    public void setvavailability(String pvavailability) {
        this.vavailability = pvavailability;
    }

    public String getvNO() {
        return this.vehicleNO;
    }

    public String getvtype() {
        return this.vehicletype;
    }

    public String getvmodel() {
        return this.vehiclemodel;
    }

    public String getvservicedate() {
        return this.vservicedate;
    }

    public String getvavailability() {
        return this.vavailability;
    }
}
