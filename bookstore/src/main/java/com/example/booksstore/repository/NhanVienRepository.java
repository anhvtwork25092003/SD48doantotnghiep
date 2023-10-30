package com.example.booksstore.repository;

import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.entities.Sach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    Page<NhanVien> findAll(Pageable pageable);

    Page<NhanVien> findAll(Specification<NhanVien> spec, Pageable pageable);

    NhanVien findBySdt(String sdt);

    NhanVien findByIdNhanVien(int id);
}
