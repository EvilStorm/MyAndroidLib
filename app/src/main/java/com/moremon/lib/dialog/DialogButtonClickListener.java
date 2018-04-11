package com.moremon.lib.dialog;

import android.app.Dialog;

/**
 * Dialog Button Click Listener
 * Created by evilstorm on 2017. 8. 9..
 */

public interface DialogButtonClickListener {
    public static final int FIRST_BUTTON = 1;
    public static final int SECOND_BUTTON = 2;
    public static final int THIRD_BUTTON = 3;

    void onClick(Dialog dialog, int pos);

}
