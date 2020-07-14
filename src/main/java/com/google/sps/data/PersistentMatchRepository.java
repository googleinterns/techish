package com.google.sps.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.User;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements the user repository using the datastore 
 */
public class PersistentMatchRepository implements MatchRepository {
  
  private final DatastoreService datastore;
  
  public PersistentMatchRepository() {
      datastore = DatastoreServiceFactory.getDatastoreService();
  }

  public void addMatchToDatabase(User user, User match) {
    String userId = user.getId();

    //make embedded entity for match
    EmbeddedEntity matchEntity = new EmbeddedEntity("Match");
    matchEntity.setProperty("id", match.getId());
    matchEntity.setProperty("email", match.getEmail());
    matchEntity.setProperty("specialties", match.getSpecialties());

    //see if user ID is already saved

    //if yes: add match onto that

    //if no: make new User entity

    //make new user entity
    //userEntity.setProperty(

    

    datastore.put(userEntity);
  }

  public Collection<User> fetchUserEntities(PreparedQuery results) {
    Collection<User> userEntities = new ArrayList<>();

    for (Entity entity : results.asIterable()) {
        String name = (String) entity.getProperty("name");
        Collection<String> specialties = (Collection<String>) entity.getProperty("specialties");
        String company = (String) entity.getProperty("company");

        User userObject = new User(name);
        if(specialties != null) {
            for (String specialty : specialties) {
                userObject.addSpecialty(specialty);
            }
        }
        if(company != null) {
            userObject.setCompany(company);
        }
        userEntities.add(userObject);
    }
    return userEntities;
  }

  // function to fetch the user from the database
  public Collection<User> fetchUserProfiles() {
    Query query = new Query("User");
    PreparedQuery results = datastore.prepare(query);
    Collection<User> userProfiles = fetchUserEntities(results);
    return userProfiles;
  }

  // function that sets a query filter for the results in the datastore
  public PreparedQuery getQueryFilterForName(String name) {
    Filter userNameFilter = new FilterPredicate("name", FilterOperator.EQUAL, name);
    Query query = new Query("User").setFilter(userNameFilter);
    PreparedQuery results = datastore.prepare(query);
    return results;
  }

    // function that fetches a single user, collection of users, filter on that user for name?
  public Collection<User> fetchUsersWithName(String userName) {
    PreparedQuery results = getQueryFilterForName(userName);
    Collection<User> userProfiles = fetchUserEntities(results);

    return userProfiles;
  }

  // function that adds user to the database if it does not exist already
  public void addUser(User user) {
    String userName = user.getName();
    Collection<User> userProfiles = fetchUsersWithName(userName);
    /*
     * if the userProfiles is larger than 1, that means the 
     * if it doesnt exist then it gets added
    */
    if(userProfiles.size() == 0) {
        addUserToDatabase(user);
    }
  }

  public void removeUserProfile(User user) throws Exception{
    String inputUserName = user.getName();
    PreparedQuery results = getQueryFilterForName(inputUserName);
    int size = results.countEntities();

    /*
     *  if size is greater than 0 then there are entities available,
     *  if size is 0 then users do not equal, so an exception is thrown
    */
    if(size > 0){
        for (Entity entity : results.asIterable()) {
            String currentName = (String) entity.getProperty("name");
            if(inputUserName.equals(currentName)) {
                Key current = entity.getKey();
                datastore.delete(current);
            }

        }
    } else {
        throw new Exception("Can't remove " + inputUserName + " not found in datastore.");
    }
      
  }
  public void removeUser(User user) throws Exception {
        removeUserProfile(user);
  }

  public Collection<User> getAllUsers() {
    Collection<User> results = fetchUserProfiles();
    return results;
  }

  public String toString() {
    StringBuilder toReturn = new StringBuilder();
    Collection <User> allUsers = getAllUsers();
    for(User user : allUsers) {
        toReturn.append(user.toString());
        toReturn.append(" ");
    }
    return toReturn.toString();
  }

}