package com.google.sps;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.sps.data.PersistentMatchRepository;
import com.google.sps.data.PersistentUserRepository;
import com.google.sps.data.User;
import com.google.sps.data.UserRepository;
import java.util.Collection;
import java.util.HashSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


/** */
@RunWith(JUnit4.class)
public final class PersistentMatchRepositoryTest {

  private static final User USER_A = new User("User A");
  private static final User MATCH_A = new User("Match A");
  private static final User MATCH_B = new User("Match B");
  private static final User MATCH_C = new User("Match C");
  private UserRepository userRepository;

  private LocalServiceTestHelper localHelper =
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

 @Mock private HttpServletRequest request;

  @Mock private HttpServletResponse response; 

  @Before
  public void setUp() throws Exception {
    USER_A.setId("123");
    MATCH_A.setId("456");
    MATCH_B.setId("789");
    MATCH_C.setId("101111");
    localHelper.setUp();
    MockitoAnnotations.initMocks(this);

    userRepository = PersistentUserRepository.getInstance();

    //add users to userRepository
    userRepository.addUser(USER_A);
    userRepository.addUser(MATCH_A);
    userRepository.addUser(MATCH_B);
    userRepository.addUser(MATCH_C);
  }

  @After
  public void tearDown() throws Exception {
    localHelper.tearDown();
  }

  @Test
  public void addMatchTest() {
    PersistentMatchRepository emptyRepo = PersistentMatchRepository.getInstance();
    emptyRepo.addMatch(USER_A, MATCH_A);
    int expected = 1;

    Assert.assertEquals(expected, emptyRepo.getMatchesForUser(USER_A).size());
  }

   @Test
  public void addMultipleMatches() {
    PersistentMatchRepository emptyRepo = PersistentMatchRepository.getInstance();
    emptyRepo.addMatch(USER_A, MATCH_A);
    emptyRepo.addMatch(USER_A, MATCH_B);
    emptyRepo.addMatch(USER_A, MATCH_C);
    int expected = 3;

    Assert.assertEquals(expected, emptyRepo.getMatchesForUser(USER_A).size());
  }

  @Test
  public void removeMatchThatExists() {
    PersistentMatchRepository emptyRepo = PersistentMatchRepository.getInstance();
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
    PersistentMatchRepository emptyRepo = PersistentMatchRepository.getInstance();
    try {
      emptyRepo.removeMatch(USER_A, MATCH_A);
      Assert.fail("Expected exception in removeMatchThatDoesNotExist()");
    } catch (Exception e) {
      // don't need to do anything here because test should catch exception
    }
  }

  @Test
  public void addSameMatchMultipleTimes_ShouldNotRepeat() {
    PersistentMatchRepository emptyRepo = PersistentMatchRepository.getInstance();
    emptyRepo.addMatch(USER_A, MATCH_A);
    emptyRepo.addMatch(USER_A, MATCH_A);
    emptyRepo.addMatch(USER_A, MATCH_A);
    int expected = 1;

    Assert.assertEquals(expected, emptyRepo.getMatchesForUser(USER_A).size());
  }

  @Test
  public void getMatchesForUserThatExists() {
    PersistentMatchRepository emptyRepo = PersistentMatchRepository.getInstance();
    emptyRepo.addMatch(USER_A, MATCH_A);
    Collection<User> expected = new HashSet<User>();
    expected.add(MATCH_A);

    Collection<User> actual = emptyRepo.getMatchesForUser(USER_A);
    Assert.assertEquals(expected.size(), actual.size());
  }

  @Test
  public void getMatchesForUserThatDoesNotExist() {
    PersistentMatchRepository emptyRepo = PersistentMatchRepository.getInstance();
    Collection<User> expected = new HashSet<User>();

    Collection<User> actual = emptyRepo.getMatchesForUser(USER_A);
    Assert.assertEquals(expected.size(), actual.size());
  }
}