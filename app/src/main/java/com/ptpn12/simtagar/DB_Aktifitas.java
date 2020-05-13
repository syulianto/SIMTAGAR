package com.ptpn12.simtagar;

public class DB_Aktifitas {
    int urut;
    int kebun;
    int jenis;
    String elama;
    String ebiaya;
    String esdm;
    String ket;
    String cx;
    String cy;
    String waktu;
    int userid;
    int kirim;
    int dari_server;
    int id_server;
    int asli;

    public DB_Aktifitas() {}

    public DB_Aktifitas (int urut, int kebun, int jenis, String elama, String ebiaya, String esdm, String ket,
                         String cx, String cy, String waktu, int userid, int kirim, int dari_server, int id_server, int asli) {
        this.urut = urut;
        this.kebun = kebun;
        this.jenis = jenis;
        this.elama = elama;
        this.ebiaya = ebiaya;
        this.esdm = esdm;
        this.ket = ket;
        this.cx = cx;
        this.cy = cy;
        this.waktu = waktu;
        this.userid = userid;
        this.kirim = kirim;
        this.dari_server = dari_server;
        this.id_server = id_server;
        this.asli = asli;
    }

    public int getUrut() { return this.urut; }
    public int getKebun() { return this.kebun; }
    public int getJenis() { return this.jenis; }
    public String getElama() { return this.elama; }
    public String getEbiaya() { return this.ebiaya; }
    public String getEsdm() { return this.esdm; }
    public String getKet() { return this.ket; }
    public String getCX() { return this.cx; }
    public String getCY() { return this.cy; }
    public String getWaktu() { return this.waktu; }
    public int getUserID() { return this.userid; }
    public int getKirim() { return this.kirim; }
    public int getDariServer() { return this.dari_server; }
    public int getIDServer() { return this.id_server; }
    public int getAsli() { return this.asli; }

    public void setUrut(int urut) { this.urut = urut; }
    public void setKebun(int kebun) { this.kebun = kebun; }
    public void setJenis(int jenis) { this.jenis = jenis; }
    public void setElama(String elama) { this.elama = elama; }
    public void setEBiaya(String ebiaya) { this.ebiaya = ebiaya; }
    public void setEsdm(String esdm) { this.esdm = esdm; }
    public void setKet(String ket) { this.ket = ket; }
    public void setCX(String cx) { this.cx = cx; }
    public void setCY(String cy) { this.cy = cy; }
    public void setWaktu(String waktu) { this.waktu = waktu; }
    public void setUserID(int userid) { this.userid = userid; }
    public void setKirim(int kirim) { this.kirim = kirim; }
    public void setDariServer(int dari_server) { this.dari_server = dari_server; }
    public void setIDServer(int id_server) { this.id_server = id_server; }
    public void setAsli(int asli) { this.asli = asli; }
}
