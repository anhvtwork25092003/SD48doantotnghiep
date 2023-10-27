package com.example.booksstore.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class UuDaiController {
    @GetMapping("/uu-dai")
    public String HienThiTrangUuDai(){
        return "user/UuDai";
    }

}
