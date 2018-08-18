package com.example.adammb.redesignpelemzamannow.screen.detail;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adammb.redesignpelemzamannow.R;
import com.example.adammb.redesignpelemzamannow.data.model.Pelem;
import com.example.adammb.redesignpelemzamannow.shared.adapter.ListPelemAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.adammb.redesignpelemzamannow.db.DatabaseContract.CONTENT_URI;
import static com.example.adammb.redesignpelemzamannow.db.DatabaseContract.PelemColumn.ORIGINAL_TITLE;
import static com.example.adammb.redesignpelemzamannow.db.DatabaseContract.PelemColumn.OVERVIEW;
import static com.example.adammb.redesignpelemzamannow.db.DatabaseContract.PelemColumn.POSTER_PATH;
import static com.example.adammb.redesignpelemzamannow.db.DatabaseContract.PelemColumn.RELEASE_DATE;
import static com.example.adammb.redesignpelemzamannow.db.DatabaseContract.PelemColumn.VOTE_AVERAGE;
import static com.example.adammb.redesignpelemzamannow.db.DatabaseContract.PelemColumn.VOTE_COUNT;

public class DetailPelemActivity extends AppCompatActivity{
    @BindView(R.id.tv_detail_original_title)
    TextView tvDetailOriginalTitle;
    @BindView(R.id.img_detail_poster)
    ImageView imgDetailPoster;
    @BindView(R.id.tv_detail_rate_film)
    TextView tvDetailRateFilm;
    @BindView(R.id.tv_detail_vote_count)
    TextView tvVoteCount;
    @BindView(R.id.tv_detail_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_detail_description)
    TextView tvDescription;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Pelem pelem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pelem);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.movie_detail);
        Bundle intent = getIntent().getExtras();
        if (intent.getString("cursor") != null) {
            Uri uri = getIntent().getData();
            if (uri != null) {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) pelem = new Pelem(cursor);
                    cursor.close();
                }
            }
        } else {
            pelem = intent.getParcelable(ListPelemAdapter.EXTRA_MOVIE);
        }

        if (pelem != null) {
            tvDetailOriginalTitle.setText(pelem.getOriginal_title());

            Glide.with(this)
                    .load(pelem.getPoster_path())
                    .crossFade()
                    .into(imgDetailPoster);

            tvDetailRateFilm.setText(String.valueOf(pelem.getVote_average()));
            tvVoteCount.setText(String.valueOf(pelem.getVote_count()));
            tvDescription.setText(pelem.getOverview());

            SimpleDateFormat releaseDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            try {
                Date date = releaseDate.parse(pelem.getRelease_date());
                SimpleDateFormat formattedDate = new SimpleDateFormat("dd-MMMM-yyyy", Locale.US);
                tvReleaseDate.setText(formattedDate.format(date));
            } catch (ParseException e) {
                tvReleaseDate.setText(pelem.getRelease_date());
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.btn_favorite})
    public void onFavoriteButtonClicked(){
        ContentValues args = new ContentValues();
        args.put(ORIGINAL_TITLE,pelem.getOriginal_title());
        args.put(OVERVIEW,pelem.getOverview());
        args.put(RELEASE_DATE,pelem.getRelease_date());
        args.put(POSTER_PATH,pelem.getPoster_path());
        args.put(VOTE_AVERAGE,pelem.getVote_average());
        args.put(VOTE_COUNT,pelem.getVote_count());
        getContentResolver().insert(CONTENT_URI, args);

        Toast.makeText(this,"This film is already listed in your Favorite List",Toast.LENGTH_LONG).show();
    }
}
