package com.google.sps;

import com.google.sps.data.NonPersistentUserRepository;
import com.google.sps.data.User;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** */
@RunWith(JUnit4.class)
public final class NonPersistentUserRepositoryTest {

  private static final User USER_A = new User("User A");

  @Test
  public void addFakeMentorsTest() {
    NonPersistentUserRepository testDataRepo = new NonPersistentUserRepository();
    testDataRepo.addFakeMentors();
    String expected = "Andre Jerry Julie ";
    Assert.assertEquals(expected, testDataRepo.toString());
  }

  @Test
  public void addUserTest() {
    NonPersistentUserRepository emptyRepo = new NonPersistentUserRepository();
    emptyRepo.addUser(USER_A);
    String expected = "User A ";
    Assert.assertEquals(expected, emptyRepo.toString());
  }

  @Test
  public void removeUserThatExists() {
    NonPersistentUserRepository emptyRepo = new NonPersistentUserRepository();
    emptyRepo.addUser(USER_A);
    try {
      emptyRepo.removeUser(USER_A);
      String expected = "";
      Assert.assertEquals(expected, emptyRepo.toString());
    } catch (Exception e) {
      Assert.fail("Exception should not be thrown in removeUserThatExists");
    }
  }

  @Test
  public void removeUserThatDoesNotExist() {
    NonPersistentUserRepository emptyRepo = new NonPersistentUserRepository();
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

      NonPersistentUserRepository myRepo = new NonPersistentUserRepository();
      myRepo.addUser(userA);
      myRepo.addUser(userB);
      myRepo.addUser(userC);

      Collection<User> expected = new ArrayList<User>();
      expected.add(userA);
      expected.add(userB);
      expected.add(userC);

      Assert.assertEquals(expected, myRepo.getAllUsers());
  }

  @Test
  public void addSameUserMultipleTimes() {
      User userA = new User("John");

      NonPersistentUserRepository myRepo = new NonPersistentUserRepository();
      myRepo.addUser(userA);
      myRepo.addUser(userA);
      myRepo.addUser(userA);

      Collection<User> allUsers = myRepo.getAllUsers();

      //myRepo should only have 1 user stored
      Assert.assertEquals(1, allUsers.size());
  }

  @Test
  public void multipleAddAndRemove() {
      User userA = new User("John");
      User userB = new User("Bob");

      NonPersistentUserRepository myRepo = new NonPersistentUserRepository();
      myRepo.addUser(userA);
      myRepo.addUser(userB);

      Collection<User> allUsers = myRepo.getAllUsers();

      //myRepo should have 2 users stored
      Assert.assertEquals(2, allUsers.size());

      try {
        myRepo.removeUser(userB);
      } catch (Exception e) {}
      allUsers = myRepo.getAllUsers();
      //myRepo should now only have 1 user stored
      Assert.assertEquals(1, allUsers.size());

      myRepo.addUser(userB);
      allUsers = myRepo.getAllUsers();
      //myRepo should have 2 users stored again
      Assert.assertEquals(2, allUsers.size());
  }

}
