package com.example.george.redtubesearch.Tasks;

import android.os.AsyncTask;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by George on 10/21/2015.
 */
public class DownloadXmlTask<T> extends AsyncTask<String,Void,T> {
    final private Class<T> classOfT;

    public DownloadXmlTask(Class<T> classOfT) {
        this.classOfT = classOfT;
    }

    @Override
    protected T doInBackground(String... params) {
        try {
            return loadXmlFromNetwork(params[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private T loadXmlFromNetwork(String url) throws Exception {
        URL urlObj = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
        BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
        Serializer serializer = new Persister();
        return serializer.read(classOfT, in);
    }
}
