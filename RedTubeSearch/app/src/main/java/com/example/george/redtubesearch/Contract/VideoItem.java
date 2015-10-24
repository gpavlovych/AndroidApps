package com.example.george.redtubesearch.Contract;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by George on 10/4/2015.
 */
@Root(name="video",strict = false)
public class VideoItem {
    private final String title;
    private final String thumbUrl;
    private final String videoUrl;

    public VideoItem(@Element(name="title") String title, @Attribute(name = "thumb") String thumbUrl, @Attribute(name = "embed_url") String videoUrl) {
        this.title = title;
        this.thumbUrl = thumbUrl;
        this.videoUrl = videoUrl;
    }

    @Element(name="title")
    public String getTitle() {
        return title;
    }

    @Attribute(name = "thumb")
    public String getThumbUrl() {
        return thumbUrl;
    }

    @Attribute(name = "embed_url")
    public String getVideoUrl() {
        return videoUrl;
    }
}
