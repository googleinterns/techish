package com.google.sps.data;

public final class MatchRequest {
  // in the future, this will become a data structure but for MVP, we are just matching based on one
  // criteria
  private final String productArea;

  public MatchRequest(String area) {
    productArea = area;
  }

  public String getCriteria() {
    return productArea;
  }
}
