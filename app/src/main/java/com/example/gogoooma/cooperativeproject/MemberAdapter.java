package com.example.gogoooma.cooperativeproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MemberAdapter extends ArrayAdapter<Member>{
    View v;
    Context context;
    List<Member> list;
    Member now;

    public MemberAdapter(@NonNull Context context, int resource, @NonNull List<Member> objects) {
        super(context, resource, objects);
        this.context = context;
        list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        v = inflater.inflate(R.layout.row, null);
        now = list.get(position);

        TextView name = (TextView) v.findViewById(R.id.projMemberName);
        TextView phone = (TextView) v.findViewById(R.id.projMemberPhone);
        name.setText(now.getName());
        phone.setText(now.getPhoneNum());

        return v;
    }
}