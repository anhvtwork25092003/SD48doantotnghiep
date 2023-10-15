package com.example.booksstore.controller.admin.quanly;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quan-ly")
public class QuanLyController {
    @GetMapping("/tong-quan")
    public String quanLyTongQuan() {
        return "/admin/quanly/TongQuanQuanLy";
    }
}
