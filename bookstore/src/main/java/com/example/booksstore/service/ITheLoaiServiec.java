package com.example.booksstore.service;

import com.example.booksstore.entities.TheLoai;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ITheLoaiServiec {
    TheLoai voitheloai(TheLoai theLoai);

    List<TheLoai> fillAll();

}
