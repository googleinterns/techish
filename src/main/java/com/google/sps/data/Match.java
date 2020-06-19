package com.google.sps.data;

public final class Match {

  private final String name;

  public Match(String name) {
    this.name = name;
  }

  public String toString() {
      return name;
  }

  public boolean equals(Match match) {
      return this.name == match.name;
  }
}
