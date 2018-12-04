package com.hw2.Homework2ListOfEvents;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EventActivity extends AppCompatActivity implements DeleteDialog.NoticeDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.hw2.Homework2ListOfEvents.R.layout.activity_event);

        FloatingActionButton removeEvent = (FloatingActionButton)findViewById(com.hw2.Homework2ListOfEvents.R.id.removeEvent);
        removeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = DeleteDialog.newInstance();
                newFragment.show( getSupportFragmentManager(), "DeleteDialogTag" );
            }
        });

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        MainActivity.myEvents.remove(MainActivity.selected_item);
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
