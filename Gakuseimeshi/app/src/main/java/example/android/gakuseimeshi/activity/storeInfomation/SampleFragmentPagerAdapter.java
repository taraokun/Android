package example.android.gakuseimeshi.activity.storeInfomation;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import example.android.gakuseimeshi.activity.map.MapsActivity;
import example.android.gakuseimeshi.activity.storeInfomation.Fragment.InformationFragment;

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"店舗情報", "地図"};
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
                return new InformationFragment();
            case 1:
                return new MapsActivity();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
