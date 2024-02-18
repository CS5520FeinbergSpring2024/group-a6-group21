package edu.northeastern.group21;
import edu.northeastern.group21.Movie;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;


public class MovieList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.movieLists_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        fetchMovies();

        // Mock data for testing
        List<Movie> mockMoviesList = new ArrayList<>();
        mockMoviesList.add(new Movie("Minions & More 1", "TV", "2022 | 48 min", 6.5, "https://www.filmofilia.com/wp-content/uploads/2013/06/DESPICABLE-ME-2-Phil-The-Minion-Poster.jpg"));
        mockMoviesList.add(new Movie("The School for Good and Evil Poster and Teaser Trailer ", "Drama", "2022 | 1h 48min", 8.5, "https://www.filmofilia.com/wp-content/uploads/2022/07/the-school-for-good-evil-poster-and-teaser-trailer-1.jpg"));
        mockMoviesList.add(new Movie("The Protector (2022) Poster and Trailer", "Drama", "1994 | 2h 22m", 8.8, "https://www.filmofilia.com/wp-content/uploads/2022/07/the-protector-2022-poster-and-trailer-0.jpg"));
        mockMoviesList.add(new Movie("The Godfather ", "Drama", "1972 | 2h 55m", 9.2, "https://static.wikia.nocookie.net/international-entertainment-project/images/9/9b/The_Godfather_-_poster_%28English%29.jpg/revision/latest?cb=20231004233807.jpg"));
        mockMoviesList.add(new Movie("Accidental Texan Poster", "TV", "2022 | 48 min", 6.5, "https://www.filmofilia.com/wp-content/uploads/2024/02/accidental-texan-poster-1.jpg"));
        mockMoviesList.add(new Movie("Avatar ", "TV", "2022 | 48 min", 5.0, "https://m.media-amazon.com/images/M/MV5BYjhiNjBlODctY2ZiOC00YjVlLWFlNzAtNTVhNzM1YjI1NzMxXkEyXkFqcGdeQXVyMjQxNTE1MDA@._V1_.jpg"));
        mockMoviesList.add(new Movie("What Josiah Saw Poster and Official Trailer ", "Drama", "2019 | 2h 22m", 8.2, "https://www.filmofilia.com/wp-content/uploads/2022/07/what-josiah-saw-poster-and-official-trailer-0.jpg"));
        mockMoviesList.add(new Movie("MOVIE POSTERSAlone at Night Poster and Trailer ", "Drama", "2019 | 2h 38m", 9.6, "https://www.filmofilia.com/wp-content/uploads/2023/01/alone-at-night-poster-and-trailer-0.jpg"));
        mockMoviesList.add(new Movie("Ant-Man And The Wasp: Quantumania Poster", "Drama", "2023 | 2h 55m", 9.6, "https://www.filmofilia.com/wp-content/uploads/2022/07/ant-man-and-the-wasp-quantumania-poster-0.jpg"));
        mockMoviesList.add(new Movie("Paws of Fury: The Legend of Hank Posters and Trailers", "Drama", "2022 | 1h 55m", 8.6, "https://www.filmofilia.com/wp-content/uploads/2022/07/paws-of-fury-the-legend-of-hank-posters-and-trailers-12.jpg"));
        MovieAdapter adapter = new MovieAdapter(this, mockMoviesList);
        recyclerView.setAdapter(adapter);
    }

    private void fetchMovies(){

    }
}
