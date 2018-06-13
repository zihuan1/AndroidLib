package com.qrcode.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qrcode.app.zxing.MipcaActivityCapture;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void erweima(View view) {
        startActivity(new Intent(this, MipcaActivityCapture.class));
    }
}
