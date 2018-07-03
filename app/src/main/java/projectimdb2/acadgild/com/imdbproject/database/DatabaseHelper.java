package projectimdb2.acadgild.com.imdbproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import projectimdb2.acadgild.com.imdbproject.interfaces.constants.DATABASE_CONSTANTS;
import projectimdb2.acadgild.com.imdbproject.models.detailmodels.MovieDetailPojo;

public class DatabaseHelper extends SQLiteOpenHelper implements DATABASE_CONSTANTS {


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //method thats called when its created
    @Override
    public void onCreate(SQLiteDatabase db) {
        //execute database table creation if it doesnt exist
        db.execSQL(CREATE_MY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    //method that inserts records into the database
    public void insertRecordDB(ContentValues contentValues) {
        try {
            //create new sqllitedatabase and get the writeabledatabase
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            //Perform an insert and store the int results from the insert to be used as a test to see if it needs updated instead
            int testForUpdate = (int) sqLiteDatabase.insertWithOnConflict(TABLE_MOVIEDETAILS, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
            //check if the testForReplace if its true then it needs to be updates instead of inserted
            if (testForUpdate == -1) {
                //update the database entry instead of inserting with new information
                sqLiteDatabase.update(TABLE_MOVIEDETAILS, contentValues, COLUMN_ID + "=?", new String[]{contentValues.get(COLUMN_ID).toString()});
            }
            //close database
            sqLiteDatabase.close();
            //catch handler
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //method that gets all items from database depending on the selection they asked for watchlist or favorites
    public ArrayList<MovieDetailPojo> getMoviesSelection(String selection) {
        //initialize and create new arraylist
        ArrayList<MovieDetailPojo> arrayList = new ArrayList<>();
        //create new sqlitedabase and get readable database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        //create query to select all from table where whatever they selected is equal to Yes
        String query = "select * from "+ TABLE_MOVIEDETAILS + " where " + selection + "= 'Y'";
        //create a cursor and get the results from the query
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        //test if the cursor isnt null/empty
        if (cursor != null){
            //move cursor to the first one
            cursor.moveToFirst();
            //a do loop
            do {
                //create new moviedetail pojo
                MovieDetailPojo movieDetailPojo = new MovieDetailPojo();
                //set the moviedetail pojo set the id from the cursor
                movieDetailPojo.setId(cursor.getInt(0));
                //set the moviedetail pojo set the title from the cursor
                movieDetailPojo.setTitle(cursor.getString(1));
                //set the moviedetail pojo set the release date from the cursor
                movieDetailPojo.setRelease_date(cursor.getString(2));
                //set the moviedetail pojo set the poster path from the cursor
                movieDetailPojo.setPoster_path(cursor.getString(3));
                //set the moviedetail pojo set the popularity from the cursor
                movieDetailPojo.setPopularity(cursor.getInt(4));
                //set the moviedetail pojo set the vote average from the cursor
                movieDetailPojo.setVote_average((cursor.getString(5)));
                //set the moviedetail pojo set the vote count from the cursor
                movieDetailPojo.setVote_count((cursor.getString(6)));
                //set the moviedetail pojo set the overview from the cursor
                movieDetailPojo.setOverview((cursor.getString(7)));
                //set the moviedetail pojo set the favorite from the cursor
                movieDetailPojo.setFavorite((cursor.getString(8)));
                //set the moviedetail pojo set the watchlist from the cursor
                movieDetailPojo.setWatchlist((cursor.getString(9)));
                //add moviedetail pojo to the arraylist
                arrayList.add(movieDetailPojo);
                //while cursor sets to move to next
            } while (cursor.moveToNext()) ;
            //close the cursor since its no loner needed
            cursor.close();
        }
        //return the arraylist
        return arrayList;
    }
}
