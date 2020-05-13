package com.ptpn12.simtagar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class frm_Permulaan_Check extends AppCompatActivity {
    private static final int PERMISSION_ALL = 0;
    private Handler h;
    private Runnable r;
    /*
      SharedPreferences mPrefs;
      final String settingScreenShownPref = "settingScreenShown";
      final String versionCheckedPref = "versionChecked";
    */
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_permulaan_check);

        h = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(Splash.this, "Runnable started", Toast.LENGTH_SHORT).show();

  /*          // (OPTIONAL) these lines to check if the `First run` ativity is required
                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;

                mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = mPrefs.edit();

                Boolean settingScreenShown = mPrefs.getBoolean(settingScreenShownPref, false);
                int savedVersionCode = mPrefs.getInt(versionCheckedPref, 1);

                if (!settingScreenShown || savedVersionCode != versionCode) {
                    startActivity(new Intent(Splash.this, FirstRun.class));
                    editor.putBoolean(settingScreenShownPref, true);
                    editor.putInt(versionCheckedPref, versionCode);
                    editor.commit();
                }
                else
  */
                startActivity(new Intent(frm_Permulaan_Check.this, LoginActivity.class));
                finish();
            }
        };

        String[] PERMISSIONS = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.CAMERA
        };

        if(!hasPermissions(this, PERMISSIONS)){
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
        }
        else
            h.postDelayed(r, 1500);
    }

    @TargetApi(23)
    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int index = 0;
        if( checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Aplikasi ini membutuhkan akses ke internet", Toast.LENGTH_SHORT).show();
            finish();
        }
        if( checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Aplikasi ini membutuhkan akses ke internet", Toast.LENGTH_SHORT).show();
            finish();
        }
        if( checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Aplikasi ini membutuhkan akses untuk menulis berkas", Toast.LENGTH_SHORT).show();
            finish();
        }
        if( checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Aplikasi ini membutuhkan akses lokasi (GPS)", Toast.LENGTH_SHORT).show();
            finish();
        }
        if( checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Aplikasi ini membutuhkan akses lokasi (GPS)", Toast.LENGTH_SHORT).show();
            finish();
        }
        if( checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Aplikasi ini membutuhkan akses ke kamera", Toast.LENGTH_SHORT).show();
            finish();
        }
        h.postDelayed(r, 1500);
    }

    private boolean hasPermissions(Context context, String... allPermissionNeeded ) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && allPermissionNeeded != null) {
            for (String permission : allPermissionNeeded)
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                    return false;
            return true;
        }
        return true;
    }

}
