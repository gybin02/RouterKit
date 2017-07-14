package com.seeker.tony.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.meiyou.annotation.Test;
import com.meiyou.jet.annotation.JFindViewOnClick;
import com.meiyou.jet.process.Jet;
import com.seeker.tony.myapplication.route.Route;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @JFindViewOnClick(R.id.btn_findView)
    Button btnHello;

    @JFindViewOnClick(R.id.btn_findview_onclick)
    Button btnWorld;

    String temp = "";

    @JFindViewOnClick(R.id.btn_implement)
    Button btn_implement;

    @JFindViewOnClick(R.id.btn_log)
    Button btn_log;
    String uri = "";
    Route route = Route.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Jet.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Test("")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Test("")
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_findView:
                uri = "meiyou:///home";
                route.run(uri);
                break;
            case R.id.btn_findview_onclick:
                uri = "meiyou:///home/action";
                route.run(uri);
                break;
            case R.id.btn_implement:
                break;
            case R.id.btn_log:
//                testLog(10);
//                testAOP();
                break;
        }

    }

}
