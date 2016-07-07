package me.jimmyshaw.dropbucketlist.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import me.jimmyshaw.dropbucketlist.utilities.Util;

// This is a custom recycler view class that behaves differently from the normal recycler view.
public class CustomRecyclerView extends RecyclerView {

    // Using empty list will allow us to make a list with zero items. This is the only way to initialize
    // an empty list and to avoid null pointer exceptions.
    private List<View> mViewsToShowIfRecyclerViewIsNotEmpty = Collections.emptyList();
    private List<View> mViewsToShowIfRecyclerViewIsEmpty = Collections.emptyList();

    // Our app's main activity includes a recycler view and a tool bar. The vision is that the
    // recycler view and tool bar will only appear when the recycler view has data to display.
    // If there's no data then the main UI will only show our app's symbol and the Add a drop button.
    // To do this, we use the abstract class called AdapterDataObserver with methods that will
    // each check whether or not the recycler view contains data.
    private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            updateUI();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            updateUI();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            updateUI();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            updateUI();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            updateUI();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            updateUI();
        }
    };

    // Typical constructor to initialize a recycler view from code. The other two constructors are
    // to initialize the constructor from xml.
    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        // Whenever our recycler view has its adapter set, we'll modify the adapter's usual
        // behavior by having it register our adapter data observer. Every time set adapter is called
        // our adapter changes and we fire off the observer's onChange method that we defined above.
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mAdapterDataObserver);
        }

        mAdapterDataObserver.onChanged();
    }

    // When the adapter data observer determines our recycler view contains no data, the hideIfEmpty
    // method that takes in a view(s) will execute and hide the view(s). We'll use this method to hide
    // our tool bar but we could pass in other view widgets and they will be hidden.
    public void hideIfEmpty(View... viewsToHide) {
        mViewsToShowIfRecyclerViewIsNotEmpty = Arrays.asList(viewsToHide);
    }

    // When the adapter data observer determines our recycler view contains no data, the showIfEmpty
    // method that takes in a custom view(s) will execute and display that custom view(s).
    // What we will pass in is the empty drops view that will display nothing but the app logo and
    // the Add a drop button but we could pass in other views to show them if we want.
    public void showIfEmpty(View... viewsToShow) {
        mViewsToShowIfRecyclerViewIsEmpty = Arrays.asList(viewsToShow);
    }

    private void updateUI() {
        // This recycler view must always have an adapter or our functionality that derives from
        // the adapter data observer won't work. Our lists of if empty views and if non-empty views
        // cannot be empty because then we wouldn't have any views for this method to update.
        if (getAdapter() != null && !mViewsToShowIfRecyclerViewIsEmpty.isEmpty() && !mViewsToShowIfRecyclerViewIsNotEmpty.isEmpty()) {

            if (getAdapter().getItemCount() == 0) {
                // When the adapter item count is 0 then the recycler view is empty with no goals to
                // display so we set the visibility of this recycler view to gone and then display
                // whichever views we'd like to show when the recycler view is empty.
                Util.showViews(mViewsToShowIfRecyclerViewIsEmpty);

                // Hide the recycler view.
                setVisibility(View.GONE);

                // Hide all other views that are meant to be hidden.
                Util.hideViews(mViewsToShowIfRecyclerViewIsNotEmpty);
            }
            else {
                // Show all other views that are meant to be shown.
                Util.showViews(mViewsToShowIfRecyclerViewIsNotEmpty);

                // Show the recycler view.
                setVisibility(View.VISIBLE);

                // Hide all if empty views.
                Util.hideViews(mViewsToShowIfRecyclerViewIsEmpty);
            }
        }
    }
}
