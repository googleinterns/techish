package com.google.sps.data;

import java.util.Date;

public final class ProfessionalProfile {

  private final String fullname;
  private final String school;
  private final String careerTitle;

  /**
   * @param {!String} fullname User's full name.
   * @param {!String} company The user's college.
   * @param {!String} careerTitle User's career title.
   */
  public ProfessionalProfile(String fullname, String company, String careerTitle) {
    this.fullname = fullname;
    this.school = company;
    this.careerTitle = careerTitle;
  }
} 