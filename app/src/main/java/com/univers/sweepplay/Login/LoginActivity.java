package com.univers.sweepplay.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.univers.sweepplay.Login.helpers.InputValidation;
import com.univers.sweepplay.Login.sql.DatabaseHelper;
import com.univers.sweepplay.Main_Activity.MainActivityScroll;
import com.univers.sweepplay.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    public NestedScrollView nestedScrollView;
    public TextInputLayout inputEmail;
    public TextInputLayout inputPassword;
    public TextInputEditText textInputEmail;
    public TextInputEditText textInputPassword;
    public AppCompatButton buttonLogin;
    public AppCompatTextView linkRegister;
    public InputValidation inputValidation;
    public DatabaseHelper databaseHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPassword = findViewById(R.id.textInputPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        linkRegister = findViewById(R.id.linkRegister);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        buttonLogin.setOnClickListener(this);
        linkRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects database
     * */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.linkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), com.univers.sweepplay.Login.RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEmail, inputEmail, getString(R.string.error_message_email))) {
            return; }
        if (!inputValidation.isInputEditTextEmail(textInputEmail, inputEmail, getString(R.string.error_message_email2))) {
            return; }
        if (!inputValidation.isInputEditTextFilled(textInputPassword, inputPassword, getString(R.string.error_message_email3))) {
            return; }
        if (databaseHelper.checkUser(textInputEmail.getText().toString().trim()
                , textInputPassword.getText().toString().trim())) {
            Intent accountsIntent = new Intent(activity, MainActivityScroll.class);
            accountsIntent.putExtra("EMAIL", textInputEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);
        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEmail.setText(null);
        textInputPassword.setText(null);
    }
}