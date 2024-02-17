package edu.northeastern.group21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;


import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

// added by Meng
import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;




public class WebService extends AppCompatActivity {
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();

    private TextInputLayout yearSearchLayout;
    private Spinner genreSearchLayout;
    private Button advancedSearch;
    private Button hideAdvancedSearch;

    // added by Meng
    private final String TAG = "____webService----";

    private SearchResult searchResult = new SearchResult();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);
        yearSearchLayout = (TextInputLayout) findViewById(R.id.textInputEditLayoutYear);
        genreSearchLayout = (Spinner) findViewById(R.id.spinnerGenre);
        advancedSearch = (Button) findViewById(R.id.buttonAdvancedSearch);
        hideAdvancedSearch = (Button) findViewById(R.id.buttonHideAdvancedSearch);
        hideAdvancedSearch();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView);
        progressBar.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinnerGenre,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSearchLayout.setAdapter(adapter);
      
        // added by Meng
        progressBar.setMax(5);
        PingWebServiceTask task = new PingWebServiceTask();
        task.execute("2024", "Drama");

        // Start long running operation in a background thread
//        new Thread(new Runnable() {
//            public void run() {
//                while (progressStatus < 100) {
//                    progressStatus += 1;
//                    // Update the progress bar and display the
//                    //current value in the text view
//                    handler.post(new Runnable() {
//                        public void run() {
//                            progressBar.setProgress(progressStatus);
//                            textView.setText(progressStatus+"%");
//                        }
//                    });
//                    try {
//                        // Sleep for 200 milliseconds.
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
    }

    // added by Meng
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
                            textView.setText(progressStatus + "%");
                            Log.d(TAG, "update progress: " + progressStatus);
                            try {
                                // Sleep for 200 milliseconds.
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
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

    public void hideAdvancedSearch() {
        yearSearchLayout.setVisibility(View.INVISIBLE);
        genreSearchLayout.setVisibility(View.INVISIBLE);
        advancedSearch.setVisibility(View.VISIBLE);
        hideAdvancedSearch.setVisibility(View.INVISIBLE);
    }

    public void showAdvancedSearch(View view) {
        yearSearchLayout.setVisibility(View.VISIBLE);
        genreSearchLayout.setVisibility(View.VISIBLE);
        advancedSearch.setVisibility(View.INVISIBLE);
        hideAdvancedSearch.setVisibility(View.VISIBLE);
    }

    public void hideAdvancedSearch(View view) {
        hideAdvancedSearch();
    }

    //Search function currently only contains UI related code
    public void search(View view) {
        yearSearchLayout.setVisibility(View.INVISIBLE);
        genreSearchLayout.setVisibility(View.INVISIBLE);
        advancedSearch.setVisibility(View.INVISIBLE);
        hideAdvancedSearch.setVisibility(View.INVISIBLE);
    }

    public void onclick(View view) {
        int theId = view.getId();

        if (theId == R.id.buttonSearch) {
            // Click for search item
            Intent intent = new Intent(WebService.this, MovieList.class);
            startActivity(intent);
        }
    }

    // added by Meng
    private class PingWebServiceTask extends AsyncTask<String, Integer, SearchResult> {

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute");
            progressBar.setProgress(0);
            textView.setText("0%");
            progressBar.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }

        protected void onProgressUpdate(Integer... values) {
            Log.d(TAG, "Making progress...");
            int progress = values[0];
            Log.d(TAG, "update progress: " + progress);
            progressBar.setProgress(progress);
            textView.setText(progress / 5 * 100 + "%");
        }

        protected void onPostExecute(SearchResult searchResult) {
            Log.d(TAG, "onPostExecute");
            textView.setText("100%");
        }

        @Override
        protected SearchResult doInBackground(String... params) {
            // Set up variables for progress tracking
            int totalSteps = 5; // Total steps for the task
            int currentStep = 0; // Current step

            // define discrete progress points to update the UI accordingly: step 1
            currentStep++;
            publishProgress(currentStep);
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

            // step 2
            currentStep++;
            publishProgress(currentStep);
            URL url = null;
            HttpURLConnection conn = null;
            BufferedReader bufferedReader = null;
            try {
                url = new URL(fullUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("X-RapidAPI-Key", "565cb1aff7mshb7c1524ceda0bb9p139deajsn713776dec5cf");
                conn.setRequestProperty("X-RapidAPI-Host", "moviesdatabase.p.rapidapi.com");
                conn.setDoInput(true);

                conn.connect();

                // step 3
                currentStep++;
                publishProgress(currentStep);

                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                final String response = stringBuilder.toString();

                // step 4
                currentStep++;
                publishProgress(currentStep);
                Gson gson = new Gson();
                searchResult = gson.fromJson(response, SearchResult.class);
                List<MovieJson> movieJsonList = searchResult.getResults();
                for (MovieJson movieJson : movieJsonList) {
                    Log.d(TAG, "___title: " + movieJson.getTitleText().getText() + ", " + movieJson.getReleaseDate().getYear());
                }

                // step 5
                currentStep++;
                publishProgress(currentStep);
                return searchResult;
            } catch (ProtocolException e) {
                throw new RuntimeException(e);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                // Close the connections
                if (conn != null) {
                    conn.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}