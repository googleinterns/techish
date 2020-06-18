package com.google.sps.data;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public interface Database {
  public void writeToDatabase(Entity toWrite);

  public PreparedQuery getFromDatabase(Query query);
}
