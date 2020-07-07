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
    // String toReturn = "";
    // toReturn += name;
    // toReturn += ": ";

    // if(this.specialties.size() == 0) {
    //     toReturn += "no specialties";
    // } else {
    //     for(int i = 0; i < specialties.size() - 1; i++) {
    //         toReturn += specialties.get(i);
    //         toReturn += ", ";
    //     }

    //     toReturn += specialties.get(specialties.size() - 1);
    // }

    // return toReturn;

    return new Gson().toJson(this);

  }

  public String toJsonString() {
    Gson gson = new Gson();
    return gson.toJson(this);
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
