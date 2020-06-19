package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.sps.data.Match;
import com.google.sps.data.NonPersistentMatchRepository;
import com.google.sps.data.User;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for creating new saved matches AND listing matches. */
@WebServlet("/matches")
public class MatchServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // hardcoded data
    NonPersistentMatchRepository testRepository = new NonPersistentMatchRepository();
    User testUser = new User("Test User");
    testRepository.addTestData(testUser);

    Collection<Match> matches = testRepository.getMatchesForUser(testUser);

    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(matches));
  }
}
