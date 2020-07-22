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

  @Before
  public void setup() {
  
  }

  @Test
  public void incompleteTest() {
      MatchRanking matchRanking = new MatchRanking();
      Map<String, Integer> result = matchRanking.countWordInstances("Hi my name is  hadley, hadley, .. today is FRIDAY");

  }

 
}
