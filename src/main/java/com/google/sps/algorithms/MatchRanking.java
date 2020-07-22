package com.google.sps.algorithms;

import java.util.*;

//this is the initial brute-force algorithm to solve the problem. Once this works, I will refine & make the code more efficient.
public final class MatchRanking {

    public List<String> query() {
        //todo
        return sortBiosByScore(newMatchScores);
    }

    public Map<String, Double> scoreNewMatches(Collection<String> savedMatchBios, Collection<String> allUserBios, Collection<String> newMatchBios) {
        //count words in all of the saved matches
        Map<String, Integer> savedMatchesWordCount = countWordInstances(savedMatchBios);

        //count words in all of the user bios
        Map<String, Integer> allUserWordCount = countWordInstances(allUserBios);

        //calculate score for each new match bio
        Map<String, Double> newBioScores = new HashMap<String, Integer>();
        for(String newBio : newMatchBios) {
            Double bioScore = calculateBioScore(newBio, savedMatchesWordCount, allUserWordCount);
            newBioScores.add(newBio, bioScore);
        }

        return newBioScores;
    }

    public List<String> sortBiosByScore(Map<String, double> newMatchScores) {
        //todo
    }

    private Double calculateBioScore(String bio, Map<String, Integer> savedMatchesWordCount, Map<String, Integer> allUserWordCount) {
        Double numerator = 1; 
        Double denominator = 1; 

        String[] bioWords = bio.toLowerCase().split("\\W+");

        //calculate numerator using savedMatchesWordCount
        for(String word : bioWords) {
            Double wordCount = savedMatchesWordCount.get(word);

            //skip word if it was not in the wordcount
            if(wordCount != null) {
                numerator *= (wordCount / (Double)savedMatchesWordCount.size());
            }
        }

        //calculate denominator using allUserWordCount
        for(String word : bioWords) {
            Double wordCount = allUserWordCount.get(word);

            //skip word if it was not in the wordcount
            if(wordCount != null) {
                denominator *= (wordCount / (Double)allUserWordCount.size());
            }
        }

        return (numerator/denominator);
    }

    private Map<String, Integer> countWordInstances(Collection<String> allBios) {
        Map<String, Integer> wordCounts = new HashMap<String, Integer>();

        for(String bio : allBios) {

            String[] bioWords = bio.toLowerCase().split("\\W+");
            
            //add each word in bio to map
            for(String word : bioWords) {
                if(wordCounts.containsKey(word)) {
                    Integer oldCount = wordCounts.get(word);
                    wordCounts.put(word, oldCount + 1); 
                } else {
                    wordCounts.put(word, 1);
                }
            }

        }
        return wordCounts;
    }

}
