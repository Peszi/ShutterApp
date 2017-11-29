package com.pheasant.shutterapp.shutter.ui.features.authentication;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.pheasant.shutterapp.R;

/**
 * Created by Peszi on 2017-04-24.
 */

public class FormChecker { // TODO incorrect form data messages

    /* LOGIN FORM */
    public static boolean checkLoginData(View view, final String email, final String password) {
        if (email.isEmpty() || !email.contains("@")) {
            FormChecker.showMessage(view, R.string.form_email_error_message);
            return false;
        }
        if (password.isEmpty()) {
            FormChecker.showMessage(view, R.string.form_password_error_message);
            return false;
        }
        return true;
    }

    /* REGISTER FORM */
    public static boolean checkRegisterData(View view, final String name, final String email, final String password, final int avatar, final boolean termsAndConditionsCheckbox) {
        if (name.isEmpty()) {
            FormChecker.showMessage(view, R.string.form_name_error_message);
            return false;
        }
        if (email.isEmpty() || !email.contains("@")) {
            FormChecker.showMessage(view, R.string.form_email_error_message);
            return false;
        }
        if (password.length() < 4) {
            FormChecker.showMessage(view, R.string.form_password_error_message);
            return false;
        }
        if (avatar < 0) {
            FormChecker.showMessage(view, R.string.form_avatar_error_message);
            return false;
        }
        if (!termsAndConditionsCheckbox) {
            FormChecker.showMessage(view, R.string.form_terms_and_conditions_error_message);
            return false;
        }
        return true;
    }

    private static void showMessage(View view, int stringId) {
        Snackbar.make(view, stringId, Snackbar.LENGTH_LONG).show();
    }
}
