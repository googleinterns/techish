package com.google.sps;

import com.google.sps.algorithms.MatchQuery;
import com.google.sps.data.MatchRequest;
import com.google.sps.algorithms.AbuseDetection;
import java.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
/** */
@RunWith(JUnit4.class)
public final class AbuseDetectionTest {  

 
  public boolean addMultipleRequests(AbuseDetection instance){
    LocalTime inputTime = LocalTime.parse("18:19:20.010");
    boolean value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("18:19:20.112");
    value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("18:19:20.115");
    value = instance.addRequest(inputTime);

    inputTime = LocalTime.parse("18:19:20.180");
    value = instance.addRequest(inputTime);

    inputTime = LocalTime.parse("18:19:20.340");
    value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("18:19:20.590");
    value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("18:19:20.605");
    value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("18:19:20.712");
    value = instance.addRequest(inputTime);
   
    inputTime = LocalTime.parse("18:19:20.832");
    value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("18:19:20.866");
    value = instance.addRequest(inputTime);
    
    return value;
  }
 public boolean addRequestsNearMidnight(AbuseDetection instance){
    LocalTime inputTime = LocalTime.parse("23:59:55.010");
    boolean value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("23:59:55.055");
    value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("23:59:55.124");
    value = instance.addRequest(inputTime);

    inputTime = LocalTime.parse("23:59:55.231");
    value = instance.addRequest(inputTime);

    inputTime = LocalTime.parse("23:59:55.344");
    value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("23:59:55.421");
    value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("23:59:55.560");
    value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("23:59:55.612");
    value = instance.addRequest(inputTime);
   
    inputTime = LocalTime.parse("23:59:55.723");
    value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("23:59:55.800");
    value = instance.addRequest(inputTime);

    return value;
  }

   public boolean add20Requests(AbuseDetection instance){
    LocalTime inputTime = LocalTime.parse("23:59:55.010");
    boolean value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("23:59:55.015");
    value = instance.addRequest(inputTime);

    inputTime = LocalTime.parse("23:59:55.026");
    value = instance.addRequest(inputTime);

    inputTime = LocalTime.parse("23:59:55.036");
    value = instance.addRequest(inputTime);
 
    inputTime = LocalTime.parse("23:59:55.047");
    value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("23:59:55.070");
    value = instance.addRequest(inputTime);
        
    inputTime = LocalTime.parse("23:59:55.090");
    value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("23:59:55.124");
    value = instance.addRequest(inputTime);

    inputTime = LocalTime.parse("23:59:55.145");
    value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("23:59:55.231");
    value = instance.addRequest(inputTime);

    inputTime = LocalTime.parse("23:59:55.271");
    value = instance.addRequest(inputTime);

    inputTime = LocalTime.parse("23:59:55.344");
    value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("23:59:55.421");
    value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("23:59:55.560");
    value = instance.addRequest(inputTime);

    inputTime = LocalTime.parse("23:59:55.590");
    value = instance.addRequest(inputTime);
    
    inputTime = LocalTime.parse("23:59:55.612");
    value = instance.addRequest(inputTime);

    inputTime = LocalTime.parse("23:59:55.642");
    value = instance.addRequest(inputTime);
   
    inputTime = LocalTime.parse("23:59:55.723");
    value = instance.addRequest(inputTime);

    inputTime = LocalTime.parse("23:59:55.800");
    value = instance.addRequest(inputTime);

    inputTime = LocalTime.parse("23:59:55.900");
    value = instance.addRequest(inputTime);
   
    return value;
  }

  @Test
  public void addingOneTimeRequest() {
    AbuseDetection instance = new AbuseDetection(1, 10);
    LocalTime inputTime = LocalTime.parse("18:19:20.010");
    boolean value = instance.addRequest(inputTime);

    Assert.assertEquals(true, value);
  }

  @Test
  public void addingMultipleRequestsGetFalse() {
    AbuseDetection instance = new AbuseDetection(1, 10);
    boolean returnValue = addMultipleRequests(instance);
    LocalTime inputTime = LocalTime.parse("18:19:20.901");
    boolean value = instance.addRequest(inputTime);

    Assert.assertEquals(false, value);
  }

  @Test
  public void addingMultipleRequestsGetTrue() {
    AbuseDetection instance = new AbuseDetection(1, 10);
    boolean returnValue = addMultipleRequests(instance);
    LocalTime inputTime = LocalTime.parse("18:19:21.012");
    boolean value = instance.addRequest(inputTime);

    Assert.assertEquals(true, value);
  }

  @Test
  public void requestOverSecondReturnsFalse() {
    AbuseDetection instance = new AbuseDetection(1, 10);
    boolean returnValue = addMultipleRequests(instance);
    LocalTime inputTime1 = LocalTime.parse("18:19:21.012");
    boolean curVal = instance.addRequest(inputTime1);
    LocalTime otherTime = LocalTime.parse("18:19:21.109");
    boolean value = instance.addRequest(otherTime);

    Assert.assertEquals(false, value);
  }
  

  @Test
  public void requestOverSecondReturnsTrue() {
    AbuseDetection instance = new AbuseDetection(1, 10);
    boolean returnValue = addMultipleRequests(instance);
    LocalTime inputTime1 = LocalTime.parse("18:19:21.012");
    boolean curVal = instance.addRequest(inputTime1);
    LocalTime otherTime = LocalTime.parse("18:19:21.109");
    boolean valueReturned = instance.addRequest(otherTime);
    LocalTime timeInputted = LocalTime.parse("18:19:21.416");
    boolean value = instance.addRequest(timeInputted);

    Assert.assertEquals(true, value);
  }

  @Test
  public void addingRequestsNearMidnightTest() {
    AbuseDetection instance = new AbuseDetection(1, 10);
    boolean returnValue  = addRequestsNearMidnight(instance);
    LocalTime inputTime = LocalTime.parse("00:21:20.020");
    boolean value = instance.addRequest(inputTime);

    Assert.assertEquals(false, value);
  }

  @Test
  public void instance20RequestsReturnTrue() {
    AbuseDetection instance = new AbuseDetection(10, 20);
    boolean returnValue  = add20Requests(instance);

    
    Assert.assertEquals(true, returnValue);
  }

  @Test
  public void instance20RequestsReturnFalse() {
    AbuseDetection instance = new AbuseDetection(10, 20);
    boolean returnValue  = add20Requests(instance);
    LocalTime inputTime = LocalTime.parse("00:21:20.020");
    boolean value = instance.addRequest(inputTime);
    
    Assert.assertEquals(false, value);
  }
}
