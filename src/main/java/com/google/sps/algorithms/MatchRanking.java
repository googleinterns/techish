package com.google.sps.algorithms;

import java.util.*;

//this is the initial brute-force algorithm to solve the problem. Once this works, I will refine & make the code more efficient.
public final class MatchRanking {

    public List<String> query(Collection<String> savedMatchBios, Collection<String> allUserBios, Collection<String> newMatchBios) {
        Map<String, Double> newMatchScores = scoreNewMatches(savedMatchBios, allUserBios, newMatchBios);
        return sortBiosByScore(newMatchScores);
    }

    public Map<String, Double> scoreNewMatches(Collection<String> savedMatchBios, Collection<String> allUserBios, Collection<String> newMatchBios) {
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

    public List<String> sortBiosByScore(Map<String, Double> newMatchScores) {
        List<String> orderedBios = new ArrayList<String>();
        Object[] a = newMatchScores.entrySet().toArray();

        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Double>) o2).getValue()
                        .compareTo(((Map.Entry<String, Double>) o1).getValue());
            }
        });

        for (Object e : a) {
        orderedBios.add(((Map.Entry<String, Double>) e).getKey());
        }

        return orderedBios;
    }

    private double calculateBioScore(String bio, Map<String, Integer> savedMatchesWordCount, Map<String, Integer> allUserWordCount) {
        double numerator = 1.0; 
        double denominator = 1.0; 

        String[] bioWords = bio.toLowerCase().split("\\W+");

        //calculate numerator using savedMatchesWordCount
        for(String word : bioWords) {
            Integer wordCount = savedMatchesWordCount.get(word);

             //skip word if it was not in the wordcount
            if(wordCount != null) {
                double wordCountDouble = wordCount.intValue();
                numerator *= (wordCountDouble / savedMatchesWordCount.size());
            }
        }

        //calculate denominator using allUserWordCount
        for(String word : bioWords) {
            Integer wordCount = allUserWordCount.get(word);
            
            //skip word if it was not in the wordcount
            if(wordCount != null) {
                double wordCountDouble = wordCount.intValue();
                denominator *= (wordCountDouble / allUserWordCount.size());
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
