package com.example.booksstore.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UuDaiChiTietController {
    @GetMapping("/uu-dai-chi-tiet")
    public String HienThiTrangUuDaiChiTiet(){
        return "user/UuDaiChiTiet";
    }

}
