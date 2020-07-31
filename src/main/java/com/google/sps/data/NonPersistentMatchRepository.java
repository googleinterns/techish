package com.google.sps.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Match repository that stores matches in a ConcurrentHashMap so that we don't need to access
 * datastore for testing. All operations are thread-safe for the ConcurrentHashMap, but retrieval
 * operations do not entail locking.
 */
public class NonPersistentMatchRepository implements MatchRepository {
  private Map<String, List<User>> userMatches;

  public NonPersistentMatchRepository() {
    userMatches = new ConcurrentHashMap();
  }

  // returns the User that data was added for
  public User addTestData() {
    User matchA = new User("Scott Miller");
    matchA.addSpecialty("Database");
    User matchB = new User("Trevor Morgan");
    matchB.addSpecialty("Security");
    User matchC = new User("Twila Singleton");
    matchC.addSpecialty("Graphics");
    User matchD = new User("Rhonda Garrett");
    matchD.addSpecialty("DoS");
    matchD.addSpecialty("Security");
    User testUser = new User("Test User");
    testUser.setId("000");
    List<User> allMatches = new ArrayList<User>(Arrays.asList(matchA, matchB, matchC, matchD));
    userMatches.put(testUser.getId(), allMatches);
    return testUser;
  }

  //add a new match given User ID
  public void addMatch(String userId, User match) {
    if (userMatches.containsKey(userId)) {
      List<User> currentMatches = userMatches.get(userId);
      currentMatches.add(match);
      userMatches.replace(userId, currentMatches);
    } else {
      List<User> newMatch = new ArrayList<User>(Arrays.asList(match));
      userMatches.put(userId, newMatch);
    }
  }

  //add a new match given User
  public void addMatch(User user, User match) {
    addMatch(user.getId(), match);
  }

  //remove a match given User ID
  public void removeMatch(String userId, User match) throws Exception {
    if (userMatches.containsKey(userId)) {
      List<User> currentMatches = userMatches.get(userId);
      currentMatches.remove(match);
      userMatches.replace(userId, currentMatches);
    } else {
      throw new Exception("User does not exist");
    }
  }

  //remove a match given User
  public void removeMatch(User user, User match) throws Exception {
    removeMatch(user.getId(), match); 
  }

  //get matches for User given User ID
  public Collection<User> getMatchesForUser(String userId) {
    if (userMatches.containsKey(userId)) {
      return userMatches.get(userId);
    } else { 
      List<User> emptyMatch = new ArrayList<User>();
      userMatches.put(userId, emptyMatch);
      return emptyMatch;
    }
  }

  //get matches for User given User
  public Collection<User> getMatchesForUser(User user) {
    return getMatchesForUser(user.getId());
  }

  public String toString() {
    return userMatches.toString();
  }
}
