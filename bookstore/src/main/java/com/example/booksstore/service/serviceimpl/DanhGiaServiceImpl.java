package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.DanhGia;
import com.example.booksstore.repository.IDanhGiarepository;
import com.example.booksstore.service.IDanhGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class DanhGiaServiceImpl implements IDanhGiaService {
    @Autowired
    IDanhGiarepository iDanhGiarepository;
    @Override
    public void capnhatdanhgia(Integer idDanhGia) {

        Optional<DanhGia> optionalDanhGia = iDanhGiarepository.findById(idDanhGia);
        if (optionalDanhGia.isPresent()) {
            DanhGia danhGia = optionalDanhGia.get();
            danhGia.setTrangThai(1);
            iDanhGiarepository.save(danhGia);
        }
    }

    @Override
    public void xoadanhgia(Integer idDanhGia) {
        Optional<DanhGia> optionalDanhGia = iDanhGiarepository.findById(idDanhGia);
        if (optionalDanhGia.isPresent()) {
            DanhGia danhGia = optionalDanhGia.get();
            danhGia.setTrangThai(2);
            iDanhGiarepository.save(danhGia);
        }
    }
}
