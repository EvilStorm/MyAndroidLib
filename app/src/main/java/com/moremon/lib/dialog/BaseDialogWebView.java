package com.moremon.lib.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.moremon.lib.utils.LogUtil;


public class BaseDialogWebView extends WebView {

    private Context context;
    public BaseDialogWebView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BaseDialogWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public BaseDialogWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public boolean onBackpressed(){
        if(canGoBack()){
            goBack();
            return true;
        }
        return false;
    }

    private void init(){
        getSettings().setJavaScriptEnabled(true);
        getSettings().setLoadWithOverviewMode(true);
        setWebChromeClient(new CustomChromeClient());
        setWebViewClient(new CustomWebViewClient(context));

    }

    private class CustomChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                    .setMessage(message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create();

            dialog.show();
            result.confirm();
            return true;
        }
    }

    private class CustomWebViewClient extends WebViewClient {

        private Context context;
        private CustomWebViewClient(Context context) {
            this.context = context;
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtil.e("shouldOverrideUrlLoading : " + url);
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            LogUtil.e("On Start Page Url : " + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            LogUtil.e("onPageFinished : " + url);
        }
    }
}
