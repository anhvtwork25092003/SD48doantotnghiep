package com.example.booksstore.controller.admin.login;

import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.service.serviceimpl.NhanVienServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    NhanVienServiceImpl nhanVienService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login/sign_in";
    }
    @PostMapping("/login")
    public String login(@RequestParam String phone, @RequestParam String password, HttpSession session) {
        NhanVien nhanVien = nhanVienService.login(phone, password);
        if (nhanVien != null) {
            String role = nhanVienService.getNhanVienRole(nhanVien);
            if (role.equals("admin")) {
                // Điều hướng đến trang quản trị
                return "redirect:/admin/quanly/layoutchungquanly/menuQuanTri";
            } else if (role.equals("manager")) {
                // Điều hướng đến trang quản lý
                return "redirect:/admin/quanly/layoutchungquanly/menuQuanLy";
            } else if (role.equals("employee")) {
                // Điều hướng đến trang nhân viên
                return "redirect:/admin/quanly/layoutchungnhanvien/menuhanVien";
            }
        }
        // Đăng nhập không thành công, quay lại trang đăng nhập
        return "redirect:/login";
    }
}
