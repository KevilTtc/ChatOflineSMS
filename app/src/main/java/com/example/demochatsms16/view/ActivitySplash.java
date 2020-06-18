package com.example.demochatsms16.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.demochatsms16.R;


public class ActivitySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initPermission();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                        this, Manifest.permission.READ_SMS) ==
                        PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                this, Manifest.permission.SEND_SMS) ==
                PackageManager.PERMISSION_GRANTED) {

            Intent i = new Intent(ActivitySplash.this , MainView.class);
            startActivity(i);


        } else {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.SEND_SMS}, 1000);
            Intent i = new Intent(ActivitySplash.this , MainView.class);
            startActivity(i);

        }
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000){
            Intent i = new Intent(ActivitySplash.this , MainView.class);
            startActivity(i);
            finish();
        }

    }
}
