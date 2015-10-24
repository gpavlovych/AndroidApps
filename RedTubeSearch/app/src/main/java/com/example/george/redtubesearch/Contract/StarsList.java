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
    private final List<String> stars;

    public StarsList(@ElementList(inline = true,entry = "star") List<String> stars) {
        this.stars = stars;
    }

    @ElementList(inline = true,entry = "star")
    public List<String> getStars() {
        return stars;
    }
}
