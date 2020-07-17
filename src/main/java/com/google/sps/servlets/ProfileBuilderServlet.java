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
import com.google.sps.data.SessionContext;
import com.google.sps.data.User;
import java.io.IOException;
import java.lang.NullPointerException;
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
    String userType = request.getParameter("user-Type");

    if(userType != null) {
        if(userType.equals("Mentee")){

            String userName = request.getParameter("name-input");
            if(userName != null) {
                User currentUser = new User(userName);
                
                String id = SessionContext.getInstance().getLoggedInUserId();
                String userSchool = request.getParameter("school-input");
                String userMajor = request.getParameter("major-input");

                if(id != null) {
                    currentUser.setId(id);
                }
                if(userSchool != null ) {
                    currentUser.setSchool(userSchool);
                }
                if(userMajor != null) {
                    currentUser.setMajor(userMajor);
                }
                PersistentUserRepository.getInstance().addUser(currentUser);
            }
        }
        else if(userType.equals("Mentor")){

            String userName = request.getParameter("profName-input");
            if(userName != null) {
                User currentUser = new User(userName);
                
                String id = SessionContext.getInstance().getLoggedInUserId();
                String userCompany = request.getParameter("company-input");
                String userOccupation = request.getParameter("careerTitle-input");
                String[] userSpecialties = request.getParameterValues("specialty-input");
                if(id != null) {
                    currentUser.setId(id);
                }
                if(userCompany != null) {
                    currentUser.setCompany(userCompany);
                }
                if(userOccupation != null) {
                    currentUser.setOccupation(userOccupation);
                }
                if(userSpecialties != null) {
                    for(String specialty: userSpecialties) {
                        currentUser.addSpecialty(specialty);
                    }
                }
                
                PersistentUserRepository.getInstance().addUser(currentUser);
            } 
        }
        else{
            throw new IOException("Error invalid usertype '"+ userType + "' ");
        }
    }

    response.sendRedirect("/logged_in_homepage.html");
  }
}