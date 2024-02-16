package edu.northeastern.group21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebService extends AppCompatActivity {
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();

    private final String TAG = "____webService----";

    private String paramGenre = "genre";
    private String year = "year";

private SearchResult searchResult = new SearchResult();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView);

        PingWebServiceTask task = new PingWebServiceTask();
        task.execute("2024", "Drama");

    }

    private void loadProgressBar() {
        // Start long running operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            textView.setText(progressStatus+"%");
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private class PingWebServiceTask extends AsyncTask<String, Integer, SearchResult> {

        protected void onProgressUpdate(Integer... values) {
            Log.d(TAG, "Making progress...");
        }

        @Override
        protected SearchResult doInBackground(String... params) {

            String year = params[0];
            String genre = params[1];

            String baseUrl = "https://moviesdatabase.p.rapidapi.com/titles/x/upcoming";
            String queryString = null;
            try {
                queryString = String.format("year=%s&genre=%s",
                        URLEncoder.encode(year, "UTF-8"),
                        URLEncoder.encode(genre, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            String fullUrl = baseUrl + "?" + queryString;

            Log.d(TAG, fullUrl);

            URL url = null;
            HttpURLConnection conn = null;
            try {

                url = new URL(fullUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("X-RapidAPI-Key", "565cb1aff7mshb7c1524ceda0bb9p139deajsn713776dec5cf");
                conn.setRequestProperty("X-RapidAPI-Host", "moviesdatabase.p.rapidapi.com");
                conn.setDoInput(true);

                conn.connect();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                final String response =  stringBuilder.toString();

//                InputStream inputStream = conn.getInputStream();
//                final String resp = convertStreamToString(inputStream);

                Log.d(TAG, "doInBackground: " + response);

//                Gson gson = new Gson();
//
//                // Define the type of the list you want to convert to
//
////                Person person = gson.fromJson(jsonString, Person.class);
////                Type listType = new TypeToken<List<Movie>>() {}.getType();
//                searchResult = gson.fromJson(response, SearchResult.class);
//
//                Log.d(TAG, "___size: " + searchResult.getEntries());
                return searchResult;
            } catch (ProtocolException e) {
                throw new RuntimeException(e);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }  finally {
                // Close the connections
                if (conn != null) {
                    conn.disconnect();
                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        }

//        @Override
//        protected void onPostExecute(String... s) {
//            super.onPostExecute(s);
////            TextView result_view = (TextView)findViewById(R.id.result_textview);
////            result_view.setText(s[0]);
//        }
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext()? s.next().replace(",", ",\n") : "";
    }

}