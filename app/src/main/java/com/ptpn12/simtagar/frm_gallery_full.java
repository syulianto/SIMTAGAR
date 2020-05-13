package com.ptpn12.simtagar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

public class frm_gallery_full extends AppCompatActivity implements View.OnTouchListener {

    private ImageView imageView;
    private Bitmap bmp;


    float scalediff;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private float oldDist = 1f;
    private float d = 0f;
    private float newRot = 0f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_gallery_full);

        // get intent data
        Intent i = getIntent();

        // Selected image id
        int position = i.getExtras().getInt("id");
        String afp = i.getExtras().getString("afp");
        //Log.e("FULL", afp);
        //Gallery_ImageAdapter imageAdapter = new Gallery_ImageAdapter();
        /*ImageView imageView = (ImageView) findViewById(R.id.gallery_full_image_view);*/
        imageView = (ImageView) findViewById(R.id.gallery_full_image_view);

        //imageView.setScaleType(ImageView.ScaleType.MATRIX);
        imageView.setOnTouchListener(this);


        //mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        //Log.e("FULL", "MAU DECODE");
        File file = new File(afp);
        bmp = sws_decodeFile(file);
        //BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inJustDecodeBounds = false;
        //options.inPreferredConfig = Config.RGB_565;
        //options.inDither = true;
        //Bitmap bmp = BitmapFactory.decodeFile(afp,options);
        // Set the decoded bitmap into ImageView
        //Log.e("FULL", "SELESAI DECODE Di Frm Gallery Full");
        imageView.setImageBitmap(bmp);

        //Log.e("FULL", "ENDING");

    }

    private Bitmap sws_decodeFile(File f) {
        Bitmap b = null;
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            FileInputStream fis = new FileInputStream(f);
            try {
                BitmapFactory.decodeStream(fis, null, o);
            } catch (OutOfMemoryError e) {
                fis.close();
                finish();
            } finally {
                fis.close();
            }

            // In Samsung Galaxy S3, typically max memory is 64mb
            // Camera max resolution is 3264 x 2448, times 4 to get Bitmap memory of 30.5mb for one bitmap
            // If we use scale of 2, resolution will be halved, 1632 x 1224 and x 4 to get Bitmap memory of 7.62mb
            // We try use 25% memory which equals to 16mb maximum for one bitmap
            long maxMemory = Runtime.getRuntime().maxMemory();
            int maxMemoryForImage = (int) (maxMemory / 100 * 25);

            // Refer to
            // http://developer.android.com/training/displaying-bitmaps/cache-bitmap.html
            // A full screen GridView filled with images on a device with
            // 800x480 resolution would use around 1.5MB (800*480*4 bytes)
            // When bitmap option's inSampleSize doubled, pixel height and
            // weight both reduce in half
            int scale = 1;
            while ((o.outWidth / scale) * (o.outHeight / scale) * 4 > maxMemoryForImage)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            try {
                b = BitmapFactory.decodeStream(fis, null, o2);

            } catch (OutOfMemoryError e) {
                fis.close();
                finish();
            } finally {
                fis.close();
            }
        }
        catch (IOException e) {}
        return b;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bmp = null;
        //super.onDestroy();
        //Runtime.getRuntime().gc();
        //System.gc();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bmp = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bmp = null;
    }

    int startwidth;
    int startheight;
    float dx = 0, dy = 0, x = 0, y = 0;
    float angle = 0;

    @SuppressLint("NewApi") @Override
    public boolean onTouch(View arg0, MotionEvent arg1) {
        //final ImageView view = (ImageView) v;

        ((BitmapDrawable) imageView.getDrawable()).setAntiAlias(true);
        LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) arg0.getLayoutParams();

        switch (arg1.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                startwidth = lParams.width;
                startheight = lParams.height;
                dx = arg1.getRawX() - lParams.leftMargin + startwidth /2;
                dy = arg1.getRawY() - lParams.topMargin + startheight/2;
                mode = DRAG;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(arg1);
                if (oldDist > 10f) {
                    mode = ZOOM;
                }

                d = rotation(arg1);

                break;
            case MotionEvent.ACTION_UP:

                break;

            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;

                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {

                    x = arg1.getRawX();
                    y = arg1.getRawY();

                    int orglm = lParams.leftMargin;
                    int orgtm = lParams.topMargin;

                    lParams.leftMargin = (int) (x - dx);
                    lParams.topMargin = (int) (y - dy);

                    lParams.rightMargin = (int)(lParams.rightMargin + (orglm- lParams.leftMargin) );
                    lParams.bottomMargin = (int)(lParams.bottomMargin + (orgtm-lParams.topMargin));


                    arg0.setLayoutParams(lParams);

                } else if (mode == ZOOM) {

                    if (arg1.getPointerCount() == 2) {

                        //newRot = rotation(arg1);
                        //float r = newRot - d;
                        //angle = r;

                        x = arg1.getRawX();
                        y = arg1.getRawY();

                        float newDist = spacing(arg1);
                        if (newDist > 10f) {
                            float scale = newDist / oldDist * arg0.getScaleX();
                            if (scale > 0.6) {
                                scalediff = scale;
                                arg0.setScaleX(scale);
                                arg0.setScaleY(scale);

                            }
                        }

                        arg0.animate().rotationBy(angle).setDuration(0).setInterpolator(new LinearInterpolator()).start();

                        x = arg1.getRawX();
                        y = arg1.getRawY();

                        int orglm = lParams.leftMargin;
                        int orgtm = lParams.topMargin;

                        lParams.leftMargin = (int) ((x - dx) + scalediff);
                        lParams.topMargin = (int) ((y - dy) + scalediff);

                        lParams.rightMargin = (int)(lParams.rightMargin + (orglm - lParams.leftMargin) );
                        lParams.bottomMargin = (int)(lParams.bottomMargin + (orgtm - lParams.topMargin));


                        arg0.setLayoutParams(lParams);


                    }
                }
                break;
        }

        return true;
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

}
