package com.example.booksstore.repository;

import com.example.booksstore.entities.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDonHangRepo extends JpaRepository<DonHang, Integer> {
}
