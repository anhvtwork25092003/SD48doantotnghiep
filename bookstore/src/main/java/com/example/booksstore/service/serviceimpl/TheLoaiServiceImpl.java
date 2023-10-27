package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.TheLoai;
import com.example.booksstore.repository.TheLoaiRepository;
import com.example.booksstore.service.ITheLoaiServiec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheLoaiServiceImpl implements ITheLoaiServiec {
    @Autowired
    private TheLoaiRepository theLoaiRepository;


    @Override
    public Page<TheLoai> pageOfTheLoai(Pageable pageable) {
        return theLoaiRepository.findAll(pageable);
    }

    @Override
    public TheLoai creatTheLoai(TheLoai theLoai) {
       return theLoaiRepository.save(theLoai);
    }

    @Override
    public TheLoai GetTheLoaiByID(Integer id) {
        return theLoaiRepository.findTheLoaiByID(id);
    }

    @Override
    public List<TheLoai> findAllTheLoai() {
        return theLoaiRepository.findAll();
    }


}
