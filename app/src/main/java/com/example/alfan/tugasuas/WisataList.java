package com.example.alfan.tugasuas;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class WisataList extends AppCompatActivity {
    GridView gridView;
    ArrayList<Wisata> list;
    WisataListAdapter adapter = null;
    String kategori[] = {"-- Pilih Kategori --","Pantai","Gunung","Musium","AirTerjun","Taman"};

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new WisataListAdapter(this, R.layout.wisata_items, list);
        gridView.setAdapter(adapter);

        // get all data from sqlite
        Cursor cursor = DataWisata.sqLiteHelper.getData("SELECT * FROM wisata");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String judul = cursor.getString(1);
            String alamat = cursor.getString(2);
            String pin = cursor.getString(3);
            String deskripsi = cursor.getString(4);
            String kategori = cursor.getString(5);
            byte[] image = cursor.getBlob(6);

            list.add(new Wisata(id,judul, alamat,pin ,deskripsi,kategori,image));
        }
        adapter.notifyDataSetChanged();

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(WisataList.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            Cursor c = DataWisata.sqLiteHelper.getData("SELECT Id FROM wisata");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            showDialogUpdate(WisataList.this, arrID.get(position));
                        } else {
                            // delete
                            Cursor c = DataWisata.sqLiteHelper.getData("SELECT Id FROM wisata");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });

    }
    ImageView imageViewFood;
    private void showDialogUpdate(Activity activity, final int position){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_wisata_activity);
        dialog.setTitle("Update");
        imageViewFood = (ImageView) dialog.findViewById(R.id.imageViewUpdate);
        final EditText edtJudul = (EditText) dialog.findViewById(R.id.ujudul);
        final EditText edtAlamat = (EditText) dialog.findViewById(R.id.ualamat);
        final EditText edtPin = (EditText) dialog.findViewById(R.id.upin);
        final EditText edtDeskripsi = (EditText) dialog.findViewById(R.id.udeskripsi);
        final Spinner edtSpinner= (Spinner) dialog.findViewById(R.id.ukategori);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);

        ArrayAdapter<String> arrayKategori = new ArrayAdapter<>(this, R.layout.activity_kategori, R.id.textkategori, kategori);
        edtSpinner.setAdapter(arrayKategori);

        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imageViewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request photo library
                ActivityCompat.requestPermissions(
                        WisataList.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kategori = edtSpinner.getSelectedItem().toString();
                try {
                    DataWisata.sqLiteHelper.updateData(
                            edtJudul.getText().toString().trim(),
                            edtAlamat.getText().toString().trim(),
                            edtPin.getText().toString().trim(),
                            edtDeskripsi.getText().toString().trim(),
                            kategori,
                            DataWisata.imageViewToByte(imageViewFood),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update successfully!!!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                updateFoodList();
            }
        });
    }

    private void showDialogDelete(final int Id){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(WisataList.this);

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    DataWisata.sqLiteHelper.deleteData(Id);
                    Toast.makeText(getApplicationContext(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateFoodList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void updateFoodList(){
        // get all data from sqlite
        Cursor cursor = DataWisata.sqLiteHelper.getData("SELECT * FROM wisata");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String judul = cursor.getString(1);
            String alamat = cursor.getString(2);
            String pin = cursor.getString(3);
            String deskripsi = cursor.getString(4);
            String kategori = cursor.getString(5);
            byte[] image = cursor.getBlob(6);

            list.add(new Wisata(id,judul,alamat,pin,deskripsi, kategori, image));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewFood.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
