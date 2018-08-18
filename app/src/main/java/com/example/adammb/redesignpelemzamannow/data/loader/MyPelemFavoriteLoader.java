package com.example.adammb.redesignpelemzamannow.data.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.adammb.redesignpelemzamannow.screen.main.MainActivity;

import static com.example.adammb.redesignpelemzamannow.db.DatabaseContract.CONTENT_URI;

public class MyPelemFavoriteLoader extends AsyncTaskLoader<Cursor> {
    private Cursor cursor;
    private boolean hasResult = false;
    private static final String TAG = MainActivity.class.getSimpleName();

    public MyPelemFavoriteLoader(@NonNull Context context) {
        super(context);

        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(cursor);
    }

    @Override
    public void deliverResult(@Nullable Cursor data) {
        cursor = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult) {
            cursor = null;
            hasResult = false;
        }
    }

    @Nullable
    @Override
    public Cursor loadInBackground() {
        return getContext().getContentResolver().query(CONTENT_URI,null,null,null,null);
    }
}
