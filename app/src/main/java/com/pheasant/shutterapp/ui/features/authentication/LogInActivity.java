package com.pheasant.shutterapp.ui.features.authentication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.api.util.Request;
import com.pheasant.shutterapp.ui.ShutterActivity;
import com.pheasant.shutterapp.api.request.LoginRequest;
import com.pheasant.shutterapp.api.listeners.RequestResultListener;
import com.pheasant.shutterapp.util.IntentKey;
import com.pheasant.shutterapp.util.Permissions;
import com.pheasant.shutterapp.util.Util;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener, RequestResultListener {

    private final int LOGGING_TIMEOUT = 5000;

    private final String DEFAULT_EMAIL = "second@email.com";
    private final String DEFAULT_PASS = "1234";

    private EditText emailField;
    private EditText passField;

    private LoginRequest loginRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {         // TODO account manager
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        Util.setupFont(this.getApplicationContext(), this.getWindow());
        Util.removeInputDirectFocus(this.getWindow());
        Permissions.requestPermission(this, Permissions.CAMERA_PREMISSION, 0);

        this.loginRequest = new LoginRequest();
        this.loginRequest.setRequestListener(this);
        this.setupUI();
        this.logWithDefaultData(); // TODO TMP auto login
    }

    private void setupUI() {
        this.emailField = (EditText) this.findViewById(R.id.main_email_input);
        this.passField = (EditText) this.findViewById(R.id.main_password_input);
        this.findViewById(R.id.main_log_in_button).setOnClickListener(this);
        this.findViewById(R.id.main_sign_up_link).setOnClickListener(this);
        this.findViewById(R.id.main_demo_link).setOnClickListener(this);
    }

    // Buttons callback
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_log_in_button: this.logInAction(); break;
            case R.id.main_sign_up_link: this.startSignActivity(); break;
            case R.id.main_demo_link: this.showExampleAccountDialog(); break;
        }
    }

    // Request callback
    @Override
    public void onResult(int resultCode) {
        if (resultCode == Request.RESULT_OK) {
            this.startShutterActivity(this.loginRequest.getApiKey());
        } else {

        }
    }

    // Log in
    public void logInAction() {
        if (Util.isInternetConnection(this, this.getWindow().getDecorView()) // check internet connection
                && FormChecker.checkLoginData(this.getWindow().getDecorView(), this.getEmail(), this.getPass())) { // check form input data
            if (Permissions.havePermission(LogInActivity.this, Permissions.CAMERA_PREMISSION)) {
                this.loginRequest.sendRequest(this.getEmail(), this.getPass());
            } else {
                Snackbar.make(LogInActivity.this.getWindow().getDecorView(), "you will need a camera permission", Snackbar.LENGTH_LONG).show();
                Permissions.requestPermission(LogInActivity.this, Permissions.CAMERA_PREMISSION, 0);
            }
        }
    }

    // Start Activity

    private void startSignActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(IntentKey.USER_EMAIL, this.getEmail());
        intent.putExtra(IntentKey.USER_PASSWORD, this.getPass());
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        this.startActivity(intent);
    }

    private void startShutterActivity(String apiKey) {
        Intent intent = new Intent(LogInActivity.this, ShutterActivity.class);
        intent.putExtra(IntentKey.USER_API_KEY, apiKey);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        this.startActivity(intent);
    }

    // Default data

    private void logWithDefaultData() {
        this.emailField.setText(this.DEFAULT_EMAIL);
        this.passField.setText(this.DEFAULT_PASS);
        this.findViewById(R.id.main_log_in_button).performClick();
    }

    private void showExampleAccountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
        builder.setTitle(R.string.example_dialog_title)
                .setMessage(R.string.example_dialog_message)
                .setPositiveButton(R.string.example_dialog_accept, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LogInActivity.this.logWithDefaultData();
                    }
                })
                .setNegativeButton(R.string.example_dialog_reject, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog exampleAccountDialog = builder.create();
        exampleAccountDialog.show();
    }

    // Getters

    private String getEmail() { return this.emailField.getText().toString(); }

    private String getPass() { return this.passField.getText().toString(); }

}
