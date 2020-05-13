package com.ptpn12.simtagar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class frm_camera_caption extends AppCompatActivity {
    Button btn1;
    Button btn2;
    EditText txt1;
    ImageView imgView;
    String afp;
    String nfile;
    Spinner obyek;

    private ArrayList<String> kode = new ArrayList<String>();
    private ArrayList<String> nama = new ArrayList<String>();
    List<DB_Ref_Obyek> setobyek = new LinkedList<DB_Ref_Obyek>();
    MySQLLiteHelper sws_db;
    DB_Setting dbsetting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sws_db = new MySQLLiteHelper(getApplicationContext());
        setContentView(R.layout.frm_camera_caption);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            afp = extras.getString("afp");
            nfile = extras.getString("nfile");
        }

        btn1 = (Button) findViewById(R.id.camcap_btn_proses);
        btn2 = (Button) findViewById(R.id.camcap_btn_cancel);
        txt1 = (EditText) findViewById(R.id.camcap_edit);
        imgView = (ImageView) findViewById(R.id.camcap_imgView);
        obyek = (Spinner) findViewById(R.id.camcap_obyek);
        Bitmap bmp = BitmapFactory.decodeFile(afp);
        imgView.setImageBitmap(bmp);

        setobyek = sws_db.DBRefObyek_Get(0);
        for( int iix = 0; iix < setobyek.size(); iix++ ) {
            kode.add(String.valueOf(setobyek.get(iix).getKode()));
            nama.add(setobyek.get(iix).getNama());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nama);
        obyek.setAdapter(adapter);

        btn2.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View arg0) {
                Intent intent=new Intent();
                setResult(0,intent);
                finish();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View arg0) {
                String atext;
                int pilihan = obyek.getSelectedItemPosition();
                atext = txt1.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("ket", atext);
                intent.putExtra("afp", afp);
                intent.putExtra("nfile", nfile);
                intent.putExtra("jenis", kode.get(pilihan));
                setResult(1,intent);
                finish();
            }
        });

    }
}