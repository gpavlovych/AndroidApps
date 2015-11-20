package com.example.george.redtubesearch.Contract;

import java.util.List;

/**
 * Created by George on 10/23/2015.
 */
public class StarsList {
    private final List<Star> stars;

    public StarsList(List<Star> stars) {
        this.stars = stars;
    }

    public List<Star> getStars() {
        return stars;
    }
}
