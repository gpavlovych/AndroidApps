package com.example.george.redtubesearch;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by George on 10/4/2015.
 */
public class BitmapDownloader  extends AsyncTask<String,Void,Bitmap>
{
    private final ImageView view;

    public BitmapDownloader(ImageView view)
{
    this.view = view;
}
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();

    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        // TODO Auto-generated method stub
        try
        {
            //URL url = new URL( "http://a3.twimg.com/profile_images/670625317/aam-logo-v3-twitter.png");
            String url = urls[0];

            return downloadBitmap(url);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if(result!=null)
        {
            view.setImageBitmap(result);
        }

    }

    private Bitmap downloadBitmap(String url) throws IOException {
        URL urlObj = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
        BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        return bitmap;
    }
}