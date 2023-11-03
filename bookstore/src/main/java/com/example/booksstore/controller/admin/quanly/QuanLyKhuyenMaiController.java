package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.KhuyenMai;
import com.example.booksstore.service.serviceimpl.IKhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/quan-ly")
public class QuanLyKhuyenMaiController {

    @Autowired
    IKhuyenMaiService iKhuyenMaiService;

    public String hienThiTrangKhuyenMai(Model model, @RequestParam(defaultValue = "1") int page) {


        int pageSize = 5; // Đặt kích thước trang mặc định
        Pageable pageable = PageRequest.of(page - 1, pageSize); // Số trang bắt đầu từ 0
        Page<KhuyenMai> khuyenMaiPages;
        khuyenMaiPages = iKhuyenMaiService.getAllKhuyenMaiTheoTrangThai(pageable, 1);

        return "/admin/quanly/KhuyenMai2";
    }

}
