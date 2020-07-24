package com.google.sps.algorithms;

import com.google.sps.data.MatchRepository;
import com.google.sps.data.MatchRequest;
import com.google.sps.data.PersistentUserRepository;
import com.google.sps.data.User;
import com.google.sps.data.UserRepository;
import java.lang.Exception;
import java.io.IOException; 
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;



public final class AbuseDetection {

    private Map <HttpServletRequest, LocalTime> mapOfRequests = new Map<HttpServletRequest, LocalTime>;
    private int requestCounter;
    private LocalTime time1;
    private LocalTime time2;
    private HttpServletRequest requestResult;

  /** 
  *  This method receives a request and adds it to the hashmap
  */
  public void requestMade(HttpServletRequest request) {
      LocalTime currentTime = LocalTime.now();
      mapOfRequests.put(currentTime, request);
  }

  /**
  *  Function takes a Map of requests and local time, loops through them, and when it passes
  *  10 requests per second then it responds to the user with an error. At the end of the function
  *  it returns a map of filtered requests.   
  */
   public Collection<HttpServletRequest> AbuseDetection(mapOfRequests) throws IOException {
        Collection<HttpServletRequest> filteredRequests = new ArrayList<>();
        int requestsSize = requests.size();
        

        for (Map.Entry currentRequest : mapOfRequests.entrySet()) {
            if(requestCounter > 9) {
               LocalTime currentTime = currentRequest.getKey();
               HttpServletRequest currentKey = currentRequest.getValue();

               filteredRequests.add(currentKey);

            }
            requestCounter++;
        }
        return requestResult;
  }

}