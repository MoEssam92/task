package com.mo.essam.testtask.activity;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mo.essam.testtask.R;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {
    ImageView backImage;
    TextView titleText;

    ListView contactsList;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String>  StoreContacts = new ArrayList<>();
    Cursor cursor ;
    String name, number ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        initializeViews();

        getContacts();
    }

    public void getContacts(){

        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

        while (cursor!=null && cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            StoreContacts.add(name + "\n" + "\t" + number);

        }

        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1,
                StoreContacts);

        contactsList.setAdapter(arrayAdapter);

        if (cursor!=null) {
            cursor.close();
        }

    }

    private void initializeViews(){

        backImage = findViewById(R.id.backImage);
        titleText = findViewById(R.id.titleText);
        contactsList = findViewById(R.id.contactsList);

        titleText.setText(getString(R.string.contacts));
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


}
