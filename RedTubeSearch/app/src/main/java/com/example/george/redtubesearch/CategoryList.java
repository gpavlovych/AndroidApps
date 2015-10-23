package com.example.george.redtubesearch;


import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by George on 10/22/2015.
 */
@Root(name="categories")
public class CategoryList {
    @ElementList(inline = true,entry="category")
    private List<String> categories;

    public List<String> getCategories()
    {
        return categories;
    }
}
