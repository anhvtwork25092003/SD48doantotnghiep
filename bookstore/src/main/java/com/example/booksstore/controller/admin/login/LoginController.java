package com.example.booksstore.controller.admin.login;

import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.entities.NhanVien;
import com.example.booksstore.service.serviceimpl.KhachHangServiceImpl;
import com.example.booksstore.service.serviceimpl.NhanVienServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    NhanVienServiceImpl nhanVienService;

    @Autowired
    KhachHangServiceImpl khachHangService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login/sign_in";
    }

    @PostMapping("/logins")
    public String login(@RequestParam("email") String email, @RequestParam("matKhau") String password, HttpSession session) {
        NhanVien nhanVien = nhanVienService.login(email, password);
        if (nhanVien != null) {
            session.setAttribute("dangnhapnhanvien", nhanVien);
            String role = nhanVienService.getNhanVienRole(nhanVien);
            if (role.equals("Quan tri")) {
                // Điều hướng đến trang quản trị
                return "redirect:/quan-ly/tong-quan";
            } else if (role.equals("Quan ly")) {
                // Điều hướng đến trang quản lý
                return "redirect:/quan-ly/tong-quan";
            } else if (role.equals("Nhan vien")) {
                // Điều hướng đến trang nhân viên
                return "redirect:/quan-ly/don-hang/cho-xac-nhan";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/login/khach-hang")
    public String showLoginFormkh() {
        return "user/login/sign_inkh";
    }

    @PostMapping("/loginskh")
    public String loginkh(@RequestParam("email") String email, @RequestParam("matKhau") String password, HttpSession session) {
        KhachHang khachHang = khachHangService.login(email, password);
        if (khachHang != null) {
            session.setAttribute("loggedInUser", khachHang);
            return "redirect:/trang-chu";
        }
        return "redirect:/login/khach-hang";
    }
}
