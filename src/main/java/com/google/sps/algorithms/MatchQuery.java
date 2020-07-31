package com.google.sps.algorithms;

import com.google.sps.data.MatchRepository;
import com.google.sps.data.MatchRequest;
import com.google.sps.data.PersistentUserRepository;
import com.google.sps.data.User;
import com.google.sps.data.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class MatchQuery {
 
  /**
  * This method takes a current User, MatchRequest, and a Collection of users that are already saved and returns 
  * all Users in the User Repository that match the criteria in MatchRequest AND are not already
  * saved in the userSavedMatches collection.
  */
  public Collection<User> query(User currentUser, MatchRequest request, Collection<User> userSavedMatches) {
    PersistentUserRepository userRepository = PersistentUserRepository.getInstance();
    return query(currentUser, request, userSavedMatches, userRepository);
  }

  //overload of query allows UserRepository to be passed in for testing
  public Collection<User> query(User currentUser, MatchRequest request, Collection<User> userSavedMatches, UserRepository userRepository) {
      
    Collection<User> allUsers = userRepository.getAllUsers();
    Collection<User> mentorMatches = new ArrayList<User>();

    String matchCriteria = request.getCriteria();

    //return empty collection if there is no criteria
    if(matchCriteria == "") {
      return mentorMatches;
    }

    for(User potentialMentor : allUsers) {
      Collection<String> mentorSpecialties = potentialMentor.getSpecialties();

      boolean isCurrentUser = potentialMentor.equals(currentUser);
      boolean isAlreadySaved = userSavedMatches.contains(potentialMentor);
      boolean sharedSpecialty = mentorSpecialties.contains(matchCriteria);

      //see if new mentor is not same as logged in user AND is not already saved AND contains correct criteria
      if(!isCurrentUser && !isAlreadySaved && sharedSpecialty) {
        mentorMatches.add(potentialMentor);
      }
    }

    //HADLEY THIS IS THE CAUSE OF THE ERROR FiX IT HERE WHEN YOU WAKE UP
    Map<String, Integer> bioMap;
    if(userRepository instanceof PersistentUserRepository) {
        //get biomap directly from persistent user repository
        PersistentUserRepository userRepo = PersistentUserRepository.getInstance();
        bioMap = userRepo.getMapForUser(currentUser);
    }  else {
        bioMap = currentUser.getBioMap();
    }

    System.out.println("BIO MAP: " + bioMap);
 
    List<User> rankedMatches = MatchRanking.rankMatches(bioMap, mentorMatches);
    return rankedMatches;
  }

}
