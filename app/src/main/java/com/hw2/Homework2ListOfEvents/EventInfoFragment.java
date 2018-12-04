package com.hw2.Homework2ListOfEvents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventInfoFragment extends Fragment {
    public EventInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();

        Event receivedTask = intent.getParcelableExtra( MainActivity.eventExtra );
        if (receivedTask != null)
            displayTask(receivedTask);

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( com.hw2.Homework2ListOfEvents.R.layout.fragment_event_info, container, false );
    }

    public void displayTask( Event event) {
        ((TextView)getActivity().findViewById( com.hw2.Homework2ListOfEvents.R.id.EventName  ) ).setText( event.Name );
        ((TextView)getActivity().findViewById( com.hw2.Homework2ListOfEvents.R.id.EventDate ) ).setText( event.Date );
        ((TextView)getActivity().findViewById( com.hw2.Homework2ListOfEvents.R.id.EventShortDescription ) ).setText( event.Short_description );
        ImageView taskImage = (ImageView) getActivity().findViewById( com.hw2.Homework2ListOfEvents.R.id.EventPic );
        if ( event.picID != 0 ) {
            taskImage.setImageResource(event.picID);
        } else {
            taskImage.setImageBitmap(null);
        }
    }
}
