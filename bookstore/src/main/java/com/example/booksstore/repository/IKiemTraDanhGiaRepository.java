package com.example.booksstore.repository;

import com.example.booksstore.entities.KiemTraDanhGia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IKiemTraDanhGiaRepository extends JpaRepository<KiemTraDanhGia, Integer> {
    KiemTraDanhGia findKiemTraDanhGiaByKhachHangIdKhachHangAndSachIdSach(int idKhachHang, int idSach);
}
