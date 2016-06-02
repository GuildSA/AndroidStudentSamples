package me.jimmyshaw.materialdesigntabs.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

// The adapter holds data and inflates data. The data to be used for this particular adapter are
// our fragmentList and titleList from the TabsText data source class. The adapter will then inflate
// them to be used in our viewpager items.
public class TabsTextAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private List<String> mTitleList;

    public TabsTextAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);

        mFragmentList = fragmentList;
        mTitleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        // Return a particular fragment that will be inflated based on the current position
        // of the viewpager item.
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        // This method should always return the number of items in the viewpager. The number of items
        // is determined by how many fragments are in our fragment list.
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
