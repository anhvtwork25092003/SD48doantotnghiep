package com.example.booksstore.repository;

import com.example.booksstore.entities.TraHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITraHangRepository extends JpaRepository<TraHang, Integer> {

    Page<TraHang> findAllByTrangThai(Pageable pageable, int trangThai);
}
