package com.google.sps.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.User;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

/**
 * This class saves User IDs of every User and their matches, to get the 
 * specific User, query the User Database with the ID
 */
public class PersistentMatchRepository implements MatchRepository {
  
  private final DatastoreService datastore;
  private final Gson gson;
  private final PersistentUserRepository userRepository;
  
  public PersistentMatchRepository() {
      datastore = DatastoreServiceFactory.getDatastoreService();
      gson = new Gson();
      userRepository = new PersistentUserRepository();
  }

  private void addNewEntity(String userId, String matchString) {
    Entity userEntity = new Entity("User", userId);

    userEntity.setProperty("userId", userId);
    userEntity.setProperty("matchIds", matchString);
    datastore.put(userEntity);
  }

  private void addMatchToExistingUser(String userId, String matchId) {
    Collection<String> currentMatches = getMatchIdsForUser(userId);
    currentMatches.add(matchId);
    String matchString = gson.toJson(currentMatches);
    addNewEntity(userId, matchString);
  }

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

  // function to fetch the ID Map from the database
  private Map<String, Collection<String>> fetchFullMap() {
    Query query = new Query("User");
    PreparedQuery results = datastore.prepare(query);
    Map<String, Collection<String>> idMap = fetchMapFromQuery(results);
    return idMap;
  }

//   function that sets a query filter for the results in the datastore
  private PreparedQuery getQueryFilterForId(String userId) {
    Filter userNameFilter = new FilterPredicate("userId", FilterOperator.EQUAL, userId);
    Query query = new Query("User").setFilter(userNameFilter);
    PreparedQuery results = datastore.prepare(query);
    return results;
  }

    // function that fetches a single user, collection of users, filter on that user for name
  private Map<String, Collection<String>> fetchMapFromId(String userId) {
    PreparedQuery results = getQueryFilterForId(userId);
    Map<String, Collection<String>> userMap = fetchMapFromQuery(results);

    return userMap;
  }

  // function that adds user and match to the database if it does not exist already
  public void addMatch(User user, User match) {
    String userId = user.getId();
    String matchId = match.getId();
    Map<String, Collection<String>> userMap = fetchMapFromId(userId);

    if(userMap.size() == 0) { //user is not already saved
        Collection<String> matchCollection = new HashSet<String>();
        matchCollection.add(matchId);
        String jsonMatch = gson.toJson(matchCollection);
        addNewEntity(userId, jsonMatch);
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
    String matchString = gson.toJson(currentMatches);
    addNewEntity(userId, matchString);
  }

  private Collection<String> getMatchIdsForUser(String userId) {
    Map<String, Collection<String>> userEntry = fetchMapFromId(userId);
    return userEntry.get(userId);
  }

  //gets User IDs of all matches & looks them up in the PersistentUserRepository. Returns Collection of Users.
  public Collection<User> getMatchesForUser(User user)  {
      Collection<String> matches = getMatchIdsForUser(user.getId());
      Collection<User> toReturn = new HashSet<User>();

      //return empty collection if user doesn't exist
      if(matches == null) {
          return toReturn;
      }


      for(String userId : matches) {
          User newMatch = userRepository.fetchUserWithId(userId);
          toReturn.add(newMatch);
      }
      return toReturn;
  }

  public String toString() {
    Map<String, Collection<String>> allMatches = fetchFullMap();
    return allMatches.toString();
  }
}