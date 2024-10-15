package com.fpt.khangpq.se1726recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.VH> {

    private List<Word> data;

    public DictionaryAdapter(List<Word> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public DictionaryAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.word_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DictionaryAdapter.VH holder, int position) {
        Word w = data.get(position);
        holder.setData(w);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class VH extends RecyclerView.ViewHolder {

        private TextView tvWord;
        private TextView tvDefinition;

        private void bindingView() {
            tvWord = itemView.findViewById(R.id.tvWord);
            tvDefinition = itemView.findViewById(R.id.tvDefinition);
        }

        private void bindingAction() {
            tvWord.setOnClickListener(this::onTvWordClick);
            tvDefinition.setOnClickListener(this::onTvDefinitionClick);
            itemView.setOnClickListener(this::onItemViewClick);
        }

        private void onItemViewClick(View view) {
            Toast.makeText(view.getContext(),
                    tvWord.getText() + " --- " + tvDefinition.getText(),
                    Toast.LENGTH_SHORT).show();
        }

        private void onTvDefinitionClick(View view) {
            Toast.makeText(view.getContext(), tvDefinition.getText(), Toast.LENGTH_SHORT).show();
        }

        private void onTvWordClick(View view) {
            Toast.makeText(view.getContext(), tvWord.getText(), Toast.LENGTH_SHORT).show();
        }

        public VH(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
            Log.d("DucNM_Debug", "tvWord" + tvWord);
            Log.d("DucNM_Debug", "tvDefinition" + tvDefinition);
        }

        public void setData(Word w) {
            tvWord.setText(w.getWord());
            tvDefinition.setText(w.getDefinition());
        }
    }
}
