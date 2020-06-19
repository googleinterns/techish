package com.google.sps.data;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import java.lang.Exception;
import java.util.Collection;

/**
* interface for reading/writing match data that is abstracted
* from the database used.
*/
public interface EditMatch {

    public void addMatch(User user, Match match);
    public void removeMatch(User user, Match match) throws Exception;
    public Collection<Match> getMatchesForUser(User user);

}
