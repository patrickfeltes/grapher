package com.patrickfeltes.graphingprogram.userinterface.graphs;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Window;

import com.patrickfeltes.graphingprogram.userinterface.genericactivities.AuthenticatedActivity;
import com.patrickfeltes.graphingprogram.R;

// code for tabbed activity comes from:
// https://www.linux.com/learn/android-app-development-beginners-navigation-tabs
public class GraphingActivity extends AuthenticatedActivity {
    private ActionBar.Tab tab1;
    private ActionBar.Tab tab2;

    private Fragment graphingFragment;
    private Fragment equationFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_graphing);

        graphingFragment = new GraphingFragment();
        equationFragment = new EquationFragment();

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
