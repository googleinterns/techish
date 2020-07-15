package com.google.sps;

import com.google.sps.data.PersistentMatchRepository;
import com.google.sps.data.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/** */
@RunWith(JUnit4.class)
public final class PersistentMatchRepositoryTest {

  private static final User USER_A = new User("User A");
  private static final User MATCH_A = new User("Match A");

  private LocalServiceTestHelper localHelper =
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

 @Mock private HttpServletRequest request;

  @Mock private HttpServletResponse response; 

  @Before
  public void setUp() throws Exception {
    USER_A.setId("123");
    MATCH_A.setId("456");
    localHelper.setUp();
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    localHelper.tearDown();
  }


//   @Test
//   public void addTestDataTest() {
//     NonPersistentMatchRepository testDataRepo = new NonPersistentMatchRepository();
//     User testUser = testDataRepo.addTestData();
//     int expected = 4;

//     Assert.assertEquals(expected, testDataRepo.getMatchesForUser(testUser).size());
//   }

  @Test
  public void addMatchTest() {
    PersistentMatchRepository emptyRepo = new PersistentMatchRepository();
    emptyRepo.addMatch(USER_A, MATCH_A);
    int expected = 1;

    Assert.assertEquals(expected, emptyRepo.getMatchesForUser(USER_A).size());
  }

  @Test
  public void removeMatchThatExists() {
    PersistentMatchRepository emptyRepo = new PersistentMatchRepository();
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
    PersistentMatchRepository emptyRepo = new PersistentMatchRepository();
    try {
      emptyRepo.removeMatch(USER_A, MATCH_A);
      Assert.fail("Expected exception in removeMatchThatDoesNotExist()");
    } catch (Exception e) {
      // don't need to do anything here because test should catch exception
    }
  }
}
