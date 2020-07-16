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
        User mentorA = new User("Andre Harder");
        mentorA.addSpecialty("Machine Learning");
        mentorA.addSpecialty("DoS");
        mentorA.setEmail("andre@gmail.com");
        mentorA.setId("1");

        User mentorB = new User("Jerry Chang");
        mentorB.addSpecialty("Electrical Engineering");
        mentorB.addSpecialty("DoS");
        mentorB.setEmail("jerry@gmail.com");
        mentorB.setId("2");

        User mentorC = new User("Julie Johnson");
        mentorC.addSpecialty("Machine Learning");
        mentorC.addSpecialty("Security");
        mentorC.setEmail("julie@gmail.com");
        mentorC.setId("3");

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

     //return a User object from a google User
     //if user is not in repository, it creates a new one
     public User getUser(com.google.appengine.api.users.User googleUser) {
         String userEmail = googleUser.getEmail();

        for(User user : allUsers) {
            if (user.getEmail() == userEmail) {
                return user;
            }
        }

        User newUser = new User(googleUser.getNickname());
        newUser.setEmail(userEmail);
        newUser.setId(googleUser.getUserId());

        allUsers.add(newUser);
        return newUser;
     }
 }