package com.google.sps.algorithms;

import com.google.sps.data.MatchRepository;
import com.google.sps.data.MatchRequest;
import com.google.sps.data.PersistentUserRepository;
import com.google.sps.data.User;
import com.google.sps.data.UserRepository;
import java.util.ArrayList;
import java.util.Collection;

import java.util.*;


public final class MatchQuery {
  
  private PersistentUserRepository userRepository = PersistentUserRepository.getInstance();

  /**
  * This method takes a MatchRequest and a Collection of users that are already saved and returns 
  * all Users in the User Repository that match the criteria in MatchRequest AND are not already
  * saved in the userSavedMatches collection.
  */
  public Collection<User> query(MatchRequest request, Collection<User> userSavedMatches) {
    return query(request, userSavedMatches, userRepository);
  }

  //overload of query allows UserRepository to be passed in for testing
  public Collection<User> query(MatchRequest request, Collection<User> userSavedMatches, PersistentUserRepository userRepository) {
      
    Collection<User> potentialMentors = userRepository.getAllUsers();
    Collection<User> mentorMatches = new ArrayList<User>();

    String matchCriteria = request.getCriteria();

    //return empty collection if there is no criteria
    if(matchCriteria == "") {
      return mentorMatches;
    }

    for(User potentialMentor : potentialMentors) {
        Collection<String> mentorSpecialties = potentialMentor.getSpecialties();

        //see if new mentor is not already saved AND contains correct criteria
        if(!(userSavedMatches.contains(potentialMentor)) && (mentorSpecialties.contains(matchCriteria))) {
            mentorMatches.add(potentialMentor);
        }
    }

    Collection<User> allUsers = userRepository.fetchUserProfiles();
    List<User> rankedMatches = MatchRanking.rankMatches(userSavedMatches, allUsers, mentorMatches);
    
    return rankedMatches;
  }

//   private Collection<User> rankMatches(Collection<User> newMatches, Collection<User> savedMatches) {
//       //get all user bios
//       Collection<User> allUsers = userRepository.fetchUserProfiles();
//       Collection<String> allUserBios = new ArrayList<String>();
//       for(User user : allUsers) {
//           allUserBios.add(user.getBio());
//       }

//       //get saved bios
//       Collection<String> savedUserBios = new ArrayList<String>();
//       for(User user : savedMatches) {
//           savedUserBios.add(user.getBio());
//       }
      
//       //get new match bios
//       Collection<String> newMatchBios = new ArrayList<String>();
//       for(User user : newMatches) {
//           newMatchBios.add(user.getBio());
//       }

//       //get ranking
//       List<String> rankedBios = MatchRanking.rankMatches(savedUserBios, allUserBios, newMatchBios);

//       //todo account for same bios
//       Map<String, User> bioToUser = new HashMap<String, User>();
//       for(User newMatch : newMatches) {
          
//           bioToUser.put(newMatch.getBio(), newMatch);
//       }

    
//       //map bio to user then return the users
      
//       List<User> rankedUsers = new ArrayList<User>();
//       for(String userBio : rankedBios) {
//           rankedUsers.add(bioToUser.get(userBio));
//       }
    
//       return rankedUsers;


//   }



}
