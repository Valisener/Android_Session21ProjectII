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
import projectimdb2.acadgild.com.imdbproject.models.castandcrewmodel.Crew;
import projectimdb2.acadgild.com.imdbproject.interfaces.constants.MOVIE_CONSTANTS;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.ViewHolder> implements MOVIE_CONSTANTS {
    //initialize an arraylist for crew
    ArrayList<Crew> crewArrayList;
    //constructor that sets the arraylist to the arraylist thats passed
    public CrewAdapter(ArrayList<Crew> crewArrayList) {
        //Set the arraylist to the arraylist thats passed in the constructor
        this.crewArrayList = crewArrayList;
    }

    @NonNull
    @Override
    public CrewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Create view with the custom layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_detail_crews, null);
//        make a new viewholder with the view
        CrewAdapter.ViewHolder viewHolder = new CrewAdapter.ViewHolder(view);
//        return the viewholder
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CrewAdapter.ViewHolder holder, int position) {
        //String that adds the base link and combines it with the profile path giving the entire Url used to get the image
        String image_Url = BASE_IMAGE_URL_POSTER + crewArrayList.get(position).getProfile_path();
        //Use picasso to set the image for the variable crew poster on layout, use image not available if its not available
        Picasso.get().load(image_Url).placeholder(R.drawable.not_available).into(holder.crew_Poster);
        //set the job title text from the arraylist value
        holder.crew_Job_Title.setText(crewArrayList.get(position).getJob());
        //set the crew name from the arraylist value
        holder.crew_Name.setText(crewArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        //return arraylist size
        return crewArrayList.size();
    }
    //viewholder
    static class ViewHolder extends RecyclerView.ViewHolder{
        //imageview that holds the crew poster
        private ImageView crew_Poster;
        //initialize textview variable that holds the crew name
        private TextView crew_Name;
        //initialize textview variable that holds their job title
        private TextView crew_Job_Title;
        //viewholder
        private ViewHolder(View itemView) {
            //call super first
            super(itemView);
            //reference the poster variable to the id in the layout
            crew_Poster = itemView.findViewById(R.id.details_crew_photo);
            //reference the name to the id in the layout
            crew_Name = itemView.findViewById(R.id.details_crew_name);
            //reference the title to the id in the layout
            crew_Job_Title = itemView.findViewById(R.id.details_crew_job_title);
        }
    }
}