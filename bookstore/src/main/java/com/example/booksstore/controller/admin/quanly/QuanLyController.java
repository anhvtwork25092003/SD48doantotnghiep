package com.example.booksstore.controller.admin.quanly;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quanly")
public class QuanLyController {
    @GetMapping()
    public String hienThiTrangQuanLy (){
        return "/admin/quanly/layoutchungquanly/menuQuanLy";
    }
    @GetMapping("/tongquan")
    public String hienThiTrangTongQuanQuanLy (){
        return "/admin/quanly/TongQuanQuanLy";
    }
}
