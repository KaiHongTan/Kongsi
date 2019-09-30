package com.kaihongtan.kongsi.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kaihongtan.kongsi.PrivacySettings;
import com.kaihongtan.kongsi.ProfileSettings;
import com.kaihongtan.kongsi.R;
import com.kaihongtan.kongsi.Reviews;
import com.kaihongtan.kongsi.SettingsActivity;

public class NotificationsFragment extends Fragment {
    Button profile;
    Button appsettings;
    Button privacysettings;
    Button reviews;
    Button logout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        profile = root.findViewById(R.id.button7);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ProfileSettings.class);
                startActivity(i);
            }
        });
        appsettings = root.findViewById(R.id.button8);
        appsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SettingsActivity.class);
                startActivity(i);
            }
        });
        privacysettings = root.findViewById(R.id.button9);
        privacysettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PrivacySettings.class);
                startActivity(i);
            }
        });
        reviews = root.findViewById(R.id.button10);
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Reviews.class);
                startActivity(i);
            }
        });
        logout = root.findViewById(R.id.button11);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return root;
    }
}