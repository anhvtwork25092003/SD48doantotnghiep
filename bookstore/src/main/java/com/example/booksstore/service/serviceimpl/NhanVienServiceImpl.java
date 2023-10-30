package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.entities.Sach;
import com.example.booksstore.repository.NhanVienRepository;
import com.example.booksstore.service.INhanVienService;
import com.example.booksstore.specification.NhanVienSpecification;
import com.example.booksstore.specification.SachSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class NhanVienServiceImpl implements INhanVienService {
    @Autowired
    NhanVienRepository nhanVienRepository;

    @Override
    public NhanVien login(String sdt, String matkhau) {
        NhanVien nhanVien = nhanVienRepository.findBySdt(sdt);
        if (nhanVien != null && nhanVien.getMatKhau().equals(matkhau)) {
            if (nhanVien.getTrangThai().equals(1)) {
                return nhanVien;
            }
        }
        return null;
    }

    @Override
    public String getNhanVienRole(NhanVien nhanVien) {
        return nhanVien.getChucVu();
    }

    @Override
    public Page<NhanVien> pageOfNhanVien(Pageable pageable) {
        return nhanVienRepository.findAll(pageable);
    }

    @Override
    public NhanVien save(NhanVien nhanVien) {
        return nhanVienRepository.save(nhanVien);
    }

    @Override
    public Page<NhanVien> searchNhanVien(String maNhanVien, String tenNhanVien, Integer trangThai, Pageable pageable) {
        Specification<NhanVien> spec = NhanVienSpecification.filterNhanVien(maNhanVien,tenNhanVien,trangThai);
        return nhanVienRepository.findAll(spec, pageable);

    }

    @Override
    public NhanVien getOne(int id) {
        return nhanVienRepository.findByIdNhanVien(id);
    }


}
