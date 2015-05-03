/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 *
 * @author fasaloni
 */
public class FileAccess {
    
    /**
     * Method used for saving the result of all the actions that occurred during the 
     * execution of the program.
     * @param result 
     *              The string that contains all the information that occurred.
     */
    public static void save(String result) {
        try {
            File file = new File(DataConstants.COMPLETE_PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(result);
            bufferedWriter.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * Method used for reading the result document 
     * @return the string that contains all the information of the result document.
     */
    public static String read() {
        String answer = "";
        try{
            FileInputStream fileInputStream = new FileInputStream(DataConstants.COMPLETE_PATH);
            try (DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null)   {
                    answer = line;
                }
            }
        }catch (IOException e){ 
            System.err.println(e.getMessage());
        }
        //TODO ?????
        if(answer.length() != 0) save("");
        return answer;
    }
}
