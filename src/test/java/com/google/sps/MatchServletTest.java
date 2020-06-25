package com.google.sps;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.google.gson.Gson;
import com.google.sps.data.Match;
import com.google.sps.data.MatchRepository;
import com.google.sps.data.NonPersistentMatchRepository;
import com.google.sps.data.User;
import com.google.sps.servlets.MatchServlet;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MatchServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    private static MatchRepository testRepository;
    private static User testUser;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        NonPersistentMatchRepository repository = new NonPersistentMatchRepository();
        testUser = repository.addTestData();
        testRepository = repository;
    }

    @Test
    public void testMatchDoGet() throws IOException, ServletException {
        Gson gson = new Gson();
        Collection<Match> matches = testRepository.getMatchesForUser(testUser);
        String expected = gson.toJson(matches);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        MatchServlet matchServlet = new MatchServlet();
        matchServlet.init();
        matchServlet.doGet(request, response);

        String result = sw.getBuffer().toString().trim();

        pw.flush();
        assertEquals(expected, result);
    }

    @Test
    public void testMatchDoPost() throws IOException, ServletException {
        String[] matches = {"matchA", "matchB", "matchC"};
        when(request.getParameterValues("new-matches")).thenReturn(matches);

        MatchServlet matchServlet = new MatchServlet();
        matchServlet.init();
        matchServlet.doPost(request, response);

        verify(request, atLeast(1)).getParameterValues("new-matches");
        testMatchDoGet();
    }
}
