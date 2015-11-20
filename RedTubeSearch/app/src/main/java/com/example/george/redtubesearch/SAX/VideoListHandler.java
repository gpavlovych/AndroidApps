package com.example.george.redtubesearch.SAX;

import com.example.george.redtubesearch.Contract.Star;
import com.example.george.redtubesearch.Contract.StarsList;
import com.example.george.redtubesearch.Contract.VideoItem;
import com.example.george.redtubesearch.Contract.VideoList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 11/20/2015.
 */
public class VideoListHandler extends DefaultHandler {
    private String elementValue = null;
    private List<VideoItem> list = null;
    private VideoList data = null;
    private VideoItem dataItem;
    private String title;
    private String thumbUrl;
    private String videoUrl;
    private boolean elementOnVideos = false;
    private boolean elementOnVideo = false;
    private boolean elementOnTitle = false;


    public VideoList getXMLData() {
        return data;
    }

    /**
     * This will be called when the tags of the XML starts.
     **/
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if (qName.equals("videos")) {
            elementOnVideos = true;
            list = new ArrayList<VideoItem>();
        }
        if (elementOnVideos && qName.equals("video")) {
            elementOnVideo = true;
            thumbUrl = attributes.getValue("thumb");
            videoUrl = attributes.getValue("embed_url");
        }
        if (elementOnVideo && qName.equals("title")) {
            elementOnTitle = true;
        }
    }

    /**
     * This will be called when the tags of the XML end.
     **/
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {


        /**
         * Sets the values after retrieving the values from the XML tags
         * */
        if (elementOnVideo && qName.equalsIgnoreCase("title")) {
            elementOnTitle = false;
        }
        if (elementOnVideos && qName.equalsIgnoreCase("video")) {
            elementOnVideo = false;
            dataItem = new VideoItem(title, thumbUrl, videoUrl);
            list.add(dataItem);
        } else if (qName.equalsIgnoreCase("videos")) {
            elementOnVideos = false;
            data = new VideoList(list);
        }
    }

    /**
     * This is called to get the tags value
     **/
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if (elementOnTitle) {
            elementValue = new String(ch, start, length);
            title = elementValue;
            elementOnTitle = false;
        }
    }
}
