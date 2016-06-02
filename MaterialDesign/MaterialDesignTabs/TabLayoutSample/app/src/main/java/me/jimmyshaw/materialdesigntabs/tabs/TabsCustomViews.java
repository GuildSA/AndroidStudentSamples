package me.jimmyshaw.materialdesigntabs.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.jimmyshaw.materialdesigntabs.R;
import me.jimmyshaw.materialdesigntabs.adapters.TabsCustomViewsAdapter;
import me.jimmyshaw.materialdesigntabs.adapters.TabsTextAdapter;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentFive;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentFour;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentOne;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentSix;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentThree;
import me.jimmyshaw.materialdesigntabs.fragments.FragmentTwo;

public class TabsCustomViews extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_custom_views);

        initialize();

        prepareDataResource();

        // Instantiate the adapter and bind the adapter with the viewpager. Then bind the viewpager
        // with the tab layout.
        TabsCustomViewsAdapter adapter = new TabsCustomViewsAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        mViewPager.setAdapter(adapter);

        // The viewpager must be bound with the tab layout AFTER 1) the viewpager adapter has been
        // instantiated and 2) the viewpager itself has already been bound with the adapter or
        // otherwise the app will crash.
        mTabLayout.setupWithViewPager(mViewPager);

        setTabsCustomViews();

    }

    private void setTabsCustomViews() {
        setTabsCustomViews(0, "WALL", "TAB ONE");
        setTabsCustomViews(1, "MESSAGES", "TAB TWO");
        setTabsCustomViews(2, "VIDEO", "TAB THREE");
        setTabsCustomViews(3, "NEWS", "TAB FOUR");
        setTabsCustomViews(4, "PHOTOS", "TAB FIVE");
        setTabsCustomViews(5, "SETTINGS", "TAB SIX");
    }

    private void setTabsCustomViews(int position, String title, String subtitle) {
        LinearLayout tabView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.tab_item, null);

        TextView textViewTitle = (TextView) tabView.findViewById(R.id.tab_text_title);
        textViewTitle.setText(title);

        TextView textViewSubtitle = (TextView) tabView.findViewById(R.id.tab_text_subtitle);
        textViewSubtitle.setText(subtitle);

        mTabLayout.getTabAt(position).setCustomView(tabView);
    }

    private void initialize() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tabs with Custom Views Example");

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
