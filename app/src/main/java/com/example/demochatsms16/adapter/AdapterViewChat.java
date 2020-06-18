package com.example.demochatsms16.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demochatsms16.R;
import com.example.demochatsms16.model.ObDataMess;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class AdapterViewChat extends RecyclerView.Adapter<AdapterViewChat.ViewHolder> {
    List<ObDataMess> dataMessList;

    public AdapterViewChat(List<ObDataMess> list) {
        this.dataMessList = list;
    }

    @NotNull
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
            return new AdapterViewChat.ViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_left, parent, false);
            return new AdapterViewChat.ViewHolder(view);
        }
    }


    public int getItemCount() {
        return dataMessList.size();
    }

    public void onBindViewHolder(@NotNull AdapterViewChat.ViewHolder holder, int position) {
        ObDataMess obDataMess = dataMessList.get(position);
        holder.tvChat.setText(obDataMess.getSmg());
    }


    public int getItemViewType(int position) {
        return ((ObDataMess)this.dataMessList.get(position)).isSent() == 1 ? 1 : 0;
    }

    @NotNull
    public final List getList() {
        return this.dataMessList;
    }

    public final void setList(@NotNull List var1) {
        this.dataMessList = var1;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvChat;
        public ViewHolder(@NotNull View itemView) {

            super(itemView);
            tvChat = (TextView)itemView.findViewById(R.id.tv_chat);
        }
    }
}
