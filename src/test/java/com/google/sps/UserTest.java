package com.google.sps;

import com.google.sps.data.User;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** */
@RunWith(JUnit4.class)
public final class UserTest {
  @Test
  public void userConstructorAndToString() {
    User myUser = new User("user name");
    String expected = "user name";
    Assert.assertEquals(expected, myUser.toString());
  }

  @Test
  public void addSpecialty() {
      User userA = new User("John");
      userA.addSpecialty("ML");
      Collection<String> specialties = userA.getSpecialties();

      Assert.assertEquals(1, specialties.size());
  }

  @Test
  public void userEquals() {
    User userA = new User("John");
    User userB = new User("John");

    Assert.assertTrue(userA.equals(userB));
  }

  @Test
  public void userNotEquals() {
    User userA = new User("John");
    User userB = new User("Not John");

    Assert.assertFalse(userA.equals(userB));
  }
}
