// package com.google.sps;

// import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;
// import static org.mockito.Mockito.atLeast;

// import com.google.appengine.api.datastore.DatastoreService;
// import com.google.appengine.api.datastore.DatastoreServiceFactory;
// import com.google.appengine.api.datastore.Entity;
// import com.google.appengine.api.datastore.Key;
// import com.google.appengine.api.datastore.PreparedQuery;
// import com.google.appengine.api.datastore.Query;
// import com.google.appengine.api.datastore.Query.CompositeFilter;
// import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
// import com.google.appengine.api.datastore.Query.Filter;
// import com.google.appengine.api.datastore.Query.FilterOperator;
// import com.google.appengine.api.datastore.Query.FilterPredicate;
// import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
// import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
// import com.google.gson.Gson;
// import com.google.sps.data.User;
// import com.google.sps.data.PersistentUserRepository;
// import com.google.sps.servlets.ProfileBuilderServlet;
// import java.io.IOException;
// import java.io.PrintWriter;
// import java.io.StringWriter;
// import java.lang.NullPointerException;
// import java.util.Collection;
// import java.util.ArrayList;
// import java.util.List;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import org.junit.After;
// import org.junit.Assert;
// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.junit.runners.JUnit4;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.mockito.MockitoAnnotations;

// public class ProfileBuilderServletTest {
    
//   @Mock
//   private HttpServletRequest request;

//   @Mock
//   private HttpServletResponse response;


//   private final ProfileBuilderServlet profileBuilderServlet =
//     new ProfileBuilderServlet();

//   private LocalServiceTestHelper localHelper =
//     new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    
    
//   @Before
//   public void setUp() throws Exception {
//     request = Mockito.mock(HttpServletRequest.class);       
//     response = Mockito.mock(HttpServletResponse.class);

//     localHelper.setUp();
//   }

//   @After
//   public void tearDown() throws Exception {
//     localHelper.tearDown();
//   }
    
//   public void setRequestAsMentor() {

//     when(request.getParameter("user-Type")).thenReturn("Mentor");
//     when(request.getParameter("profName-input")).thenReturn("Jeff");
//     when(request.getParameter("company-input")).thenReturn("Google");
//     when(request.getParameter("specialty-input")).thenReturn("Security");
//   }

//   public void setRequestAsMentee() {

//     when(request.getParameter("user-Type")).thenReturn("Mentee");
//     when(request.getParameter("name-input")).thenReturn("Bobby");
//     when(request.getParameter("school-input")).thenReturn( "University of California Berkeley");
//     when(request.getParameter("major-input")).thenReturn("Computer Science");
//   }

//   @Test
//   public void doPostRedirectsUrl() throws IOException, ServletException {
//     setRequestAsMentor();
//     profileBuilderServlet.doPost(request, response);

//     verify(response, times(1)).sendRedirect("/logged_in_homepage.html");
//    }

//   @Test
//   public void doPostHasMissingUserInformation() throws IOException, ServletException {
//     DatastoreService dataService = DatastoreServiceFactory.getDatastoreService();
 
//     String userType = null;
//     when(request.getParameter("user-Type")).thenReturn(userType);
//     String mentorName = null;
//     when(request.getParameter("profName-input")).thenReturn(mentorName);
//     String company = null;
//     when(request.getParameter("company-input")).thenReturn(company);
//     String specialty = null;
//     when(request.getParameter("specialty-input")).thenReturn(specialty);

//     profileBuilderServlet.doPost(request, response);
//     verify(response, times(1)).sendRedirect("/logged_in_homepage.html");
//     Assert.assertEquals(0, dataService.prepare(new Query("User")).countEntities(withLimit(10)));
//    }
  
//   @Test
//   public void addMentorProfileThenCheckSize() throws IOException, ServletException {
//     DatastoreService dataService = DatastoreServiceFactory.getDatastoreService();
//     setRequestAsMentor();
//     profileBuilderServlet.doPost(request, response);

//     Assert.assertEquals(1, dataService.prepare(new Query("User")).countEntities(withLimit(10)));
//   }

//   @Test
//   public void addingMultipleProfiles() throws IOException, ServletException {
//     DatastoreService dataService = DatastoreServiceFactory.getDatastoreService();
//     setRequestAsMentee();
//     profileBuilderServlet.doPost(request, response);

//     setRequestAsMentor();
//     profileBuilderServlet.doPost(request, response);

//     Assert.assertEquals(7, dataService.prepare(new Query("User")).countEntities(withLimit(10)));
//   }

//   @Test
//   public void addMenteeProfileThenCheckSize() throws IOException, ServletException {
//     DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
//     setRequestAsMentee();
//     profileBuilderServlet.doPost(request, response);

//     Assert.assertEquals(1, ds.prepare(new Query("User")).countEntities(withLimit(10)));
//   }

//   @Test
//   public void checkMenteeInfoIsSame() throws IOException, ServletException {
//     DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
//     String userType = "Mentee";
//     when(request.getParameter("user-Type")).thenReturn(userType);
//     String menteeName = "Sandy Cane";
//     when(request.getParameter("name-input")).thenReturn(menteeName);
//     String school = "Stanford University";
//     when(request.getParameter("school-input")).thenReturn(school);
//     String major = "Computer Science";
//     when(request.getParameter("major-input")).thenReturn(major);

//     profileBuilderServlet.doPost(request, response);
//     PreparedQuery results = ds.prepare(new Query("User"));
    
//     Entity entity = results.asIterable().iterator().next();

//     Assert.assertEquals(menteeName, (String) entity.getProperty("name"));
//     Assert.assertEquals(school, (String) entity.getProperty("school"));
//     Assert.assertEquals(major, (String) entity.getProperty("major"));
//   }

//   @Test
//   public void checkMentorInfoIsSame() throws IOException, ServletException {
//     DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
//     String userType = "Mentor";
//     when(request.getParameter("user-Type")).thenReturn(userType);
//     String mentorName = "Jose Martinez";
//     when(request.getParameter("profName-input")).thenReturn(mentorName);
//     String company = "Google";
//     when(request.getParameter("company-input")).thenReturn(company);
//     String occupation = "Site Reliability Engineer";
//     when(request.getParameter("careerTitle-input")).thenReturn(occupation);
//     String[] specialties = new String[2];
//     specialties[0] = "Cloud Computing";
//     specialties[1] = "Infrastructure";
//     when(request.getParameterValues("specialty-input")).thenReturn(specialties);

//     profileBuilderServlet.doPost(request, response);
//     PreparedQuery results = ds.prepare(new Query("User"));
//     Entity entity = results.asIterable().iterator().next();
    
//     Assert.assertEquals(mentorName, (String) entity.getProperty("name"));
//     Assert.assertEquals(company, (String) entity.getProperty("company"));
//     Assert.assertEquals(occupation, (String) entity.getProperty("occupation"));
//     Collection<String>  specialtiesCollection = (Collection<String>) entity.getProperty("specialties");
//     String[] specialtiesResult = new String[specialtiesCollection.size()];
//     specialtiesResult = specialtiesCollection.toArray(specialtiesResult);
//     Assert.assertArrayEquals(specialties, specialtiesCollection.toArray(specialtiesResult));
//   }

// }
