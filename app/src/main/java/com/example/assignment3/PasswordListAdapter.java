package com.example.assignment3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PasswordListAdapter extends RecyclerView.Adapter<PasswordListAdapter.PasswordViewHolder> {
    private List<Password> passwordList;
    private OnItemDeleteClickListener deleteClickListener;

    public PasswordListAdapter(List<Password> passwordList, OnItemDeleteClickListener listener) {
        this.passwordList = passwordList;
        this.deleteClickListener = listener;
    }

    @NonNull
    @Override
    public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.password_list_item, parent, false);
        return new PasswordViewHolder(view, deleteClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordViewHolder holder, int position) {
        Password password = passwordList.get(position);
        holder.siteName.setText(password.getSiteName());
        holder.siteUrl.setText(password.getSiteUrl());
        holder.login.setText(password.getLogin());
        holder.sitePassword.setText(password.getSitePassword());
    }

    @Override
    public int getItemCount() {
        return passwordList.size();
    }

    static class PasswordViewHolder extends RecyclerView.ViewHolder {
        TextView siteName;
        TextView siteUrl;
        TextView login;
        TextView sitePassword;
        ImageButton editButton;

        PasswordViewHolder(View itemView, OnItemDeleteClickListener listener) {
            super(itemView);
            siteName = itemView.findViewById(R.id.site_name);
            siteUrl = itemView.findViewById(R.id.site_url);
            login = itemView.findViewById(R.id.login);
            sitePassword = itemView.findViewById(R.id.site_password);
            editButton = itemView.findViewById(R.id.ib_edit);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemDelete(position);
                    }
                }
            });
        }
    }

    public interface OnItemDeleteClickListener {
        void onItemDelete(int position);
    }


}
