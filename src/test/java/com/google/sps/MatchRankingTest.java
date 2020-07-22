package com.google.sps;

import com.google.sps.algorithms.MatchRanking;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

/** */
@RunWith(JUnit4.class)
public final class MatchRankingTest {

  private static final String BIO_A = "Working in network security at Google.";
  private static final String BIO_B = "Securing RPC endpoints in cloud";
  private static final String BIO_C = "Serving frontend pages with low latency";
  private static final String BIO_D = "Android app security";
  private static final String BIO_E = "Frontend security applied to dropbox";
  private Collection<String> allUserBios;
  private MatchRanking MATCH_RANKING;

  @Before
  public void setup() {
      allUserBios = new HashSet<String>();
      allUserBios.add(BIO_A);
      allUserBios.add(BIO_B);
      allUserBios.add(BIO_C);
      allUserBios.add(BIO_D);
      allUserBios.add(BIO_E);

      MATCH_RANKING = new MatchRanking();
  }

  @Test
  public void incompleteTest() {
      Collection<String> savedMatchBios = new HashSet<String>();
       savedMatchBios.add(BIO_D);
       savedMatchBios.add(BIO_E);

       Collection<String> newMatchBios = new HashSet<String>();
       newMatchBios.add(BIO_A);
       newMatchBios.add(BIO_B);
       newMatchBios.add(BIO_C);

       Map<String, Double> newBioScores = MATCH_RANKING.scoreNewMatches(savedMatchBios, allUserBios, newMatchBios);

       System.out.println("MAP: " + newBioScores.toString());

  }

    //DO TESTS FOR LOTS OF WEIRD BIOS - save them as constants so that they can be reused
 
}
