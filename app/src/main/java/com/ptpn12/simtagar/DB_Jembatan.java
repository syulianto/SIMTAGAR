package com.ptpn12.simtagar;

public class DB_Jembatan {
    int urut;
    int jenis;
    int kondisi;
    int kebun;
    String waktu;
    int userid;
    String ket;
    String cx;
    String cy;
    int kirim;
    String lebar;
    int dari_server;
    int id_server;
    int asli;

    public DB_Jembatan() {}

    public DB_Jembatan(
            int urut, int jenis, int kondisi, int kebun, String waktu, int userid, String ket,
            String cx, String cy, int kirim, String lebar, int dari_server, int id_server, int asli) {
        this.urut = urut;
        this.jenis = jenis;
        this.kondisi = kondisi;
        this.kebun = kebun;
        this.waktu = waktu;
        this.userid = userid;
        this.ket = ket;
        this.cx = cx;
        this.cy = cy;
        this.kirim = kirim;
        this.lebar = lebar;
        this.dari_server = dari_server;
        this.id_server = id_server;
        this.asli = asli;
    }

    public void setUrut(int urut) { this.urut = urut; }
    public void setJenis(int jenis) { this.jenis = jenis; }
    public void setKondisi(int kondisi) { this.kondisi = kondisi; }
    public void setKebun(int kebun) { this.kebun = kebun; }
    public void setWaktu(String waktu) { this.waktu = waktu; }
    public void setUserID(int userid) { this.userid = userid; }
    public void setKet(String ket) { this.ket = ket; }
    public void setCX(String cx) { this.cx = cx; }
    public void setCY(String cy) { this.cy = cy; }
    public void setKirim(int kirim) { this.kirim = kirim; }
    public void setLebar(String lebar) { this.lebar = lebar; }
    public void setDariServer(int dari_server) { this.dari_server = dari_server; }
    public void setIDServer(int id_server) { this.id_server = id_server; }
    public void setAsli(int asli) { this.asli = asli; }

    public int getUrut() { return this.urut; }
    public int getJenis() { return this.jenis; }
    public int getKondisi() { return this.kondisi; }
    public int getKebun() { return this.kebun; }
    public String getWaktu() { return this.waktu; }
    public int getUserID() { return this.userid; }
    public String getKet() { return this.ket; }
    public String getCX() { return this.cx; }
    public String getCY() { return this.cy; }
    public int getKirim() { return this.kirim; }
    public String getLebar() { return this.lebar; }
    public int getDariServer() { return this.dari_server; }
    public int getIDServer() { return this.id_server; }
    public int getAsli() { return this.asli; }
}
