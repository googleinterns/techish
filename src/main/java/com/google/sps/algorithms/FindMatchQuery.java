package com.google.sps.algorithms;

import java.util.Collection;
import java.util.ArrayList;
import com.google.sps.data.MatchRequest;
import com.google.sps.data.User;

public final class FindMatchQuery {

    public Collection<User> query(MatchRequest request) {
        Collection<User> mock = new ArrayList<User>();
        User userA = new User(request.getCriteria());
        mock.add(userA);
        return mock;
    }




}