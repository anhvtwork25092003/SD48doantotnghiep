package com.example.booksstore.service;

import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.TheLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


public interface ITheLoaiServiec {
    Page<TheLoai> pageOfTheLoai(Pageable pageable);

    TheLoai creatTheLoai(TheLoai theLoai);

    TheLoai GetTheLoaiByID(Integer id);

    Page<TheLoai> searchTheLoai(String tentheloai, Pageable pageable);

    List<TheLoai>findAllTheLoai();

}
