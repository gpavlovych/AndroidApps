package com.example.george.redtubesearch.SAX;

import com.example.george.redtubesearch.Contract.Star;
import com.example.george.redtubesearch.Contract.StarsList;
import com.example.george.redtubesearch.Contract.TagList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 11/20/2015.
 */
public class TagListHandler extends DefaultHandler {
    String elementValue = null;
    Boolean elementOn = false;
    private List<String> list = null;
    private TagList data = null;
    private String dataItem;

    public TagList getXMLData() {
        return data;
    }

    /**
     * This will be called when the tags of the XML starts.
     **/
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if (qName.equals("tags")) {
            list = new ArrayList<String>();
        }
        if (qName.equals("tag")) {
            elementOn = true;
        }
    }

    /**
     * This will be called when the tags of the XML end.
     **/
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        elementOn = false;

        /**
         * Sets the values after retrieving the values from the XML tags
         * */
        if (qName.equalsIgnoreCase("tag")) {
            list.add(dataItem);
        } else if (qName.equalsIgnoreCase("tags")) {
            data = new TagList(list);
        }
    }

    /**
     * This is called to get the tags value
     **/
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if (elementOn) {
            elementValue = new String(ch, start, length);
            dataItem = elementValue;
            elementOn = false;
        }
    }
}
