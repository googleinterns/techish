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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/profile")
public class ProfileBuilderServlet extends HttpServlet {

  private static final Gson gson = new Gson();
  
  private int maxProfiles = 10;


  private void storeStudentProfile(String fullname, String school, String major) {
    Entity profileEntity = new Entity("StudentProfile");
    profileEntity.setProperty("fullname", fullname);
    profileEntity.setProperty("school", school);
    profileEntity.setProperty("major", major);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(profileEntity);
  }  

  private void storeProfessionalProfile(String fullname, String company, String careerTitle) {
    Entity profileEntity = new Entity("ProfessionalProfile");
    profileEntity.setProperty("fullname", fullname);
    profileEntity.setProperty("company", company);
    profileEntity.setProperty("careerTitle", careerTitle);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(profileEntity);
  }

  private List<StudentProfile> fetchStudentProfile() {
        Query query = new Query("StudentProfile");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);
        List<Entity> resultsList = results.asList(FetchOptions.Builder.withLimit(maxProfiles));

        List<StudentProfile> students = new ArrayList<>();
        for (Entity entity : resultsList) {
            long id = entity.getKey().getId();
            String fullname = (String) entity.getProperty("fullname");
            String school = (String) entity.getProperty("school");
            String major = (String) entity.getProperty("major");

            StudentProfile studentObject = new StudentProfile(id, fullname, school, major);
            students.add(studentObject);
        }
        return students;
  }

  private List<ProfessionalProfile> fetchProfessionalProfile() {
        Query query = new Query("ProfessionalProfile");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);
        List<Entity> resultsList = results.asList(FetchOptions.Builder.withLimit(maxProfiles));

        List<ProfessionalProfile> professionals = new ArrayList<>();
        for (Entity entity : resultsList) {
            long id = entity.getKey().getId();
            String fullname = (String) entity.getProperty("fullname");
            String company = (String) entity.getProperty("company");
            String careerTitle = (String) entity.getProperty("careerTitle");

            ProfessionalProfile professionalObject = new ProfessionalProfile(id, fullname, company, careerTitle);
            professionals.add(professionalObject);
        }
        return professionals;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String userType = request.getParameter("user-Type");
    if(userType.contains("student")){
        List<StudentProfile> studentProfiles = fetchStudentProfile();

        response.setContentType("application/json;");
        String jsonStudentProfiles = gson.toJson(studentProfiles);
        response.getWriter().println(jsonStudentProfiles);
    }
    else if(userType.contains("professional")){
        List<ProfessionalProfile> professionalProfile = fetchProfessionalProfile();

        response.setContentType("application/json;");
        String jsonProfessionalProfiles = gson.toJson(professionalProfile);
        response.getWriter().println(jsonProfessionalProfiles);
    }
    else{
        System.err.println("Error invalid userType");
    }
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
        System.err.println("Error invalid userType");
    }

    response.sendRedirect("/index.html");
  }
}