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

public class frm_ld_tanaman_IA extends BaseAdapter {
    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    frm_ld_tanaman_list tempValues=null;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public frm_ld_tanaman_IA(Activity a, ArrayList d) {
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
        public TextView lingkar;
        public TextView tinggi;
        public TextView jarak;
        public TextView afdeling;
        public TextView blok;
        public TextView ttanam;
        public TextView tpanen;
        public TextView apanen;
        public TextView bpanen;
        public TextView koord;
        public TextView ket;
        public TextView dariserver;
        public TextView kirim;
    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        frm_ld_tanaman_IA.ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.frm_ld_tanaman_list, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new frm_ld_tanaman_IA.ViewHolder();
            holder.nourut = (TextView) vi.findViewById(R.id.ld_tanaman_no);
            holder.jenis = (TextView) vi.findViewById(R.id.ld_tanaman_jenis);
            holder.lingkar = (TextView) vi.findViewById(R.id.ld_tanaman_lingkar);
            holder.tinggi = (TextView) vi.findViewById(R.id.ld_tanaman_tinggi);
            holder.jarak = (TextView) vi.findViewById(R.id.ld_tanaman_jarak);
            holder.afdeling = (TextView) vi.findViewById(R.id.ld_tanaman_afdeling);
            holder.blok = (TextView) vi.findViewById(R.id.ld_tanaman_blok);
            holder.ttanam = (TextView) vi.findViewById(R.id.ld_tanaman_ttanam);
            holder.tpanen = (TextView) vi.findViewById(R.id.ld_tanaman_tpanen);
            holder.apanen = (TextView) vi.findViewById(R.id.ld_tanaman_apanen);
            holder.bpanen = (TextView) vi.findViewById(R.id.ld_tanaman_bpanen);
            holder.koord = (TextView) vi.findViewById(R.id.ld_tanaman_koord);
            holder.ket = (TextView) vi.findViewById(R.id.ld_tanaman_ket);
            holder.dariserver = (TextView) vi.findViewById(R.id.ld_tanaman_dari_server);
            holder.kirim = (TextView) vi.findViewById(R.id.ld_tanaman_kirim);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(frm_ld_tanaman_IA.ViewHolder)vi.getTag();

        if(data.size()<=0) {
            holder.nourut.setText("Tidak ada data");
            holder.jenis.setText(" ");
            holder.lingkar.setText(" ");
            holder.tinggi.setText(" ");
            holder.jarak.setText(" ");
            holder.afdeling.setText(" ");
            holder.blok.setText(" ");
            holder.ttanam.setText(" ");
            holder.tpanen.setText(" ");
            holder.apanen.setText(" ");
            holder.bpanen.setText(" ");
            holder.koord.setText(" ");
            holder.ket.setText(" ");
            holder.dariserver.setText(" ");
            holder.kirim.setText(" ");
        } else {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = (frm_ld_tanaman_list) data.get( position );

            /************  Set Model values in Holder elements ***********/
            holder.nourut.setText(String.valueOf(position+1));
            holder.jenis.setText( tempValues.getKetJenis() );
            holder.lingkar.setText(tempValues.getLingkar());
            holder.tinggi.setText(tempValues.getTinggi());
            holder.jarak.setText(tempValues.getJarak());
            holder.afdeling.setText(tempValues.getAfdeling());
            holder.blok.setText(tempValues.getBlok());
            holder.ttanam.setText(String.valueOf(tempValues.getTtanam()));
            holder.tpanen.setText(String.valueOf(tempValues.getTpanen()));
            holder.apanen.setText(String.valueOf(tempValues.getApanen()));
            holder.bpanen.setText(String.valueOf(tempValues.getBpanen()) + " " + tempValues.getSpanen());
            String koord = format("%.4f",Double.parseDouble(tempValues.getCx())) + ", " + format("%.4f",Double.parseDouble(tempValues.getCy()));
            holder.koord.setText( koord );
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
