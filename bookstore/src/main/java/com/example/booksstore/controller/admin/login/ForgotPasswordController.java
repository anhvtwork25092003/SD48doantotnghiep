package com.example.booksstore.controller.admin.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgotPasswordController {

        @GetMapping("/sign_up")
        public String showForgotPasswordForm() {
            return "user/login/sign_up";
        }

        @PostMapping("/sign_up")
        public String processForgotPassword(@RequestParam("email") String email, Model model) {
            // Xử lý quên mật khẩu ở đây, gửi email xác nhận hoặc thay đổi mật khẩu
            // ...
            // Đoạn mã trong phương thức processForgotPassword của controller

            // Thông báo cho người dùng rằng họ sẽ nhận được một email hướng dẫn
            model.addAttribute("message", "Chúng tôi đã gửi hướng dẫn khôi phục mật khẩu vào địa chỉ email của bạn.");
            return "user/login/sign_up";
        }
    }

