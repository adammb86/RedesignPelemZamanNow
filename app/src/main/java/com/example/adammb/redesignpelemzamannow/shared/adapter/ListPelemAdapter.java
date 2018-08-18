package com.example.adammb.redesignpelemzamannow.shared.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.adammb.redesignpelemzamannow.R;
import com.example.adammb.redesignpelemzamannow.data.model.Pelem;
import com.example.adammb.redesignpelemzamannow.screen.detail.DetailPelemActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListPelemAdapter extends RecyclerView.Adapter<ListPelemAdapter.CategoryViewHolder> {
    private Context context;
    private LinkedList<Pelem> listPelem;
    public static final String EXTRA_MOVIE = "extraMovie";

    public ListPelemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    public LinkedList<Pelem> getListPelem() {
        return listPelem;
    }

    public void setListPelem(LinkedList<Pelem> listPelem) {
        this.listPelem = listPelem;
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
                    detailIntent.putExtra(EXTRA_MOVIE, getListPelem().get(getAdapterPosition()));


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
        holder.tvOriginalTitle.setText(getListPelem().get(position).getOriginal_title());
        holder.tvDescription.setText(getListPelem().get(position).getOverview());

        SimpleDateFormat releaseDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date date = releaseDate.parse(getListPelem().get(position).getRelease_date());
            SimpleDateFormat formattedDate = new SimpleDateFormat("dd-MMMM-yyyy", Locale.US);
            holder.tvReleaseDate.setText(formattedDate.format(date));
        } catch (ParseException e) {
            holder.tvReleaseDate.setText(getListPelem().get(position).getRelease_date());
            e.printStackTrace();
        }

        Glide.with(context)
                .load(getListPelem().get(position).getPoster_path())
                .crossFade()
                .into(holder.imgPosterFilm);
    }

    @Override
    public int getItemCount() {
        return getListPelem().size();
    }
}
