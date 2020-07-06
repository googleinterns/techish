package com.google.sps.data;

import java.util.Collection;
import java.util.HashSet;

public final class User {

  private long id;
  private String profileType;
  private String name;
  private String school;
  private String major;
  private String company;
  private String occupation;
  private String specialty;
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

  public Collection<String> getProductArea() {
      return specialties;
  }

  public void setID(long id) {
      this.id = id;
  }
 
  public void setProfileType(String profileType) { 
      this.profileType = profileType;
  }
 
  public void setSchool(String school) { 
      this.school = school;
  }
 
   public void setMajor(String major) { 
      this.major = major;
  }
 
  public void setCompany(String company) {
      this.company = company;
  }
 
  public void setSpeciality(String specialty) {
      this.specialty = specialty;
  }

}
