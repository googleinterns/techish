package com.google.sps.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaticNameData {
  private List<String> names;

  public StaticNameData() {
    names = new ArrayList<String>(Arrays.asList("John", "Sarah", "Julie", "Daniel"));
  }

  public void writeToDatabase(String toWrite) {
    names.add(toWrite);
  }

  public List<String> getFromDatabase() {
    return names;
  }
}
