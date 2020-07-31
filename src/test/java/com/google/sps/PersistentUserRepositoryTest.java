package com.google.sps;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.sps.data.PersistentUserRepository;
import com.google.sps.data.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

@RunWith(JUnit4.class)
public final class PersistentUserRepositoryTest { 
    
  @Mock private HttpServletRequest request;

  @Mock private HttpServletResponse response; 

  private LocalServiceTestHelper localHelper =
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  private static final User USER_A = new User("User A");
  private static final User MATCH_A = new User("Match A");

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    localHelper.setUp();

    USER_A.setId("1");
    MATCH_A.setId("2");
  }

  @After
  public void tearDown() throws Exception {
    localHelper.tearDown();
  }

  @Test
  public void addFakeMentorsTest() {
    PersistentUserRepository testDataRepo = new PersistentUserRepository();
    int expected = 5;
    int result = testDataRepo.getAllUsers().size();
    Assert.assertEquals(expected, result);
  }

  @Test
  public void addUserTest() {
    PersistentUserRepository repository = new PersistentUserRepository();
    repository.addUser(USER_A);
    int expected = 6;
    int result = repository.getAllUsers().size();
    Assert.assertEquals(expected, result);
  }

  @Test
  public void userSpecialtyWrittenBack() {
    PersistentUserRepository repository = new PersistentUserRepository();
    Collection<String> expected = new HashSet<>();
    expected.add("Machine Learning");
    expected.add("Systems");
    
    Assert.assertEquals(expected, repository.getAllUsers().iterator().next().getSpecialties());
  }

  @Test
  public void userIDWrittenBack() throws Exception {
    PersistentUserRepository repository = new PersistentUserRepository();
    User userA = new User("Sergey");
    String userID = "82129102381L";
    userA.setId(userID);
    repository.addUser(userA);

    Collection<User> allUsers = repository.getAllUsers();
   
    Assert.assertEquals(userID, repository.fetchUserWithId("82129102381L").getId());
  }

  @Test
  public void userCompanyWrittenBack() throws Exception {
    PersistentUserRepository repository = new PersistentUserRepository();
    User userA = new User("Sergey");
    String company = "Google";
    userA.setId("12345");
    userA.setCompany(company);
    repository.addUser(userA);

    Collection<User> allUsers = repository.getAllUsers();
    
    Assert.assertEquals(company, repository.fetchUserWithId("12345").getCompany());
  }
  
  @Test
  public void userOccupationWrittenBack() throws Exception {
    PersistentUserRepository repository = new PersistentUserRepository();
    User userA = new User("Larry");
    userA.setId("6655");
    String occupation = "Security Engineer";
    userA.setOccupation(occupation);
    repository.addUser(userA);

    Collection<User> allUsers = repository.getAllUsers();

    Assert.assertEquals(occupation, repository.fetchUserWithId("6655").getOccupation());
  }

  @Test
  public void userBioWrittenBack() throws Exception {
    PersistentUserRepository repository = new PersistentUserRepository();
    User userA = new User("Larry");
    userA.setId("6655");
    String bio = "I am an engineer at Google.";
    userA.setBio(bio);
    repository.addUser(userA);

    Collection<User> allUsers = repository.getAllUsers();

    Assert.assertEquals(bio, repository.fetchUserWithId("6655").getBio());
  }

  @Test
  public void userMapWrittenBack() throws Exception {
    PersistentUserRepository repository = new PersistentUserRepository();
    User userA = new User("Larry");
    userA.setId("6655");
    Map<String, Integer> bioMap = new HashMap<String, Integer>();
    bioMap.put("hello", 2);
    userA.setBioMap(bioMap);
    repository.addUser(userA);

    Collection<User> allUsers = repository.getAllUsers();

    Assert.assertEquals(bioMap, repository.fetchUserWithId("6655").getBioMap());
  }

  @Test
  public void removeUserThatExists() {
    PersistentUserRepository repository = new PersistentUserRepository();
    repository.addUser(USER_A);
    try {
      repository.removeUser(USER_A);
      int expected = 5;
      int result = repository.getAllUsers().size();
      Assert.assertEquals(expected, result);
    } catch (Exception e) {
      Assert.fail("Exception should not be thrown in removeUserThatExists");
    }
  }

  @Test
  public void removeUserThatDoesNotExist() {
    PersistentUserRepository repository = new PersistentUserRepository();
    try {
      repository.removeUser(USER_A);
      Assert.fail("Exception should be thrown in removeUserThatDoesNotExist()");
    } catch (Exception e) {
      // don't need to do anything here because test should catch exception
    }
  }

  @Test
  public void getAllUsersTest() {
      User userA = new User("John");
      userA.setId("44");
      User userB = new User("Bob");
      userB.setId("55");
      User userC = new User("Haley");
      userC.setId("66");

      PersistentUserRepository myRepo = new PersistentUserRepository();
      myRepo.addUser(userA);
      myRepo.addUser(userB);
      myRepo.addUser(userC);

      Collection<User> results = myRepo.getAllUsers();
      int resultsLength = results.size();

      int expectedLength = 8;

      Assert.assertEquals(resultsLength, expectedLength);
  }

  @Test
  public void addSameUserMultipleTimes() {
      User userA = new User("John");
      userA.setId("66788");

      PersistentUserRepository myRepo = new PersistentUserRepository();
      myRepo.addUser(userA);
      myRepo.addUser(userA);
      myRepo.addUser(userA);

      Collection<User> allUsers = myRepo.getAllUsers();
      int actualSize = allUsers.size();

      Assert.assertEquals(6, actualSize);
  }

  @Test
  public void multipleAddAndRemove() {
      User userA = new User("John");
      userA.setId("6677");
      User userB = new User("Bob");
      userB.setId("7766");

      PersistentUserRepository myRepo = new PersistentUserRepository();
      myRepo.addUser(userA);
      myRepo.addUser(userB);

      Collection<User> allUsers = myRepo.getAllUsers();
      int currentSize = allUsers.size();


      //myRepo should have 7 users stored
      Assert.assertEquals(7, currentSize);

      try {
        myRepo.removeUser(userB);
      } catch (Exception e) {
          Assert.fail("Can't remove user that does not exist");
      }
      allUsers = myRepo.getAllUsers();
      //myRepo should now only have 6 users stored
      
      currentSize = allUsers.size();
      Assert.assertEquals(6, currentSize);

      myRepo.addUser(userB);
      //myRepo should have 7 users stored again
      Assert.assertEquals(7, myRepo.getAllUsers().size());
  }

  @Test
  public void singleAddAndGetUser() {
      User userA = new User("Sundar");
      userA.setId("55555");

      PersistentUserRepository myRepo = new PersistentUserRepository();
      myRepo.addUser(userA);
      Collection<User> allUsers = myRepo.getAllUsers();
      int currentSize = allUsers.size();

      //myRepo should have 6 users stored
      Assert.assertEquals(6, currentSize);
  }

  @Test
  public void singleAddAndRemoveUser() {
      User userA = new User("Sundar");
      userA.setId("62221");

      PersistentUserRepository myRepo = new PersistentUserRepository();
      myRepo.addUser(userA);

      try {
        myRepo.removeUser(userA);
      } catch (Exception e) {
          Assert.fail("Can't remove user that does not exist");
      }
      Collection<User> allUsers = myRepo.getAllUsers();
      int currentSize = allUsers.size();

      //myRepo should have 5 users stored
      Assert.assertEquals(5, currentSize);
  }

  @Test
  public void addUserNameThatSameAsCompanyName() {
      User userA = new User("Mckinsey");
      userA.setId("122");
      User userB = new User("John");
      userB.setId("322");
      userB.setCompany("Mckinsey");

      PersistentUserRepository myRepo = new PersistentUserRepository();
      myRepo.addUser(userA);
      myRepo.addUser(userB);
    
      try {
        myRepo.removeUser(userB);
      } catch (Exception e) {
          Assert.fail("Can't remove user that does not exist");
      }

      Collection<User> allUsers = myRepo.getAllUsers();
      int currentSize = allUsers.size();

      //myRepo should have 6 users stored
      Assert.assertEquals(6, currentSize);
  }

  @Test
  public void userFetchIdMethodTest() {
    PersistentUserRepository repository = new PersistentUserRepository();
    User userA = new User("Bobby");
    String userID = "82129102381L";
    userA.setId(userID);
    repository.addUser(userA);
    String resultName = "";
    
    try{
        User userMatch = repository.fetchUserWithId(userID);
        resultName = userMatch.getName();
    } catch(Exception e) {
        Assert.fail("User with that ID does not exist");
    }
 
    Assert.assertEquals("Bobby", resultName);
  }

}
