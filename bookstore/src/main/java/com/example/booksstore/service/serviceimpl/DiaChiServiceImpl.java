package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.DiaChi;
import com.example.booksstore.repository.IDiaChiRepository;
import com.example.booksstore.service.IDiaChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DiaChiServiceImpl implements IDiaChiService {

    @Autowired
    private IDiaChiRepository iDiaChiRepository;
    @Override
    public Page<DiaChi> finAll(Pageable pageable) {
        return iDiaChiRepository.findAll(pageable);
    }
}
