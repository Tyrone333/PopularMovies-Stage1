package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    ImageView mThumbnailImageView;
    TextView mTitleTextView;
    TextView mReleaseDateTextView;
    TextView mVoteAverageTextView;
    TextView mPlotTextView;

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mThumbnailImageView = (ImageView) findViewById(R.id.iv_detail_thumbnail);
        mTitleTextView = (TextView) findViewById(R.id.tv_title_detailview);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_releasedate_detailview);
        mVoteAverageTextView = (TextView) findViewById(R.id.tv_voteaverage_detailview);
        mPlotTextView = (TextView) findViewById(R.id.tv_plot_detailview);

        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra("currentMovie")) {
                mMovie = intent.getExtras().getParcelable("currentMovie");
                String thumbnailLink = "http://image.tmdb.org/t/p/w185" + mMovie.getThumbnail();
                Picasso.with(getApplicationContext()).load(thumbnailLink).into(mThumbnailImageView);
                mTitleTextView.setText(mMovie.getTitle());
                mReleaseDateTextView.setText(mMovie.getRealeaseDate());
                mVoteAverageTextView.setText(getString(R.string.rating) + Double.toString(mMovie.getVoteAverage()));
                mPlotTextView.setText(mMovie.getPlot());

            }
        }
    }
}
