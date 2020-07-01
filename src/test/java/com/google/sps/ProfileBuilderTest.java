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
    // 
    @Test
    public void userTypeIsNotEmpty() throws IOException, ServletException {
        String userType = "!!!";
        when(request.getParameter("user-Type")).thenReturn(userType);
        userServlet.doPost(request, response);
        Assert.assertFalse(userType.isEmpty());
    }

     //Test for what happens when usertype isn't student / professional
    @Test
    public void userTypeIsIncorrect() throws IOException, ServletException {
        String result = request.getParameter("user-Type");
        when(request.getParameter("user-Type")).thenReturn(result);
        userServlet.doPost(request, response);
        String expected = "student";
        String expected2 = "professional";
        Assert.assertTrue(result.contains("student"));
    }
    // Test for checking other parameters for if student 

    // Test for student  calling function 

    // Test to check if prof and getting parameters

    // Test for professional  calling function 
}
