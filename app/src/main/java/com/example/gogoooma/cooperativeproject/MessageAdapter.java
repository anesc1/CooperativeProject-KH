package com.example.gogoooma.cooperativeproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    List<MessageData> m_data;

    public MessageAdapter(List<MessageData> data) {
        m_data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.r_people.setText("From : " + m_data.get(position).getSender() + "       " +
                "To : " + m_data.get(position).getReceiver());
        holder.r_message.setText(m_data.get(position).getMessage());

        holder.index = position;
    }

    @Override
    public int getItemCount() {
        return m_data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView r_people;
        TextView r_message;
        int index;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            r_people = itemView.findViewById(R.id.people);
            r_message = itemView.findViewById(R.id.message);
        }

        @Override
        public void onClick(View v) {
            // db에서 삭제
        }
    }
}
