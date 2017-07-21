package com.seeker.tony.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.meiyou.jet.annotation.JFindViewOnClick;
import com.meiyou.jet.process.Jet;
import com.meiyou.router.Router;

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
    @JFindViewOnClick(R.id.btn_init)
    Button btn_init;

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
        route = Router.getInstance();
        switch (id) {
            case R.id.btn_findView:
                uri = "meiyou:///home?param=\"hello\"";
                route.run(uri);
                break;
            case R.id.btn_findview_onclick:
                uri = "meiyou:///action?param=1&test=true";
                route.run(uri);
                break;
            case R.id.btn_implement:
                uri = "meiyou:///moduleb/action";
                route.run(uri);
                break;
            case R.id.btn_log:
                uri = "meiyou:///moduleb";
                route.run(uri);
//                testLog(10);
//                testAOP();
                break;
            case R.id.btn_init:
                temp();
                break;
        }

    }

    private void temp() {
        try {
            Router.getInstance().registerAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    @Test("")
    private void test() {
//        HashMap map = RouterTable.map;
//        Log.w(TAG, "test: " + map.size());
//        URI uri = new URI("");
//        Uri.parse()
//        HelloChina helloChina = new HelloChina();
    }

}
