package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.Sach;
import com.example.booksstore.service.ISachService;
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
@RequestMapping("/quan-ly")
public class QuanLySachController {

    @Autowired
    ISachService iSachService;

    @GetMapping("/sach/hien-thi")
    public String hienThiTrangTongQuanQuanLy(Model model, @RequestParam(defaultValue = "1") int page ){

        int pageSize = 2; // Đặt kích thước trang mặc định

        Pageable pageable = PageRequest.of(page - 1, pageSize); // Số trang bắt đầu từ 0

        Page<Sach> pageOfSach = iSachService.pageOfSach(pageable);

        model.addAttribute("pageOfSach", pageOfSach);

        return "user/sanpham/sanpham";
    }

}
