package com.bdb.mobilebanking.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.bdb.mobilebanking.MainActivity;
import com.bdb.mobilebanking.R;
import com.bdb.mobilebanking.fragments.MenuFragment;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;

public class ErrorHandler {

    public String onErrorResponse(VolleyError volleyError) {
        String message = null;
        if (volleyError instanceof NetworkError) {
            message = "Device not connected to Internet. Please check your connection!";
        } else if (volleyError instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (volleyError instanceof AuthFailureError) {
            message = "Authentication failure. Please check your connection!";
        } else if (volleyError instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (volleyError instanceof TimeoutError) {
            message = "Server not responding! Please try again later or contact service provider.";
        }
        return message;
    }

    public void successDisplay(final Activity activity) {
        new TTFancyGifDialog.Builder(activity)
                .setTitle("Success!")
                .setMessage("Successfully Printed")
                .setPositiveBtnText("OK")
                .setGifResource(R.drawable.success)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        FragmentManager manager = ((MainActivity) activity).getSupportFragmentManager();
                        for (int i = 0; i < manager.getBackStackEntryCount(); ++i) {
                            manager.popBackStack();
                        }
                        manager.beginTransaction().replace(R.id.content_main, new MenuFragment()).commit();
                    }
                })
                .build();
    }

    public void errorDisplay(Activity activity, String message) {
        final SharedPreferences pref = activity.getSharedPreferences("PREF", Context.MODE_PRIVATE);
        new TTFancyGifDialog.Builder(activity)
                .setTitle("Error!")
                .setMessage(message)
                .setPositiveBtnText("OK")
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        pref.edit().putInt("printer_state", 1).apply();
                    }
                })
                .setGifResource(R.drawable.error)
                .build();
    }
}
