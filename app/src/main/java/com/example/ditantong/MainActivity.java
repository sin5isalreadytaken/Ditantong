package com.example.ditantong;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

//public class MainActivity extends AppCompatActivity {
    public class MainActivity extends Activity {

    private Bitmap ditantongbmp;
    private Bitmap buxingbmp;
    private Bitmap qixingbmp;
    private Bitmap gongjiaobmp;
    private Bitmap ditiebmp;
    private Bitmap huoqubmp;
    private Bitmap running;
    private Bitmap build;
    private Bitmap schoolbus;
    private Bitmap train;
    private ImageView ditantong;
    private ImageView buxing;
    private ImageView qixing;
    private ImageView gongjiao;
    private ImageView ditie;
    private ImageView huoqu;
    private ImageView waypic;
    private double way = 0.001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        running = BitmapFactory.decodeResource(getResources(), R.drawable.running);
        build = BitmapFactory.decodeResource(getResources(), R.drawable.build);
        schoolbus = BitmapFactory.decodeResource(getResources(), R.drawable.schoolbus);
        train = BitmapFactory.decodeResource(getResources(), R.drawable.train);
        waypic = (ImageView)findViewById(R.id.waypic);
        waypic.setImageBitmap(running);

        ditantong = (ImageView)findViewById(R.id.ditantong);
        ditantongbmp = BitmapFactory.decodeResource(getResources(), R.drawable.ditantong);
        ditantong.setImageBitmap(ditantongbmp);

        buxing = (ImageView)findViewById(R.id.buxing);
        buxingbmp = BitmapFactory.decodeResource(getResources(), R.drawable.buxing);
        buxing.setImageBitmap(buxingbmp);
        buxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                way = 0.001;
                waypic.setImageBitmap(running);
            }
        });

        qixing = (ImageView)findViewById(R.id.qixing);
        qixingbmp = BitmapFactory.decodeResource(getResources(), R.drawable.qixing);
        qixing.setImageBitmap(qixingbmp);
        qixing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                way = 0.00098;
                waypic.setImageBitmap(build);
            }
        });

        gongjiao = (ImageView)findViewById(R.id.gongjiao);
        gongjiaobmp = BitmapFactory.decodeResource(getResources(), R.drawable.gongjiao);
        gongjiao.setImageBitmap(gongjiaobmp);
        gongjiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                way = 0.0009;
                waypic.setImageBitmap(schoolbus);
            }
        });

        ditie = (ImageView)findViewById(R.id.ditie);
        ditiebmp = BitmapFactory.decodeResource(getResources(), R.drawable.ditie);
        ditie.setImageBitmap(ditiebmp);
        ditie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                way = 0.0008;
                waypic.setImageBitmap(train);
            }
        });

        huoqu = (ImageView)findViewById(R.id.huoqu);
        huoqubmp = BitmapFactory.decodeResource(getResources(), R.drawable.huoqu);
        huoqu.setImageBitmap(huoqubmp);
        huoqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, control.class);
                i.putExtra("way", way);
                way = 0.001;
                startActivity(i);
            }
        });
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
}
