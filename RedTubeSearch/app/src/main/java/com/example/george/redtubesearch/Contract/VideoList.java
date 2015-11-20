package com.example.george.redtubesearch.Contract;

import java.util.List;

/**
 * Created by George on 10/23/2015.
 */
public class VideoList {
    public VideoList(List<VideoItem> videos)
    {
        this.videos = videos;
    }

    private final List<VideoItem> videos;

    public List<VideoItem> getVideos()
    {
        return videos;
    }
}
