package com.google.sps.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.sps.data.User;

public final class MatchRanking {

    private static final double EPSILON = 0.000001;

    /**
    * Function takes collection of user's saved matches, all users, and new match users and 
    * returns a list of the new matches as users in ranked order from highest to lowest score 
    * (higher score = more likely that the user will select it).
    */
    public static List<User> rankMatches(Collection<User> savedMatches, Collection<User> allUsers, Collection<User> newMatches) {
        Map<User, Double> newMatchScores = scoreNewMatches(savedMatches, allUsers, newMatches);
        return sortUsersByScore(newMatchScores);
    }


    /**
    * Returns a map of User scores - should only be used in testing. 
    */
    public static Map<User, Double> getMatchScores(Collection<User> savedMatches, Collection<User> allUsers, Collection<User> newMatches) {
        return scoreNewMatches(savedMatches, allUsers, newMatches);
    }

    /**
    * Function that takes the three User collections and returns a map with each new match mapped to its score.
    */
    private static Map<User, Double> scoreNewMatches(Collection<User> savedMatches, Collection<User> allUsers, Collection<User> newMatches) {
        Collection<String> savedMatchBios = new ArrayList<String>();
        for(User user : savedMatches) {
            savedMatchBios.add(user.getBio());
        }

        Collection<String> allUserBios = new ArrayList<String>();
        for(User user : allUsers) {
            allUserBios.add(user.getBio());
        }

        //count words in all of the saved match bios
        Map<String, Integer> savedMatchesWordCount = countWordInstances(savedMatchBios);

        //count words in all of the user bios
        Map<String, Integer> allUserWordCount = countWordInstances(allUserBios);

        //calculate score for each new match bio
        Map<User, Double> newMatchScores = new HashMap<User, Double>();
        for(User newMatch : newMatches) {
            double bioScore = calculateBioScore(newMatch.getBio(), savedMatchesWordCount, allUserWordCount);
            newMatchScores.put(newMatch, new Double(bioScore));
        }

        return newMatchScores;
    }

    /**
    * Function that takes the map of users to their scores and returns a list of the users in sorted order from highest to lowest score.
    */
    private static List<User> sortUsersByScore(Map<User, Double> newMatchScores) {
        List<User> orderedUsers = new ArrayList<User>();
        Object[] matchArray = newMatchScores.entrySet().toArray();

        Arrays.sort(matchArray, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<User, Double>) o2).getValue()
                .compareTo(((Map.Entry<User, Double>) o1).getValue());
            }
        });

        for (Object entry : matchArray) {
        orderedUsers.add(((Map.Entry<User, Double>) entry).getKey());
        }

        return orderedUsers;
    }

    /**
    * Function that takes a bio and maps of the word counts in saved bios and all bios, and returns the score for the 
    * bio as a double. The score is the nth root of the regular score to normalize larger bios.
    */
    private static double calculateBioScore(String bio, Map<String, Integer> savedMatchesWordCount, Map<String, Integer> allUserWordCount) {
        //separate bio words by whitespace and store in an array
        String[] bioWords = bio.toLowerCase().split("\\W+");

        double numerator = calculateBioProbability(bioWords, savedMatchesWordCount);
        double denominator = calculateBioProbability(bioWords, allUserWordCount);
        double result = numerator / denominator;

        //if result == NaN, return 0
        if(result != result) {
            return 0.0;
        }

        //take the nth root to normalize result so that there is no bias towards longer/shorter bios
        double nthRoot = Math.pow(result, (1.0/bioWords.length));
        return nthRoot;
    }

    /**
    * Calculate the probability of generating a bio given a certain word count map.
    */
    private static double calculateBioProbability(String[] bioWords, Map<String, Integer> givenMap) {
        double toReturn = 1.0;

        for(String word : bioWords) {
            Integer wordCount = givenMap.get(word);

            if(wordCount != null) {
                double wordCountDouble = wordCount.intValue();
                toReturn *= (wordCountDouble / givenMap.size());
            } else { //multiply by epsilon value so that missing words lower the score
                toReturn *= EPSILON;
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
