package com.ptpn12.simtagar;

public class DB_Tanaman {
    int urut;
    int jenis;
    String lingkar;
    String jarak;
    String tinggi;
    int banyak;
    int kebun;
    String waktu;
    int userid;
    String afdeling;
    String blok;
    int ttanam;
    int tpanen;
    String apanen;
    String bpanen;
    String spanen;
    String ket;
    String cx;
    String cy;
    int kirim;
    int dari_server;
    int id_server;
    int asli;

    public DB_Tanaman() {}

    public DB_Tanaman(int urut, int jenis, String lingkar, String jarak, String tinggi, int banyak, int kebun, String waktu, int userid,
                      String afdeling, String blok, int ttanam, int tpanen, String apanen, String bpanen, String spanen, String ket,
                      String cx, String cy, int kirim, int dari_server, int id_server, int asli) {
        this.urut = urut;
        this.jenis = jenis;
        this.lingkar = lingkar;
        this.jarak = jarak;
        this.tinggi = tinggi;
        this.banyak = banyak;
        this.kebun = kebun;
        this.waktu = waktu;
        this.userid = userid;
        this.afdeling = afdeling;
        this.blok = blok;
        this.ttanam = ttanam;
        this.tpanen = tpanen;
        this.apanen = apanen;
        this.bpanen = bpanen;
        this.spanen = spanen;
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
    public void setLingkar(String lingkar) { this.lingkar = lingkar; }
    public void setTinggi(String tinggi) { this.tinggi = tinggi; }
    public void setJarak(String jarak) { this.jarak = jarak; }
    public void setBanyak(int banyak) { this.banyak = banyak; }
    public void setKebun(int kebun) { this.kebun = kebun; }
    public void setWaktu(String waktu) { this.waktu = waktu; }
    public void setUserID(int userid) { this.userid = userid; }
    public void setAfdeling(String afdeling) { this.afdeling = afdeling; }
    public void setBlok(String blok) { this.blok = blok; }
    public void setTTanam(int ttanam) { this.ttanam = ttanam; }
    public void setTPanen(int tpanen) { this.tpanen = tpanen; }
    public void setAPanen(String apanen) { this.apanen = apanen; }
    public void setBPanen(String bpanen) { this.bpanen = bpanen; }
    public void setSPanen(String spanen) { this.spanen = spanen; }
    public void setKet(String ket) { this.ket = ket; }
    public void setCX(String cx) { this.cx = cx; }
    public void setCY(String cy) { this.cy = cy; }
    public void setKirim(int kirim) { this.kirim = kirim; }
    public void setDariServer(int dari_server) { this.dari_server = dari_server; }
    public void setIDServer(int id_server) { this.id_server = id_server; }
    public void setAsli(int asli) { this.asli = asli; }

    public int getUrut() { return this.urut; }
    public int getJenis() { return this.jenis; }
    public String getLingkar() { return this.lingkar; }
    public String getTinggi() { return this.tinggi; }
    public String getJarak() { return this.jarak; }
    public int getBanyak() { return this.banyak; }
    public int getKebun() { return this.kebun; }
    public String getWaktu() { return this.waktu; }
    public int getUserID() { return this.userid; }
    public String getAfdeling() { return this.afdeling; }
    public String getBlok() { return this.blok; }
    public int getTTanam() { return this.ttanam; }
    public int getTPanen() { return this.tpanen; }
    public String getAPanen() { return this.apanen; }
    public String getBPanen() { return this.bpanen; }
    public String getSPanen() { return this.spanen; }
    public String getKet() { return this.ket; }
    public String getCX() { return this.cx; }
    public String getCY() { return this.cy; }
    public int getKirim() { return this.kirim; }
    public int getDariServer() { return this.dari_server; }
    public int getIDServer() { return this.id_server; }
    public int getAsli() { return this.asli; }
}
