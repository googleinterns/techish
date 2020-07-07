package com.google.sps.data;

import java.util.Collection;
import java.util.HashSet;


public final class User {
  public static enum ProfileType {
    STUDENT, MENTOR;
  };

  private long id;
  private String company;
  private String major;
  private String name;
  private String occupation;
  private ProfileType userType;
  private String school;
  private Collection<String> specialties;

  public User(String name) {
    this.name = name;
    specialties = new HashSet<String>();
  }

  public String toString() {
    return name;
  }

  public boolean equals(User user) {
    return this.name == user.name;
  }

  public void addProductArea(String toAdd) {
      specialties.add(toAdd);
  }

  public Collection<String> getSpecialties() {
      return specialties;
  }

  public void setProfileType(String input) {
      if(input == "student"){
        this.userType = ProfileType.STUDENT;
      }
      else {
        this.userType = ProfileType.MENTOR;
      }
  }

  public ProfileType getProfileType() {
      return this.userType;
  }

  public void setID(long id) {
      this.id = id;
  }

  public long getID() {
      return id;
  }
 
  public void setSchool(String school) { 
      this.school = school;
  }
 
  public String getSchool() {
      return school;
  }

   public void setMajor(String major) { 
      this.major = major;
  }
 
  public String getMajor() {
      return major;
  }

  public void setCompany(String company) {
      this.company = company;
  }
  
  public String getCompany() {    
      return company;
  }

  public void setOccupation(String occupation) {
      this.occupation = occupation;
  }
  
  public String getOccupation() {    
      return occupation;
  }
 
}
