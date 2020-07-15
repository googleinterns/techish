package com.google.sps.data;

import java.util.Collection;

/** interface for reading/writing match data that is abstracted from the database used. */
public interface MatchRepository {

  public void addMatch(String userId, User match);

  public void removeMatch(String userId, User match) throws Exception;

  public Collection<User> getMatchesForUser(String userId);

  public String toString();
}
