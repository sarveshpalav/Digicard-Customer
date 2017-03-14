package project.digicard.com.digicard_customer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Sarvesh on 21-12-2016.
 */

public class TabsPagerAdapterLoggedIn extends FragmentPagerAdapter {

    public TabsPagerAdapterLoggedIn(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new HomePageFragment();
            case 1:
                // Games fragment activity
                return new CardsFragment();
            case 2:
                return new AdListFragment();
            case 3 :
                return new LogsFragment();
            case 4:
                return new ProfileFragment();
            case 5:
                return new ProfileFragment();

        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 5;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Home";
            case 1:
                return "Cards";
            case 2:
                return "Offers";
            case 3:
                return "Log";

            default:
                return "My Profile";


        }
    }
}
