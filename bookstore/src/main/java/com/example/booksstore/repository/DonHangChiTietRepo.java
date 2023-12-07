package com.example.booksstore.repository;

import com.example.booksstore.dto.TopSanPhamDTO;
import com.example.booksstore.entities.DonHang;
import com.example.booksstore.entities.DonHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DonHangChiTietRepo extends JpaRepository<DonHangChiTiet, Integer> {

    @Query("SELECT new com.example.booksstore.dto.TopSanPhamDTO(d.sach.tenSach AS tenSanPham, SUM(d.soLuong) AS soLuong) " +
            "FROM DonHangChiTiet d " +
            "GROUP BY d.sach.tenSach " +
            "ORDER BY SUM(d.soLuong) DESC limit 10")
    List<TopSanPhamDTO> findTop10Products();
}
