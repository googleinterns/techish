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

  private static final User USER_A = new User("User A");
  private static final Match MATCH_A = new Match("Match A");

  @Test
  public void addTestDataTest() {
    NonPersistentMatchRepository testDataRepo = new NonPersistentMatchRepository();
    User testUser = testDataRepo.addTestData();
    String expected = "{Test User=[John, Sarah, David, Kate]}";
    Assert.assertEquals(expected, testDataRepo.toString());
  }

  @Test
  public void addMatchTest() {
    NonPersistentMatchRepository emptyRepo = new NonPersistentMatchRepository();
    emptyRepo.addMatch(USER_A, MATCH_A);
    String expected = "{User A=[Match A]}";
    Assert.assertEquals(expected, emptyRepo.toString());
  }

  @Test
  public void removeMatchThatExists() {
    NonPersistentMatchRepository emptyRepo = new NonPersistentMatchRepository();
    emptyRepo.addMatch(USER_A, MATCH_A);
    try {
      emptyRepo.removeMatch(USER_A, MATCH_A);
      String expected = "{User A=[]}";
      Assert.assertEquals(expected, emptyRepo.toString());
    } catch (Exception e) {
      Assert.fail("Exception should not be thrown in removeMatchThatExists");
    }
  }

  @Test
  public void removeMatchThatDoesNotExist() {
    NonPersistentMatchRepository emptyRepo = new NonPersistentMatchRepository();
    try {
      emptyRepo.removeMatch(USER_A, MATCH_A);
      Assert.fail("Expected exception in removeMatchThatDoesNotExist()");
    } catch (Exception e) {
      // don't need to do anything here because test should catch exception
    }
  }
}
