package com.google.sps;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.gson.Gson;
import com.google.sps.data.DatastoreRepository;
import com.google.sps.data.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
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
public final class DatastoreRepositoryTest { 
    
  @Mock private HttpServletRequest request;

  @Mock private HttpServletResponse response; 

  private LocalServiceTestHelper localHelper =
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  private DatastoreService dataService = DatastoreServiceFactory.getDatastoreService();

  private static final User USER_A = new User("User A");
  private static final User MATCH_A = new User("Match A");

  public User addTestData(DatastoreRepository input) {
    User matchA = new User("Hadley");
    String matchAName = matchA.toString();
    input.addDatabase(matchAName);

    User matchB = new User("Sam");
    String matchBName = matchB.toString();
    input.addDatabase(matchBName);

    User matchC = new User("Andre");
    String matchCName = matchC.toString();
    input.addDatabase(matchCName);

    User matchD = new User("Jerry");
    String matchDName = matchD.toString();
    input.addDatabase(matchDName);
    
    User testUser = new User("Test User");
    return testUser;
  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    localHelper.setUp();
  }

  @After
  public void tearDown() throws Exception {
    localHelper.tearDown();
  }

  @Test
  public void addTestDataTest() {
    DatastoreRepository testDataRepo = new DatastoreRepository();

    DatastoreService sample = testDataRepo.getDatastore();
    User testUser = addTestData(testDataRepo);
    String expected = "{Test User=[Hadley, Sam, Andre, Jerry]}";

    String result = testDataRepo.profileToString(testUser);
    
    Assert.assertEquals(expected, result);
  }
 /*
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
  */
}
