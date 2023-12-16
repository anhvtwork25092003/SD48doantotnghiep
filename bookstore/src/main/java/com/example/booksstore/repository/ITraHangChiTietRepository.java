package com.example.booksstore.repository;

import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.TraHangChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITraHangChiTietRepository extends JpaRepository<TraHangChiTiet, Integer> {
    Page<TraHangChiTiet> findAll(Pageable pageable);
}
