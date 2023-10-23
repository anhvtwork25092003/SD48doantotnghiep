package com.example.booksstore.service;

import com.example.booksstore.entities.DiaChi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDiaChiService {
    Page<DiaChi> finAll(Pageable pageable);
}
