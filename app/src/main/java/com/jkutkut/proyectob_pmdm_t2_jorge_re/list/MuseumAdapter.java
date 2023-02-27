package com.jkutkut.proyectob_pmdm_t2_jorge_re.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jkutkut.proyectob_pmdm_t2_jorge_re.R;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.result.Graph;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MuseumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Graph> data;

    public MuseumAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<Graph> newData) {
        data.clear();
        data.addAll(newData);
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.museum_fragment, parent, false);
        return new MuseumViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        ((MuseumViewHolder) holder).bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class MuseumViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtvName;

        public MuseumViewHolder(@NotNull View itemView) {
            super(itemView);
            txtvName = itemView.findViewById(R.id.txtvName);
        }

        public void bind(Graph museum) {
            txtvName.setText(museum.getTitle());
        }
    }
}
