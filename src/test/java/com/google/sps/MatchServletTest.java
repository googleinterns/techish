package com.google.sps;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import com.google.gson.Gson;
import com.google.sps.algorithms.AbuseDetection;
import com.google.sps.data.MatchRepository;
import com.google.sps.data.PersistentMatchRepository;
import com.google.sps.data.PersistentUserRepository;
import com.google.sps.data.SessionContext;
import com.google.sps.data.User;
import com.google.sps.data.UserRepository;
import com.google.sps.servlets.MatchServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.util.Collection;
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

public class MatchServletTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserRepository userRepository;
    private User testUser;
    private Gson gson;
    private MatchServlet matchServlet;
    private SessionContext sessionContext;
    private PersistentMatchRepository matchRepository;
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

        userRepository = PersistentUserRepository.getInstance();
        matchRepository = PersistentMatchRepository.getInstance();
        testUser = matchRepository.addTestData();

        gson = new Gson();
        matchServlet = new MatchServlet();
        matchServlet.testOnlySetContext(sessionContext);
    }

    @After
    public void tearDown() throws Exception {
      localHelper.tearDown();
    }

    @Test
    public void doGet_returnMatches() throws IOException, ServletException {
        when(sessionContext.isUserLoggedIn()).thenReturn(true);
        when(sessionContext.getLoggedInUser()).thenReturn(testUser);

        Collection<User> matches = matchRepository.getMatchesForUser(testUser);
        String expected = gson.toJson(matches);
    
        //call doGet
        String result = doGetHelper(request, response, matchServlet);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void doGet_UserNotLoggedIn() throws IOException, ServletException {
        when(sessionContext.isUserLoggedIn()).thenReturn(false);
        String expected = gson.toJson(null);
        String result = doGetHelper(request, response, matchServlet);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void fullCycleTest_changeNumberMatches() throws IOException, ServletException {
        when(sessionContext.isUserLoggedIn()).thenReturn(true);
        when(sessionContext.getLoggedInUser()).thenReturn(testUser);

        //First doGet Call
        String result = doGetHelper(request, response, matchServlet);
        int numMatches = matchesInString(result);
        Assert.assertEquals(4, numMatches);

        //DoPost to add 3 more matches
        User userA = new User("John");
        userA.setId("433");
        User userB = new User("Bob");
        userB.setId("344");
        User userC = new User("Cathy");
        userC.setId("777");
        userB.addSpecialty("Security");
        userB.addSpecialty("DoS");
        userC.addSpecialty("Artificial Intelligence");
        User[] newMatchesArray = {userA, userB, userC};
        for(User user : newMatchesArray) {
            userRepository.addUser(user);
        }
        String[] newMatches = {gson.toJson(userA), gson.toJson(userB), gson.toJson(userC)};
        when(request.getParameterValues("new-matches")).thenReturn(newMatches);
        matchServlet.doPost(request, response);
        verify(response, times(1)).sendRedirect("/logged_in_homepage.html");

        //doGet again to verify there are now 7 matches
        result = doGetHelper(request, response, matchServlet);
        numMatches = matchesInString(result);
        Assert.assertEquals(7, numMatches);
    }

    @Test
    public void nullParameterValues_ShouldNotThrowError() throws IOException, ServletException {
        when(sessionContext.isUserLoggedIn()).thenReturn(true);
        when(sessionContext.getLoggedInUser()).thenReturn(testUser);
        String[] nullMatches = null;
        when(request.getParameterValues("new-matches")).thenReturn(nullMatches);
        matchServlet.doPost(request, response);

        verify(response, times(1)).sendRedirect("/logged_in_homepage.html");
    }

    @Test
    public void doGetRequestReturnMatches() throws IOException, ServletException {
        when(sessionContext.isUserLoggedIn()).thenReturn(true);
        when(sessionContext.getLoggedInUser()).thenReturn(testUser);

        Collection<User> matches = matchRepository.getMatchesForUser(testUser);
        String expected = gson.toJson(matches);
    
        //call doGet
        String result = doGetHelper(request, response, matchServlet);

        Assert.assertEquals(expected, result);
    }
    @Test
    public void doGetRequestReturnError() throws IOException, ServletException {
        when(sessionContext.isUserLoggedIn()).thenReturn(true);
        when(sessionContext.getLoggedInUser()).thenReturn(testUser);

        doGetHelper(request, response, matchServlet);
        doGetHelper(request, response, matchServlet);
        doGetHelper(request, response, matchServlet);
        doGetHelper(request, response, matchServlet);
        doGetHelper(request, response, matchServlet);
        doGetHelper(request, response, matchServlet);
        doGetHelper(request, response, matchServlet);
        doGetHelper(request, response, matchServlet);
        doGetHelper(request, response, matchServlet);
        doGetHelper(request, response, matchServlet);
    
        //call doGet
        String result = doGetHelper(request, response, matchServlet);

        verify(response, times(1)).sendRedirect("/index.html");
    }

    private String doGetHelper(HttpServletRequest request, HttpServletResponse response, MatchServlet matchServlet)
        throws IOException, ServletException
     {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        matchServlet.doGet(request, response);

        String result = stringWriter.getBuffer().toString().trim();
        
        printWriter.flush();
        return result;
    }

    //count number of opening brackets in result string, which is equal to number of matches
    private int matchesInString(String result) {
        int numMatches = (result.length() - result.replace("{", "").length());
        return numMatches;
    }

}
