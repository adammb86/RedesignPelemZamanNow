package com.example.adammb.redesignpelemzamannow.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.adammb.redesignpelemzamannow.R;
import com.example.adammb.redesignpelemzamannow.data.model.Pelem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.adammb.redesignpelemzamannow.db.DatabaseContract.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Cursor cursor;
    private Context mContext;
    private int mAppWidgetId;


    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();

        cursor=mContext.getContentResolver().query(CONTENT_URI,null,null,null,null);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(cursor==null)
            return 0;
        else
            return cursor.getCount();

    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Pelem pelem=getItem(position);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .load(pelem.getPoster_path())
                    .asBitmap()
                    .error(new ColorDrawable(mContext.getResources().getColor(R.color.colorPrimary)))
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        rv.setImageViewBitmap(R.id.imageView,bitmap);

        Bundle extras = new Bundle();
        extras.putInt(WidgetFavourite.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private Pelem getItem(int position){
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Pelem(cursor);
    }
}
