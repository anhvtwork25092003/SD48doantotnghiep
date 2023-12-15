package com.example.booksstore.service;


import com.example.booksstore.entities.DonHangChiTiet;

import java.util.List;

public interface TraHangService {

    List<DonHangChiTiet> danhSachSanPhamCoTheDoiTra(int idDonHang);
}
