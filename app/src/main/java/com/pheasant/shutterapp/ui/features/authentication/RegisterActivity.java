package com.pheasant.shutterapp.ui.features.authentication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.api.listeners.RequestResultListener;
import com.pheasant.shutterapp.api.request.LoginRequest;
import com.pheasant.shutterapp.api.request.RegisterRequest;
import com.pheasant.shutterapp.api.util.Request;
import com.pheasant.shutterapp.ui.dialog.LoadingDialog;
import com.pheasant.shutterapp.util.BundlePacker;
import com.pheasant.shutterapp.util.Permissions;
import com.pheasant.shutterapp.util.Util;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnClickListener, RequestResultListener, LoadingDialog.LoadingDialogListener {

    private EditText nameField;
    private EditText emailField;
    private EditText passField;
    private CheckBox termsCheck;

    private AvatarPicker avatarPicker;
    private AlertDialog termsDialog;

    private LoadingDialog loadingDialog;
    private RegisterRequest registerRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_register);
        Util.setupFont(this.getApplicationContext(), this.getWindow());
        Util.removeInputDirectFocus(this.getWindow());
        // Request
        this.registerRequest = new RegisterRequest();
        this.registerRequest.setRequestListener(this);
        this.loadingDialog = new LoadingDialog(this, R.string.form_server_registering_message);
        this.loadingDialog.setListener(this);
        // UI
        this.avatarPicker = new AvatarPicker(this, this.getWindow().getDecorView());
        this.setupUI(BundlePacker.getEmailFromBundle(this.getIntent()), BundlePacker.getPassFromBundle(this.getIntent()));
        this.setupTermsDialog();
    }

    private void setupUI(String email, String pass) {
        this.nameField = (EditText) this.findViewById(R.id.register_name_input);
        this.emailField = (EditText) this.findViewById(R.id.register_email_input);
        this.passField = (EditText) this.findViewById(R.id.register_password_input);
        this.termsCheck = (CheckBox) this.findViewById(R.id.register_terms_and_conditions);
        this.termsCheck.setOnClickListener(this);
        this.findViewById(R.id.register_sign_up_button).setOnClickListener(this);
        this.findViewById(R.id.register_go_back_link).setOnClickListener(this);
        this.emailField.setText(email);
        this.passField.setText(pass);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_sign_up_button: this.signUpAction(); break;
            case R.id.register_go_back_link: this.finishRegisterActivity(); break;
            case R.id.register_terms_and_conditions: this.showTermsDialog(); break;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        this.termsCheck.setChecked(true);
        this.termsDialog.dismiss();
    }

    // Request Callback
    @Override
    public void onRequestResult(int resultCode) {
        if (resultCode == Request.RESULT_OK && this.registerRequest.isSuccess()) {
            this.loadingDialog.onSuccess();
            this.finishRegisterActivity();
        } else {
            this.loadingDialog.onFail(this.registerRequest.getServerMessage());
        }
    }

    // Loading Dialog Callback
    @Override
    public void onLoadingCanceled() {
        this.registerRequest.cancelRequest();
    }

    private void showTermsDialog() {
        this.termsCheck.setChecked(false); // uncheck until terms and conditions gonna be read
        this.termsDialog.show();
    }

    /* SIGN UP (register request) */
    private void signUpAction() {
        if (Util.isInternetConnection(this, this.getWindow().getDecorView()) // check internet connection
                && FormChecker.checkRegisterData(this.getWindow().getDecorView(),
                this.getName(), this.getEmail(), this.getPass(), this.avatarPicker.getAvatarID(), this.isTermsChecked())) {  // check form input data
                this.loadingDialog.showDialog();
                this.registerRequest.sendRequest(this.getName(),this.getEmail(), this.getPass(), this.avatarPicker.getAvatarID());
//            final RegisterRequest registerRequest = new RegisterRequest(this.getUserName(), this.getUserEmail(), this.getUserPassword(), this.getUserAvatar());
//            final LoadingDialog requestDialog = new LoadingDialog();
//            requestDialog.showDialog(this, this.getResources().getString(R.string.form_server_registering_message), this.REGISTERING_TIMEOUT, registerRequest); // updateList waiting dialog and show
//            registerRequest.setOnRequestResultListener(new RequestResultListener() {
//                @Override
//                public void onRequestResult(int resultCode) {
//                    if (resultCode == Request.RESULT_OK) {
//                        requestDialog.dismissDailog();
//                        Snackbar.make(RegisterActivity.this.getWindow().getDecorView(), registerRequest.getMessage().toLowerCase(), Snackbar.LENGTH_LONG).setCallback(new Snackbar.Callback() { // server feedback message
//                            @Override
//                            public void onDismissed(Snackbar snackbar, int event) {
//                                super.onDismissed(snackbar, event);
//                                RegisterActivity.this.finish(); // then go back to login
//                            }
//                        }).show();
//                    } else {
//                        requestDialog.prepareErrorMessage(RegisterActivity.this.getResources().getString(R.string.form_server_error_message));
//                    }
//                }
//            });
//            registerRequest.execute();
        }
    }

    // Go back to Log in
    private void finishRegisterActivity() {
        this.finish();
    }

    /* Terms and Conditions Dialog */
    private void setupTermsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle(R.string.terms_and_conditons_dialog_title)
                .setMessage(R.string.terms_and_conditons_dialog_content)
                .setPositiveButton(R.string.terms_and_conditons_dialog_agree, this)
                .setNegativeButton(R.string.terms_and_conditons_dialog_reject, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        this.termsDialog = builder.create();
    }

    private String getName() {
        return this.nameField.getText().toString();
    }

    private String getEmail() {
        return this.emailField.getText().toString();
    }

    private String getPass() {
        return this.passField.getText().toString();
    }

    private boolean isTermsChecked() {
        return this.termsCheck.isChecked();
    }

}
