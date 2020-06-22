package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.JsonObject;
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

  /*This method uses the UsersAPI to send a Get Request and see if the user is logged in,
  and is expected to return a string with the url and boolean value all in a Json string */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    JsonObject loginInfo = new JsonObject();
    UserService userService = UserServiceFactory.getUserService();
    Boolean isLoggedIn = userService.isUserLoggedIn();

    String url =
        isLoggedIn
            ? userService.createLogoutURL("/index.html")
            : userService.createLoginURL("/index.html");

    loginInfo.addProperty("Url", url);
    loginInfo.addProperty("Bool", isLoggedIn);
    response.setContentType("application/json");
    response.getWriter().println(loginInfo.toString());
  }
}
