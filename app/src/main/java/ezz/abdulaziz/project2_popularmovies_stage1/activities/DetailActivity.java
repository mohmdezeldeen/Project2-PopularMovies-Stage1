package ezz.abdulaziz.project2_popularmovies_stage1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ezz.abdulaziz.project2_popularmovies_stage1.R;
import ezz.abdulaziz.project2_popularmovies_stage1.model.Movie;
import ezz.abdulaziz.project2_popularmovies_stage1.utilities.NetworkUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivBackPoster)
    ImageView mBackdropPoster;
    @BindView(R.id.tvMovieTitleLabel)
    TextView mMovieTitleLabel;
    @BindView(R.id.tvMovieTitle)
    TextView mMovieTitle;
    @BindView(R.id.tvOverViewLabel)
    TextView mOverViewLabel;
    @BindView(R.id.tvOverView)
    TextView mOverView;
    @BindView(R.id.tvVoteAverageLabel)
    TextView mVoteAverageLabel;
    @BindView(R.id.tvVoteAverage)
    TextView mVoteAverage;
    @BindView(R.id.tvVoteCountLabel)
    TextView mVoteCountLabel;
    @BindView(R.id.tvVoteCount)
    TextView mVoteCount;
    @BindView(R.id.tvReleaseDateLabel)
    TextView mReleaseDateLabel;
    @BindView(R.id.tvReleaseDate)
    TextView mReleaseDate;
    @BindView(R.id.rbVoteAverage)
    RatingBar mRBVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (movie == null) {
            // Movie data unavailable
            closeOnError();
            return;
        }
        populateUI(movie);
        Picasso.with(this).load(NetworkUtils.POSTER_URL + NetworkUtils.PosterSize.w500 + movie
                .getBackdropPath()).into(mBackdropPoster);

        setTitle(movie.getTitle());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Movie movie) {
        mMovieTitle.setText(movie.getTitle());
        mVoteAverage.setText(movie.getVoteAverage().toString());
        mRBVoteAverage.setRating(Float.parseFloat(movie.getVoteAverage().toString()));
        mVoteCount.setText(movie.getVoteCount().toString());
        mReleaseDate.setText(movie.getReleaseDate());
        mOverView.setText(movie.getOverView());


        if (movie.getVoteAverage().toString().isEmpty() || movie.getVoteAverage() == null) {
            mVoteAverageLabel.setVisibility(View.GONE);
            mVoteAverage.setVisibility(View.GONE);
        } else {
            mVoteAverage.setText(movie.getVoteAverage().toString());
            mRBVoteAverage.setRating(Float.parseFloat(movie.getVoteAverage().toString()));
        }
        if (movie.getVoteCount().toString().isEmpty() || movie.getVoteCount() == null) {
            mVoteCountLabel.setVisibility(View.GONE);
            mVoteCount.setVisibility(View.GONE);
        } else {
            mVoteCount.setText(movie.getVoteCount().toString());
        }
        if (movie.getReleaseDate().toString().isEmpty() || movie.getReleaseDate() == null) {
            mReleaseDateLabel.setVisibility(View.GONE);
            mReleaseDate.setVisibility(View.GONE);
        } else {
            mReleaseDate.setText(movie.getReleaseDate().toString());
        }
        if (movie.getOverView().isEmpty() || movie.getOverView() == null) {
            mOverViewLabel.setVisibility(View.GONE);
            mOverView.setVisibility(View.GONE);
        } else {
            mOverView.setText(movie.getOverView());
        }
    }
}
