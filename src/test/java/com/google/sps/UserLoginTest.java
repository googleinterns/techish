package com.google.sps;

import static org.mockito.Mockito.when;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.sps.data.User;
import com.google.sps.data.UserRepository;
import com.google.sps.servlets.UserLoginServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit4.class)
public class UserLoginTest {

  @Mock private HttpServletRequest request;

  @Mock private HttpServletResponse response;

  private UserLoginServlet userServlet =
    new UserLoginServlet();  

  private LocalServiceTestHelper localHelper =
    new LocalServiceTestHelper(new LocalUserServiceTestConfig())
        .setEnvIsAdmin(true);
  
  
  private JsonObject getLoginServletResponse() throws ServletException, IOException {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    /*
    Mocking the print writer bc
    UserLoginServlet writes its reponse in response.getWriter() 
    */
    when(response.getWriter()).thenReturn(printWriter);

    userServlet.doGet(request, response);

    String responseStr = stringWriter.getBuffer().toString().trim();
    JsonElement responseJsonElement = new JsonParser().parse(responseStr);
    JsonObject responseJsonObject = responseJsonElement.getAsJsonObject();
    
    return responseJsonObject;
  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    localHelper.setUp();

  }
  
  @After
  public void tearDown() throws Exception {
    localHelper.tearDown();
  }


  @Test
  public void userLoggedInReturningLogOut() throws ServletException, IOException  {
    localHelper.setEnvIsLoggedIn(true);
    
    JsonObject responseJsonObject = getLoginServletResponse();
    String logInUrl = responseJsonObject.get("LogInUrl").getAsString();
    String logOutUrl = responseJsonObject.get("LogOutUrl").getAsString();
   
    Assert.assertTrue(logOutUrl.contains("logout"));
    Assert.assertTrue(logInUrl.isEmpty());
  }

   @Test
  public void userLoggedOutReturningLogIn() throws ServletException, IOException  {
    localHelper.setEnvIsLoggedIn(false);

    JsonObject responseJsonObject = getLoginServletResponse();
    String logInUrl = responseJsonObject.get("LogInUrl").getAsString();
    String logOutUrl = responseJsonObject.get("LogOutUrl").getAsString();

    Assert.assertTrue(logInUrl.contains("login"));
    Assert.assertTrue(logOutUrl.isEmpty());
  }

}