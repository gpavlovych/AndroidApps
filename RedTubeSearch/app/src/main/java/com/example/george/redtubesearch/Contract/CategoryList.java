package com.example.george.redtubesearch.Contract;


import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by George on 10/22/2015.
 */
@Root(name="categories")
public class CategoryList {
    private final List<String> categories;

    public CategoryList(@ElementList(inline = true,entry="category") List<String> categories) {
        this.categories = categories;
    }

    @ElementList(inline = true,entry="category")
    public List<String> getCategories()
    {
        return categories;
    }
}