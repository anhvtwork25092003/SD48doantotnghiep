package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.SachTacGia;
import com.example.booksstore.repository.SachTacGiaRepository;
import com.example.booksstore.service.SachTacGiaService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Repository
public class SachTacGiaServiceimpl implements SachTacGiaService {
    private SachTacGiaRepository sachre;



    @Override
    public List<SachTacGia> getAll() {
        List<SachTacGia> s1=sachre.findAll();
        return s1;
    }
}
