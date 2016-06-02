package me.jimmyshaw.materialdesigntabs.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.jimmyshaw.materialdesigntabs.R;

public class FragmentFour extends Fragment {

    public FragmentFour() {
        Log.i("Fragment Check", "Fragment Four Created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_four, container, false);

    }
}


