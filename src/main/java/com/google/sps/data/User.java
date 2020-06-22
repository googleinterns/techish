package com.google.sps.data;

import com.google.sps.data.Match;

public final class User {

  private final String name;

  public User(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }

  public boolean equals(User user) {
    return this.name == user.name;
  }

  public Match toMatch() {
      Match newMatch = new Match(name);
      return newMatch;
  }
}
