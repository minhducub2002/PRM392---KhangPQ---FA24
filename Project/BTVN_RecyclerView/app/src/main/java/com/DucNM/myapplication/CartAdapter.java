package com.DucNM.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import com.DucNM.myapplication.ui.home.Category;
import com.DucNM.myapplication.ui.home.ListCategoryAdapter;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Product> data;

    public CartAdapter(List<Product> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Product product = data.get(position);
        holder.setData(product);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvProduct;
        private TextView tvDetail;

        private void bindingView() {
            tvProduct = itemView.findViewById(R.id.tvProduct);
            tvDetail = itemView.findViewById(R.id.tvDetail);
        }

        private void bindingAction() {

        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
        }

        public void setData(Product product) {
            tvProduct.setText(product.getName());
            tvDetail.setText(product.getDetail());
        }
    }
}
