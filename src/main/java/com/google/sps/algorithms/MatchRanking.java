package com.google.sps.algorithms;

import java.util.*;

//this is the initial brute-force algorithm to solve the problem. Once this works, I will refine & make the code more efficient.
public final class MatchRanking {

    public List<String> query() {
        //todo
        return sortBiosByScore(newMatchScores);
    }

    public Map<String, int> scoreNewMatches(Collection<String> savedMatchBios, Collection<String> allUserBios, Collection<String> newMatchBios) {
    
    }

    public List<String> sortBiosByScore(Map<String, int> newMatchScores) {

    }

    public Map<String, Integer> countWordInstances(String bio) {
        Map<String, Integer> wordCounts = new HashMap<String, Integer>();
        String[] bioWords = bio.toLowerCase().split("\\W+");
        
        for(String word : bioWords) {
            if(wordCounts.containsKey(word)) {
                Integer oldCount = wordCounts.get(word);
                wordCounts.put(word, oldCount + 1); 
            } else {
                wordCounts.put(word, 1);
            }
        }
        return wordCounts;
    }






}