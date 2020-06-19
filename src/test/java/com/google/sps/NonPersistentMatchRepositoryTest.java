package com.google.sps;

import com.google.sps.data.Match;
import com.google.sps.data.NonPersistentMatchRepository;
import com.google.sps.data.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** */
@RunWith(JUnit4.class)
public final class NonPersistentMatchRepositoryTest {

  private static final NonPersistentMatchRepository TEST_DATA_REPO =
      new NonPersistentMatchRepository();
  private static final NonPersistentMatchRepository EMPTY_REPO = new NonPersistentMatchRepository();
  private static final User TEST_USER = TEST_DATA_REPO.addTestData();
  private static final User USER_A = new User("User A");
  private static final Match MATCH_A = new Match("Match A");

  @Test
  public void addTestDataTest() {
    String expected = "{Test User=[John, Sarah, David, Kate]}";
    Assert.assertEquals(expected, TEST_DATA_REPO.toString());
  }

  @Test
  public void addMatchTest() {
    EMPTY_REPO.addMatch(USER_A, MATCH_A);
    String expected = "{User A=[Match A]}";
    Assert.assertEquals(expected, EMPTY_REPO.toString());
  }

  @Test
  public void removeMatchThatExists() {
    try {
      EMPTY_REPO.removeMatch(USER_A, MATCH_A);
      String expected = "{User A=[]}";
      Assert.assertEquals(expected, EMPTY_REPO.toString());
    } catch (Exception e) {
      Assert.fail("Exception should not be thrown in removeMatchThatExists");
    }
  }

  @Test
  public void removeMatchThatDoesNotExist() {
    try {
      EMPTY_REPO.removeMatch(USER_A, MATCH_A);
      Assert.fail("Expected exception in removeMatchThatDoesNotExist()");
    } catch (Exception e) {
      // don't need to do anything here because test should pass
    }
  }
}
