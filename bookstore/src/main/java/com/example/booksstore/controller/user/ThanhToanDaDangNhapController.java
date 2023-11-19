package com.example.booksstore.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThanhToanDaDangNhapController {
    @GetMapping("/thanh-toan-da-dang-nhap")
    public String chiTietKhuyenMai(Model model) {
        return "user/ThanhToanDaDangNhap";
    }

}
