package com.ducnm.btvn_sqliteopenhelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> ProductList;
    private IClickItemProduct iClickItemProduct;

    public interface IClickItemProduct {
        void updateProduct(Product product);

        void deleteProduct(Product product);
    }

    public ProductAdapter(IClickItemProduct iClickItemProduct) {
        this.iClickItemProduct = iClickItemProduct;
    }

    public ProductAdapter(List<Product> productList) {
        this.ProductList = productList;
    }

    public void setProductList(List<Product> newProductList) {
        //this.ProductList.clear();
        this.ProductList = newProductList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        final Product product = ProductList.get(position);
        if (product == null) {
            return;
        }

        holder.setData(product);

        holder.btnUpdateProduct.setOnClickListener(view -> iClickItemProduct.updateProduct(product));
        holder.btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemProduct.deleteProduct(product);
            }

        });
    }

    @Override
    public int getItemCount() {
        if (ProductList != null) {
            return ProductList.size();
        } else {
            return 0;
        }
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView tvProductName;
        private TextView tvProductDescription;
        private TextView tvProductPrice;
        private Button btnUpdateProduct;
        private Button btnDeleteProduct;

        private void bindingView() {
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductDescription = itemView.findViewById(R.id.tvProductDescription);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            btnUpdateProduct = itemView.findViewById(R.id.btnUpdateProduct);
            btnDeleteProduct = itemView.findViewById(R.id.btnDeleteProduct);
        }

        private void bindingAction() {

        }


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
        }

        public void setData(Product p) {
            tvProductName.setText(p.getProductName());
            tvProductDescription.setText(p.getProductDescription());
            tvProductPrice.setText(p.getProductPrice().toString());
        }
    }
}
