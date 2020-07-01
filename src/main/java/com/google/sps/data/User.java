package com.google.sps.data;

import java.util.Collection;
import java.util.HashSet;

public final class User {

  private final String name;
  private final Collection<String> productArea;

  public User(String name) {
    this.name = name;
    productArea = new HashSet<String>();
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

  public Collection<String> getProductAreas() {
      return productArea;
  }

}
