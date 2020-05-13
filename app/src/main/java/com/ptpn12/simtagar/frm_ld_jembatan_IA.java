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

import androidx.fragment.app.Fragment;

import static java.lang.String.*;

public class frm_ld_jembatan_IA extends BaseAdapter {
    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    frm_ld_jembatan_list tempValues=null;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public frm_ld_jembatan_IA(Activity a, ArrayList d) {
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
        public TextView nourut;
        public TextView jenis;
        public TextView kondisi;
        public TextView lebar;
        public TextView koord;
        public TextView ket;
        public TextView dariserver;
        public TextView kirim;
    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.frm_ld_jembatan_list, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.nourut = (TextView) vi.findViewById(R.id.ld_jembatan_no);
            holder.jenis = (TextView) vi.findViewById(R.id.ld_jembatan_jenis);
            holder.kondisi = (TextView) vi.findViewById(R.id.ld_jembatan_kondisi);
            holder.lebar = (TextView) vi.findViewById(R.id.ld_jembatan_lebar);
            holder.koord = (TextView) vi.findViewById(R.id.ld_jembatan_koord);
            holder.ket = (TextView) vi.findViewById(R.id.ld_jembatan_ket);
            holder.dariserver = (TextView) vi.findViewById(R.id.ld_jembatan_dari_server);
            holder.kirim = (TextView) vi.findViewById(R.id.ld_jembatan_kirim);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0) {
            holder.nourut.setText("Tidak ada data");
            holder.jenis.setText(" ");
            holder.kondisi.setText(" ");
            holder.lebar.setText(" ");
            holder.koord.setText(" ");
            holder.ket.setText(" ");
            holder.dariserver.setText(" ");
            holder.kirim.setText(" ");
        } else {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = (frm_ld_jembatan_list) data.get( position );

            /************  Set Model values in Holder elements ***********/
            holder.nourut.setText(String.valueOf(position+1));
            holder.jenis.setText( tempValues.getKetJenis() );
            holder.kondisi.setText( tempValues.getKetKondisi() );
            holder.lebar.setText( tempValues.getLebar() );
            String koord = format("%.4f",Double.parseDouble(tempValues.getCx())) + ", " + format("%.4f",Double.parseDouble(tempValues.getCy()));
            holder.koord.setText( koord );
            holder.ket.setText( tempValues.getKetJenis() );
            holder.dariserver.setText( tempValues.getKetDariServer() );
            holder.kirim.setText( tempValues.getKetKirim() );
            holder.ket.setText( tempValues.getKet());

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