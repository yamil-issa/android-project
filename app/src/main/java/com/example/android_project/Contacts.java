package com.example.android_project;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Contacts extends Fragment {

    private ListView contactsListView;
    private ArrayList<Contact> contactsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        contactsListView = view.findViewById(R.id.contacts_list_view);

        ContentResolver cr = getActivity().getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            contactsList = new ArrayList<>();
            do {
                @SuppressLint("Range") String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contactsList.add(new Contact(contactName, false));
            } while (cursor.moveToNext());
            cursor.close();

            ContactsAdapter adapter = new ContactsAdapter(getActivity(), contactsList);
            contactsListView.setAdapter(adapter);
        }

        return view;
    }
    public class Contact {
        private String name;
        private boolean isSelected;

        public Contact(String name, boolean isSelected) {
            this.name = name;
            this.isSelected = isSelected;
        }

        public String getName() {
            return name;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

    }
    public class ContactsAdapter extends ArrayAdapter<Contact> {
        private Context context;
        private ArrayList<Contact> contactsList;

        public ContactsAdapter(Context context, ArrayList<Contact> contactsList) {
            super(context, 0, contactsList);
            this.context = context;
            this.contactsList = contactsList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
            }

            Contact contact = contactsList.get(position);

            TextView contactNameTextView = view.findViewById(R.id.contact_name);
            contactNameTextView.setText(contact.getName());

            CheckBox contactCheckBox = view.findViewById(R.id.contact_checkbox);
            contactCheckBox.setChecked(contact.isSelected());

            contactCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Contact contact = (Contact) cb.getTag();
                    contact.setSelected(cb.isChecked());
                }
            });

            contactCheckBox.setTag(contact);

            return view;
        }


    }
}