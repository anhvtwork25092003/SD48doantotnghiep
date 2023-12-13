package com.example.booksstore.controller.admin.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

        @GetMapping("/logout")
        public String logout(HttpServletRequest request, HttpSession session) {
            // Xóa thông tin đăng nhập khỏi session
            session.removeAttribute("loggedInUser");
            // Hoặc session.invalidate() để xóa toàn bộ session

            // Chuyển hướng về trang đăng nhập hoặc trang chủ
            return "redirect:/trang-chu";
        }
    @GetMapping("/logout/nv")
    public String logoutnv(HttpServletRequest request, HttpSession session) {
        // Xóa thông tin đăng nhập khỏi session
        session.removeAttribute("dangnhapnhanvien");
        // Hoặc session.invalidate() để xóa toàn bộ session

        // Chuyển hướng về trang đăng nhập hoặc trang chủ
        return "redirect:/login";
    }

}
