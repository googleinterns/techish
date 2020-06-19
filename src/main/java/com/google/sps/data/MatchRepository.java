package com.google.sps.data;

import java.util.Collection;

/** interface for reading/writing match data that is abstracted from the database used. */
public interface MatchRepository {

  public void addMatch(User user, Match match);

  public void removeMatch(User user, Match match) throws Exception;

  public Collection<Match> getMatchesForUser(User user);
}
