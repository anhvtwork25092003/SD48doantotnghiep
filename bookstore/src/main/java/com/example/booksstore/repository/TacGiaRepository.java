package com.example.booksstore.repository;

import com.example.booksstore.entities.TacGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TacGiaRepository extends JpaRepository<TacGia,Integer> {
    Page<TacGia> findAll(Pageable pageable);
    @Query("select a from TacGia a where a.idTacGia = ?1")
    TacGia findTacGiaByID(Integer id);
    Page<TacGia> findAll(Specification<TacGia> spec, Pageable pageable);

}
