package project.digicard.com.digicard_customer.Adapters;

import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Sarvesh on 4/25/2017.
 */

public class MyCustomAnimation extends Animation {

    public final static int COLLAPSE = 1;
    public final static int EXPAND = 0;

    private View mView;
    private int mEndHeight;
    private int mType;
    private RelativeLayout.LayoutParams mLayoutParams;

    public MyCustomAnimation(View view, int duration, int type) {

        setDuration(duration);
        mView = view;
        mEndHeight = mView.getHeight();
        mLayoutParams = ((RelativeLayout.LayoutParams) view.getLayoutParams());
        mType = type;
        if (mType == EXPAND) {
            mLayoutParams.height = 0;
        } else {
            mLayoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        }
        view.setVisibility(View.VISIBLE);
    }

    public int getHeight() {
        return mView.getHeight();
    }

    public void setHeight(int height) {
        mEndHeight = height;
    }
}
