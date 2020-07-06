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
  private Map<User, List<User>> userMatches;

  public NonPersistentMatchRepository() {
    userMatches = new ConcurrentHashMap();
  }

  // returns the User that data was added for
  public User addTestData() {
    User matchA = new User("Scott Miller");
    User matchB = new User("Trevor Morgan");
    User matchC = new User("Twila Singleton");
    User matchD = new User("Rhonda Garrett");
    User testUser = new User("Test User");
    List<User> allMatches = new ArrayList<User>(Arrays.asList(matchA, matchB, matchC, matchD));
    userMatches.put(testUser, allMatches);
    return testUser;
  }

  public void addMatch(User user, User match) {
    if (userMatches.containsKey(user)) {
      List<User> currentMatches = userMatches.get(user);
      currentMatches.add(match);
      userMatches.replace(user, currentMatches);
    } else {
      List<User> newMatch = new ArrayList<User>(Arrays.asList(match));
      userMatches.put(user, newMatch);
    }
  }

  public void removeMatch(User user, User match) throws Exception {
    if (userMatches.containsKey(user)) {
      List<User> currentMatches = userMatches.get(user);
      currentMatches.remove(match);
      userMatches.replace(user, currentMatches);
    } else {
      throw new Exception("User does not exist");
    }
  }

  public Collection<User> getMatchesForUser(User user) {
    if (userMatches.containsKey(user)) {
      return userMatches.get(user);
    } else {
      List<User> emptyMatch = new ArrayList<User>();
      userMatches.put(user, emptyMatch);
      return emptyMatch;
    }
  }

  public String toString() {
    return userMatches.toString();
  }
}
