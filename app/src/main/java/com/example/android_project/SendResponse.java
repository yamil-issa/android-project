package com.example.android_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.Manifest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class SendResponse extends Fragment {

    private ListView mAutoReplyListView;
    private ArrayList<String> mAutoReplyList;
    private ArrayAdapter<String> mAdapter;
    private SharedPreferences mPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_response, container, false);


        mAutoReplyListView = view.findViewById(R.id.auto_reply_list_view);

        mPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);

        mAutoReplyList = new ArrayList<>(Arrays.asList(mPrefs.getString("auto_replies", "").split(",")));
        mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mAutoReplyList);
        mAutoReplyListView.setAdapter(mAdapter);


        Button sendButton = view.findViewById(R.id.send_response_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Send the selected auto-reply to the selected contact
            }
        });

        return view;
    }
}