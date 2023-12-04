package com.example.booksstore.controller.user;

import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.repository.IThongBaoKhachHangRepo;
import jakarta.servlet.http.HttpSession;
import jdk.jfr.Registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/thong-bao")
public class testThongBaoLan2 {

    @Autowired
    private IThongBaoKhachHangRepo thongBaoKhachHangRepository;

    @GetMapping
    public String hienThiTrangTestThongBao(Model model, HttpSession session) {
        KhachHang khachHangDangNhap = (KhachHang) session.getAttribute("loggedInUser");
        if (khachHangDangNhap != null) {
            model.addAttribute("idThongBao", khachHangDangNhap.getIdKhachHang());
        }
        return "/user/testThongBao";
    }
}
