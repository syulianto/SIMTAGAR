package com.ptpn12.simtagar;

public class DB_Patok {
    int urut;
    String nama;
    int jenis;
    int kebun;
    String cx;
    String cy;
    String waktu;
    int userid;
    int kirim;
    String ket;
    int dari_server;
    int id_server;
    int asli;

    public DB_Patok() {}

    public DB_Patok (
            int urut, String nama, int jenis, int kebun, String cx, String cy, String waktu,
            int userid, int kirim, String ket, int dari_server, int id_server, int asli) {
        this.urut = urut;
        this.nama = nama;
        this.jenis = jenis;
        this.kebun = kebun;
        this.cx = cx;
        this.cy = cy;
        this.waktu = waktu;
        this.userid = userid;
        this.kirim = kirim;
        this.ket = ket;
        this.dari_server = dari_server;
        this.id_server = id_server;
        this.asli = asli;
    }

    public int getUrut() { return this.urut; }
    public String getNama() { return this.nama; }
    public int getJenis() { return this.jenis; }
    public int getKebun() { return this.kebun; }
    public String getCX() { return this.cx; }
    public String getCY() { return this.cy; }
    public String getWaktu() { return this.waktu; }
    public int getUserID() { return this.userid; }
    public int getKirim() { return this.kirim; }
    public String getKet() { return this.ket; }
    public int getDariServer() { return this.dari_server; }
    public int getIDServer() { return this.id_server; }
    public int getAsli() { return this.asli; }

    public void setUrut(int urut) { this.urut = urut; }
    public void setNama(String nama) { this.nama = nama; }
    public void setJenis(int jenis) { this.jenis = jenis; }
    public void setKebun(int kebun) { this.kebun = kebun; }
    public void setCX(String cx) { this.cx = cx; }
    public void setCY(String cy) { this.cy = cy; }
    public void setWaktu(String waktu) { this.waktu = waktu; }
    public void setUserID(int userid) { this.userid = userid; }
    public void setKirim(int kirim) { this.kirim = kirim; }
    public void setKet(String ket) { this.ket = ket; }
    public void setDariServer(int dari_server) { this.dari_server = dari_server; }
    public void setIDServer(int id_server) { this.id_server = id_server; }
    public void setAsli(int asli) { this.asli = asli; }
}
