package com.example.demochatsms16.vieư;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ActionMode.Callback;
import android.view.View.OnClickListener;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.demochatsms16.Interface.EventCheckBox;
import com.example.demochatsms16.Interface.EventFloatingButon;
import com.example.demochatsms16.Interface.EventResoultDelete;
import com.example.demochatsms16.R;
import com.example.demochatsms16.adapter.AdapterMainSMG;
import com.example.demochatsms16.viewchat.MsgActivity;
import com.example.demochatsms16.model.ObDataMess;
import com.example.demochatsms16.viewmain.ViewModelMain;
import com.example.demochatsms16.viewmain.EventListMsg;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.ActionBar.DISPLAY_SHOW_CUSTOM;


public final class MainActivity extends AppCompatActivity implements OnClickListener,
        EventListMsg, EventResoultDelete, EventFloatingButon, EventCheckBox {
    private AdapterMainSMG adapterMg;

    // ViewModel viewModel = new ViewModelProvider(ViewModelStoreOwner.class, ViewModelMain.class);
    public ViewModelMain model;
    private LinearLayoutManager linearLayoutManager;
    private ActionMode actionMode;
    private int MY_PERMISSIONS_SEND_SMS;
    private IntentFilter intentFilter;
    private List lisStrin;
    private HashMap hashMap;
    private FloatingActionButton floatingActionButton;

    @NotNull
    public final ViewModelMain getModel() {
        ViewModelMain viewModelMain = this.model;

        return viewModelMain;
    }

    public final void setModel(@NotNull ViewModelMain var1) {
        this.model = var1;
    }


    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.N)
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ActionBar actionBar = this.getSupportActionBar();
        model = new ViewModelProvider(this).get(ViewModelMain.class);
        if (actionBar != null) {
            actionBar.setDisplayOptions(DISPLAY_SHOW_CUSTOM);
        }

        actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setCustomView(R.layout.acition_bar_center);
        }

        // model = new ViewModelProvider(this,new ViewModelMain()).get(ViewModelStoreOwner.class);
        model = new ViewModelProvider(this).get(ViewModelMain.class);
        this.intentFilter = new IntentFilter();

        intentFilter.addAction("SMS_RECEIVED_ACTION");
        BroadcastReceiver broadcastReceiver = this.intentReceiver();

        this.registerReceiver(broadcastReceiver, intentFilter);
        if (ContextCompat.checkSelfPermission((Context) this, "android.permission.RECEIVE_MMS")
                != 0 && !ActivityCompat.shouldShowRequestPermissionRationale((Activity) this,
                "android.permission.RECEIVE_MMS")) {
            ActivityCompat.requestPermissions((Activity) this, new String[]{"android.permission.RECEIVE_SMS"},
                    this.MY_PERMISSIONS_SEND_SMS);
        }

        model.getListMSG((Context) this, (EventListMsg) this);
        this.linearLayoutManager = new LinearLayoutManager((Context) this);
        RecyclerView recyclerViewMain = findViewById(R.id.rccMg);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        floatingActionButton.setOnClickListener(this);
    }

    private void init() {
        floatingActionButton = findViewById(R.id.flAddMg);
    }

    private BroadcastReceiver intentReceiver() {
        return new BroadcastReceiver() {
            @RequiresApi(Build.VERSION_CODES.N)
            public void onReceive(@org.jetbrains.annotations.Nullable Context context,
                                  @org.jetbrains.annotations.Nullable Intent intent) {
                MainActivity.this.getModel().getListMSG((Context) MainActivity.this, (EventListMsg) MainActivity.this);
            }
        };
    }

    @RequiresApi(Build.VERSION_CODES.N)
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1 || requestCode == 2) && resultCode == -1) {
            model.getListMSG((Context) this, (EventListMsg) this);
        }

    }

    public void getlistMsg(@NotNull LiveData listMsg) {
        listMsg.observe((LifecycleOwner) this, (Observer) (new Observer() {

            public void onChanged(Object var1) {
                this.onChanged((List) var1);
            }

            public final void onChanged(List<ObDataMess> list) {
                AdapterMainSMG adapterMg = new AdapterMainSMG(list, getApplication());
                adapterMg.setiClickFloatingButon(MainActivity.this);
                adapterMg.setiCheckBox(MainActivity.this);
                RecyclerView recyclerView = findViewById((R.id.rccMg));
                recyclerView.setAdapter(adapterMg);

            }
        }));
    }

    public void onClick(@NotNull String number) {
        Intent intent = new Intent((Context) this, MsgActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("number", number);
        intent.putExtras(bundle);
        this.startActivityForResult(intent, 1);
    }

    public void resultNumberSMS(@NotNull String number, boolean isCheck) {
        if (isCheck) {
            this.lisStrin.add(number);
        } else {
            this.lisStrin.remove(number);
        }

        if (this.lisStrin.size() == 0) {
            if (actionMode != null) {
                actionMode.finish();
            }
        } else {
            this.actionMode = this.startActionMode((Callback) (new MainActivity.ActionModeCallback()));
        }

        int i = 0;
        int strLine = this.lisStrin.size() - 1;
        if (strLine > 0 && i <= strLine) {
            while (true) {
                Log.e("aa", (String) this.lisStrin.get(i));
                if (i == strLine) {
                    break;
                }

                ++i;
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    public void getResultDelete(int resuilt) {
        if (resuilt == 0) {
            Toast.makeText((Context) this, (CharSequence) "Thanh cong", Toast.LENGTH_LONG).show();
            if (actionMode != null) {
                actionMode.finish();
            }

            ViewModelMain var2 = this.model;

            var2.getListMSG((Context) this, (EventListMsg) this);
        }

    }

    public MainActivity() {
        List list = (List) (new ArrayList());
        this.lisStrin = list;
    }

    @Override
    public void onClick(View v) {
        Integer var2 = v != null ? v.getId() : null;
        int var3 = R.id.flAddMg;
        if (var2 != null) {
            if (var2 == var3) {
                Intent intent = new Intent((Context)this, MsgActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("number", "");
                intent.putExtras(bundle);
                this.startActivityForResult(intent, 2);
            }
        }

    }

    @Override
    public void onClickFloatingButonNewSMS(@NotNull String var1) {
        onClick(var1);
    }

    public final class ActionModeCallback implements Callback {
        public boolean onActionItemClicked(@Nullable ActionMode mode, @Nullable MenuItem item) {
            MainActivity.this.getModel().delete((Context) MainActivity.this,
                    MainActivity.this.lisStrin, (EventResoultDelete) MainActivity.this);
            return false;
        }

        public boolean onCreateActionMode(@Nullable ActionMode mode, @Nullable Menu menu) {
            if (mode != null) {
                MenuInflater menuInflater = mode.getMenuInflater();
                if (menuInflater != null) {
                    menuInflater.inflate(R.menu.menu_toolbar, menu);
                }
            }

            if (mode != null) {
                mode.setTitle((CharSequence) "Delete");
            }

            return true;
        }

        public boolean onPrepareActionMode(@Nullable ActionMode mode, @Nullable Menu menu) {
            return true;
        }

        public void onDestroyActionMode(@Nullable ActionMode mode) {
        }
    }
}
