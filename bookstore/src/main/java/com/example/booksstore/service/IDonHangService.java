package com.example.booksstore.service;

import com.example.booksstore.entities.DonHangChiTiet;

public interface IDonHangService {
    void truSoLuongTonKho(DonHangChiTiet donHangChiTietDaMuatronghoaDon);

    void tangSoLuongTonKho(DonHangChiTiet donHangChiTiet);
}
