package com.example.george.redtubesearch.Contract;


/**
 * Created by George on 10/4/2015.
 */
public class VideoItem {
    private final String title;
    private final String thumbUrl;
    private final String videoUrl;

    public VideoItem(String title, String thumbUrl, String videoUrl) {
        this.title = title;
        this.thumbUrl = thumbUrl;
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
