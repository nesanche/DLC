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
import java.util.Arrays;
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
        List<Ranking> rankingList = new ArrayList();
        int documentsCount = dataAccess.getDocumentsCount();
        if (documentsCount != 0)
        {
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

            for (Map.Entry entry : ranking.entrySet()) {
                rankingList.add(new Ranking((int) entry.getKey(), (double) entry.getValue()));
            }
            Collections.sort(rankingList);
            return rankingList;
            }
        else
        {
            return rankingList;
        }
    }

    /**
     * Method used for giving format to all the words.
     * @return a list of words.
     */
    private LinkedList<String> prepareWords() {
        LinkedList<String> retrievedWords = new LinkedList();
        ArrayList<String> stopWords = new ArrayList();
        stopWords.addAll(Arrays.asList("A", "ABOUT", "ABOVE", "ABOVE", "ACROSS", "AFTER", "AFTERWARDS", "AGAIN", "AGAINST", "ALL", "ALMOST", "ALONE", "ALONG", "ALREADY", "ALSO","ALTHOUGH","ALWAYS","AM","AMONG", "AMONGST", "AMOUNGST", "AMOUNT",  "AN", "AND", "ANOTHER", "ANY","ANYHOW","ANYONE","ANYTHING","ANYWAY", "ANYWHERE", "ARE", "AROUND", "AS",  "AT", "BACK","BE","BECAME", "BECAUSE","BECOME","BECOMES", "BECOMING", "BEEN", "BEFORE", "BEFOREHAND", "BEHIND", "BEING", "BELOW", "BESIDE", "BESIDES", "BETWEEN", "BEYOND", "BILL", "BOTH", "BOTTOM","BUT", "BY", "CALL", "CAN", "CANNOT", "CANT", "CO", "CON", "COULD", "COULDNT", "CRY", "DE", "DESCRIBE", "DETAIL", "DO", "DONE", "DOWN", "DUE", "DURING", "EACH", "EG", "EIGHT", "EITHER", "ELEVEN","ELSE", "ELSEWHERE", "EMPTY", "ENOUGH", "ETC", "EVEN", "EVER", "EVERY", "EVERYONE", "EVERYTHING", "EVERYWHERE", "EXCEPT", "FEW", "FIFTEEN", "FIFY", "FILL", "FIND", "FIRE", "FIRST", "FIVE", "FOR", "FORMER", "FORMERLY", "FORTY", "FOUND", "FOUR", "FROM", "FRONT", "FULL", "FURTHER", "GET", "GIVE", "GO", "HAD", "HAS", "HASNT", "HAVE", "HE", "HENCE", "HER", "HERE", "HEREAFTER", "HEREBY", "HEREIN", "HEREUPON", "HERS", "HERSELF", "HIM", "HIMSELF", "HIS", "HOW", "HOWEVER", "HUNDRED", "IE", "IF", "IN", "INC", "INDEED", "INTEREST", "INTO", "IS", "IT", "ITS", "ITSELF", "KEEP", "LAST", "LATTER", "LATTERLY", "LEAST", "LESS", "LTD", "MADE", "MANY", "MAY", "ME", "MEANWHILE", "MIGHT", "MILL", "MINE", "MORE", "MOREOVER", "MOST", "MOSTLY", "MOVE", "MUCH", "MUST", "MY", "MYSELF", "NAME", "NAMELY", "NEITHER", "NEVER", "NEVERTHELESS", "NEXT", "NINE", "NO", "NOBODY", "NONE", "NOONE", "NOR", "NOT", "NOTHING", "NOW", "NOWHERE", "OF", "OFF", "OFTEN", "ON", "ONCE", "ONE", "ONLY", "ONTO", "OR", "OTHER", "OTHERS", "OTHERWISE", "OUR", "OURS", "OURSELVES", "OUT", "OVER", "OWN","PART", "PER", "PERHAPS", "PLEASE", "PUT", "RATHER", "RE", "SAME", "SEE", "SEEM", "SEEMED", "SEEMING", "SEEMS", "SERIOUS", "SEVERAL", "SHE", "SHOULD", "SHOW", "SIDE", "SINCE", "SINCERE", "SIX", "SIXTY", "SO", "SOME", "SOMEHOW", "SOMEONE", "SOMETHING", "SOMETIME", "SOMETIMES", "SOMEWHERE", "STILL", "SUCH", "SYSTEM", "TAKE", "TEN", "THAN", "THAT", "THE", "THEIR", "THEM", "THEMSELVES", "THEN", "THENCE", "THERE", "THEREAFTER", "THEREBY", "THEREFORE", "THEREIN", "THEREUPON", "THESE", "THEY", "THICKV", "THIN", "THIRD", "THIS", "THOSE", "THOUGH", "THREE", "THROUGH", "THROUGHOUT", "THRU", "THUS", "TO", "TOGETHER", "TOO", "TOP", "TOWARD", "TOWARDS", "TWELVE", "TWENTY", "TWO", "UN", "UNDER", "UNTIL", "UP", "UPON", "US", "VERY", "VIA", "WAS", "WE", "WELL", "WERE", "WHAT", "WHATEVER", "WHEN", "WHENCE", "WHENEVER", "WHERE", "WHEREAFTER", "WHEREAS", "WHEREBY", "WHEREIN", "WHEREUPON", "WHEREVER", "WHETHER", "WHICH", "WHILE", "WHITHER", "WHO", "WHOEVER", "WHOLE", "WHOM", "WHOSE", "WHY", "WILL", "WITH", "WITHIN", "WITHOUT", "WOULD", "YET", "YOU", "YOUR", "YOURS", "YOURSELF", "YOURSELVES", "THE"));
        tokens = new StringTokenizer(words);
        while (tokens.hasMoreTokens()) {
            String word = WordParser.parseWord(tokens.nextToken());
            if (!stopWords.contains(word) && word.length() > 0 && !word.equals(" ")) {
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
