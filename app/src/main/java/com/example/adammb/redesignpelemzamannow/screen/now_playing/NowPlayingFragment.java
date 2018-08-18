package com.example.adammb.redesignpelemzamannow.screen.now_playing;


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
import com.example.adammb.redesignpelemzamannow.data.loader.MyPelemNowPlayingLoader;
import com.example.adammb.redesignpelemzamannow.data.model.Pelem;
import com.example.adammb.redesignpelemzamannow.shared.adapter.ListPelemAdapter;

import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<LinkedList<Pelem>> {
    private ListPelemAdapter listPelemAdapter;
    private int loaderID = 100;
    private LinkedList<Pelem> listPelem;


    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_view_pelem);
        listPelemAdapter = new ListPelemAdapter(getActivity());
        listPelem = new LinkedList<>();
        listPelemAdapter.setListPelem(listPelem);
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
    public Loader<LinkedList<Pelem>> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyPelemNowPlayingLoader(getActivity());

    }

    @Override
    public void onLoadFinished(@NonNull Loader<LinkedList<Pelem>> loader, LinkedList<Pelem> data) {
        listPelemAdapter.setListPelem(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<LinkedList<Pelem>> loader) {
        listPelemAdapter.setListPelem(null);
    }
}
