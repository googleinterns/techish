package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.sps.algorithms.AbuseDetection;
import com.google.sps.algorithms.MatchQuery;
import com.google.sps.data.MatchRepository;
import com.google.sps.data.MatchRequest;
import com.google.sps.data.PersistentMatchRepository;
import com.google.sps.data.PersistentUserRepository;
import com.google.sps.data.SessionContext;
import com.google.sps.data.User;
import com.google.sps.data.UserRepository;
import com.google.sps.servlets.MatchServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for displaying new mentor matches after user fills out match form. */
@WebServlet("/new-matches-query")
public class NewMatchQueryServlet extends HttpServlet {

  private MatchRepository matchRepository = PersistentMatchRepository.getInstance();  
  private SessionContext sessionContext = SessionContext.getInstance();
  private AbuseDetection abuseDetectionFeature = new AbuseDetection(Duration.ofSeconds(1),10);

  public void testOnlySetContext(SessionContext sessionContext) {
      this.sessionContext = sessionContext;
  }

  //method to override AbuseDetectionFeature with Mock FOR TESTING        
  public void testOnlySetAbuseDetection(AbuseDetection abuseFeature) {
    this.abuseDetectionFeature = abuseFeature;
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    Gson gson = new Gson();
    String jsonResponse = "";
    if(abuseDetectionFeature != null) {

        // Time of Requests passed into addRequest, boolean represents if added to back end or not        
        boolean isRequestPassed = abuseDetectionFeature.addRequest(new Date()); 

        // if true, means the request was added to the backend to get processed
        if(isRequestPassed) {
            // Convert the JSON to an instance of MatchRequest.
            MatchRequest matchRequest = getMatchRequest(request, gson);

            User currentUser = sessionContext.getLoggedInUser();

            Collection<User> userSavedMatches = matchRepository.getMatchesForUser(currentUser);

            // Find the possible matches.
            MatchQuery matchQuery = new MatchQuery();
            Collection<User> answer = matchQuery.query(matchRequest, userSavedMatches);

            // Convert the answer to JSON
            jsonResponse = gson.toJson(answer);
        }
        // request did not make it to the backend. it got blocked. 
        else {
            System.err.println("Error too many requests, so request couldn't be added");
            jsonResponse = gson.toJson("/error.html");
            response.sendRedirect("/error.html");
        }
    }
    else{
        System.err.println("Error abuseDetectionFeature is not initialized");
        response.sendRedirect("/index.html");
    }
    // Send the JSON back as the response
    response.setContentType("application/json");
    response.getWriter().println(jsonResponse);
  }

  private MatchRequest getMatchRequest(HttpServletRequest request, Gson gson) {
      MatchRequest toReturn = new MatchRequest();
      try {
          BufferedReader reader = request.getReader();
          toReturn = gson.fromJson(reader, MatchRequest.class);          
      } catch (Exception e) {
          System.err.println("Exception thrown in getMatchRequest");
      }

      return toReturn;
  }

}
