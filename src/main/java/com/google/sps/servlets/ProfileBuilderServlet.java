package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.sps.data.PersistentUserRepository;
import com.google.sps.data.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for creating new profiles. */
@WebServlet("/profile")
public class ProfileBuilderServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    PersistentUserRepository emptyRepo = new PersistentUserRepository();
    String userType = request.getParameter("user-Type");
    
    if(userType.equals("Mentee")){
        String userName = request.getParameter("name-input");
        User currentUser = new User(userName);

        String userSchool = request.getParameter("school-input");
        currentUser.setSchool(userSchool);
        String userMajor = request.getParameter("major-input");
        currentUser.setMajor(userMajor);
        emptyRepo.addUser(currentUser);
    }
    else if(userType.contains("Mentor")){
        String userName = request.getParameter("profName-input");
        User currentUser = new User(userName);

        String userCompany = request.getParameter("company-input");
        currentUser.setCompany(userCompany);
        String userSpecialty = request.getParameter("specialty-input");
        currentUser.addSpecialty(userSpecialty);

        emptyRepo.addUser(currentUser);
    }
    else{
        throw new IOException("Error invalid usertype");
    }

    response.sendRedirect("/logged_in_homepage.html");
  }
}