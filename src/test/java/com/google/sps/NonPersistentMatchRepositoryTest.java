package com.google.sps;

import com.google.sps.data.NonPersistentMatchRepository;
import com.google.sps.data.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** */
@RunWith(JUnit4.class)
public final class NonPersistentMatchRepositoryTest {

  private static final User USER_A = new User("User A");
  private static final User MATCH_A = new User("Match A");

  @Before
  public void init() {
    USER_A.setId("123");
  }

  @Test
  public void addTestDataTest() {
    NonPersistentMatchRepository testDataRepo = new NonPersistentMatchRepository();
    User testUser = testDataRepo.addTestData();
    int expected = 4;

    Assert.assertEquals(expected, testDataRepo.getMatchesForUser(testUser).size());
  }

  @Test
  public void addMatchTest() {
    NonPersistentMatchRepository emptyRepo = new NonPersistentMatchRepository();
    emptyRepo.addMatch(USER_A, MATCH_A);
    int expected = 1;

    Assert.assertEquals(expected, emptyRepo.getMatchesForUser(USER_A).size());
  }

  @Test
  public void removeMatchThatExists() {
    NonPersistentMatchRepository emptyRepo = new NonPersistentMatchRepository();
    emptyRepo.addMatch(USER_A, MATCH_A);
    try {
      emptyRepo.removeMatch(USER_A, MATCH_A);
      int expected = 0;

      Assert.assertEquals(expected, emptyRepo.getMatchesForUser(USER_A).size());
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
