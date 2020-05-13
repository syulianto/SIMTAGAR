package com.ptpn12.simtagar;

public class DB_Obyek {
    int urut;
    int jenis;
    String cx;
    String cy;
    String waktu;
    int userid;
    String ket;
    int kirim;
    int dari_server;
    int id_server;
    int asli;

    public DB_Obyek() {}

    public DB_Obyek(
            int urut, int jenis, String waktu, int userid, String ket, String cx,
            String cy, int kirim, int dari_server, int id_server, int asli) {
        this.urut = urut;
        this.jenis = jenis;
        this.waktu = waktu;
        this.userid = userid;
        this.ket = ket;
        this.cx = cx;
        this.cy = cy;
        this.kirim = kirim;
        this.dari_server = dari_server;
        this.id_server = id_server;
        this.asli = asli;
    }

    public void setUrut(int urut) { this.urut = urut; }
    public void setJenis(int jenis) { this.jenis = jenis; }
    public void setWaktu(String waktu) { this.waktu = waktu; }
    public void setUserID(int userid) { this.userid = userid; }
    public void setKet(String ket) { this.ket = ket; }
    public void setCX(String cx) { this.cx = cx; }
    public void setCY(String cy) { this.cy = cy; }
    public void setKirim(int kirim) { this.kirim = kirim; }
    public void setDariServer(int dari_server) { this.dari_server = dari_server; }
    public void setIDServer(int id_server) { this.id_server = id_server; }
    public void setAsli(int asli) { this.asli = asli; }

    public int getUrut() { return this.urut; }
    public int getJenis() { return this.jenis; }
    public String getWaktu() { return this.waktu; }
    public int getUserID() { return this.userid; }
    public String getKet() { return this.ket; }
    public String getCX() { return this.cx; }
    public String getCY() { return this.cy; }
    public int getKirim() { return this.kirim; }
    public int getDariServer() { return this.dari_server; }
    public int getIDServer() { return this.id_server; }
    public int getAsli() { return this.asli; }
}
