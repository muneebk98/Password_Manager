package com.example.assignment3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleBinAdapter extends RecyclerView.Adapter<RecycleBinAdapter.RecycleBinViewHolder> {

    private List<RecycleBinItem> recycleBinItems;

    public RecycleBinAdapter(List<RecycleBinItem> recycleBinItems) {
        this.recycleBinItems = recycleBinItems;
    }

    @NonNull
    @Override
    public RecycleBinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_item, parent, false);
        return new RecycleBinViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return recycleBinItems.size();
    }

    public static class RecycleBinViewHolder extends RecyclerView.ViewHolder {
        public TextView siteNameTextView;
        public TextView siteUrlTextView;
        public TextView loginTextView;
        public TextView sitePasswordTextView;

        private ImageButton deleteButton;

        public RecycleBinViewHolder(View itemView) {
            super(itemView);
            siteNameTextView = itemView.findViewById(R.id.siteNameTextView);
            siteUrlTextView = itemView.findViewById(R.id.siteUrlTextView);
            loginTextView = itemView.findViewById(R.id.loginTextView);
            sitePasswordTextView = itemView.findViewById(R.id.sitePasswordTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }




    @Override
    public void onBindViewHolder(@NonNull RecycleBinViewHolder holder, int position) {
        RecycleBinItem recycleBinItem = recycleBinItems.get(position);
        holder.siteNameTextView.setText(recycleBinItem.getSiteName());
        holder.siteUrlTextView.setText(recycleBinItem.getSiteUrl());
        holder.loginTextView.setText(recycleBinItem.getLogin());
        holder.sitePasswordTextView.setText(recycleBinItem.getSitePassword());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDB db = new MyDB(view.getContext());
                db.restorePassword(recycleBinItem.getId());
                db.close();
                notifyDataSetChanged();
                removeItem(position);
            }
        });
    }

    public void removeItem(int position) {
        if (position >= 0 && position < recycleBinItems.size()) {
            recycleBinItems.remove(position);
            notifyItemRemoved(position);
        }
    }



}