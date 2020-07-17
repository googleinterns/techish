package com.google.sps.data;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.PersistentUserRepository;
import com.google.sps.data.User;
import com.google.sps.data.UserRepository;
import java.lang.Exception;

public class SessionContext {

  private final UserService userService;
  private static UserRepository userRepository;
  private static SessionContext instance;

  /**
  * Constructor that initializes the user repository.
  */
  public SessionContext(UserRepository userRepository) {
    userService = UserServiceFactory.getUserService();
    this.userRepository = userRepository;
  }
  /**
  * Getter method for getting the instance
  */

  public static SessionContext getInstance() {
      if(instance == null){
           UserRepository userRepo = new PersistentUserRepository();
           instance = new SessionContext(userRepo);
      }
      return instance;
  }
  
  /**
  * Overload constructor with UserService for testing.
  */
  public SessionContext(UserRepository userRepository, UserService userService) {
      this.userService = userService;
      this.userRepository = userRepository;
  }

  /**
  * method that returns the current logged in User or null if
  * no user is logged in
  */
  public User getLoggedInUser() {
    com.google.appengine.api.users.User currentGoogleUser = userService.getCurrentUser();

    if(currentGoogleUser == null) {
      return null;
    } else {
        return userRepository.getUser(currentGoogleUser);
    
    }
  }

  /**
  * returns user ID.
  */
  public String getLoggedInUserId() {
    User loggedInUser = getLoggedInUser();
    if(loggedInUser == null) {
        return null;
    }
    return loggedInUser.getId();
  }

  /**
  * method that returns boolean if there is a user logged in or not
  */
  public boolean isUserLoggedIn() {
    return userService.isUserLoggedIn();
  }

  public boolean userProfileExists() {
    String id = getLoggedInUserId();
    if(id != null) {
        try {
            User userExists = PersistentUserRepository.getInstance().fetchUserWithId(id);

            return userExists.getId().equals(id);

        } catch (Exception e){
            System.err.println("Caught Exception" + e);
        }
    }
    return false;
  }

}
