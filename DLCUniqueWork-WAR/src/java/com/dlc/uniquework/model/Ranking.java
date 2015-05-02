/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.model;

/**
 *
 * @author fasaloni
 */
public class Ranking implements Comparable<Ranking> {
    
    private int id;
    private double rank;

    public Ranking(int id, double rank) {
        this.id = id;
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRank() {
        return rank;
    }

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
