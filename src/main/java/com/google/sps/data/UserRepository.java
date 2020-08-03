package com.google.sps.data;

import java.util.Collection;
import java.util.Map;

/** interface for reading/writing user data that is abstracted from the database used. */
public interface UserRepository {

  public void addUser(User user);

  public void removeUser(User user) throws Exception;

  public Collection<User> getAllUsers();

  public String toString();

  // Uses 'googleUser' to return a User with same ID
  public User getUser(com.google.appengine.api.users.User googleUser);

  //method to add fake users for testing and for MVP demo
  public void addFakeMentors();

  public User fetchUserWithId(String id);

  public Map<String, Integer> getMapForUser(User user);
}
