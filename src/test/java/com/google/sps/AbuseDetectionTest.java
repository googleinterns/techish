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

  public void addMultipleRequests(AbuseDetection instance){
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

  public void addRequestsNearMidnight(AbuseDetection instance){
    instance.addRequest(LocalTime.parse("23:59:55.010"));
    instance.addRequest(LocalTime.parse("23:59:55.055"));
    instance.addRequest(LocalTime.parse("23:59:55.124"));
    instance.addRequest(LocalTime.parse("23:59:55.231"));
    instance.addRequest(LocalTime.parse("23:59:55.344"));
    instance.addRequest(LocalTime.parse("23:59:55.421"));
    instance.addRequest(LocalTime.parse("23:59:55.560"));
    instance.addRequest(LocalTime.parse("23:59:55.612"));
    instance.addRequest(LocalTime.parse("23:59:55.723"));
    instance.addRequest(LocalTime.parse("23:59:55.800"));
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

  @Test
  public void addingOneTimeRequest() {
    Duration currentDur = Duration.ofSeconds(1);
    AbuseDetection instance = new AbuseDetection(currentDur, 10);
    LocalTime inputTime = LocalTime.parse("18:19:20.010");
    boolean value = instance.addRequest(inputTime);

    Assert.assertEquals(true, value);
  }

  @Test
  public void addingMultipleRequestsGetFalse() {
    Duration currentDur = Duration.ofSeconds(1);
    AbuseDetection instance = new AbuseDetection(currentDur, 10);
    addMultipleRequests(instance);
    LocalTime inputTime = LocalTime.parse("18:19:20.901");
    boolean value = instance.addRequest(inputTime);

    Assert.assertEquals(false, value);
  }


  @Test
  public void addingMultipleRequestsGetTrue() {
    Duration currentDur = Duration.ofSeconds(1);
    AbuseDetection instance = new AbuseDetection(currentDur, 10);
    addMultipleRequests(instance);
    LocalTime inputTime = LocalTime.parse("18:19:21.012");
    boolean value = instance.addRequest(inputTime);

    Assert.assertEquals(true, value);
  }

  @Test
  public void requestOverSecondReturnsFalse() {
    Duration currentDur = Duration.ofSeconds(1);
    AbuseDetection instance = new AbuseDetection(currentDur, 10);
    addMultipleRequests(instance);
    LocalTime inputTime1 = LocalTime.parse("18:19:21.012");
    boolean curVal = instance.addRequest(inputTime1);
    LocalTime otherTime = LocalTime.parse("18:19:21.109");
    boolean value = instance.addRequest(otherTime);

    Assert.assertEquals(false, value);
  }
  

  @Test
  public void requestOverSecondReturnsTrue() {
    Duration currentDur = Duration.ofSeconds(1);
    AbuseDetection instance = new AbuseDetection(currentDur, 10);
    addMultipleRequests(instance);
    LocalTime inputTime1 = LocalTime.parse("18:19:21.012");
    boolean curVal = instance.addRequest(inputTime1);
    LocalTime otherTime = LocalTime.parse("18:19:21.109");
    boolean valueReturned = instance.addRequest(otherTime);
    LocalTime timeInputted = LocalTime.parse("18:19:21.416");
    boolean value = instance.addRequest(timeInputted);

    Assert.assertEquals(true, value);
  }

  @Test
  public void adding11RequestsNearMidnight() {
    Duration currentDur = Duration.ofSeconds(1);
    AbuseDetection instance = new AbuseDetection(currentDur, 10);
    addRequestsNearMidnight(instance);
    LocalTime inputTime = LocalTime.parse("00:01:01.020");
    boolean value = instance.addRequest(inputTime);

    Assert.assertEquals(true, value);
  }

  @Test
  public void instance20RequestsReturnTrue() {
    Duration currentDur = Duration.ofSeconds(2);
    AbuseDetection instance = new AbuseDetection(currentDur, 20);
    boolean returnValue  = add20Requests(instance);
    
    Assert.assertEquals(true, returnValue);
  }

  @Test
  public void instance20RequestsReturnFalse() {
    Duration currentDur = Duration.ofSeconds(2);
    AbuseDetection instance = new AbuseDetection(currentDur, 10);
    boolean returnValue = add20Requests(instance);
    LocalTime inputTime = LocalTime.parse("18:19:22.020");
    boolean value = instance.addRequest(inputTime);
    
    Assert.assertEquals(false, value);
  }

}
