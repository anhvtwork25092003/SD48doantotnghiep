package com.example.booksstore.service;

import com.example.booksstore.entities.TacGia;
import com.example.booksstore.entities.TheLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ITheLoaiServiec {
    Page<TheLoai> pageOfTheLoai(Pageable pageable);

    TheLoai creatTheLoai(TheLoai theLoai);

    TheLoai GetTheLoaiByID(Integer id);

    List<TheLoai>findAllTheLoai();

}
