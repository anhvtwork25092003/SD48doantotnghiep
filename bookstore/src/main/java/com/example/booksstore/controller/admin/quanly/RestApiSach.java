package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.repository.ISachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sach")
public class RestApiSach {

    @Autowired
    ISachRepository iSachRepository;

    @GetMapping("/danh-sach-ma-vach")
    public List<String> layDanhSachMaVach() {
        List<String> danhSachMaVach = iSachRepository.findAllMaVach();
        if (danhSachMaVach == null || danhSachMaVach.size() == 0) {
            return null;
        } else {
            return danhSachMaVach;
        }
    }
}
