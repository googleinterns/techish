package com.google.sps;

import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.google.sps.algorithms.MatchQuery;
import com.google.sps.data.MatchRequest;
import com.google.sps.data.User;
import com.google.sps.servlets.NewMatchQueryServlet;
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
    public void doPost_returnsNewMatches() throws IOException, ServletException {
        // MatchQuery matchQuery = new MatchQuery();
        // Collection<User> answer = matchQuery.query(new MatchRequest()); 
 
        // StringWriter StringWriter = new StringWriter();
        // PrintWriter printWriter = new PrintWriter(StringWriter);
        // when(response.getWriter()).thenReturn(printWriter);
 
        // NewMatchQueryServlet myServlet =new NewMatchQueryServlet();
        // myServlet.doPost(request, response);

        // Gson gson = new Gson();
        // String expected = gson.toJson(answer);
        // String result = StringWriter.getBuffer().toString().trim();

        // printWriter.flush();
        // Assert.assertEquals(expected, result);
    }
}
