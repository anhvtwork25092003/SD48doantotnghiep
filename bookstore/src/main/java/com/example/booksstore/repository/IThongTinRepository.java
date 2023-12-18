package com.example.booksstore.repository;


import com.example.booksstore.entities.ThongTin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IThongTinRepository extends JpaRepository<ThongTin, Integer> {
}
