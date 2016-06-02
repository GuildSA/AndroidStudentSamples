package me.jimmyshaw.materialdesigntabs.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import me.jimmyshaw.materialdesigntabs.R;
import me.jimmyshaw.materialdesigntabs.adapters.TabsScrollAdapter;
import me.jimmyshaw.materialdesigntabs.adapters.TabsTextAdapter;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentFive;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentFour;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentOne;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentSix;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentThree;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentTwo;

public class TabsScroll extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_scroll);

        initialize();

        prepareDataResource();

        // Instantiate the adapter and bind the adapter with the viewpager. Then bind the viewpager
        // with the tab layout.
        TabsScrollAdapter adapter = new TabsScrollAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        mViewPager.setAdapter(adapter);

        // The viewpager must be bound with the tab layout AFTER 1) the viewpager adapter has been
        // instantiated and 2) the viewpager itself has already been bound with the adapter or
        // otherwise the app will crash.
        mTabLayout.setupWithViewPager(mViewPager);


    }

    private void initialize() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabs with Scrolling Example");

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    private void prepareDataResource() {
        addData(new FragmentOne(), "ONE");
        addData(new FragmentTwo(), "TWO");
        addData(new FragmentThree(), "THREE");
        addData(new FragmentFour(), "FOUR");
        addData(new FragmentFive(), "FIVE");
        addData(new FragmentSix(), "SIX");
    }

    private void addData(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mTitleList.add(title);
    }
}
