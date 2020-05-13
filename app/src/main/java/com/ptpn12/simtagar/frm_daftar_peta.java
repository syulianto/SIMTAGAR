package com.ptpn12.simtagar;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class frm_daftar_peta extends AppCompatActivity {
    MySQLLiteHelper sws_db;
    DB_Setting dbsetting;
    TableLayout ll;
    Button btn_ok, btn_cancel;
    CheckBox checkBox;
    TextView tv;

    EditText ket, lebar;
    List<DB_KML> setkml = new LinkedList<DB_KML>();
    DB_KML akml;
    int anid = 0;
    LinkedList listid = new LinkedList();
    LinkedList listidrow = new LinkedList();
    LinkedList listidtoc = new LinkedList();
    LinkedList listidpeta = new LinkedList();
    LinkedList listiddb = new LinkedList();
    LinkedList listnama = new LinkedList();
    LinkedList downloadedid = new LinkedList();
    LinkedList downloadediddb = new LinkedList();
    LinkedList listfolder = new LinkedList();
    LinkedList listfolderkompit = new LinkedList();
    LinkedList downloadedfolder = new LinkedList();
    int glob_idrow, glob_idtoc, glob_idpeta, glob_iddb;
    String glob_folder, glob_nama, glob_folder_komplit;

    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    int banyak_data = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_daftar_peta);
        sws_db = new MySQLLiteHelper(getApplicationContext());
        TableLayout ll = (TableLayout) findViewById(R.id.daftar_peta_tl);
        btn_ok = (Button) findViewById(R.id.daftar_peta_proses);
        btn_cancel = (Button) findViewById(R.id.daftar_peta_batal);
        downloadedid.clear();

        registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        setkml = sws_db.DBKML_Get(0,"",99);
        for( int i=0; i < setkml.size(); i++ ) {
            akml = setkml.get(i);
            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            checkBox = new CheckBox(this);
            tv = new TextView(this);
            checkBox.setText("");
            anid = View.generateViewId();
            checkBox.setId(anid);
            listid.add(anid);
            listidrow.add(akml.getIDRow());
            listidpeta.add(akml.getIDPeta());
            listidtoc.add(akml.getIDTOC());
            listiddb.add(akml.getID());
            listnama.add(akml.getNama());
            String[] separated = akml.getFolder().split("/");
            listfolder.add(separated[separated.length-1]);
            listfolderkompit.add(akml.getFolder());
            checkBox.setButtonTintList(getColorStateList(R.color.hijaudefault));
            tv.setText(akml.getNama());
            row.addView(checkBox);
            row.addView(tv);
            ll.addView(row,i);
        }

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                DB_Setting asetting;
                CheckBox cb;
                for( int i = 0; i < listid.size(); i++ ) {
                    int dum = (int) listid.get(i);
                    cb = (CheckBox) findViewById(dum);
                    if( cb.isChecked()) {
                        //berarti harus download
                        String anama = (String) listnama.get(i);
                        glob_iddb = (int) listiddb.get(i);
                        glob_idrow = (int) listidrow.get(i);
                        glob_idtoc = (int) listidtoc.get(i);
                        glob_idpeta = (int) listidpeta.get(i);
                        glob_nama = (String) listnama.get(i);
                        glob_folder = (String) listfolder.get(i);
                        glob_folder_komplit = (String) listfolderkompit.get(i);
                        beginDownload(glob_iddb, glob_idrow, glob_idtoc, glob_idpeta, glob_nama, glob_folder, glob_folder_komplit);
                    }
                }

                //Intent intent1 = new Intent();
                //setResult(1103, intent1);
                //finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent1 = new Intent();
                setResult(1104, intent1);
                finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
    }


    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            for( int i = 0; i < downloadedid.size(); i++ ) {
                long dumid = (long) downloadedid.get(i);
                Log.e("FDP Complete", String.valueOf(dumid) + " === " + String.valueOf(id));
                if( dumid == id ) {
                    List<DB_KML> dumsetkml = new LinkedList<DB_KML>();
                    int iddb = (int) downloadediddb.get(i);
                    String folder = (String) downloadedfolder.get(i);
                    Log.e("FDP ODC", folder);
                    dumsetkml = sws_db.DBKML_Get(iddb,"",99);
                    for( int j = 0; j < dumsetkml.size(); j++ ) {
                        DB_KML dk = dumsetkml.get(j);
                        sws_db.DBKML_Update(iddb, dk.getNama(), dk.getIDKebun(), dk.getIDRow(),
                                dk.getIDTOC(), dk.getIDPeta(), dk.getFolder(), dk.getTampil(), 1);
                        File file=new File(getExternalFilesDir(null), folder);
                        long asize = file.length();
                        Toast.makeText(getApplicationContext(), "Peta " + dk.getNama() + " selesai diunduh (" + String.valueOf(asize) + " byte)", Toast.LENGTH_SHORT).show();
                    }
                    banyak_data = banyak_data - 1;
                    if( banyak_data <= 0 ) {
                        //Intent intent1 = new Intent();
                        //setResult(1104, intent1);
                        finish();
                    }
                }
            }
        }
    };

    private void beginDownload(int iddb, int idrow, int idtoc, int idpeta, String nama, String folder, String folderkomplit){
        File file=new File(getExternalFilesDir(null), folder);
        Log.e("DP", String.valueOf(file));
        /*
        Create a DownloadManager.Request with all the information necessary to start the download
         */
        dbsetting = sws_db.DBSetting_Get();
        String alamat = "http://" + dbsetting.getWebip();
        if( dbsetting.getVd().equals("")) {
            alamat = alamat + "/mob_peta.php";
        } else {
            alamat = alamat + "/" + dbsetting.getVd() + "/mob_peta.php";
        }
        //alamat = alamat + "/data/" + folderkomplit;
        Log.e("FDP", alamat);

        alamat = alamat + "?id=" + String.valueOf(idrow) + "&idtoc=" + String.valueOf(idtoc) + "&idpeta=" + String.valueOf(idpeta);
        //alamat = "http://103.253.107.79/data/so/so_01.kml";
        //alamat = "http://www.gadgetsaint.com/wp-content/uploads/2016/11/cropped-web_hi_res_512.png";
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(alamat))
                .setTitle(nama)// Title of the Download Notification
                .setDescription("Sedang melakukan pengunduhan")// Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
        DownloadManager downloadManager= (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        long downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
        Log.e("FDP DownloadID", String.valueOf(downloadID));
        downloadedid.add(downloadID);
        downloadediddb.add(iddb);
        downloadedfolder.add(folder);
        banyak_data = banyak_data + 1;

        /* ----------------------- gak kepake
        DownloadManager.Query query = null;
        Cursor c = null;
        query = new DownloadManager.Query();
        if(query!=null) {
            query.setFilterByStatus(DownloadManager.STATUS_FAILED|DownloadManager.STATUS_PAUSED|DownloadManager.STATUS_SUCCESSFUL|
                    DownloadManager.STATUS_RUNNING|DownloadManager.STATUS_PENDING);
        } else {
            return;
        }
        c = downloadManager.query(query);
        if(c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Toast.makeText(getApplicationContext(), "Paused", Toast.LENGTH_SHORT).show();
                    break;
                case DownloadManager.STATUS_PENDING:
                    Toast.makeText(getApplicationContext(), "Pending", Toast.LENGTH_SHORT).show();
                    break;
                case DownloadManager.STATUS_RUNNING:
                    Toast.makeText(getApplicationContext(), "Running", Toast.LENGTH_SHORT).show();
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    Toast.makeText(getApplicationContext(), "Sukses", Toast.LENGTH_SHORT).show();
                    break;
                case DownloadManager.STATUS_FAILED:
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            Toast.makeText(getApplicationContext(), "KOSONG", Toast.LENGTH_SHORT).show();
        }
        ----------------------------------------------------- */
    }
}