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

public class frm_tanaman extends AppCompatActivity {
    MySQLLiteHelper sws_db;
    DB_Setting dbsetting;
    String acx, acy;
    Spinner jenis;
    EditText lingkar, tinggi, jarak, banyak, afdeling, blok, ttanam, tpanen, bpanen, spanen, ket;
    Button btn_ok, btn_cancel;

    private ArrayList<String> kode_kebun = new ArrayList<String>();
    private ArrayList<String> nama_kebun = new ArrayList<String>();
    private ArrayList<String> kode_tanaman = new ArrayList<String>();
    private ArrayList<String> nama_tanaman = new ArrayList<String>();
    List<DB_Ref_Kebun> setkebun = new LinkedList<DB_Ref_Kebun>();
    List<DB_Ref_Komoditi> settanaman = new LinkedList<DB_Ref_Komoditi>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_tanaman);
        sws_db = new MySQLLiteHelper(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            acx = extras.getString("cx");
            acy = extras.getString("cy");
        }

        jenis = (Spinner) findViewById(R.id.tanam_jenis);
        //kebun = (Spinner) findViewById(R.id.tanam_kebun);
        lingkar = (EditText) findViewById(R.id.tanam_lingkar);
        tinggi = (EditText) findViewById(R.id.tanam_tinggi);
        jarak = (EditText) findViewById(R.id.tanam_jarak);
        banyak = (EditText) findViewById(R.id.tanam_banyak);
        afdeling = (EditText) findViewById(R.id.tanam_afdeling);
        blok = (EditText) findViewById(R.id.tanam_blok);
        ttanam = (EditText) findViewById(R.id.tanam_ttanam);
        tpanen = (EditText) findViewById(R.id.tanam_tpanen);
        bpanen = (EditText) findViewById(R.id.tanam_bpanen);
        spanen = (EditText) findViewById(R.id.tanam_spanen);
        ket = (EditText) findViewById(R.id.tanam_ket);
        btn_ok = (Button) findViewById(R.id.tanam_proses);
        btn_cancel = (Button) findViewById(R.id.tanam_batal);

        //setkebun = sws_db.DBRefKebun_Get(0);
        //for( int iix = 0; iix < setkebun.size(); iix++ ) {
        //    kode_kebun.add(String.valueOf(setkebun.get(iix).getKode()));
        //    nama_kebun.add(setkebun.get(iix).getNama());
        //}
        //ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nama_kebun);
        //kebun.setAdapter(adapter1);

        settanaman = sws_db.DBRefKomoditi_Get(0);
        for( int iix = 0; iix < settanaman.size(); iix++ ) {
            kode_tanaman.add(String.valueOf(settanaman.get(iix).getKode()));
            nama_tanaman.add(settanaman.get(iix).getNama());
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nama_tanaman);
        jenis.setAdapter(adapter2);


        btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                DB_Setting asetting;
                asetting = sws_db.DBSetting_Get();
                int userid = asetting.getActiveUser();
                int kebun = asetting.getActiveKebun();
                int pilihan = jenis.getSelectedItemPosition();
                int jenis = Integer.parseInt(kode_tanaman.get(pilihan));
                //pilihan = kebun.getSelectedItemPosition();
                //int kebun = Integer.parseInt(kode_kebun.get(pilihan));
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String logdate = df.format(c.getTime());
                String aket = ket.getText().toString().trim();
                String vlingkar = lingkar.getText().toString().trim();
                String vjarak = jarak.getText().toString().trim();
                String vtinggi = tinggi.getText().toString().trim();
                String vbanyak = banyak.getText().toString().trim();
                int vbanyaki = 0;
                try {
                    vbanyaki = Integer.parseInt(vbanyak);
                } catch (NumberFormatException  e) {
                    vbanyaki = 0;
                }
                String vafdeling = afdeling.getText().toString().trim();
                String vblok = blok.getText().toString().trim();
                String vttanam = ttanam.getText().toString().trim();
                int vttanami = 0;
                try {
                    vttanami = Integer.parseInt(vttanam);
                } catch (NumberFormatException  e) {
                    vttanami = 0;
                }
                String vtpanen= tpanen.getText().toString().trim();
                int vtpaneni = 0;
                try {
                    vtpaneni = Integer.parseInt(vtpanen);
                } catch (NumberFormatException  e) {
                    vtpaneni = 0;
                }
                String vapanen = "";
                String vbpanen= bpanen.getText().toString().trim();
                String vspanen= spanen.getText().toString().trim();
                    if( kebun == 0 ) {
                        Toast.makeText(getApplicationContext(),"Kebun belum didefinisikan", Toast.LENGTH_SHORT).show();
                        return;
                    }


                //(int jenis, String lingkar, String jarak, String tinggi, int banyak, int kebun, String waktu, int userid,
                //String afdeling, String blok, int ttanam, int tpanen, String apanen, String bpanen, String spanen,
                //String ket, String cx, String cy, int kirim) {

                sws_db.DBTanaman_Add(jenis, vlingkar, vjarak, vtinggi, vbanyaki, kebun, logdate, userid, vafdeling, vblok,
                        vttanami, vtpaneni, vapanen, vbpanen, vspanen, aket, acx, acy, 0, 0, 0, 1);

                Intent intent1=new Intent();
                setResult(1106,intent1);
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