package com.example.pharmareminder.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pharmareminder.R;
import com.example.pharmareminder.data.model.Prescription;

public class PrescriptionAdapter extends ListAdapter<Prescription, PrescriptionAdapter.PVH> {

    protected PrescriptionAdapter() {
        super(DIFF_CALLBACK);
    }

    static final DiffUtil.ItemCallback<Prescription> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Prescription>() {
                @Override
                public boolean areItemsTheSame(@NonNull Prescription oldItem, @NonNull Prescription newItem) {
                    return oldItem.id == newItem.id;
                }
                @Override
                public boolean areContentsTheSame(@NonNull Prescription oldItem, @NonNull Prescription newItem) {
                    return oldItem.hasReceivedToday == newItem.hasReceivedToday &&
                           oldItem.isActive == newItem.isActive;
                }
            };

    @NonNull
    @Override
    public PVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PVH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prescription, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PVH holder, int position) {
        Prescription p = getItem(position);
        holder.title.setText(p.shortName + " (" + p.timeTerm + ")");
        holder.itemView.setOnClickListener(v -> DetailActivity.start(v.getContext(), p.id));
    }

    static class PVH extends RecyclerView.ViewHolder {
        TextView title;
        PVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
        }
    }
}
