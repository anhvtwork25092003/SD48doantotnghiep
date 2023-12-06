package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.NhanVien;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quan-ly")
public class QuanLyController {
    @GetMapping("/tong-quan")
    public String quanLyTongQuan(HttpSession session, Model model) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien != null) {
            if (nhanVien.getChucVu().equalsIgnoreCase("Quan ly")) {
                model.addAttribute("loggedInUser", nhanVien);
                return "/admin/quanly/TongQuanQuanLy";
            } else {
                return "redirect:/quan-ly/sach/hien-thi";
            }
        }
        return "redirect:/login";
    }
}
