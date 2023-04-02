package com.example.android_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.Manifest;
import android.telephony.SmsManager;
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

    private ListView contactsListView;
    private ListView mAutoReplyListView;
    private ArrayList<String> mAutoReplyList;
    private ArrayAdapter<String> mAdapter;
    private SharedPreferences mPrefs;

    private ArrayList<Contacts.Contact> selectedContacts;

    public  int selectedAutoReplyIndex = -1;

    public String selectedAutoReply;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_send_response, container, false);

        Contacts contactList = new Contacts();

        selectedContacts = contactList.getSelectedContacts();

        contactsListView = view.findViewById(R.id.contact_list_view);

        Contacts.ContactsAdapter adapter = new Contacts.ContactsAdapter(getActivity(), selectedContacts);
        contactsListView.setAdapter(adapter);

        mAutoReplyListView = view.findViewById(R.id.auto_reply_list_view);

        mPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);

        mAutoReplyList = new ArrayList<>(Arrays.asList(mPrefs.getString("auto_replies", "").split(",")));
        mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mAutoReplyList);
        mAutoReplyListView.setAdapter(mAdapter);



        mAutoReplyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // vérifier si le message sélectionné est déjà sélectionné ou non
                if (selectedAutoReplyIndex != -1) {
                    View previousView = parent.getChildAt(selectedAutoReplyIndex);
                    previousView.setBackgroundColor(Color.TRANSPARENT);
                }
                selectedAutoReplyIndex = position;
                view.setBackgroundColor(Color.BLUE);
                    // Récupérer la valeur de l'élément sélectionné
                    selectedAutoReply = mAutoReplyList.get(position);

            }
        });


        Button sendButton = view.findViewById(R.id.send_response_button);

        //send the sms to the selected contact
        sendButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                for(int i = 0; i < selectedContacts.size(); i++) {
                    SmsManager smsManager = SmsManager.getDefault();
                    String phoneNumber = selectedContacts.get(i).getPhoneNumber();

                    smsManager.sendTextMessage(phoneNumber, null, selectedAutoReply, null, null);

                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Votre message a été envoyé");
                builder.show();
            }
        });

        return view;
    }
}