package com.example.booksstore.repository;

import com.example.booksstore.entities.Sach;
import com.example.booksstore.entities.TacGia;
import com.example.booksstore.entities.TheLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheLoaiRepository extends JpaRepository<TheLoai,Integer> {
    Page<TheLoai> findAllByTrangThaiOrderByIdTheLoai(Pageable pageable,int trangThai);
    @Query("select a from TheLoai a where a.idTheLoai = ?1")
   TheLoai findTheLoaiByID(Integer IdTheLoai);
    Page<TheLoai> findAll(Specification<TheLoai> spec, Pageable pageable);


    List<TheLoai> findAllByTrangThai(int trangThai);
}
