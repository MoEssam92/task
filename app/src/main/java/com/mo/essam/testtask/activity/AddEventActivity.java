package com.mo.essam.testtask.activity;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mo.essam.testtask.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class AddEventActivity extends AppCompatActivity {
    ImageView backImage;
    TextView titleText, dateText;
    EditText titleEdit,descEdit;

    String date="";
    int year=1992,month=5,day=9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        initializeViews();

        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH);
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    }

    public void selectDate(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd",new Locale("en"));
                SimpleDateFormat format_2 = new SimpleDateFormat("yyyyMMdd",new Locale("en"));
                String dateString = year+"/"+(month+1)+"/"+dayOfMonth;
                try {
                    date=format_2.format(format.parse(dateString));
                    dateText.setText(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        },year,month,day);
        datePickerDialog.show();
    }

    public void addNewEvent(View view) {
        if (date.equalsIgnoreCase("")){
            Toast.makeText(this, "Please Select Event finish Date", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(titleEdit.getText().toString())){
            titleEdit.setError("Field is required");
        }
        else if (TextUtils.isEmpty(descEdit.getText().toString())){
            descEdit.setError("Field is required");
        }
        else {
            requestNewEvent();
        }
    }

    private void requestNewEvent(){
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();

        String today = year+""+month+""+day;

        values.put(CalendarContract.Events.DTSTART, today);
        values.put(CalendarContract.Events.TITLE, titleEdit.getText().toString());
        values.put(CalendarContract.Events.DESCRIPTION, descEdit.getText().toString());

        TimeZone timeZone = TimeZone.getDefault();
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());

        values.put(CalendarContract.Events.CALENDAR_ID, 1);

        values.put(CalendarContract.Events.RRULE, "FREQ=DAILY;UNTIL="
                + date);
        values.put(CalendarContract.Events.DURATION, "+P1H");

        values.put(CalendarContract.Events.HAS_ALARM, 1);

        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

        finish();
    }

    private void initializeViews(){
        backImage = findViewById(R.id.backImage);
        titleText = findViewById(R.id.titleText);
        titleEdit = findViewById(R.id.titleEdit);
        descEdit = findViewById(R.id.descEdit);
        dateText = findViewById(R.id.dateText);

        titleText.setText(getString(R.string.add_event));
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
