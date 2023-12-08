package com.example.booksstore.repository;

import com.example.booksstore.entities.DanhGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDanhGiarepository extends JpaRepository<DanhGia, Integer> {
    List<DanhGia> findAllBySachIdSachAndTrangThai(int idSach, int trangThai);
    Page<DanhGia> findAllByTrangThai(Pageable pageable,int trangthai);
    List<DanhGia> findAllBySach_IdSachAndTrangThai(Integer idSach,int trangthai);
    Page<DanhGia> findByIdDanhGia(Pageable pageable,Integer id);
}
