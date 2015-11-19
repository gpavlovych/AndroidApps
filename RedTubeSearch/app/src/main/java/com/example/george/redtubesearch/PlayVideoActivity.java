package com.example.george.redtubesearch;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayVideoActivity extends AppCompatActivity {
    private MediaController mediaControls;

    private class DownloadHtmlTask extends AsyncTask<String, Void, String> {
        private final Activity context;

        public DownloadHtmlTask(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            //ShowProgressBar();
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                // Thread.sleep(10000);
                return loadStringFromNetwork(urls[0]);
            } catch (IOException e) {
                return null;
            }
        }

        private String loadStringFromNetwork(String url) throws IOException {
            URL urlObj = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);
        }

        private String readStream(BufferedInputStream content) throws IOException {
            int numRead;
            final int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            ByteArrayOutputStream outString = new ByteArrayOutputStream();
            try{
                while ((numRead = content.read(buffer)) != -1) {
                    outString.write(buffer, 0, numRead);
                }
            } finally {
                content.close();
            }
            return new String(outString.toByteArray(), Charset.defaultCharset());
        }

        @Override
        protected void onPostExecute(String result) {
            VideoView videoView = (VideoView)findViewById(R.id.videoView);
            Pattern p = Pattern.compile("<source[^<]*src=\"([^\"]*)\"");
            Matcher m = p.matcher(result);
            while (m.find()) { // Find each match in turn; String can't do this.
                result = m.group(1); // Access a submatch group; String can't do this.
            }
            videoView.setVideoURI(Uri.parse(result));
            videoView.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        VideoView videoView = (VideoView)findViewById(R.id.videoView);
        if (mediaControls == null) {
            mediaControls = new MediaController(this);
        }
        videoView.setMediaController(mediaControls);
        Uri embeduri = getIntent().getData();
        new DownloadHtmlTask(this).execute(embeduri.toString());
    }
}
