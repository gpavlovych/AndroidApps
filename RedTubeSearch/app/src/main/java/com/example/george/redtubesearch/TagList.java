package com.example.george.redtubesearch;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by George on 10/22/2015.
 */
@Root(name = "tags")
public class TagList {
    @ElementList(inline = true,entry = "tag")
    private List<String> tags;
    public List<String> getTags()
    {
        return tags;
    }
}
