package com.patrickfeltes.graphingprogram.userinterface.graphs;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;

import com.patrickfeltes.graphingprogram.R;

// code for listener comes from:
// https://www.linux.com/learn/android-app-development-beginners-navigation-tabs
public class TabListener implements ActionBar.TabListener {
    Fragment fragment;

    public TabListener(Fragment fragment) {
        this.fragment = fragment;
    }

    // when the tab is selected, put the correct fragment in the container
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.replace(R.id.fragment_container, fragment);
    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.remove(fragment);
    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}
}