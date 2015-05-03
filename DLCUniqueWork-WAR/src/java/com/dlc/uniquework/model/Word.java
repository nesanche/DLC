/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.model;

/**
 * Class in charge of define a word
 * @author fasaloni
 */
public class Word {
    
    private int id;
    private String word;
    private int counter;

    /**
     * Constructor of the class
     * @param id
     *           The id of the word.
     * @param word
     *            The word in question.
     * @param counter 
     *            How many time appears the word.
     */
    public Word(int id, String word, int counter) {
        this.id = id;
        this.word = word;
        this.counter = counter;
    }

    /**
     * Method used for getting the id of the word.
     * @return the id.
     */
    public int getId() {
        return id;
    }

    /**
     * Method used for setting the id of the word.
     * @param id 
     *          The new id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method used for getting the word.
     * @return the word.
     */
    public String getWord() {
        return word;
    }    

    /**
     * Method used for setting the word.
     * @param word 
     *            The new word.
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * Method used for getting the count.
     * @return the count.
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Method used for setting the count.
     * @param counter 
     *                The new count.
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }
}
