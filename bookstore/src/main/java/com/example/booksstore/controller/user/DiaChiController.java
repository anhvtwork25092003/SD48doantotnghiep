package com.example.booksstore.controller.user;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/nguoi-dung")
public class DiaChiController {
    @GetMapping("/thong-tin-dia-chi")
    public String hienThiThongTinDiaChiGiaoHang(Model model
    ) {

        return null;
    }
}
