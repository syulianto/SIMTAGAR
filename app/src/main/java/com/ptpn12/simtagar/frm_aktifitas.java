package com.ptpn12.simtagar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class frm_aktifitas extends AppCompatActivity {
    MySQLLiteHelper sws_db;
    DB_Setting dbsetting;
    String acx, acy;
    Spinner jenis, kebun;
    Button btn_ok, btn_cancel;
    EditText ket, lama, biaya, sdm;
    private ArrayList<String> kode_kondisi = new ArrayList<String>();
    private ArrayList<String> nama_kondisi = new ArrayList<String>();
    private ArrayList<String> kode_jenis = new ArrayList<String>();
    private ArrayList<String> nama_jenis = new ArrayList<String>();
    private ArrayList<String> kode_kebun = new ArrayList<String>();
    private ArrayList<String> nama_kebun = new ArrayList<String>();
    List<DB_Ref_Kebun> setkebun = new LinkedList<DB_Ref_Kebun>();
    List<DB_Ref_Aktifitas> setjalan = new LinkedList<DB_Ref_Aktifitas>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_aktifitas);
        sws_db = new MySQLLiteHelper(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            acx = extras.getString("cx");
            acy = extras.getString("cy");
        }

        jenis = (Spinner) findViewById(R.id.aktifitas_jenis);
        //kebun = (Spinner) findViewById(R.id.aktifitas_kebun);
        ket = (EditText) findViewById(R.id.aktifitas_ket);
        lama = (EditText) findViewById(R.id.aktifitas_lama);
        biaya = (EditText) findViewById(R.id.aktifitas_biaya);
        sdm = (EditText) findViewById(R.id.aktifitas_sdm);
        btn_ok = (Button) findViewById(R.id.aktifitas_proses);
        btn_cancel = (Button) findViewById(R.id.aktifitas_batal);

        //setkebun = sws_db.DBRefKebun_Get(0);
        //for( int iix = 0; iix < setkebun.size(); iix++ ) {
        //    kode_kebun.add(String.valueOf(setkebun.get(iix).getKode()));
        //    nama_kebun.add(setkebun.get(iix).getNama());
        //}
        //ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nama_kebun);
        //kebun.setAdapter(adapter1);

        setjalan = sws_db.DBRefAktifitas_Get(0);
        for( int iix = 0; iix < setjalan.size(); iix++ ) {
            kode_jenis.add(String.valueOf(setjalan.get(iix).getKode()));
            Log.e("AKTIFITAS", setjalan.get(iix).getNama());
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
                //pilihan = kebun.getSelectedItemPosition();
                //int kebun = Integer.parseInt(kode_kebun.get(pilihan));
                String alama = lama.getText().toString().trim();
                String asdm = sdm.getText().toString().trim();
                String abiaya = biaya.getText().toString().trim();
                if( kebun == 0 ) {
                    Toast.makeText(getApplicationContext(),"Kebun belum didefinisikan", Toast.LENGTH_SHORT).show();
                    return;
                }

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String logdate = df.format(c.getTime());
                String aket = ket.getText().toString().trim();
                sws_db.DBAktifitas_Add(kebun,jenis,alama,abiaya,asdm,aket,acx, acy, logdate,userid,0,0,0, 1);
                Intent intent1=new Intent();
                setResult(1101,intent1);
                finish();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent1 = new Intent();
                setResult(113, intent1);
                finish();
            }
        });
    }
}