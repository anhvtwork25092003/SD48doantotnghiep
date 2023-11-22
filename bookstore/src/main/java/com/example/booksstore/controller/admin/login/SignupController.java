package com.example.booksstore.controller.admin.login;

import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.repository.IKhachHangRepository;
import com.example.booksstore.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sign-up")
public class SignupController {

    @Autowired
    private IKhachHangRepository iKhachHangRepository;

    @Autowired
    private EmailSenderService senderService;

    @GetMapping
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new KhachHang());
        return "user/login/sign_up";
    }

    @PostMapping
    public String signUp(@ModelAttribute("user") KhachHang khachHang, Model model) {
        // Check if email already exists
        KhachHang existingUser = iKhachHangRepository.findByEmail(khachHang.getEmail());
        if (existingUser != null) {
            model.addAttribute("error", "Email đã tồn tại");
            return "user/login/sign_up";
        }
        // Encode password (you should use a proper password encoder)
        khachHang.setEmail(khachHang.getEmail());
        khachHang.setMatKhau(khachHang.getMatKhau());
        khachHang.setHoVaTen(khachHang.getHoVaTen());
        khachHang.setSdt(khachHang.getSdt());
        khachHang.setTrangThai(1);
        KhachHang khachHang1 = iKhachHangRepository.findBySdt(khachHang.getSdt());
        if (khachHang1 != null) {
            model.addAttribute("error", "Sdt đã tồn taị");
            return "user/login/sign_up";
        }

        iKhachHangRepository.save(khachHang);
        senderService.sendSimpleEmail(khachHang.getEmail(), "Thông Tin Tài Khoản Của Bạn",
                "Họ Và Tên: " + khachHang.getHoVaTen() + "\n" +
                        "Email Đăng Ký: " + khachHang.getEmail() + "\n" +
                        "Số Điện Thoại: " + khachHang.getSdt() + "\n" +
                        "Mật Khẩu: " + khachHang.getMatKhau());
        return "redirect:/login/khach-hang";
    }

}
