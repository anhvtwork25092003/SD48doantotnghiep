package com.example.booksstore.controller.user;

import com.example.booksstore.entities.Sach;
import com.example.booksstore.service.ISachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TrangChuController {
    @Autowired
    ISachService iSachService;
    @GetMapping("/trang-chu")
    public String HienThiTrangChu(Model model){
        List<Sach> sachList = iSachService.sachmoi();
        List<Sach> saches = sachList.subList(0, 5);

        model.addAttribute("sachmoi", saches);

        return "user/TrangChu";
    }

}
