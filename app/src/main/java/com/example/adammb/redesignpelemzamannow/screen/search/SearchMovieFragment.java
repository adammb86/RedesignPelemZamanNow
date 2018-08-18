package com.example.adammb.redesignpelemzamannow.screen.search;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adammb.redesignpelemzamannow.R;
import com.example.adammb.redesignpelemzamannow.data.loader.MyPelemSearchLoader;
import com.example.adammb.redesignpelemzamannow.data.model.Pelem;
import com.example.adammb.redesignpelemzamannow.shared.adapter.ListPelemAdapter;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMovieFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<LinkedList<Pelem>> {

    @BindView(R.id.rv_view_pelem)
    RecyclerView rvPelem;

    private static final String EXTRA_TITLE = "extraTitle";

    private ListPelemAdapter listPelemAdapter;
    private int loaderID = 102;
    private LinkedList<Pelem> listPelem;


    public SearchMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        RecyclerView recyclerView = view.findViewById(R.id.rv_view_pelem);
        listPelemAdapter = new ListPelemAdapter(getActivity());
        listPelem = new LinkedList<>();
        listPelemAdapter.setListPelem(listPelem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listPelemAdapter);

        rvPelem.setHasFixedSize(true);

        getActivity().getSupportLoaderManager().initLoader(loaderID, savedInstanceState, this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_TITLE, query);

                getActivity().getSupportLoaderManager().restartLoader(loaderID, bundle, SearchMovieFragment.this);

                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @NonNull
    @Override
    public Loader<LinkedList<Pelem>> onCreateLoader(int id, @Nullable Bundle args) {
        String originalTitle = "";

        if (args != null)
            originalTitle = args.getString(EXTRA_TITLE);

        return new MyPelemSearchLoader(getActivity(), originalTitle);

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
