package edu.northeastern.group21;

import edu.northeastern.group21.Movie;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class MovieList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;

    // added by Meng
    private String searchYear;
    private String selectedGenre;
    private final String TAG = "-----MovieList----";
    private List<MovieJson> movieJsonList = new ArrayList<>();

    private Thread searchThread;
    private Boolean apiAccessible = true;

    List<Movie> mockMoviesList = new ArrayList<>();

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.movieLists_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

//        progressBar = (ProgressBar) findViewById(R.id.progressBarOnList);

        fetchMovies();
    }

    private void fetchMovies() {
        searchThread = new Thread(() -> {
            URL url = null;
            HttpURLConnection conn = null;
            BufferedReader bufferedReader = null;
            try {
                url = new URL(getFullUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("X-RapidAPI-Key", "565cb1aff7mshb7c1524ceda0bb9p139deajsn713776dec5cf");
                conn.setRequestProperty("X-RapidAPI-Host", "moviesdatabase.p.rapidapi.com");
                conn.setDoInput(true);

                conn.connect();
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    apiAccessible = true;
                    bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    final String response = stringBuilder.toString();

                    Gson gson = new Gson();
                    SearchResult searchResult = gson.fromJson(response, SearchResult.class);
                    movieJsonList = searchResult.getResults();
                    Log.d(TAG, "movieJsonList: " + movieJsonList == null ? "null" : String.valueOf(movieJsonList.size()));
                    for (MovieJson movieJson : movieJsonList) {
                        String imageUrl = movieJson.getPrimaryImage() == null? "" : movieJson.getPrimaryImage().getUrl();

                        // String name, String genres, String releaseYear, Double rating, String posterUrl
                        mockMoviesList.add(new Movie(movieJson.getTitleText().getText(),
                                movieJson.getTitleType().getText(),
                                String.valueOf(movieJson.getReleaseDate().getYear()),
                                0.0,
                                imageUrl));
                        Log.d(TAG, "___title: " + movieJson.getTitleText().getText() + ", " + movieJson.getReleaseDate().getYear());
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mockMoviesList.size() == 0) {
                                showToast("No result");
                            } else {
                                MovieAdapter adapter = new MovieAdapter(MovieList.this, mockMoviesList);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    });
                } else {
                    apiAccessible = false;
                    showToast("Something wrong with the web services. Come back later.");
                }
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
        });
        searchThread.start();
    }

    private String getFullUrl() {
        String baseUrl = "https://moviesdatabase.p.rapidapi.com/titles/x/upcoming?";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            searchYear = bundle.getString("year");
            if (searchYear != null && !searchYear.isEmpty()) {
                searchYear = "year=" + searchYear;
            } else {
                searchYear = "";
            }
            selectedGenre = bundle.getString("genre");
            if (selectedGenre != null && !selectedGenre.equals("Genre Category")) {
                selectedGenre = "&genre=" + selectedGenre;
            } else {
                selectedGenre = "";
            }
        }

        String fullUrl = baseUrl + searchYear + selectedGenre;
        Log.d(TAG, "getFullUrl: " + fullUrl);
        return fullUrl;
    }

    private void showToast(String message) {
        Toast.makeText(MovieList.this, message, Toast.LENGTH_SHORT).show();
    }
}
