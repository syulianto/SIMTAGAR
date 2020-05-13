package com.ptpn12.simtagar;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

public class frm_absensi_camera extends AppCompatActivity {
    double curpos_lat = 0;
    double curpos_lon = 0;
    String pengirim = "";
    String alamat = "";
    int sid = 0;
    int segid = 0;
    MySQLLiteHelper sws_db;
    boolean langsungkirim = false;
    String acx, acy, devid, nohp, asegmen;
    int asurid, akotak;
    DB_Setting asetting;
    DB_Image aimage;
    String timeStamp = "";
    String formattedDate = "";
    String jns = "";

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String IMAGE_DIRECTORY_NAME = "SIMTAGAR";
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;

    private Uri fileUri; // file url to store image/video
    int idrow = 0;
    String idpek = "";
    int idmandor = 0;
    int pilihan = 0;
    String cx = "";
    String cy = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_camera);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        sws_db = new MySQLLiteHelper(getApplicationContext());
        asetting = sws_db.DBSetting_Get();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idrow = Integer.parseInt(extras.getString("idrow"));
            idpek = extras.getString("idpek");
            idmandor = Integer.parseInt(extras.getString("idmandor"));
            pilihan = Integer.parseInt(extras.getString("pilihan"));
            cx = extras.getString("cx");
            cy = extras.getString("cy");
        }
        jns = "1";
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(), "HP Anda tidak mempunyai kamera atau kamera tidak berfungsi", Toast.LENGTH_LONG).show();
            finish();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (getFromPref(this, ALLOW_KEY)) {
                showSettingsAlert();
            } else if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)

                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {
                    showAlert();
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }
        }

        if( jns.equals("1") ) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.ptpn12.simtagar.fileprovider",
                        photoFile);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }

        } else {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
        }
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(frm_absensi_camera.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Aplikasi membutuhkan izin mengakses kamera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK DIIJINKAN",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "IJINKAN",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(frm_absensi_camera.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                });
        alertDialog.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(frm_absensi_camera.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Aplikasi membutuhkan ijin mengakses kamera");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK DIIJINKAN",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(frm_absensi_camera.this);
                    }
                });

        alertDialog.show();
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }

        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            return true;
        } else {
            return false;
        }
    }

    private Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /*
     * returning image / video
     */
    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) { Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
            formattedDate = "IMG_" + timeStamp + ".jpg";
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
            formattedDate = "VID_" + timeStamp + ".mp4";
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private static Bitmap createScaledBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, 0, 0);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return scaledBitmap;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                File file = new File(currentPhotoPath);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (bitmap != null) {
                    String tglnow = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    String jamnow = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                    sws_db = new MySQLLiteHelper(getApplicationContext());
                    List<DB_Absensi> setabsensi = new LinkedList<DB_Absensi>();
                    setabsensi = sws_db.DBAbsensi_Get(idrow, idpek, idmandor, tglnow);
                    if( setabsensi.size() == 0 ) {
                        //baru pertama
                        if( pilihan == 0 ) {
                            //pagi
                            sws_db.DBAbsensi_Add(
                                    idrow, idpek, idmandor, tglnow, jamnow, "", "", currentPhotoPath,"",1,0,
                                    cx, cy, "","", tglnow, "0000-00-00", 0);
                        } else {
                            sws_db.DBAbsensi_Add(
                                    idrow, idpek, idmandor, tglnow, "", jamnow, "","", currentPhotoPath,0,1,
                                    "","", cx, cy, "0000-00-00", tglnow, 0);
                        }
                    } else {
                        //update karena sudah ada di tgl ybs
                        DB_Absensi anabsensi = setabsensi.get(0);
                        if( pilihan == 0 ) {
                            //pagi
                            sws_db.DBAbsensi_Update(
                                    idrow, idpek, idmandor, tglnow, jamnow, anabsensi.getJamp(), anabsensi.getLokasi(), currentPhotoPath,
                                    anabsensi.getFotop(),1, anabsensi.getAdap(), cx, cy, anabsensi.getCXP(), anabsensi.getCYP(),
                                    tglnow, anabsensi.getWaktup(), anabsensi.getKirim());
                        } else {
                            sws_db.DBAbsensi_Update(
                                    idrow, idpek, idmandor, tglnow, anabsensi.getJamb(), jamnow, anabsensi.getLokasi(), anabsensi.getFotob(),
                                    currentPhotoPath, anabsensi.getAdab(), 1, anabsensi.getCXB(), anabsensi.getCYB(), cx, cy,
                                    anabsensi.getWaktub(), tglnow, anabsensi.getKirim());
                        }
                    }
                    Toast.makeText(frm_absensi_camera.this, "Absensi telah tersimpan",Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent();
                    setResult(113, intent1);
                    finish();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Pengguna meng-cancel perekaman foto", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Gagal dalam merekam foto", Toast.LENGTH_SHORT).show();
            }
        } else if( requestCode == 1) {
            if(resultCode == 1 ) {
                String out_file = data.getStringExtra("afp");
                String formattedDate = data.getStringExtra("nfile");
                String ket = data.getStringExtra("ket");
                String jenis = data.getStringExtra("jenis");
            }
        }
        //finish();
        return;
    }
}