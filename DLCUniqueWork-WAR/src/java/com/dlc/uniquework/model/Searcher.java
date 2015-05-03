/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.model;

import com.dlc.uniquework.data.DataAccess;
import com.dlc.uniquework.data.IDataAccess;
import com.dlc.uniquework.utils.WordParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class in charge of create the ranking of the words.
 * @author fasaloni
 */
public class Searcher {

    private Map<Integer, Double> ranking;
    private StringTokenizer tokens;
    private String words;
    private int count;
    private IDataAccess dataAccess;

    /**
     * Constructor of the class
     * @param words 
     *              The words you want to look for.
     */
    public Searcher(String words) {
        this.words = words;
        count = 0;
        dataAccess = DataAccess.getInstance();
    }

    /**
     * Method used for searching all the words and generate a ranking of them
     * @return a list with all the words.
     */
    public List<Ranking> search() {
        ranking = new HashMap();
        int documentsCount = dataAccess.getDocumentsCount();
        List<String> retrievedWords = prepareWords();
        List<Post> postingList;
        if (!retrievedWords.isEmpty()) {
            for (String retrievedWord : retrievedWords) {
                postingList = (ArrayList<Post>) dataAccess.getPostList(retrievedWord);
                int wordAppareance = dataAccess.getWordAppearance(retrievedWord);
                for (Post post : postingList) {
                    fillRankings(post, documentsCount, wordAppareance);
                }
            }
        }
        count = ranking.size();
        List<Ranking> rankingList = new ArrayList();
        for (Map.Entry entry : ranking.entrySet()) {
            rankingList.add(new Ranking((int) entry.getKey(), (double) entry.getValue()));
        }
        Collections.sort(rankingList);
        return rankingList;
    }

    /**
     * Method used for giving format to all the words.
     * @return a list of words.
     */
    private LinkedList<String> prepareWords() {
        LinkedList<String> retrievedWords = new LinkedList();
        tokens = new StringTokenizer(words);
        while (tokens.hasMoreTokens()) {
            String word = WordParser.parseWord(tokens.nextToken());
            if (word.length() > 0 && !word.equals(" ")) {
                retrievedWords.add(word);
            }
        }
        return retrievedWords;
    }

    /**
     * Method used for filling the ranking with the words and their appearances.
     * @param post
     *              The word in question.
     * @param documentsCount
     *                      In how many documents appears the word                     
     * @param wordAppareance 
     *                      How many times appears the word in all documents
     */
    private void fillRankings(Post post, int documentsCount, double wordAppareance) {
        double rankingValue;
        if (ranking.containsKey(post.getId())) {
            rankingValue = ranking.get(post.getId());
            rankingValue += 1 + post.getCount() * Math.log10(((double) documentsCount / (double) wordAppareance));
            this.ranking.put(post.getId(), rankingValue);
        } else {
            double log = Math.log10(((double) documentsCount / (double) wordAppareance));
            rankingValue = post.getCount() * log;
            ranking.put(post.getId(), rankingValue);
        }
    }

    /**
     * Method used for getting the count of the words.
     * @return the count.
     */
    public int getCount() {
        return count;
    }
}
