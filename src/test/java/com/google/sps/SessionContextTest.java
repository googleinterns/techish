package com.google.sps;

import static org.mockito.Mockito.when;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.NonPersistentUserRepository;
import com.google.sps.data.SessionContext;
import com.google.sps.data.User;
import com.google.sps.data.UserRepository;
import java.lang.Exception;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SessionContextTest {

    private SessionContext sessionContext;
    private UserRepository userRepository;
    private UserService userService;
    
    @Before
    public void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = Mockito.mock(UserService.class);
        sessionContext = new SessionContext(userRepository, userService);
    }

    @Test
    public void getLoggedInUser_ReturnUser() {
        User testUser = new User("Bob");
        com.google.appengine.api.users.User googleUser = new com.google.appengine.api.users.User("email", "domain");
        when(userService.getCurrentUser()).thenReturn(googleUser);
        when(userRepository.getUser(googleUser)).thenReturn(testUser);

        User resultUser = sessionContext.getLoggedInUser();

        Assert.assertEquals(testUser, resultUser);
    }

    @Test
    public void getLoggedInUser_ReturnNull() {
        User testUser = null;
        com.google.appengine.api.users.User googleUser = null;
        when(userService.getCurrentUser()).thenReturn(googleUser);

        User resultUser = sessionContext.getLoggedInUser();

        Assert.assertEquals(testUser, resultUser);
    }

    @Test
    public void isUserLoggedIn_ReturnTrue() {
        boolean expected = true;
        when(userService.isUserLoggedIn()).thenReturn(expected);

        boolean result = sessionContext.isUserLoggedIn();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void isUserLoggedIn_ReturnFalse() {
        boolean expected = false;
        when(userService.isUserLoggedIn()).thenReturn(expected);

        boolean result = sessionContext.isUserLoggedIn();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void getLoggedInUserId_ReturnId() throws Exception {
        User testUser = new User("Bob");
        String expected = "1234";
        testUser.setId(expected);
        com.google.appengine.api.users.User googleUser = new com.google.appengine.api.users.User("email", "domain");
        when(userService.getCurrentUser()).thenReturn(googleUser);
        when(userRepository.getUser(googleUser)).thenReturn(testUser);

        String result = sessionContext.getLoggedInUserId();

        Assert.assertEquals(expected, result);
    }

}
