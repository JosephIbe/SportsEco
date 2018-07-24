package com.jjoey.sportseco.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CoachProfilePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titlesList = new ArrayList<>();

    public CoachProfilePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addTabs(Fragment fragment, String name){
        fragmentList.add(fragment);
        titlesList.add(name);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titlesList.get(position);
    }
}
