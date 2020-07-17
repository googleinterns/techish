package com.google.sps.data;

import java.util.Collection;

/** interface for reading/writing user data that is abstracted from the database used. */
public interface UserRepository {

  public void addUser(User user);

  public void removeUser(User user) throws Exception;

  public Collection<User> getAllUsers();

  public String toString();

  //creates a User from 'googleUser' if one doesn't exist, otherwise returns existing User with same ID
  public User getUser(com.google.appengine.api.users.User googleUser);

  public void addFakeMentors();
}
