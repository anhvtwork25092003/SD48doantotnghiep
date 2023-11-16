package com.example.booksstore.controller.admin.nhanvien;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NhanVienController {
    @GetMapping("/tong-quan-nhan-vien")
    public String nhanVienTongQuan() {
        return "/admin/nhanvien/layoutchungnhanvien/menuNhanVien";
    }
}
