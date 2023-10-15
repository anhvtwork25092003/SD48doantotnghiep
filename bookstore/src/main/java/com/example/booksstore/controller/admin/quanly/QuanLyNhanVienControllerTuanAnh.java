package com.example.booksstore.controller.admin.quanly;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quan-ly-nhan-vien")
public class QuanLyNhanVienControllerTuanAnh {
    @GetMapping()
    public String hienThiManHinhQuanlyNhanVien() {
        return "/user/nhanvien/thanh_nhanvien";
    }
}
