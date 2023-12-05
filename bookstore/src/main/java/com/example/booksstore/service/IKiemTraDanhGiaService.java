package com.example.booksstore.service;

public interface IKiemTraDanhGiaService {
    boolean checkKhaNangDanhGia(int idKhachHang, int idSach);

    void giamLuotDanhGia(int idKhachHang, int idSach);

    boolean xacMinhDanhGiaKhachHang(int idKhachHang, int idDanhGia);
}
