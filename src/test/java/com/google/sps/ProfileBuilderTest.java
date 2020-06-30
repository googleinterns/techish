package com.google.sps;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ProfileBuilderTest {
    @Mock private HttpServletRequest request;
    
    @Mock private HttpServletResponse response;
    
    private UserLoginServlet userServlet =
      new UserLoginServlet();  
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void professionalUserChoice() throws IOException, ServletException {
        
        when(request.getParameterValues("user-Type")).thenReturn(newMatches);
        userServlet.doPost(request, response);
    }

}