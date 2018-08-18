package com.example.adammb.redesignpelemzamannow.screen.main;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adammb.redesignpelemzamannow.R;
import com.example.adammb.redesignpelemzamannow.screen.favorite.FavoriteFragment;
import com.example.adammb.redesignpelemzamannow.screen.now_playing.NowPlayingFragment;
import com.example.adammb.redesignpelemzamannow.screen.search.SearchMovieFragment;
import com.example.adammb.redesignpelemzamannow.screen.upcoming.UpcomingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    String profileImageUrl = "https://lh4.googleusercontent.com/--nZN5m7RBD4/AAAAAAAAAAI/AAAAAAAAAAA/I9tjTdhBqs0/s36-c-k/photo.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
        fragmentTransaction.replace(R.id.frame_container, nowPlayingFragment, NowPlayingFragment.class.getSimpleName());

        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        CircleImageView profileCircleImageView = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);

        Glide.with(this)
                .load(profileImageUrl)
                .into(profileCircleImageView);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        if (id == R.id.nav_now_playing) {
            NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
            fragmentTransaction.replace(R.id.frame_container, nowPlayingFragment, NowPlayingFragment.class.getSimpleName());
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(R.string.now_playing_movie);
        } else if (id == R.id.nav_upcoming) {
            UpcomingFragment upcomingFragment = new UpcomingFragment();
            fragmentTransaction.replace(R.id.frame_container, upcomingFragment, NowPlayingFragment.class.getSimpleName());
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(R.string.upcoming_movie);
        } else if (id == R.id.nav_search_film) {
            SearchMovieFragment searchMovieFragment = new SearchMovieFragment();
            fragmentTransaction.replace(R.id.frame_container, searchMovieFragment, NowPlayingFragment.class.getSimpleName());
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(R.string.search_movie);
        } else if (id == R.id.nav_app_language) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        } else if (id == R.id.nav_favorite_film) {
            FavoriteFragment favoriteFragment = new FavoriteFragment();
            fragmentTransaction.replace(R.id.frame_container, favoriteFragment, NowPlayingFragment.class.getSimpleName());
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(R.string.favorite);
        } else {
            Toast.makeText(this, "Dibuat oleh Adam Mukharil Bachtiar", Toast.LENGTH_SHORT).show();
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
