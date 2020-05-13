package com.ptpn12.simtagar;

public class DB_Pekerja {
    int urut;
    int idkebun;
    int idafd;
    int idmandor;
    String idpeg;
    String nama;
    int jab;

    public DB_Pekerja() {}

    public DB_Pekerja(int urut, int idkebun, int idafd, int idmandor, String idpeg, String nama, int jab) {
        this.urut = urut;
        this.idkebun = idkebun;
        this.idafd = idafd;
        this.idmandor = idmandor;
        this.idpeg = idpeg;
        this.nama = nama;
        this.jab = jab;
    }

    public void setUrut(int urut) { this.urut = urut; }
    public void setIDkebun(int idkebun) { this.idkebun = idkebun; }
    public void setIDAfd(int idafd) { this.idafd = idafd; }
    public void setIDMandor(int idmandor) { this.idmandor = idmandor; }
    public void setIDPeg(String idpeg) { this.idpeg = idpeg; }
    public void setNama(String nama) { this.nama = nama; }
    public void setJabatan(int jab) { this.jab = jab; }

    public int getUrut() { return this.urut; }
    public int getIDKebun() { return this.idkebun; }
    public int getIDAfd() { return this.idafd; }
    public int getIDMandor() { return this.idmandor; }
    public String getIDPeg() { return this.idpeg; }
    public String getNama() { return this.nama; }
    public int getJabatan() { return this.jab; }
}

