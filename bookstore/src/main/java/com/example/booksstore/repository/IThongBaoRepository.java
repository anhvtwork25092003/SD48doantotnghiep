package com.example.booksstore.repository;

import com.example.booksstore.entities.ThongBao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IThongBaoRepository  extends JpaRepository<ThongBao, Integer> {

}
