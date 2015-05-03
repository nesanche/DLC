/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.data;

import com.dlc.uniquework.model.Post;
import com.dlc.uniquework.model.Word;
import java.util.HashMap;
import java.util.List;

/**
 * Interface that contains all the methods used for interacting with the database.
 * @author fasaloni
 */
public interface IDataAccess {
    
    /**
     * Method used for getting all the documents store in the database.
     * @return a list of documents.
     */
    List getDocuments();
    
    /**
     * Method used for getting all the documents that contains a specific word.
     * @param id
     *          The id of the word you want to look for.
     * @return a list of documents that contain the word in question.
     */
    List getDocumentsById(int id);
    
    /**
     * Method used for checking if a specific document exists. 
     * @param file
     *              Is the document you want to check.
     * @return true if it exists or false if it doesn´t exist
     */
    boolean checkDocument(String file);
    
    /**
     * Method use for inserting a single document in the document table, also update or insert all the words
     * in the word table and finally update or insert the table WordRepeatedInDocument.
     * @param map
     *              Conatins the words that are in the document.
     * @param file
     *              Document to insert in the database.
     * @return true if the insertion was successfully or false otherwise.          
     */
    boolean insertTable(HashMap map, String file);
    
    /**
     * Method used for getting a list of all the words that are part of the param word
     * @param word
     *              The word that you want to look for.
     * @return a list of all the words.
     */
    List<Word> getList(String word);
    
    /**
     * Method used for getting the list that would be shown when the user search for a specific word.
     * @param word
     *              The word you want to look for.
     * @return a list of all the appearances 
     */
    List<Post> getPostList(String word);
    
    /**
     * Method used for getting how many documents are in the database.
     * @return the count of the documents.
     */
    int getDocumentsCount();
    
    /**
     * Method used for getting how many times a specific word appears in all the documents
     * @param word
     *              The word you want to look for.
     * @return the count of all appearances 
     */
    int getWordAppearance(String word);
    
    /**
     * Method used for getting a speficif document of the database.
     * @param id
     *          The id of the document you want to look for.
     * @return the string of the document.
     */
    String getLocation(int id);
}
