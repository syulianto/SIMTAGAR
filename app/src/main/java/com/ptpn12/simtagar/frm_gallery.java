package com.ptpn12.simtagar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class frm_gallery extends AppCompatActivity {
    // Declare variables
    private String[] snimage;
    private String[] spimage;
    private String[] sjimage;
    private File[] listFile;
    private GridView grid;
    private frm_gallery_IA adapter;
    private File file, file1;
    private Context myContext;
    private MySQLLiteHelper adatabase;
    private int surveiid = 0;
    private int segmenid = 0;
    private int userid = 0;
    private int jenis = 0;
    private String devid;
    private DB_Setting asetting;

    private String fname = "";
    private String fpath = "";

    private static final String IMAGE_DIRECTORY_NAME = "RAPIDMAPPING";
    private static final String IMAGE_DIRECTORY_NAME1 = "Notes";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_gallery);

        // Check for SD Card
        if (!Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show();
        } else {
            file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            //file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);
            //file1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME1);
        }

        myContext = this;
        adatabase = new MySQLLiteHelper(getApplicationContext());
        asetting = adatabase.DBSetting_Get();

        DB_Image dbimage;
        List<DB_Image> setimage = new LinkedList<DB_Image>();
        int cix = 0;
        List<String> nimage = new ArrayList<String>();
        List<String> pimage = new ArrayList<String>();
        List<String> jimage = new ArrayList<String>();

        if (file.isDirectory()) {
            listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++) {
                fname = listFile[i].getName();
                fpath = listFile[i].getAbsolutePath();
                //Log.e("GALLERY", fname);
                setimage = adatabase.DBImage_Get(0,99,1,99);
                if( setimage.size() > 0) {
                    nimage.add(fname);
                    pimage.add(fpath);
                    jimage.add("1");
                    Log.e("Gallery", fpath + ":::" + fname);
                }
            }
        }

        snimage = new String[nimage.size()];
        snimage = nimage.toArray(snimage);
        spimage = new String[pimage.size()];
        spimage = pimage.toArray(spimage);
        sjimage = new String[jimage.size()];
        sjimage = jimage.toArray(sjimage);

        GridView gridView = (GridView) findViewById(R.id.grid_view);
        // Instance of ImageAdapter Class
        gridView.setAdapter(new frm_gallery_IA(this, spimage, snimage, sjimage));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(getApplicationContext(), frm_gallery_full.class);
                i.putExtra("id", position);
                i.putExtra("afp", spimage[(int)id]);
                startActivity(i);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                final int selpos = position;
                return true;
            }
        });

        //gridView.invalidate()
    }
}