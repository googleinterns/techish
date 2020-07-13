package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.sps.data.MatchRepository;
import com.google.sps.data.NonPersistentMatchRepository;
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

    NonPersistentMatchRepository repository = new NonPersistentMatchRepository();
    User testUser = repository.addTestData();

    //real user login check
    userService = UserServiceFactory.getUserService();


    //Set MatchRepository and Current User as ServletContext so that they can be accessed by all servlets
    getServletContext().setAttribute("matchRepository", repository);
    getServletContext().setAttribute("currentUser", testUser);
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Gson gson = new Gson();

    //is user logged in?
    if(!userService.isUserLoggedIn()) {
        //return null for matches so that page redirects to logged out homepage
        response.getWriter().println(gson.toJson(null));
    } else {
        ServletContext servletContext = getServletContext();
        MatchRepository testRepository = (MatchRepository) servletContext.getAttribute("matchRepository");
        User testUser = (User) servletContext.getAttribute("currentUser");
        Collection<User> matches = testRepository.getMatchesForUser(testUser);

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
