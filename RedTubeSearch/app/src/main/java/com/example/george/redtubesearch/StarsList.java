package com.example.george.redtubesearch;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Collection;
import java.util.List;

/**
 * Created by George on 10/23/2015.
 */
@Root(name = "stars")
public class StarsList {
    @ElementList(inline = true,entry = "star")
    private List<String> stars;

    public List<String> getStars() {
        return stars;
    }
}
