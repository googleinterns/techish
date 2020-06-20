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
  private Map<User, List<Match>> userMatches;

  public NonPersistentMatchRepository() {
    userMatches = new ConcurrentHashMap();
  }

  // returns the User that data was added for
  public User addTestData() {
    Match matchA = new Match("John");
    Match matchB = new Match("Sarah");
    Match matchC = new Match("David");
    Match matchD = new Match("Kate");
    User testUser = new User("Test User");
    List<Match> allMatches = new ArrayList<Match>(Arrays.asList(matchA, matchB, matchC, matchD));
    userMatches.put(testUser, allMatches);
    return testUser;
  }

  public void addMatch(User user, Match match) {
    if (userMatches.containsKey(user)) {
      List<Match> currentMatches = userMatches.get(user);
      currentMatches.add(match);
      userMatches.replace(user, currentMatches);
    } else {
      List<Match> newMatch = new ArrayList<Match>(Arrays.asList(match));
      userMatches.put(user, newMatch);
    }
  }

  public void removeMatch(User user, Match match) throws Exception {
    if (userMatches.containsKey(user)) {
      List<Match> currentMatches = userMatches.get(user);
      currentMatches.remove(match);
      userMatches.replace(user, currentMatches);
    } else {
      throw new Exception("User does not exist");
    }
  }

  public Collection<Match> getMatchesForUser(User user) {
    if (userMatches.containsKey(user)) {
      return userMatches.get(user);
    } else {
      List<Match> emptyMatch = new ArrayList<Match>();
      userMatches.put(user, emptyMatch);
      return emptyMatch;
    }
  }

  public String toString() {
    return userMatches.toString();
  }
}
