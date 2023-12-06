package com.example.booksstore.service;

import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.entities.KiemTraDanhGia;
import com.example.booksstore.entities.Sach;

public interface IKiemTraDanhGiaService {
    boolean checkKhaNangDanhGia(int idKhachHang, int idSach);

    void giamLuotDanhGia(int idKhachHang, int idSach);

    boolean xacMinhDanhGiaKhachHang(int idKhachHang, int idDanhGia);

    KiemTraDanhGia save(KhachHang khachHang, Sach sach);

}
