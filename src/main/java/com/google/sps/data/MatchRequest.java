package com.google.sps.data;

public final class MatchRequest {
  // in the future, this will become a data structure but for MVP, we are just matching based on one
  // criteria
  private final String specialty;

  public MatchRequest(String area) {
    specialty = area;
  }

  public MatchRequest() {
      specialty = "";
  }

  public String getCriteria() {
    return specialty;
  }
}
