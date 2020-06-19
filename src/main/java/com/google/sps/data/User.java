package com.google.sps.data;

public final class User {

  private final String name;
  //TODO: add any more data you want to save for each user

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
