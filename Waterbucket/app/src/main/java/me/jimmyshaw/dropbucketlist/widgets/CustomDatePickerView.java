package me.jimmyshaw.dropbucketlist.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.jimmyshaw.dropbucketlist.R;


// This class extends LinearLayout because our custom date picker layout xml file has the foot
// element of LinearLayout.
public class CustomDatePickerView extends LinearLayout implements View.OnTouchListener {

    public static final String TAG = "Jim";

    private TextView mTextViewMonth;
    private TextView mTextViewDay;
    private TextView mTextViewYear;

    private Calendar mCalendar;

    private SimpleDateFormat mSimpleDateFormat;

    private Drawable mUpNormal;
    private Drawable mUpPressed;
    private Drawable mDownNormal;
    private Drawable mDownPressed;

    // These int variables represent the boundaries of our drawable text views.
    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;

    private boolean mIncrement;
    private boolean mDecrement;
    // The increment or decrement delay in milliseconds.
    private static final int DELAY = 200;

    // The purpose of using a handler is to take in to consideration long pressing on a date field's
    // up or down arrow. A message that an arrow is pressed is added to the message queue. This handler
    // handles that message by either increment or decrement the field value after a specified delay.
    // If the arrow press continues then a new message is sent to the queue and the cycle repeats until
    // the user no longer clicks.
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (mIncrement) {
                increment(mActiveTextViewId);
            }

            if (mDecrement) {
                decrement(mActiveTextViewId);
            }

            if (mIncrement || mDecrement) {
                mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, DELAY);
            }
            return true;
        }
    });

    // Every message managed by the handler has to be encoded to a particular type, that type is int.
    // The int value itself can arbitrarily be whatever int we want as long as we know what it means.
    private int MESSAGE_WHAT = 123;
    private int mActiveTextViewId;

    public CustomDatePickerView(Context context) {
        super(context);
        initialize(context);
    }

    // These last two constructors are needed if we want to create our custom date picker view
    // from xml as opposed to in code.
    public CustomDatePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public CustomDatePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        // The purpose of this initialize method is to use the LayoutInflater to inflate the specific
        // custom date picker layout and bind it to this specific custom date picker class.
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_custom_date_picker_view,
                CustomDatePickerView.this);

        // An alternative to using the default Java calendar would be the JodaTime for Android library.
        mCalendar = Calendar.getInstance();
        // We instantiate a SimpleDateFormat class in order to set how we'd like our months to be displayed.
        // The actual updating of the month format will take place in our updateCalendar method right
        // before we set the text of the month text view.
        mSimpleDateFormat = new SimpleDateFormat("MMM");

        mUpNormal = ContextCompat.getDrawable(context, R.drawable.ic_up_normal);
        mUpPressed = ContextCompat.getDrawable(context, R.drawable.ic_up_pressed);
        mDownNormal = ContextCompat.getDrawable(context, R.drawable.ic_down_normal);
        mDownPressed = ContextCompat.getDrawable(context, R.drawable.ic_down_pressed);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        // We must save our instance state to prevent the user input values from being destroyed
        // on rotation.
        // The difference between Parcels and Bundles is that items in a Parcel must be read back in
        // the same sequence they were written. Items in a Bundle are identified by a key string and
        // can be retrieved in any order.
        // Parcelable is an interface that our objects can implement in order to save our state to, or
        // restore from, a Parcel. A Parcel can contain one or more Bundles. A Bundle implements the
        // Parcelable interface.
        super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        // We put in the super as it contains all the data that the view is holding. This super MUST
        // be retrieved in onRestoreInstanceState or our app will crash.
        bundle.putParcelable("super", super.onSaveInstanceState());
        bundle.putInt("month", mCalendar.get(Calendar.MONTH));
        bundle.putInt("day", mCalendar.get(Calendar.DAY_OF_MONTH));
        bundle.putInt("year", mCalendar.get(Calendar.YEAR));
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Parcelable) {
            Bundle bundle = (Bundle) state;
            state = bundle.getParcelable("super");

            int month = bundle.getInt("month");
            int day = bundle.getInt("day");
            int year = bundle.getInt("year");

            updateCalendar(month, day, year, 0, 0, 0);
        }
        // we call super so that our linear layout can restore its own data.
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onFinishInflate() {
        // This special method manages our views after the layout file as been inflated. The work
        // done in this method is considered the final phase of the overall inflation process.
        super.onFinishInflate();

        // We're allowed to use the this keyword because our class extends a LinearLayout, which
        // itself is a view.
        mTextViewMonth = (TextView) this.findViewById(R.id.text_view_month);
        mTextViewDay = (TextView) this.findViewById(R.id.text_view_day);
        mTextViewYear = (TextView) this.findViewById(R.id.text_view_year);

        // To process the user's click of the up or down button on our custom date picker, we have to
        // include an onTouchListener on each widget.
        mTextViewMonth.setOnTouchListener(this);
        mTextViewDay.setOnTouchListener(this);
        mTextViewYear.setOnTouchListener(this);

        // After initializing our widgets above, we call the updateCalendar method with the
        // date parameters passed in after getting them from the calendar member variable.
        // Since our date picker doesn't take into account the hour, minute or second, we'll pass 0s.
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int year = mCalendar.get(Calendar.YEAR);
        updateCalendar(month, day, year, 0, 0, 0);
    }

    private void updateCalendar(int month, int day, int year, int hour, int minute, int second) {
        // Our goal's deadline date is in MM/DD/YYYY format. We get an instance of calendar above and set
        // month, day and year with the user input values from the date picker widget. Even though
        // our app isn't exact in specifying the time, we have to set those fields to a value anyway
        // in order to pass in the date to our Drop object as time-in-milliseconds.

        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, day);
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.HOUR, hour);
        mCalendar.set(Calendar.MINUTE, minute);
        mCalendar.set(Calendar.SECOND, second);

        mTextViewMonth.setText(mSimpleDateFormat.format(mCalendar.getTime()));
        mTextViewDay.setText(String.valueOf(day));
        mTextViewYear.setText(String.valueOf(year));

    }

    public long getTime() {
        return mCalendar.getTimeInMillis();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        // The passed in view is the view that was touched but which one? That's determined by a
        // switch statement that takes in the view's id.
        switch (view.getId()) {
            case R.id.text_view_month:
                processTouchEvents(mTextViewMonth, motionEvent);
                break;
            case R.id.text_view_day:
                processTouchEvents(mTextViewDay, motionEvent);
                break;
            case R.id.text_view_year:
                processTouchEvents(mTextViewYear, motionEvent);
                break;
        }

        // The boolean specifies whether or not the touch event has been consumed. True for yes or
        // false for no.
        return true;
    }

    private void processTouchEvents(TextView textView, MotionEvent motionEvent) {
        // To contain a touch event to a particular region of the text view drawable, we must take
        // the boundaries and calculate with them accordingly.
        Drawable[] drawables = textView.getCompoundDrawables();

        if (hasTopDrawable(drawables) && hasBottomDrawable(drawables)) {
            Rect boundsTop = drawables[TOP].getBounds();
            Rect boundsBottom = drawables[BOTTOM].getBounds();

            float x = motionEvent.getX();
            float y = motionEvent.getY();

            mActiveTextViewId = textView.getId();

            // Which drawable region was clicked? The top or the bottom?
            // If the top drawable region was clicked, do all the motion event processing for it.
            // The same applies to the bottom drawable region. If the click was to neither region,
            // we reset everything.
            if (topDrawableClicked(textView, boundsTop.height(), x, y)) {
                if (isActionDown(motionEvent)) {
                    mIncrement = true;
                    increment(textView.getId());
                    mHandler.removeMessages(MESSAGE_WHAT);
                    mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, DELAY);
                    changeDrawable(textView, true);
                }
                if (isActionUpOrCancel(motionEvent)) {
                    mIncrement = false;
                    changeDrawable(textView, false);
                }
            }
            else if (bottomDrawableClicked(textView, boundsBottom.height(), x, y)) {
                if (isActionDown(motionEvent)) {
                    mDecrement = true;
                    decrement(textView.getId());
                    mHandler.removeMessages(MESSAGE_WHAT);
                    mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, DELAY);
                    changeDrawable(textView, true);
                }
                if (isActionUpOrCancel(motionEvent)) {
                    mDecrement = false;
                    changeDrawable(textView, false);
                }
            }
            else {
                mIncrement = false;
                mDecrement = false;
                changeDrawable(textView, false);
            }
        }
    }

    private boolean hasTopDrawable(Drawable[] drawables) {
        return drawables[TOP] != null;
    }

    private boolean topDrawableClicked(TextView textView, int drawableHeight, float x, float y) {
        // Width of the text view - padding right.
        int maxX = textView.getWidth() - textView.getPaddingRight();
        // Padding left.
        int minX = textView.getPaddingLeft();
        // Padding top + drawable height.
        int maxY = textView.getPaddingTop() + drawableHeight;
        // Padding top of the text view.
        int minY = textView.getPaddingTop();

        // Return true if x and y falls inside of our defined bounds.
        return x > minX && x < maxX && y > minY && y < maxY;
    }

    private boolean hasBottomDrawable(Drawable[] drawables) {
        return drawables[BOTTOM] != null;
    }

    private boolean bottomDrawableClicked(TextView textView, int drawableHeight, float x, float y) {
        // Width of the text view - padding right.
        int maxX = textView.getWidth() - textView.getPaddingRight();
        // Padding left.
        int minX = textView.getPaddingLeft();
        // Total height of the text view - padding bottom.
        int maxY = textView.getHeight() - textView.getPaddingBottom();
        // Total height of the text view - the height of the drawable region.
        int minY = maxY - drawableHeight;


        return x > minX && x < maxX && y > minY && y < maxY;
    }

    private void changeDrawable(TextView textView, boolean isPressed) {
        // The purpose of this method is to change the up or down arrow drawables depending on whether
        // the they're pressed or not. Unpressed, we use the default drawable. Pressed or held, we
        // change it to another drawable.
        if (isPressed) {
            if (mIncrement) {
                // Left, Top, Right, Down regions of the text view.
                textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.date_picker_up_pressed, 0, R.drawable.date_picker_down_normal);
            }
            if (mDecrement) {
                textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.date_picker_up_normal, 0, R.drawable.date_picker_down_pressed);
            }
        }
        else {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.date_picker_up_normal, 0, R.drawable.date_picker_down_normal);
        }
    }

    private boolean isActionDown(MotionEvent motionEvent) {
        // ACTION_DOWN is when we first touch the screen.
        // ACTION_MOVE takes place after ACTION_DOWN when we move our finger or we hold our finger.
        // ACTION_UP takes place after we released our touch action.
        // ACTION_CANCEL occurs when the current touch action is aborted (usually by Android itself).
        return motionEvent.getAction() == MotionEvent.ACTION_DOWN;
    }

    private boolean isActionUpOrCancel(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL;
    }

    private void increment(int id) {
        // Which date field are we incrementing?
        switch (id) {
            case R.id.text_view_month:
                // Get the calendar month and increment it by 1.
                mCalendar.add(Calendar.MONTH, 1);
                break;
            case R.id.text_view_day:
                mCalendar.add(Calendar.DAY_OF_MONTH, 1);
                break;
            case R.id.text_view_year:
                mCalendar.add(Calendar.YEAR, 1);
                break;
        }
        refreshCalendarUI(mCalendar);
    }

    private void decrement(int id) {
        // Which date field are we decrementing?
        switch (id) {
            case R.id.text_view_month:
                // Get the calendar month and decrement it by 1.
                mCalendar.add(Calendar.MONTH, -1);
                break;
            case R.id.text_view_day:
                mCalendar.add(Calendar.DAY_OF_MONTH, -1);
                break;
            case R.id.text_view_year:
                mCalendar.add(Calendar.YEAR, -1);
                break;
        }
        refreshCalendarUI(mCalendar);
    }

    private void refreshCalendarUI(Calendar calendar) {
        mTextViewMonth.setText(mSimpleDateFormat.format(mCalendar.getTime()));

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        mTextViewDay.setText(String.valueOf(day));
        mTextViewYear.setText(String.valueOf(year));
    }
}
