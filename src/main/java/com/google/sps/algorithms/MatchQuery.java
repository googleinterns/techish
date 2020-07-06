package com.google.sps.algorithms;

import com.google.sps.data.MatchRequest;
import com.google.sps.data.User;
import com.google.sps.data.NonPersistentUserRepository;
import java.util.ArrayList;
import java.util.Collection;

public final class MatchQuery {

  public Collection<User> query(MatchRequest request) {

    NonPersistentUserRepository mockRepo = new NonPersistentUserRepository();
    mockRepo.addFakeMentors();

    Collection<User> mockMentors = mockRepo.getAllUsers();
    Collection<User> mentorMatches = new ArrayList<User>();

    String matchCriteria = request.getCriteria();

    for(User potentialMentor : mockMentors) {
        Collection<String> mentorSpecialties = potentialMentor.getSpecialties();
        if(mentorSpecialties.contains(matchCriteria)) {
            mentorMatches.add(potentialMentor);
        }
    }

    return mentorMatches;
  }
}
