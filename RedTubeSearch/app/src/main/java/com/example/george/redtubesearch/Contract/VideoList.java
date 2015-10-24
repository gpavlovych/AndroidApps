package com.example.george.redtubesearch.Contract;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by George on 10/23/2015.
 */
@Root(name="root",strict = false)
public class VideoList {
    public VideoList(@ElementList(inline=false,name="videos") List<VideoItem> videos)
    {
        this.videos = videos;
    }

    private final List<VideoItem> videos;

    @ElementList(inline=false,name="videos")
    public List<VideoItem> getVideos()
    {
        return videos;
    }
}
