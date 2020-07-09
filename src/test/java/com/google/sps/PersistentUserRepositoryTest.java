package com.google.sps;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.gson.Gson;
import com.google.sps.data.PersistentUserRepository;
import com.google.sps.data.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
public final class PersistentUserRepositoryTest { 
    
  @Mock private HttpServletRequest request;

  @Mock private HttpServletResponse response; 

  private LocalServiceTestHelper localHelper =
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  private static final User USER_A = new User("User A");
  private static final User MATCH_A = new User("Match A");

  public void addFakeMentors(PersistentUserRepository input) {
    User mentorA = new User("Andre");
    mentorA.addProductArea("Machine Learning");
    mentorA.addProductArea("DoS");

    User mentorB = new User("Jerry");
    mentorB.addProductArea("Electrical Engineering");
    mentorB.addProductArea("DoS");

    User mentorC = new User("Julie");
    mentorC.addProductArea("Machine Learning");
    mentorC.addProductArea("Security");

    input.addUser(mentorA);
    input.addUser(mentorB);
    input.addUser(mentorC);
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
  public void addFakeMentorsTest() {
    PersistentUserRepository testDataRepo = new PersistentUserRepository();
    addFakeMentors(testDataRepo);
    String expected = "Andre Jerry Julie ";
    String result = testDataRepo.toString();
    Assert.assertEquals(expected, result);
  }

  @Test
  public void addUserTest() {
    PersistentUserRepository emptyRepo = new PersistentUserRepository();
    emptyRepo.addUser(USER_A);
    String expected = "User A ";
    String result = emptyRepo.toString();
    Assert.assertEquals(expected, result);
  }

  @Test
  public void removeUserThatExists() {
    PersistentUserRepository emptyRepo = new PersistentUserRepository();
    emptyRepo.addUser(USER_A);
    try {
      emptyRepo.removeUser(USER_A);
      String expected = "";
      String result = emptyRepo.toString();
      Assert.assertEquals(expected, result);
    } catch (Exception e) {
      Assert.fail("Exception should not be thrown in removeUserThatExists");
    }
  }

  @Test
  public void removeUserThatDoesNotExist() {
    PersistentUserRepository emptyRepo = new PersistentUserRepository();
    try {
      emptyRepo.removeUser(USER_A);
      Assert.fail("Exception should be thrown in removeUserThatDoesNotExist()");
    } catch (Exception e) {
      // don't need to do anything here because test should catch exception
    }
  }

  @Test
  public void getAllUsersTest() {
      User userA = new User("John");
      User userB = new User("Bob");
      User userC = new User("Haley");

      PersistentUserRepository myRepo = new PersistentUserRepository();
      myRepo.addUser(userA);
      myRepo.addUser(userB);
      myRepo.addUser(userC);

      Collection<User> results = myRepo.getAllUsers();
      int resultsLength = results.size();

      Collection<User> expected = new ArrayList<User>();
      expected.add(userA);
      expected.add(userB);
      expected.add(userC);
      int expectedLength = expected.size();

      Assert.assertEquals(resultsLength, expectedLength);
  }

  @Test
  public void addSameUserMultipleTimes() {
      User userA = new User("John");

      PersistentUserRepository myRepo = new PersistentUserRepository();
      myRepo.addUser(userA);
      myRepo.addUser(userA);
      myRepo.addUser(userA);

      Collection<User> allUsers = myRepo.getAllUsers();
      int actualSize = allUsers.size();

      //myRepo should only have 1 user stored
      Assert.assertEquals(1, actualSize);
  }

  @Test
  public void multipleAddAndRemove() {
      User userA = new User("John");
      User userB = new User("Bob");

      PersistentUserRepository myRepo = new PersistentUserRepository();
      myRepo.addUser(userA);
      myRepo.addUser(userB);

      Collection<User> allUsers = myRepo.getAllUsers();
      int currentSize = allUsers.size();


      //myRepo should have 2 users stored
      Assert.assertEquals(2, currentSize);

      try {
        myRepo.removeUser(userB);
      } catch (Exception e) {
          Assert.fail("Can't remove user that does not exist");
      }
      allUsers = myRepo.getAllUsers();
      //myRepo should now only have 1 user stored
      
      currentSize = allUsers.size();
      Assert.assertEquals(1, currentSize);

      myRepo.addUser(userB);
      allUsers = myRepo.getAllUsers();
      currentSize = allUsers.size();
      //myRepo should have 2 users stored again
      Assert.assertEquals(2, currentSize);
  }

  @Test
  public void singleAddAndGetUser() {
      User userA = new User("Sundar");

      PersistentUserRepository myRepo = new PersistentUserRepository();
      myRepo.addUser(userA);
      Collection<User> allUsers = myRepo.getAllUsers();
      int currentSize = allUsers.size();

      //myRepo should have 1 user stored
      Assert.assertEquals(1, currentSize);
  }

  @Test
  public void singleAddAndRemoveUser() {
      User userA = new User("Sundar");

      PersistentUserRepository myRepo = new PersistentUserRepository();
      myRepo.addUser(userA);

      try {
        myRepo.removeUser(userA);
      } catch (Exception e) {
          Assert.fail("Can't remove user that does not exist");
      }
      Collection<User> allUsers = myRepo.getAllUsers();
      int currentSize = allUsers.size();

      //myRepo should have 0 users stored
      Assert.assertEquals(0, currentSize);
  }

}
