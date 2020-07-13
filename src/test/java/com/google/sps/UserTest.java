package com.google.sps;

import com.google.gson.Gson;
import com.google.sps.data.User;
import com.google.sps.data.User.ProfileType;
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
    String expected = new Gson().toJson(myUser);
    
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
    String userA_Name = userA.toString();
    String userB_Name = userB.toString();

    Assert.assertEquals(userA_Name, userB_Name);
  }

  @Test
  public void userNotEquals() {
    User userA = new User("John");
    User userB = new User("Not John");

    Assert.assertNotEquals(userA, userB);
  }
  
  @Test
  public void setProfileTypeANDGetProfileType(){
      User userA = new User("Tom");
      String input ="student";
      ProfileType expected = userA.toEnum(input);
      userA.setProfileType(expected);
      ProfileType result = userA.getProfileType();
      
      Assert.assertEquals(expected, result);
  }

  @Test
  public void setIdAndGetId(){
      User userA = new User("Jeff");
      String expected = "82129102381L";
      userA.setId(expected);
      String result = userA.getId();

      Assert.assertEquals(expected, result);
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
