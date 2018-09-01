
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author foxyl
 */
public class FileHandle {

    private ArrayList<Worker> workerList;
    private String fileName;

    /**
     * Initialize Filehandle class
     *
     * @param fileName      for input file.
     */
    public FileHandle(String fileName) {
        this.fileName = fileName;
        this.workerList = new ArrayList<>();
    }

    /**
     * Read input file to ArrayList and sum dublicate date working hours
     * together
     *
     */
    public ArrayList<Worker> readFile() {
        // Class making all calculations
        Calculations calculations = new Calculations();

        try (Scanner doc = new Scanner(new File(this.fileName))) {
            // Skip header line
            String workerInfo = doc.nextLine();
            while (doc.hasNextLine()) {
                // read worker info
                workerInfo = doc.nextLine();
                Worker worker = new Worker();
                // read line comma by comma to take info into object
                int index = workerInfo.indexOf(",");
                worker.setName(workerInfo.substring(0, (index)));

                workerInfo = workerInfo.substring(index + 1);

                index = workerInfo.indexOf(",");
                worker.setID(Integer.parseInt(workerInfo.substring(0, (index))));

                workerInfo = workerInfo.substring(index + 1);

                index = workerInfo.indexOf(",");
                worker.setDate(workerInfo.substring(0, index));

                int personExists = -1;
                // check workers already handled
                for (Worker personToLook : this.workerList) {
                    // if worker already has done work this day
                    if (personToLook.getID() == worker.getID()
                            && personToLook.getDate().equals(worker.getDate())) {
                        // copy existing data for current worker 
                        worker = personToLook;
                        personExists = this.workerList.indexOf(personToLook);
                        break;
                    }
                }

                workerInfo = workerInfo.substring(index + 1);

                index = workerInfo.indexOf(",");
                String startTime = workerInfo.substring(0, index);

                workerInfo = workerInfo.substring(index + 1);

                String endTime = workerInfo.substring(0, workerInfo.length());

                // calculate total working hour as minutes
                worker.setMinutes(worker.getMinutes() + calculations.getMinutes(startTime, endTime));
                // calculate evening work compensation minutes
                worker.setEvening(worker.getEvening() + calculations.getEvening(startTime, endTime));

                // add new object or update existing
                if (personExists == -1) {
                    this.workerList.add(worker);
                } else {
                    this.workerList.set(personExists, worker);
                }
                // sort list by ID
                Collections.sort(this.workerList, Worker.compWorkerID);
            }
        } catch (Exception e) {
            System.out.println("Error: " + "File " + this.fileName + " reading failed.");
        }
        return this.workerList;
    }

    /**
     * Write output file "output_" + given file name
     *
     */

    public void writeFile(HashMap<Integer, Worker> printable, String filename) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output_" + filename));
            String header = "ID,NAME,HOURS,SALARY,EVENING WORK,OT 25%,OT 50%,OT 100%\n";
            writer.write(header);

            for (int ID : printable.keySet()) {
                writer.write(printable.get(ID).toString() + "\n");
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
