package projectimdb2.acadgild.com.imdbproject;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import projectimdb2.acadgild.com.imdbproject.interfaces.constants.MOVIE_CONSTANTS;
import projectimdb2.acadgild.com.imdbproject.interfaces.responsehandler.ResponseHandler;

public class PostDataToWebsite extends AsyncTask<Void, Void, Void> implements MOVIE_CONSTANTS {
    //context not sure why its here but teacher had this so i followed suit
    private Context context;
    //string that contains the website url initialization
    private String websiteUrl;
    //double that contains the rating that the user enters
    private double rating;

    //Constructor that accepts response handler and context
    PostDataToWebsite(Context con, String url, String rating) {
        //set context
        this.context = con;
        //set website url to be used
        websiteUrl = url;
        //set the rating
        this.rating = Double.parseDouble(rating);
    }

    //method that is performed in the background
    @Override
    protected Void doInBackground(Void... voids) {
        //test if the rating is a value between 0.0 and 10.0
        if ( (rating >0.0) & (rating < 10.0)) {
        //create new okhttpclient
        OkHttpClient client = new OkHttpClient();
        //create new media type json
        MediaType mediaType = MediaType.parse("application/json");
        //create new request body to make the request in the post
        RequestBody body = RequestBody.create(mediaType, "{\"value\":" + rating + "}");
        //make a request object containing the body,url, and build it
        Request request = new Request.Builder()
                .url(websiteUrl)
                .post(body)
                .addHeader("content-type", "application/json;charset=utf-8")
                .build();
        try {
            //set the response and execute the post
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            //catch in case it fails
            e.printStackTrace();
            }
        }

        //send back the null response since i dont need the response
        return null;
    }

}
