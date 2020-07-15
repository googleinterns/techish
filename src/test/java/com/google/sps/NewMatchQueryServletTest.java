package com.google.sps;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import com.google.gson.Gson;
import com.google.sps.algorithms.MatchQuery;
import com.google.sps.data.MatchRepository;
import com.google.sps.data.MatchRequest;
import com.google.sps.data.NonPersistentMatchRepository;
import com.google.sps.data.User;
import com.google.sps.servlets.MatchServlet;
import com.google.sps.servlets.NewMatchQueryServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletConfig;
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

public class NewMatchQueryServletTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private NonPersistentMatchRepository repository;
    private User testUser;
    private Gson gson;
    private ServletContext servletContext;
    private NewMatchQueryServlet newMatchQueryServlet;

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
        newMatchQueryServlet = new NewMatchQueryServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
            public User getLoggedInUser() {
                return testUser;
            }
        };
    }

    @Test
    public void doPost_returnsNewMatches() throws IOException, ServletException {
        MatchQuery matchQuery = new MatchQuery();
        Collection<User> userSavedMatches = new ArrayList<User>();
        Collection<User> answer = matchQuery.query(new MatchRequest(), userSavedMatches);
 
        StringWriter StringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(StringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        newMatchQueryServlet.doPost(request, response);

        Gson gson = new Gson();
        String expected = gson.toJson(answer);
        String result = StringWriter.getBuffer().toString().trim();

        printWriter.flush();
        Assert.assertEquals(expected, result);
    }
}
