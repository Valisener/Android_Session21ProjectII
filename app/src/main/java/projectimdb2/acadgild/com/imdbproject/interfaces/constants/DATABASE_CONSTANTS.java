package projectimdb2.acadgild.com.imdbproject.interfaces.constants;

public interface DATABASE_CONSTANTS {
    //string that holds the text create table  for the database creation
    String CREATE_TABLE = "CREATE TABLE ";
    //string that holds the text moviedetails for the database creation
    String TABLE_MOVIEDETAILS = "moviedetails";
    //string that holds the text comma  for the database creation
    String COMMA = ", ";
    //string that holds the text id  for the database creation
    String COLUMN_ID = "id";
    //string that holds the text title for the database creation
    String COLUMN_TITLE = "title";
    //string that holds the text releasedate for the database creation
    String COLUMN_RELEASE_DATE = "releasedate";
    //string that holds the text posterpath for the database creation
    String COLUMN_POSTER_PATH = "posterpath";
    //string that holds the text popularity for the database creation
    String COLUMN_POPULARITY = "popularity";
    //string that holds the text voteaverage for the database creation
    String COLUMN_VOTE_AVERAGE = "voteaverage";
    //string that holds the text votecount for the database creation
    String COLUMN_VOTE_COUNT = "votecount"    ;
    //string that holds the text overview for the database creation
    String COLUMN_MOVIE_RAW_DETAILS  = "overview" ;
    //string that holds the text favorites for the database creation
    String COLUMN_IS_FAVORITE   = "favorites" ;
    //string that holds the text watchlist for the database creation
    String COLUMN_IS_WATCHLIST = "watchlist"  ;
    //string that holds the text for database name  for the database creation
    String DATABASE_NAME = "mymoviedatabase";
    //integer that holds the databaseversion number
    int DATABASE_VERSION = 1;
    //string that holds the text
    String OPEN_BRACKET = "(";
    //string that holds the text
    String DATATYPE_VARCHAR = " VARCHAR";
    //string that holds the text
    String DATATYPE_NUMERIC = " INTEGER";
    //string that holds the text REAL for the database creation
    String DATATYPE_REAL = " REAL";


    //String that create the database table i used a string since android said it would be better than stringbuilder
    String CREATE_MY_TABLE = CREATE_TABLE +
            TABLE_MOVIEDETAILS + OPEN_BRACKET +
            COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_TITLE +             DATATYPE_VARCHAR + COMMA  +
            COLUMN_RELEASE_DATE +      DATATYPE_VARCHAR + COMMA  +
            COLUMN_POSTER_PATH +       DATATYPE_VARCHAR + COMMA  +
            COLUMN_POPULARITY +        DATATYPE_REAL    + COMMA  +
            COLUMN_VOTE_AVERAGE +      DATATYPE_REAL    + COMMA  +
            COLUMN_VOTE_COUNT +        DATATYPE_NUMERIC + COMMA  +
            COLUMN_MOVIE_RAW_DETAILS + DATATYPE_VARCHAR + COMMA  +
            COLUMN_IS_FAVORITE +       DATATYPE_VARCHAR + COMMA  +
            COLUMN_IS_WATCHLIST +      DATATYPE_VARCHAR + COMMA  +
            "UNIQUE (" + COLUMN_ID + ") ON CONFLICT REPLACE)";
}
