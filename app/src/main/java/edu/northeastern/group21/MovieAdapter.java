package edu.northeastern.group21;

import edu.northeastern.group21.Movie;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;



public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
            Movie movie = movies.get(position);
            holder.tvMovieName.setText(movie.getName());
            holder.tvGenres.setText(movie.getGenres());
            holder.tvReleaseYear.setText(movie.getReleaseDate());
            holder.tvRate.setText(String.valueOf(movie.getRate()));

            // Use Glide to load the image from URL into the ImageView
        Glide.with(context)
                .load(movie.getPosterUrl())
                .into(holder.ivPosterImage);
        }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPosterImage;
        TextView tvMovieName, tvGenres, tvReleaseYear, tvRate;
        ConstraintLayout constraintLayout;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPosterImage = itemView.findViewById(R.id.ivPosterImage);
            tvMovieName = itemView.findViewById(R.id.tv_MovieName);
            tvGenres = itemView.findViewById(R.id.tv_Genres);
            tvReleaseYear = itemView.findViewById(R.id.tv_ReleaseYear);
            tvRate = itemView.findViewById(R.id.tv_Rate);
            constraintLayout = itemView.findViewById(R.id.movieLists_recycler_view);
        }
    }
}

