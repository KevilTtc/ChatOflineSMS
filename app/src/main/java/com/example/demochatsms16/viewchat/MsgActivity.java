package com.example.demochatsms16.viewchat;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demochatsms16.R;
import com.example.demochatsms16.adapter.AdapterViewChat;
import com.example.demochatsms16.dialog.CommonActivity;
import com.example.demochatsms16.dialog.CommonDialog;
import com.example.demochatsms16.viewchat.viewmodelchat.ViewModelChatSMS;
import com.example.demochatsms16.viewchat.viewmodelchat.ListChat;
import com.example.demochatsms16.database.DataBaseMsg;
import com.example.demochatsms16.model.ObDataMess;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public final class MsgActivity extends AppCompatActivity implements View.OnClickListener, ListChat {
    private DataBaseMsg database = new DataBaseMsg((Context) this);
    private LinearLayoutManager linearLayoutManager;
    private AdapterViewChat adapterSMS;
    @NotNull
    public ViewModelChatSMS model;
    private String number = "";
    private IntentFilter intentFilter;
    private Button btnSend;
    private EditText edtNumberSent;
    private TextView tvNumberSent;
    private EditText edt_msg;
    private RecyclerView recyClerSMSActivity;
    private Toolbar toolbar;
    BroadcastReceiver broadcastReceiver;
    Application activity;


    @NotNull
    public final ViewModelChatSMS getModel() {
        ViewModelChatSMS viewModelChatSMS = this.model;
        return viewModelChatSMS;
    }

    public final void setModel(@NotNull ViewModelChatSMS var1) {
        this.model = var1;
    }

    public boolean onCreateOptionsMenu(@Nullable Menu menu) {
        return true;
    }


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_chat);
        activity = getApplication();
        init();
        // model = new ViewModelProvider.of(this).get(ViewModelChat.class);
        model = new ViewModelProvider(this).get(ViewModelChatSMS.class);
        String stringExtra = this.getIntent().getStringExtra("number");
        this.number = stringExtra;
        this.intentFilter = new IntentFilter();


        intentFilter.addAction("SMS_RECEIVED_ACTION");
        broadcastReceiver = (BroadcastReceiver) this.intentReceiver();
        this.registerReceiver(broadcastReceiver, intentFilter);
        this.checkper();

        if (number == null || number.length() == 0) {

            edtNumberSent.setVisibility(View.VISIBLE);

        } else {
            edtNumberSent.setVisibility(View.GONE);
            tvNumberSent.setVisibility(View.VISIBLE);
            tvNumberSent.setHint("");
            tvNumberSent.setText(number);

        }

        model.getChat(this.number, (Context) this, (ListChat) this);
        this.linearLayoutManager = new LinearLayoutManager((Context) this);
        recyClerSMSActivity.setLayoutManager(linearLayoutManager);

        btnSend.setOnClickListener(this);
    }

    private void init() {
        edtNumberSent = findViewById(R.id.edtNumberSent);
        tvNumberSent = findViewById(R.id.tvNumberSent);
        edt_msg = findViewById(R.id.edt_msg);
        btnSend = findViewById(R.id.btnSentMg);
        recyClerSMSActivity = findViewById(R.id.rccChat);
        toolbar = findViewById(R.id.toolbar);
    }

    public final void checkper() {
        if (ContextCompat.checkSelfPermission((Context) this, Manifest.permission.SEND_SMS) != 0
                && !ActivityCompat.shouldShowRequestPermissionRationale((Activity) this,
                Manifest.permission.SEND_SMS)) {
            ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.SEND_SMS}, 5);
        }

    }

    public void onClick(@Nullable View v) {
        Integer idSend = v != null ? v.getId() : null;
        int id = R.id.btnSentMg;
        if (idSend != null) {
            if (idSend == id) {


                String strNumberSend = edtNumberSent.getText().toString();
                String edtSMG = edt_msg.getText().toString();

                if (!CommonActivity.isNullOrEmpty(strNumberSend) && !CommonActivity.isNullOrEmpty(edtSMG)){
                    edtNumberSent.setVisibility(View.GONE);
                    tvNumberSent.setVisibility(View.VISIBLE);
                    if (strNumberSend != null && strNumberSend.length() != 0) {

                        tvNumberSent.setText(strNumberSend);
                        this.number = tvNumberSent.getText().toString();
                    }

                    this.sendMsg(number, edtSMG);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();

                    String formatDate = formatter.format(date);
                    ObDataMess data = new ObDataMess(0, number, edtSMG, formatDate, 1);
                    database.insertSMG(data);
                    model.getChat(this.number, (Context) this, (ListChat) this);
                    edt_msg.setText("");
                    this.setResult(Activity.RESULT_OK);
                }else if (CommonActivity.isNullOrEmpty(strNumberSend)){
                    // CommonDialog.showConfirmValidate(activity,R.string.validate_number);
                    Toast.makeText(getApplicationContext(),getString(R.string.validate_number), Toast.LENGTH_LONG).show();
                }else if (CommonActivity.isNullOrEmpty(edtSMG)){
                    // CommonDialog.showConfirmValidate(activity,R.string.validate_smg);
                    Toast.makeText(getApplicationContext(),getString(R.string.validate_smg), Toast.LENGTH_LONG).show();
                }

            }
        }

    }


    public final void sendMsg(@NotNull String number, @NotNull String myMsg) {
        String sent = "Message Sent";
        String delivered = "Message delivered";
        PendingIntent sentPI = PendingIntent.getBroadcast((Context) this, 0, new Intent(sent), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast((Context) this, 0, new Intent(delivered), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number, (String) null, myMsg, sentPI, deliveredPI);


    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);

    }

    private BroadcastReceiver intentReceiver() {
        return new BroadcastReceiver() {
            public void onReceive(@Nullable Context context, @Nullable Intent intent) {
                MsgActivity.this.getModel().getChat(MsgActivity.this.number,
                        (Context) MsgActivity.this, (ListChat) MsgActivity.this);
            }
        };
    }

    public void listChat(@NotNull LiveData liveData) {
        if (liveData != null) {
            liveData.observe((LifecycleOwner) this, (Observer) (new Observer() {

                public void onChanged(Object var1) {
                    this.onChanged((List) var1);
                }

                public final void onChanged(List list) {
                    adapterSMS = new AdapterViewChat(list);
                    if (!list.isEmpty()) {
                        tvNumberSent.setText(((ObDataMess) list.get(0)).getNumber());
                    }
                    recyClerSMSActivity = findViewById(R.id.rccChat);
                    recyClerSMSActivity.setAdapter(adapterSMS);
                    recyClerSMSActivity.scrollToPosition(list.size() - 1);
                }
            }));

        }


    }

}