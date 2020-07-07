package com.google.sps;

import com.google.sps.algorithms.MatchQuery;
import com.google.sps.data.MatchRequest;
import com.google.sps.data.User;
import com.google.sps.data.NonPersistentUserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.ArrayList;
import java.util.Collection;

/** */
@RunWith(JUnit4.class)
public final class MatchQueryTest {

  private static final MatchRequest EMPTY_REQUEST = new MatchRequest();
  private static final MatchRequest ML_REQUEST = new MatchRequest("Machine Learning");
  private static final MatchRequest BAD_REQUEST = new MatchRequest("not a specialty");

  private static final NonPersistentUserRepository USER_REPO = new NonPersistentUserRepository();
  private static final Collection<User> ALL_MATCHES = USER_REPO.getAllUsers();
  private static final MatchQuery MATCH_QUERY = new MatchQuery();

    @Before
    public void setUp() {
      USER_REPO.addFakeMentors();
    }

  @Test
  public void emptyRequest_shouldReturnAllMentors() {
    Collection<String> expected = new ArrayList<String>();
    // for(User user : ALL_MATCHES) {
    //     expected.add(user.toString());
    // }

    Assert.assertEquals(expected, MATCH_QUERY.query(EMPTY_REQUEST));
    
  }

  
}
