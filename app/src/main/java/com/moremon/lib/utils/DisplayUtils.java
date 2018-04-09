package com.moremon.lib.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.moremon.lib.R;


/**
 * Created by evilstorm on 2017. 12. 15..
 */

public class DisplayUtils {

    private static ProgressDialog mProgressDialog = null;

    public static void showProgressDialog(Activity activity, String message) {
        View focusView = activity.getCurrentFocus();
        if(focusView != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }

        hideProgressDialog();
//        mProgressDialog = new LoadingDialog(new ContextThemeWrapper(activity, android.R.style.Theme_Holo_Light));
        mProgressDialog  = new ProgressDialog(new ContextThemeWrapper(activity, android.R.style.Theme_Holo_Light));
        if(message == null || message.length() <= 0) {
            message = activity.getString(R.string.info_wait_a_min);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public static void showProgressDialog(Activity activity) {
        showProgressDialog(activity, "");
    }

    public static void hideProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                mProgressDialog = null;
            }
        } catch (Exception e) {
            mProgressDialog = null;
        }
    }

}
