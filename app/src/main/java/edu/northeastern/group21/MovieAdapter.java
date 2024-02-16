package edu.northeastern.group21;

import edu.northeastern.group21.Movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
        holder.tvDateAndTime.setText(movie.getReleaseDate());
        holder.tvRate.setText(movie.getRate());

        // Load the image using Glide or Picasso
        Glide.with(context)
                .load(movie.getPosterUrl())
                .error(R.drawable.frame17) // Display an error image if the load fails
                .into(holder.ivPosterImage);


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPosterImage;
        TextView tvMovieName, tvGenres, tvDateAndTime, tvRate;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPosterImage = itemView.findViewById(R.id.ivPosterImage);
            tvMovieName = itemView.findViewById(R.id.tv_MovieName);
            tvGenres = itemView.findViewById(R.id.tv_Genres);
            tvDateAndTime = itemView.findViewById(R.id.tv_DateAndTime);
            tvRate = itemView.findViewById(R.id.tv_Rate);
        }
    }
}

