package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.DiaChi;
import com.example.booksstore.service.IDiaChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dia-chi")
public class QuanLyDiaChiController {

    @Autowired
    IDiaChiService iDiaChiService;

    @GetMapping("/hien-thi")
    public String hienThiTrangDiaChi(Model model, @RequestParam(defaultValue = "1") int page){
        int pageSize = 10; //đặt kích thước trang mặc định
        Pageable pageable = PageRequest.of(page -1, pageSize);
        Page<DiaChi> pageOfDiaChi = iDiaChiService.finAll(pageable);
        model.addAttribute("pageOfDiaChi", pageOfDiaChi);
        return "user/diachinhanhang";
    }
}
