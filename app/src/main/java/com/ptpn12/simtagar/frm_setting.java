package com.ptpn12.simtagar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class frm_setting extends AppCompatActivity {
    EditText setting_webip, setting_vd;
    Button setting_proses, setting_batal;
    CheckBox setting_kejadian, setting_patok, setting_aktifitas, setting_jalan, setting_jembatan;
    CheckBox setting_tanaman, setting_saluran, setting_geotag;
    RadioButton setting_peta1, setting_peta2, setting_peta3, setting_peta4;
    RadioButton setting_koord1, setting_koord2, setting_koord3, setting_koord4;
    CheckBox setting_pz, setting_pl;

    MySQLLiteHelper sws_db;
    DB_Setting dbsetting;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_setting);

        setting_webip = (EditText) findViewById(R.id.setting_webip);
        setting_vd = (EditText) findViewById(R.id.setting_vd);
        setting_kejadian = (CheckBox) findViewById(R.id.setting_kejadian);
        setting_patok = (CheckBox) findViewById(R.id.setting_patok);
        setting_aktifitas = (CheckBox) findViewById(R.id.setting_aktifitas);
        setting_jalan = (CheckBox) findViewById(R.id.setting_jalan);
        setting_jembatan = (CheckBox) findViewById(R.id.setting_jembatan);
        setting_tanaman = (CheckBox) findViewById(R.id.setting_tanaman);
        setting_saluran = (CheckBox) findViewById(R.id.setting_saluran);
        setting_geotag = (CheckBox) findViewById(R.id.setting_geotag);
        setting_pl = (CheckBox) findViewById(R.id.setting_pl);
        setting_pz = (CheckBox) findViewById(R.id.setting_pz);
        setting_peta1 = (RadioButton) findViewById(R.id.setting_peta1);
        setting_peta2 = (RadioButton) findViewById(R.id.setting_peta2);
        setting_peta3 = (RadioButton) findViewById(R.id.setting_peta3);
        setting_peta4 = (RadioButton) findViewById(R.id.setting_peta4);
        setting_koord1 = (RadioButton) findViewById(R.id.setting_koord1);
        setting_koord2 = (RadioButton) findViewById(R.id.setting_koord2);
        setting_koord3 = (RadioButton) findViewById(R.id.setting_koord3);
        setting_koord4 = (RadioButton) findViewById(R.id.setting_koord4);
        setting_proses = (Button) findViewById(R.id.setting_proses);
        setting_batal = (Button) findViewById(R.id.setting_batal);

        sws_db = new MySQLLiteHelper(getApplicationContext());
        update_data();

        setting_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        setting_proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String webip = "";
                String vd = setting_vd.getText().toString().trim();
                webip = setting_webip.getText().toString().trim();
                if( webip.equals("")) {
                    Toast.makeText(getApplicationContext(), "Alamat server harus diisi", Toast.LENGTH_LONG).show();
                    return;
                }
                if( Patterns.WEB_URL.matcher(webip).matches() == false) {
                    Toast.makeText(getApplicationContext(), "Alamat server tidak valid", Toast.LENGTH_LONG).show();
                    return;
                }
                int pz, pl;
                int jmap = 1;
                int jkoord = 1;
                int tampil_kejadian, tampil_patok, tampil_pekerjaan, tampil_aktifitas, tampil_jalan, tampil_jembatan;
                int tampil_tanaman, tampil_obyek, tampil_saluran, tampil_geotag;
                if( setting_pz.isChecked()) { pz = 1; } else { pz = 0; }
                if( setting_pl.isChecked()) { pl = 1; } else { pl = 0; }
                if( setting_kejadian.isChecked()) { tampil_kejadian = 1; } else { tampil_kejadian = 0; }
                if( setting_patok.isChecked()) { tampil_patok = 1; } else { tampil_patok = 0; }
                tampil_pekerjaan = 0;
                if( setting_aktifitas.isChecked()) { tampil_aktifitas = 1; } else { tampil_aktifitas = 0; }
                if( setting_jalan.isChecked()) { tampil_jalan = 1; } else { tampil_jalan = 0; }
                if( setting_jembatan.isChecked()) { tampil_jembatan = 1; } else { tampil_jembatan = 0; }
                if( setting_tanaman.isChecked()) { tampil_tanaman = 1; } else { tampil_tanaman = 0; }
                tampil_obyek = 0;
                if( setting_saluran.isChecked()) { tampil_saluran = 1; } else { tampil_saluran = 0; }
                if( setting_geotag.isChecked()) { tampil_geotag = 1; } else { tampil_geotag = 0; }
                if( setting_peta1.isChecked()) { jmap = 1; }
                if( setting_peta2.isChecked()) { jmap = 2; }
                if( setting_peta3.isChecked()) { jmap = 3; }
                if( setting_peta4.isChecked()) { jmap = 4; }
                if( setting_koord1.isChecked()) { jkoord = 1; }
                if( setting_koord2.isChecked()) { jkoord = 2; }
                if( setting_koord3.isChecked()) { jkoord = 3; }
                if( setting_koord4.isChecked()) { jkoord = 4; }
                DB_Setting asetting;
                asetting = sws_db.DBSetting_Get();
                sws_db.DBSetting_Update(webip, vd, asetting.getIkirim(), asetting.getDver(), pz, pl, jmap, asetting.getActiveUser(),
                        asetting.getTFoto(), asetting.getTAktif(), asetting.getTInstruksi(), asetting.getTTrack(),
                        asetting.getTKML(), asetting.getTTeman(), asetting.getAlarm(), asetting.getJam(), asetting.getMenit(),
                        asetting.getPesan(), asetting.getIntInstruksi(), asetting.getIntOutbound(), asetting.getIntSetting(),
                        asetting.getIntTeman(), asetting.getIntGPS(), asetting.getIntInternet(), asetting.getIntTrack(),
                        asetting.getIntLateIns(), tampil_kejadian, tampil_patok, tampil_pekerjaan, tampil_aktifitas,
                        tampil_jalan, tampil_jembatan, tampil_tanaman, tampil_obyek, tampil_saluran, tampil_geotag, asetting.getActiveKebun(),
                        jkoord);
                //((MainActivity) getParent()).updateSetting();
                Toast.makeText(getApplicationContext(), "Pengaturan telah dirubah", Toast.LENGTH_LONG).show();
                Intent returnIntent = new Intent();
                setResult(1, returnIntent);
                finish();
            }
        });

    }

    private void update_data() {
        dbsetting = sws_db.DBSetting_Get();
        setting_webip.setText(dbsetting.getWebip());
        setting_vd.setText(dbsetting.getVd());
        if( dbsetting.getTampilAktifitas() == 1) { setting_aktifitas.setChecked(true); } else { setting_aktifitas.setChecked(false); }
        if( dbsetting.getTampilPatok() == 1) { setting_patok.setChecked(true); } else { setting_patok.setChecked(false); }
        if( dbsetting.getTampilKejadian() == 1) { setting_kejadian.setChecked(true); } else { setting_kejadian.setChecked(false); }
        if( dbsetting.getTampilJalan() == 1) { setting_jalan.setChecked(true); } else { setting_jalan.setChecked(false); }
        if( dbsetting.getTampilJembatan() == 1) { setting_jembatan.setChecked(true); } else { setting_jembatan.setChecked(false); }
        if( dbsetting.getTampilTanaman() == 1) { setting_tanaman.setChecked(true); } else { setting_tanaman.setChecked(false); }
        if( dbsetting.getTampilSaluran() == 1) { setting_saluran.setChecked(true); } else { setting_saluran.setChecked(false); }
        if( dbsetting.getTampilGeotag() == 1) { setting_geotag.setChecked(true); } else { setting_geotag.setChecked(false); }
        if( dbsetting.getPz() == 1) { setting_pz.setChecked(true); } else { setting_pz.setChecked(false); }
        if( dbsetting.getPl() == 1) { setting_pl.setChecked(true); } else { setting_pl.setChecked(false); }
        if( dbsetting.getJMap() == 1) { setting_peta1.setChecked(true); }
        if( dbsetting.getJMap() == 2) { setting_peta2.setChecked(true); }
        if( dbsetting.getJMap() == 3) { setting_peta3.setChecked(true); }
        if( dbsetting.getJMap() == 4) { setting_peta4.setChecked(true); }
        if( dbsetting.getJKoord() == 1) { setting_koord1.setChecked(true); }
        if( dbsetting.getJKoord() == 2) { setting_koord2.setChecked(true); }
        if( dbsetting.getJKoord() == 3) { setting_koord3.setChecked(true); }
        if( dbsetting.getJKoord() == 4) { setting_koord4.setChecked(true); }
    }

    @Override
    public void onResume() {
        super.onResume();
        update_data();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(frm_setting.this);
        alertDialogBuilder.setTitle("SIMTAGAR");
        alertDialogBuilder.setMessage("Tekan Ya untuk keluar");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Ya",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                //map_main.this.finish();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("SWS_EXIT", true);
                startActivity(intent);
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
