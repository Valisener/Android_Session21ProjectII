package projectimdb2.acadgild.com.imdbproject.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import projectimdb2.acadgild.com.imdbproject.R;
import projectimdb2.acadgild.com.imdbproject.models.detailmodels.MovieDetailPojo;
import projectimdb2.acadgild.com.imdbproject.interfaces.constants.MOVIE_CONSTANTS;


public class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailAdapter.ViewHolder> implements MOVIE_CONSTANTS {
    //    variable that holds the context
    private Context context;
    //arraylist that holds the movie details information about the movies
    private ArrayList<MovieDetailPojo> moviesDetailArray;

    //    Constructor that passes the arraylist and sets it
    public MovieDetailAdapter(ArrayList<MovieDetailPojo> moviesDetailArray){ ;
        //set the arraylist to the arraylist information thats passed to the constructor
        this.moviesDetailArray = moviesDetailArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Create view with the custom layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_details_row,null);
//        make a new viewholder with the view
        ViewHolder viewHolder = new ViewHolder(view);
//        return the viewholder
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //create a decimal formatter to make the money look better
        DecimalFormat formatter = new DecimalFormat("#,###");
        //Set string variable to the base url of the poser plus the poster path
        String image_Url = BASE_IMAGE_URL_POSTER + moviesDetailArray.get(position).getPoster_path();
        //set movie title in holder from arraylist
        holder.title.setText(moviesDetailArray.get(position).getTitle());
        //set movie summary which is a shortened overview in holder from arraylist
        holder.summary.setText(moviesDetailArray.get(position).getOverview());
        //set movie release date in holder from arraylist
        holder.releaseDate.setText(moviesDetailArray.get(position).getRelease_date());
        //set movie budget in holder from arraylist
        holder.budget.setText(formatter.format(moviesDetailArray.get(position).getBudget()));
        //set movie revenue in holder from arraylist
        holder.revenue.setText(formatter.format(moviesDetailArray.get(position).getRevenue()));
        //set movie status as in has it been released yet in holder from arraylist
        holder.status.setText(moviesDetailArray.get(position).getStatus());
        //set movie average vote its rating in holder from arraylist
        holder.voteAverage.setText(String.valueOf(moviesDetailArray.get(position).getVote_average()));
        //set movie votes in total in holder from arraylist
        holder.voteTotal.setText(String.valueOf(moviesDetailArray.get(position).getVote_count()));
        //set movie over view that displays information about the movie itself as a description in holder from arraylist
        holder.overview.setText(moviesDetailArray.get(position).getOverview());
        //set the popularity ratings bar to the valuein the arraylist
        holder.popularityBar.setRating((float)moviesDetailArray.get(position).getPopularity()/10);
        //set the score ratings bar to the value in the arraylist
        holder.userScoreBar.setRating(Float.parseFloat(moviesDetailArray.get(position).getVote_average())/10.0f);
        //Use picasso to set the image for the variable poster on layout, use image loading if its busy loading as a placeholder
        Picasso.get().load(image_Url).placeholder(R.drawable.loading).into(holder.poster);
    }

    @Override
    public int getItemCount() {
//        returns the number of items in the list
        return moviesDetailArray.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView poster;
        //initialize textview variable that holds the title for the movie
        private TextView title;
        //initialize textview variable that holds the release date details for the movie
        private TextView releaseDate;
        //initialize textview variable that holds the budget for the movie
        private TextView budget;
        //initialize textview variable that holds the revenue for the movie
        private TextView revenue;
        //initialize textview variable that holds the status for the movie
        private TextView status;
        //initialize textview variable that holds the vote average the ranking for the movie
        private TextView voteAverage;
        //initialize textview variable that holds the total votes the movie received
        private TextView voteTotal;
        //initialize textview variable that holds a shortened overview to display to the user
        private TextView summary;
        //initialize textview variable holds the entire overview about the movie to display to the user
        private TextView overview;
        //initialize the popularity rating bar
        private RatingBar popularityBar;
        //initialize the score rating bar
        private RatingBar userScoreBar;
        //viewholder class
        private ViewHolder(View itemView) {
            //call super constructor
            super(itemView);
            //reference poster image to the layout id
            poster = itemView.findViewById(R.id.details_image_movie);
            //reference title of movie to the layout id
            title = itemView.findViewById(R.id.details_movie_title);
            //reference summary to the layout id
            summary = itemView.findViewById(R.id.details_movie_summary);
            //reference the release date of movie to the layout id
            releaseDate = itemView.findViewById(R.id.details_release_date_modify);
            //reference the budget of the movie to the layout id
            budget = itemView.findViewById(R.id.details_budget_modify);
            //reference the revenue of the movie to the layout id
            revenue = itemView.findViewById(R.id.details_revenue_modify);
            //reference the status of the movie if it was released or what, to the layout id
            status = itemView.findViewById(R.id.details_movie_status_modify);
            //reference the average vote for the movie its rating i suppose to the layout id
            voteAverage = itemView.findViewById(R.id.details_movie_vote_average);
            //reference the total users that voted to the layout id
            voteTotal = itemView.findViewById(R.id.details_movie_vote_total_modify);
            //reference the over view to the layout id
            overview = itemView.findViewById(R.id.details_movie_overview);
            //reference the popularity bar
            popularityBar = itemView.findViewById(R.id.detail_rating_bar);
            //reference the rating bar for user ratings
            userScoreBar = itemView.findViewById(R.id.detail_rating_bar_votes);
            }
    }
}
