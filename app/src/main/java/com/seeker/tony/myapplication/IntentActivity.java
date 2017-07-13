package com.seeker.tony.myapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.meiyou.jet.annotation.JIntent;
import com.meiyou.jet.process.Jet;

import java.io.Serializable;

public class IntentActivity extends AppCompatActivity {
    @JIntent("stringExtra")
    String stringExtra;
    @JIntent("intExtra")
    int intExtra;
    @JIntent("longExtra")
    long longExtra;
    @JIntent("booleanExtra")
    boolean booleanExtra;
    @JIntent("serializable")
    Serializable serializable;
    @JIntent("bundle")
    Bundle bundle;
    @JIntent("stringArrays")
    String[] stringArrays;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        Jet.bind(this);
//        Intent intent = getIntent();
//        String stringExtra = intent.getStringExtra("stringExtra");
//        int intExtra = intent.getIntExtra("intExtra", 0);
//        long longExtra = intent.getLongExtra("longExtra", 0);
//        boolean booleanExtra = intent.getBooleanExtra("booleanExtra", false);
//        Serializable serializable = intent.getSerializableExtra("serializable");
//        Bundle bundle = intent.getBundleExtra("bundle");
//        String[] stringArrays = intent.getStringArrayExtra("stringArray");

        Log.e("intent", "intent test");
//        View content = findViewById(R.id.fl_content);
        BlankFragment blankFragment = BlankFragment.newInstance(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_content, blankFragment);
        transaction.commit();
    }
}
