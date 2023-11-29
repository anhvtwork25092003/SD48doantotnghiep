package com.example.booksstore.controller.user;

import com.example.booksstore.entities.DiaChi;
import com.example.booksstore.entities.DonHang;
import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.repository.IDonHangRepo;
import com.example.booksstore.repository.IKhachHangRepository;
import com.example.booksstore.service.IKhachHangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LichSuDonHangController {
    @Autowired
    IDonHangRepo iDonHangRepo;

    @GetMapping("/lich-su-don-hang/hien-thi")
    public String hienThiTrangDiaChi(Model model ,HttpSession session) {
        KhachHang khachHang = (KhachHang) session.getAttribute("loggedInUser");
        List<DonHang> danhSachdonHang = iDonHangRepo.findAllBykhachHang(khachHang);
        model.addAttribute("donHanglist", danhSachdonHang);
        return "user/lichsudonhang";
    }



}
