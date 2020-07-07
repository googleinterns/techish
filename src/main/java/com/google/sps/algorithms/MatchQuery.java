package com.google.sps.algorithms;

import com.google.sps.data.MatchRequest;
import com.google.sps.data.User;
import com.google.sps.data.NonPersistentUserRepository;
import java.util.ArrayList;
import java.util.Collection;

public final class MatchQuery {

  public Collection<String> query(MatchRequest request) {

    NonPersistentUserRepository mockRepo = new NonPersistentUserRepository();
    mockRepo.addFakeMentors();

    Collection<User> mockMentors = mockRepo.getAllUsers();
    Collection<String> mentorMatches = new ArrayList<String>();

    String matchCriteria = request.getCriteria();

    for(User potentialMentor : mockMentors) {
        Collection<String> mentorSpecialties = potentialMentor.getSpecialties();
        if(mentorSpecialties.contains(matchCriteria)) {
            System.out.println("ALGORITHM STRING: " + potentialMentor.toJsonString());
            mentorMatches.add(potentialMentor.toJsonString());
        }
    }

    return mentorMatches;
  }
}
