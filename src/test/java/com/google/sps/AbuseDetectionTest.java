package com.google.sps;

import com.google.sps.algorithms.AbuseDetection;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
/** */
@RunWith(JUnit4.class)
public final class AbuseDetectionTest {  

  private SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");

  /*
  * 1 request is added. The timestamp is: 18:19:20.010.
  */ 
  @Test
  public void addingOneTimeRequest() {
    AbuseDetection instance = new AbuseDetection(Duration.ofSeconds(1), 10);
    try {
        boolean value = instance.addRequest(df.parse("07-27 18:19:20.010"));
        // 1 request since 18:19:20.010, since it's less than 10 the 
        // request adds and thus returns true
        Assert.assertEquals(true, value);
    } catch(Exception e){
        System.err.println("Exception handled: " + e);
    }
  }

  /*
  * 10 Requests are added. First timestamp: 18:19:20.010. The
  * Last Timestamp 18:19:20.866. 
  */
  @Test
  public void addingMultipleRequestsTests() {
    AbuseDetection instance = new AbuseDetection(Duration.ofSeconds(1), 10);
    try {
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:20.010")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:20.112")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:20.115")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:20.180")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:20.340")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:20.590")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:20.605")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:20.712")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:20.832")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:20.866")));

        // 10 requests were added since 18:19:20.010, thus the 11th within 1 second at 18:19:20.901 fails.
        Assert.assertEquals(false, instance.addRequest(df.parse("7-27 18:19:20.901")));
        // The 12th is not within a second, so it gets added, which returns true
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.012")));

    } catch (Exception e) {
        Assert.fail("Caught exception: " + e);
    }
  }


  /*
  * 10 Requests are added. First timestamp: 23:59:55.010. The
  * Last Timestamp 00:00:00.000. 
  */
  @Test
  public void adding11RequestsNearMidnight() {
    AbuseDetection instance = new AbuseDetection(Duration.ofSeconds(1), 10);
    try {
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 23:59:59.000")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 23:59:59.065")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 23:59:59.120")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 23:59:59.260")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 23:59:59.450")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 23:59:59.590")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 23:59:59.650")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 23:59:59.740")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 23:59:59.860")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 00:00:59.900")));
    
        // 10 Requests were added since 23:59:55.010, the 11th request is within a second so it cannot get added, so it gets true
        Assert.assertEquals(true, instance.addRequest(df.parse("7-28 00:00:00.010")));
        // The 12th request is within a second so it cannot get added, so it gets false
        Assert.assertEquals(false, instance.addRequest(df.parse("7-28 00:00:00.064")));

    } catch (Exception e){
        Assert.fail("Exception caught:" + e);
    }
  }

  /*
  * 20 Requests are added. First timestamp: 18:19:21.000. The
  * Last Timestamp 18:19:21.900. 
  */
  @Test
  public void instance20RequestsReturnFalse() {
    AbuseDetection instance = new AbuseDetection(Duration.ofSeconds(2), 20);
     try {
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.000")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.015")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.026")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.036")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.047")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.070")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.090")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.124")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.145")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.231")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.271")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.344")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.421")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.560")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.590")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.612")));      
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.642")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.723")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.800")));
        Assert.assertEquals(true, instance.addRequest(df.parse("7-27 18:19:21.900"))); 
        
        // 20 Requests were added within 18:19:21.000, the 21th request is not within 2 seconds so it returns false and doesn't get added
        Assert.assertEquals(false, instance.addRequest(df.parse("7-27 18:19:22.020"))); 

    } catch (Exception e) {
        Assert.fail("Caught exception: " + e);
    }
  }

}
