package com.hw2.Homework2ListOfEvents;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements com.hw2.Homework2ListOfEvents.DeleteDialog.NoticeDialogListener {

    public static final String EVENT_FILE = "com.nvwa.hw2.EventFile";
    public static final String NUM_EVENTS = "NumOfEvents";
    public static final String NAME = "name";
    public static final String DATE = "date";
    public static final String SHORT_DESCRIPTION = "short_description_";
    public static final String PIC = "pic_";

    static public int selected_item = -1;
    public static final String eventExtra = "Event";
    static public ArrayList<Event> myEvents;
    static {
        myEvents = new ArrayList<Event>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.hw2.Homework2ListOfEvents.R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(com.hw2.Homework2ListOfEvents.R.id.toolbar);
        setSupportActionBar(toolbar);

        readDataFromFile();

        ListAdapter eventListAdapter = new ArrayAdapter<Event>( this, android.R.layout.simple_list_item_1, android.R.id.text1, myEvents );
        ListView people = (ListView)findViewById(com.hw2.Homework2ListOfEvents.R.id.events);
        people.setAdapter(eventListAdapter);

        FloatingActionButton addEvent = (FloatingActionButton) findViewById(com.hw2.Homework2ListOfEvents.R.id.addEvent);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), CreateEventEntry.class );
                startActivity(intent);
            }
        });

        if ( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ) {
            EventInfoFragment frag = (EventInfoFragment) getSupportFragmentManager().findFragmentById(com.hw2.Homework2ListOfEvents.R.id.eventInfo);
            frag.displayTask( new Event("", "", "") );

            FloatingActionButton removePerson = (FloatingActionButton) findViewById(com.hw2.Homework2ListOfEvents.R.id.removeEvent);
            removePerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = DeleteDialog.newInstance();
                    newFragment.show( getSupportFragmentManager(), "DeleteDialogTag" );
                }
            });
        }

        people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_item = position;
                if ( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ) {
                    EventInfoFragment frag = (EventInfoFragment)getSupportFragmentManager().findFragmentById(com.hw2.Homework2ListOfEvents.R.id.eventInfo);
                    frag.displayTask( (Event)parent.getItemAtPosition(position) );
                } else {
                    Intent intent = new Intent( getApplicationContext(), EventActivity.class );
                    Event tmp = (Event) parent.getItemAtPosition(position);
                    intent.putExtra(eventExtra, tmp);

                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListAdapter eventListAdapter = new ArrayAdapter<Event>( this, android.R.layout.simple_list_item_1, android.R.id.text1, myEvents );
        ListView event = (ListView)findViewById(com.hw2.Homework2ListOfEvents.R.id.events);
        event.setAdapter(eventListAdapter);
        ((ArrayAdapter) eventListAdapter).notifyDataSetChanged();
        saveDataToFile();
    }

    private void readDataFromFile() {
        myEvents.clear();
        String filename = "myEvents.txt";
        String delim = ";";
        FileInputStream inputStream;
        try {
            inputStream = openFileInput(filename);
            BufferedReader reader = new BufferedReader( new FileReader( inputStream.getFD() ) );
            String line;
            while ( (line = reader.readLine() ) != null ) {
                String EventName = line.substring( 0, line.indexOf(delim) );
                line = line.substring( line.indexOf(delim) + 1 );
                String EventDate = line.substring( 0, line.indexOf(delim) );
                line = line.substring( line.indexOf(delim) + 1 );
                String EventShortDescription = line.substring( 0, line.indexOf(delim) );
                line = line.substring( line.indexOf(delim) + 1 );
                Event tmp = new Event(EventName, EventDate, EventShortDescription, Integer.parseInt(line) );
                myEvents.add(tmp);
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveDataToFile() {
        String filename = "myEvents.txt";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput( filename, Context.MODE_PRIVATE );
            BufferedWriter writer = new BufferedWriter( new FileWriter( outputStream.getFD() ) );
            String delim = ";";

            for ( Integer i = 0; i < myEvents.size(); i++ ) {
                Event tmp = myEvents.get(i);
                String line = tmp.Name + delim + tmp.Date + delim + tmp.Short_description + delim + tmp.picID;
                writer.write( line );
                writer.newLine();
            }
            writer.close();
        } catch ( IOException ex ) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.hw2.Homework2ListOfEvents.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.hw2.Homework2ListOfEvents.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        myEvents.remove(selected_item);
        ListAdapter eventListAdapter = new ArrayAdapter<Event>( this, android.R.layout.simple_list_item_1, android.R.id.text1, myEvents);
        ListView event = (ListView)findViewById(com.hw2.Homework2ListOfEvents.R.id.events);
        event.setAdapter(eventListAdapter);
        ((ArrayAdapter) eventListAdapter).notifyDataSetChanged();
        EventInfoFragment frag = (EventInfoFragment) getSupportFragmentManager().findFragmentById(com.hw2.Homework2ListOfEvents.R.id.eventInfo);
        frag.displayTask( new Event("", "", "") );
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
