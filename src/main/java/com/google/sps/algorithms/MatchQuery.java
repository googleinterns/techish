package com.google.sps.algorithms;

import com.google.sps.data.MatchRequest;
import com.google.sps.data.User;
import com.google.sps.data.NonPersistentUserRepository;
import com.google.sps.data.MatchRepository;
import java.util.ArrayList;
import java.util.Collection;

public final class MatchQuery {

//   public MatchQuery()

  public Collection<User> query(MatchRequest request, Collection<User> userSavedMatches) {
    //Access User Repository
    NonPersistentUserRepository mockRepo = new NonPersistentUserRepository();
    mockRepo.addFakeMentors();

    Collection<User> mockMentors = mockRepo.getAllUsers();
    Collection<User> mentorMatches = new ArrayList<User>();

    String matchCriteria = request.getCriteria();

    //return empty collection if there is no criteria
    if(matchCriteria == "") {
      return mentorMatches;
    }

    //debugging
    System.out.println("USER SAVED MATCHES: " + userSavedMatches);

    for(User potentialMentor : mockMentors) {
        Collection<String> mentorSpecialties = potentialMentor.getSpecialties();
        System.out.println("Boolean for " + potentialMentor + " : " + userSavedMatches.contains(potentialMentor));

        //see if new mentor is not already saved AND contains correct criteria
        if(!(userSavedMatches.contains(potentialMentor)) && (mentorSpecialties.contains(matchCriteria))) {
            mentorMatches.add(potentialMentor);
        }
    }
    
    return mentorMatches;
  }
}
