package com.google.sps;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.users.dev.LocalUserService;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.sps.servlets.UserLoginServlet;
import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


@RunWith(JUnit4.class)
public class UserLoginTest {

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private UserLoginServlet userServlet;

  @Before
  public void setUp() throws Exception  {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void loggedInUserReturnsLogOutUrl() throws ServletException, IOException  {
    LocalServiceTestHelper helper =
    new LocalServiceTestHelper(new LocalUserServiceTestConfig())
        .setEnvIsAdmin(true).setEnvIsLoggedIn(true);
    helper.setUp();
    
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(printWriter);

    userServlet = new UserLoginServlet();
    userServlet.doGet(request, response);

    String responseString = stringWriter.getBuffer().toString().trim();
    JsonElement responseJsonElement = new JsonParser().parse(responseString);
         
    JsonObject responseJsonObject = responseJsonElement.getAsJsonObject();
    String logInUrl = responseJsonObject.get("LogInUrl").getAsString();
    String logOutUrl = responseJsonObject.get("LogOutUrl").getAsString();
    
    Assert.assertTrue(logInUrl.isEmpty());
    Assert.assertFalse(logOutUrl.isEmpty());
    Assert.assertTrue(logOutUrl.contains("logout"));
    helper.tearDown();
  }

  @Test
  public void loggedOutUserReturnsLogInUrl() throws ServletException, IOException  {
    LocalServiceTestHelper helper =
    new LocalServiceTestHelper(new LocalUserServiceTestConfig())
        .setEnvIsAdmin(true).setEnvIsLoggedIn(false);
    helper.setUp();

    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(printWriter);

    userServlet = new UserLoginServlet();
    userServlet.doGet(request, response);

    String response = stringWriter.getBuffer().toString().trim();
    JsonElement responseJsonElement = new JsonParser().parse(response);
         
    JsonObject responseJsonObject = responseJsonElement.getAsJsonObject();
    String logInUrl = responseJsonObject.get("LogInUrl").getAsString();
    String logOutUrl = responseJsonObject.get("LogOutUrl").getAsString();
    
    Assert.assertFalse(logInUrl.isEmpty());
    Assert.assertTrue(logOutUrl.isEmpty());
    Assert.assertTrue(logInUrl.contains("login"));
    helper.tearDown();
  }

}