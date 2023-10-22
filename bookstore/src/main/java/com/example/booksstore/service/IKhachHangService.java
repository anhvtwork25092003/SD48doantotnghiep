package com.example.booksstore.service;

import com.example.booksstore.entities.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IKhachHangService {
    Page<KhachHang> pageOfKhachHang(Pageable pageable);
}
