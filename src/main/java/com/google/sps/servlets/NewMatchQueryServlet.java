package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.sps.algorithms.FindMatchQuery;
import com.google.sps.data.MatchRequest;
import com.google.sps.data.User;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for displaying new mentor matches after user fills out match form. */
@WebServlet("/new-matches-query")
public class NewMatchQueryServlet extends HttpServlet {

  @Override
  public void init() {}

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Gson gson = new Gson();

    // Convert the JSON to an instance of MatchRequest.
    MatchRequest matchRequest = gson.fromJson(request.getReader(), MatchRequest.class);

    // Find the possible matches.
    FindMatchQuery findMatchQuery = new FindMatchQuery();
    Collection<User> answer = findMatchQuery.query(matchRequest);

    // Convert the times to JSON
    String jsonResponse = gson.toJson(answer);

    // Send the JSON back as the response
    response.setContentType("application/json");
    response.getWriter().println(jsonResponse);
  }
}
