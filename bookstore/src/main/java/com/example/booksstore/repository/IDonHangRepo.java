package com.example.booksstore.repository;

import com.example.booksstore.dto.TopSanPhamDTO;
import com.example.booksstore.entities.DonHang;
import com.example.booksstore.entities.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IDonHangRepo extends JpaRepository<DonHang, Integer> {
    @Query("SELECT dh FROM DonHang dh WHERE dh.khachHang = :khachHang ORDER BY dh.ngayTao DESC")
    Page<DonHang> findAllByKhachHangOrderByNgayTaoDesc(@Param("khachHang") KhachHang khachHang, Pageable pageable);

//    Page<DonHang> findAllBykhachHang(Pageable pageable,KhachHang khachHang);
    Page<DonHang> findAllByTrangThaiOrderByIdDonHang(Pageable pageable, int trangThai);
    Page<DonHang> findByMaDonHangContainingAndKhachHang_SdtContaining(Pageable pageable,String maDonHang, String sdt);
    List<DonHang> findByMaDonHangContaining(String maDonHang);
    DonHang findByIdDonHang (int idDonHang);

    List<DonHang> findByNgayThanhToanBetween(Date startTime, Date endTime);

    List<DonHang> findByNgayThanhToan(Date ngayThanhToan);

    @Query("SELECT new com.example.booksstore.dto.TopSanPhamDTO(dhct.sach.tenSach, SUM(dhct.soLuong)) " +
            "FROM DonHangChiTiet dhct " +
            "GROUP BY dhct.sach.tenSach " +
            "ORDER BY SUM(dhct.soLuong) DESC")
    List<TopSanPhamDTO> findTopSanPhamBanChayNhat();

}