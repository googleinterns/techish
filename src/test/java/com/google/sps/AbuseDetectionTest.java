package com.google.sps;

import com.google.sps.algorithms.AbuseDetection;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
/** */
@RunWith(JUnit4.class)
public final class AbuseDetectionTest {  

  public void add10Requests(AbuseDetection instance){
    instance.addRequest(LocalTime.parse("18:19:20.010"));
    instance.addRequest(LocalTime.parse("18:19:20.112"));
    instance.addRequest(LocalTime.parse("18:19:20.115"));
    instance.addRequest(LocalTime.parse("18:19:20.180"));
    instance.addRequest(LocalTime.parse("18:19:20.340"));
    instance.addRequest(LocalTime.parse("18:19:20.590"));
    instance.addRequest(LocalTime.parse("18:19:20.605"));
    instance.addRequest(LocalTime.parse("18:19:20.712"));
    instance.addRequest(LocalTime.parse("18:19:20.832"));
    instance.addRequest(LocalTime.parse("18:19:20.866"));
  }

  public void add10RequestsNearMidnight(AbuseDetection instance){
    instance.addRequest(LocalTime.parse("23:59:59.000"));
    instance.addRequest(LocalTime.parse("23:59:59.065"));
    instance.addRequest(LocalTime.parse("23:59:59.120"));
    instance.addRequest(LocalTime.parse("23:59:59.260"));
    instance.addRequest(LocalTime.parse("23:59:59.360"));
    instance.addRequest(LocalTime.parse("23:59:59.590"));
    instance.addRequest(LocalTime.parse("23:59:59.650"));
    instance.addRequest(LocalTime.parse("23:59:59.740"));
    instance.addRequest(LocalTime.parse("23:59:59.980"));
    instance.addRequest(LocalTime.parse("00:00:00.000"));
  }
  public void add10RequestsBeforeMidnight(AbuseDetection instance){
    instance.addRequest(LocalTime.parse("23:59:59.000"));
    instance.addRequest(LocalTime.parse("23:59:59.065"));
    instance.addRequest(LocalTime.parse("23:59:59.120"));
    instance.addRequest(LocalTime.parse("23:59:59.260"));
    instance.addRequest(LocalTime.parse("23:59:59.360"));
    instance.addRequest(LocalTime.parse("23:59:59.590"));
    instance.addRequest(LocalTime.parse("23:59:59.650"));
    instance.addRequest(LocalTime.parse("23:59:59.740"));
    instance.addRequest(LocalTime.parse("23:59:59.930"));
    instance.addRequest(LocalTime.parse("23:59:59.980"));
  }


  public boolean add20Requests(AbuseDetection instance){
    instance.addRequest(LocalTime.parse("18:19:21.000"));
    instance.addRequest(LocalTime.parse("18:19:21.015"));
    instance.addRequest(LocalTime.parse("18:19:21.026"));
    instance.addRequest(LocalTime.parse("18:19:21.036"));
    instance.addRequest(LocalTime.parse("18:19:21.047"));
    instance.addRequest(LocalTime.parse("18:19:21.070"));
    instance.addRequest(LocalTime.parse("18:19:21.090"));
    instance.addRequest(LocalTime.parse("18:19:21.124"));
    instance.addRequest(LocalTime.parse("18:19:21.145"));
    instance.addRequest(LocalTime.parse("18:19:21.231"));
    instance.addRequest(LocalTime.parse("18:19:21.271"));
    instance.addRequest(LocalTime.parse("18:19:21.344"));
    instance.addRequest(LocalTime.parse("18:19:21.421"));
    instance.addRequest(LocalTime.parse("18:19:21.560"));
    instance.addRequest(LocalTime.parse("18:19:21.590"));
    instance.addRequest(LocalTime.parse("18:19:21.612"));
    instance.addRequest(LocalTime.parse("18:19:21.642"));
    instance.addRequest(LocalTime.parse("18:19:21.723"));
    instance.addRequest(LocalTime.parse("18:19:21.800"));
    boolean value = instance.addRequest(LocalTime.parse("18:19:21.900"));
    return value;
  }

  /*
  * 1 request is added. The timestamp is: 18:19:20.010.
  */ 
  @Test
  public void addingOneTimeRequest() {
    Duration currentDur = Duration.ofSeconds(1);
    AbuseDetection instance = new AbuseDetection(currentDur, 10);
    LocalTime inputTime = LocalTime.parse("18:19:20.010");
    boolean value = instance.addRequest(inputTime);
    // 1 request since 18:19:20.010, since it's less than 10 the 
    // request adds and thus returns true
    Assert.assertEquals(true, value);
  }

  /*
  * 10 Requests are added. First timestamp: 18:19:20.010. The
  * Last Timestamp 18:19:20.866. 
  */
  @Test
  public void addingMultipleRequestsGetFalse() {
    Duration currentDur = Duration.ofSeconds(1);
    AbuseDetection instance = new AbuseDetection(currentDur, 10);
    add10Requests(instance);
    LocalTime inputTime = LocalTime.parse("18:19:20.901");
    boolean value = instance.addRequest(inputTime);
    // 10 requests were added since 18:19:20.010, thus the 11th within 
    // 1 second at 18:19:20.901 fails.
    Assert.assertEquals(false, value);
  }

  /*
  * 10 Requests are added. First timestamp: 18:19:20.010. The
  * Last Timestamp 18:19:20.866. 
  */
  @Test
  public void addingMultipleRequestsGetTrue() {
    Duration currentDur = Duration.ofSeconds(1);
    AbuseDetection instance = new AbuseDetection(currentDur, 10);
    add10Requests(instance);
    LocalTime inputTime = LocalTime.parse("18:19:21.012");
    boolean value = instance.addRequest(inputTime);
    // 10 requests were added since 18:19:20.010, thus the 11th is not 
    // within a second, so it gets added, which returns true
    Assert.assertEquals(true, value);
  }

  /*
  * 10 Requests are added. First timestamp: 18:19:20.010. The
  * Last Timestamp 18:19:20.866. 
  */
  @Test
  public void requestOverSecondReturnsFalse() {
    Duration currentDur = Duration.ofSeconds(1);
    AbuseDetection instance = new AbuseDetection(currentDur, 10);
    add10Requests(instance);
    LocalTime inputTime1 = LocalTime.parse("18:19:21.012");
    boolean curVal = instance.addRequest(inputTime1);
    LocalTime otherTime = LocalTime.parse("18:19:21.109");
    boolean value = instance.addRequest(otherTime);
    // 10 requests added since 18:19:20.010, 11th request isn't within
    // second, that gets added. The 12th request is wihin a second, so 
    // it does not get added, and returns false
    Assert.assertEquals(false, value);
  }
  
  /*
  * 10 Requests are added. First timestamp: 18:19:20.010. The
  * Last Timestamp 18:19:20.866. 
  */
  @Test
  public void requestOverSecondReturnsTrue() {
    Duration currentDur = Duration.ofSeconds(1);
    AbuseDetection instance = new AbuseDetection(currentDur, 10);
    add10Requests(instance);
    LocalTime inputTime1 = LocalTime.parse("18:19:21.012");
    boolean curVal = instance.addRequest(inputTime1);
    LocalTime otherTime = LocalTime.parse("18:19:21.109");
    boolean valueReturned = instance.addRequest(otherTime);
    LocalTime timeInputted = LocalTime.parse("18:19:21.416");
    boolean value = instance.addRequest(timeInputted);
    // 10 requests added since 18:19:20.010, 11th request isn't within
    // second, that gets added. The 12th request is wihin a second, so 
    // it does not get added, and returns false. The 13th request isn't 
    // within a second so it returns true and gets added.
    Assert.assertEquals(true, value);
  }

  /*
  * 10 Requests are added. First timestamp: 23:59:55.010. The
  * Last Timestamp 00:00:00.000. 
  */
  @Test
  public void adding11RequestsNearMidnight() {
    Duration currentDur = Duration.ofSeconds(1);
    AbuseDetection instance = new AbuseDetection(currentDur, 10);
    add10RequestsNearMidnight(instance);
    boolean value = instance.addRequest(LocalTime.parse("00:01:01.020"));
    // 10 Requests were added since 23:59:55.010, the 11th request is
    // within a second so it cannot get added, so it gets true
    Assert.assertEquals(true, value);
  }

  /*
  * 10 Requests are added. First timestamp: 23:59:55.010. The
  * Last Timestamp 23:59:59.980. 
  */
  @Test
  public void adding11RequestsNearMidnightRequestGetsBlocked() {
    Duration currentDur = Duration.ofSeconds(1);
    AbuseDetection instance = new AbuseDetection(currentDur, 10);
    add10RequestsBeforeMidnight(instance);
    boolean value = instance.addRequest(LocalTime.parse("00:00:00.020"));
    // 10 Requests were added since 23:59:55.010, the 11th request is
    // within a second so it cannot get added, so it gets false
    Assert.assertEquals(false, value);
  }

  /*
  * 20 Requests are added. First timestamp: 18:19:21.000. The
  * Last Timestamp 18:19:21.900. 
  */
  @Test
  public void instance20RequestsReturnTrue() {
    Duration currentDur = Duration.ofSeconds(2);
    AbuseDetection instance = new AbuseDetection(currentDur, 20);
    boolean returnValue  = add20Requests(instance);
    // 20 Requests were added within 18:19:21.000, they all get added within
    // the amount of requests allowed, so it returns true
    Assert.assertEquals(true, returnValue);
  }

 /*
  * 20 Requests are added. First timestamp: 18:19:21.000. The
  * Last Timestamp 18:19:21.900. 
  */
  @Test
  public void instance20RequestsReturnFalse() {
    Duration currentDur = Duration.ofSeconds(2);
    AbuseDetection instance = new AbuseDetection(currentDur, 10);
    boolean returnValue = add20Requests(instance);
    LocalTime inputTime = LocalTime.parse("18:19:22.020");
    boolean value = instance.addRequest(inputTime);
    // 20 Requests were added within 18:19:21.000, the 21th request is not within
    // 2 seconds so it returns false and doesn't get added
    Assert.assertEquals(false, value);
  }
}
