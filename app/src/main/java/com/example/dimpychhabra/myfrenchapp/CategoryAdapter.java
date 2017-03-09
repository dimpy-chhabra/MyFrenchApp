package com.example.dimpychhabra.myfrenchapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * Created by Dimpy Chhabra on 3/9/2017.
 */

public class CategoryAdapter extends FragmentPagerAdapter {

    public CategoryAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new FragmentNumbers();
        }else if (position == 1){
            return new FragmentSalutations();
        }else if (position == 2){
            return new FragmentColors();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
