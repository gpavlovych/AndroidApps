package com.example.george.redtubesearch.Contract;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by George on 10/22/2015.
 */
@Root(name = "tags")
public class TagList {
    private final List<String> tags;

    public TagList(@ElementList(inline = true,entry = "tag") List<String> tags) {
        this.tags = tags;
    }

    @ElementList(inline = true,entry = "tag")
    public List<String> getTags()
    {
        return tags;
    }
}
