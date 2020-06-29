package com.google.sps.data;

import java.util.Collection;

/** interface for reading/writing match data that is abstracted from the database used. */
public interface MatchRepository {

  public void addMatch(User user, User match);

  public void removeMatch(User user, User match) throws Exception;

  public Collection<User> getMatchesForUser(User user);

  public String toString();
}
