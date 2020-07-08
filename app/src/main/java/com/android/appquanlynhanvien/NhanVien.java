package com.android.appquanlynhanvien;

public class NhanVien {
    public int ID;
    public  String Ten;
    public  String SDT;
    public  byte[] Anh;

    public NhanVien(int ID, String ten, String SDT, byte[] anh) {
        this.ID = ID;
        this.Ten = ten;
        this.SDT = SDT;
        this.Anh = anh;
    }

}
