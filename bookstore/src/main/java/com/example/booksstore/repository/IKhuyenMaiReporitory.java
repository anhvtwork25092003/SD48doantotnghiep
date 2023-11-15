package com.example.booksstore.repository;

import com.example.booksstore.entities.KhuyenMai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IKhuyenMaiReporitory extends JpaRepository<KhuyenMai, Integer> {

    // lấy chương trình khuyến mãi theo trạng thái
    Page<KhuyenMai> findAllByTrangThaiOrderByIdKhuyenMai(Pageable pageable, int trangThai);

    Page<KhuyenMai> findAllByTrangThaiHienThiOrderByIdKhuyenMaiDesc(Pageable pageable, int trangThaiHienThi);

    List<KhuyenMai> findAllByTrangThaiHienThiOrderByIdKhuyenMaiDesc( int trangThaiHienThi);

    List<KhuyenMai> findAllByTrangThaiOrderByIdKhuyenMaiDesc( int trangThaiHienThi);

    @Query("SELECT k FROM KhuyenMai k WHERE k.ngayBatDau < :thoigianketthuc AND k.ngayKetThuc > :thoigianbatdau")
    List<KhuyenMai> findByTimeRange(@Param("thoigianbatdau") Date thoigianketthuc, @Param("thoigianketthuc") Date thoigianbatdau);

    @Query("SELECT k FROM KhuyenMai k WHERE k.ngayBatDau < :thoigianketthuc AND k.ngayKetThuc > :thoigianbatdau AND k.idKhuyenMai <> :idKhuyenMai")
    List<KhuyenMai> findByTimeRangeUpDate(@Param("thoigianbatdau") Date thoigianketthuc, @Param("thoigianketthuc") Date thoigianbatdau, @Param("idKhuyenMai") int idKhuyenMai);


    Page<KhuyenMai> findAll(Specification<KhuyenMai> spec, Pageable pageable);

    KhuyenMai findByIdKhuyenMai(int idKhuyenMai);


}
