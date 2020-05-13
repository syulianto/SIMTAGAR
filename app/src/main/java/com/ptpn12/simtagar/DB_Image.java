package com.ptpn12.simtagar;

public class DB_Image {
    //private static final String TBL_IMAGE_DB = "create table tbl_image ("
    //		+ "aid integer primary key autoincrement, devid text, userid integer, nfile text, "
    //		+ "cx text, cy text, kirim integer, waktu text, ket text);";

    int id;
    int userid;
    String nfile;
    String cx;
    String cy;
    int kirim;
    String waktu;
    String ket;
    int tampil;
    int jenis;
    int dari_server;
    int id_server;
    int asli;

    public DB_Image() {}

    public DB_Image(
            int id, int userid, String nfile, String cx, String cy, int kirim, String waktu,
            String ket, int tampil, int jenis, int dari_server, int id_server, int asli) {
        this.id = id;
        this.userid = userid;
        this.nfile = nfile;
        this.cx = cx;
        this.cy = cy;
        this.kirim = kirim;
        this.waktu = waktu;
        this.ket = ket;
        this.tampil = tampil;
        this.jenis = jenis;
        this.dari_server = dari_server;
        this.id_server = id_server;
        this.asli = asli;
    }

    public void setID(int id) { this.id = id; }
    public void setUserid(int userid) { this.userid = userid; }
    public void setNfile(String nfile) { this.nfile = nfile; }
    public void setCx(String cx) { this.cx = cx; }
    public void setCy(String cy) { this.cy = cy; }
    public void setKirim(int kirim) { this.kirim = kirim; }
    public void setWaktu(String waktu) { this.waktu = waktu; }
    public void setKet(String ket) { this.ket = ket; }
    public void setTampil(int tampil) { this.tampil = tampil; }
    public void setJenis(int jenis) { this.jenis = jenis; }
    public void setDariServer(int dari_server) { this.dari_server = dari_server; }
    public void setIDServer(int id_server) { this.id_server = id_server; }
    public void setAsli(int asli) { this.asli = asli; }

    public int getID() { return this.id; }
    public int getUserid() { return this.userid; }
    public String getNfile() { return this.nfile; }
    public String getCx() { return this.cx; }
    public String getCy() { return this.cy; }
    public int getKirim() { return this.kirim; }
    public String getWaktu() { return this.waktu; }
    public String getKet() { return this.ket; }
    public int getTampil() { return this.tampil; }
    public int getJenis() { return this.jenis; }
    public int getDariServer() { return this.dari_server; }
    public int getIDServer() { return this.id_server; }
    public int getAsli() { return this.asli; }
}
