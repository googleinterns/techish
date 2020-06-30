package com.google.sps.data;

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

}
