package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.TheLoai;
import com.example.booksstore.repository.ISachRepository;
import com.example.booksstore.service.ISachService;
import com.example.booksstore.specification.SachSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class SachServiceImpl implements ISachService {

    @Autowired
    ISachRepository iSachRepository;
    @Override
    public Page<Sach> pageOfSach(Pageable pageable) {
        return iSachRepository.findAll(pageable);
    }

    @Override
    public Sach save(Sach sach){
        return iSachRepository.save(sach);
    }

    @Override
    public Page<Sach> searchSach(String tenSach, String maSach, BigDecimal giaMin, BigDecimal giaMax, Set<TheLoai> theLoai, Integer trangThai, Pageable pageable) {
        Specification<Sach> spec = SachSpecification.filterSach(tenSach, maSach, giaMin, giaMax, theLoai, trangThai);
        return iSachRepository.findAll(spec, pageable);
    }

    @Override
    public Sach getOne(int id) {
        return iSachRepository.findByIdSach(id);
    }

    @Override
    public Sach getOneByMaVach(String maVach) {
        return iSachRepository.findSachByMaVach(maVach);
    }

    @Override
    public List<Sach> sachmoi() {
        return iSachRepository.findAllByOrderByIdSachDesc();
    }

    @Override
    public List<Sach> getall() {
        List<Sach> s=iSachRepository.findAll();
        return s;
    }

    @Override
    public Sach dateil(Integer idSach) {
        return iSachRepository.getById(idSach);
    }
}
