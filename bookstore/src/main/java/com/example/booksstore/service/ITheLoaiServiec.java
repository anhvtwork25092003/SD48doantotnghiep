package com.example.booksstore.service;

import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.TheLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface ITheLoaiServiec {
    Page<TheLoai> pageOfTheLoaiTheotrangthai(Pageable pageable,int trangThai);

    TheLoai creatTheLoai(TheLoai theLoai);

    TheLoai delete(Integer idtheloai);

    Page<TheLoai> searchTheLoai(String tentheloai,Integer trangthai, Pageable pageable);

    List<TheLoai>findAllTheLoai();

    TheLoai updateTrangThai(Integer IdTheLoai, Integer trangThaiUpdate);

    Optional<TheLoai> getOne(Integer IdTheLoai);

}
