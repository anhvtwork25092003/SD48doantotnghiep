package com.example.booksstore.service;

import com.example.booksstore.dto.ThongKeKhachHangResponse;
import com.example.booksstore.dto.TopSanPhamDTO;
import com.example.booksstore.entities.DonHang;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ThongKeService {
    int soLuongDonHang(int trangThai);

    BigDecimal tinhDoanhThuTheoMocThoiGian(Date startTime, Date endTime);

    BigDecimal getDoanhThuNgayHienTai();

    BigDecimal getDoanhThuNgayVuaQua();

    List<DonHang> getDonHangTrongThangHienTai();

    List<TopSanPhamDTO> getTopSanPhamBanChayNhat();

    ThongKeKhachHangResponse tinhTongSoLuongKhachHangMoi();

}
