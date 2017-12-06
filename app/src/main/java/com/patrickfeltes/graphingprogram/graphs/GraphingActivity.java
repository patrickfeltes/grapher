package com.patrickfeltes.graphingprogram.graphs;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.patrickfeltes.graphingprogram.R;

// code for tabbed activity comes from:
// https://www.linux.com/learn/android-app-development-beginners-navigation-tabs
public class GraphingActivity extends Activity {
    private ActionBar.Tab tab1;
    private ActionBar.Tab tab2;

    private Fragment graphingFragment = new GraphingFragment();
    private Fragment equationFragment = new EquationFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_graphing);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        tab1 = actionBar.newTab().setText("Graphs");
        tab2 = actionBar.newTab().setText("Equations");

        tab1.setTabListener(new TabListener(graphingFragment));
        tab2.setTabListener(new TabListener(equationFragment));

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
    }
}
