package com.example.ditantong;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

import com.baidu.mapapi.model.inner.GeoPoint;

/**
 * Created by 森梧 on 2016/4/5.
 */
public class control extends Activity{
    private Context ma = null;
    private GeoPoint A;
    private GeoPoint B;
    private double Latitude;
    private double Longitude;
    private int distance = 0;
    private int temp = distance;
    static double DEF_PI = 3.14159265359; // PI
    static double DEF_2PI= 6.28318530712; // 2*PI
    static double DEF_PI180= 0.01745329252; // PI/180.0
    static double DEF_R =6370693.5; // radius of earth
    private double way;
    private Bitmap bg1bmp;
    private Bitmap bg2bmp;
    private Bitmap gobmp;
    private Bitmap pausebmp;
    private Bitmap stopbmp;
    private Bitmap mapcountbmp;
    private ImageView bg1;
    private ImageView bg2;
    private ImageView go;
    private ImageView stop;
    private ImageView mapcount;
    private TextView show;
    private String wayText = "步行";
    private String jin = "今日你已";
    private String mi = "米，已为低碳出行贡献";
    private String pian = "片绿叶";
    private boolean flag = true;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control);
        ma = this;
        Intent c = getIntent();
        Bundle bundle = c.getExtras();
        way = bundle.getDouble("way");
        if(way == 0.00098)
            wayText = "骑行";
        else if(way == 0.0009)
            wayText = "乘坐公交";
        else if(way == 0.0008)
            wayText = "乘坐地铁";
        show = (TextView)findViewById(R.id.show);
        show.setText(jin+wayText+'0'+mi+'0'+pian);

        bg1 = (ImageView)findViewById(R.id.bg1);
        bg1bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bg1);
        bg1.setImageBitmap(bg1bmp);

        bg2 = (ImageView)findViewById(R.id.bg2);
        bg2bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
        bg2.setImageBitmap(bg2bmp);

        go = (ImageView)findViewById(R.id.go);
        pausebmp = BitmapFactory.decodeResource(getResources(), R.drawable.pause);
        gobmp = BitmapFactory.decodeResource(getResources(), R.drawable.go);
        go.setImageBitmap(gobmp);
        if (!this.isOPen(this)){
            this.openGPS(this);
        }
        final LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = !flag;
                if(flag)
                    go.setImageBitmap(gobmp);
                else
                    go.setImageBitmap(pausebmp);
                if(!flag){
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
                        Toast.makeText(ma, "请打开GPS定位", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        stop = (ImageView)findViewById(R.id.stop);
        stopbmp = BitmapFactory.decodeResource(getResources(), R.drawable.stop);
        stop.setImageBitmap(stopbmp);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                distance = 0;
            }
        });

        mapcount = (ImageView)findViewById(R.id.mapcount);
        mapcountbmp = BitmapFactory.decodeResource(getResources(), R.drawable.mapcount);
        mapcount.setImageBitmap(mapcountbmp);
        mapcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                Intent j = new Intent(control.this, mapCount.class);
                j.putExtra("leaf",(int)(temp*way));
                j.putExtra("way",way);
                temp = 0;
                distance = 0;
                startActivity(j);
            }
        });
    }

    public void disCount(Location currentLocation){
        if(flag)
            return;
        if(currentLocation != null){
            StringBuffer sb = new StringBuffer(256);
            Latitude = currentLocation.getLatitude();
            Longitude = currentLocation.getLongitude();

            B = new GeoPoint(Latitude, Longitude);
            distance += GetShortDistance(A.getLatitudeE6(), A.getLongitudeE6(), B.getLatitudeE6(), B.getLongitudeE6());
            temp = distance;
            A = B;
            show.setText(jin+wayText+distance+mi+(int)(distance*way)+pian);
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
