package projectimdb2.acadgild.com.imdbproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import projectimdb2.acadgild.com.imdbproject.R;
import projectimdb2.acadgild.com.imdbproject.models.castandcrewmodel.Cast;
import projectimdb2.acadgild.com.imdbproject.interfaces.constants.MOVIE_CONSTANTS;

public class CastsAdapter extends RecyclerView.Adapter<CastsAdapter.ViewHolder> implements MOVIE_CONSTANTS {
    //initialize an arraylist for cast
    private ArrayList<Cast> castArrayList;
    //constructor that sets the arraylist
    public CastsAdapter(ArrayList<Cast> castArrayList) {
        //Set the arraylist to the arraylist thats passed in the constructor
        this.castArrayList = castArrayList;
    }
    @NonNull
    @Override
    public CastsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Create view with the custom layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_detail_casts, null);
//        make a new viewholder with the view
        CastsAdapter.ViewHolder viewHolder = new CastsAdapter.ViewHolder(view);
//        return the viewholder
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CastsAdapter.ViewHolder holder, int position) {
        //String that adds the base link and combines it with the profile path giving the entire Url used to get the image
        String image_Url = BASE_IMAGE_URL_POSTER + castArrayList.get(position).getProfile_path();
        //Use picasso to set the image for the variable crew poster on layout, use image not available if its not available
        Picasso.get().load(image_Url).placeholder(R.drawable.not_available).into(holder.actor_Picture);
        //sets the actor's name from the cast arraylist information
        holder.actor_Name.setText(castArrayList.get(position).getName());
        //set the actor's character from the cast arraylist information
        holder.actor_Character.setText(castArrayList.get(position).getCharacter());
    }

    @Override
    public int getItemCount() {
        //returns the size of the arraylist as in the number of items in the arraylist
        return castArrayList.size();
    }
    //viewholder
    static class ViewHolder extends RecyclerView.ViewHolder{
        //initialize imageview variable that holds the picture for the cast in question
        ImageView actor_Picture;
        //initialize textview variable that holds the actor's name
        TextView actor_Name;
        //initialize textview variable that holds the actor's character name
        TextView actor_Character;
        //viewholder
        private  ViewHolder(View itemView) {
            //call the super constructor
            super(itemView);
            //reference the imageview picture to the layout id
            actor_Picture = itemView.findViewById(R.id.details_casts_photo);
            //reference the textview actor name to the layout id
            actor_Name = itemView.findViewById(R.id.details_casts_name);
            //reference the textview actor character to the layout id
            actor_Character = itemView.findViewById(R.id.details_casts_character_name);
        }
    }
}