package com.example.booksstore.repository;

import com.example.booksstore.entities.TacGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TacGiaRepository extends JpaRepository<TacGia,Integer> {
    @Query("select a from TacGia a where a.idTacGia = ?1")
    TacGia findTacGiaByID(Integer id);
}
