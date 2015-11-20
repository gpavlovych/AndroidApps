package com.example.george.redtubesearch.SAX;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by George on 11/20/2015.
 */
public class XMLGettersSetters {
    private ArrayList<String> company = new ArrayList<String>();
    public ArrayList<String> getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company.add(company);
        Log.i("This is the company:", company);
    }
}
