package com.example.booksstore.service;

import com.example.booksstore.entities.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IKhachHangService {
    Page<KhachHang> pageOfKhachHang(Pageable pageable);

    Page<KhachHang> searchKhachHang(String maKhachHang, String sdt, Integer trangThai, Pageable pageable);

    KhachHang save(KhachHang khachHang);

    List<KhachHang> findAll();

    KhachHang login(String sdt, String matkhau);
}
