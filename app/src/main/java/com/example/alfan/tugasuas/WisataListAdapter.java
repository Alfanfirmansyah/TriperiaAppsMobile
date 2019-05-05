package com.example.alfan.tugasuas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Quoc Nguyen on 13-Dec-16.
 */

public class WisataListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Wisata> wisataList;

    public WisataListAdapter(Context context, int layout, ArrayList<Wisata> wisataList) {
        this.context = context;
        this.layout = layout;
        this.wisataList = wisataList;
    }

    @Override
    public int getCount() {
        return wisataList.size();
    }

    @Override
    public Object getItem(int position) {
        return wisataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtjudul, txtalamat,txtpin,txtdeskripsi;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);


            holder.txtjudul = (TextView) row.findViewById(R.id.ljudul);
            holder.txtpin = (TextView) row.findViewById(R.id.lpin);
            holder.imageView = (ImageView) row.findViewById(R.id.imgFood);

            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Wisata wisata = wisataList.get(position);

        holder.txtjudul.setText(wisata.getJudul());
        holder.txtpin.setText(wisata.getAlamat());
        byte[] wisataImage = wisata.getImage();

        Bitmap bitmap = BitmapFactory.decodeByteArray(wisataImage, 0, wisataImage.length);
        holder.imageView.setImageBitmap(bitmap);


        return row;
    }
}
