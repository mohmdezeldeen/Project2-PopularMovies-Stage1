package ezz.abdulaziz.project2_popularmovies_stage1.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ezz.abdulaziz.project2_popularmovies_stage1.model.Movie;

/**
 * Created by EZZ on 2/27/2018.
 */

public class JSONUtils {

    public static List<Movie> parseMovieJson(String json) {
        List<Movie> movieList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resultsArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < resultsArray.length(); i++) {
                Movie movie = new Movie();
                JSONObject movieObject = resultsArray.optJSONObject(i);
                movie.setVoteCount(movieObject.optInt("vote_count"));
                movie.setId(movieObject.optLong("id"));
                movie.setTitle(movieObject.optString("title"));
                movie.setOverView(movieObject.optString("overview"));
                movie.setVoteAverage(movieObject.optDouble("vote_average"));
                movie.setPosterPath(movieObject.optString("poster_path"));
                movie.setBackdropPath(movieObject.optString("backdrop_path"));
                movie.setPopularity(movieObject.optDouble("popularity"));
                movie.setReleaseDate(movieObject.optString("release_date"));
                movieList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }
}
