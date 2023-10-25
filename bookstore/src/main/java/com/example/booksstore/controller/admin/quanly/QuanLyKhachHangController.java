package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.service.IDiaChiService;
import com.example.booksstore.service.IKhachHangService;
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
public class QuanLyKhachHangController {

    @Autowired
    IKhachHangService iKhachHangService;

    @Autowired
    IDiaChiService iDiaChiService;

    @GetMapping("/khach-hang/hien-thi")
    public String hienThiTrangQuanLyKhachHang(Model model, @RequestParam(defaultValue = "1") int page){
        int pageSize = 2; //Đặt kích thước trang

        Pageable pageable = PageRequest.of(page - 1, pageSize); // số trang bắt đầu từ 0

        Page<KhachHang> pageOfKhachHang = iKhachHangService.pageOfKhachHang(pageable);
        model.addAttribute("pageOfKhachHang", pageOfKhachHang);
        model.addAttribute("listDiaChi", iDiaChiService.finAll(pageable));
        return "admin/quanly/KhachHang";
    }

}
