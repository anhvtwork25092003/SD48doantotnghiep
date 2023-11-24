package com.example.booksstore.repository;

import com.example.booksstore.entities.ThongTinGiaoHang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IThongTinGiaoHangRepo  extends JpaRepository<ThongTinGiaoHang, Integer> {
}
