package com.google.sps.data;

import com.google.gson.Gson;
import java.util.Collection;
import java.util.ArrayList;

public final class User {

  private final String name;
  private final ArrayList<String> specialties;

  public User(String name) {
    this.name = name;
    specialties = new ArrayList<String>();
  }

  public String toString() {
    return new Gson().toJson(this);
  }    
      
  public boolean equals(User user) {
    return this.name == user.name;
  }

  public void addSpecialty(String toAdd) {
      specialties.add(toAdd);
  }

  public Collection<String> getSpecialties() {
      return specialties;
  }

}
