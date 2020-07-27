package com.google.sps;

import com.google.sps.algorithms.MatchRanking;
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

import com.google.sps.data.User;


/** */
@RunWith(JUnit4.class)
public final class MatchRankingTest {

  private User BIO_A = new User("");

  private User BIO_A_DOUBLED = new User("");

  private User BIO_B = new User("");

  private User BIO_C = new User("");

  private User BIO_D = new User("");
 
  private User BIO_E = new User("");

  private User BIO_LONG = new User("");
 
  private User BIO_LONG_SECURITY = new User("");
  
  private User BIO_SHORT = new User("");
  private User BIO_EMPTY = new User("");
  private User BIO_SPANISH = new User("");
  private User BIO_BAD_FORMAT = new User("");
  private User BIO_NUMBERS = new User("");
  private User BIO_FORWARD = new User("");
  private User BIO_BACKWARD = new User("");

//   private static final User BIO_A = "Working in network security at Google.";
//   private static final User BIO_A_DOUBLED = "Working working in in network network security security at at google google.";
//   private static final User BIO_B = "Securing RPC endpoints in cloud";
//   private static final User BIO_C = "Serving frontend pages with low latency";
//   private static final User BIO_D = "Android app security";
//   private static final User BIO_E = "Frontend security applied to dropbox";
//   private static final User BIO_LONG = "I was first introduced to CS in fifth grade through the FIRST LEGO League robotic competitions. " +
//   "In high school, I wanted to continue learning about CS, so I co-founded a Girls Who Code club. We met weekly and used tutorials to teach ourselves basic coding, " +
//   "wrote simple apps in Swift, watched TED talks, and invited local women in STEM to speak. I took my first actual coding class in Java freshman year of collegewhile my major was " +
//   "Biomedical Engineering and honestly, I fell in love. I enjoy learning, but for the first time ever, it didn’t feel like school; I enjoyed coding so much that I worked extra to develop my own technical skills, " +
//   "spending vast amounts of personal time reading ahead in the textbook, watching tutorials, and creating my own programs for random small tasks that I thought of. I changed my major to CS before the end of freshman" +
//   " year and I joined Women in Computing to gain exposure to the field. Last summer, during my internship at the Beckman Institute for Advanced Science and Technology, I was chosen as the intern who developed scripts " +
//    "to analyze MRI data for my team’s project.";
//   private static final User BIO_LONG_SECURITY = "security security I love security security security security security I love security security security security security I love security security security security security " +
//   "security security I love security security security security security I love security security security security security I love security security security security security I love security security security security " +
//   "security security I love security security security security security I love security security security security security I love security security security security security I love security security security security " +
//   "security I love security security security security security I love security security security security security I love security security security security security I love security security security security security I love ";
//   private static final User BIO_SHORT = "hi";
//   private static final User BIO_EMPTY = "";
//   private static final User BIO_SPANISH = "Soy un ingeniero de software que trabaja en aprendizaje automático, inteligencia artificial y big data.";
//   private static final User BIO_BAD_FORMAT = "haiiiiiiiii,,,,,,!!!    my name          is JOHN AND I LOVE COMPUTERSSSSSSssssssm!!";
//   private static final User BIO_NUMBERS = "12346 234 132dfs 4adslfk92 113224";
//   private static final User BIO_FORWARD = "security is life";
//   private static final User BIO_BACKWARD = "life is security";
  private Collection<User> allUserBios;

  @Before
  public void setup() {
        BIO_A.setBio("Working in network security at Google.");
        BIO_A.setId("1a");
        BIO_A_DOUBLED.setBio("Working working in in network network security security at at google google.");
        BIO_A_DOUBLED.setId("2b");
        BIO_B.setBio("Securing RPC endpoints in cloud");
        BIO_B.setId("3c");
        BIO_C.setBio("Serving frontend pages with low latency");
        BIO_C.setId("4d");
 BIO_D.setBio("Android app security");
 BIO_D.setId("5e");
  BIO_E.setBio("Frontend security applied to dropbox");
  BIO_E.setBio("6f");
 BIO_LONG.setBio("I was first introduced to CS in fifth grade through the FIRST LEGO League robotic competitions. " +
  "In high school, I wanted to continue learning about CS, so I co-founded a Girls Who Code club. We met weekly and used tutorials to teach ourselves basic coding, " +
  "wrote simple apps in Swift, watched TED talks, and invited local women in STEM to speak. I took my first actual coding class in Java freshman year of collegewhile my major was " +
  "Biomedical Engineering and honestly, I fell in love. I enjoy learning, but for the first time ever, it didn’t feel like school; I enjoyed coding so much that I worked extra to develop my own technical skills, " +
  "spending vast amounts of personal time reading ahead in the textbook, watching tutorials, and creating my own programs for random small tasks that I thought of. I changed my major to CS before the end of freshman" +
  " year and I joined Women in Computing to gain exposure to the field. Last summer, during my internship at the Beckman Institute for Advanced Science and Technology, I was chosen as the intern who developed scripts " +
   "to analyze MRI data for my team’s project.");
   BIO_LONG.setId("7g");
   BIO_LONG_SECURITY.setBio("security security I love security security security security security I love security security security security security I love security security security security security " +
  "security security I love security security security security security I love security security security security security I love security security security security security I love security security security security " +
  "security security I love security security security security security I love security security security security security I love security security security security security I love security security security security " +
  "security I love security security security security security I love security security security security security I love security security security security security I love security security security security security I love ");
  BIO_LONG_SECURITY.setId("8h");
    BIO_SHORT.setBio("hi");
    BIO_SHORT.setId("9j");
      BIO_EMPTY.setBio("");
      BIO_EMPTY.setId("10k");
        BIO_SPANISH.setBio("Soy un ingeniero de software que trabaja en aprendizaje automático, inteligencia artificial y big data.");
        BIO_SPANISH.setId("11l");
      BIO_BAD_FORMAT.setBio("haiiiiiiiii,,,,,,!!!    my name          is JOHN AND I LOVE COMPUTERSSSSSSssssssm!!");
      BIO_BAD_FORMAT.setId("12q");
  BIO_NUMBERS.setBio("12346 234 132dfs 4adslfk92 113224");
  BIO_NUMBERS.setId("13e");
  BIO_FORWARD.setBio("security is life");
  BIO_FORWARD.setId("14r");

  BIO_BACKWARD.setBio("life is security");
  BIO_BACKWARD.setId("15y");




      allUserBios = new ArrayList<User>();
      allUserBios.add(BIO_A);
      allUserBios.add(BIO_A_DOUBLED);
      allUserBios.add(BIO_B);
      allUserBios.add(BIO_C);
      allUserBios.add(BIO_D);
      allUserBios.add(BIO_E);
      allUserBios.add(BIO_LONG);
      allUserBios.add(BIO_LONG_SECURITY);
      allUserBios.add(BIO_SHORT);
      allUserBios.add(BIO_EMPTY);
      allUserBios.add(BIO_SPANISH);
      allUserBios.add(BIO_BAD_FORMAT);
      allUserBios.add(BIO_NUMBERS);
      allUserBios.add(BIO_FORWARD);
      allUserBios.add(BIO_BACKWARD);
  }

  //bio A should have a higher score than bio B because both D and E have "security" in them
  @Test
  public void basicRanking() {
      Collection<User> savedMatchBios = new HashSet<User>();
      savedMatchBios.add(BIO_D);
      savedMatchBios.add(BIO_E);

      Collection<User> newMatchBios = new HashSet<User>();
      newMatchBios.add(BIO_A);
      newMatchBios.add(BIO_B);

      List<User> expected = new ArrayList<User>();
      expected.add(BIO_A);
      expected.add(BIO_B);

      List<User> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }

  //the long bio should have a lower score because it has no words in common with the saved bios
  @Test
  public void testLongBio() {
      Collection<User> savedMatchBios = new HashSet<User>();
      savedMatchBios.add(BIO_A);
      savedMatchBios.add(BIO_B);
      savedMatchBios.add(BIO_D);

      Collection<User> newMatchBios = new HashSet<User>();
      newMatchBios.add(BIO_LONG);
      newMatchBios.add(BIO_E);

      List<User> expected = new ArrayList<User>();
      expected.add(BIO_E);
      expected.add(BIO_LONG);

      List<User> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }
  
  //the bio with bad formatting should have a lower score because it has nothing in common with the saved bios
  @Test
  public void badBioLowerScore() {
      Collection<User> savedMatchBios = new HashSet<User>();
      savedMatchBios.add(BIO_A);
      savedMatchBios.add(BIO_B);
      savedMatchBios.add(BIO_D);

      Collection<User> newMatchBios = new HashSet<User>();
      newMatchBios.add(BIO_BAD_FORMAT);
      newMatchBios.add(BIO_E);

      List<User> expected = new ArrayList<User>();
      expected.add(BIO_E);
      expected.add(BIO_BAD_FORMAT);

      List<User> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }

  //the bio in spanish should have a lower score because it has nothing in common with saved bios
  @Test
  public void wrongLanguageLowerScore() {
      Collection<User> savedMatchBios = new HashSet<User>();
      savedMatchBios.add(BIO_A);
      savedMatchBios.add(BIO_B);
      savedMatchBios.add(BIO_D);

      Collection<User> newMatchBios = new HashSet<User>();
      newMatchBios.add(BIO_SPANISH);
      newMatchBios.add(BIO_E);

      List<User> expected = new ArrayList<User>();
      expected.add(BIO_E);
      expected.add(BIO_SPANISH);

      List<User> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }

  //the bio with no words matched should have a lower score because it has nothing in common with saved bios
  @Test
  public void bioWithNoWordMatches() {
      Collection<User> savedMatchBios = new HashSet<User>();
      savedMatchBios.add(BIO_A);
      savedMatchBios.add(BIO_B);
      savedMatchBios.add(BIO_D);

      Collection<User> newMatchBios = new HashSet<User>();
      newMatchBios.add(BIO_SHORT);
      newMatchBios.add(BIO_E);

      List<User> expected = new ArrayList<User>();
      expected.add(BIO_E);
      expected.add(BIO_SHORT);

      List<User> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }

  //two bios that are the same should only be returned once
  @Test
  public void twoSameBio() {
      Collection<User> savedMatchBios = new HashSet<User>();
      savedMatchBios.add(BIO_A);
      savedMatchBios.add(BIO_B);
      savedMatchBios.add(BIO_D);

      Collection<User> newMatchBios = new HashSet<User>();
      newMatchBios.add(BIO_E);
      newMatchBios.add(BIO_E);

      List<User> expected = new ArrayList<User>();
      expected.add(BIO_E);

      List<User> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }

  //the empty bio should be ranked last because it should have a score of 0
  @Test
  public void emptyBioLast() {
      Collection<User> savedMatchBios = new HashSet<User>();
      savedMatchBios.add(BIO_A);
      savedMatchBios.add(BIO_B);
      savedMatchBios.add(BIO_D);

      Collection<User> newMatchBios = new HashSet<User>();
      newMatchBios.add(BIO_E);
      newMatchBios.add(BIO_EMPTY);

      List<User> expected = new ArrayList<User>();
      expected.add(BIO_E);
      expected.add(BIO_EMPTY);

      List<User> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }

  //the bio with only numbers should be last because it has nothing in common with the saved bios
  @Test
  public void numberBioLast() {
      Collection<User> savedMatchBios = new HashSet<User>();
      savedMatchBios.add(BIO_A);
      savedMatchBios.add(BIO_B);
      savedMatchBios.add(BIO_D);

      Collection<User> newMatchBios = new HashSet<User>();
      newMatchBios.add(BIO_E);
      newMatchBios.add(BIO_NUMBERS);

      List<User> expected = new ArrayList<User>();
      expected.add(BIO_E);
      expected.add(BIO_NUMBERS);

      List<User> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }

  //two bios with the same words should have the same scores
  @Test
  public void twoBiosSameScore() {
      Collection<User> savedMatchBios = new HashSet<User>();
      savedMatchBios.add(BIO_A);
      savedMatchBios.add(BIO_B);
      savedMatchBios.add(BIO_D);

      Collection<User> newMatchBios = new HashSet<User>();
      newMatchBios.add(BIO_FORWARD);
      newMatchBios.add(BIO_BACKWARD);

      Map<User, Double> matchScores = MatchRanking.getMatchScores(savedMatchBios, allUserBios, newMatchBios);
      
      Assert.assertTrue(compareDoubles(matchScores.get(BIO_FORWARD), matchScores.get(BIO_BACKWARD)));
  }

  //a long bio that has the word "security" a lot should be ranked higher than the bio with "security" once
  @Test
  public void longBioLotsOfKeyword() {
      Collection<User> savedMatchBios = new HashSet<User>();
      savedMatchBios.add(BIO_D);
      savedMatchBios.add(BIO_E);
      savedMatchBios.add(BIO_FORWARD);
      savedMatchBios.add(BIO_BACKWARD);

      Collection<User> newMatchBios = new HashSet<User>();
      newMatchBios.add(BIO_A);
      newMatchBios.add(BIO_LONG_SECURITY);

      List<User> expected = new ArrayList<User>();
      expected.add(BIO_LONG_SECURITY);
      expected.add(BIO_A);

      List<User> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }

  //compares two bios with the same words, but one with two of every word and makes sure that they have the same score
  @Test
  public void nthRootTest() {
      Collection<User> savedMatchBios = new HashSet<User>();
      savedMatchBios.add(BIO_D);
      savedMatchBios.add(BIO_E);

      Collection<User> newMatchBios = new HashSet<User>();
      newMatchBios.add(BIO_A);
      newMatchBios.add(BIO_A_DOUBLED);

      Map<User, Double> matchScores = MatchRanking.getMatchScores(savedMatchBios, allUserBios, newMatchBios);

      Assert.assertTrue(compareDoubles(matchScores.get(BIO_A), matchScores.get(BIO_A_DOUBLED)));
  }

  //helper method to compare doubles used in some tests
  private static boolean compareDoubles(double d1, double d2) {
    return (Math.abs(d1 - d2) < 0.0001);
  }

}
