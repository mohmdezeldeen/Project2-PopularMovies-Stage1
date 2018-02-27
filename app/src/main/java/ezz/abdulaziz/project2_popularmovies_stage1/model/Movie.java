package ezz.abdulaziz.project2_popularmovies_stage1.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by EZZ on 2/27/2018.
 */

public class Movie implements Parcelable
{
    private String title;
    private String moviePoster;
    private String plotSynopsis;
    private String userRating;
    private Date releaseDate;

    public Movie()
    {

    }

    public Movie(String title, String moviePoster, String plotSynopsis, String userRating, Date releaseDate)
    {
        this.title = title;
        this.moviePoster = moviePoster;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getMoviePoster()
    {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster)
    {
        this.moviePoster = moviePoster;
    }

    public String getPlotSynopsis()
    {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis)
    {
        this.plotSynopsis = plotSynopsis;
    }

    public String getUserRating()
    {
        return userRating;
    }

    public void setUserRating(String userRating)
    {
        this.userRating = userRating;
    }

    public Date getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    protected Movie(Parcel in)
    {
        title = in.readString();
        moviePoster = in.readString();
        plotSynopsis = in.readString();
        userRating = in.readString();
        long tmpReleaseDate = in.readLong();
        releaseDate = tmpReleaseDate != -1 ? new Date(tmpReleaseDate) : null;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(title);
        dest.writeString(moviePoster);
        dest.writeString(plotSynopsis);
        dest.writeString(userRating);
        dest.writeLong(releaseDate != null ? releaseDate.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>()
    {
        @Override
        public Movie createFromParcel(Parcel in)
        {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size)
        {
            return new Movie[size];
        }
    };
}
