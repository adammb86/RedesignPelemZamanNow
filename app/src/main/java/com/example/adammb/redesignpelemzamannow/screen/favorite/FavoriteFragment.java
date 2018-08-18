package com.example.adammb.redesignpelemzamannow.screen.favorite;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adammb.redesignpelemzamannow.R;
import com.example.adammb.redesignpelemzamannow.data.loader.MyPelemFavoriteLoader;
import com.example.adammb.redesignpelemzamannow.shared.adapter.CursorAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private CursorAdapter listPelemAdapter;
    private int loaderID = 110;


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_view_pelem);
        listPelemAdapter = new CursorAdapter(getActivity());
        listPelemAdapter.setListPelem(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listPelemAdapter);

        getActivity().getSupportLoaderManager().initLoader(loaderID, savedInstanceState, this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyPelemFavoriteLoader(getActivity());

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
