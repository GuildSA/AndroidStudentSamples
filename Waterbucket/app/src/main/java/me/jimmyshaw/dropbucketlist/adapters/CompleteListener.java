package me.jimmyshaw.dropbucketlist.adapters;

public interface CompleteListener {
    // This method takes in a row item position and marks that row item at that
    // position as completed.
    void onComplete(int position);
}
