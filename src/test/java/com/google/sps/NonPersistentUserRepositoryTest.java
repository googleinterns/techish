package com.google.sps;

import com.google.sps.data.NonPersistentUserRepository;
import com.google.sps.data.User;
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
    } catch (Exception e) {
      Assert.fail("Exception should not be thrown in removeUserThatDoesNotExist()");
    }
  }
}
