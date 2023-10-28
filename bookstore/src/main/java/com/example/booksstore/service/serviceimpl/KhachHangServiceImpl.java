package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.repository.IKhachHangRepository;
import com.example.booksstore.service.IKhachHangService;
import com.example.booksstore.specification.KhachHangSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class KhachHangServiceImpl implements IKhachHangService {

    @Autowired
    IKhachHangRepository iKhachHangRepository;
    @Override
    public Page<KhachHang> pageOfKhachHang(Pageable pageable) {
        return iKhachHangRepository.findAll(pageable);
    }

    @Override
    public Page<KhachHang> searchKhachHang(String maKhachHang, String sdt, Integer trangThai, Pageable pageable) {
         Specification<KhachHang> spec = KhachHangSpecification.filterKhachHang(maKhachHang,sdt, trangThai);
         return iKhachHangRepository.findAll(spec, pageable);
    }
}
