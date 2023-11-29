package com.example.booksstore.repository;

import com.example.booksstore.entities.DonHang;
import com.example.booksstore.entities.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDonHangRepo extends JpaRepository<DonHang, Integer> {
    List<DonHang> findAllBykhachHang(KhachHang khachHang);
}
