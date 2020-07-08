package com.google.sps.algorithms;

import com.google.sps.data.MatchRequest;
import com.google.sps.data.User;
import com.google.sps.data.NonPersistentUserRepository;
import com.google.sps.data.MatchRepository;
import java.util.ArrayList;
import java.util.Collection;

public final class MatchQuery {

//   public MatchQuery()

  public Collection<User> query(MatchRequest request, MatchRepository matchRepo) {
    NonPersistentUserRepository mockRepo = new NonPersistentUserRepository();
    mockRepo.addFakeMentors();

    Collection<User> mockMentors = mockRepo.getAllUsers();
    Collection<User> mentorMatches = new ArrayList<User>();

    String matchCriteria = request.getCriteria();

    //return empty collection if there is no criteria
    if(matchCriteria == "") {
      return mentorMatches;
    }

    User fakeTestUser = new User("");
    Collection<User> alreadySavedMatches = matchRepo.getMatchesForUser(fakeTestUser);

    //get already saved matches using a fake request
    // HttpServletRequest request = Mockito.mock(HttpServletRequest.class); 
    // MatchServlet matchServlet = new MatchServlet();
    // matchServlet.init();
    // Collection<User> matches = 

    for(User potentialMentor : mockMentors) {
        Collection<String> mentorSpecialties = potentialMentor.getSpecialties();
        if(mentorSpecialties.contains(matchCriteria)) {
            mentorMatches.add(potentialMentor);
        }
    }
    
    return mentorMatches;
  }
}
