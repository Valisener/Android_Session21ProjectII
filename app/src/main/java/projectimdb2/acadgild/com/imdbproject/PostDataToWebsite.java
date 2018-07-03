package projectimdb2.acadgild.com.imdbproject;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import projectimdb2.acadgild.com.imdbproject.interfaces.constants.MOVIE_CONSTANTS;

public class PostDataToWebsite extends AsyncTask<Void, Void, Void> implements MOVIE_CONSTANTS {
    //context not sure why its here but teacher had this so i followed suit
    private Context context;
    //string that contains the website url initialization
    private String websiteUrl;

    //Constructor that accepts response handler and context
    PostDataToWebsite(Context con, String url) {
        //set context
        this.context = con;
        //set website url to be used
        websiteUrl = url;
    }

    //method that is performed in the background
    @Override
    protected Void doInBackground(Void... voids) {
        //create new okhttpclient
        OkHttpClient client = new OkHttpClient();
        //create new media type json
        MediaType mediaType = MediaType.parse("application/json");
        //create new request body to make the request in the post
        RequestBody body = RequestBody.create(mediaType, "{\"value\":8.5}");
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
        //send back the null response since i dont need the response
        return null;
    }

}
