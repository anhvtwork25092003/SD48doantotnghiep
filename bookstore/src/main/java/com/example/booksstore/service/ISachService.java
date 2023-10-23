package com.example.booksstore.service;

import com.example.booksstore.entities.Sach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISachService {
    Page<Sach> pageOfSach(Pageable pageable);

    Sach save(Sach sach);
}
