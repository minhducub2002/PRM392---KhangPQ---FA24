package com.DucNM.myapplication.ui.home;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DucNM.myapplication.R;

import java.util.List;

public class ListCategoryAdapter extends RecyclerView.Adapter<ListCategoryAdapter.ViewHolder> {

    private List<Category> data;

    public ListCategoryAdapter(List<Category> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ListCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.categories_item, parent, false);
        Log.d("DucNM_Debug", "create View Holder roi nha");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCategoryAdapter.ViewHolder holder, int position) {
        Category category = data.get(position);
        holder.setData(category);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageButton btnCategory;
        private TextView tvCategory;

        private void bindingView() {
            btnCategory = itemView.findViewById(R.id.btnCategory);
            tvCategory = itemView.findViewById(R.id.tvCategory);
        }

        private void bindingAction() {
            btnCategory.setOnClickListener(this::onBtnCategoryClick);
        }

        private void onBtnCategoryClick(View view) {
            Intent intent = new Intent(view.getContext(), ProductDetail.class);
            intent.putExtra("imageResId", data.get(getAdapterPosition()).getCategoryResId());
            intent.putExtra("categoryName", data.get(getAdapterPosition()).getCategoryName());
            view.getContext().startActivity(intent);
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
            Log.d("DucNM_Debug", "ivCategory: " + btnCategory);
            Log.d("DucNM_Debug", "tvCategory: " + tvCategory);
        }

        public void setData(Category category) {
            btnCategory.setImageResource(category.getCategoryResId());
            tvCategory.setText(category.getCategoryName());
        }
    }
}
