package com.google.sps;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;


import com.google.sps.data.MatchRepository;
import com.google.sps.data.NonPersistentMatchRepository;
import com.google.gson.Gson;
import com.google.sps.algorithms.MatchQuery;
import com.google.sps.data.MatchRequest;
import com.google.sps.data.User;
import com.google.sps.servlets.MatchServlet;
import com.google.sps.servlets.NewMatchQueryServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
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
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

public class NewMatchQueryServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doPost_returnsNewMatches() throws IOException, ServletException {
        MatchQuery matchQuery = new MatchQuery();
        Collection<User> userSavedMatches = new ArrayList<User>();
        Collection<User> answer = matchQuery.query(new MatchRequest(), userSavedMatches);
 
        StringWriter StringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(StringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        //initialize variables
        MatchRepository testRepository;
        NonPersistentMatchRepository repository = new NonPersistentMatchRepository();
        User testUser = repository.addTestData();
        testRepository = repository;
         
        //mock ServletContext
        final ServletContext servletContext = Mockito.mock(ServletContext.class);

        //override getServletContext
        NewMatchQueryServlet myServlet = new NewMatchQueryServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };
        when(servletContext.getAttribute("matchRepository")).thenReturn(repository);
        when(servletContext.getAttribute("currentUser")).thenReturn(testUser);

        myServlet.doPost(request, response);

        Gson gson = new Gson();
        String expected = gson.toJson(answer);
        String result = StringWriter.getBuffer().toString().trim();

        printWriter.flush();
        Assert.assertEquals(expected, result);
    }
}
