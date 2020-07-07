package com.google.sps.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class implements the match repository using the datastore 
 */
public class DatastoreRepository implements MatchRepository {
  private DatastoreService datastore;
  
  public DatastoreRepository() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  // returns the User that data was added for
  public User addTestData() {
    User matchA = new User("Hadley");
    String matchAName = matchA.toString();
    addDatabase(matchAName);

    User matchB = new User("Sam");
    String matchBName = matchB.toString();
    addDatabase(matchBName);

    User matchC = new User("Andre");
    String matchCName = matchC.toString();
    addDatabase(matchCName);

    User matchD = new User("Jerry");
    String matchDName = matchD.toString();
    addDatabase(matchDName);
    
    User testUser = new User("Test User");
    String testUserName = testUser.toString();
    addDatabase(testUserName);

  }
  // function to add profile to database
  public void addDatabase(String input) {
    Entity userEntity = new Entity("User");
    userEntity.setProperty("name", input);
    datastore.put(userEntity);
  }

  public void removeMatch(User user, User match) throws Exception {

  }

  public Collection<User> getMatchesForUser(User user) {

  }

  public String toString() {
      return 
  }

}