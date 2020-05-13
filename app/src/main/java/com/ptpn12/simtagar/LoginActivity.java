package com.ptpn12.simtagar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    Button b1,b2;
    EditText ed1,ed2;
    MySQLLiteHelper sws_db;
    DB_Setting dbsetting;
    ProgressDialog progressDialog;

    TextView tx1;
    int counter = 3;
    private String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_login);

        if( getIntent().getBooleanExtra("SWS_EXIT", false)) {
            getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            LoginActivity.this.finish();
            return;
        }

        sws_db = new MySQLLiteHelper(getApplicationContext());
        dbsetting = sws_db.DBSetting_Get();

        if( dbsetting.getActiveUser() != 0 ) {
            Intent intent;
            intent = new Intent(LoginActivity.this, MapsActivity.class);
            startActivity(intent);
            return;
        }

        b1 = (Button)findViewById(R.id.login_proses);
        ed1 = (EditText)findViewById(R.id.login_user);
        ed2 = (EditText)findViewById(R.id.login_pwd);

        //b2 = (Button)findViewById(R.id.login_cancel);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dum = ed1.getText().toString().trim();
                if( dum.equals("")) {
                    Toast.makeText(getApplicationContext(),"Isikan nama pengguna",Toast.LENGTH_SHORT).show();
                    return;
                }
                dum = ed2.getText().toString().trim();
                if( dum.equals("")) {
                    Toast.makeText(getApplicationContext(),"Isikan kata sandi dahulu",Toast.LENGTH_SHORT).show();
                    return;
                }
                new doLogin().execute();
            }
        });

        //b2.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        finish();
        //    }
        //});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.loginactivitymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting:
                // do your code
                Intent intent = new Intent(getApplicationContext(), frm_setting.class);
                startActivityForResult(intent, 7001);
                return true;
            case R.id.menu_keluar:
                // do your code
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class doLogin extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Sedang melakukan verifikasi pengguna..."); // Setting Message
            progressDialog.setTitle("SIMTAGAR"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(true);
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            String jsonStr = null;
            String alamat = "http://" + dbsetting.getWebip();
            if( dbsetting.getVd().equals("")) {
                alamat = alamat + "/mob_login.php";
            } else {
                alamat = alamat + "/" + dbsetting.getVd() + "/mob_login.php";
            }
            String nuser = ed1.getText().toString();
            String pwd = ed2.getText().toString();
            try {
                URL url = new URL(alamat);
                Map<String,Object> params = new LinkedHashMap<>();
                params.put("nama", nuser);
                params.put("pwd", pwd);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String,Object> param : params.entrySet()) {
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
                jsonStr = sb.toString();
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray sws_data = jsonObj.getJSONArray("data");

                    // looping through All Contacts
                    for (int i = 0; i < sws_data.length(); i++) {
                        JSONObject c = sws_data.getJSONObject(i);
                        int stat = c.getInt("status");
                        if( stat == 1 ) {
                            //berhasil masuk
                            String id = c.getString("id");
                            String idpeg = c.getString("idpeg");
                            String nama = c.getString("nama");
                            String idkebun = c.getString("idkebun");
                            String tingkat = c.getString("tingkat");
                            String jabatan = c.getString("jabatan");

                            sws_db.DBUser_Delete_All();
                            sws_db.DBUser_Add(Integer.parseInt(id),idpeg,nama, "","","",
                                    Integer.parseInt(idkebun),"",Integer.parseInt(tingkat), jabatan);
                            sws_db.DBSetting_UpdateUser(Integer.parseInt(id), Integer.parseInt(idkebun));
                            Log.e("LOGIN", "KEBUN BARU --> " + idkebun);

                            JSONArray ref_afdeling = c.getJSONArray("ref_afdeling");
                            for( int j=0; j < ref_afdeling.length(); j++ ) {
                                JSONObject d = ref_afdeling.getJSONObject(j);
                                idkebun = d.getString("idkebun");
                                String idafd = d.getString("idafd");
                                nama = d.getString("nama");
                                List<DB_Ref_Afdeling> setobj = new LinkedList<DB_Ref_Afdeling>();
                                setobj = sws_db.DBRefAfdeling_Get(Integer.parseInt(idkebun), Integer.parseInt(idafd));
                                if( setobj.size() > 0) {
                                    sws_db.DBRefAfdeling_Update(Integer.parseInt(idkebun), Integer.parseInt(idafd), nama);
                                } else {
                                    sws_db.DBRefAfdeling_Add(Integer.parseInt(idkebun), Integer.parseInt(idafd), nama);
                                }
                            }
                            JSONArray ref_aktifitas = c.getJSONArray("ref_aktifitas");
                            for( int j=0; j < ref_aktifitas.length(); j++ ) {
                                JSONObject d = ref_aktifitas.getJSONObject(j);
                                String kode = d.getString("kode");
                                nama = d.getString("nama");
                                List<DB_Ref_Aktifitas> setobj = new LinkedList<DB_Ref_Aktifitas>();
                                setobj = sws_db.DBRefAktifitas_Get(Integer.parseInt(kode));
                                if( setobj.size() > 0) {
                                    sws_db.DBRefAktifitas_Update(Integer.parseInt(kode), nama);
                                } else {
                                    sws_db.DBRefAktifitas_Add(Integer.parseInt(kode), nama);
                                }
                            }
                            JSONArray ref_jalan = c.getJSONArray("ref_jalan");
                            for( int j=0; j < ref_jalan.length(); j++ ) {
                                JSONObject d = ref_jalan.getJSONObject(j);
                                String kode = d.getString("kode");
                                nama = d.getString("nama");
                                List<DB_Ref_Jalan> setobj = new LinkedList<DB_Ref_Jalan>();
                                setobj = sws_db.DBRefJalan_Get(Integer.parseInt(kode));
                                if( setobj.size() > 0) {
                                    sws_db.DBRefJalan_Update(Integer.parseInt(kode), nama);
                                } else {
                                    sws_db.DBRefJalan_Add(Integer.parseInt(kode), nama);
                                }
                            }
                            JSONArray ref_jalan_kondisi = c.getJSONArray("ref_jalan_kondisi");
                            for( int j=0; j < ref_jalan_kondisi.length(); j++ ) {
                                JSONObject d = ref_jalan_kondisi.getJSONObject(j);
                                String kode = d.getString("kode");
                                nama = d.getString("nama");
                                List<DB_Ref_Jalan_Kondisi> setobj = new LinkedList<DB_Ref_Jalan_Kondisi>();
                                setobj = sws_db.DBRefJalanKondisi_Get(Integer.parseInt(kode));
                                if( setobj.size() > 0) {
                                    sws_db.DBRefJalanKondisi_Update(Integer.parseInt(kode), nama);
                                } else {
                                    sws_db.DBRefJalanKondisi_Add(Integer.parseInt(kode), nama);
                                }
                            }
                            JSONArray ref_jembatan = c.getJSONArray("ref_jembatan");
                            for( int j=0; j < ref_jembatan.length(); j++ ) {
                                JSONObject d = ref_jembatan.getJSONObject(j);
                                String kode = d.getString("kode");
                                nama = d.getString("nama");
                                List<DB_Ref_Jembatan> setobj = new LinkedList<DB_Ref_Jembatan>();
                                setobj = sws_db.DBRefJembatan_Get(Integer.parseInt(kode));
                                if( setobj.size() > 0) {
                                    sws_db.DBRefJembatan_Update(Integer.parseInt(kode), nama);
                                } else {
                                    sws_db.DBRefJembatan_Add(Integer.parseInt(kode), nama);
                                }
                            }
                            JSONArray ref_jembatan_kondisi = c.getJSONArray("ref_jembatan_kondisi");
                            for( int j=0; j < ref_jembatan_kondisi.length(); j++ ) {
                                JSONObject d = ref_jembatan_kondisi.getJSONObject(j);
                                String kode = d.getString("kode");
                                nama = d.getString("nama");
                                List<DB_Ref_Jembatan_Kondisi> setobj = new LinkedList<DB_Ref_Jembatan_Kondisi>();
                                setobj = sws_db.DBRefJembatanKondisi_Get(Integer.parseInt(kode));
                                if( setobj.size() > 0) {
                                    sws_db.DBRefJembatanKondisi_Update(Integer.parseInt(kode), nama);
                                } else {
                                    sws_db.DBRefJembatanKondisi_Add(Integer.parseInt(kode), nama);
                                }
                            }
                            JSONArray ref_kebun = c.getJSONArray("ref_kebun");
                            for( int j=0; j < ref_kebun.length(); j++ ) {
                                JSONObject d = ref_kebun.getJSONObject(j);
                                String kode = d.getString("kode");
                                nama = d.getString("nama");
                                List<DB_Ref_Kebun> setobj = new LinkedList<DB_Ref_Kebun>();
                                setobj = sws_db.DBRefKebun_Get(Integer.parseInt(kode));
                                if( setobj.size() > 0) {
                                    sws_db.DBRefKebun_Update(Integer.parseInt(kode), nama);
                                } else {
                                    sws_db.DBRefKebun_Add(Integer.parseInt(kode), nama);
                                }
                            }
                            JSONArray ref_kejadian = c.getJSONArray("ref_kejadian");
                            for( int j=0; j < ref_kejadian.length(); j++ ) {
                                JSONObject d = ref_kejadian.getJSONObject(j);
                                String kode = d.getString("kode");
                                nama = d.getString("nama");
                                List<DB_Ref_Kejadian> setobj = new LinkedList<DB_Ref_Kejadian>();
                                setobj = sws_db.DBRefKejadian_Get(Integer.parseInt(kode));
                                if( setobj.size() > 0) {
                                    sws_db.DBRefKejadian_Update(Integer.parseInt(kode), nama);
                                } else {
                                    sws_db.DBRefKejadian_Add(Integer.parseInt(kode), nama);
                                }
                            }
                            JSONArray ref_komoditi = c.getJSONArray("ref_komoditi");
                            for( int j=0; j < ref_komoditi.length(); j++ ) {
                                JSONObject d = ref_komoditi.getJSONObject(j);
                                String kode = d.getString("kode");
                                nama = d.getString("nama");
                                List<DB_Ref_Komoditi> setobj = new LinkedList<DB_Ref_Komoditi>();
                                setobj = sws_db.DBRefKomoditi_Get(Integer.parseInt(kode));
                                if( setobj.size() > 0) {
                                    sws_db.DBRefKomoditi_Update(Integer.parseInt(kode), nama);
                                } else {
                                    sws_db.DBRefKomoditi_Add(Integer.parseInt(kode), nama);
                                }
                            }
                            JSONArray ref_obyek = c.getJSONArray("ref_obyek");
                            for( int j=0; j < ref_obyek.length(); j++ ) {
                                JSONObject d = ref_obyek.getJSONObject(j);
                                String kode = d.getString("kode");
                                nama = d.getString("nama");
                                List<DB_Ref_Obyek> setobj = new LinkedList<DB_Ref_Obyek>();
                                setobj = sws_db.DBRefObyek_Get(Integer.parseInt(kode));
                                if( setobj.size() > 0) {
                                    sws_db.DBRefObyek_Update(Integer.parseInt(kode), nama);
                                } else {
                                    sws_db.DBRefObyek_Add(Integer.parseInt(kode), nama);
                                }
                            }
                            JSONArray ref_patok = c.getJSONArray("ref_patok");
                            for( int j=0; j < ref_patok.length(); j++ ) {
                                JSONObject d = ref_patok.getJSONObject(j);
                                String kode = d.getString("kode");
                                nama = d.getString("nama");
                                List<DB_Ref_Patok> setobj = new LinkedList<DB_Ref_Patok>();
                                setobj = sws_db.DBRefPatok_Get(Integer.parseInt(kode));
                                if( setobj.size() > 0) {
                                    sws_db.DBRefPatok_Update(Integer.parseInt(kode), nama);
                                } else {
                                    sws_db.DBRefPatok_Add(Integer.parseInt(kode), nama);
                                }
                            }
                            JSONArray ref_pekerjaan = c.getJSONArray("ref_pekerjaan");
                            for( int j=0; j < ref_pekerjaan.length(); j++ ) {
                                JSONObject d = ref_pekerjaan.getJSONObject(j);
                                String kode = d.getString("kode");
                                nama = d.getString("nama");
                                List<DB_Ref_Pekerjaan> setobj = new LinkedList<DB_Ref_Pekerjaan>();
                                setobj = sws_db.DBRefPekerjaan_Get(Integer.parseInt(kode));
                                if( setobj.size() > 0) {
                                    sws_db.DBRefPekerjaan_Update(Integer.parseInt(kode), nama);
                                } else {
                                    sws_db.DBRefPekerjaan_Add(Integer.parseInt(kode), nama);
                                }
                            }
                            JSONArray ref_saluran = c.getJSONArray("ref_saluran");
                            for( int j=0; j < ref_saluran.length(); j++ ) {
                                JSONObject d = ref_saluran.getJSONObject(j);
                                String kode = d.getString("kode");
                                nama = d.getString("nama");
                                List<DB_Ref_Saluran> setobj = new LinkedList<DB_Ref_Saluran>();
                                setobj = sws_db.DBRefSaluran_Get(Integer.parseInt(kode));
                                if( setobj.size() > 0) {
                                    sws_db.DBRefSaluran_Update(Integer.parseInt(kode), nama);
                                } else {
                                    sws_db.DBRefSaluran_Add(Integer.parseInt(kode), nama);
                                }
                            }
                            JSONArray ref_saluran_kondisi = c.getJSONArray("ref_saluran_kondisi");
                            for( int j=0; j < ref_saluran_kondisi.length(); j++ ) {
                                JSONObject d = ref_saluran_kondisi.getJSONObject(j);
                                String kode = d.getString("kode");
                                nama = d.getString("nama");
                                List<DB_Ref_Saluran_Kondisi> setobj = new LinkedList<DB_Ref_Saluran_Kondisi>();
                                setobj = sws_db.DBRefSaluranKondisi_Get(Integer.parseInt(kode));
                                if( setobj.size() > 0) {
                                    sws_db.DBRefSaluranKondisi_Update(Integer.parseInt(kode), nama);
                                } else {
                                    sws_db.DBRefSaluranKondisi_Add(Integer.parseInt(kode), nama);
                                }
                            }
                            JSONArray kmls = c.getJSONArray("kml");
                            for( int j=0; j < kmls.length(); j++ ) {
                                JSONObject d = kmls.getJSONObject(j);
                                nama = d.getString("nama");
                                String idkebuns = d.getString("idkebun");
                                String idrow = d.getString("idrow");
                                String idtoc = d.getString("idtoc");
                                String idpeta = d.getString("idpeta");
                                String folder = d.getString("folder");

                                List<DB_KML> setobj = new LinkedList<DB_KML>();
                                setobj = sws_db.DBKML_GetFromDB(Integer.parseInt(idtoc), Integer.parseInt(idpeta));
                                if( setobj.size() > 0) {
                                    int dumid = setobj.get(0).getID();
                                    int dumtampil = setobj.get(0).getTampil();
                                    int dumsudah = setobj.get(0).getSudah();
                                    sws_db.DBKML_Update(dumid, nama, Integer.parseInt(idkebuns), Integer.parseInt(idrow),
                                            Integer.parseInt(idtoc), Integer.parseInt(idpeta), folder, dumtampil, dumsudah);
                                } else {
                                    sws_db.DBKML_Add(nama, Integer.parseInt(idkebuns), Integer.parseInt(idrow),
                                            Integer.parseInt(idtoc), Integer.parseInt(idpeta), folder, 0, 0);
                                }
                            }

                            JSONArray js_refjab = c.getJSONArray("ref_jab");
                            if( js_refjab != null ) {
                                for (int j = 0; j < js_refjab.length(); j++) {
                                    JSONObject d = js_refjab.getJSONObject(j);
                                    int jab_id = Integer.parseInt(d.getString("id"));
                                    String jab_nama = d.getString("nama");
                                    List<DB_Ref_Jabatan> setobj = new LinkedList<DB_Ref_Jabatan>();
                                    setobj = sws_db.DBRefJabatan_Get(jab_id);
                                    if (setobj.size() > 0) {
                                        sws_db.DBRefJabatan_Delete(jab_id);
                                    }
                                    sws_db.DBRefJabatan_Add(jab_id, jab_nama);
                                }
                            }

                            JSONArray js_refpekerja = c.getJSONArray("pekerja");
                            if( js_refpekerja != null ) {
                                for (int j = 0; j < js_refpekerja.length(); j++) {
                                    JSONObject d = js_refpekerja.getJSONObject(j);
                                    int pekerja_urut = Integer.parseInt(d.getString("urut"));
                                    int pekerja_idkebun = Integer.parseInt(d.getString("idkebun"));
                                    int pekerja_idafd = Integer.parseInt(d.getString("idafd"));
                                    String pekerja_idpeg = d.getString("idpeg");
                                    int pekerja_idmandor = Integer.parseInt(d.getString("idmandor"));
                                    String pekerja_nama = d.getString("nama");
                                    int pekerja_jab = Integer.parseInt(d.getString("jab"));

                                    List<DB_Pekerja> setobj = new LinkedList<DB_Pekerja>();
                                    setobj = sws_db.DBPekerja_Get(pekerja_urut, pekerja_idkebun, pekerja_idafd, pekerja_idpeg, pekerja_idmandor);
                                    if (setobj.size() > 0) {
                                        //int pekerja_urut = setobj.get(0).getUrut();
                                        sws_db.DBPekerja_Update(pekerja_urut,pekerja_idkebun, pekerja_idafd, pekerja_idmandor, pekerja_idpeg, pekerja_nama, pekerja_jab);
                                    } else {
                                        sws_db.DBPekerja_Add(pekerja_urut, pekerja_idkebun, pekerja_idafd, pekerja_idmandor, pekerja_idpeg, pekerja_nama, pekerja_jab);
                                    }
                                }
                            }

                            JSONArray js_akt = c.getJSONArray("aktifitas");
                            if( js_akt != null ) {
                                //sws_db.DBAktifitas_Add(kebun,jenis,alama,abiaya,asdm,aket,acx, acy, logdate,userid,0,0,0);
                                for (int j = 0; j < js_akt.length(); j++) {
                                    JSONObject d = js_akt.getJSONObject(j);
                                    String akt_jenis = d.getString("jenis");
                                    String akt_lama = d.getString("lama");
                                    String akt_biaya = d.getString("biaya");
                                    String akt_sdm = d.getString("sdm");
                                    String akt_cx = d.getString("x");
                                    String akt_cy = d.getString("y");
                                    String akt_waktu = d.getString("waktu");
                                    String akt_userid = d.getString("userid");
                                    String akt_id_server = d.getString("id");
                                    String akt_ket = d.getString("ket");
                                    List<DB_Aktifitas> setobj = new LinkedList<DB_Aktifitas>();
                                    setobj = sws_db.DBAktifitas_CheckDariServer(Integer.parseInt(akt_id_server));
                                    if (setobj.size() > 0) {
                                        int akt_urut = setobj.get(0).getUrut();
                                        sws_db.DBAktifitas_Delete(akt_urut);
                                    }
                                    sws_db.DBAktifitas_Add(Integer.parseInt(idkebun), Integer.parseInt(akt_jenis), akt_lama, akt_biaya, akt_sdm, akt_ket,
                                            akt_cx, akt_cy, akt_waktu, Integer.parseInt(akt_userid), 1, 1, Integer.parseInt(akt_id_server), 2);
                                }
                            }
                            JSONArray js_kej = c.getJSONArray("kejadian");
                            if( js_kej != null ) {
                                for (int j = 0; j < js_kej.length(); j++) {
                                    JSONObject d = js_kej.getJSONObject(j);
                                    String kej_jenis = d.getString("jenis");
                                    String kej_ket = d.getString("ket");
                                    String kej_cx = d.getString("x");
                                    String kej_cy = d.getString("y");
                                    String kej_waktu = d.getString("waktu");
                                    String kej_userid = d.getString("userid");
                                    String kej_id_server = d.getString("id");
                                    List<DB_Kejadian> setobj = new LinkedList<DB_Kejadian>();
                                    setobj = sws_db.DBKejadian_CheckDariServer(Integer.parseInt(kej_id_server));
                                    if (setobj.size() > 0) {
                                        int kej_urut = setobj.get(0).getUrut();
                                        sws_db.DBKejadian_Delete(kej_urut);
                                    }
                                    //kebun, jenis, aket, acx, acy, logdate, userid, 0,0,0);
                                    sws_db.DBKejadian_Add(Integer.parseInt(idkebun), Integer.parseInt(kej_jenis), kej_ket, kej_cx, kej_cy, kej_waktu,
                                            Integer.parseInt(kej_userid), 1, 1, Integer.parseInt(kej_id_server), 2);
                                }
                            }
                            JSONArray js_patok = c.getJSONArray("patok");
                            if( js_patok != null ) {
                                for (int j = 0; j < js_patok.length(); j++) {
                                    JSONObject d = js_patok.getJSONObject(j);
                                    String pat_jenis = d.getString("jenis");
                                    String pat_nama = d.getString("nama");
                                    String pat_cx = d.getString("x");
                                    String pat_cy = d.getString("y");
                                    String pat_waktu = d.getString("waktu");
                                    String pat_ket = d.getString("ket");
                                    String pat_userid = d.getString("userid");
                                    String pat_id_server = d.getString("id");
                                    List<DB_Patok> setobj = new LinkedList<DB_Patok>();
                                    setobj = sws_db.DBPatok_CheckDariServer(Integer.parseInt(pat_id_server));
                                    if (setobj.size() > 0) {
                                        int pat_urut = setobj.get(0).getUrut();
                                        sws_db.DBPatok_Delete(pat_urut);
                                    }
                                    //(anama, jenis, kebun, acx, acy, logdate, userid, 0, aket,0,0);
                                    sws_db.DBPatok_Add(pat_nama, Integer.parseInt(pat_jenis), Integer.parseInt(idkebun), pat_cx, pat_cy, pat_waktu,
                                            Integer.parseInt(pat_userid), 1, pat_ket, 1, Integer.parseInt(pat_id_server), 2);
                                }
                            }
                            JSONArray js_jalan = c.getJSONArray("jalan");
                            if( js_jalan != null ) {
                                for (int j = 0; j < js_jalan.length(); j++) {
                                    JSONObject d = js_jalan.getJSONObject(j);
                                    String jal_jenis = d.getString("jenis");
                                    String jal_kondisi = d.getString("kondisi");
                                    String jal_waktu = d.getString("waktu");
                                    String jal_userid = d.getString("userid");
                                    String jal_ket = d.getString("ket");
                                    String jal_cx = d.getString("x");
                                    String jal_cy = d.getString("y");
                                    String jal_lebar = d.getString("lebar");
                                    String jal_id_server = d.getString("id");
                                    List<DB_Jalan> setobj = new LinkedList<DB_Jalan>();
                                    setobj = sws_db.DBJalan_CheckDariServer(Integer.parseInt(jal_id_server));
                                    if (setobj.size() > 0) {
                                        int jal_urut = setobj.get(0).getUrut();
                                        sws_db.DBJalan_Delete(jal_urut, 99, 0);
                                    }
                                    //jenis, kondisi, kebun, logdate, userid, aket, acx, acy, 0, alebar,0,0
                                    sws_db.DBJalan_Add(
                                            Integer.parseInt(jal_jenis), Integer.parseInt(jal_kondisi), Integer.parseInt(idkebun), jal_waktu,
                                            Integer.parseInt(jal_userid), jal_ket, jal_cx, jal_cy, 1, jal_lebar,
                                            1, Integer.parseInt(jal_id_server), 2);
                                }
                            }

                            JSONArray js_jembatan = c.getJSONArray("jembatan");
                            if( js_jembatan != null ) {
                                for (int j = 0; j < js_jembatan.length(); j++) {
                                    JSONObject d = js_jembatan.getJSONObject(j);
                                    String jem_jenis = d.getString("jenis");
                                    String jem_kondisi = d.getString("kondisi");
                                    String jem_waktu = d.getString("waktu");
                                    String jem_userid = d.getString("userid");
                                    String jem_ket = d.getString("ket");
                                    String jem_cx = d.getString("x");
                                    String jem_cy = d.getString("y");
                                    String jem_lebar = d.getString("lebar");
                                    String jem_id_server = d.getString("id");
                                    List<DB_Jembatan> setobj = new LinkedList<DB_Jembatan>();
                                    setobj = sws_db.DBJembatan_CheckDariServer(Integer.parseInt(jem_id_server));
                                    if (setobj.size() > 0) {
                                        int jem_urut = setobj.get(0).getUrut();
                                        sws_db.DBJalan_Delete(jem_urut, 99, 0);
                                    }
                                    //jenis, kondisi, kebun, logdate, userid, aket, acx, acy, 0, alebar,0,0
                                    sws_db.DBJembatan_Add(
                                            Integer.parseInt(jem_jenis), Integer.parseInt(jem_kondisi), Integer.parseInt(idkebun), jem_waktu,
                                            Integer.parseInt(jem_userid), jem_ket, jem_cx, jem_cy, 1, jem_lebar,
                                            1, Integer.parseInt(jem_id_server), 2);
                                }
                            }

                            JSONArray js_tanaman = c.getJSONArray("tanaman");
                            if( js_tanaman != null ) {
                                for (int j = 0; j < js_tanaman.length(); j++) {
                                    JSONObject d = js_tanaman.getJSONObject(j);
                                    String tan_jenis = d.getString("jenis");
                                    String tan_lingkar = d.getString("lingkar");
                                    String tan_jarak = d.getString("jarak");
                                    String tan_tinggi = d.getString("tinggi");
                                    String tan_banyaki = d.getString("banyak");
                                    String tan_waktu = d.getString("waktu");
                                    String tan_userid = d.getString("userid");
                                    String tan_afd = d.getString("afd");
                                    String tan_blok = d.getString("blok");
                                    String tan_ttanami = d.getString("ttanam");
                                    String tan_tpaneni = d.getString("tpanen");
                                    String tan_vapanen = d.getString("apanen");
                                    String tan_vbpanen = d.getString("bpanen");
                                    String tan_vspanen = d.getString("spanen");
                                    String tan_ket = d.getString("ket");
                                    String tan_cx = d.getString("x");
                                    String tan_cy = d.getString("y");
                                    String tan_id_server = d.getString("id");
                                    List<DB_Tanaman> setobj = new LinkedList<DB_Tanaman>();
                                    setobj = sws_db.DBTanaman_CheckDariServer(Integer.parseInt(tan_id_server));
                                    if (setobj.size() > 0) {
                                        int tan_urut = setobj.get(0).getUrut();
                                        sws_db.DBJalan_Delete(tan_urut, 99, 0);
                                    }
                                    //jenis, vlingkar, vjarak, vtinggi, vbanyaki, kebun, logdate, userid, vafdeling, vblok,
                                    //vttanami, vtpaneni, vapanen, vbpanen, vspanen, aket, acx, acy, 0, 0, 0
                                    sws_db.DBTanaman_Add(Integer.parseInt(tan_jenis), tan_lingkar, tan_jarak, tan_tinggi, Integer.parseInt(tan_banyaki),
                                            Integer.parseInt(idkebun), tan_waktu, Integer.parseInt(tan_userid), tan_afd, tan_blok, Integer.parseInt(tan_ttanami),
                                            Integer.parseInt(tan_tpaneni), tan_vapanen, tan_vbpanen, tan_vspanen, tan_ket, tan_cx, tan_cy,
                                            0, 1, Integer.parseInt(tan_id_server), 2);
                                }
                            }

                            JSONArray js_saluran = c.getJSONArray("saluran");
                            if( js_saluran != null ) {
                                for (int j = 0; j < js_saluran.length(); j++) {
                                    JSONObject d = js_saluran.getJSONObject(j);
                                    String sal_jenis = d.getString("jenis");
                                    String sal_kondisi = d.getString("kondisi");
                                    String sal_waktu = d.getString("waktu");
                                    String sal_userid = d.getString("userid");
                                    String sal_ket = d.getString("ket");
                                    String sal_cx = d.getString("x");
                                    String sal_cy = d.getString("y");
                                    String sal_lebar = d.getString("lebar");
                                    String sal_id_server = d.getString("id");
                                    List<DB_Saluran> setobj = new LinkedList<DB_Saluran>();
                                    setobj = sws_db.DBSaluran_CheckDariServer(Integer.parseInt(sal_id_server));
                                    if (setobj.size() > 0) {
                                        int sal_urut = setobj.get(0).getUrut();
                                        sws_db.DBJalan_Delete(sal_urut, 99, 0);
                                    }
                                    //jenis, kondisi, kebun, logdate, userid, aket, acx, acy, 0, alebar,0,0);
                                    sws_db.DBSaluran_Add(Integer.parseInt(sal_jenis), Integer.parseInt(sal_kondisi), Integer.parseInt(idkebun), sal_waktu,
                                            Integer.parseInt(sal_userid), sal_ket, sal_cx, sal_cy, 1, sal_lebar, 1,
                                            Integer.parseInt(sal_id_server), 2);
                                }
                            }




                            Intent intent;
                            intent = new Intent(LoginActivity.this, MapsActivity.class);
                            startActivity(intent);
                        } else {
                            Log.e(TAG, "Gagal dalam melakukan verifikasi pengguna");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),
                                            "Gagal dalam melakukan verifikasi pengguna",
                                            Toast.LENGTH_LONG)
                                            .show();
                                }
                            });
                        }
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Gagal dalam mengambil data dari servere");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Gagal dalam mengambil data dari server",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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