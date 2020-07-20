package com.google.sps.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.sps.data.User;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class saves User IDs of every User and their matches as Strings.
 */
public class PersistentMatchRepository implements MatchRepository {
  
  private final DatastoreService datastore;
  private final Gson gson;
  private final PersistentUserRepository userRepository;
  private static PersistentMatchRepository instance = null;
  
  public PersistentMatchRepository() {
    datastore = DatastoreServiceFactory.getDatastoreService();
    gson = new Gson();
    userRepository = PersistentUserRepository.getInstance();
    // userRepository = new PersistentUserRepository();
    addTestData();
  }

  public static PersistentMatchRepository getInstance() {
        if(instance == null) {
          instance = new PersistentMatchRepository();
      }

      return instance;
  }

  public User addTestData() {
    User matchA = new User("Scott Miller");
    matchA.addSpecialty("Database");
    matchA.setId("11");
    User matchB = new User("Trevor Morgan");
    matchB.addSpecialty("Security");
    matchB.setId("22");
    User matchC = new User("Twila Singleton");
    matchC.addSpecialty("Graphics");
    matchC.setId("33");
    User matchD = new User("Rhonda Garrett");
    matchD.addSpecialty("DoS");
    matchD.addSpecialty("Security");
    matchD.setId("44");
    User testUser = new User("Test User");
    testUser.setId("000");
    testUser.setEmail("test@example.com");
    List<User> allMatches = new ArrayList<User>(Arrays.asList(matchA, matchB, matchC, matchD));

    userRepository.addUser(testUser);
    for(User match : allMatches) {
        addMatch(testUser, match);
        userRepository.addUser(match);
    }

    return testUser;
  }

  public void addMatch(User user, User match) {
    String userId = user.getId();
    String matchId = match.getId();
    Collection<String> userMatches = getMatchIdsForUser(userId);

    if(userMatches.isEmpty()) { //user is not already saved
      Collection<String> matchCollection = new HashSet<String>();
      matchCollection.add(matchId);
      addNewEntity(userId, matchCollection);
    } else { //user is already saved
      addMatchToExistingUser(userId, matchId);
    }
  }

  public void removeMatch(User user, User match) throws Exception {
    String userId = user.getId();
    String matchId = match.getId();
    Collection<String> currentMatches = getMatchIdsForUser(userId);

    if(!currentMatches.contains(matchId)) {
      throw new Exception("Cannot remove match that doesn't exist.");
    }
    currentMatches.remove(matchId);
    addNewEntity(userId, currentMatches);
  }

  /**
  * Gets User IDs of all matches & looks them up in the PersistentUserRepository. Returns Collection of Users.
  */
  public Collection<User> getMatchesForUser(User user) {
    Collection<String> matches = getMatchIdsForUser(user.getId());
    Collection<User> toReturn = new HashSet<User>();

    //return empty collection if user doesn't exist
    if(matches == null) {
      return toReturn;
    }

    for(String userId : matches) {
      try {
        User newMatch = userRepository.fetchUserWithId(userId);
        toReturn.add(newMatch);
      //catch exception if user is not in persistent repository
      } catch (Exception e) {
          System.err.println("UserID was not found in Persistent Repository: " + userId + ". Error: " + e.toString());
      }
      
    }
    return toReturn;
  }

  public String toString() {
    Map<String, Collection<String>> allMatches = fetchFullMap();
    return allMatches.toString();
  }

 /**
  * Adds an entity to the datastore, or overrides an existing 
  * entity when new matches are added.
  */
  private void addNewEntity(String userId, Collection<String> matchCollection) {
    String matchString = gson.toJson(matchCollection);

    Entity userEntity = new Entity("UserMatch", userId);
    userEntity.setProperty("userId", userId);
    userEntity.setProperty("matchIds", matchString);
    datastore.put(userEntity);
  }

 /**
  * Modifies the json string and adds a match to an existing user.
  */
  private void addMatchToExistingUser(String userId, String matchId) {
    Collection<String> currentMatches = getMatchIdsForUser(userId);
    currentMatches.add(matchId);
    addNewEntity(userId, currentMatches);
  }

  /**
  * Returns a Map of all the users and saved matches fitting 
  * PreparedQuery results.
  */
  private Map<String, Collection<String>> fetchMapFromQuery(PreparedQuery results) {
    Map<String, Collection<String>> idMap = new HashMap<String, Collection<String>>();

    for (Entity entity : results.asIterable()) {
      String userId = (String) entity.getProperty("userId");
      String matchIds = (String) entity.getProperty("matchIds");
      Collection<String> matchCollection = gson.fromJson(matchIds, HashSet.class);

      idMap.put(userId, matchCollection);
    }

    return idMap;
  }

  /**
  * Fetches the full ID Map from the database.
  */
  private Map<String, Collection<String>> fetchFullMap() {
    Query query = new Query("UserMatch");
    PreparedQuery results = datastore.prepare(query);
    Map<String, Collection<String>> idMap = fetchMapFromQuery(results);
    return idMap;
  }

  /**
  * Sets a query filter based on User ID for the results in the datastore.
  */
  private PreparedQuery getQueryFilterForId(String userId) {
    Filter userNameFilter = new FilterPredicate("userId", FilterOperator.EQUAL, userId);
    Query query = new Query("UserMatch").setFilter(userNameFilter);
    PreparedQuery results = datastore.prepare(query);
    return results;
  }

  /**
  * Fetches a single user's match map given their User ID.
  */
  private Map<String, Collection<String>> fetchMapFromId(String userId) {
    PreparedQuery results = getQueryFilterForId(userId);
    Map<String, Collection<String>> userMap = fetchMapFromQuery(results);

    return userMap;
  }

  private Collection<String> getMatchIdsForUser(String userId) {
    Map<String, Collection<String>> userEntry = fetchMapFromId(userId);
    Collection<String> matchIds = new HashSet<String>();

    if(userEntry.isEmpty()) {
      return matchIds;
    } else {
      return userEntry.get(userId);
    }
  }

}
