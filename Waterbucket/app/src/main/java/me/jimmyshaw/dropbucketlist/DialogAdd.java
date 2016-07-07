package me.jimmyshaw.dropbucketlist;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

import io.realm.Realm;
import me.jimmyshaw.dropbucketlist.models.Drop;
import me.jimmyshaw.dropbucketlist.widgets.CustomDatePickerView;

public class DialogAdd extends DialogFragment {

    private ImageButton mButtonClose;
    private EditText mInputEditText;
    // We're using our own custom date picker type but if in the future we'd like to switch back to
    // the default date picker, simply change the type to DatePicker.
    private CustomDatePickerView mInputDatePicker;
    private Button mButtonAdd;

    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();

            switch (id) {
                case R.id.button_add_it:
                    addAction();
                    break;
            }

            dismiss();
        }
    };

    private void addAction() {
        // Get the value of the goal or task item. Get the time of when it was added.
        String goal = mInputEditText.getText().toString();

        long dateAdded = System.currentTimeMillis();

        // To use Realm, we have to configure it and then add the configuration to a Realm instance.
        // Since we already configured Realm on start up in the AppDropBucketList class we can
        // simply get a Realm instance without issue.
        Realm realm = Realm.getDefaultInstance();
        Drop drop = new Drop(dateAdded, mInputDatePicker.getTime(), goal, false);
        // Since copyToRealm is a write instruction, it must be used with a transaction.
        realm.beginTransaction();
        realm.copyToRealm(drop);
        realm.commitTransaction();
        realm.close();
    }

    public DialogAdd() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mButtonClose = (ImageButton) view.findViewById(R.id.button_close);
        mInputEditText = (EditText) view.findViewById(R.id.edit_text_drop);
        mInputDatePicker = (CustomDatePickerView) view.findViewById(R.id.date_picker_view_date);
        mButtonAdd = (Button) view.findViewById(R.id.button_add_it);

        mButtonClose.setOnClickListener(mButtonClickListener);
        mButtonAdd.setOnClickListener(mButtonClickListener);

    }
}
