package com.google.sps.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for displaying new mentor matches. */
@WebServlet("/new-matches-query")
public class NewMatchQueryServlet extends HttpServlet {

  @Override
  public void init() {}

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // todo

    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(matches));
  }
}
