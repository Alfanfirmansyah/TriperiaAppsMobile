package com.example.alfan.tugasuas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;



public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context, String judul, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, judul, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String judul, String alamat, String pin, String deskripsi,String kategori, byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO wisata VALUES (NULL, ?, ?, ?,?,?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();


        statement.bindString(1, judul);
        statement.bindString(2, alamat);
        statement.bindString(3, pin);
        statement.bindString(4, deskripsi);
        statement.bindString(5, kategori);
        statement.bindBlob(6, image);


        statement.executeInsert();
    }

    public void updateData(String judul, String alamat,String pin,String deskripsi,String kategori, byte[] image, int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE wisata SET judul = ?, alamat = ?, pin = ?, deskripsi = ? , kategori = ?,image = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, judul);
        statement.bindString(2, alamat);
        statement.bindString(3, pin);
        statement.bindString(4, deskripsi);
        statement.bindString(5, kategori);
        statement.bindBlob(6, image);
        statement.bindDouble(7, (double)id);

        statement.execute();
        database.close();
    }

    public  void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM WISATA WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
