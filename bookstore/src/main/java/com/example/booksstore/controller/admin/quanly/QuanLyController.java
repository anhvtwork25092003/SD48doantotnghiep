package com.example.booksstore.controller.admin.quanly;

import com.example.booksstore.entities.NhanVien;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuanLyController {
    @GetMapping("/tong-quan-quan-ly")
    public String quanLyTongQuan(HttpSession session, Model model) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("loggedInUser");
        if (nhanVien != null) {
            model.addAttribute("loggedInUser", nhanVien);
            return "/admin/quanly/TongQuanQuanLy";
        }
      return "redirect:/login";
    }
}
