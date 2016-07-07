package me.jimmyshaw.dropbucketlist.adapters;

public interface SwipeListener {
    // This method takes in the position of the row item that was swiped so that it can be deleted.
    void onSwipe(int position);
}
