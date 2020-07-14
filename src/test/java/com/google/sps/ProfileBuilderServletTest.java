package com.google.sps;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.atLeast;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.gson.Gson;
import com.google.sps.data.User;
import com.google.sps.data.PersistentUserRepository;
import com.google.sps.servlets.ProfileBuilderServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ProfileBuilderServletTest {
    
  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;


  private ProfileBuilderServlet userServlet;

  private LocalServiceTestHelper localHelper =
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    
    
  @Before
  public void setUp() throws Exception {
    userServlet = new ProfileBuilderServlet(); 
    request = Mockito.mock(HttpServletRequest.class);       
    response = Mockito.mock(HttpServletResponse.class);

    localHelper.setUp();
  }

  @After
  public void tearDown() throws Exception {
    localHelper.tearDown();
  }
    
  public void addMentorProfile() {
    String userType = "Mentor";
    when(request.getParameter("user-Type")).thenReturn(userType);
    String mentorName = "Jeff";
    when(request.getParameter("profName-input")).thenReturn(mentorName);
    String company = "Google";
    when(request.getParameter("company-input")).thenReturn(company);
    String specialty = "Security";
    when(request.getParameter("specialty-input")).thenReturn(specialty);
  }

  public void addMenteeProfile() {
    String userType = "Mentee";
    when(request.getParameter("user-Type")).thenReturn(userType);
    String menteeName = "Bobby";
    when(request.getParameter("name-input")).thenReturn(menteeName);
    String school = "University of California Berkeley";
    when(request.getParameter("school-input")).thenReturn(school);
    String major = "Computer Science";
    when(request.getParameter("major-input")).thenReturn(major);
  }

  @Test
  public void doPostRedirectsUrl() throws IOException, ServletException {
    addMentorProfile();
    userServlet.doPost(request, response);

    verify(response, times(1)).sendRedirect("/logged_in_homepage.html");
   }
  
  @Test
  public void addMentorProfileThenCheckSize() throws IOException, ServletException {
    DatastoreService dataService = DatastoreServiceFactory.getDatastoreService();
    addMentorProfile();
    userServlet.doPost(request, response);

    Assert.assertEquals(1, dataService.prepare(new Query("User")).countEntities(withLimit(10)));
  }

  @Test
  public void addMenteeProfileThenCheckSize() throws IOException, ServletException {
    DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    addMenteeProfile();
    userServlet.doPost(request, response);

    Assert.assertEquals(1, ds.prepare(new Query("User")).countEntities(withLimit(10)));
  }

  @Test
  public void checkMenteeSchoolInfoIsSame() throws IOException, ServletException {
    DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    String userType = "Mentee";
    when(request.getParameter("user-Type")).thenReturn(userType);
    String menteeName = "Sandy Cane";
    when(request.getParameter("name-input")).thenReturn(menteeName);
    String school = "Stanford University";
    when(request.getParameter("school-input")).thenReturn(school);
    String major = "Computer Science";
    when(request.getParameter("major-input")).thenReturn(major);

    userServlet.doPost(request, response);
    Filter schoolFilter = new FilterPredicate("school", FilterOperator.EQUAL, school);
    PreparedQuery results = ds.prepare(new Query("User").setFilter(schoolFilter));
    String result = "";
    for (Entity entity : results.asIterable()) {
        result = (String) entity.getProperty("school");
    }
 
    Assert.assertEquals(school, result);
  }
    
  @Test
  public void checkMentorSpecialityIsSame() throws IOException, ServletException {
    DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    String userType = "Mentor";
    when(request.getParameter("user-Type")).thenReturn(userType);
    String mentorName = "Sergey Page";
    when(request.getParameter("profName-input")).thenReturn(mentorName);
    String company = "Google";
    when(request.getParameter("company-input")).thenReturn(company);
    String specialty = "Algorithmic Design";
    when(request.getParameter("specialty-input")).thenReturn(specialty);

    userServlet.doPost(request, response);

    Filter specialtyFilter = new FilterPredicate("specialties", FilterOperator.EQUAL, specialty);
    PreparedQuery results = ds.prepare( new Query("User").setFilter(specialtyFilter));
    Collection<String> specialtiesResult = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
        specialtiesResult = (Collection<String>) entity.getProperty("specialties");
    }
    int resultSize = specialtiesResult.size();
    Assert.assertEquals(1, resultSize);
  }
  
  @Test
  public void sendUserCheckIfNameMatches() throws IOException, ServletException {
    DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    String userType = "Mentor";
    when(request.getParameter("user-Type")).thenReturn(userType);
    String mentorName = "John Person";
    when(request.getParameter("profName-input")).thenReturn(mentorName);
    String company = "Google";
    when(request.getParameter("company-input")).thenReturn(company);
    String specialty = "Artificial Intelligence";
    when(request.getParameter("specialty-input")).thenReturn(specialty);

    userServlet.doPost(request, response);

    Filter nameFilter = new FilterPredicate("name", FilterOperator.EQUAL, mentorName);
    PreparedQuery results = ds.prepare(new Query("User").setFilter(nameFilter));
    String nameResult = "";
    for (Entity entity : results.asIterable()) {
        nameResult = (String) entity.getProperty("name");
    }
    Assert.assertEquals(mentorName, nameResult);
  }

// test to filter based on the name

// test that checks profile data is same as inputted.
}
