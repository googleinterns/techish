package com.google.sps.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MatchRanking {

    /**
    * Function takes collection of user's saved bios, all user bios, and new match bios and 
    * returns a list of the new match bios in ranked order from highest to lowest score 
    * (higher score = more likely that the user will select it). 
    */
    public static List<String> query(Collection<String> savedMatchBios, Collection<String> allUserBios, Collection<String> newMatchBios) {
        Map<String, Double> newMatchScores = scoreNewMatches(savedMatchBios, allUserBios, newMatchBios);
        return sortBiosByScore(newMatchScores);
    }

    /**
    * Function that takes the three bio collections and returns a map with each new user bio mapped to it's score.
    */
    private static Map<String, Double> scoreNewMatches(Collection<String> savedMatchBios, Collection<String> allUserBios, Collection<String> newMatchBios) {
        //count words in all of the saved matches
        Map<String, Integer> savedMatchesWordCount = countWordInstances(savedMatchBios);

        //count words in all of the user bios
        Map<String, Integer> allUserWordCount = countWordInstances(allUserBios);

        //calculate score for each new match bio
        Map<String, Double> newBioScores = new HashMap<String, Double>();
        for(String newBio : newMatchBios) {
            double bioScore = calculateBioScore(newBio, savedMatchesWordCount, allUserWordCount);
            newBioScores.put(newBio, new Double(bioScore));
        }

        return newBioScores;
    }

    /**
    * Function that takes the map of bios and their scores and returns a list of the bios in sorted order from highest to lowest score.
    */
    private static List<String> sortBiosByScore(Map<String, Double> newMatchScores) {
        List<String> orderedBios = new ArrayList<String>();
        Object[] scoresArray = newMatchScores.entrySet().toArray();

        Arrays.sort(scoresArray, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Double>) o2).getValue()
                        .compareTo(((Map.Entry<String, Double>) o1).getValue());
            }
        });

        for (Object entry : scoresArray) {
        orderedBios.add(((Map.Entry<String, Double>) entry).getKey());
        }

        return orderedBios;
    }

    /**
    * Function that takes a bio and maps of the word counts in saved bios and all bios, and returns the score for the 
    * bio as a double.
    */
    private static double calculateBioScore(String bio, Map<String, Integer> savedMatchesWordCount, Map<String, Integer> allUserWordCount) {
        //separate bio words by whitespace and store in an array
        String[] bioWords = bio.toLowerCase().split("\\W+");

        double numerator = calculateBioProbability(bioWords, savedMatchesWordCount);
        double denominator = calculateBioProbability(bioWords, allUserWordCount);

        return (numerator/denominator);
    }

    /**
    * Calculate the probability of generating a bio given a certain word count map.
    */
    private static double calculateBioProbability(String[] bioWords, Map<String, Integer> givenMap) {
        double toReturn = 0.0;

        for(String word : bioWords) {
            Integer wordCount = givenMap.get(word);

            //skip word if it is not in the given map
            if(wordCount != null) {
                double wordCountDouble = wordCount.intValue();
                //take log to make large numbers easier to analyze
                toReturn += Math.log(wordCountDouble / givenMap.size());
            }
        }

        return toReturn;
    }

    /**
    * Given a collection of bios as strings, return a map with counts of each word in bios.
    */
    private static Map<String, Integer> countWordInstances(Collection<String> allBios) {
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
