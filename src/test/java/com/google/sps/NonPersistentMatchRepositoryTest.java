package com.google.sps;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** */
@RunWith(JUnit4.class)
public final class NonPersistentMatchRepositoryTest {
  @Test
  public void emptyTest() {
    Assert.assertFalse(1 == 2);
  }  
}
