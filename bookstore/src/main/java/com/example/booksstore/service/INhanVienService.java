package com.example.booksstore.service;

import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.entities.Sach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface INhanVienService {
    NhanVien login(String sdt, String matkhau);

    String getNhanVienRole(NhanVien nhanVien);

    Page<NhanVien> pageOfNhanVien(Pageable pageable);

    NhanVien save(NhanVien nhanVien);

    Page<NhanVien> searchNhanVien(String maNhanVien, String tenNhanVien, Integer trangThai, Pageable pageable);

    NhanVien getOne(int id);
}
