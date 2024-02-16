package edu.northeastern.group21;
import edu.northeastern.group21.Movie;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public class MovieList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        // Initialize RecyclerView here
        RecyclerView recyclerView = findViewById(R.id.movieLists_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Use this for a vertical list

        // Mock data for testing
        List<Movie> mockMoviesList = new ArrayList<>();
        mockMoviesList.add(new Movie("Minions & More 1", "TV", "2022 | 48 min", "6.5", "https://www.filmofilia.com/wp-content/uploads/2013/06/DESPICABLE-ME-2-Phil-The-Minion-Poster.jpg"));
        mockMoviesList.add(new Movie("Avatar ", "TV", "2022 | 48 min", "6.5", "https://m.media-amazon.com/images/M/MV5BYjhiNjBlODctY2ZiOC00YjVlLWFlNzAtNTVhNzM1YjI1NzMxXkEyXkFqcGdeQXVyMjQxNTE1MDA@._V1_.jpg"));
        mockMoviesList.add(new Movie("Dr. Strangelove or: How I Learned to Stop Worrying and Love the Bomb  ", "Drama", "1994 | 2h 22m", "8.8", "https://www.filmofilia.com/wp-content/uploads/2013/06/DESPICABLE-ME-2-Phil-The-Minion-Poster.jpg"));
        mockMoviesList.add(new Movie("The Godfather ", "Drama", "1972 | 2h 55m", "9.2", "https://static.wikia.nocookie.net/international-entertainment-project/images/9/9b/The_Godfather_-_poster_%28English%29.jpg/revision/latest?cb=20231004233807.jpg"));
        mockMoviesList.add(new Movie("Minions & More 1", "TV", "2022 | 48 min", "6.5", "https://www.filmofilia.com/wp-content/uploads/2013/06/DESPICABLE-ME-2-Phil-The-Minion-Poster.jpg"));
        mockMoviesList.add(new Movie("Avatar ", "TV", "2022 | 48 min", "6.5", "https://m.media-amazon.com/images/M/MV5BYjhiNjBlODctY2ZiOC00YjVlLWFlNzAtNTVhNzM1YjI1NzMxXkEyXkFqcGdeQXVyMjQxNTE1MDA@._V1_.jpg"));
        mockMoviesList.add(new Movie("Forrest Gump ", "Drama", "1994 | 2h 22m", "8.8", "https://www.filmofilia.com/wp-content/uploads/2013/06/DESPICABLE-ME-2-Phil-The-Minion-Poster.jpg"));
        mockMoviesList.add(new Movie("The Godfather ", "Drama", "1972 | 2h 55m", "Kazakh TV talking head Borat is dispatched to the United States to report on the greatest country in the world. With a documentary crew in tow, Borat becomes more interested in locating and marrying Pamela Anderson.", "https://static.wikia.nocookie.net/international-entertainment-project/images/9/9b/The_Godfather_-_poster_%28English%29.jpg/revision/latest?cb=20231004233807.jpg"));


        MovieAdapter adapter = new MovieAdapter(this, mockMoviesList);
        recyclerView.setAdapter(adapter);
    }
}
