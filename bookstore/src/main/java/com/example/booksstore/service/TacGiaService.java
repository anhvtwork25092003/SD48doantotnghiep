package com.example.booksstore.service;

import com.example.booksstore.entities.TacGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TacGiaService {
    Page<TacGia> pageOfTacGia(Pageable pageable);
    TacGia createTacGia(TacGia tacgia);

    TacGia GetTacGiaByID(Integer id);

    void delete(TacGia tacGia);

    List<TacGia> findAllTacGia();

    TacGia UpdateTacGia(Integer id,TacGia tacGia);
}
