
import java.util.ArrayList;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author foxyl
 */
public class Calculations {

    private double salaryBasic = 4.25;
    private double salEvening = 1.25;


    public int getMinutes(String start, String end) {

        int index = end.indexOf(":");
        int hours = Integer.parseInt(end.substring(0, (index)));
        int minutes = Integer.parseInt(end.substring(index + 1, end.length()));

        index = start.indexOf(":");
        hours -= Integer.parseInt(start.substring(0, (index)));
        minutes -= Integer.parseInt(start.substring(index + 1, start.length()));

        if (hours < 0) {
            hours += 24;
        }

        return (hours * 60 + minutes);
    }

    public int getEvening(String start, String end) {

        int hours = 0;
        int minutes = 0;

        int index = start.indexOf(":");
        int startHrs = Integer.parseInt(start.substring(0, (index)));

        index = end.indexOf(":");
        int endHrs = Integer.parseInt(end.substring(0, (index)));

        // if start time and end time is between 19:00 - 6:00 then it's 
        // evening work compensation whole time
        if ((startHrs >= 19 && (endHrs <= 23 || endHrs <= 6))) {

            hours = endHrs - startHrs;
            if (hours < 0) {
                hours += 24;
            }
            minutes = calcMinutes(end);
            minutes -= calcMinutes(start);

        } else if ((startHrs >= 0 && startHrs <= 6)) { // if start time is before 6:00 in the morning
            // if shift ends after 6:00
            if (endHrs > 6) {
                hours = 6 - startHrs;
            } else {  // if shift starts and ends before 6:00
                hours = endHrs - startHrs;
            }

            minutes = calcMinutes(end);
            minutes -= calcMinutes(start);

        } else if ((endHrs <= 23 && endHrs >= 19) || (endHrs <= 6 && endHrs >= 0)) { //if shift ends between 19:00 - 6:00

            if (endHrs <= 6) {
                // if shift ends in the morning before 6:00 and it has started before 19:00
                hours = endHrs + 5;
            } else {
                hours = endHrs - 19;
                if (hours < 0) {
                    hours += 24;
                }
            }
            minutes = calcMinutes(end);
            minutes -= calcMinutes(start);
        } else {
            // do nothing
        }
        // Don't return minutes < 0
        if ((hours * 60 + minutes) > 0) {
            return (hours * 60 + minutes);
        } else {
            return 0;
        }
    }

    public void CalcOvetime(ArrayList<Worker> workerList) {
        for (Worker worker : workerList) {
            // if worker has done overtime
            if (worker.getMinutes() > 480) {
                int index = workerList.indexOf(worker);
                // if overtime is 3 hours or less
                if (worker.getMinutes() <= 660) {
                    worker.setOvertime25((worker.getMinutes() - 480));
                } // if overtime is more than 3 hours and less than 4 hours
                else if (worker.getMinutes() > 660 && worker.getMinutes() <= 720) {
                    worker.setOvertime25(180);
                    worker.setOvertime50((worker.getMinutes() - 660));
                } // if overtime is more than 4 hours
                else {
                    worker.setOvertime25(180);
                    worker.setOvertime50(60);
                    worker.setOvertime100((worker.getMinutes() - 720));
                }
                workerList.set(index, worker);
            }
        }
    }

    public HashMap<Integer, Worker> sumHours(ArrayList<Worker> workerList) {

        // Printable list of workers and salaries
        HashMap<Integer, Worker> printable = new HashMap<>();

        // Combine worker details to new printable list
        for (Worker worker : workerList) {

            if (printable.containsKey(worker.getID())) {
                
                Worker printWorker = printable.get(worker.getID());
                // add minutes together from every day
                printWorker.setMinutes(printWorker.getMinutes() + worker.getMinutes());
                printWorker.setEvening(printWorker.getEvening() + worker.getEvening());
                printWorker.setOvertime25(printWorker.getOvertime25() + worker.getOvertime25());
                printWorker.setOvertime50(printWorker.getOvertime50() + worker.getOvertime50());
                printWorker.setOvertime100(printWorker.getOvertime100() + worker.getOvertime100());
                printable.put(worker.getID(), printWorker);

            } else {
                printable.put(worker.getID(), worker);
            }
        }
        return printable;
    }
    public double getTotal(Worker worker){

        double salary = this.salaryBasic * ((double) worker.getMinutes() / 60.0);
        double evening = this.salEvening * ((double) worker.getEvening() / 60.0);
        double over25 = (this.salaryBasic * 0.25) * ((double) worker.getOvertime25() / 60.0);
        double over50 = (this.salaryBasic * 0.50) * ((double) worker.getOvertime50() / 60.0);
        double over100 = this.salaryBasic * ((double) worker.getOvertime100() / 60.0);
        double total = salary + evening + over25 + over50 + over100;


        return total;
    }

    static int calcMinutes(String time) {
        int index = time.indexOf(":");
        return (Integer.parseInt(time.substring(index + 1, time.length())));
    }
}
