package com.ptpn12.simtagar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.String.format;

public class frm_ld_kejadian_IA extends BaseAdapter {
    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    frm_ld_kejadian_list tempValues=null;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public frm_ld_kejadian_IA(Activity a, ArrayList d) {
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
        public TextView koord;
        public TextView waktu;
        public TextView ket;
        public TextView dariserver;
        public TextView kirim;
    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        frm_ld_kejadian_IA.ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.frm_ld_kejadian_list, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new frm_ld_kejadian_IA.ViewHolder();
            holder.nourut = (TextView) vi.findViewById(R.id.ld_kejadian_no);
            holder.jenis = (TextView) vi.findViewById(R.id.ld_kejadian_jenis);
            holder.koord = (TextView) vi.findViewById(R.id.ld_kejadian_koord);
            holder.waktu = (TextView) vi.findViewById(R.id.ld_kejadian_waktu);
            holder.ket = (TextView) vi.findViewById(R.id.ld_kejadian_ket);
            holder.dariserver = (TextView) vi.findViewById(R.id.ld_kejadian_dari_server);
            holder.kirim = (TextView) vi.findViewById(R.id.ld_kejadian_kirim);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(frm_ld_kejadian_IA.ViewHolder)vi.getTag();

        if(data.size()<=0) {
            holder.nourut.setText("Tidak ada data");
            holder.jenis.setText(" ");
            holder.koord.setText(" ");
            holder.ket.setText(" ");
            holder.waktu.setText(" ");
            holder.dariserver.setText(" ");
            holder.kirim.setText(" ");
        } else {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = (frm_ld_kejadian_list) data.get( position );

            /************  Set Model values in Holder elements ***********/
            holder.nourut.setText(String.valueOf(position+1));
            holder.jenis.setText( tempValues.getKetJenis() );
            String koord = format("%.4f",Double.parseDouble(tempValues.getCx())) + ", " + format("%.4f",Double.parseDouble(tempValues.getCy()));
            holder.koord.setText( koord );
            holder.waktu.setText( ZZ_Util.sws_getTgl(tempValues.getWaktu()) );
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
