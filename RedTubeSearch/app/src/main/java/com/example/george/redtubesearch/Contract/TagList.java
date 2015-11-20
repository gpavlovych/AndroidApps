package com.example.george.redtubesearch.Contract;

import java.util.List;

/**
 * Created by George on 10/22/2015.
 */
public class TagList {
    private final List<String> tags;

    public TagList(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags()
    {
        return tags;
    }
}
