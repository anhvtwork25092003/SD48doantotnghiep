package com.example.booksstore.repository;

import com.example.booksstore.entities.TacGia;
import com.example.booksstore.entities.TheLoai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TheLoaiRepository extends JpaRepository<TheLoai,Integer> {
//    @Query("select a from TheLoai a where a.IdTheLoai = ?1");
//    TheLoai findTheLoaiByID(Integer id);
}
