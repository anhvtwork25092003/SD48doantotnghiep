package com.example.booksstore.repository;

import com.example.booksstore.entities.DiaChi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDiaChiRepository extends JpaRepository<DiaChi, Integer> {
    Page<DiaChi> findAll(Pageable pageable);
}
