package com.google.sps.algorithms;

import com.google.sps.data.MatchRequest;
import com.google.sps.data.User;
import java.util.ArrayList;
import java.util.Collection;

public final class FindMatchQuery {

  public Collection<User> query(MatchRequest request) {
    Collection<User> mock = new ArrayList<User>();
    User userA = new User(request.getCriteria());
    mock.add(userA);
    return mock;
  }
}
