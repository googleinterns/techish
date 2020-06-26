package com.google.sps.data;

import java.util.ArrayList;
import java.util.Collection;

public final class MatchRequest {
    //in the future, this will become a data structure but for MVP, we are just matching based on one criteria
    private final String matchCriteria;

    public MatchRequest(String parameter) {
        matchCriteria = parameter;
    }

    public String getCriteria() {
        return matchCriteria;
    }

    public void changeCriteria(String newParameter) {
        matchCriteria = newParameter;
    }
}
  private final Collection<String> matchCriteria = new ArrayList();
}
