package com.example.booksstore.repository;

import com.example.booksstore.entities.DonHang;
import com.example.booksstore.entities.DonHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonHangChiTietRepo extends JpaRepository<DonHangChiTiet, Integer> {
}
