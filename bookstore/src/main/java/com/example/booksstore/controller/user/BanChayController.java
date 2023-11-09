package com.example.booksstore.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BanChayController {
    @GetMapping("/ban-chay")
    public String HienThiTrangBanChay(){
        return "user/trang_chu_xem_them";
    }
}
