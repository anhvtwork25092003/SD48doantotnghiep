package com.example.booksstore.repository;

import com.example.booksstore.entities.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IKhachHangRepository extends JpaRepository<KhachHang, Integer> {
    Page<KhachHang> findAll(Pageable pageable);
}
