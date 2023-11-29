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

import java.util.List;

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

    @Override
    public KhachHang save(KhachHang khachHang) {
        return iKhachHangRepository.save(khachHang);
    }

    @Override
    public List<KhachHang> findAll() {
        return iKhachHangRepository.findAll();
    }

    @Override
    public KhachHang login(String email, String matkhau) {
        KhachHang khachHang = iKhachHangRepository.findByEmail(email);
        if (khachHang != null && khachHang.getMatKhau().equals(matkhau)) {
            if (khachHang.getTrangThai().equals(1)) {
                return khachHang;
            }
        }
        return null;
    }

    @Override
    public KhachHang detail(Integer idKhachHang) {
        return iKhachHangRepository.getById(idKhachHang);
    }

}
