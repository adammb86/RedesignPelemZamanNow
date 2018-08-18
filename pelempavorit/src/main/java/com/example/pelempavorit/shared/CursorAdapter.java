package com.example.pelempavorit.shared;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pelempavorit.R;
import com.example.pelempavorit.data.Pelem;
import com.example.pelempavorit.screen.detail.DetailPelemActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.pelempavorit.db.DatabaseContract.CONTENT_URI;

public class CursorAdapter extends RecyclerView.Adapter<CursorAdapter.CategoryViewHolder> {
    private Context context;
    private Cursor cursor;
    public static final String EXTRA_MOVIE = "extraMovie";

    public CursorAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    public Cursor getListPelem() {
        return cursor;
    }

    public void setListPelem(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_movie_title)
        TextView tvOriginalTitle;
        @BindView(R.id.tv_movie_description)
        TextView tvDescription;
        @BindView(R.id.tv_release_date)
        TextView tvReleaseDate;
        @BindView(R.id.img_poster_film)
        ImageView imgPosterFilm;
        @BindView(R.id.btn_detail)
        Button btnDetail;
        @BindView(R.id.btn_share)
        Button btnShare;


        public CategoryViewHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailIntent = new Intent(context, DetailPelemActivity.class);
                    detailIntent.setData(Uri.parse(CONTENT_URI+"/"+getItem(getAdapterPosition()).getId()));

                    context.startActivity(detailIntent);
                }
            });

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Tombol imi tombol share", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pelem, parent, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Pelem pelem = getItem(position);
        holder.tvOriginalTitle.setText(pelem.getOriginal_title());
        holder.tvDescription.setText(pelem.getOverview());

        SimpleDateFormat releaseDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date date = releaseDate.parse(pelem.getRelease_date());
            SimpleDateFormat formattedDate = new SimpleDateFormat("dd-MMMM-yyyy", Locale.US);
            holder.tvReleaseDate.setText(formattedDate.format(date));
        } catch (ParseException e) {
            holder.tvReleaseDate.setText(pelem.getRelease_date());
            e.printStackTrace();
        }

        Glide.with(context)
                .load(pelem.getPoster_path())
                .crossFade()
                .into(holder.imgPosterFilm);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return getListPelem().getCount();
    }

    private Pelem getItem(int position){
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Pelem(cursor);
    }
}
