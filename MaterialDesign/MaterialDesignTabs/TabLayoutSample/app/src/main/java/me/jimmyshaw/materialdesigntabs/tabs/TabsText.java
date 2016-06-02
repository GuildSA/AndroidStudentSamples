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
import me.jimmyshaw.materialdesigntabs.adapters.TabsTextAdapter;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentOne;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentThree;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentTwo;

public class TabsText extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_text);

        initialize();

        prepareDataResource();

        // Instantiate the adapter and bind the adapter with the viewpager. Then bind the viewpager
        // with the tab layout.
        TabsTextAdapter adapter = new TabsTextAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        mViewPager.setAdapter(adapter);

        // The viewpager must be bound with the tab layout AFTER 1) the viewpager adapter has been
        // instantiated and 2) the viewpager itself has already been bound with the adapter or
        // otherwise the app will crash.
        mTabLayout.setupWithViewPager(mViewPager);

        setTabsWithIcons();
    }

    // This method will set the text tabs with icons on top.
    private void setTabsWithIcons() {
        mTabLayout.getTabAt(0).setIcon(R.drawable.facebook);
        mTabLayout.getTabAt(1).setIcon(R.drawable.instagram);
        mTabLayout.getTabAt(2).setIcon(R.drawable.twitter);
    }

    private void initialize() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabs with Text Example");

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    private void prepareDataResource() {
        addData(new FragmentOne(), "ONE");
        addData(new FragmentTwo(), "TWO");
        addData(new FragmentThree(), "THREE");
    }

    private void addData(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mTitleList.add(title);
    }
}
