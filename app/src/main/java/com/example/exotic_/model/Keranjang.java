package com.example.exotic_.model;

import com.google.firebase.database.Exclude;

public class Keranjang {
    private String NamaPelanggan;
    private String Uid;
    private String Alamat;
    private String Nohp;
    private String NamaBarang;
    private String JumlahBarang;
    private String HargaBarang;
    private String GambarBarang;
    private String Key;

public Keranjang(){
    //Penting OJO DI HAPUS SAT!!!!!!!!!!
}

    public Keranjang(String namaPelanggan, String uid, String alamat, String nohp, String namaBarang, String jumlahBarang, String hargaBarang, String gambarBarang) {
        NamaPelanggan = namaPelanggan;
        Uid = uid;
        Alamat = alamat;
        Nohp = nohp;
        NamaBarang = namaBarang;
        JumlahBarang = jumlahBarang;
        HargaBarang = hargaBarang;
        GambarBarang = gambarBarang;
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

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public String getNohp() {
        return Nohp;
    }

    public void setNohp(String nohp) {
        Nohp = nohp;
    }

    public String getNamaBarang() {
        return NamaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        NamaBarang = namaBarang;
    }

    public String getJumlahBarang() {
        return JumlahBarang;
    }

    public void setJumlahBarang(String jumlahBarang) {
        JumlahBarang = jumlahBarang;
    }

    public String getHargaBarang() {
        return HargaBarang;
    }

    public void setHargaBarang(String hargaBarang) {
        HargaBarang = hargaBarang;
    }

    public String getGambarBarang() {
        return GambarBarang;
    }

    public void setGambarBarang(String gambarBarang) {
        GambarBarang = gambarBarang;
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
