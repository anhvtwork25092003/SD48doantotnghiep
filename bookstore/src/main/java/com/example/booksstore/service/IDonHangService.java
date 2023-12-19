package com.example.booksstore.service;

import com.example.booksstore.entities.DonHang;
import com.example.booksstore.entities.DonHangChiTiet;
import com.example.booksstore.entities.GioHangChiTiet;
import com.example.booksstore.entities.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IDonHangService {
    void truSoLuongTonKho(DonHangChiTiet donHangChiTietDaMuatronghoaDon);

    void tangSoLuongTonKho(DonHangChiTiet donHangChiTiet);

    BigDecimal tinhTongTienDonHangDaTinhVanChuyen(List<GioHangChiTiet> gioHangChiTietList);
    BigDecimal tinhTongTienDonHangChuaTinhVanChuyen(List<GioHangChiTiet> gioHangChiTietList);

    Page<DonHang> searchDOnHang(String maDonHang, Date startDate, Date endDate, int trangThai, Pageable pageable);

}
