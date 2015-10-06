package com.example.george.redtubesearch;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends AppCompatActivity {

    public static final String REDTUBE_URL = "http://api.redtube.com/?data=redtube.Videos.searchVideos&output=xml&search=%s&tags[]=MILF&thumbsize=all";

    private class DownloadXmlTask extends AsyncTask<String, Void, List<VideoItem>> {
        private final Activity context;

        public DownloadXmlTask(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            ShowProgressBar();
        }

        @Override
        protected List<VideoItem> doInBackground(String... urls) {
            try {
                // Thread.sleep(10000);
                return loadXmlFromNetwork(urls[0]);
            } catch (IOException e) {
                return null;
            } catch (XmlPullParserException e) {
                return null;
            }
        }

        private List<VideoItem> loadXmlFromNetwork(String url) throws IOException, XmlPullParserException {
            URL urlObj = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readEntries(parser, "root");
        }

        @NonNull
        private List<VideoItem> readEntries(XmlPullParser parser, String startTag) throws XmlPullParserException, IOException {
            List<VideoItem> entries = new ArrayList<VideoItem>();
            parser.require(XmlPullParser.START_TAG, "", startTag);
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                switch (name) {
                    case "video":
                        entries.add(readEntry(parser));
                        break;
                    case "videos":
                        return readEntries(parser, "videos");
                    default:
                        skip(parser);
                        break;
                }
            }
            return entries;
        }

        private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                throw new IllegalStateException();
            }
            int depth = 1;
            while (depth != 0) {
                switch (parser.next()) {
                    case XmlPullParser.END_TAG:
                        depth--;
                        break;
                    case XmlPullParser.START_TAG:
                        depth++;
                        break;
                }
            }
        }

        private VideoItem readEntry(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, "", "video");
            String title = null;
            String embedUrl = null;
            String link = null;
            embedUrl = parser.getAttributeValue("", "embed_url");
            link = parser.getAttributeValue("", "thumb");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();

                if (name.equals("title")) {
                    title = parser.getText();
                    if (title == null) {
                        parser.nextToken();
                        title = parser.getText();
                    }
                    while (parser.next() != XmlPullParser.END_TAG) parser.next();
                } /*else if (name.equals("summary")) {
                    summary = readSummary(parser);
                } else if (name.equals("link")) {
                    link = readLink(parser);
                }*/ else {
                    skip(parser);
                }
            }
            return new VideoItem(title, link, embedUrl);
        }

        @Override
        protected void onPostExecute(List<VideoItem> result) {
            HideProgressBar();
            final VideoItem itemArray[] = result.toArray(new VideoItem[result.size()]);
            VideosListAdapter adapter = new VideosListAdapter(this.context, itemArray);
            ListView list = (ListView) findViewById(R.id.videosList);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(context,PlayVideoActivity.class);
                    intent.setData(Uri.parse(itemArray[+position].videoUrl.replace("player/","")));
                    startActivity(intent);
                }
            });
        }
    }

    private void ShowProgressBar() {
        final ProgressBar contentLoading = (ProgressBar) findViewById(R.id.contentLoading);
        contentLoading.setVisibility(ProgressBar.VISIBLE);
    }

    private void HideProgressBar() {
        final ProgressBar contentLoading = (ProgressBar) findViewById(R.id.contentLoading);
        contentLoading.setVisibility(ProgressBar.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        HideProgressBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void searchButton_Click(View view) throws ExecutionException, InterruptedException {
        final EditText searchEditText = (EditText) findViewById(R.id.searchEditText);

        String searchRequest = searchEditText.getText().toString();

        new DownloadXmlTask(this).execute(String.format(REDTUBE_URL,searchRequest));
    }
}
