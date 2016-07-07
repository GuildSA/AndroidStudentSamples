package me.jimmyshaw.dropbucketlist.adapters;

// The purpose of the Filter interface is to provide values for shared preferences. When the user
// clicks a particular filter menu action, the filtered result data set along with the corresponding filter
// value from here will be saved in shared preferences. Whenever the app's onCreate is called, the
// filtered data set will be retrieved from shared preferences and be loaded to the screen.
public interface Filter {
    int NONE = 0;
    int MOST_TIME_REMAINING = 1;
    int LEAST_TIME_REMAINING = 2;
    int COMPLETE = 3;
    int INCOMPLETE = 4;
}
