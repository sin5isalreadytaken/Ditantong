package com.example.ditantong;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 森梧 on 2016/4/6.
 */
public class mapCount extends Activity{
    private Bitmap bg3bmp;
    private Bitmap tendbmp;
    private Bitmap bluebmp;
    private ImageView bg3;
    private ImageView tend;
    private ImageView blue;
    private MapView mMapView;
    private TextView show1;
    private BaiduMap bdMap;
    private Context ma;
    private double Latitude;
    private double Longitude;
    private String ri = "您的今日绿叶总贡献值为";
    private String yue = "您本月的绿叶总贡献值为";
    private String nian = "您本年度的绿叶总贡献值为";
    private String pian = "片";
    private int leaf = 0;
    private GeoPoint A;
    private GeoPoint B;
    private double way = 0.001;
    private double distance = 0;
    private int today;
    private int tomonth;
    private int toyear;
    private int all;
    static double DEF_PI = 3.14159265359; // PI
    static double DEF_2PI= 6.28318530712; // 2*PI
    static double DEF_PI180= 0.01745329252; // PI/180.0
    static double DEF_R =6370693.5; // radius of earth
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mapandcount);
        ma = this;
        Intent c = getIntent();
        Bundle bundle = c.getExtras();
        leaf = bundle.getInt("leaf");
        way = bundle.getDouble("way");
        Context cv = mapCount.this;
        SharedPreferences preferences = getSharedPreferences("cv", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Date toDay = new Date();
        SimpleDateFormat dateFormatd = new SimpleDateFormat("yy-MM-dd");
        SimpleDateFormat dateFormatm = new SimpleDateFormat("yy-MM");
        SimpleDateFormat dateFormaty = new SimpleDateFormat("yy");
        String todayStr = dateFormatd.format(toDay);
        String tomonthStr = dateFormatm.format(toDay);
        String toyearStr = dateFormaty.format(toDay);
        today = preferences.getInt(todayStr, 0) + leaf;
        tomonth = preferences.getInt(tomonthStr, 0) + leaf;
        toyear = preferences.getInt(toyearStr, 0) + leaf;
        editor.putInt(todayStr, today);
        editor.putInt(tomonthStr, tomonth);
        editor.putInt(toyearStr, toyear);
        all = preferences.getInt("all", 0) + leaf;
        editor.putInt("all",all);
        editor.commit();

        bg3 = (ImageView)findViewById(R.id.bg3);
        bg3bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bg3);
        bg3.setImageBitmap(bg3bmp);

        show1 = (TextView)findViewById(R.id.show1);
        show1.setText(ri + today + pian + '\n'+ yue +  tomonth + pian + '\n' + nian + toyear + pian);

        tend = (ImageView)findViewById(R.id.tend);
        tendbmp = BitmapFactory.decodeResource(getResources(), R.drawable.tend);
        tend.setImageBitmap(tendbmp);
        tend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(mapCount.this, tendency.class);
                startActivity(k);
            }
        });

        mMapView = (MapView) findViewById(R.id.bmapview);
        bdMap = mMapView.getMap();
        bdMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        bdMap.setMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(36.0, 103.73)));
        MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(17.5f);
        bdMap.animateMapStatus(u);

        blue = (ImageView)findViewById(R.id.blue);
        bluebmp = BitmapFactory.decodeResource(getResources(), R.drawable.blue);
        blue.setImageBitmap(bluebmp);

        if (!this.isOPen(this)){
            this.openGPS(this);
        }
        final LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

                    try {
                        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if (location != null) {
                            Latitude = location.getLatitude();
                            Longitude = location.getLongitude();
                            A = new GeoPoint(Latitude, Longitude);
                            disCount(location);
                        }
                    } catch (SecurityException e) {
                        Toast.makeText(ma, "请打开GPS定位服务", Toast.LENGTH_SHORT).show();
                    }

                    try {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new LocationListener() {

                            public void onLocationChanged(Location location) {
                                // TODO Auto-generated method stub
                                disCount(location);
                            }

                            public void onProviderDisabled(String provider) {
                                // TODO Auto-generated method stub

                            }

                            public void onProviderEnabled(String provider) {
                                // TODO Auto-generated method stub
                                try {

                                } catch (SecurityException e) {
                                }
                            }

                            public void onStatusChanged(String provider, int status, Bundle extras) {
                                // TODO Auto-generated method stub
                            }

                        });
                    } catch (SecurityException e) {
                        Toast.makeText(ma, "请打开GPS定位服务", Toast.LENGTH_SHORT).show();
                    }
    }

    public void disCount(Location currentLocation){
        if(currentLocation != null){
            StringBuffer sb = new StringBuffer(256);
            Latitude = currentLocation.getLatitude();
            Longitude = currentLocation.getLongitude();

            B = new GeoPoint(Latitude, Longitude);
            bdMap.setMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(Latitude, Longitude)));
            MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(17.5f);
            bdMap.animateMapStatus(u);
            distance += GetShortDistance(A.getLatitudeE6(), A.getLongitudeE6(), B.getLatitudeE6(), B.getLongitudeE6());
            A = B;
            if(distance>1250){
                leaf = (int)(distance*way);
                distance = 0;
                Context cv = mapCount.this;
                SharedPreferences preferences = getSharedPreferences("cv", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                Date toDay = new Date();
                SimpleDateFormat dateFormatd = new SimpleDateFormat("yy-MM-dd");
                SimpleDateFormat dateFormatm = new SimpleDateFormat("yy-MM");
                SimpleDateFormat dateFormaty = new SimpleDateFormat("yy");
                String todayStr = dateFormatd.format(toDay);
                String tomonthStr = dateFormatm.format(toDay);
                String toyearStr = dateFormaty.format(toDay);
                today = preferences.getInt(todayStr, 0) + leaf;
                tomonth = preferences.getInt(tomonthStr, 0) + leaf;
                toyear = preferences.getInt(toyearStr, 0) + leaf;
                editor.putInt(todayStr, today);
                editor.putInt(tomonthStr, tomonth);
                editor.putInt(toyearStr, toyear);
                all = preferences.getInt("all", 0) + leaf;
                editor.putInt("all",all);
                editor.commit();
            }
            Context cv = mapCount.this;
            SharedPreferences preferences = getSharedPreferences("cv", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            Date toDay = new Date();
            SimpleDateFormat dateFormatd = new SimpleDateFormat("yy-MM-dd");
            SimpleDateFormat dateFormatm = new SimpleDateFormat("yy-MM");
            SimpleDateFormat dateFormaty = new SimpleDateFormat("yy");
            String todayStr = dateFormatd.format(toDay);
            String tomonthStr = dateFormatm.format(toDay);
            String toyearStr = dateFormaty.format(toDay);
            today = preferences.getInt(todayStr, 0);
            tomonth = preferences.getInt(tomonthStr, 0);
            toyear = preferences.getInt(toyearStr, 0);
            show1.setText(ri + today + pian + '\n'+ yue +  tomonth + pian + '\n' + nian + toyear + pian);
        }
    }
    public int GetShortDistance(double lon1, double lat1, double lon2, double lat2)
    {
        double ew1, ns1, ew2, ns2;
        double dx, dy, dew;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 经度差
        dew = ew1 - ew2;
        // 若跨东经和西经180 度，进行调整
        if (dew > DEF_PI)
            dew = DEF_2PI - dew;
        else if (dew < -DEF_PI)
            dew = DEF_2PI + dew;
        dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
        dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
        // 勾股定理求斜边长
        distance = Math.sqrt(dx * dx + dy * dy);
        return (int)distance;
    }

    public static final boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }
}
