package com.example.alfan.tugasuas;

import android.database.Cursor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;

public class WisataKategori3 extends AppCompatActivity {
    GridView gridView;
    ArrayList<Wisata> list;
    WisataListAdapter adapter = null;
    String kategori[] = {"-- Pilih Kategori --", "Pantai", "Gunung", "Musium", "AirTerjun", "Taman"};

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_kategori);

        gridView = (GridView) findViewById(R.id.gridView2);
        list = new ArrayList<>();
        adapter = new WisataListAdapter(this, R.layout.wisata_kategori, list);
        gridView.setAdapter(adapter);

        // get all data from sqlite

            Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM wisata where kategori = 'Musium' ");
            list.clear();

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String judul = cursor.getString(1);
                    String alamat = cursor.getString(2);
                    String pin = cursor.getString(3);
                    String deskripsi = cursor.getString(4);
                    String kategori = cursor.getString(5);
                    byte[] image = cursor.getBlob(6);

                    list.add(new Wisata(id, judul, alamat, pin, deskripsi, kategori, image));
                }



        adapter.notifyDataSetChanged();
    }
}