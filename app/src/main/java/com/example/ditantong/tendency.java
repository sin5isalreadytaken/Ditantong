package com.example.ditantong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 森梧 on 2016/4/6.
 */
public class tendency extends Activity{
    private String[] date7 = new String[7];
    private String[] point7 = new String[7];
    private String[] x7 = new String[7];
    private String[] date30 = new String[7];
    private String[] point30 = new String[7];
    private String[] x30 = new String[7];
    private Bitmap auctionbmp;
    private ImageView auction;
    private Bitmap bg6bmp;
    private ImageView bg6;
    private TextView all;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tendency);

        bg6 = (ImageView)findViewById(R.id.bg6);
        bg6bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bg6);
        bg6.setImageBitmap(bg6bmp);

        all = (TextView)findViewById(R.id.all);
        Context cv = tendency.this;
        SharedPreferences preferences = getSharedPreferences("cv", Context.MODE_PRIVATE);
        int tempall = preferences.getInt("all", 0);
        all.setText("您已累计贡献" + tempall + "片绿叶");

        auction = (ImageView)findViewById(R.id.auction);
        auctionbmp = BitmapFactory.decodeResource(getResources(), R.drawable.auction);
        auction.setImageBitmap(auctionbmp);
        auction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l = new Intent(tendency.this, auctionPage.class);
                startActivity(l);
            }
        });

        Date day7 = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        SimpleDateFormat cal = new SimpleDateFormat("yy-MM-dd");
        SimpleDateFormat month = new SimpleDateFormat("yy-MM");
        int point = 0, pointMax = 0;
        String temp = "";
        for(int i = 0 ; i < 7; i++){
            Date theday = new Date(day7.getTime() - i * 24 * 60 * 60 *1000);
            temp = cal.format(theday);
            point = preferences.getInt(temp, 0);
            temp = dateFormat.format(theday);
            pointMax = (pointMax > point) ? pointMax : point;
            date7[6 - i] = temp;
            point7[6 - i] = point + "";
        }
        for(point = pointMax; point % 7 != 0; point++);
        x7[0] = "";
        for(int i = 1; i < 7; i ++){
            if(point == 0){
                x7[i] = "";
                x7[1] = "1";
            }
            else
                x7[i] = (point / 7 * i) + "";
        }
        point = 0;
        pointMax = 0;
        date30[6] = month.format(day7);
        point30[6] = preferences.getInt(date30[6], 0) + "";
        point = Integer.parseInt(point30[6]);
        pointMax = point;
        for(int i = 1 ; i < 7; i++){
            day7 = monthBefore(day7);
            temp = month.format(day7);
            point = preferences.getInt(temp, 0);
            pointMax = (pointMax > point) ? pointMax : point;
            date30[6 - i] = temp;
            point30[6 - i] = point + "";
        }
        for(point = pointMax; point % 7 != 0; point++);
        x30[0] = "";
        for(int i = 1; i < 7; i ++){
            if(point == 0){
                x30[i] = "";
                x30[1] = "1";
            }
            else
                x30[i] = (point / 7 * i) + "";
        }

        LinearLayout layout1 = (LinearLayout)findViewById(R.id.charviewd);
        LinearLayout layout2 = (LinearLayout)findViewById(R.id.charviewm);
        layout1.setBackgroundResource(R.drawable.bg4);
        layout2.setBackgroundResource(R.drawable.bg5);

        com.example.ditantong.ChartView lineView1 = new com.example.ditantong.ChartView(this);
        com.example.ditantong.ChartView lineView2 = new com.example.ditantong.ChartView(this);
        lineView1.setInfo(date7, x7, point7, "日走势图");
        lineView2.setInfo(date30, x30, point30, "月走势图");
        layout1.addView(lineView1);
        layout2.addView(lineView2);
    }

    public Date monthBefore(Date day){
        Date newDay = day;
        for(int i = 0; i < 30; i++){
            newDay = new Date(newDay.getTime() - 24 * 60 * 60 *1000);
        }
        return newDay;
    }
}
