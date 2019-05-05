package com.example.alfan.tugasuas;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class MainActivity extends AppCompatActivity {

    ImageView img1,img2,img3,img4,img5;
    WisataList wl;
    MainActivity ma;
    FragmentHome home;
    TextView lsemua;
    public static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteHelper = new SQLiteHelper(this, "wisatadb", null, 1);
        home = new FragmentHome();

//menampilkan fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.container2,home);
       transaction.commit();


        lsemua   = (TextView) findViewById(R.id.seeall);
        img1 = (ImageView) findViewById(R.id.pantai);
        img2 = (ImageView) findViewById(R.id.gunung);
        img3 = (ImageView) findViewById(R.id.musium);
        img4 = (ImageView) findViewById(R.id.airterjun);
        img5 = (ImageView) findViewById(R.id.taman);



        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,WisataKategori.class);
                startActivity(intent);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WisataKategori2.class);
                startActivity(intent);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WisataKategori3.class);
                startActivity(intent);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WisataKategori4.class);
                startActivity(intent);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WisataKategori5.class);
                startActivity(intent);
            }
        });
        lsemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WisataAll.class);
                startActivity(intent);
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mg:
                Intent itn = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(itn);
                break;
            case R.id.action_grid:
                Intent itn2 = new Intent(MainActivity.this,HubungiActivity.class);
                startActivity(itn2);
                break;
            case R.id.action_cardview:
                Intent itn3 = new Intent(MainActivity.this,BantuanActivity.class);
                startActivity(itn3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
