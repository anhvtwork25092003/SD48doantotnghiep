package com.example.booksstore.service;

import com.example.booksstore.entities.DonHangChiTiet;
import com.example.booksstore.entities.GioHangChiTiet;

import java.math.BigDecimal;
import java.util.List;

public interface IDonHangService {
    void truSoLuongTonKho(DonHangChiTiet donHangChiTietDaMuatronghoaDon);

    void tangSoLuongTonKho(DonHangChiTiet donHangChiTiet);

    BigDecimal tinhTongTienDonHangDaTinhVanChuyen(List<GioHangChiTiet> gioHangChiTietList);
    BigDecimal tinhTongTienDonHangChuaTinhVanChuyen(List<GioHangChiTiet> gioHangChiTietList);
}
