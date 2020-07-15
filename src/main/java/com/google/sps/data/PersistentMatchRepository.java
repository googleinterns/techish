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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import java.util.*;

/**
 * This class saves User IDs of every User and their matches, to get the 
 * specific User, query the User Database with the ID
 */
public class PersistentMatchRepository implements MatchRepository {
  
  private final DatastoreService datastore;
  
  public PersistentMatchRepository() {
      datastore = DatastoreServiceFactory.getDatastoreService();
  }

  public void addUserMatchPairToDatabase(String userId, String matchId) {
    Entity userEntity = new Entity("User");
    userEntity.setProperty("userId", userId);
    userEntity.setProperty("matchIds", matchId);
    datastore.put(userEntity);
  }

  public void addMatchToAlreadyExistingUser(String userId, String matchId)  {
    Map<String, String> matchMap = fetchUserWithId(userId);
    String matchString = matchMap.get(userId);
    String[] matchArray = matchString.split(" ");

    //make sure match isn't already saved for user
    boolean matchAlreadySaved = false;
    for(String matchIdSaved : matchArray) {
        if(matchIdSaved.equals(matchId)) {
            matchAlreadySaved = true;
            break;
        }
    }

    if(!matchAlreadySaved) {
        matchString += " ";
        matchString += matchId;
    }

    //TODO EDIT OR DELETE OLD ENTITY
    //delete old entity
    // removeUser(userId);

    addUserMatchPairToDatabase(userId, matchString);
  }


//   public Collection<User> getMatchesAsCollection(String userId) {

//   }

  public Map<String, String> fetchMatchMap(PreparedQuery results) {
    Map<String, String> idMap = new HashMap<String, String>();

    for (Entity entity : results.asIterable()) {
        String userId = (String) entity.getProperty("userId");
        String matchIds = (String) entity.getProperty("matchIds");

        idMap.put(userId, matchIds);
    }
    return idMap;
  }

  // function to fetch the ID Map from the database
  public Map<String, String> fetchUserProfiles() {
    Query query = new Query("User");
    PreparedQuery results = datastore.prepare(query);
    Map<String, String> idMap = fetchMatchMap(results);
    return idMap;
  }

//   function that sets a query filter for the results in the datastore
  public PreparedQuery getQueryFilterForId(String userId) {
    Filter userNameFilter = new FilterPredicate("userId", FilterOperator.EQUAL, userId);
    Query query = new Query("User").setFilter(userNameFilter);
    PreparedQuery results = datastore.prepare(query);
    return results;
  }

    // function that fetches a single user, collection of users, filter on that user for name
  public Map<String, String> fetchUserWithId(String userId) {
    PreparedQuery results = getQueryFilterForId(userId);
    Map<String, String> userMap = fetchMatchMap(results);

    return userMap;
  }

  // function that adds user and match to the database if it does not exist already
  public void addMatch(User user, User match) {
    String userId = user.getId();
    String matchId = match.getId();
    Map<String, String> userMap = fetchUserWithId(userId);

    if(userMap.size() == 0) { //user is not already saved
        addUserMatchPairToDatabase(userId, matchId);
    } else { //user is already saved
        addMatchToAlreadyExistingUser(userId, matchId);
    }
  }

  public void removeUserProfile(String userId) throws Exception{
    PreparedQuery results = getQueryFilterForId(userId);
    int size = results.countEntities();

    /*
     *  if size is greater than 0 then there are entities available,
     *  if size is 0 then users do not equal, so an exception is thrown
    */
    if(size > 0){
        for (Entity entity : results.asIterable()) {
          Key current = entity.getKey();
          datastore.delete(current);
        }
    } else {
        throw new Exception("Can't remove " + userId + " not found in datastore.");
    }   
  }


  public void removeUser(String userId) throws Exception {
        removeUserProfile(userId);
  }

  public void removeMatch(User user, User match) throws Exception {
      //todo

  }

  public String getMatchesForUserAsString(User user) {
    String userId = user.getId();
    Map<String, String> userEntry = fetchUserWithId(userId);
    return userEntry.get(userId);
  }

  //gets User IDs of all matches & looks them up in the PersistentUserRepository. Returns Collection of Users.
  public Collection<User> getMatchesForUser(User user) {
      String matchesAsString = getMatchesForUserAsString(user);
      String[] matchArray = matchesAsString.split(" ");
      Collection<User> toReturn = new ArrayList<User>();
      PersistentUserRepository userRepository = new PersistentUserRepository();

      for(String userId : matchArray) {
          User newMatch = userRepository.fetchUserWithId(userId);
          toReturn.add(newMatch);
      }

      return toReturn;
  }

  public String toString() {
      return "todo: toString method for PersistentMatchRepository.";
  }

//   public Collection<User> getAllUsers() {
//     Collection<User> results = fetchUserProfiles();
//     return results;
//   }

//   public String toString() {
//     StringBuilder toReturn = new StringBuilder();
//     Collection <User> allUsers = getAllUsers();
//     for(User user : allUsers) {
//         toReturn.append(user.toString());
//         toReturn.append(" ");
//     }
//     return toReturn.toString();
//   }

}