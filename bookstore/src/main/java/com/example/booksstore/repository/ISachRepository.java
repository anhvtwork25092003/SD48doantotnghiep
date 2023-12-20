package com.example.booksstore.repository;

import com.example.booksstore.entities.Sach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISachRepository extends JpaRepository<Sach, Integer> {
    Page<Sach> findAll(Pageable pageable);

    Page<Sach> findAll(Specification<Sach> spec, Pageable pageable);

    @Query("SELECT s FROM Sach s WHERE s.trangThai = 1")
    List<Sach> findAllSachDangHoatDong();

    Sach findByIdSach(int id);

    Page<Sach> findAllByTrangThaiOrderByIdSachDesc(int trangThai, Pageable pageable);



    Sach findSachByMaVach(String maVach);
    Sach findSachByMaVachNot(String maVach);

    @Query("SELECT s.maVach FROM Sach s")
    List<String> findAllMaVach();
}
