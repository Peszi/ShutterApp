package com.pheasant.shutterapp.shutter.ui.features.authentication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.ui.ShutterActivity;
import com.pheasant.shutterapp.shutter.api.request.LoginRequest;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.utils.IntentKey;
import com.pheasant.shutterapp.utils.Permissions;
import com.pheasant.shutterapp.utils.RequestDialog;
import com.pheasant.shutterapp.utils.Util;

public class LogInActivity extends AppCompatActivity {

    private final int LOGGING_TIMEOUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        Permissions.requestPermission(this, Permissions.CAMERA_PREMISSION, 0);
        // TODO check permissions
        // TODO account manager
        // Layout
        Util.setupFont(this.getApplicationContext(), this.getWindow());
        Util.removeInputDirectFocus(this.getWindow());
        this.prepareLogInButtonListener();
        this.prepareSignUpLinkListener();
        this.prepareDemoLinkListener();

        this.makeExampleAccountAction(); // TODO TMP auto login
    }

    /* ====================================== LISTENERS ========================================= */

    /* LOGIN */
    private void prepareLogInButtonListener() {
        Button logInButton = (Button) this.findViewById(R.id.main_log_in_button);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInActivity.this.makeLogInAction();
            }
        });
    }

    /* SIGN UP */
    private void prepareSignUpLinkListener() {
        TextView signUpLink = (TextView) this.findViewById(R.id.main_sign_up_link);
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInActivity.this.makeSignUpAction();
            }
        });
    }

    /* EXAMPLE ACCOUNT */
    private void prepareDemoLinkListener() {
        TextView exampleAccount = (TextView) this.findViewById(R.id.main_demo_link);
        exampleAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInActivity.this.prepareExampleAccountDialog();
            }
        });
    }

    /* ======================================= ACTIONS ========================================== */

    /* LOGIN (login request) */
    public void makeLogInAction() {
        if (Util.isInternetConnection(this, this.getWindow().getDecorView()) // check internet connection
                && FormChecker.checkLoginData(this.getWindow().getDecorView(), this.getUserEmail(), this.getUserPassword())) { // check form input data
            final LoginRequest loginRequest = new LoginRequest(this.getUserEmail(), this.getUserPassword());
            final RequestDialog requestDialog = new RequestDialog();
            requestDialog.showDialog(this, this.getResources().getString(R.string.form_server_logging_message), this.LOGGING_TIMEOUT, loginRequest); // updateList waiting dialog and show
            loginRequest.setOnRequestResultListener(new RequestResultListener() {
                @Override
                public void onResult(int resultCode) {
                    if (resultCode == Request.RESULT_OK) {
                        requestDialog.dismissDailog();
                        Log.d("RESPONSE", "APIKEY " + loginRequest.getUserData().getApiKey());
                        if (Permissions.havePermission(LogInActivity.this, Permissions.CAMERA_PREMISSION)) {
                            Intent intent = new Intent(LogInActivity.this, ShutterActivity.class);
//                        LogInActivity.this.finish();
                            intent.putExtra(IntentKey.USER_DATA, loginRequest.getUserData());
                          LogInActivity.this.startActivity(intent);
                        } else {
                            Snackbar.make(LogInActivity.this.getWindow().getDecorView(), "you will need a camera permission", Snackbar.LENGTH_LONG).show();
                            Permissions.requestPermission(LogInActivity.this, Permissions.CAMERA_PREMISSION, 0);
                        }
                    } else {
                        requestDialog.prepareErrorMessage(LogInActivity.this.getResources().getString(R.string.form_server_logging_error_message));
                    }
                }
            });
            loginRequest.execute();
        }
    }

    /* SIGN UP (pass data to the register form) */
    private void makeSignUpAction() {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(IntentKey.USER_EMAIL, this.getUserEmail());
        intent.putExtra(IntentKey.USER_PASSWORD, this.getUserPassword());
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        this.startActivity(intent); // put form data to register activity
    }

    /* EXAMPLE ACCOUNT (fill the form example account data) */
    private void makeExampleAccountAction() {
        this.setUserEmail(R.string.example_email);
        this.setUserPassword(R.string.example_password);
        this.findViewById(R.id.main_log_in_button).performClick();
    }

    /* ======================================= DIALOGS ========================================== */

    /* EXAMPLE ACCOUNT DIALOG (check whether we gonna use example account data or not) */
    private void prepareExampleAccountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
        builder.setTitle(R.string.example_dialog_title)
                .setMessage(R.string.example_dialog_message)
                .setPositiveButton(R.string.example_dialog_accept, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LogInActivity.this.makeExampleAccountAction();
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

    /* ================================== SETTERS & GETTERS ===================================== */

    /* SETTERS */
    private void setUserEmail(final int resId) {
        ((EditText) LogInActivity.this.findViewById(R.id.main_email_input)).setText(resId);
    }

    private void setUserPassword(final int resId) {
        ((EditText) LogInActivity.this.findViewById(R.id.main_password_input)).setText(resId);
    }

    /* GETTERS */
    private String getUserEmail() {
        return ((EditText) LogInActivity.this.findViewById(R.id.main_email_input)).getText().toString();
    }

    private String getUserPassword() {
        return ((EditText) LogInActivity.this.findViewById(R.id.main_password_input)).getText().toString();
    }
}
