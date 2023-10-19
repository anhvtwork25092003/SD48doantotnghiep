package com.example.booksstore.service.serviceimpl;

import com.example.booksstore.entities.Sach;
import com.example.booksstore.repository.ISachRepository;
import com.example.booksstore.service.ISachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SachServiceImpl implements ISachService {

    @Autowired
    ISachRepository iSachRepository;
    @Override
    public Page<Sach> pageOfSach(Pageable pageable) {
        return iSachRepository.findAll(pageable);
    }
}
