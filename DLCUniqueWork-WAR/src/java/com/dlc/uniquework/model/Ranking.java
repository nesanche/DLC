/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.model;

/**
 * Class in charge of create the ranking of the word you are looking for.
 * @author fasaloni
 */
public class Ranking implements Comparable<Ranking> {
    
    private int id;
    private double rank;

    /**
     * Constructor of the class.
     * @param id
     *            The id of the word.   
     * @param rank 
     *              The position of the word in the ranking.
     */
    public Ranking(int id, double rank) {
        this.id = id;
        this.rank = rank;
    }

    /**
     * Method used for getting the id of the ranking.
     * @return the id of the ranking.
     */
    public int getId() {
        return id;
    }

    /**
     * Method used for setting the id of the ranking.
     * @param id 
     *          The new id of the ranking.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method used for getting rank the ranking.
     * @return the rank of the ranking.
     */
    public double getRank() {
        return rank;
    }

    /**
     * Method used for setting the ranking.
     * @param rank 
     *              The new rank of the ranking.
     */
    public void setRank(double rank) {
        this.rank = rank;
    }

    @Override
    public int compareTo(Ranking ranking) {
        if (this.rank > ranking.getRank())
            return -1;           
        else if (this.rank < ranking.getRank())
            return 1;
        else
            return 0;
    }
}
