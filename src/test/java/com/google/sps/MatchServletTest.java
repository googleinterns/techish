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
    @Test
    public void doGet_returnMatches() throws IOException, ServletException {
        //Initialize variables
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class); 
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        MatchRepository testRepository;
        NonPersistentMatchRepository repository = new NonPersistentMatchRepository();
        User testUser = repository.addTestData();
        testRepository = repository;
        Gson gson = new Gson();

        MatchServlet matchServlet = new MatchServlet();
        matchServlet.init();

        //get expected result
        Collection<User> matches = testRepository.getMatchesForUser(testUser);
        String expected = gson.toJson(matches);

        //call doGet
        String result = doGetHelper(request, response, matchServlet);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void fullCycleTest_changeNumberMatches() throws IOException, ServletException {
        //Initialize variables
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class); 
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    
        MatchServlet matchServlet = new MatchServlet();
        matchServlet.init();

        //First doGet Call
        String result = doGetHelper(request, response, matchServlet);
        int numMatches = matchesInString(result);
        Assert.assertEquals(4, numMatches);

        //DoPost to add 3 more matches
        String[] newMatches = {"John", "Bob", "Cathy"};
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
        //Initialize variables
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class); 
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    
        MatchServlet matchServlet = new MatchServlet();
        matchServlet.init();

        String[] nullMatches = null;
        when(request.getParameterValues("new-matches")).thenReturn(nullMatches);
        matchServlet.doPost(request, response);

        verify(response, times(1)).sendRedirect("/logged_in_homepage.html");
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
