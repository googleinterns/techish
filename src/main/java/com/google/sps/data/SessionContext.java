package com.google.sps.data;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.User;
import com.google.sps.data.NonPersistentUserRepository;
import com.google.sps.data.UserRepository;
import java.lang.Exception;

public class SessionContext {

  private final UserService userService;
  private final UserRepository userRepository;

  public SessionContext(UserRepository userRepository) {
    userService = UserServiceFactory.getUserService();
    this.userRepository = userRepository;
  }

  /**
  * method that returns the current logged in User or null if
  * no user is logged in
  */
  public User getLoggedInUser() {
    User currentUser;
    com.google.appengine.api.users.User currentGoogleUser = userService.getCurrentUser();
    
    if(currentGoogleUser == null) {
      currentUser = null;
    } else {
      currentUser = userRepository.getUser(currentGoogleUser);
    }

    return currentUser;
  }

  /**
  * returns user ID.
  */
  public String getLoggedInUserId() {
    User loggedInUser = getLoggedInUser();
    return loggedInUser.getId();
  }

  /**
  * method that returns boolean if there is a user logged in or not
  */
  public boolean isUserLoggedIn() {
    return userService.isUserLoggedIn();
  }
}
