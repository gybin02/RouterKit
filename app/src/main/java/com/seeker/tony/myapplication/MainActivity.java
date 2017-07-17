package com.seeker.tony.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.meiyou.jet.annotation.JFindViewOnClick;
import com.meiyou.jet.process.Jet;
import com.meiyou.router.Router;
import com.meiyou.router.data.RouterTable;

import java.util.HashMap;

//@JUri("")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

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
    Router route = Router.getInstance();

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

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_findView:
                uri = "meiyou:///home?param=\"hello\"";
                route.run(uri);
                break;
            case R.id.btn_findview_onclick:
                uri = "meiyou:///home/action?param=1&test=true";
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

    //    @Test("")
    private void test() {
        HashMap map = RouterTable.map;
        Log.w(TAG, "test: " + map.size());
//        HelloChina helloChina = new HelloChina();
    }

}
