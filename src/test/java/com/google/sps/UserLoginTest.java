package com.google.sps;

import com.google.sps.servlets.UserLoginServlet;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.JsonObject;
import java.io.*;
import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.mockito.Mockito.*;




/** */
@RunWith(JUnit4.class)
public final class UserLoginTest {
	

  public void userLoggedIn() {
    HttpServletRequest request = mock(HttpServletRequest.class);      
    HttpServletResponse response = mock(HttpServletResponse.class);
    
    new MyServlet().doGet(request, response);
    String expected = "/_ah/logout?continue=%2Findex.html";
    String expected = "/_ah/logout?continue=%2Findex.html";

    Assert.assertEquals(expected, actual);
  }

//   @Test
//   public void userLoggedOut() {

//     String actual = userService.createLoginURL("/index.html");
//     String expected = "/_ah/login?continue=%2Findex.html";
//     Assert.assertEquals(expected, actual);
//   }
}