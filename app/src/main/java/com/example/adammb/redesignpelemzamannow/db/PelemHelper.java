package com.example.adammb.redesignpelemzamannow.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.adammb.redesignpelemzamannow.data.model.Pelem;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.adammb.redesignpelemzamannow.db.DatabaseContract.PelemColumn.ORIGINAL_TITLE;
import static com.example.adammb.redesignpelemzamannow.db.DatabaseContract.PelemColumn.OVERVIEW;
import static com.example.adammb.redesignpelemzamannow.db.DatabaseContract.PelemColumn.POSTER_PATH;
import static com.example.adammb.redesignpelemzamannow.db.DatabaseContract.PelemColumn.RELEASE_DATE;
import static com.example.adammb.redesignpelemzamannow.db.DatabaseContract.PelemColumn.VOTE_AVERAGE;
import static com.example.adammb.redesignpelemzamannow.db.DatabaseContract.PelemColumn.VOTE_COUNT;
import static com.example.adammb.redesignpelemzamannow.db.DatabaseContract.TABLE_NAME;

public class PelemHelper {
    private static String DATABASE_TABLE = TABLE_NAME;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public PelemHelper(Context context){
        this.context = context;
    }

    public PelemHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<Pelem> query(){
        ArrayList<Pelem> arrayList = new ArrayList<Pelem>();
        Cursor cursor = database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null,_ID +" DESC"
                ,null);
        cursor.moveToFirst();

        Pelem pelem;

        if (cursor.getCount()>0) {
            do {

                pelem = new Pelem();
                pelem.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                pelem.setOriginal_title(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_TITLE)));
                pelem.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                pelem.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                pelem.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                pelem.setVote_average(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
                pelem.setVote_count(cursor.getInt(cursor.getColumnIndexOrThrow(VOTE_COUNT)));

                arrayList.add(pelem);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Pelem pelem){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(ORIGINAL_TITLE,pelem.getOriginal_title());
        initialValues.put(OVERVIEW,pelem.getOverview());
        initialValues.put(RELEASE_DATE,pelem.getRelease_date());
        initialValues.put(POSTER_PATH,pelem.getPoster_path());
        initialValues.put(VOTE_AVERAGE,pelem.getVote_average());
        initialValues.put(VOTE_COUNT,pelem.getVote_count());

        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(Pelem pelem){
        ContentValues args = new ContentValues();
        args.put(ORIGINAL_TITLE,pelem.getOriginal_title());
        args.put(OVERVIEW,pelem.getOverview());
        args.put(RELEASE_DATE,pelem.getRelease_date());
        args.put(POSTER_PATH,pelem.getPoster_path());
        args.put(VOTE_AVERAGE,pelem.getVote_average());
        args.put(VOTE_COUNT,pelem.getVote_count());

        return database.update(DATABASE_TABLE, args, _ID + "= '" + pelem.getId() + "'", null);
    }

    public int delete(int id){
        return database.delete(TABLE_NAME, _ID + " = '"+id+"'", null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }

}
