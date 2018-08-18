package com.example.pelempavorit.screen.main;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.pelempavorit.R;
import com.example.pelempavorit.shared.CursorAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.pelempavorit.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private CursorAdapter listPelemAdapter;
    private int loaderID = 110;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_view_pelem)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        listPelemAdapter = new CursorAdapter(this);
        listPelemAdapter.setListPelem(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listPelemAdapter);

        getSupportLoaderManager().initLoader(loaderID, savedInstanceState, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        listPelemAdapter.setListPelem(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        listPelemAdapter.setListPelem(null);
    }
}
