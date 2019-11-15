package com.bdb.mobilebanking.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bdb.mobilebanking.R;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;

import in.aabhasjindal.otptextview.OtpTextView;

public class CoreUtils {

    public boolean isConnected(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public boolean isInternetAvailable() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }

    public void archedProgress(ArchedImageProgressBar progressBar, Resources resources) {
        Bitmap Icon = BitmapFactory.decodeResource(resources, R.drawable.bdbl);
        progressBar.setProgressImage(Icon, 21.0f);
        progressBar.setCircleSize(20.0f);
        progressBar.setArchSize(22.75f);
        progressBar.setArchColor(Color.parseColor("#4cb050"));
        progressBar.setArchLength(210);
        progressBar.setArchStroke(8.85f);
        progressBar.setArchSpeed(5);
    }

    public void loadProgress(ArchedImageProgressBar progressBar, Resources resources) {
        progressBar.setVisibility(View.VISIBLE);
        Bitmap Icon = BitmapFactory.decodeResource(resources, R.drawable.bdbl);
        progressBar.setProgressImage(Icon, 31.0f);
        progressBar.setCircleSize(30.0f);
        progressBar.setArchSize(32.75f);
        progressBar.setArchColor(Color.parseColor("#FFFFFF"));
        progressBar.setArchLength(210);
        progressBar.setArchStroke(8.85f);
        progressBar.setArchSpeed(5);
    }

    public void checkEmptyValues(EditText text, TextView view) {
        if (!text.getText().toString().trim().equals("")) {
            view.setText(null);
        }
    }

    public void checkEmptyValues(OtpTextView text, TextView view) {
        if (!text.getOTP().equals("")) {
            view.setText(null);
        }
    }

    public String replaceChar(String str, char ch, int index) {
        StringBuilder myString = new StringBuilder(str);
        myString.setCharAt(index, ch);
        return myString.toString();
    }
}