package com.google.sps.algorithms;

import com.google.sps.data.MatchRequest;
import com.google.sps.data.User;
import java.util.ArrayList;
import java.util.Collection;

public final class MatchQuery {

  public Collection<User> query(MatchRequest request) {
    Collection<User> mock = new ArrayList<User>();
    User userA = new User("New Match 1");
    User userB = new User("New Match 2");
    User userC = new User("New Match 3");
    mock.add(userA);
    mock.add(userB);
    mock.add(userC);
    return mock;
  }
}
