/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.model;

/**
 * Class in charge of define the post that would be used for posting the words.
 * @author fasaloni
 */
public class Post {
    private int id;
    private int count;

    /**
     * Constructor of the class.
     * @param id
     *            The id of the word
     * @param count 
     *              The count that represent how many times a word is repeated in the documents.
     */
    public Post(int id, int count) {
        this.id = id;
        this.count = count;
    }

    /**
     * Method used for getting the id of the post.
     * @return the id of the post.
     */
    public int getId() {
        return id;
    }

    /**
     * Method used for setting the id of the post.
     * @param id 
     *           The new id of the post.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method used for getting de count of the post.
     * @return the count of the post.
     */
    public int getCount() {
        return count;
    }

    /**
     * Method used for setting the count of the post
     * @param count 
     *              The new count of the post.
     */
    public void setCount(int count) {
        this.count = count;
    }    
}
