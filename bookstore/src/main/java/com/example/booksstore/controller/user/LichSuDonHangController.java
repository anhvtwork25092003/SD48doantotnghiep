package com.example.booksstore.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LichSuDonHangController {



    @GetMapping("/lich-su-don-hang/hien-thi")
    public String lichsudonhanghienthi(Model model){
        return "user/lichsudonhang";
    }


}
