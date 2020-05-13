package com.ptpn12.simtagar;

public class DB_Absensi {
    int idrow;
    String idpek;
    int idmandor;
    String tgl;
    String jamb;
    String jamp;
    String lokasi;
    String fotob;
    String fotop;
    int adab;
    int adap;
    String cxb;
    String cyb;
    String cxp;
    String cyp;
    String waktub;
    String waktup;
    int kirim;

    public void DB_Absensi() {}

    public void DB_Absensi(
            int idrow, String idpek, int idmandor, String tgl, String jamb, String jamp, String lokasi,
            String fotob, String fotop, int adab, int adap, String cxb, String cyb, String cxp, String cyp, String waktub, String waktup) {
        this.idrow = idrow;
        this.idpek = idpek;
        this.idmandor = idmandor;
        this.tgl = tgl;
        this.jamb = jamb;
        this.jamp = jamp;
        this.lokasi = lokasi;
        this.fotob = fotob;
        this.fotop = fotop;
        this.adab = adab;
        this.adap = adap;
        this.cxb = cxb;
        this.cyb = cyb;
        this.cxp = cxp;
        this.cyp = cyp;
        this.waktub = waktub;
        this.waktup = waktup;
        this.kirim = kirim;
    }

    public void setIDRow( int idrow ) { this.idrow = idrow; }
    public void setIDPek( String idpek ) { this.idpek = idpek; }
    public void setIDMandor( int idmandor ) { this.idrow = idmandor; }
    public void setTgl( String tgl ) { this.tgl = tgl; }
    public void setJamb( String jamb ) { this.jamb = jamb; }
    public void setJamp( String jamp ) { this.jamp = jamp; }
    public void setLokasi( String lokasi ) { this.lokasi = lokasi; }
    public void setFotob( String fotob ) { this.fotob = fotob; }
    public void setFotop( String fotop ) { this.fotop = fotop; }
    public void setAdab(int adab ) { this.adab = adab; }
    public void setAdap(int adap ) { this.adap = adap; }
    public void setCXB(String cxb) { this.cxp = cxb; }
    public void setCYB(String cyb) { this.cyp = cyb; }
    public void setCXP(String cxp) { this.cxp = cxp; }
    public void setCYP(String cyp) { this.cyp = cyp; }
    public void setWaktub(String waktub) { this.waktub = waktub; }
    public void setWaktup(String waktup) { this.waktup = waktup; }
    public void setKirim(int kirim) { this.kirim = kirim; }

    public int getIDRow() { return this.idrow; }
    public String getIDPek() { return this.idpek; }
    public int getIDMandor() { return this.idmandor; }
    public String getTgl() { return this.tgl; }
    public String getJamb() { return this.jamb; }
    public String getJamp() { return this.jamp; }
    public String getLokasi() { return this.lokasi; }
    public String getFotob() { return this.fotob; }
    public String getFotop() { return this.fotop; }
    public int getAdab() { return this.adab; }
    public int getAdap() { return this.adap; }
    public String getCXB() { return this.cxb; }
    public String getCYB() { return this.cyb; }
    public String getCXP() { return this.cxp; }
    public String getCYP() { return this.cyp; }
    public String getWaktub() { return this.waktub; }
    public String getWaktup() { return this.waktup; }
    public int getKirim() { return this.kirim; }
}
