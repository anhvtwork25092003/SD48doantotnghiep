package com.example.booksstore.repository;

import com.example.booksstore.entities.GioHang;
import com.example.booksstore.entities.GioHangChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GioHangChiTietReposutory extends JpaRepository<GioHangChiTiet, Integer> {
    Page<GioHangChiTiet> findAllByGioHang(Pageable pageable, GioHang gioHang);
}
