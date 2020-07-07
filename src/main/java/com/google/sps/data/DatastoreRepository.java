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
import com.google.sps.data.User;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * This class implements the match repository using the datastore 
 */
public class DatastoreRepository implements MatchRepository {
  private Map<User, List<User>> userMatches = new HashMap<User,List<User>>();
  private List<User> allProfiles;
  
  private static final Gson gson = new Gson();
  private DatastoreService datastore;
  private int maxProfiles = 10;
  
  public DatastoreRepository() {
      datastore = DatastoreServiceFactory.getDatastoreService();
  }
  public DatastoreService getDatastore() {
      return datastore;
  }

  // function to add profile to database
  public void addDatabase(String input) {
    Entity userEntity = new Entity("User");
    userEntity.setProperty("name", input);
    datastore.put(userEntity);
  }

  public List<User> fetchUserProfile() {
    Query query = new Query("User");
    PreparedQuery results = datastore.prepare(query);
    List<Entity> resultsList = results.asList(FetchOptions.Builder.withLimit(maxProfiles));

    List<User> userProfiles = new ArrayList<>();
    for (Entity entity : resultsList) {
        String name = (String) entity.getProperty("name");

        User userObject = new User(name);
        userProfiles.add(userObject);
    }
    return userProfiles;
  }

  public void addMatch(User user, User match) {

  }
  public void removeMatch(User user, User match) throws Exception {

  }

  public Collection<User> getMatchesForUser(User user) {
      List<User> emptyMatch = new ArrayList<User>();
      
      return emptyMatch;
  }
 
  public String profileToString(User inputUser) {
      allProfiles = fetchUserProfile();
      userMatches.put(inputUser, allProfiles);

    //   String jsonMatches = gson.ToJson(userMatches);

      return userMatches.toString();
  }
}