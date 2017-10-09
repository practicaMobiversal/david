package com.example.david.thumbsplit;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.david.thumbsplit.Fragments.CategoriesFragment;
import com.example.david.thumbsplit.Fragments.FavoritesFragment;
import com.example.david.thumbsplit.Fragments.NewestFragment;
import com.example.david.thumbsplit.Fragments.TrendingFragment;

/**
 * Created by David on 9/27/2017.
 */

class SectionPagerAdapter extends FragmentStatePagerAdapter {
    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                CategoriesFragment categoriesFragment =new CategoriesFragment();
                return categoriesFragment;
            case 1:
                NewestFragment newestFragment=new NewestFragment();
                return newestFragment;

            case 2:TrendingFragment trendingFragment =new TrendingFragment();
                return trendingFragment;

            case 3:FavoritesFragment favoritesFragment= new FavoritesFragment();
                return favoritesFragment;

            default: return null;

        }

    }

    @Override
    public int getCount() {
        return 4 ;
    }

    public CharSequence getPageTitle(int position){

        switch(position) {
            case 0:
                return "CATEGORIES";
            case 1:
                return "NEWEST";
            case 2:
                return "TRENDING";
            case 3:
                return "FAVORITES";
            default:
                return null;
        }


    }
}
