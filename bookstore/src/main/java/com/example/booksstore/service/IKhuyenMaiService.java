package com.example.booksstore.service;

import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.entities.Sach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IKhuyenMaiService {
    Page<KhuyenMai> getAllKhuyenMaiTheoTrangThai(Pageable pageable, int trangThai);

    KhuyenMai SaveOrUpdateKhuyenMai(KhuyenMai khuyenMai);

    Map<String, Object> checkPromotion(Long idSach);

    KhuyenMai updateTrangThai(int IdKhuyenMai, int trạngThaiUpdate);

    List<String> layThongTinSachTrongKhuyenMai(Set<Sach> sachs, Date thoigianbatdau, Date thoigianketthuc);
    List<String> layThongTinSachTrongKhuyenMaiChoUpdate(Set<Sach> sachs, Date thoigianbatdau, Date thoigianketthuc, int IdKhuyenMai);

    Page<KhuyenMai> searchKhuyenMai(String tenKhuyenMai,Date ngayBatDau, Date ngayKetThuc, Integer trangThai, Pageable pageable);

}
