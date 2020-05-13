package com.ptpn12.simtagar;

public class DB_Ref_Jalan_Kondisi {
    int kode;
    String nama;

    public DB_Ref_Jalan_Kondisi() {}

    public DB_Ref_Jalan_Kondisi(int kode, String nama) {
        this.kode = kode;
        this.nama = nama;
    }

    public void setKode(int kode) { this.kode = kode; }
    public void setNama(String nama) { this.nama = nama; }

    public int getKode() { return this.kode; }
    public String getNama() { return this.nama; }
}
