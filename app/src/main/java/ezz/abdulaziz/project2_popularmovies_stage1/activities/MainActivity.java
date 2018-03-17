package ezz.abdulaziz.project2_popularmovies_stage1.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ezz.abdulaziz.project2_popularmovies_stage1.R;
import ezz.abdulaziz.project2_popularmovies_stage1.model.Movie;
import ezz.abdulaziz.project2_popularmovies_stage1.model.MovieAdapter;
import ezz.abdulaziz.project2_popularmovies_stage1.utilities.JSONUtils;
import ezz.abdulaziz.project2_popularmovies_stage1.utilities.NetworkUtils;

import static ezz.abdulaziz.project2_popularmovies_stage1.utilities.NetworkUtils.MOST_POPULAR_SORT;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private List<Movie> mMovieList;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private MovieAdapter mMovieAdapter;
    private static String PARAM_SORT_TYPE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        PARAM_SORT_TYPE = preferences.getString(getString(R.string.pref_sort_type), MOST_POPULAR_SORT);
        preferences.registerOnSharedPreferenceChangeListener(this);


        mMovieList = new ArrayList<>();
        loadDataFromMovieDB();

        /*check if screen ORIENTATION_LANDSCAPE add more 2 column to grid layout,
         to make grid sensible */
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        mMovieAdapter = new MovieAdapter(mMovieList, this);
        mRecyclerView.setAdapter(mMovieAdapter);
        mMovieAdapter.notifyDataSetChanged();

    }

    private void loadDataFromMovieDB() {
        // Check internet Connection
        if (!NetworkUtils.isConnectionAvailable(MainActivity.this)) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                    R.string.error_internet_connection
                    , Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(R.string.retry, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //if connection found load data from Api
                    loadDataFromMovieDB();
                }
            });
            snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
            snackbar.show();
        } else {
            new GetDataTask().execute();
        }
    }


    class GetDataTask extends AsyncTask<Void, Void, List<Movie>> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage(getString(R.string.loading_data));
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            //get url from NetworkUtils by buildUrl method
            URL url = NetworkUtils.buildUrl(PARAM_SORT_TYPE);

            //get json data from url by getJsonFromAPi method
            String jsonFromAPi = NetworkUtils.getJsonFromAPi(url);

            //parse json data by parseMovieJson
            List<Movie> moviesList = JSONUtils.parseMovieJson(jsonFromAPi);

            /*check list if not empty to create new object of movie,
             * then but it in list of objects of movie
             */
            if (!moviesList.isEmpty() || moviesList != null) {
                for (Movie m : moviesList)
                    mMovieList.add(m);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> aVoid) {
            super.onPostExecute(aVoid);
            mMovieAdapter.notifyDataSetChanged();
            dialog.dismiss();
        }
    }

    @Override
    public void onListItemClick(int itemClickIndex) {
        launchDetailActivity(itemClickIndex);
    }


    private void launchDetailActivity(int position) {
        Movie movie = mMovieList.get(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_sort_type)))
            PARAM_SORT_TYPE = sharedPreferences.getString(key, MOST_POPULAR_SORT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
