package com.moremon.lib.utils;

import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

    public static final DecimalFormat MONEY_FORMAT_1 = new DecimalFormat("#,##0");

    public static final SimpleDateFormat DATE_FORAMT_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSsss");
    public static final SimpleDateFormat DATE_FORAMT_2 = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_FORAMT_3 = new SimpleDateFormat("yy.MM.dd");
    public static final SimpleDateFormat DATE_FORAMT_4 = new SimpleDateFormat("a HH:mm");
    public static final SimpleDateFormat DATE_FORAMT_5 = new SimpleDateFormat("dd일 HH:00");
    public static final SimpleDateFormat DATE_FORAMT_6 = new SimpleDateFormat("MM월 dd일");
    public static final SimpleDateFormat DATE_FORAMT_7 = new SimpleDateFormat("yy년 MM월");
    public static final SimpleDateFormat DATE_FORAMT_8 = new SimpleDateFormat("MM/dd HH:mm");


    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }

    public static boolean isNull(String str){
        if(str == null || str.trim().equals("") || str.trim().toLowerCase().equals("none")){
            return true;
        }
        return false;
    }

    public static boolean isNull(TextView view){
        if(view == null){
            return true;
        }
        return isNull(view.getText().toString());
    }

    public static boolean isNull(EditText view){
        if(view == null){
            return true;
        }

        return isNull(view.getText().toString());
    }

    public static boolean isNotNull(String str){
        return !isNull(str);
    }
    public static boolean isNotNull(TextView view){
        return !isNull(view);
    }
    public static boolean isNotNull(EditText view){
        return !isNull(view);
    }

    public static String getString(EditText view){

        if(isNull(view)){
            return null;
        }

        return view.getText().toString().trim();
    }

    public static String getString(TextView view){
        if(isNull(view)){
            return null;
        }
        return view.getText().toString().trim();
    }

    public static int[] getCharactorPosition(String fullStr, String targetStr) {
        int[] result = new int[2];
        result[0] = fullStr.indexOf(targetStr);
        result[1] = result[0] + targetStr.length();

        return result;
    }

    public static boolean isCorrectKorean(String str) {
        String patternStr = "[\\u00C0-\\u00FF\\uAC00-\\uD7A3a-zA-Z0-9\\s?]{1," + str.length() + "}";
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
