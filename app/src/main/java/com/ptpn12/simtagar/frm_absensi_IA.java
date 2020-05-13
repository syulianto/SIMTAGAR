package com.ptpn12.simtagar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.String.*;

public class frm_absensi_IA extends BaseAdapter {
    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    frm_absensi_list tempValues=null;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public frm_absensi_IA(Activity a, ArrayList d) {
        /********** Take passed values **********/
        activity = a;
        data=d;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {
        if(data.size()<=0) return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{
        public TextView nama;
        public TextView jab;
        public TextView absenb;
        public TextView absenp;
        public ImageView imgb;
        public ImageView imgp;
    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.list_pekerja, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.nama = (TextView) vi.findViewById(R.id.pekerjal_nama);
            holder.jab =(TextView)vi.findViewById(R.id.pekerjal_jab);
            holder.absenb =(TextView)vi.findViewById(R.id.pekerjal_absenb);
            holder.absenp =(TextView)vi.findViewById(R.id.pekerjal_absenp);
            holder.imgb =(ImageView)vi.findViewById(R.id.pekerjal_imgb);
            holder.imgp =(ImageView)vi.findViewById(R.id.pekerjal_imgp);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0) {
            holder.nama.setText("Tidak Ada Data");
            holder.jab.setText(" ");
            holder.absenb.setText(" ");
            holder.absenp.setText(" ");
            holder.imgb.setImageResource(R.drawable.icon_user);
            holder.imgp.setImageResource(R.drawable.icon_user);
        } else {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = (frm_absensi_list) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.nama.setText( tempValues.getNama() );
            holder.jab.setText( tempValues.getJabatan());
            holder.absenb.setText( tempValues.getAbsenb() );
            holder.absenp.setText( tempValues.getAbsenp() );

            Bitmap bmp = null;
            if( tempValues.getImgb().equals("")) {
                holder.imgb.setImageResource(R.drawable.icon_user);
            } else {
                //foto disini
                BitmapFactory.Options bmOptions= new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds= true;
                BitmapFactory.decodeFile(tempValues.getImgb(), bmOptions);
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
                        bmp = BitmapFactory.decodeFile(tempValues.getImgb(), bmOptions);
                        //bmp = BitmapFactory.decodeFile(mFilePaths[position]);
                        holder.imgb.setImageBitmap(bmp);
                    }
                    catch(OutOfMemoryError e) {
                        bmOptions.inSampleSize= scaleFactor;
                        Log.d("tag", "OutOfMemoryError: " + e.toString());
                    }
                }
                while(bmp == null && scaleFactor <= 256);
            }
            if( tempValues.getImgp().equals("")) {
                holder.imgp.setImageResource(R.drawable.icon_user);
            } else {
                //foto disini
                BitmapFactory.Options bmOptions= new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds= true;
                BitmapFactory.decodeFile(tempValues.getImgp(), bmOptions);
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
                        bmp = BitmapFactory.decodeFile(tempValues.getImgp(), bmOptions);
                        //bmp = BitmapFactory.decodeFile(mFilePaths[position]);
                        holder.imgp.setImageBitmap(bmp);
                    }
                    catch(OutOfMemoryError e) {
                        bmOptions.inSampleSize= scaleFactor;
                        Log.d("tag", "OutOfMemoryError: " + e.toString());
                    }
                }
                while(bmp == null && scaleFactor <= 256);

            }



            /******** Set Item Click Listner for LayoutInflater for each row *******/

            //vi.setOnClickListener((View.OnClickListener) new OnItemClickListener( position ));
        }
        return vi;
    }

    //@Override
    //public void onClick(View v) {
    //    Log.e("sekitar IA", "=====Row button clicked=====");
    //}

    /********* Called when Item click in ListView ************/
    //private class OnItemClickListener  implements View.OnClickListener {
    //    private int mPosition;

    //    OnItemClickListener(int position){
    //        mPosition = position;
    //    }

    //    @Override
    //    public void onClick(View arg0) {
    //CustomListViewAndroidExample sct = (CustomListViewAndroidExample)activity;
    /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/
    //sct.onItemClick(mPosition);
    //    }
    //}
}