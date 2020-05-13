package com.ptpn12.simtagar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class frm_absensi extends AppCompatActivity {
    MySQLLiteHelper sws_db;
    DB_Setting dbsetting;
    ListView hasil;
    Button btn_proses;
    public frm_absensi CustomListView = null;
    public ArrayList<frm_absensi_list> CustomListViewValuesArr = new ArrayList<frm_absensi_list>();
    frm_absensi_IA adapter;
    Dialog jenis_absen;
    String cx, cy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_pekerja);
        sws_db = new MySQLLiteHelper(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cx = extras.getString("cx");
            cy = extras.getString("cy");
        }

        btn_proses = (Button) findViewById(R.id.pekerja_proses);
        hasil = (ListView) findViewById(R.id.pekerja_list);
        CustomListView = this;
        update_hasil();

        btn_proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent1 = new Intent();
                setResult(113, intent1);
                finish();
            }
        });

        hasil.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                // TODO Auto-generated method stub
                final frm_absensi_list clv = CustomListViewValuesArr.get(position);

                jenis_absen = new Dialog(frm_absensi.this);
                // Set GUI of login screen
                jenis_absen.setContentView(R.layout.frm_absensi_jenis);
                jenis_absen.setTitle("Jenis Absensi");

                // Init button of login GUI
                Button btnProses = (Button) jenis_absen.findViewById(R.id.jabsen_proses);
                Button btnCancel = (Button) jenis_absen.findViewById(R.id.jabsen_batal);
                final Spinner spinkebun = (Spinner) jenis_absen.findViewById(R.id.jabsen_jenis);
                final String[] jabsen = {"Absen Pagi", "Absen Sore"};
                ArrayAdapter arrayAdapter = new ArrayAdapter(frm_absensi.this, android.R.layout.simple_spinner_item, jabsen);
                spinkebun.setAdapter(arrayAdapter);
                // Attached listener for login GUI button
                btnProses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pilihan = spinkebun.getSelectedItemPosition();
                        jenis_absen.dismiss();
                        Intent intent;
                        intent = new Intent(getApplicationContext(), frm_absensi_camera.class);
                        intent.putExtra("cx", cx);
                        intent.putExtra("cy", cy);
                        intent.putExtra( "idrow", String.valueOf(clv.getIDRow()));
                        intent.putExtra( "idpek", clv.getIDPek());
                        intent.putExtra("idmandor", String.valueOf(clv.getIdMandor()));
                        intent.putExtra("pilihan", String.valueOf(pilihan));
                        startActivityForResult(intent, 101);

                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jenis_absen.dismiss();
                    }
                });
                // Make dialog box visible.
                jenis_absen.show();

                //Log.e("FS", String.valueOf(clv.getJarak()));
                //Toast.makeText(MainActivity.this, listItemsValue[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        update_hasil();
    }

    private void update_hasil() {
        CustomListViewValuesArr.clear();
        //mulai melakukan pencarian
        String dumket;
        int dumkode;
        dbsetting = sws_db.DBSetting_Get();
        int activeuser = dbsetting.getActiveUser();
        List<DB_Pekerja> setpekerja = new LinkedList<DB_Pekerja>();
        List<DB_Absensi> setabsensi = new LinkedList<DB_Absensi>();
        setpekerja = sws_db.DBPekerja_Get(0,0,0,"",activeuser);
        DB_Pekerja pekerja;
        DB_Absensi absensi;
        String ajab;
        String anabsenb = "";
        String anabsenp = "";
        String aharib = "";
        String aharip = "";
        String ajamb = "";
        String ajamp = "";
        String imgb = "";
        String imgp = "";
        List<DB_Ref_Jabatan> setjab = new LinkedList<DB_Ref_Jabatan>();
        for( int i = 0; i < setpekerja.size(); i++ ) {
            pekerja = setpekerja.get(i);
            setjab = sws_db.DBRefJabatan_Get(pekerja.getJabatan());
            if( setjab.size() == 0 ) {
                ajab = "Tidak diketahui/tidak diisi";
            } else {
                ajab = setjab.get(0).getNama();
            }
            setabsensi = sws_db.DBAbsensi_GetUruttgl(pekerja.getUrut(), pekerja.getIDPeg(), pekerja.getIDMandor());
            if( setabsensi.size() == 0 ) {
                anabsenb = "Belum ada absen";
                anabsenp = "Belum ada absen";
                aharib = "0000-00-00";
                aharip = "0000-00-00";
                ajamb = "00:00:00";
                ajamp = "00:00:00";
                imgb = "";
                imgp = "";
            } else {
                absensi = setabsensi.get(0);
                anabsenb = get_format_tgl(absensi.getAdab(), absensi.getTgl(), absensi.getJamb());
                anabsenp = get_format_tgl(absensi.getAdap(), absensi.getTgl(), absensi.getJamp());
                aharib = absensi.getTgl();
                aharip = absensi.getTgl();
                ajamb = absensi.getJamb();
                ajamp = absensi.getJamp();
                imgb = absensi.getFotob();
                imgp = absensi.getFotop();
            }

            final frm_absensi_list fsl = new frm_absensi_list();
            fsl.setNama(pekerja.getNama());
            fsl.setJabatan(ajab);
            fsl.setAbsenb(anabsenb);
            fsl.setAbsenp(anabsenp);
            fsl.setHarib(aharib);
            fsl.setHarip(aharip);
            fsl.setJamb(ajamb);
            fsl.setJamp(ajamp);
            fsl.setImgb(imgb);
            fsl.setImgp(imgp);
            fsl.setIDRow(pekerja.getUrut());
            fsl.setIDPek(pekerja.getIDPeg());
            fsl.setIDMandor(pekerja.getIDMandor());
            CustomListViewValuesArr.add( fsl );
        }
        adapter=new frm_absensi_IA( CustomListView, CustomListViewValuesArr );
        hasil.setAdapter( adapter );
    }

    private String get_format_tgl(int ada, String tgl, String jam) {
        if( ada == 0 ) {
            return "Belum ada absensi";
        }
        String[] separated = tgl.split("-");
        String output = separated[2];
        if( separated[1].equals("01") ) { output = output + " Januari " + separated[0]; }
        if( separated[1].equals("02") ) { output = output + " Februari " + separated[0]; }
        if( separated[1].equals("03") ) { output = output + " Maret " + separated[0]; }
        if( separated[1].equals("04") ) { output = output + " April " + separated[0]; }
        if( separated[1].equals("05") ) { output = output + " Mei " + separated[0]; }
        if( separated[1].equals("06") ) { output = output + " Juni " + separated[0]; }
        if( separated[1].equals("07") ) { output = output + " Juli " + separated[0]; }
        if( separated[1].equals("08") ) { output = output + " Agustus " + separated[0]; }
        if( separated[1].equals("09") ) { output = output + " September " + separated[0]; }
        if( separated[1].equals("10") ) { output = output + " Oktober " + separated[0]; }
        if( separated[1].equals("11") ) { output = output + " November " + separated[0]; }
        if( separated[1].equals("12") ) { output = output + " Desember " + separated[0]; }
        output = output + " " + jam;
        return output;
    }
}
