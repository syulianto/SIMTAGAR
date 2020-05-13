package com.ptpn12.simtagar;

public class DB_Ref_Jabatan {
    int id;
    String nama;

    public DB_Ref_Jabatan() {}

    public DB_Ref_Jabatan(int id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public void setID(int id) { this.id = id; }
    public void setNama(String nama) { this.nama = nama; }

    public int getID() { return this.id; }
    public String getNama() { return this.nama; }
}
