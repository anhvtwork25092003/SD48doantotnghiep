package com.example.booksstore.repository;

import com.example.booksstore.entities.Sach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ISachRepository extends JpaRepository<Sach ,Integer> {
    Page<Sach> findAll(Pageable pageable);
    Page<Sach> findAll(Specification<Sach> spec, Pageable pageable);
    Sach findByIdSach(int id);
}
