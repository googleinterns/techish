package com.google.sps;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.gson.Gson;
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
import java.util.ArrayList;
import java.util.Collection;
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
    // = PersistentUserRepository.getInstance();
    private User testUser;
    private Gson gson;
    private NewMatchQueryServlet newMatchQueryServlet;
    private SessionContext sessionContext;

    private LocalServiceTestHelper localHelper =
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setup() {
        localHelper.setUp();
        request = Mockito.mock(HttpServletRequest.class); 
        response = Mockito.mock(HttpServletResponse.class);
        sessionContext = Mockito.mock(SessionContext.class);
        testUser = new User("Test User");
        testUser.setId("000");
        testUser.setEmail("test@example.com");

        userRepository = PersistentUserRepository.getInstance();

        gson = new Gson();
        newMatchQueryServlet = new NewMatchQueryServlet();
        newMatchQueryServlet.testOnlySetContext(sessionContext);
    }

    @After
    public void tearDown() throws Exception {
        localHelper.tearDown();
    }

    @Test
    public void doPost_returnsNewMatches() throws IOException, ServletException {
        when(sessionContext.getLoggedInUser()).thenReturn(testUser);

        MatchQuery matchQuery = new MatchQuery();
        Collection<User> userSavedMatches = new ArrayList<User>();
        Collection<User> answer = matchQuery.query(testUser, new MatchRequest(), userSavedMatches);
 
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
