package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.ThongTin;
import com.example.booksstore.repository.IThongTinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/thong-tin")
public class apiThongTin {
    @Autowired
    IThongTinRepository footerInfo;

    @GetMapping
    public ThongTin getFooterInfo() {
        // Truy vấn cơ sở dữ liệu hoặc xử lý logic để lấy thông tin footer
        ThongTin tt = footerInfo.findAll().get(0);
        return tt;
    }
}
