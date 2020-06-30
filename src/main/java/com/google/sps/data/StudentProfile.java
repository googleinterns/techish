package com.google.sps.data;

import java.util.Date;

public final class StudentProfile {

  private final long id;
  private final String fullname;
  private final String school;
  private final String major;

  /**
   * @param {!id} id The unique identifier for each profile.
   * @param {!String} fullname User's full name.
   * @param {!String} school The user's college.
   * @param {!String} fullname User's major.
   */
  public StudentProfile(long id, String fullname, String school, String major) {
    this.id = id;
    this.fullname = fullname;
    this.school = school;
    this.major = major;
  }
} 