package com.example.booksstore.repository;

import com.example.booksstore.entities.DiaChi;
import com.example.booksstore.entities.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDiaChiRepository extends JpaRepository<DiaChi, Integer> {
    Page<DiaChi> findAll(Pageable pageable);


    List<DiaChi> findAllByKhachHangDiaChi(KhachHang khachHang);
}
