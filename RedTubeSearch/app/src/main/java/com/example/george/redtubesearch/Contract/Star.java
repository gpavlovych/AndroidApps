package com.example.george.redtubesearch.Contract;


/**
 * Created by George on 11/12/2015.
 */
public class Star {
    private final String name;
    private final String thumbUrl;
    private boolean selected;

    public Star(String thumbUrl, String name) {
        this.thumbUrl = thumbUrl;
        this.name = name;
        this.selected=false;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getName() {
        return name;
    }

    public void setSelected(boolean value) {
        selected = value;
    }

    public boolean getSelected() {
        return selected;
    }

    @Override
    public String toString() {
        return name;
    }
}
