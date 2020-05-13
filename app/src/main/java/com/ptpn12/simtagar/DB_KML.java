package com.ptpn12.simtagar;

public class DB_KML {
    int id;
    String nama;
    int idkebun;
    int idrow;
    int idtoc;
    int idpeta;
    String folder;
    int tampil;
    int sudah;

    public DB_KML() {}

    //private static final String[] TBL_KML_COL = {"id", "nama", "idkebun", "idrow", "idtoc", "idpeta", "folder"};
    public DB_KML(int id, String nama, int idkebun, int idrow, int idtoc, int idpeta, String folder, int tampil, int sudah) {
        this.id = id;
        this.nama = nama;
        this.idkebun = idkebun;
        this.idrow = idrow;
        this.idtoc = idtoc;
        this.idpeta = idpeta;
        this.folder = folder;
        this.tampil = tampil;
        this.sudah = sudah;
    }

    public int getID() { return this.id; }
    public String getNama() { return this.nama; }
    public int getIDKebun() { return this.idkebun; }
    public int getIDRow() { return this.idrow; }
    public int getIDTOC() { return this.idtoc; }
    public int getIDPeta() { return this.idpeta; }
    public String getFolder() { return this.folder; }
    public int getTampil() { return this.tampil; }
    public int getSudah() { return this.sudah; }

    public void setID(int id) { this.id = id; }
    public void setNama(String nama) { this.nama = nama; }
    public void setIDKebun(int idkebun) { this.idkebun = idkebun; }
    public void setIDRow(int idrow) { this.idrow = idrow; }
    public void setIDTOC(int idtoc) { this.idtoc = idtoc; }
    public void setIDPeta(int idpeta) { this.idpeta = idpeta; }
    public void setFolder(String folder) { this.folder = folder; }
    public void setTampil(int tampil) { this.tampil = tampil; }
    public void setSudah(int sudah) { this.sudah = sudah; }
}
