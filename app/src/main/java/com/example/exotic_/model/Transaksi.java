package com.example.exotic_.model;

import com.google.firebase.database.Exclude;

public class Transaksi {
    private String NamaPelanggan;
    private String Uid;
    private String AlamatPelanggan;
    private String Nohp;
    private String GambarAkun;
    private String TotalBayar;
    private String Key;

public Transaksi(){
    //Penting OJO DI HAPUS SAT!!!!!!!!!!
}

    public Transaksi(String namaPelanggan, String uid, String alamatPelanggan, String nohp, String gambarAkun, String totalBayar) {
        NamaPelanggan = namaPelanggan;
        Uid = uid;
        AlamatPelanggan = alamatPelanggan;
        Nohp = nohp;
        GambarAkun = gambarAkun;
        TotalBayar = totalBayar;
    }

    public String getNamaPelanggan() {
        return NamaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        NamaPelanggan = namaPelanggan;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getAlamatPelanggan() {
        return AlamatPelanggan;
    }

    public void setAlamatPelanggan(String alamatPelanggan) {
        AlamatPelanggan = alamatPelanggan;
    }

    public String getNohp() {
        return Nohp;
    }

    public void setNohp(String nohp) {
        Nohp = nohp;
    }

    public String getGambarAkun() {
        return GambarAkun;
    }

    public void setGambarAkun(String gambarAkun) {
        GambarAkun = gambarAkun;
    }

    public String getTotalBayar() {
        return TotalBayar;
    }

    public void setTotalBayar(String totalBayar) {
        TotalBayar = totalBayar;
    }

    @Exclude
    public String getKey() {
        return Key;
    }

    @Exclude
    public void setKey(String key) {
        Key = key;
    }
}
