package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.SachTheLoai;
import com.example.booksstore.entities.TheLoai;
import com.example.booksstore.repository.TheLoaiRepository;
import com.example.booksstore.service.ITheLoaiServiec;
import com.example.booksstore.specification.SachSpecification;
import com.example.booksstore.specification.TheLoaiSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public TheLoai  delete(Integer idtheloai) {
        return theLoaiRepository.findTheLoaiByID(idtheloai);
    }

    @Override
    public Page<TheLoai> searchTheLoai(String tentheloai,Integer trangthai, Pageable pageable) {
        Specification<TheLoai> spec = TheLoaiSpecification.filterTheLoai(tentheloai,trangthai);
        return theLoaiRepository.findAll(spec,pageable);
    }


    @Override
    public List<TheLoai> findAllTheLoai() {
        return theLoaiRepository.findAll();
    }


}
