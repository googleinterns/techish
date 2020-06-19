package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.sps.data.MockData;
import com.google.sps.data.Match;
import com.google.sps.data.User;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
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
    MockData myData = new MockData();
    User mockUser = new User("Mock User");
    myData.addMockData(mockUser);

    Collection<Match> matches = myData.getMatchesForUser(mockUser);

    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(matches));
  }
}
