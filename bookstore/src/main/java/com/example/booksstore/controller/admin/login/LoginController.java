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
    public String login(@RequestParam("sdt") String phone, @RequestParam("matKhau") String password, HttpSession session) {
        NhanVien nhanVien = nhanVienService.login(phone, password);
        if (nhanVien != null) {
            session.setAttribute("loggedInUser",nhanVien);
            String role = nhanVienService.getNhanVienRole(nhanVien);
            if (role.equals("admin")) {
                // Điều hướng đến trang quản trị
                return "redirect:/tong-quan-quan-tri";
            } else if (role.equals("manager")) {
                // Điều hướng đến trang quản lý
                return "redirect:/tong-quan-quan-ly";
            } else if (role.equals("employee")) {
                // Điều hướng đến trang nhân viên
                return "redirect:/tong-quan-nhan-vien";
            }
        }
        return "redirect:/login";
    }
    @GetMapping("/login/khach-hang")
    public String showLoginFormkh() {
        return "user/login/sign_inkh";
    }
    @PostMapping("/loginskh")
    public String loginkh(@RequestParam("sdt") String phone, @RequestParam("matKhau") String password, HttpSession session) {
        KhachHang khachHang = khachHangService.login(phone, password);
        if (khachHang != null) {
            session.setAttribute("loggedInUser",khachHang);
            return "redirect:/trang-chu";
        }
        return "redirect:/login/khach-hang";
    }
}
