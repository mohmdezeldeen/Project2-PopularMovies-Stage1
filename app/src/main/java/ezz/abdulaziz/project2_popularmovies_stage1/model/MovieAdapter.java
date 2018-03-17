package ezz.abdulaziz.project2_popularmovies_stage1.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ezz.abdulaziz.project2_popularmovies_stage1.R;
import ezz.abdulaziz.project2_popularmovies_stage1.utilities.NetworkUtils.PosterSize;

import static ezz.abdulaziz.project2_popularmovies_stage1.utilities.NetworkUtils.POSTER_URL;

/**
 * Created by EZZ on 2018-02-27.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> mMoviesList;
    private final ListItemClickListener mOnClickListener;


    public MovieAdapter(List<Movie> mMoviesList, ListItemClickListener mOnClickListener) {
        this.mMoviesList = mMoviesList;
        this.mOnClickListener = mOnClickListener;
    }


    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_grid_item,
                parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MovieViewHolder holder, int position) {
        Movie movie = mMoviesList.get(position);
        holder.itemView.setTag(movie.getId());
        Picasso.with(holder.mMoviePoster.getContext()).load(POSTER_URL + PosterSize.w185 +
                movie.getPosterPath()).centerCrop().fit().into(holder.mMoviePoster);
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ivMoviePoster)
        ImageView mMoviePoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick()
        public void onClick(View view) {
            mOnClickListener.onListItemClick(getAdapterPosition());
        }
    }


    public interface ListItemClickListener {
        void onListItemClick(int itemClickIndex);
    }
}
