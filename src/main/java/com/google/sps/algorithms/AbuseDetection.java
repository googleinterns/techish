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
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


public final class AbuseDetection {

    // private Collection <LocalTime> timesOfRequests = new ArrayList<LocalTime>();
    private Collection <Date> timesOfRequests = new ArrayList<Date>();

    private int requestCounter = 0;
    private int requestsDropped = 0;

    private final int currentNumRequestsAllowed;
    private final Duration timePeriod;
  
  /**
  * Constructor that initializes currentNumRequestsAllowed and timePeriod
  * The durationInSeconds represents the amount of seconds passed 
  * in for this time interval of requests.
  * The requestsAllowed is the amount of requests that are being allowed
  * during that timeValue interval.
  */
  public AbuseDetection(Duration requestInterval, int requestsAllowed) { 
    this.timePeriod = requestInterval;
    this.currentNumRequestsAllowed = requestsAllowed;
  }

  /**
  *  Function takes a local time, tries to add requests only if there are not
  *  more than the currentRequestsAllowed variable, and less than the 
  *  timePeriod. If added the method returns true, if not it returns false.
  */
   public boolean addRequest(LocalTime currentTime) {
        Instant currentTimeToInstance =  currentTime.atDate(LocalDate.now()).
        atZone(ZoneId.systemDefault()).toInstant();
        Date timeToDate = Date.from(currentTimeToInstance);
    
        boolean returnValue = false;
        if(requestCounter < currentNumRequestsAllowed) {
            timesOfRequests.add(timeToDate);

            returnValue = true;
            requestCounter++;

        }
        else {

            for(Date currentDate : timesOfRequests) {
                Instant instant = Instant.ofEpochMilli(currentDate.getTime());
                LocalTime currentDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();

                Duration timeDifference = Duration.between(currentDateTime, currentTime);
                int difference = timeDifference.compareTo(timePeriod);

                long timePeriodlong = timePeriod.getSeconds();
                int timePeriodInt = (int)timePeriodlong;
               
                if(difference >= timePeriodInt) {
                    timesOfRequests.remove(currentDate);
                    requestsDropped++;

    
                    timesOfRequests.add(timeToDate);
                    returnValue = true;
                    break;
                }
            }
        }

        return returnValue;
  }

}