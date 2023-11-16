package com.example.booksstore.repository;

import com.example.booksstore.entities.GioHang;
import com.example.booksstore.entities.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GioHangRepository extends JpaRepository<GioHang, Integer> {
    GioHang findByKhachHang(KhachHang khachHang);
}
