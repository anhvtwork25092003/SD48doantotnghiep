package com.example.booksstore.service;

import com.example.booksstore.entities.TacGia;

import java.util.List;

public interface TacGiaService {
    TacGia createTacGia(TacGia tacgia);

    TacGia GetTacGiaByID(Integer id);

    void delete(TacGia tacGia);

    List<TacGia> findAllTacGia();

    TacGia UpdateTacGia(Integer id,TacGia tacGia);
}
