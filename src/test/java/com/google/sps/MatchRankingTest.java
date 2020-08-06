package com.google.sps;

import com.google.sps.algorithms.MatchRanking;
import com.google.sps.data.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** */
@RunWith(JUnit4.class)
public final class MatchRankingTest {

  private User user_a = new User("");
  private User user_a_doubled = new User("");
  private User user_b = new User("");
  private User user_c = new User("");
  private User user_d = new User("");
  private User user_e = new User("");
  private User user_long = new User("");
  private User user_long_security = new User("");
  private User user_short = new User("");
  private User user_empty = new User("");
  private User user_spanish = new User("");
  private User user_bad_format = new User("");
  private User user_numbers = new User("");
  private User user_forward = new User("");
  private User user_forward_repeat = new User("");
  private User user_backward = new User("");
  private String[] keywords = {"security", "engineer", "software", "Security", "data", "work", "secure", "engineering", "work", "google", "microsoft", "structures",
      "working", "network", "securing", "endpoints", "cloud", "android", "app", "application"};
  private String[] nonKeywords = {"giraffe", "beach", "tennis", "horse", "monkey", "english", "history", "bear", "hi", "asdf", "qwudfdsewff", "adslkfjasdf", "bored",
       "latte", "coffee", "tired", "dont", "what", "boring", "cow", "moose", "lettuce", "blah"};

  @Before
  public void setup() {
    user_a.setBio("Working in network security at Google.");
    user_a.setId("1a");
    user_a_doubled.setBio("Working working in in network network security security at at google google.");
    user_a_doubled.setId("2b");
    user_b.setBio("Securing RPC endpoints in cloud");
    user_b.setId("3c");
    user_c.setBio("Serving frontend pages with low latency");
    user_c.setId("4d");
    user_d.setBio("Android app security");
    user_d.setId("5e");
    user_e.setBio("Frontend security applied to dropbox");
    user_e.setId("6f");
    user_long.setBio("I was first introduced to CS in fifth grade through the FIRST LEGO League robotic competitions. " +
  "In high school, I wanted to continue learning about CS, so I co-founded a Girls Who Code club. We met weekly and used tutorials to teach ourselves basic coding, " +
  "wrote simple apps in Swift, watched TED talks, and invited local women in STEM to speak. I took my first actual coding class in Java freshman year of collegewhile my major was " +
  "Biomedical Engineering and honestly, I fell in love. I enjoy learning, but for the first time ever, it didn’t feel like school; I enjoyed coding so much that I worked extra to develop my own technical skills, " +
  "spending vast amounts of personal time reading ahead in the textbook, watching tutorials, and creating my own programs for random small tasks that I thought of. I changed my major to CS before the end of freshman" +
  " year and I joined Women in Computing to gain exposure to the field. Last summer, during my internship at the Beckman Institute for Advanced Science and Technology, I was chosen as the intern who developed scripts " +
   "to analyze MRI data for my team’s project.");
    user_long.setId("7g");
    user_long_security.setBio("security security I love security security security security security I love security security security security security I love security security security security security " +
  "security security I love security security security security security I love security security security security security I love security security security security security I love security security security security " +
  "security security I love security security security security security I love security security security security security I love security security security security security I love security security security security " +
  "security I love security security security security security I love security security security security security I love security security security security security I love security security security security security I love ");
    user_long_security.setId("8h");
    user_short.setBio("hi");
    user_short.setId("9j");
    user_empty.setBio("");
    user_empty.setId("10k");
    user_spanish.setBio("Soy un ingeniero de software que trabaja en aprendizaje automático, inteligencia artificial y big data.");
    user_spanish.setId("11l");
    user_bad_format.setBio("haiiiiiiiii,,,,,,!!!    my name          is JOHN AND I LOVE COMPUTERSSSSSSssssssm!!");
    user_bad_format.setId("12q");
    user_numbers.setBio("12346 234 132dfs 4adslfk92 113224");
    user_numbers.setId("13e");
    user_forward.setBio("security is life");
    user_forward.setId("14r");
    user_forward_repeat.setBio("security is life");
    user_forward_repeat.setId("57792");
    user_backward.setBio("life is security");
    user_backward.setId("15y");
  }

  //User A should have a higher score than User B because both D and E have "security" in them
  @Test
  public void basicRanking() {
    User currentUser = new User("");
    addNewBio(user_d.getBio(), currentUser);
    addNewBio(user_e.getBio(), currentUser);

    Collection<User> newMatches = new HashSet<User>();
    newMatches.add(user_a);
    newMatches.add(user_b);

    List<User> expected = new ArrayList<User>();
    expected.add(user_a);
    expected.add(user_b);

    List<User> result = MatchRanking.rankMatches(currentUser.getBioMap(), newMatches);
    Assert.assertEquals(expected, result);
  }

  //the long bio user should have a lower score because it has no words in common with the saved users
  @Test
  public void longBioTest() {
    User currentUser = new User("");
    addNewBio(user_a.getBio(), currentUser);
    addNewBio(user_b.getBio(), currentUser);
    addNewBio(user_d.getBio(), currentUser);

    Collection<User> newMatches = new HashSet<User>();
    newMatches.add(user_long);
    newMatches.add(user_e);

    List<User> expected = new ArrayList<User>();
    expected.add(user_e);
    expected.add(user_long);

    List<User> result = MatchRanking.rankMatches(currentUser.getBioMap(), newMatches);
    Assert.assertEquals(expected, result);
  }
  
  //the user with bad formatting should have a lower score because it has nothing in common with the saved users
  @Test
  public void badBioTest() {
    User currentUser = new User("");
    addNewBio(user_a.getBio(), currentUser);
    addNewBio(user_b.getBio(), currentUser);
    addNewBio(user_d.getBio(), currentUser);

    Collection<User> newMatches = new HashSet<User>();
    newMatches.add(user_bad_format);
    newMatches.add(user_e);

    List<User> expected = new ArrayList<User>();
    expected.add(user_e);
    expected.add(user_bad_format);

    List<User> result = MatchRanking.rankMatches(currentUser.getBioMap(), newMatches);
    Assert.assertEquals(expected, result);
  }

  //the user in spanish should have a lower score because it has nothing in common with saved users
  @Test
  public void wrongLanguageTest() {
    User currentUser = new User("");
    addNewBio(user_a.getBio(), currentUser);
    addNewBio(user_b.getBio(), currentUser);
    addNewBio(user_d.getBio(), currentUser);

    Collection<User> newMatches = new HashSet<User>();
    newMatches.add(user_spanish);
    newMatches.add(user_e);

    List<User> expected = new ArrayList<User>();
    expected.add(user_e);
    expected.add(user_spanish);

    List<User> result = MatchRanking.rankMatches(currentUser.getBioMap(), newMatches);
    Assert.assertEquals(expected, result);
  }

  //the user with no words matched should have a lower score because it has nothing in common with saved users
  @Test
  public void shortBioTest() {
    User currentUser = new User("");
    addNewBio(user_a.getBio(), currentUser);
    addNewBio(user_b.getBio(), currentUser);
    addNewBio(user_d.getBio(), currentUser);

    Collection<User> newMatches = new HashSet<User>();
    newMatches.add(user_short);
    newMatches.add(user_e);

    List<User> expected = new ArrayList<User>();
    expected.add(user_e);
    expected.add(user_short);

    List<User> result = MatchRanking.rankMatches(currentUser.getBioMap(), newMatches);
    Assert.assertEquals(expected, result);
  }

  //two users that are the same should only be returned once
  @Test
  public void duplicateUserTest() {
    User currentUser = new User("");
    addNewBio(user_a.getBio(), currentUser);
    addNewBio(user_b.getBio(), currentUser);
    addNewBio(user_d.getBio(), currentUser);

    Collection<User> newMatches = new HashSet<User>();
    newMatches.add(user_e);
    newMatches.add(user_e);

    List<User> expected = new ArrayList<User>();
    expected.add(user_e);

    List<User> result = MatchRanking.rankMatches(currentUser.getBioMap(), newMatches);
    Assert.assertEquals(expected, result);
  }

  //the empty user should be ranked last because it should have a score of 0
  @Test
  public void emptyBioTest() {
    User currentUser = new User("");
    addNewBio(user_a.getBio(), currentUser);
    addNewBio(user_b.getBio(), currentUser);
    addNewBio(user_d.getBio(), currentUser);

    Collection<User> newMatches = new HashSet<User>();
    newMatches.add(user_e);
    newMatches.add(user_empty);

    List<User> expected = new ArrayList<User>();
    expected.add(user_e);
    expected.add(user_empty);

    List<User> result = MatchRanking.rankMatches(currentUser.getBioMap(), newMatches);
    Assert.assertEquals(expected, result);
  }

  //the user with only numbers should be last because it has nothing in common with the saved users
  @Test
  public void numberBioTest() {
    User currentUser = new User("");
    addNewBio(user_a.getBio(), currentUser);
    addNewBio(user_b.getBio(), currentUser);
    addNewBio(user_d.getBio(), currentUser);

    Collection<User> newMatches = new HashSet<User>();
    newMatches.add(user_e);
    newMatches.add(user_numbers);

    List<User> expected = new ArrayList<User>();
    expected.add(user_e);
    expected.add(user_numbers);

    List<User> result = MatchRanking.rankMatches(currentUser.getBioMap(), newMatches);
    Assert.assertEquals(expected, result);
  }

  //two users with the same words in their bios should have the same scores
  @Test
  public void sameWordsInBiosTest() {
    User currentUser = new User("");
    addNewBio(user_a.getBio(), currentUser);
    addNewBio(user_b.getBio(), currentUser);
    addNewBio(user_d.getBio(), currentUser);

    Collection<User> newMatches = new HashSet<User>();
    newMatches.add(user_forward);
    newMatches.add(user_backward);

    Map<User, Double> matchScores = MatchRanking.getMatchScores(currentUser.getBioMap(), newMatches);
      
    Assert.assertTrue(compareDoubles(matchScores.get(user_forward), matchScores.get(user_backward)));
  }

  //two users with equal bios should have the same scores
  @Test
  public void equalBiosTest() {
    User currentUser = new User("");
    addNewBio(user_a.getBio(), currentUser);
    addNewBio(user_b.getBio(), currentUser);
    addNewBio(user_d.getBio(), currentUser);

    Collection<User> newMatches = new HashSet<User>();
    newMatches.add(user_forward);
    newMatches.add(user_forward_repeat);

    Map<User, Double> matchScores = MatchRanking.getMatchScores(currentUser.getBioMap(), newMatches);
      
    Assert.assertTrue(compareDoubles(matchScores.get(user_forward), matchScores.get(user_forward_repeat)));
  }

  //a long user bio that has the word "security" a lot should be ranked higher than the user with "security" once
  @Test
  public void longBioWithKeywordTest() {
    User currentUser = new User("");
    addNewBio(user_d.getBio(), currentUser);
    addNewBio(user_e.getBio(), currentUser);
    addNewBio(user_forward.getBio(), currentUser);
    addNewBio(user_backward.getBio(), currentUser);

    Collection<User> newMatches = new HashSet<User>();
    newMatches.add(user_a);
    newMatches.add(user_long_security);

    List<User> expected = new ArrayList<User>();
    expected.add(user_long_security);
    expected.add(user_a);

    List<User> result = MatchRanking.rankMatches(currentUser.getBioMap(), newMatches);
    Assert.assertEquals(expected, result);
  }

  //compares two users with the same words, but one with two of every word and makes sure that they have the same score
  @Test
  public void nthRootTest() {
    User currentUser = new User("");
    addNewBio(user_d.getBio(), currentUser);
    addNewBio(user_e.getBio(), currentUser);

    Collection<User> newMatches = new HashSet<User>();
    newMatches.add(user_a);
    newMatches.add(user_a_doubled);

    Map<User, Double> matchScores = MatchRanking.getMatchScores(currentUser.getBioMap(), newMatches);

    Assert.assertTrue(compareDoubles(matchScores.get(user_a), matchScores.get(user_a_doubled)));
  }

  //helper method to compare doubles used in some tests
  private static boolean compareDoubles(double d1, double d2) {
    return (Math.abs(d1 - d2) < 0.0001);
  }

  @Test
  public void testDifferentLengthBios() {
    User currentUser = new User("");
    addNewBio(user_a.getBio(), currentUser);
    addNewBio(user_b.getBio(), currentUser);
    addNewBio(user_d.getBio(), currentUser);

    User userGood = new User("");
    userGood.setId("09876");
    User userBad = new User("");
    userBad.setId("123456");

    for(int i = 10; i < 5000; i += 50) {
      try {
        userGood.setBio(generateRandomBio(i, 1.0));
        userBad.setBio(generateRandomBio(i, 0.0));

        Collection<User> newMatches = new HashSet<User>();
        newMatches.add(userGood);
        newMatches.add(userBad);

        List<User> result = MatchRanking.rankMatches(currentUser.getBioMap(), newMatches);
        Assert.assertEquals(userGood, result.get(0));
      } catch (Exception e) {
        System.err.println("System could not handle bio of length " + i + ". See testDifferentLengthBios for more details.");
        break;
      }
    }
  }

  //prints the percent keywords that pass with bio ranked at the top
  @Test
  public void testDifferentPercentKeywordBios() {
    User currentUser = new User("");
    addNewBio(user_a.getBio(), currentUser);
    addNewBio(user_b.getBio(), currentUser);
    addNewBio(user_d.getBio(), currentUser);

    User userGood = new User("");
    userGood.setId("09876");
    User userBad = new User("");
    userBad.setId("123456");

    //test different percentages of keywords against a 0% keyword bio
    for(double i = 0.05; i <= 1.0; i += 0.05) {
      userGood.setBio(generateRandomBio(50, i));
      userBad.setBio(generateRandomBio(50, 0.0));

      Collection<User> newMatches = new HashSet<User>();
      newMatches.add(userGood);
      newMatches.add(userBad);

      List<User> result = MatchRanking.rankMatches(currentUser.getBioMap(), newMatches);
      Assert.assertEquals(userGood, result.get(0));
    }
  }

  //method for testing to directly add new bio to user
  private void addNewBio(String newBio, User user) {
    String[] bioWords = newBio.toLowerCase().split("\\W+");
    Map<String, Integer> savedMatchWordCount = user.getBioMap();
            
    //add each word in bio to map
    for(String word : bioWords) {
        if(savedMatchWordCount.containsKey(word)) {
            Integer oldCount = savedMatchWordCount.get(word);
            savedMatchWordCount.put(word, oldCount + 1); 
        } else {
            savedMatchWordCount.put(word, 1);
        }
    }

    user.setBioMap(savedMatchWordCount);
  }

  private String generateRandomBio(int targetLength, double keywordPercentage) {
    int numKeywords = (int)Math.ceil(keywordPercentage * targetLength);
    int numNonKeyWords = targetLength - numKeywords;

    Random random = new Random();
    StringBuilder builder = new StringBuilder();

    //add keywords
    for(int i = 0; i < numKeywords; ++i) {
      int keywordIndex = random.nextInt(keywords.length - 1);
      String newWord = keywords[keywordIndex];
      builder.append(newWord + " ");
    }

    //add non-keywords
    for(int i = 0; i < numNonKeyWords; ++i) {
      int nonKeywordIndex = random.nextInt(nonKeywords.length - 1);
      String newWord = nonKeywords[nonKeywordIndex];
      builder.append(newWord + " ");
    }

    return builder.toString();
  }

}
