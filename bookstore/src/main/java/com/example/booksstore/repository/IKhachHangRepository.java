package com.example.booksstore.repository;

import com.example.booksstore.entities.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface IKhachHangRepository extends JpaRepository<KhachHang, Integer> {
    Page<KhachHang> findAll(Pageable pageable);

    Page<KhachHang> findAll(Specification<KhachHang> spec, Pageable pageable);

    KhachHang findByEmail(String email);

    KhachHang findBySdt(String sdt);

    List<KhachHang> findAllByNgayTaoTaiKhoanBetween(Date startTime, Date endTime);
}
