package com.example.booksstore.repository;

import com.example.booksstore.entities.ThongBaoKhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IThongBaoKhachHangRepo extends JpaRepository<ThongBaoKhachHang, Integer> {
    // lấy danh sách thông báo qua id người dùng
    List<ThongBaoKhachHang> findByKhachHangIdKhachHang(Integer idKhachHang);

}
