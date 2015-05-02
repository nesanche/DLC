/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.model;

import com.dlc.uniquework.data.DataAccess;
import com.dlc.uniquework.utils.WordParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author fasaloni
 */
public class Indexer {

    private final Map<String, Integer> map;
    private final String directory;
    private StringTokenizer tokens;

    private static final String ALREADY_PROCESSED_DOCUMENT_MESSAGE = "El documento citado ya ha sido procesado.";
    private static final String SUCCESSFULL_PROCESSED_DOCUMENT_MESSAGE = "El documento citado ha sido procesado correctamente.";
    private static final String ERROR_PROCESSED_DOCUMENT_MESSAGE = "Ocurrió un error al intentar procesar el documento.";

    
    
    public Indexer(String directory) {
        this.map = new HashMap<>();
        this.directory = directory;
    }

    @Override
    public String toString() {
        return map.toString();
    }

    public String doIndexation() {
        try {
            File directoryFile = new File(directory);
            if (!DataAccess.getInstance().checkDocument(directoryFile.getName())) {
                return ALREADY_PROCESSED_DOCUMENT_MESSAGE;
            }
            if (directoryFile.exists()) {
                try (BufferedReader info = new BufferedReader(new InputStreamReader(new FileInputStream(new File(directory)), "ISO-8859-1"))) {
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
            }
        } catch (IOException e) {
            return ERROR_PROCESSED_DOCUMENT_MESSAGE;
        }
        return ERROR_PROCESSED_DOCUMENT_MESSAGE;
    }

}
