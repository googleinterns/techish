package com.google.sps;

import com.google.sps.algorithms.MatchQuery;
import com.google.sps.data.MatchRequest;
import com.google.sps.data.NonPersistentMatchRepository;
import com.google.sps.data.NonPersistentUserRepository;
import com.google.sps.data.User;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** */
@RunWith(JUnit4.class)
public final class MatchQueryTest {

  private static final MatchRequest EMPTY_REQUEST = new MatchRequest();
  private static final MatchRequest ML_REQUEST = new MatchRequest("Machine Learning");
  private static final MatchRequest BAD_REQUEST = new MatchRequest("not a specialty");
  private static final MatchQuery MATCH_QUERY = new MatchQuery();

  private NonPersistentMatchRepository matchRepo;
  private User testUser;
  Collection<User> userSavedMatches;

  @Before
  public void setup() {
    matchRepo = new NonPersistentMatchRepository();
    testUser = matchRepo.addTestData();
    userSavedMatches = matchRepo.getMatchesForUser(testUser.getId());
  }

  @Test
  public void emptyRequest_shouldReturnNoMentors() {
    Assert.assertTrue(MATCH_QUERY.query(EMPTY_REQUEST, userSavedMatches).isEmpty());
  }

  @Test
  public void mlRequest_ShouldReturnMLMentors() {
    Assert.assertEquals(2, MATCH_QUERY.query(ML_REQUEST, userSavedMatches).size());
  }

  @Test
  public void badRequest_ShouldReturnNoMentors() {
    Assert.assertTrue(MATCH_QUERY.query(BAD_REQUEST, userSavedMatches).isEmpty());
  }

  @Test
  public void noRepeatMatches() {
    //Save one of the mentors so that it should skip over and only return 1 new mentor
    User newMentor = new User("Andre Harder");
    newMentor.addSpecialty("Machine Learning");
    userSavedMatches.add(newMentor);

    Assert.assertEquals(1, MATCH_QUERY.query(ML_REQUEST, userSavedMatches).size());
  }

}
