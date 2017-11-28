package com.pheasant.shutterapp.features.authentication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.network.request.RegisterRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.shared.views.RequestDialog;
import com.pheasant.shutterapp.utils.IntentKey;
import com.pheasant.shutterapp.utils.Util;

public class RegisterActivity extends AppCompatActivity {

    private final int REGISTERING_TIMEOUT = 5000;

    private int avatarID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_register);
        // Layout
        Util.setupFont(this.getApplicationContext(), this.getWindow());
        Util.removeInputDirectFocus(this.getWindow());
        this.getPassedUserData();
        this.prepareAvatarPicker();
        this.prepareTermsAndConditionsLinkListener();
        this.prepareSignUpListener();
        this.prepareGoBackLinkListener();
    }

    /* GET USER DATA (get passed user email and/or password) */
    private void getPassedUserData() {
        final Bundle bundle = this.getIntent().getExtras();
        final String userEmail = bundle.getString(IntentKey.USER_EMAIL);
        final String userPassword = bundle.getString(IntentKey.USER_PASSWORD);
        this.setUserEmail(userEmail);
        this.setUserPassword(userPassword);
    }

    /* ====================================== LISTENERS ========================================= */

    /* PICKER (set listener to both icon and button) */
    private void preparePickListener(final AlertDialog.Builder builder) {
        RelativeLayout pickView = (RelativeLayout) findViewById(R.id.register_avatar);
        RelativeLayout pickButton = (RelativeLayout) findViewById(R.id.register_picker);
        View.OnClickListener pickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.show();
            }
        };
        pickView.setOnClickListener(pickListener);
        pickButton.setOnClickListener(pickListener);
    }

    /* TERMS AND CONDITIONS */
    private void prepareTermsAndConditionsLinkListener() {
        final CheckBox termsAndConditionsCheckBox = (CheckBox) this.findViewById(R.id.register_terms_and_conditions);
        termsAndConditionsCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                termsAndConditionsCheckBox.setChecked(false); // uncheck until terms and conditions gonna be read
                RegisterActivity.this.prepareTermsAndConditionsDialog(termsAndConditionsCheckBox);
            }
        });
    }

    /* SIGN UP */
    private void prepareSignUpListener() {
        Button signUpButton = (Button) this.findViewById(R.id.register_sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.makeSignUpAction();
            }
        });
    }

    /* GO BACK */
    private void prepareGoBackLinkListener() {
        TextView goBackLink = (TextView) this.findViewById(R.id.register_go_back_link);
        goBackLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.makeGoBackAction();
            }
        });
    }

    /* ======================================= ACTIONS ========================================== */

    /* SIGN UP (register request) */
    private void makeSignUpAction() {
        if (Util.isInternetConnection(this, this.getWindow().getDecorView()) // check internet connection
                && FormChecker.checkRegisterData(this.getWindow().getDecorView(),
                    this.getUserName(), this.getUserEmail(), this.getUserPassword(), this.getUserAvatar(), this.isTermsAndConditionsChecked())) {  // check form input data
            final RegisterRequest registerRequest = new RegisterRequest(this.getUserName(), this.getUserEmail(), this.getUserPassword(), this.getUserAvatar());
            final RequestDialog requestDialog = new RequestDialog();
            requestDialog.showDialog(this, this.getResources().getString(R.string.form_server_registering_message), this.REGISTERING_TIMEOUT, registerRequest); // updateList waiting dialog and show
            registerRequest.setOnRequestResultListener(new RequestResultListener() {
                @Override
                public void onResult(int resultCode) {
                    if (resultCode == Request.RESULT_OK) {
                        requestDialog.dismissDailog();
                        Snackbar.make(RegisterActivity.this.getWindow().getDecorView(), registerRequest.getMessage().toLowerCase(), Snackbar.LENGTH_LONG).setCallback(new Snackbar.Callback() { // server feedback message
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                super.onDismissed(snackbar, event);
                                RegisterActivity.this.finish(); // then go back to login
                            }
                        }).show();
                    } else {
                        requestDialog.prepareErrorMessage(RegisterActivity.this.getResources().getString(R.string.form_server_error_message));
                    }
                }
            });
            registerRequest.execute();
        }
    }

    /* GO BACK */
    private void makeGoBackAction() {
        this.finish();
    }

    /* ======================================= DIALOGS ========================================== */

    /* PICKER (show list of existing avatars to pick) */
    private void prepareAvatarPicker() {
        final String[] avatarsList = this.getResources().getStringArray(R.array.register_avatar_list);
        final TypedArray avatarsIcons = this.getResources().obtainTypedArray(R.array.avatars);
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setSingleChoiceItems(avatarsList, -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RegisterActivity.this.avatarID = which;
                        RegisterActivity.this.setAvatarIcon(avatarsIcons.getResourceId(which + 1, 0));
                        RegisterActivity.this.setPickButtonText(avatarsList[which]);
                        dialog.dismiss();
                    }
                }).setTitle(R.string.register_picker).create();
        this.preparePickListener(builder);
    }

    /* TERMS AND CONDITIONS (show application terms & conditions) */
    private void prepareTermsAndConditionsDialog(final CheckBox checkBox) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle(R.string.terms_and_conditons_dialog_title)
                .setMessage(R.string.terms_and_conditons_dialog_content)
                .setPositiveButton(R.string.terms_and_conditons_dialog_agree, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        checkBox.setChecked(true);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.terms_and_conditons_dialog_reject, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog demoDialog = builder.create();
        demoDialog.show();
    }

    /* ================================== SETTERS & GETTERS ===================================== */

    /* SETTERS */
    private void setAvatarIcon(final int resID) {
        ((ImageView) RegisterActivity.this.findViewById(R.id.register_avatar_icon)).setImageResource(resID);
    }

    private void setPickButtonText(final String text) {
        ((TextView) RegisterActivity.this.findViewById(R.id.register_picker_title)).setText(text);
    }

    private void setUserEmail(final String email) {
        ((EditText) RegisterActivity.this.findViewById(R.id.register_email_input)).setText(email);
    }

    private void setUserPassword(final String password) {
        ((EditText) RegisterActivity.this.findViewById(R.id.register_password_input)).setText(password);
    }

    /* GETTERS */
    private String getUserName() {
        return ((EditText) RegisterActivity.this.findViewById(R.id.register_name_input)).getText().toString();
    }

    private String getUserEmail() {
        return ((EditText) RegisterActivity.this.findViewById(R.id.register_email_input)).getText().toString();
    }

    private String getUserPassword() {
        return ((EditText) RegisterActivity.this.findViewById(R.id.register_password_input)).getText().toString();
    }

    private int getUserAvatar() {
        return this.avatarID;
    }

    private boolean isTermsAndConditionsChecked() {
        return ((CheckBox) this.findViewById(R.id.register_terms_and_conditions)).isChecked();
    }
}
