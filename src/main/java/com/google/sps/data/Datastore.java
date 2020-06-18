package com.google.sps.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Datastore implements Database {

  private DatastoreService datastore;

  public Datastore() {
    this.datastore = DatastoreServiceFactory.getDatastoreService();
  }

  public void writeToDatabase(Entity toWrite) {
    datastore.put(toWrite);
  }

  public PreparedQuery getFromDatabase(Query query) {
    return datastore.prepare(query);
  }
}
