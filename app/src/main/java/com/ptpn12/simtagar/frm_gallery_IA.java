package com.ptpn12.simtagar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class frm_gallery_IA extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public String[] mFilePaths;
    public String[] mFileNames;
    public String[] mJenis;


    static class ViewHolder {
        TextView textViewAndroid;
        ImageView imageViewAndroid;
    }

    // Constructor
    public frm_gallery_IA() {}

    public frm_gallery_IA(Context c, String[] fpath, String[] fname, String[] jenis){
        mContext = c;
        mFilePaths = fpath;
        mFileNames = fname;
        mJenis = jenis;
    }

    @Override
    public int getCount() {
        return mFilePaths.length;
    }

    public String getFP(int position) {
        return mFilePaths[position];
    }

    public String getIAJenis(int position) {
        return mJenis[position];
    }

    public String getFileNames(int position) {
        return mFileNames[position];
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //View gridView;
        ViewHolder holder;

        if (convertView == null) {

//			gridView = new View(mContext);

            // get layout from mobile.xml
            convertView = inflater.inflate(R.layout.frm_gallery_mobile, null);
            holder = new ViewHolder();

            // set value into textview
            TextView textView = (TextView) convertView
                    .findViewById(R.id.grid_item_label);


            holder.textViewAndroid = textView;

            // set image based on selected text
            ImageView imageView = (ImageView) convertView
                    .findViewById(R.id.grid_item_image);

            holder.imageViewAndroid = imageView;


            convertView.setTag(holder);



        } else {
            //gridView = (View) convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textViewAndroid.setText(mFileNames[position]);

        String mobile = mFileNames[position];
        Bitmap bmp = null;
        if (mJenis[position].equals("1")) {
            BitmapFactory.Options bmOptions= new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds= true;
            BitmapFactory.decodeFile(mFilePaths[position], bmOptions);
            int photoW= bmOptions.outWidth;
            int photoH= bmOptions.outHeight;
            int w = 640;
            int h = 480;
            if( photoW > photoH ) {
                w = 640;
                h = 480;
            } else {
                w = 480;
                h = 640;
            }



            // Determine how much to scale down the image.

            int scaleFactor= (int) Math.max(1.0, Math.min((double) photoW / (double)w, (double)photoH / (double)h));    //1, 2, 3, 4, 5, 6, ...
            scaleFactor= (int) Math.pow(2.0, Math.floor(Math.log((double) scaleFactor) / Math.log(2.0)));               //1, 2, 4, 8, ...

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds= false;
            bmOptions.inSampleSize= scaleFactor;
            bmOptions.inPurgeable= true;

            do {
                try {
                    //Log.d("tag", "scaleFactor: " + scaleFactor);
                    scaleFactor*= 2;
                    bmOptions.inJustDecodeBounds= false;
                    bmOptions.inSampleSize= scaleFactor;
                    bmOptions.inPurgeable= true;
                    bmp = BitmapFactory.decodeFile(mFilePaths[position], bmOptions);
                    //bmp = BitmapFactory.decodeFile(mFilePaths[position]);
                    holder.imageViewAndroid.setImageBitmap(bmp);
                }
                catch(OutOfMemoryError e) {
                    bmOptions.inSampleSize= scaleFactor;
                    Log.d("tag", "OutOfMemoryError: " + e.toString());
                }
            }
            while(bmp == null && scaleFactor <= 256);
        } else if (mJenis[position].equals("2")) {
            bmp = ThumbnailUtils.createVideoThumbnail(mFilePaths[position], MediaStore.Video.Thumbnails.MICRO_KIND);
            holder.imageViewAndroid.setImageBitmap(bmp);
        }

        return convertView;
    }


}
