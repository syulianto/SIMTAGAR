package com.ptpn12.simtagar;

public class DB_User {
    //private static final String TBL_USER_DB = "create table tbl_user ("
    //		+ "aid integer primary key autoincrement, devid text, userid integer, nlengkap text, nlogin text, "
    //		+ "pwd text, tkt integer, sbu text, kebun text, afd text, blok text, nsbu text, nkebun text);";

    int id;
    String idpeg;
    String nama;
    String devid;
    String nohp;
    String email;
    int idkebun;
    String kebunkml;
    int tingkat;
    String jabatan;

    public DB_User() {}

    public DB_User(int id, String idpeg, String nama, String devid, String nohp, String email, int idkebun,
                   String kebunkml, int tingkat, String jabatan) {
        this.id = id;
        this.idpeg = idpeg;
        this.nama = nama;
        this.devid = devid;
        this.nohp = nohp;
        this.email = email;
        this.idkebun = idkebun;
        this.kebunkml = kebunkml;
        this.tingkat = tingkat;
        this.jabatan = jabatan;
    }

    public void setID(int id) { this.id = id; }
    public void setIDPeg(String idpeg) { this.idpeg = idpeg; }
    public void setNama(String nama) { this.nama = nama; }
    public void setDevid(String devid) { this.devid = devid; }
    public void setNoHP(String nohp) { this.nohp = nohp; }
    public void setEmail(String email) { this.email = email; }
    public void setIDKebun(int idkebun) { this.idkebun = idkebun; }
    public void setKebunKML(String kebunkml) { this.kebunkml = kebunkml; }
    public void setTingkat(int tingkat) { this.tingkat = tingkat; }
    public void setJabatan(String jabatan) { this.jabatan = jabatan; }

    public int getID() { return this.id; }
    public String getIDPeg() { return this.idpeg; }
    public String getNama() { return this.nama; }
    public String getDevid() { return this.devid; }
    public String getNoHP() { return this.nohp; }
    public String getEmail() { return this.email; }
    public int getIDKebun() { return this.idkebun; }
    public String getKebunKML() { return this.kebunkml; }
    public int getTingkat() { return this.tingkat; }
    public String getJabatan() { return this.jabatan; }
}
