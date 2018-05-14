package com.example.gogoooma.cooperativeproject;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileActivity extends Fragment {
    View v;
    Member user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_profile, container, false);

        init();
        return v;
    }

    public void init(){
        user = GlobalVariable.g_user;
        TextView profileName2 = (TextView) v.findViewById(R.id.profileName2);
        TextView profileAge2 = (TextView) v.findViewById(R.id.profileAge2);
        TextView profilePhone2 = (TextView) v.findViewById(R.id.profilePhone2);

        profileName2.setText(user.getName());
        profileAge2.setText(user.getAge() + "");
        profilePhone2.setText(user.getPhoneNum());
    }
}
