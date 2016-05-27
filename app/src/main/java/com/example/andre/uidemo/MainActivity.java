package com.example.andre.uidemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Window;

import com.astuetz.PagerSlidingTabStrip;
import com.example.andre.uidemo.fragment.Fragment2;
import com.example.andre.uidemo.fragment.Fragment3;
import com.example.andre.uidemo.fragment.ListRefreshFragment;

public class MainActivity extends FragmentActivity {
    private static int PAGE_NUM = 3;
    private ViewPager mViewPager;
    private PagerSlidingTabStrip tabs;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new SlidePageAdapter(getSupportFragmentManager()));

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(mViewPager);

    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0){
            super.onBackPressed();
        }else{
            mViewPager.setCurrentItem(0);
        }
    }

    private class SlidePageAdapter extends FragmentPagerAdapter{
        private final String[] TITLES = getResources().getStringArray(R.array.title_array);

       public SlidePageAdapter(FragmentManager fm) {
           super(fm);
       }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
       public Fragment getItem(int position) {
           Fragment fragment;
           switch (position){
               case 0:
                   return fragment = new ListRefreshFragment();
               case 1:
                   return fragment = new Fragment2();
               case 2:
                   return fragment = new Fragment3();
               default:
                   return new Fragment();
           }

       }

       @Override
       public int getCount() {
           return PAGE_NUM;
       }
   }




}
