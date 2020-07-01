package com.google.sps;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
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
        request = createMock(HttpServletRequest.class);
        String userType = "student";
        when(request.getParameter("user-Type")).thenReturn(userType);
        userServlet.doPost(request, response);

        //GET data from GET Request
        // create Get requst with student parmater
        //when anyone calls getparameter u return mock parameter
        
     
        
        // send get request
        userServlet.doGet(request, response);

        //Compare results


        Assert.assertEquals(1,1);
    }
    // Test for checking other parameters for if student 
 


    // Test to check if prof and getting parameters

    // Test for professional  calling function 
}
