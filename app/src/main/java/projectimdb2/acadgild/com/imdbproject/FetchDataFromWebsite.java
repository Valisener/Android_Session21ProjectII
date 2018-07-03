package projectimdb2.acadgild.com.imdbproject;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import projectimdb2.acadgild.com.imdbproject.interfaces.constants.MOVIE_CONSTANTS;
import projectimdb2.acadgild.com.imdbproject.interfaces.responsehandler.ResponseHandler;

public class FetchDataFromWebsite extends AsyncTask<Void, Void, Void> implements MOVIE_CONSTANTS {
    //context not sure why its here but teacher had this so i followed suit
    private Context context;
    //reponse handler initialization
    private ResponseHandler responseHandler;
    //String that contains the Response data from the json file initialization
    private String responseData;
    //string that contains the website url initialization
    private String websiteUrl;
    private String type;

    //Constructor that accepts response handler and context
    FetchDataFromWebsite(Context con, String url, ResponseHandler responseHandler,String type){
        //set the variable for context
        context = con;
        //set responsehandler
        this.responseHandler = responseHandler;
        //set website url
        websiteUrl=url;
        //set the type variable
        this.type = type;
    }
    //different contructor that handles the call from main window that does just the list of movies not detail
    FetchDataFromWebsite(Context con, String url, ResponseHandler responseHandler){
        //set the variable for context
        context = con;
        //set responsehandler
        this.responseHandler = responseHandler;
        //set website url
        websiteUrl=url;
        //set the type variable
        this.type = null;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //Create new httpclient
        OkHttpClient okHttpClient= new OkHttpClient();
        //Set the connection time out in case it cant connect
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        //set the read timeout in case it cant read the data in a timely manner
        okHttpClient.setReadTimeout(120,TimeUnit.SECONDS);
        //create a new request and pass the url address
        Request request = new Request.Builder().url(websiteUrl).build();
        //initialize the string response data to null
        responseData = null;
        try {
            //make a request from the client server
            Response response = okHttpClient.newCall(request).execute();
            //check if it was successful
            if (response.isSuccessful()){
                //Set response data to the response since it was successful
                responseData = response.body().string();
            }
            //catch exception in case an error occurs
        } catch (IOException e) {
            e.printStackTrace();
        }
        //send back the null response
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //set the information in the response handler to the string that was fetched from json file
        //and update the activity
        responseHandler.updateActivity(responseData,type);
    }
}
