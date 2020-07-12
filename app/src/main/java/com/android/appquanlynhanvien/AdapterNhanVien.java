package com.android.appquanlynhanvien;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static java.nio.file.Files.delete;

public class AdapterNhanVien extends BaseAdapter {
    Activity context;
    ArrayList<NhanVien> list;

    public AdapterNhanVien(Activity context, ArrayList<NhanVien> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview_row, null);
            ImageView imgHinhDaiDien = (ImageView) convertView.findViewById(R.id.imgAnh);
            TextView txttid = (TextView) convertView.findViewById(R.id.txtid);
            TextView txtten = (TextView) convertView.findViewById(R.id.txtten);
            TextView txtsdt = (TextView) convertView.findViewById(R.id.txtsdt);
            Button btnsua = (Button) convertView.findViewById(R.id.btnsua);
            Button btnxoa = (Button) convertView.findViewById(R.id.btnxoa);

            final NhanVien nhanVien = list.get(position);
            txttid.setText(nhanVien.ID + "");
            txtten.setText(nhanVien.Ten);
            txtsdt.setText(nhanVien.SDT);

            Bitmap bmHinhDaiDien = BitmapFactory.decodeByteArray(nhanVien.Anh, 0, nhanVien.Anh.length);
            imgHinhDaiDien.setImageBitmap(bmHinhDaiDien);
            btnsua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("ID", nhanVien.ID);
                    context.startActivity(intent);
                }
            });
            btnxoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setIcon(android.R.drawable.ic_delete);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên này không?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete(nhanVien.ID);
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

        return convertView;

    }

    private void delete(int idNhanVien) {
        SQLiteDatabase database = Database.initDatabase(context, "appnhanvien.db");
        database.delete("NhanVien", "ID = ?", new String[] {idNhanVien + ""});
        list.clear();
        Cursor cursor =database.rawQuery("SELECT * FROM NhanVien", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String sdt = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);

            list.add(new NhanVien(id, ten, sdt, anh));
        }
        notifyDataSetChanged();
    }
}
