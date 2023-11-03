package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.repository.IKhuyenMaiReporitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class KhuyenMaiServiceImpl  implements  IKhuyenMaiService{

    @Autowired
    IKhuyenMaiReporitory iKhuyenMaiReporitory;
     @Override
     public Page<KhuyenMai> getAllKhuyenMaiTheoTrangThai(Pageable pageable, int trangThai){
         return  this.iKhuyenMaiReporitory.findAllByTrangThaiOrderByIdKhuyenMai(pageable, trangThai);
     }
}
