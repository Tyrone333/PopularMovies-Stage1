package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tyrone3 on 17.01.17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private static final String TAG = "MoviesAdapter";

    private static final String STATIC_MOVIE_URL = "http://image.tmdb.org/t/p/w185";

    Context context;

    private ArrayList<Movie> mMovieData;

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie oneMovie);
    }

    public MoviesAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movies_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.size();
    }

    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView thumbnail;

        public MoviesAdapterViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            view.setOnClickListener(this);

        }

        public void bindView(int i) {
            Picasso.with(context).load(STATIC_MOVIE_URL + mMovieData.get(i).getThumbnail()).into(thumbnail);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie oneMovie = mMovieData.get(adapterPosition);
            mClickHandler.onClick(oneMovie);
        }
    }

    public void setMovieData(ArrayList<Movie> movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}
