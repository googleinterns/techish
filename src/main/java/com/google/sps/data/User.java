package com.google.sps.data;

import com.google.gson.Gson;
import java.util.Collection;
import java.util.HashSet;


public final class User {
  public static enum ProfileType {
    STUDENT, MENTOR;
  };

  private String id;
  private String company;
  private String major;
  private String name;
  private String email;
  private String occupation;
  private ProfileType userType;
  private String school;
  private Collection<String> specialties;

  public User(String name) {
    this.name = name;
    specialties = new HashSet<String>();
  }

  public String toString() {
    return new Gson().toJson(this);
  } 

  public ProfileType toEnum(String input) {
      if(input == "student" || input == "STUDENT") {
          input = "STUDENT";
      }
      else if(input == "mentor" || input == "MENTOR") {
          input = "MENTOR";
      }
      else {
          System.err.println("Invalid input type");
      }
      
      ProfileType result = ProfileType.valueOf(input);
      return result;
  }


  public boolean equals(Object user) {
      System.out.println("IN USER EQUALS");

      
    if (user instanceof User) {
        
        
        User toCompare = (User) user;
        
        System.out.println("comparing " + toCompare + " to " + this + " result: " + this.name.equals(toCompare.name));


        return this.name.equals(toCompare.name);
    }
    return false;
  }

  public void addSpecialty(String toAdd) {
      specialties.add(toAdd);
  }

  public Collection<String> getSpecialties() {
      return specialties;
  }

  public void setProfileType(ProfileType input) {
      if(input == ProfileType.STUDENT) {
        this.userType = ProfileType.STUDENT;
      }
      else if (input == ProfileType.MENTOR){
        this.userType = ProfileType.MENTOR;
      }
      else {
          System.err.println("Invalid profile type");
      }
  }

  public ProfileType getProfileType() {
      return this.userType;
  }

  public void setId(String id) {
      this.id = id;
  }

  public String getId() {
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

  public void setEmail(String email) {
      this.email = email;
  }

  public String getEmail() {
      return email;
  }
 
}
