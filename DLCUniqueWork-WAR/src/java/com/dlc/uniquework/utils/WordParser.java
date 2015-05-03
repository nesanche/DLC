/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class in charge of give format to the words.
 * @author fasaloni
 */
public class WordParser {
    
    private static final String REGEX_PARSING_WORD = "[^ÑÁÉÍÓÚA-Z]";
    
    /**
     * Method used for giving format to a word.
     * @param word
     *             The word you want to give format.
     * @return the word formatted.
     */
    public static String parseWord(String word) {
        String parsedWord = word.toUpperCase();
        Pattern pattern = Pattern.compile(REGEX_PARSING_WORD);
        Matcher match = pattern.matcher(parsedWord);
        if (match.find()) {
            parsedWord = match.replaceAll("");
        }
        return parsedWord;
    }
}
