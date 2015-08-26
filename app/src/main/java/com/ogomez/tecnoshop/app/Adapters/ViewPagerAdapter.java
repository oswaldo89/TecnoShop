package com.ogomez.tecnoshop.app.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ogomez.tecnoshop.app.Fragments.CategoriasFragment;
import com.ogomez.tecnoshop.app.Fragments.UltimosFragment;

/**
 * Created by Oswaldo Gomez on 23/07/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int pos) {

        Fragment fragment = null;
        switch(pos) {

            case 0:
                fragment = new UltimosFragment();
                break;
            case 1:
                fragment = new CategoriasFragment();
                break;
            /*
            case 2:
                fragment = new DummyFragment();
                break;
                */
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

}