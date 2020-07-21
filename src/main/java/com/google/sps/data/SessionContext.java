package com.google.sps.data;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.PersistentUserRepository;
import com.google.sps.data.User;
import com.google.sps.data.UserRepository;
import java.lang.Exception;

public class SessionContext {

  private final UserService userService;
  private final UserRepository userRepository;
  private static SessionContext instance = null;

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
    if(instance == null) {
      UserRepository userRepo = PersistentUserRepository.getInstance();
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

  public static SessionContext getInstance() {
      if (instance == null) {
          UserRepository userRepo = PersistentUserRepository.getInstance();
          instance = new SessionContext(userRepo);
      }
      return instance;
  }

  /**
  * method that returns the current logged in User or null if
  * no user is logged in
  */
  public User getLoggedInUser() {
    com.google.appengine.api.users.User currentGoogleUser = userService.getCurrentUser();
    return currentGoogleUser == null ? null : userRepository.getUser(currentGoogleUser);    
  }

  /**
  * returns user ID.
  */
  public String getLoggedInUserId() {

    User loggedInUser = getLoggedInUser();
<<<<<<< HEAD

    if(loggedInUser == null) {
        return null;
    }

    return loggedInUser.getId();
=======
    return loggedInUser == null ? null : loggedInUser.getId();
>>>>>>> e01b421b9880056d036c3aa6dbcab060f3196d31
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
            User userExists = PersistentUserRepository.getInstance().fetchUserWithId(id);
            if(userExists != null){
                return userExists.getId().equals(id);
            }
    }
    return false;
  }
}
