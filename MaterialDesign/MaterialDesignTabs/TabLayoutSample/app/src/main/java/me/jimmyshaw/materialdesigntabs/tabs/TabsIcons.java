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
import me.jimmyshaw.materialdesigntabs.adapters.TabsIconsAdapter;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentFive;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentFour;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentOne;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentSix;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentThree;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentTwo;

public class TabsIcons extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_icons);

        initialize();

        prepareDataResource();

        // Instantiate the adapter and bind the adapter with the viewpager. Then bind the viewpager
        // with the tab layout.
        TabsIconsAdapter adapter = new TabsIconsAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(adapter);

        // The viewpager must be bound with the tab layout AFTER 1) the viewpager adapter has been
        // instantiated and 2) the viewpager itself has already been bound with the adapter or
        // otherwise the app will crash.
        mTabLayout.setupWithViewPager(mViewPager);

        // Likewise, only set the tabs with their icons after the tab layout has been bound with
        // the view pager.
        setTabsWithIcons();
    }

    private void setTabsWithIcons() {
        mTabLayout.getTabAt(0).setIcon(R.drawable.facebook);
        mTabLayout.getTabAt(1).setIcon(R.drawable.instagram);
        mTabLayout.getTabAt(2).setIcon(R.drawable.twitter);
        mTabLayout.getTabAt(3).setIcon(R.drawable.youtube);
        mTabLayout.getTabAt(4).setIcon(R.drawable.linkedin);
        mTabLayout.getTabAt(5).setIcon(R.drawable.whatsapp);
    }

    private void initialize() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabs with Icons Example");

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    private void prepareDataResource() {
        mFragmentList.add(new FragmentOne());
        mFragmentList.add(new FragmentTwo());
        mFragmentList.add(new FragmentThree());
        mFragmentList.add(new FragmentFour());
        mFragmentList.add(new FragmentFive());
        mFragmentList.add(new FragmentSix());
    }

}

