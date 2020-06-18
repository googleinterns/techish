package com.google.sps.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaticNameData  {
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