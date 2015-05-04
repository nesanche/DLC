/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.data;

import com.dlc.uniquework.utils.PropertyProvider;


/**
 * Class that contains all the constants that will be used in all the methods
 * @author fasaloni
 */
public class DataConstants {
    
    //general constants
    //static final String URL = PropertyProvider.getInstance().getProperty("database_folder");
    static final String URL = "C:\\\\Users\\\\juancruz\\\\Desktop\\\\";
    static final String FILE_NAME = "result.txt";
    static final String COMPLETE_PATH = URL + FILE_NAME;
    static final String DRIVER_NAME = "org.sqlite.JDBC";
    static final String DATABASE_NAME = "dlc_final_work.sqlite";
    static final String CONNECTION_STRING = "jdbc:sqlite:" + URL + DATABASE_NAME;
    
    //table words
    static final String WORD_TABLE = "Words";
    static final String WORD_TABLE_ID_COLUMN = "id";
    static final String WORD_TABLE_WORD_COLUMN = "word";
    static final String WORD_TABLE_COUNT_COLUMN = "count";
    
    //table documents
    static final String DOCUMENTS_TABLE = "Documents";
    static final String DOCUMENTS_TABLE_ID_COLUMN = "id";
    static final String DOCUMENTS_TABLE_NAME_COLUMN = "name";
    
    //table word_documents
    static final String WORD_REPEATED_DOCUMENT_TABLE = "WordsRepeatedInDocument";
    static final String WORD_REPEATED_DOCUMENT_TABLE_WORD_ID_COLUMN = "idWord";
    static final String WORD_REPEATED_DOCUMENT_TABLE_DOCUMENT_ID_COLUMN = "idDocument";
    static final String WORD_REPEATED_DOCUMENT_TABLE_COUNT_COLUMN = "count";
}
