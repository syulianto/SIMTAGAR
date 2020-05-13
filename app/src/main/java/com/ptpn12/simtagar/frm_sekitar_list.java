package com.ptpn12.simtagar;

public class frm_sekitar_list {
    private String jenis = "";
    private String cx = "";
    private String cy = "";
    private double jarak = 0;
    private String ket = "";
    private int img = 0;

    public String getJenis() { return this.jenis; }
    public String getCX() { return this.cx; }
    public String getCY() { return this.cy; }
    public double getJarak() { return this.jarak; }
    public String getKet() { return this.ket; }
    public int getImg() { return this.img; }

    public void setJenis(String jenis) { this.jenis = jenis; }
    public void setImg ( int img ){ this.img = img; }
    public void setCX (String cx){ this.cx = cx; }
    public void setCY (String cy){ this.cy = cy; }
    public void setKet(String ket) { this.ket = ket; }
    public void setJarak(Double jarak) { this.jarak = jarak; }
}