package com.example.ditantong;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

/**
 * Created by 森梧 on 2016/4/6.
 */
public class auctionPage extends Activity{
    private Bitmap bg7bmp;
    private Bitmap l300bmp;
    private Bitmap arrowbmp;
    private Bitmap bookbmp;
    private Bitmap l1000bmp;
    private Bitmap cardbmp;
    private Bitmap l2000bmp;
    private Bitmap subwaybmp;
    private Bitmap thxbmp;
    private ImageView bg7;
    private ImageView l300;
    private ImageView arrow1;
    private ImageView book;
    private ImageView l1000;
    private ImageView arrow2;
    private ImageView card;
    private ImageView l2000;
    private ImageView arrow3;
    private ImageView subway;
    private ImageView thx;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.auctionpage);

        bg7bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bg7);
        l300bmp = BitmapFactory.decodeResource(getResources(), R.drawable.l300);
        arrowbmp = BitmapFactory.decodeResource(getResources(), R.drawable.arrowto);
        bookbmp = BitmapFactory.decodeResource(getResources(), R.drawable.book);
        l1000bmp = BitmapFactory.decodeResource(getResources(), R.drawable.l1000);
        cardbmp = BitmapFactory.decodeResource(getResources(), R.drawable.card);
        l2000bmp = BitmapFactory.decodeResource(getResources(), R.drawable.l2000);
        subwaybmp = BitmapFactory.decodeResource(getResources(), R.drawable.subway);
        thxbmp = BitmapFactory.decodeResource(getResources(), R.drawable.thx);

        bg7 = (ImageView)findViewById(R.id.bg7);
        l300 = (ImageView)findViewById(R.id.l300);
        arrow1 = (ImageView)findViewById(R.id.arrow1);
        book = (ImageView)findViewById(R.id.book);
        l1000 = (ImageView)findViewById(R.id.l1000);
        arrow2 = (ImageView)findViewById(R.id.arrow2);
        card = (ImageView)findViewById(R.id.card);
        l2000 = (ImageView)findViewById(R.id.l2000);
        arrow3 = (ImageView)findViewById(R.id.arrow3);
        subway = (ImageView)findViewById(R.id.subway);
        thx = (ImageView)findViewById(R.id.thx);

        bg7.setImageBitmap(bg7bmp);
        l300.setImageBitmap(l300bmp);
        arrow1.setImageBitmap(arrowbmp);
        book.setImageBitmap(bookbmp);
        l1000.setImageBitmap(l1000bmp);
        arrow2.setImageBitmap(arrowbmp);
        card.setImageBitmap(cardbmp);
        l2000.setImageBitmap(l2000bmp);
        arrow3.setImageBitmap(arrowbmp);
        subway.setImageBitmap(subwaybmp);
        thx.setImageBitmap(thxbmp);
    }
}
