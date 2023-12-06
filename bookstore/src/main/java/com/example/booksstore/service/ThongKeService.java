package com.example.booksstore.service;

import com.example.booksstore.entities.DonHang;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ThongKeService {
    BigDecimal tinhDoanhThuTheoMocThoiGian(Date startTime, Date endTime);

    BigDecimal getDoanhThuNgayHienTai();

    BigDecimal getDoanhThuNgayVuaQua();
    List<DonHang> getDonHangTrongThangHienTai();
}
