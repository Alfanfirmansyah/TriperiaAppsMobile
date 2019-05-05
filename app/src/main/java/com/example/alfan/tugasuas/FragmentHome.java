package com.example.alfan.tugasuas;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.database.SQLException;
import java.sql.SQLDataException;
import java.util.ArrayList;

import io.supercharge.shimmerlayout.ShimmerLayout;



public class FragmentHome extends Fragment {
    GridView gridView;
    ArrayList<Wisata> list;
    WisataListAdapter adapter = null;
    public static SQLiteHelper sqLiteHelper;
    View view;
    String[] daftar1;
    public FragmentHome() {

    }


    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        sqLiteHelper = new SQLiteHelper(getActivity().getApplicationContext(), "wisatadb", null, 1);
        view = inflater.inflate(R.layout.fragment_home, container, false);
        gridView = (GridView) view.findViewById(R.id.gridViewF);
        list = new ArrayList<>();
        adapter = new WisataListAdapter(getActivity().getApplicationContext(), R.layout.wisata_items, list);


        gridView.setAdapter(adapter);


        // get all data from sqlite
try {
    Cursor cursor = FragmentHome.sqLiteHelper.getData("SELECT * FROM wisata order by Id DESC LIMIT 6");
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
}
catch (Exception e){
    e.printStackTrace();
}
        adapter.notifyDataSetChanged();
        /// Menampilkan Detail Daftar Wisata ///
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int arg1, long id) {

                SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
                Cursor cur = db.rawQuery("SELECT * FROM wisata order by Id DESC", null);

                daftar1 = new String[cur.getCount()];

                cur.moveToFirst();
                for (int cc = 0; cc < cur.getCount(); cc++) {
                    cur.moveToPosition(cc);
                    daftar1[cc] = cur.getString(0).toString();
                }
                final String selection = daftar1[arg1];

                Cursor cursor = FragmentHome.sqLiteHelper.getData("SELECT * FROM wisata where Id= "+selection);

                cursor.moveToNext();
                String judul = cursor.getString(1);
                String alamat = cursor.getString(2);
                String pin = cursor.getString(3);
                String deskripsi = cursor.getString(4);
                String kategori = cursor.getString(5);
                byte[] image = cursor.getBlob(6);


                CharSequence items = ""+deskripsi+" \n\nPin : "+pin+" \n\nAlamat : "+alamat;
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

                dialog.setTitle("Detail  ");
                dialog.setMessage(items);

                dialog.show();
                return true;
            }
        });

        return view;
    }





}
