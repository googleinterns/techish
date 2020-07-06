package com.google.sps.data;

import java.util.Collection;
import java.util.HashSet;

public final class User {

  private final long id;
  private final String profileType;
  private final String name;
  private final String school;
  private final String major;
  private final String company;
  private final String occupation;
  private final String speciality;
  private final Collection<String> specialties;

  public User(String name) {
    this.name = name;
    specialties = new HashSet<String>();
  }

  public String toString() {
    return name;
  }

  public boolean equals(User user) {
    return this.id == user.id;
  }

  public void addSpecialty(String toAdd) {
      specialties.add(toAdd);
  }

  public Collection<String> getSpecialties() {
      return specialties;
  }
  
   //Constructor for users that are students
  public User(String profileType, long id, String name, String school, String major) {
    this.profileType = profileType;
    this.id = id;
    this.name = name;
    this.school = school;
    this.major = major;
  }

 //Constructor for users that are professionals
  public User(String profileType, long id, String name, String company, String occupation, String specialty) {
    this.profileType = profileType;
    this.id = id;
    this.name = name;
    this.company = company;
    this.occupation = occupation;
    this.specialty = speciality;
  }

}
