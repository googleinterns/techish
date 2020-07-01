package com.google.sps.data;

import java.util.ArrayList;

public final class User {

  private final String name;
  private final ArrayList<String> productArea;

  public User(String name) {
    this.name = name;
    productArea = new ArrayList<String>();
  }

  public String toString() {
    return name;
  }

  public boolean equals(User user) {
    return this.name == user.name;
  }

  public void addProductArea(String toAdd) {
      productArea.add(toAdd);
  }

  public ArrayList<String> getProductAreas() {
      return productArea;
  }

}
