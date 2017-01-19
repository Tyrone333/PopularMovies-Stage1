package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tyrone3 on 18.01.17.
 */

public class Movie implements Parcelable {
    private String mTitle;
    private String mReleaseDate;
    private String mThumbnail;
    private double mVoteAverage;
    private String mPlot;

    public Movie(String title, String realeaseDate, String thumbnail, double voteAverage, String plot) {
        mTitle = title;
        mReleaseDate = realeaseDate;
        mThumbnail = thumbnail;
        mVoteAverage = voteAverage;
        mPlot = plot;
    }

    protected Movie(Parcel in) {
        mTitle = in.readString();
        mReleaseDate = in.readString();
        mThumbnail = in.readString();
        mVoteAverage = in.readDouble();
        mPlot = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public String getRealeaseDate() {
        return mReleaseDate;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public String getPlot() {
        return mPlot;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mThumbnail);
        parcel.writeDouble(mVoteAverage);
        parcel.writeString(mPlot);
    }
}



