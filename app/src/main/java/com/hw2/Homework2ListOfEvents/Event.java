package com.hw2.Homework2ListOfEvents;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    public String Name, Date, Short_description;
    int picID;

    Event() {

    }

    Event(String n, String d ) {
        Name = n;
        Date = d;
        Short_description = null;
        picID = 0;
    }

    Event(String n, String d, String s ) {
        Name = n;
        Date = d;
        Short_description = s;
        picID = 0;
    }

    Event(String n, String d, String s, int pic ) {
        Name = n;
        Date = d;
        Short_description = s;
        picID = pic;
    }

    protected Event(Parcel in) {
        Name = in.readString();
        Date = in.readString();
        Short_description = in.readString();
        picID = in.readInt();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public String toString() {
        return Name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Date);
        dest.writeString(Short_description);
        dest.writeInt(picID);
    }

}
