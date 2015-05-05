/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.services;

import com.dlc.uniquework.data.DataAccess;
import com.dlc.uniquework.utils.WordParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Class in charge of doing all the insertions to the database.
 * @author fasaloni
 */
public class Indexer {

    private final Map<String, Integer> map;
    private final String directory;
    private StringTokenizer tokens;

    private static final String ALREADY_PROCESSED_DOCUMENT_MESSAGE = "The document has already been processed.";
    private static final String SUCCESSFULL_PROCESSED_DOCUMENT_MESSAGE = "The document has been processed.";
    private static final String ERROR_PROCESSED_DOCUMENT_MESSAGE = "An error has been occurred during the processing.";

    
    /**
     * Constructor of the class.
     * @param directory 
     *                  The name of the directory.
     */
    public Indexer(String directory) {
        this.map = new HashMap<>();
        this.directory = directory;
    }

    @Override
    public String toString() {
        return map.toString();
    }

    /**
     * Method used for inserting(indexing) all docmuents into the database, plus all the 
     * words in each document.
     * @return a string that mention if the whole operation was succesfull or if occured an error
     * during the operation.
     */
    public String doIndexation() {
        try {
            File directoryFile = new File(directory);
            if (!DataAccess.getInstance().checkDocument(directoryFile.getName())) {
                return ALREADY_PROCESSED_DOCUMENT_MESSAGE;
            }
            if (directoryFile.exists()) {
                    FileInputStream fileInputStream = new FileInputStream(directoryFile);
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "ISO-8859-1");
                    BufferedReader info = new BufferedReader(inputStreamReader);
                    String line = info.readLine();
                    while (line != null) {
                        tokens = new StringTokenizer(line);
                        while (tokens.hasMoreTokens()) {
                            String word = WordParser.parseWord(tokens.nextToken());
                            if (word.length() > 0 && !word.equals(" ")) {
                                if (!map.containsKey(word)) {
                                    map.put(word, 1);
                                } else {
                                    int count = map.get(word);
                                    map.put(word, count++);
                                }
                            }
                        }
                        line = info.readLine();
                    }
                    if (DataAccess.getInstance().insertTable((HashMap) map, directoryFile.getName())) {
                        return SUCCESSFULL_PROCESSED_DOCUMENT_MESSAGE;
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
                return ERROR_PROCESSED_DOCUMENT_MESSAGE;
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return ERROR_PROCESSED_DOCUMENT_MESSAGE;
            }
        return ERROR_PROCESSED_DOCUMENT_MESSAGE;
    }

}
