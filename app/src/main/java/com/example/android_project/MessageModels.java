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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_auto_reply, container, false);

        // Retrieve a reference to the ListView
        mAutoReplyListView = view.findViewById(R.id.replies_list_view);

        // Initialize the SharedPreferences
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

        // Retrieve a reference to the "Add" button
        Button addButton = view.findViewById(R.id.add_reply_button);

        // Set a click listener for the "Add" button
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
        // Create a new AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the dialog title and message
        builder.setTitle("Ajouter votre reponse automatique");

        // Create a new EditText view for the dialog
        final EditText input = new EditText(getActivity());
        builder.setView(input);

        // Set a click listener for the "OK" button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Retrieve the text from the EditText view
                String text = input.getText().toString().trim();

                // Add the new auto-reply to the list
                mAutoReplyList.add(text);

                // Update the ListView adapter
                mAdapter.notifyDataSetChanged();

                // Save the updated auto-reply list to SharedPreferences
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putString("auto_replies", TextUtils.join(",", mAutoReplyList));
                editor.apply();
            }
        });

        // Set a click listener for the "Cancel" button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });

        // Show the dialog
        builder.show();
    }
}