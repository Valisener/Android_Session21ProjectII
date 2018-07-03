package projectimdb2.acadgild.com.imdbproject.interfaces.constants;

public interface MOVIE_CONSTANTS {
    //set the base url for the movie lists
    String BASE_URL = "http://api.themoviedb.org/3/movie/";
    //set the last part for the url of the movie lists
    String apiKey = "?api_key=8496be0b2149805afa458ab8ec27560c";
    //String with the value of latest for the url
    String LATEST_TEXT = "latest";
    //String with the value of upcoming for the url
    String UPCOMING_TEXT = "upcoming";
    //String with the value of nowplaying for the url
    String NOW_PLAYING_TEXT = "now_playing";
    //String with the value of popular for the url
    String POPULAR_TEXT = "popular";
    //String with the value of toprated for the url
    String TOP_RATED = "top_rated";
    //This is for posting the rating the base part of the url for it
    String POST_BASE = "https://api.themoviedb.org/3/movie/";
    //this is for posting the rating 3rd part of it 2nd part is the movieid thats handled elsewhere 3rd part is the guestsession id
    String POST_GUEST_SESSION = "/rating?guest_session_id=41d7e0796d8b0ac4f53df5af9a6d2141";
    //this is for posting the rating this part holds the apikey for posting
    String POST_API_KEY = "&api_key=8496be0b2149805afa458ab8ec27560c";
    //String that contains the first part of the url for the poster on the movies list Not detailed movies just movies list
    String BASE_IMAGE_URL_POSTER = "https://image.tmdb.org/t/p/w185_and_h278_bestv2/";
    //String that contains the first part of the url for the posters on the detailed movie list
    String DETAIL_MOVIE_POSTER_IMAGE_BASE = "http://api.themoviedb.org/3/movie/";
    //String that contains the last part of the url for the posters on the detailed movie list
    String DETAIL_MOVIE_POSTER_IMAGE_APIKEY = "/images?api_key=8496be0b2149805afa458ab8ec27560c";

    //String that contains the first part for the detailed movie url
    String MOVIE_DETAIL_URL_BASE = "http://api.themoviedb.org/3/movie/";
    //String that contains the api key part for the detailed movie url
    String MOVIE_DETAIL_API_KEY = "?api_key=f47dd4de64c6ef630c2b0d50a087cc33";
    //String that contains the first part for the cast and crew url
    String MOVIE_DETAIL_CAST_AND_CREW_BASE = "http://api.themoviedb.org/3/movie/";
    //String that contains the last part for the cast and crew url
    String MOVIE_DETAIL_CAST_AND_CREW_END = "/credits?api_key=8496be0b2149805afa458ab8ec27560c";
    //String that contains the first part for the trailer url
    String MOVIE_DETAIL_TRAILERS_BASE = "http://api.themoviedb.org/3/movie/";
    //String that contains the last part for the trailer url
    String MOVIE_DETAIL_TRAILERS_END = "/videos?api_key=8496be0b2149805afa458ab8ec27560c";
    //String that contains the first part for the youtube url
    String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    //String that contains the url to the latest movies
    final String URL_MOVIES_LATEST = BASE_URL + LATEST_TEXT + apiKey;
    //String that contains the url to the upcoming movies
    final String URL_MOVIES_UPCOMING = BASE_URL + UPCOMING_TEXT + apiKey;
    //String that contains the url to the now playing movies
    final String URL_MOVIES_NOW_PLAYING = BASE_URL + NOW_PLAYING_TEXT + apiKey;
    //String that contains the url to the popular movies
    final String URL_MOVIES_POPULAR = BASE_URL + POPULAR_TEXT + apiKey;
    //String that contains the url to the top rated movies
    final String URL_MOVIES_TOP_RATED = BASE_URL + TOP_RATED + apiKey;
}