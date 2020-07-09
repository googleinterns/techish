package com.google.sps.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.User;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * This class implements the match repository using the datastore 
 */
public class UserDatastoreRepository implements UserRepository {
    
  private Collection<User> allUsers = new ArrayList<>();
  
  private DatastoreService datastore;
  
  private int maxProfiles = 10;
  
  public UserDatastoreRepository() {
      datastore = DatastoreServiceFactory.getDatastoreService();
  }

  // function to add profile to database
  public void addUserToDatabase(User user) {
    String name = user.toString();
    Collection<String> specialties = user.getSpecialties();

    Entity userEntity = new Entity("User");
    userEntity.setProperty("name", name);
    if(specialties.size() > 0){
        userEntity.setProperty("specialties", specialties);
    }
    datastore.put(userEntity);
  }

  // function to fetch the user from the database
  public Collection<User> fetchUserProfiles() {
    Query query = new Query("User");
    PreparedQuery results = datastore.prepare(query);
    List<Entity> resultsList = results.asList(FetchOptions.Builder.withLimit(maxProfiles));

    Collection<User> userProfiles = new ArrayList<>();
    for (Entity entity : resultsList) {
        String name = (String) entity.getProperty("name");
        Collection<String> specialties = (Collection) entity.getProperty("specialties");

        User userObject = new User(name);
        if(specialties != null) {
            for (String specialty : specialties) {
                userObject.addProductArea(specialty);
            }
        }
        userProfiles.add(userObject);
    }
    return userProfiles;
  }

  public void removeUserProfile(User user){
    Query query = new Query("User");
    PreparedQuery results = datastore.prepare(query);
    List<Entity> resultsList = results.asList(FetchOptions.Builder.withLimit(maxProfiles));

    String name = user.toString();

    for (Entity entity : resultsList) {
        String currentName = (String) entity.getProperty("name");
        if(name.contains(currentName)) {
            Key current = entity.getKey();
            datastore.delete(current);
        }
    }
  }
  // function that adds user to the database if it does not exist already
  public void addUser(User user) {
    allUsers = fetchUserProfiles();
    /*
     * if the user exists in the database then nothing happens,
     * if it doesnt exist then it gets added
    */
    if(!allUsers.contains(user)) {
        allUsers.add(user);
        addUserToDatabase(user);
    }

  }

  public void removeUser(User user) throws Exception {
    if(allUsers.contains(user)) {
        allUsers.remove(user);
        removeUserProfile(user);
    } else {
        throw new Exception("Can't remove user that does not exist");
    }
  }

  public Collection<User> getAllUsers() {
    Collection<User> results = fetchUserProfiles();
    return results;
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