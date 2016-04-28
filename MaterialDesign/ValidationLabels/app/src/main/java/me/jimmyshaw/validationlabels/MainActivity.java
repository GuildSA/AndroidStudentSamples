package me.jimmyshaw.validationlabels;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mUsername;
    private TextInputLayout mInputLayoutUsername;
    private EditText mPassword;
    private TextInputLayout mInputLayoutPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsername = (EditText) findViewById(R.id.edit_text_username);
        mInputLayoutUsername = (TextInputLayout) findViewById(R.id.input_layout_username);
        mPassword = (EditText) findViewById(R.id.edit_text_password);
        mInputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
    }

    public void validateAndLogin(View view) {
        if (validateUsername() && validatePassword()) {
            Toast.makeText(this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateUsername() {
        if (mUsername.getText().toString().isEmpty()) {
            mInputLayoutUsername.setError("Username cannot be blank");
            return false;
        }
        else {
            // The setErrorEnabled method with false means that if the username condition is met,
            // that validation error messages will be removed from our edit text box.
            mInputLayoutUsername.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String password = mPassword.getText().toString().trim();

        if (password.length() < 8) {
            mInputLayoutPassword.setError("Password must be at least 8 characters");
            return false;
        }
        else {
            mInputLayoutPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void clearInputFields(View view) {
        mUsername.setText("");
        // After the edit text inputs are clear, reset the cursor at the username field.
        mUsername.requestFocus();
        mInputLayoutUsername.setErrorEnabled(false);

        mPassword.setText("");
        mInputLayoutPassword.setErrorEnabled(false);
    }
}
