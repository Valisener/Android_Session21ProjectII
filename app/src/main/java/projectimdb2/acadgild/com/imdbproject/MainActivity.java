package projectimdb2.acadgild.com.imdbproject;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import projectimdb2.acadgild.com.imdbproject.adapter.MoviesAdapter;
import projectimdb2.acadgild.com.imdbproject.database.DatabaseHelper;
import projectimdb2.acadgild.com.imdbproject.interfaces.constants.DATABASE_CONSTANTS;
import projectimdb2.acadgild.com.imdbproject.models.detailmodels.MovieDetailPojo;
import projectimdb2.acadgild.com.imdbproject.interfaces.constants.MOVIE_CONSTANTS;
import projectimdb2.acadgild.com.imdbproject.interfaces.onclicklistener.OnItemClickListener;
import projectimdb2.acadgild.com.imdbproject.interfaces.responsehandler.ResponseHandler;

public class MainActivity extends AppCompatActivity implements MOVIE_CONSTANTS,ResponseHandler,OnItemClickListener,DATABASE_CONSTANTS {
    //initialize recyclerview reference
    RecyclerView recyclerView;
    //initialize the adapter reference
    MoviesAdapter moviesAdapter;
    //initialize the array that holds the movies
    ArrayList<MovieDetailPojo> myArrayMovies;
    //initialize the website Url and set it to upcoming so thats always used when first created
    String websiteUrl = URL_MOVIES_UPCOMING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the layout to activity main
        setContentView(R.layout.activity_main);
        //reference the recyclerview to the layout
        recyclerView = findViewById(R.id.movies_list_recyclerview);
        //run a check to see if they are connected to the internet
        if (isConnectedToInternet()) {
            //Initialize a new instance of the class FetchDatafromwebsite that fetches the data from the website
            FetchDataFromWebsite fetchDataFromWebsite = new FetchDataFromWebsite(MainActivity.this, websiteUrl, this, null);
            //execute the aynck task that fetches the data from the website url
            fetchDataFromWebsite.execute();
        }
        else {
            //if they are not connected to internet display error message
            Toast.makeText(MainActivity.this,"Check internet Connection",Toast.LENGTH_LONG).show();;
        }
    }
    //method that tests to see if they are connected to internet
    private boolean isConnectedToInternet() {
        //boolean that handles the value to find out if they are true connected to internet or false not connected
        boolean isConnected = false;
        //create the connectivity manager
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        //create networkinfo that gets the network information in other words it tells wether or not its connected to internet
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //test to see if the network info is not null, that means it has internet connection
        if (networkInfo != null) {
            //set the boolean to true since they have internet
            isConnected = true;
        }
        //return is connected variable
        return isConnected;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //make a call to the super
        super.onCreateOptionsMenu(menu);
        // create menu inflater and get inflater
        MenuInflater menuInflater = getMenuInflater();
        //inflate the menu to the menu list menu
        menuInflater.inflate(R.menu.menu_list,menu);
        //return true
        return true;
    }

    //menu options when selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //switch statement to check which menu item was clicked
        switch (item.getItemId()){
            //check id if its mostpopular id
            case R.id.submenu_mostpopular:
                //if most popular was clicked then refresh the list with the url for most popular
                setListAndRefresh(URL_MOVIES_POPULAR);
                //break out of the switch statement
                break;
            //check id if its upcoming id
            case R.id.submenu_upcoming:
                //if upcoming was clicked then refresh the list with the url for upcominb movies
                setListAndRefresh(URL_MOVIES_UPCOMING);
                //break out of the switch statement
                break;
            //check id if its latestmovies id
            case R.id.submenu_latestmovies:
                //if latest movies was clicked then refresh the list with the url for latest movies
                setListAndRefresh(URL_MOVIES_LATEST);
                //break out of the switch statement
                break;
            //check id if its nowplaying id
            case R.id.submenu_nowplaying:
                //if now playing was clicked then refresh the list with the url for now playing
                setListAndRefresh(URL_MOVIES_NOW_PLAYING);
                //break out of the switch statement
                break;
            //check id if its toprated id
            case R.id.submenu_toprated:
                //if top rated was clicked then refresh the list with the url for top rated
                setListAndRefresh(URL_MOVIES_TOP_RATED);
                //break out of the switch statement
                break;
            //check id if its favorites id
            case R.id.menu_favorites:
                populateListFromDatabaseSelection(COLUMN_IS_FAVORITE);
                //break out of the switch statement
                break;
            //check id if its watchlist id
            case R.id.menu_watchlist:
                populateListFromDatabaseSelection(COLUMN_IS_WATCHLIST);
                //break out of the switch statement
                break;
            //check id if its refresh id
            case R.id.menu_refresh:
                setListAndRefresh(null);
                //break out of the switch statement
                break;
        }
        //return true
        return true;
    }
    //method that populates the list from the database depending on the selection of favorites or watchlist
    private void populateListFromDatabaseSelection(String columnSelection) {
        //create new databasehelper object that handles the database interactions
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this,DATABASE_NAME,null,DATABASE_VERSION);
        //set the arraylist to the database helper arraylist based on the selection
        myArrayMovies = databaseHelper.getMoviesSelection(columnSelection);
        //create new moviesadapter with context, arraylist, onclick listener
        moviesAdapter = new MoviesAdapter(myArrayMovies, this);
        //create new linear layout manager set it to vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        //Set the layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //set adapter to the recycler view
        recyclerView.setAdapter(moviesAdapter);
    }

    //method that sets the new list with a new url or refreshes the list if clicked depending
    public void setListAndRefresh(String url) {
        //check if there is a new url to use if so set it otherwise use the old website url on whats set currently
        if (url != null) {
            //set the new website url
            websiteUrl = url;
        }
        //create new instance of fetchdatafromwebsite
        FetchDataFromWebsite fetchDataFromWebsite = new FetchDataFromWebsite(MainActivity.this,websiteUrl,this);
        //execute the asynktask
        fetchDataFromWebsite.execute();
    }

    //method that updates the activity called from asyncktask
    @Override
    public void updateActivity(String responseData,String type) {
        //Make a new arraylist for movies
        myArrayMovies = new ArrayList<>();
        //check if its the link to latest movies if so do it a specfic way
        if (websiteUrl.equals(URL_MOVIES_LATEST)){
            //create new gson
            Gson gson = new Gson();
            //Grab the value and store it as a moviesPojo
            MovieDetailPojo movieDetailPojo = gson.fromJson(responseData,MovieDetailPojo.class);
            //Store all the values from the Json file into an arraylist
            myArrayMovies.add(movieDetailPojo);
        }
        //Uses the normal way to get the data and populate a moviedetailpojo with the values from results
        else {
            //Use a try since it might fail
            try {
                //create new jsonobject with the response data taken from the fetchfromwebsite class
                JSONObject jsonObj = new JSONObject(responseData);
                //get the array where the results are at
                JSONArray results = jsonObj.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject theMovie = results.getJSONObject(i);
                    //get the poster path and put it in a string variable
                    String moviePosterPath = theMovie.getString("poster_path");
                    //get the movie id and put it in a string variable
                    int movieId = theMovie.getInt("id");
                    //get the movie title and put it in a string variable
                    String movieTitle = theMovie.getString("title");
                    //get the release date and put it in a string variable
                    String movieReleasedDate = theMovie.getString("release_date");
                    //get the popularity and put it in a string variable
                    double moviePopularity = theMovie.getDouble("popularity");
                    //get the vote count and put it in a string variable
                    String movieVoteCount = theMovie.getString("vote_count");
                    //get the vote average and put it in a string variable
                    String movieVoteAverage = theMovie.getString("vote_average");

                    //create new moviedetailpojo
                    MovieDetailPojo moviePojo = new MovieDetailPojo();
                    //set the movie title for the moviedetailpojo
                    moviePojo.setTitle(movieTitle);
                    //set the movie release date for the moviedetailpojo
                    moviePojo.setRelease_date(movieReleasedDate);
                    //set the popularity for the moviedetailpojo
                    moviePojo.setPopularity(moviePopularity);
                    //set the vote count for the moviedetailpojo
                    moviePojo.setVote_count(movieVoteCount);
                    //set the vote average for the moviedetailpojo
                    moviePojo.setVote_average(movieVoteAverage);
                    //set the poster path for the moviedetailpojo
                    moviePojo.setPoster_path(moviePosterPath);
                    //set the movie id for the moviedetailpojo
                    moviePojo.setId(movieId);
                    //finally add the moviedetailpojo to the array list
                    myArrayMovies.add(moviePojo);

                }
            } catch (JSONException e) {
                //print error message if there is one
                e.printStackTrace();
            }
        }
        //create new moviesadapter with context, arraylist, onclick listener
        moviesAdapter = new MoviesAdapter(myArrayMovies, this);
        //create new linear layout manager set it to vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        //Set the layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //set adapter to the recycler view
        recyclerView.setAdapter(moviesAdapter);
    }

    //On click listener that handles the onclicks
    @Override
    public void onItemClick(View v, int position) {
        //Create new intent that goes to the detail screen
        Intent intent = new Intent(MainActivity.this,MovieDetailActivity.class);
        //put the movie id for the url in the intent extra
        intent.putExtra("movieId",myArrayMovies.get(position).getId());
        //start the activity in the intent
        startActivity(intent);
    }
}