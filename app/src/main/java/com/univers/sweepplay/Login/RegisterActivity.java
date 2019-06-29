package com.univers.sweepplay.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.univers.sweepplay.Login.helpers.InputValidation;
import com.univers.sweepplay.Login.model.User;
import com.univers.sweepplay.Login.sql.DatabaseHelper;
import com.univers.sweepplay.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout inputName;
    private TextInputLayout inputEmail;
    private TextInputLayout inputPassword;
    private TextInputLayout inputConfirmPassword ;
    private TextInputEditText textInputName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = findViewById(R.id.nestedScrollView);
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
        textInputName = findViewById(R.id.textInputName);
        textInputEditTextEmail = findViewById(R.id.textInputEmail);
        textInputEditTextPassword = findViewById(R.id.textInputPassword);
        textInputConfirmPassword = findViewById(R.id.textInputConfirmPassword );
        appCompatButtonRegister = findViewById(R.id.appCompatButtonRegister);
        appCompatTextViewLoginLink = findViewById(R.id.appCompatTextViewLoginLink);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputName, inputName, getString(R.string.error_message_name))) {
            return; }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, inputEmail, getString(R.string.error_message_email))) {
            return; }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, inputEmail, getString(R.string.error_message_email))) {
            return; }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, inputPassword, getString(R.string.error_message_password))) {
            return; }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputConfirmPassword,
                inputConfirmPassword , getString(R.string.error_password_match))) {
            return; }

        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {
            user.setName(textInputName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());
            databaseHelper.addUser(user);
            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputConfirmPassword.setText(null);
    }
}