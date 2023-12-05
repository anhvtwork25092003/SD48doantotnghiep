package com.example.booksstore.repository;

import com.example.booksstore.entities.DanhGia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDanhGiarepository extends JpaRepository<DanhGia, Integer> {
    List<DanhGia> findAllBySachIdSachAndTrangThai(int idSach, int trangThai);
}
