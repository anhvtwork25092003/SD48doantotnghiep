package com.example.booksstore.controller.admin.quantrivien;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class QuanTriController {
    @GetMapping("/tong-quan-quan-tri")
    public String quanLyTongQuan() {
        return "/admin/quanly/TongQuanQuanTri";
    }
}
