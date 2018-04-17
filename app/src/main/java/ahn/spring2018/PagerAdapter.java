package ahn.spring2018;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    String[] titles = new String[] {"Scarne's Dice", "Schedule", "Infinite Kittens", "MADGram"};

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DiceFragment();
            case 1:
                return new ScheduleFragment();
            case 2:
                return new KittensFragment();
            case 3:
                return new FeedFragment();
            default:
                return new DiceFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
