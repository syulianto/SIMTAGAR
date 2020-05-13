package com.ptpn12.simtagar;

import android.app.Activity;
import android.content.Context;
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

public class frm_sekitar_IA extends BaseAdapter {
    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    frm_sekitar_list tempValues=null;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public frm_sekitar_IA(Activity a, ArrayList d) {
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
        public TextView jenis;
        public TextView ket;
        public TextView koord;
        public TextView jarak;
        public ImageView gambar;
    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.frm_sekitar_list, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.jenis = (TextView) vi.findViewById(R.id.sekitarl_jenis);
            holder.ket =(TextView)vi.findViewById(R.id.sekitarl_ket);
            holder.koord =(TextView)vi.findViewById(R.id.sekitarl_koord);
            holder.jarak =(TextView)vi.findViewById(R.id.sekitarl_jarak);
            holder.gambar =(ImageView)vi.findViewById(R.id.sekitarl_img);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0) {
            holder.jenis.setText("Tidak Ada Data");
            holder.ket.setText(" ");
            holder.koord.setText(" ");
            holder.jarak.setText(" ");
            holder.gambar.setImageResource(R.drawable.obyek);
        } else {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = (frm_sekitar_list) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.jenis.setText( tempValues.getJenis() );
            holder.ket.setText( tempValues.getKet());
            String koord = format("%.4f",Double.parseDouble(tempValues.getCX())) + ", " + format("%.4f",Double.parseDouble(tempValues.getCY()));
            holder.koord.setText( koord );
            double jarakm = tempValues.getJarak();
            String jarak = format("%.4f", jarakm) + " km";
            holder.jarak.setText( "Jarak dari lokasi : " + jarak );
            int gambar = R.drawable.obyek;
            if( tempValues.getImg() == 1 ) { gambar = R.drawable.aktifitas; }
            if( tempValues.getImg() == 2 ) { gambar = R.drawable.kejadian; }
            if( tempValues.getImg() == 3 ) { gambar = R.drawable.patok; }
            if( tempValues.getImg() == 4 ) { gambar = R.drawable.jalan; }
            if( tempValues.getImg() == 5 ) { gambar = R.drawable.jembatan; }
            if( tempValues.getImg() == 6 ) { gambar = R.drawable.tanaman; }
            if( tempValues.getImg() == 7 ) { gambar = R.drawable.saluran; }
            if( tempValues.getImg() == 8 ) { gambar = R.drawable.foto; }
            holder.gambar.setImageResource(gambar);

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