package com.jet.jet.module_b;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jet.annotation.JUri;

@JUri("/moduleb")
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        
        
    }
}
