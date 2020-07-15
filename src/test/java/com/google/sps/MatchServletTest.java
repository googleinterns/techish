package com.google.sps;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.google.sps.data.MatchRepository;
import com.google.sps.data.NonPersistentMatchRepository;
import com.google.sps.data.User;
import com.google.sps.servlets.MatchServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MatchServletTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private NonPersistentMatchRepository repository;
    private User testUser;
    private Gson gson;
    private ServletContext servletContext;
    private MatchServlet matchServlet;

    @Before
    public void setup() {
        request = Mockito.mock(HttpServletRequest.class); 
        response = Mockito.mock(HttpServletResponse.class);
        repository = new NonPersistentMatchRepository();
        testUser = repository.addTestData();
        gson = new Gson();

        //mock ServletContext
        servletContext = Mockito.mock(ServletContext.class);
        when(servletContext.getAttribute("matchRepository")).thenReturn(repository);

        // override getServletContext and getLoggedInUser
        matchServlet = new MatchServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
            public User getLoggedInUser() {
                return testUser;
            }
        };
    }

    @Test
    public void doGet_returnMatches() throws IOException, ServletException {
        //get expected result
        Collection<User> matches = repository.getMatchesForUser(testUser);
        String expected = gson.toJson(matches);

        //call doGet
        String result = doGetHelper(request, response, matchServlet);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void fullCycleTest_changeNumberMatches() throws IOException, ServletException {
        //First doGet Call
        String result = doGetHelper(request, response, matchServlet);
        int numMatches = matchesInString(result);
        Assert.assertEquals(4, numMatches);

        //DoPost to add 3 more matches
        User userA = new User("John");
        User userB = new User("Bob");
        User userC = new User("Cathy");
        userB.addSpecialty("Security");
        userB.addSpecialty("DoS");
        userC.addSpecialty("Artificial Intelligence");
        User[] newMatchesArray = {userA, userB, userC};
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
        String[] nullMatches = null;
        when(request.getParameterValues("new-matches")).thenReturn(nullMatches);
        matchServlet.doPost(request, response);

        verify(response, times(1)).sendRedirect("/logged_in_homepage.html");
    }
    
    @Test
    public void nullUser_doGet() throws IOException, ServletException {
        User nullUser = null;

        matchServlet = new MatchServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
            public User getLoggedInUser() {
                return nullUser;
            }
        };

        String expected = gson.toJson(null);

        //call doGet
        String result = doGetHelper(request, response, matchServlet);
        Assert.assertEquals(expected, result);
    }

     @Test
    public void nullUser_doPost() throws IOException, ServletException {
        User nullUser = null;

        matchServlet = new MatchServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
            public User getLoggedInUser() {
                return nullUser;
            }
        };

        try {
            //call doPost
            matchServlet.doPost(request, response);
            Assert.fail("Exception not caught");
        } catch (IOException e) {
            //nothing to do if exception is caught
        }
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
