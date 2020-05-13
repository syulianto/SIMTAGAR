package com.ptpn12.simtagar;

public class DB_Setting {
    String webip;
    String vd;
    int ikirim;
    int dver;
    int pz;
    int pl;
    int jmap;
    int tfoto;
    int taktif;
    int tinstruksi;
    int ttrack;
    int tkml;
    int tteman;
    int activeuser;
    int alarm;
    int jam;
    int menit;
    String pesan;
    String int_inst;
    String int_outbound;
    String int_setting;
    String int_teman;
    String int_gps;
    String int_internet;
    String int_track;
    String int_lateins;
    int tampil_kejadian;
    int tampil_patok;
    int tampil_pekerjaan;
    int tampil_aktifitas;
    int tampil_jalan;
    int tampil_jembatan;
    int tampil_tanaman;
    int tampil_obyek;
    int tampil_saluran;
    int tampil_geotag;
    int activekebun;
    int jkoord;
    //int_inst text, int_outbound text, int_setting text, int_teman text, "
    //+ "int_gps text, int_internet text

    public DB_Setting() {}

    public DB_Setting(String webip, String vd, int ikirim, int dver, int pz, int pl, int jmap, int activeuser, int tfoto, int taktif,
                      int tinstruksi, int ttrack, int tkml, int tteman, int alarm, int jam, int menit, String pesan, String int_inst, String int_outbound,
                      String int_setting, String int_teman, String int_gps, String int_internet, String int_track,
                      String int_lateins, int tampil_kejadian, int tampil_patok, int tampil_pekerjaan, int tampil_aktifitas,
                      int tampil_jalan, int tampil_jembatan, int tampil_tanaman, int tampil_obyek, int tampil_saluran,
                      int tampil_geotag, int activekebun, int jkoord) {
        this.webip = webip;
        this.vd = vd;
        this.ikirim = ikirim;
        this.dver = dver;
        this.pz = pz;
        this.pl = pl;
        this.jmap = jmap;
        this.tfoto = tfoto;
        this.taktif = taktif;
        this.tinstruksi = tinstruksi;
        this.ttrack = ttrack;
        this.tkml = tkml;
        this.tteman = tteman;
        this.activeuser = activeuser;
        this.alarm = alarm;
        this.jam = jam;
        this.menit = menit;
        this.pesan = pesan;
        this.int_inst = int_inst;
        this.int_outbound = int_outbound;
        this.int_setting = int_setting;
        this.int_teman = int_teman;
        this.int_gps = int_gps;
        this.int_internet = int_internet;
        this.int_track = int_track;
        this.int_lateins = int_lateins;
        this.tampil_aktifitas = tampil_aktifitas;
        this.tampil_geotag = tampil_geotag;
        this.tampil_jalan = tampil_jalan;
        this.tampil_jembatan = tampil_jembatan;
        this.tampil_kejadian = tampil_kejadian;
        this.tampil_obyek = tampil_obyek;
        this.tampil_patok = tampil_patok;
        this.tampil_pekerjaan = tampil_pekerjaan;
        this.tampil_saluran = tampil_saluran;
        this.tampil_tanaman = tampil_tanaman;
        this.activekebun = activekebun;
        this.jkoord = jkoord;
    }

    public void setWebip(String webip) { this.webip = webip; }
    public void setVd(String vd) { this.vd = vd; }
    public void setIkirim(int ikirim) { this.ikirim = ikirim; }
    public void setDver(int dver) { this.dver = dver; }
    public void setPz(int pz) { this.pz = pz; }
    public void setPl(int pl) { this.pl = pl; }
    public void setJMap(int jmap) { this.jmap = jmap; }
    public void setActiveUser(int activeuser) { this.activeuser = activeuser; }
    public void setTFoto(int tfoto) { this.tfoto = tfoto; }
    public void setTAktif(int taktif) { this.taktif = taktif; }
    public void setTInstruksi(int tinstruksi) { this.tinstruksi = tinstruksi; }
    public void setTTrack(int ttrack) { this.ttrack = ttrack; }
    public void setTKML(int tkml) { this.tkml = tkml; }
    public void setTTeman(int tteman) { this.tteman = tteman; }
    public void setAlarm(int alarm) { this.alarm = alarm; }
    public void setJam(int jam) { this.jam = jam; }
    public void setMenit(int menit) { this.menit = menit; }
    public void setPesan(String pesan) { this.pesan = pesan; }
    public void setIntInstruksi(String int_inst) { this.int_inst = int_inst; }
    public void setIntOutbound(String int_outbound) { this.int_outbound = int_outbound; }
    public void setIntSetting(String int_setting) { this.int_setting = int_setting; }
    public void setIntTeman(String int_teman) { this.int_teman = int_teman; }
    public void setIntGPS(String int_gps) { this.int_gps = int_gps; }
    public void setIntInternet(String int_internet) { this.int_internet = int_internet; }
    public void setIntTrack(String int_track) { this.int_track = int_track; }
    public void setIntLateIns(String int_lateins) { this.int_lateins = int_lateins; }
    public void setTampilKejadian(int tampil_kejadian) { this.tampil_kejadian = tampil_kejadian; }
    public void setTampilPatok(int tampil_patok) { this.tampil_patok = tampil_patok; }
    public void setTampilPekerjaan(int tampil_pekerjaan) { this.tampil_pekerjaan = tampil_pekerjaan; }
    public void setTampilAktifitas(int tampil_aktifitas) { this.tampil_aktifitas = tampil_aktifitas; }
    public void setTampilJalan(int tampil_jalan) { this.tampil_jalan = tampil_jalan; }
    public void setTampilJembatan(int tampil_jembatan) { this.tampil_jembatan = tampil_jembatan; }
    public void setTampilTanaman(int tampil_tanaman) { this.tampil_tanaman = tampil_tanaman; }
    public void setTampilObyek(int tampil_obyek) { this.tampil_obyek = tampil_obyek; }
    public void setTampilSaluran(int tampil_saluran) { this.tampil_saluran = tampil_saluran; }
    public void setTampilGeotag(int tampil_geotag) { this.tampil_geotag = tampil_geotag; }
    public void setActiveKebun(int activekebun) { this.activekebun = activekebun; }
    public void setJKoord(int jkoord) { this.jkoord = jkoord; }


    public String getWebip() { return this.webip; }
    public String getVd() { return this.vd; }
    public int getIkirim() { return this.ikirim; }
    public int getDver() { return this.dver; }
    public int getPz() { return this.pz; }
    public int getPl() { return this.pl; }
    public int getJMap() { return this.jmap; }
    public int getActiveUser() { return this.activeuser; }
    public int getTFoto() { return this.tfoto; }
    public int getTAktif() { return this.taktif; }
    public int getTInstruksi() { return this.tinstruksi; }
    public int getTTrack() { return this.ttrack; }
    public int getTKML() { return this.tkml; }
    public int getTTeman() { return this.tteman; }
    public int getAlarm() { return this.alarm; }
    public int getJam() { return this.jam; }
    public int getMenit() { return this.menit; }
    public String getPesan() { return this.pesan; }
    public String getIntInstruksi() { return this.int_inst; }
    public String getIntOutbound() { return this.int_outbound; }
    public String getIntSetting() { return this.int_setting; }
    public String getIntTeman() { return this.int_teman; }
    public String getIntGPS() { return this.int_gps; }
    public String getIntInternet() { return this.int_internet; }
    public String getIntTrack() { return this.int_track; }
    public String getIntLateIns() { return this.int_lateins; }
    public int getTampilKejadian() { return this.tampil_kejadian; }
    public int getTampilPatok() { return this.tampil_patok; }
    public int getTampilPekerjaan() { return this.tampil_pekerjaan; }
    public int getTampilAktifitas() { return this.tampil_aktifitas; }
    public int getTampilJalan() { return this.tampil_jalan; }
    public int getTampilJembatan() { return this.tampil_jembatan; }
    public int getTampilTanaman() { return this.tampil_tanaman; }
    public int getTampilObyek() { return this.tampil_obyek; }
    public int getTampilSaluran() { return this.tampil_saluran; }
    public int getTampilGeotag() { return this.tampil_geotag; }
    public int getActiveKebun() { return this.activekebun; }
    public int getJKoord() { return this.jkoord; }
}
