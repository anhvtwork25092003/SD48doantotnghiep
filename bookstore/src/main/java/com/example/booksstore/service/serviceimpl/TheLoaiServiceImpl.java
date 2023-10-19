package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.TheLoai;
import com.example.booksstore.repository.TheLoaiRepository;
import com.example.booksstore.service.ITheLoaiServiec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheLoaiServiceImpl implements ITheLoaiServiec {
    @Autowired
    private TheLoaiRepository theLoaiRepository;


    @Override
    public TheLoai voitheloai(TheLoai theLoai) {
        return null;
    }

    @Override
    public List<TheLoai> fillAll() {
        return theLoaiRepository.findAll();
    }


}
