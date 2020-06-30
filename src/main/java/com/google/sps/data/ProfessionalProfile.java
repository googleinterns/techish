package com.google.sps.data;

import java.util.Date;

public final class ProfessionalProfile {
  
  private final long id;
  private final String fullname;
  private final String school;
  private final String careerTitle;

  /**
   * @param {!id} id The unique identifier for each profile.
   * @param {!String} fullname User's full name.
   * @param {!String} company The user's company.
   * @param {!String} careerTitle User's career title.
   */
  public ProfessionalProfile(long id, String fullname, String company, String careerTitle) {
    this.id = id;
    this.fullname = fullname;
    this.school = company;
    this.careerTitle = careerTitle;
  }
} 