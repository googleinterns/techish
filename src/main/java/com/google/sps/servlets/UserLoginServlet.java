package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.SessionContext;
import com.google.gson.JsonObject;
import java.lang.Exception;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
    name = "UserAPI",
    description = "UserAPI: Login / Logout with UserService",
    urlPatterns = "/userapi")
public class UserLoginServlet extends HttpServlet {

  private SessionContext sessionContext;

  public UserLoginServlet() {
    this(SessionContext.getInstance());
  }
   // test only call the second one
  public UserLoginServlet(SessionContext passedVariable){
    sessionContext = passedVariable;
  }

  /*This method uses the UsersAPI to send a Get Request and see if the user is logged in,
  and is expected to return a string with the url and boolean value all in a Json string */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    JsonObject loginInfo = new JsonObject();
    UserService userService = UserServiceFactory.getUserService();
    Boolean isLoggedIn = userService.isUserLoggedIn();

    /* This checks to see if user is logged in and then decides whether to store
      a logged out url or log in url. A log out url is needed when a user is
      logged in so that they can log out of their user account on the platform.
      The same goes with being logged out, we make the url shown be the log in
      url so a user can log in to their account and use the platform.
    */
    if (isLoggedIn) {
      String loggedOutUrl = userService.createLogoutURL("/index.html");
      loginInfo.addProperty("LogOutUrl", loggedOutUrl);
      loginInfo.addProperty("LogInUrl", "");
      try {
        boolean hasProfileInDatastore = sessionContext.userProfileExists();
        loginInfo.addProperty("HasProfile", hasProfileInDatastore);
    
      } catch(Exception e) {
        System.err.println("Exception has been caught " + e);
      }
    } else {
      String loggedInUrl = userService.createLoginURL("/index.html");
   
    
      loginInfo.addProperty("LogInUrl", loggedInUrl);
      loginInfo.addProperty("LogOutUrl", "");
    }

    response.setContentType("application/json");
    response.getWriter().println(loginInfo.toString());
  }
}
