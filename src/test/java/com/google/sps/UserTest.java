package com.google.sps;

import com.google.sps.data.User;
import com.google.sps.data.User.profileType;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Tests for User java */
@RunWith(JUnit4.class)
public final class UserTest {
  @Test
  public void userConstructorAndToString() {
    User myUser = new User("user name");
    String expected = "user name";
    Assert.assertEquals(expected, myUser.toString());
  }

  @Test
  public void addProductArea() {
      User userA = new User("John");
      userA.addProductArea("ML");
      Collection<String> productAreas = userA.getSpecialties();

      Assert.assertEquals(1, productAreas.size());
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

    Assert.assertFalse((userA).equals(userB));
  }
  
  @Test
  public void setProfileTypeANDGetProfileType(){
      User userA = new User("Tom");
      profileType expected = profileType.STUDENT;
      String input ="student";
      userA.setProfileType(input);
      profileType result = userA.getProfileType();
      
      Assert.assertEquals(expected, result);
  }

  @Test
  public void setIDAndGetID(){
      User userA = new User("Jeff");
      long expected = 82129102381L;
      userA.setID(expected);
      Long result = userA.getID();

      Assert.assertEquals(Long.valueOf(expected), result);
  }

  @Test
  public void setSchoolAndGetSchool(){
      User userA = new User("Ben");
      String expected = "Stanford University";
      userA.setSchool(expected);
      String result = userA.getSchool();

      Assert.assertEquals(expected, result);
  }

  @Test
  public void setMajorAndGetMajor(){
      User userA = new User("Tim");
      String expected = "Computer Science";
      userA.setMajor(expected);
      String result = userA.getMajor();

      Assert.assertEquals(expected, result);
  }

  @Test
  public void setCompanyandGetCompany(){
      User userA = new User("Sergey");
      String expected = "Google";
      userA.setCompany(expected);
      String result = userA.getCompany();

      Assert.assertEquals(expected, result);
  }

  @Test
  public void setOccupationAndGetOccupation(){
      User userA = new User("Larry");
      String expected = "Product Manager";
      userA.setOccupation(expected);
      String result = userA.getOccupation();

      Assert.assertEquals(expected, result);
  }
}
