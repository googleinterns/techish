package com.google.sps;

import com.google.sps.data.Match;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** */
@RunWith(JUnit4.class)
public final class MatchTest {
  @Test
  public void matchConstructorAndToString() {
    Match myMatch = new Match("match name");
    String expected = "match name";
    Assert.assertEquals(expected, myMatch.toString());
  }

  @Test
  public void matchEquals() {
    Match matchA = new Match("John");
    Match matchB = new Match("John");

    Assert.assertTrue(matchA.equals(matchB));
  }

  @Test
  public void matchNotEquals() {
    Match matchA = new Match("John");
    Match matchB = new Match("Not John");

    Assert.assertFalse(matchA.equals(matchB));
  }  
}
