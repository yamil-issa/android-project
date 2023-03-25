package com.example.android_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class MessageModels extends Fragment {

    private ListView mAutoReplyListView;
    private ArrayList<String> mAutoReplyList;
    private ArrayAdapter<String> mAdapter;
    private SharedPreferences mPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_auto_reply, container, false);


        mAutoReplyListView = view.findViewById(R.id.replies_list_view);


        mPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);

        // Load the saved auto-reply list from SharedPreferences
        mAutoReplyList = new ArrayList<>(Arrays.asList(mPrefs.getString("auto_replies", "").split(",")));
        mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mAutoReplyList);
        mAutoReplyListView.setAdapter(mAdapter);

        // Set a click listener for the ListView items
        mAutoReplyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: Implement auto-reply functionality here
            }
        });

        Button addButton = view.findViewById(R.id.add_reply_button);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAutoReplyDialog();
            }
        });

        return view;
    }

    // Show a dialog to add a new auto-reply
    private void showAddAutoReplyDialog() {
        // AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setTitle("Ajouter votre reponse automatique");


        final EditText input = new EditText(getActivity());
        builder.setView(input);

        // click listener for the "OK" button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String text = input.getText().toString().trim();

                mAutoReplyList.add(text);

                mAdapter.notifyDataSetChanged();

                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putString("auto_replies", TextUtils.join(",", mAutoReplyList));
                editor.apply();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });

        builder.show();
    }
}