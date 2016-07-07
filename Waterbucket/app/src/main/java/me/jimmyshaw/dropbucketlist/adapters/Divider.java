package me.jimmyshaw.dropbucketlist.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import me.jimmyshaw.dropbucketlist.R;

public class Divider extends RecyclerView.ItemDecoration {

    public static final String TAG = "Jim";

    private Drawable mDivider;
    private int mOrientation;

    public Divider(Context context, int orientation) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.divider);

        // The orientation parameter is used to check and make sure our recycler view is running
        // in the vertical orientation when we draw the divider.
        if (orientation != LinearLayoutManager.VERTICAL) {
            throw new IllegalArgumentException("This item decoration can only be used with a RecyclerView " +
                    "that has a LinearLayoutManager with its orientation set to vertical.");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawHorizontalDivider(c, parent, state);
        }
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        // Four boundary values are needed to draw our divider.
        int left, top, right, bottom;

        // Force the divider to respect each row item's padding.
        left = parent.getPaddingLeft();
        right = parent.getWidth() - parent.getPaddingRight();

        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            // If our recycler view item is the footer then we don't draw the divider.
            if (AdapterDrops.FOOTER != parent.getAdapter().getItemViewType(i)) {
                View currentChild = parent.getChildAt(i);
                // Force the divider to respect the child view's margins by using LayoutParams.
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) currentChild.getLayoutParams();
                top = currentChild.getTop() - params.topMargin;
                bottom = top + mDivider.getIntrinsicHeight();
                // After determining the boundaries values, we set the bounds of the divider and then
                // draw it.
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // Modify the boundary values to change spacing between row items.
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            // left, top, right, bottom
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }
    }
}
