package com.ptpn12.simtagar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class frm_download extends AppCompatActivity {
    MySQLLiteHelper sws_db;
    DB_Setting dbsetting;
    //Spinner kebun;
    CheckBox cb_kejadian, cb_aktifitas, cb_patok, cb_jalan, cb_jembatan, cb_tanaman, cb_saluran, cb_pekerja;
    Button btn_ok, btn_cancel;
    List<DB_Ref_Kebun> setkebun = new LinkedList<DB_Ref_Kebun>();
    private ArrayList<String> kode_kebun = new ArrayList<String>();
    private ArrayList<String> nama_kebun = new ArrayList<String>();
    private String TAG = LoginActivity.class.getSimpleName();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_download);
        sws_db = new MySQLLiteHelper(getApplicationContext());

        //kebun = (Spinner) findViewById(R.id.download_kebun);
        cb_kejadian = (CheckBox) findViewById(R.id.download_kejadian);
        cb_aktifitas = (CheckBox) findViewById(R.id.download_Aktifitas);
        cb_patok = (CheckBox) findViewById(R.id.download_patok);
        cb_jalan = (CheckBox) findViewById(R.id.download_jalan);
        cb_jembatan = (CheckBox) findViewById(R.id.download_jembatan);
        cb_tanaman = (CheckBox) findViewById(R.id.download_tanaman);
        cb_saluran = (CheckBox) findViewById(R.id.download_saluran);
        cb_pekerja = (CheckBox) findViewById(R.id.download_pekerja);
        btn_ok = (Button) findViewById(R.id.download_proses);
        btn_cancel = (Button) findViewById(R.id.download_batal);

        //setkebun = sws_db.DBRefKebun_Get(0);
        //for( int iix = 0; iix < setkebun.size(); iix++ ) {
        //    kode_kebun.add(String.valueOf(setkebun.get(iix).getKode()));
        //    nama_kebun.add(setkebun.get(iix).getNama());
        //}
        //ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nama_kebun);
        //kebun.setAdapter(adapter1);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new doDownload().execute();

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

    private class doDownload extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(frm_download.this);
            progressDialog.setMessage("Sedang mengunduh data"); // Setting Message
            progressDialog.setTitle("SIMTAGAR"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(true);
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            String jsonStr = null;
            int user = 0;
            dbsetting = sws_db.DBSetting_Get();
            String alamat = "http://" + dbsetting.getWebip();
            if( dbsetting.getVd().equals("")) {
                alamat = alamat + "/mob_download.php";
            } else {
                alamat = alamat + "/" + dbsetting.getVd() + "/mob_download.php";
            }
            user = dbsetting.getActiveUser();
            int kebun = dbsetting.getActiveKebun();
            //int pilihan = kebun.getSelectedItemPosition();
            //int kebun = Integer.parseInt(kode_kebun.get(pilihan));
            String akt, kej, pat, jal, jem, tan, sal, pek;
            if( cb_aktifitas.isChecked() ) { akt = "1"; } else { akt = "0"; }
            if( cb_kejadian.isChecked() ) { kej = "1"; } else { kej = "0"; }
            if( cb_patok.isChecked() ) { pat = "1"; } else { pat = "0"; }
            if( cb_jalan.isChecked() ) { jal = "1"; } else { jal = "0"; }
            if( cb_jembatan.isChecked() ) { jem = "1"; } else { jem = "0"; }
            if( cb_tanaman.isChecked() ) { tan = "1"; } else { tan = "0"; }
            if( cb_saluran.isChecked() ) { sal = "1"; } else { sal = "0"; }
            if( cb_pekerja.isChecked() ) { pek = "1"; } else { pek = "0"; }

            try {
                URL url = new URL(alamat);
                Map<String,Object> params = new LinkedHashMap<>();
                params.put("user", String.valueOf(user));
                params.put("idkebun", String.valueOf(kebun));
                params.put("akt", akt);
                params.put("kej", kej);
                params.put("pat", pat);
                params.put("jal", jal);
                params.put("jem", jem);
                params.put("tan", tan);
                params.put("sal", sal);
                params.put("pek", pek);

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
                    for (int i = 0; i < sws_data.length(); i++) {
                        JSONObject c = sws_data.getJSONObject(i);
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
                                sws_db.DBAktifitas_Add(kebun, Integer.parseInt(akt_jenis), akt_lama, akt_biaya, akt_sdm, akt_ket,
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
                                sws_db.DBKejadian_Add(kebun, Integer.parseInt(kej_jenis), kej_ket, kej_cx, kej_cy, kej_waktu,
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
                                sws_db.DBPatok_Add(pat_nama, Integer.parseInt(pat_jenis), kebun, pat_cx, pat_cy, pat_waktu,
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
                                        Integer.parseInt(jal_jenis), Integer.parseInt(jal_kondisi), kebun, jal_waktu,
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
                                        Integer.parseInt(jem_jenis), Integer.parseInt(jem_kondisi), kebun, jem_waktu,
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
                                        kebun, tan_waktu, Integer.parseInt(tan_userid), tan_afd, tan_blok, Integer.parseInt(tan_ttanami),
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
                                sws_db.DBSaluran_Add(Integer.parseInt(sal_jenis), Integer.parseInt(sal_kondisi), kebun, sal_waktu,
                                        Integer.parseInt(sal_userid), sal_ket, sal_cx, sal_cy, 1, sal_lebar, 1,
                                        Integer.parseInt(sal_id_server), 2);
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

                        //progressDialog.dismiss();
                        //Intent intent = new Intent(frm_download.this,MapsActivity.class);
                        //setResult(1, intent);
                        //startActivity(intent);
                        //finish();
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
}
