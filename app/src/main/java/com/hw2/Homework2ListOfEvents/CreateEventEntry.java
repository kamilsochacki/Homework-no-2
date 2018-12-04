package com.hw2.Homework2ListOfEvents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static java.lang.Math.random;

public class CreateEventEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_entry);
    }

    public void submit(View view) {
        String EventName = ((EditText)findViewById(R.id.newEventName)).getText().toString();
        String EventDate = ((EditText)findViewById(R.id.newEventDate)).getText().toString();
        String EventShortDescription = ((EditText)findViewById(R.id.newEventShortDescription)).getText().toString();

        if ( EventName.isEmpty() )
            EventName = getResources().getString(R.string.EventName);
        if ( EventDate.isEmpty() )
            EventDate = getResources().getString(R.string.EventDate);
        if ( EventShortDescription.isEmpty() )
            EventShortDescription = getResources().getString(R.string.EventShortDescription);

        String EventPic = "pic" + (int)(  1 + random() * 5 );
        int EventPicID = getResources().getIdentifier(EventPic, "drawable", getPackageName() );
        Event tmp = new Event( EventName, EventDate, EventShortDescription, EventPicID );
        MainActivity.myEvents.add(tmp);

        finish();
    }
}
