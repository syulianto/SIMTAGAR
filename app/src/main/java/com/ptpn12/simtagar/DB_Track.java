package com.ptpn12.simtagar;

public class DB_Track {
    int urut;
    String cx;
    String cy;
    String waktu;
    int userid;
    int kirim;

    public DB_Track() {}

    public DB_Track (int urut, String cx, String cy, String waktu, int userid, int kirim) {
        this.urut = urut;
        this.cx = cx;
        this.cy = cy;
        this.waktu = waktu;
        this.userid = userid;
        this.kirim = kirim;
    }

    public int getUrut() { return this.urut; }
    public String getCX() { return this.cx; }
    public String getCY() { return this.cy; }
    public String getWaktu() { return this.waktu; }
    public int getUserID() { return this.userid; }
    public int getKirim() { return this.kirim; }

    public void setUrut(int urut) { this.urut = urut; }
    public void setCX(String cx) { this.cx = cx; }
    public void setCY(String cy) { this.cy = cy; }
    public void setWaktu(String waktu) { this.waktu = waktu; }
    public void setUserID(int userid) { this.userid = userid; }
    public void setKirim(int kirim) { this.kirim = kirim; }
}
