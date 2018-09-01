/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author foxyl
 */
public class MonWageCalc {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // List of workers
        ArrayList<Worker> workerList = new ArrayList<>();
        // Command line reader
        Scanner reader = new Scanner(System.in);

        // Get file name from the user
        System.out.println("Give file name?");
        //FileName "HourList201403.csv"
        String fileName = reader.nextLine();
        FileHandle fileHandle = new FileHandle(fileName);

        // Does all calculations
        Calculations calcSal = new Calculations();
        // Read input file 
        workerList = fileHandle.readFile();
        // Calculate overtime
        calcSal.CalcOvetime(workerList);

        // Calculate total working hours and extras
        HashMap<Integer, Worker> printable = new HashMap<>();
        printable = calcSal.sumHours(workerList);
        // write printable file 
        fileHandle.writeFile(printable, fileName);
    }
}
