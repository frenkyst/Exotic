package com.example.exotic_.model;

import com.google.firebase.database.Exclude;

public class BarangAksesoris {
private String NamaBarang;
private String JenisBarang;
private String JumlahBarang;
private String HargaBarang;
private String GambarBarang;
    private String Key;

public BarangAksesoris(){
    //Penting OJO DI HAPUS SAT!!!!!!!!!!
}

    public BarangAksesoris(String namaBarang, String jenisBarang, String jumlahBarang, String hargaBarang, String gambarBarang) {
        NamaBarang = namaBarang;
        JenisBarang = jenisBarang;
        JumlahBarang = jumlahBarang;
        HargaBarang = hargaBarang;
        GambarBarang = gambarBarang;
    }

    public String getNamaBarang() {
        return NamaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        NamaBarang = namaBarang;
    }

    public String getJenisBarang() {
        return JenisBarang;
    }

    public void setJenisBarang(String jenisBarang) {
        JenisBarang = jenisBarang;
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
