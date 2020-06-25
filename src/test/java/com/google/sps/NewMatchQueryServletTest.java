package com.google.sps;

import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.google.sps.data.Match;
import com.google.sps.data.MatchRepository;
import com.google.sps.algorithms.MatchQuery;
import com.google.sps.data.MatchRequest;
import com.google.sps.data.NonPersistentMatchRepository;
import com.google.sps.data.User;
import com.google.sps.servlets.NewMatchQueryServlet;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


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
    public void testNewMatchDoPost() throws IOException, ServletException {
        MatchQuery matchQuery = new MatchQuery();
        Collection<User> answer = matchQuery.query(new MatchRequest()); 
 
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
 
        NewMatchQueryServlet myServlet =new NewMatchQueryServlet();
        myServlet.doPost(request, response);

        Gson gson = new Gson();
        String expected = gson.toJson(answer);
        String result = sw.getBuffer().toString().trim();
        Assert.assertEquals(expected, result);
    }
}
