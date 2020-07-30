package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.sps.algorithms.AbuseDetection;
import com.google.sps.data.MatchRepository;
import com.google.sps.data.PersistentMatchRepository;
import com.google.sps.data.PersistentUserRepository;
import com.google.sps.data.SessionContext;
import com.google.sps.data.User;
import com.google.sps.data.UserRepository;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
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

  private MatchRepository matchRepository = PersistentMatchRepository.getInstance();  
  private SessionContext sessionContext = SessionContext.getInstance();
  private AbuseDetection abuseDetectionFeature;


  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  //method to override SessionContext with Mock FOR TESTING        
  public void testOnlySetContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }


  //method to override AbuseDetectionFeature with Mock FOR TESTING        
  public void testOnlySetAbuseDetection(AbuseDetection abuseFeature) {
    this.abuseDetectionFeature = abuseFeature;
  }


  /*
  * This method relates to rate limiting problem by analyzing the time of the 
  * request and limiting the amount of requests coming in. This is a problem
  * because malicious users can overload requests to the backend, so we created
  * this method to help limit the amount of requests coming to the backend.
  */
  public boolean analyzeTimeOfRequest(HttpServletRequest request) {
    return abuseDetectionFeature.addRequest(new Date());
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    Gson gson = new Gson();
    response.setContentType("application/json;");

    //is user logged in?
    if(!sessionContext.isUserLoggedIn()) {
        //return null for matches so that page redirects to logged out homepage
        response.getWriter().println(gson.toJson(null));
    } else {
        boolean isRequestPassed = analyzeTimeOfRequest(request);
        // if true, means the request was passed and added to the backend to get processed
        if(isRequestPassed) {
            Collection<User> matches = matchRepository.getMatchesForUser(sessionContext.getLoggedInUser());
            response.getWriter().println(gson.toJson(matches));
        }
        // request did not pass to the backend 
        else {
            System.err.println("Error too many requests, so request couldn't be added");
            response.sendRedirect("/errorPage.html");
        }
    }
  }

  @Override
  public synchronized void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    if (!sessionContext.isUserLoggedIn()) {
        System.err.println("Logged out user cannot access POST request.");
    } else {
      if (request.getParameterValues("new-matches") != null) {
        String[] matchesToSave = request.getParameterValues("new-matches");
        User userToAdd = sessionContext.getLoggedInUser();

        for (String matchName : matchesToSave) {
          User newMatch = new Gson().fromJson(matchName, User.class);
          matchRepository.addMatch(userToAdd, newMatch);
        }
      } else {
        System.err.println("new-matches is null in MatchServlet doPost()");
      }
      response.sendRedirect("/logged_in_homepage.html");
    }
  }

}
