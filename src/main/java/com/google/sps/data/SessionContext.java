package com.google.sps.data;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.User;
import com.google.sps.data.NonPersistentUserRepository;
import com.google.sps.data.UserRepository;
import java.lang.Exception;

public class SessionContext {

    private UserService userService;

    public SessionContext() {
        userService = UserServiceFactory.getUserService();
    }

    /**
    * method that returns the current logged in User or null if
    * no user is logged in
    */
    public User getLoggedInUser() {
    User currentUser;
    NonPersistentUserRepository userRepository = new NonPersistentUserRepository();
    
    com.google.appengine.api.users.User currentGoogleUser = userService.getCurrentUser();
    if(currentGoogleUser == null) {
      currentUser = null;
    } else {
      currentUser = userRepository.getUser(currentGoogleUser);
    }

    return currentUser;
  }

  /**
  * returns user ID. If no user is logged in, throws Exception.
  */
  public String getLoggedInUserId() throws Exception {
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