package projectimdb2.acadgild.com.imdbproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import projectimdb2.acadgild.com.imdbproject.R;
import projectimdb2.acadgild.com.imdbproject.interfaces.constants.MOVIE_CONSTANTS;
import projectimdb2.acadgild.com.imdbproject.models.trailersmodels.Results;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> implements MOVIE_CONSTANTS {
    //arraylist variable that holds the trailers information
    private ArrayList<Results> trailersArrayList;
    //variable that holds the context needed for onclick listener
    private Context context;
    //constructor that sets the context and arraylist
    public TrailersAdapter (Context context, ArrayList<Results> trailersArrayList){
        //set the context
        this.context = context;
        //set the arraylist
        this.trailersArrayList = trailersArrayList;
    }

    @NonNull
    @Override
    public TrailersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create view with the custom layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_detail_trailers, null);
        //make a new viewholder with the view
        TrailersAdapter.ViewHolder viewHolder = new TrailersAdapter.ViewHolder(view);
        //return the viewholder
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersAdapter.ViewHolder holder, int position) {
        //set the trailer text from the arraylist information
        holder.trailerText.setText(trailersArrayList.get(position).getType());
        //String that holds the entire website url for the trailer to be passed to the onclick listener
        final String trailerWebsiteUrl = YOUTUBE_BASE_URL + trailersArrayList.get(position).getKey();
        //Set a onclick listner for when they click on the trailer it launches to the website link in question to watch the trailers
        holder.trailerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailerWebsiteUrl)));
            }
        });
    }
    @Override
    public int getItemCount() {
        //return the arraylist size
        return trailersArrayList.size();
    }
    //viewholder
    class ViewHolder extends RecyclerView.ViewHolder {
        //initialize the textview variable
        private TextView trailerText;
        //viewholder
        private ViewHolder(View itemView) {
            //call super constructor
            super(itemView);
            //reference the textview trailer text to the layout with id stated
            trailerText = itemView.findViewById(R.id.details_trailers_textview);
        }
    }
}
