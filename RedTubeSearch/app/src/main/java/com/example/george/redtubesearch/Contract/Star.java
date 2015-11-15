package com.example.george.redtubesearch.Contract;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Created by George on 11/12/2015.
 */
@Root(name="star",strict = false)
public class Star {
    private final String name;
    private final String thumbUrl;
    private boolean selected;

    public Star(@Attribute(name = "thumb") String thumbUrl, @Text(data = true) String name) {
        this.thumbUrl = thumbUrl;
        this.name = name;
        this.selected=false;
    }

    @Attribute(name = "thumb")
    public String getThumbUrl() {
        return thumbUrl;
    }

    @Text(data = true)
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
