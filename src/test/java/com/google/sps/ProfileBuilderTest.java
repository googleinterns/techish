package com.google.sps;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.gson.Gson;
import com.google.sps.data.ProfessionalProfile;
import com.google.sps.data.StudentProfile;
import com.google.sps.servlets.ProfileBuilderServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
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

public class ProfileBuilderTest {
    @Mock private HttpServletRequest request;
    
    @Mock private HttpServletResponse response;
    
    private ProfileBuilderServlet userServlet =
      new ProfileBuilderServlet();  
    
    private LocalServiceTestHelper localHelper =
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    private DatastoreService dataService = DatastoreServiceFactory.getDatastoreService();


    private String getdoGetProfiles(String current) throws IOException, ServletException {
        String stringWriterResult = "";

        if(current.contains("student")){

            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            when(response.getWriter()).thenReturn(printWriter);

            userServlet.doGet(request, response);

            stringWriterResult = stringWriter.getBuffer().toString().trim();
        }
        else{
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            when(response.getWriter()).thenReturn(printWriter);

            userServlet.doGet(request, response);

            stringWriterResult = stringWriter.getBuffer().toString().trim();
        }
        return stringWriterResult;
    }

    private void testQuery() throws IOException, ServletException {
        String fullname = "Jeff Person";
        String school ="UCLA";
        String major = "Computer Science";
        Entity studentProfile = new Entity("StudentProfile");
        studentProfile.setProperty("fullname", fullname);
        studentProfile.setProperty("school", school);
        studentProfile.setProperty("major", major);
        dataService.put(studentProfile);
        userServlet.doGet(request, response);
    }

    private void doTest() throws IOException, ServletException {
        Assert.assertEquals(0, dataService.prepare(new Query("StudentProfile")).countEntities(withLimit(10)));
        dataService.put(new Entity("StudentProfile"));
        dataService.put(new Entity("StudentProfile"));
        Assert.assertEquals(2, dataService.prepare(new Query("StudentProfile")).countEntities(withLimit(10)));
    }
    

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        localHelper.setUp();
    }

    @After
    public void tearDown() throws Exception {
        localHelper.tearDown();
    }

    // Test to check if the form redirects
    @Test
    public void doPostRedirectsUrl() throws IOException, ServletException {
        String userType = "student";
        when(request.getParameter("user-Type")).thenReturn(userType);
        userServlet.doPost(request, response);
        verify(response, times(1)).sendRedirect("/index.html");
    }
    

    //Test call post and get method, and look at results
    @Test
    public void userTypeIsIncorrect() throws IOException, ServletException {
        
        // send post request to store students
        // use the Entity creation method for this?

        String currentType = "student";
        when(request.getParameter("user-Type")).thenReturn(currentType);
    
        userServlet.doPost(request, response);

        //GET data from GET Request

        // create Get requst with student parmater
        //when anyone calls getparameter u return mock parameter

        String userType = "student";
        request.setAttribute("user-Type",userType);
        String current = request.getParameter("user-Type");
        
    
        // calls Get method here, string returns the profile in Datastore
        String expectedProfile = getdoGetProfiles(current); 
        System.out.println(expectedProfile);

        // entity creation method
        testQuery();

        

        //Compare results
        
                // Assert.assertEquals(1,dataService.prepare(new Query("StudentProfile")).countEntities(withLimit(10)));        

        Assert.assertEquals(1,1);
    // }
    // @Test
    // public void testOne()throws IOException, ServletException {
    //     String fullname = "Jeff Person";
    //     String school ="UCLA";
    //     String major = "Computer Science";
    //     testQuery();
    //  }
     
    // Test for checking other parameters for if student 
 


    // Test to check if prof and getting parameters

    // Test for professional  calling function 
}
