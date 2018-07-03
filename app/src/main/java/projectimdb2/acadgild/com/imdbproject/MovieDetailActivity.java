package projectimdb2.acadgild.com.imdbproject;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import projectimdb2.acadgild.com.imdbproject.adapter.CastsAdapter;
import projectimdb2.acadgild.com.imdbproject.adapter.CrewAdapter;
import projectimdb2.acadgild.com.imdbproject.adapter.MovieDetailAdapter;
import projectimdb2.acadgild.com.imdbproject.adapter.PostersAdapter;
import projectimdb2.acadgild.com.imdbproject.adapter.TrailersAdapter;
import projectimdb2.acadgild.com.imdbproject.database.DatabaseHelper;
import projectimdb2.acadgild.com.imdbproject.interfaces.constants.DATABASE_CONSTANTS;
import projectimdb2.acadgild.com.imdbproject.models.castandcrewmodel.Cast;
import projectimdb2.acadgild.com.imdbproject.models.castandcrewmodel.Crew;
import projectimdb2.acadgild.com.imdbproject.models.detailmodels.MovieDetailPojo;
import projectimdb2.acadgild.com.imdbproject.interfaces.constants.MOVIE_CONSTANTS;
import projectimdb2.acadgild.com.imdbproject.models.postermodels.Posters;
import projectimdb2.acadgild.com.imdbproject.interfaces.responsehandler.ResponseHandler;
import projectimdb2.acadgild.com.imdbproject.models.trailersmodels.Results;

public class MovieDetailActivity extends AppCompatActivity implements ResponseHandler,MOVIE_CONSTANTS,DATABASE_CONSTANTS {
    //initialize the RecyclerView for the main detailed view
    RecyclerView detailRecyclerView;
    //initialize the RecyclerView for the posters view
    RecyclerView posterRecyclerView;
    //initialize the RecyclerView for the cast view
    RecyclerView castRecyclerView;
    //initialize the RecyclerView for the crew view
    RecyclerView crewRecyclerView;
    //initialize the RecyclerView for the trailer view
    RecyclerView trailersRecyclerView;
    //initialize movie detail adapter
    MovieDetailAdapter movieDetailAdapter;
    //initalize poster adapter
    PostersAdapter postersAdapter;
    //initialize casts adapter
    CastsAdapter castsAdapter;
    //initialize crew adapter
    CrewAdapter crewAdapter;
    //initialize trailers adapter
    TrailersAdapter trailersAdapter;
    //initialize the moviedetailPojo arraylist
    ArrayList<MovieDetailPojo> movieArrayList;
    //initialize the posters array list
    ArrayList<Posters> postersArrayList;
    //initialize the cast arraylist
    ArrayList<Cast> castArrayList;
    //initialize the crew arraylist
    ArrayList<Crew> crewArrayList;
    //initialize the trailers arraylist
    ArrayList<Results> trailerArrayList;
    //initialize the database helper that handles the database
    DatabaseHelper databaseHelper;
    //initialize the movie's id the one that was clicked
    int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set layout for moviedetailscreen class
        setContentView(R.layout.movie_detail_screen);
        //get the movie id from the intent
        movieId = getIntent().getIntExtra("movieId",0);
        //Set the string to an url that gets the detailed movie link to get the information for the movie
        String detailUrl = MOVIE_DETAIL_URL_BASE + movieId + MOVIE_DETAIL_API_KEY;
        //Set the string to an url that will get the posters information about the movie
        String posterUrl = DETAIL_MOVIE_POSTER_IMAGE_BASE + movieId + DETAIL_MOVIE_POSTER_IMAGE_APIKEY;
        //Set the string to an url that will get the cast and crew information about the movie
        String castAndCrewUrl = MOVIE_DETAIL_CAST_AND_CREW_BASE + movieId + MOVIE_DETAIL_CAST_AND_CREW_END;
        //Set the string to an url that will get the trailer information about the movie
        String trailerUrl = MOVIE_DETAIL_TRAILERS_BASE + movieId + MOVIE_DETAIL_TRAILERS_END;
        //reference the detail recyclerview to the layout id
        detailRecyclerView = findViewById(R.id.movies_details_recyclerview);
        //refence the poster recyclerview to the layout id
        posterRecyclerView = findViewById(R.id.movies_details_poster_recyclerview);
        //reference the trailer recyclerview to the layout id
        trailersRecyclerView = findViewById(R.id.movies_details_trailer_recyclerview);
        //reference the cast recyclerview to the layout id
        castRecyclerView = findViewById(R.id.movies_details_casts_recyclerview);
        //reference the crew recyclerview to the layout id
        crewRecyclerView = findViewById(R.id.movies_details_crew_recyclerview);
        //Create new fetchdatafrom website object that has the url for basic information about the movie
        FetchDataFromWebsite fetchDetailDataFromWebsite = new FetchDataFromWebsite(this, detailUrl,this, "main");
        //perform the task for fetchdatafromwebsite object that gets the basic movie information
        fetchDetailDataFromWebsite.execute();
        //Create new fetchdatafrom website object that has the url for poster information about the movie
        FetchDataFromWebsite fetchPosterDataFromWebsite = new FetchDataFromWebsite(this, posterUrl ,this, "poster");
        //perform the task for fetchdatafromwebsite object that gets the poster information about the movie
        fetchPosterDataFromWebsite.execute();
        //Create new fetchdatafrom website object that has the url for cast and crew information about the movie
        FetchDataFromWebsite fetchCastAndCrewDataFromWebsite = new FetchDataFromWebsite(this,castAndCrewUrl,this,"castandcrew");
        //perform the task for fetchdatafromwebsite object that gets the cast and crew information about the movie
        fetchCastAndCrewDataFromWebsite.execute();
        //Create new fetchdatafrom website object that has the url for trailers information about the movie
        FetchDataFromWebsite fetchTrailersDataFromWebsite = new FetchDataFromWebsite(this,trailerUrl ,this,"trailers");
        //perform the task for fetchdatafromwebsite object that gets the trailers information about the movie
        fetchTrailersDataFromWebsite.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //make a call to the super
        super.onCreateOptionsMenu(menu);
        // create menu inflater and get inflater
        MenuInflater menuInflater = getMenuInflater();
        //inflate the menu to the menu list menu detailed list
        menuInflater.inflate(R.menu.menu_detailed_list,menu);
        //return true
        return true;
    }

    //menu options when selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //switch statement to check which menu item was clicked
        switch (item.getItemId()){
            //check if id is detail favorites
            case R.id.menu_detail_favorites:
                //call method that adds the movie on display to favorites
                addMovieAsFavoriteOrWatchlist(COLUMN_IS_FAVORITE);
                //break out of the switch statement
                break;
                //check if the id detail watchlist was selected
            case R.id.menu_detail_watchlist:
                //call method that adds the movie on display to watchlist
                addMovieAsFavoriteOrWatchlist(COLUMN_IS_WATCHLIST);
                //break out of the switch statement
                break;
                //check if post rating was selected
            case R.id.menu_detailed_post_rating:
                postUserRating();
                //break out of the switch statement
                break;
        }
        //return true
        return true;
    }
    //method that sends the post with the user rating
    private void postUserRating() {
        //Set the website url to be used in a string of url
        String url = POST_BASE + movieId + POST_GUEST_SESSION + POST_API_KEY;
        //make a new class that performs a post and gives the value 8.5
        PostDataToWebsite postDataToWebsite = new PostDataToWebsite(MovieDetailActivity.this,url);
        //execute the post
        postDataToWebsite.execute();
    }
    //method that adds the movie the user is looking at as a favorite or to the watchlist
    private void addMovieAsFavoriteOrWatchlist(String selection) {
        //create new databasehelper class
        databaseHelper=new DatabaseHelper(MovieDetailActivity.this, DATABASE_NAME,null,DATABASE_VERSION);
        // create new moviedetailpojo
        MovieDetailPojo movieDetailPojo;
        //set the moviedetail to the one in the arraylist
        movieDetailPojo = movieArrayList.get(0);
        //check what was selected column is favorite
        if (selection.equals(COLUMN_IS_FAVORITE)){
            //if true then set the favorite to yes
            movieDetailPojo.setFavorite("Y");
        }
        //else check if watchlist was selected
        else if (selection.equals(COLUMN_IS_WATCHLIST)){
            //if true then set the watchlist to yes
            movieDetailPojo.setWatchlist("Y");
        }
        //create contentvalues to hold the values needed to be passwed to the database helper and insert the data into the database
        ContentValues contentValues = new ContentValues();
        //set the id in the contentvalues
        contentValues.put(COLUMN_ID,movieDetailPojo.getId());
        //set the TITLE in the contentvalues
        contentValues.put(COLUMN_TITLE,movieDetailPojo.getTitle());
        //set the RELEASE_DATE in the contentvalues
        contentValues.put(COLUMN_RELEASE_DATE,movieDetailPojo.getRelease_date());
        //set the VOTE_COUNT in the contentvalues
        contentValues.put(COLUMN_VOTE_COUNT,movieDetailPojo.getVote_count());
        //set the VOTE_AVERAGE in the contentvalues
        contentValues.put(COLUMN_VOTE_AVERAGE,movieDetailPojo.getVote_average());
        //set the MOVIE_RAW_DETAILS in the contentvalues which is the overview
        contentValues.put(COLUMN_MOVIE_RAW_DETAILS,movieDetailPojo.getOverview());
        //set the POSTER_PATH in the contentvalues
        contentValues.put(COLUMN_POSTER_PATH,movieDetailPojo.getPoster_path());
        //set the POPULARITY in the contentvalues
        contentValues.put(COLUMN_POPULARITY,movieDetailPojo.getPopularity());
        //set the FAVORITE in the contentvalues
        contentValues.put(COLUMN_IS_FAVORITE,movieDetailPojo.getFavorite());
        //set the WATCHLIST in the contentvalues
        contentValues.put(COLUMN_IS_WATCHLIST,movieDetailPojo.getWatchlist());
        //call databasehelper with contentvalues to insert the data into the database
        databaseHelper.insertRecordDB(contentValues);
    }
    //method that updates the activity screen called from responsehandler
    @Override
    public void updateActivity(String responseData, String type) {
        //test if type is poster
        if (type.equals("poster")){
            //call method that populates the poster arraylist with posters and pass along the responsedata
            populatePostersRecyclerview(responseData);
        }
        //test if type is main
        if (type.equals("main")) {
            //call method that populates the main arraylist with movie information and pass along the responsedata
            populateDetailRecyclerview(responseData);
        }
        //test if type is castandcrew
        if (type.equals("castandcrew")) {
            //call method that populates the castandcrew arraylist with cast and crew information and pass along the responsedata
            populateCastAndCrewRecyclerview(responseData);
        }
        //test if type is trailers
        if (type.equals("trailers")){
            //call method that populates the trailers arraylist with trailer information and pass along the responsedata
            populateTrailersRecyclerview(responseData);
        }
    }

    //This method is called from updateactivity that handles the poster recyclerview
    private void populatePostersRecyclerview(String responseData) {
        //Create new posters arraylist
        postersArrayList = new ArrayList<>();
        //Create new JSONObject that will handle posters
        JSONObject jsonObjPoasters;
        //Create new JSONArray for the poster results
        JSONArray posterResults;
        //try in case it fails
        try {
            //create new JSONobject for posters with the response data
            jsonObjPoasters = new JSONObject(responseData);
            //get the posters information from the JSONObject's jsonarray
            posterResults = jsonObjPoasters.getJSONArray("posters");
            //loop through all the results to add
            for (int i = 0; i < posterResults.length(); i++) {
                //create new jsonobject from whichever number from the posterresults
                JSONObject theMovie = posterResults.getJSONObject(i);
                //store the poster path in a new string variable that it gets from themovie JSONobject
                String moviePosterPath = theMovie.getString("file_path");
                //initialize new posters model
                Posters postersModel = new Posters();
                //set the poster path for the posters
                postersModel.setFile_path(moviePosterPath);
                //add postermodel to the arraylist that handles posters
                postersArrayList.add(postersModel);

            }
            //error handler
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //create new adapter for posters with the new information from the arraylist that it got earlier
        postersAdapter = new PostersAdapter(postersArrayList);

        LinearLayoutManager linearLayoutManagerPoster = new LinearLayoutManager(MovieDetailActivity.this,LinearLayoutManager.HORIZONTAL,false);
        //set the layout manager on the recyclerview
        posterRecyclerView.setLayoutManager(linearLayoutManagerPoster);
        //set the adapter on the recycler view
        posterRecyclerView.setAdapter(postersAdapter);


    }
    //method is called from update activity for main information about the movie recyclerview and populates it with information
    private void populateDetailRecyclerview(String responseData) {
        //create new arraylist get rid of the old data inside if there is old data inside
        movieArrayList = new ArrayList<>();
        //create Gson instance
        Gson gson = new Gson();
        //Grab the value and store it as a moviesPojo
        MovieDetailPojo movieDetailPojo = gson.fromJson(responseData, MovieDetailPojo.class);
        //Store all the values from the Json file into an arraylist
        movieArrayList.add(movieDetailPojo);
        //create a new adapter with a new arraylist that was declared earlier
        movieDetailAdapter = new MovieDetailAdapter(movieArrayList);
        //create new layoutmanager for the basic movie details
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MovieDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        //set the layout manager on the recyclerview
        detailRecyclerView.setLayoutManager(linearLayoutManager);
        //set the adapter on the recycler view
        detailRecyclerView.setAdapter(movieDetailAdapter);
    }
    //this method is called from update activity for cast and crew recyclerview and populates it with information
    private void populateCastAndCrewRecyclerview(String responseData) {
        //create new arraylist for cast
        castArrayList = new ArrayList<>();
        //create new arraylist for crew
        crewArrayList = new ArrayList<>();
        //initialize JSONObject that will have all the information for cast and crew
        JSONObject jsonObjCastAndCrew;
        //initialize a jsonarray for both cast and crew
        JSONArray castResults,crewResults;
        try {
            //create a new jsonobject that has all the cast and crew
            jsonObjCastAndCrew = new JSONObject(responseData);
            //store the results for cast in this jsonarray
            castResults = jsonObjCastAndCrew.getJSONArray("cast");
            //store the results for crew in this jsonarray
            crewResults = jsonObjCastAndCrew.getJSONArray("crew");
            //loop through all the data inside the cast results jsonarray
            for (int i = 0; i < castResults.length(); i++) {
                //make a new JSONObject with data from the jsonarray for cast
                JSONObject castObj = castResults.getJSONObject(i);
                //get the pster path from the castobject and store it in a string variable
                String castPosterPath = castObj.getString("profile_path");
                //get the cast name from the cast object and store it in a string variable
                String castName = castObj.getString("name");
                //get the character name from the cast object and store it in a string variable
                String castCharacter = castObj.getString("character");
                //create new cast object
                Cast cast = new Cast();
                //set the profile path for the cast object
                cast.setProfile_path(castPosterPath);
                //set the character name from response data to cast object
                cast.setCharacter(castCharacter);
                //set name from response data to cast object
                cast.setName(castName);
                //add the cast object to the cast arraylist
                castArrayList.add(cast);
            }
            //loop through the crew results data thats in the jsonarray
            for (int i = 0; i < crewResults.length(); i++) {
                //make a new JSONObject with data from teh jsonarray for crew
                JSONObject crewObj = crewResults.getJSONObject(i);
                //get poster path from response data and put it in crewPosterPath variable
                String crewPosterPath = crewObj.getString("profile_path");
                //get crew job from response data and put it in crewJob variable
                String crewJob = crewObj.getString("job");
                //get crew name from response data and put it in crewName variable
                String crewName = crewObj.getString("name");
                //create new crew object
                Crew crew = new Crew();
                //set name from response data to crew object
                crew.setName(crewName);
                //set job from response data to crew object
                crew.setJob(crewJob);
                //set profile path from response data to crew object
                crew.setProfile_path(crewPosterPath);
                //add the crew object to the crew arraylist
                crewArrayList.add(crew);
            }
            //exception handler
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //create new adapter for cast
        castsAdapter = new CastsAdapter(castArrayList);
        //create new adapter for crew
        crewAdapter = new CrewAdapter(crewArrayList);
        //create new layoutmanager for cast
        LinearLayoutManager linearLayoutManagerCast = new LinearLayoutManager(MovieDetailActivity.this,LinearLayoutManager.HORIZONTAL,false);
        //create new layoutmanager for crew
        LinearLayoutManager linearLayoutManagerCrew = new LinearLayoutManager(MovieDetailActivity.this,LinearLayoutManager.HORIZONTAL,false);
        //set the layout manager on the recyclerview for cast
        castRecyclerView.setLayoutManager(linearLayoutManagerCast);
        //set the layout manager on the recyclerview for crew
        crewRecyclerView.setLayoutManager(linearLayoutManagerCrew);
        //set the adapter on the recycler view for cast
        castRecyclerView.setAdapter(castsAdapter);
        //set the adapter on the recycler view for crew
        crewRecyclerView.setAdapter(crewAdapter);
    }
    //method that populates the recyclerview for trailers
    private void populateTrailersRecyclerview(String responseData) {
        //create new arraylist for trailer
        trailerArrayList = new ArrayList<>();
        //initialize new JSONObject for trailers
        JSONObject jsonObjTrailers;
        //initialize new JSONArray that will contain trailer results
        JSONArray trailersResults;
        //try in case it fails
        try {
            //set JSONObject to a new one with the responseData
            jsonObjTrailers = new JSONObject(responseData);
            //use trailerresults jsonarray to se the array from the response to the data contained there in
            trailersResults = jsonObjTrailers.getJSONArray("results");
            //make a for loop to loop through all the information in the trailer array results
            for (int i = 0; i < trailersResults.length(); i++) {
                //get JSONObject from each object inside the trailer results
                JSONObject theTrailers = trailersResults.getJSONObject(i);
                //get the trailer type from the response data
                String trailerType = theTrailers.getString("type");
                //get the trailer url from the response data
                String trailerUrl = theTrailers.getString("key");
                //create new results pojo thats technically the trailer information thats needed
                Results trailerPojo = new Results();
                //set key in trailerpojo
                trailerPojo.setKey(trailerUrl);
                //set type in trailerpojo
                trailerPojo.setType(trailerType);
                //add trailer to trailer arraylist
                trailerArrayList.add(trailerPojo);
            }
            //exception handler
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //create new adapter with the trailerarraylist
        trailersAdapter = new TrailersAdapter(MovieDetailActivity.this,trailerArrayList);
        //create new linearlayoutmanager
        LinearLayoutManager linearLayoutManagerTrailers = new LinearLayoutManager(MovieDetailActivity.this,LinearLayoutManager.HORIZONTAL,false);
        //set the layout manager on the recyclerview
        trailersRecyclerView.setLayoutManager(linearLayoutManagerTrailers);
        //set the adapter on the recycler view
        trailersRecyclerView.setAdapter(trailersAdapter);
    }
}
