package me.jimmyshaw.fragmentpageradapter.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.jimmyshaw.fragmentpageradapter.R;

public class FragmentFive extends Fragment {

    public FragmentFive() {
        Log.i("Fragment", "Fragment Five Created");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_five, container, false);
    }
}

