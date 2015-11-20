package com.example.george.redtubesearch.Contract;


import java.util.List;

/**
 * Created by George on 10/22/2015.
 */
public class CategoryList {
    private final List<String> categories;

    public CategoryList(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCategories()
    {
        return categories;
    }
}
