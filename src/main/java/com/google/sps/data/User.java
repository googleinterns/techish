package com.google.sps.data;

import java.util.Collection;
import java.util.HashSet;
package profileType;


public final class User {

public enum profileType{
    STUDENT("student"),
    MENTOR("mentor");

    private String profileInput;

    profileType(String input) {
        this.profileInput = input;
    }

    public String getProfileInput() {
        return this.profileInput;
    }
}

  private Long id;
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

  public Collection<String> getSpecialties() {
      return specialties;
  }

  public void setID(Long id) {
      this.id = id;
  }

  public Long getID() {
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
 
 
  public void setSpecialty(String specialty) {
      this.specialty = specialty;
  }

  public String getSpecialty() {
      return specialty;   
  }

}
