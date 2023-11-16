package com.example.booksstore.controller.user;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gio-hang")
public class GioHangController {
    
    @GetMapping("/danh-sach-san-pham")
    public String xemDanhSachSanPhamTrongGioHang(){

        // lấy ra khách hàng và giỏ hàng và
        return null;
    }
}
