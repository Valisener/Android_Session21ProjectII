package projectimdb2.acadgild.com.imdbproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import projectimdb2.acadgild.com.imdbproject.R;
import projectimdb2.acadgild.com.imdbproject.interfaces.constants.MOVIE_CONSTANTS;
import projectimdb2.acadgild.com.imdbproject.models.postermodels.Posters;

public class PostersAdapter extends RecyclerView.Adapter<PostersAdapter.ViewHolder> implements MOVIE_CONSTANTS {
    //arraylist variable that holds all the posters in the arraylist
    private ArrayList<Posters> postersArrayList;
    //constructor that sets the arraylist to the arraylist thats passed
    public PostersAdapter(ArrayList<Posters> posters) {
        //set the arraylist thats passed to the arraylist thats here
        postersArrayList = posters;
    }

    @NonNull
    @Override
    public PostersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Create view with the custom layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_detail_posters, null);
//        make a new viewholder with the view
        PostersAdapter.ViewHolder viewHolder = new PostersAdapter.ViewHolder(view);
//        return the viewholder
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull PostersAdapter.ViewHolder holder, int position) {
        //Variable that stores the entire image url to use to get the image file
        String image_Url = BASE_IMAGE_URL_POSTER + postersArrayList.get(position).getFile_path();
        //use picasso to load the image into the imageview holder that holds the poster
        Picasso.get().load(image_Url).placeholder(R.drawable.loading).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        //return the arraylist size
        return postersArrayList.size();
    }
    //viewholder
    static class ViewHolder extends RecyclerView.ViewHolder{
        //initialize imageview variable
        private ImageView poster;
        //viewholder
        private ViewHolder(View itemView) {
            //call super constructor
            super(itemView);
            //reference the poster to the layout id that points to movie poster
            poster = itemView.findViewById(R.id.detail_movie_poster);
        }
    }
}