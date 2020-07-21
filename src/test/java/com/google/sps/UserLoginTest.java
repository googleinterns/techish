package com.google.sps;

import static org.mockito.Mockito.when;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.sps.data.PersistentUserRepository;
import com.google.sps.data.SessionContext;
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

  private SessionContext sessionContext;

  private UserRepository userRepository;

  private UserLoginServlet userServlet;

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

  public User makeAndAddTestUser() {
    User testUser = new User("Bob");
    String expected = "1234";
    testUser.setId(expected);
    String email = "test@example.com";
    testUser.setEmail(email);
    userRepository.addUser(testUser);
    return testUser;
  }

  @Before
  public void setUp() throws Exception {
    localHelper.setUp();
    request = Mockito.mock(HttpServletRequest.class); 
    response = Mockito.mock(HttpServletResponse.class);
    
    sessionContext = Mockito.mock(SessionContext.class);
    userRepository = PersistentUserRepository.getInstance();
    userServlet = new UserLoginServlet(sessionContext);  
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

  @Test
  public void hasProfileReturnsFalse() throws ServletException, IOException  {
    localHelper.setEnvIsLoggedIn(true);

    JsonObject responseJsonObject = getLoginServletResponse();

    String logInUrl = responseJsonObject.get("LogInUrl").getAsString();
    String logOutUrl = responseJsonObject.get("LogOutUrl").getAsString();
    Boolean isUserInDatabase = responseJsonObject.get("HasProfile").getAsBoolean();
    
    Assert.assertTrue(logOutUrl.contains("logout"));
    Assert.assertEquals(isUserInDatabase, false);
  }

  @Test
  public void hasProfileReturnTrue() throws ServletException, IOException  {
    localHelper.setEnvIsLoggedIn(true);

    User firstUser = makeAndAddTestUser();
    User testUser = new User("Bob");
    String expected = "1234";
    testUser.setId(expected);
    String email = "test@example.com";
    testUser.setEmail(email);

    when(sessionContext.getLoggedInUser()).thenReturn(testUser);
    when(sessionContext.userProfileExists()).thenReturn(true);

    JsonObject responseJsonObject = getLoginServletResponse();
    String logInUrl = responseJsonObject.get("LogInUrl").getAsString();
    String logOutUrl = responseJsonObject.get("LogOutUrl").getAsString();
    Boolean isUserInDatabase = responseJsonObject.get("HasProfile").getAsBoolean();
    
    Assert.assertTrue(logOutUrl.contains("logout"));
    Assert.assertEquals(isUserInDatabase, true);
  }
}