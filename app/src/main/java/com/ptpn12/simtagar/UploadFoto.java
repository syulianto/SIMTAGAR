package com.ptpn12.simtagar;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UploadFoto {

    //public static final String UPLOAD_URL= "http://simplifiedcoding.16mb.com/VideoUpload/upload.php";

    private int serverResponseCode;
    //toupload.UploadVideo(sdPath, alamat, urut, userid, nfile, cx, cy, waktu, ket);
    //int id, int userid, String nfile, String cx, String cy, int kirim, String waktu,
    //            String ket, int tampil, int jenis, int dari_server, int id_server, int asli) {
    public String UploadFoto(
            String id, String userid, String nfile, String cx, String cy, String kirim, String waktu, String ket, String tampil,
            String jenis, String dari_server, String id_server, String asli, String alamat) {

        String fileName = nfile;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        File sourceFile = new File(nfile);
        if (!sourceFile.isFile()) {
            //Log.e("Huzza", "Source File Does not exist");
            return null;
        }


        try {
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL(alamat);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("img", fileName);
            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"img\";filename=\"" + fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available();
            //Log.e("uploadVideo", "Initial .available : " + bytesAvailable);

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);



            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"id\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(id);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"nfile\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(nfile);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"cx\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(cx);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"cy\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(cy);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"kirim\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(kirim);
            dos.writeBytes(lineEnd);

            //String id, String userid, String nfile, String cx, String cy, String kirim, String waktu, String ket, String tampil,
            //            String jenis, String dari_server, String id_server, String asli, String alamat) {


            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"waktu\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(waktu);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"ket\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(ket);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"tampil\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(tampil);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"jenis\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(jenis);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"dari_server\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(dari_server);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"id_server\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(id_server);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"asli\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(asli);
            dos.writeBytes(lineEnd);
            //selesai unggah video


            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            serverResponseCode = conn.getResponseCode();

            fileInputStream.close();
            dos.flush();
            dos.close();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            Log.e("uploadVideo", ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("uploadVideo", e.getMessage());
        }

        if (serverResponseCode == 200) {
            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                if( sb.toString().contains("SWS_OK")) {
                    String res[] = {};
                    res = sb.toString().split("\\|");
                    if( res.length == 2 ) {
                        int anid = Integer.parseInt(res[1]);
                        MySQLLiteHelper sws_db;
                        sws_db = new MySQLLiteHelper(SimtagarGlobal.getAppContext());
                        //sws_db.DBKejadian_UpdateKirim(anid);
                        sws_db.DBImage_UpdateKirim(anid);
                    }
                }
            } catch (IOException ioex) {
            }
            return sb.toString();
        }else {
            return "Could not upload";
        }
    }
}
