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
import com.google.sps.data.ProfessionalProfile;
import com.google.sps.data.StudentProfile;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/profile")
public class ProfileBuilderServlet extends HttpServlet {

  private void storeStudentProfile(String fullname, String school, String major) {
    Entity profileEntity = new Entity("StudentProfile");
    long id = profileEntity.getKey().getId();
    profileEntity.setProperty("id", id);
    profileEntity.setProperty("fullname", fullname);
    profileEntity.setProperty("school", school);
    profileEntity.setProperty("major", major);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(profileEntity);
  }  

  private void storeProfessionalProfile(String fullname, String company, String careerTitle) {
    Entity profileEntity = new Entity("ProfessionalProfile");
    long id = profileEntity.getKey().getId();
    profileEntity.setProperty("id", id);
    profileEntity.setProperty("fullname", fullname);
    profileEntity.setProperty("company", company);
    profileEntity.setProperty("careerTitle", careerTitle);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(profileEntity);
  }


 @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String userType = request.getParameter("user-Type");
    if(userType.contains("student")){
        String fullname = request.getParameter("name-input");
        String school = request.getParameter("school-input");
        String major = request.getParameter("major-input");
        storeStudentProfile(fullname, school, major);
    }
    else if(userType.contains("professional")){
        String profName = request.getParameter("profName-input");
        String company = request.getParameter("company-input");
        String careerTitle = request.getParameter("careerTitle-input");
        storeProfessionalProfile(profName, company, careerTitle);
    }
    else{
        System.out.println("Error invalid userType");
    }

    response.sendRedirect("/index.html");
  }
}