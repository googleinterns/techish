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

/** */
@RunWith(JUnit4.class)
public final class MatchRankingTest {

  private static final String BIO_A = "Working in network security at Google.";
  private static final String BIO_A_DOUBLED = "Working working in in network network security security at at google google.";
  private static final String BIO_B = "Securing RPC endpoints in cloud";
  private static final String BIO_C = "Serving frontend pages with low latency";
  private static final String BIO_D = "Android app security";
  private static final String BIO_E = "Frontend security applied to dropbox";
  private static final String BIO_LONG = "I was first introduced to CS in fifth grade through the FIRST LEGO League robotic competitions. " +
  "In high school, I wanted to continue learning about CS, so I co-founded a Girls Who Code club. We met weekly and used tutorials to teach ourselves basic coding, " +
  "wrote simple apps in Swift, watched TED talks, and invited local women in STEM to speak. I took my first actual coding class in Java freshman year of collegewhile my major was " +
  "Biomedical Engineering and honestly, I fell in love. I enjoy learning, but for the first time ever, it didn’t feel like school; I enjoyed coding so much that I worked extra to develop my own technical skills, " +
  "spending vast amounts of personal time reading ahead in the textbook, watching tutorials, and creating my own programs for random small tasks that I thought of. I changed my major to CS before the end of freshman" +
  " year and I joined Women in Computing to gain exposure to the field. Last summer, during my internship at the Beckman Institute for Advanced Science and Technology, I was chosen as the intern who developed scripts " +
   "to analyze MRI data for my team’s project.";
  private static final String BIO_LONG_SECURITY = "security security I love security security security security security I love security security security security security I love security security security security security " +
  "security security I love security security security security security I love security security security security security I love security security security security security I love security security security security " +
  "security security I love security security security security security I love security security security security security I love security security security security security I love security security security security " +
  "security I love security security security security security I love security security security security security I love security security security security security I love security security security security security I love ";
  private static final String BIO_SHORT = "hi";
  private static final String BIO_EMPTY = "";
  private static final String BIO_SPANISH = "Soy un ingeniero de software que trabaja en aprendizaje automático, inteligencia artificial y big data.";
  private static final String BIO_BAD_FORMAT = "haiiiiiiiii,,,,,,!!!    my name          is JOHN AND I LOVE COMPUTERSSSSSSssssssm!!";
  private static final String BIO_NUMBERS = "12346 234 132dfs 4adslfk92 113224";
  private static final String BIO_FORWARD = "security is life";
  private static final String BIO_BACKWARD = "life is security";
  private Collection<String> allUserBios;

  @Before
  public void setup() {
      allUserBios = new HashSet<String>();
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

  @Test
  public void basicRanking() {
      Collection<String> savedMatchBios = new HashSet<String>();
      savedMatchBios.add(BIO_D);
      savedMatchBios.add(BIO_E);

      Collection<String> newMatchBios = new HashSet<String>();
      newMatchBios.add(BIO_A);
      newMatchBios.add(BIO_B);

      List<String> expected = new ArrayList<String>();
      expected.add(BIO_A);
      expected.add(BIO_B);

      List<String> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }

  @Test
  public void testLongBio() {
      Collection<String> savedMatchBios = new HashSet<String>();
      savedMatchBios.add(BIO_A);
      savedMatchBios.add(BIO_B);
      savedMatchBios.add(BIO_D);

      Collection<String> newMatchBios = new HashSet<String>();
      newMatchBios.add(BIO_LONG);
      newMatchBios.add(BIO_E);

      List<String> expected = new ArrayList<String>();
      expected.add(BIO_E);
      expected.add(BIO_LONG);

      List<String> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }

  @Test
  public void badBioLowerScore() {
      Collection<String> savedMatchBios = new HashSet<String>();
      savedMatchBios.add(BIO_A);
      savedMatchBios.add(BIO_B);
      savedMatchBios.add(BIO_D);

      Collection<String> newMatchBios = new HashSet<String>();
      newMatchBios.add(BIO_BAD_FORMAT);
      newMatchBios.add(BIO_E);

      List<String> expected = new ArrayList<String>();
      expected.add(BIO_E);
      expected.add(BIO_BAD_FORMAT);

      List<String> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }

  @Test
  public void wrongLanguageLowerScore() {
      Collection<String> savedMatchBios = new HashSet<String>();
      savedMatchBios.add(BIO_A);
      savedMatchBios.add(BIO_B);
      savedMatchBios.add(BIO_D);

      Collection<String> newMatchBios = new HashSet<String>();
      newMatchBios.add(BIO_SPANISH);
      newMatchBios.add(BIO_E);

      List<String> expected = new ArrayList<String>();
      expected.add(BIO_E);
      expected.add(BIO_SPANISH);

      List<String> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }

  @Test
  public void bioWithNoWordMatches() {
      Collection<String> savedMatchBios = new HashSet<String>();
      savedMatchBios.add(BIO_A);
      savedMatchBios.add(BIO_B);
      savedMatchBios.add(BIO_D);

      Collection<String> newMatchBios = new HashSet<String>();
      newMatchBios.add(BIO_SHORT);
      newMatchBios.add(BIO_E);

      List<String> expected = new ArrayList<String>();
      expected.add(BIO_E);
      expected.add(BIO_SHORT);

      List<String> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }

  @Test
  public void twoSameBio() {
      Collection<String> savedMatchBios = new HashSet<String>();
      savedMatchBios.add(BIO_A);
      savedMatchBios.add(BIO_B);
      savedMatchBios.add(BIO_D);

      Collection<String> newMatchBios = new HashSet<String>();
      newMatchBios.add(BIO_E);
      newMatchBios.add(BIO_E);

      List<String> expected = new ArrayList<String>();
      expected.add(BIO_E);

      List<String> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }

  @Test
  public void emptyBioLast() {
      Collection<String> savedMatchBios = new HashSet<String>();
      savedMatchBios.add(BIO_A);
      savedMatchBios.add(BIO_B);
      savedMatchBios.add(BIO_D);

      Collection<String> newMatchBios = new HashSet<String>();
      newMatchBios.add(BIO_E);
      newMatchBios.add(BIO_EMPTY);

      List<String> expected = new ArrayList<String>();
      expected.add(BIO_E);
      expected.add(BIO_EMPTY);

      List<String> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }

  @Test
  public void numberBioLast() {
      Collection<String> savedMatchBios = new HashSet<String>();
      savedMatchBios.add(BIO_A);
      savedMatchBios.add(BIO_B);
      savedMatchBios.add(BIO_D);

      Collection<String> newMatchBios = new HashSet<String>();
      newMatchBios.add(BIO_E);
      newMatchBios.add(BIO_NUMBERS);

      List<String> expected = new ArrayList<String>();
      expected.add(BIO_E);
      expected.add(BIO_NUMBERS);

      List<String> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }

  @Test
  public void twoBiosSameScore() {
      Collection<String> savedMatchBios = new HashSet<String>();
      savedMatchBios.add(BIO_A);
      savedMatchBios.add(BIO_B);
      savedMatchBios.add(BIO_D);

      Collection<String> newMatchBios = new HashSet<String>();
      newMatchBios.add(BIO_FORWARD);
      newMatchBios.add(BIO_BACKWARD);

      Map<String, Double> matchScores = MatchRanking.getMatchScores(savedMatchBios, allUserBios, newMatchBios);
      
      Assert.assertTrue(compareDoubles(matchScores.get(BIO_FORWARD), matchScores.get(BIO_BACKWARD)));
      
  }

  @Test
  public void longBioLotsOfKeyword() {
      Collection<String> savedMatchBios = new HashSet<String>();
      savedMatchBios.add(BIO_D);
      savedMatchBios.add(BIO_E);
      savedMatchBios.add(BIO_FORWARD);
      savedMatchBios.add(BIO_BACKWARD);

      Collection<String> newMatchBios = new HashSet<String>();
      newMatchBios.add(BIO_A);
      newMatchBios.add(BIO_LONG_SECURITY);

      List<String> expected = new ArrayList<String>();
      expected.add(BIO_LONG_SECURITY);
      expected.add(BIO_A);

      List<String> result = MatchRanking.rankMatches(savedMatchBios, allUserBios, newMatchBios);
      Assert.assertEquals(expected, result);
  }

  @Test
  public void nthRootTest() {
      Collection<String> savedMatchBios = new HashSet<String>();
      savedMatchBios.add(BIO_D);
      savedMatchBios.add(BIO_E);

      Collection<String> newMatchBios = new HashSet<String>();
      newMatchBios.add(BIO_A);
      newMatchBios.add(BIO_A_DOUBLED);

      Map<String, Double> matchScores = MatchRanking.getMatchScores(savedMatchBios, allUserBios, newMatchBios);

      Assert.assertTrue(compareDoubles(matchScores.get(BIO_A), matchScores.get(BIO_A_DOUBLED)));
  }

  private static boolean compareDoubles(double d1, double d2) {
    final double THRESHOLD = .0001;
 
    if (Math.abs(d1 - d2) < THRESHOLD) {
      return true;
    }
    else {
      return false;
    }
  }

}
