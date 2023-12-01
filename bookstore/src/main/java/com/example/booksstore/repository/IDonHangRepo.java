package com.example.booksstore.repository;

import com.example.booksstore.entities.DonHang;
import com.example.booksstore.entities.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDonHangRepo extends JpaRepository<DonHang, Integer> {
    List<DonHang> findAllBykhachHang(KhachHang khachHang);
    Page<DonHang> findAllByTrangThaiOrderByIdDonHang(Pageable pageable, int trangThai);
    Page<DonHang> findByMaDonHangContainingAndKhachHang_SdtContaining(Pageable pageable,String maDonHang, String sdt);
    List<DonHang> findByMaDonHangContaining(String maDonHang);
    DonHang findByIdDonHang (int idDonHang);
}