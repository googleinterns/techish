package com.google.sps;

import static org.mockito.Mockito.when;

import com.google.sps.data.SessionContext;
import com.google.sps.data.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SessionContextTest {

    private SessionContext sessionContext;
    
    @Before
    public void setup() {
        sessionContext = Mockito.mock(SessionContext.class);
    }

    @Test
    public void getLoggedInUser_ReturnUser() {
        User testUser = new User("Bob");
        when(sessionContext.getLoggedInUser()).thenReturn(testUser);

        User resultUser = sessionContext.getLoggedInUser();

        Assert.assertEquals(testUser, resultUser);
    }

    @Test
    public void getLoggedInUser_ReturnNull() {
        User testUser = null;
        when(sessionContext.getLoggedInUser()).thenReturn(testUser);

        User resultUser = sessionContext.getLoggedInUser();

        Assert.assertEquals(testUser, resultUser);
    }

    @Test
    public void isUserLoggedIn_ReturnTrue() {
        boolean expected = true;
        when(sessionContext.isUserLoggedIn()).thenReturn(expected);

        boolean result = sessionContext.isUserLoggedIn();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void isUserLoggedIn_ReturnFalse() {
        boolean expected = false;
        when(sessionContext.isUserLoggedIn()).thenReturn(expected);

        boolean result = sessionContext.isUserLoggedIn();

        Assert.assertEquals(expected, result);
    }

}
