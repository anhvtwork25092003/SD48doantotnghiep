package com.example.booksstore.repository;

import com.example.booksstore.entities.KhuyenMai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IKhuyenMaiReporitory  extends JpaRepository<KhuyenMai, Integer> {

    // lấy chương trình khuyến mãi theo trạng thái
    Page<KhuyenMai> findAllByTrangThaiOrderByIdKhuyenMai(Pageable pageable, int trangThai);

}
