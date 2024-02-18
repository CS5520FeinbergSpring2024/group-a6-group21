package edu.northeastern.group21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;


import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
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
    private TextInputEditText textInputEditTextYear;

    private String selectedGenre = null;
    private final String TAG = "-----webService----";


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
        textInputEditTextYear = findViewById(R.id.textInputEditTextYear);
        genreSearchLayout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item from the spinner
                selectedGenre = parentView.getItemAtPosition(position).toString();

                // Do something with the selected item
                Log.d("Spinner", "Selected genre: " + selectedGenre);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle case where nothing is selected (optional)
            }
        });

    }

    // added by Meng for AsyncTask
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
    }

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
            Bundle bundle = new Bundle();
            bundle.putString("year", textInputEditTextYear.getText().toString());
            bundle.putString("genre", selectedGenre);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

}