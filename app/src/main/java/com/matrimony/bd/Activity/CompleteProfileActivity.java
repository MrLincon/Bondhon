package com.matrimony.bd.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.matrimony.bd.R;

public class CompleteProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}