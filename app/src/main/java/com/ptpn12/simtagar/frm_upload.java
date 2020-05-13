package com.ptpn12.simtagar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class frm_upload extends AppCompatActivity {
    MySQLLiteHelper sws_db;
    DB_Setting dbsetting;
    CheckBox cb_kejadian, cb_aktifitas, cb_patok, cb_jalan, cb_jembatan, cb_tanaman, cb_saluran, cb_foto, cb_absensi;
    Spinner spin_kirim, spin_bln, spin_thn;
    Button btn_ok, btn_cancel;
    List<DB_Ref_Kebun> setkebun = new LinkedList<DB_Ref_Kebun>();
    private ArrayList<String> kode_kebun = new ArrayList<String>();
    private ArrayList<String> nama_kebun = new ArrayList<String>();
    private String TAG = LoginActivity.class.getSimpleName();
    ProgressDialog progressDialog;

    int kejadian_jml, kejadian_done;
    int aktifitas_jml, aktifitas_done;
    int patok_jml, patok_done;
    int jalan_jml, jalan_done;
    int jembatan_jml, jembatan_done;
    int tanaman_jml, tanaman_done;
    int saluran_jml, saluran_done;
    int foto_jml, foto_done;
    int absensi_jml, absensi_done;
    int total_jml, total_done;
    int ctr_upload = 0;
    String[] arr_bln = new String[] {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    String[] arr_kir = new String[] {"Seluruh data", "Data yang belum dikirim", "Data yang sudah pernah dikirim"};

    List<String> lst_thn = new ArrayList<String>();
    ProgressDialog progressdialog;
    ProgressBar upload_pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_upload);
        sws_db = new MySQLLiteHelper(getApplicationContext());

        cb_kejadian = (CheckBox) findViewById(R.id.upload_kejadian);
        cb_aktifitas = (CheckBox) findViewById(R.id.upload_Aktifitas);
        cb_patok = (CheckBox) findViewById(R.id.upload_patok);
        cb_jalan = (CheckBox) findViewById(R.id.upload_jalan);
        cb_jembatan = (CheckBox) findViewById(R.id.upload_jembatan);
        cb_tanaman = (CheckBox) findViewById(R.id.upload_tanaman);
        cb_saluran = (CheckBox) findViewById(R.id.upload_saluran);
        cb_foto = (CheckBox) findViewById(R.id.upload_foto);
        cb_absensi = (CheckBox) findViewById(R.id.upload_absensi);
        btn_ok = (Button) findViewById(R.id.upload_proses);
        btn_cancel = (Button) findViewById(R.id.upload_batal);
        spin_kirim = (Spinner) findViewById(R.id.upload_kirim);
        spin_bln = (Spinner) findViewById(R.id.upload_bln);
        spin_thn = (Spinner) findViewById(R.id.upload_thn);
        spin_bln.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr_bln));
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int ypos = 0;
        spin_bln.setSelection(month);

        lst_thn.clear();
        for( int iix= year - 2; iix < year+3; iix++ ) {
            lst_thn.add(String.valueOf(iix));
        }
        spin_thn.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lst_thn));
        spin_thn.setSelection(2);
        spin_kirim.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr_kir));
        spin_kirim.setSelection(0);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(frm_upload.this);
                alertDialogBuilder.setTitle("SIMTAGAR");
                alertDialogBuilder.setMessage("Tekan Ya untuk mulai mengupload data");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    @SuppressLint("LongLogTag")
                    public void onClick(DialogInterface dialog, int id) {
                        kejadian_jml = 0; kejadian_done = 0;
                        aktifitas_jml = 0; aktifitas_done = 0;
                        patok_jml = 0; patok_done = 0;
                        jalan_jml = 0; jalan_done = 0;
                        jembatan_jml = 0; jembatan_done = 0;
                        tanaman_jml = 0; tanaman_done = 0;
                        saluran_jml = 0; saluran_done = 0;
                        foto_jml = 0; foto_done = 0;
                        absensi_jml = 0; absensi_done = 0;
                        total_jml = 0; total_done = 0;

                        List<DB_Kejadian> setkej = new LinkedList<DB_Kejadian>();
                        DB_Kejadian akej;
                        List<DB_Aktifitas> setakt = new LinkedList<DB_Aktifitas>();
                        DB_Aktifitas aakt;
                        List<DB_Patok> setpat = new LinkedList<DB_Patok>();
                        DB_Patok apat;
                        List<DB_Jalan> setjal = new LinkedList<DB_Jalan>();
                        DB_Jalan ajal;
                        List<DB_Jembatan> setjem = new LinkedList<DB_Jembatan>();
                        DB_Jembatan ajem;
                        List<DB_Tanaman> settan = new LinkedList<DB_Tanaman>();
                        DB_Tanaman atan;
                        List<DB_Saluran> setsal = new LinkedList<DB_Saluran>();
                        DB_Saluran asal;
                        List<DB_Image> setfot = new LinkedList<DB_Image>();
                        DB_Image afot;
                        List<DB_Absensi> setabs = new LinkedList<DB_Absensi>();
                        DB_Absensi aabs;
                        int pilkirim = 99;
                        if( spin_kirim.getSelectedItemPosition() == 0 ) { pilkirim = 99; }
                        if( spin_kirim.getSelectedItemPosition() == 1 ) { pilkirim = 0; }
                        if( spin_kirim.getSelectedItemPosition() == 2 ) { pilkirim = 1; }
                        String pilwaktu = spin_thn.getSelectedItem().toString() + "-" + String.format("%02d", (spin_bln.getSelectedItemPosition()+1)) + "-01";


                        if( cb_kejadian.isChecked()) {
                            setkej = sws_db.DBKejadian_Get(0,0, pilkirim, pilwaktu,2);
                            kejadian_jml = setkej.size();
                        }
                        if( cb_aktifitas.isChecked()) {
                            setakt = sws_db.DBAktifitas_Get(0, 0, pilkirim, pilwaktu, 2);
                            aktifitas_jml = setakt.size();
                        }
                        if( cb_patok.isChecked()) {
                            setpat = sws_db.DBPatok_GetUpload(0,0,pilkirim, pilwaktu, 2);
                            patok_jml = setpat.size();
                        }
                        if( cb_jalan.isChecked()) {
                            setjal = sws_db.DBJalan_GetUpload(0, pilkirim, pilwaktu, 2);
                            jalan_jml = setjal.size();
                        }
                        if( cb_jembatan.isChecked()) {
                            setjem = sws_db.DBJembatan_GetUpload(0, pilkirim, pilwaktu, 2);
                            jembatan_jml = setjem.size();
                        }
                        if( cb_tanaman.isChecked()) {
                            settan = sws_db.DBTanaman_GetUpload(0,pilkirim, pilwaktu, 2);
                            tanaman_jml = settan.size();
                        }
                        if( cb_saluran.isChecked()) {
                            setsal = sws_db.DBSaluran_GetUpload(0,pilkirim, pilwaktu, 2);
                            saluran_jml = setsal.size();
                        }
                        if( cb_foto.isChecked()) {
                            setfot = sws_db.DBImage_GetUpload(0,0,0,pilkirim, pilwaktu, 2);
                            foto_jml = setfot.size();
                        }
                        if( cb_absensi.isChecked()) {
                            setabs = sws_db.DBAbsensi_GetUpload(0,"",0, pilwaktu, pilkirim);
                            absensi_jml = setabs.size();
                        }
                        total_jml = kejadian_jml + aktifitas_jml + patok_jml + jalan_jml + jembatan_jml + tanaman_jml + saluran_jml + foto_jml + absensi_jml;
                        if( total_jml == 0 ) {
                            Toast.makeText(getBaseContext(), "Tidak ada data yang perlu diunggah.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        ctr_upload = 0;
                        progressdialog = new ProgressDialog(frm_upload.this);
                        progressdialog.setMessage("Melakukan pengunggahan data...");
                        progressdialog.setMax(total_jml);
                        progressdialog.setProgress(0);
                        progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        if(!progressdialog.isShowing()) {
                            progressdialog.show();
                        }
                        for( int ctr_kej=0; ctr_kej < kejadian_jml; ctr_kej++ ) {
                            akej = setkej.get(ctr_kej);
                            try {
                                //String urut, String kebun, String jenis, String ket, String cx, String cy, String userid, String kirim,
                                //            String dari_server, String id_server, String asli)
                                doUploadKejadian(
                                        String.valueOf(akej.getUrut()), String.valueOf(akej.getKebun()), String.valueOf(akej.getJenis()),
                                        akej.getKet(), akej.getCX(), akej.getCY(), String.valueOf(akej.getUserID()), String.valueOf(akej.getKirim()),
                                        String.valueOf(akej.getDariServer()), String.valueOf(akej.getIDServer()), String.valueOf(akej.getAsli()));
                                //ctr_upload += 1;
                                if(progressdialog.isShowing()) {
                                    progressdialog.setProgress(ctr_upload);
                                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                                }
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                Log.e("frm_upload - doUploadKejadian", e.getMessage());
                            }
                        }

                        for( int ctr_akt=0; ctr_akt < aktifitas_jml; ctr_akt++ ) {
                            aakt = setakt.get(ctr_akt);
                            try {
                                //String urut, String kebun, String jenis, String ket, String cx, String cy, String userid, String kirim,
                                //            String dari_server, String id_server, String asli)
                                doUploadAktifitas(
                                        String.valueOf(aakt.getUrut()), String.valueOf(aakt.getKebun()), String.valueOf(aakt.getJenis()),
                                        aakt.getElama(), aakt.getEbiaya(), aakt.getEsdm(), aakt.getKet(), aakt.getCX(), aakt.getCY(),
                                        aakt.getWaktu(), String.valueOf(aakt.getUserID()), String.valueOf(aakt.getKirim()),
                                        String.valueOf(aakt.getDariServer()), String.valueOf(aakt.getIDServer()), String.valueOf(aakt.getAsli()));
                                //ctr_upload += 1;
                                if(progressdialog.isShowing()) {
                                    progressdialog.setProgress(ctr_upload);
                                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                                }
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                Log.e("frm_upload - doUploadAktifitas", e.getMessage());
                            }
                        }

                        for( int ctr_pat=0; ctr_pat < patok_jml; ctr_pat++ ) {
                            apat = setpat.get(ctr_pat);
                            try {
                                //String urut, String kebun, String jenis, String ket, String cx, String cy, String userid, String kirim,
                                //            String dari_server, String id_server, String asli)
                                doUploadPatok(
                                        String.valueOf(apat.getUrut()), apat.getNama(), String.valueOf(apat.getJenis()),
                                        String.valueOf(apat.getKebun()), apat.getCX(), apat.getCY(), apat.getWaktu(),
                                        String.valueOf(apat.getUserID()), String.valueOf(apat.getKirim()), apat.getKet(),
                                        String.valueOf(apat.getDariServer()), String.valueOf(apat.getIDServer()), String.valueOf(apat.getAsli()));
                                //ctr_upload += 1;
                                if(progressdialog.isShowing()) {
                                    progressdialog.setProgress(ctr_upload);
                                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                                }
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                Log.e("frm_upload - doUploadPatok", e.getMessage());
                            }
                        }

                        for( int ctr_jal=0; ctr_jal < jalan_jml; ctr_jal++ ) {
                            ajal = setjal.get(ctr_jal);
                            try {
                                //String urut, String kebun, String jenis, String ket, String cx, String cy, String userid, String kirim,
                                //            String dari_server, String id_server, String asli)
                                doUploadJalan(
                                        String.valueOf(ajal.getUrut()), String.valueOf(ajal.getJenis()), String.valueOf(ajal.getKondisi()),
                                        String.valueOf(ajal.getKebun()), ajal.getWaktu(), String.valueOf(ajal.getUserID()), ajal.getKet(),
                                        ajal.getCX(), ajal.getCY(), String.valueOf(ajal.getKirim()), ajal.getLebar(), String.valueOf(ajal.getDariServer()),
                                        String.valueOf(ajal.getIDServer()), String.valueOf(ajal.getAsli()));
                                //ctr_upload += 1;
                                if(progressdialog.isShowing()) {
                                    progressdialog.setProgress(ctr_upload);
                                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                                }
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                Log.e("frm_upload - doUploadJalan", e.getMessage());
                            }
                        }

                        for( int ctr_jem=0; ctr_jem < jembatan_jml; ctr_jem++ ) {
                            ajem = setjem.get(ctr_jem);
                            try {
                                //String urut, String kebun, String jenis, String ket, String cx, String cy, String userid, String kirim,
                                //            String dari_server, String id_server, String asli)
                                doUploadJembatan(
                                        String.valueOf(ajem.getUrut()), String.valueOf(ajem.getJenis()), String.valueOf(ajem.getKondisi()),
                                        String.valueOf(ajem.getKebun()), ajem.getWaktu(), String.valueOf(ajem.getUserID()), ajem.getKet(),
                                        ajem.getCX(), ajem.getCY(), String.valueOf(ajem.getKirim()), ajem.getLebar(), String.valueOf(ajem.getDariServer()),
                                        String.valueOf(ajem.getIDServer()), String.valueOf(ajem.getAsli()));
                                //ctr_upload += 1;
                                if(progressdialog.isShowing()) {
                                    progressdialog.setProgress(ctr_upload);
                                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                                }
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                Log.e("frm_upload - doUploadJembatan", e.getMessage());
                            }
                        }

                        for( int ctr_tan=0; ctr_tan < tanaman_jml; ctr_tan++ ) {
                            atan = settan.get(ctr_tan);
                            try {
                                //String urut, String kebun, String jenis, String ket, String cx, String cy, String userid, String kirim,
                                //            String dari_server, String id_server, String asli)
                                doUploadTanaman(
                                        String.valueOf(atan.getUrut()), String.valueOf(atan.getJenis()), atan.getLingkar(), atan.getJarak(),
                                        atan.getTinggi(), String.valueOf(atan.getBanyak()), String.valueOf(atan.getKebun()), atan.getWaktu(),
                                        String.valueOf(atan.getUserID()), atan.getAfdeling(), atan.getBlok(), String.valueOf(atan.getTTanam()),
                                        String.valueOf(atan.getTPanen()), atan.getAPanen(), atan.getBPanen(), atan.getSPanen(), atan.getKet(),
                                        atan.getCX(), atan.getCY(), String.valueOf(atan.getKirim()), String.valueOf(atan.getDariServer()),
                                        String.valueOf(atan.getIDServer()), String.valueOf(atan.getAsli()));
                                //ctr_upload += 1;
                                if(progressdialog.isShowing()) {
                                    progressdialog.setProgress(ctr_upload);
                                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                                }
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                Log.e("frm_upload - doUploadTanaman", e.getMessage());
                            }
                        }

                        for( int ctr_sal=0; ctr_sal < saluran_jml; ctr_sal++ ) {
                            asal = setsal.get(ctr_sal);
                            try {
                                //String urut, String kebun, String jenis, String ket, String cx, String cy, String userid, String kirim,
                                //            String dari_server, String id_server, String asli)
                                doUploadSaluran(
                                        String.valueOf(asal.getUrut()), String.valueOf(asal.getJenis()), String.valueOf(asal.getKondisi()),
                                        String.valueOf(asal.getKebun()), asal.getWaktu(), String.valueOf(asal.getUserID()), asal.getKet(),
                                        asal.getCX(), asal.getCY(), String.valueOf(asal.getKirim()), asal.getLebar(),
                                        String.valueOf(asal.getDariServer()), String.valueOf(asal.getIDServer()), String.valueOf(asal.getAsli()));
                                //ctr_upload += 1;
                                if(progressdialog.isShowing()) {
                                    progressdialog.setProgress(ctr_upload);
                                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                                }
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                Log.e("frm_upload - doUploadSaluran", e.getMessage());
                            }
                        }
                        for( int ctr_foto=0; ctr_foto < foto_jml; ctr_foto++ ) {
                            afot = setfot.get(ctr_foto);
                            try {
                                //private static final String TBL_IMAGE_DB = "create table tbl_image ("
                                //            + "id integer primary key autoincrement, userid integer, nfile text, cx text, cy text, "
                                //            + "kirim integer, waktu text, ket text, tampil integer, jenis integer, dari_server integer, id_server integer, asli integer);";
                                doUploadFoto(
                                        String.valueOf(afot.getID()), String.valueOf(afot.getUserid()), afot.getNfile(), afot.getCx(), afot.getCy(),
                                        String.valueOf(afot.getKirim()), afot.getWaktu(), afot.getKet(), String.valueOf(afot.getTampil()), String.valueOf(afot.getJenis()),
                                        String.valueOf(afot.getDariServer()), String.valueOf(afot.getIDServer()), String.valueOf(afot.getAsli()));
                                //ctr_upload += 1;
                                if(progressdialog.isShowing()) {
                                    progressdialog.setProgress(ctr_upload);
                                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                                }
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                Log.e("frm_upload - doUploadFoto", e.getMessage());
                            }
                        }
                        for( int ctr_abs=0; ctr_abs < foto_jml; ctr_abs++ ) {
                            aabs = setabs.get(ctr_abs);
                            try {
                                //(idrow integer, idpek text, idmandor integer, tgl text, "
                                //        + "jamb text, jamp text, lokasi text, fotob text, fotop text, adab integer, adap integer, cxb text, cyb text, cxp text, "
                                //        + "cyp text, waktub text, waktup text, int kirim)
                                doUploadAbsensi(
                                        String.valueOf(aabs.getIDRow()), aabs.getIDPek(), String.valueOf(aabs.getIDMandor()), aabs.getTgl(),
                                        aabs.getJamb(), aabs.getJamp(), aabs.getLokasi(), aabs.getFotob(), aabs.getFotop(),
                                        String.valueOf(aabs.getAdab()), String.valueOf(aabs.getAdap()), aabs.getCXB(), aabs.getCYB(),
                                        aabs.getCXP(), aabs.getCYP(), aabs.getWaktub(), aabs.getWaktup(), String.valueOf(aabs.getKirim()));
                                //ctr_upload += 1;
                                if(progressdialog.isShowing()) {
                                    progressdialog.setProgress(ctr_upload);
                                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                                }
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                Log.e("frm_upload - doUploadAbsensi", e.getMessage());
                            }
                        }

                    }
                });
                alertDialogBuilder.setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
                alertDialogBuilder.show();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent1 = new Intent();
                setResult(1, intent1);
                finish();
            }
        });
    }

    int max_thread = 0;

    private void doUploadKejadian(
            String urut, String kebun, String jenis, String ket, String cx, String cy, String userid, String kirim,
            String dari_server, String id_server, String asli) throws InterruptedException {

        class doUploadKejadianAsync extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {
                max_thread += 1;
                String urut = params[0];
                String kebun = params[1];
                String jenis = params[2];
                String ket = params[3];
                String cx = params[4];
                String cy = params[5];
                String userid = params[6];
                String kirim = params[7];
                String dari_server = params[8];
                String id_server = params[9];
                String asli = params[10];

                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); return "";}
                }

                dbsetting = sws_db.DBSetting_Get();
                String alamat = "http://" + dbsetting.getWebip();
                if( dbsetting.getVd().equals("")) {
                    alamat = alamat + "/mob_upload_kejadian.php";
                } else {
                    alamat = alamat + "/" + dbsetting.getVd() + "/mob_upload_kejadian.php";
                }

                try {
                    URL url = new URL(alamat);
                    Map<String,Object> params1 = new LinkedHashMap<>();
                    params1.put("urut", urut);
                    params1.put("kebun", kebun);
                    params1.put("jenis", jenis);
                    params1.put("ket", ket);
                    params1.put("cx", cx);
                    params1.put("cy", cy);
                    params1.put("userid", userid);
                    params1.put("kirim", kirim);
                    params1.put("dari_server", dari_server);
                    params1.put("id_server", id_server);
                    params1.put("asli", asli);

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String,Object> param : params1.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postDataBytes);

                    // read the response
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();

                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append('\n');
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (MalformedURLException e) {
                    Log.e(TAG, "MalformedURLException: " + e.getMessage());
                } catch (ProtocolException e) {
                    Log.e(TAG, "ProtocolException: " + e.getMessage());
                } catch (IOException e) {
                    Log.e(TAG, "IOException: " + e.getMessage());
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected  void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                ctr_upload += 1;
                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                }
                super.onPostExecute(result);
                max_thread -= 1;
                if( result == null ) {
                    return;
                }
                if( result.contains("SWS_NOVALID")) {
                    return;
                } else {
                    if( result.contains("SWS_OK")) {
                        String[] arr_output = {};
                        arr_output = result.split("\\|");
                        if( arr_output.length != 2 ) {
                            return;
                        }
                        int anid = Integer.parseInt(arr_output[1]);
                        sws_db.DBKejadian_UpdateKirim(anid);
                    }
                    return;
                }
            }
        }

        doUploadKejadianAsync doUploadKejadianAsync = new doUploadKejadianAsync();
        //String urut, String devid, String userid, String waktu, String Cx, String Cy
        //doUploadGPSAsync.execute(id, userid, waktu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            doUploadKejadianAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, urut, kebun, jenis, ket, cx, cy, userid, kirim, dari_server,
                    id_server, asli);
        }  else{
            doUploadKejadianAsync.execute(urut, kebun, jenis, ket, cx, cy, userid, kirim, dari_server, id_server, asli);
        }
    }

    private void doUploadAktifitas(
            String urut, String kebun, String jenis, String elama, String ebiaya, String esdm, String ket, String cx, String cy,
            String waktu, String userid, String kirim, String dari_server, String id_server, String asli) throws InterruptedException {

        class doUploadAktifitasAsync extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {
                max_thread += 1;
                String urut = params[0];
                String kebun = params[1];
                String jenis = params[2];
                String elama = params[3];
                String ebiaya = params[4];
                String esdm = params[5];
                String ket = params[6];
                String cx = params[7];
                String cy = params[8];
                String waktu = params[9];
                String userid = params[10];
                String kirim = params[11];
                String dari_server = params[12];
                String id_server = params[13];
                String asli = params[14];

                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); return "";}
                }

                dbsetting = sws_db.DBSetting_Get();
                String alamat = "http://" + dbsetting.getWebip();
                if( dbsetting.getVd().equals("")) {
                    alamat = alamat + "/mob_upload_aktifitas.php";
                } else {
                    alamat = alamat + "/" + dbsetting.getVd() + "/mob_upload_aktifitas.php";
                }

                try {
                    URL url = new URL(alamat);
                    Map<String,Object> params1 = new LinkedHashMap<>();
                    params1.put("urut", urut);
                    params1.put("kebun", kebun);
                    params1.put("jenis", jenis);
                    params1.put("elama", elama);
                    params1.put("ebiaya", ebiaya);
                    params1.put("esdm", esdm);
                    params1.put("ket", ket);
                    params1.put("cx", cx);
                    params1.put("cy", cy);
                    params1.put("waktu", waktu);
                    params1.put("userid", userid);
                    params1.put("kirim", kirim);
                    params1.put("dari_server", dari_server);
                    params1.put("id_server", id_server);
                    params1.put("asli", asli);


                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String,Object> param : params1.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postDataBytes);

                    // read the response
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();

                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append('\n');
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (MalformedURLException e) {
                    Log.e(TAG, "MalformedURLException: " + e.getMessage());
                } catch (ProtocolException e) {
                    Log.e(TAG, "ProtocolException: " + e.getMessage());
                } catch (IOException e) {
                    Log.e(TAG, "IOException: " + e.getMessage());
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected  void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                ctr_upload += 1;
                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                }
                super.onPostExecute(result);
                max_thread -= 1;
                if( result == null ) {
                    return;
                }
                if( result.contains("SWS_NOVALID")) {
                    return;
                } else {
                    if( result.contains("SWS_OK")) {
                        String[] arr_output = {};
                        arr_output = result.split("\\|");
                        if( arr_output.length != 2 ) {
                            return;
                        }
                        int anid = Integer.parseInt(arr_output[1]);
                        sws_db.DBAktifitas_UpdateKirim(anid);
                    }
                    return;
                }
            }
        }

        doUploadAktifitasAsync doUploadAktifitasAsync = new doUploadAktifitasAsync();
        //String urut, String devid, String userid, String waktu, String Cx, String Cy
        //doUploadGPSAsync.execute(id, userid, waktu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            doUploadAktifitasAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, urut, kebun, jenis, elama, ebiaya, esdm,
                    ket, cx, cy, waktu, userid, kirim, dari_server, id_server, asli);
        }  else{
            doUploadAktifitasAsync.execute(urut, kebun, jenis, elama, ebiaya, esdm, ket, cx, cy, waktu, userid, kirim, dari_server, id_server, asli);
        }
    }

    private void doUploadPatok(
            String urut, String nama, String jenis, String kebun, String cx, String cy, String waktu, String userid,
            String kirim, String ket, String dari_server, String id_server, String asli) throws InterruptedException {

        class doUploadPatokAsync extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {
                max_thread += 1;
                String urut = params[0];
                String nama = params[1];
                String jenis = params[2];
                String kebun = params[3];
                String cx = params[4];
                String cy = params[5];
                String waktu = params[6];
                String userid = params[7];
                String kirim = params[8];
                String ket = params[9];
                String dari_server = params[10];
                String id_server = params[11];
                String asli = params[12];

                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); return "";}
                }

                dbsetting = sws_db.DBSetting_Get();
                String alamat = "http://" + dbsetting.getWebip();
                if( dbsetting.getVd().equals("")) {
                    alamat = alamat + "/mob_upload_patok.php";
                } else {
                    alamat = alamat + "/" + dbsetting.getVd() + "/mob_upload_patok.php";
                }

                try {
                    URL url = new URL(alamat);
                    Map<String,Object> params1 = new LinkedHashMap<>();
                    params1.put("urut", urut);
                    params1.put("nama", nama);
                    params1.put("jenis", jenis);
                    params1.put("kebun", kebun);
                    params1.put("cx", cx);
                    params1.put("cy", cy);
                    params1.put("waktu", waktu);
                    params1.put("userid", userid);
                    params1.put("kirim", kirim);
                    params1.put("ket", ket);
                    params1.put("dari_server", dari_server);
                    params1.put("id_server", id_server);
                    params1.put("asli", asli);

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String,Object> param : params1.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postDataBytes);

                    // read the response
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();

                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append('\n');
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (MalformedURLException e) {
                    Log.e(TAG, "MalformedURLException: " + e.getMessage());
                } catch (ProtocolException e) {
                    Log.e(TAG, "ProtocolException: " + e.getMessage());
                } catch (IOException e) {
                    Log.e(TAG, "IOException: " + e.getMessage());
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected  void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                ctr_upload += 1;
                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                }
                super.onPostExecute(result);
                max_thread -= 1;
                if( result == null ) {
                    return;
                }
                if( result.contains("SWS_NOVALID")) {
                    return;
                } else {
                    if( result.contains("SWS_OK")) {
                        String[] arr_output = {};
                        arr_output = result.split("\\|");
                        if( arr_output.length != 2 ) {
                            return;
                        }
                        int anid = Integer.parseInt(arr_output[1]);
                        sws_db.DBPatok_UpdateKirim(anid);
                    }
                    return;
                }
            }
        }

        doUploadPatokAsync doUploadPatokAsync = new doUploadPatokAsync();
        //String urut, String devid, String userid, String waktu, String Cx, String Cy
        //doUploadGPSAsync.execute(id, userid, waktu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            doUploadPatokAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, urut, nama, jenis, kebun, cx, cy, waktu,
                    userid, kirim, ket, dari_server, id_server, asli);
        }  else{
            doUploadPatokAsync.execute(urut, nama, jenis, kebun, cx, cy, waktu, userid, kirim, ket, dari_server, id_server, asli);
        }
    }

    private void doUploadJalan(
            String urut, String jenis, String kondisi, String kebun, String waktu, String userid, String ket, String cx, String cy,
            String kirim, String lebar, String dari_server, String id_server, String asli) throws InterruptedException {

        class doUploadJalanAsync extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {
                max_thread += 1;
                String urut = params[0];
                String jenis = params[1];
                String kondisi = params[2];
                String kebun = params[3];
                String waktu = params[4];
                String userid = params[5];
                String ket = params[6];
                String cx = params[7];
                String cy = params[8];
                String kirim = params[9];
                String lebar = params[10];
                String dari_server = params[11];
                String id_server = params[12];
                String asli = params[13];

                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); return "";}
                }

                dbsetting = sws_db.DBSetting_Get();
                String alamat = "http://" + dbsetting.getWebip();
                if( dbsetting.getVd().equals("")) {
                    alamat = alamat + "/mob_upload_jalan.php";
                } else {
                    alamat = alamat + "/" + dbsetting.getVd() + "/mob_upload_jalan.php";
                }

                try {
                    URL url = new URL(alamat);
                    Map<String,Object> params1 = new LinkedHashMap<>();
                    params1.put("urut", urut);
                    params1.put("jenis", jenis);
                    params1.put("kondisi", kondisi);
                    params1.put("kebun", kebun);
                    params1.put("waktu", waktu);
                    params1.put("userid", userid);
                    params1.put("ket", ket);
                    params1.put("cx", cx);
                    params1.put("cy", cy);
                    params1.put("kirim", kirim);
                    params1.put("lebar", lebar);
                    params1.put("dari_server", dari_server);
                    params1.put("id_server", id_server);
                    params1.put("asli", asli);

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String,Object> param : params1.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postDataBytes);

                    // read the response
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();

                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append('\n');
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (MalformedURLException e) {
                    Log.e(TAG, "MalformedURLException: " + e.getMessage());
                } catch (ProtocolException e) {
                    Log.e(TAG, "ProtocolException: " + e.getMessage());
                } catch (IOException e) {
                    Log.e(TAG, "IOException: " + e.getMessage());
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected  void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                ctr_upload += 1;
                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                }
                super.onPostExecute(result);
                max_thread -= 1;
                if( result == null ) {
                    return;
                }
                if( result.contains("SWS_NOVALID")) {
                    return;
                } else {
                    if( result.contains("SWS_OK")) {
                        String[] arr_output = {};
                        arr_output = result.split("\\|");
                        if( arr_output.length != 2 ) {
                            return;
                        }
                        int anid = Integer.parseInt(arr_output[1]);
                        sws_db.DBJalan_UpdateKirim(anid);
                    }
                    return;
                }
            }
        }

        doUploadJalanAsync doUploadJalanAsync = new doUploadJalanAsync();
        //String urut, String devid, String userid, String waktu, String Cx, String Cy
        //doUploadGPSAsync.execute(id, userid, waktu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            doUploadJalanAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, urut, jenis, kondisi, kebun, waktu, userid, ket, cx, cy,
                    kirim, lebar, dari_server, id_server, asli);
        }  else{
            doUploadJalanAsync.execute(urut, jenis, kondisi, kebun, waktu, userid, ket, cx, cy, kirim, lebar, dari_server, id_server, asli);
        }
    }

    private void doUploadJembatan(
            String urut, String jenis, String kondisi, String kebun, String waktu, String userid, String ket, String cx, String cy,
            String kirim, String lebar, String dari_server, String id_server, String asli) throws InterruptedException {

        class doUploadJembatanAsync extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {
                max_thread += 1;
                String urut = params[0];
                String jenis = params[1];
                String kondisi = params[2];
                String kebun = params[3];
                String waktu = params[4];
                String userid = params[5];
                String ket = params[6];
                String cx = params[7];
                String cy = params[8];
                String kirim = params[9];
                String lebar = params[10];
                String dari_server = params[11];
                String id_server = params[12];
                String asli = params[13];

                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); return "";}
                }

                dbsetting = sws_db.DBSetting_Get();
                String alamat = "http://" + dbsetting.getWebip();
                if( dbsetting.getVd().equals("")) {
                    alamat = alamat + "/mob_upload_jembatan.php";
                } else {
                    alamat = alamat + "/" + dbsetting.getVd() + "/mob_upload_jembatan.php";
                }

                try {
                    URL url = new URL(alamat);
                    Map<String,Object> params1 = new LinkedHashMap<>();
                    params1.put("urut", urut);
                    params1.put("jenis", jenis);
                    params1.put("kondisi", kondisi);
                    params1.put("kebun", kebun);
                    params1.put("waktu", waktu);
                    params1.put("userid", userid);
                    params1.put("ket", ket);
                    params1.put("cx", cx);
                    params1.put("cy", cy);
                    params1.put("kirim", kirim);
                    params1.put("lebar", lebar);
                    params1.put("dari_server", dari_server);
                    params1.put("id_server", id_server);
                    params1.put("asli", asli);

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String,Object> param : params1.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postDataBytes);

                    // read the response
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();

                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append('\n');
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (MalformedURLException e) {
                    Log.e(TAG, "MalformedURLException: " + e.getMessage());
                } catch (ProtocolException e) {
                    Log.e(TAG, "ProtocolException: " + e.getMessage());
                } catch (IOException e) {
                    Log.e(TAG, "IOException: " + e.getMessage());
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected  void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                ctr_upload += 1;
                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                }
                super.onPostExecute(result);
                max_thread -= 1;
                if( result == null ) {
                    return;
                }
                if( result.contains("SWS_NOVALID")) {
                    return;
                } else {
                    if( result.contains("SWS_OK")) {
                        String[] arr_output = {};
                        arr_output = result.split("\\|");
                        if( arr_output.length != 2 ) {
                            return;
                        }
                        int anid = Integer.parseInt(arr_output[1]);
                        sws_db.DBJembatan_UpdateKirim(anid);
                    }
                    return;
                }
            }
        }

        doUploadJembatanAsync doUploadJembatanAsync = new doUploadJembatanAsync();
        //String urut, String devid, String userid, String waktu, String Cx, String Cy
        //doUploadGPSAsync.execute(id, userid, waktu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            doUploadJembatanAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, urut, jenis, kondisi, kebun, waktu, userid, ket, cx, cy,
                    kirim, lebar, dari_server, id_server, asli);
        }  else{
            doUploadJembatanAsync.execute(urut, jenis, kondisi, kebun, waktu, userid, ket, cx, cy, kirim, lebar, dari_server, id_server, asli);
        }
    }

    private void doUploadTanaman(
            String urut, String jenis, String lingkar, String jarak, String tinggi, String banyak, String kebun, String waktu, String userid,
            String afdeling, String blok, String ttanam, String tpanen, String apanen, String bpanen, String spanen, String ket,
            String cx, String cy, String kirim, String dari_server, String id_server, String asli) throws InterruptedException {

        class doUploadTanamanAsync extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {
                max_thread += 1;
                String urut = params[0];
                String jenis = params[1];
                String lingkar = params[2];
                String jarak = params[3];
                String tinggi = params[4];
                String banyak = params[5];
                String kebun = params[6];
                String waktu = params[7];
                String userid = params[8];
                String afdeling = params[9];
                String blok = params[10];
                String ttanam = params[11];
                String tpanen = params[12];
                String apanen = params[13];
                String bpanen = params[14];
                String spanen = params[15];
                String ket = params[16];
                String cx = params[17];
                String cy = params[18];
                String kirim = params[19];
                String dari_server = params[20];
                String id_server = params[21];
                String asli = params[22];

                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); return "";}
                }

                dbsetting = sws_db.DBSetting_Get();
                String alamat = "http://" + dbsetting.getWebip();
                if( dbsetting.getVd().equals("")) {
                    alamat = alamat + "/mob_upload_tanaman.php";
                } else {
                    alamat = alamat + "/" + dbsetting.getVd() + "/mob_upload_tanaman.php";
                }

                try {
                    URL url = new URL(alamat);
                    Map<String,Object> params1 = new LinkedHashMap<>();
                    params1.put("urut", urut);
                    params1.put("jenis", jenis);
                    params1.put("lingkar", lingkar);
                    params1.put("jarak", jarak);
                    params1.put("tinggi", tinggi);
                    params1.put("banyak", banyak);
                    params1.put("kebun", kebun);
                    params1.put("waktu", waktu);
                    params1.put("userid", userid);
                    params1.put("afdeling", afdeling);
                    params1.put("blok", blok);
                    params1.put("ttanam", ttanam);
                    params1.put("tpanen", tpanen);
                    params1.put("apanen", apanen);
                    params1.put("bpanen", bpanen);
                    params1.put("spanen", spanen);
                    params1.put("ket", ket);
                    params1.put("cx", cx);
                    params1.put("cy", cy);
                    params1.put("kirim", kirim);
                    params1.put("dari_server", dari_server);
                    params1.put("id_server", id_server);
                    params1.put("asli", asli);

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String,Object> param : params1.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postDataBytes);

                    // read the response
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();

                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append('\n');
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (MalformedURLException e) {
                    Log.e(TAG, "MalformedURLException: " + e.getMessage());
                } catch (ProtocolException e) {
                    Log.e(TAG, "ProtocolException: " + e.getMessage());
                } catch (IOException e) {
                    Log.e(TAG, "IOException: " + e.getMessage());
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected  void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                ctr_upload += 1;
                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                }
                super.onPostExecute(result);
                max_thread -= 1;
                if( result == null ) {
                    return;
                }
                if( result.contains("SWS_NOVALID")) {
                    return;
                } else {
                    if( result.contains("SWS_OK")) {
                        String[] arr_output = {};
                        arr_output = result.split("\\|");
                        if( arr_output.length != 2 ) {
                            return;
                        }
                        int anid = Integer.parseInt(arr_output[1]);
                        sws_db.DBJembatan_UpdateKirim(anid);
                    }
                    return;
                }
            }
        }

        doUploadTanamanAsync doUploadTanamanAsync = new doUploadTanamanAsync();
        //String urut, String devid, String userid, String waktu, String Cx, String Cy
        //doUploadGPSAsync.execute(id, userid, waktu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            doUploadTanamanAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, urut, jenis, lingkar, jarak, tinggi, banyak, kebun, waktu,
                    userid, afdeling, blok, ttanam, tpanen, apanen, bpanen, spanen, ket, cx, cy, kirim, dari_server, id_server, asli);
        }  else{
            doUploadTanamanAsync.execute(urut, jenis, lingkar, jarak, tinggi, banyak, kebun, waktu, userid, afdeling, blok, ttanam, tpanen,
                    apanen, bpanen, spanen, ket, cx, cy, kirim, dari_server, id_server, asli);
        }
    }

    private void doUploadSaluran(
            String urut, String jenis, String kondisi, String kebun, String waktu, String userid, String ket, String cx, String cy,
            String kirim, String lebar, String dari_server, String id_server, String asli) throws InterruptedException {

        class doUploadSaluranAsync extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {
                max_thread += 1;
                String urut = params[0];
                String jenis = params[1];
                String kondisi = params[2];
                String kebun = params[3];
                String waktu = params[4];
                String userid = params[5];
                String ket = params[6];
                String cx = params[7];
                String cy = params[8];
                String kirim = params[9];
                String lebar = params[10];
                String dari_server = params[11];
                String id_server = params[12];
                String asli = params[13];

                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); return "";}
                }

                dbsetting = sws_db.DBSetting_Get();
                String alamat = "http://" + dbsetting.getWebip();
                if( dbsetting.getVd().equals("")) {
                    alamat = alamat + "/mob_upload_saluran.php";
                } else {
                    alamat = alamat + "/" + dbsetting.getVd() + "/mob_upload_saluran.php";
                }

                try {
                    URL url = new URL(alamat);
                    Map<String,Object> params1 = new LinkedHashMap<>();
                    params1.put("urut", urut);
                    params1.put("jenis", jenis);
                    params1.put("kondisi", kondisi);
                    params1.put("kebun", kebun);
                    params1.put("waktu", waktu);
                    params1.put("userid", userid);
                    params1.put("ket", ket);
                    params1.put("cx", cx);
                    params1.put("cy", cy);
                    params1.put("kirim", kirim);
                    params1.put("lebar", lebar);
                    params1.put("dari_server", dari_server);
                    params1.put("id_server", id_server);
                    params1.put("asli", asli);

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String,Object> param : params1.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postDataBytes);

                    // read the response
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();

                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append('\n');
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (MalformedURLException e) {
                    Log.e(TAG, "MalformedURLException: " + e.getMessage());
                } catch (ProtocolException e) {
                    Log.e(TAG, "ProtocolException: " + e.getMessage());
                } catch (IOException e) {
                    Log.e(TAG, "IOException: " + e.getMessage());
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected  void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                ctr_upload += 1;
                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                }
                super.onPostExecute(result);
                max_thread -= 1;
                if( result == null ) {
                    return;
                }
                if( result.contains("SWS_NOVALID")) {
                    return;
                } else {
                    if( result.contains("SWS_OK")) {
                        String[] arr_output = {};
                        arr_output = result.split("\\|");
                        if( arr_output.length != 2 ) {
                            return;
                        }
                        int anid = Integer.parseInt(arr_output[1]);
                        sws_db.DBSaluran_UpdateKirim(anid);
                    }
                    return;
                }
            }
        }

        doUploadSaluranAsync doUploadSaluranAsync = new doUploadSaluranAsync();
        //String urut, String devid, String userid, String waktu, String Cx, String Cy
        //doUploadGPSAsync.execute(id, userid, waktu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            doUploadSaluranAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, urut, jenis, kondisi, kebun, waktu, userid, ket, cx, cy,
                    kirim, lebar, dari_server, id_server, asli);
        }  else{
            doUploadSaluranAsync.execute(urut, jenis, kondisi, kebun, waktu, userid, ket, cx, cy, kirim, lebar, dari_server, id_server, asli);
        }
    }

    //String.valueOf(afot.getID()), String.valueOf(afot.getUserid()), afot.getNfile(), afot.getCx(), afot.getCy(),
    //                                        String.valueOf(afot.getKirim()), afot.getWaktu(), afot.getKet(), String.valueOf(afot.getTampil()), String.valueOf(afot.getJenis()),
    //                                        String.valueOf(afot.getDariServer()), String.valueOf(afot.getIDServer()), String.valueOf(afot.getAsli());
    private void doUploadFoto(
            String id, String userid, String nfile, String cx, String cy, String kirim, String waktu, String ket, String tampil,
            String jenis, String dari_server, String id_server, String asli) throws InterruptedException {

        class doUploadFotoAsync extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {
                max_thread += 1;
                String id = params[0];
                String userid = params[1];
                String nfile = params[2];
                String cx = params[3];
                String cy = params[4];
                String kirim = params[5];
                String waktu = params[6];
                String ket = params[7];
                String tampil = params[8];
                String jenis = params[9];
                String dari_server = params[10];
                String id_server = params[11];
                String asli = params[12];

                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); return "";}
                }

                dbsetting = sws_db.DBSetting_Get();
                String alamat = "http://" + dbsetting.getWebip();
                if( dbsetting.getVd().equals("")) {
                    alamat = alamat + "/mob_upload_foto.php";
                } else {
                    alamat = alamat + "/" + dbsetting.getVd() + "/mob_upload_foto.php";
                }

                try {
                    URL url = new URL(alamat);
                    Map<String,Object> params1 = new LinkedHashMap<>();
                    params1.put("id", id);
                    params1.put("userid", userid);
                    params1.put("nfile", nfile);
                    params1.put("cx", cx);
                    params1.put("cy", cy);
                    params1.put("kirim", kirim);
                    params1.put("waktu", waktu);
                    params1.put("ket", ket);
                    params1.put("tampil", tampil);
                    params1.put("jenis", jenis);
                    params1.put("dari_server", dari_server);
                    params1.put("id_server", id_server);
                    params1.put("asli", asli);

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String,Object> param : params1.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postDataBytes);

                    // read the response
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();

                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append('\n');
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (MalformedURLException e) {
                    Log.e(TAG, "MalformedURLException: " + e.getMessage());
                } catch (ProtocolException e) {
                    Log.e(TAG, "ProtocolException: " + e.getMessage());
                } catch (IOException e) {
                    Log.e(TAG, "IOException: " + e.getMessage());
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected  void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                ctr_upload += 1;
                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                }
                super.onPostExecute(result);
                max_thread -= 1;
                if( result == null ) {
                    return;
                }
                if( result.contains("SWS_NOVALID")) {
                    return;
                } else {
                    if( result.contains("SWS_OK")) {
                        String[] arr_output = {};
                        arr_output = result.split("\\|");
                        if( arr_output.length != 2 ) {
                            return;
                        }
                        int anid = Integer.parseInt(arr_output[1]);
                        sws_db.DBSaluran_UpdateKirim(anid);
                    }
                    return;
                }
            }
        }

        doUploadFotoAsync doUploadFotoAsync = new doUploadFotoAsync();
        //String urut, String devid, String userid, String waktu, String Cx, String Cy
        //doUploadGPSAsync.execute(id, userid, waktu);
        //String id, String userid, String nfile, String cx, String cy, String kirim, String waktu, String ket, String tampil,
        //            String jenis, String dari_server, String id_server, String asli
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            doUploadFotoAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, id, userid, nfile, cx, cy, kirim, waktu, ket, tampil,
                    jenis, dari_server, id_server, asli);
        }  else{
            doUploadFotoAsync.execute(id, userid, nfile, cx, cy, kirim, waktu, ket, tampil,
                    jenis, dari_server, id_server, asli);
        }
    }

    //String.valueOf(aabs.getIDRow()), aabs.getIDPek(), String.valueOf(aabs.getIDMandor()), aabs.getTgl(),
    //                                        aabs.getJamb(), aabs.getJamp(), aabs.getLokasi(), aabs.getFotob(), aabs.getFotop(),
    //                                        String.valueOf(aabs.getAdab()), String.valueOf(aabs.getAdap()), aabs.getCXB(), aabs.getCYB(),
    //                                        aabs.getCXP(), aabs.getCYP(), aabs.getWaktub(), aabs.getWaktup(), String.valueOf(aabs.getKirim()));
    private void doUploadAbsensi(
            String idrow, String idpek, String idmandor, String tgl, String jamb, String jamp, String lokasi, String fotob, String fotop,
            String adab, String adap, String cxb, String cyb, String cxp, String cyp, String waktub, String waktup, String kirim) throws InterruptedException {

        class doUploadAbsensiAsync extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {
                max_thread += 1;
                String idrow = params[0];
                String idpek = params[1];
                String idmandor = params[2];
                String tgl = params[3];
                String jamb = params[4];
                String jamp = params[5];
                String lokasi = params[6];
                String fotob = params[7];
                String fotop = params[8];
                String adab = params[9];
                String adap = params[10];
                String cxb = params[11];
                String cyb = params[12];
                String cxp = params[13];
                String cyp = params[14];
                String waktub = params[15];
                String waktup = params[16];
                String kirim = params[17];

                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); return "";}
                }

                dbsetting = sws_db.DBSetting_Get();
                String alamat = "http://" + dbsetting.getWebip();
                if( dbsetting.getVd().equals("")) {
                    alamat = alamat + "/mob_upload_absensi.php";
                } else {
                    alamat = alamat + "/" + dbsetting.getVd() + "/mob_upload_absensi.php";
                }

                try {
                    URL url = new URL(alamat);
                    Map<String,Object> params1 = new LinkedHashMap<>();
                    params1.put("idrow", idrow);
                    params1.put("idpek", idpek);
                    params1.put("idmandor", idmandor);
                    params1.put("tgl", tgl);
                    params1.put("jamb", jamb);
                    params1.put("jamp", jamp);
                    params1.put("lokasi", lokasi);
                    params1.put("fotob", fotob);
                    params1.put("fotop", fotop);
                    params1.put("adab", adab);
                    params1.put("adap", adap);
                    params1.put("cxb", cxb);
                    params1.put("cyb", cyb);
                    params1.put("cxp", cyb);
                    params1.put("cyp", cyb);
                    params1.put("waktub", waktub);
                    params1.put("waktup", waktup);
                    params1.put("kirim", kirim);

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String,Object> param : params1.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postDataBytes);

                    // read the response
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();

                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append('\n');
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (MalformedURLException e) {
                    Log.e(TAG, "MalformedURLException: " + e.getMessage());
                } catch (ProtocolException e) {
                    Log.e(TAG, "ProtocolException: " + e.getMessage());
                } catch (IOException e) {
                    Log.e(TAG, "IOException: " + e.getMessage());
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected  void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                ctr_upload += 1;
                if(progressdialog.isShowing()) {
                    progressdialog.setProgress(ctr_upload);
                    if( ctr_upload >= total_jml) { progressdialog.dismiss(); }
                }
                super.onPostExecute(result);
                max_thread -= 1;
                if( result == null ) {
                    return;
                }
                if( result.contains("SWS_NOVALID")) {
                    return;
                } else {
                    if( result.contains("SWS_OK")) {
                        String[] arr_output = {};
                        arr_output = result.split("\\|");
                        if( arr_output.length != 2 ) {
                            return;
                        }
                        int anid = Integer.parseInt(arr_output[1]);
                        sws_db.DBSaluran_UpdateKirim(anid);
                    }
                    return;
                }
            }
        }

        doUploadAbsensiAsync doUploadAbsensiAsync = new doUploadAbsensiAsync();
        //String urut, String devid, String userid, String waktu, String Cx, String Cy
        //doUploadGPSAsync.execute(id, userid, waktu);
        //String idrow, String idpek, String idmandor, String tgl, String jamb, String jamp, String lokasi, String fotob, String fotop,
        //            String adab, String adap, String cxb, String cyb, String cxp, String cyp, String waktub, String waktup, String kirim
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            doUploadAbsensiAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, idrow, idpek, idmandor, tgl, jamb, jamp, lokasi, fotob, fotop,
                    adab, adap, cxb, cyb, cxp, cyp, waktub, waktup, kirim);
        }  else{
            doUploadAbsensiAsync.execute(idrow, idpek, idmandor, tgl, jamb, jamp, lokasi, fotob, fotop,
                    adab, adap, cxb, cyb, cxp, cyp, waktub, waktup, kirim);
        }
    }

}

