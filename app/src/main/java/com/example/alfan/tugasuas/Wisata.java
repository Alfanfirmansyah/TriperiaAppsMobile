package com.example.alfan.tugasuas;

public class Wisata {
    private int id;
    private String judul;
    private String alamat;
    private String pin;
    private String deskripsi;
    private String kategori;

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public Wisata(int id, String judul, String alamat, String pin, String deskripsi, String kategori, byte[] image) {
        this.id = id;
        this.judul = judul;
        this.alamat = alamat;
        this.pin = pin;
        this.deskripsi = deskripsi;
        this.kategori = kategori;
        this.image = image;
    }

    private byte[] image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }



}
