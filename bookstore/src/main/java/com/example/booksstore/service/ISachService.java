package com.example.booksstore.service;

import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.TacGia;
import com.example.booksstore.entities.TheLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ISachService {
    Page<Sach> pageOfSach(Pageable pageable);

    Sach save(Sach sach);

    Page<Sach> searchSach(String tenSach, String maSach, BigDecimal giaMin, BigDecimal giaMax, Set<TheLoai> theLoai, Integer trangThai, Pageable pageable);

    Page<Sach> TimKiemSach(String tenSach, BigDecimal giaMin, BigDecimal giaMax, Set<TheLoai> theLoai, Set<TacGia> tacGias, String sapXepGia, Pageable pageable);

    Sach getOne(int id);

    Sach getOneByMaVach(String maVach);

    List<Sach> sachmoi();

    List<Sach> getall();

    Sach dateil(Integer idSach);
}
