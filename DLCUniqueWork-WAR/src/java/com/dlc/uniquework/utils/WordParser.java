/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author fasaloni
 */
public class WordParser {
    
    private static final String REGEX_PARSING_WORD = "[^ÑÁÉÍÓÚA-Z]";
    
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
