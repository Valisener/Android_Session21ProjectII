package projectimdb2.acadgild.com.imdbproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import projectimdb2.acadgild.com.imdbproject.R;
import projectimdb2.acadgild.com.imdbproject.models.detailmodels.MovieDetailPojo;
import projectimdb2.acadgild.com.imdbproject.interfaces.constants.MOVIE_CONSTANTS;
import projectimdb2.acadgild.com.imdbproject.interfaces.onclicklistener.OnItemClickListener;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> implements MOVIE_CONSTANTS {
    //    variable that holds the list of android versions
    private ArrayList<MovieDetailPojo> movies;
    //initialize on click listener from my interface
    private OnItemClickListener listener;

    //    Constructor that sets the arraylist, and the onclick listener
    public MoviesAdapter(ArrayList<MovieDetailPojo> myArrayMovies, OnItemClickListener listener) {
        //set the arraylist from the arraylist thats passed
        movies = myArrayMovies;
        //set the listener from the arraylist thats passed
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create view with the custom layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list, null);
        //make a new viewholder with the view
        final ViewHolder viewHolder = new ViewHolder(view);
        //set onclick listener for the recycler view
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on the listener on click use the interface that listens
                listener.onItemClick(v, viewHolder.getLayoutPosition());
            }
        });
        //return viewholder
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //set the image url for the poster
        String image_Url = BASE_IMAGE_URL_POSTER + movies.get(position).getPoster_path();
        //set the movie title for the movie from the data in the arraylist
        holder.movieTitle.setText(movies.get(position).getTitle());
        //set the movie release date for the movie from the data in the arraylist
        holder.released.setText(movies.get(position).getRelease_date());
        //set the movie rating for the movie from the data in the arraylist
        holder.rating.setText(String.valueOf(movies.get(position).getVote_average()));
        //set the movie vote count for the movie from the data in the arraylist
        holder.userVoteCount.setText(movies.get(position).getVote_count());
        //set the popularity ratings bar to the value listed in the arraylist
        holder.popularityBar.setRating((float)movies.get(position).getPopularity()/15);
        //set the score ratings bar to the value listed in the arraylist
        holder.scoreRatingBar.setRating(Float.parseFloat(movies.get(position).getVote_average())/10.0f);
        //picasso to set the image in the holder to the poster image
        Picasso.get().load(image_Url).placeholder(R.drawable.loading).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        //returns the number of items in the arraylist
        return movies.size();
    }
    //viewholder
    static class ViewHolder extends RecyclerView.ViewHolder{
        //initialize imageview variable that holds the poster image for the movie
        private ImageView poster;
        //initialize TextView variable that holds the movie title
        private TextView movieTitle;
        //initialize TextView variable that holds the release date on the movie
        private TextView released;
        //initialize TextView variable that holds the rating for the movie
        private TextView rating;
        //initialize TextView variable that holds the total votes on the movie
        private TextView userVoteCount;
        //initialize the popularity rating bar
        private RatingBar popularityBar;
        //initialize the score rating bar
        private RatingBar scoreRatingBar;
        //viewholder
        private ViewHolder(View itemView) {
            //call super constructor
            super(itemView);
            //reference it to the id in the layout
            poster = itemView.findViewById(R.id.movie_list_poster);
            //reference the movie title to the layout id
            movieTitle = itemView.findViewById(R.id.movie_list_title);
            //reference the movie release date to the layout id
            released = itemView.findViewById(R.id.movie_list_released);
            //reference the movie rating to the layout id
            rating = itemView.findViewById(R.id.movie_list_voted);
            //reference the total votes for the movie
            userVoteCount = itemView.findViewById(R.id.movie_list_totalvotes);
            //reference the popularity bar
            popularityBar = itemView.findViewById(R.id.movie_list_popularity_bar);
            //reference the score rating bar
            scoreRatingBar = itemView.findViewById(R.id.movie_list_ratingbar);
        }
    }
}