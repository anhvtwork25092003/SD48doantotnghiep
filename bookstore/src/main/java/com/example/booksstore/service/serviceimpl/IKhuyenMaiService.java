package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.KhuyenMai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IKhuyenMaiService {
    abstract Page<KhuyenMai> getAllKhuyenMaiTheoTrangThai(Pageable pageable, int trangThai);
}
