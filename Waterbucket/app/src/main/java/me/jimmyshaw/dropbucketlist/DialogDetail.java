package me.jimmyshaw.dropbucketlist;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import me.jimmyshaw.dropbucketlist.adapters.CompleteListener;

public class DialogDetail extends DialogFragment {

    public static final String ARG_ROW_ITEM_POSITION = "row_item_position";

    private ImageButton mButtonClose;
    private Button mButtonCompleted;

    private CompleteListener mCompleteListener;

    // The widgets on the detail dialog can have their click functions dictated by a
    // single on click listener that differentiates between the widgets by their ids.
    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_mark_completed:
                    markAsComplete();
                    break;
                default:
                    break;
            }
            dismiss();
        }
    };

    private void markAsComplete() {
        // This method finds out which row item in the recycler view adapter was clicked and then
        // mark that row item as completed. We need to know the row item drop object at the exact
        // position where the dialog is being shown. We have to communicate between this detail
        // fragment and AdapterDrops in some way. As usual, we'll use a listener interface this time
        // called CompleteListener that has a method called onComplete that actually marks the row
        // item as completed. The implementation for onComplete takes place in ActivityMain but
        // we get access to it because we pass this listener in with the setCompleteListener method.

        // The bundle arguments that we're getting is the recycler view row item's position integer.
        // We'll use this position int to be able to mark that particular row item as completed.
        Bundle args = getArguments();
        if (mCompleteListener != null && args != null) {
            int position = args.getInt(ARG_ROW_ITEM_POSITION);
            mCompleteListener.onComplete(position);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mButtonClose = (ImageButton) view.findViewById(R.id.button_close);
        mButtonClose.setOnClickListener(mButtonClickListener);
        mButtonCompleted = (Button) view.findViewById(R.id.button_mark_completed);
        mButtonCompleted.setOnClickListener(mButtonClickListener);

    }

    public void setCompleteListener(CompleteListener listener) {
        mCompleteListener = listener;
    }
}
