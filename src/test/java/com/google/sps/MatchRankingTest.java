package com.google.sps;

import com.google.sps.algorithms.MatchRanking;
import com.google.sps.data.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
//   private Collection<User> allUsers;

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

    // allUsers = new ArrayList<User>();
    // allUsers.add(user_a);
    // allUsers.add(user_a_doubled);
    // allUsers.add(user_b);
    // allUsers.add(user_c);
    // allUsers.add(user_d);
    // allUsers.add(user_e);
    // allUsers.add(user_long);
    // allUsers.add(user_long_security);
    // allUsers.add(user_short);
    // allUsers.add(user_empty);
    // allUsers.add(user_spanish);
    // allUsers.add(user_bad_format);
    // allUsers.add(user_numbers);
    // allUsers.add(user_forward);
    // allUsers.add(user_forward_repeat);
    // allUsers.add(user_backward);

    // MatchRanking.addToAllUserWordCount(user_a.getBio());
    // MatchRanking.addToAllUserWordCount(user_a_doubled.getBio());
    // MatchRanking.addToAllUserWordCount(user_b.getBio());
    // MatchRanking.addToAllUserWordCount(user_c.getBio());
    // MatchRanking.addToAllUserWordCount(user_d.getBio());
    // MatchRanking.addToAllUserWordCount(user_e.getBio());
    // MatchRanking.addToAllUserWordCount(user_long.getBio());
    // MatchRanking.addToAllUserWordCount(user_long_security.getBio());
  }

  //User A should have a higher score than User B because both D and E have "security" in them
  @Test
  public void basicRanking() {
    //   Collection<User> savedMatches = new HashSet<User>();
    //   savedMatches.add(user_d);
    //   savedMatches.add(user_e);

    User currentUser = new User("");
    currentUser.addNewBioToMapCount(user_d.getBio());
    currentUser.addNewBioToMapCount(user_e.getBio());

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
    //   Collection<User> savedMatches = new HashSet<User>();
    //   savedMatches.add(user_a);
    //   savedMatches.add(user_b);
    //   savedMatches.add(user_d);

    User currentUser = new User("");
    currentUser.addNewBioToMapCount(user_a.getBio());
    currentUser.addNewBioToMapCount(user_b.getBio());
    currentUser.addNewBioToMapCount(user_d.getBio());

      Collection<User> newMatches = new HashSet<User>();
      newMatches.add(user_long);
      newMatches.add(user_e);

      List<User> expected = new ArrayList<User>();
      expected.add(user_e);
      expected.add(user_long);

      List<User> result = MatchRanking.rankMatches(currentUser.getBioMap(), newMatches);
      Assert.assertEquals(expected, result);
  }
  
//   //the user with bad formatting should have a lower score because it has nothing in common with the saved users
//   @Test
//   public void badBioTest() {
//       Collection<User> savedMatches = new HashSet<User>();
//       savedMatches.add(user_a);
//       savedMatches.add(user_b);
//       savedMatches.add(user_d);

//       Collection<User> newMatches = new HashSet<User>();
//       newMatches.add(user_bad_format);
//       newMatches.add(user_e);

//       List<User> expected = new ArrayList<User>();
//       expected.add(user_e);
//       expected.add(user_bad_format);

//       List<User> result = MatchRanking.rankMatches(savedMatches, newMatches);
//       Assert.assertEquals(expected, result);
//   }

//   //the user in spanish should have a lower score because it has nothing in common with saved users
//   @Test
//   public void wrongLanguageTest() {
//       Collection<User> savedMatches = new HashSet<User>();
//       savedMatches.add(user_a);
//       savedMatches.add(user_b);
//       savedMatches.add(user_d);

//       Collection<User> newMatches = new HashSet<User>();
//       newMatches.add(user_spanish);
//       newMatches.add(user_e);

//       List<User> expected = new ArrayList<User>();
//       expected.add(user_e);
//       expected.add(user_spanish);

//       List<User> result = MatchRanking.rankMatches(savedMatches, newMatches);
//       Assert.assertEquals(expected, result);
//   }

//   //the user with no words matched should have a lower score because it has nothing in common with saved users
//   @Test
//   public void shortBioTest() {
//       Collection<User> savedMatches = new HashSet<User>();
//       savedMatches.add(user_a);
//       savedMatches.add(user_b);
//       savedMatches.add(user_d);

//       Collection<User> newMatches = new HashSet<User>();
//       newMatches.add(user_short);
//       newMatches.add(user_e);

//       List<User> expected = new ArrayList<User>();
//       expected.add(user_e);
//       expected.add(user_short);

//       List<User> result = MatchRanking.rankMatches(savedMatches, newMatches);
//       Assert.assertEquals(expected, result);
//   }

//   //two users that are the same should only be returned once
//   @Test
//   public void duplicateUserTest() {
//       Collection<User> savedMatches = new HashSet<User>();
//       savedMatches.add(user_a);
//       savedMatches.add(user_b);
//       savedMatches.add(user_d);

//       Collection<User> newMatches = new HashSet<User>();
//       newMatches.add(user_e);
//       newMatches.add(user_e);

//       List<User> expected = new ArrayList<User>();
//       expected.add(user_e);

//       List<User> result = MatchRanking.rankMatches(savedMatches, newMatches);
//       Assert.assertEquals(expected, result);
//   }

//   //the empty user should be ranked last because it should have a score of 0
//   @Test
//   public void emptyBioTest() {
//       Collection<User> savedMatches = new HashSet<User>();
//       savedMatches.add(user_a);
//       savedMatches.add(user_b);
//       savedMatches.add(user_d);

//       Collection<User> newMatches = new HashSet<User>();
//       newMatches.add(user_e);
//       newMatches.add(user_empty);

//       List<User> expected = new ArrayList<User>();
//       expected.add(user_e);
//       expected.add(user_empty);

//       List<User> result = MatchRanking.rankMatches(savedMatches, newMatches);
//       Assert.assertEquals(expected, result);
//   }

//   //the user with only numbers should be last because it has nothing in common with the saved users
//   @Test
//   public void numberBioTest() {
//       Collection<User> savedMatches = new HashSet<User>();
//       savedMatches.add(user_a);
//       savedMatches.add(user_b);
//       savedMatches.add(user_d);

//       Collection<User> newMatches = new HashSet<User>();
//       newMatches.add(user_e);
//       newMatches.add(user_numbers);

//       List<User> expected = new ArrayList<User>();
//       expected.add(user_e);
//       expected.add(user_numbers);

//       List<User> result = MatchRanking.rankMatches(savedMatches, newMatches);
//       Assert.assertEquals(expected, result);
//   }

//   //two users with the same words in their bios should have the same scores
//   @Test
//   public void sameWordsInBiosTest() {
//       Collection<User> savedMatches = new HashSet<User>();
//       savedMatches.add(user_a);
//       savedMatches.add(user_b);
//       savedMatches.add(user_d);

//       Collection<User> newMatches = new HashSet<User>();
//       newMatches.add(user_forward);
//       newMatches.add(user_backward);

//       Map<User, Double> matchScores = MatchRanking.getMatchScores(savedMatches, newMatches);
      
//       Assert.assertTrue(compareDoubles(matchScores.get(user_forward), matchScores.get(user_backward)));
//   }

//   //two users with equal bios should have the same scores
//   @Test
//   public void equalBiosTest() {
//       Collection<User> savedMatches = new HashSet<User>();
//       savedMatches.add(user_a);
//       savedMatches.add(user_b);
//       savedMatches.add(user_d);

//       Collection<User> newMatches = new HashSet<User>();
//       newMatches.add(user_forward);
//       newMatches.add(user_forward_repeat);

//       Map<User, Double> matchScores = MatchRanking.getMatchScores(savedMatches, newMatches);
      
//       Assert.assertTrue(compareDoubles(matchScores.get(user_forward), matchScores.get(user_forward_repeat)));
//   }

//   //a long user bio that has the word "security" a lot should be ranked higher than the user with "security" once
//   @Test
//   public void longBioWithKeywordTest() {
//       Collection<User> savedMatches = new HashSet<User>();
//       savedMatches.add(user_d);
//       savedMatches.add(user_e);
//       savedMatches.add(user_forward);
//       savedMatches.add(user_backward);

//       Collection<User> newMatches = new HashSet<User>();
//       newMatches.add(user_a);
//       newMatches.add(user_long_security);

//       List<User> expected = new ArrayList<User>();
//       expected.add(user_long_security);
//       expected.add(user_a);

//       List<User> result = MatchRanking.rankMatches(savedMatches, newMatches);
//       Assert.assertEquals(expected, result);
//   }

//   //compares two users with the same words, but one with two of every word and makes sure that they have the same score
//   @Test
//   public void nthRootTest() {
//       Collection<User> savedMatches = new HashSet<User>();
//       savedMatches.add(user_d);
//       savedMatches.add(user_e);

//       Collection<User> newMatches = new HashSet<User>();
//       newMatches.add(user_a);
//       newMatches.add(user_a_doubled);

//       Map<User, Double> matchScores = MatchRanking.getMatchScores(savedMatches, newMatches);

//       Assert.assertTrue(compareDoubles(matchScores.get(user_a), matchScores.get(user_a_doubled)));
//   }

//   //helper method to compare doubles used in some tests
//   private static boolean compareDoubles(double d1, double d2) {
//     return (Math.abs(d1 - d2) < 0.0001);
//   }

}
