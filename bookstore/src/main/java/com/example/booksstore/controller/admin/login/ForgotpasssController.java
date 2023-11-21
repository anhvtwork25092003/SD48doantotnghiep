package com.example.booksstore.controller.admin.login;

import com.example.booksstore.entities.KhachHang;
import com.example.booksstore.repository.IKhachHangRepository;
import com.example.booksstore.service.EmailSenderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/forgotpass")
public class ForgotpasssController {
    @Autowired
    private IKhachHangRepository iKhachHangRepository;
    @Autowired
    private EmailSenderService senderService;

    @GetMapping
    public String showsendotpForm(Model model) {
        return "user/login/forgot_password";
    }

    @GetMapping("/matkhau")
    public String showforgotForm(Model model) {

        return "user/login/QuenMatKhau";
    }

    @PostMapping
    public String sendOtp(@RequestParam("email") String email, HttpSession session, Model model) {
        KhachHang existingUser = iKhachHangRepository.findByEmail(email);
        if (existingUser == null) {
            model.addAttribute("error", "Không có tài khoản phù hợp email");
            return "user/login/forgot_password";
        }
        String otp = generateOtp();

        // Lưu OTP vào session
        session.setAttribute("otp", otp);
        session.setAttribute("email", email);

        // Gửi OTP đến email
        senderService.sendSimpleEmail(email, "OTP Lấy quên mật khẩu", "Mã OTP của bạn là:" + "" + otp);

        return "redirect:/forgotpass/matkhau";
    }

    @PostMapping("/matkhau")
    public String forgotpass( @RequestParam("otp") String otp, @RequestParam("passwordcomfin") String passwordcomfin,@RequestParam("matKhau") String matKhau, HttpSession session, Model model) {
        String emailll = (String) session.getAttribute("email");
        String otpp = (String) session.getAttribute("otp");
        KhachHang existingKhachHang = iKhachHangRepository.findByEmail(emailll);
System.out.println(existingKhachHang.getEmail());
        // Validate OTP
        if (otp == null || otp.trim().isEmpty() || !otp.equals(otpp)) {
            model.addAttribute("error", "OTP không đúng hoặc rỗng");
            System.out.println("Lỗi rt");
            return "user/login/QuenMatKhau";
        }

        if (!passwordcomfin.equals(matKhau)) {
            model.addAttribute("error", "Mật khẩu không đồng dạng");
            System.out.println("Loi r");
            return "user/login/QuenMatKhau";
        }
        existingKhachHang.setMatKhau(passwordcomfin);
        iKhachHangRepository.save(existingKhachHang);
        model.addAttribute("success", "Mật khẩu đã được cập nhật thành công");
        return "redirect:/login/khach-hang";

    }

    private String generateOtp() {
        // Generate a random 6-digit OTP
        return String.valueOf(100000 + (int) (Math.random() * 900000));
    }

}
