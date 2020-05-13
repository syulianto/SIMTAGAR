package com.ptpn12.simtagar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class frm_info extends AppCompatActivity {
    MySQLLiteHelper sws_db;
    TextView tabsensi, taktifitas, tgeotag, tjalan, tjembatan;
    TextView tkejadian, tpatok, tpeta, tpekerja, tafdeling;
    TextView trefakt, trefjab, trefjalanjenis, trefjalankond, trefjembatanjenis;
    TextView trefjembatankond, trefkebun, trefkejadian, trefkomoditi, trefpatok;
    TextView trefpek, trefsaluranjenis, trefsalurankond, tsaluran, tkomoditi;
    TextView info_sdk, info_os, info_model, info_produk, info_device;
    Button btn_ok;
    Button btn_hapus;

    //System.getProperty("os.version"); // OS version
    //android.os.Build.VERSION.SDK      // API Level
    //android.os.Build.DEVICE           // Device
    //android.os.Build.MODEL            // Model
    //android.os.Build.PRODUCT          // Product

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_info);
        info_sdk = (TextView) findViewById(R.id.info_sdk);
        info_os = (TextView) findViewById(R.id.info_versios);
        info_model = (TextView) findViewById(R.id.info_model);
        info_produk = (TextView) findViewById(R.id.info_produk);
        info_device = (TextView) findViewById(R.id.info_device);

        Field[] fields = Build.VERSION_CODES.class.getFields();
        String codeName = "UNKNOWN";
        for (Field field : fields) {
            try {
                if (field.getInt(Build.VERSION_CODES.class) == Build.VERSION.SDK_INT) {
                    codeName = field.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        info_sdk.setText(String.valueOf(Build.VERSION.SDK_INT));
        info_os.setText(codeName);
        info_produk.setText(Build.PRODUCT);
        info_model.setText(Build.MODEL);
        info_device.setText(Build.DEVICE);


        sws_db = new MySQLLiteHelper(getApplicationContext());
        btn_ok = (Button) findViewById(R.id.info_proses);
        btn_hapus = (Button) findViewById(R.id.info_hapus);

        tabsensi = (TextView) findViewById(R.id.info_dbabsensi);
        taktifitas = (TextView) findViewById(R.id.info_dbaktifitas);
        tgeotag = (TextView) findViewById(R.id.info_dbgeotag);
        tjalan = (TextView) findViewById(R.id.info_dbjalan);
        tjembatan = (TextView) findViewById(R.id.info_dbjembatan);

        tkejadian = (TextView) findViewById(R.id.info_dbkejadian);
        tpatok = (TextView) findViewById(R.id.info_dbpatok);
        tpeta = (TextView) findViewById(R.id.info_dbpeta);
        tpekerja = (TextView) findViewById(R.id.info_dbpekerja);
        tafdeling = (TextView) findViewById(R.id.info_dbafdeling);

        trefakt = (TextView) findViewById(R.id.info_dbrefaktifitas);
        trefjab = (TextView) findViewById(R.id.info_dbrefjab);
        trefjalanjenis = (TextView) findViewById(R.id.info_dbrefjalan);
        trefjalankond = (TextView) findViewById(R.id.info_dbrefjalankondisi);
        trefjembatanjenis = (TextView) findViewById(R.id.info_dbrefjembatan);

        //TextView trefjembatankond, trefkebun, trefkejadian, trefkomoditi, trefpatok;
        trefjembatankond = (TextView) findViewById(R.id.info_dbrefjembatankondisi);
        trefkebun = (TextView) findViewById(R.id.info_dbkebun);
        trefkejadian = (TextView) findViewById(R.id.info_dbrefkejadian);
        trefkomoditi = (TextView) findViewById(R.id.info_dbrefkomoditi);
        trefpatok = (TextView) findViewById(R.id.info_dbrefpatok);

        //TextView trefpek, trefsaluranjenis, trefsalurankond, tsaluran, tkomoditi;
        trefpek = (TextView) findViewById(R.id.info_dbrefpekerjaan);
        trefsaluranjenis = (TextView) findViewById(R.id.info_dbrefsaluran);
        trefsalurankond = (TextView) findViewById(R.id.info_dbrefsalurankondisi);
        tsaluran = (TextView) findViewById(R.id.info_dbsaluran);
        tkomoditi = (TextView) findViewById(R.id.info_dbkomoditi);

        hitung_record();



        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent1 = new Intent();
                finish();
            }
        });
        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //mulai menghapus data
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(frm_info.this);
                alertDialogBuilder.setTitle("SIMTAGAR");
                alertDialogBuilder.setMessage("Yakin akan menghapus data?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        //map_main.this.finish();
                        sws_db.DBAbsensi_Delete(0,"",0);
                        sws_db.DBAktifitas_Delete(0);
                        sws_db.DBImage_Del(0,"",99,99);
                        sws_db.DBJalan_Delete(0,99,0);
                        sws_db.DBJembatan_Delete(0,99,0);
                        sws_db.DBKejadian_Delete(0);
                        sws_db.DBKML_Del(0,"");
                        sws_db.DBPatok_Delete(0);
                        sws_db.DBPekerja_Delete(0,0,0,"",0);
                        sws_db.DBRefAfdeling_Delete(0,0);
                        sws_db.DBRefAktifitas_Delete(0);
                        sws_db.DBRefJabatan_Delete(0);
                        sws_db.DBRefJalan_Delete(0);
                        sws_db.DBRefJalanKondisi_Delete(0);
                        sws_db.DBRefJembatan_Delete(0);
                        sws_db.DBRefJembatanKondisi_Delete(0);
                        sws_db.DBRefKebun_Delete(0);
                        sws_db.DBRefKejadian_Delete(0);
                        sws_db.DBRefKomoditi_Delete(0);
                        sws_db.DBRefPatok_Delete(0);
                        sws_db.DBRefPekerjaan_Delete(0);
                        sws_db.DBRefSaluran_Delete(0);
                        sws_db.DBRefSaluranKondisi_Delete(0);
                        sws_db.DBRefTanaman_Delete(0);
                        sws_db.DBSaluran_Delete(0,99, 0);
                        sws_db.DBTanaman_Delete(0,99,0);

                        Toast.makeText(getApplicationContext(), "Data sudah terhapus dari sistem", Toast.LENGTH_SHORT).show();
                        hitung_record();

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
        });
    }

    private void hitung_record() {
        List<DB_Absensi> setabsensi = new LinkedList<DB_Absensi>();
        setabsensi = sws_db.DBAbsensi_Get(0,"",0, "");
        tabsensi.setText(String.valueOf(setabsensi.size()));
        List<DB_Aktifitas> setakt = new LinkedList<DB_Aktifitas>();
        setakt = sws_db.DBAktifitas_Get(0,99,99,"",0);
        taktifitas.setText(String.valueOf(setakt.size()));
        List<DB_Image> setimg = new LinkedList<DB_Image>();
        setimg = sws_db.DBImage_Get(0,99,0,99);
        tgeotag.setText(String.valueOf(setimg.size()));
        List<DB_Jalan> setjalan = new LinkedList<DB_Jalan>();
        setjalan = sws_db.DBJalan_Get(0,99);
        tjalan.setText(String.valueOf(setjalan.size()));
        List<DB_Jembatan> setjemb = new LinkedList<DB_Jembatan>();
        setjemb = sws_db.DBJembatan_Get(0,99);
        tjembatan.setText(String.valueOf(setjemb.size()));

        List<DB_Kejadian> setkej = new LinkedList<DB_Kejadian>();
        setkej = sws_db.DBKejadian_Get(0,0,99,"",0);
        tkejadian.setText(String.valueOf(setkej.size()));
        List<DB_KML> setpeta = new LinkedList<DB_KML>();
        setpeta = sws_db.DBKML_Get(0,"",99);
        tpeta.setText(String.valueOf(setpeta.size()));
        List<DB_Patok> setpatok = new LinkedList<DB_Patok>();
        setpatok = sws_db.DBPatok_Get(0,0,99);
        tpatok.setText(String.valueOf(setpatok.size()));
        List<DB_Pekerja> setpekerja = new LinkedList<DB_Pekerja>();
        setpekerja = sws_db.DBPekerja_Get(0,0,0,"",0);
        tpekerja.setText(String.valueOf(setpekerja.size()));
        List<DB_Ref_Afdeling> setafd = new LinkedList<DB_Ref_Afdeling>();
        setafd = sws_db.DBRefAfdeling_Get(0,0);
        tafdeling.setText(String.valueOf(setafd.size()));

        List<DB_Ref_Aktifitas> setrakt = new LinkedList<DB_Ref_Aktifitas>();
        setrakt = sws_db.DBRefAktifitas_Get(0);
        trefakt.setText(String.valueOf(setrakt.size()));
        List<DB_Ref_Jabatan> setrjab = new LinkedList<DB_Ref_Jabatan>();
        setrjab = sws_db.DBRefJabatan_Get(0);
        trefjab.setText(String.valueOf(setrjab.size()));
        List<DB_Ref_Jalan> setrjal = new LinkedList<DB_Ref_Jalan>();
        setrjal = sws_db.DBRefJalan_Get(0);
        trefjalanjenis.setText(String.valueOf(setrjal.size()));
        List<DB_Ref_Jalan_Kondisi> setrjalk = new LinkedList<DB_Ref_Jalan_Kondisi>();
        setrjalk = sws_db.DBRefJalanKondisi_Get(0);
        trefjalankond.setText(String.valueOf(setrjalk.size()));
        List<DB_Ref_Jembatan> setrjem = new LinkedList<DB_Ref_Jembatan>();
        setrjem = sws_db.DBRefJembatan_Get(0);
        trefjembatanjenis.setText(String.valueOf(setrjem.size()));

        List<DB_Ref_Jembatan_Kondisi> setrjemk = new LinkedList<DB_Ref_Jembatan_Kondisi>();
        setrjemk = sws_db.DBRefJembatanKondisi_Get(0);
        trefjembatankond.setText(String.valueOf(setrjemk.size()));
        List<DB_Ref_Kebun> setkeb = new LinkedList<DB_Ref_Kebun>();
        setkeb = sws_db.DBRefKebun_Get(0);
        trefkebun.setText(String.valueOf(setkeb.size()));
        List<DB_Ref_Kejadian> setrkej = new LinkedList<DB_Ref_Kejadian>();
        setrkej = sws_db.DBRefKejadian_Get(0);
        trefkejadian.setText(String.valueOf(setrkej.size()));
        List<DB_Ref_Komoditi> setrkom = new LinkedList<DB_Ref_Komoditi>();
        setrkom = sws_db.DBRefKomoditi_Get(0);
        trefkomoditi.setText(String.valueOf(setrkom.size()));
        List<DB_Ref_Patok> setrpat = new LinkedList<DB_Ref_Patok>();
        setrpat = sws_db.DBRefPatok_Get(0);
        trefpatok.setText(String.valueOf(setrpat.size()));

        List<DB_Ref_Pekerjaan> setrpek = new LinkedList<DB_Ref_Pekerjaan>();
        setrpek = sws_db.DBRefPekerjaan_Get(0);
        trefpek.setText(String.valueOf(setrpek.size()));
        List<DB_Ref_Saluran> setrsal = new LinkedList<DB_Ref_Saluran>();
        setrsal = sws_db.DBRefSaluran_Get(0);
        trefsaluranjenis.setText(String.valueOf(setrsal.size()));
        List<DB_Ref_Saluran_Kondisi> setrsalk = new LinkedList<DB_Ref_Saluran_Kondisi>();
        setrsalk = sws_db.DBRefSaluranKondisi_Get(0);
        trefsalurankond.setText(String.valueOf(setrsalk.size()));
        List<DB_Saluran> setsal = new LinkedList<DB_Saluran>();
        setsal = sws_db.DBSaluran_Get(0,99);
        tsaluran.setText(String.valueOf(setsal.size()));
        List<DB_Tanaman> settan = new LinkedList<DB_Tanaman>();
        settan = sws_db.DBTanaman_Get(0,99);
        tkomoditi.setText(String.valueOf(settan.size()));
    }
}
