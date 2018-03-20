package ezz.abdulaziz.project2_popularmovies_stage1.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import ezz.abdulaziz.project2_popularmovies_stage1.BuildConfig;

/**
 * Created by EZZ on 2/27/2018.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private final static String BASIC_URL = "https://api.themoviedb.org/3/movie/";

    private final static String API_KEY_PARAM = "api_key";

    private final static String API_KEY = BuildConfig.API_KEY;

    public final static String MOST_POPULAR_SORT = "popular?";

    public static final String POSTER_URL = "http://image.tmdb.org/t/p/";


    public enum PosterSize {
        w92, w154, w185, w342, w500, w780, original
    }

    public static boolean isConnectionAvailable(@NonNull Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    public static URL buildUrl(String paramSortType) {
        Uri builtUri = Uri.parse(BASIC_URL + paramSortType).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "#Built URI " + url);
        return url;
    }

    public static String getJsonFromAPi(URL url) {

        OkHttpClient client = new OkHttpClient();
        client.setWriteTimeout(15, TimeUnit.MINUTES);
        client.setReadTimeout(15, TimeUnit.MINUTES);
        client.setConnectTimeout(15, TimeUnit.MINUTES);

        Request request = new Request.Builder().url(url).build();
        Response response = null;
        String json = null;
        try {
            response = client.newCall(request).execute();
            json = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "#JSON Data:" + json);
        return json;
    }
}
