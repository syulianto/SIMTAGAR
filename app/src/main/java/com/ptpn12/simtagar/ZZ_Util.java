package com.ptpn12.simtagar;

public class ZZ_Util {
    public static String sws_LPad(String str, Integer length, char car) {
        return (str + String.format("%" + length + "s", "").replace(" ", String.valueOf(car))).substring(0, length);
    }

    public static String sws_RPad(String str, Integer length, char car) {
        return (String.format("%" + length + "s", "").replace(" ", String.valueOf(car)) + str).substring(str.length(), length + str.length());
    }

    public static String sws_getTgl(String atgl) {
        if( atgl.equals("")) {
            return " ";
        }
        String[] dum = atgl.split("-");
        if( dum.length != 3 ) {
            return " ";
        }
        String output = dum[2];
        int abln = Integer.parseInt(dum[1]);
        if( abln == 1 ) { output = output + " Januari " + dum[0]; }
        if( abln == 2 ) { output = output + " Februari " + dum[0]; }
        if( abln == 3 ) { output = output + " Maret " + dum[0]; }
        if( abln == 4 ) { output = output + " April " + dum[0]; }
        if( abln == 5 ) { output = output + " Mei " + dum[0]; }
        if( abln == 6 ) { output = output + " Juni " + dum[0]; }
        if( abln == 7 ) { output = output + " Juli " + dum[0]; }
        if( abln == 8 ) { output = output + " Agustus " + dum[0]; }
        if( abln == 9 ) { output = output + " September " + dum[0]; }
        if( abln == 10 ) { output = output + " Oktober " + dum[0]; }
        if( abln == 11 ) { output = output + " November " + dum[0]; }
        if( abln == 12 ) { output = output + " Desember " + dum[0]; }
        return output;
    }
}
