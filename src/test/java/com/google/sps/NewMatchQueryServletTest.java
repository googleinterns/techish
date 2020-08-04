package com.google.sps;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.gson.Gson;
import com.google.sps.algorithms.AbuseDetection;
import com.google.sps.algorithms.MatchQuery;
import com.google.sps.data.MatchRepository;
import com.google.sps.data.MatchRequest;
import com.google.sps.data.PersistentMatchRepository;
import com.google.sps.data.PersistentUserRepository;
import com.google.sps.data.SessionContext;
import com.google.sps.data.User;
import com.google.sps.data.UserRepository;
import com.google.sps.servlets.MatchServlet;
import com.google.sps.servlets.NewMatchQueryServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class NewMatchQueryServletTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserRepository userRepository;
    private User testUser;
    private Gson gson;
    private NewMatchQueryServlet newMatchQueryServlet;
    private SessionContext sessionContext;
    private AbuseDetection abuseFeature;

    private LocalServiceTestHelper localHelper =
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setup() {
        localHelper.setUp();
        request = Mockito.mock(HttpServletRequest.class); 
        response = Mockito.mock(HttpServletResponse.class);
        sessionContext = Mockito.mock(SessionContext.class);
        abuseFeature = Mockito.mock(AbuseDetection.class);

        testUser = new User("Test User");
        testUser.setId("000");
        testUser.setEmail("test@example.com");

        userRepository = PersistentUserRepository.getInstance();

        gson = new Gson();
        newMatchQueryServlet = new NewMatchQueryServlet();
        newMatchQueryServlet.testOnlySetContext(sessionContext);
        newMatchQueryServlet.testOnlySetAbuseDetection(abuseFeature);
    }

    @After
    public void tearDown() throws Exception {
        localHelper.tearDown();
    }

    @Test
    public void doPostReturnError() throws IOException, ServletException {
        when(sessionContext.getLoggedInUser()).thenReturn(testUser);
        when(abuseFeature.addRequest(Mockito.any(Date.class))).thenReturn(false);
    
   
        MatchQuery matchQuery = new MatchQuery();
        Collection<User> userSavedMatches = new ArrayList<User>();
        Collection<User> answer = matchQuery.query(testUser, new MatchRequest(), userSavedMatches);
 
        StringWriter StringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(StringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        newMatchQueryServlet.doPost(request, response);

        Gson gson = new Gson();
        String expected = gson.toJson("/error.html");
        String result = StringWriter.getBuffer().toString().trim();

        printWriter.flush();
        Assert.assertEquals(expected, result);
    }

    @Test
    public void doPostAbuseFeatureNotInitialized() throws IOException, ServletException {
        AbuseDetection abuseInstance = null;
        newMatchQueryServlet.testOnlySetAbuseDetection(abuseInstance);
        when(sessionContext.getLoggedInUser()).thenReturn(testUser);
    
        StringWriter StringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(StringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        newMatchQueryServlet.doPost(request, response);

        verify(response, times(1)).sendRedirect("/index.html");
    }
}
