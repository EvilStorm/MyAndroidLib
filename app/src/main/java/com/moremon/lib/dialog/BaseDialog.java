package com.moremon.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moremon.lib.R;
import com.moremon.lib.utils.LogUtil;
import com.moremon.lib.utils.StringUtil;


public class BaseDialog extends Dialog implements View.OnClickListener{

    private final int DESC_TYPE_TEXT = 0;
    private final int DESC_TYPE_HTTP = 1;
    private final int DESC_TYPE_HTML = 2;
    private final int DESC_TYPE_IMAGES = 3;

    private int descript_type = DESC_TYPE_TEXT;

    private Builder builder;

    private BaseDialog(Builder builder) {
        super(builder.context);
        this.builder = builder;

        setLayout();
    }

    @Override
    public void onBackPressed() {

        if(descript_type != DESC_TYPE_TEXT){
            BaseDialogWebView webView = (BaseDialogWebView)findViewById(R.id.wb_descript);
            webView.onBackpressed();
            return;
        }

        super.onBackPressed();
    }

    private void setLayout(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.base_dialog);

        setCancelable(builder.isCancelAble);
        setCanceledOnTouchOutside(builder.isCancelAbleOutSizeTouch);

        setButtons();

        if(StringUtil.isNotNull(builder.title)){
            TextView tv_title = (TextView)findViewById(R.id.tv_title);
            tv_title.setGravity(builder.titleGravity);
            tv_title.setText(builder.title);
            if(builder.titleFontSize != -1){
                tv_title.setTextSize(builder.titleFontSize);
            }
        } else {
            findViewById(R.id.lay_title).setVisibility(View.GONE);
        }

        if(StringUtil.isNotNull(builder.descript)){
            setDecript(builder.descript);
        }

        setDialogSize();

    }

    private void setDialogSize(){
        if(descript_type == DESC_TYPE_HTTP || descript_type == DESC_TYPE_IMAGES){
            Display display = ((WindowManager)builder.context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            Point p = new Point();
            display.getSize(p);
            int width = (int) (p.x * builder.widthRate); //Display 사이즈의 40%
            int height = (int) (p.y * builder.heightRate); //Display 사이즈의 60%
            getWindow().getAttributes().width = width;
            getWindow().getAttributes().height = height;
        }
    }

    private void setPager() {
        ViewPager pager = findViewById(R.id.pager);
        pager.setVisibility(View.VISIBLE);
        pager.setAdapter(new ImagePagerAdater(getContext()));

    }
    private class ImagePagerAdater extends PagerAdapter {
        Context context;
        LayoutInflater mLayoutInflater;

        public ImagePagerAdater(Context context) {
            this.context = context;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {

            return builder.images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            ImageView imagV = (ImageView) mLayoutInflater.inflate(R.layout.row_pager, container, false);

            Glide.with(context)
                    .load(builder.images[position])
                    .into(imagV);

            imagV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(builder.linkUrl[position]));
                    context.startActivity(browserIntent);
                }
            });
            container.addView(imagV);

            return imagV;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }

    private void setDecript(String desc) {
        if (builder.images != null) {
            descript_type = DESC_TYPE_IMAGES;
            setPager();
        }else if(builder.descript.startsWith("<html>")){
            descript_type = DESC_TYPE_HTML;
            setWebView().loadData(desc, "text/html; charset=UTF-8", null);
        }else if(builder.descript.startsWith("http")) {
            descript_type = DESC_TYPE_HTTP;
            setWebView().loadUrl(builder.descript);
        }else{
            descript_type = DESC_TYPE_TEXT;
            TextView tv_descript = (TextView) findViewById(R.id.tv_descript);
            tv_descript.setVisibility(View.VISIBLE);
            tv_descript.setGravity(builder.descriptGravity);
            tv_descript.setText(builder.descript);
            tv_descript.setMovementMethod(new ScrollingMovementMethod());
            if(builder.descriptFontSize != -1){
                tv_descript.setTextSize(builder.descriptFontSize);
            }
        }

        if(builder.showCheckBox) {
            View checkbox = findViewById(R.id.lay_checkbox);
            checkbox.setVisibility(View.VISIBLE);
            TextView chxText = findViewById(R.id.tv_chx_text);
            if(StringUtil.isNotNull(builder.checkBoxText)) {
                chxText.setText(builder.checkBoxText);
            }
        }
    }

    private BaseDialogWebView setWebView(){
        LinearLayout lay_webview = (LinearLayout)findViewById(R.id.lay_webview);
        lay_webview.setVisibility(View.VISIBLE);
        BaseDialogWebView wb = (BaseDialogWebView)findViewById(R.id.wb_descript);
        wb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wb.setWebViewClient(new WebViewClient());
        return wb;
    }

    private void setButtons(){

        TextView[] btn = new TextView[3];

        btn[0] = (TextView)findViewById(R.id.btn_first);
        btn[1] = (TextView)findViewById(R.id.btn_second);
        btn[2] = (TextView)findViewById(R.id.btn_third);

//        findViewById(R.id.btn_first).setOnClickListener(this);
//        findViewById(R.id.btn_second).setOnClickListener(this);
//        findViewById(R.id.btn_third).setOnClickListener(this);

        int size = builder.buttonText.length;

        for (int i=0; i<size; i++){
            TextView item = btn[i];
            item.setText(builder.buttonText[i]);
            if(builder.buttonTextColor != null){
                item.setTextColor(builder.buttonTextColor);
            }
            if(builder.buttonBackGroundColor != null){
                item.setBackground(builder.buttonBackGroundColor);
            }
            if(builder.buttonTextSize > -1){
                item.setTextSize(builder.buttonTextSize);
            }

            btn[i].setOnClickListener(this);
            btn[i].setVisibility(View.VISIBLE);
        }

        if(builder.buttonTextColorArray != null) {
            for (int i=0; i<builder.buttonTextColorArray.length; i++) {
                btn[i].setTextColor(builder.buttonTextColorArray[i]);
            }
        }
        if(builder.buttonBackGroundColorArray != null) {
            for (int i=0; i<builder.buttonBackGroundColorArray.length; i++) {
                btn[i].setBackground(builder.buttonBackGroundColorArray[i]);
            }
        }

        if(builder.buttonText.length == 2) {
            if(builder.buttonBackGroundColor == null && builder.buttonBackGroundColorArray == null) {
                btn[0].setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                btn[0].setBackgroundResource(R.drawable.sel_base_dialog_button_cancel);
            }
        }

    }

    @Override
    public void onClick(View v) {
        builder.listener.onClick(this, Integer.parseInt((String)v.getTag()));
        View checkbox = findViewById(R.id.lay_checkbox);
        if(checkbox.getVisibility() == View.VISIBLE) {
            CheckBox cbx = findViewById(R.id.checkbox);
            builder.checkedListener.checked(cbx.isChecked());
        }
    }

    public static class Builder{
        private Context context;
        private boolean isCancelAble = true;
        private boolean isCancelAbleOutSizeTouch = true;

        private float widthRate = 0.4f;
        private float heightRate= 0.5f;
        private String title;
        private float titleFontSize = -1;
        private String descript;
        private float descriptFontSize = -1;
        private String[] buttonText;

        private boolean showCheckBox;
        private String checkBoxText;

        private String[] images;
        private String[] linkUrl;

        private StateListDrawable buttonBackGroundColor;
        private StateListDrawable[] buttonBackGroundColorArray;
        private ColorStateList buttonTextColor;
        private ColorStateList[] buttonTextColorArray;

        private int buttonTextSize = -1;
        private int titleGravity = Gravity.LEFT;
        private int descriptGravity = Gravity.LEFT;

        private DialogButtonClickListener listener = new DialogButtonClickListener() {
            @Override
            public void onClick(Dialog dialog, int pos) {
            }
        };

        private DialogCheckedListener checkedListener = new DialogCheckedListener() {
            @Override
            public void checked(boolean isChecked) {
            }
        };

        private DialogLinkClickedListener linkClickedListener = new DialogLinkClickedListener() {
            @Override
            public void loadLink(String url) {
            }
        };

        public BaseDialog build() {
            return new BaseDialog(this);
        }

        public Builder(Context context){
            this.context = context;
        }

        public Builder setListener(DialogButtonClickListener listener){
            this.listener = listener;
            return this;
        }
        public Builder setCheckStateChangeListener(DialogCheckedListener listener){
            this.checkedListener = listener;
            return this;
        }
        public Builder setLinkClickListener(DialogLinkClickedListener listener){
            this.linkClickedListener = listener;
            return this;
        }

        public Builder(Context context, float widthRate, float heightRate){
            this.context = context;
            this.widthRate = widthRate;
            this.heightRate = heightRate;
        }

        public Builder setLayoutSize(float widthRate, float heightRate){
            this.widthRate = widthRate;
            this.heightRate = heightRate;
            return this;
        }

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }
        public Builder setTitleFontSize(float size){
            this.titleFontSize = size;
            return this;
        }
        public Builder setTitle(String title, float fontSize){
            this.title = title;
            this.titleFontSize = fontSize;
            return this;
        }

        public Builder setDescript(String descript){
            this.descript = descript;
            return this;
        }
        public Builder setDescriptFontSize(float size){
            this.descriptFontSize = size;
            return this;
        }
        public Builder setDescript(String descript, float fontSize){
            this.descript = descript;
            this.descriptFontSize = fontSize;
            return this;
        }

        public Builder setTitleGravity(int gravity){
            this.titleGravity = gravity;
            return this;
        }
        public Builder setDescriptGravity(int gravity){
            this.descriptGravity= gravity;
            return this;
        }
        public Builder setGravity(int gravity){
            this.titleGravity = gravity;
            this.descriptGravity= gravity;
            return this;
        }
        public Builder setButtonText(String... text){
            this.buttonText = text;
            return this;
        }
        public Builder setButtonBackGroundColor(StateListDrawable color){
            this.buttonBackGroundColor = color;
            return this;
        }
        public Builder setButtonTextColor(ColorStateList color){
            this.buttonTextColor = color;
            return this;
        }
        public Builder setButtonTextSize(int size){
            this.buttonTextSize = size;
            return this;
        }
        public Builder setButtonBackGroundColor(StateListDrawable[] color) {
            this.buttonBackGroundColorArray = color;
            return this;
        }
        public Builder setButtonTextColor(ColorStateList[] color){
            this.buttonTextColorArray = color;
            return this;
        }


        public Builder setButton(StateListDrawable backgroundColor, ColorStateList textColor, int textSize, String... text){
            this.buttonBackGroundColor = backgroundColor;
            this.buttonTextColor = textColor;
            this.buttonTextSize = textSize;
            this.buttonText = text;
            return this;
        }

        public Builder setCancelable(boolean flag){
            isCancelAble = flag;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean flag){
            isCancelAbleOutSizeTouch = flag;
            return this;
        }

        public Builder showCheckBox(boolean flag) {
            showCheckBox = flag;
            return this;
        }
        public Builder setCheckboxText(String txt) {
            checkBoxText = txt;
            return this;
        }

        public Builder setImages(String[] images, String[] linkUrls){
            this.images = images;
            this.linkUrl = linkUrls;
            return this;
        }


    }


}
