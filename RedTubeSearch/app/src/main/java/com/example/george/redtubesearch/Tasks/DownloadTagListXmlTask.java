package com.example.george.redtubesearch.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.george.redtubesearch.Contract.StarsList;
import com.example.george.redtubesearch.Contract.TagList;
import com.example.george.redtubesearch.SAX.StarsListHandler;
import com.example.george.redtubesearch.SAX.TagListHandler;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by George on 10/21/2015.
 */
public class DownloadTagListXmlTask extends AsyncTask<String,Void, TagList> {

    private static final String TAG = "SAXTask";

    @Override
    protected TagList doInBackground(String... params) {
        try {
            return loadXmlFromNetwork(params[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private TagList loadXmlFromNetwork(String url) throws Exception {
        Log.i(TAG, "loadXmlFromNetwork.start");
        try {
            URL urlObj = new URL(url);
            SAXParserFactory saxPF = SAXParserFactory.newInstance();
            SAXParser saxP = saxPF.newSAXParser();
            XMLReader xmlR = saxP.getXMLReader();

            TagListHandler myXMLHandler = new TagListHandler();
            xmlR.setContentHandler(myXMLHandler);
            xmlR.parse(new InputSource(urlObj.openStream()));
            return myXMLHandler.getXMLData();
        }
        finally {
            Log.i(TAG, "loadXmlFromNetwork.end");
        }
    }
}
