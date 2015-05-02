/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.services;

import com.dlc.uniquework.data.DataAccess;
import com.dlc.uniquework.data.IDataAccess;
import com.dlc.uniquework.model.Post;
import com.dlc.uniquework.model.Ranking;
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
 *
 * @author fasaloni
 */
public class Searcher {

    private Map<Integer, Double> ranking;
    private StringTokenizer tokens;
    private String words;
    private int count;
    private IDataAccess dataAccess;

    public Searcher(String words) {
        this.words = words;
        count = 0;
        dataAccess = DataAccess.getInstance();
    }

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

    public int getCount() {
        return count;
    }
}
