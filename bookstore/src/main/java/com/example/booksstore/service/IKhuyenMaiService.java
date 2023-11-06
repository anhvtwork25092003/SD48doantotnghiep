package com.example.booksstore.service;

import com.example.booksstore.entities.KhuyenMai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface IKhuyenMaiService {
    Page<KhuyenMai> getAllKhuyenMaiTheoTrangThai(Pageable pageable, int trangThai);

    KhuyenMai SaveOrUpdateKhuyenMai(KhuyenMai khuyenMai);

    Map<String, Object> checkPromotion(Long idSach);

    KhuyenMai updateTrangThai(int IdKhuyenMai, int trạngThaiUpdate);
}
