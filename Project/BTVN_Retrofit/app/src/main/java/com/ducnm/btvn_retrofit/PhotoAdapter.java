package com.ducnm.btvn_retrofit;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ducnm.btvn_retrofit.dto.Photo;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    List<Photo> photos;

    public void setPhotoList(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoAdapter.PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.photo_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.PhotoViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.setData(photo);
    }

    @Override
    public int getItemCount() {
        if (photos != null) {
            return photos.size();
        } else {
            return 0;
        }
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUrl;
        private TextView tvTitle;

        void bindingView() {
            imgUrl = itemView.findViewById(R.id.imgUrl);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        void bindingAction() {
        }

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
        }

        public void setData(Photo photo) {
            Log.d("DucNM_Debug", "setData: " + photo.toString());
            tvTitle.setText(photo.getTitle());
            // TODO: set image
            String url = photo.getUrl();
            if (url != null) {
                Glide.with(itemView.getContext())
                        .load(url)
                        .error(R.mipmap.ic_error)
                        .placeholder(R.mipmap.ic_loading)
                        .into(imgUrl);
            }
        }
    }
}
