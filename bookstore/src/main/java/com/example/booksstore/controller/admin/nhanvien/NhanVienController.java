package com.example.booksstore.controller.admin.nhanvien;

import com.example.booksstore.entities.NhanVien;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NhanVienController {
    @GetMapping("/tong-quan-nhan-vien")
    public String nhanVienTongQuan(Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("dangnhapnhanvien");
        if (nhanVien != null) {
            model.addAttribute("loggedInUser", nhanVien);
//            return "/admin/nhanvien/layoutchungnhanvien/menuNhanVien";
//            return "/admin/quanly/DonHangCho";
            return "redirect:/quan-ly/don-hang/cho-xac-nhan";
        }
        return "redirect:/login";
    }


    @GetMapping("/logout-nhan-vien")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInUser");
        return "redirect:/login";
    }
}
