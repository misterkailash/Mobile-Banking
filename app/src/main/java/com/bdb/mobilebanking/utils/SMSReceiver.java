package com.bdb.mobilebanking.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

    private static SMSListerner mListener;
    String sms;

    public static void bindListener(SMSListerner listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();
        assert data != null;
        Object[] pdus = (Object[]) data.get("pdus");
        assert pdus != null;
        for (Object pdus1 : pdus) {
            SmsMessage message = SmsMessage.createFromPdu((byte[]) pdus1);
            String sender = message.getDisplayOriginatingAddress();
            boolean from = sender.endsWith("BDB");
            String msgBody = message.getMessageBody();
            sms = msgBody.replaceAll("[^0-9]", "");
            if (from) {
                mListener.messageReceived(sms);
            }
        }
    }
}
