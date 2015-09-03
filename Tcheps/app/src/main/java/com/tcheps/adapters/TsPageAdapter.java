package com.tcheps.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mael-fosso on 9/3/15.
 */
public class TsPageAdapter extends FragmentPagerAdapter  {

    private Context mContext;
    private List<Fragment> fragmentList;
    private List<String> fragmentTitleList;

    public TsPageAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;

        fragmentList = new ArrayList<Fragment>();
        fragmentTitleList = new ArrayList<String>();
    }

    public void addFragment(Fragment f, String title) {
        fragmentList.add(f);
        fragmentTitleList.add(title);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
}
