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
  private static final MatchQuery MATCH_QUERY = new MatchQuery();

  @Test
  public void emptyRequest_shouldReturnNoMentors() {
    Collection<String> expected = new ArrayList<String>();

    Assert.assertEquals(expected, MATCH_QUERY.query(EMPTY_REQUEST));
  }

  @Test
  public void mlRequest_ShouldReturnMLMentors() {
    Collection<String> expected = new ArrayList<String>();

    User mentorA = new User("Andre Harder");
    mentorA.addSpecialty("Machine Learning");
    mentorA.addSpecialty("DoS");

    User mentorC = new User("Julie Johnson");
    mentorC.addSpecialty("Machine Learning");
    mentorC.addSpecialty("Security");

    expected.add(mentorA.toString());
    expected.add(mentorC.toString());

    Assert.assertEquals(expected, MATCH_QUERY.query(ML_REQUEST));
  }

  @Test
  public void badRequest_ShouldReturnNoMentors() {
    Collection<String> expected = new ArrayList<String>();

    Assert.assertEquals(expected, MATCH_QUERY.query(BAD_REQUEST));
  }

}
