package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.sps.data.MatchRepository;
import com.google.sps.data.NonPersistentMatchRepository;
import com.google.sps.data.UserRepository;
import com.google.sps.data.NonPersistentUserRepository;
import com.google.sps.data.User;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for creating new saved matches AND listing matches. */
@WebServlet("/matches")
public class MatchServlet extends HttpServlet {

  private UserService userService;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    NonPersistentMatchRepository matchRepository = new NonPersistentMatchRepository();
    // User testUser = matchRepository.addTestData();
    User currentUser;

    //get logged in user
    NonPersistentUserRepository UserRepository = new NonPersistentUserRepository();
    userService = UserServiceFactory.getUserService();
    com.google.appengine.api.users.User currentGoogleUser = userService.getCurrentUser();
    if(currentGoogleUser == null) {
        currentUser = null;
    } else {
        currentUser = UserRepository.getUser(currentGoogleUser);
    }


    //Set MatchRepository and Current User as ServletContext so that they can be accessed by all servlets
    getServletContext().setAttribute("matchRepository", matchRepository);
    getServletContext().setAttribute("currentUser", currentUser);
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Gson gson = new Gson();
    ServletContext servletContext = getServletContext();
    User currentUser = (User) servletContext.getAttribute("currentUser");

    //is user logged in?
    if(currentUser == null) {
        //return null for matches so that page redirects to logged out homepage
        response.getWriter().println(gson.toJson(null));
    } else {
        
        MatchRepository testRepository = (MatchRepository) servletContext.getAttribute("matchRepository");
        
        Collection<User> matches = testRepository.getMatchesForUser(currentUser);

        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(matches));
    }
  }

  @Override
  public synchronized void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    ServletContext servletContext = getServletContext();
    MatchRepository testRepository = (MatchRepository) servletContext.getAttribute("matchRepository");
    User testUser = (User) servletContext.getAttribute("currentUser");

    if(request.getParameterValues("new-matches") != null) {
      String[] matchesToSave = request.getParameterValues("new-matches");

      for (String matchName : matchesToSave) {
        User newMatch = new Gson().fromJson(matchName, User.class);
        testRepository.addMatch(testUser, newMatch);
      }
    } else {
      System.err.println("new-matches is null in MatchServlet doPost()");
    }
    response.sendRedirect("/logged_in_homepage.html");
  }

}
