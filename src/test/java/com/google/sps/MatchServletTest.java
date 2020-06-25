package com.google.sps;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import com.google.gson.Gson;
import com.google.sps.data.Match;
import com.google.sps.data.MatchRepository;
import com.google.sps.data.NonPersistentMatchRepository;
import com.google.sps.data.User;
import com.google.sps.servlets.MatchServlet;
import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import org.junit.Assert;
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
    public void doGet_returnsSavedMatches() throws IOException, ServletException {
        Gson gson = new Gson();
        Collection<Match> matches = testRepository.getMatchesForUser(testUser);
        String expected = gson.toJson(matches);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        MatchServlet matchServlet = new MatchServlet();
        matchServlet.init();
        matchServlet.doGet(request, response);

        String result = stringWriter.getBuffer().toString().trim();

        printWriter.flush();
        Assert.assertEquals(expected, result);
    }

    @Test
    public void doPostOnce_storesNewMatches() throws IOException, ServletException {
        MatchServlet matchServlet = prepDoPost();
        matchServlet.doPost(request, response);
        verify(request, atLeast(1)).getParameterValues("new-matches");
    }

    @Test
    public void doPostTwice_storesNewMatches() throws IOException, ServletException {
        MatchServlet matchServlet = prepDoPost();
        matchServlet.doPost(request, response);
        matchServlet.doPost(request, response);
        verify(request, atLeast(2)).getParameterValues("new-matches");
    }

    private MatchServlet prepDoPost() throws IOException, ServletException {
        String[] matches = {"matchA", "matchB", "matchC"};
        when(request.getParameterValues("new-matches")).thenReturn(matches);

        MatchServlet matchServlet = new MatchServlet();
        matchServlet.init();

        return matchServlet;
    }

}
