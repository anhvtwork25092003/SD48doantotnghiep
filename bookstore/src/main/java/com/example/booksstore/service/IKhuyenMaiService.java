package com.example.booksstore.service;

import com.example.booksstore.entities.KhuyenMai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IKhuyenMaiService {
    Page<KhuyenMai> getAllKhuyenMaiTheoTrangThai(Pageable pageable, int trangThai);

}
