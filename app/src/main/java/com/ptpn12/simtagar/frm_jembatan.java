package com.ptpn12.simtagar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class frm_jembatan extends AppCompatActivity {
    MySQLLiteHelper sws_db;
    DB_Setting dbsetting;
    String acx, acy;
    Spinner kondisi, jenis;
    Button btn_ok, btn_cancel;

    EditText ket, lebar;
    private ArrayList<String> kode_kondisi = new ArrayList<String>();
    private ArrayList<String> nama_kondisi = new ArrayList<String>();
    private ArrayList<String> kode_jenis = new ArrayList<String>();
    private ArrayList<String> nama_jenis = new ArrayList<String>();
    private ArrayList<String> kode_kebun = new ArrayList<String>();
    private ArrayList<String> nama_kebun = new ArrayList<String>();
    List<DB_Ref_Kebun> setkebun = new LinkedList<DB_Ref_Kebun>();
    List<DB_Ref_Jembatan> setjalan = new LinkedList<DB_Ref_Jembatan>();
    List<DB_Ref_Jembatan_Kondisi> setkondisi = new LinkedList<DB_Ref_Jembatan_Kondisi>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_jembatan);
        sws_db = new MySQLLiteHelper(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            acx = extras.getString("cx");
            acy = extras.getString("cy");
        }

        kondisi = (Spinner) findViewById(R.id.jembatan_kondisi);
        jenis = (Spinner) findViewById(R.id.jembatan_jenis);
        //kebun = (Spinner) findViewById(R.id.jembatan_kebun);
        ket = (EditText) findViewById(R.id.jembatan_ket);
        lebar = (EditText) findViewById(R.id.jembatan_lebar);
        btn_ok = (Button) findViewById(R.id.jembatan_proses);
        btn_cancel = (Button) findViewById(R.id.jembatan_batal);

        //setkebun = sws_db.DBRefKebun_Get(0);
        //for( int iix = 0; iix < setkebun.size(); iix++ ) {
        //    kode_kebun.add(String.valueOf(setkebun.get(iix).getKode()));
        //    nama_kebun.add(setkebun.get(iix).getNama());
        //}
        //ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nama_kebun);
        //kebun.setAdapter(adapter1);

        setkondisi = sws_db.DBRefJembatanKondisi_Get(0);
        for( int iix = 0; iix < setkondisi.size(); iix++ ) {
            kode_kondisi.add(String.valueOf(setkondisi.get(iix).getKode()));
            nama_kondisi.add(setkondisi.get(iix).getNama());
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nama_kondisi);
        kondisi.setAdapter(adapter2);

        setjalan = sws_db.DBRefJembatan_Get(0);
        for( int iix = 0; iix < setjalan.size(); iix++ ) {
            kode_jenis.add(String.valueOf(setjalan.get(iix).getKode()));
            nama_jenis.add(setjalan.get(iix).getNama());
        }
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nama_jenis);
        jenis.setAdapter(adapter3);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                DB_Setting asetting;
                asetting = sws_db.DBSetting_Get();
                int userid = asetting.getActiveUser();
                int kebun = asetting.getActiveKebun();
                int pilihan = jenis.getSelectedItemPosition();
                int jenis = Integer.parseInt(kode_jenis.get(pilihan));
                pilihan = kondisi.getSelectedItemPosition();
                int kondisi = Integer.parseInt(kode_kondisi.get(pilihan));
                //pilihan = kebun.getSelectedItemPosition();
                //int kebun = Integer.parseInt(kode_kebun.get(pilihan));
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String logdate = df.format(c.getTime());
                String aket = ket.getText().toString().trim();
                String alebar = lebar.getText().toString().trim();
                if( kebun == 0 ) {
                    Toast.makeText(getApplicationContext(),"Kebun belum didefinisikan", Toast.LENGTH_SHORT).show();
                    return;
                }

                sws_db.DBJembatan_Add(jenis, kondisi, kebun, logdate, userid, aket, acx, acy, 0, alebar,0,0, 1);

                Intent intent1=new Intent();
                setResult(1105,intent1);
                finish();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent1=new Intent();
                setResult(113,intent1);
                finish();
            }
        });
    }
}