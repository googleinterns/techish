package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.sps.data.Match;
import com.google.sps.data.Database;
import com.google.sps.data.Datastore;
import com.google.sps.data.StaticNameData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for creating new saved matches AND listing matches. */
@WebServlet("/matches")
public class MatchServlet extends HttpServlet {

  // TODO: wrapper class for datastore
  // hardcoded data
  StaticNameData myData = new StaticNameData();
  

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
  
    List<String> matches = myData.getFromDatabase();

    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(matches));
  }

//   private List<Match> getMatches() {
//       //TODO
//   }


}