package com.example.booksstore.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PayController {
    @GetMapping("/pay")
    public String HienThiTrangUuDaiLon(Model model){
        return "user/pay";
    }
}
