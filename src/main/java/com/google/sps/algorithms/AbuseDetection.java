package com.google.sps.algorithms;

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

    private Collection <Date> timesOfRequests = new ArrayList<Date>();

    private int requestCounter = 0;
    private int requestsDropped = 0;

    private final int currentNumRequestsAllowed;
    private final Duration timePeriod;
  
  /**
  * Constructor that initializes currentNumRequestsAllowed and timePeriod
  * The requestInterval represents the time passed in for
  * this time interval of requests.
  * The requestsAllowed is the amount of requests that are being allowed
  * during that timeValue interval.
  */
  public AbuseDetection(Duration requestInterval, int requestsAllowed) { 
    this.timePeriod = requestInterval;
    this.currentNumRequestsAllowed = requestsAllowed;
  }

  /**
  *  Function takes a local Date, tries to add requests only if there are not
  *  more than the currentRequestsAllowed variable, and less than the 
  *  Date. If added the method returns true, if not it returns false.
  */
   public boolean addRequest(Date currentDate) {
   
        if(requestCounter < currentNumRequestsAllowed) {
            timesOfRequests.add(currentDate);
            requestCounter++;
            return true;
        }
        else {
            Date requestDate = timesOfRequests.iterator().next();
          
            long diffBetweenDates = currentDate.getTime() - requestDate.getTime();
            Duration timeDifference = Duration.ofMillis(diffBetweenDates);

            // checking first comparison to only execute inner if when the timeDifference
            // is greater than timePeriod. 
            if(timeDifference.compareTo(timePeriod) > 0)   {
                timesOfRequests.remove(requestDate);
                requestsDropped++;

                timesOfRequests.add(currentDate);
                return true;
            } 
            
        }

        return false;
  }

}