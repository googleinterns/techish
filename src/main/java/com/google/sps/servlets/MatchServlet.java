package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;  
import com.google.sps.data.MatchRepository;
import com.google.sps.data.NonPersistentMatchRepository;
import com.google.sps.data.User;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for creating new saved matches AND listing matches. */
@WebServlet("/matches")
public class MatchServlet extends HttpServlet {

  private static MatchRepository testRepository;
  private static User testUser;

  @Override
  public void init() {
    NonPersistentMatchRepository repository = new NonPersistentMatchRepository();
    testUser = repository.addTestData();
    testRepository = repository;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    Collection<User> matches = testRepository.getMatchesForUser(testUser);

    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(matches));
  }

  @Override
  public synchronized void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

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
