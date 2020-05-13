package com.ptpn12.simtagar;

public class DB_Ref_Afdeling {
    int kode;
    int idkebun;
    String nama;

    public DB_Ref_Afdeling() {}

    public DB_Ref_Afdeling(int idkebun, int kode, String nama) {
        this.idkebun = idkebun;
        this.kode = kode;
        this.nama = nama;
    }

    public void setIdkebun(int idkebun) { this.idkebun = idkebun; }
    public void setKode(int kode) { this.kode = kode; }
    public void setNama(String nama) { this.nama = nama; }

    public int getIdkebun() { return this.idkebun; }
    public int getKode() { return this.kode; }
    public String getNama() { return this.nama; }
}
