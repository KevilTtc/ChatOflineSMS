package com.example.demochatsms16.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.example.demochatsms16.database.DataBaseMsg;
import com.example.demochatsms16.model.ObDataMess;

import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class SendSmsReceiver extends BroadcastReceiver {
    public void onReceive(@Nullable Context context, @Nullable Intent intent) {

        Bundle extras = intent != null ? intent.getExtras() : null;
        if (extras != null) {
            Object object = extras.get("pdus");

            Object[] sms = (Object[]) object;
            int i = 0;

            for (int indext = sms.length; i < indext; ++i) {
                String formart = extras.getString("format");
                SmsMessage smsMessage;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    object = sms[i];
                    smsMessage = SmsMessage.createFromPdu((byte[]) object, formart);
                } else {
                    object = sms[i];
                    smsMessage = SmsMessage.createFromPdu((byte[]) object);
                }

                String phonenumber = smsMessage.getOriginatingAddress();
                String messageText = smsMessage.getMessageBody().toString();
                DataBaseMsg databse = new DataBaseMsg(context);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                ObDataMess obDataMess = new ObDataMess(0, phonenumber, messageText, formatter.format(date), 0);
                databse.insertSMG(obDataMess);
                if (context != null) {
                    context.sendBroadcast((new Intent("SMS_RECEIVED_ACTION")).putExtra("incomingSms", messageText));
                }
            }
        }

    }
}
