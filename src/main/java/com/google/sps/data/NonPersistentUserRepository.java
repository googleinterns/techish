package com.google.sps.data;

import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * User repository that stores users in an ArrayList so that we don't need to access
 * datastore for testing.
 */
 public class NonPersistentUserRepository implements UserRepository {
     private Collection<User> allUsers;

     public NonPersistentUserRepository() {
         allUsers = new ArrayList<User>();
     }

     public void addFakeMentors() {
        User mentorA = new User("Andre");
        mentorA.addProductArea("Machine Learning");
        mentorA.addProductArea("DoS");

        User mentorB = new User("Jerry");
        mentorB.addProductArea("Electrical Engineering");
        mentorB.addProductArea("DoS");

        User mentorC = new User("Julie");
        mentorC.addProductArea("Machine Learning");
        mentorC.addProductArea("Security");

        addUser(mentorA);
        addUser(mentorB);
        addUser(mentorC);
     }

     public void addUser(User user) {
         if(!allUsers.contains(user)) {
            allUsers.add(user);
         }
     }

     public void removeUser(User user) throws Exception {
         if(allUsers.contains(user)) {
           allUsers.remove(user);
         } else {
             throw new Exception("Can't remove user that does not exist");
         }
     }

     public Collection<User> getAllUsers() {
         return allUsers;
     }

     public String toString() {
         StringBuilder toReturn = new StringBuilder();
         for(User user : allUsers) {
             toReturn.append(user.toString());
             toReturn.append(" ");
         }
         return toReturn.toString();
     }
 }