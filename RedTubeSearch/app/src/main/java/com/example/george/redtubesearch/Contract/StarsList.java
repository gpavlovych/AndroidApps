package com.example.george.redtubesearch.Contract;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Collection;
import java.util.List;

/**
 * Created by George on 10/23/2015.
 */
@Root(name = "stars")
public class StarsList {
    private final List<Star> stars;

    public StarsList(@ElementList(inline = true,entry = "star") List<Star> stars) {
        this.stars = stars;
    }

    @ElementList(inline = true,entry = "star")
    public List<Star> getStars() {
        return stars;
    }
}
