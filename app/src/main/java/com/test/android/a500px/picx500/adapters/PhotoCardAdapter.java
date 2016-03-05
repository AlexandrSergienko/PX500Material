package com.test.android.a500px.picx500.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.test.android.a500px.picx500.R;
import com.test.android.a500px.picx500.models.Photo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alex on 05.03.2016.
 */
public class PhotoCardAdapter extends RecyclerView.Adapter<PhotoCardAdapter.ViewHolder> {

    private List<Photo> items;
    private Context context;
    private AdapterView.OnItemClickListener onItemClickListener;

    public PhotoCardAdapter(List<Photo> photoList, AdapterView.OnItemClickListener onItemClickListener) {
        items = photoList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_card, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.position = position;
        Glide
                .with(context)
                .load(items.get(position).getImageUrl())
                .diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.photo)
        ImageView photo;
        int position;

        public ViewHolder(View view) {
            super(view);
            view.setTag(this);
            if(onItemClickListener!=null) {
                view.setOnClickListener(this);
            }
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(null, view, position, position);
        }
    }


}
