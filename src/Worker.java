
import java.text.DecimalFormat;
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author foxyl
 */
public class Worker {

    private int ID;
    private String name;
    private String date;
    private int totalMinutes;
    private int evening;
    private int overTime25;
    private int overTime50;
    private int overTime100;

    public Worker() {
        this.name = "";
        this.ID = 0;
        this.date = "";
        this.totalMinutes = 0;
        this.evening = 0;
        this.overTime25 = 0;
        this.overTime50 = 0;
        this.overTime100 = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMinutes(int hours) {
        this.totalMinutes = hours;
    }

    public void setEvening(int evening) {
        this.evening = evening;
    }

    public void setOvertime25(int overTime) {
        this.overTime25 = overTime;
    }

    public void setOvertime50(int overTime) {
        this.overTime50 = overTime;
    }

    public void setOvertime100(int overTime) {
        this.overTime100 = overTime;
    }

    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.ID;
    }

    public String getDate() {
        return this.date;
    }

    public int getMinutes() {
        return this.totalMinutes;
    }

    public int getEvening() {
        return this.evening;
    }

    public int getOvertime25() {
        return this.overTime25;
    }

    public int getOvertime50() {
        return this.overTime50;
    }

    public int getOvertime100() {
        return this.overTime100;
    }

    public static Comparator<Worker> compWorkerID = new Comparator<Worker>() {

        public int compare(Worker w1, Worker w2) {

            int worker1 = w1.getID();
            int worker2 = w2.getID();

            /*For ascending order*/
            return worker1 - worker2;

        }
    };

    @Override
    public String toString() {

        Calculations calcSal = new Calculations();
        
        // Print "ID,NAME,HOURS,SALARY,EVENING WORK,25%,50%,100%"
        return this.ID + ","
                + this.name
                + "," + ((double) this.totalMinutes / 60.0)
                + ",\"$" + new DecimalFormat("##.##").format(calcSal.getTotal(this))
                + "\"," + (double) (this.getEvening() / 60.0)
                + "," + (double) (this.getOvertime25() / 60.0)
                + "," + (double) (this.getOvertime50() / 60.0)
                + "," + (double) (this.getOvertime100() / 60.0);
    }
}
