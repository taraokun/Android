package example.android.gakuseimeshi.activity.storeInfomation;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import example.android.gakuseimeshi.activity.map.MapsActivity;
import example.android.gakuseimeshi.activity.storeInfomation.Fragment.FirstFragment;
import example.android.gakuseimeshi.activity.storeInfomation.Fragment.SecondFragment;
import example.android.gakuseimeshi.activity.storeInfomation.Fragment.ThirdFragment;

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[]{"トップ", "メニュー", "口コミ", "地図"};
    private Context mContext;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();
            case 2:
                return new ThirdFragment();
            case 3:
                return new MapsActivity();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
